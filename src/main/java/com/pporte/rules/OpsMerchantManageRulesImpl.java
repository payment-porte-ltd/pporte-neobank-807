package com.pporte.rules;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.OpsManageMerchantDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.model.MccGroup;
import com.pporte.model.Merchant;
import com.pporte.model.Risk;
import com.pporte.model.User;
import com.pporte.utilities.Utilities;
import org.apache.commons.lang3.StringUtils;


import framework.v8.Rules;

public class OpsMerchantManageRulesImpl implements Rules {
	
private static String className = OpsMerchantManageRulesImpl.class.getSimpleName();

	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null)
			Utilities.callOpsException(request, response, ctx, "Session has expired, please log in again");

		switch (rulesaction) {
		case "Pending Merchants":
			try {
				
				String langPref = null;
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();

				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opsmerch");
				request.setAttribute("lastrule", "Pending Merchants");
				request.setAttribute("hashmccgroup", (HashMap<String,String>)OpsManageMerchantDao.class.getConstructor().newInstance().gethashMccGroup());
				ArrayList<Merchant> arrPendingMerch = (ArrayList<Merchant>)OpsManageMerchantDao.class.getConstructor().newInstance().getPendingMerchants();
				request.setAttribute("pendingmerchants", arrPendingMerch);
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsPendingMerchantPage()).forward(request, response);
				} finally {
					if(arrPendingMerch !=null) arrPendingMerch = null;	
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
						
		case "opsshowspecificmerch":
			try {
								
				String merchantCode = null; String langPref = null; String verifyMerchFlag = "";
				if (request.getParameter("hdnmerchantcode") != null)  	  merchantCode = request.getParameter("hdnmerchantcode").trim();
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				if (request.getParameter("hdnmerchverify") != null) 	  verifyMerchFlag = request.getParameter("hdnmerchverify").trim();
				NeoBankEnvironment.setComment(3, className, "inside opsshowspecificmerch:merchantCode  " + merchantCode +" verifyMerchFlag "+ verifyMerchFlag);

				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opsmerch");
				request.setAttribute("lastrule", "Pending Merchants");
				response.setContentType("text/html");
				
				request.setAttribute("verifymerchantflag", verifyMerchFlag );
				Merchant m_MerchantDetails = (Merchant)OpsManageMerchantDao.class.getConstructor().newInstance().getSpecificMerchantDetails(merchantCode);
				request.setAttribute("specificmerchant", m_MerchantDetails);
				request.setAttribute("mcclist", (ArrayList<MccGroup>)OpsManageMerchantDao.class.getConstructor().newInstance().getAllMCCRroupList());
				request.setAttribute("kycdocslist", (ArrayList<String>)OpsManageMerchantDao.class.getConstructor().newInstance().getAllKYCDocsForMerchant(merchantCode));
				request.setAttribute("userdetails", (ArrayList<Merchant>)OpsManageMerchantDao.class.getConstructor().newInstance().getMerchantUserDetails(merchantCode));
				request.setAttribute("merchrisk", (ArrayList<Risk>)OpsManageMerchantDao.class.getConstructor().newInstance().getMerchantRiskProfile());
				
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getOpsEditSpecificMerchanPage()).forward(request, response);
				} finally {
					if (m_MerchantDetails != null)
						m_MerchantDetails = null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
			
		case "Risk Profile":
			try {
				
				String langPref = null;
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();

				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opsmerch");
				request.setAttribute("lastrule", "Risk Profile");
				request.setAttribute("merchantrisks", (ArrayList<Risk>)OpsManageMerchantDao.class.getConstructor().newInstance().getMerchantRiskProfiles());
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsMerchantRiskProfilePage()).forward(request, response);
				} finally {

				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
			
		case "View MCC Group":
			try {
				
				String langPref = null;
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();

				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opsmerch");
				request.setAttribute("lastrule", "View MCC Group");
				request.setAttribute("merchantmccc", (ArrayList<MccGroup>)OpsManageMerchantDao.class.getConstructor().newInstance().getMerchantMCC());
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsMerchanMCCPage()).forward(request, response);
				} finally {

				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
			
		case "opsnewmccgroup":
			try {
				
				String langPref = null; String mccName = null; String mccFromRange=null; String mccToRange=null;
				String mccGeneric = null; String userId = null; String userType = null;

				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				if(request.getParameter("mccgroupname")!=null)			  mccName = request.getParameter("mccgroupname").trim();
				if(request.getParameter("mccfromrange")!=null)			  mccFromRange = request.getParameter("mccfromrange").trim();
				if(request.getParameter("mcctorange")!=null)			  mccToRange = request.getParameter("mcctorange").trim();
				if(request.getParameter("mccgeneric")!=null)			  mccGeneric = request.getParameter("mccgeneric").trim();
				
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opsmerch");
				request.setAttribute("lastrule", "View MCC Group");
				
				userId = ((User)session.getAttribute("SESS_USER")).getUserId();
				userType = ((User)session.getAttribute("SESS_USER")).getUserType();
				
				boolean result  = (boolean)OpsManageMerchantDao.class.getConstructor().newInstance().AddNewMccGroup( mccName, mccFromRange, mccToRange, mccGeneric );
				
				if(result) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",StringUtils.substring("Created new mcc group " + userId , 0, 48));
				}else {
					NeoBankEnvironment.setComment(1, className, "Error in creting new mcc group");

				}
									
				request.setAttribute("merchantmccc", (ArrayList<MccGroup>)OpsManageMerchantDao.class.getConstructor().newInstance().getMerchantMCC());
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsMerchanMCCPage()).forward(request, response);
				} finally {
					if (langPref!=null)langPref=null; if (mccName!=null)mccName=null; if (mccFromRange!=null)mccFromRange=null;
					if (mccToRange!=null)mccToRange=null; if (mccGeneric!=null)mccGeneric=null; if (userId!=null)userId=null;
					if (userType!=null) userType=null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
			
		case "opseditmccgroup":
			try {
				
				String langPref = null; String mccName = null; String mccFromRange=null; String mccToRange=null;
				String mccGeneric = null; String userId = null; String userType = null; String mccId = null;
				NeoBankEnvironment.setComment(2, className, "inside opseditmccgroup rule");

				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				if(request.getParameter("editmccgroupname")!=null)			  mccName = request.getParameter("editmccgroupname").trim();
				if(request.getParameter("editmccfromrange")!=null)			  mccFromRange = request.getParameter("editmccfromrange").trim();
				if(request.getParameter("editmcctorange")!=null)			  mccToRange = request.getParameter("editmcctorange").trim();
				if(request.getParameter("editmccgeneric")!=null)			  mccGeneric = request.getParameter("editmccgeneric").trim();
				if(request.getParameter("editmccid")!=null)			  mccId = request.getParameter("editmccid").trim();
				
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opsmerch");
				request.setAttribute("lastrule", "View MCC Group");
				
				userId = ((User)session.getAttribute("SESS_USER")).getUserId();
				userType = ((User)session.getAttribute("SESS_USER")).getUserType();
				
				boolean result  = (boolean)OpsManageMerchantDao.class.getConstructor().newInstance().UpdateMccGroup( mccId, mccName, mccFromRange, mccToRange, mccGeneric );
				
				if(result) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",StringUtils.substring("Updated mcc group Id " + userId , 0, 48));
				}else {
					NeoBankEnvironment.setComment(1, className, "Error in updating mcc group  mccId"+ mccId );

				}
									
				request.setAttribute("merchantmccc", (ArrayList<MccGroup>)OpsManageMerchantDao.class.getConstructor().newInstance().getMerchantMCC());
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsMerchanMCCPage()).forward(request, response);
				} finally {

				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		case "opsaddnewrisk":
			try {
				
				String langPref = null; String riskDesc = null; String riskPaymentAction=null; String riskStatus=null;
				String userId = null; String userType = null;

				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				if(request.getParameter("hdnriskdesc")!=null)			  riskDesc = request.getParameter("hdnriskdesc").trim();
				if(request.getParameter("hdnpaymentaction")!=null)		  riskPaymentAction = request.getParameter("hdnpaymentaction").trim();
				if(request.getParameter("hdnstatus")!=null)			      riskStatus = request.getParameter("hdnstatus").trim();
				
				NeoBankEnvironment.setComment(2, className, "riskDesc " + riskDesc + " riskPaymentAction "+riskPaymentAction + " riskStatus "+riskStatus);

				
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opsmerch");
				request.setAttribute("lastrule", "Risk Profile");
				
				userId = ((User)session.getAttribute("SESS_USER")).getUserId();
				userType = ((User)session.getAttribute("SESS_USER")).getUserType();
				
				boolean result  = (boolean)OpsManageMerchantDao.class.getConstructor().newInstance().AddNewMerchantRiskFactor( riskDesc, riskPaymentAction, riskStatus );
				
				if(result) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",StringUtils.substring("Created new Risk Factor " + userId , 0, 48));
				}else {
					NeoBankEnvironment.setComment(1, className, "Error in creting new Risk Factor");

				}
									
				request.setAttribute("merchantrisks", (ArrayList<Risk>)OpsManageMerchantDao.class.getConstructor().newInstance().getMerchantRiskProfiles());
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsMerchantRiskProfilePage()).forward(request, response);
				} finally {

				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		case "opseditmerchrisk":
			try {
				
				String langPref = null; String riskDesc = null; String riskPaymentAction=null; String riskStatus=null;
				String userId = null; String userType = null; String riskId = null;

				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				if(request.getParameter("hdneditriskdesc")!=null)			  riskDesc = request.getParameter("hdneditriskdesc").trim();
				if(request.getParameter("hdneditpaymentaction")!=null)		  riskPaymentAction = request.getParameter("hdneditpaymentaction").trim();
				if(request.getParameter("hdneditstatus")!=null)			  riskStatus = request.getParameter("hdneditstatus").trim();
				if(request.getParameter("editriskid")!=null)			  riskId = request.getParameter("editriskid").trim();
				
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opsmerch");
				request.setAttribute("lastrule", "Risk Profile");
				
				userId = ((User)session.getAttribute("SESS_USER")).getUserId();
				userType = ((User)session.getAttribute("SESS_USER")).getUserType();
				
				boolean result  = (boolean)OpsManageMerchantDao.class.getConstructor().newInstance().UpdateMerchantRiskFactor(riskId, riskDesc, riskPaymentAction, riskStatus );
				
				if(result) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",StringUtils.substring("Created new Risk Factor " + userId , 0, 48));
				}else {
					NeoBankEnvironment.setComment(1, className, "Error in creting new Risk Factor");

				}
									
				request.setAttribute("merchantrisks", (ArrayList<Risk>)OpsManageMerchantDao.class.getConstructor().newInstance().getMerchantRiskProfiles());
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsMerchantRiskProfilePage()).forward(request, response);
				} finally {

				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
			
		case "View Merchants":
			try {
				
				String langPref = null;
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();

				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opsmerch");
				request.setAttribute("lastrule", "View Merchants");
				request.setAttribute("hashmccgroup", (HashMap<String,String>)OpsManageMerchantDao.class.getConstructor().newInstance().gethashMccGroup());
				ArrayList<Merchant> arrMerchants = (ArrayList<Merchant>)OpsManageMerchantDao.class.getConstructor().newInstance().getAllMerchants();
				request.setAttribute("allmerchants", arrMerchants);
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsAllMerchantPage()).forward(request, response);
				} finally {
					if(arrMerchants !=null) arrMerchants = null; if(langPref !=null) langPref = null;		
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
			
		case "View Merchant Plans":
			try {
				
				String langPref = null;
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				NeoBankEnvironment.setComment(3, className, "inside View Merchant Plans ");

				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opsmprc");
				request.setAttribute("lastrule", "View Merchant Plans");
				
				//ArrayList<Merchant> arrMerchantPricingPlans = (ArrayList<Merchant>)OpsManageMerchantDao.class.getConstructor().newInstance().getMerchantsPricingPlan();
				//request.setAttribute("merchantpricingplan", arrMerchantPricingPlans);
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewMerchantPlanPage()).forward(request, response);
				} finally {
					//if(arrMerchantPricingPlans !=null) arrMerchantPricingPlans = null;	
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
			ServletContext context) throws Exception {

		
	}

	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			 ServletContext ctx) throws Exception {
		HttpSession session = request.getSession(false);

		switch (rulesaction) {
		case "opseditmerchuser":
			try {
				PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
				String langPref = null; String userName = null; String userContact=null; String userEmail=null;
				String userRole = null; String status = null; String userDesignation = null; String userId = null;
				String userType = null; String merchUserId = null; String merchUserNationalId = null; String errorMsg = "true";

				if(request.getParameter("hdnlangpref")!=null)			    langPref = request.getParameter("hdnlangpref").trim();
				if(request.getParameter("merchusername")!=null)			    userName = request.getParameter("merchusername").trim();
				if(request.getParameter("merchusercontact")!=null)		    userContact = request.getParameter("merchusercontact").trim();
				if(request.getParameter("merchuseremail")!=null)		    userEmail = request.getParameter("merchuseremail").trim();
				if(request.getParameter("hdnuserrole")!=null)	            userRole = request.getParameter("hdnuserrole").trim();
				if(request.getParameter("hdnstatus")!=null)			        status = request.getParameter("hdnstatus").trim();
				if(request.getParameter("merchuserdesig")!=null)	        userDesignation = request.getParameter("merchuserdesig").trim();
				if(request.getParameter("merchuid")!=null)	        merchUserId = request.getParameter("merchuid").trim();
				if(request.getParameter("nationalid")!=null)	        merchUserNationalId = request.getParameter("nationalid").trim();
				
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opsmerch");
				request.setAttribute("lastrule", "Risk Profile");
				
				userId = ((User)session.getAttribute("SESS_USER")).getUserId();
				userType = ((User)session.getAttribute("SESS_USER")).getUserType();
				
				boolean result  = (boolean)OpsManageMerchantDao.class.getConstructor().newInstance().UpdateMerchantUser(merchUserId, userName, userContact, userEmail, 
						userRole, status, userDesignation, merchUserNationalId );
				
				if(result) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",StringUtils.substring("Updated merchant user "+ merchUserId , 0, 48));
					obj.add("error", gson.toJsonTree("false")); // no error
					errorMsg = "false";
				    obj.add("message", gson.toJsonTree("Merchant user updated successful")); 

				}else {
					NeoBankEnvironment.setComment(1, className, "Error in Updating merchant user");
					errorMsg = "Error in updating merchant user ";

				}
				
				try { 
					 obj.add("error", gson.toJsonTree(errorMsg)); 
					 out_json.print( gson.toJson(obj));
					
				}finally {
					
					if(out_json !=null) out_json.close(); out_json = null; if(obj !=null) obj =null; if(userName !=null) userName=null;
					if(langPref !=null) langPref =null; if(gson !=null) gson=null;if(userContact !=null) userContact=null;
					if(userEmail !=null) userEmail=null; if(userRole !=null) userRole=null; if(userDesignation !=null) userDesignation=null; 
					if(merchUserId !=null) merchUserId=null; if(merchUserNationalId !=null) merchUserNationalId=null; if(errorMsg !=null) errorMsg=null; 
					if(userId !=null) userId=null; if(userType !=null) userType=null;
				}
			   
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in opseditmerchuser rule, Please try again letter");
			}
			break;
			
		case "opseditmerchdetails":
			try {
				NeoBankEnvironment.setComment(3, className, "insude opseditmerchdetails " );

				PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
				String langPref = null; String merchantCode = null; String companyName=null; String businessPhone=null;
				String billRef = null; String busdDescription = null; String physicalAddress = null; String userId = null;
				String userType = null; String merchType = null; String merchStatus = null; String errorMsg = "true";
				String merchMcc = null; String verifyMerchFlag = ""; String merchRisk = null;

				if(request.getParameter("hdnlangpref")!=null)			    langPref = request.getParameter("hdnlangpref").trim();
				if(request.getParameter("merchantcode")!=null)			    merchantCode = request.getParameter("merchantcode").trim();
				if(request.getParameter("companyname")!=null)		    companyName = request.getParameter("companyname").trim();
				if(request.getParameter("businessphone")!=null)		    businessPhone = request.getParameter("businessphone").trim();
				if(request.getParameter("billref")!=null)	            billRef = request.getParameter("billref").trim();
				if(request.getParameter("bussinessdescription")!=null)   busdDescription = request.getParameter("bussinessdescription").trim();
				if(request.getParameter("physicaladdress")!=null)	        physicalAddress = request.getParameter("physicaladdress").trim();
				if(request.getParameter("hdnmerchtype")!=null)	        merchType = request.getParameter("hdnmerchtype").trim();
				if(request.getParameter("hdnmerchstatus")!=null)	        merchStatus = request.getParameter("hdnmerchstatus").trim();
				if(request.getParameter("hdnmccgroup")!=null)	        merchMcc = request.getParameter("hdnmccgroup").trim();
				if(request.getParameter("hdnactivatemerch")!=null)	        verifyMerchFlag = request.getParameter("hdnactivatemerch").trim();
				if(request.getParameter("hdnriskprf")!=null)	        merchRisk = request.getParameter("hdnriskprf").trim();
				
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opsmerch");
				request.setAttribute("lastrule", "Risk Profile");
				
				userId = ((User)session.getAttribute("SESS_USER")).getUserId();
				userType = ((User)session.getAttribute("SESS_USER")).getUserType();
				
				NeoBankEnvironment.setComment(3, className, "insude merchantCode " +merchantCode+ " merchStatus "+ merchStatus+ " merchRisk "+ merchRisk);
 
				boolean result  = (boolean)OpsManageMerchantDao.class.getConstructor().newInstance().UpdateMerchantDetails(merchantCode, companyName,businessPhone,billRef,
						busdDescription, physicalAddress, merchType, merchStatus, merchMcc, userId, verifyMerchFlag );
				
				if(result) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",StringUtils.substring("Updated merchant Details "+ merchantCode , 0, 48));
					obj.add("error", gson.toJsonTree("false")); // no error
					errorMsg = "false";
				    obj.add("message", gson.toJsonTree("Merchant details updated successful")); 

				}else {
					NeoBankEnvironment.setComment(1, className, "Error in Updating merchant user");
					errorMsg = "Error in Activating Merchant  ";

				}
				
				try {
					 obj.add("error", gson.toJsonTree(errorMsg)); 
					 out_json.print( gson.toJson(obj));
						
				}finally {
				
					if(out_json !=null) out_json.close(); out_json = null; if(obj !=null) obj =null; if(merchantCode !=null) merchantCode=null;
					if(langPref !=null) langPref =null; if(gson !=null) gson=null;if(companyName !=null) companyName=null;
					if(businessPhone !=null) businessPhone=null; if(billRef !=null) billRef=null; if(busdDescription !=null) busdDescription=null; 
					if(physicalAddress !=null) physicalAddress=null; if(userType !=null) userType=null; if(errorMsg !=null) errorMsg=null; 
					if(userId !=null) userId=null; if(merchStatus !=null) merchStatus=null;
					if(merchMcc !=null) merchMcc=null; if(verifyMerchFlag !=null) verifyMerchFlag=null;
				}
				
			   								
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in opseditmerchdetails rule, Please try again letter");
			}
			break;
			
			
		default:
			throw new IllegalArgumentException("Unexpected value: " + rulesaction);
		}
		
	}

}
