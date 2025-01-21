package com.pporte.rules;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.model.BlockchainDetails;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;

public class OpsManageBlockchainRulesImpl implements Rules{
	private static String className = OpsManageBlockchainRulesImpl.class.getSimpleName();
	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response, ServletContext ctx)
			throws Exception {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null) Utilities.callException(request, response, ctx, "Session has expired, please log in again");
		switch (rulesaction) {
			case "Blockchain Transactions":
				try {
					request.setAttribute("lastaction", "blockchain");
					request.setAttribute("lastrule", "Blockchain Transactions");
					response.setContentType("text/html");
					try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewBlockchainTransactionsPage()).forward(request, response);
					} finally {
						
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
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
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null) Utilities.callException(request, response, ctx, "Session has expired, please log in again");
		switch (rulesaction) {
			
		case"opsblockchaintxn":
			try {
				NeoBankEnvironment.setComment(3, className," Inside opsblockchaintxn ");

				String countValue=null;JsonObject obj = new JsonObject();PrintWriter output = null;Gson gson = new Gson();
				JsonArray jsonarray=null; String buttonValue=null;String userType=null;ArrayList<BlockchainDetails> arrTxnList=null;
				BlockchainDetails m_txnDetail=null; ConcurrentHashMap<String,String> hashMode=null;
				hashMode= new ConcurrentHashMap<String,String>(); hashMode.put("D", "Debit"); hashMode.put("C", "Credit");
				String txnDateTime []=null;String txnCode []=null;String sysReference []=null;String txnMode []=null;
				String currencyId []=null;String txnAmount []=null;
				

				
				if(request.getParameter("countvalue")!=null) countValue = request.getParameter("countvalue").trim();
				if(request.getParameter("buttonvalue")!=null) buttonValue = request.getParameter("buttonvalue").trim();
				if(request.getParameter("usertype")!=null) userType = request.getParameter("usertype").trim();

				NeoBankEnvironment.setComment(3, className," Inside countValue "+countValue+" buttonValue "+buttonValue+" userType "+userType);

				
				if (session.getAttribute("SESS_USER") == null) 
					throw new Exception ("Session has expired, please log in again");

				SystemUtilsDao.class.getConstructor().newInstance();
				jsonarray=(JsonArray)SystemUtilsDao.getTransactionsInBlockchain(buttonValue,userType,countValue);
				NeoBankEnvironment.setComment(3, className," jsonArray "+jsonarray.toString());
				if (jsonarray!=null) {
					arrTxnList = new ArrayList<BlockchainDetails>();
					for(int i=0;i<jsonarray.size();i++){ 
					 	String datagson = Utilities.hexToASCII(jsonarray.get(i).getAsJsonObject().get("data").getAsString());
						 JsonObject linejsonobj = JsonParser.parseString(datagson).getAsJsonObject();
						 m_txnDetail = new BlockchainDetails();
						 m_txnDetail.setTxnDateTime(linejsonobj.get("txndetails").getAsJsonObject().get("txndatetime").getAsString());
						 m_txnDetail.setTxnCode( linejsonobj.get("txndetails").getAsJsonObject().get("txncode").getAsString());
						 m_txnDetail.setSystemReference( linejsonobj.get("txndetails").getAsJsonObject().get("sysreference").getAsString());
						 m_txnDetail.setTxnMode( hashMode.get(linejsonobj.get("txndetails").getAsJsonObject().get("txnmode").getAsString()));
						 m_txnDetail.setTxnCurrencyId(linejsonobj.get("txndetails").getAsJsonObject().get("txncurrencyid").getAsString());
						 m_txnDetail.setTxnAmount(Utilities.getMoneyinDecimalFormat( linejsonobj.get("txndetails").getAsJsonObject().get("txnamount").getAsString()));
						 arrTxnList.add(m_txnDetail);
						}
					
					Collections.reverse(arrTxnList);// This is to order the resulting transaction from ascending order to descending order
					}
						
						 obj.add("data", gson.toJsonTree(arrTxnList));
						 obj.add("error", gson.toJsonTree("false"));		
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));
				} finally {
					if(output!=null) output.close(); 
					if(gson!=null) gson=null; 	
					if(jsonarray!=null) jsonarray=null;	
					if(txnDateTime!=null) txnDateTime=null;	if(txnCode!=null) txnCode=null; 	if(sysReference!=null) sysReference=null;
					if(txnAmount!=null) txnAmount=null;	if(txnMode!=null) txnMode=null; 	if(currencyId!=null) currencyId=null;
					if (arrTxnList!=null) arrTxnList=null; if (m_txnDetail!=null)m_txnDetail=null; if(userType!=null)userType=null;
					if (hashMode!=null)hashMode=null; 
					if (countValue!=null)countValue=null; if (buttonValue!=null)buttonValue=null;
				}	
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in opsblockchaintxn, Please try again letter");
			}
			break;
		
			
		case"opsgetblockchaininfo":
			try {
				JsonObject obj = new JsonObject();PrintWriter output = null;Gson gson = new Gson();
				JsonArray jsonarray=null;String custRecordInfo=null;
				NeoBankEnvironment.setComment(3, className," Inside opsgetblockchaininfo ");
				PrintWriter jsonOutput_1 = null;
				jsonOutput_1 = response.getWriter();
				if (session.getAttribute("SESS_USER") == null) 
					throw new Exception ("Session has expired, please log in again");
				NeoBankEnvironment.setComment(3, className,"Before jsonarray ");

				jsonarray=(JsonArray)SystemUtilsDao.class.getConstructor().newInstance().getBlockchainRecordInfoCustomerLedger();
				NeoBankEnvironment.setComment(2, className, "jsonarrayCust is  "+	jsonarray);
				if (jsonarray!=null) {
					//PPWalletEnvironment.setComment(2, className, "Json size  "+jsonarray.size());
					 for(int i=0;i<jsonarray.size();i++){ 
						 	 custRecordInfo = jsonarray.get(i).getAsJsonObject().get("confirmed").toString();
						 	 
					 }
					 
					 NeoBankEnvironment.setComment(2, className, "custRecordInfo is  "+custRecordInfo);
					 obj.add("data", gson.toJsonTree(custRecordInfo));
	                 obj.add("error", gson.toJsonTree("false"));	
					
				}else {
					obj.add("error", gson.toJsonTree("true"));
					obj.add("message", gson.toJsonTree("No data present"));
				}try {
					NeoBankEnvironment.setComment(2, className, "Going to front end "+custRecordInfo);
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					jsonOutput_1.print(gson.toJson(obj));
				} finally {
					if(output!=null) output.close(); 
					if(gson!=null) gson=null; 	
					if(jsonarray!=null) jsonarray=null;	if (custRecordInfo!=null)custRecordInfo=null;
					
				}

			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in opsgetblockchaininfo, Please try again letter");
			}
			break;
	}
  }
}
