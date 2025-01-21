package com.pporte.rules;


import java.io.PrintWriter;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.OpsManagePricingPlanDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.model.PricingPlan;
import com.pporte.model.User;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;

public class OpsManagePricingPlanRulesImpl implements Rules{
private static String className = OpsManagePricingPlanRulesImpl.class.getSimpleName();

	@Override
	public void performOperation(String ruleaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception{
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null)
			Utilities.callOpsException(request, response, ctx, "Session has expired, please log in again");		
		switch (ruleaction) {

		case "Product Plans":
			try {
				request.setAttribute("lastaction", "opspln");
				request.setAttribute("lastrule", ruleaction);
				response.setContentType("text/html");
				request.setAttribute("pricingplans", (ArrayList<PricingPlan>) OpsManagePricingPlanDao.class.getConstructor()
						.newInstance().getCustomerPricingPlan());
				try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsManagePricingPlansPage()).forward(request, response);
				} finally {
					// nothing to flush here
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+ruleaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}		
		break;	

		case "Customer Plan Allocation":
			try {
				request.setAttribute("lastaction", "opspln");
				request.setAttribute("lastrule", ruleaction);
				response.setContentType("text/html");
				request.setAttribute("pricingplans", (ArrayList<PricingPlan>) OpsManagePricingPlanDao.class.getConstructor()
						.newInstance().getCustomerPricingPlan());
				try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsPricingPlansAllocatePage()).forward(request, response);
				} finally {
					// nothing to flush here
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+ruleaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}	
		break;	

		default:
			throw new IllegalArgumentException("Rule not defined value: " + ruleaction);
		}
	}

	@Override
	public void performJSONOperation(String arg0, HttpServletRequest arg1, HttpServletResponse arg2,
			ServletContext arg3) throws Exception {
	
	}

	@SuppressWarnings("unused")
	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		HttpSession session = request.getSession(false);
		switch (rulesaction) {
		

		case "opsaddnewplan":
			try {
				PrintWriter out_json = response.getWriter();  JsonObject obj = new JsonObject();boolean success=false; 
				String addPlanName = null;String addPlanStatus = null; String addPlanPrice = null; String addPlanID=null;
				String addPlanDesc1 = ""; String addPlanDesc2 = ""; String addPlanDesc3 = ""; String addPlanDesc4 = ""; 
				User user=null; String userId=null;
				user = (User) session.getAttribute("SESS_USER");
				userId = user.getUserId();
				if(request.getParameter("addplansname")!=null) 		addPlanName = StringUtils.trim(request.getParameter("addplansname"));
				if(request.getParameter("hdnadduplanstatus")!=null) addPlanStatus = StringUtils.trim(request.getParameter("hdnadduplanstatus"));
				if(request.getParameter("addplanprice")!=null) addPlanPrice = StringUtils.trim(request.getParameter("addplanprice"));
				if(request.getParameter("addplanid")!=null) addPlanID = StringUtils.trim(request.getParameter("addplanid"));
				
				
				if(request.getParameter("addplandesc1")!=null) addPlanDesc1 = StringUtils.trim(request.getParameter("addplandesc1"));
				if(request.getParameter("addplandesc2")!=null) addPlanDesc2 = StringUtils.trim(request.getParameter("addplandesc2"));
				if(request.getParameter("addplandesc3")!=null) addPlanDesc3 = StringUtils.trim(request.getParameter("addplandesc3"));
				if(request.getParameter("addplandesc4")!=null) addPlanDesc4 = StringUtils.trim(request.getParameter("addplandesc4"));
				
				NeoBankEnvironment.setComment(3,className, "addPlanName "+addPlanName
						+" addPlanStatus "+addPlanStatus+" addPlanPrice "+addPlanPrice+" addPlanDesc1 "+
						addPlanDesc1+" addPlanDesc2 "+addPlanDesc2 +" addPlanDesc3 "+addPlanDesc3+" addPlanDesc4 "+addPlanDesc4);
				
				success = (boolean)OpsManagePricingPlanDao.class.getConstructor().newInstance()
								.addPlanDetails(addPlanName,addPlanStatus, addPlanPrice,
										addPlanDesc1, addPlanDesc2, addPlanDesc3, addPlanDesc4,addPlanID);	
				if(success) {
					// Audit trail
					//OCP --> Operation add new plan pricing plan for customer
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, "O",
							"OAN"," Operation add new plan, planId:- "+addPlanID);
					obj.addProperty("error", "false"); 
	        		obj.addProperty("message", "Update Success"); 
				}else {
					obj.addProperty("error", "true"); 
	        		obj.addProperty("message", "Update failed");
				}
				try {
					out_json = response.getWriter();
					out_json.print(obj);
				}finally {
					if(addPlanName!=null) addPlanName=null;if(addPlanStatus!=null) addPlanStatus=null; if(obj!=null) obj = null;if(out_json!=null) out_json.close();
					if(addPlanDesc1!=null) addPlanDesc1=null; if(addPlanDesc2!=null) addPlanDesc2=null; if(addPlanDesc3!=null) addPlanDesc3=null; if(addPlanDesc4!=null) addPlanDesc4=null;
					if (userId!=null)userId=null; if (user!=null)user=null;
				}				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in adding Plans, Please try again letter");
			}			
			break;			
		case "opseditplanupdate":
			try {
				PrintWriter out_json = response.getWriter();  JsonObject obj = new JsonObject();boolean success=false; 
				String editPlanId = null; String editPlanName = null;String editPlanStatus = null;	String editPlanPrice = null;
				String editPlanDesc1 = "";	String editPlanDesc2 = "";	String editPlanDesc3 = "";	String editPlanDesc4 = "";
				User user=null; String userId=null;
				user = (User) session.getAttribute("SESS_USER");
				userId = user.getUserId();
				if(request.getParameter("hdneditplanid")!=null) editPlanId = StringUtils.trim(request.getParameter("hdneditplanid"));
				if(request.getParameter("editplanname")!=null) editPlanName = StringUtils.trim(request.getParameter("editplanname"));
				if(request.getParameter("hdneditplanstatus")!=null) editPlanStatus = StringUtils.trim(request.getParameter("hdneditplanstatus"));
				if(request.getParameter("editplanprice")!=null) editPlanPrice = StringUtils.trim(request.getParameter("editplanprice"));
				
				if(request.getParameter("editplandesc1")!=null) editPlanDesc1 = StringUtils.trim(request.getParameter("editplandesc1"));
				if(request.getParameter("editplandesc2")!=null) editPlanDesc2 = StringUtils.trim(request.getParameter("editplandesc2"));
				if(request.getParameter("editplandesc3")!=null) editPlanDesc3 = StringUtils.trim(request.getParameter("editplandesc3"));
				if(request.getParameter("editplandesc4")!=null) editPlanDesc4 = StringUtils.trim(request.getParameter("editplandesc4"));
				
				NeoBankEnvironment.setComment(3, className, "editPlanId "+
				editPlanId+" editPlanName "+editPlanName+" editPlanStatus");
				
				success = (boolean)OpsManagePricingPlanDao.class.getConstructor().newInstance()
						.updatePricingPlanDetails(editPlanId, editPlanName,editPlanStatus, editPlanPrice,
								editPlanDesc1, editPlanDesc2, editPlanDesc3, editPlanDesc4);				
				if(success) {
					// Audit trail
					//OCP --> Operation edit  pricing plan for users
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, "O",
							"OEP"," Operation editing planId:- "+editPlanId);
					obj.addProperty("error", "false"); 
	        		obj.addProperty("message", "Update Success"); 
				}else {
					obj.addProperty("error", "true"); 
	        		obj.addProperty("message", "Update failed");
				}
				try {
					out_json = response.getWriter();
					out_json.print(obj);
				}finally {
					if(editPlanId!=null) editPlanId=null; if(editPlanName!=null) editPlanName=null;if(editPlanStatus!=null) editPlanStatus=null;if(obj!=null) obj = null;
					if(out_json!=null) out_json.close(); if (userId!=null)userId=null; if (user!=null)user=null;
				}				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in opseditplanupdate rule, Please try again letter");
			}
				
			break;
		case "opspriceplansearchcustomer":
			try {
				PrintWriter out_json = response.getWriter();  JsonObject obj = new JsonObject();boolean success=false; 
				String customerId = ""; PricingPlan pricing = null; Gson gson = new Gson();
				
				String relationshipNo = ""; String custPhoneNo = "";
				
				if (request.getParameter("searchbyrelno") != null) 		relationshipNo = request.getParameter("searchbyrelno").trim();
				if (request.getParameter("searchcustid") != null) 		customerId = request.getParameter("searchcustid").trim();
				if (request.getParameter("searchmobileno") != null) 		custPhoneNo = request.getParameter("searchmobileno").trim();
				
				NeoBankEnvironment.setComment(3,className, "customerId "+customerId+" relationshipNo "+relationshipNo+" custPhoneNo "+custPhoneNo);
				pricing = (PricingPlan)OpsManagePricingPlanDao.class.getConstructor().newInstance().getPricingPlanForCustomer(customerId,relationshipNo,custPhoneNo);
					if(pricing!=null) {
						obj.addProperty("error", "false"); 
		        		obj.addProperty("message", "Success"); 
		        		obj.add("data", gson.toJsonTree( pricing)); 
	        	 	        		
	        		}else {
						obj.addProperty("error", "nodata"); 
		        		obj.addProperty("message", "No data");
				}
				try {
					out_json = response.getWriter();
					NeoBankEnvironment.setComment(3, className," opspriceplansearchcustomer String is " + gson.toJson(obj));
					out_json.print(obj);
				}finally {
					if(pricing!=null) pricing=null; if(customerId!=null) customerId=null;if(obj!=null) obj = null;if(out_json!=null) out_json.close();
					if(gson!=null)gson=null; if(relationshipNo!=null)relationshipNo=null;if(custPhoneNo!=null)custPhoneNo=null;
					
					
			  }				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in opspriceplansearchcustomer rule, Please try again letter");
			}
			break;
		case "opsaddplanallocatecust":
			try { 
				//THIS RULEIS NOT IN USE
				PrintWriter out_json = response.getWriter();  JsonObject obj = new JsonObject();boolean success=false; 
				String planId = null; String customerId = null;	String status = null;			
				if(request.getParameter("hdneditplanid")!=null) planId = StringUtils.trim(request.getParameter("hdneditplanid"));
				if(request.getParameter("hdneditcustomerid")!=null) customerId = StringUtils.trim(request.getParameter("hdneditcustomerid"));
				if(request.getParameter("hdneditplanstatus")!=null) status = StringUtils.trim(request.getParameter("hdneditplanstatus"));
				
				NeoBankEnvironment.setComment(3,className, "planId "+planId+" customerId "+customerId+" status "+status);
				success = (boolean)OpsManagePricingPlanDao.class.getConstructor().newInstance()
						.updatePricingPlanForCustomer(planId, customerId, status);
				
				if(success) {
					obj.addProperty("error", "false"); 
	        		obj.addProperty("message", "Update Success");      			        		
				}else {
					obj.addProperty("error", "true"); 
	        		obj.addProperty("message", "Plan Not updated");
				}
				try {
					out_json = response.getWriter();
					out_json.print(obj);
				}finally {
					if(planId!=null) planId=null; if(customerId!=null) customerId=null; if(status!=null) status=null;if(obj!=null) obj = null;if(out_json!=null) out_json.close();
				}				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in updating rules, Please try again letter");
			}				
			break;
		case "opseditcustallocateupdate":
			try {
				User user=null; String userId=null;
				user = (User) session.getAttribute("SESS_USER");
				userId = user.getUserId();
				PrintWriter out_json = response.getWriter();  JsonObject obj = new JsonObject();boolean success=false; 
				String planId = null; String customerId = null;		String reason=null; String existingPlanId=null;
				if(request.getParameter("hdnadduplanid")!=null) planId = StringUtils.trim(request.getParameter("hdnadduplanid"));
				if(request.getParameter("addcustid")!=null) customerId = StringUtils.trim(request.getParameter("addcustid"));
				if(request.getParameter("currentplanid")!=null) existingPlanId = StringUtils.trim(request.getParameter("currentplanid"));
				if(request.getParameter("reason")!=null) reason = StringUtils.trim(request.getParameter("reason"));
				NeoBankEnvironment.setComment(1, className, "planId "+planId+" customerId "+customerId);
				success = (boolean)OpsManagePricingPlanDao.class.getConstructor().newInstance()
						.changePricingPlanForCustomer(planId, customerId,reason,existingPlanId);

				if(success) {
					// Audit trail
					//OCP --> Operation change pricing plan for customer
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, "O",
							"OCP"," Operation change pricing plan for customer "+customerId+" from "+existingPlanId+" to "+planId);
					obj.addProperty("error", "false"); 
	        		obj.addProperty("message", "Plan Allocated Successful");      			        		
				}else {
					obj.addProperty("error", "true"); 
	        		obj.addProperty("message", "Plan Not updated");
				}
				try {
					out_json = response.getWriter();
					out_json.print(obj);
				}finally {
					if(planId!=null) planId=null; if(customerId!=null) customerId=null;	if(obj!=null) obj = null;if(out_json!=null) out_json.close();
					if (reason!=null)reason=null; if(existingPlanId!=null)existingPlanId=null;
					if (userId!=null)userId=null; if (user!=null)user=null;
				}				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in changing rule, Please try again letter");
			}				
			break;			
		}
		
	}




}
