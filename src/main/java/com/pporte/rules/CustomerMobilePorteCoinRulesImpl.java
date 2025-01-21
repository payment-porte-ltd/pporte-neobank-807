package com.pporte.rules;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.stellar.sdk.KeyPair;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.CustomerCoinsDao;
import com.pporte.dao.CustomerDao;
import com.pporte.dao.CustomerDigitalAssetsDao;
import com.pporte.dao.CustomerExternalCoinDao;
import com.pporte.dao.CustomerMobilePorteCoinDao;
import com.pporte.dao.CustomerPorteCoinDao;
import com.pporte.dao.CustomerWalletDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.dao.TDAManagementDao;
import com.pporte.dao.WalletAuthorizationDao;
import com.pporte.model.AssetAccount;
import com.pporte.model.Transaction;
import com.pporte.model.AssetTransaction;
import com.pporte.model.AssetTransactions;
import com.pporte.model.User;
import com.pporte.utilities.Bip39Utility;
import com.pporte.utilities.StellarSDKUtility;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;

public class CustomerMobilePorteCoinRulesImpl implements Rules {
	private static String className = CustomerMobilePorteCoinRulesImpl.class.getSimpleName();

	@Override
	public void performJSONOperation(String arg0, HttpServletRequest arg1, HttpServletResponse arg2,
			ServletContext arg3) throws Exception {
		
	}

	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		
		switch (rulesaction) {
			case "get_porte_coins_detailsmbl":
				
				try {
					
					JsonObject obj = new JsonObject();
					PrintWriter output = null;
					Gson gson = new Gson();String langPref=null;
					User user = null;
					String relationshipNo = null;KeyPair assetKeyPair = null;
					ArrayList<AssetAccount> assetCoinDetails = null;
					String tokenValue = null;
					if(request.getParameter("relno")!=null)	 relationshipNo = request.getParameter("relno").trim();
					if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
					if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

					//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
					if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
						NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v�lido, vuelva a iniciar sesi�n");
						}else {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
						}
						return;
					}

					//get assets						 
					String publicKey = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
					assetKeyPair = KeyPair.fromAccountId(publicKey);
					assetCoinDetails = StellarSDKUtility.getAccountBalance(assetKeyPair);

					if(assetCoinDetails!=null) {
						obj.add("publickey", gson.toJsonTree(publicKey));
						obj.add("data", gson.toJsonTree(assetCoinDetails));
						obj.add("error", gson.toJsonTree("false"));
					}
					else {
						obj.add("error", gson.toJsonTree("true"));
					}					
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						output = response.getWriter();
						output.print(gson.toJson(obj));
					} finally {
						if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						if (obj != null)obj = null; if (tokenValue != null)tokenValue = null;
						if (assetCoinDetails != null)assetCoinDetails = null; 
						if (user != null)user = null; 
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in getting porte coins, Please try again letter");
				}
			break;
			case "get_last_five_porte_txnmbl":
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" inside get_last_five_porte_txnmbl ");

					JsonObject obj = new JsonObject();
					User user = null; String tokenValue = null;
					String relationshipNo= null;String langPref=null;
					PrintWriter output = null;
					ArrayList<AssetTransactions> arrTransactions = null;
					Gson gson = new Gson();
					String txnDesc= null;
					if(request.getParameter("relno")!=null)	 relationshipNo = request.getParameter("relno").trim();
					if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
					if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

					//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
					if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
						NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v�lido, vuelva a iniciar sesi�n");

						}else {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
						}
						return;
					}

					NeoBankEnvironment.setComment(3, className, " get_last_five_porte_txnmbl "+relationshipNo );

					String accountId = (String)CustomerMobilePorteCoinDao.class.getConstructor() .newInstance().getAssetAccountId(relationshipNo);
					arrTransactions = StellarSDKUtility.getAccountTransactions(accountId, "5");
					if(arrTransactions!=null) {
						obj.add("data", gson.toJsonTree(arrTransactions));
						obj.add("error", gson.toJsonTree("false"));
					}else {
						obj.add("error", gson.toJsonTree("true"));
					}
					
					
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						output = response.getWriter();
						output.print(gson.toJson(obj));
					} finally {
						if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						if (obj != null)obj = null;
						if (arrTransactions != null)arrTransactions = null; 
						if (user != null)user = null; if (tokenValue != null)tokenValue = null;
						if (txnDesc != null)txnDesc = null; 
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in geting Transactions, Please try again letter");
				}
				break;
			case "get_crypto_assets_detailsmbl":
				
				try {
					JsonObject obj = new JsonObject();
					PrintWriter output = null;  String relationshipNo= null;

					Gson gson = new Gson();
					if(request.getParameter("relno")!=null)	 relationshipNo = request.getParameter("relno").trim();
					List<AssetAccount> cryptoAssetCoinForUser = null;
					/*List<AssetAccount> cryptoAssetCoinForUser =(List<AssetAccount>)
							CustomerPorteCoinDao.class.getConstructor().newInstance().getPorteAssetDetailsForCustomer(relationshipNo);
					*/
					
					String publicKey = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
					KeyPair userAccount = KeyPair.fromAccountId(publicKey);
					cryptoAssetCoinForUser = StellarSDKUtility.getAccountBalance(userAccount);

					obj.add("data", gson.toJsonTree(cryptoAssetCoinForUser));
					obj.add("error", gson.toJsonTree("false"));
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						output = response.getWriter();
						output.print(gson.toJson(obj));
					} finally {
						if (output != null)output.close();  if (gson != null)gson = null;
						if (obj != null)obj = null;
						if (cryptoAssetCoinForUser != null)cryptoAssetCoinForUser = null; 
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in geting crypto assets, Please try again letter");
				}
			break;
			case "get_crypto_assets_detailsmbl_without_btc":
				
				try {
					JsonObject obj = new JsonObject();String langPref=null;
					PrintWriter output = null;  String relationshipNo= null;
					Gson gson = new Gson(); String tokenValue = null;
					if(request.getParameter("relno")!=null)	 relationshipNo = request.getParameter("relno").trim();
					if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
					if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

					//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
					if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
						NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v�lido, vuelva a iniciar sesi�n");

						}else {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
						}
						return;
					}
					List<AssetAccount> cryptoAssetCoinForUser = null;
					/*List<AssetAccount> cryptoAssetCoinForUser =(List<AssetAccount>)
							CustomerPorteCoinDao.class.getConstructor().newInstance().getPorteAssetDetailsForCustomer(relationshipNo);
					*/
					
					String publicKey = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
					KeyPair userAccount = KeyPair.fromAccountId(publicKey);
					cryptoAssetCoinForUser = StellarSDKUtility.getAccountBalanceWithNoBTC(userAccount);

					obj.add("data", gson.toJsonTree(cryptoAssetCoinForUser));
					obj.add("error", gson.toJsonTree("false"));
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						output = response.getWriter();
						output.print(gson.toJson(obj));
					} finally {
						if (output != null)output.close();  if (gson != null)gson = null;
						if (obj != null)obj = null; if (tokenValue != null)tokenValue = null;
						if (cryptoAssetCoinForUser != null)cryptoAssetCoinForUser = null; 
						if (langPref != null)langPref = null; 
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in geting crypto assets, Please try again letter");
				}
			break;
			case "get_crypto_assets_details_offramp":
				
				try {
					JsonObject obj = new JsonObject(); String tokenValue = null;
					PrintWriter output = null;  String relationshipNo= null;String langPref=null;

					Gson gson = new Gson();
					if(request.getParameter("relno")!=null)	 relationshipNo = request.getParameter("relno").trim();
					if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
					if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

					//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
					if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
						NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v�lido, vuelva a iniciar sesi�n");

						}else {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
						}
						return;
					}
					List<AssetAccount> cryptoAssetCoinForUser = null;
					/*List<AssetAccount> cryptoAssetCoinForUser =(List<AssetAccount>)
							CustomerPorteCoinDao.class.getConstructor().newInstance().getPorteAssetDetailsForCustomer(relationshipNo);
					*/
					
					String publicKey = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
					KeyPair userAccount = KeyPair.fromAccountId(publicKey);
					cryptoAssetCoinForUser = StellarSDKUtility.getAccountOffRampAssets(userAccount);

					obj.add("data", gson.toJsonTree(cryptoAssetCoinForUser));
					obj.add("error", gson.toJsonTree("false"));
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						output = response.getWriter(); 
						output.print(gson.toJson(obj));
					} finally {
						if (output != null)output.close();  if (gson != null)gson = null;
						if (obj != null)obj = null;if (tokenValue != null)tokenValue = null;
						if (cryptoAssetCoinForUser != null)cryptoAssetCoinForUser = null; 
						if (langPref != null)langPref = null; 
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in geting crypto assets, Please try again letter");
				}
			break;
			case "buy_porte_coin_mbl":
				try {
					JsonObject obj = new JsonObject(); String customerCharges = null;
					User user = null; String minimumTxnAmount = null;
					String relationshipNo= null; double newWalletBalanceFromStellar = 0;
					PrintWriter output = null;String langPref=null;
					Gson gson = new Gson();
					String amount= null; //Amount In usd
					String channel =null; // T= Tokenized Card, F=Fiat Wallet, B =Bank  
					Boolean success = false;
					String walletBalance = null;
					String txnUserCode = null;
					String walletDetails =null;
					String fiatWalletId=null;
					String porteWalletId=null; //This is either porte token or vessel coin
					String payComments=""; String tokenValue = null;
					String referenceNo="";
					String tokenId = null;
					String assetCode = null;
					String txnPayMode = null;
					String extSystemRef = null;
					String cvv = null;
					String amountPorte=null; //Get this from stellar
					String authStatus=""; String authMessage=""; String walletId=null; String authResponse=null;
					boolean recordAuthorization=false; String currencyId = null; String transactionCode=null;
					String txnMode=null;String userType=null;
					List<Transaction> listTxn = null;
				
					
					if(request.getParameter("buy_coin_asset")!=null)	 assetCode = request.getParameter("buy_coin_asset").trim();
					if(request.getParameter("receivedamountbuy")!=null)	 amountPorte = request.getParameter("receivedamountbuy").trim();
					if(request.getParameter("buy_amount")!=null)	 	 amount = request.getParameter("buy_amount").trim();
					if(request.getParameter("buy_payment_method")!=null) channel = request.getParameter("buy_payment_method").trim();
					if(channel.equalsIgnoreCase("T")) {
						if(request.getParameter("tokenid")!=null)	 tokenId = request.getParameter("tokenid").trim();
						if(request.getParameter("cvv")!=null)	 	cvv = request.getParameter("cvv").trim();
						NeoBankEnvironment.setComment(3, className, "inside buy_porte_coin_mbl card selected tokenId "+ tokenId + " cvv "+ cvv );
					}
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

					NeoBankEnvironment.setComment(3, className, "inside buy_porte_coin_mbl assetCode "+assetCode
							+" amount  "+ amount + " amountPorte "+ amountPorte + " channel "+ channel+ " relationshipNo "+ relationshipNo );
					
//					String []assetCodeValues = assetCode.split(",");
//					assetCode = assetCodeValues[1];
					//walletId = assetCodeValues[0];
					NeoBankEnvironment.setComment(3, className, "assetCode value is "+assetCode );					
		
					walletDetails = (String)CustomerMobilePorteCoinDao.class.getConstructor()
							.newInstance().getPorteWalletDetails(relationshipNo);
					if(walletDetails==null) {
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "error", "Estimado cliente, no tiene la billetera correspondiente, "
									+ "cree la billetera y vuelva a intentarlo");
						}else {
							Utilities.sendJsonResponse(response, "error", "Dear customer your dont have coresponding wallet, "
									+ "please create wallet and try again");						}
						
						
						return;
					}
					porteWalletId = walletDetails.split(",")[0];	
					userType="C";
					txnUserCode = Utilities.generateTransactionCode(10);
					if(channel.equalsIgnoreCase("T")) {
						//Debit card here
						//Call Gate way here
						//Get charges 
						
						if(assetCode.equals(NeoBankEnvironment.getPorteTokenCode())) {
							txnPayMode = NeoBankEnvironment.getCodeBuyPorteCoinViaToken();
						}
						if(assetCode.equals(NeoBankEnvironment.getVesselCoinCode())) {
							txnPayMode = NeoBankEnvironment.getCodeBuyVesselCoinViaToken();
						}
						if(assetCode.equals(NeoBankEnvironment.getUSDCCode())) {
							txnPayMode = NeoBankEnvironment.getCodeBuyUSDCViaToken();
						}
						if(assetCode.equals(NeoBankEnvironment.getXLMCode())) {
							txnPayMode = NeoBankEnvironment.getCodeBuyXLMViaToken();
						}
						
						referenceNo = txnPayMode+ "-" + (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
								+ Utilities.genAlphaNumRandom(9);
						customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
								NeoBankEnvironment.getDefaultCustomerUserType(),txnPayMode, amount);
						 minimumTxnAmount=customerCharges.substring(customerCharges.indexOf("|")+1, customerCharges.length());
						
						if ( Double.parseDouble(amount)< Double.parseDouble(minimumTxnAmount)) { 
							if(langPref.equalsIgnoreCase("ES")) {
								Utilities.sendJsonResponse(response, "error", "El monto de la transacci�n no puede ser inferior a "+minimumTxnAmount);

							}else {
								Utilities.sendJsonResponse(response, "error", "Transaction amount can not be less than "+minimumTxnAmount);
							}
							throw new Exception("minimumAmount amount inputed:- Minimum exptected is:- "+minimumTxnAmount+"Amount inputed is"+amount);
						}
						
						extSystemRef=Utilities.genAlphaNumRandom(24).toUpperCase(); //Remove this after integration						
						success = (Boolean) CustomerMobilePorteCoinDao.class.getConstructor().newInstance().buyPorteCoinViaToken(relationshipNo, tokenId,  amount, payComments, 
								 referenceNo, txnUserCode, customerCharges, txnPayMode, assetCode, extSystemRef, porteWalletId, newWalletBalanceFromStellar, amountPorte);
				
					}
					if(channel.equalsIgnoreCase("F")) {
						walletDetails = (String)CustomerWalletDao.class.getConstructor()
								.newInstance().getFiatWalletBalance(relationshipNo);
						fiatWalletId = walletDetails.split(",")[0];
						walletBalance = walletDetails.split(",")[1];
					
						if(assetCode.equals(NeoBankEnvironment.getPorteTokenCode())) {
							txnPayMode = NeoBankEnvironment.getCodeBuyPorteCoinViaFiatWallet();
						}
						if(assetCode.equals(NeoBankEnvironment.getVesselCoinCode())) {
							txnPayMode = NeoBankEnvironment.getCodeBuyVesselCoinViaFiatWallet();
						}
						if(assetCode.equals(NeoBankEnvironment.getUSDCCode())) {
							txnPayMode = NeoBankEnvironment.getCodeBuyUSDCViaFiatWallet();
						}
						if(assetCode.equals(NeoBankEnvironment.getXLMCode())) {
							txnPayMode = NeoBankEnvironment.getCodeBuyXLMViaFiatWallet();
						}
						txnMode="D";// Debit
						currencyId = NeoBankEnvironment.getUSDCurrencyId();
						transactionCode=(new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
								+ Utilities.genAlphaNumRandom(9);
						referenceNo = txnPayMode+ "-" + transactionCode;
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
						
						// 
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
								}							return;
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
								}							return;
							}
						}
						/****** End of Wallet Authorization******/	
					   success = (Boolean) CustomerMobilePorteCoinDao.class.getConstructor().newInstance().buyPorteCoinViaFiatWallet(relationshipNo, fiatWalletId,  amount, payComments, 
							referenceNo, txnUserCode, txnPayMode, assetCode,  porteWalletId, newWalletBalanceFromStellar, customerCharges, amountPorte,transactionCode );					
					
					}
					
					if (success) {
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, "C",
								txnPayMode," Bought porte internal coin "+assetCode);
						// Get pending buy transactions
						listTxn = (List<Transaction>)CustomerMobilePorteCoinDao.class.getConstructor().
								newInstance().getPendingTransactions(relationshipNo);
						obj.add("error", gson.toJsonTree("false"));
						if(langPref.equalsIgnoreCase("ES")) {
							obj.add("message", gson.toJsonTree("Ha iniciado con éxito la compra de "+assetCode +":"+ amountPorte + 
									". Número de referencia " +txnUserCode+" ,se le notificará cuando reciba las monedas en su billetera.")); 
						}else {
							obj.add("message", gson.toJsonTree("You have successfully iniatiated the purchase of "+assetCode +":"+ amountPorte + 
									". Reference Number " +txnUserCode+" ,you will be notified when you receive the coins on your wallet.")); 						}
						
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
						if (porteWalletId != null)porteWalletId = null; if (tokenId != null)tokenId = null;
						if (assetCode != null)assetCode = null; 
						if (txnPayMode != null)txnPayMode = null; if (extSystemRef != null)extSystemRef = null;
						if (amount != null)amount = null; if (langPref != null)langPref = null; 
						if (user != null)user = null; 
						if (walletBalance != null)walletBalance = null; 
						if (walletDetails != null)walletDetails = null; 
						if (fiatWalletId != null)fiatWalletId = null; 
						if (payComments != null)payComments = null; 
						if (referenceNo != null)referenceNo = null; 
						if (userType != null)userType = null; 
						if (currencyId!=null); currencyId=null;   if (walletId!=null); walletId=null; if (authResponse != null)authResponse = null;
						if (transactionCode!=null); transactionCode=null;    if (txnMode!=null); txnMode=null; 
						if (cvv != null)cvv = null; if (tokenValue != null)tokenValue = null;
						if (listTxn!=null)listTxn=null;
 
					}								
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in coin purchase, Please try again letter");
				}
			break;
			
			case "sell_porte_coin_mbl":
				try {
					JsonObject obj = new JsonObject(); PrintWriter output = null;
					String sourceAssetCode = null; 	String tokenValue = null;
					String destionAssetCode = null; String authMethod = null;
					String sourceAssetIssuer = null; 	String langPref=null;
					String destinationIssuier = null; 	
					String sourceAmount = null; 
					String destMinAmount = null; 
					String sourceAcount = null; 
					String sourceAcountId = null; 
					String relationshipNo= null;
					String hasMnemonic = null; String password  = null; String mnemoniStringFromDB  = null;
					boolean passIsCorrect = false; KeyPair keyPair = null;
					if(request.getParameter("sourceassetcode")!=null)	 sourceAssetCode = request.getParameter("sourceassetcode").trim();
					if(request.getParameter("destassetcode")!=null)	 destionAssetCode = request.getParameter("destassetcode").trim();
					if(request.getParameter("source_amount")!=null)	 sourceAmount = request.getParameter("source_amount").trim();
					if(request.getParameter("destminamount")!=null)	 destMinAmount = request.getParameter("destminamount").trim();
					//if(request.getParameter("sourceaccount")!=null)	 sourceAcount = request.getParameter("sourceaccount").trim();
					if(request.getParameter("sourceaccountId")!=null)	 sourceAcountId = request.getParameter("sourceaccountId").trim();
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

					
					if(request.getParameter("hasMnemonic")!=null)	 hasMnemonic = request.getParameter("hasMnemonic").trim();
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
						sourceAcount = String.valueOf(keyPair.getSecretSeed());
					}else {
						if(request.getParameter("security")!=null)	 sourceAcount = request.getParameter("security").trim();
					}
					
					
				 	KeyPair sourceAccountKeyPair = KeyPair.fromSecretSeed(sourceAcount);
				 	if(!sourceAccountKeyPair.getAccountId().equals(sourceAcountId)) {
				 		obj.addProperty("error","true"); 
				 		if(langPref.equalsIgnoreCase("ES")) {
							obj.addProperty("message", "Clave secreta incorrecta");

						}else {
							obj.addProperty("message", "Incorrect Secret key");
						}
						output = response.getWriter();
						output.print(obj);
						return;
				 	}				 			
				 			
					if(sourceAssetCode.equals("PORTE")) {
						sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(sourceAssetCode);
					}else if(sourceAssetCode.equals("XLM")) {
						sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(sourceAssetCode);
					}else if(sourceAssetCode.equals("VESL")) {
						sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(sourceAssetCode);
					}else if(sourceAssetCode.equals("USDC")) {
						sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(sourceAssetCode);
					}
					
					if(destionAssetCode.equals("PORTE")) {
						destinationIssuier = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(destionAssetCode);
					}else if(destionAssetCode.equals("XLM")) {
						destinationIssuier = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(destionAssetCode);
					}else if(destionAssetCode.equals("VESL")) {
						destinationIssuier = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(destionAssetCode);
					}else if(destionAssetCode.equals("USDC")) {
						destinationIssuier = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(destionAssetCode);
					}
										
					NeoBankEnvironment.setComment(3, className, "inside sell_porte_coin_mbl sourceAssetCode "+sourceAssetCode
							+" destionAssetCode  "+ destionAssetCode + " sourceAssetIssuer "+ sourceAssetIssuer + " destinationIssuier "+ destinationIssuier
							+ " sourceAmount "+ sourceAmount + " destMinAmount "+destMinAmount +" sourceAcount "+ sourceAcount);
				
					String result = StellarSDKUtility.pathPaymentStrictSend(sourceAssetCode, destionAssetCode, sourceAssetIssuer, destinationIssuier,
							destMinAmount, sourceAmount, sourceAcount);
					
					if(result.equals("success")) {
						NeoBankEnvironment.setComment(3, className,"========== Payment success on Stellar " + java.time.LocalTime.now() );
						
						obj.addProperty("error","false"); 
						if(langPref.equalsIgnoreCase("ES")) {
							obj.addProperty("message", "Has intercambiado con éxito"+sourceAmount+" "+ sourceAssetCode+ " to "+destMinAmount+" "+ destionAssetCode); 

						}else {
							obj.addProperty("message", "You've successfuly swapped "+sourceAmount+" "+ sourceAssetCode+ " to "+destMinAmount+" "+ destionAssetCode); 
						}
					}else {
						obj.addProperty("error","true"); 
						obj.addProperty("message", result); 
						NeoBankEnvironment.setComment(3, className,"========== Payment Failed on Stellar " + java.time.LocalTime.now() );
					}
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +obj.toString());
						output = response.getWriter();
						output.print(obj);
					} finally {
						if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null;
						if (obj != null)obj = null;if (destinationIssuier != null)destinationIssuier = null;
						if (sourceAssetCode != null)sourceAssetCode = null; if (tokenValue != null)tokenValue = null;
						if (destionAssetCode != null)destionAssetCode = null;
						if (sourceAssetIssuer != null)sourceAssetIssuer = null; 
						if (sourceAcount != null)sourceAcount = null; 
						if (hasMnemonic != null)hasMnemonic = null; 
						if (password != null)password = null; if(authMethod!=null) authMethod= null;
						if (mnemoniStringFromDB != null)mnemoniStringFromDB = null; 
						if (keyPair != null)keyPair = null;
					}								
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Exchange Assets Failed, Try again");
				}
				
				break;
				
			case "transfer_porte_coin_mbl":
				try {
					NeoBankEnvironment.setComment(3, className,"========== start transfer_porte_coin_mbl " + java.time.LocalTime.now() );

					JsonObject obj = new JsonObject(); User user = null;  String relationshipNo= null; PrintWriter output = null;
					Gson gson = new Gson(); String amount= null; String receiverEmail =null; Boolean success = false;
					String walletBalance = null; 	String txnUserCode = null; String walletDetails =null; 
					String senderWalletId=null; String payComments=""; 	String referenceNo=""; String userType = null;
				    String receiverWalletId = null; String assetCodeSender = null; String [] splitResult=null;
				    String stellarHash=null; String tokenValue = null;  String authMethod = null;String langPref=null;

					String assetCodeReceiver = null; String txnPayMode = null; String extSystemRef = null; String userId = null;
					String senderKey = null; KeyPair destinationAccount = null; KeyPair sourceAccount =null; 
					String sourcewalletIdInternal = null;	String destinationwalletIdInternal = null;
					String destinationAssetBalance = null; String sourceAssetBalance = null; boolean proceed = true;
					String sourceAssetIssuer = null; String sourceAcountId = null; String results = null;
					String hasMnemonic = null; String password  = null; String mnemoniStringFromDB  = null;
					boolean passIsCorrect = false; KeyPair keyPair = null;
					if(request.getParameter("relno")!=null)	 relationshipNo = request.getParameter("relno").trim();
					if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
					if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

					//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
					if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
						NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v�lido, vuelva a iniciar sesi�n");

						}else {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
						}
						return;
					}
					if(request.getParameter("hasMnemonic")!=null)hasMnemonic = request.getParameter("hasMnemonic").trim();
					if(hasMnemonic.equals("true")) {
						if(request.getParameter("auth_method")!=null)	 authMethod = request.getParameter("auth_method").trim();
						if(authMethod.equals("P")) {
							NeoBankEnvironment.setComment(3, className, " authMethod is "+authMethod);
							if(request.getParameter("security")!=null)	 password = request.getParameter("security").trim();
							passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
									.checkIfPasswordIsCorrect(relationshipNo, password);
							if(!passIsCorrect) {
								NeoBankEnvironment.setComment(1, className, "Password is not correct");
								if(langPref.equalsIgnoreCase("ES")) {
									Utilities.sendJsonResponse(response, "error", "Por favor ingrese la contrase�a correcta");

								}else {
									Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
								}
								return;
							}
						}
						//Get mnemonic code  here
						mnemoniStringFromDB = (String) CustomerDao.class.getConstructor().newInstance()
								.getmnemonicCode(relationshipNo);
						keyPair= Bip39Utility.masterKeyGeneration(mnemoniStringFromDB.replaceAll(",", " "));
						senderKey = String.valueOf(keyPair.getSecretSeed());
					}else {
						if(request.getParameter("security")!=null)	 senderKey = request.getParameter("security").trim();
					}

					
					if(request.getParameter("transfer_amount")!=null) amount = request.getParameter("transfer_amount").trim();
					if(request.getParameter("sender_asset")!=null)	 assetCodeSender = request.getParameter("sender_asset").trim();
					if(request.getParameter("receiver_asset")!=null)  assetCodeReceiver = request.getParameter("receiver_asset").trim();
					if(request.getParameter("uid")!=null)	 userId = request.getParameter("uid").trim();
					if(request.getParameter("usertype")!=null)	 userType = request.getParameter("usertype").trim();
					if(request.getParameter("narrative")!=null)	 payComments = request.getParameter("narrative").trim();
					if(request.getParameter("receiverwallet")!=null)	 receiverWalletId = request.getParameter("receiverwallet").trim();
					//if(request.getParameter("signkey")!=null)	 senderKey = request.getParameter("signkey").trim();
					
					NeoBankEnvironment.setComment(3,className,"assetCodeSender is "+assetCodeSender +" receiverWalletId "+receiverWalletId);
					//String []assetCodeValues = assetCodeSender.split(",");
					//assetCodeSender = assetCodeValues[1];
					NeoBankEnvironment.setComment(3, className," inside transfer_porte_coin_mbl is receiverEmail " + receiverEmail +" amount "+ amount + " assetCodeSender "+ assetCodeSender
							+" assetCodeReceiver "+ assetCodeReceiver + " relationshipNo "+ relationshipNo + " userId "+ userId + " userType "+ userType);
					
					//Currently cross-asset transfer not supported
					if(!assetCodeSender.equals(assetCodeReceiver)) {
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "error", "Actualmente no se admite la transferencia de activos del receptor");

						}else {
							Utilities.sendJsonResponse(response, "error", "Currently the receiver asset Transfer not supported");
						}
						return;
					}
					
					/**Check if accounts exists*/
					//Sender key SB7AJALFNBC62RFB7VWCB7EWBT47XA6IYW4I2XFSX5CKPEDMZZFT6XSD
					//receiver walletid GCLVJMQHLYKJKGZOCC5Z24NGAFMJTSSF4N4VF77CZICXTGYAE5XSGUBP
					try {
						 sourceAccount = KeyPair.fromSecretSeed(senderKey);
					}catch (Exception e) {
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "error", "La clave proporcionada no es v�lida");

						}else {
							Utilities.sendJsonResponse(response, "error", "Key provided is invalid");
						}
						return;
					}
					
					try {
				 		 destinationAccount = KeyPair.fromAccountId(receiverWalletId);
					}catch (Exception e) {
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "error", "La billetera del receptor no es v�lida");

						}else {
							Utilities.sendJsonResponse(response, "error", "Receiver wallet is invalid");
						}
						return;
					}
						
					sourceAcountId = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
					if(sourceAcountId == null)
						if(langPref.equalsIgnoreCase("ES")) {
							throw new Exception("El cliente no tiene una cuenta estelar.");

						}else {
							throw new Exception("Customer does not have a Stellar account.");
						}
					
					 if(assetCodeSender.equals(NeoBankEnvironment.getPorteTokenCode())) 
						 sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
									.newInstance().getIssueingAccountPublicKey(assetCodeSender);
						if(assetCodeSender.equals(NeoBankEnvironment.getUSDCCode())) 
							sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
									.newInstance().getIssueingAccountPublicKey(assetCodeSender);
						if(assetCodeSender.equals(NeoBankEnvironment.getVesselCoinCode())) 
							sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
									.newInstance().getIssueingAccountPublicKey(assetCodeSender);
					
					
					KeyPair source = KeyPair.fromSecretSeed(senderKey);
					NeoBankEnvironment.setComment(3, className, "senderKey  "+ senderKey+" sourceAssetIssuer "+
							sourceAssetIssuer+"  account from pvt "+ source.getAccountId());
					if(!source.getAccountId().equals(sourceAcountId)) {
						obj.addProperty("error", "true"); 
						if(langPref.equalsIgnoreCase("ES")) {
			        		obj.addProperty("message", "La clave secreta es incorrecta");
							Utilities.sendJsonResponse(response, "error", "La clave secreta es incorrecta");


						}else {
			        		obj.addProperty("message", "Secret Key is incorrect"); 
							Utilities.sendJsonResponse(response, "error", "Secret Key is incorrect ");

						}
					
						NeoBankEnvironment.setComment(3, className, "Secret Key is incorrect ");
						return;
					}
					/**Check from the Network if exist*/
		
					if(StellarSDKUtility.CheckAccountIfExist(sourceAccount)==false) {
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "error", "La cuenta de origen no existe");

						}else {
							Utilities.sendJsonResponse(response, "error", "Source account does not exist");
						}
						return;
					}
					if(StellarSDKUtility.CheckAccountIfExist(destinationAccount)==false) {
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "error", "La cuenta de destino no existe");

						}else {
							Utilities.sendJsonResponse(response, "error", "Destination account does not exist");
						}
						return;
					}
					
					if((sourceAccount.getAccountId()).equals(destinationAccount.getAccountId())) {
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "error", "No puede enviar activos desde su billetera a la misma cuenta de billetera");

						}else {
							Utilities.sendJsonResponse(response, "error", "You cannot send Asset from your wallet to the same wallet account");
						}
						return;
					}
					//Before posting payment first check the FIAT wallet if it has some amount to cater for the internal transaction charge.
					
					/**Post payments on stellar*/
					NeoBankEnvironment.setComment(3, className,"========== getting into Stellar " + java.time.LocalTime.now() );					
								
					if(assetCodeSender.equals(NeoBankEnvironment.getXLMCode())) {
						 results = StellarSDKUtility.sendNativeCoinPayment(assetCodeReceiver, sourceAccount, destinationAccount, amount, payComments);					
					}else {
						 results = StellarSDKUtility.sendNoNNativeCoinPayment(assetCodeReceiver, sourceAccount, destinationAccount, amount, payComments, sourceAssetIssuer);					
						
					}
					splitResult=results.split(",");
					stellarHash=splitResult[1];
					if(splitResult[0].equals("success")) { 
						//Stellar operation success
						NeoBankEnvironment.setComment(3, className,"========== Payment success on Stellar " + java.time.LocalTime.now() );
					}else {
						NeoBankEnvironment.setComment(3, className,"========== Payment failed on Stellar " + java.time.LocalTime.now() );
						Utilities.sendJsonResponse(response, "error", results);
						return;
					}
					
					//Get the Wallet balance from Stellar Network after transaction
					/**Sender*/
					ArrayList<AssetAccount> accountBalances = StellarSDKUtility.getAccountBalance(sourceAccount);
					if(accountBalances != null) {

						for(int i = 0; i< accountBalances.size(); i++) {
					 			sourceAssetBalance = accountBalances.get(i).getAssetBalance();
								NeoBankEnvironment.setComment(3, className,"========== "+assetCodeSender +" Source Asset balance is "+ sourceAssetBalance );
						}
						
					}
					
					/**Sender*/
					ArrayList<AssetAccount> receiverAccountBalances = StellarSDKUtility.getAccountBalance(destinationAccount);
					if(receiverAccountBalances != null) {
						for(int i = 0; i< receiverAccountBalances.size(); i++) {
					 			destinationAssetBalance = receiverAccountBalances.get(i).getAssetBalance();
								NeoBankEnvironment.setComment(3, className,"========== "+assetCodeSender +" Destination Asset balance is "+ destinationAssetBalance );
						}
						
					}
										
					//Get the internal wallet details and update the internal wallet ledgers for the coin. **This can be removed later if not required
					sourcewalletIdInternal = (String)CustomerPorteCoinDao.class.getConstructor().newInstance().getAssetWalletDetails(relationshipNo, assetCodeSender, sourceAccount.getAccountId());
					destinationwalletIdInternal = (String)CustomerPorteCoinDao.class.getConstructor().newInstance().getAssetWalletDetails(relationshipNo, assetCodeSender, destinationAccount.getAccountId());
					NeoBankEnvironment.setComment(3, className," === check existence "  );

					//Here wallet provided in not managed by Porte portfoilio.  We dont have to update any ledger on the system
					if(sourcewalletIdInternal ==null) {
						NeoBankEnvironment.setComment(3, className,"========== source not present in porte portfolio  accountId "+ destinationAccount.getAccountId() );
					    //Utilities.sendJsonResponse(response, "error", "Payment successful");
					    proceed = false;
					}
					
					if(destinationwalletIdInternal ==null) {
						NeoBankEnvironment.setComment(3, className,"========== destination not present in porte portfolio  accountId "+ destinationAccount.getAccountId() );

					    //Utilities.sendJsonResponse(response, "error", "Payment successful");
					    proceed = false;
					}
					
				
					/*
				   /**Initial Checks
					walletDetails = (String)CustomerPorteCoinDao.class.getConstructor().newInstance().getPorteWalletDetails(relationshipNo, assetCodeSender);
					checkIfEmailExist= (Boolean)CustomerDao.class.getConstructor().newInstance().checkIfEmailExist(receiverEmail);
					receiverWalletId = (String)CustomerPorteCoinDao.class.getConstructor().newInstance().getReceiverPorteWalletId(receiverEmail, assetCodeSender);
					
					if(!checkIfEmailExist) {
						Utilities.sendJsonResponse(response, "error", "Receiver Does not exist");
						return;
					}
					if(walletDetails==null) {
						Utilities.sendJsonResponse(response, "error", "Dear customer your dont have corresponding wallet, please create wallet and try again");
						return;
					}
					if(receiverWalletId==null) {
						Utilities.sendJsonResponse(response, "error", "Dear customer, Receiver does not have corresponding wallet");
						return;
					}
					senderWalletId = walletDetails.split(",")[0];
					walletBalance = walletDetails.split(",")[1];
					
					if(receiverWalletId.equals(senderWalletId)) {
						Utilities.sendJsonResponse(response, "error", "You cannot send Asset from your wallet to the same wallet account");
						return;
					}
					if(!assetCodeReceiver.equals(assetCodeSender)) {
						Utilities.sendJsonResponse(response, "error", "Transfer to different wallet asset not supported at the moment ");
						return;
					}
					 End Initial Checks*/
					NeoBankEnvironment.setComment(3, className,"assetCodeSender " +assetCodeSender );

					if(assetCodeSender.equals(NeoBankEnvironment.getPorteTokenCode())) {
						txnPayMode= NeoBankEnvironment.getCodePorteUtilityCoinP2P();
					}else if(assetCodeSender.equals(NeoBankEnvironment.getVesselCoinCode())) {
						txnPayMode= NeoBankEnvironment.getCodeVesselCoinP2P();
					}else if(assetCodeSender.equals(NeoBankEnvironment.getXLMCode())) {
						txnPayMode= NeoBankEnvironment.getCodeXLMCoinP2P();
					}else if(assetCodeSender.equals(NeoBankEnvironment.getUSDCCode())) {
						txnPayMode= NeoBankEnvironment.getCodeUSDCCoinP2P();
					}else {
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "error", "Vuelva a intentarlo m�s tarde");
						}else {
							Utilities.sendJsonResponse(response, "error", "Try again later");
						}
						NeoBankEnvironment.setComment(3, className," Unknown txnPayMode " +txnPayMode );
						return;
					}
					
					txnUserCode = Utilities.generateTransactionCode(10);
					referenceNo = txnPayMode+ "-" + (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
							+ Utilities.genAlphaNumRandom(9);
					//proceed = false;  //DONT UPDATE INTERNAL LEDGER FOR NOW REFER TO STELLAR
					if(proceed) {  //Proceed to update Internal ledger
						NeoBankEnvironment.setComment(3, className," === proceed " +referenceNo );

						String customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
								NeoBankEnvironment.getDefaultCustomerUserType(),txnPayMode, amount);
						String minimumTxnAmount=customerCharges.substring(customerCharges.indexOf("|")+1, customerCharges.length());
						
						if ( Double.parseDouble(amount)< Double.parseDouble(minimumTxnAmount)) { 
							if(langPref.equalsIgnoreCase("ES")) {
								Utilities.sendJsonResponse(response, "error", "El monto de la transacci�n no puede ser inferior a "+minimumTxnAmount);

							}else {
								Utilities.sendJsonResponse(response, "error", "Transaction amount can not be less than "+minimumTxnAmount);
							}
							return;
						}
						//Check balance // this will be captured in the authorization module once we plugin
						/*double senderDebitAmount =  Double.parseDouble(amount) + 
								Double.parseDouble(customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|")));
						if ( senderDebitAmount> Double.parseDouble(walletBalance)) {
							Utilities.sendJsonResponse(response, "error", "Dear customer you have insufficient funds, Kindly top up and try again");
							return;
						}*/
						
						//Connect to External API here
						extSystemRef=stellarHash; 
						NeoBankEnvironment.setComment(3, className," entering core" +extSystemRef );

						success = (Boolean) CustomerPorteCoinDao.class.getConstructor().newInstance().porteCoinP2P(relationshipNo, sourcewalletIdInternal,  amount, payComments, 
								referenceNo, txnUserCode, customerCharges, txnPayMode, assetCodeSender, extSystemRef, destinationwalletIdInternal, sourceAssetBalance, destinationAssetBalance);
						
						if (success) {
							///SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, moduleCode,StringUtils.substring("Porte Asset "+assetCodeSender+"  P2P" + referenceNo , 0, 48));
							SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, "C",
									txnPayMode," Porte Asset "+assetCodeSender+"  P2P");
							obj.add("error", gson.toJsonTree("false"));
							//obj.add("message", gson.toJsonTree("You have transfered " + assetCodeSender+" "+ Utilities.getMoneyinDecimalFormat(amount) + 
							if(langPref.equalsIgnoreCase("ES")) {
								obj.add("message", gson.toJsonTree("Pago exitoso  "+ amount +  " "+assetCodeSender )); 

							}else {
								obj.add("message", gson.toJsonTree("Payment success  "+ amount +  " "+assetCodeSender )); 
							}

						}else {
							obj.add("error", gson.toJsonTree("true")); 
							if(langPref.equalsIgnoreCase("ES")) {
								obj.add("message", gson.toJsonTree("Transacci�n fallida")); 

							}else {
								obj.add("message", gson.toJsonTree("Transaction failed")); 
							}
						}						
					}else {
						//This account is not managed by Porte portfolio
						obj.add("error", gson.toJsonTree("false"));
						//obj.add("message", gson.toJsonTree("You have transfered " + assetCodeSender+" "+ Utilities.getMoneyinDecimalFormat(amount) + 
						if(langPref.equalsIgnoreCase("ES")) {
							obj.add("message", gson.toJsonTree("Pago exitoso  "+ amount +  " "+assetCodeSender )); 

						}else {
							obj.add("message", gson.toJsonTree("Payment success  "+ amount +  " "+assetCodeSender )); 
						}
					}
							
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));
					NeoBankEnvironment.setComment(3, className,"========== end transfer_porte_coin_mbl " + java.time.LocalTime.now() );

				} finally {
					NeoBankEnvironment.setComment(3, className,"========== start cleaning transfer_porte_coin_mbl " + java.time.LocalTime.now() );

					if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
					if (obj != null)obj = null; if (receiverEmail != null)receiverEmail = null;
					if (amount != null)amount = null;  if (assetCodeReceiver != null)assetCodeReceiver = null;
					if (extSystemRef != null)extSystemRef = null; if (tokenValue != null)tokenValue = null;
					if (user != null)user = null; if(authMethod!=null) authMethod= null;
					if (walletBalance != null)walletBalance = null; 
					if (walletDetails != null)walletDetails = null; 
					if (senderWalletId != null)senderWalletId = null; 
					if (payComments != null)payComments = null; 
					if (referenceNo != null)referenceNo = null;  if (receiverWalletId != null) receiverWalletId = null; 
					if (assetCodeSender != null)assetCodeSender = null; if (txnPayMode != null)txnPayMode = null;
					if (stellarHash!=null)stellarHash=null; if (splitResult!=null)splitResult=null;
					if (hasMnemonic != null)hasMnemonic = null;  if (password != null)password = null; 
					if (mnemoniStringFromDB != null)mnemoniStringFromDB = null;  if (keyPair != null)keyPair = null;

					NeoBankEnvironment.setComment(3, className,"========== after cleaning transfer_porte_coin_mbl " + java.time.LocalTime.now() );

				}								
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in transfer coin, Please try again letter");
				}
			break;
			
			case "get_last_x_porte_txn":
				try {
					JsonObject obj = new JsonObject();
					User user = null; String tokenValue = null;
					String relationshipNo= null;
					PrintWriter output = null;
					ArrayList<AssetTransactions> arrTransactions = null;
					List<Transaction> listTxn = new ArrayList<Transaction>();
					Gson gson = new Gson();
					String txnDesc= null;
					if(request.getParameter("relno")!=null)	 relationshipNo = request.getParameter("relno").trim();
					if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
					//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
					if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
						NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
						return;
					}

					String accountId = (String)CustomerMobilePorteCoinDao.class.getConstructor() .newInstance().getAssetAccountId(relationshipNo);
					arrTransactions = StellarSDKUtility.getAccountTransactions(accountId, "10");
					listTxn = (List<Transaction>)CustomerMobilePorteCoinDao.class.getConstructor().
							newInstance().getPendingTransactions(relationshipNo);
					
					obj.add("data", gson.toJsonTree(arrTransactions));
					obj.add("txn", gson.toJsonTree(listTxn));
					obj.add("error", gson.toJsonTree("false"));
					
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						output = response.getWriter();
						output.print(gson.toJson(obj));
					} finally {
						if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						if (obj != null)obj = null; if (tokenValue != null)tokenValue = null;
						if (arrTransactions != null)arrTransactions = null; 
						if (user != null)user = null; 
						if (txnDesc != null)txnDesc = null; 
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in geting Transactions, Please try again letter");
				}
				break;
				
			case "get_ext_txn_btn_dates_mbl":
				try {
					JsonObject obj = new JsonObject(); User user = null; String relationshipNo= null;
					PrintWriter output = null; 	List<AssetTransaction> arrTransactions = null;
					ConcurrentHashMap<String, String> hashTxnRules = null; Gson gson = new Gson();
					String txnDesc= null; String dateTo= null; String dateFrom =null; 	String oldFormat = "MM/dd/yyyy";
					String newFormat = "yyyy-MM-dd"; SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
					
					//if(request.getParameter("datefrom")!=null) 	dateFrom = request.getParameter("datefrom").trim();
					//if(request.getParameter("dateto")!=null)	 dateTo = request.getParameter("dateto").trim();
					
					if(request.getParameter("relno")!=null)	 relationshipNo = request.getParameter("relno").trim();
										
					NeoBankEnvironment.setComment(3, className, "get_ext_txn_btn_dates:dateFrom  "+dateFrom +" dateTo "+dateTo);
					
					hashTxnRules = (ConcurrentHashMap<String, String>)CustomerExternalCoinDao.class.getConstructor().newInstance().getTransactionTypes();
					
					Date d = sdf.parse(dateFrom); Date d2 = sdf.parse(dateTo);
					sdf.applyPattern(newFormat);  sdf.applyPattern(newFormat);
					dateFrom = sdf.format(d);     dateTo = sdf.format(d2);
					
					//NeoBankEnvironment.setComment(3, className, "dateFrom "+ dateFrom);
					//NeoBankEnvironment.setComment(3, className, "dateTo "+ dateTo);
					
					arrTransactions = (List<AssetTransaction>)CustomerExternalCoinDao.class.getConstructor().newInstance().getExternalCoinTxnBtnDates(dateFrom, dateTo, relationshipNo);
					//arrTransactions = (List<Transaction>)CustomerExternalCoinDao.class.getConstructor().newInstance().getExternalCoinTxnBtnDates(relationshipNo);
					NeoBankEnvironment.setComment(3, className, "After fetch ");
					if (arrTransactions!=null) {
						for (int i = 0; i <arrTransactions.size(); i++) {
							if (arrTransactions.get(i).getSystemReferenceInt()!=null) {
								txnDesc= hashTxnRules.get(arrTransactions.get(i).getSystemReferenceInt().split("-")[0]);
								arrTransactions.get(i).setTxnDescription(txnDesc);
							}
						}
					}
					
					if(arrTransactions !=null && arrTransactions.size() >0 ) {
						obj.add("data", gson.toJsonTree(arrTransactions));
						obj.add("error", gson.toJsonTree("false"));
						
					}else {
						obj.add("data", gson.toJsonTree(arrTransactions));
						obj.add("error", gson.toJsonTree("true"));
					}
					
					try {
						//NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						output = response.getWriter();
						output.print(gson.toJson(obj));
					} finally {
						if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						if (obj != null)obj = null; if (dateFrom != null)dateFrom = null;
						if (arrTransactions != null)arrTransactions = null; 
						if (user != null)user = null; 
						if (dateTo != null)dateTo = null; 
						if (hashTxnRules != null)hashTxnRules = null; 
						if (txnDesc != null)txnDesc = null; 
						if (sdf != null)sdf = null; if (d != null)d = null; 
						if (d2 != null)d2  = null; 
					}								
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in geting Transactions, Please try again letter");
				}
				break;
	
			case "buy_coin_pending_txn_mbl":
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" inside buy_coin_pending_txn_mbl ");

					JsonObject obj = new JsonObject();
					User user = null;String langPref=null;
					String relationshipNo= null;
					PrintWriter output = null;
					ArrayList<AssetTransactions> arrTransactions = null;
					Gson gson = new Gson();
					String txnDesc= null;
					String tokenValue = null;
					List<Transaction> listTxn = null;
					
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

					listTxn = (List<Transaction>)CustomerMobilePorteCoinDao.class.getConstructor().
							newInstance().getPendingTransactions(relationshipNo);
					
					obj.add("data", gson.toJsonTree(listTxn));
					obj.add("error", gson.toJsonTree("false"));
					
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						output = response.getWriter();
						output.print(gson.toJson(obj));
					} finally {
						if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						if (obj != null)obj = null;
						if (arrTransactions != null)arrTransactions = null; 
						if (user != null)user = null; 
						if (langPref != null)langPref = null; 
						if (txnDesc != null)txnDesc = null; if (tokenValue != null)tokenValue = null; 
						if (listTxn!=null)listTxn=null;
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in geting Transactions, Please try again letter");
				}
				break;
			case "asset_exchange_conversion":
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" inside asset_exchange_conversion ");

					JsonObject obj = new JsonObject(); User user = null; String sourceAsset= null;
					String destAssetCode= null; PrintWriter output = null; Gson gson = new Gson();
					String sourceAssetIssuer= null;  String destinationAssetIssuer= null; String sourceAmount= null;
					String destinationAmount= null;  String sourceAssetType= null;String langPref=null;

					if(request.getParameter("source")!=null)	 sourceAsset = request.getParameter("source").trim();
					if(request.getParameter("destination")!=null)	 destAssetCode = request.getParameter("destination").trim();
					if(request.getParameter("amount")!=null)	 sourceAmount = request.getParameter("amount").trim();
					if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

					
					NeoBankEnvironment.setComment(3, className, " in asset_exchange_conversion sourceAsset "+sourceAsset+" "
							+ "destAssetCode "+ destAssetCode + " sourceAmount "+ sourceAmount  );
					if(sourceAsset.equals(destAssetCode)) {
						destinationAmount = sourceAmount;
						obj.add("data", gson.toJsonTree(destinationAmount));
						obj.add("error", gson.toJsonTree("false"));
						output = response.getWriter();
						output.print(gson.toJson(obj));
						return;
					}
					if(sourceAsset.equals(NeoBankEnvironment.getPorteTokenCode())) {
						sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(sourceAsset);
						sourceAssetType  ="credit_alphanum12";
					}else if(sourceAsset.equals(NeoBankEnvironment.getVesselCoinCode())) {
						sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(sourceAsset);
						sourceAssetType  ="credit_alphanum4";
					}else if(sourceAsset.equals(NeoBankEnvironment.getUSDCCode())) {
						sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(sourceAsset);
						sourceAssetType  ="credit_alphanum4";
					}else if(sourceAsset.equals(NeoBankEnvironment.getStellarBTCxCode())) {
						sourceAssetIssuer =  (String)TDAManagementDao.class.getConstructor().newInstance().getBTCxIssuingAccount();
						sourceAssetType  ="credit_alphanum4";
				    }

					if(destAssetCode.equals(NeoBankEnvironment.getPorteTokenCode())) {
						destinationAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(destAssetCode);
					}else if(destAssetCode.equals(NeoBankEnvironment.getVesselCoinCode())) {
						destinationAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(destAssetCode);
					}else if(destAssetCode.equals(NeoBankEnvironment.getUSDCCode())) {
						destinationAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(destAssetCode);
					}else if(destAssetCode.equals(NeoBankEnvironment.getStellarBTCxCode())) {
						destinationAssetIssuer = (String)TDAManagementDao.class.getConstructor().newInstance().getBTCxIssuingAccount();
					}
					
				   if(sourceAsset.equals(NeoBankEnvironment.getXLMCode())) {
						sourceAssetType  ="native";
						sourceAsset = "";
						sourceAssetIssuer = "";
					}
				   
				   if(destAssetCode.equals(NeoBankEnvironment.getXLMCode())) {
						destAssetCode = NeoBankEnvironment.getXLMCode();
						destinationAssetIssuer = "";
					}
				   
					if (sourceAsset.equals(NeoBankEnvironment.getStellarBTCxCode())) {
						sourceAsset="BTC";
					}
					if (destAssetCode.equals(NeoBankEnvironment.getStellarBTCxCode())) {
						destAssetCode="BTC";
					}
					NeoBankEnvironment.setComment(3, className, " sourceAsset "+sourceAsset+" destAssetCode "+ destAssetCode
							+ " sourceAmount "+ sourceAmount +" sourceAssetIssuer "+ sourceAssetIssuer +" sourceAssetType "+ sourceAssetType
							+ " destinationAssetIssuer "+ destinationAssetIssuer);
					
					destinationAmount = StellarSDKUtility.getPathStrictSendWithDestinationAssets(sourceAssetType, sourceAsset,
							sourceAssetIssuer, sourceAmount, destAssetCode, destinationAssetIssuer);
					
					NeoBankEnvironment.setComment(3, className, " destinationAmount" + destinationAmount);
					
					obj.add("data", gson.toJsonTree(destinationAmount));
					obj.add("error", gson.toJsonTree("false"));
					
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						output = response.getWriter();
						output.print(gson.toJson(obj));
					} finally {
						if (output != null)output.close(); if (sourceAsset != null)sourceAsset = null; if (gson != null)gson = null;
						if (obj != null)obj = null;if (destinationAssetIssuer != null)destinationAssetIssuer = null; 
						if (sourceAssetIssuer != null)sourceAssetIssuer = null; if(langPref!=null)langPref=null;
						if (user != null)user = null; if (destinationAmount != null)destinationAmount = null; 
						if (sourceAmount != null)sourceAmount = null; if (sourceAssetType != null)sourceAssetType = null; 
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error selling rates, Please try again letter");
				}
				break;
				
				
			case "porte_asset_conversion_burning":
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" inside porte_asset_conversion ");

					JsonObject obj = new JsonObject(); User user = null; String sourceAsset= null;
					String destAssetCode= null; PrintWriter output = null; Gson gson = new Gson();
					String sourceAssetIssuer= null;  String destinationAssetIssuer= null; String sourceAmount= null;
					double destinationAmount= 0;  String sourceAssetType= null;

					if(request.getParameter("source")!=null)	 sourceAsset = request.getParameter("source").trim();
					if(request.getParameter("destination")!=null)	 destAssetCode = request.getParameter("destination").trim();
					if(request.getParameter("sourceamount")!=null)	 sourceAmount = request.getParameter("sourceamount").trim();
					
					NeoBankEnvironment.setComment(3, className, " in asset_exchange_conversion sourceAsset "+sourceAsset+" "
							+ "destAssetCode "+ destAssetCode + " sourceAmount "+ sourceAmount  );
					
					destinationAmount = (double)CustomerPorteCoinDao.class.getConstructor().newInstance().getPorteAssetConversionOffRamp(sourceAsset, destAssetCode, sourceAmount);
					
					NeoBankEnvironment.setComment(3, className, " destinationAmount" + destinationAmount);
					if(destinationAmount > 0) {
						obj.add("data", gson.toJsonTree(Utilities.getMoneyinDecimalFormat(Double.toString(destinationAmount))));
						obj.add("error", gson.toJsonTree("false"));
						
					}else {
						obj.add("error", gson.toJsonTree("true"));
					}
					
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						output = response.getWriter();
						output.print(gson.toJson(obj));
					} finally {
						if (output != null)output.close(); if (sourceAsset != null)sourceAsset = null; if (gson != null)gson = null;
						if (obj != null)obj = null;if (destinationAssetIssuer != null)destinationAssetIssuer = null; 
						if (sourceAssetIssuer != null)sourceAssetIssuer = null; 
						if (user != null)user = null; 
						if (sourceAmount != null)sourceAmount = null; if (sourceAssetType != null)sourceAssetType = null; 
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error burning rates, Please try again letter");
				}
				break;
				
			case "burn_porte_coins_mbl":
				try {
					JsonObject obj = new JsonObject(); 
					String minimumTxnAmount = null;String langPref=null;
					PrintWriter output = null;
					String relationshipNo= null; 
					Gson gson = new Gson();
					String amountToOfframp= null;//amount
					Boolean success = false; 
					String walletBalance = null; 
					String txnUserCode = null;
					String walletDetails =null; 
					String fiatWalletId=null; 	
					String porteWalletId=null; 
					String payComments=""; 	
					String referenceNo=""; 
					String tokenId = null;
					String assetCode = null; 
					String txnPayMode = null; 
					String extSystemRef = null;
					String cvv = null; 
					String amountUSD =null; //amountPorte
					String assetIssuer = null; 
					boolean burnIsSuccussful = false; String authMethod = null;
					String sourcePrivateKey = null;
					String hasMnemonic = null; String password  = null; String mnemoniStringFromDB  = null;
					boolean passIsCorrect = false; KeyPair keyPair = null; String tokenValue = null;
					
					String authStatus=""; String authMessage=""; String walletId=null; String authResponse=null;
					boolean recordAuthorization=false; String currencyId = null; String transactionCode=null;
					String txnMode=null; String userType=null;
					
					if(request.getParameter("sourceassetcode")!=null) assetCode = request.getParameter("sourceassetcode").trim();
					if(request.getParameter("source_amount")!=null) amountToOfframp = request.getParameter("source_amount").trim();
					if(request.getParameter("destminamount")!=null)	 	amountUSD = request.getParameter("destminamount").trim();
					if(request.getParameter("comment")!=null)	 payComments = request.getParameter("comment").trim();
					//if(request.getParameter("sourceaccountId")!=null)	 sourcePrivateKey = request.getParameter("sourceaccountId").trim();
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
					
					if(request.getParameter("hasMnemonic")!=null)	 hasMnemonic = request.getParameter("hasMnemonic").trim();
					if(hasMnemonic.equals("true")) {
						if(request.getParameter("auth_method")!=null)	 authMethod = request.getParameter("auth_method").trim();
						 if(authMethod.equals("P")) {
							if(request.getParameter("security")!=null)	 password = request.getParameter("security").trim();
							passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
									.checkIfPasswordIsCorrect(relationshipNo, password);
							if(!passIsCorrect) {
								NeoBankEnvironment.setComment(1, className, "Password is not correct");
								if(langPref.equalsIgnoreCase("ES")) {
									Utilities.sendJsonResponse(response, "error", "Por favor ingrese la contrase�a correcta");

								}else {
									Utilities.sendJsonResponse(response, "error", "Por favor ingrese la contraseña correcta");
								}
								return;
							}
						 }
						mnemoniStringFromDB = (String) CustomerDao.class.getConstructor().newInstance()
								.getmnemonicCode(relationshipNo);
						keyPair= Bip39Utility.masterKeyGeneration(mnemoniStringFromDB.replaceAll(",", " "));
						sourcePrivateKey = String.valueOf(keyPair.getSecretSeed());
					}else {
						if(request.getParameter("security")!=null)	 sourcePrivateKey = request.getParameter("security").trim();
					}
					
					
					
							
					NeoBankEnvironment.setComment(1, className, "amountToOfframp "+amountToOfframp);
					NeoBankEnvironment.setComment(1, className, "amountUSD "+amountUSD);
					
					walletDetails = (String)CustomerWalletDao.class.getConstructor()
							.newInstance().getFiatWalletBalance(relationshipNo);
					fiatWalletId = walletDetails.split(",")[0];
					walletBalance = walletDetails.split(",")[1];
					
					
					if(assetCode.equals(NeoBankEnvironment.getPorteTokenCode())) {
						assetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(assetCode);
						txnPayMode = NeoBankEnvironment.getCodeOfframpPorteCoinToFiatWallet();
					}
					if(assetCode.equals(NeoBankEnvironment.getUSDCCode())) {
						assetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(assetCode);
						txnPayMode = NeoBankEnvironment.getCodeOfframpUSDCToFiatWallet();
					}
					if(assetCode.equals(NeoBankEnvironment.getVesselCoinCode())) {
						assetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(assetCode);
						txnPayMode = NeoBankEnvironment.getCodeOfframpVesselCoinToFiatWallet();
					}
					txnUserCode = Utilities.generateTransactionCode(10);
					userType="C";
					txnMode="C";// Credit
					currencyId = NeoBankEnvironment.getUSDCurrencyId();
					SimpleDateFormat formatter1 = new SimpleDateFormat("yyMMddHHmmssSSS");
					transactionCode = formatter1.format(new java.util.Date()) + Utilities.genAlphaNumRandom(9);
					
					referenceNo = txnPayMode+ "-" + transactionCode;
					
				
					/****** Wallet Authorization******/
					
					authResponse=(String)WalletAuthorizationDao.class.getConstructor().newInstance().performWalletAuthorization(relationshipNo, amountUSD, txnMode);
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
						
						recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, amountUSD, txnMode, currencyId, 
								userType, referenceNo, walletId, authStatus, authMessage);
						if(!recordAuthorization) { 
							if(langPref.equalsIgnoreCase("ES")) {
								Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo más tarde");

							}else {
								Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
							}						return;
						}
						     Utilities.sendJsonResponse(response, "authfailed", authMessage);
						return;
					}else {
						// Record successful authorization
						recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, amountUSD, txnMode, currencyId, 
								userType, referenceNo, walletId, authStatus, authMessage);
						if(!recordAuthorization) { 
							if(langPref.equalsIgnoreCase("ES")) {
								Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo más tarde");

							}else {
								Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
							}						return;
						}
					}
					/****** End of Wallet Authorization******/	
						
					//Connect to External API here
					// TODO:- Get stellar hash from the API 
					extSystemRef=Utilities.genAlphaNumRandom(24).toUpperCase(); //Remove this after integration
					 //burnCoin(String sourcePrivateKey, 
					//			String assetCode, String issuerPublicKey, String coinsToBurn, String Comment)
					burnIsSuccussful = StellarSDKUtility.burnCoin(sourcePrivateKey, assetCode, assetIssuer, amountToOfframp, payComments);
					if(!burnIsSuccussful) 
						if(langPref.equalsIgnoreCase("ES")) {
							throw new Exception("La moneda en llamas falló");
	
						}else {
							throw new Exception("Burning coin failed");
						}
					success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().burnPorteCoinToFiatWallet(relationshipNo, fiatWalletId, amountUSD , payComments, 
							 referenceNo, txnUserCode, txnPayMode, assetCode, extSystemRef, porteWalletId,  amountToOfframp,transactionCode );
					if (success) {
						//TODO Add Audit trail
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, userType,
								txnPayMode, " Customer offramp of "+assetCode );
						obj.add("error", gson.toJsonTree("false"));
						if(langPref.equalsIgnoreCase("ES")) {
							obj.add("message", gson.toJsonTree("Has salido con éxito de la rampa "+assetCode +":"+ amountToOfframp)); 

						}else {
							obj.add("message", gson.toJsonTree(" You have successfuly off-ramped "+assetCode +":"+ amountToOfframp)); 
						}
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
						if (txnUserCode != null)txnUserCode = null; 
						if (porteWalletId != null)porteWalletId = null; if (tokenId != null)tokenId = null;
						if (assetCode != null)assetCode = null; 
						if (txnPayMode != null)txnPayMode = null; if (extSystemRef != null)extSystemRef = null;
						if (walletBalance != null)walletBalance = null; 
						if (walletDetails != null)walletDetails = null; if (tokenValue != null)tokenValue = null;
						if (fiatWalletId != null)fiatWalletId = null; if(authMethod!=null) authMethod= null;
						if (payComments != null)payComments = null; 
						if (referenceNo != null)referenceNo = null; 
						if (cvv != null)cvv = null;if (langPref != null)langPref = null; 
						if (hasMnemonic != null)hasMnemonic = null; 
						if (password != null)password = null; 
						if (mnemoniStringFromDB != null)mnemoniStringFromDB = null; 
						if (keyPair != null)keyPair = null;
						if (sourcePrivateKey != null)sourcePrivateKey = null;
						if (userType != null)userType = null;
						if (currencyId!=null); currencyId=null;   if (walletId!=null); walletId=null; if (authResponse != null)authResponse = null;
						if (transactionCode!=null); transactionCode=null;     if (txnMode!=null); txnMode=null; 
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in geting Transactions, Please try again letter");
				}
			
			break;
								
			default:
				throw new IllegalArgumentException("Rule not defined value: " + rulesaction);
		}
		
	}

	@Override
	public void performOperation(String rules, HttpServletRequest request, HttpServletResponse response, 
			ServletContext ctx)throws Exception {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null) 
			Utilities.callException(request, response, ctx, "Session has expired, please log in again");
		switch (rules){
		
		}
		
	}
}
