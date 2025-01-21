package com.pporte.rules;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.StringUtils;
import org.stellar.sdk.KeyPair;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.CustomerCryptoCoinDao;
import com.pporte.dao.CustomerDao;
import com.pporte.dao.CustomerDigitalAssetsDao;
import com.pporte.dao.CustomerExternalCoinDao;
import com.pporte.dao.CustomerFiatWalletDao;
import com.pporte.dao.CustomerWalletDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.dao.WalletAuthorizationDao;
import com.pporte.model.AssetCoin;
import com.pporte.model.AssetTransaction;
import com.pporte.model.CryptoAssetCoins;
import com.pporte.model.Customer;
import com.pporte.model.User;
import com.pporte.model.Wallet;
import com.pporte.utilities.Bip39Utility;
import com.pporte.utilities.StellarSDKUtility;
import com.pporte.utilities.Utilities;
import framework.v8.Rules;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomerMobileWalletRulesImpl implements Rules {
public static String className = CustomerMobileWalletRulesImpl.class.getSimpleName();
	@Override
	public void performJSONOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performMultiPartOperation (String rulesaction, HttpServletRequest request, HttpServletResponse response,
	ServletContext ctx) throws Exception {
		switch (rulesaction) {
		case "get_wallet_coin_details_mbl":
			try {
				JsonObject object = new JsonObject();
				Gson gson = new Gson();
				User user = null;
				String relationshipNo= null;
				String tokenValue = null;
				if(request.getParameter("relno")!=null)	relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					return;
				}
				
				ConcurrentHashMap<String, String> hashWallets = (ConcurrentHashMap<String, String>)CustomerWalletDao.class.getConstructor()
						.newInstance().getCustomerWallets(relationshipNo);
				List<CryptoAssetCoins> arrCryptoAssetCoin = (List<CryptoAssetCoins>)CustomerWalletDao.class.getConstructor()
						.newInstance().getCryptoAssetPorteCoins();
				
				hashWallets.entrySet().forEach(entry -> {
					NeoBankEnvironment.setComment(3, className," Hash wallets is "+entry.getKey() + " " + entry.getValue());
				});
				
				for (int i = 0; i < arrCryptoAssetCoin.size(); i++) {
					NeoBankEnvironment.setComment(3, className," arrCryptoAssetCoins "+ arrCryptoAssetCoin.get(i).getAssetCode());
					}
			        
				if(hashWallets.size() > 1) {
					for (int i = 0; i < arrCryptoAssetCoin.size(); i++) {
						if(hashWallets.contains(arrCryptoAssetCoin.get(i).getAssetCode())) {
							arrCryptoAssetCoin.remove(i);
							i--;
						}
					}
				}
				object.add("data", gson.toJsonTree(arrCryptoAssetCoin));
				object.add("error", gson.toJsonTree("false"));
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(object));
					response.getWriter().print(object);
				}finally {
					response.getWriter().close();
					if(object!=null) object = null; if (tokenValue != null)tokenValue = null;
					if(arrCryptoAssetCoin!=null) arrCryptoAssetCoin = null;
					if(gson!=null) gson = null;
					if(hashWallets!=null) hashWallets = null;
					if(user!=null) user = null;
					if(relationshipNo!=null) relationshipNo = null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
			}
			break;
			
		case "create_porte_coin_wallets_mbl":
			//NeoBankEnvironment.setComment(3, className, "Inside create_porte_coin_wallets_mbl");
			try {
				JsonObject obj = new JsonObject();String tokenValue = null;
				User user = null; int baseFee  = org.stellar.sdk.Transaction.MIN_BASE_FEE;
				String relationshipNo= null; String limit = null;
				String walletDesc=null; String authMethod = null;
				Boolean success = false; 
				PrintWriter output = null; String assetCode =null;
				String privateKey =null;
				String publicKey = null;
				String issuerAccountId = null;
				boolean createTrustline = false;
				String hasMnemonic = null; String password  = null; String mnemoniStringFromDB  = null;
				boolean passIsCorrect = false; KeyPair keyPair = null;
				
				if(request.getParameter("input_wallet_desc")!=null)	
					walletDesc = request.getParameter("input_wallet_desc").trim();
				if(request.getParameter("asset_value")!=null)	
					assetCode = request.getParameter("asset_value").trim();
//				if(request.getParameter("input_private_key")!=null)	
//					privateKey = request.getParameter("input_private_key").trim();
				if(request.getParameter("relno")!=null)	
					relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					return;
				}
				
				if(request.getParameter("hasMnemonic")!=null)	 hasMnemonic = request.getParameter("hasMnemonic").trim();
				NeoBankEnvironment.setComment(3, className, "hasMnemonic is "+hasMnemonic);
				if(hasMnemonic.equals("true")) {
					if(request.getParameter("auth_method")!=null)	 authMethod = request.getParameter("auth_method").trim();
					if(authMethod.equals("P")) {
						if(request.getParameter("security")!=null)	 password = request.getParameter("security").trim();
						passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
								.checkIfPasswordIsCorrect(relationshipNo, password);
						if(!passIsCorrect) {
							NeoBankEnvironment.setComment(1, className, "Password is not correct");
							Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
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
				
				NeoBankEnvironment.setComment(3, className, " assetCode "+assetCode );
				publicKey = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
				NeoBankEnvironment.setComment(3, className, "publicKey is "+publicKey);
				
				if(publicKey == null)
					throw new Exception("In creating wallet");
				
				if(assetCode.equals(NeoBankEnvironment.getPorteTokenCode())) 
					NeoBankEnvironment.setComment(3, className, "PORTE Asset code is "+NeoBankEnvironment.getPorteTokenCode());
					issuerAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor()
							.newInstance().getIssueingAccountPublicKey(assetCode);
				if(assetCode.equals(NeoBankEnvironment.getUSDCCode())) 
					NeoBankEnvironment.setComment(3, className, "USDC Asset code is "+NeoBankEnvironment.getUSDCCode());
					issuerAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor()
							.newInstance().getIssueingAccountPublicKey(assetCode);
				if(assetCode.equals(NeoBankEnvironment.getVesselCoinCode())) 
					NeoBankEnvironment.setComment(3, className, "VESL Asset code is "+NeoBankEnvironment.getVesselCoinCode());
					issuerAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor()
							.newInstance().getIssueingAccountPublicKey(assetCode);
				if(assetCode.equals(NeoBankEnvironment.getBitcoinCode())) 
					NeoBankEnvironment.setComment(3, className, "BTC Asset code is "+NeoBankEnvironment.getBitcoinCode());
					issuerAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor()
							.newInstance().getIssueingAccountPublicKey(assetCode);
				
				if(CustomerDao.class.getConstructor().newInstance().checkIfHasWallet(assetCode, relationshipNo)) {
					Utilities.sendJsonResponse(response, "error", "Wallet for selected asset already exist");
					return;
				}
				limit = NeoBankEnvironment.getMaxStellarAssetWalletLimit();
				NeoBankEnvironment.setComment(3,className,"Going to create a trustline");
				createTrustline = StellarSDKUtility.createTrustline(issuerAccountId, privateKey,
						 baseFee, limit,  assetCode );
				NeoBankEnvironment.setComment(3, className, "After creating a trustline");
				if(createTrustline) {
					success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
							relationshipNo, walletDesc,publicKey, assetCode);
				}else {
					throw new Exception("Error in creating Trustline");
				}
				 try {
		        	if(success == false) {
		        		obj.addProperty("error", "true"); 
		        		obj.addProperty("message", "Creating wallet failed"); 
		        	}else {
		        		SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "CCW","Customer created wallet "+assetCode );
		        		obj.addProperty("error", "false"); 
		        		obj.addProperty("message", "Wallet created suceessfully");
		        	}
					output = response.getWriter();
					output.print(obj);
					}finally {
						if(output != null) output.flush();
						if(obj!=null) obj = null; if(authMethod!=null) authMethod= null;
						if(user!=null) user = null;
						if(relationshipNo!=null) relationshipNo = null;
						if(walletDesc!=null) walletDesc = null;
						if (hasMnemonic != null)hasMnemonic = null; 
						if (password != null)password = null; 
						if (mnemoniStringFromDB != null)mnemoniStringFromDB = null; 
						if (keyPair != null)keyPair = null; if (tokenValue != null)tokenValue = null;
					}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Creating wallet failed, Try again");
			}
			break;
			
		case "get_wallet_external_coin_details_mbl":
			try {
				JsonObject object = new JsonObject();
				Gson gson = new Gson(); String tokenValue = null;
				User user = null;
				String relationshipNo= null;
				if(request.getParameter("relno")!=null)	relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					return;
				}
				
				ConcurrentHashMap<String, String> hashWallets = (ConcurrentHashMap<String, String>)CustomerWalletDao.class.getConstructor()
						.newInstance().getCustomerWallets(relationshipNo);
				ArrayList<AssetCoin> arrCryptoAssetCoin = (ArrayList<AssetCoin>)CustomerCryptoCoinDao.class.getConstructor()
						.newInstance().getExternalAssetCoins();
				if(hashWallets.size() > 1) {
					for (int i = 0; i < arrCryptoAssetCoin.size(); i++) {
						if(hashWallets.contains(arrCryptoAssetCoin.get(i).getWalletType())) {
							arrCryptoAssetCoin.remove(i);
							i--;
						}
					}
				}
				object.add("data", gson.toJsonTree(arrCryptoAssetCoin));
				object.add("error", gson.toJsonTree("false"));
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(object));
					response.getWriter().print(object);
				}finally {
					response.getWriter().close();
					if(object!=null) object = null; if (tokenValue != null)tokenValue = null;
					if(arrCryptoAssetCoin!=null) arrCryptoAssetCoin = null;
					if(gson!=null) gson = null;
					if(hashWallets!=null) hashWallets = null;
					if(user!=null) user = null;
					if(relationshipNo!=null) relationshipNo = null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
			}
			break;
			
			case "custregistercoinmbl":
			try {
				String assetCode=null; String userType=null; String assetDesc=null; boolean success = false;
				PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject(); 
				String relationshipNo = null; String errorMsg = "true";
				if(request.getParameter("selcryptocoin")!=null)	assetCode = StringUtils.trim(request.getParameter("selcryptocoin"));
				if(request.getParameter("asssetdesc")!=null)	assetDesc = StringUtils.trim(request.getParameter("asssetdesc"));
				if(request.getParameter("relno")!=null)	relationshipNo = StringUtils.trim(request.getParameter("relno"));
				userType="C"; String assetWalletDetails = null;
			
				
				NeoBankEnvironment.setComment(3, className," inside custregistercoinmbl assetCode is " + assetCode + " assetDesc "+ assetDesc);
				
				/**Call Stellar Network to register new Wallet**/ //.TODO
				String externalWalletId = Utilities.generateExternalWalletId(34);

				//Check if an asset already exist
				assetWalletDetails = (String)CustomerExternalCoinDao.class.getConstructor() .newInstance().getExternalWalletBalance(relationshipNo,assetCode);

				if(assetWalletDetails != null) {
					errorMsg = "Wallet for selected asset already exist";
					obj.add("error", gson.toJsonTree(errorMsg));
					out_json.print(gson.toJson(obj));	
					return;
				}
				
				//Create the wallet container and link with crypto
				success = CustomerCryptoCoinDao.class.getConstructor().newInstance().registerCoinForUser(assetCode, assetDesc, relationshipNo,externalWalletId );				
				
				if (success) {
					//TODO:- Add Audit trail
					errorMsg = "false";
					obj.add("message", gson.toJsonTree("Wallet created suceessfully"));
				}else {
					errorMsg = "Creating wallet failed";
				}
				
				try {
					NeoBankEnvironment.setComment(3, className," custregistercoinmbl String is " + gson.toJson(obj));
					obj.add("error", gson.toJsonTree(errorMsg));
					out_json.print(gson.toJson(obj));	
					} finally {
					if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
					if (userType != null)userType = null; if(obj!=null) obj = null; if(assetCode !=null) assetCode = null;
					 if(assetDesc !=null) assetDesc = null;  if(errorMsg !=null) errorMsg = null;
				}
			}catch(Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Register Coin failed, Try again");
			}
		break;
			case "getbeneficiariesmbl":
				try {
					JsonObject object = new JsonObject();
					Gson gson = new Gson(); String tokenValue = null;
					User user = null;String langPref=null;
					String relationshipNo= null; ArrayList<Customer> arrCustomerListReg = null;
					
					if(request.getParameter("relno")!=null)	relationshipNo = request.getParameter("relno").trim();
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
					
					arrCustomerListReg = (ArrayList<Customer>) CustomerDao.class.getConstructor().newInstance()
							.getAllRegisteredWalletsForASender(relationshipNo);
					if (arrCustomerListReg!=null) {
						object.add("error", gson.toJsonTree("false"));
						object.add("data", gson.toJsonTree(arrCustomerListReg));	
					} else {
						object.add("error", gson.toJsonTree("nodata"));
					}
				
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(object));
						response.getWriter().print(object);
					}finally {
						response.getWriter().close();
						if(object!=null) object = null; if (tokenValue != null)tokenValue = null;
						if(gson!=null) gson = null;
						if(user!=null) user = null;
						if(relationshipNo!=null) relationshipNo = null; if (arrCustomerListReg!=null)arrCustomerListReg=null;
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Failed to get beneficiaries, Try again");
				}
			 break;
		
	case"fiatwalletp2pmbl":
		try {
			
			JsonObject obj = new JsonObject();User user = null;String relationshipNo= null;PrintWriter output = null;
			Gson gson = new Gson();String amount= null;String receiverEmail =null;boolean success = false;
			String walletBalance = null;String txnUserCode = null;String walletDetails =null;String walletId=null;
			String payComments="";String referenceNo="";boolean checkIfEmailExist = false; String userType="C";
			String customerChargesValue=null; String customerCharges=null; String minimumTxnAmount=null;
			String authStatus=""; String authMessage="";  String authResponse=null;String langPref=null;
			boolean recordAuthorization=false; String currencyId = null; String transactionCode=null;
			String txnMode=null;
			String tokenValue = null;
			Wallet m_Wallet=null; List<AssetTransaction>lastFiveTransations=null;
			
			if(request.getParameter("selregistereduser")!=null)	receiverEmail = request.getParameter("selregistereduser").trim();
			if(request.getParameter("sendamount")!=null)amount = request.getParameter("sendamount").trim();
			if(request.getParameter("sendcomment")!=null)payComments = request.getParameter("sendcomment").trim();
			if(request.getParameter("relno")!=null)relationshipNo = request.getParameter("relno").trim();
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
			
			NeoBankEnvironment.setComment(3,className,"Pay comments is"+payComments);
			walletDetails = (String)CustomerWalletDao.class.getConstructor().newInstance().getFiatWalletBalance(relationshipNo);
			checkIfEmailExist= (boolean)CustomerDao.class.getConstructor().newInstance().checkIfEmailExist(receiverEmail);
			if(!checkIfEmailExist) {
				if(langPref.equalsIgnoreCase("ES")) {
					Utilities.sendJsonResponse(response, "error", "Receptor No existe");

				}else {
					Utilities.sendJsonResponse(response, "error", "Receiver Does not exist");
				}
				return;
			}
			if(walletDetails!=null) {
				walletId = walletDetails.split(",")[0];
				walletBalance = walletDetails.split(",")[1];
			}
			
			
			//
			txnMode="D";// Debit
			currencyId = NeoBankEnvironment.getUSDCurrencyId();
			txnUserCode = Utilities.generateTransactionCode(10);
			transactionCode=(new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
					+ Utilities.genAlphaNumRandom(9);
			referenceNo = NeoBankEnvironment.getCodeFiatWalletP2P()+ "-" + transactionCode;
			 customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
					NeoBankEnvironment.getDefaultCustomerUserType(),NeoBankEnvironment.getCodeFiatWalletP2P(), amount);
			 minimumTxnAmount=customerCharges.substring(customerCharges.indexOf("|")+1, customerCharges.length());
			 customerChargesValue = customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|"));
			 
			 NeoBankEnvironment.setComment(3,className,"customerChargesValue is "+customerChargesValue);
			if ((Double.parseDouble(amount)+Double.parseDouble(customerChargesValue)) >Double.parseDouble(walletBalance)) {
				if(langPref.equalsIgnoreCase("ES")) {
					Utilities.sendJsonResponse(response, "error", "No tiene saldo suficiente para completar esta transacci�n. Recargue para continuar");

				}else {
					Utilities.sendJsonResponse(response, "error", "You do not have enough balance to complete this transaction. Please top up to proceed");
				}
				return;
			}
			
			if ( Double.parseDouble(amount)< Double.parseDouble(minimumTxnAmount)) {
				if(langPref.equalsIgnoreCase("ES")) {
					Utilities.sendJsonResponse(response, "error", "El monto de la transacci�n no puede ser inferior a "+minimumTxnAmount);

				}else {
					Utilities.sendJsonResponse(response, "error", "Transaction amount can not be less than "+minimumTxnAmount);
				}
				throw new Exception("minimumAmount amount inputed:- Minimum exptected is:- "+minimumTxnAmount+"Amount inputed is"+amount);
			}
			
			/****** Wallet Authorization******/
			
			authResponse=(String)WalletAuthorizationDao.class.getConstructor().newInstance().performWalletAuthorization(relationshipNo, amount, txnMode);
			if (authResponse.isEmpty()) {
				if(langPref.equalsIgnoreCase("ES")) {
					Utilities.sendJsonResponse(response, "authfailed", "Error en la autorizaci�n, int�ntalo de nuevo m�s tarde");

				}else {
					Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
				}
				return;
			}
			authStatus=authResponse.substring(0,authResponse.indexOf(","));
			authMessage=authResponse.substring(authResponse.indexOf(",")+1,authResponse.indexOf("|"));
			walletId=authResponse.substring(authResponse.indexOf("|")+1,authResponse.length());
			
			NeoBankEnvironment.setComment(3, className, "walletId is "+walletId);
	
			if (authStatus.equals("S")==false) {
				//Authorization failed
				// Record failed authorization
				
				recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, amount, txnMode, currencyId, 
						userType, referenceNo, walletId, authStatus, authMessage);
				if(!recordAuthorization) { 
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "authfailed", "Error en la autorizaci�n, int�ntalo de nuevo m�s tarde");

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
						Utilities.sendJsonResponse(response, "authfailed", "Error en la autorizaci�n, int�ntalo de nuevo m�s tarde");

					}else {
						Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
					}
				return;
				}
			}
			/****** End of Wallet Authorization******/
			NeoBankEnvironment.setComment(3, className, "walletId is "+walletId);
			success = (boolean) CustomerFiatWalletDao.class.getConstructor().newInstance().fiatWalletP2P(relationshipNo, walletId,  amount, payComments, 
					receiverEmail, referenceNo, txnUserCode, customerCharges,transactionCode);
			
			if (success) {
			
				SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, userType,
						NeoBankEnvironment.getCodeFiatWalletP2P(), " Fiat P2P send to "+receiverEmail );
				// Get wallet details and last 5 transactions
				m_Wallet= (Wallet)CustomerWalletDao.class.getConstructor().newInstance().getCustomerWalletDetails(relationshipNo,userType);
				// Get last five transaction
				lastFiveTransations= (List<AssetTransaction>)CustomerWalletDao.class.getConstructor().newInstance().getFiatWalletLastTenTxn(relationshipNo);
				
				obj.add("error", gson.toJsonTree("false"));
				obj.add("data", gson.toJsonTree(m_Wallet));
				obj.add("transactions", gson.toJsonTree(lastFiveTransations));
				
				if(langPref.equalsIgnoreCase("ES")) {
					obj.add("message", gson.toJsonTree("has transferido " + Utilities.getMoneyinDecimalFormat(amount) + 
							" a " + receiverEmail +". Número de referencia " +txnUserCode)); 
				}else {
					obj.add("message", gson.toJsonTree("You have transfered " + Utilities.getMoneyinDecimalFormat(amount) + 
							" to " + receiverEmail +". Reference Number " +txnUserCode));
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
				if (obj != null)obj = null; if (receiverEmail != null)receiverEmail = null;
				if (amount != null)amount = null; if (tokenValue != null)tokenValue = null;
				if (user != null)user = null; 
				if (walletBalance != null)walletBalance = null; 
				if (walletDetails != null)walletDetails = null;  if (userType!=null) userType=null;
				if (walletId != null)walletId = null; 
				if (payComments != null)payComments = null; 
				if (referenceNo != null)referenceNo = null; 
				if (customerChargesValue != null)customerChargesValue = null; if (customerCharges != null)customerCharges = null; 
				if (minimumTxnAmount != null)minimumTxnAmount = null; 
				if (currencyId!=null); currencyId=null;   if (authResponse != null)authResponse = null;
				if (transactionCode!=null); transactionCode=null;   if (txnMode!=null); txnMode=null;
				if (lastFiveTransations!=null)lastFiveTransations=null;
				if(m_Wallet!=null) m_Wallet=null;
			}								
			
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
			Utilities.sendJsonResponse(response, "error", "Error Sending Money, Try again");
		}
		break;
		
		case "searchbeneficiariesmbl":
			try {
				JsonObject object = new JsonObject();
				Gson gson = new Gson(); String relationshipNo=null; Customer m_CustomerDetails =new Customer();
				User user = null;  ArrayList<Customer> arrCustomerListReg = null;String tokenValue = null;
				String customerEmail= null; ArrayList<Customer> arrCustomerList = null;String langPref=null;
				
				if(request.getParameter("input_register_receiver")!=null)	customerEmail = request.getParameter("input_register_receiver").trim();
				if(request.getParameter("relno")!=null)	relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

				NeoBankEnvironment.setComment(3, className, "relationshipNo is "+relationshipNo);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v�lido, vuelva a iniciar sesi�n");

					}else {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					}
					return;
				}

				
				// Check if sender email has inputed their own email. 
			
				m_CustomerDetails= (Customer)CustomerDao.class.getConstructor().newInstance().getFullCustomerProfile(relationshipNo);
				
				NeoBankEnvironment.setComment(3, className, "m_CustomerDetails is "+m_CustomerDetails.getUnmaskedEmail());
				if (m_CustomerDetails.getUnmaskedEmail().equals(customerEmail)) {
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "error", "No puedes registrar tu propio correo electr�nico");

					}else {
						Utilities.sendJsonResponse(response, "error", "You cannot register your own email");
					}
				return;	
				}
				
				// Check if customer has already been registered. 
				arrCustomerListReg = (ArrayList<Customer>) CustomerDao.class.getConstructor().newInstance()
						.getAllRegisteredWalletsForASender(relationshipNo);
				
				if(arrCustomerListReg!=null) {
					for (int i=0; i<arrCustomerListReg.size();i++) {

						if (((Customer) arrCustomerListReg.get(i)).getEmail().equals(customerEmail)) {
							if(langPref.equalsIgnoreCase("ES")) {
								Utilities.sendJsonResponse(response, "error", "Este correo ya ha sido registrado");

							}else {
								Utilities.sendJsonResponse(response, "error", "This email has already been registered");
							}
							return;
						}
						
					}
				}
				
				
				arrCustomerList= (ArrayList<Customer>) CustomerDao.class.getConstructor().newInstance().getAllCustDetailsForRegistration(customerEmail);
				
				if (arrCustomerList!=null) {
					object.add("error", gson.toJsonTree("false"));
					object.add("data", gson.toJsonTree(arrCustomerList));	
				} else {
					object.add("error", gson.toJsonTree("nodata"));
				}
			
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(object));
					response.getWriter().print(object);
				}finally {
					response.getWriter().close();
					if(object!=null) object = null; if (tokenValue != null)tokenValue = null;
					if(gson!=null) gson = null;
					if(user!=null) user = null;
					if(customerEmail!=null) customerEmail = null; if (arrCustomerList!=null)arrCustomerList=null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Failed to get beneficiaries, Try again");
			}
		 break;
		
	case"customeraddbenmbl":
		try {
			NeoBankEnvironment.setComment(3, className, " Inside customer add user");

			String receiverWalletId = null; String senderRelNo =null; String receiverRelNo=null;  
			List<Customer> customerListReg = null;String langPref=null;
			JsonObject obj = new JsonObject();
			PrintWriter output = null; String tokenValue = null;
			Gson gson = new Gson();

			if(request.getParameter("hdnwalletid")!=null)	receiverWalletId = StringUtils.trim(request.getParameter("hdnwalletid"));
			if(request.getParameter("hdnreceiverrelno")!=null)	receiverRelNo = StringUtils.trim(request.getParameter("hdnreceiverrelno"));
			if(request.getParameter("relno")!=null)	senderRelNo = StringUtils.trim(request.getParameter("relno"));
			if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
			if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

			//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
			if(!Utilities.compareMobileToken(senderRelNo, tokenValue)) {
				NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
				if(langPref.equalsIgnoreCase("ES")) {
					Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v�lido, vuelva a iniciar sesi�n");

				}else {
					Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
				}
				return;
			}
			
			NeoBankEnvironment.setComment(3, className, "Receiver wallet id is "+receiverWalletId +" Receiver Relationship no is "+receiverRelNo);
		
			boolean result = (boolean) CustomerDao.class.getConstructor().newInstance()
					.insertReceiverWalletForRegistration(receiverWalletId, senderRelNo, receiverRelNo);
			NeoBankEnvironment.setComment(3, className, "After insertReceiverWalletForRegistration CustomerDao  ");
			
			if(result == false) {
        		obj.addProperty("error", "true"); 
        		if(langPref.equalsIgnoreCase("ES")) {
            		obj.addProperty("message", "No se pudo registrar el usuario"); 

				}else {
	        		obj.addProperty("message", "Failed to register user"); 
				}
        	}else {
        		customerListReg =(List<Customer>)CustomerDao.class.getConstructor().newInstance().getAllRegisteredWalletsForASender(senderRelNo);
        		if(customerListReg!=null) {
        			//TODO:- Add audit trail
        			obj.addProperty("error", "false"); 
	        		obj.add("data", gson.toJsonTree(customerListReg));
	        		if(langPref.equalsIgnoreCase("ES")) {
		        		obj.addProperty("message", "Usuario registrado exitosamente");

					}else {
		        		obj.addProperty("message", "Successfuly registered user");
					}
        		}else {
        			obj.addProperty("error", "true"); 
	        		if(langPref.equalsIgnoreCase("ES")) {
	            		obj.addProperty("message", "No se pudo registrar el usuario"); 

					}else {
		        		obj.addProperty("message", "Failed to register user"); 
					}
        		}
        	}
			try {
				NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + obj);
				output = response.getWriter();
				output.print(obj);
			}finally {
				if (receiverWalletId != null)receiverWalletId = null;if (senderRelNo != null)senderRelNo = null;
				if (tokenValue != null)tokenValue = null; if(output!=null)output.close();
				if (langPref != null)langPref = null;
			}
		}catch(Exception e) {
			NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
			Utilities.callException(request, response, ctx, e.getMessage());
		}
		break;
		
		}
	}

	@Override
	public void performOperation (String rulesaction, HttpServletRequest request, HttpServletResponse response,
	ServletContext ctx)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
