package com.pporte.rules;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.stellar.sdk.Asset;
import org.stellar.sdk.KeyPair;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.CustomerDao;
import com.pporte.dao.CustomerDigitalAssetsDao;
import com.pporte.dao.CustomerDigitalCurrenciesDao;
import com.pporte.dao.CustomerPorteCoinDao;
import com.pporte.dao.OpsManageCryptoDao;
import com.pporte.dao.RemittanceDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.dao.TDAManagementDao;
import com.pporte.model.AssetAccount;
import com.pporte.model.AssetCoin;
import com.pporte.model.AssetTransactions;
import com.pporte.model.BTCTransction;
import com.pporte.model.BitcoinDetails;
import com.pporte.model.CryptoAssetCoins;
import com.pporte.model.Customer;
import com.pporte.model.PaymentOffer;
import com.pporte.model.Transaction;
import com.pporte.model.User;
import com.pporte.utilities.BitcoinUtility;
import com.pporte.utilities.CurrencyTradeUtility;
import com.pporte.utilities.ExchangeRatesUtility;
import com.pporte.utilities.SendEmailUtility;
import com.pporte.utilities.StellarSDKUtility;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class TDAManagementRulesImpl implements Rules{
	private String className=TDAManagementRulesImpl.class.getSimpleName();
	@Override
	public void performJSONOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		
	}

	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		HttpSession session=request.getSession(false);
		if (session.getAttribute("SESS_USER") == null) 
			Utilities.callOpsException(request, response, ctx, "Session has expired, please log in again");
		switch (rulesaction) {
		case "create_stellar_btcx_account":
			try {
				String hasAccount = null; User user = null;
				String publicKey = null; JsonObject stellarAcount = null;
				Boolean createAccountIsSuccessfull = false;
				Boolean accountExist = false; JsonObject obj = new JsonObject();
				KeyPair keyPair = null; Boolean success = false;  String userId=null;
				PrintWriter output = null; String assetCode =null; String moduleCode=null;
			
				userId = ((User)session.getAttribute("SESS_USER")).getUserId();
				assetCode  = NeoBankEnvironment.getStellarBTCxCode();
				if(request.getParameter("has_account")!=null)hasAccount = request.getParameter("has_account").trim();
	
				// Check if BTCx Stellar account has already been linked to the system using our system.
				if(TDAManagementDao.class.getConstructor().newInstance().checkIfBTCxAccountHasBeenLinked()) {
					Utilities.sendJsonResponse(response, "error", "You have already linked Stellar BTCx Account in our system");
					return;
				}
				
				if(Boolean.parseBoolean(hasAccount)) {
					//Check if account exist
					if(request.getParameter("input_public_key")!=null)publicKey = request.getParameter("input_public_key").trim();
					keyPair =  KeyPair.fromAccountId(publicKey);
					accountExist = StellarSDKUtility.CheckAccountIfExist(keyPair);
					if(!accountExist) {
						NeoBankEnvironment.setComment(3, className, " Account Does not exist in stellar ");
						Utilities.sendJsonResponse(response, "error", " Account Does not exist in stellar ");
						return;
					}
					success = (Boolean) TDAManagementDao.class.getConstructor().newInstance().linkStellarBTCxAccount(keyPair.getAccountId(), assetCode );
				}
				
				if(!Boolean.parseBoolean(hasAccount)) {
					keyPair = StellarSDKUtility.generateKeyPair();
					stellarAcount = new JsonObject();
					stellarAcount = StellarSDKUtility.createAccount(keyPair);
					createAccountIsSuccessfull = stellarAcount.get("successful").getAsBoolean();
					if(createAccountIsSuccessfull)
						success = (Boolean) TDAManagementDao.class.getConstructor().newInstance().linkStellarBTCxAccount(keyPair.getAccountId(), assetCode );
				}
				if(success) {
					moduleCode="TCB";// TDA Create BTCx Stellar  Account
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId,"T", moduleCode,"TDA Create Stellar BTCx Account" );
					obj.addProperty("error", "false");
					if(!Boolean.parseBoolean(hasAccount)) {
						obj.addProperty("message", "You have successfully created Stellar Account "
								+"\n"+"Account number is: "+keyPair.getAccountId()+"\n"+"Private Key is: "+String.valueOf( keyPair.getSecretSeed()));
					}else {

						obj.addProperty("message", "You have successfully Linked your Stellar Account ");
					}
				}else {
					obj.addProperty("error", "false");
					obj.addProperty("message", "Creating account failed");
				}
				try {

					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
					output = response.getWriter();
					output.print(obj);
				} finally {
					if(user!=null) user=null; if(publicKey!=null) publicKey=null;
					if(stellarAcount!=null) stellarAcount=null; if(obj!=null) obj=null;if (moduleCode!=null) moduleCode=null;
					if (userId!=null)userId=null; 
					if(keyPair!=null) keyPair=null;if(output!=null) output.close(); if (assetCode!=null) assetCode=null;
				}
				
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in Linking BTCx Stellar Account, Please try again letter");
			}
			
	    break;
		
		
			case "create_btcx_trustline":
				try {
					JsonObject obj = new JsonObject();
					User user = null; int baseFee  = org.stellar.sdk.Transaction.MIN_BASE_FEE;
					String userId= null; String limit = null;
					Boolean success = false; 
					PrintWriter output = null; String assetCode =null;
					String privateKey =null;
					String btcxAccount = null; String moduleCode=null;
					String issuerAccountId = null;
					boolean createTrustline = false;
				
					user = (User) session.getAttribute("SESS_USER");
					userId = user.getUserId();
					
					if(request.getParameter("asset_value")!=null)	
						assetCode = request.getParameter("asset_value").trim();
					if(request.getParameter("input_private_key")!=null)	
						privateKey = request.getParameter("input_private_key").trim();
					NeoBankEnvironment.setComment(3, className, " assetCode "+assetCode );
					
					if (assetCode.equals(NeoBankEnvironment.getStellarBTCxCode())) {
						//if(request.getParameter("btcx_issuer")!=null)	
						//issuerAccountId = request.getParameter("btcx_issuer").trim();
						issuerAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(assetCode);
						// NOTE:- assetCode has been changed to BTC because in the system the we refer Stellar Bitcoin as BTCX but when
						// 		  sending it to Stellar the code should be BTC. 
						assetCode=NeoBankEnvironment.getBitcoinCode();
					}
					if (assetCode.equals(NeoBankEnvironment.getPorteTokenCode())) {
//						issuerAccountId=NeoBankEnvironment.getPorteIssuerAccountId();
						issuerAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(assetCode);
						
					}
					if (assetCode.equals(NeoBankEnvironment.getVesselCoinCode())) {
//						issuerAccountId=NeoBankEnvironment.getVesselIssuerAccountId();
						issuerAccountId=(String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(assetCode);
					}
					
					btcxAccount= TDAManagementDao.class.getConstructor().newInstance().getBTCxDistributionAccount();					
					if(btcxAccount == null) {
						throw new Exception("Stellar BTCx Account not created,please create first before creating trustline ");
						}
					
					KeyPair source = KeyPair.fromSecretSeed(privateKey);
				     if(!source.getAccountId().equals(btcxAccount)) {
						obj.addProperty("error", "true"); 
		        		obj.addProperty("message", "Secret key is incorrect"); 
		        		output = response.getWriter();
						output.print(obj);
				    	 return;
				     }
				
					limit = NeoBankEnvironment.getMaxStellarAssetWalletLimit();
					createTrustline = StellarSDKUtility.createTrustline(issuerAccountId, privateKey,
							 baseFee, limit,  assetCode );
					if(createTrustline) {
						// Save Issuer account in the DB
						success = (Boolean)TDAManagementDao.class.getConstructor().newInstance().saveBTCXIssuerAccount(
								issuerAccountId, assetCode);
					}else {
						throw new Exception("Internal error in creating Trustline");
					}
					 try {
			        	if(success == false) {
			        		obj.addProperty("error", "true"); 
			        		obj.addProperty("message", "Creating trustline failed"); 
			        	}else {
			        		moduleCode="TTC"; // TDA  Trustline creation
			        		SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId,"T", moduleCode,"TDA  Trustline creation "+assetCode );
			        		obj.addProperty("error", "false"); 
			        		obj.addProperty("message", "Trustline Created Successfully");
			        	}
						output = response.getWriter();
						output.print(obj);
						}finally {
							if(output != null) output.flush();
							if(obj!=null) obj = null;
							if(user!=null) user = null;
							if(userId!=null) userId = null;
							if(btcxAccount!=null) btcxAccount = null;
						}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Creating trustline failed, Please try again letter");
				}
				break;
				
			 	case "addassetpricing":
			        try {
						String assetCode = null; String assetSellRate = null; 	String priceStatus = null;	
						String userId = null; String userType = null; 	Boolean success = false; String rate = null;	
						JsonObject obj = new JsonObject(); PrintWriter out_json = response.getWriter(); String status = null;
						 Gson gson = new Gson();  String moduleCode=null;
						
						

						 if(request.getParameter("hdnaddassetcode")!=null)	assetCode = StringUtils.trim( request.getParameter("hdnaddassetcode") );
						 if(request.getParameter("addexhangerate")!=null)	assetSellRate = StringUtils.trim( request.getParameter("addexhangerate") );
						 if(request.getParameter("hdnaddsellratetystatus")!=null)	priceStatus = StringUtils.trim( request.getParameter("hdnaddsellratetystatus") );
				
						
						userId=((User) session.getAttribute("SESS_USER")).getUserId();
						userType=((User) session.getAttribute("SESS_USER")).getUserType();
		
						success = (boolean)TDAManagementDao.class.getConstructor().newInstance().addNewAssetPricing(assetCode, assetSellRate, priceStatus);
		
						if(success) {
							moduleCode="APC"; // Asset Pricing Creation
							SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, moduleCode,"Created new Asset Pricing for " + assetCode);
							
						    obj.add("error", gson.toJsonTree("false")); 
						    obj.add("message", gson.toJsonTree(assetCode+" Pricing Created successful")); 
		
			        	}else {
						    obj.add("error", gson.toJsonTree("true")); 
						    obj.add("message", gson.toJsonTree("Error in creating asset pricing")); 
			        	}
						try {
							 out_json.print( gson.toJson(obj));
						}finally {
							if(assetCode!=null) assetCode=null; if(userType!=null) userType=null; if(assetSellRate!=null) assetSellRate=null; 
							if(priceStatus!=null) priceStatus=null; if(userId!=null) userId=null; if(rate !=null) rate=null;
							if(status!=null) status=null;  if (moduleCode!=null)moduleCode=null;
							if(obj!=null) obj = null; if(out_json!=null) out_json.close();
						}
						
					}catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "TDa Create BTCx Pricing failed, Please try again letter");
					}
				break;
			 	case "addassetexchangepricing":
			 		try {
			 			String sourceAssetCode = null; String destinationAssetCode=null; String assetExchangeRate = null; 	String priceStatus = null;	
			 			String userId = null; String userType = null; 	Boolean success = false; String rate = null;	
			 			JsonObject obj = new JsonObject(); PrintWriter out_json = response.getWriter(); String status = null;
			 			Gson gson = new Gson();  String moduleCode=null;
			 			
			 			if(request.getParameter("seladdsourceassetcode")!=null)	sourceAssetCode = StringUtils.trim( request.getParameter("seladdsourceassetcode") );
			 			if(request.getParameter("seladddestinationassetcode")!=null)	destinationAssetCode = StringUtils.trim( request.getParameter("seladddestinationassetcode") );
			 			if(request.getParameter("addexhangerate")!=null)	assetExchangeRate = StringUtils.trim( request.getParameter("addexhangerate") );
			 			if(request.getParameter("selladdstatus")!=null)	priceStatus = StringUtils.trim( request.getParameter("selladdstatus") );
			 			
			 			userId=((User) session.getAttribute("SESS_USER")).getUserId();
			 			userType=((User) session.getAttribute("SESS_USER")).getUserType();
			 			
			 			success = (boolean)TDAManagementDao.class.getConstructor().newInstance().addNewAssetExchangePricing(sourceAssetCode, destinationAssetCode, assetExchangeRate, priceStatus);
			 			
			 			if(success) {
			 				moduleCode="AEC"; // Asset Exchange Pricing Creation
			 				SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, moduleCode,"Created new Asset Exchange for source asset " +
			 						sourceAssetCode+ " destination asset "+destinationAssetCode);
			 				obj.add("error", gson.toJsonTree("false")); 
			 				obj.add("message", gson.toJsonTree("Asset Exchange Rate for "+sourceAssetCode+ " and "+sourceAssetCode+" Created successful")); 
			 				
			 			}else {
			 				obj.add("error", gson.toJsonTree("true")); 
			 				obj.add("message", gson.toJsonTree("Error in creating asset exchange")); 
			 			}
			 			try {
			 				out_json.print( gson.toJson(obj));
			 			}finally {
			 				if(sourceAssetCode!=null) sourceAssetCode=null; if(destinationAssetCode!=null) destinationAssetCode=null; if(userType!=null) userType=null;
			 				if(assetExchangeRate!=null) assetExchangeRate=null; 
			 				if(priceStatus!=null) priceStatus=null; if(userId!=null) userId=null; if(rate !=null) rate=null;
			 				if(status!=null) status=null;  if (moduleCode!=null)moduleCode=null;
			 				if(obj!=null) obj = null; if(out_json!=null) out_json.close();
			 			}
			 			
			 		}catch (Exception e) {
			 			NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
			 			Utilities.sendJsonResponse(response, "error", "TDA Create Asset Exchange Pricing failed, Please try again letter");
			 		}
			 		break;
				case "editassetpricing":
					try {
						String assetCode = null; String assetSellRate = null; 	String priceStatus = null;	
						String userId = null; String userType = null; 	Boolean success = false; String rate = null;	
						JsonObject obj = new JsonObject(); PrintWriter out_json = response.getWriter(); String status = null;
					    Gson gson = new Gson(); String sequencId = null;String moduleCode=null;
						
						 if(request.getParameter("editassetcode")!=null)	assetCode = StringUtils.trim( request.getParameter("editassetcode") );
						 if(request.getParameter("editsellrate")!=null)	assetSellRate = StringUtils.trim( request.getParameter("editsellrate") );
						 if(request.getParameter("hdnsellratetystatus")!=null)	priceStatus = StringUtils.trim( request.getParameter("hdnsellratetystatus") );
						 if(request.getParameter("hdnsequenceno")!=null)	sequencId = StringUtils.trim( request.getParameter("hdnsequenceno") );
						
						
						NeoBankEnvironment.setComment(3, className, "inside editbtcxpricing " + assetCode+ " assetSellRate "
								+ assetSellRate +" priceStatus "+priceStatus +" sequencId "+ sequencId );
						
						userId=((User) session.getAttribute("SESS_USER")).getUserId();
						userType=((User) session.getAttribute("SESS_USER")).getUserType();

						success = (boolean)TDAManagementDao.class.getConstructor().newInstance().editAssetPricing(assetCode, assetSellRate, priceStatus, sequencId);

						if(success) {
							moduleCode="APE"; // Asset Pricing Editing
							SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, moduleCode,"TDA Updated Asset Pricing" + assetCode);
							
			        		obj.add("error", gson.toJsonTree("false")); 
						    obj.add("message", gson.toJsonTree(assetCode+" Pricing updated successful")); 

			        	}else {
			        		obj.add("error", gson.toJsonTree("true")); 
						    obj.add("message", gson.toJsonTree("Error in updating BTCx Pricing")); 

			        	}
						try {
							 out_json.print( gson.toJson(obj));
						}finally {
							if(assetCode!=null) assetCode=null; if(userType!=null) userType=null; if(assetSellRate!=null) assetSellRate=null; 
							if(priceStatus!=null) priceStatus=null; if(userId!=null) userId=null; if(rate !=null) rate=null;
							if(sequencId!=null) sequencId=null; if(status!=null) status=null; 
							if(obj!=null) obj = null; if(out_json!=null) out_json.close(); if (moduleCode!=null)moduleCode=null;
						}
						
					}catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "TDA Edit BTCx Pricing failed, Please try again letter");
					}
					break;
				case "editassetexhangepricing":
					try {
						String sourceAssetCode = null; String destinationAssetCode = null; 	String priceStatus = null;	
						String userId = null; String userType = null; 	Boolean success = false; String rate = null;	
						JsonObject obj = new JsonObject(); PrintWriter out_json = response.getWriter(); String status = null;
						Gson gson = new Gson(); String sequencId = null;String moduleCode=null;
						
						if(request.getParameter("editsourceassetcode")!=null)	sourceAssetCode = StringUtils.trim( request.getParameter("editsourceassetcode") );
						if(request.getParameter("editdestinationassetcode")!=null)	destinationAssetCode = StringUtils.trim( request.getParameter("editdestinationassetcode") );
						if(request.getParameter("selleditstatus")!=null)	priceStatus = StringUtils.trim( request.getParameter("selleditstatus") );
						if(request.getParameter("hdnsequenceno")!=null)	sequencId = StringUtils.trim( request.getParameter("hdnsequenceno") );
						
						userId=((User) session.getAttribute("SESS_USER")).getUserId();
						userType=((User) session.getAttribute("SESS_USER")).getUserType();
						
						success = (boolean)TDAManagementDao.class.getConstructor().newInstance().editAssetExchangePricing( priceStatus, sequencId);
						
						if(success) {
							moduleCode="AEE"; // Asset Exchange Pricing Editing
							SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, moduleCode,"TDA Updated Asset Exchange Pricing for " 
							+ sourceAssetCode+ " and "+ destinationAssetCode);
							
							obj.add("error", gson.toJsonTree("false")); 
							obj.add("message", gson.toJsonTree("Asset Exchange for"+sourceAssetCode+" and "+destinationAssetCode+" updated successful")); 
							
						}else {
							obj.add("error", gson.toJsonTree("true")); 
							obj.add("message", gson.toJsonTree("Error in updating Asset Exchange Pricing")); 
							
						}
						try {
							out_json.print( gson.toJson(obj));
						}finally {
							if(sourceAssetCode!=null) sourceAssetCode=null; if(userType!=null) userType=null; if(destinationAssetCode!=null) destinationAssetCode=null; 
							if(priceStatus!=null) priceStatus=null; if(userId!=null) userId=null; if(rate !=null) rate=null;
							if(sequencId!=null) sequencId=null; if(status!=null) status=null; 
							if(obj!=null) obj = null; if(out_json!=null) out_json.close(); if (moduleCode!=null)moduleCode=null;
						}
					}catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "TDA Edit Asset Exchange Pricing failed, Please try again letter");
					}
					break;
					
				case "addporteassetmarkuprate":
			        try {
						String assetCode = null; String porteAssetMarkupRate = null; 	String priceStatus = null;	
						String userId = null; String userType = null; 	Boolean success = false; String rate = null;	
						JsonObject obj = new JsonObject(); PrintWriter out_json = response.getWriter(); String status = null;
						String errorMsg = ""; Gson gson = new Gson();  String moduleCode=null;

						 if(request.getParameter("hdnaddassetcode")!=null)	assetCode = StringUtils.trim( request.getParameter("hdnaddassetcode") );
						 if(request.getParameter("addexhangerate")!=null)	porteAssetMarkupRate = StringUtils.trim( request.getParameter("addexhangerate") );
						 if(request.getParameter("hdnaddsellratetystatus")!=null)	priceStatus = StringUtils.trim( request.getParameter("hdnaddsellratetystatus") );
						 				
						NeoBankEnvironment.setComment(3, className, "addporteassetmarkuprate" + assetCode+ " assetSellRate "
								+ porteAssetMarkupRate +" priceStatus "+priceStatus );
						
						
						userId=((User) session.getAttribute("SESS_USER")).getUserId();
						userType=((User) session.getAttribute("SESS_USER")).getUserType();
		
						success = (boolean)TDAManagementDao.class.getConstructor().newInstance().addNewPorteAssetMarkupRate(assetCode, porteAssetMarkupRate, priceStatus);
		
						if(success) {
							moduleCode="PMC"; // Porteasset Markup Creation
							SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, moduleCode,
									StringUtils.substring("Created new markup rate for" + assetCode, 0, 48));
							
			        		errorMsg = "false";
						    obj.add("message", gson.toJsonTree(assetCode+" Markup Rate created successful")); 
		
			        	}else {
							errorMsg = "Error in creating markup rate";
		
			        	}
						try {
							 obj.add("error", gson.toJsonTree(errorMsg)); 
							 out_json.print( gson.toJson(obj));
						}finally {
							if(assetCode!=null) assetCode=null; if(userType!=null) userType=null; if(porteAssetMarkupRate!=null) porteAssetMarkupRate=null; 
							if(priceStatus!=null) priceStatus=null; if(userId!=null) userId=null; if(rate !=null) rate=null;
							if(status!=null) status=null; if (errorMsg!=null)errorMsg=null; if (moduleCode!=null)moduleCode=null;
							if(obj!=null) obj = null; if(out_json!=null) out_json.close();
						}
						
					}catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "TDa Create Porte Asset Markup Rate failed, Please try again letter");
					}
				break;
				
				case "editporteassetmarkuprate":
					try {
						String assetCode = null; String porteAssetMarkupRate = null; 	String priceStatus = null;	
						String userId = null; String userType = null; 	Boolean success = false; String rate = null;	
						JsonObject obj = new JsonObject(); PrintWriter out_json = response.getWriter(); String status = null;
						String errorMsg = ""; Gson gson = new Gson(); String sequencId = null;String moduleCode=null;
						
						 if(request.getParameter("editassetcode")!=null)	assetCode = StringUtils.trim( request.getParameter("editassetcode") );
						 if(request.getParameter("editsellrate")!=null)	porteAssetMarkupRate = StringUtils.trim( request.getParameter("editsellrate") );
						 if(request.getParameter("hdnsellratetystatus")!=null)	priceStatus = StringUtils.trim( request.getParameter("hdnsellratetystatus") );
						 if(request.getParameter("hdnsequenceno")!=null)	sequencId = StringUtils.trim( request.getParameter("hdnsequenceno") );
						 
						
						NeoBankEnvironment.setComment(3, className, "inside editporteassetmarkuprate " + assetCode+ " assetSellRate "
								+ porteAssetMarkupRate +" priceStatus "+priceStatus +" sequencId "+ sequencId );
						
						userId=((User) session.getAttribute("SESS_USER")).getUserId();
						userType=((User) session.getAttribute("SESS_USER")).getUserType();

						success = (boolean)TDAManagementDao.class.getConstructor().newInstance().editPorteAssetMarkupRate(assetCode, porteAssetMarkupRate, priceStatus, sequencId);

						if(success) {
							moduleCode="PME"; // PorteAsset Markup rate Editing
							SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, moduleCode,
									StringUtils.substring("TDA Updated Porte Asset Markup Rate" + assetCode, 0, 48));
							
			        		errorMsg = "false";
						    obj.add("message", gson.toJsonTree("Porte Asset Markup Rate updated successful")); 

			        	}else {
							errorMsg = "Error in updating  Porte Asset Markup Rate";

			        	}
						try {
							 obj.add("error", gson.toJsonTree(errorMsg)); 
							 out_json.print( gson.toJson(obj));
						}finally {
							if(assetCode!=null) assetCode=null; if(userType!=null) userType=null; if(porteAssetMarkupRate!=null) porteAssetMarkupRate=null; 
							if(priceStatus!=null) priceStatus=null; if(userId!=null) userId=null; if(rate !=null) rate=null;
							if(sequencId!=null) sequencId=null; if(status!=null) status=null; 
							if(obj!=null) obj = null; if(out_json!=null) out_json.close(); if (moduleCode!=null)moduleCode=null;
						}
						
					}catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "TDA Edit  Porte Asset Markup Rate failed, Please try again letter");
					}
					break;
				case "approve_fiat_to_btcx_transaction":
					try {
						String custRelNo=null; String publicKey=null; 
						String txnAmount=null; String coinAmount=null; 
						String assetCode=null; String distributorAccountPrivateKey=null; 
						PrintWriter out_json = response.getWriter();  JsonObject obj = new JsonObject();
						boolean success=false;  String extSystemRef = "";String comment=null;
						String buyStatus = null; User user = null;
						String txnCode = null; String [] splitResponse=null; 
						String issuerAccountId = null;
						String customerUserId=null;
						Customer c_Details=null; String subject=null; String content=null;
						if(request.getParameter("relno")!=null)	custRelNo = request.getParameter("relno").trim();
						if(request.getParameter("publickey")!=null)	publicKey = request.getParameter("publickey").trim();
						if(request.getParameter("txnamount")!=null)	txnAmount = request.getParameter("txnamount").trim();
						if(request.getParameter("coinamount")!=null) coinAmount = request.getParameter("coinamount").trim();
						if(request.getParameter("privatekey")!=null) distributorAccountPrivateKey = request.getParameter("privatekey").trim();
						if(request.getParameter("txncode")!=null) txnCode = request.getParameter("txncode").trim();
						if(request.getParameter("comment")!=null) comment = request.getParameter("comment").trim();
						if(request.getParameter("cuustmerid")!=null) customerUserId = request.getParameter("cuustmerid").trim();
						
						
						 if (session.getAttribute("SESS_USER") == null) 
								throw new Exception ("Session has expired, please log in again");
						 user = (User) session.getAttribute("SESS_USER");
						 
						 NeoBankEnvironment.setComment(3,className," txnCode is "+txnCode);

						issuerAccountId = (String)TDAManagementDao.class.getConstructor().newInstance().getBTCxIssuingAccount();
						// NOTE:- assetCode has been changed to BTC because in the system the we refer Stellar Bitcoin as BTCX but when
						// 		  sending it to Stellar the code should be BTC. 
						assetCode=NeoBankEnvironment.getBitcoinCode();
						buyStatus = StellarSDKUtility.buyNoNNativeCoinPaymentWithStellarHashResponse(distributorAccountPrivateKey,publicKey,assetCode,
									issuerAccountId, coinAmount,comment );
						
						 splitResponse=buyStatus.split(",");
						 extSystemRef=splitResponse[1];
						 NeoBankEnvironment.setComment(3,className,"buyStatus is "+buyStatus+" past here hash is "+extSystemRef);
						if(splitResponse[0].equals("true")) { 
							success= (boolean)OpsManageCryptoDao.class.getConstructor().newInstance().updateCustomerDetails(txnCode, extSystemRef);
								if(success) {
									SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(user.getUserId(),"C", "BIC","Approved Fiat to BTCx transaction" );
									obj.addProperty("error", "false"); 
					        		obj.addProperty("message", "Transaction approved: "+coinAmount+" "+assetCode+" has been transfered to "+customerUserId); 
					        		
					        		subject="YOU HAVE RECEIVED "+assetCode+" IN YOUR WALLET!";
									 content="Your purchase of "+coinAmount+" "+assetCode+" has been sent you.";
									// Send to user
									 c_Details=(Customer)CustomerDao.class.getConstructor().newInstance().getFullCustomerProfile(custRelNo);
									 
									String sendto = c_Details.getUnmaskedEmail();
									String sendSubject = subject;
									String sendContent = content;
									String customerName = c_Details.getName();
										
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
								}else {
									obj.addProperty("error", "true"); 
					        		obj.addProperty("message", "Transaction failed");
								}
						}else {
							obj.addProperty("error", "true"); 
			        		obj.addProperty("message", "Transaction failed in Stellar");
						}
						try {
							out_json = response.getWriter();
							out_json.print(obj);
						}finally {
							 if(coinAmount!=null) coinAmount=null; if (comment!=null)comment=null;
							if(custRelNo!=null) custRelNo=null; if(publicKey!=null) publicKey=null;
							if(assetCode!=null) assetCode=null; if(distributorAccountPrivateKey!=null) distributorAccountPrivateKey=null;
							if(obj!=null) obj = null; if (customerUserId!=null)customerUserId=null; if (splitResponse!=null)splitResponse=null;
							if (buyStatus!=null)buyStatus=null;
							if(txnCode!=null) txnCode = null; if (txnAmount!=null)txnAmount=null;
							if(out_json!=null) out_json.close();
						}
						
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "Error in approving transaction, Please try again letter");
					}
						
					break;
				case "approve_assets_to_btcx_transaction":
					try {
						NeoBankEnvironment.setComment(3, className,"==== start approve_assets_to_btcx_transaction " + java.time.LocalTime.now() );
						
						JsonObject obj = new JsonObject(); User user = null;  String userId= null; PrintWriter output = null;
						Gson gson = new Gson(); String amount= null; String receiverEmail =null; Boolean success = false;
						String walletBalance = null; 	 String walletDetails =null; 
						String senderWalletId=null; String payComments=""; 	String referenceNo=""; 
						String receiverWalletId = null; String assetCodeSender = null; String customerId=null;
						String assetCodeReceiver = null; String txnPayMode = null; String extSystemRef = null; 
						String senderKey = null; KeyPair destinationAccount = null; KeyPair sourceAccount =null; 
						String custRelNo=null;
						
						
						String sourceAssetIssuer = null; String sourceAcountId = null; String results = null;
						String [] splitResult=null; String stellarHash=null; String transactionCode=null;
						String sourceAsset=null; String subject=null; String content=null; Customer c_Details=null;
						
						if(request.getParameter("destinationamount")!=null) amount = request.getParameter("destinationamount").trim();
						if(request.getParameter("sourceassetcode")!=null) sourceAsset = request.getParameter("sourceassetcode").trim();
						if(request.getParameter("destinationassetcode")!=null)	 assetCodeSender = request.getParameter("destinationassetcode").trim();
						if(request.getParameter("narrative")!=null)	 payComments = request.getParameter("narrative").trim();
						if(request.getParameter("publickey")!=null)	 receiverWalletId = request.getParameter("publickey").trim(); //Public Key
						if(request.getParameter("txncode")!=null)	 transactionCode = request.getParameter("txncode").trim(); //Public Key
						if(request.getParameter("privatekey")!=null)	 senderKey = request.getParameter("privatekey").trim();
						if(request.getParameter("customerId")!=null)	 customerId = request.getParameter("customerId").trim();
						if(request.getParameter("relno")!=null)	 custRelNo = request.getParameter("relno").trim();
						
						if (session.getAttribute("SESS_USER") == null) 
							throw new Exception ("Session has expired, please log in again");
						user = (User) session.getAttribute("SESS_USER");
						userId = user.getUserId();
						
						NeoBankEnvironment.setComment(3, className," inside approve_assets_to_btcx_transaction is receiverEmail " + receiverEmail +" amount "+ amount + " assetCodeSender "+ assetCodeSender
								+" assetCodeReceiver "+ assetCodeReceiver+" userId "+ userId );
						
						/**Check if accounts exists*/
						
						//Sender key SB7AJALFNBC62RFB7VWCB7EWBT47XA6IYW4I2XFSX5CKPEDMZZFT6XSD
						//receiver walletid GCLVJMQHLYKJKGZOCC5Z24NGAFMJTSSF4N4VF77CZICXTGYAE5XSGUBP
						try {
							 sourceAccount = KeyPair.fromSecretSeed(senderKey);
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
						sourceAcountId = (String)TDAManagementDao.class.getConstructor().newInstance().getBTCxDistributionAccount();
						sourceAssetIssuer= (String)TDAManagementDao.class.getConstructor().newInstance().getBTCxIssuingAccount();
						
						
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
						
						 if (assetCodeSender.equals(NeoBankEnvironment.getStellarBTCxCode())) {
							 // Note:- If BTCX change code to BTC to send to Stellar
							 results = StellarSDKUtility.sendNoNNativeCoinPayment(
									 NeoBankEnvironment.getBitcoinCode(), sourceAccount, destinationAccount, amount, payComments, sourceAssetIssuer);
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
						
						txnPayMode=NeoBankEnvironment.getCodeTDASwapPorteAssetsForBTCx();
						
						referenceNo = txnPayMode+ "-" + (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
								+ Utilities.genAlphaNumRandom(9);
						
							 // Get Stellar Txn hash
							extSystemRef= stellarHash;
							
							success = (boolean)TDAManagementDao.class.getConstructor().newInstance().updateCustomerDetails(transactionCode, extSystemRef);
							if (success) {
								String moduleCode = txnPayMode;
								SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, "C",
										moduleCode," TDA "+sourceAsset+" Asset swap to BTCx ");
								obj.add("error", gson.toJsonTree("false"));
								//obj.add("message", gson.toJsonTree("You have transfered " + assetCodeSender+" "+ Utilities.getMoneyinDecimalFormat(amount) + 
								obj.add("message", gson.toJsonTree("Transaction approved: "+ amount +  " "+assetCodeSender+" has been transferred to "+customerId )); 
								
								subject="YOU HAVE RECEIVED "+assetCodeSender+" IN YOUR WALLET!";
								 content="Your purchase of "+amount+" "+assetCodeSender+" has been sent you.";
								// Send to user
								 c_Details=(Customer)CustomerDao.class.getConstructor().newInstance().getFullCustomerProfile(custRelNo);
								 
								String sendto = c_Details.getUnmaskedEmail();
								String sendSubject = subject;
								String sendContent = content;
								String customerName = c_Details.getName();
									
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

							}else {
								obj.add("error", gson.toJsonTree("true")); 
								obj.add("message", gson.toJsonTree("Transaction failed")); 
							}
									
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						output = response.getWriter();
						output.print(gson.toJson(obj));
						NeoBankEnvironment.setComment(3, className,"========== end approve_assets_to_btcx_transaction " + java.time.LocalTime.now() );

					} finally {
						if (output != null)output.close();  if (gson != null)gson = null;
						if (obj != null)obj = null; if (receiverEmail != null)receiverEmail = null;
						if (amount != null)amount = null;  if (assetCodeReceiver != null)assetCodeReceiver = null;
						if (extSystemRef != null)extSystemRef = null; 
						if (user != null)user = null; 
						if (walletBalance != null)walletBalance = null; 
						if (walletDetails != null)walletDetails = null; 
						if (senderWalletId != null)senderWalletId = null; 
						if (payComments != null)payComments = null; 
						if (referenceNo != null)referenceNo = null;  if (receiverWalletId != null) receiverWalletId = null; 
						if (assetCodeSender != null)assetCodeSender = null; if (txnPayMode != null)txnPayMode = null;
						if (custRelNo!=null)custRelNo=null;
						if (c_Details!=null)c_Details=null; if (subject!=null);subject=null; if (content!=null);content=null;
					 }								
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "true", "Error in TDA Asset Swap to BTCx Please try again later");
					}
				break;
				
				case "exchange_porte_assets_to_btcx":
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
						User user = null;
						String moduleCode=null;
						String userId=null;
					
						if(request.getParameter("coin_asset")!=null)	 sourceAssetCode = request.getParameter("coin_asset").trim();
						if(request.getParameter("receiver_asset")!=null)	 destionAssetCode = request.getParameter("receiver_asset").trim();
						if(request.getParameter("sell_amount")!=null)	 sourceAmount = request.getParameter("sell_amount").trim();
						if(request.getParameter("receivedamount")!=null)	 destMinAmount = request.getParameter("receivedamount").trim();
						if(request.getParameter("private_key")!=null)	 privateKey = request.getParameter("private_key").trim();
						
						
						user = (User) session.getAttribute("SESS_USER");
						userId=user.getUserId();
						sourceAcountId = (String)TDAManagementDao.class.getConstructor().newInstance().getBTCxDistributionAccount();
						if(sourceAcountId == null)
							throw new Exception(" TDA Account doesn't exist in stellar");
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
					 			
						if(sourceAssetCode.equals(NeoBankEnvironment.getPorteTokenCode())) {
							sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
									.newInstance().getIssueingAccountPublicKey(sourceAssetCode);
						}else if(sourceAssetCode.equals(NeoBankEnvironment.getXLMCode())) {
							sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
									.newInstance().getIssueingAccountPublicKey(sourceAssetCode);
						}else if(sourceAssetCode.equals(NeoBankEnvironment.getVesselCoinCode())) {
							sourceAssetIssuer =(String)CustomerDigitalAssetsDao.class.getConstructor()
									.newInstance().getIssueingAccountPublicKey(sourceAssetCode);
						}else if(sourceAssetCode.equals(NeoBankEnvironment.getUSDCCode())) {
							sourceAssetIssuer = (String)CustomerDigitalAssetsDao.class.getConstructor()
									.newInstance().getIssueingAccountPublicKey(sourceAssetCode);
						}else if(sourceAssetCode.equals(NeoBankEnvironment.getStellarBTCxCode())) {
							sourceAssetIssuer = (String)TDAManagementDao.class.getConstructor().newInstance().getBTCxIssuingAccount();
						}
						
						if(destionAssetCode.equals(NeoBankEnvironment.getPorteTokenCode())) {
							destinationIssuier = (String)CustomerDigitalAssetsDao.class.getConstructor()
									.newInstance().getIssueingAccountPublicKey(destionAssetCode);
						}else if(destionAssetCode.equals(NeoBankEnvironment.getXLMCode())) {
							destinationIssuier = (String)CustomerDigitalAssetsDao.class.getConstructor()
									.newInstance().getIssueingAccountPublicKey(destionAssetCode);
						}else if(destionAssetCode.equals(NeoBankEnvironment.getVesselCoinCode())) {
							destinationIssuier = (String)CustomerDigitalAssetsDao.class.getConstructor()
									.newInstance().getIssueingAccountPublicKey(destionAssetCode);
						}else if(destionAssetCode.equals(NeoBankEnvironment.getUSDCCode())) {
							destinationIssuier = (String)CustomerDigitalAssetsDao.class.getConstructor()
									.newInstance().getIssueingAccountPublicKey(destionAssetCode);
						}else if(destionAssetCode.equals(NeoBankEnvironment.getStellarBTCxCode())) {
							destinationIssuier = (String)TDAManagementDao.class.getConstructor().newInstance().getBTCxIssuingAccount();
						}
											
						NeoBankEnvironment.setComment(3, className, "inside sell_porte_coin_mbl sourceAssetCode "+sourceAssetCode
								+" destionAssetCode  "+ destionAssetCode + " sourceAssetIssuer "+ sourceAssetIssuer + " destinationIssuier "+ destinationIssuier
								+ " sourceAmount "+ sourceAmount + " destMinAmount "+destMinAmount +" privateKey "+ privateKey);
					
						String result = StellarSDKUtility.pathPaymentStrictSend(sourceAssetCode, destionAssetCode, sourceAssetIssuer, destinationIssuier,
								destMinAmount, sourceAmount, privateKey);
						
						if(result.equals("success")) {
							
							NeoBankEnvironment.setComment(3, className,"========== Payment success on Stellar " + java.time.LocalTime.now() );
							moduleCode="TEP";// TDA Exchange Porte Assets to BTCX
							SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId,"T", moduleCode,"TDA Exchange Porte Assets to BTCX" );
							obj.addProperty("error","false"); 
							obj.addProperty("message", "You've successfuly swapped "+sourceAmount+" "+ sourceAssetCode+ " to "+destMinAmount+" "+ destionAssetCode); 
					
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
							if (output != null)output.close(); 
							if (obj != null)obj = null;if (destinationIssuier != null)destinationIssuier = null;
							if (sourceAssetCode != null)sourceAssetCode = null; 
							if (destionAssetCode != null)destionAssetCode = null;
							if (sourceAssetIssuer != null)sourceAssetIssuer = null; 
							if (privateKey != null)privateKey = null; if (user != null)user = null; 
							if (moduleCode!=null)moduleCode=null; if (userId!=null)userId=null;
						}								
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "Exchange Assets Failed, Try again");
					}
					break;
					
				case "addbtcaddress":
					try {
						JsonObject obj = new JsonObject(); PrintWriter output = null; String btcAddress=null; 
						String status=null;User user = null; boolean success=false; String assetCode=null; String userId=null;
						String moduleCode=null; 
						
						if(request.getParameter("btcaddress")!=null)	 btcAddress = request.getParameter("btcaddress").trim();
						if(request.getParameter("seladdstatus")!=null)	 status = request.getParameter("seladdstatus").trim();
						if(request.getParameter("seladdassetcode")!=null)	 assetCode = request.getParameter("seladdassetcode").trim();
							
						success=(boolean)TDAManagementDao.class.getConstructor().newInstance().saveBTCAddress(btcAddress,status, assetCode);
						user=(User) session.getAttribute("SESS_USER");
						userId=user.getUserId();
						
						if (success) {
							// Add Audit trail
							moduleCode="TBA";// TDA Adding Bitcoin Address
							SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId,"T", moduleCode,"TDA added BTC address" );
							obj.addProperty("error", "false");
							obj.addProperty("message", "BTC Address "+btcAddress+" added successfully.");
						}else {
							obj.addProperty("error", "true");
							obj.addProperty("message", "Failed to add BTC address");
						}
						try {
							output=response.getWriter();
							output.print(obj);
						}finally {
							if (output != null)output.close();if (obj != null)obj = null;
							if (btcAddress!=null) btcAddress=null; if (userId!=null) userId=null;
							if (moduleCode!=null) moduleCode=null; if (assetCode!=null) assetCode=null;
						}
					}catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "Adding BTC Address Failed, Try again");
					}
				break;
				case "editbtcaddress":
					try {
						JsonObject obj = new JsonObject(); PrintWriter output = null; 
						String status=null;User user = null; boolean success=false; String assetCode=null; String userId=null;
						String moduleCode=null;  String btcAddress=null;
						
						if(request.getParameter("selleditstatus")!=null)	 status = request.getParameter("selleditstatus").trim();
						if(request.getParameter("editassetcode")!=null)	 assetCode = request.getParameter("editassetcode").trim();
						if(request.getParameter("editbtcaddress")!=null)	 btcAddress = request.getParameter("editbtcaddress").trim();
						
						
						success=(boolean)TDAManagementDao.class.getConstructor().newInstance().editBTCAddress(assetCode, status, btcAddress);
						user=(User) session.getAttribute("SESS_USER");
						userId=user.getUserId();
						
						if (success) {
							// Add Audit trail
							moduleCode="TUB";// TDA Updated Bitcoin Address
							SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId,"T", moduleCode,"TDA updated Bitcoin Address status" );
							obj.addProperty("error", "false");
							obj.addProperty("message", "BTC Address status updated successfully.");
						}else {
							obj.addProperty("error", "true");
							obj.addProperty("message", "Failed to update BTC status");
						}
						try {
							output=response.getWriter();
							output.print(obj);
						}finally {
							if (output != null)output.close();if (obj != null)obj = null;
							if (userId!=null) userId=null;if (moduleCode!=null) moduleCode=null;
							if (assetCode!=null) assetCode=null; if (btcAddress!=null)btcAddress=null;
						}
					}catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "Updating BTC Address Status Failed, Try again");
					}
					break;
					
				case "approve_fiat_to_btc_request":
					try {
						String custRelNo=null; String receiverAddress=null; 
						String txnAmount=null; String coinAmount=null; 
						String assetCode=null; String tdaAccountPrivateKey=null; 
						PrintWriter out_json = response.getWriter();  JsonObject obj = new JsonObject();
						boolean success=false;  String extSystemRef = "";String comment=null;
						String responseStatus = null; User user = null;
						String txnCode = null; String [] splitResponse=null; 
						String customerUserId=null;
						String tdaAccountPubKey=null; String tdaAddress=null;
						ArrayList<BitcoinDetails> arrBitcoinDetails=null;
						JsonObject createBTCTransactionObj=null;
						JsonObject signBTCTransactionObj=null;
						String subject=null; String content=null;
						Customer c_Details=null;
						
						if(request.getParameter("relno")!=null)	custRelNo = request.getParameter("relno").trim();
						if(request.getParameter("receiveraddress")!=null)	receiverAddress = request.getParameter("receiveraddress").trim();
						if(request.getParameter("txnamount")!=null)	txnAmount = request.getParameter("txnamount").trim();
						if(request.getParameter("coinamount")!=null) coinAmount = request.getParameter("coinamount").trim();
						if(request.getParameter("assetcode")!=null)	assetCode = request.getParameter("assetcode").trim();
						if(request.getParameter("privatekey")!=null) tdaAccountPrivateKey = request.getParameter("privatekey").trim();
						if(request.getParameter("txncode")!=null) txnCode = request.getParameter("txncode").trim();
						if(request.getParameter("comment")!=null) comment = request.getParameter("comment").trim();
						if(request.getParameter("customerid")!=null) customerUserId = request.getParameter("customerid").trim();
						if(request.getParameter("pubkey")!=null) tdaAccountPubKey = request.getParameter("pubkey").trim();
						
					
	
						 if (session.getAttribute("SESS_USER") == null) 
								throw new Exception ("Session has expired, please log in again");
						 user = (User) session.getAttribute("SESS_USER");
						 
						 NeoBankEnvironment.setComment(3,className," txnCode is "+txnCode);

						arrBitcoinDetails = (ArrayList<BitcoinDetails>)TDAManagementDao.class.getConstructor().newInstance().getBTCAddressDetails();
						
						for (int i=0;i<arrBitcoinDetails.size();i++) {
							tdaAddress=((BitcoinDetails)arrBitcoinDetails.get(i)).getAddress();
						}
						
						createBTCTransactionObj = BitcoinUtility.createTransaction(tdaAddress, receiverAddress,coinAmount);
						signBTCTransactionObj = BitcoinUtility.signTransaction(createBTCTransactionObj, tdaAccountPrivateKey, tdaAccountPubKey);
						extSystemRef = signBTCTransactionObj.get("tx").getAsJsonObject().get("hash").getAsString(); //Get BTC TXN hash
							
		
							success= (boolean)OpsManageCryptoDao.class.getConstructor().newInstance().updateCustomerDetails(txnCode, extSystemRef);
								if(success) {
									 // Module Code:- TAB-  TDA Approve BTC Request 
									SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(user.getUserId(),"C", "TAB","Approved Fiat to BTC transaction" );
									obj.addProperty("error", "false"); 
					        		obj.addProperty("message", "Transaction approved: "+coinAmount+" "+assetCode+" has been transfered to "+customerUserId); 
					        		
					        		subject="YOU HAVE RECEIVED "+assetCode+" IN YOUR WALLET!";
									 content="Your purchase of "+coinAmount+" "+assetCode+" has been sent you.";
									// Send to user
									 c_Details=(Customer)CustomerDao.class.getConstructor().newInstance().getFullCustomerProfile(custRelNo);
									 
									String sendto = c_Details.getUnmaskedEmail();
									String sendSubject = subject;
									String sendContent = content;
									String customerName = c_Details.getName();
										
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
								
								}else {
									obj.addProperty("error", "true"); 
					        		obj.addProperty("message", "Transaction failed");
								}
						
						try {
							out_json = response.getWriter();
							NeoBankEnvironment.setComment(3, className, rulesaction+"Obj String is "+obj.toString());
							out_json.print(obj);
						}finally {
							 if(coinAmount!=null) coinAmount=null; if (comment!=null)comment=null;
							if(custRelNo!=null) custRelNo=null; if(tdaAddress!=null) tdaAddress=null;
							if(assetCode!=null) assetCode=null; if(tdaAccountPubKey!=null) tdaAccountPubKey=null;
							if(obj!=null) obj = null; if (customerUserId!=null)customerUserId=null; if (splitResponse!=null)splitResponse=null;
							if (responseStatus!=null)responseStatus=null; if (tdaAccountPrivateKey!=null) tdaAccountPrivateKey=null;
							if(txnCode!=null) txnCode = null; if (txnAmount!=null)txnAmount=null;
							if(out_json!=null) out_json.close(); if (receiverAddress!=null)receiverAddress=null;
							if (arrBitcoinDetails!=null)arrBitcoinDetails=null;
							if (c_Details!=null)c_Details=null; if (subject!=null);subject=null; if (content!=null);content=null;
						}
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "Error in approving BTC transaction, Please try again letter");
					}		
					break;
					
					
				case "getting_offers_from_stellar":
					try {
						
							JsonObject obj = new JsonObject(); PrintWriter output = null;
							String sourceAssetCode = null;Gson gson = new Gson();
							String currencyCode = null;
							String amountToSpent = null;
							String relationshipNo= null;
							String sourceAssetIssuer= null;
							String sourceAssetType= null;
							List<Asset> issuersList = null;
							List<PaymentOffer> offers = null;
							User user = null;
							if(request.getParameter("source_coin")!=null)	 sourceAssetCode = request.getParameter("source_coin").trim();
							if(request.getParameter("sel_currency")!=null)	 currencyCode = request.getParameter("sel_currency").trim();
							if(request.getParameter("amount_to_spend")!=null)	 amountToSpent = request.getParameter("amount_to_spend").trim();
							if(request.getParameter("relno")!=null)	 relationshipNo = request.getParameter("relno").trim();
							
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
							
							issuersList = CurrencyTradeUtility.getIssuersData(currencyCode);
							if(issuersList==null) 
								throw new Exception("Error in getting asset issuers");
							offers = CurrencyTradeUtility.getPathStrictSendWithDestinationAssets(issuersList, sourceAssetCode, 
									amountToSpent, sourceAssetType, sourceAssetIssuer);
							if(offers==null) 
								throw new Exception("Error in getting offers from the DEX");
							 offers = CurrencyTradeUtility.sortListByDestinationAmount(offers);
							 
							if(offers !=null) {
								obj.add("data", gson.toJsonTree(offers));
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
								if (obj != null)obj = null; if (sourceAssetCode != null)sourceAssetCode = null; if (currencyCode != null)currencyCode = null;
								if (offers != null)offers = null; if (amountToSpent != null)amountToSpent = null;if (sourceAssetIssuer != null)sourceAssetIssuer = null;
								if (user != null)user = null; 
								if (sourceAssetType != null)sourceAssetType = null; 
							}
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "Error in getting offers, Please try again letter");
					}
					break;
				case "tda_complete_remitance_txn":
					try {
						JsonObject obj = new JsonObject(); PrintWriter output = null;
						String exchangeAsset = NeoBankEnvironment.getUSDCCode();
						Gson gson = new Gson(); String amountUSD = null; 
						String patnerPublicKey= null; String txnHash = null; boolean succcess = false;
						String usdcPrivateKey= null; String systemRef = null;
						String comment = null; String currency=NeoBankEnvironment.getUSDCurrencyId();
						if(request.getParameter("amount_in_usd")!=null)	 amountUSD = request.getParameter("amount_in_usd").trim();
						if(request.getParameter("usdc_private_key")!=null)	 usdcPrivateKey = request.getParameter("usdc_private_key").trim();
						if(request.getParameter("partner_public_key")!=null)	 patnerPublicKey = request.getParameter("partner_public_key").trim();
						if(request.getParameter("sys_ref")!=null)	 systemRef = request.getParameter("sys_ref").trim();
						if(request.getParameter("txn_comment")!=null)	 comment = request.getParameter("txn_comment").trim();
						
						double usdcAmountDouble = (double) CustomerDigitalCurrenciesDao.class.getConstructor().newInstance().
								getAssetConversionRate(amountUSD, exchangeAsset,currency);
						DecimalFormat df_obj = new DecimalFormat("#.#######");
						KeyPair sourceAccount = KeyPair.fromSecretSeed(usdcPrivateKey);
						KeyPair destinationAccount = KeyPair.fromAccountId(patnerPublicKey);
						String issuerPublicKey = (String)CustomerDigitalAssetsDao.class.getConstructor() 
								.newInstance().getIssueingAccountPublicKey(exchangeAsset);
						String result = StellarSDKUtility.sendNativeCoinPayment(
								exchangeAsset, sourceAccount, destinationAccount, df_obj.format(usdcAmountDouble), "");
						if(result.split(",")[0].contains("success")) {
							txnHash = result.split(",")[1];
							succcess = (boolean)RemittanceDao.class.getConstructor() 
									.newInstance().tdaUpadteRemittanceTxn(systemRef, txnHash, comment);
						}
						if(succcess) {
							obj.add("error", gson.toJsonTree("false"));
							obj.add("message", gson.toJsonTree("Transaction Updated successfuly"));
						}else {
							obj.add("error", gson.toJsonTree("true"));
							obj.add("message", gson.toJsonTree("Transaction Update failed"));
						}
						try {
							NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
							output = response.getWriter();
							output.print(gson.toJson(obj));
						} finally {
							if (output != null)output.close(); if (exchangeAsset != null)exchangeAsset = null; if (gson != null)gson = null;
							if (obj != null)obj = null; if (amountUSD != null)amountUSD = null; if (usdcPrivateKey != null)usdcPrivateKey = null;
							if (txnHash != null)txnHash = null; if (systemRef != null)systemRef = null;if (comment != null)comment = null;
							if (df_obj != null)df_obj = null; if (sourceAccount != null)sourceAccount = null; if (destinationAccount != null)destinationAccount = null;
							if (issuerPublicKey != null)issuerPublicKey = null; if (result != null)result = null; if (currency!=null)currency=null;
						}
						
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "Error in completing transaction, Please try again letter");
					}
				break;
					
					
				case "get_usdc_to_transfer":
					try {
						JsonObject obj = new JsonObject(); PrintWriter output = null;
						String exchangeAsset = NeoBankEnvironment.getUSDCCode(); String currency=NeoBankEnvironment.getUSDCurrencyId();
						Gson gson = new Gson(); String amountUSD = null;  //In Destination Asset  String currency=NeoBankEnvironment.getUSDCurrencyId();
						if(request.getParameter("amount_in_usd")!=null)	 amountUSD = request.getParameter("amount_in_usd").trim();
						
						double usdcAmountDouble = (double) CustomerDigitalCurrenciesDao.class.getConstructor().newInstance().
								getAssetConversionRate(amountUSD, exchangeAsset,currency);
						DecimalFormat df_obj = new DecimalFormat("#.######");						
						try {
							obj.add("data", gson.toJsonTree("You are about to transfer "+df_obj.format(usdcAmountDouble)+" "+exchangeAsset));
							obj.add("error", gson.toJsonTree("false"));
							output = response.getWriter();
							output.print(gson.toJson(obj));
						}finally {
							if (output != null)output.close(); if (amountUSD != null)amountUSD = null; if (gson != null)gson = null;
							if (obj != null)obj = null; if (df_obj != null)df_obj = null; if (currency!=null)currency=null;
						}
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "Error getting USDC anount, Please try again letter");
					}
					break;
					
				case"tda_update_btc_prices_from_coingecko":
					try {
						NeoBankEnvironment.setComment(3, className,"Inside tda_update_btc_prices_from_coingecko");
						JsonObject obj = new JsonObject(); Gson gson = new Gson(); PrintWriter out_json = response.getWriter();
						double bitcoinRate = 0; 
						boolean success = false;
						String userId=((User) session.getAttribute("SESS_USER")).getUserId(); 
						String userType=((User) session.getAttribute("SESS_USER")).getUserType();
						//Call coin Gecko API
						JsonObject rateResponseObj = ExchangeRatesUtility.getBTCFiatExchangeRatesFromCoingecko();
						NeoBankEnvironment.setComment(3, className,"Step 1");
						NeoBankEnvironment.setComment(3, className, " rateResponseObj "+rateResponseObj );
						if(rateResponseObj.equals(null)) throw new Exception("Failed to get rates from Coingecko");
						bitcoinRate = rateResponseObj.get("bitcoin").getAsJsonObject().get("usd").getAsDouble();
						NeoBankEnvironment.setComment(3, className,"Step 2 bitcoinRate "+bitcoinRate);
						String[] assetsToUpdate = {NeoBankEnvironment.getBitcoinCode()};
						double [] assetRatess = {bitcoinRate};
						
						success = (boolean)SystemUtilsDao.class.getConstructor().newInstance().tdaUpdateAssetPricesFromCoingecko(assetsToUpdate, assetRatess);
						NeoBankEnvironment.setComment(3, className,"Step 3 ");
						if(success) {
							SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",
									StringUtils.substring("TDA  Asset Pricing From Coingecko", 0, 48));
							obj.add("message", gson.toJsonTree("BTC Pricing updated successful")); 
							obj.add("error", gson.toJsonTree("false")); 
						}else {
							obj.add("message", gson.toJsonTree("Error in updating BTC pricing")); 
							obj.add("error", gson.toJsonTree("false")); 
						}
						try {
							NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
							 out_json.print( gson.toJson(obj));
						}finally {
							if(obj!=null) obj=null;  if(gson!=null) gson=null; if(assetsToUpdate!=null) assetsToUpdate=null; 
							if(assetRatess!=null) assetRatess=null; 
							if(rateResponseObj!=null) rateResponseObj=null; if(userType!=null) userType=null; if(out_json!=null) out_json.close(); 
						}
						
						
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "TDA Updating BTC Pricing failed, Please try again letter");
					}
					
					
					break;
				case"tda_update_btcx_prices_from_coingecko":
					try {
						NeoBankEnvironment.setComment(3, className,"Inside tda_update_btc_prices_from_coingecko");
						JsonObject obj = new JsonObject(); Gson gson = new Gson(); PrintWriter out_json = response.getWriter();
						double bitcoinRate = 0; 
						boolean success = false;
						String userId=((User) session.getAttribute("SESS_USER")).getUserId(); 
						String userType=((User) session.getAttribute("SESS_USER")).getUserType();
						//Call coin Gecko API
						JsonObject rateResponseObj = ExchangeRatesUtility.getBTCFiatExchangeRatesFromCoingecko();
						NeoBankEnvironment.setComment(3, className,"Step 1");
						NeoBankEnvironment.setComment(3, className, " rateResponseObj "+rateResponseObj );
						if(rateResponseObj.equals(null)) throw new Exception("Failed to get rates from Coingecko");
						bitcoinRate = rateResponseObj.get("bitcoin").getAsJsonObject().get("usd").getAsDouble();
						NeoBankEnvironment.setComment(3, className,"Step 2 bitcoinRate "+bitcoinRate);
						String[] assetsToUpdate = {NeoBankEnvironment.getStellarBTCxCode()};
						double [] assetRatess = {bitcoinRate};
						
						success = (boolean)SystemUtilsDao.class.getConstructor().newInstance().tdaUpdateAssetPricesFromCoingecko(assetsToUpdate, assetRatess);
						NeoBankEnvironment.setComment(3, className,"Step 3 ");
						if(success) {
							SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",
									StringUtils.substring("TDA  Asset Pricing From Coingecko", 0, 48));
							obj.add("message", gson.toJsonTree("BTC Pricing updated successful")); 
							obj.add("error", gson.toJsonTree("false")); 
						}else {
							obj.add("message", gson.toJsonTree("Error in updating BTC pricing")); 
							obj.add("error", gson.toJsonTree("false")); 
						}
						try {
							NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
							 out_json.print( gson.toJson(obj));
						}finally {
							if(obj!=null) obj=null;  if(gson!=null) gson=null; if(assetsToUpdate!=null) assetsToUpdate=null; 
							if(assetRatess!=null) assetRatess=null; 
							if(rateResponseObj!=null) rateResponseObj=null; if(userType!=null) userType=null; if(out_json!=null) out_json.close(); 
						}
						
						
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "TDA Updating BTC Pricing failed, Please try again letter");
					}
					
					
					break;

		}	
	}

	@Override
	public void performOperation (String rulesaction, HttpServletRequest request, HttpServletResponse response,
	ServletContext ctx)
			throws Exception {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null)
			Utilities.callOpsException(request, response, ctx, "Session has expired, please log in again");
		switch (rulesaction) {
		
		case "TDA Dashboard":
			try {
				ArrayList<BitcoinDetails> arrBitcoinDetails=null;
				String bitcoinAddress=null;
				String createdOn=null; 
				String status=null;
				BitcoinDetails m_BitcoinDetails=null;
				arrBitcoinDetails=(ArrayList<BitcoinDetails>)TDAManagementDao.class.getConstructor().newInstance().getBTCAddressDetails();
				if (arrBitcoinDetails!=null) {
					int count =arrBitcoinDetails.size();
					for (int i=0; i<count;i++) {
						createdOn=((BitcoinDetails)arrBitcoinDetails.get(i)).getCreatedOn();
						status=((BitcoinDetails)arrBitcoinDetails.get(i)).getStatus();	
						bitcoinAddress=((BitcoinDetails)arrBitcoinDetails.get(i)).getAddress();
					}
					// Call BlockCypher API to get Address Balance
					m_BitcoinDetails= BitcoinUtility.getBTCAddressBalance(bitcoinAddress);
					m_BitcoinDetails.setStatus(status);
					m_BitcoinDetails.setCreatedOn(createdOn);
				}
				request.setAttribute("lastaction", "tdadash");
				request.setAttribute("lastrule", "TDA Dashboard");
				request.setAttribute("btcaccountdetails",m_BitcoinDetails);
				response.setContentType("text/html");
				try{
					ctx.getRequestDispatcher(NeoBankEnvironment.getTDABTCAccountInformationPage()).forward(request,response);			
				}finally {
					 if (arrBitcoinDetails!=null) arrBitcoinDetails=null; if (bitcoinAddress!=null)bitcoinAddress=null;
					 if (createdOn!=null)createdOn=null; if (status!=null)status=null; 
					 if (m_BitcoinDetails!=null) m_BitcoinDetails=null;
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());	
			}
		break;
	
		case "Purchase BTCx":
			try {
				request.setAttribute("lastaction", "tdaacct");	
				request.setAttribute("lastrule", "Purchase BTCx");
				User user = null;
				String relationshipNo= null;
				 if (session.getAttribute("SESS_USER") == null) 
						throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				relationshipNo = user.getRelationshipNo();
				String publicKey = (String)TDAManagementDao.class.getConstructor().newInstance().getBTCxDistributionAccount();
				KeyPair userAccount = null;
				NeoBankEnvironment.setComment(3, className, " Public key is  "+publicKey);
				ArrayList<AssetAccount> accountBalances = null;
				if(publicKey != null && publicKey != "") {
					userAccount = KeyPair.fromAccountId(publicKey);
					accountBalances = StellarSDKUtility.getAccountBalance(userAccount);
				}
				
				response.setContentType("text/html");
				request.setAttribute("externalwallets",accountBalances);
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getTDAPurchaseBtcxPage()).forward(request, response);
				} finally {
					if(relationshipNo != null) relationshipNo =null; if(user != null) user =null;
					if(publicKey != null) publicKey =null; if(userAccount != null) userAccount =null;
					if(accountBalances != null) accountBalances =null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		case "Configuration":
			try {
				//Get BTCx account to display 
				String btcxAccount =null; KeyPair userAccount = null; ArrayList<AssetAccount> accountBalances = null; String assetCode=null; 
				ArrayList<AssetAccount> arryAssetAccountDetails =null;
				
				btcxAccount= TDAManagementDao.class.getConstructor().newInstance().getBTCxDistributionAccount();
				NeoBankEnvironment.setComment(3, className,"Past here 1 "+btcxAccount );
				
				if (btcxAccount!=null ) {
					// Get BTCx balance from Stellar
					userAccount = KeyPair.fromAccountId(btcxAccount);
					accountBalances = StellarSDKUtility.getAccountBalance(userAccount);
				}
				NeoBankEnvironment.setComment(3, className,"Past here 2 " );
				assetCode=NeoBankEnvironment.getStellarBTCxCode();
				arryAssetAccountDetails =TDAManagementDao.class.getConstructor().newInstance().getBTCXAccountDetails(assetCode);
				NeoBankEnvironment.setComment(3, className,"Past here 3 " );
				request.setAttribute("lastaction", "tdaacct");	
				request.setAttribute("btcxaccount", btcxAccount);	
				request.setAttribute("accountbalances",accountBalances);
				request.setAttribute("arraccountdetails",arryAssetAccountDetails);
				request.setAttribute("lastrule", "Configuration");
				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getTDAAccountConfigurationPage()).forward(request, response);
				}
				finally {
					if (btcxAccount!=null) btcxAccount=null; if(userAccount != null)userAccount = null;
					if(accountBalances != null)accountBalances = null;  if (arryAssetAccountDetails!=null)arryAssetAccountDetails=null;
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		case "Fiat to BTCx":
			try {
				ArrayList<AssetCoin> arrBTCxPricing =null; String assetCode=null;
				assetCode=NeoBankEnvironment.getStellarBTCxCode();
				arrBTCxPricing = (ArrayList<AssetCoin>) TDAManagementDao.class .getConstructor().newInstance().getFiatToAssetPricing(assetCode);
				ArrayList<CryptoAssetCoins> arrAssets = (ArrayList<CryptoAssetCoins>) TDAManagementDao.class .getConstructor().newInstance().
						getPorteAssetDetails(assetCode);
				request.setAttribute("assets", arrAssets);
				request.setAttribute("lastaction", "tdaprcing");	
				request.setAttribute("lastrule", "Fiat to BTCx");
				request.setAttribute("btcxpricing", arrBTCxPricing);

				response.setContentType("text/html");
				try {
				ctx.getRequestDispatcher(NeoBankEnvironment.getTDAFiatToBTCxPricingPage()).forward(request, response);
				}finally {
					if (arrBTCxPricing!=null)arrBTCxPricing=null; if (assetCode!=null)assetCode=null;
					if (arrAssets!=null)arrAssets=null;
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		case "Porte Asset to BTCx":
			try {
				ArrayList<AssetCoin> arrPorteAssetsToBTCxMarkUpRates =null; String assetCode=null;
				assetCode=NeoBankEnvironment.getStellarBTCxCode(); ArrayList<CryptoAssetCoins> arrAssets=null;
				arrPorteAssetsToBTCxMarkUpRates = (ArrayList<AssetCoin>) TDAManagementDao.class .getConstructor().newInstance().getPorteAssetsMarkUpRates();
				arrAssets = (ArrayList<CryptoAssetCoins>) CustomerPorteCoinDao.class .getConstructor().newInstance().getPorteAssetDetails();
				
				request.setAttribute("lastaction", "tdaprcing");	
				request.setAttribute("lastrule", "Porte Asset to BTCx");
				request.setAttribute("porteassetsmarkuprates", arrPorteAssetsToBTCxMarkUpRates);
				request.setAttribute("assets", arrAssets);

				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getTDAPorteAssetToBTCxPricingPage()).forward(request, response);
				}finally {
					if (arrPorteAssetsToBTCxMarkUpRates!=null)arrPorteAssetsToBTCxMarkUpRates=null; if (assetCode!=null)assetCode=null;
					if (arrAssets!=null) arrAssets=null;
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
				}
			break;
		
		case "Request from Fiat":
			try {
				List <Transaction> btcxRequestTransactions=null;
				btcxRequestTransactions=(List<Transaction>) TDAManagementDao.class.getConstructor().newInstance().getBTCxFromFiatRequests();
				request.setAttribute("lastaction", "tdarqst");	
				request.setAttribute("lastrule", "Request from Fiat");
				request.setAttribute("fiattobtctransactions", btcxRequestTransactions);
	
				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getTDARequestFromFiatPage()).forward(request, response);
				}finally {
					if (btcxRequestTransactions!=null)btcxRequestTransactions=null;
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());				
			}
			break;
			
		case "Request from Porte Assets":
			try {
				ArrayList <Transaction> porteAssetsToBtcxRequestTransactions=null;
				porteAssetsToBtcxRequestTransactions=(ArrayList<Transaction>) TDAManagementDao.class.getConstructor().newInstance().getBTCxFromPorteAssetsRequests();
				request.setAttribute("lastaction", "tdarqst");	
				request.setAttribute("lastrule", "Request from Porte Assets");
				request.setAttribute("porteassetestobtctransactions", porteAssetsToBtcxRequestTransactions);
				response.setContentType("text/html");
				try {
				ctx.getRequestDispatcher(NeoBankEnvironment.getTDARequestFromPorteAssetsPage()).forward(request, response);
				}finally {
					if (porteAssetsToBtcxRequestTransactions!=null)porteAssetsToBtcxRequestTransactions=null;
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());				
			}
			break;
		case "Display Stellar Transactions":
			try {
				request.setAttribute("lastaction", "tdaacct");
				request.setAttribute("lastrule", "Display Stellar Transactions");
				response.setContentType("text/html");
				String relationshipNo= null;
				User user = null;
				 if (session.getAttribute("SESS_USER") == null) 
						throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				String publicKey = (String)TDAManagementDao.class.getConstructor().newInstance().getBTCxDistributionAccount();
				NeoBankEnvironment.setComment(3, className, " Public key is  "+publicKey);
				KeyPair userAccount = null;
				String limit = NeoBankEnvironment.getStellarTdaTransactionLimit();
				ArrayList<AssetTransactions> assetTransactions = null;
				if(publicKey != null && publicKey != "") {
					userAccount = KeyPair.fromAccountId(publicKey);
					assetTransactions = StellarSDKUtility.getAccountTransactions(publicKey, limit);
				}
				request.setAttribute("assetTransactions",assetTransactions);
				request.setAttribute("publickey",publicKey);
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getTDADisplayStellarTransactionsPage()).forward(request, response);
				}finally {
					if(relationshipNo != null) relationshipNo =null; if(user != null) user =null;
					if(publicKey != null) publicKey =null; if(userAccount != null) userAccount =null;
					if(assetTransactions != null) assetTransactions =null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());			}
			break;
			
		case "Account Information":
			try {
				
				ArrayList<BitcoinDetails> arrBitcoinDetails=null;
				String bitcoinAddress=null;
				String createdOn=null; 
				String status=null;
				BitcoinDetails m_BitcoinDetails=null;
				arrBitcoinDetails=(ArrayList<BitcoinDetails>)TDAManagementDao.class.getConstructor().newInstance().getBTCAddressDetails();
				if (arrBitcoinDetails!=null) {
					int count =arrBitcoinDetails.size();
					for (int i=0; i<count;i++) {
						createdOn=((BitcoinDetails)arrBitcoinDetails.get(i)).getCreatedOn();
						status=((BitcoinDetails)arrBitcoinDetails.get(i)).getStatus();	
						bitcoinAddress=((BitcoinDetails)arrBitcoinDetails.get(i)).getAddress();
					}
					// Call BlockCypher API to get Address Balance
					m_BitcoinDetails= BitcoinUtility.getBTCAddressBalance(bitcoinAddress);
					m_BitcoinDetails.setStatus(status);
					m_BitcoinDetails.setCreatedOn(createdOn);
				}
				request.setAttribute("lastaction", "tdabtc");
				request.setAttribute("lastrule", "Account Information");
				request.setAttribute("btcaccountdetails",m_BitcoinDetails);
				response.setContentType("text/html");
				try{
					ctx.getRequestDispatcher(NeoBankEnvironment.getTDABTCAccountInformationPage()).forward(request,response);			
				}finally {
					 if (arrBitcoinDetails!=null) arrBitcoinDetails=null; if (bitcoinAddress!=null)bitcoinAddress=null;
					 if (createdOn!=null)createdOn=null; if (status!=null)status=null; 
					 if (m_BitcoinDetails!=null) m_BitcoinDetails=null;
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());	
			}
		break;	
		case"Fiat to BTC Rates":
			try {
				ArrayList<AssetCoin> arrBTCPricing =null; String assetCode=null;
				assetCode=NeoBankEnvironment.getBitcoinCode();
				arrBTCPricing = (ArrayList<AssetCoin>) TDAManagementDao.class .getConstructor().newInstance().
						getFiatToAssetPricing(assetCode);
				ArrayList<CryptoAssetCoins> arrAssets = (ArrayList<CryptoAssetCoins>) TDAManagementDao.class .getConstructor().newInstance().
						getPorteAssetDetails(assetCode);
				request.setAttribute("assets", arrAssets);
				request.setAttribute("lastaction", "tdabtc");	
				request.setAttribute("lastrule", "Fiat to BTC Rates");
				request.setAttribute("btcpricing", arrBTCPricing);
				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getTDAFiatToBTCPricingPage()).forward(request, response);
				}finally {
					if (arrBTCPricing!=null)arrBTCPricing=null; if (assetCode!=null)assetCode=null;
					if (arrAssets!=null)arrAssets=null;
				}
				
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());	
			}
		break;
		case"BTC to BTCx Rates":
			try {
				ArrayList<AssetCoin> arrAssetExchangePricing =null; String sourceAssetCode=null; String destinationCode=null;
				sourceAssetCode=NeoBankEnvironment.getBitcoinCode();
				destinationCode=NeoBankEnvironment.getStellarBTCxCode();
				arrAssetExchangePricing = (ArrayList<AssetCoin>) TDAManagementDao.class .getConstructor().newInstance().getAssetExchange(sourceAssetCode,destinationCode);
				
				request.setAttribute("lastaction", "tdabtc");	
				request.setAttribute("lastrule", "BTC to BTCx Rates");
				request.setAttribute("assetexchangepricing", arrAssetExchangePricing);
				response.setContentType("text/html");
				try {
				ctx.getRequestDispatcher(NeoBankEnvironment.getTDABitcoinToStellarBitcoinPricingPage()).forward(request, response);
				}finally {
					if (arrAssetExchangePricing!=null)arrAssetExchangePricing=null; if (destinationCode!=null)destinationCode=null;
					if (sourceAssetCode!=null)sourceAssetCode=null;
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());	
			}
		break;
		case"View Fiat to BTC Requests":
			try {
				ArrayList <Transaction> btcRequestTransactions=null;
				btcRequestTransactions=(ArrayList<Transaction>) TDAManagementDao.class.getConstructor().newInstance().getFiatToBTCRequests();
				request.setAttribute("lastaction", "tdabtc");	
				request.setAttribute("lastrule", "View Fiat to BTC Requests");
				request.setAttribute("fiattobtctransactions", btcRequestTransactions);
				response.setContentType("text/html");
				
			try {
				ctx.getRequestDispatcher(NeoBankEnvironment.getTDAFiatToBTCRequestPage()).forward(request, response);
				}finally {
					if (btcRequestTransactions!=null) btcRequestTransactions=null;
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());	
			}
		break;
		case"Request from BTC":
			try {
				ArrayList <Transaction> btcToBTCXRequestTransactions=null;
				btcToBTCXRequestTransactions=(ArrayList<Transaction>) TDAManagementDao.class.getConstructor().newInstance().getBTCTOBTCXRequests();
				request.setAttribute("lastaction", "tdarqst");	
				request.setAttribute("lastrule", "Request from BTC");
				request.setAttribute("btctobtcxtransactions", btcToBTCXRequestTransactions);
				response.setContentType("text/html");
			try {
				ctx.getRequestDispatcher(NeoBankEnvironment.getTDABitcoinToStellarBitcoinRequestPage()).forward(request, response);
				}finally {
					if (btcToBTCXRequestTransactions!=null)btcToBTCXRequestTransactions=null;
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());	
			}
			break;
		case"Display BTC Transactions":
			try {
				// Call 
				ArrayList<BTCTransction>  arrBitcoinTransactions=null;
				ArrayList<BitcoinDetails> arrBitcoinAccount=null;
				String bitcoinAddress=null; JsonObject txnObj=null;String limit=null;
				arrBitcoinAccount=(ArrayList<BitcoinDetails>)TDAManagementDao.class.getConstructor().newInstance().getBTCAddressDetails();
				if (arrBitcoinAccount!=null) {
					int count =arrBitcoinAccount.size();
					for (int i=0; i<count;i++) {	
						bitcoinAddress=(String)arrBitcoinAccount.get(i).getAddress();	
					}
					limit="100";
					// Call BlockCypher API to get Address Transactions
					txnObj = BitcoinUtility.getBTCAccountTxn(bitcoinAddress, limit);
					arrBitcoinTransactions = BitcoinUtility.processTrnsactions(txnObj, bitcoinAddress);
				}
				request.setAttribute("lastaction", "tdabtc");
				request.setAttribute("lastrule", "Display BTC Transactions");
				request.setAttribute("btcaccounttxn",arrBitcoinTransactions);
				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getTDADisplayBitcoinTransactionPage()).forward(request, response);
				}finally {
					if (arrBitcoinTransactions!=null)arrBitcoinTransactions=null; if (bitcoinAddress!=null)bitcoinAddress=null;
					if (arrBitcoinAccount!=null)arrBitcoinAccount=null; if(txnObj != null) txnObj =null; if (limit!=null)limit=null;
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());	
			}
			break;
			
		case "Fiat Remittance Request":
			try {
				NeoBankEnvironment.setComment(3, className, "Step 1 ");
				 String currency=NeoBankEnvironment.getUSDCurrencyId();
				String subject=null; String content=null;
				String usdcDistributionAccount = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance()
						.getDistributionAccountPublicKey(NeoBankEnvironment.getUSDCCode());
				NeoBankEnvironment.setComment(3, className, "Step 2 "+usdcDistributionAccount);
				KeyPair keyPair = KeyPair.fromAccountId(usdcDistributionAccount);
				ArrayList<AssetAccount> accountBalance = StellarSDKUtility.getAccountBalance(keyPair);
				String usdBalance = null;
				for (int i = 0; i < accountBalance.size(); i++) {
					if(accountBalance.get(i).getAssetCode().equals(NeoBankEnvironment.getUSDCCode())) {
						usdBalance = accountBalance.get(i).getAssetBalance();
					}
				}
				NeoBankEnvironment.setComment(3, className, "Step 3 "+usdBalance);
				String hasEnoughXLM = ""; 
				String totalSumRequested =  (String)RemittanceDao.class.getConstructor().newInstance()
						.totalAmountOfUnProcessedRemit();
				double totalSumRequestedUSDC = (double) CustomerDigitalCurrenciesDao.class.getConstructor().newInstance().
						getAssetConversionRate(totalSumRequested, NeoBankEnvironment.getUSDCCode(),currency);
				NeoBankEnvironment.setComment(3, className, "Step usdBalance "+usdBalance);
				NeoBankEnvironment.setComment(3, className, "Step totalSumRequestedUSDC "+totalSumRequestedUSDC);
				List<Transaction> pendingTransactions = (List<Transaction>) RemittanceDao.class.getConstructor().newInstance().
						getTDAPendingTransactions();
				if(Double.parseDouble(usdBalance)<totalSumRequestedUSDC) {
					hasEnoughXLM = "false";
					subject="INSUFFICIENT USDC BALANCE!";
					 content="The current USDC Balance is not enough to perform transactions in the system. The current balance"
					 		+ " is :- "+usdBalance+" "+NeoBankEnvironment.getUSDCCode()+" while the required USDC is "+totalSumRequestedUSDC+System.lineSeparator()+
					 		"Please have it to up";
					// Send to user
					String sendto = NeoBankEnvironment.getAdminEmailAddressNotifier();
					String sendSubject = subject;
					String sendContent = content;
					String customerName = " Admin";
					
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
					
				}else {
					hasEnoughXLM = "true";
				}
				NeoBankEnvironment.setComment(3, className, "Step 4 "+hasEnoughXLM);
				request.setAttribute("lastaction", "tdarmt");
				request.setAttribute("lastrule", "Fiat Remittance Request");
				request.setAttribute("usdc_balance", usdBalance);
				request.setAttribute("has_enough_xlm", hasEnoughXLM);
				request.setAttribute("transactions", pendingTransactions);
				response.setContentType("text/html");
				
				 try {
					 NeoBankEnvironment.setComment(3, className, "Finaly "); 
						ctx.getRequestDispatcher(NeoBankEnvironment.getTDADisplayFiatRemittanceTxnPage()).forward(request, response);
				 } finally {
					 if(usdcDistributionAccount!=null)usdcDistributionAccount = null;  if(keyPair!=null)keyPair = null;
					 if(accountBalance!=null)accountBalance = null;  if(usdBalance!=null)usdBalance = null;
					 if(hasEnoughXLM!=null)hasEnoughXLM = null;  if(totalSumRequested!=null)totalSumRequested = null;
					 if(pendingTransactions!=null)pendingTransactions=null; if (currency!=null)currency=null;

				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());	
			}
			
			break;
			
		
			
		}	
	}

}
