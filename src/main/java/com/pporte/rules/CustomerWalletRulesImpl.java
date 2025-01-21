package com.pporte.rules;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.digest.DigestUtils;
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
import com.pporte.dao.CustomerCryptoCoinDao;
import com.pporte.dao.CustomerDao;
import com.pporte.dao.CustomerDigitalAssetsDao;
import com.pporte.dao.CustomerFiatWalletDao;
import com.pporte.dao.CustomerWalletDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.dao.UserLoginDao;
import com.pporte.dao.WalletAuthorizationDao;
import com.pporte.model.AssetAccount;
import com.pporte.model.AssetCoin;
import com.pporte.model.CryptoAssetCoins;
import com.pporte.model.Customer;
import com.pporte.model.AssetTransaction;
import com.pporte.model.User;
import com.pporte.model.Wallet;
import com.pporte.utilities.Bip39Utility;
import com.pporte.utilities.StellarSDKUtility;
import com.pporte.utilities.Utilities;


import framework.v8.Rules;

public class CustomerWalletRulesImpl implements Rules{
	private static String className = CustomerWalletRulesImpl.class.getSimpleName();
	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response, ServletContext ctx)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null) Utilities.callException(request, response, ctx, "Session has expired, please log in again");
		switch (rulesaction) {
		
		case "View Wallet":
				try {
					request.setAttribute("lastaction", "wal");
					request.setAttribute("lastrule", "View Wallet");
					response.setContentType("text/html");
					User user = null;String langPref=null;
					Wallet wallet = null;
					String userType = "C";
					String relationshipNo= null;
					 
					if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
					 if (session.getAttribute("SESS_USER") == null) 
							throw new Exception ("Session has expired, please log in again");
					user = (User) session.getAttribute("SESS_USER");
					relationshipNo = user.getRelationshipNo();
					wallet= (Wallet)CustomerWalletDao.class.getConstructor().newInstance().getCustomerWalletDetails(relationshipNo,userType);
					
					request.setAttribute("wallet",wallet);
					request.setAttribute("langpref", langPref);
					try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerViewWalletPage()).forward(request, response);
					} finally {
						if(wallet!=null) wallet = null; 
						if(user!=null) user = null; 
						if(relationshipNo!=null) relationshipNo = null; 
					}
					
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
					Utilities.callException(request, response, ctx, e.getMessage());

				}
			break;	
			case "Create Wallet":
				try {
					request.setAttribute("lastaction", "porte");
					request.setAttribute("lastrule", "Create Wallet");
					request.setAttribute("assetcoins", (List<AssetCoin>) CustomerCryptoCoinDao.class.getConstructor()
							.newInstance().getExternalAssetCoins());
					response.setContentType("text/html");
					try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerCreateWalletPage()).forward(request, response);
					} finally {
						
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
					Utilities.callException(request, response, ctx, e.getMessage());
				}
				break;	
			case "Topup Wallet":
				try {
					String langPref=null;
					if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
					request.setAttribute("lastaction", "wal");
					request.setAttribute("lastrule", "Topup Wallet");
					response.setContentType("text/html");
					request.setAttribute("langpref", langPref);
					try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerTopupWalletPage()).forward(request, response);
					} finally {
						if (langPref!=null) langPref=null;
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
					Utilities.callException(request, response, ctx, e.getMessage());
				}
				break;
			case "Send Money":
				try {
					String langPref=null;
					if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
					request.setAttribute("langpref", langPref);
					ArrayList<Customer> arrCustomerListReg = null; String custRelNo=null;
					custRelNo = ((User)session.getAttribute("SESS_USER")).getRelationshipNo();

					arrCustomerListReg = (ArrayList<Customer>) CustomerDao.class.getConstructor().newInstance()
							.getAllRegisteredWalletsForASenderWeb(custRelNo);
					

					request.setAttribute("lastaction", "wal");
					request.setAttribute("lastrule", "Send Money");
					response.setContentType("text/html");
					request.setAttribute("registeredusers", arrCustomerListReg);				
					try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerWalletPayAnyonePage()).forward(request, response);
					} finally {
						if (langPref!=null) langPref=null;
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
					Utilities.callException(request, response, ctx, e.getMessage());
				}
				break;
			/*case "Register Stellar Account":
				try {
					request.setAttribute("lastaction", "wal");
					request.setAttribute("lastrule", "Register Stellar Account");
					response.setContentType("text/html");
					try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerRegisterStellarAccountPage()).forward(request, response);
					} finally {
						
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
					Utilities.callException(request, response, ctx, e.getMessage());
				}
				
				break;*/
				
				
			case "Cash Transactions":
				try {
					String langPref=null;
					if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
					
					request.setAttribute("langpref", langPref.toUpperCase());
					request.setAttribute("lastaction", "wal");
					request.setAttribute("lastrule", "Cash Transactions");
					response.setContentType("text/html");
					try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerWalletCashTransactionsPage()).forward(request, response);
					} finally {
						if (langPref!=null) langPref=null;
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
					Utilities.callException(request, response, ctx, e.getMessage());
				}
				break;
			case"register_receiver_page":
				try {
					String langPref=null;
					if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
					request.setAttribute("langpref", langPref);
					request.setAttribute("lastaction", "wal");
					request.setAttribute("lastrule", "register_receiver_page");
					try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerRegisterReceiver()).forward(request, response);
					}finally {
						if (langPref!=null) langPref=null;
					}
				}catch(Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
					Utilities.callException(request, response, ctx, e.getMessage());
				}
				break;
			case"Register Stellar Account":
				try {
					request.setAttribute("lastaction", "porte");
					request.setAttribute("lastrule", "Register Stellar Account");
					try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerRegisterStellarAccountPage()).forward(request, response);
					}finally {
						
					}
				}catch(Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
					Utilities.callException(request, response, ctx, e.getMessage());
				}
				break;
		 }		
	}

	@Override
	public void performJSONOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		
		
	}

	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx)throws Exception{
	
		 switch (rulesaction) {
			 case "getwalletdetails":
				  try {

					String relationshipNo=null;  Wallet  m_Wallet= null; String userType=null;
					PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject(); 
					List <AssetTransaction> lastFiveTransations=null; List<Wallet> wallets=new ArrayList<Wallet>();
					//TODO check if session is null, this rule used in web only
					HttpSession session = request.getSession(false); 
					if (session.getAttribute("SESS_USER") == null) {
						Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
						return;
					}
					 
					if(request.getParameter("relno")!=null)				relationshipNo = request.getParameter("relno").trim();
					userType="C";
					
					m_Wallet= (Wallet)CustomerWalletDao.class.getConstructor().newInstance().getCustomerWalletDetails(relationshipNo,userType);
					
					if (m_Wallet!=null) {
						wallets.add(m_Wallet);
					}
					// Get last five transaction
					lastFiveTransations= (List<AssetTransaction>)CustomerWalletDao.class.getConstructor().newInstance().getFiatWalletLastTenTxn(relationshipNo);
					
					String publicKey = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
					
					KeyPair userAccount = null;
					ArrayList<AssetAccount> accountBalances = null;
					NeoBankEnvironment.setComment(3, className, " Public key is  "+publicKey);
					if(publicKey != null && publicKey != "") {
						userAccount = KeyPair.fromAccountId(publicKey);
						accountBalances = StellarSDKUtility.getAccountBalance(userAccount);
						
					} else {
						//Initalizing an empty list so that it doesn't throw a null error on the frontend
						accountBalances = new ArrayList<AssetAccount>();
					}
					if (m_Wallet!=null) {
						obj.add("error", gson.toJsonTree("false"));
						obj.add("data", gson.toJsonTree(wallets));
						obj.add("external_wallets", gson.toJsonTree(accountBalances));
						obj.add("publickey", gson.toJsonTree(publicKey));
						if (lastFiveTransations!=null) {
							obj.add("transactions", gson.toJsonTree(lastFiveTransations));
						}
						
					}else {
						obj.add("error", gson.toJsonTree("true"));
						obj.add("message", gson.toJsonTree("nodata"));
					}
					
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						out_json.print(gson.toJson(obj));
					} finally {
						if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null; 
						if(accountBalances!=null) accountBalances=null; if (lastFiveTransations!=null)lastFiveTransations=null; if(wallets!=null)wallets=null;
						if (userType != null)userType = null; if (obj != null)obj = null; if(m_Wallet!=null) m_Wallet=null; if(publicKey!=null) publicKey = null;
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				}
				break;
			 case "getwalletdetails_mbl":
				 try {
					 String tokenValue = null;
					 String relationshipNo=null;  Wallet  m_Wallet= null; String userType=null;
					 PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject(); 
					 List <AssetTransaction> lastFiveTransations=null;  String currentBalance="0.00";
					 String lastTransactionAmount=null; String lastTransactionTxnMode=null; Double previousWalletBalance=null;
					 Double percentageChange=0.00;  String concactpercentageChange=null;userType="C";String langPref=null;

					 if(request.getParameter("relno")!=null)				relationshipNo = request.getParameter("relno").trim();
					 
					 if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
					 if(request.getParameter("hdnlangpref")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlangpref"));
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
					 
					 m_Wallet= (Wallet)CustomerWalletDao.class.getConstructor().newInstance().getCustomerWalletDetails(relationshipNo,userType);
					 currentBalance=m_Wallet.getCurrentBalance().replaceAll(",", "");
					 // Get last five transaction
					 lastFiveTransations= (List<AssetTransaction>)CustomerWalletDao.class.getConstructor().newInstance().getFiatWalletLastTenTxn(relationshipNo);
					 // TODO:- Revisit this algorithm of getting percentage change
					 if (lastFiveTransations!=null) {
						 for (int i=0; i<lastFiveTransations.size();i++) {
							 NeoBankEnvironment.setComment(3, className, " Inside here "+lastFiveTransations.size());
							 if (i == (lastFiveTransations.size()-1)) {
								 NeoBankEnvironment.setComment(3, className, " Inside here ");
								 // ((Statements) arrMiniStatement.get(i)).getTxnAmount();
								 lastTransactionAmount= (String)lastFiveTransations.get(i).getTxnAmount().replaceAll(",", "");
								 lastTransactionTxnMode= (String)lastFiveTransations.get(i).getTxnMode(); 
							 }
						 } 
						 if (lastTransactionTxnMode!=null) {
							 if (lastTransactionTxnMode.equals("C") ) {
								 previousWalletBalance= Double.valueOf(currentBalance)-Double.valueOf(lastTransactionAmount);
								 
							 }else if (lastTransactionTxnMode.equals("D")) {
								 previousWalletBalance= Double.valueOf(currentBalance)+Double.valueOf(lastTransactionAmount);
								
							 }
							 
							 percentageChange = ((1 - previousWalletBalance / Double.valueOf(currentBalance))) * 100;  
						 }
					
					 }
					 
					 String publicKey = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
					 
					 KeyPair userAccount = null;
					 ArrayList<AssetAccount> accountBalances = null;
					 NeoBankEnvironment.setComment(3, className, " Public key is  "+publicKey);
					 if(publicKey != null && publicKey != "") {
						 userAccount = KeyPair.fromAccountId(publicKey);
						 accountBalances = StellarSDKUtility.getAccountBalance(userAccount);
						 
					 } else {
						 //Initalizing an empty list so that it doesn't throw a null error on the frontend
						 accountBalances = new ArrayList<AssetAccount>();
					 }
					 DecimalFormat df= new DecimalFormat("0.00");
					 if(!Double.toString(percentageChange).startsWith("-")){
						 concactpercentageChange= "+"+" "+df.format(percentageChange).toString(); 
					 }else {
						 concactpercentageChange=df.format(percentageChange).toString();
					 }
					
					 NeoBankEnvironment.setComment(3, className,"Concat string is "+concactpercentageChange);
						 obj.add("error", gson.toJsonTree("false"));
						 obj.add("data", gson.toJsonTree(m_Wallet));
						 obj.add("external_wallets", gson.toJsonTree(accountBalances));
						 obj.add("publickey", gson.toJsonTree(publicKey));
						 obj.add("percentagechange", gson.toJsonTree(concactpercentageChange));
						 if (lastFiveTransations!=null) {
							 obj.add("transactions", gson.toJsonTree(lastFiveTransations));
						 }
						 
					 
					 try {
						 NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						 out_json.print(gson.toJson(obj));
					 } finally {
						 if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null; 
						 if(accountBalances!=null) accountBalances=null; if (lastFiveTransations!=null)lastFiveTransations=null;
						 if (userType != null)userType = null; if (obj != null)obj = null; if(m_Wallet!=null) m_Wallet=null; if(publicKey!=null) publicKey = null;
						 if (currentBalance != null)currentBalance = null; if (lastTransactionAmount != null)lastTransactionAmount = null; if(lastTransactionTxnMode!=null) lastTransactionTxnMode=null; 
						 if(percentageChange!=null) percentageChange = null; if (tokenValue != null)tokenValue = null;
						 if(concactpercentageChange!=null) concactpercentageChange = null;

					 }
				 }catch (Exception e) {
					 NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					 Utilities.sendJsonResponse(response, "error", "Error in fetching wallet details, try again");
				 }
				 break;
				
			case "get_wallet_coin_details":
				try {
					JsonObject object = new JsonObject();
					Gson gson = new Gson();
					User user = null;
					String relationshipNo= null;
					HttpSession session = request.getSession(false);
					if (session.getAttribute("SESS_USER") == null) {
						Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
						return;
					}
					user = (User) session.getAttribute("SESS_USER");
					relationshipNo = user.getRelationshipNo();
					ConcurrentHashMap<String, String> hashWallets = (ConcurrentHashMap<String, String>)CustomerWalletDao.class.getConstructor()
							.newInstance().getCustomerWallets(relationshipNo);
					List<CryptoAssetCoins> arrCryptoAssetCoin = (List<CryptoAssetCoins>)CustomerWalletDao.class.getConstructor()
							.newInstance().getCryptoAssetPorteCoins();
					if(hashWallets.size() > 1) {
						for (int i = 0; i < arrCryptoAssetCoin.size(); i++) {
							if(hashWallets.contains(arrCryptoAssetCoin.get(i).getWalletType())) {
								arrCryptoAssetCoin.remove(i);
							}
						}
					}
					object.add("data", gson.toJsonTree(arrCryptoAssetCoin));
					try {
						response.getWriter().print(object);
					}finally {
						response.getWriter().close();
						if(object!=null) object = null;
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
			
			case "create_porte_coin_wallets":
				try {
					JsonObject obj = new JsonObject();
					User user = null; int baseFee  = org.stellar.sdk.Transaction.MIN_BASE_FEE;
					String relationshipNo= null; String limit = null;
					String walletDesc=null;
					Boolean success = false; 
					PrintWriter output = null; String assetCode =null;
					String privateKey =null;
					String publicKey = null;
					String issuerAccountId = null;
					boolean createTrustline = false;
					String hasMnemonic = null; String password  = null; String mnemoniStringFromDB  = null;
					boolean passIsCorrect = false; KeyPair keyPair = null;
					
					HttpSession session = request.getSession(false);
					if (session.getAttribute("SESS_USER") == null) {
						Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
						return;
					}
					user = (User) session.getAttribute("SESS_USER");
					relationshipNo = user.getRelationshipNo();
					
					if(request.getParameter("hasMnemonic")!=null)	 hasMnemonic = request.getParameter("hasMnemonic").trim();
					NeoBankEnvironment.setComment(3, className, "hasMnemonic "+hasMnemonic);
					if(hasMnemonic.equals("true")) {
						if(request.getParameter("security")!=null)	 password = request.getParameter("security").trim();
						//NeoBankEnvironment.setComment(3, className, "password is  "+password+"Relationship number "+relationshipNo);
						passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
								.checkIfPasswordIsCorrect(relationshipNo, password);
						if(!passIsCorrect) {
							NeoBankEnvironment.setComment(1, className, "Password is not correct");
							Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
							return;
						}
						mnemoniStringFromDB = (String) CustomerDao.class.getConstructor().newInstance()
								.getmnemonicCode(relationshipNo);
						keyPair= Bip39Utility.masterKeyGeneration(mnemoniStringFromDB.replaceAll(",", " "));
						privateKey = String.valueOf(keyPair.getSecretSeed());
					}else {
						if(request.getParameter("security")!=null)	 privateKey = request.getParameter("security").trim();
					}
				
					if(request.getParameter("input_wallet_desc")!=null)	walletDesc = request.getParameter("input_wallet_desc").trim();
					if(request.getParameter("asset_value")!=null)	assetCode = request.getParameter("asset_value").trim();
					if(request.getParameter("input_private_key")!=null)	privateKey = request.getParameter("input_private_key").trim();
					NeoBankEnvironment.setComment(3, className, " assetCode "+assetCode );
					publicKey = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
					
					if(publicKey == null) {
						throw new Exception("In creating wallet");
						}
					NeoBankEnvironment.setComment(3, className, " Step 1 " );
					NeoBankEnvironment.setComment(3, className, " privateKey is "+privateKey );
					NeoBankEnvironment.setComment(3, className, " publicKey is "+publicKey );

					KeyPair source = KeyPair.fromSecretSeed(privateKey);
				     if(!source.getAccountId().equals(publicKey)) {
						
						Utilities.sendJsonResponse(response, "true", "Secret key is incorrect");
				    	 return;
				     }
					
				     NeoBankEnvironment.setComment(3, className, " Step 2 " );
					if(assetCode.equals(NeoBankEnvironment.getPorteTokenCode())) 
						issuerAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(assetCode);
					if(assetCode.equals(NeoBankEnvironment.getUSDCCode())) 
						issuerAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(assetCode);
					if(assetCode.equals(NeoBankEnvironment.getVesselCoinCode())) 
						issuerAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(assetCode);
					if(assetCode.equals(NeoBankEnvironment.getStellarBTCxCode())) 
						issuerAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor()
								.newInstance().getIssueingAccountPublicKey(assetCode);
					
					NeoBankEnvironment.setComment(3, className, " Step 3 " );
					if(CustomerDao.class.getConstructor().newInstance().checkIfHasWallet(assetCode, relationshipNo)) {
						Utilities.sendJsonResponse(response, "error", "Wallet for selected asset already exist");
						return;
					}
					NeoBankEnvironment.setComment(3, className, " Step 4 " );
					limit = NeoBankEnvironment.getMaxStellarAssetWalletLimit();
					if(assetCode.equalsIgnoreCase(NeoBankEnvironment.getStellarBTCxCode())) {
						createTrustline = StellarSDKUtility.createTrustline(issuerAccountId, privateKey,
								 baseFee, limit,  NeoBankEnvironment.getBitcoinCode() );
						if(createTrustline) {
							success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
									relationshipNo, walletDesc,publicKey, assetCode);
						}else {
							throw new Exception("Error in creating Trustline");
						}
					}else {
						createTrustline = StellarSDKUtility.createTrustline(issuerAccountId, privateKey,
								 baseFee, limit,  assetCode );
						if(createTrustline) {
							success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
									relationshipNo, walletDesc,publicKey, assetCode);
						}else {
							throw new Exception("Error in creating Trustline");
						}
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
							if(obj!=null) obj = null;
							if(user!=null) user = null;
							if(relationshipNo!=null) relationshipNo = null;
							if(walletDesc!=null) walletDesc = null;
							if (hasMnemonic != null)hasMnemonic = null; 
							if (password != null)password = null; 
							if (mnemoniStringFromDB != null)mnemoniStringFromDB = null; 
							if (privateKey != null)privateKey = null; 
							if (publicKey != null)publicKey = null; 
							if (issuerAccountId != null)issuerAccountId = null; 
							if (keyPair != null)keyPair = null;
							if (source != null)source = null;
						}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Creating wallet failed, Please try again letter");
				}
				break;
				
				
				case "getwalletbalance":
					try {
						JsonObject obj = new JsonObject();
						User user = null;
						String relationshipNo= null;
						String walletId=null;
						PrintWriter output = null;
						Wallet wallet = null;
						Gson gson = new Gson();
						HttpSession session = request.getSession(false);
						if (session.getAttribute("SESS_USER") == null) {
							Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
							return;
						}
						user = (User) session.getAttribute("SESS_USER");
						relationshipNo = user.getRelationshipNo();
						if(request.getParameter("walletid")!=null)	
							walletId = request.getParameter("walletid").trim();
						wallet = (Wallet)CustomerWalletDao.class.getConstructor().newInstance().getWalletBalance(walletId);
						if (wallet!=null) {
							obj.add("data", gson.toJsonTree(wallet));
							obj.add("error", gson.toJsonTree("false"));
						}else {
							obj.add("error", gson.toJsonTree("true")); 
							obj.add("msg", gson.toJsonTree("Wallet Details not found")); 
						}
						try {
							NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
							output = response.getWriter();
							output.print(gson.toJson(obj));
						} finally {
							if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
							if (wallet != null)wallet = null; if (obj != null)obj = null;
							if (walletId != null)walletId = null; 
							if (user != null)user = null; 
						}
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "Error in geting wallet balance, Please try again letter");
					}
					break;
					case "get_last_ten_txn":
						try {
							JsonObject obj = new JsonObject();
							User user = null;
							String relationshipNo= null;
							PrintWriter output = null;
							List<AssetTransaction> transactionList = null;
							ConcurrentHashMap<String, String> hashTxnRules = null;
							Gson gson = new Gson();
							String txnDesc= null;
							HttpSession session = request.getSession(false);
							if (session.getAttribute("SESS_USER") == null) {
								Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
								return;
							}
							user = (User) session.getAttribute("SESS_USER");
							relationshipNo = user.getRelationshipNo();
							transactionList = (List<AssetTransaction>)CustomerWalletDao.class.getConstructor()
									.newInstance().getFiatWalletLastTenTxn(relationshipNo);
							
							hashTxnRules = (ConcurrentHashMap<String, String>)CustomerWalletDao.class.getConstructor()
									.newInstance().getTransactionRules();
							if (transactionList!=null) {
								for (int i = 0; i <transactionList.size(); i++) {
									if(transactionList.get(i).getSystemReferenceInt().contains("AC")) {
										transactionList.get(i).setTxnDescription(
												hashTxnRules.get(transactionList.get(i).getSystemReferenceInt().split("-")[0])+" Fee");
									}else {
										transactionList.get(i).setTxnDescription(
												hashTxnRules.get(transactionList.get(i).getSystemReferenceInt().split("-")[0]));
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
						
						case "get_txn_btn_dates":
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
								HttpSession session = request.getSession(false);
								if (session.getAttribute("SESS_USER") == null) {
									Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
									return;
								}
								user = (User) session.getAttribute("SESS_USER");
								relationshipNo = user.getRelationshipNo();
								hashTxnRules = (ConcurrentHashMap<String, String>)CustomerWalletDao.class.getConstructor()
										.newInstance().getTransactionRules();
								
								
								Date d = sdf.parse(dateFrom); Date d2 = sdf.parse(dateTo);
								sdf.applyPattern(newFormat);  sdf.applyPattern(newFormat);
								dateFrom = sdf.format(d);     dateTo = sdf.format(d2);
								
								NeoBankEnvironment.setComment(3, className, "dateFrom "+ dateFrom);
								NeoBankEnvironment.setComment(3, className, "dateTo "+ dateTo);
								
								transactionList = (List<AssetTransaction>)CustomerWalletDao.class.getConstructor()
										.newInstance().getFiatWalletTxnBtnDates(dateFrom, dateTo, relationshipNo);
								NeoBankEnvironment.setComment(3, className, "After fetch ");
								if (transactionList!=null) {
									for (int i = 0; i <transactionList.size(); i++) {
										/*if (transactionList.get(i).getSystemReferenceInt()!=null) {
											txnDesc= hashTxnRules.get(transactionList.get(i).getSystemReferenceInt().split("-")[0]);
											transactionList.get(i).setTxnDescription(txnDesc);
										}*/
										if(transactionList.get(i).getSystemReferenceInt().contains("AC")) {
											transactionList.get(i).setTxnDescription(
													hashTxnRules.get(transactionList.get(i).getSystemReferenceInt().split("-")[0])+" Fee");
										}else {
											transactionList.get(i).setTxnDescription(
													hashTxnRules.get(transactionList.get(i).getSystemReferenceInt().split("-")[0]));
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
								Utilities.sendJsonResponse(response, "error", "Error in geting Transactions, Please try again letter");
							}
							break;
							
							case"fiatwalletp2p":
								try {
									NeoBankEnvironment.setComment(3, className,"Inside fiatwalletp2p");
									JsonObject obj = new JsonObject();User user = null;String relationshipNo= null;PrintWriter output = null;
									Gson gson = new Gson();String amount= null;String receiverEmail =null;boolean success = false;
									String walletBalance = null;String txnUserCode = null;String walletDetails =null;String walletId=null;
									String payComments="";String referenceNo="";boolean checkIfEmailExist = false; String userType="C";
									String customerChargesValue=null; String customerCharges=null; String minimumTxnAmount=null;String langPref=null;
									
									String authStatus=""; String authMessage=""; String authResponse=null;
									boolean recordAuthorization=false; String currencyId = null; String txnMode=null; String transactionCode=null;
									if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
									if(request.getParameter("hdnselregistereduser")!=null)	receiverEmail = request.getParameter("hdnselregistereduser").trim();
									if(request.getParameter("amount")!=null)amount = request.getParameter("amount").trim();
									if(request.getParameter("comment")!=null)payComments = request.getParameter("comment").trim();
									NeoBankEnvironment.setComment(3,className,"Pay comments is"+payComments);
									HttpSession session = request.getSession(false);
									if (session.getAttribute("SESS_USER") == null) {
										if(langPref.equalsIgnoreCase("en")) {
											Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
										}else {
											Utilities.sendSessionExpiredResponse(response, "error", "La sesi�n ha caducado, vuelva a iniciar sesi�n");
										}
										return;
									}
									user = (User) session.getAttribute("SESS_USER");
									relationshipNo = user.getRelationshipNo();
									walletDetails = (String)CustomerWalletDao.class.getConstructor().newInstance().getFiatWalletBalance(relationshipNo);
									checkIfEmailExist= (boolean)CustomerDao.class.getConstructor().newInstance().checkIfEmailExist(receiverEmail);
									if(!checkIfEmailExist) {
										if(langPref.equalsIgnoreCase("en")) {
											Utilities.sendJsonResponse(response, "error", "Receiver Does not exist");
										}else {
											Utilities.sendJsonResponse(response, "error", "Receptor No existe");
										}
										return;
									}
									if(walletDetails!=null) {
										walletId = walletDetails.split(",")[0];
										walletBalance = walletDetails.split(",")[1];
									}
									NeoBankEnvironment.setComment(3, className, "Wallet id is "+walletId+" wallet balance "+walletBalance);
								
									//
									txnMode="D";
									currencyId = NeoBankEnvironment.getUSDCurrencyId();
									txnUserCode = Utilities.generateTransactionCode(10);
									transactionCode=(new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
											+ Utilities.genAlphaNumRandom(9);
									referenceNo = NeoBankEnvironment.getCodeFiatWalletP2P()+ "-" +transactionCode;
									 customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
											NeoBankEnvironment.getDefaultCustomerUserType(),NeoBankEnvironment.getCodeFiatWalletP2P(), amount);
									 minimumTxnAmount=customerCharges.substring(customerCharges.indexOf("|")+1, customerCharges.length());
									// Check Wallet Balance

									customerChargesValue = customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|"));
									// Check Wallet Balance
									 NeoBankEnvironment.setComment(3,className,"customerChargesValue is "+customerChargesValue);
									 
									if ((Double.parseDouble(amount)+Double.parseDouble(customerChargesValue)) >Double.parseDouble(walletBalance)) {
										if(langPref.equalsIgnoreCase("en")) {
											Utilities.sendJsonResponse(response, "error", "You do not have enough balance to complete this transaction. Please topup to proceed");
										}else {
											Utilities.sendJsonResponse(response, "error", "No tiene saldo suficiente para completar esta transacci�n. Recargue para continuar");
										}
										return;
									}
									if ( Double.parseDouble(amount)< Double.parseDouble(minimumTxnAmount)) { 
										if(langPref.equalsIgnoreCase("en")) {
											Utilities.sendJsonResponse(response, "error", "Transaction amount can not be less than "+minimumTxnAmount);
										}else {
											Utilities.sendJsonResponse(response, "error", "El monto de la transacci�n no puede ser inferior a "+minimumTxnAmount);
										}
										throw new Exception("minimumAmount amount inputed:- Minimum exptected is:- "+minimumTxnAmount+"Amount inputed is"+amount);
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
											if(langPref.equalsIgnoreCase("en")) {
												 Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again");
											}else {
												 Utilities.sendJsonResponse(response, "authfailed", "Error en la autorizaci�n, int�ntalo de nuevo");
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
											if(langPref.equalsIgnoreCase("en")) {
												Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again");
											}else {
												Utilities.sendJsonResponse(response, "authfailed", "Error en la autorizaci�n, int�ntalo de nuevo");
											}
										return;
										}
									}
									
									/****** End of Wallet Authorization******/	
									
									success = (boolean) CustomerFiatWalletDao.class.getConstructor().newInstance().fiatWalletP2P(relationshipNo, walletId,  amount, payComments, 
											receiverEmail, referenceNo, txnUserCode, customerCharges,transactionCode);
									
									if (success) {
										SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, userType,
												NeoBankEnvironment.getCodeFiatWalletP2P(), " Fiat P2P send to "+receiverEmail );
										obj.add("error", gson.toJsonTree("false"));
										if(langPref.equalsIgnoreCase("en")) {
											obj.add("message", gson.toJsonTree("You have transfered " + Utilities.getMoneyinDecimalFormat(amount) + 
												" to " + receiverEmail +". Reference Number " +txnUserCode)); 
										}else {
											obj.add("message", gson.toJsonTree("Has transferido " + Utilities.getMoneyinDecimalFormat(amount) + 
													" a " + receiverEmail +". N�mero de referencia " +txnUserCode)); 
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
										if (obj != null)obj = null; if (receiverEmail != null)receiverEmail = null;
										if (amount != null)amount = null; 
										if (user != null)user = null; 
										if (walletBalance != null)walletBalance = null; 
										if (walletDetails != null)walletDetails = null; if(userType!=null) userType=null;
										if (walletId != null)walletId = null; 
										if (payComments != null)payComments = null; 
										if (referenceNo != null)referenceNo = null; 
										if (customerChargesValue != null)customerChargesValue = null; if (customerCharges != null)customerCharges = null; 
										if (minimumTxnAmount != null)minimumTxnAmount = null;
										if (currencyId!=null); currencyId=null;  if (authResponse != null)authResponse = null;
										if (transactionCode!=null); transactionCode=null;    if (txnMode!=null); txnMode=null; 
									}								
									
								} catch (Exception e) {
									NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
									Utilities.sendJsonResponse(response, "error", "Error Sending Money, Please try again letter");
								}
								break;
							case "create_mnemocic_code":
								try {
									String hasAccount = null; User user = null;
									String publicKey = null; JsonObject stellarAcount = null;
									JsonObject obj = new JsonObject();String mnemoniccode=null;
									KeyPair keyPair = null;String relationshipNo = null;
									PrintWriter output = null; char [] mneumonic=null; 
									if(request.getParameter("has_account")!=null)hasAccount = request.getParameter("has_account").trim();
									if(request.getParameter("relno")!=null)relationshipNo = request.getParameter("relno").trim();
									HttpSession session = request.getSession(false); 
									if (session.getAttribute("SESS_USER") == null) {
										Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
										return;
									}
						
									NeoBankEnvironment.setComment(3, className, "hasAccount is "+hasAccount);
									// Check if has already linked his account to stellar using our system.
									if(CustomerCoinsDao.class.getConstructor().newInstance().checkIfStellarHasBeenLinkedByCustomer(relationshipNo)) {
										Utilities.sendJsonResponse(response, "error", "You have already linked your Stellar account in our system");
										return;
									}
									NeoBankEnvironment.setComment(3, className, "hasAccount is "+hasAccount);
									if(!Boolean.parseBoolean(hasAccount)) {
										mneumonic = Bip39Utility.generateMnemonic();
										SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "CCA","Mneumonic code generated" );
										mnemoniccode= new String(mneumonic);
										obj.addProperty("error", "false");
										obj.addProperty("data", mnemoniccode);
									}else {
										obj.addProperty("error", "true");
										obj.addProperty("message", "Error in creating mnemonic code");
									}
									try {
										NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
										output = response.getWriter();
										output.print(obj);
									} finally {
										if(user!=null) user=null; if(publicKey!=null) publicKey=null;
										if(stellarAcount!=null) stellarAcount=null; if(obj!=null) obj=null;
										if(mnemoniccode!=null) mnemoniccode=null; if(hasAccount!=null) hasAccount=null;
										if(keyPair!=null) keyPair=null;if(output!=null) output.close();
									}
								} catch (Exception e) {
									NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
									Utilities.sendJsonResponse(response, "error", "Error In creating mneumonic code, Please try again letter");
								}
								break;
							case "create_mnemocic_code_mbl":
								try {
									String hasAccount = null; User user = null; String tokenValue = null;
									String publicKey = null; JsonObject stellarAcount = null;
									JsonObject obj = new JsonObject();String mnemoniccode=null;
									KeyPair keyPair = null;String relationshipNo = null;
									PrintWriter output = null; char [] mneumonic=null; 
									if(request.getParameter("has_account")!=null)hasAccount = request.getParameter("has_account").trim();
									if(request.getParameter("relno")!=null)relationshipNo = request.getParameter("relno").trim();
									if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
									//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
									if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
										NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
										Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
										return;
									}

						
									NeoBankEnvironment.setComment(3, className, "hasAccount is "+hasAccount);
									// Check if has already linked his account to stellar using our system.
									if(CustomerCoinsDao.class.getConstructor().newInstance().checkIfStellarHasBeenLinkedByCustomer(relationshipNo)) {
										Utilities.sendJsonResponse(response, "error", "You have already linked your Stellar account in our system");
										return;
									}
									NeoBankEnvironment.setComment(3, className, "hasAccount is "+hasAccount);
									if(!Boolean.parseBoolean(hasAccount)) {
										mneumonic = Bip39Utility.generateMnemonic();
										SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "CCA","Mneumonic code generated" );
										mnemoniccode= new String(mneumonic);
										obj.addProperty("error", "false");
										obj.addProperty("data", mnemoniccode);
									}else {
										obj.addProperty("error", "true");
										obj.addProperty("message", "Error in creating mnemonic code");
									}
									try {
										NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
										output = response.getWriter();
										output.print(obj);
									} finally {
										if(user!=null) user=null; if(publicKey!=null) publicKey=null;if (tokenValue != null)tokenValue = null;
										if(stellarAcount!=null) stellarAcount=null; if(obj!=null) obj=null;
										if(mnemoniccode!=null) mnemoniccode=null; if(hasAccount!=null) hasAccount=null;
										if(keyPair!=null) keyPair=null;if(output!=null) output.close();
									}
								} catch (Exception e) {
									NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
									Utilities.sendJsonResponse(response, "error", "Error In creating mneumonic code, Please try again letter");
								}
								break;
								
							case "create_stellar_account":
								try {
									NeoBankEnvironment.setComment(3,className,"Inside create_stellar_account");
									String hasAccount = null; User user = null;
									String publicKey = null; JsonObject stellarAcount = null;
									Boolean createAccountIsSuccessfull = false;
									Boolean accountExist = false; JsonObject obj = new JsonObject();
									KeyPair keyPair = null; Boolean success = false; String relationshipNo = null;
									PrintWriter output = null;String Password=null;String mnemonicCode=null;
									String secretKey = ""; String assetCode = NeoBankEnvironment.getXLMCode();
									String encryptedMnemonic=null; String tokenizedMnemonic=null;String userType="C";
									
									HttpSession session = request.getSession(false); 
									if (session.getAttribute("SESS_USER") == null) {
										Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
										return;
									}
									
									if(request.getParameter("has_account")!=null)hasAccount = request.getParameter("has_account").trim();
									if(request.getParameter("relno")!=null)relationshipNo = request.getParameter("relno").trim();
									if(request.getParameter("password")!=null)Password = request.getParameter("password").trim();
									if(request.getParameter("arr")!=null)mnemonicCode = request.getParameter("arr").trim();
									
									NeoBankEnvironment.setComment(3,className,"hasAccount "+hasAccount);
									// Check if has already linked his account to stellar using our system.
									encryptedMnemonic= Utilities.tripleEncryptData(mnemonicCode);
									char [] mnemonicchar= mnemonicCode.replaceAll(",", " ").toCharArray();
									user = (User)UserLoginDao.class.getConstructor().newInstance().validate( relationshipNo, Password,userType );	
									if(user!=null) {
								    	// check password
									
								    	if(user.getPassword().equals(DigestUtils.md5Hex(Utilities.encryptString(Password))) == false) {
											NeoBankEnvironment.setComment(3, className, "password is wrong");
											Utilities.sendJsonResponse(response, "error", "Incorrect Password");
											return;
								    		}					    	
								    }
									if(CustomerCoinsDao.class.getConstructor().newInstance().checkIfStellarHasBeenLinkedByCustomer(relationshipNo)) {
										Utilities.sendJsonResponse(response, "error", "You have already linked your Stellar account in our system");
										return;
									}else{
										boolean result  = (boolean)CustomerCoinsDao.class.getConstructor().newInstance().insertMnemonicCode(relationshipNo, encryptedMnemonic);
										if(result) {
											SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, userType, "C",StringUtils.substring("Inserted Mnemonic Code " + relationshipNo , 0, 48));
										}else {
											NeoBankEnvironment.setComment(1, className, "Error in inserting mnemonic code");
										}
									}
									if(Boolean.parseBoolean(hasAccount)) {
										//Check if account exist
										tokenizedMnemonic="N";
										if(request.getParameter("input_public_key")!=null)publicKey = request.getParameter("input_public_key").trim();
										
										keyPair =  KeyPair.fromAccountId(publicKey);
										accountExist = StellarSDKUtility.CheckAccountIfExist(keyPair);
										if(!accountExist) {
											Utilities.sendJsonResponse(response, "error", " Account Does not exist in stellar ");
											return;
										}
										success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerStellarAccount(Utilities.encryptString(keyPair.getAccountId())
												, secretKey, relationshipNo, assetCode,tokenizedMnemonic );
									}
									
									if(!Boolean.parseBoolean(hasAccount)) {
										tokenizedMnemonic="Y";
										keyPair = Bip39Utility.generateKeyPairs(mnemonicchar);
										stellarAcount = new JsonObject();
										stellarAcount = StellarSDKUtility.createAccount(keyPair);
										createAccountIsSuccessfull = stellarAcount.get("successful").getAsBoolean();
										if(createAccountIsSuccessfull)
											success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerStellarAccount(Utilities.encryptString(keyPair.getAccountId())
													, secretKey, relationshipNo, assetCode,tokenizedMnemonic );
									}
									if(success) {
										SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "CCA","Customer created stellar account" );
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
										if(user!=null) user=null; if(publicKey!=null) publicKey=null;if(hasAccount!=null) hasAccount=null;
										if(stellarAcount!=null) stellarAcount=null; if(obj!=null) obj=null;if(createAccountIsSuccessfull!=null) createAccountIsSuccessfull=null;
										if(keyPair!=null) keyPair=null;if(output!=null) output.close();if(accountExist!=null) accountExist=null;
										if(Password!=null) Password=null;if(mnemonicCode!=null) mnemonicCode=null;if(success!=null) success=null;
										if(encryptedMnemonic!=null) encryptedMnemonic=null;if(mnemonicchar!=null) mnemonicchar=null;
										if(relationshipNo!=null) relationshipNo=null;if(secretKey!=null) secretKey=null;
										if(assetCode!=null) assetCode=null;if(tokenizedMnemonic!=null) tokenizedMnemonic=null;
									}
									
								} catch (Exception e) {
									NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
									Utilities.sendJsonResponse(response, "error", "Error In creating account, Please try again letter");
								}
								break;
							case "create_stellar_account_mbl":
								try {
									//NeoBankEnvironment.setComment(3,className,"Inside create_stellar_account");
									String hasAccount = null; User user = null; String tokenValue = null;
									String publicKey = null; JsonObject stellarAcount = null;
									Boolean createAccountIsSuccessfull = false;
									Boolean accountExist = false; JsonObject obj = new JsonObject();
									KeyPair keyPair = null; Boolean success = false; String relationshipNo = null;
									PrintWriter output = null;String Password=null;String mnemonicCode=null;
									String secretKey = ""; String assetCode = NeoBankEnvironment.getXLMCode();
									String encryptedMnemonic=null; String tokenizedMnemonic=null;String userType="C";
									
									if(request.getParameter("has_account")!=null)hasAccount = request.getParameter("has_account").trim();
									if(request.getParameter("relno")!=null)relationshipNo = request.getParameter("relno").trim();
									if(request.getParameter("password")!=null)Password = request.getParameter("password").trim();
									if(request.getParameter("arr")!=null)mnemonicCode = request.getParameter("arr").trim();
									if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
									//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
									if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
										NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
										Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
										return;
									}
									
									NeoBankEnvironment.setComment(3,className,"hasAccount "+hasAccount);
									// Check if has already linked his account to stellar using our system.
									encryptedMnemonic= Utilities.tripleEncryptData(mnemonicCode);
									char [] mnemonicchar= mnemonicCode.replaceAll(",", " ").toCharArray();
									user = (User)UserLoginDao.class.getConstructor().newInstance().validate( relationshipNo, Password,userType );	
									if(user!=null) {
								    	// check password

										if(user.getPassword().equals(DigestUtils.md5Hex(Utilities.encryptString(Password))) == false) {

											Utilities.sendJsonResponse(response, "error", "Incorrect Password");
								    		}	
								    }
									if(CustomerCoinsDao.class.getConstructor().newInstance().checkIfStellarHasBeenLinkedByCustomer(relationshipNo)) {
										Utilities.sendJsonResponse(response, "error", "You have already linked your Stellar account in our system");
										return;
									}else{
										boolean result  = (boolean)CustomerCoinsDao.class.getConstructor().newInstance().insertMnemonicCode(relationshipNo, encryptedMnemonic);
										if(result) {
											SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, userType, "C",StringUtils.substring("Inserted Mnemonic Code " + relationshipNo , 0, 48));
										}else {
											NeoBankEnvironment.setComment(1, className, "Error in inserting mnemonic code");
										}
									}
									if(Boolean.parseBoolean(hasAccount)) {
										//Check if account exist
										tokenizedMnemonic="N";
										if(request.getParameter("input_public_key")!=null)publicKey = request.getParameter("input_public_key").trim();
										
										keyPair =  KeyPair.fromAccountId(publicKey);
										accountExist = StellarSDKUtility.CheckAccountIfExist(keyPair);
										if(!accountExist) {
											Utilities.sendJsonResponse(response, "error", " Account Does not exist in stellar ");
											return;
										}
										success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerStellarAccount(Utilities.encryptString(keyPair.getAccountId())
												, secretKey, relationshipNo, assetCode,tokenizedMnemonic );
									}
									
									if(!Boolean.parseBoolean(hasAccount)) {
										tokenizedMnemonic="Y";
										keyPair = Bip39Utility.generateKeyPairs(mnemonicchar);
										stellarAcount = new JsonObject();
										stellarAcount = StellarSDKUtility.createAccount(keyPair);
										createAccountIsSuccessfull = stellarAcount.get("successful").getAsBoolean();
										if(createAccountIsSuccessfull)
											success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerStellarAccount(Utilities.encryptString(keyPair.getAccountId())
													, secretKey, relationshipNo, assetCode,tokenizedMnemonic );
									}
									if(success) {
										SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "CCA","Customer created stellar account" );
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
										if(user!=null) user=null; if(publicKey!=null) publicKey=null;if(hasAccount!=null) hasAccount=null;
										if(stellarAcount!=null) stellarAcount=null; if(obj!=null) obj=null;if(createAccountIsSuccessfull!=null) createAccountIsSuccessfull=null;
										if(keyPair!=null) keyPair=null;if(output!=null) output.close();if(accountExist!=null) accountExist=null;
										if(Password!=null) Password=null;if(mnemonicCode!=null) mnemonicCode=null;if(success!=null) success=null;
										if(encryptedMnemonic!=null) encryptedMnemonic=null;if(mnemonicchar!=null) mnemonicchar=null;
										if(relationshipNo!=null) relationshipNo=null;if(secretKey!=null) secretKey=null; if (tokenValue != null)tokenValue = null;
										if(assetCode!=null) assetCode=null;if(tokenizedMnemonic!=null) tokenizedMnemonic=null;
									}
									
								} catch (Exception e) {
									NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
									Utilities.sendJsonResponse(response, "error", "Error In creating account, Please try again letter");
								}
								break;
							case"cust_with_stellar_acc":
								try {
									User user = null;Boolean hasAccount = true;String tokenizedMnemonic=null;
									String publicKey = null; JsonObject stellarAcount = null;
									JsonObject obj = new JsonObject();String nmemonic=null;Boolean accountExist = false;
									KeyPair keyPair = null; String relationshipNo = null;String password=null;
									PrintWriter output = null; String firstMnemonic=null;String userType="C";
									String thirdMnemonic=null;String secMnemonic=null;String fourhMnemonic=null;String fifthMnemonic=null;
									String sixthMnemonic=null;String seventhMnemonic=null;String eithMnemonic=null;String nineMnemonic=null;
									String tenMnemonic=null;String eleventMnemonic=null;String twelveMnemonic=null;String stellarPublicKey=null;
									String encryptedMnemonic=null;String secretKey="";Boolean succss = false;String assetCode = NeoBankEnvironment.getXLMCode();
									if(request.getParameter("relno")!=null)relationshipNo = request.getParameter("relno").trim();
									if(request.getParameter("first_code")!=null)firstMnemonic = request.getParameter("first_code").trim();
									if(request.getParameter("second_code")!=null)secMnemonic = request.getParameter("second_code").trim();
									if(request.getParameter("third_code")!=null)thirdMnemonic = request.getParameter("third_code").trim();
									if(request.getParameter("fourth_code")!=null)fourhMnemonic = request.getParameter("fourth_code").trim();
									if(request.getParameter("fifth_code")!=null)fifthMnemonic = request.getParameter("fifth_code").trim();
									if(request.getParameter("sixth_code")!=null)sixthMnemonic = request.getParameter("sixth_code").trim();
									if(request.getParameter("seventh_code")!=null)seventhMnemonic = request.getParameter("seventh_code").trim();
									if(request.getParameter("eight_code")!=null)eithMnemonic = request.getParameter("eight_code").trim();
									if(request.getParameter("nineth_code")!=null)nineMnemonic = request.getParameter("nineth_code").trim();
									if(request.getParameter("tenth_code")!=null)tenMnemonic = request.getParameter("tenth_code").trim();
									if(request.getParameter("eleventh_code")!=null)eleventMnemonic = request.getParameter("eleventh_code").trim();
									if(request.getParameter("twelve_code")!=null)twelveMnemonic = request.getParameter("twelve_code").trim();
									if(request.getParameter("input_public_key")!=null)stellarPublicKey = request.getParameter("input_public_key").trim();
									if(request.getParameter("pwsd")!=null)password = request.getParameter("pwsd").trim();
									HttpSession session = request.getSession(false); 
									if (session.getAttribute("SESS_USER") == null) {
										Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
										return;
									}
									
									
									nmemonic=firstMnemonic.concat(" "+secMnemonic+" "+thirdMnemonic+" "+fourhMnemonic+" "+fifthMnemonic+" "+sixthMnemonic+" "+
											seventhMnemonic+" "+eithMnemonic+" "+nineMnemonic+" "+tenMnemonic+" "+eleventMnemonic+" "+twelveMnemonic);	
									
									user = (User)UserLoginDao.class.getConstructor().newInstance().validate( relationshipNo, password,userType );	
									if(user!=null) {
								    	if(user.getPassword().equals( DigestUtils.md5Hex(Utilities.encryptString(password))) == false) {
											Utilities.sendJsonResponse(response, "error", "Incorrect Password");
											return;
								    		}					    	
								    }
									if(hasAccount) {
										//Check if account exist
										NeoBankEnvironment.setComment(3,className,"Check if account exists");
										tokenizedMnemonic="Y";
										keyPair =  KeyPair.fromAccountId(stellarPublicKey);
										accountExist = StellarSDKUtility.CheckAccountIfExist(keyPair);
										if(!accountExist) {
											Utilities.sendJsonResponse(response, "error", " Account Does not exist in stellar ");
											return;
										}
										NeoBankEnvironment.setComment(3, className, "Going to the database");
										succss = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerStellarAccount(Utilities.encryptString(keyPair.getAccountId())
												, secretKey, relationshipNo, assetCode,tokenizedMnemonic );
										if (!succss) {
											Utilities.sendJsonResponse(response, "error", " Failed to create account, try again later. ");
											return;
										}
									
									}
									NeoBankEnvironment.setComment(3, className, "Generated Mnemonic code is "+Bip39Utility.masterKeyGeneration(nmemonic).getAccountId());
									
									if(stellarPublicKey.equals(Bip39Utility.masterKeyGeneration(nmemonic).getAccountId())) {
										encryptedMnemonic= Utilities.encryptString(nmemonic);
										
										boolean result  = (boolean)CustomerCoinsDao.class.getConstructor().newInstance().insertMnemonicCode(relationshipNo, encryptedMnemonic);
										if(result) {
											SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, userType, "C",StringUtils.substring("Inserted Mnemonic Code " + relationshipNo , 0, 48));
											obj.addProperty("error", "false");
											obj.addProperty("message", "Account linked successfully");
										}else {
											NeoBankEnvironment.setComment(1, className, "Error in linking stellar accounts");
											Utilities.sendJsonResponse(response, "error", "Error in linking stellar accounts");
											return;
										}
										
									}else{
										Utilities.sendJsonResponse(response, "error", "Public key is not equal to the one generated by mnemonic code");
										return;
									}try {
										NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
										output = response.getWriter();
										output.print(obj);
									} finally {
										if(user!=null) user=null; if(publicKey!=null) publicKey=null;if(fifthMnemonic!=null) fifthMnemonic=null;
										if(stellarAcount!=null) stellarAcount=null; if(obj!=null) obj=null;if(sixthMnemonic!=null) sixthMnemonic=null;
										if(keyPair!=null) keyPair=null;if(output!=null) output.close();if(seventhMnemonic!=null) seventhMnemonic=null;
										if(encryptedMnemonic!=null) encryptedMnemonic=null;if(secMnemonic!=null) secMnemonic=null;
										if(firstMnemonic!=null) firstMnemonic=null;if(thirdMnemonic!=null) thirdMnemonic=null;
										if(fourhMnemonic!=null) fourhMnemonic=null;if(eithMnemonic!=null) eithMnemonic=null;
										if(nineMnemonic!=null) nineMnemonic=null;if(tenMnemonic!=null) tenMnemonic=null;
										if(eleventMnemonic!=null) eleventMnemonic=null;if(twelveMnemonic!=null) twelveMnemonic=null;
										if(stellarPublicKey!=null) stellarPublicKey=null;if(password!=null) password=null;
									}
								} catch (Exception e) {
									NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
									Utilities.sendJsonResponse(response, "error", "Error In creating account, Please try again letter");
								}
								break;
							case"cust_with_stellar_acc_mbl":
								try {
									User user = null;Boolean hasAccount = true;String tokenizedMnemonic=null;
									String publicKey = null; JsonObject stellarAcount = null; String tokenValue = null;
									JsonObject obj = new JsonObject();String nmemonic=null;Boolean accountExist = false;
									KeyPair keyPair = null; String relationshipNo = null;String password=null;
									PrintWriter output = null; String firstMnemonic=null;String userType="C";
									String thirdMnemonic=null;String secMnemonic=null;String fourhMnemonic=null;String fifthMnemonic=null;
									String sixthMnemonic=null;String seventhMnemonic=null;String eithMnemonic=null;String nineMnemonic=null;
									String tenMnemonic=null;String eleventMnemonic=null;String twelveMnemonic=null;String stellarPublicKey=null;
									String encryptedMnemonic=null;String secretKey="";Boolean succss = false;String assetCode = NeoBankEnvironment.getXLMCode();
									if(request.getParameter("relno")!=null)relationshipNo = request.getParameter("relno").trim();
									if(request.getParameter("first_code")!=null)firstMnemonic = request.getParameter("first_code").trim();
									if(request.getParameter("second_code")!=null)secMnemonic = request.getParameter("second_code").trim();
									if(request.getParameter("third_code")!=null)thirdMnemonic = request.getParameter("third_code").trim();
									if(request.getParameter("fourth_code")!=null)fourhMnemonic = request.getParameter("fourth_code").trim();
									if(request.getParameter("fifth_code")!=null)fifthMnemonic = request.getParameter("fifth_code").trim();
									if(request.getParameter("sixth_code")!=null)sixthMnemonic = request.getParameter("sixth_code").trim();
									if(request.getParameter("seventh_code")!=null)seventhMnemonic = request.getParameter("seventh_code").trim();
									if(request.getParameter("eight_code")!=null)eithMnemonic = request.getParameter("eight_code").trim();
									if(request.getParameter("nineth_code")!=null)nineMnemonic = request.getParameter("nineth_code").trim();
									if(request.getParameter("tenth_code")!=null)tenMnemonic = request.getParameter("tenth_code").trim();
									if(request.getParameter("eleventh_code")!=null)eleventMnemonic = request.getParameter("eleventh_code").trim();
									if(request.getParameter("twelve_code")!=null)twelveMnemonic = request.getParameter("twelve_code").trim();
									if(request.getParameter("input_public_key")!=null)stellarPublicKey = request.getParameter("input_public_key").trim();
									if(request.getParameter("pwsd")!=null)password = request.getParameter("pwsd").trim();
									if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
									//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
									if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
										NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
										Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
										return;
									}
									
									nmemonic=firstMnemonic.concat(" "+secMnemonic+" "+thirdMnemonic+" "+fourhMnemonic+" "+fifthMnemonic+" "+sixthMnemonic+" "+
											seventhMnemonic+" "+eithMnemonic+" "+nineMnemonic+" "+tenMnemonic+" "+eleventMnemonic+" "+twelveMnemonic);	
									
									user = (User)UserLoginDao.class.getConstructor().newInstance().validate( relationshipNo, password,userType );	
									if(user!=null) {
										if(user.getPassword().equals( DigestUtils.md5Hex((Utilities.encryptString(password)))) == false) {
								    	//if(user.getPassword().equals( Utilities.tripleEncryptData(password )) == false) {
											Utilities.sendJsonResponse(response, "error", "Incorrect Password");
											return;
								    		}					    	
								    }
									if(hasAccount) {
										//Check if account exist
										NeoBankEnvironment.setComment(3,className,"Check if account exists");
										tokenizedMnemonic="Y";
										keyPair =  KeyPair.fromAccountId(stellarPublicKey);
										accountExist = StellarSDKUtility.CheckAccountIfExist(keyPair);
										if(!accountExist) {
											Utilities.sendJsonResponse(response, "error", " Account Does not exist in stellar ");
											return;
										}
										NeoBankEnvironment.setComment(3, className, "Going to the database");
										succss = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerStellarAccount(Utilities.encryptString(keyPair.getAccountId())
												, secretKey, relationshipNo, assetCode,tokenizedMnemonic );
										if (!succss) {
											Utilities.sendJsonResponse(response, "error", " Failed to create account, try again later. ");
											return;
										}
									
									}
									NeoBankEnvironment.setComment(3, className, "Generated Mnemonic code is "+Bip39Utility.masterKeyGeneration(nmemonic).getAccountId());
									
									if(stellarPublicKey.equals(Bip39Utility.masterKeyGeneration(nmemonic).getAccountId())) {
										encryptedMnemonic= Utilities.encryptString(nmemonic);
										
										boolean result  = (boolean)CustomerCoinsDao.class.getConstructor().newInstance().insertMnemonicCode(relationshipNo, encryptedMnemonic);
										if(result) {
											SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, userType, "C",StringUtils.substring("Inserted Mnemonic Code " + relationshipNo , 0, 48));
											obj.addProperty("error", "false");
											obj.addProperty("message", "Account linked successfully");
										}else {
											NeoBankEnvironment.setComment(1, className, "Error in linking stellar accounts");
											Utilities.sendJsonResponse(response, "error", "Error in linking stellar accounts");
											return;
										}
										
									}else{
										Utilities.sendJsonResponse(response, "error", "Public key is not equal to the one generated by mnemonic code");
										return;
									}try {
										NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
										output = response.getWriter();
										output.print(obj);
									} finally {
										if(user!=null) user=null; if(publicKey!=null) publicKey=null;if(fifthMnemonic!=null) fifthMnemonic=null;
										if(stellarAcount!=null) stellarAcount=null; if(obj!=null) obj=null;if(sixthMnemonic!=null) sixthMnemonic=null;
										if(keyPair!=null) keyPair=null;if(output!=null) output.close();if(seventhMnemonic!=null) seventhMnemonic=null;
										if(encryptedMnemonic!=null) encryptedMnemonic=null;if(secMnemonic!=null) secMnemonic=null;
										if(firstMnemonic!=null) firstMnemonic=null;if(thirdMnemonic!=null) thirdMnemonic=null;
										if(fourhMnemonic!=null) fourhMnemonic=null;if(eithMnemonic!=null) eithMnemonic=null;
										if(nineMnemonic!=null) nineMnemonic=null;if(tenMnemonic!=null) tenMnemonic=null;
										if(eleventMnemonic!=null) eleventMnemonic=null;if(twelveMnemonic!=null) twelveMnemonic=null;
										if(stellarPublicKey!=null) stellarPublicKey=null;if(password!=null) password=null;
										if (tokenValue != null)tokenValue = null;
									}
								} catch (Exception e) {
									NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
									Utilities.sendJsonResponse(response, "error", "Error In creating account, Please try again letter");
								}
								break;
							case"nomnemonic_stellar_account":
								try {
									NeoBankEnvironment.setComment(3,className,"inside nomnemonic_stellar_account");
									String relationshipNo=null; String stellarPublicKey=null;String password=null;KeyPair keyPair = null;
									User user = null;String userType="C";Boolean hasAccount = true;String tokenizedMnemonic=null;
									Boolean accountExist = false;String secretKey = ""; String assetCode = NeoBankEnvironment.getXLMCode();
									Boolean success = false; JsonObject obj = new JsonObject();PrintWriter output = null;
									HttpSession session = request.getSession(false); 
									if (session.getAttribute("SESS_USER") == null) {
										Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
										return;
									}
									
									if(request.getParameter("relno")!=null)relationshipNo = request.getParameter("relno").trim();
									if(request.getParameter("nomnemonic_input_public_key")!=null)stellarPublicKey = request.getParameter("nomnemonic_input_public_key").trim();
									if(request.getParameter("nomnemonicpwsd")!=null)password = request.getParameter("nomnemonicpwsd").trim();
									NeoBankEnvironment.setComment(3,className,"relationshipNo is "+relationshipNo+"stellarPublicKey "+stellarPublicKey+"password "+password+" hasAccount "+hasAccount);
									user = (User)UserLoginDao.class.getConstructor().newInstance().validate( relationshipNo, password,userType );	
									if(user!=null) {
								    	if(user.getPassword().equals( DigestUtils.md5Hex(Utilities.encryptString(password))) == false) {
											Utilities.sendJsonResponse(response, "error", "Incorrect Password");
											return;
								    		}					    	
								    }
									if(CustomerCoinsDao.class.getConstructor().newInstance().checkIfStellarHasBeenLinkedByCustomer(relationshipNo)) {
										Utilities.sendJsonResponse(response, "error", "You have already linked your Stellar account in our system");
										return;
									}
									NeoBankEnvironment.setComment(3, className, "Going to check if the user exists");
									if(hasAccount) {
										//Check if account exist
										NeoBankEnvironment.setComment(3,className,"Check if account exists");
										tokenizedMnemonic="N";
										keyPair =  KeyPair.fromAccountId(stellarPublicKey);
										accountExist = StellarSDKUtility.CheckAccountIfExist(keyPair);
										if(!accountExist) {
											Utilities.sendJsonResponse(response, "error", " Account Does not exist in stellar ");
											return;
										}
										NeoBankEnvironment.setComment(3, className, "Going to the database");
										success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerStellarAccount(Utilities.encryptString(keyPair.getAccountId())
												, secretKey, relationshipNo, assetCode,tokenizedMnemonic );
									}
									NeoBankEnvironment.setComment(3, className, "After checking if user exists");
									if(success) {
										NeoBankEnvironment.setComment(3, className, "Inside success");
										SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "CCA","Customer created stellar account" );
										obj.addProperty("error", "false");
										if(!hasAccount) {
											obj.addProperty("message", "You have successfully linked your Stellar Account "
													+"\n"+"Account number is: "+keyPair.getAccountId()+"\n"+"Private Key is: "+String.valueOf( keyPair.getSecretSeed()));
										}else {

											obj.addProperty("message", "You have successfully Linked your Stellar Account ");
										}
									}else {
										obj.addProperty("error", "true");
										obj.addProperty("message", "Linking account failed");
									}try {
										NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
										output = response.getWriter();
										output.print(obj);
									} finally {
										if(output!=null) output.close();if(user!=null) user=null;if(relationshipNo!=null) relationshipNo=null;
										if(stellarPublicKey!=null) stellarPublicKey=null;if(password!=null) password=null;if(keyPair!=null) keyPair=null;
										if(userType!=null) userType=null;if(hasAccount!=null) hasAccount=null;if(tokenizedMnemonic!=null) tokenizedMnemonic=null;
										if(accountExist!=null) accountExist=null;if(secretKey!=null) secretKey=null;if(assetCode!=null) assetCode=null;
										if(success!=null) success=null;if(obj!=null) obj=null;
									}
								} catch (Exception e) {
									NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
									Utilities.sendJsonResponse(response, "error", "Error In creating account, Please try again letter");
								}
								break;
							case"nomnemonic_stellar_account_mbl":
								try {
									NeoBankEnvironment.setComment(3,className,"inside nomnemonic_stellar_account");
									String relationshipNo=null; String stellarPublicKey=null;String password=null;KeyPair keyPair = null;
									User user = null;String userType="C";Boolean hasAccount = true;String tokenizedMnemonic=null;
									Boolean accountExist = false;String secretKey = ""; String assetCode = NeoBankEnvironment.getXLMCode();
									Boolean success = false; JsonObject obj = new JsonObject();PrintWriter output = null;
									String tokenValue = null;
									if(request.getParameter("relno")!=null)relationshipNo = request.getParameter("relno").trim();
									if(request.getParameter("nomnemonic_input_public_key")!=null)stellarPublicKey = request.getParameter("nomnemonic_input_public_key").trim();
									if(request.getParameter("nomnemonicpwsd")!=null)password = request.getParameter("nomnemonicpwsd").trim();
									NeoBankEnvironment.setComment(3,className,"relationshipNo is "+relationshipNo+"stellarPublicKey "+stellarPublicKey+"password "+password+" hasAccount "+hasAccount);
									if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
									//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
									if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
										NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
										Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
										return;
									}

									
									user = (User)UserLoginDao.class.getConstructor().newInstance().validate( relationshipNo, password,userType );	
									if(user!=null) {
								    	if(user.getPassword().equals( Utilities.encryptString(password )) == false) {
											Utilities.sendJsonResponse(response, "error", "Incorrect Password");
											return;
								    		}					    	
								    }
									if(CustomerCoinsDao.class.getConstructor().newInstance().checkIfStellarHasBeenLinkedByCustomer(relationshipNo)) {
										Utilities.sendJsonResponse(response, "error", "You have already linked your Stellar account in our system");
										return;
									}
									NeoBankEnvironment.setComment(3, className, "Going to check if the user exists");
									if(hasAccount) {
										//Check if account exist
										NeoBankEnvironment.setComment(3,className,"Check if account exists");
										tokenizedMnemonic="N";
										keyPair =  KeyPair.fromAccountId(stellarPublicKey);
										accountExist = StellarSDKUtility.CheckAccountIfExist(keyPair);
										if(!accountExist) {
											Utilities.sendJsonResponse(response, "error", " Account Does not exist in stellar ");
											return;
										}
										NeoBankEnvironment.setComment(3, className, "Going to the database");
										success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerStellarAccount(Utilities.encryptString(keyPair.getAccountId())
												, secretKey, relationshipNo, assetCode,tokenizedMnemonic );
									}
									NeoBankEnvironment.setComment(3, className, "After checking if user exists");
									if(success) {
										NeoBankEnvironment.setComment(3, className, "Inside success");
										SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "CCA","Customer created stellar account" );
										obj.addProperty("error", "false");
										if(!hasAccount) {
											obj.addProperty("message", "You have successfully linked your Stellar Account "
													+"\n"+"Account number is: "+keyPair.getAccountId()+"\n"+"Private Key is: "+String.valueOf( keyPair.getSecretSeed()));
										}else {

											obj.addProperty("message", "You have successfully Linked your Stellar Account ");
										}
									}else {
										obj.addProperty("error", "true");
										obj.addProperty("message", "Linking account failed");
									}try {
										NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
										output = response.getWriter();
										output.print(obj);
									} finally {
										if(output!=null) output.close();if(user!=null) user=null;if(relationshipNo!=null) relationshipNo=null;
										if(stellarPublicKey!=null) stellarPublicKey=null;if(password!=null) password=null;if(keyPair!=null) keyPair=null;
										if(userType!=null) userType=null;if(hasAccount!=null) hasAccount=null;if(tokenizedMnemonic!=null) tokenizedMnemonic=null;
										if(accountExist!=null) accountExist=null;if(secretKey!=null) secretKey=null;if(assetCode!=null) assetCode=null;
										if(success!=null) success=null; if (tokenValue != null)tokenValue = null;
									}
								} catch (Exception e) {
									NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
									Utilities.sendJsonResponse(response, "error", "Error In creating account, Please try again letter");
								}
								break;
		 }		
		
		
	}

	

}
