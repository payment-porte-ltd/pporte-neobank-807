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
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.CustomerWalletDao;
import com.pporte.dao.OpsManageBusinessLedgerDao;
import com.pporte.model.AssetTransaction;
import com.pporte.model.AuditTrail;
import com.pporte.model.User;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;

public class OpsManageBusinessLedgerImpl implements Rules{
private static String className = OpsManageBusinessLedgerImpl.class.getSimpleName();

	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception{
		//HttpSession session = request.getSession(false);
		switch (rulesaction) {
		case "Business Ledger":
			try {
				String langPref = null;
				if(request.getParameter("hdnlangpref")!=null) langPref= request.getParameter("hdnlangpref");
				request.setAttribute("accruedbalance", (AssetTransaction)OpsManageBusinessLedgerDao.class.getConstructor().newInstance().getAccruedBalance());

				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "dash");
				request.setAttribute("lastrule", "Business Ledger");
				try {
					response.setContentType("text/html");
					ctx.getRequestDispatcher(NeoBankEnvironment.getOpsBusinessLedgerPage()).forward(request, response);
				} finally {
					if(langPref !=null)langPref=null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
			
		case "Audit Logs":
			try {
				String langPref = null;
				if(request.getParameter("hdnlangpref")!=null) langPref= request.getParameter("hdnlangpref");

				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "dash");
				request.setAttribute("lastrule", "Audit Logs");
				try {
					response.setContentType("text/html");
					ctx.getRequestDispatcher(NeoBankEnvironment.getOpsSystemAuditTrailPage()).forward(request, response);
				} finally {
					if(langPref !=null)langPref=null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		default:
			throw new IllegalArgumentException("Rule not defined value: " + rulesaction);
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
		
		case "get_business_ledger_txn":
			NeoBankEnvironment.setComment(3, className," Inside get_business_ledger_txn");

			try {
				JsonObject obj = new JsonObject();User user = null; String walletType ="F";
				PrintWriter output = null;List<AssetTransaction> transactionList = null;ConcurrentHashMap<String, String> hashTxnRules = null;
				Gson gson = new Gson();String txnDesc= null; String payType=null; ConcurrentHashMap<String, String> hashPayChannel = null;
				String pymtChannel=null;
				
				 if (session.getAttribute("SESS_USER") == null) throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				transactionList = (List<AssetTransaction>)OpsManageBusinessLedgerDao.class.getConstructor().newInstance().getBusinessLedgerTxn();

				hashTxnRules = (ConcurrentHashMap<String, String>)CustomerWalletDao.class.getConstructor().newInstance().getTransactionRules();
				NeoBankEnvironment.setComment(3, className," hashTxnRules is "+hashTxnRules.size());

				hashPayChannel = new ConcurrentHashMap<String, String>();
				
				hashPayChannel.put("W", "Wallet");
				hashPayChannel.put("T", "Token");
				hashPayChannel.put("C", "Crypto");
				
				

				if (transactionList!=null) {
					for (int i = 0; i <transactionList.size(); i++) {
						if (transactionList.get(i).getPayType()!=null) {
							payType= hashTxnRules.get(transactionList.get(i).getPayType());
							transactionList.get(i).setPayType(payType);
							
							pymtChannel = hashPayChannel.get(transactionList.get(i).getPymtChannel());
							transactionList.get(i).setPymtChannel(pymtChannel);
						
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
				Utilities.sendJsonResponse(response, "error", "Error in geting Transactions, Please try again letter");
			}
			break;
		case"get_business_ledger_txn_btn_dates":
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
				String payType=null; ConcurrentHashMap<String, String> hashPayChannel = null;
				String pymtChannel=null;
				
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
				
				transactionList = (List<AssetTransaction>)OpsManageBusinessLedgerDao.class.getConstructor()
						.newInstance().getOpsBusinessTxnBtnDates(dateFrom, dateTo);
				NeoBankEnvironment.setComment(3, className, "After fetch ");
				
				hashPayChannel = new ConcurrentHashMap<String, String>();
				
				
				hashPayChannel.put("W", "Wallet");
				hashPayChannel.put("T", "Token");
				hashPayChannel.put("C", "Crypto");
				
				if (transactionList!=null) {
					for (int i = 0; i <transactionList.size(); i++) {
						if (transactionList.get(i).getPayType()!=null) {
							payType= hashTxnRules.get(transactionList.get(i).getPayType());
							transactionList.get(i).setPayType(payType);
							
							pymtChannel = hashPayChannel.get(transactionList.get(i).getPymtChannel());
							transactionList.get(i).setPymtChannel(pymtChannel);
							
							
						}
					}
					obj.add("data", gson.toJsonTree(transactionList));
					obj.add("error", gson.toJsonTree("false"));
				}else {
					obj.add("data", gson.toJsonTree("nodata"));
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
				Utilities.sendJsonResponse(response, "error", "Error in get_business_ledger_txn_btn_dates , Please try again letter");
			}
			break;
		case"get_audit_trail_details":
			try {
				JsonObject obj = new JsonObject();User user = null;
				PrintWriter output = null;List<AuditTrail> auditTrailDetails = null;
				Gson gson = new Gson();String txnDesc= null;ConcurrentHashMap<String, String> hashUser = null;
				String userType=null;
				if (session.getAttribute("SESS_USER") == null) throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				auditTrailDetails = (List<AuditTrail>)OpsManageBusinessLedgerDao.class.getConstructor().newInstance().getAuditTrailDetails();
				
				hashUser = new ConcurrentHashMap<String, String>();
				hashUser.put("C", "Customer");
				hashUser.put("P", "Partner");
				hashUser.put("O", "Operator");
				hashUser.put("S", "Super Admin");

				if (auditTrailDetails!=null) {
					for (int i = 0; i <auditTrailDetails.size(); i++) {
						if (auditTrailDetails.get(i).getUserType()!=null) {
							userType = hashUser.get(auditTrailDetails.get(i).getUserType());
							auditTrailDetails.get(i).setUserType(userType);
						}
					}
					obj.add("data", gson.toJsonTree(auditTrailDetails));
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
					if (obj != null)obj = null;if (hashUser != null)hashUser = null; 
					if (auditTrailDetails != null)auditTrailDetails = null; 
					if (user != null)user = null; if (userType != null)userType = null; 
					if (txnDesc != null)txnDesc = null; 
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in get_audit_trail_details, Please try again letter");
			}
			break;

			
		case "get_audit_trail_btn_dates":
			try {
				JsonObject obj = new JsonObject();
				User user = null;
				PrintWriter output = null;List<AuditTrail> auditTrailDetails = null;
				ConcurrentHashMap<String, String> hashTxnRules = null;
				Gson gson = new Gson();
				String txnDesc= null;
				String dateTo= null;
				String dateFrom =null;ConcurrentHashMap<String, String> hashUser = null;
				String userType=null;
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
				
				auditTrailDetails = (List<AuditTrail>)OpsManageBusinessLedgerDao.class.getConstructor().newInstance().getFilteredAuditTrailDetails(dateFrom, dateTo);

				hashUser = new ConcurrentHashMap<String, String>();
				hashUser.put("C", "Customer");
				hashUser.put("P", "Partner");
				hashUser.put("O", "Operator");
				hashUser.put("S", "Super Admin");

				if (auditTrailDetails!=null) {
					for (int i = 0; i <auditTrailDetails.size(); i++) {
						if (auditTrailDetails.get(i).getUserType()!=null) {
							userType = hashUser.get(auditTrailDetails.get(i).getUserType());
							NeoBankEnvironment.setComment(3,className,"userType is "+userType);
							auditTrailDetails.get(i).setUserType(userType);
						}
					}
					obj.add("data", gson.toJsonTree(auditTrailDetails));
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
					if (auditTrailDetails != null)auditTrailDetails = null; 
					if (user != null)user = null; if (hashUser != null)hashUser = null; 
					if (dateTo != null)dateTo = null; if (userType != null)userType = null; 
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
		
			default:
			throw new IllegalArgumentException("Rule not defined value: " + rulesaction);
		}
	}



}
