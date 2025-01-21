package com.pporte.rules;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.stellar.sdk.KeyPair;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.CustomerWalletDao;
import com.pporte.dao.OpsFiatWalletDao;
import com.pporte.dao.OpsManageCustomerDao;
import com.pporte.dao.OpsManageWalletDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.model.CryptoAssetCoins;
import com.pporte.model.Customer;
import com.pporte.model.Transaction;
import com.pporte.model.AssetAccount;
import com.pporte.model.AssetTransaction;
import com.pporte.model.User;
import com.pporte.model.Wallet;
import com.pporte.utilities.StellarSDKUtility;
import com.pporte.utilities.Utilities;
import framework.v8.Rules;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class OpsManageWalletsRulesImpl  implements Rules {
	private static String className = OpsManageWalletsRulesImpl.class.getSimpleName();

	@Override
	public void performJSONOperation(String arg0, HttpServletRequest arg1, HttpServletResponse arg2,
			ServletContext arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null) 
			Utilities.callException(request, response, ctx, "Session has expired, please log in again");
		switch (rulesaction) {
		case "add_wallet_asset":
			try {
				JsonObject obj = new JsonObject();
				PrintWriter output = null;
				String assetCode = null;
				String assetDesc = null;
				String status = null;
				String assetType = null;
				String walletType = null;
				String userId = null;
				User user = null; 
				String issuerAccountId= null;
				String distributionAccountId= null;
				String liquidityAccountId= null;
				
				boolean success = false;
			
				user = (User) session.getAttribute("SESS_USER");
				userId = user.getUserId();
				
				if(request.getParameter("asset_code")!=null)	
					assetCode = request.getParameter("asset_code").trim();
				if(request.getParameter("asset_desc")!=null)	
					assetDesc = request.getParameter("asset_desc").trim();
				if(request.getParameter("status")!=null)	
					status = request.getParameter("status").trim();
				if(request.getParameter("asset_type")!=null)	
					assetType = request.getParameter("asset_type").trim();
				if(request.getParameter("wallet_type")!=null)	
					walletType = request.getParameter("wallet_type").trim();
				
				if(request.getParameter("issuer_acc_id")!=null)	
					issuerAccountId = request.getParameter("issuer_acc_id").trim();
				if(request.getParameter("distribution_acc_id")!=null)	
					distributionAccountId = request.getParameter("distribution_acc_id").trim();
				if(request.getParameter("liquidity_acc_id")!=null)	
					liquidityAccountId = request.getParameter("liquidity_acc_id").trim();
				
				success = (Boolean)OpsManageWalletDao.class.getConstructor().newInstance().
						addWalletAssets(assetCode, assetDesc, status,assetType, walletType, 
								issuerAccountId, distributionAccountId, liquidityAccountId );
				if (success) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, "O",
							"WAL"," Added Wallet asset "+assetCode);
					obj.addProperty("error", "false");
					obj.addProperty("message", "You have successfully added asset "+assetCode ); 
				}else {
					obj.addProperty("error","true"); 
					obj.addProperty("message", "Adding Asset failed"); 
				}
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + obj.toString());
					output = response.getWriter();
					output.print(obj);
				} finally {
					if (output != null)output.close();
					if (obj != null)obj = null;if (assetCode != null)assetCode = null;
					if (status != null)status = null;
					if (assetType != null)assetType = null;if (walletType != null)walletType = null;
					if (userId != null)userId = null;if (user != null)user = null;
					 
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in Adding Asset failed, Please try again letter");
			}
		break;
		case "edit_wallet_asset":
			try {
				JsonObject obj = new JsonObject();
				PrintWriter output = null;
				String assetCode = null;
				String assetDesc = null;
				String status = null;
				String assetType = null;
				String walletType = null;
				String userId = null;
				String assetId = null;
				User user = null; 
				String issuerAccountId= null;
				String distributionAccountId= null;
				String liquidityAccountId= null;
				
				boolean success = false;
				
				if (session.getAttribute("SESS_USER") == null) 
					throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				userId = user.getUserId();
				
				if(request.getParameter("edit_asset_code")!=null)	
					assetCode = request.getParameter("edit_asset_code").trim();
				if(request.getParameter("edit_asset_id")!=null)	
					assetId = request.getParameter("edit_asset_id").trim();
				if(request.getParameter("edit_asset_desc")!=null)	
					assetDesc = request.getParameter("edit_asset_desc").trim();
				if(request.getParameter("edit_status")!=null)	
					status = request.getParameter("edit_status").trim();
				if(request.getParameter("edit_asset_type")!=null)	
					assetType = request.getParameter("edit_asset_type").trim();
				if(request.getParameter("edit_wallet_type")!=null)	
					walletType = request.getParameter("edit_wallet_type").trim();
				
				if(request.getParameter("edit_issuer_acc_id")!=null)	
					issuerAccountId = request.getParameter("edit_issuer_acc_id").trim();
				if(request.getParameter("edit_distribution_acc_id")!=null)	
					distributionAccountId = request.getParameter("edit_distribution_acc_id").trim();
				if(request.getParameter("edit_liquidity_acc_id")!=null)	
					liquidityAccountId = request.getParameter("edit_liquidity_acc_id").trim();
				
				success = (Boolean)OpsManageWalletDao.class.getConstructor().newInstance().
						editWalletAssets(assetCode, assetDesc, status,assetType, walletType, assetId, issuerAccountId, distributionAccountId, liquidityAccountId  );
				if (success) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, "O",
							"WAL"," Edited Wallet asset ID: "+assetId);
					obj.addProperty("error", "false");
					obj.addProperty("message", "You have successfully edited ID: "+assetId ); 
				}else {
					obj.addProperty("error","true"); 
					obj.addProperty("message", "Editing  failed"); 
				}
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + obj.toString());
					output = response.getWriter();
					output.print(obj);
				} finally {
					if (output != null)output.close();
					if (obj != null)obj = null;if (assetCode != null)assetCode = null;
					if (assetId != null)assetId = null;if (status != null)status = null;
					if (assetType != null)assetType = null;if (walletType != null)walletType = null;
					if (userId != null)userId = null;if (user != null)user = null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in Editing Asset failed, Please try again letter");
			}
		break;
		
			case "opblockwalletid":
				try {
						String relationshipNo=null; String walletId=null; String langPref=null;boolean success=false;  String blockCodeId=null;
						PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject(); User user=null;
						if (request.getParameter("viewrelationshipno") != null)  	  relationshipNo = request.getParameter("viewrelationshipno").trim();
						if (request.getParameter("editblockcoddeid") != null)  	  blockCodeId = request.getParameter("editblockcoddeid").trim();
						if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
						if(request.getParameter("viewwalletid")!=null)			   walletId = request.getParameter("viewwalletid").trim();
						String userId=null;
						
						user = (User) session.getAttribute("SESS_USER");
						userId = user.getUserId();
						NeoBankEnvironment.setComment(3, className, "Relationship number "+relationshipNo +"block code description is "+walletId);
						
						success= (boolean)OpsFiatWalletDao.class.getConstructor().newInstance().OpsUpdateBlockId(relationshipNo,walletId,blockCodeId);
			
						
						if (success) {
							// Module Code:- Ops Blocking Wallet - OBW
							SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId,"C", "OBW"," Ops Blocking Wallet: "+walletId+" Blockcode: "+blockCodeId );
							obj.add("error", gson.toJsonTree("false"));
						}else {
							obj.add("error", gson.toJsonTree("true")); 
							obj.add("message", gson.toJsonTree("Failed to block wallet")); 
						}
						try {
							NeoBankEnvironment.setComment(3, className," opblockwalletid String is " + gson.toJson(obj));
							out_json.print(gson.toJson(obj));
						} finally {
							if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
							if (obj != null)obj = null;if (langPref != null)langPref = null;if (blockCodeId != null)blockCodeId = null;
							if (walletId != null)walletId = null; if (user!=null)user=null; if (userId!=null) userId=null;
						}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "true", "Error in Blocking Wallets, Please try again letter");
				}
			break;
			
		case "opunblockwalletid":
			try {
			String relationshipNo=null; String walletId=null; boolean success=false;  String blockCodeId=null;
			PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject(); User user=null;
			String userId=null;
		
			if(request.getParameter("viewwalletid_unblock")!=null)			   walletId = request.getParameter("viewwalletid_unblock").trim();
			blockCodeId="1";// Default Code for wallet
			
			user = (User) session.getAttribute("SESS_USER");
			userId = user.getUserId();
			
			success= (boolean)OpsFiatWalletDao.class.getConstructor().newInstance().OpsUpdateBlockId(relationshipNo,walletId,blockCodeId);
		
			if (success) {
				// Module Code:- Ops Unblocking Wallet - OUW
				SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId,"C", "OUW"," Ops Unblocking Wallet: "+walletId );
				obj.add("error", gson.toJsonTree("false"));
			}else {
				obj.add("error", gson.toJsonTree("true")); 
				obj.add("message", gson.toJsonTree("Failed to unblock wallet")); 
			}
			try {
				NeoBankEnvironment.setComment(3, className," opunblockwalletid String is " + gson.toJson(obj));
				out_json.print(gson.toJson(obj));
			} finally {
				if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
				if (obj != null)obj = null;if (blockCodeId != null)blockCodeId = null;
				if (walletId != null)walletId = null; if (user!=null)user=null; if (userId!=null) userId=null;
			}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "true", "Error in Unblocking Wallets, Please try again letter");
			}
			break;
		}
	}

	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response, 
			ServletContext ctx)
			throws Exception {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null) 
			Utilities.callOpsException(request, response, ctx, "Session has expired, please log in again");
		switch (rulesaction){
			case "View Wallets":
				try {
					String langPref = null; 
					if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
	
					request.setAttribute("langPref", langPref);
					request.setAttribute("lastaction", "opswal");
					request.setAttribute("lastrule", "View Wallets");
					
					ArrayList<Customer> customers = null;
					customers = (ArrayList<Customer>) OpsManageCustomerDao.class.getConstructor().newInstance().getCustomersDetails();
					
					request.setAttribute("customers", customers);
					response.setContentType("text/html");
					 try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewWalletsPage()).forward(request, response);
					} finally {
						if(customers !=null) customers = null;	
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
				}
			break;
			case "viewspesificcustomerwals":
				try {
					String langPref = null;
					String relationshipNo = null;
					Wallet custFiatWallet =null;
					if(request.getParameter("hdnlangpref")!=null)langPref = request.getParameter("hdnlangpref").trim();
					if(request.getParameter("relno")!=null)relationshipNo = request.getParameter("relno").trim();
					request.setAttribute("langPref", langPref);
					request.setAttribute("lastaction", "opswal");
					request.setAttribute("lastrule", "View Wallets");
					custFiatWallet= (Wallet)CustomerWalletDao.class.getConstructor().newInstance().getCustomerWalletDetails(relationshipNo,"C");
					request.setAttribute("custfiatwallet", custFiatWallet); 
					response.setContentType("text/html");
					try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewSpecificCustomerWalletsPage()).forward(request, response);
					} finally {
						if(custFiatWallet !=null) custFiatWallet = null;	if (relationshipNo!=null)relationshipNo=null;
					}
				
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
				}
			break;
			
			case "Wallet Assets":
				try {
					String langPref = null; 
					if(request.getParameter("hdnlangpref")!=null)	langPref = request.getParameter("hdnlangpref").trim();
					request.setAttribute("langPref", langPref);
					request.setAttribute("lastaction", "opswal");
					request.setAttribute("lastrule", "Wallet Assets");
					List<CryptoAssetCoins> walletAssets = null;
					walletAssets = (List<CryptoAssetCoins>) OpsManageWalletDao.class.getConstructor().newInstance().getWalletAssets();
					request.setAttribute("walletAssets", walletAssets);
					response.setContentType("text/html");
					 try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewWalletAssetsPage()).forward(request, response);
					} finally {
						if(walletAssets !=null) walletAssets = null;	
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
				}
			break;
			
			case "View Fiat Wallet Transactions":
				try {
					String langPref = null; 
					if(request.getParameter("hdnlangpref")!=null)	langPref = request.getParameter("hdnlangpref").trim();
					request.setAttribute("langPref", langPref);
					request.setAttribute("lastaction", "opswal");
					request.setAttribute("lastrule", "Wallet Assets");
					List<AssetTransaction> transactions = null;
					transactions = (List<AssetTransaction>) OpsManageWalletDao.class.getConstructor().newInstance().geFiatWalletTransactions();
					request.setAttribute("transactions", transactions);
					response.setContentType("text/html");
					 try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewWalletAssetsPage()).forward(request, response);
					} finally {
						if(transactions !=null) transactions = null;	
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
				}
			break;
			
			case "View Porte Wallets":
				try {
					String langPref = null; 
					if(request.getParameter("hdnlangpref")!=null)	langPref = request.getParameter("hdnlangpref").trim();
					ArrayList<Customer> arrPorteWalletDetails = (ArrayList<Customer>)OpsManageWalletDao.class.getConstructor().newInstance().getAllPorteWaletsDetails();
					request.setAttribute("portetxn", arrPorteWalletDetails);
					request.setAttribute("langPref", langPref);
					request.setAttribute("lastaction", "opswal");
					request.setAttribute("lastrule", "View Porte Wallets");
					response.setContentType("text/html");
					 try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewPorteWalletsPage()).forward(request, response);
					} finally {
						
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
				}
				
				break;
			case "porte_wallets":
				try {
					String publicKey = null; String customerName = null; String relationshipNo = null; 
					if(request.getParameter("hdnpublickey")!=null)	
						publicKey = request.getParameter("hdnpublickey").trim();
					if(request.getParameter("hdncustname")!=null)	
						customerName = request.getParameter("hdncustname").trim();
					if(request.getParameter("hdnrelno")!=null)	
						relationshipNo = request.getParameter("hdnrelno").trim();
					request.setAttribute("lastaction", "opswal");
					request.setAttribute("lastrule", "View Porte Wallets");
					request.setAttribute("custname", customerName);
					request.setAttribute("relNo", relationshipNo);
					request.setAttribute("publickey", publicKey);
					
					KeyPair keyPair = KeyPair.fromAccountId(publicKey);
					ArrayList<AssetAccount> listAssetAccount = StellarSDKUtility.getAccountBalance(keyPair);
					request.setAttribute("stelarwal", listAssetAccount );
					
					response.setContentType("text/html");
					try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewSpecificUserPortWal()).forward(request, response);
					} finally {
						if(listAssetAccount !=null)listAssetAccount=null;
						if(publicKey !=null)publicKey=null;
						if(customerName !=null)customerName=null;
						if(relationshipNo !=null)relationshipNo=null;
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
				}
				break;
				
			case"view_more_porte_wal_details":
				try {
					String langPref = null;
					String customerRelNo = ""; String customerId = ""; String custPhoneNo = ""; String custName=""; 
				   // String userId = null; String userType = null; 
				    ArrayList <Customer> arrCustomerDetails = null;
				    

					if (request.getParameter("hdnlangpref") != null)    	langPref = request.getParameter("hdnlangpref").trim();
					if (request.getParameter("searchbyrelno") != null) 		customerRelNo = request.getParameter("searchbyrelno").trim();
					if (request.getParameter("searchbycustid") != null) 		customerId = request.getParameter("searchbycustid").trim();
					if (request.getParameter("searchmobileno") != null) 		custPhoneNo = request.getParameter("searchmobileno").trim();
					if (request.getParameter("searchbycustname") != null) 		custName = request.getParameter("searchbycustname").trim();
					
					NeoBankEnvironment.setComment(3, className, "insude customerRelNo " + customerRelNo + " customerId " + customerId + 
							" custPhoneNo "+ custPhoneNo + " custName "+ custName);

					try {
						if(customerRelNo.equals("") && customerId.equals("") &&custPhoneNo.equals("") && custName.equals("")) {
							throw new Exception("All Search criteria cannot be blank");
						}
						
						request.setAttribute("langPref", langPref);
						request.setAttribute("lastaction", "opswal");
						request.setAttribute("lastrule", "View Porte Wallets");
						
						//userId=((User) session.getAttribute("SESS_USER")).getUserId();
						//userType=((User) session.getAttribute("SESS_USER")).getUserType();
						
						NeoBankEnvironment.setComment(3, className, "insude customerRelNo " + customerRelNo + " customerId " + customerId + 
								" custPhoneNo "+ custPhoneNo + " custName "+ custName);


						arrCustomerDetails = (ArrayList<Customer>)OpsFiatWalletDao.class.getConstructor().newInstance().getSearchOpsSpecificPorteWalDetails(
								custName, customerRelNo,customerId,custPhoneNo);


						if(arrCustomerDetails !=null && arrCustomerDetails.size()>0 ) {
							NeoBankEnvironment.setComment(3, className, "customer found "+ arrCustomerDetails.size());											
						}else {
							NeoBankEnvironment.setComment(3, className, "Exception is customer details not available ");										
						}
						
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, "Exception is customer search "+e.getMessage());											
					}
						request.setAttribute("portetxn", arrCustomerDetails);
						response.setContentType("text/html");
						
					try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewPorteWalletsPage()).forward(request, response);
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, "Exception in calling RequestDispatcher is "+e.getMessage());
					}finally {
						if(arrCustomerDetails !=null) arrCustomerDetails = null; if(customerRelNo != null) customerRelNo=null;	
						if(customerId != null) customerId=null;	if(custPhoneNo != null) custPhoneNo=null;
						if(custName != null) custName = null; 
					}
												
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
				}
				break;
			case "Block Wallets":
				try {
					String langPref = null; 
				    ArrayList<Customer> arrWalletDetails=null;
				    ArrayList<Customer>arrBlockDetails = null;

					if(request.getParameter("hdnlangpref")!=null)	langPref = request.getParameter("hdnlangpref").trim();
					request.setAttribute("langPref", langPref);
					request.setAttribute("lastaction", "opswal");
					request.setAttribute("lastrule", "Block Wallets");
					arrWalletDetails = (ArrayList<Customer>)OpsFiatWalletDao.class.getConstructor().newInstance().getAllWalletsDetails();
					arrBlockDetails = (ArrayList<Customer>)OpsFiatWalletDao.class.getConstructor().newInstance().getAllBlockDetails();
					//NeoBankEnvironment.setComment(1, className, "Going to jsp");

					request.setAttribute("allwallets", arrWalletDetails);
					request.setAttribute("allblockiddetails", arrBlockDetails);
					response.setContentType("text/html");
					 try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getOpsBlockWalletsPage()).forward(request, response);
					} finally {
						if (arrWalletDetails!=null)arrWalletDetails=null; if (arrBlockDetails!=null)arrBlockDetails=null;
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
				}
				break;
			case "View Authorized Transactions":
				try {
					String langPref = null; 
					ArrayList<Transaction> arrAuthorizedWalletTransactions=null;
					
					if(request.getParameter("hdnlangpref")!=null)	langPref = request.getParameter("hdnlangpref").trim();
					request.setAttribute("langPref", langPref);
					request.setAttribute("lastaction", "opswal");
					request.setAttribute("lastrule", "View Authorized Transactions");
					arrAuthorizedWalletTransactions = (ArrayList<Transaction>)OpsFiatWalletDao.class.getConstructor().newInstance().getWalletAuthorizationsTransactions();
					
					request.setAttribute("transactions", arrAuthorizedWalletTransactions);
					response.setContentType("text/html");
					try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsAuthorizedWalletTransactionsPage()).forward(request, response);
					} finally {
						if (arrAuthorizedWalletTransactions!=null) arrAuthorizedWalletTransactions=null;
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
				}
				break;
				
			case "get_filtered_cust_wallets": 
				
				try {
					 ArrayList<Customer> arrWalletDetails=null;
					 ArrayList<Customer>arrBlockDetails = null;
					 
					String CustomerCode = ""; String customerId = ""; String custPhoneNo = ""; String custName=""; 
					
					if (request.getParameter("searchrelnno") != null) 		CustomerCode = request.getParameter("searchrelnno").trim();
					if (request.getParameter("searchcustid") != null) 		customerId = request.getParameter("searchcustid").trim();
					if (request.getParameter("searchmobileno") != null) 		custPhoneNo = request.getParameter("searchmobileno").trim();
					if (request.getParameter("searchcustname") != null) 		custName = request.getParameter("searchcustname").trim();
					
					if(CustomerCode.equals("") && customerId.equals("") &&custPhoneNo.equals("") && custName.equals("")) {
						throw new Exception("All Search criteria cannot be blank");
					}
					
					NeoBankEnvironment.setComment(3, className, "insude CustomerCode " + CustomerCode + " customerId " + customerId + 
							" custPhoneNo "+ custPhoneNo + " custName "+ custName);
					
					
					arrWalletDetails = (ArrayList<Customer>)OpsFiatWalletDao.class.getConstructor().newInstance().getAllFilteredWalletsDetails(custName, CustomerCode,customerId,custPhoneNo);
					arrBlockDetails = (ArrayList<Customer>)OpsFiatWalletDao.class.getConstructor().newInstance().getAllBlockDetails();
					
					request.setAttribute("allwallets", arrWalletDetails);
					request.setAttribute("allblockiddetails", arrBlockDetails);
					request.setAttribute("lastaction", "opswal");
					request.setAttribute("lastrule", "Block Wallets");
					response.setContentType("text/html");
						
					try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsBlockWalletsPage()).forward(request, response);
					}finally {
						if(arrWalletDetails !=null) arrWalletDetails = null; if(CustomerCode != null) CustomerCode=null;	
						if(customerId != null) customerId=null;	if(custPhoneNo != null) custPhoneNo=null;
						if(custName != null) custName = null; if (arrBlockDetails!=null)arrBlockDetails=null;
					  }						
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
				}
				break;
		}
		
	}

}
