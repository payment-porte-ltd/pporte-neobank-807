package com.pporte.rules;

import java.io.PrintWriter;
import java.util.ArrayList;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.OpsManageCustomerDao;
import com.pporte.dao.OpsManageDashboardDao;
import com.pporte.model.Customer;
import com.pporte.model.DisputeTracker;
import com.pporte.model.OpsDashboard;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;

public class OpsDashboardRulesImpl implements Rules{
	private static String className = OpsDashboardRulesImpl.class.getSimpleName();

	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx)	throws Exception {
		switch (rulesaction) {
		
			case "Ops Dashboard":
				try {
					String langPref = null; OpsDashboard totalUsers=null;
					OpsDashboard fiatWalletBalance=null;OpsDashboard totaldisputes=null;
					if(request.getParameter("hdnlang")!=null)			  langPref = request.getParameter("hdnlang").trim();
					
					totalUsers =	(OpsDashboard) OpsManageDashboardDao.class.getConstructor().newInstance().getTotalUsers();
					fiatWalletBalance=(OpsDashboard) OpsManageDashboardDao.class.getConstructor().newInstance().getFiatBalanceDetails();
					totaldisputes=(OpsDashboard) OpsManageDashboardDao.class.getConstructor().newInstance().getTotalDisputes();
					NeoBankEnvironment.setComment(1, className, "totalUsers are  "+totalUsers.getTotalUsers() );
					
					ArrayList<Customer> arrPendingCustomers = (ArrayList<Customer>)OpsManageCustomerDao.class.getConstructor().newInstance().getPendingCustomers();
					ArrayList<DisputeTracker>arrDisputes = (ArrayList<DisputeTracker>) OpsManageCustomerDao.class.getConstructor().newInstance().getActiveDisputes();
					
					request.setAttribute("pendingcustomers",arrPendingCustomers );
					request.setAttribute("tenlatestactivedisputes", arrDisputes);
					request.setAttribute("fiatwalletbal",fiatWalletBalance );
					request.setAttribute("totaldisputes",totaldisputes );
					request.setAttribute("totalusers",totalUsers );
					request.setAttribute("langPref", langPref);
					request.setAttribute("lastaction", "dash");
					request.setAttribute("lastrule", "Ops Dashboard");

					//TODO Take dashboard Parameters and data here				
					response.setContentType("text/html");
					 try {
						 ctx.getRequestDispatcher(NeoBankEnvironment.getOperationsDashboardPage()).forward(request, response);
					} finally {
						if(langPref !=null) langPref = null; if (totalUsers!=null) totalUsers=null;
						if (fiatWalletBalance!=null) fiatWalletBalance=null;  if (totaldisputes!=null) totaldisputes=null;
						if (arrPendingCustomers!=null) arrPendingCustomers=null; if (arrDisputes!=null) arrDisputes=null;
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
				}
				break;
		}
	}
	
	
	@Override
	public void performJSONOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			 ServletContext ctx) throws Exception {
		switch (rulesaction) {
		case "ops_businessledger_graph":
			try {
				NeoBankEnvironment.setComment(3, className," Inside ops_businessledger_graph");

				String relationshipNo=null;String langPref=null;
				PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
				
				if(request.getParameter("hdnlangpref")!=null)langPref = request.getParameter("hdnlangpref").trim();
								
				ArrayList<String> arrMonthlyTransaction  = (ArrayList<String>)OpsManageDashboardDao.class.getConstructor()
						.newInstance().getBussinessLedgerTxnBtwnDates();
				NeoBankEnvironment.setComment(3, className," After Arraylist");

				if(arrMonthlyTransaction!=null) {
					obj.add("error", gson.toJsonTree("false"));
					obj.add("data", gson.toJsonTree(arrMonthlyTransaction));
				}else{
					NeoBankEnvironment.setComment(3, className," Error is no data");
					obj.add("error", gson.toJsonTree("nodata"));
				}	
				try {
					NeoBankEnvironment.setComment(3, className," getfiatmonthlyforgraph String is " + gson.toJson(obj));
					out_json.print(gson.toJson(obj)); if(arrMonthlyTransaction !=null) arrMonthlyTransaction=null;
				} finally {
					if(langPref !=null) langPref = null;if (out_json != null)out_json.close();
					if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
					if (obj != null)obj = null;
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
}
