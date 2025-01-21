package com.pporte.rules;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.CustomerCryptoCoinDao;
import com.pporte.dao.CustomerDao;
import com.pporte.dao.CustomerExternalCoinDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.model.AssetCoin;
import com.pporte.model.AssetTransaction;
import com.pporte.model.User;
import com.pporte.model.Wallet;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;

public class CustomerCryptoCoinRulesImpl implements Rules{
private static String className = CustomerCryptoCoinRulesImpl.class.getSimpleName();

	@Override
	public void performOperation(String ruleaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception{
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null) 
			Utilities.callException(request, response, ctx, "Session has expired, please log in again");
		switch (ruleaction) {
		case "Register Coin":
			try {

				request.setAttribute("lastaction", "cryp");
				request.setAttribute("lastrule", "Register Coin");
				response.setContentType("text/html");
					request.setAttribute("assetcoins", (List<AssetCoin>) CustomerCryptoCoinDao.class.getConstructor()
						.newInstance().getExternalAssetCoins());
			   try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerCryptoRegCoinPage()).forward(request,
							response);
				} finally {
				}

			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + ruleaction + " is " + e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			break;
		case "View Coins":
			try {
				String relationshipNo = null;
				request.setAttribute("lastaction", "cryp");
				request.setAttribute("lastrule", "View Coins");
				response.setContentType("text/html");
				relationshipNo = ((User)session.getAttribute("SESS_USER")).getRelationshipNo();

				request.setAttribute("wallettxns", (List<AssetTransaction>) CustomerExternalCoinDao.class.getConstructor()
						.newInstance().getExternalCoinLastFiveTxn(relationshipNo));
					request.setAttribute("assetcoinswallet", (List<Wallet>) CustomerCryptoCoinDao.class.getConstructor()
							.newInstance().getAssetCoinsWallets(relationshipNo));
					
					ConcurrentHashMap<String, String>hashTxnType = (ConcurrentHashMap<String, String>)CustomerExternalCoinDao.class.getConstructor().newInstance().getTransactionTypes();
					request.setAttribute("hashtxntype", hashTxnType);
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerCryptoViewCoinPage()).forward(request, response);
				} finally {
					if (relationshipNo != null)
						relationshipNo = null;
				}

			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + ruleaction + " is " + e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			break;
		case "Buy Coins":
			try {

				request.setAttribute("lastaction", "cryp");
				request.setAttribute("lastrule", "Buy Coins");
				response.setContentType("text/html");
				String userType = "C"; 	String relationshipNo = null;
				relationshipNo = ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
				request.setAttribute("assetcoinswallet", (List<Wallet>) CustomerCryptoCoinDao.class.getConstructor()
						.newInstance().getAssetCoinsWallets(relationshipNo));
				
				try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerCryptoBuyCoinPage()).forward(request, response);
					
				} finally {
					if (userType != null)
						userType = null;
				}

			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + ruleaction + " is " + e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			break;
		case "Sell Coins":
			try {

				request.setAttribute("lastaction", "cryp");
				request.setAttribute("lastrule", "Sell Coins");
				response.setContentType("text/html");
				String userType = "C"; String relationshipNo = null;
				relationshipNo = ((User)session.getAttribute("SESS_USER")).getRelationshipNo();

				try {
					request.setAttribute("assetcoinswallet", (List<Wallet>) CustomerCryptoCoinDao.class.getConstructor()
							.newInstance().getAssetCoinsWallets(relationshipNo));
					ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerCryptoSellCoinPage()).forward(request,
							response);
				} finally {
					if (userType != null)
						userType = null;
				}

			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + ruleaction + " is " + e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			break;
			
		case "Pay Anyone":
			try {

				request.setAttribute("lastaction", "cryp");
				request.setAttribute("lastrule", "Pay Anyone");
				response.setContentType("text/html");
				String relationshipNo = null;
					relationshipNo = ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
					request.setAttribute("assetcoinswallet", (List<Wallet>) CustomerCryptoCoinDao.class.getConstructor()
							.newInstance().getAssetCoinsWallets(relationshipNo));
					
					request.setAttribute("assetcoins", (List<AssetCoin>) CustomerCryptoCoinDao.class.getConstructor()
							.newInstance().getExternalAssetCoins());
					
					request.setAttribute("wallettxns", (List<AssetTransaction>) CustomerExternalCoinDao.class.getConstructor()
							.newInstance().getExternalCoinLastFiveTxn(relationshipNo));
					
					try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerCryptoPayAnyonePage()).forward(request, response);				
						
					} finally {
						if (relationshipNo != null)
							relationshipNo = null;
					}

			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + ruleaction + " is " + e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			break;
		case "View Transactions":
			try {

				request.setAttribute("lastaction", "cryp");
				request.setAttribute("lastrule", "View Transactions");
				response.setContentType("text/html");
				String userType = "C";
				String relationshipNo = null; 

					relationshipNo = ((User)session.getAttribute("SESS_USER")).getRelationshipNo();

					request.setAttribute("wallettxns", (List<AssetTransaction>) CustomerExternalCoinDao.class.getConstructor()
							.newInstance().getExternalCoinLastFiveTxn(relationshipNo));
					 ConcurrentHashMap<String, String>hashTxnType = (ConcurrentHashMap<String, String>)CustomerExternalCoinDao.class.getConstructor().newInstance().getTransactionTypes();
					request.setAttribute("hashtxntype", hashTxnType);
			   try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerCryptoViewTransPage()).forward(request, response);
				     
				} finally {
					if (userType != null) userType = null; if(hashTxnType !=null) hashTxnType =null;
				}

			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + ruleaction + " is " + e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			break;
		default:
			throw new IllegalArgumentException("Rule not defined value: " + ruleaction);
		}


		
	}

	@Override
	public void performJSONOperation(String arg0, HttpServletRequest arg1, HttpServletResponse arg2,
			ServletContext arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unused")
	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null) Utilities.callException(request, response, ctx, "Session has expired, please log in again");
		
		switch (rulesaction) {
	
		case "custregistercoin":
			
			try {
				String assetCode=null; String userType=null; String assetDesc=null; boolean success = false;
				PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject(); 
				String relationshipNo = null; String errorMsg = "true";
				if(request.getParameter("hdnassetcoincode")!=null)	assetCode = StringUtils.trim(request.getParameter("hdnassetcoincode"));
				if(request.getParameter("asssetdesc")!=null)	assetDesc = StringUtils.trim(request.getParameter("asssetdesc"));
				userType="C";
				User user = (User)session.getAttribute("SESS_USER");
				relationshipNo = user.getRelationshipNo();
				
				NeoBankEnvironment.setComment(3, className," inside custregistercoin assetCode is " + assetCode + " assetDesc "+ assetDesc);
				
				/**Call Stellar Network to register new Wallet**/ //.TODO
				String externalWalletId = Utilities.generateExternalWalletId(34);

				
				//Create the wallet container and link with crypto
				success = CustomerCryptoCoinDao.class.getConstructor().newInstance().registerCoinForUser(assetCode, assetDesc, relationshipNo,externalWalletId );				
				
				if (success) {
					errorMsg = "false";
					obj.add("message", gson.toJsonTree("Crpto Coin registered succcessful"));
				}else {
					errorMsg = "Failed in Crypto Coin registration";
				}
				
				try {
					NeoBankEnvironment.setComment(3, className," custregistercoin String is " + gson.toJson(obj));
					
					obj.add("error", gson.toJsonTree(errorMsg));
					out_json.print(gson.toJson(obj));	
					} finally {
					if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
					if (userType != null)userType = null; if(obj!=null) obj = null; if(assetCode !=null) assetCode = null;
					 if(assetDesc !=null) assetDesc = null;  if(errorMsg !=null) errorMsg = null;
				}
			}catch(Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Register Coin failed, Please try again letter");
			}
		break;
		case "transfer_external_coin":
			
			try {
				String senderWallet=null; String userType=null; String receiverWallet=null; boolean success = false;
				PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject(); 
				String relationshipNo = null; String errorMsg = "true"; String sendAmount = null; String assetCode = null;
				String senderwalletId = null; String senderwalletBalance = null; String txnPayMode = null;
				String receiverwalletId = null; String receiverassetCode = null; String txnUserCode = null;
				userType="C"; String receiverWalletDetails = null; String senderWalletDetails = null; String userId = null;
				String referenceNo = null; String extSystemRef = null; String payComments = ""; String receiverEmail = null;
				PrintWriter output = null; String receiverAssetCode = null; boolean checkIfEmailExist = false;
				
				if(request.getParameter("hdnfrowalletid")!=null)	senderWallet = StringUtils.trim(request.getParameter("hdnfrowalletid"));
				if(request.getParameter("hdnsendercointype")!=null)	receiverAssetCode = StringUtils.trim(request.getParameter("hdnsendercointype"));
				if(request.getParameter("hdnreceivercointype")!=null)	assetCode = StringUtils.trim(request.getParameter("hdnreceivercointype"));
				//if(request.getParameter("receiverwallet")!=null)	receiverWallet = StringUtils.trim(request.getParameter("receiverwallet"));
				if(request.getParameter("receiverwallet")!=null)	receiverEmail = StringUtils.trim(request.getParameter("receiverwallet"));
				if(request.getParameter("sendamount")!=null)	sendAmount = StringUtils.trim(request.getParameter("sendamount"));
				User user = (User)session.getAttribute("SESS_USER");
				relationshipNo = user.getRelationshipNo();
				userId = user.getCustomerId();
						
				NeoBankEnvironment.setComment(3, className," inside transfer_external_coin senderWallet is " + senderWallet + " receiverWallet "+ receiverWallet
						+ " sendAmount "+ sendAmount);
				
				if (session.getAttribute("SESS_USER") == null)  throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				relationshipNo = user.getRelationshipNo();
				senderWalletDetails = (String)CustomerCryptoCoinDao.class.getConstructor() .newInstance().getSenderWalletDetails(senderWallet, relationshipNo, assetCode);
						
				checkIfEmailExist= (Boolean)CustomerDao.class.getConstructor().newInstance().checkIfEmailExist(receiverEmail);
				receiverWallet = (String)CustomerExternalCoinDao.class.getConstructor().newInstance().getReceiverExternalWalletId(receiverEmail, assetCode);
				
				receiverWalletDetails = (String)CustomerCryptoCoinDao.class.getConstructor() .newInstance().getReceiverWalletDetails(receiverWallet);

				if(!checkIfEmailExist) {
					Utilities.sendJsonResponse(response, "error", "Receiver Does not exist");
					return;
				}
				if(receiverWallet ==null) {
					Utilities.sendJsonResponse(response, "error", "Receiver Wallet Does not exist");
					return;
				}
				
				if(!receiverAssetCode.equals(assetCode)) {
					Utilities.sendJsonResponse(response, "error", "Transfer to different wallet asset not supported at the moment ");
					return;
				}
				
				if(receiverWalletDetails==null) {
					Utilities.sendJsonResponse(response, "error", "Receiver wallet does not exist");
					return;
				}else {
					receiverwalletId = receiverWalletDetails.split(",")[0];
					receiverassetCode = receiverWalletDetails.split(",")[1];
				}
				if(senderWalletDetails==null) {
					Utilities.sendJsonResponse(response, "error", "Dear Customer your dont have corresponding wallet, "
							+ "please create wallet and try again");
					return;
				}else {
					senderwalletId = senderWalletDetails.split(",")[0];
					senderwalletBalance = senderWalletDetails.split(",")[1];
				}
				
				if(receiverwalletId.equals(senderwalletId)) {
					Utilities.sendJsonResponse(response, "error", "You cannot send Asset from your wallet to the same wallet account");
					return;
				}
								
				if(!assetCode.equals(receiverassetCode)){
					
					Utilities.sendJsonResponse(response, "error", "Transfer to different wallet asset not supported at the momment ");
					return;
					
				}
							
			if(assetCode.equals(NeoBankEnvironment.getBitcoinCode())) {
				txnPayMode= NeoBankEnvironment.getCodeBitCoinP2P();
			}else if(assetCode.equals(NeoBankEnvironment.getEthereumCode())) {
				txnPayMode= NeoBankEnvironment.getCodeEthereumCoinP2P();
			}else if(assetCode.equals(NeoBankEnvironment.geLitecoinCode())) {
				txnPayMode= NeoBankEnvironment.getCodeLitecoinCoinP2P();
			}else {
				Utilities.sendJsonResponse(response, "error", "Try again later");
				NeoBankEnvironment.setComment(3, className," Unknown txnPayMode " +txnPayMode );
				return;
			}
			
			txnUserCode = Utilities.generateTransactionCode(10);
			referenceNo = txnPayMode+ "-" + (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())+ Utilities.genAlphaNumRandom(9);
			String customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
					NeoBankEnvironment.getDefaultCustomerUserType(),txnPayMode, sendAmount);
			String minimumTxnAmount=customerCharges.substring(customerCharges.indexOf("|")+1, customerCharges.length());
			
			if ( Double.parseDouble(sendAmount)< Double.parseDouble(minimumTxnAmount)) { 
				Utilities.sendJsonResponse(response, "error", "Transaction amount can not be less than "+minimumTxnAmount);
				return;
			}
			//Check balance // this will be captured in the authorization module once we plugin
			double senderDebitAmount =  Double.parseDouble(sendAmount) + 
					Double.parseDouble(customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|")));
			if ( senderDebitAmount> Double.parseDouble(senderwalletBalance)) {
				Utilities.sendJsonResponse(response, "error", "Insufficient coin balance");
				return;
			}
			
			//Connect to External API here
			extSystemRef=Utilities.genAlphaNumRandom(24).toUpperCase(); //Remove this after integration
			
			success = (Boolean) CustomerCryptoCoinDao.class.getConstructor().newInstance().externalCoinP2P(relationshipNo, senderWallet, sendAmount, payComments, 
					receiverEmail, referenceNo, txnUserCode, customerCharges, txnPayMode, assetCode, extSystemRef, receiverWallet);
			if (success) {
				String moduleCode = txnPayMode;
				SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, moduleCode,StringUtils.substring("External "+assetCode+" Coin P2P" + referenceNo , 0, 48));
				obj.add("error", gson.toJsonTree("false"));
				obj.add("message", gson.toJsonTree("You have transfered " + sendAmount +" "+ assetCode+ 
						"  to wallet ID " + receiverWallet +". Reference Number " +txnUserCode)); 
			}else {
				obj.add("error", gson.toJsonTree("true"));
				obj.add("message", gson.toJsonTree("Transaction failed"));
			}
			try {				
				NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));				
				out_json.print(gson.toJson(obj));
			} finally {
				if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
				if (obj != null)obj = null; if (receiverEmail != null)receiverEmail = null;
				if (sendAmount != null)sendAmount = null; 
				if (extSystemRef != null)extSystemRef = null; 
				if (user != null)user = null; 
				if (senderwalletBalance != null)senderwalletBalance = null; 
				if (senderWalletDetails != null)senderWalletDetails = null; 
				if (senderwalletId != null) senderwalletId = null; 
				if (receiverwalletId != null) receiverwalletId = null; 
				if (receiverassetCode != null) receiverassetCode = null; 
				if (payComments != null)payComments = null; 
				if (referenceNo != null)referenceNo = null;  
				if (assetCode != null)assetCode = null; if (txnPayMode != null)txnPayMode = null;
			}	
		
			}catch(Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Transfer coin failed, Please try again letter");
			}
		break;
		
	case "buy_external_coin":
			
			try {
				String userType=null; boolean success = false;
				response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject(); 
				String relationshipNo = null; String amountToSpend = null; String assetCode = null;
				String assetWalletId = null;  String paymentMode = null; String tokenId = null;
				String customerCharges = null; String minimumTxnAmount = null; String assetAmount = null;
				double newWalletBalanceFromStellar = 0; String txnPayMode = null;
				String txnUserCode = null; userType="C";  String userId = null;PrintWriter output = null;
				String referenceNo = null; String extSystemRef = null; String payComments = ""; 
				String walletDetails = null; String fiatWalletId = null; String walletBalance = null;
								
				if(request.getParameter("hdnbuyassetcode")!=null)	assetCode = StringUtils.trim(request.getParameter("hdnbuyassetcode"));
				if(request.getParameter("hdnbuyforwalletid")!=null)	assetWalletId = StringUtils.trim(request.getParameter("hdnbuyforwalletid"));
				if(request.getParameter("hdnpaymentmode")!=null)	paymentMode = StringUtils.trim(request.getParameter("hdnpaymentmode"));
			//	if(request.getParameter("hdnpaymentmode")!=null)	fiatWalletid = StringUtils.trim(request.getParameter("hdnpaymentmode"));
				if(request.getParameter("hdntokenid")!=null)	tokenId = StringUtils.trim(request.getParameter("hdntokenid"));
				if(request.getParameter("sendamount")!=null)	amountToSpend = StringUtils.trim(request.getParameter("sendamount"));
				if(request.getParameter("receivedamount")!=null)	assetAmount = StringUtils.trim(request.getParameter("receivedamount"));
				User user = (User)session.getAttribute("SESS_USER");
				relationshipNo = user.getRelationshipNo();
				userId = user.getCustomerId();
						
				NeoBankEnvironment.setComment(3, className," inside buy_external_coin assetCode is " + assetCode + " assetWalletId "+ assetWalletId
						+ " amountToSpend "+ amountToSpend +" paymentMode "+ paymentMode + " tokenId "+ tokenId + " userId "+ userId + " relationshipNo "+ relationshipNo );
				
				txnUserCode = Utilities.generateTransactionCode(10);
				
				if(assetCode.equals(NeoBankEnvironment.getBitcoinCode())) {
					txnPayMode= NeoBankEnvironment.getCodeBuyBitCoin();
				}else if(assetCode.equals(NeoBankEnvironment.getEthereumCode())) {
					txnPayMode= NeoBankEnvironment.getCodeBuyEthereumCoin();
				}else if(assetCode.equals(NeoBankEnvironment.geLitecoinCode())) {
					txnPayMode= NeoBankEnvironment.getCodeBuyLitecoinCoin();
				}else {
					Utilities.sendJsonResponse(response, "error", "Try again later");
					NeoBankEnvironment.setComment(3, className," Unknown txnPayMode " +txnPayMode );
					return;
				}
							
				if(paymentMode.equalsIgnoreCase("T")) { // Token
					//Debit card here
					//Call Gate way here
					//Get charges
					
					referenceNo = txnPayMode+ "-" + (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
							+ Utilities.genAlphaNumRandom(9);
					customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
							NeoBankEnvironment.getDefaultCustomerUserType(),txnPayMode, amountToSpend);
					minimumTxnAmount=customerCharges.substring(customerCharges.indexOf("|")+1, customerCharges.length());
					
					if ( Double.parseDouble(amountToSpend)< Double.parseDouble(minimumTxnAmount)) { 
						Utilities.sendJsonResponse(response, "error", "Transaction amount can not be less than "+minimumTxnAmount);
						throw new Exception("minimumAmount amount inputed:- Minimum exptected is:- "+minimumTxnAmount+"Amount inputed is"+amountToSpend);
					}
					extSystemRef=Utilities.genAlphaNumRandom(24).toUpperCase(); //Remove this after integration
					success = (Boolean) CustomerCryptoCoinDao.class.getConstructor().newInstance().buyExternalCoinViaToken(relationshipNo, tokenId,  amountToSpend, payComments, 
							 referenceNo, txnUserCode, customerCharges, txnPayMode, assetCode, extSystemRef, assetWalletId, assetAmount,newWalletBalanceFromStellar);
			
				}else if(paymentMode.equalsIgnoreCase("W")) {// Fiat
					walletDetails = (String)CustomerCryptoCoinDao.class.getConstructor() .newInstance().getFiatWalletBalance(relationshipNo);
					fiatWalletId = walletDetails.split(",")[0];
					walletBalance = walletDetails.split(",")[1];
				
					referenceNo = txnPayMode+ "-" + (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
							+ Utilities.genAlphaNumRandom(9);
					customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
							NeoBankEnvironment.getDefaultCustomerUserType(),txnPayMode, amountToSpend);
					minimumTxnAmount=customerCharges.substring(customerCharges.indexOf("|")+1, customerCharges.length());
					
					if ( Double.parseDouble(amountToSpend)< Double.parseDouble(minimumTxnAmount)) { 
						Utilities.sendJsonResponse(response, "error", "Transaction amount can not be less than "+minimumTxnAmount);
						return;
					}
					//Check balance // this will be captured in the authorization module once we plugin
					double senderDebitAmount =  Double.parseDouble(amountToSpend) + 
							Double.parseDouble(customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|")));
					if ( senderDebitAmount> Double.parseDouble(walletBalance)) {
						Utilities.sendJsonResponse(response, "error", "Dear customer you have insufficient funds, Kindly top up and try again");
						return;
					}
					//Connect to External API here
					extSystemRef=Utilities.genAlphaNumRandom(24).toUpperCase(); //Remove this after integration
					success = (Boolean) CustomerCryptoCoinDao.class.getConstructor().newInstance().buyExternalCoinViaWallet(relationshipNo, fiatWalletId,  amountToSpend, payComments, 
							 referenceNo, txnUserCode, txnPayMode, assetCode, extSystemRef, assetWalletId, newWalletBalanceFromStellar, customerCharges,assetAmount );
				}
				
				if (success) {
					String moduleCode = txnPayMode;
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, moduleCode,StringUtils.substring("Buy "+assetCode+" Coin " + referenceNo , 0, 48));
					
					obj.add("error", gson.toJsonTree("false"));
					obj.add("message", gson.toJsonTree("You have successfully bought "+assetCode +":"+ assetAmount + 
							" Reference Number " +txnUserCode)); 
				}else {
					obj.add("error", gson.toJsonTree("true")); 
					obj.add("message", gson.toJsonTree("Transaction failed")); 
				}
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));
				} finally {
					if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
					if (obj != null)obj = null; if (minimumTxnAmount != null)minimumTxnAmount = null;
					if (txnUserCode != null)txnUserCode = null; if (paymentMode != null)paymentMode = null;
					if (assetAmount != null)assetAmount = null; if (tokenId != null)tokenId = null;
					if (assetCode != null)assetCode = null; if (referenceNo != null)referenceNo = null;
					if (txnPayMode != null)txnPayMode = null; if (extSystemRef != null)extSystemRef = null;
					if (amountToSpend != null)amountToSpend = null;  if (user != null)user = null;   
					if (assetWalletId != null)assetWalletId = null;  if (payComments != null)payComments = null; 
			}	
		
		}catch(Exception e) {
			NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
			Utilities.sendJsonResponse(response, "error", "Transfer coin failed, Please try again letter");
		}
		break;
		
	case "sell_external_coin":
		
		try {
			String userType=null; boolean success = false;
			response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject(); 
			String relationshipNo = null; String assetCode = null;
			String assetWalletId = null;  String paymentMode = null; String tokenId = null;
			String customerCharges = null; String minimumTxnAmount = null; String assetAmount = null;
			double newWalletBalanceFromStellar = 0; String txnPayMode = null; String amountReceived = null;
			String txnUserCode = null; userType="C";  String userId = null;PrintWriter output = null;
			String referenceNo = null; String extSystemRef = null; String payComments = ""; 
			String assetWalletDetails = null;  String fiatWalletDetails = null;String fiatWalletId = null; String assetWalletBalance = null;
							
			if(request.getParameter("hdnsellassetcode")!=null)	assetCode = StringUtils.trim(request.getParameter("hdnsellassetcode"));
			if(request.getParameter("sellamount")!=null)	assetAmount = StringUtils.trim(request.getParameter("sellamount"));
			if(request.getParameter("sellequivamount")!=null)	amountReceived = StringUtils.trim(request.getParameter("sellequivamount"));
			if(request.getParameter("hdnsellforwalletid")!=null)	assetWalletId = StringUtils.trim(request.getParameter("hdnsellforwalletid"));
			
			User user = (User)session.getAttribute("SESS_USER");
			relationshipNo = user.getRelationshipNo();
			userId = user.getCustomerId();
					
			NeoBankEnvironment.setComment(3, className," inside sell_external_coin assetCode is " + assetCode + " assetWalletId "+ assetWalletId
					+ " amountReceived "+ amountReceived +" paymentMode "+ paymentMode + " tokenId "+ tokenId + " userId "+ userId + " relationshipNo "+ relationshipNo );
			
			txnUserCode = Utilities.generateTransactionCode(10);
			
			if(assetCode.equals(NeoBankEnvironment.getBitcoinCode())) {
				txnPayMode= NeoBankEnvironment.getCodeSellBitCoin();
			}else if(assetCode.equals(NeoBankEnvironment.getEthereumCode())) {
				txnPayMode= NeoBankEnvironment.getCodeSellEthereumCoin();
			}else if(assetCode.equals(NeoBankEnvironment.geLitecoinCode())) {
				txnPayMode= NeoBankEnvironment.getCodeSellLitecoinCoin();
			}else {
				Utilities.sendJsonResponse(response, "error", "Try again later");
				NeoBankEnvironment.setComment(3, className," Unknown txnPayMode " +txnPayMode );
				return;
			}
			
			//To remove this once the API connectivity is ready
			assetWalletDetails = (String)CustomerExternalCoinDao.class.getConstructor() .newInstance().getExternalWalletBalance(relationshipNo,assetCode);
			//WalletId = walletDetails.split(",")[0];
			assetWalletBalance = assetWalletDetails.split(",")[1];
			
			fiatWalletDetails = (String)CustomerCryptoCoinDao.class.getConstructor() .newInstance().getFiatWalletBalance(relationshipNo);
			fiatWalletId = fiatWalletDetails.split(",")[0];
			
			referenceNo = txnPayMode+ "-" + (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
					+ Utilities.genAlphaNumRandom(9);
			customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
					NeoBankEnvironment.getDefaultCustomerUserType(),txnPayMode, amountReceived);
			minimumTxnAmount=customerCharges.substring(customerCharges.indexOf("|")+1, customerCharges.length());
			
			if ( Double.parseDouble(amountReceived)< Double.parseDouble(minimumTxnAmount)) { 
				Utilities.sendJsonResponse(response, "error", "Transaction amount can not be less than "+minimumTxnAmount);
				return;
			}
			//Check balance // this will be captured in the authorization module once we plugin
			double senderDebitAmount =  Double.parseDouble(assetAmount) + 
					Double.parseDouble(customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|")));
			if ( senderDebitAmount> Double.parseDouble(assetWalletBalance)) {
				Utilities.sendJsonResponse(response, "error", "Dear customer you have insufficient "+assetCode +" Coin in your wallet");
				return;
			}
			
			//Connect to External API here
			extSystemRef=Utilities.genAlphaNumRandom(24).toUpperCase(); //Remove this after integration
			success = (Boolean) CustomerExternalCoinDao.class.getConstructor().newInstance().sellExternalCoin(relationshipNo, fiatWalletId,  amountReceived, payComments, 
					 referenceNo, txnUserCode, txnPayMode, assetCode, extSystemRef, assetWalletId, newWalletBalanceFromStellar, customerCharges,assetAmount );
		
			if (success) {
				String moduleCode = txnPayMode;
				SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, moduleCode,StringUtils.substring("Sell "+assetCode+" Coin " + referenceNo , 0, 48));
				
				obj.add("error", gson.toJsonTree("false"));
				obj.add("message", gson.toJsonTree("You have successfully bought "+assetCode +":"+assetAmount + 
						" Reference Number " +txnUserCode)); 
			}else {
				obj.add("error", gson.toJsonTree("true")); 
				obj.add("message", gson.toJsonTree("Transaction failed")); 
			}
			try {
				NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
				output = response.getWriter();
				output.print(gson.toJson(obj));
			} finally {
				if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
				if (obj != null)obj = null; if (minimumTxnAmount != null)minimumTxnAmount = null;
				if (txnUserCode != null)txnUserCode = null; if (paymentMode != null)paymentMode = null;
				if (assetAmount != null)assetAmount = null; if (tokenId != null)tokenId = null;
				if (assetCode != null)assetCode = null; if (referenceNo != null)referenceNo = null;
				if (txnPayMode != null)txnPayMode = null; if (extSystemRef != null)extSystemRef = null;
				if (amountReceived != null)amountReceived = null;  if (user != null)user = null;   
				if (assetWalletId != null)assetWalletId = null;  if (payComments != null)payComments = null; 
		}	
	
	}catch(Exception e) {
		NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
		Utilities.sendJsonResponse(response, "error", "Transfer coin failed, Please try again letter");
	}
	break;
	
	case "get_ext_txn_btn_dates":
		try {
			JsonObject obj = new JsonObject(); User user = null; String relationshipNo= null;
			PrintWriter output = null; 	List<AssetTransaction> arrTransactions = null;
			ConcurrentHashMap<String, String> hashTxnRules = null; Gson gson = new Gson();
			String txnDesc= null; String dateTo= null; String dateFrom =null; 	String oldFormat = "MM/dd/yyyy";
			String newFormat = "yyyy-MM-dd"; SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
			
			if(request.getParameter("datefrom")!=null) 	dateFrom = request.getParameter("datefrom").trim();
			if(request.getParameter("dateto")!=null)	 dateTo = request.getParameter("dateto").trim();
			
			NeoBankEnvironment.setComment(3, className, "get_ext_txn_btn_dates:dateFrom  "+dateFrom +" dateTo "+dateTo);

			 if (session.getAttribute("SESS_USER") == null) 
				 throw new Exception ("Session has expired, please log in again");
			user = (User) session.getAttribute("SESS_USER");
			relationshipNo = user.getRelationshipNo();
			hashTxnRules = (ConcurrentHashMap<String, String>)CustomerExternalCoinDao.class.getConstructor().newInstance().getTransactionTypes();
			
			Date d = sdf.parse(dateFrom); Date d2 = sdf.parse(dateTo);
			sdf.applyPattern(newFormat);  sdf.applyPattern(newFormat);
			dateFrom = sdf.format(d);     dateTo = sdf.format(d2);
			
			NeoBankEnvironment.setComment(3, className, "dateFrom "+ dateFrom);
			NeoBankEnvironment.setComment(3, className, "dateTo "+ dateTo);
			
			arrTransactions = (List<AssetTransaction>)CustomerExternalCoinDao.class.getConstructor()
					.newInstance().getExternalCoinTxnBtnDates(dateFrom, dateTo, relationshipNo);
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
						
		default:
			throw new IllegalArgumentException("Rule not defined value: " + rulesaction);
		}
		
		
		
	}



}
