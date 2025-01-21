package com.pporte.rules;

import java.io.PrintWriter;
import java.util.ArrayList;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.OpsSystemManageLoyaltyDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.model.Customer;
import com.pporte.model.LoyaltyRules;
import com.pporte.model.TransactionRules;
import com.pporte.model.User;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;

public class OpsLoyaltyRulesImpl implements Rules {
	private static String className = OpsLoyaltyRulesImpl.class.getSimpleName();
	
	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		HttpSession session = request.getSession(false);
		switch (rulesaction) {
		case "Create Loyalty Rule":
			try {
				
				String langPref = null;
				if(request.getParameter("hdnlang")!=null)			  langPref = request.getParameter("hdnlang").trim();

				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opslyt");
				request.setAttribute("lastrule", "Create Loyalty Rule");
				
				ArrayList<LoyaltyRules> arrLoyaltyRules = (ArrayList<LoyaltyRules>) OpsSystemManageLoyaltyDao.class .getConstructor().newInstance().getAllLoyaltyRules();
				request.setAttribute("allloyaltyrules", arrLoyaltyRules);
				request.setAttribute("arrtxnrules", (ArrayList<TransactionRules>)SystemUtilsDao.class.getConstructor().newInstance().getTransactionRules());

				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewLoyaltyPage()).forward(request, response);
				 } finally {
					 if(arrLoyaltyRules != null) arrLoyaltyRules = null; if(langPref !=null) langPref = null;
					 
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;	
			
		case "opseditloyaltyrules":
			try {
				
				String langPref = null; String payMode = null; String ruleDesc=null; String pointConversion=null;
				String crytoConversion = null; String userId = null; String userType = null; String LoyaltyStatus = null;
				String planUserType = null; String lyltyUserType = null;
				
				NeoBankEnvironment.setComment(2, className, "inside opseditloyaltyrules rule");

				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				if(request.getParameter("editpaymode")!=null)			  payMode = request.getParameter("editpaymode").trim();
				if(request.getParameter("editruledesc")!=null)			  ruleDesc = request.getParameter("editruledesc").trim();
				if(request.getParameter("editpointconvtn")!=null)		  pointConversion = request.getParameter("editpointconvtn").trim();
				if(request.getParameter("editcryptoconvtn")!=null)		  crytoConversion = request.getParameter("editcryptoconvtn").trim();
				if(request.getParameter("hdnloyaltystatus")!=null)		  LoyaltyStatus = request.getParameter("hdnloyaltystatus").trim();
				if(request.getParameter("hdnusertype")!=null)			  lyltyUserType = request.getParameter("hdnusertype").trim();
				
				NeoBankEnvironment.setComment(2, className, "inside  payMode "+payMode);

				
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opslyt");
				request.setAttribute("lastrule", "Create Loyalty Rule");
				
				userId = ((User)session.getAttribute("SESS_USER")).getUserId();
				userType = ((User)session.getAttribute("SESS_USER")).getUserType();
				
				boolean result  = (boolean)OpsSystemManageLoyaltyDao.class.getConstructor().newInstance().UpdateLoyaltyRules( payMode, ruleDesc, 
						pointConversion, crytoConversion, LoyaltyStatus, lyltyUserType );
				
				if(result) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",StringUtils.substring("Updated Loyalty Rule payMode " + payMode , 0, 48));
				}else {
					NeoBankEnvironment.setComment(1, className, "Error in updating Loyalty Rules payMode "+ payMode );

				}
									
				ArrayList<LoyaltyRules> arrLoyaltyRules = (ArrayList<LoyaltyRules>) OpsSystemManageLoyaltyDao.class .getConstructor().newInstance().getAllLoyaltyRules();
				request.setAttribute("allloyaltyrules", arrLoyaltyRules);
				request.setAttribute("arrtxnrules", (ArrayList<TransactionRules>)SystemUtilsDao.class.getConstructor().newInstance().getTransactionRules());
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewLoyaltyPage()).forward(request, response);
				 } finally {
										
					if(langPref !=null) langPref=null;if(arrLoyaltyRules !=null) arrLoyaltyRules=null;
					if(payMode !=null) payMode=null; if(ruleDesc !=null) ruleDesc=null;if(pointConversion !=null) pointConversion=null;
					if(LoyaltyStatus !=null) LoyaltyStatus=null;if(crytoConversion !=null) crytoConversion=null;if(userType !=null) userType=null;
					if(planUserType !=null) planUserType=null;if(lyltyUserType !=null) lyltyUserType=null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
			
		
			
		case "View Customer Loyalty":
			try {
				
				String langPref = null;
				if(request.getParameter("hdnlang")!=null)			  langPref = request.getParameter("hdnlang").trim();

				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opslyt");
				request.setAttribute("lastrule", "View Customer Loyalty");
				
				ArrayList<Customer> arrCustLoyaltyPoints = (ArrayList<Customer>) OpsSystemManageLoyaltyDao.class .getConstructor().newInstance().getAllCustomerLoyalty();
				request.setAttribute("allCustomerLoyaly", arrCustLoyaltyPoints);
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewCustomerLoyaltyPage()).forward(request, response);
				 } finally {
					 if(arrCustLoyaltyPoints != null) arrCustLoyaltyPoints = null; if(langPref !=null) langPref = null;
					 
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
	public void performJSONOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext contex) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			 ServletContext ctx) throws Exception {		
		HttpSession session = request.getSession(false);
		switch (rulesaction) {
		case "opscustredeemlytlty":
			try {
				String reletionshipNo=null; String pointsAccrude = null; String userType = null;
				String langPref = null; boolean result = false; String errorMsg = "false"; 
				PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();

			    NeoBankEnvironment.setComment(3, className, "opscustredeemlytlty");

				if(request.getParameter("hdncustcode")!=null)				reletionshipNo = request.getParameter("hdncustcode").trim();
				if(request.getParameter("hdnpointsaccrued")!=null)			pointsAccrude = request.getParameter("hdnpointsaccrued").trim(); 
				if(request.getParameter("hdnlang")!=null)			       langPref = request.getParameter("hdnlang").trim();
				request.setAttribute("langpref", langPref);
				
				try {					
					 result = (boolean)OpsSystemManageLoyaltyDao.class.getConstructor().newInstance().initiateOpsCustLoyaltyRedeem(reletionshipNo, pointsAccrude );
						
					 if(result) {
						    errorMsg = "false";
						    
					 }else {
						    errorMsg = "Currently the module is under development";
						 //throw new Exception("Login Failed " + message);
						 
					 }
					  //  NeoBankEnvironment.setComment(3, className,"errorMsg is " + errorMsg); 
					    obj.add("error", gson.toJsonTree(errorMsg)); 
					  //  obj.add("token", gson.toJsonTree(  Utilities.issueTimeToken()  ));
					    out_json.print( gson.toJson(obj));
					   // NeoBankEnvironment.setComment(3, className, " gson "+ gson.toJson(obj));
					    
				} catch (Exception e1) {
					NeoBankEnvironment.setComment(1, className, "User Problem with the opscustredeemlytlty method "+e1.getMessage());
					   obj.add("error", gson.toJsonTree(errorMsg));
					    out_json.print( gson.toJson(obj));
					    NeoBankEnvironment.setComment(1, className, "Exception,  gson "+ gson.toJson(obj));
					    
					throw new Exception ("Problem occured in Ops Customer redeem Loyalty points");
				}finally {
					if(userType !=null) userType = null; if(out_json!=null) out_json.close();if(obj !=null) obj = null; 
					if(langPref !=null)  langPref = null; if(gson !=null) gson = null;
					
				}
				
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "  Exception in  opscustredeemlytlty is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in opscustredeemlytlty, Please try again letter");
			}
		break;
		
		case "opsaddloyaltyrules":
			try {
				
				String langPref = null; String payMode = null; String ruleDesc=null; String pointConversion=null;
				String crytoConversion = null; String userId = null; String userType = null; String LoyaltyStatus = null;
				String planUserType = null; String lyltyUserType = null;String errorMsg = "true"; 
				PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
				
				NeoBankEnvironment.setComment(2, className, "inside opsaddloyaltyrules rule");

				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				if(request.getParameter("hdnaddpaymode")!=null)			  payMode = request.getParameter("hdnaddpaymode").trim();
				if(request.getParameter("addruledesc")!=null)			  ruleDesc = request.getParameter("addruledesc").trim();
				if(request.getParameter("addpointconvtn")!=null)		  pointConversion = request.getParameter("addpointconvtn").trim();
				if(request.getParameter("addcryptoconvtn")!=null)		  crytoConversion = request.getParameter("addcryptoconvtn").trim();
				if(request.getParameter("hdnaddloyaltystatus")!=null)	  LoyaltyStatus = request.getParameter("hdnaddloyaltystatus").trim();
				if(request.getParameter("hdnaddusertype")!=null)		  lyltyUserType = request.getParameter("hdnaddusertype").trim();
				
				String [] arrPaymode =payMode.split(",");
				payMode = arrPaymode[0];
				ruleDesc = arrPaymode[1];
				NeoBankEnvironment.setComment(2, className, "inside payMode "+ payMode);

				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opslyt");
				request.setAttribute("lastrule", "Create Loyalty Rule");
				
				userId = ((User)session.getAttribute("SESS_USER")).getUserId();
				userType = ((User)session.getAttribute("SESS_USER")).getUserType();
				//Check if exist
				boolean isExist  = (boolean)OpsSystemManageLoyaltyDao.class.getConstructor().newInstance().checkifLoyaltyRuleExist( payMode, ruleDesc, lyltyUserType );
				NeoBankEnvironment.setComment(3, className, "isExist : " + isExist);

				if(isExist) {
					Utilities.sendJsonResponse(response, "error", "Failed : Loyalty Rule already exist");
					return;
				}
				
				boolean result  = (boolean)OpsSystemManageLoyaltyDao.class.getConstructor().newInstance().AddNewLoyaltyRules( payMode, ruleDesc, 
						pointConversion, crytoConversion, LoyaltyStatus, lyltyUserType );
				
				if(result) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",StringUtils.substring("Added new Loyalty Rules payMode " + payMode , 0, 48));
					errorMsg = "false";
					obj.add("message", gson.toJsonTree("Loyalty Rule "+payMode+" Created successfully "));

				}else {
					NeoBankEnvironment.setComment(1, className, "Error in adding Loyalty payMode "+ payMode );
					errorMsg = "Failed to add new Loyalty rule "+payMode ;
				}		
				 try {
					 obj.add("error", gson.toJsonTree(errorMsg));
					  out_json.print( gson.toJson(obj));				
				 
				 } finally {
					
					if(langPref !=null) langPref=null;
					if(payMode !=null) payMode=null; if(ruleDesc !=null) ruleDesc=null;if(pointConversion !=null) pointConversion=null;
					if(LoyaltyStatus !=null) LoyaltyStatus=null;if(crytoConversion !=null) crytoConversion=null;if(userType !=null) userType=null;
					if(planUserType !=null) planUserType=null;if(lyltyUserType !=null) lyltyUserType=null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Faied in Creating Loyalty Rule, Try again");
			}
			break;
		
		default:
			throw new IllegalArgumentException("Rule not defined value: " + rulesaction);
		}
		
	}

	

}
