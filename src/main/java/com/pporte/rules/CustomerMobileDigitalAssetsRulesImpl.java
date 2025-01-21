package com.pporte.rules;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.stellar.sdk.KeyPair;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.CustomerDao;
import com.pporte.dao.CustomerDigitalAssetsDao;
import com.pporte.dao.CustomerDigitalCurrenciesDao;
import com.pporte.dao.CustomerMobilePorteCoinDao;
import com.pporte.dao.CustomerPorteCoinDao;
import com.pporte.dao.CustomerWalletDao;
import com.pporte.dao.OpsManagePricingPlanDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.dao.WalletAuthorizationDao;
import com.pporte.model.PricingPlan;
import com.pporte.model.Transaction;
import com.pporte.model.User;
import com.pporte.utilities.Bip39Utility;
import com.pporte.utilities.CurrencyTradeUtility;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CustomerMobileDigitalAssetsRulesImpl implements Rules {
	private static String className = CustomerMobileDigitalAssetsRulesImpl.class.getSimpleName();
	@Override
	public void performJSONOperation(String arg0, HttpServletRequest arg1, HttpServletResponse arg2,
			ServletContext arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		
		switch (rulesaction) {
		case "buy_Mobile_btcx_using_fiat":
			try {
				JsonObject obj = new JsonObject(); String customerCharges = null;
				User user = null; String minimumTxnAmount = null; PrintWriter output = null;
				String relationshipNo= null; String langPref=null;
				Gson gson = new Gson(); String amount= null; String channel =null; // T= Tokenized Card, F=Fiat Wallet, B =Bank  
				Boolean success = false; 	String walletBalance = null; String txnUserCode = null;
				String walletDetails =null; String fiatWalletId=null; 	String btcxWalletId=null; //This is either porte token or vessel coin
				String payComments=""; 	String referenceNo=""; String tokenId = null;
				String assetCode = null; String txnPayMode = null; String extSystemRef = null;
				String cvv = null; String amountBTCx=null; //Get this from stellar
				String authStatus=""; String authMessage=""; String walletId=null; String authResponse=null;
				boolean recordAuthorization=false; String currencyId = null; String transactionCode=null;
				String txnMode=null; String userType=null;
				String tokenValue = null; List<Transaction> listTxn = null;

				
				if(request.getParameter("coin_asset")!=null) assetCode = request.getParameter("coin_asset").trim();
				if(request.getParameter("receivedamountbtcx")!=null) amount = request.getParameter("receivedamountbtcx").trim();
				if(request.getParameter("amount")!=null)	 	amountBTCx = request.getParameter("amount").trim();
				if(request.getParameter("comment")!=null)	 payComments = request.getParameter("comment").trim();
				if(request.getParameter("payment_method")!=null) channel = request.getParameter("payment_method").trim();
				if(request.getParameter("relno")!=null) relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

				//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es válido, vuelva a iniciar sesión");

					}else {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					}
					return;
				}
				if(channel.equalsIgnoreCase("T")) {
				if(request.getParameter("tokenid")!=null)	
						tokenId = request.getParameter("tokenid").trim();
					if(request.getParameter("cvv")!=null)	
						cvv = request.getParameter("cvv").trim();
				}			
				NeoBankEnvironment.setComment(1, className, "amountBTCx "+amountBTCx);
				NeoBankEnvironment.setComment(1, className, "amount "+amount);
				btcxWalletId = (String)CustomerPorteCoinDao.class.getConstructor()
						.newInstance().getAssetWalletDetails(relationshipNo, assetCode, assetCode);
				/*if(walletDetails==null) {
					Utilities.sendJsonResponse(response, "error", "Dear customer your dont have coresponding wallet, "
							+ "please create wallet and try again");
					return;
				}
				btcxWalletId = walletDetails.split(",")[0];*/
				txnUserCode = Utilities.generateTransactionCode(10);
				if(channel.equalsIgnoreCase("T")) {
					//Debit card here
					//Call Gate way here
					//Get charges 
					txnPayMode = NeoBankEnvironment.getCodeBuyBTCxViaToken();
					
					referenceNo = txnPayMode+ "-" + (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
							+ Utilities.genAlphaNumRandom(9);
					customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
							NeoBankEnvironment.getDefaultCustomerUserType(),txnPayMode, amount);
					 minimumTxnAmount=customerCharges.substring(customerCharges.indexOf("|")+1, customerCharges.length());
					
					if ( Double.parseDouble(amount)< Double.parseDouble(minimumTxnAmount)) { 
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "error", "El monto de la transacción no puede ser inferior a "+minimumTxnAmount);
							throw new Exception("cantidad mínima cantidad ingresada:- El mínimo esperado es:- "+minimumTxnAmount+"Amount inputed is"+amount);
						}else {
							Utilities.sendJsonResponse(response, "error", "Transaction amount can not be less than "+minimumTxnAmount);
							throw new Exception("minimumAmount amount inputed:- Minimum exptected is:- "+minimumTxnAmount+"Amount inputed is"+amount);						}
						
					}
					extSystemRef=Utilities.genAlphaNumRandom(24).toUpperCase(); //Remove this after integration
					NeoBankEnvironment.setComment(3,className,"tokenId is "+tokenId);
					success = (Boolean) CustomerDigitalCurrenciesDao.class.getConstructor().newInstance().
							buyBTCxCoinViaToken(relationshipNo, tokenId, amount, payComments, referenceNo, 
									txnUserCode, customerCharges, txnPayMode, assetCode, extSystemRef, btcxWalletId, amountBTCx);
					
				}
				if(channel.equalsIgnoreCase("F")) {
					walletDetails = (String)CustomerWalletDao.class.getConstructor()
							.newInstance().getFiatWalletBalance(relationshipNo);
					fiatWalletId = walletDetails.split(",")[0];
					walletBalance = walletDetails.split(",")[1];
					txnMode="D";// Debit
					userType="C";
					currencyId = NeoBankEnvironment.getUSDCurrencyId();
					transactionCode=(new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
							+ Utilities.genAlphaNumRandom(9);
					txnPayMode = NeoBankEnvironment.getCodeBuyBTCxViaFiat();
					referenceNo = txnPayMode+ "-" +transactionCode;
					customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
							NeoBankEnvironment.getDefaultCustomerUserType(),txnPayMode, amount);
					 minimumTxnAmount=customerCharges.substring(customerCharges.indexOf("|")+1, customerCharges.length());
					
					if ( Double.parseDouble(amount)< Double.parseDouble(minimumTxnAmount)) { 
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "error", "El monto de la transacción no puede ser inferior a "+minimumTxnAmount);

						}else {
							Utilities.sendJsonResponse(response, "error", "Transaction amount can not be less than "+minimumTxnAmount);
						}
						return;
					}
					//Check balance // this will be captured in the authorization module once we plugin
					double senderDebitAmount =  Double.parseDouble(amount) + 
							Double.parseDouble(customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|")));
					if ( senderDebitAmount> Double.parseDouble(walletBalance)) {
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "error", "Estimado cliente, no tiene fondos suficientes. Por favor, recargue y vuelva a intentarlo.");

						}else {
							Utilities.sendJsonResponse(response, "error", "Dear customer you have insufficient funds, Kindly top up and try again");
						}
						return;
					}
					
					/****** Wallet Authorization******/
					
					authResponse=(String)WalletAuthorizationDao.class.getConstructor().newInstance().performWalletAuthorization(relationshipNo, amount, txnMode);
					if (authResponse.isEmpty()) {
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo más tarde");

						}else {
							Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
						}
						return;
					}
					authStatus=authResponse.substring(0,authResponse.indexOf(","));
					authMessage=authResponse.substring(authResponse.indexOf(",")+1,authResponse.indexOf("|"));
					walletId=authResponse.substring(authResponse.indexOf("|")+1,authResponse.length());
			
					if (authStatus.equals("S")==false) {
						//Authorization failed
						// Record failed authorization
						
						recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, amount, txnMode, currencyId, 
								userType, referenceNo, walletId, authStatus, authMessage);
						if(!recordAuthorization) { 
							if(langPref.equalsIgnoreCase("ES")) {
								Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo más tarde");

							}else {
								Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
							}
						return;
						}
						     Utilities.sendJsonResponse(response, "authfailed", authMessage);
						return;
					}else {
						// Record successful authorization
						recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, amount, txnMode, currencyId, 
								userType, referenceNo, walletId, authStatus, authMessage);
						if(!recordAuthorization) { 
							if(langPref.equalsIgnoreCase("ES")) {
								Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo más tarde");

							}else {
								Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
							}
						return;
						}
					}
					/****** End of Wallet Authorization******/	
					success = (Boolean) CustomerDigitalCurrenciesDao.class.getConstructor().newInstance().buyBTCxViaFiatWallet(
							relationshipNo, fiatWalletId, amount, payComments, referenceNo, txnUserCode, txnPayMode,
							assetCode, extSystemRef, btcxWalletId, customerCharges, amountBTCx,transactionCode);
				}
				if (success) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, "C",
							txnPayMode," Buy BTCx Using fiat money ");
					listTxn = (List<Transaction>)CustomerMobilePorteCoinDao.class.getConstructor().
							newInstance().getPendingTransactions(relationshipNo);
					
					
					obj.add("error", gson.toJsonTree("false"));
					if(langPref.equalsIgnoreCase("ES")) {
						obj.add("message", gson.toJsonTree(" Su transacción de "+assetCode +":"+ amountBTCx + 
								" se está procesando te avisaremos una vez finalizada la operación"));
					}else {
						obj.add("message", gson.toJsonTree(" Your Transaction of "+assetCode +":"+ amountBTCx + 
								" is being processed we will notify you once the operation is done "));					}
					 
					obj.add("data", gson.toJsonTree(listTxn));

				}else {
					obj.add("error", gson.toJsonTree("true"));
					if(langPref.equalsIgnoreCase("ES")) {
						obj.add("message", gson.toJsonTree("Transacción fallida")); 

					}else {
						obj.add("message", gson.toJsonTree("Transaction failed")); 
					}
				}
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));
				} finally {
					if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
					if (obj != null)obj = null; if (minimumTxnAmount != null)minimumTxnAmount = null;
					if (txnUserCode != null)txnUserCode = null; if (channel != null)channel = null;
					if (btcxWalletId != null)btcxWalletId = null; if (tokenId != null)tokenId = null;
					if (assetCode != null)assetCode = null; 
					if (txnPayMode != null)txnPayMode = null; if (extSystemRef != null)extSystemRef = null;
					if (amount != null)amount = null; if (langPref != null)langPref = null; 
					if (user != null)user = null; 
					if (walletBalance != null)walletBalance = null; 
					if (walletDetails != null)walletDetails = null; 
					if (fiatWalletId != null)fiatWalletId = null; 
					if (payComments != null)payComments = null; 
					if (referenceNo != null)referenceNo = null; 
					if (currencyId!=null); currencyId=null;   if (walletId!=null); walletId=null; if (authResponse != null)authResponse = null;
					if (transactionCode!=null); transactionCode=null; if (userType!=null); userType=null;  if (txnMode!=null); txnMode=null; 
					if (cvv != null)cvv = null; if (tokenValue != null)tokenValue = null;
					if (listTxn!=null)listTxn=null;
				}								
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in coin purchase, Please try again letter");
			}
		break;
		case "check_if_customer_mobile_has_mnemonic_code":
			try {
				String relationshipNo = null;
				String hasMnemonicCode = null;
				JsonObject obj = new JsonObject();
				PrintWriter out_json = response.getWriter();
				if(request.getParameter("relno")!=null) relationshipNo = request.getParameter("relno").trim();
				NeoBankEnvironment.setComment(3,className,"Relationship number is "+relationshipNo);
				
				hasMnemonicCode = (String) CustomerDao.class.getConstructor().newInstance()
						.checkIfUserHasMnemonicCode(relationshipNo);
				
				if(hasMnemonicCode.equalsIgnoreCase("Y")) {
					obj.addProperty("error", "false");
					obj.addProperty("hasmnemonic", "true");
				}else {
					obj.addProperty("error", "false");
					obj.addProperty("hasmnemonic", "false");
				}
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + (obj.toString()));
					out_json = response.getWriter();
					out_json.print((obj));
				} finally {
					if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; 
					if (hasMnemonicCode != null)hasMnemonicCode = null; if (obj != null) obj = null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Check failed , Try again");
			}
			break;
		case "mobile_exchange_btcx_with_porte_coin":
			try {
				JsonObject obj = new JsonObject(); User user = null; String sourceAsset= null;String langPref=null;
				String destAssetCode= null; PrintWriter output = null; Gson gson = new Gson();
				String sourceAssetIssuer= null;  String destinationAssetIssuer= null; String sourceAmount= null;
				String destinationAmount= null;  String sourceAssetType= null; String relationshipNo = null;
				String hasMnemonic = null; String password  = null; String mnemoniStringFromDB  = null;
				boolean passIsCorrect = false; KeyPair keyPair = null; String privateKey = null;
				String tdaAccount = null; boolean success = false; String result = "";String authMethod = null;
				String payType = NeoBankEnvironment.getCodeExchangePorteAssetForBTCx();
				String internalReference = null; String sourceAcountId  = null; String tokenValue = null;
				List<Transaction> listTxn = null;
				
				
				if(request.getParameter("source_asset")!=null)	 sourceAsset = request.getParameter("source_asset").trim();
				if(request.getParameter("destination_asset")!=null)	 destAssetCode = request.getParameter("destination_asset").trim();
				if(request.getParameter("source_amount")!=null)	 sourceAmount = request.getParameter("source_amount").trim();
				if(request.getParameter("destination_amount")!=null)	 destinationAmount = request.getParameter("destination_amount").trim();
				if(request.getParameter("relno")!=null) relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("hasMnemonic")!=null)	 hasMnemonic = request.getParameter("hasMnemonic").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

				//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es válido, vuelva a iniciar sesión");

					}else {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					}
					return;
				}
				
				NeoBankEnvironment.setComment(3, className, "sourceAsset "+sourceAsset+" destAssetCode "+destAssetCode+" soureAmount "+sourceAmount
						+" destinationAmount "+destinationAmount+" relationshipNo "+relationshipNo+" hasMnemonic "+hasMnemonic);
				if(hasMnemonic.equals("true")) {
					if(request.getParameter("auth_method")!=null)	 authMethod = request.getParameter("auth_method").trim();
					if(authMethod.equals("P")) {
						if(request.getParameter("security")!=null)	 password = request.getParameter("security").trim();
						passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
								.checkIfPasswordIsCorrect(relationshipNo, password);
						if(!passIsCorrect) {
							NeoBankEnvironment.setComment(1, className, "Password is not correct");
							if(langPref.equalsIgnoreCase("ES")) {
								Utilities.sendJsonResponse(response, "error", "Por favor ingrese la contraseña correcta");

							}else {
								Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
							}
							return;
						}
					}
					mnemoniStringFromDB = (String) CustomerDao.class.getConstructor().newInstance()
							.getmnemonicCode(relationshipNo);
					keyPair= Bip39Utility.masterKeyGeneration(mnemoniStringFromDB.replaceAll(",", " "));
					privateKey = String.valueOf(keyPair.getSecretSeed());
				}else {
					if(request.getParameter("security")!=null)	 privateKey = request.getParameter("security").trim();
				}
				
				
				
				if(sourceAsset.equals(NeoBankEnvironment.getPorteTokenCode())) {
					sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
							.newInstance().getIssueingAccountPublicKey(sourceAsset);
				}else if(sourceAsset.equals(NeoBankEnvironment.getVesselCoinCode())) {
					sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
							.newInstance().getIssueingAccountPublicKey(sourceAsset);
				}else if(sourceAsset.equals(NeoBankEnvironment.getUSDCCode())) {
					sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
							.newInstance().getIssueingAccountPublicKey(sourceAsset);
				}else if(sourceAsset.equals(NeoBankEnvironment.getXLMCode())) {
					sourceAssetType="native";
					sourceAssetIssuer="";
				}
				
				sourceAcountId = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
				if(sourceAcountId == null)
					if(langPref.equalsIgnoreCase("ES")) {
						throw new Exception("El cliente no tiene una cuenta estelar.");

					}else {
						throw new Exception("Customer does not have a Stellar account.");
					}
				
				KeyPair source = KeyPair.fromSecretSeed(privateKey);
				NeoBankEnvironment.setComment(3, className, "privateKey  "+ privateKey+" sourceAssetIssuer "+
						sourceAssetIssuer+"  account from pvt "+ source.getAccountId());
				if(!source.getAccountId().equals(sourceAcountId)) {
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "true", "La clave secreta es incorrecta");

					}else {
						Utilities.sendJsonResponse(response, "true", "Secret Key is incorrect");
					}
					return;
				}
				
				tdaAccount = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().
						getAssetDistributionAccount(destAssetCode);
				KeyPair sourceAccount  = null;
				KeyPair destinationAccount = null;
				if(sourceAsset.equals(NeoBankEnvironment.getXLMCode())) {
					result = CurrencyTradeUtility.sendNativeCoinPayment(tdaAccount, privateKey, sourceAmount);
				}else {			
					sourceAccount = KeyPair.fromSecretSeed(privateKey);
					destinationAccount = KeyPair.fromAccountId(tdaAccount);
					result = CurrencyTradeUtility.sendNoNNativeCoinPayment(sourceAsset, sourceAccount, 
							destinationAccount, sourceAmount, "", sourceAssetIssuer);
				}
				
				if(result.split(",")[0].equals("success")) {
					internalReference =  NeoBankEnvironment.getCodeExchangePorteAssetForBTCx()+ "-" 
				   + (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
							+ Utilities.genAlphaNumRandom(9);
					success = (Boolean) CustomerDigitalAssetsDao.class.getConstructor().newInstance().
							insertBTCExchange(payType, relationshipNo, sourceAsset, sourceAmount, 
									destAssetCode, destinationAmount, internalReference, result.split(",")[1]);
				}else {
					if(langPref.equalsIgnoreCase("ES")) {
						throw new Exception("Error en la transferencia de activos");

					}else {
						throw new Exception("Error in transferring assets");
					}
				}
				if (success) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, "C",
							payType," Exchanged "+sourceAsset+" to  BTCx ");
					
					
					listTxn = (List<Transaction>)CustomerDigitalCurrenciesDao.class.getConstructor().newInstance().getPendingBTCxSwapping(relationshipNo);

					obj.add("data", gson.toJsonTree(listTxn));
					obj.add("error", gson.toJsonTree("false"));
					if(langPref.equalsIgnoreCase("ES")) {
						obj.add("message", gson.toJsonTree("Su transacción de "+destAssetCode +":"+ destinationAmount + 
								" se está procesando te avisaremos una vez finalizada la operación ")); 
					}else {
						obj.add("message", gson.toJsonTree(" Your Transaction of "+destAssetCode +":"+ destinationAmount + 
								" is being processed we will notify you once the operation is done ")); 					}
					
				}else {
					obj.add("error", gson.toJsonTree("true")); 
					if(langPref.equalsIgnoreCase("ES")) {
						obj.add("message", gson.toJsonTree("Transacción fallida")); 

					}else {
						obj.add("message", gson.toJsonTree("Transaction failed")); 
					}
				}

				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" Response is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));
				} finally {
					if (hasMnemonic != null)hasMnemonic = null; 
					if (password != null)password = null; if(authMethod!=null) authMethod= null; 
					if (mnemoniStringFromDB != null)mnemoniStringFromDB = null; 
					if (keyPair != null)keyPair = null;
					if (langPref != null)langPref = null;
					if (obj != null)obj = null; if (user != null)user = null; if (sourceAsset != null)sourceAsset = null;
					if (destAssetCode != null)destAssetCode = null; if(output!= null)output.close(); if (gson != null)gson = null;
					if (sourceAssetIssuer != null)sourceAssetIssuer = null; if (destinationAssetIssuer != null)destinationAssetIssuer = null; 
					if (sourceAssetType != null)sourceAssetType = null; if (relationshipNo != null)relationshipNo = null; 
					if (payType != null)payType = null; if (internalReference != null)internalReference = null; 
					if (sourceAcountId != null)sourceAcountId = null;
					if (privateKey != null)privateKey = null;if (tdaAccount != null)tdaAccount = null; if (result != null)result = null;
					if (sourceAccount != null)sourceAccount = null;if (destinationAccount != null)destinationAccount = null; 
					if (source != null)source = null; if (destinationAmount != null)destinationAmount = null; 
					if (tokenValue != null)tokenValue = null;if (listTxn != null)listTxn = null;
					
				}
					
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in getting exchanging assets to BTCx, Please try again letter");
			}
			break;
		
		case "view_mbl_pending_btcx_swapping_txn":
			NeoBankEnvironment.setComment(2, className, "Inside view_mbl_pending_btcx_swapping_txn");
			try {
				JsonObject obj = new JsonObject();
				User user = null;
				String relationshipNo= null;
				PrintWriter output = null;
				List<Transaction> listTxn = null;
				Gson gson = new Gson();
				String txnDesc= null;
				if(request.getParameter("relno")!=null)	 relationshipNo = request.getParameter("relno").trim();

				NeoBankEnvironment.setComment(3,className,"Relationship number is "+relationshipNo);
				listTxn = (List<Transaction>)CustomerDigitalCurrenciesDao.class.getConstructor().newInstance().getPendingBTCxSwapping(relationshipNo);
				

				if(listTxn!=null) {
					NeoBankEnvironment.setComment(3,className,"listTxn size is "+listTxn.size());
					obj.add("data", gson.toJsonTree(listTxn));
					obj.add("error", gson.toJsonTree("false"));
				}else {
					obj.add("error", gson.toJsonTree("nodata"));
					obj.add("message", gson.toJsonTree("There is no data"));
				}
				
				
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));
				} finally {
					if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
					if (obj != null)obj = null;
					if (listTxn != null)listTxn = null; 
					if (user != null)user = null; 
					if (txnDesc != null)txnDesc = null; 
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in geting Transactions, Please try again letter");
			}
			break;
		case"getplandetails":
			try {
				JsonObject obj = new JsonObject();
				User user = null;String langPref=null;
				String relationshipNo= null;
				PrintWriter output = null;
				ArrayList <PricingPlan> listTxn = null;
				Gson gson = new Gson();
				String txnDesc= null; String tokenValue = null;
				
				if(request.getParameter("relno")!=null)	 relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

				//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es válido, vuelva a iniciar sesión");

					}else {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					}
					return;
				}
				NeoBankEnvironment.setComment(3,className,"Relationship number is "+relationshipNo);
				listTxn = (ArrayList<PricingPlan>)OpsManagePricingPlanDao.class.getConstructor().newInstance().getCustomerPricingPlan();
				
				if(listTxn!=null) {
					NeoBankEnvironment.setComment(3,className,"listTxn size is "+listTxn.size());
					obj.add("data", gson.toJsonTree(listTxn));
					obj.add("error", gson.toJsonTree("false"));
				}else {
					obj.add("error", gson.toJsonTree("true"));
					if(langPref.equalsIgnoreCase("ES")) {
						obj.add("message", gson.toJsonTree("No hay datos"));

					}else {
						obj.add("message", gson.toJsonTree("There is no data"));
					}
				}
				
				
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));
				} finally {
					if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
					if (obj != null)obj = null;
					if (langPref != null)langPref = null;
					if (listTxn != null)listTxn = null; 
					if (user != null)user = null; if (tokenValue != null)tokenValue = null;
					if (txnDesc != null)txnDesc = null; 
				}
				
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error, Please try again letter");
			}
			break;
		case"getsilverplandetails":
			try {
				JsonObject obj = new JsonObject();
				User user = null;String langPref=null;
				String relationshipNo= null;
				PrintWriter output = null;
				ArrayList <PricingPlan> listTxn = null;
				Gson gson = new Gson();
				String txnDesc= null; String tokenValue = null;
				
				if(request.getParameter("relno")!=null)	 relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

				//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es válido, vuelva a iniciar sesión");

					}else {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					}
					return;
				}
				NeoBankEnvironment.setComment(3,className,"Relationship number is "+relationshipNo);
				listTxn = (ArrayList<PricingPlan>)OpsManagePricingPlanDao.class.getConstructor().newInstance().getCustomerSilverPricingPlan();
				
				if(listTxn!=null) {
					NeoBankEnvironment.setComment(3,className,"listTxn size is "+listTxn.size());
					obj.add("data", gson.toJsonTree(listTxn));
					obj.add("error", gson.toJsonTree("false"));
				}else {
					obj.add("error", gson.toJsonTree("true"));
					if(langPref.equalsIgnoreCase("ES")) {
						obj.add("message", gson.toJsonTree("No hay datos"));

					}else {
						obj.add("message", gson.toJsonTree("There is no data"));
					}
				}
				
				
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));
				} finally {
					if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
					if (obj != null)obj = null; if (tokenValue != null)tokenValue = null;
					if (listTxn != null)listTxn = null; 
					if (langPref != null)langPref = null; 
					if (user != null)user = null; 
					if (txnDesc != null)txnDesc = null; 
				}
				
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error, Please try again letter");
			}
			break;
		}
		
		
	}

	@Override
	public void performOperation(String rules, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx)
			throws Exception {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null) 
			Utilities.callException(request, response, ctx, "Session has expired, please log in again");
		switch (rules) {

				
		
		}
		
	}

}
