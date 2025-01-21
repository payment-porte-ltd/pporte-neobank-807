package com.pporte.rules;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.CustomerDao;
import com.pporte.dao.CustomerDigitalAssetsDao;
import com.pporte.dao.CustomerPorteCoinDao;
import com.pporte.dao.OpsManageCryptoDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.model.CryptoAssetCoins;
import com.pporte.model.Customer;
import com.pporte.model.Transaction;
import com.pporte.model.User;
import com.pporte.model.WalletAssetAccounts;
import com.pporte.utilities.CurrencyTradeUtility;
import com.pporte.utilities.SendEmailUtility;
import com.pporte.utilities.StellarSDKUtility;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class OpsManageCryptoRulesImpl implements Rules {
	private static String className = OpsManageCryptoRulesImpl.class.getSimpleName();
	@Override
	public void performJSONOperation(String arg0, HttpServletRequest arg1, HttpServletResponse arg2,
			ServletContext arg3) throws Exception {
		
		
	}

	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		HttpSession session = request.getSession(false);
		switch (rulesaction) {
		case "approve_porte_transactions":
			try {
				String custRelNo=null; String publicKey=null; String comment =""; 
				String txnAmount=null; String coinAmount=null; 
				String assetCode=null; String distributorAccountPrivateKey=null; 
				PrintWriter out_json = response.getWriter();  JsonObject obj = new JsonObject();
				boolean success=false;  String extSystemRef = "";
				boolean buyStatus = false; User user = null;
				String txnCode = null;
				String issuerAccountId = null;
				String subject=null; String content=null;
				Customer c_Details=null;
				
				if(request.getParameter("relno")!=null)	custRelNo = request.getParameter("relno").trim();
				if(request.getParameter("publickey")!=null)	publicKey = request.getParameter("publickey").trim();
				if(request.getParameter("txnamount")!=null)	txnAmount = request.getParameter("txnamount").trim();
				if(request.getParameter("coinamount")!=null) coinAmount = request.getParameter("coinamount").trim();
				if(request.getParameter("assetcode")!=null)	assetCode = request.getParameter("assetcode").trim();
				if(request.getParameter("privatekey")!=null) distributorAccountPrivateKey = request.getParameter("privatekey").trim();
				if(request.getParameter("txncode")!=null) txnCode = request.getParameter("txncode").trim();
				if(request.getParameter("comment")!=null) comment = request.getParameter("comment").trim();
				
				
				 if (session.getAttribute("SESS_USER") == null) 
						throw new Exception ("Session has expired, please log in again");
				 user = (User) session.getAttribute("SESS_USER");
				 
				 if(assetCode.equals(NeoBankEnvironment.getPorteTokenCode())) 
						issuerAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor()
						.newInstance().getIssueingAccountPublicKey(assetCode);
					if(assetCode.equals(NeoBankEnvironment.getUSDCCode())) 
						issuerAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor()
						.newInstance().getIssueingAccountPublicKey(assetCode);
					if(assetCode.equals(NeoBankEnvironment.getVesselCoinCode())) 
						issuerAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor()
						.newInstance().getIssueingAccountPublicKey(assetCode);
					
					if(assetCode.equals(NeoBankEnvironment.getXLMCode())) {
						
						buyStatus = StellarSDKUtility.buyNativeCoinPaymen(distributorAccountPrivateKey, publicKey, assetCode, issuerAccountId, coinAmount, comment);				
					}else {

					buyStatus = StellarSDKUtility.buyNoNNativeCoinPayment(distributorAccountPrivateKey,publicKey,assetCode,
							issuerAccountId, coinAmount,comment );
					}				
				if(buyStatus) 
					//Get external ref 
					success= (boolean)OpsManageCryptoDao.class.getConstructor().newInstance().updateCustomerDetails(txnCode, extSystemRef);
				if(success) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(user.getUserId(),"C", "BIC","Approved transaction" );
					obj.addProperty("error", "false"); 
	        		obj.addProperty("message", "Transaction Approved"); 
	        		
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
					out_json.print(obj);
				}finally {
					if(comment!=null) comment=null; if(coinAmount!=null) coinAmount=null;
					if(custRelNo!=null) custRelNo=null; if(publicKey!=null) publicKey=null;
					if(assetCode!=null) assetCode=null; if(distributorAccountPrivateKey!=null) distributorAccountPrivateKey=null;
					if(obj!=null) obj = null;
					if(txnCode!=null) txnCode = null; if (txnAmount!=null)txnAmount=null;
					if(out_json!=null) out_json.close();
					if (c_Details!=null)c_Details=null; if (subject!=null);subject=null; if (content!=null);content=null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in approving transaction, Please try again letter");
			}
				
			break;
		case "ops_create_claimable_balance":
			try {
				String userId=null; String receiverPublicKey=null; String comment =""; 
				String txnAmount=null; String issuerAccountId = null;
				String assetCode=null; String sourceAccountPrivateKey=null; 
				PrintWriter out_json = response.getWriter();  JsonObject obj = new JsonObject();
				User user = null;
				String result = null;  String status = null; String txnHash = null;
				if(request.getParameter("sender_asset")!=null)	assetCode = request.getParameter("sender_asset").trim();
				if(request.getParameter("input_receiver")!=null)	receiverPublicKey = request.getParameter("input_receiver").trim();
				if(request.getParameter("commet")!=null)	comment = request.getParameter("commet").trim();
				if(request.getParameter("sendamount")!=null)	txnAmount = request.getParameter("sendamount").trim();
				if(request.getParameter("security")!=null)	sourceAccountPrivateKey = request.getParameter("security").trim();
				 if(assetCode.equals(NeoBankEnvironment.getPorteTokenCode())) 
					 issuerAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor()
						.newInstance().getIssueingAccountPublicKey(assetCode);
				 if(assetCode.equals(NeoBankEnvironment.getUSDCCode())) 
					 issuerAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor()
						.newInstance().getIssueingAccountPublicKey(assetCode);
				 if(assetCode.equals(NeoBankEnvironment.getVesselCoinCode())) 
					 issuerAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor()
						.newInstance().getIssueingAccountPublicKey(assetCode);
				 
				 if (session.getAttribute("SESS_USER") == null) 
						throw new Exception ("Session has expired, please log in again");
				 user = (User) session.getAttribute("SESS_USER");
				 
				 result = CurrencyTradeUtility.createClaimableBalance(sourceAccountPrivateKey, receiverPublicKey, txnAmount, 
						 assetCode, issuerAccountId, comment);
				 
				 NeoBankEnvironment.setComment(3, className, "result is "+result);
				 
				 if(result ==null) 
					 throw new Exception("Error in creating claimable balance");
				 
				 
				 if(result.trim().equalsIgnoreCase("success")) {
					 SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(user.getUserId(),"C", "CCB","Created claimable balance for this account "+receiverPublicKey );
					 obj.addProperty("error", "false"); 
		        	 obj.addProperty("message", "Claimable balance created suceessfully"); 
				 }else {
					 obj.addProperty("error", "true"); 
		        	 obj.addProperty("message", "Transaction failed");
				 }
				 
				 try {
					out_json = response.getWriter();
					out_json.print(obj);
				}finally {
					if(comment!=null) comment=null; if(userId!=null) userId=null;
					if(receiverPublicKey!=null) receiverPublicKey=null; if(sourceAccountPrivateKey!=null) sourceAccountPrivateKey=null;
					if(assetCode!=null) assetCode=null; if(issuerAccountId!=null) issuerAccountId=null;
					if(obj!=null) obj = null;
					if(txnHash!=null) txnHash = null;
					if(user!=null) user = null; if (txnAmount!=null)txnAmount=null;
					if(result!=null) result = null; if (status!=null)status=null;
					if(out_json!=null) out_json.close();
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in creating claimable balance, Please try again letter");
			}
			break;
			
		 case "ops_edit_asset_accounts":
			 try {
				 String userId=null; String accountType = null; String publicKey = null; String status = null;
					String assetCode=null; String userType = null; boolean success = false;
					PrintWriter out_json = response.getWriter();  JsonObject obj = new JsonObject();
					User user = null; String sequeceId = null;
					if(request.getParameter("editassetcode")!=null)	assetCode = request.getParameter("editassetcode").trim();
					if(request.getParameter("selleditaccounttype")!=null)	accountType = request.getParameter("selleditaccounttype").trim();
					if(request.getParameter("editpublickey")!=null)	publicKey = request.getParameter("editpublickey").trim();
					if(request.getParameter("selleditstatus")!=null)	status = request.getParameter("selleditstatus").trim();
					if(request.getParameter("hdnsequenceno")!=null)	sequeceId = request.getParameter("hdnsequenceno").trim();
					
					NeoBankEnvironment.setComment(3, className, "assetCode "+assetCode+" accountType "+accountType+" publicKey "+publicKey
							+" status "+status+" sequeceId "+sequeceId);
					
					userId=((User) session.getAttribute("SESS_USER")).getUserId();
					userType=((User) session.getAttribute("SESS_USER")).getUserType();
					success = (boolean)OpsManageCryptoDao.class.getConstructor().newInstance().editAssetAccountDetails(sequeceId, accountType, publicKey, status);

					if(success) {
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",
								StringUtils.substring("Ops Updated Wallet Asset Account sequince id "+sequeceId +" Asset code is "+ assetCode, 0, 48));
					    obj.addProperty("message", "Account updated successfuly");
					    obj.addProperty("error", "false");
		        	}else {
		        		 obj.addProperty("message", "Account update failed");
						 obj.addProperty("error", "true");
		        	}
					try {
						 out_json.print( (obj));
					}finally {
						if(assetCode!=null) assetCode=null; if(userType!=null) userType=null; 
						if(sequeceId!=null) sequeceId=null; 
						if(accountType!=null) accountType=null; if(userId!=null) userId=null; if(publicKey !=null) publicKey=null;
						if(user!=null) user=null; if(status!=null) status=null; 
						if(obj!=null) obj = null; if(out_json!=null) out_json.close();
					}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in Editing accounts, Please try again letter");
			}
			 
			 break;
			 
		 case "ops_add_asset_accounts":
			try {
				
				String assetCode = null; String accountType = null; 	String publicKey = null;	
				String userId = null; String userType = null; 	boolean success = false;	
				JsonObject obj = new JsonObject(); PrintWriter out_json = response.getWriter(); String status = null;
				
				 if(request.getParameter("seladdassetcode")!=null)	assetCode = StringUtils.trim( request.getParameter("seladdassetcode") );
				 if(request.getParameter("selladdaccounttype")!=null)	accountType = StringUtils.trim( request.getParameter("selladdaccounttype") );
				 if(request.getParameter("addpublickey")!=null)	publicKey = StringUtils.trim( request.getParameter("addpublickey") );
				 if(request.getParameter("selladdstatus")!=null)	status = StringUtils.trim( request.getParameter("selladdstatus") );
				 				
				
				userId=((User) session.getAttribute("SESS_USER")).getUserId();
				userType=((User) session.getAttribute("SESS_USER")).getUserType();

				success = (boolean)OpsManageCryptoDao.class.getConstructor().newInstance().
						addNewAssetConfigurationAccount(assetCode, accountType, publicKey, status);

				if(success) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",
							StringUtils.substring("OPs added new Asset Account" + assetCode, 0, 48));
					
				    obj.addProperty("message", "Asset Account Created successfully");
				    obj.addProperty("error", "false");

	        	}else {
	        		obj.addProperty("message", "Error in adding Asset Account");
				    obj.addProperty("error", "true");

	        	}
				try {
					 out_json.print((obj));
				}finally {
					if(assetCode!=null) assetCode=null; if(userType!=null) userType=null;
					if(accountType!=null) accountType=null; if(publicKey!=null) publicKey=null; 
					if(userId!=null) userId=null; if(status!=null) status=null; 
					if(obj!=null) obj = null; if(out_json!=null) out_json.close();
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			 
			 break;
		
		}
		
	}

	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response, ServletContext ctx)
			throws Exception {
		switch (rulesaction) {
			case "View Porte Request":
				try {
					request.setAttribute("lastaction", "opscrypto");
					request.setAttribute("lastrule", "View Porte Request");
					response.setContentType("text/html");
					request.setAttribute("transactions", (List<Transaction>) OpsManageCryptoDao.class.getConstructor()
							.newInstance().getPorteRequests());
					try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewPorteRequestPage()).forward(request, response);
					} finally {
						
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
				}
				
				break;
			case "Create Claimable Balance":
				try {
					request.setAttribute("lastaction", "opscrypto");
					request.setAttribute("lastrule", "Create Claimable Balance");
					response.setContentType("text/html");
					try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getOpsCreateClaimableBalancePage()).forward(request, response);
					} finally {
						
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
				}
				break;
			case "Wallet Asset Accounts":
				try {
					request.setAttribute("lastaction", "opscrypto");
					request.setAttribute("lastrule", rulesaction);
					request.setAttribute("assets", (ArrayList<CryptoAssetCoins>) CustomerPorteCoinDao.class .getConstructor().newInstance().getPorteAssetDetails());
					request.setAttribute("accounts", (ArrayList<WalletAssetAccounts>) OpsManageCryptoDao.class.getConstructor()
							.newInstance().getAllWalletAssetAccounts());
					response.setContentType("text/html");
					try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getOpsWalletAssetAccountsPage()).forward(request, response);
					} finally {
						
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
					Utilities.callException(request, response, ctx, e.getMessage());
				}
				
				break;
		}
		
	}

}
