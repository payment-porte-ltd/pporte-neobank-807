package com.pporte.rules;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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
import com.pporte.dao.CustomerPorteCoinDao;
import com.pporte.dao.CustomerWalletDao;
import com.pporte.dao.FundStellarAccountDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.dao.TDAManagementDao;
import com.pporte.dao.WalletAuthorizationDao;
import com.pporte.model.AssetAccount;
import com.pporte.model.AssetTransactions;
import com.pporte.model.CryptoAssetCoins;
import com.pporte.model.PorteAsset;
import com.pporte.model.Transaction;
import com.pporte.model.AssetTransaction;
import com.pporte.model.User;
import com.pporte.utilities.Bip39Utility;
import com.pporte.utilities.SendEmailUtility;
import com.pporte.utilities.StellarSDKUtility;
import com.pporte.utilities.Utilities;
import framework.v8.Rules;

public class CustomerPorteCoinRulesImpl implements Rules {
	private static String className = CustomerPorteCoinRulesImpl.class.getSimpleName();

	@Override
	public void performJSONOperation(String arg0, HttpServletRequest arg1, HttpServletResponse arg2,
			ServletContext arg3) throws Exception {
		
		
	}

	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		HttpSession session = request.getSession(false);
		
		switch (rulesaction) {
			case "get_porte_coins_details":
				
				try {
					if (session.getAttribute("SESS_USER") == null) 
						Utilities.callException(request, response, ctx, "Session has expired, please log in again");
					
					JsonObject obj = new JsonObject();
					PrintWriter output = null;
					Gson gson = new Gson();
					User user = null;
					String relationshipNo = null;
					user = (User) session.getAttribute("SESS_USER");
					relationshipNo = user.getRelationshipNo();
					List<PorteAsset> porteCoinList =(List<PorteAsset>) CustomerPorteCoinDao.class.
							getConstructor().newInstance().getPorteAssetDetailsForCustomer(relationshipNo);
					if(porteCoinList !=null) {
						obj.add("data", gson.toJsonTree(porteCoinList));
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
						if (porteCoinList != null)porteCoinList = null; 
						if (user != null)user = null; 
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in geting porte coins, Please try again letter");
				}
			break;
			case "get_crypto_assets_details":
				try {
					JsonObject obj = new JsonObject();
					PrintWriter output = null;
					Gson gson = new Gson();
					List<CryptoAssetCoins> cryptoAssetCoinList =(List<CryptoAssetCoins>)
							CustomerPorteCoinDao.class.getConstructor().newInstance().getPorteCoinDetails();
					
					
					obj.add("data", gson.toJsonTree(cryptoAssetCoinList));
					obj.add("error", gson.toJsonTree("false"));
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						output = response.getWriter();
						output.print(gson.toJson(obj));
					} finally {
						if (output != null)output.close();  if (gson != null)gson = null;
						if (obj != null)obj = null;
						if (cryptoAssetCoinList != null)cryptoAssetCoinList = null; 
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in geting crypto assets, Please try again letter");
				}
				
			break;
			case "get_crypto_assets_details_without_btc":
				try {
					JsonObject obj = new JsonObject();
					PrintWriter output = null;
					Gson gson = new Gson();
					List<CryptoAssetCoins> cryptoAssetCoinList =(List<CryptoAssetCoins>)
							CustomerPorteCoinDao.class.getConstructor().newInstance().getPorteCoinWihoutBTCDetails();
					
					obj.add("data", gson.toJsonTree(cryptoAssetCoinList));
					obj.add("error", gson.toJsonTree("false"));
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						output = response.getWriter();
						output.print(gson.toJson(obj));
					} finally {
						if (output != null)output.close();  if (gson != null)gson = null;
						if (obj != null)obj = null;
						if (cryptoAssetCoinList != null)cryptoAssetCoinList = null; 
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in geting crypto assets, Please try again letter");
				}
				
			break;
			case "get_crypto_assets_details_vesl_porte":
				try {
					JsonObject obj = new JsonObject();
					PrintWriter output = null;
					Gson gson = new Gson();
					List<CryptoAssetCoins> cryptoAssetCoinList =(List<CryptoAssetCoins>)
							CustomerPorteCoinDao.class.getConstructor().newInstance().getVesselAndPorteDetails();
					
					obj.add("data", gson.toJsonTree(cryptoAssetCoinList));
					obj.add("error", gson.toJsonTree("false"));
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						output = response.getWriter();
						output.print(gson.toJson(obj));
					} finally {
						if (output != null)output.close();  if (gson != null)gson = null;
						if (obj != null)obj = null;
						if (cryptoAssetCoinList != null)cryptoAssetCoinList = null; 
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in geting crypto assets, Please try again letter");
				}
				
			break;
			case "transfer_porte_coin":
				try {
					NeoBankEnvironment.setComment(3, className,"==== start transfer_porte_coin " + java.time.LocalTime.now() );

					JsonObject obj = new JsonObject(); User user = null;  String relationshipNo= null; PrintWriter output = null;
					Gson gson = new Gson(); String amount= null; String receiverEmail =null; Boolean success = false;
					String walletBalance = null; 	String txnUserCode = null; String walletDetails =null; 
					String senderWalletId=null; String payComments=""; 	String referenceNo=""; 
					String receiverWalletId = null; String assetCodeSender = null;
					String assetCodeReceiver = null; String txnPayMode = null; String extSystemRef = null; String userId = null;
					String senderKey = null; KeyPair destinationAccount = null; KeyPair sourceAccount =null; 
					String sourcewalletIdInternal = "";	String destinationwalletIdInternal = "";
					String destinationAssetBalance = null; String sourceAssetBalance = null; boolean proceed = true;
					String sourceAssetIssuer = null; String sourceAcountId = null; String results = null;
					String [] splitResult=null;String stellarHash=null;
					String mnemonicCode=null; String langPref = null;
					String hasMnemonic = null; String password  = null; String mnemoniStringFromDB  = null;
					boolean passIsCorrect = false; KeyPair keyPair = null;

					if(request.getParameter("sendamount")!=null) amount = request.getParameter("sendamount").trim();
					if(request.getParameter("sender_asset")!=null)	 assetCodeSender = request.getParameter("sender_asset").trim();
					if(request.getParameter("narrative")!=null)	 payComments = request.getParameter("narrative").trim();
					if(request.getParameter("input_receiver")!=null)	 receiverWalletId = request.getParameter("input_receiver").trim(); //Public Key
					if(request.getParameter("input_private_key")!=null)	 senderKey = request.getParameter("input_private_key").trim();
					if(request.getParameter("mnemonic_code")!=null)	 mnemonicCode = request.getParameter("mnemonic_code").trim();
					if(request.getParameter("hasMnemonic")!=null)	 hasMnemonic = request.getParameter("hasMnemonic").trim();
					if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
					if (session.getAttribute("SESS_USER") == null) 
						throw new Exception ("Session has expired, please log in again");
					user = (User) session.getAttribute("SESS_USER");
					relationshipNo = user.getRelationshipNo();
					
					if(hasMnemonic.equals("true")) {
						if(request.getParameter("security")!=null)	 password = request.getParameter("security").trim();
						passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
								.checkIfPasswordIsCorrect(relationshipNo, password);
						
						if(!passIsCorrect) {
							NeoBankEnvironment.setComment(1, className, "Password is not correct");
							if(langPref.equalsIgnoreCase("en")) {
								Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
							}else {
								Utilities.sendJsonResponse(response, "error", "Por favor ingrese la contrase�a correcta");
							}
							
							return;
						}
						mnemoniStringFromDB = (String) CustomerDao.class.getConstructor().newInstance()
								.getmnemonicCode(relationshipNo);
						keyPair= Bip39Utility.masterKeyGeneration(mnemoniStringFromDB.replaceAll(",", " "));
						senderKey = String.valueOf(keyPair.getSecretSeed());
					}else {
						if(request.getParameter("security")!=null)	 senderKey = request.getParameter("security").trim();
					}
					
				
					
					NeoBankEnvironment.setComment(3, className," inside transfer_porte_coin_mbl is receiverEmail " + receiverEmail +" amount "+ amount + " assetCodeSender "+ assetCodeSender
							+" assetCodeReceiver "+ assetCodeReceiver + " relationshipNo "+ relationshipNo + " userId "+ userId +"mnemonicCode "+mnemonicCode);
					try {
						if(mnemonicCode!=null) {
							NeoBankEnvironment.setComment(3, className,"==== mnemonicCode is not null ");
							KeyPair srcAccount = Bip39Utility.masterKeyGeneration(mnemonicCode);
							sourceAccount = KeyPair.fromSecretSeed(srcAccount.toString());
							NeoBankEnvironment.setComment(3, className,"==== sourceAccount " + sourceAccount+" "+ java.time.LocalTime.now() );
						}else {
							sourceAccount = KeyPair.fromSecretSeed(senderKey);
						}
					}catch (Exception e) {
						Utilities.sendJsonResponse(response, "error", "Key provided is invalid");
						return;
					}
					
					try {
				 		 destinationAccount = KeyPair.fromAccountId(receiverWalletId);
					}catch (Exception e) {
						Utilities.sendJsonResponse(response, "error", "Receiver wallet is invalid");
						return;
					}
					sourceAcountId = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
					if(sourceAcountId == null)
						throw new Exception("Customer has no wallet in stellar");
					
					 if(assetCodeSender.equals(NeoBankEnvironment.getPorteTokenCode())) 
						 sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
									.newInstance().getIssueingAccountPublicKey(assetCodeSender);
						if(assetCodeSender.equals(NeoBankEnvironment.getUSDCCode())) 
							sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
									.newInstance().getIssueingAccountPublicKey(assetCodeSender);
						if(assetCodeSender.equals(NeoBankEnvironment.getVesselCoinCode())) 
							sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
									.newInstance().getIssueingAccountPublicKey(assetCodeSender);
						
						//if(assetCodeSender.equals(NeoBankEnvironment.getStellarLumensCode())) 
							//sourceAssetIssuer = NeoBankEnvironment.getXLMIsssuerAccountId();
					
					KeyPair source = KeyPair.fromSecretSeed(senderKey);
					NeoBankEnvironment.setComment(3, className, "senderKey  "+ senderKey+" sourceAssetIssuer "+
							sourceAssetIssuer+"  account from pvt "+ source.getAccountId());
					if(!source.getAccountId().equals(sourceAcountId)) {
						obj.addProperty("error", "true"); 
		        		obj.addProperty("message", "Secret Key is incorrect"); 
						NeoBankEnvironment.setComment(3, className, "Secret Key is incorrect ");
						Utilities.sendJsonResponse(response, "error", "Secret Key is incorrect ");
						return;
					}
									
					/**Check from the Network if exist*/
					//boolean exist = StellarSDKUtility.CheckAccountIfExist(sourceAccount);
					if(StellarSDKUtility.CheckAccountIfExist(sourceAccount)==false) {
						Utilities.sendJsonResponse(response, "error", "Source account does not exist");
						return;
					}
					if(StellarSDKUtility.CheckAccountIfExist(destinationAccount)==false) {
						Utilities.sendJsonResponse(response, "error", "Destination account does not exist");
						return;
					}
					
					if((sourceAccount.getAccountId()).equals(destinationAccount.getAccountId())) {
						Utilities.sendJsonResponse(response, "error", "You cannot send Asset from your wallet to the same wallet account");
						return;
					}
					
					//Before posting payment first check the FIAT wallet if it has some amount to cater for the internal transaction charge.
					
					/**Post payments on stellar*/
					NeoBankEnvironment.setComment(3, className,"========== getting into Stellar " + java.time.LocalTime.now() );
					
					if(assetCodeSender.equals(NeoBankEnvironment.getXLMCode())) {
						 results = StellarSDKUtility.sendNativeCoinPayment(
								assetCodeSender, sourceAccount, destinationAccount, amount, payComments);					
				 		}else {
						 results = StellarSDKUtility.sendNoNNativeCoinPayment(
								assetCodeSender, sourceAccount, destinationAccount, amount, payComments, sourceAssetIssuer);					
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
					
					sourcewalletIdInternal = (String)CustomerPorteCoinDao.class.getConstructor().newInstance().getAssetWalletDetails(relationshipNo, assetCodeSender, sourceAccount.getAccountId());
					if(sourcewalletIdInternal ==null) {
						NeoBankEnvironment.setComment(3, className,"========== destination not present in porte portfolio  accountId "+ destinationAccount.getAccountId() );

					    //Utilities.sendJsonResponse(response, "error", "Payment successful");
					    proceed = false;
					}
					
					if(assetCodeSender.equals(NeoBankEnvironment.getPorteTokenCode())) {
						txnPayMode= NeoBankEnvironment.getCodePorteUtilityCoinP2P();
					}else if(assetCodeSender.equals(NeoBankEnvironment.getVesselCoinCode())) {
						txnPayMode= NeoBankEnvironment.getCodeVesselCoinP2P();
					}else if(assetCodeSender.equals(NeoBankEnvironment.getXLMCode())) {
						txnPayMode= NeoBankEnvironment.getCodeXLMCoinP2P();
					}else if(assetCodeSender.equals(NeoBankEnvironment.getUSDCCode())) {
						txnPayMode= NeoBankEnvironment.getCodeUSDCCoinP2P();
					}else {
						Utilities.sendJsonResponse(response, "error", "Try again later");
						NeoBankEnvironment.setComment(3, className," Unknown txnPayMode " +txnPayMode );
						return;
					}
					
					txnUserCode = Utilities.generateTransactionCode(10);
					referenceNo = txnPayMode+ "-" + (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
							+ Utilities.genAlphaNumRandom(9);
					
					if(proceed) {  //Proceed to update Internal ledger
						String customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
								NeoBankEnvironment.getDefaultCustomerUserType(),txnPayMode, amount);
						String minimumTxnAmount=customerCharges.substring(customerCharges.indexOf("|")+1, customerCharges.length());
						
						if ( Double.parseDouble(amount)< Double.parseDouble(minimumTxnAmount)) { 
							Utilities.sendJsonResponse(response, "error", "Transaction amount can not be less than "+minimumTxnAmount);
							return;
						}
						
						
						extSystemRef= stellarHash;
						
						success = (Boolean) CustomerPorteCoinDao.class.getConstructor().newInstance().porteCoinP2P(relationshipNo, sourcewalletIdInternal,  amount, payComments, 
								referenceNo, txnUserCode, customerCharges, txnPayMode, assetCodeSender, extSystemRef, destinationwalletIdInternal, sourceAssetBalance, destinationAssetBalance);
						if (success) {
							String moduleCode = txnPayMode;
							///SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, moduleCode,StringUtils.substring("Porte Asset "+assetCodeSender+"  P2P" + referenceNo , 0, 48));
							SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, "C",
									moduleCode," Porte Asset "+assetCodeSender+" "+ txnPayMode);
							obj.add("error", gson.toJsonTree("false"));
							//obj.add("message", gson.toJsonTree("You have transfered " + assetCodeSender+" "+ Utilities.getMoneyinDecimalFormat(amount) + 
							if(langPref.equalsIgnoreCase("en")) {
								obj.add("message", gson.toJsonTree("Payment success: "+ amount +  " "+assetCodeSender )); 
							}else {
								obj.add("message", gson.toJsonTree("Pago exitoso: "+ amount +  " "+assetCodeSender )); 
							}
							

						}else {
							obj.add("error", gson.toJsonTree("true")); 
							if(langPref.equalsIgnoreCase("en")) {
								obj.add("message", gson.toJsonTree("Transaction failed")); 
							}else {
								obj.add("message", gson.toJsonTree("Transacci�n fallida")); 
							}
							
						}
						
						
					}else {
						//This account is not managed by Porte portfolio
						obj.add("error", gson.toJsonTree("false"));
						//obj.add("message", gson.toJsonTree("You have transfered " + assetCodeSender+" "+ Utilities.getMoneyinDecimalFormat(amount) + 
						if(langPref.equalsIgnoreCase("en")) {
							obj.add("message", gson.toJsonTree("Payment success: "+ amount +  " "+assetCodeSender )); 
						}else {
							obj.add("message", gson.toJsonTree("Pago exitoso: "+ amount +  " "+assetCodeSender )); 
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
					if (extSystemRef != null)extSystemRef = null; 
					if (user != null)user = null; 
					if (walletBalance != null)walletBalance = null; 
					if (walletDetails != null)walletDetails = null; 
					if (senderWalletId != null)senderWalletId = null; 
					if (payComments != null)payComments = null; 
					if (hasMnemonic != null)hasMnemonic = null; 
					if (password != null)password = null; 
					if (mnemoniStringFromDB != null)mnemoniStringFromDB = null; 
					if (keyPair != null)keyPair = null; 
					if (senderKey != null)senderKey = null; 
					if (txnUserCode != null)txnUserCode = null; 
					if (sourcewalletIdInternal != null)sourcewalletIdInternal = null; 
					if (referenceNo != null)referenceNo = null; 
					if (receiverWalletId != null) receiverWalletId = null; 
					if (assetCodeSender != null)assetCodeSender = null; if (txnPayMode != null)txnPayMode = null;
					if (stellarHash!=null) stellarHash=null;if (splitResult!=null)splitResult=null;
					NeoBankEnvironment.setComment(3, className,"========== after cleaning transfer_porte_coin_mbl " + java.time.LocalTime.now() );

				}								
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in transfer coin, Please try again letter");
				}
			break;
			case "buy_porte_coin":
				try {
					JsonObject obj = new JsonObject(); String customerCharges = null;
					User user = null; String minimumTxnAmount = null; PrintWriter output = null;
					String relationshipNo= null; double newWalletBalanceFromStellar = 0; 
					Gson gson = new Gson(); String amount= null; String channel =null; // T= Tokenized Card, F=Fiat Wallet, B =Bank  
					Boolean success = false; 	String walletBalance = null; String txnUserCode = null;
					String walletDetails =null; String fiatWalletId=null; 	String porteWalletId=null; //This is either porte token or vessel coin
					String payComments=""; 	String referenceNo=""; String tokenId = null;
					String assetCode = null; String txnPayMode = null; String extSystemRef = null;
					String cvv = null; String amountPorte=null; //Get this from stellar
					String authStatus=""; String authMessage=""; String walletId=null; String authResponse=null;
					boolean recordAuthorization=false; String currencyId = null; String transactionCode=null;
					String txnMode=null; String userType=null;
					String langPref=null;
					if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
					if(request.getParameter("coin_asset")!=null) assetCode = request.getParameter("coin_asset").trim();
					if(request.getParameter("receivedamount")!=null) amount = request.getParameter("receivedamount").trim();
					if(request.getParameter("amount")!=null)	 	amountPorte = request.getParameter("amount").trim();
					if(request.getParameter("comment")!=null)	 payComments = request.getParameter("comment").trim();
					if(request.getParameter("payment_method")!=null) channel = request.getParameter("payment_method").trim();
					if(channel.equalsIgnoreCase("T")) {
					if(request.getParameter("tokenid")!=null)	
							tokenId = request.getParameter("tokenid").trim();
						if(request.getParameter("cvv")!=null)	
							cvv = request.getParameter("cvv").trim();
					}			
					NeoBankEnvironment.setComment(1, className, "amountPorte "+amountPorte);
					NeoBankEnvironment.setComment(1, className, "amount "+amount);
					if (session.getAttribute("SESS_USER") == null) 
						throw new Exception ("Session has expired, please log in again");
					user = (User) session.getAttribute("SESS_USER");
					relationshipNo = user.getRelationshipNo();
					walletDetails = (String)CustomerPorteCoinDao.class.getConstructor()
							.newInstance().getAssetWalletDetails(relationshipNo, assetCode, assetCode);
					if(walletDetails==null) {
						if(langPref.equalsIgnoreCase("en")) {
							Utilities.sendJsonResponse(response, "error", "Dear customer your dont have coresponding wallet, "
									+ "please create wallet and try again");
						}else {
							Utilities.sendJsonResponse(response, "error", "Estimada cliente, no tiene billetera coresponding., "
									+ "por favor crea una billetera e int�ntalo de nuevo");
						}
						
						return;
					}
					userType="C";//Customer
					porteWalletId = walletDetails.split(",")[0];
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
							Utilities.sendJsonResponse(response, "error", "Transaction amount can not be less than "+minimumTxnAmount);
							throw new Exception("minimumAmount amount inputed:- Minimum exptected is:- "+minimumTxnAmount+"Amount inputed is"+amount);
						}
						extSystemRef=Utilities.genAlphaNumRandom(24).toUpperCase(); //Remove this after integration
						success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().buyPorteCoinViaToken(relationshipNo, tokenId,  amount, payComments, 
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
						txnUserCode = Utilities.generateTransactionCode(10);
						transactionCode=(new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
								+ Utilities.genAlphaNumRandom(9);
						referenceNo = txnPayMode+ "-" +transactionCode;
						customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
								NeoBankEnvironment.getDefaultCustomerUserType(),txnPayMode, amount);
						 minimumTxnAmount=customerCharges.substring(customerCharges.indexOf("|")+1, customerCharges.length());
						
						if ( Double.parseDouble(amount)< Double.parseDouble(minimumTxnAmount)) { 
							Utilities.sendJsonResponse(response, "error", "Transaction amount can not be less than "+minimumTxnAmount);
							return;
						}
						//Check balance //
						double senderDebitAmount =  Double.parseDouble(amount) + 
								Double.parseDouble(customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|")));
						if ( senderDebitAmount> Double.parseDouble(walletBalance)) {
							Utilities.sendJsonResponse(response, "error", "Dear customer you have insufficient funds, Kindly top up and try again");
							return;
						}
						/****** Wallet Authorization******/
						
						authResponse=(String)WalletAuthorizationDao.class.getConstructor().newInstance().performWalletAuthorization(relationshipNo, amount, txnMode);
						if (authResponse.isEmpty()) {
							Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
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
								 Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again");
							return;
							}
							     Utilities.sendJsonResponse(response, "authfailed", authMessage);
							return;
						}else {
							// Record successful authorization
							recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, amount, txnMode, currencyId, 
									userType, referenceNo, walletId, authStatus, authMessage);
							if(!recordAuthorization) { 
								 Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again");
							return;
							}
						}
						/****** End of Wallet Authorization******/	
						
						
						//Connect to External API here
						//extSystemRef=Utilities.genAlphaNumRandom(24).toUpperCase(); //Remove this after integration
						success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().buyPorteCoinViaFiatWallet(relationshipNo, fiatWalletId,  amount, payComments, 
								 referenceNo, txnUserCode, txnPayMode, assetCode,  porteWalletId, newWalletBalanceFromStellar, customerCharges, amountPorte,transactionCode );
					}
					if (success) {
						
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, "C",
								txnPayMode,"Bought BTCx Using fiat money");
						obj.add("error", gson.toJsonTree("false"));
						if(langPref.equalsIgnoreCase("en")) {
							obj.add("message", gson.toJsonTree(" You Transaction of "+assetCode +":"+ amountPorte + 
									" is being processed we will notify you once the operation is done ")); 
						}else {
							obj.add("message", gson.toJsonTree(" Tu Transacci�n de "+assetCode +":"+ amountPorte + 
									" se est� procesando te avisaremos una vez finalizada la operaci�n ")); 
						}
						
					}else {
						obj.add("error", gson.toJsonTree("true")); 
						if(langPref.equalsIgnoreCase("en")) {
							obj.add("message", gson.toJsonTree("Transaction failed")); 
						}else {
							obj.add("message", gson.toJsonTree("Transacci�n fallida")); 
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
						if (amount != null)amount = null; 
						if (user != null)user = null; 
						if (walletBalance != null)walletBalance = null; 
						if (walletDetails != null)walletDetails = null; 
						if (fiatWalletId != null)fiatWalletId = null; 
						if (payComments != null)payComments = null; 
						if (referenceNo != null)referenceNo = null; 
						if (cvv != null)cvv = null; 
						if (userType!=null) userType=null;
						if (currencyId!=null); currencyId=null;   if (walletId!=null); walletId=null; if (authResponse != null)authResponse = null;
						if (transactionCode!=null); transactionCode=null;    if (txnMode!=null); txnMode=null; 
					}								
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in coin purchase, Please try again letter");
				}
			break;
			case "get_porte_txn_btn_dates":
				try {
					JsonObject obj = new JsonObject();
					User user = null;
					String relationshipNo= null;
					PrintWriter output = null;
					List<AssetTransaction> transactionList = null;
					ConcurrentHashMap<String, String> hashTxnRules = null;
					Gson gson = new Gson();
					String txnDesc= null;
					String dateTo= null;
					String dateFrom =null;
					String oldFormat = "MM/dd/yyyy";
					String newFormat = "yyyy-MM-dd";
					SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
					
					if(request.getParameter("datefrom")!=null)	
						dateFrom = request.getParameter("datefrom").trim();
					if(request.getParameter("dateto")!=null)	
						dateTo = request.getParameter("dateto").trim();
					
					 if (session.getAttribute("SESS_USER") == null) 
							throw new Exception ("Session has expired, please log in again");
					user = (User) session.getAttribute("SESS_USER");
					relationshipNo = user.getRelationshipNo();
					hashTxnRules = (ConcurrentHashMap<String, String>)CustomerWalletDao.class.getConstructor()
							.newInstance().getTransactionRules();
					
					
					Date d = sdf.parse(dateFrom); Date d2 = sdf.parse(dateTo);
					sdf.applyPattern(newFormat);  sdf.applyPattern(newFormat);
					dateFrom = sdf.format(d);     dateTo = sdf.format(d2);
					
					NeoBankEnvironment.setComment(3, className, "dateFrom "+ dateFrom);
					NeoBankEnvironment.setComment(3, className, "dateTo "+ dateTo);
					
					transactionList = (List<AssetTransaction>)CustomerPorteCoinDao.class.getConstructor()
							.newInstance().getPorteCoinTxnBtnDates(dateFrom, dateTo, relationshipNo);
					NeoBankEnvironment.setComment(3, className, "After fetch ");
					if (transactionList!=null) {
						for (int i = 0; i <transactionList.size(); i++) {
							if (transactionList.get(i).getSystemReferenceInt()!=null) {
								if (transactionList.get(i).getSystemReferenceInt() .indexOf("-AC") == -1) {
									txnDesc= hashTxnRules.get(transactionList.get(i).getSystemReferenceInt().split("-")[0]);
									transactionList.get(i).setTxnDescription(txnDesc);
								}else {
									txnDesc= hashTxnRules.get(transactionList.get(i).getSystemReferenceInt().split("-")[0])+" "+"Transaction Charge";
									transactionList.get(i).setTxnDescription(txnDesc);
								}
							}
						}
					}
					NeoBankEnvironment.setComment(3, className, "After loop ");
					obj.add("data", gson.toJsonTree(transactionList));
					obj.add("error", gson.toJsonTree("false"));
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						output = response.getWriter();
						output.print(gson.toJson(obj));
					} finally {
						if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						if (obj != null)obj = null; if (dateFrom != null)dateFrom = null;
						if (transactionList != null)transactionList = null; 
						if (user != null)user = null; 
						if (dateTo != null)dateTo = null; 
						if (hashTxnRules != null)hashTxnRules = null; 
						if (txnDesc != null)txnDesc = null; 
						if (sdf != null)sdf = null; if (d != null)d = null; 
						if (d2 != null)d2  = null; 
					}								
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in buying coin, Please try again letter");
				}
				break;
				
			case "get_last_five_porte_txn":
				try {
					JsonObject obj = new JsonObject();
					User user = null;
					String relationshipNo= null;
					PrintWriter output = null;
					List<AssetTransaction> transactionList = null;
					ConcurrentHashMap<String, String> hashTxnRules = null;
					Gson gson = new Gson();
					String txnDesc= null;
					 if (session.getAttribute("SESS_USER") == null) 
							throw new Exception ("Session has expired, please log in again");
					user = (User) session.getAttribute("SESS_USER");
					relationshipNo = user.getRelationshipNo();
					transactionList = (List<AssetTransaction>)CustomerPorteCoinDao.class.getConstructor()
							.newInstance().getPorteLastFiveTxn(relationshipNo);
					
					hashTxnRules = (ConcurrentHashMap<String, String>)CustomerWalletDao.class.getConstructor()
							.newInstance().getTransactionRules();
					if (transactionList!=null) {
						for (int i = 0; i <transactionList.size(); i++) {
							if (transactionList.get(i).getSystemReferenceInt()!=null) {
								if (transactionList.get(i).getSystemReferenceInt() .indexOf("-AC") == -1) {
									txnDesc= hashTxnRules.get(transactionList.get(i).getSystemReferenceInt().split("-")[0]);
									transactionList.get(i).setTxnDescription(txnDesc);
								}else {
									txnDesc= hashTxnRules.get(transactionList.get(i).getSystemReferenceInt().split("-")[0])+" "+"Transaction Charge";
									transactionList.get(i).setTxnDescription(txnDesc);
								}
							}
						}
					}
					obj.add("data", gson.toJsonTree(transactionList));
					obj.add("error", gson.toJsonTree("false"));
					
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						output = response.getWriter();
						output.print(gson.toJson(obj));
					} finally {
						if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						if (obj != null)obj = null;
						if (transactionList != null)transactionList = null; 
						if (user != null)user = null; 
						if (hashTxnRules != null)hashTxnRules = null; 
						if (txnDesc != null)txnDesc = null; 
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in geting Transactions, Please try again letter");
				}
				break;
			case "sell_porte_coin":
				try {
					JsonObject obj = new JsonObject(); PrintWriter output = null;
					String sourceAssetCode = null; 	
					String destionAssetCode = null; 	
					String sourceAssetIssuer = null; 	
					String destinationIssuier = null; 	
					String sourceAmount = null; 
					String destMinAmount = null; 
					String privateKey = null; 
					String sourceAcountId = null; 
					String relationshipNo= null;
					User user = null;
					String hasMnemonic = null; String password  = null; String mnemoniStringFromDB  = null;
					boolean passIsCorrect = false; KeyPair keyPair = null;
					String langPref=null;
					if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
					
					if(request.getParameter("coin_asset")!=null)	 sourceAssetCode = request.getParameter("coin_asset").trim();
					if(request.getParameter("receiver_asset")!=null)	 destionAssetCode = request.getParameter("receiver_asset").trim();
					if(request.getParameter("sell_amount")!=null)	 sourceAmount = request.getParameter("sell_amount").trim();
					if(request.getParameter("receivedamount")!=null)	 destMinAmount = request.getParameter("receivedamount").trim();
					//if(request.getParameter("private_key")!=null)	 privateKey = request.getParameter("private_key").trim();
					if(request.getParameter("relno")!=null)	 relationshipNo = request.getParameter("relno").trim();
					
					if(request.getParameter("hasMnemonic")!=null)	 hasMnemonic = request.getParameter("hasMnemonic").trim();
					if(hasMnemonic.equals("true")) {
						if(request.getParameter("security")!=null)	 password = request.getParameter("security").trim();
						passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
								.checkIfPasswordIsCorrect(relationshipNo, password);
						if(!passIsCorrect) {
							NeoBankEnvironment.setComment(1, className, "Password is not correct");
							if(langPref.equalsIgnoreCase("en")) {
								Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
							}else {
								Utilities.sendJsonResponse(response, "error", "Por favor ingrese la contrase�a correcta");
							}
							return;
						}
						mnemoniStringFromDB = (String) CustomerDao.class.getConstructor().newInstance()
								.getmnemonicCode(relationshipNo);
						keyPair= Bip39Utility.masterKeyGeneration(mnemoniStringFromDB.replaceAll(",", " "));
						privateKey = String.valueOf(keyPair.getSecretSeed());
					}else {
						if(request.getParameter("security")!=null)	 privateKey = request.getParameter("security").trim();
					}
					
					if (session.getAttribute("SESS_USER") == null) {
						if(langPref.equalsIgnoreCase("en")) {
							Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
						}else {
							Utilities.sendSessionExpiredResponse(response, "error", "La sesión ha caducado, vuelva a iniciar sesión");
						}
						return;
					}
					
					//user = (User) session.getAttribute("SESS_USER");
					//relationshipNo = user.getRelationshipNo();
					sourceAcountId = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
					if(sourceAcountId == null)
						throw new Exception("Customer has no wallet in stellar");
				 	KeyPair sourceAccountKeyPair = KeyPair.fromSecretSeed(privateKey);

					NeoBankEnvironment.setComment(3, className," sourceAcountId " + sourceAcountId+ " sourceAccountKeyPair "
							+ ""+ sourceAccountKeyPair.getAccountId() + " privateKey "+ privateKey);

				 	if(!sourceAccountKeyPair.getAccountId().equals(sourceAcountId)) {
				 		obj.addProperty("error","true"); 
						obj.addProperty("message", "Incorrect Secret key");
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
							+ " sourceAmount "+ sourceAmount + " destMinAmount "+destMinAmount +" privateKey "+ privateKey);
				
					String result = StellarSDKUtility.pathPaymentStrictSend(sourceAssetCode, destionAssetCode, sourceAssetIssuer, destinationIssuier,
							destMinAmount, sourceAmount, privateKey);
					
					if(result.equals("success")) {
						NeoBankEnvironment.setComment(3, className,"========== Payment success on Stellar " + java.time.LocalTime.now() );
						
						obj.addProperty("error","false"); 
						if(langPref.equalsIgnoreCase("en")) {
							obj.addProperty("message", "You've successfuly swapped "+sourceAmount+" "+ sourceAssetCode+ " to "+destMinAmount+" "+ destionAssetCode); 
						}else {
							obj.addProperty("message", "Has intercambiado con éxito "+sourceAmount+" "+ sourceAssetCode+ " a "+destMinAmount+" "+ destionAssetCode); 
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
						if (sourceAssetCode != null)sourceAssetCode = null; 
						if (destionAssetCode != null)destionAssetCode = null;
						if (sourceAssetIssuer != null)sourceAssetIssuer = null; 
						if (privateKey != null)privateKey = null; if (user != null)user = null; 
						if (hasMnemonic != null)hasMnemonic = null; 
						if (password != null)password = null; 
						if (mnemoniStringFromDB != null)mnemoniStringFromDB = null; 
						if (keyPair != null)keyPair = null;
					}								
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Exchange Assets Failed, Try again");
				}
				break;
				
			case "get_expected_amount":
				try {
					String sourceAssetType = null; String sourceAssetCode= null;
					String sourceAssetIssuer=null; String sourceAmount=null; String destAssetCode = null;
					String destAssetIssuer=null; String destinationAmount = null;
					JsonObject obj = new JsonObject();
					PrintWriter output = null;
					if(request.getParameter("coin_asset")!=null)	
						sourceAssetCode = request.getParameter("coin_asset").trim();
					if(request.getParameter("receiver_asset")!=null)	
						destAssetCode = request.getParameter("receiver_asset").trim();
					if(request.getParameter("amount")!=null)	
						sourceAmount = request.getParameter("amount").trim();
					
					//Source Asset
					if(sourceAssetCode.equals(NeoBankEnvironment.getPorteTokenCode())) {
						sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(sourceAssetCode);
						sourceAssetType  ="credit_alphanum12";
					}
					if(sourceAssetCode.equals(NeoBankEnvironment.getUSDCCode())) {
						sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(sourceAssetCode);
						sourceAssetType  ="credit_alphanum4";
					}
					if(sourceAssetCode.equals(NeoBankEnvironment.getVesselCoinCode())) {
						sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(sourceAssetCode);
						sourceAssetType  ="credit_alphanum4";
					}
					if(sourceAssetCode.equals(NeoBankEnvironment.getXLMCode())) {
						sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(sourceAssetCode);
						sourceAssetType  ="native";
					}
					if(sourceAssetCode.equals(NeoBankEnvironment.getStellarBTCxCode())) {
						sourceAssetIssuer =  (String)TDAManagementDao.class.getConstructor().newInstance().getBTCxIssuingAccount();
						sourceAssetType  ="credit_alphanum4";
					}
					//Destination Asset
					if(destAssetCode.equals(NeoBankEnvironment.getPorteTokenCode())) {
						destAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(destAssetCode);
						sourceAssetType  ="credit_alphanum12";
					}
					if(destAssetCode.equals(NeoBankEnvironment.getUSDCCode())) {
						sourceAssetType  ="credit_alphanum4";
						destAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(destAssetCode);
					}
					if(destAssetCode.equals(NeoBankEnvironment.getVesselCoinCode())) {
						destAssetIssuer =  (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(destAssetCode);
						sourceAssetType  ="credit_alphanum4";
					}
					if(destAssetCode.equals(NeoBankEnvironment.getVesselCoinCode())) {
						destAssetIssuer =  (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(destAssetCode);
						sourceAssetType  ="credit_alphanum4";
					}
					if(destAssetCode.equals(NeoBankEnvironment.getXLMCode())) {
						sourceAssetIssuer =  (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(destAssetCode);
						sourceAssetType  ="native";
					}
					if(destAssetCode.equals(NeoBankEnvironment.getStellarBTCxCode())) {
						sourceAssetIssuer =  (String)TDAManagementDao.class.getConstructor().newInstance().getBTCxIssuingAccount();
						sourceAssetType  ="credit_alphanum4";
					}
					NeoBankEnvironment.setComment(3, className, "sourceAssetType_ "+sourceAssetType+" sourceAssetIssuer "+sourceAssetIssuer+
							" sourceAssetCode_ "+sourceAssetCode+" sourceAmount_ "+
							sourceAmount+" destAssetCode_ "+destAssetCode+" destAssetIssuer_ "+destAssetIssuer);
					
					
					destinationAmount = StellarSDKUtility.getPathStrictSendWithDestinationAssets(sourceAssetType, sourceAssetCode,
							sourceAssetIssuer, sourceAmount, destAssetCode, destAssetIssuer);
					if(destinationAmount==null || destinationAmount=="" ) {
						throw new Exception("In geting destination amount");
					}
					
					obj.addProperty("destination_amount", destinationAmount);
					obj.addProperty("error","false");
					
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + obj.toString());
						output = response.getWriter();
						output.print(obj);
					} finally {
						if (output != null)output.close(); if (sourceAssetType != null)sourceAssetType = null;
						if (obj != null)obj = null;if (sourceAssetCode != null)sourceAssetCode = null; 
						if (sourceAssetIssuer != null)sourceAssetIssuer = null; 
						if (sourceAmount != null)sourceAmount = null; if (destinationAmount != null)destinationAmount = null; 
						if (destAssetCode != null)destAssetCode = null; if (destAssetIssuer != null)destAssetIssuer = null;
					}
						
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in getting destination asset, Please try again letter");
				}
				
			break;
			
			case "porte_asset_conversion":
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" inside porte_asset_conversion ");

					JsonObject obj = new JsonObject(); User user = null; String sourceAsset= null;
					String destAssetCode= null; PrintWriter output = null; Gson gson = new Gson();
					String sourceAssetIssuer= null;  String destinationAssetIssuer= null; String sourceAmount= null;
					double destinationAmount= 0;  String sourceAssetType= null; String destinationCurrency=null;

					if(request.getParameter("source")!=null)	 sourceAsset = request.getParameter("source").trim();
					if(request.getParameter("destination")!=null)	 destAssetCode = request.getParameter("destination").trim();
					if(request.getParameter("sourceamount")!=null)	 sourceAmount = request.getParameter("sourceamount").trim();
					
					
					destinationCurrency=NeoBankEnvironment.getUSDCurrencyId();
					NeoBankEnvironment.setComment(3, className, " in porte_asset_conversion sourceAsset "+sourceAsset+" "
							+ "destAssetCode "+ destAssetCode + " sourceAmount "+ sourceAmount  +" destinationCurrency "+destinationCurrency);
					
					destinationAmount = (double)CustomerPorteCoinDao.class.getConstructor().newInstance().getPorteAssetConversion( destAssetCode, sourceAmount,destinationCurrency);
					
					NeoBankEnvironment.setComment(3, className, " destinationAmount" + destinationAmount);
					
					 String finalDestinationAmaountString = null;
					 DecimalFormat df = null;
					if(destinationAmount > 0) {
						df = new DecimalFormat("#.#######");
					    df.setRoundingMode(RoundingMode.CEILING);
					    finalDestinationAmaountString = df.format(destinationAmount);
					    
						obj.add("data", gson.toJsonTree(finalDestinationAmaountString));
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
						if (user != null)user = null;  if (df != null)df = null; if (destinationCurrency!=null)destinationCurrency=null;
						if (sourceAmount != null)sourceAmount = null; if (sourceAssetType != null)sourceAssetType = null; 
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in geting Transaction rate, Please try again letter");
				}
				break;
			case "porte_asset_conversion_off_ramp":
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" inside porte_asset_conversion ");

					JsonObject obj = new JsonObject(); User user = null; String sourceAsset= null;
					PrintWriter output = null; Gson gson = new Gson();
					String amount= null;
					double destinationAmount= 0; String currency=NeoBankEnvironment.getUSDCurrencyId(); 

					if(request.getParameter("source")!=null)	 sourceAsset = request.getParameter("source").trim();
					if(request.getParameter("off_ramp_amount")!=null)	 amount = request.getParameter("off_ramp_amount").trim();
					
					NeoBankEnvironment.setComment(3, className, " in porte_asset_conversion sourceAsset "+sourceAsset+" "
							 + " amount "+ amount  );
					
					destinationAmount = (double)CustomerPorteCoinDao.class.getConstructor().newInstance().getPorteAssetConversionOffRamping(sourceAsset, amount,currency);
					
					NeoBankEnvironment.setComment(3, className, " destinationAmount" + destinationAmount);
					
					 String finalDestinationAmaountString = null;
					 DecimalFormat df = null;
					if(destinationAmount > 0) {
						df = new DecimalFormat("#.#######");
					    df.setRoundingMode(RoundingMode.CEILING);
					    finalDestinationAmaountString = df.format(destinationAmount);
					    
						obj.add("data", gson.toJsonTree(finalDestinationAmaountString));
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
						if (obj != null)obj = null;
						
						if (user != null)user = null;  if (df != null)df = null;  if (currency!=null)currency=null;
						
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in geting Transaction rate, Please try again letter");
				}
				break;
				
			case "burn_porte_coins":
				try {
					JsonObject obj = new JsonObject(); 
					User user = null; String minimumTxnAmount = null;
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
					boolean burnIsSuccussful = false;
					String sourcePrivateKey = null;
					String hasMnemonic = null; String password  = null; String mnemoniStringFromDB  = null;
					boolean passIsCorrect = false; KeyPair keyPair = null;
					String authStatus=""; String authMessage=""; String walletId=null; String authResponse=null;
					boolean recordAuthorization=false; String currencyId = null; String transactionCode=null;
					String txnMode=null; String userType=null;
					String subject=null; String content=null;String langPref=null;
					String businessThreshold=null; String businessLedgerBalance=null;
					
					if(request.getParameter("coin_asset")!=null) assetCode = request.getParameter("coin_asset").trim();
					if(request.getParameter("sell_amount")!=null) amountToOfframp = request.getParameter("sell_amount").trim();
					if(request.getParameter("amount_usd")!=null)	 	amountUSD = request.getParameter("amount_usd").trim();
					if(request.getParameter("comment")!=null)	 payComments = request.getParameter("comment").trim();
					//if(request.getParameter("private_key")!=null)	 sourcePrivateKey = request.getParameter("private_key").trim();
					if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
					if(request.getParameter("hasMnemonic")!=null)	 hasMnemonic = request.getParameter("hasMnemonic").trim();
					NeoBankEnvironment.setComment(3, className, "langPref "+langPref);

					if (session.getAttribute("SESS_USER") == null) 
						throw new Exception ("Session has expired, please log in again");
					user = (User) session.getAttribute("SESS_USER");
					relationshipNo = user.getRelationshipNo();
					NeoBankEnvironment.setComment(3, className, "hasMnemonic "+hasMnemonic);
					if(hasMnemonic.equals("true")) {
						if(request.getParameter("security")!=null)	 password = request.getParameter("security").trim();
						passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
								.checkIfPasswordIsCorrect(relationshipNo, password);
						if(!passIsCorrect) {
							NeoBankEnvironment.setComment(1, className, "Password is not correct");
							
							if(langPref.equalsIgnoreCase("en")) {
								Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
							}else {
								Utilities.sendJsonResponse(response, "error", "Por favor ingrese la contrase�a correcta");
							}
							
							return;
						}
						mnemoniStringFromDB = (String) CustomerDao.class.getConstructor().newInstance()
								.getmnemonicCode(relationshipNo);
						keyPair= Bip39Utility.masterKeyGeneration(mnemoniStringFromDB.replaceAll(",", " "));
						sourcePrivateKey = String.valueOf(keyPair.getSecretSeed());
						//NeoBankEnvironment.setComment(3, className, "Private Key from mnemonic is "+sourcePrivateKey);
					}else {
						if(request.getParameter("security")!=null)	 sourcePrivateKey = request.getParameter("security").trim();
					}
					 //NeoBankEnvironment.setComment(3, className, "sourcePrivateKey "+sourcePrivateKey);

					NeoBankEnvironment.setComment(1, className, "amountToOfframp "+amountToOfframp);
					NeoBankEnvironment.setComment(1, className, "amountUSD "+amountUSD);

					walletDetails = (String)CustomerWalletDao.class.getConstructor()
							.newInstance().getFiatWalletBalance(relationshipNo);
					fiatWalletId = walletDetails.split(",")[0];
					walletBalance = walletDetails.split(",")[1];
					
					
					if(assetCode.equals(NeoBankEnvironment.getPorteTokenCode())) {
						assetIssuer =  (String)CustomerDigitalAssetsDao.class.getConstructor()
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
						Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
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
							 Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again");
						return;
						}
						     Utilities.sendJsonResponse(response, "authfailed", authMessage);
						return;
					}else {
						// Record successful authorization
						recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, amountUSD, txnMode, currencyId, 
								userType, referenceNo, walletId, authStatus, authMessage);
						if(!recordAuthorization) { 
							 Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again");
						return;
						}
					}
					/****** End of Wallet Authorization******/	
						
					// TODO:- Get stellar hash from the API
					//Connect to External API here
					extSystemRef=Utilities.genAlphaNumRandom(24).toUpperCase(); //Remove this after integration
					 //burnCoin(String sourcePrivateKey, 
					//			String assetCode, String issuerPublicKey, String coinsToBurn, String Comment)
					burnIsSuccussful = StellarSDKUtility.burnCoin(sourcePrivateKey, assetCode, assetIssuer, amountToOfframp, payComments);
					if(!burnIsSuccussful) 
						throw new Exception("Burning coin failed");
					success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().burnPorteCoinToFiatWallet(relationshipNo, fiatWalletId, amountUSD , payComments, 
							 referenceNo, txnUserCode, txnPayMode, assetCode, extSystemRef, porteWalletId,  amountToOfframp , transactionCode);
					if (success) {
						
				
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, userType,
								txnPayMode, " Customer offramp of "+assetCode );
						// Get the business ledger balance
						businessLedgerBalance=(String)WalletAuthorizationDao.class.getConstructor().newInstance().getBusinessLedgerBalance();
						businessThreshold=NeoBankEnvironment.getBussinessLedgerThreshold();
						if (businessLedgerBalance!=null) {
							if (Double.parseDouble(businessThreshold)>Double.parseDouble(businessLedgerBalance)) {
								subject="BUSINESS LEDGER BALANCE BELOW THRESHOLD!";
								 content="The current business ledger is below the business threhold. Please top it up";
								// Send to user
								String sendto = NeoBankEnvironment.getAdminEmailAddressNotifier();
								String sendSubject = subject;
								String sendContent = content;
								String customerName = "Admin";
								
								ExecutorService executor = (ExecutorService) ctx.getAttribute("EMAIL_EXECUTOR");
								NeoBankEnvironment.setComment(3, className, "executor is  " + executor);
								@SuppressWarnings({ "unused", "rawtypes" })
								Future future = (Future) executor.submit(new Runnable() {
									public void run() {
										
										try {
											SendEmailUtility.sendMail(sendto, sendSubject, sendContent,customerName);
										} catch (Exception e) {
											NeoBankEnvironment.setComment(1, className, "Exception in sending mail " + e.getMessage());
										}
									}
								});
							}
						}
						
						obj.add("error", gson.toJsonTree("false"));
						if(langPref.equalsIgnoreCase("en")) {
							obj.add("message", gson.toJsonTree(" You have successfuly off-ramped "+assetCode +":"+ amountToOfframp)); 
						}else {
							obj.add("message", gson.toJsonTree(" Te has desviado con �xito "+assetCode +":"+ amountToOfframp)); 
						}
						
					}else {
						obj.add("error", gson.toJsonTree("true")); 
						if(langPref.equalsIgnoreCase("en")) {
							obj.add("message", gson.toJsonTree("Transaction failed")); 	
						}else {
							obj.add("message", gson.toJsonTree("Transacci�n fallida")); 
						}
					}
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						output = response.getWriter();
						output.print(gson.toJson(obj));
					} finally {
						if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						if (obj != null)obj = null; if (minimumTxnAmount != null)minimumTxnAmount = null;
						if (txnUserCode != null)txnUserCode = null; if (langPref!=null) langPref=null;
						if (porteWalletId != null)porteWalletId = null; if (tokenId != null)tokenId = null;
						if (assetCode != null)assetCode = null; 
						if (txnPayMode != null)txnPayMode = null; if (extSystemRef != null)extSystemRef = null;
						if (user != null)user = null; 
						if (walletBalance != null)walletBalance = null; 
						if (walletDetails != null)walletDetails = null; 
						if (fiatWalletId != null)fiatWalletId = null; 
						if (payComments != null)payComments = null; 
						if (referenceNo != null)referenceNo = null; 
						if (cvv != null)cvv = null; 
						if (hasMnemonic != null)hasMnemonic = null; 
						if (password != null)password = null; 
						if (mnemoniStringFromDB != null)mnemoniStringFromDB = null; 
						if (keyPair != null)keyPair = null;
						if (userType != null)userType = null;
						if (currencyId!=null); currencyId=null;   if (walletId!=null); walletId=null; if (authResponse != null)authResponse = null;
						if (transactionCode!=null); transactionCode=null;     if (txnMode!=null); txnMode=null; 
						if (subject!=null);subject=null; if (content!=null);content=null;
						if (businessThreshold!=null);businessThreshold=null; if (businessLedgerBalance!=null);businessLedgerBalance=null;
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in geting Transactions, Please try again letter");
				}
			case "get_cust_next_transactions":
				try {
					JsonObject obj = new JsonObject(); 
					ArrayList<AssetTransactions> assetTransactions =null;
					String nextLink=null; Gson gson = new Gson();PrintWriter output = null;
					String publicKey=null;
					
					if(request.getParameter("link")!=null)	 nextLink = request.getParameter("link").trim();
					if(request.getParameter("publickey")!=null)	 publicKey = request.getParameter("publickey").trim();
							// For Next Transactions all the required parameters are present on the link 
						assetTransactions = StellarSDKUtility.getAccountTransactionsCustomerWeb(publicKey, nextLink);
						
						obj.add("data", gson.toJsonTree(assetTransactions));
						obj.add("error", gson.toJsonTree("false"));
					
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						output = response.getWriter();
						output.print(gson.toJson(obj));
					} finally {
						if (output != null)output.close();  if (gson != null)gson = null;
						if (obj != null)obj = null; if (assetTransactions!=null)assetTransactions=null;
						if(nextLink!=null)nextLink=null; if (publicKey!=null)publicKey=null;
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in geting Transaction rate, Please try again letter");
				}
				break;
			case "get_cust_prev_transactions":
				try {
					JsonObject obj = new JsonObject(); 
					ArrayList<AssetTransactions> assetTransactions =null;
					String prevLink=null; Gson gson = new Gson();PrintWriter output = null;
					String publicKey=null;
					
					if(request.getParameter("link")!=null)	 prevLink = request.getParameter("link").trim();
					if(request.getParameter("publickey")!=null)	 publicKey = request.getParameter("publickey").trim();
					// For Prev Transactions all the required parameters are present on the link 
					assetTransactions = StellarSDKUtility.getAccountTransactionsCustomerWeb(publicKey,prevLink);
					
					obj.add("data", gson.toJsonTree(assetTransactions));
					obj.add("error", gson.toJsonTree("false"));
					
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						output = response.getWriter();
						output.print(gson.toJson(obj));
					} finally {
						if (output != null)output.close();  if (gson != null)gson = null;
						if (obj != null)obj = null;if (assetTransactions!=null)assetTransactions=null;
						if(prevLink!=null)prevLink=null; if (publicKey!=null)publicKey=null;
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in geting Transaction rate, Please try again letter");
				}
				break;
				
			case "exchange_asset_to_currency":
				try {
					
					JsonObject obj = new JsonObject();  String sourceAsset= null;
					String destAssetCode= null; PrintWriter output = null; Gson gson = new Gson();
					String sourceAssetIssuer= null;  String destinationAssetIssuer= null; String sourceAmount= null;
					String destinationAmount= null;  String sourceAssetType= null; String destinationCurrency=null; double expectedAmount=0;
					
					if(request.getParameter("sourceasset")!=null)	 sourceAsset = request.getParameter("sourceasset").trim();
					if(request.getParameter("destcurrency")!=null)	 destinationCurrency = request.getParameter("destcurrency").trim();
					if(request.getParameter("amount")!=null)	 sourceAmount = request.getParameter("amount").trim();
					
					
					destAssetCode=NeoBankEnvironment.getUSDCCode();
					NeoBankEnvironment.setComment(3, className, " in exchange_asset_to_currency sourceAsset "+sourceAsset+" "
							+  " destinationCurrency "+ destinationCurrency + " sourceAmount "+ sourceAmount+" destAssetCode"+destAssetCode);
					
					if (!sourceAsset.equals(NeoBankEnvironment.getUSDCCode())) {
					// 1. Exchange Source asset to USDC
							// Get Source assset issuer
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
						    } else if(sourceAsset.equals(NeoBankEnvironment.getXLMCode())) {
								sourceAssetType  ="native";
								sourceAsset = "";
								sourceAssetIssuer = "";
							}
					
						// Get USDC issuer as destination user
							
							destinationAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
									.newInstance().getIssueingAccountPublicKey(destAssetCode);
					
							
						//Get Destination amount
						NeoBankEnvironment.setComment(3, className, " sourceAsset "+sourceAsset+" destAssetCode "+ destAssetCode
								+ " sourceAmount "+ sourceAmount +" sourceAssetIssuer "+ sourceAssetIssuer +" sourceAssetType "+ sourceAssetType
								+ " destinationAssetIssuer "+ destinationAssetIssuer);
						
						destinationAmount = StellarSDKUtility.getPathStrictSendWithDestinationAssets(sourceAssetType, sourceAsset,
								sourceAssetIssuer, sourceAmount, destAssetCode, destinationAssetIssuer);
						
						NeoBankEnvironment.setComment(3, className, " destinationAmount" + destinationAmount);
						}else {
							destinationAmount=sourceAmount;
						}
						//2. Convert the USDC amount to the selected destination currency
						
					expectedAmount = (double)CustomerPorteCoinDao.class.getConstructor().newInstance().getPorteAssetConversionOffRamping( destAssetCode, destinationAmount,destinationCurrency);
					
					NeoBankEnvironment.setComment(3, className, " expectedAmount" + expectedAmount);
					
					 String finalDestinationAmountString = null;
					 DecimalFormat df = null;
					if(expectedAmount > 0) {
						df = new DecimalFormat("#.#######");
					    df.setRoundingMode(RoundingMode.CEILING);
					    finalDestinationAmountString = df.format(expectedAmount);
					    
						obj.add("amount_expected", gson.toJsonTree(finalDestinationAmountString));
						obj.add("usdc_destination_amount", gson.toJsonTree(destinationAmount));
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
						if (destinationCurrency != null)destinationCurrency = null; if (destinationAmount != null)destinationAmount = null; 
						if (sourceAmount != null)sourceAmount = null; if (sourceAssetType != null)sourceAssetType = null;
						if (finalDestinationAmountString!=null)finalDestinationAmountString=null;
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in converting rates, Please try again letter");
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
		case "Assets":
			try {
				String langPref=null;
				if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
				request.setAttribute("lastaction", "porte");
				request.setAttribute("lastrule", "Assets");
				request.setAttribute("langpref", langPref);
				response.setContentType("text/html");
				User user = null;
				String relationshipNo= null;
				 if (session.getAttribute("SESS_USER") == null) 
						throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				relationshipNo = user.getRelationshipNo();
				String publicKey = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
				KeyPair userAccount = null;
				NeoBankEnvironment.setComment(3, className, " Public key is  "+publicKey);
				ArrayList<AssetAccount> accountBalances = null;
				if(publicKey != null && publicKey != "") {
					userAccount = KeyPair.fromAccountId(publicKey);
					accountBalances = StellarSDKUtility.getAccountBalance(userAccount);
				}
				ArrayList<AssetTransactions> assetTransactions = null;
				if(publicKey != null && publicKey != "") {
					userAccount = KeyPair.fromAccountId(publicKey);
					assetTransactions = StellarSDKUtility.getAccountTransactions(publicKey, "5");
				}
				request.setAttribute("assetTransactions",assetTransactions);
				request.setAttribute("externalwallets",accountBalances);
				request.setAttribute("publickey",publicKey);
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerShpwtPorteCoinsPage()).forward(request, response);
				}finally {
					if(relationshipNo != null)relationshipNo = null; if(user != null)user = null;
					if(publicKey != null)publicKey = null; if(userAccount != null)userAccount = null;
					if(accountBalances != null)accountBalances = null; if (langPref!=null) langPref=null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			break;
			case "Display Transactions":
				try {
					String langPref=null;
					if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
					request.setAttribute("langpref", langPref);
					request.setAttribute("lastaction", "porte");
					request.setAttribute("lastrule", "Display Transactions");
					response.setContentType("text/html");
					String relationshipNo= null;
					User user = null;
					 if (session.getAttribute("SESS_USER") == null) 
							throw new Exception ("Session has expired, please log in again");
					user = (User) session.getAttribute("SESS_USER");
					relationshipNo = user.getRelationshipNo();
					String publicKey = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
					NeoBankEnvironment.setComment(3, className, " Public key is  "+publicKey);
					KeyPair userAccount = null;
					String limit = NeoBankEnvironment.getStellarCustomerTransactionLimit();
					String selfLink=NeoBankEnvironment.getStellarTestEviromentUrl() + "/accounts/" + publicKey+ "/operations?"+ "limit="+ limit + "&order=desc&"+ "join=transactions";
					ArrayList<AssetTransactions> assetTransactions = null;
					if(publicKey != null && publicKey != "") {
						userAccount = KeyPair.fromAccountId(publicKey);
						assetTransactions = StellarSDKUtility.getAccountTransactions(publicKey, "200");
					}
					request.setAttribute("assetTransactions",assetTransactions);
					request.setAttribute("publickey",publicKey);
					try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerPorteDisplayTransactions()).forward(request, response);
					}finally {
						if (langPref!=null) langPref=null;
						if(relationshipNo != null) relationshipNo =null; if(user != null) user =null;
						if(publicKey != null) publicKey =null; if(userAccount != null) userAccount =null;
						if(assetTransactions != null) assetTransactions =null; if (selfLink!=null)selfLink=null;
					} 
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
					Utilities.callException(request, response, ctx, e.getMessage());
				}
				break;
			
			case"Buy Asset":
				try {
					String langPref=null;
					if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
					request.setAttribute("lastaction", "porte");
					request.setAttribute("lastrule", "Buy Asset");
					request.setAttribute("langpref", langPref);
					response.setContentType("text/html");
					User user = null;
					String relationshipNo= null;
					 if (session.getAttribute("SESS_USER") == null) 
							throw new Exception ("Session has expired, please log in again");
					user = (User) session.getAttribute("SESS_USER");
					relationshipNo = user.getRelationshipNo();
					String publicKey = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
					KeyPair userAccount = null;
					NeoBankEnvironment.setComment(3, className, " Public key is  "+publicKey);
					ArrayList<AssetAccount> accountBalances = null;
					if(publicKey != null && publicKey != "") {
						userAccount = KeyPair.fromAccountId(publicKey);
						accountBalances = StellarSDKUtility.getAccountBalance(userAccount);
					}
					request.setAttribute("externalwallets",accountBalances);
					request.setAttribute("publickey",publicKey);
				
					try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerPorteBuyCoinsPage()).forward(request, response);
					} finally {
						if(relationshipNo != null) relationshipNo =null; if(user != null) user =null;
						if(publicKey != null) publicKey =null; if(userAccount != null) userAccount =null;
						if(accountBalances != null) accountBalances =null;if (langPref!=null) langPref=null;
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
					Utilities.callException(request, response, ctx, e.getMessage());
				}
				break;
				case"Transfer Coin":
					try {
						String mnemoniccode =null;String relationshipNo=null;User user = null;
						String langPref=null;
						if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
						NeoBankEnvironment.setComment(3, className,"langPref is "+langPref);
						 if (session.getAttribute("SESS_USER") == null) 
								throw new Exception ("Session has expired, please log in again");
						user = (User) session.getAttribute("SESS_USER");
						relationshipNo = user.getRelationshipNo();

						mnemoniccode = (String)FundStellarAccountDao.class.getConstructor().newInstance().getMnemonicKey(relationshipNo);
						NeoBankEnvironment.setComment(3, className,"mnemonic code is "+mnemoniccode);
						ArrayList<AssetAccount> accountBalances = null;
						KeyPair userAccount = null;
						String publicKey = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
						if(publicKey != null && publicKey != "") {
							userAccount = KeyPair.fromAccountId(publicKey);
							accountBalances = StellarSDKUtility.getAccountBalance(userAccount);
						}
						request.setAttribute("lastaction", "porte");
						request.setAttribute("lastrule", "Transfer Coin");
						request.setAttribute("externalwallets",accountBalances);
						request.setAttribute("mnemonic_code",mnemoniccode);
						request.setAttribute("langpref", langPref);
						response.setContentType("text/html");
						try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerPorteTransferCoin()).forward(request, response);
						} finally {
							if(relationshipNo != null) relationshipNo =null; if(user != null) user =null;
							 if(mnemoniccode != null) mnemoniccode =null;if (langPref!=null) langPref=null;
						}
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
						Utilities.callException(request, response, ctx, e.getMessage());
					}
				break;
				case "Swap":
					try {
						String langPref=null;
						if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
						request.setAttribute("lastaction", "porte");
						request.setAttribute("lastrule", "Swap");
						request.setAttribute("langpref", langPref);
						response.setContentType("text/html");
						User user = null;
						String relationshipNo= null;
						 if (session.getAttribute("SESS_USER") == null) 
								throw new Exception ("Session has expired, please log in again");
						user = (User) session.getAttribute("SESS_USER");
						relationshipNo = user.getRelationshipNo();
						String publicKey = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
						KeyPair userAccount = null;
						NeoBankEnvironment.setComment(3, className, " Public key is  "+publicKey);
						ArrayList<AssetAccount> accountBalances = null;
						if(publicKey != null && publicKey != "") {
							userAccount = KeyPair.fromAccountId(publicKey);
							accountBalances = StellarSDKUtility.getAccountBalance(userAccount);
						}
						request.setAttribute("externalwallets",accountBalances);
						try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerSellPorteCoin()).forward(request, response);
						} finally {
							if(relationshipNo != null) relationshipNo =null; if(user != null) user =null;
							if(publicKey != null) publicKey =null; if(userAccount != null) userAccount =null;
							if(accountBalances != null) accountBalances =null;if (langPref!=null) langPref=null;
						}
						
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
						Utilities.callException(request, response, ctx, e.getMessage());
					}
					break;

				case "buy_coin_pending_transactions":
					try {
						String langPref=null;
						if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
						request.setAttribute("langpref", langPref);
						request.setAttribute("lastaction", "porte");
						request.setAttribute("lastrule", "Buy Asset");
						response.setContentType("text/html");
						String realationshipNo = null;
						List<Transaction> listTxn = null;
						realationshipNo = ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
						try {
							NeoBankEnvironment.setComment(3, className, "buy_coin_pending_transactions");
							listTxn = (List<Transaction>)CustomerCoinsDao.class.getConstructor().
							newInstance().getPendingTransactions(realationshipNo);
							request.setAttribute("transactions", listTxn );
									
							ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerBuyPendingTransactionPage()).forward(request, response);
						} finally {
							if(realationshipNo!=null)realationshipNo = null;
							if(listTxn!=null)listTxn = null; if (langPref!=null) langPref=null;
						}
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
						Utilities.callException(request, response, ctx, e.getMessage());
					}
					break;
					
				case "Sell Asset to Fiat":
					try {
						String langPref=null;
						if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
						request.setAttribute("langpref", langPref);
						request.setAttribute("lastaction", "porte");
						request.setAttribute("lastrule", "Sell Asset to Fiat");
						response.setContentType("text/html");
						User user = null;
						String relationshipNo= null;
						 if (session.getAttribute("SESS_USER") == null) 
								throw new Exception ("Session has expired, please log in again");
						user = (User) session.getAttribute("SESS_USER");
						relationshipNo = user.getRelationshipNo();
						String publicKey = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
						KeyPair userAccount = null;
						NeoBankEnvironment.setComment(3, className, " Public key is  "+publicKey);
						ArrayList<AssetAccount> accountBalances = null;
						if(publicKey != null && publicKey != "") {
							userAccount = KeyPair.fromAccountId(publicKey);
							accountBalances = StellarSDKUtility.getAccountBalance(userAccount);
						}
						request.setAttribute("externalwallets",accountBalances);
						try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getOffRampPorteCoinPage()).forward(request, response);
						} finally {
							if(relationshipNo != null) relationshipNo =null; if(user != null) user =null;
							if(publicKey != null) publicKey =null; if(userAccount != null) userAccount =null;
							if(accountBalances != null) accountBalances =null; if (langPref!=null) langPref=null;
						}
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
						Utilities.callException(request, response, ctx, e.getMessage());
					}
					break;
			
				default:
					throw new IllegalArgumentException("Rule not defined value: " + rules);

		}
		
	}

}
