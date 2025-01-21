package com.pporte.rules;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.CustomerWalletDao;
import com.pporte.dao.OpsFiatWalletDao;
import com.pporte.dao.OpsManageWalletDao;
import com.pporte.model.AssetTransaction;
import com.pporte.model.AssetTransactions;
import com.pporte.model.Customer;
import com.pporte.model.Transaction;
import com.pporte.model.User;
import com.pporte.utilities.StellarSDKUtility;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;

public class OpsManageTransactionsImpl implements Rules{
private static String className = OpsManageTransactionsImpl.class.getSimpleName();

	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception{
		//HttpSession session = request.getSession(false);
		switch (rulesaction) {
		case "Fiat Wallet Transactions":
			try {
				request.setAttribute("lastaction", "opstxn");
				request.setAttribute("lastrule", "Fiat Wallet Transactions");
				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getOpsFiatWalletTransactionsPage()).forward(request, response);
				} finally {
					
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		case "Crypto Wallets Transactions":
			try {
				request.setAttribute("lastaction", "opstxn");
				request.setAttribute("lastrule", "Crypto Wallets Transactions");
				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getOptWalsCryptoletTransactionsPage()).forward(request, response);
				} finally {
					
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		case "Card Wallets Transactions":
			try {
				request.setAttribute("lastaction", "opstxn");
				request.setAttribute("lastrule", "Card Wallets Transactions");
				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getOptWalsCardTransactionsPage()).forward(request, response);
				} finally {
					
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
			
		case "Porte Transactions":
			try {
				NeoBankEnvironment.setComment(3, className, " Inside porte transactions ");
				ArrayList <Customer> arrCustomerDetails = null;
				arrCustomerDetails = (ArrayList<Customer>)OpsManageWalletDao.class.getConstructor().newInstance().getViewAllPorteTxnDetails(
						);
				request.setAttribute("lastaction", "opstxn");
				request.setAttribute("lastrule", "Porte Transactions");
				request.setAttribute("portetxn", arrCustomerDetails);
				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getOpsGetPorteTransactions()).forward(request, response);
				} finally {
					
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		case "porte_transactions":
			try {
				String publicKey = null; String customerName = null; String relationshipNo = null; 
				if(request.getParameter("hdnpublickey")!=null)	publicKey = request.getParameter("hdnpublickey").trim();
				if(request.getParameter("hdncustname")!=null)	customerName = request.getParameter("hdncustname").trim();
				if(request.getParameter("hdnrelno")!=null)	relationshipNo = request.getParameter("hdnrelno").trim();
				
				NeoBankEnvironment.setComment(3, className, "publicKey is "+publicKey+ "customerName is "+customerName+" relationshipNo is "+relationshipNo);

				request.setAttribute("lastaction", "opstxn");
				request.setAttribute("lastrule", "Porte Transactions");
				request.setAttribute("custname", customerName);
				request.setAttribute("relNo", relationshipNo);
				request.setAttribute("publickey", publicKey);
				 ArrayList<AssetTransactions> assetTransactions = StellarSDKUtility.getAccountTransactions(publicKey, "200");
				request.setAttribute("stelartxn", assetTransactions );
				response.setContentType("text/html");
				try {
					NeoBankEnvironment.setComment(3, className, "Going to jsp");

					ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewSpecificUserPortTxn()).forward(request, response);
				} finally {
					if(assetTransactions !=null)assetTransactions=null;
					if(publicKey !=null)publicKey=null;
					if(customerName !=null)customerName=null;
					if(relationshipNo !=null)relationshipNo=null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		case "view_more_porte_txn_details":
			try {
				NeoBankEnvironment.setComment(3, className, "insude view_more_porte_txn_details ");

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
					request.setAttribute("lastaction", "opstxn");
					request.setAttribute("lastrule", "Porte Transactions");
					
					//userId=((User) session.getAttribute("SESS_USER")).getUserId();
					//userType=((User) session.getAttribute("SESS_USER")).getUserType();
					
					NeoBankEnvironment.setComment(3, className, "insude customerRelNo " + customerRelNo + " customerId " + customerId + 
							" custPhoneNo "+ custPhoneNo + " custName "+ custName);

					//request.setAttribute("portetxn", (List<Customer>)OpsManageWalletDao.class.getConstructor().newInstance().getStellarCustomers() );

					arrCustomerDetails = (ArrayList<Customer>)OpsManageWalletDao.class.getConstructor().newInstance().getSearchOpsSpecificPorteTxnDetails(
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
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsGetPorteTransactions()).forward(request, response);
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Exception in calling RequestDispatcher is "+e.getMessage());
				}finally {
					if(arrCustomerDetails !=null) arrCustomerDetails = null; if(customerRelNo != null) customerRelNo=null;	
					if(customerId != null) customerId=null;	if(custPhoneNo != null) custPhoneNo=null;
					if(custName != null) custName = null; 
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		
		default:
			throw new IllegalArgumentException("Rule not defined value: " + rulesaction);
		}
	}

	@Override
	public void performJSONOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
	}

	@SuppressWarnings("unused")
	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null) Utilities.callException(request, response, ctx, "Session has expired, please log in again");
		
		switch (rulesaction) {
		case"get_fiat_txn":
			try {
				JsonObject obj = new JsonObject();User user = null; String walletType ="F";
				PrintWriter output = null;List<AssetTransaction> transactionList = null;ConcurrentHashMap<String, String> hashTxnRules = null;
				Gson gson = new Gson();String txnDesc= null;
				
				 if (session.getAttribute("SESS_USER") == null) throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				transactionList = (List<AssetTransaction>)OpsFiatWalletDao.class.getConstructor().newInstance().getFiatWalletTxn(walletType);
				hashTxnRules = (ConcurrentHashMap<String, String>)CustomerWalletDao.class.getConstructor().newInstance().getTransactionRules();
				NeoBankEnvironment.setComment(3, className," hashTxnRules is "+hashTxnRules.size());

				if (transactionList!=null) {
					for (int i = 0; i <transactionList.size(); i++) {
						if (transactionList.get(i).getSystemReferenceInt()!=null) {
							txnDesc= hashTxnRules.get(transactionList.get(i).getSystemReferenceInt().split("-")[0]);
							transactionList.get(i).setTxnDescription(txnDesc);
						}
					}
					obj.add("data", gson.toJsonTree(transactionList));
					obj.add("error", gson.toJsonTree("false"));
				}else {
					obj.add("error", gson.toJsonTree("nodata"));
				}
				
				
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));
				} finally {
					if (output != null)output.close();if (gson != null)gson = null;
					if (obj != null)obj = null;
					if (transactionList != null)transactionList = null; 
					if (user != null)user = null; 
					if (hashTxnRules != null)hashTxnRules = null; 
					if (txnDesc != null)txnDesc = null; 
					if (walletType != null)walletType = null; 
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in geting Fiat Transactions, Please try again letter");
			}
			break;
			
		case "get_fiat_txn_btn_dates":
			try {
				JsonObject obj = new JsonObject();
				User user = null;
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
				hashTxnRules = (ConcurrentHashMap<String, String>)CustomerWalletDao.class.getConstructor()
						.newInstance().getTransactionRules();
				
				
				Date d = sdf.parse(dateFrom); Date d2 = sdf.parse(dateTo);
				sdf.applyPattern(newFormat);  sdf.applyPattern(newFormat);
				dateFrom = sdf.format(d);     dateTo = sdf.format(d2);
				
				NeoBankEnvironment.setComment(3, className, "dateFrom "+ dateFrom);
				NeoBankEnvironment.setComment(3, className, "dateTo "+ dateTo);
				
				transactionList = (List<AssetTransaction>)OpsFiatWalletDao.class.getConstructor()
						.newInstance().getOpsFiatWalletTxnBtnDates(dateFrom, dateTo);
				NeoBankEnvironment.setComment(3, className, "After fetch ");
				if (transactionList!=null) {
					for (int i = 0; i <transactionList.size(); i++) {
						if (transactionList.get(i).getSystemReferenceInt()!=null) {
							txnDesc= hashTxnRules.get(transactionList.get(i).getSystemReferenceInt().split("-")[0]);
							transactionList.get(i).setTxnDescription(txnDesc);
						}
					}
					obj.add("data", gson.toJsonTree(transactionList));
					obj.add("error", gson.toJsonTree("false"));
				}else {
					obj.add("error", gson.toJsonTree("nodata"));
				}
				
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));
				} finally {
					if (output != null)output.close(); if (gson != null)gson = null;
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
				Utilities.sendJsonResponse(response, "error", "Error in geting FiatTransactions by date, Please try again letter");
			}
			break;
		case "get_card_txn_btn_dates":
			try {
				JsonObject obj = new JsonObject();
				User user = null;
				PrintWriter output = null;
				ArrayList<AssetTransaction> transactionList = null;
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
				hashTxnRules = (ConcurrentHashMap<String, String>)CustomerWalletDao.class.getConstructor()
						.newInstance().getTransactionRules();
				
				
				Date d = sdf.parse(dateFrom); Date d2 = sdf.parse(dateTo);
				sdf.applyPattern(newFormat);  sdf.applyPattern(newFormat);
				dateFrom = sdf.format(d);     dateTo = sdf.format(d2);
				
				NeoBankEnvironment.setComment(3, className, "dateFrom "+ dateFrom);
				NeoBankEnvironment.setComment(3, className, "dateTo "+ dateTo);
				
				transactionList = (ArrayList<AssetTransaction>)OpsFiatWalletDao.class.getConstructor()
						.newInstance().getOpsCardTxnBtnDates(dateFrom, dateTo);
				NeoBankEnvironment.setComment(3, className, "After fetch ");
				if (transactionList!=null) {
					for (int i = 0; i <transactionList.size(); i++) {
						if (transactionList.get(i).getSystemReferenceInt()!=null) {
							txnDesc= hashTxnRules.get(transactionList.get(i).getSystemReferenceInt().split("-")[0]);
							transactionList.get(i).setTxnDescription(txnDesc);
						}
					}
					obj.add("data", gson.toJsonTree(transactionList));
					obj.add("error", gson.toJsonTree("false"));
				}else {
					obj.add("error", gson.toJsonTree("nodata"));
				}
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));
				} finally {
					if (output != null)output.close(); if (gson != null)gson = null;
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
				Utilities.sendJsonResponse(response, "error", "Error in geting card transactions by date, Please try again letter");
			}
			break;
		case "get_auth_txn_btn_dates":
			try {
				JsonObject obj = new JsonObject();
				User user = null;
				PrintWriter output = null;
				ArrayList<Transaction> transactionList = null;
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
				
				
				Date d = sdf.parse(dateFrom); Date d2 = sdf.parse(dateTo);
				sdf.applyPattern(newFormat);  sdf.applyPattern(newFormat);
				dateFrom = sdf.format(d);     dateTo = sdf.format(d2);
				
				NeoBankEnvironment.setComment(3, className, "dateFrom "+ dateFrom);
				NeoBankEnvironment.setComment(3, className, "dateTo "+ dateTo);
				
				transactionList = (ArrayList<Transaction>)OpsFiatWalletDao.class.getConstructor()
						.newInstance().getWalletAuthorizationsTransactionsBtwDates(dateFrom, dateTo);
				if (transactionList!=null) {
					obj.add("data", gson.toJsonTree(transactionList));
					obj.add("error", gson.toJsonTree("false"));
				}else {
					obj.add("error", gson.toJsonTree("nodata"));
				}
				
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));
				} finally {
					if (output != null)output.close(); if (gson != null)gson = null;
					if (obj != null)obj = null; if (dateFrom != null)dateFrom = null;
					if (transactionList != null)transactionList = null; if (user != null)user = null; 
					if (dateTo != null)dateTo = null; if (txnDesc != null)txnDesc = null; 
					if (sdf != null)sdf = null; if (d != null)d = null; 
					if (d2 != null)d2  = null; 
				}								
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in getting authorization txns by date, Please try again letter");
			}
			break;
			
		case"get_crpto_txn":
			try {
				JsonObject obj = new JsonObject();User user = null; 
				PrintWriter output = null;List<AssetTransaction> transactionList = null;ConcurrentHashMap<String, String> hashTxnRules = null;
				Gson gson = new Gson();String txnDesc= null;
				
				 if (session.getAttribute("SESS_USER") == null) throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				transactionList = (List<AssetTransaction>)OpsFiatWalletDao.class.getConstructor().newInstance().getCryptoWalletTxn();
				hashTxnRules = (ConcurrentHashMap<String, String>)CustomerWalletDao.class.getConstructor().newInstance().getTransactionRules();

				if (transactionList!=null) {
					for (int i = 0; i <transactionList.size(); i++) {
						if (transactionList.get(i).getSystemReferenceInt()!=null) {
							txnDesc= hashTxnRules.get(transactionList.get(i).getSystemReferenceInt().split("-")[0]);
							transactionList.get(i).setTxnDescription(txnDesc);
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
					if (output != null)output.close();if (gson != null)gson = null;
					if (obj != null)obj = null;
					if (transactionList != null)transactionList = null; 
					if (user != null)user = null; 
					if (hashTxnRules != null)hashTxnRules = null; 
					if (txnDesc != null)txnDesc = null; 
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in geting CryptoTransactions, Please try again letter");
			}
			break;
		case "get_crpto_txn_btn_dates":
			try {
				JsonObject obj = new JsonObject();User user = null;PrintWriter output = null;
				List<AssetTransaction> transactionList = null;ConcurrentHashMap<String, String> hashTxnRules = null;
				Gson gson = new Gson();String txnDesc= null;String dateTo= null;String dateFrom =null;
				String oldFormat = "MM/dd/yyyy";String newFormat = "yyyy-MM-dd";
				SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
				
				if(request.getParameter("datefrom")!=null)dateFrom = request.getParameter("datefrom").trim();
				if(request.getParameter("dateto")!=null)dateTo = request.getParameter("dateto").trim();
				
				 if (session.getAttribute("SESS_USER") == null) throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				hashTxnRules = (ConcurrentHashMap<String, String>)CustomerWalletDao.class.getConstructor().newInstance().getTransactionRules();
				
				Date d = sdf.parse(dateFrom); Date d2 = sdf.parse(dateTo);
				sdf.applyPattern(newFormat);  sdf.applyPattern(newFormat);
				dateFrom = sdf.format(d);     dateTo = sdf.format(d2);
				
				NeoBankEnvironment.setComment(3, className, "dateFrom "+ dateFrom);
				NeoBankEnvironment.setComment(3, className, "dateTo "+ dateTo);
				
				transactionList = (List<AssetTransaction>)OpsFiatWalletDao.class.getConstructor()
						.newInstance().getOpsFiatWalletTxnBtnDates(dateFrom, dateTo);
				NeoBankEnvironment.setComment(3, className, "After fetch ");
				if (transactionList!=null) {
					for (int i = 0; i <transactionList.size(); i++) {
						if (transactionList.get(i).getSystemReferenceInt()!=null) {
							txnDesc= hashTxnRules.get(transactionList.get(i).getSystemReferenceInt().split("-")[0]);
							transactionList.get(i).setTxnDescription(txnDesc);
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
					if (output != null)output.close(); if (gson != null)gson = null;
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
				Utilities.sendJsonResponse(response, "error", "Error in geting Crypto Transactions by date, Please try again letter");
			}
			break;
			
		case"get_card_wallet_txn":
			try {
				JsonObject obj = new JsonObject();User user = null; 
				PrintWriter output = null;List<AssetTransaction> transactionList = null;ConcurrentHashMap<String, String> hashTxnRules = null;
				Gson gson = new Gson();String txnDesc= null;
				
				 if (session.getAttribute("SESS_USER") == null) throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				transactionList = (List<AssetTransaction>)OpsFiatWalletDao.class.getConstructor().newInstance().getCardWalletTxn();
				hashTxnRules = (ConcurrentHashMap<String, String>)CustomerWalletDao.class.getConstructor().newInstance().getTransactionRules();

				if (transactionList!=null) {
					for (int i = 0; i <transactionList.size(); i++) {
						if (transactionList.get(i).getSystemReferenceInt()!=null) {
						//	NeoBankEnvironment.setComment(3, className," Inside  transaction list "+transactionList.get(i).getSystemReferenceInt());
							txnDesc= hashTxnRules.get(transactionList.get(i).getSystemReferenceInt().split("-")[0]);
							transactionList.get(i).setTxnDescription(txnDesc);
						}
					}
					obj.add("data", gson.toJsonTree(transactionList));
					obj.add("error", gson.toJsonTree("false"));
				}else {
					obj.add("error", gson.toJsonTree("nodata"));
				}
				
				
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));
				} finally {
					if (output != null)output.close();if (gson != null)gson = null;
					if (obj != null)obj = null;
					if (transactionList != null)transactionList = null; 
					if (user != null)user = null; 
					if (hashTxnRules != null)hashTxnRules = null; 
					if (txnDesc != null)txnDesc = null; 
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in geting Card Wallet Transactions, Please try again letter");
			}
			break;
		case "search_specific_wallet":
			try {				
				String langPref = null;
				String customerRelNo = ""; String customerId = ""; String custPhoneNo = ""; String custName=""; 
				ArrayList<AssetTransaction> transactionList=null;
				JsonObject obj = new JsonObject();PrintWriter output = null; Gson gson = new Gson();
				ConcurrentHashMap<String, String> hashTxnRules = null;
				
				if (request.getParameter("hdnlangpref") != null)    	langPref = request.getParameter("hdnlangpref").trim();
				if (request.getParameter("searchbyrelno") != null) 		customerRelNo = request.getParameter("searchbyrelno").trim();
				if (request.getParameter("searchbycustid") != null) 		customerId = request.getParameter("searchbycustid").trim();
				if (request.getParameter("searchmobileno") != null) 		custPhoneNo = request.getParameter("searchmobileno").trim();
				if (request.getParameter("searchbycustname") != null) 		custName = request.getParameter("searchbycustname").trim();
				
				NeoBankEnvironment.setComment(3, className, "insude customerRelNo " + customerRelNo + " customerId " + customerId + 
						" custPhoneNo "+ custPhoneNo + " custName "+ custName);
				  if (session.getAttribute("SESS_USER") == null) throw new Exception ("Session has expired, please log in again");
				
					if(customerRelNo.equals("") && customerId.equals("") &&custPhoneNo.equals("") && custName.equals("")) {
						throw new Exception("All Search criteria cannot be blank");
					}
					
					
					NeoBankEnvironment.setComment(3, className, "insude customerRelNo " + customerRelNo + " customerId " + customerId + 
							" custPhoneNo "+ custPhoneNo + " custName "+ custName);
					hashTxnRules = (ConcurrentHashMap<String, String>)CustomerWalletDao.class.getConstructor().newInstance().getTransactionRules();					
					transactionList = (ArrayList<AssetTransaction>)OpsFiatWalletDao.class.getConstructor().newInstance().searchForWallet(
							custName, customerRelNo,customerId,custPhoneNo);
					if(transactionList !=null ) {
						NeoBankEnvironment.setComment(3, className, "customer found "+ transactionList.size());
						obj.add("data", gson.toJsonTree(transactionList));
						obj.add("error", gson.toJsonTree("false"));
					}else {
						NeoBankEnvironment.setComment(3, className, "Exception is customer details not available ");										
						obj.add("error", gson.toJsonTree("nodata"));
					}
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));
				} finally {
					if (output != null)output.close();if (gson != null)gson = null;
					if (obj != null)obj = null;
					if(transactionList !=null) transactionList = null; if(customerRelNo != null) customerRelNo=null;	
					if(customerId != null) customerId=null;	if(custPhoneNo != null) custPhoneNo=null;
					if(custName != null) custName = null; if (hashTxnRules!=null)hashTxnRules=null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in searching wallet transactions, Please try again letter");
			}
			break;
			
			default:
			throw new IllegalArgumentException("Rule not defined value: " + rulesaction);
		}
	}



}
