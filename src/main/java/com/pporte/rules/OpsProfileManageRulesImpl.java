package com.pporte.rules;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.OpsProfileManageDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.model.User;
import com.pporte.utilities.SendEmailUtility;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;

public class OpsProfileManageRulesImpl implements Rules{
	private static String className = OpsSysTransactionsImpl.class.getSimpleName();


	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null)
			Utilities.callOpsException(request, response, ctx, "Session has expired, please log in again");

		switch (rulesaction) {
		case "View Profile":
			try {
				
				String langPref = null;
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();

				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "prf");
				request.setAttribute("lastrule", "View Profile");
				
				request.setAttribute("opsuser", (User)OpsProfileManageDao.class.getConstructor().newInstance().getSpecificOpsUser( ((User)session.getAttribute("SESS_USER")).getUserId()   ));
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsSysProfilePage()).forward(request, response);
				 } finally {
					 if(langPref !=null)langPref=null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		case "Manage Ops Users":
			try {
				
				String langPref = null;
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();

				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "prf");
				request.setAttribute("lastrule", "Manage Ops Users");
				
				request.setAttribute("allopsusers", (ArrayList <User>)OpsProfileManageDao.class.getConstructor().newInstance().getAllOperationUsers());
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsManageUsersPage()).forward(request, response);
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
		
		
	}

	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			 ServletContext ctx) throws Exception {
		HttpSession session = request.getSession(false);
		switch (rulesaction) {
		case "opseditprofile":
			try {
				NeoBankEnvironment.setComment(3, className, "insude opseditprofile " );

				PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
				String langPref = null; String userId = null; String userName=null; String opsUserContact=null;
				String createdOn = null; String opsUserType = null; String emailAddress = null; String opsUserId = null;
				String userType = null; String opsStatus = null; String expiry = null; String errorMsg = "true";

				try {
					if(request.getParameter("hdnlangpref")!=null)			    langPref = request.getParameter("hdnlangpref").trim();
					if(request.getParameter("editopsuid")!=null)			    opsUserId = request.getParameter("editopsuid").trim();
					if(request.getParameter("editusername")!=null)		        userName = request.getParameter("editusername").trim();
					if(request.getParameter("editusercontact")!=null)		    opsUserContact = request.getParameter("editusercontact").trim();
					if(request.getParameter("edituseremail")!=null)		        emailAddress = request.getParameter("edituseremail").trim();
	
					
					request.setAttribute("langPref", langPref);
					request.setAttribute("lastaction", "prf");
					request.setAttribute("lastrule", "View Profile");
					
					userId = ((User)session.getAttribute("SESS_USER")).getUserId();
					userType = ((User)session.getAttribute("SESS_USER")).getUserType();
					
					NeoBankEnvironment.setComment(3, className, "insude opsUserId " +opsUserId+ " opsUserType "+ opsUserType);
	 
					boolean result  = (boolean)OpsProfileManageDao.class.getConstructor().newInstance().UpdateOpsUserProfileDetails(opsUserId, userName,opsUserContact,emailAddress );
					
					if(result) {
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",StringUtils.substring("Updated Ops User Profile for "+ opsUserId , 0, 48));
						obj.add("error", gson.toJsonTree("false")); // no error
						errorMsg = "false";
					    obj.add("message", gson.toJsonTree("Profile details updated successful")); 

					}else {
						NeoBankEnvironment.setComment(1, className, "Error in Updating Profile details");
						errorMsg = "Updating Profile details failed. Try again";

					}
				    obj.add("error", gson.toJsonTree(errorMsg)); 
				    out_json.print( gson.toJson(obj));
					
				} catch (Exception e1) {
					NeoBankEnvironment.setComment(1, className, " User Problem with the login method "+e1.getMessage());
					   obj.add("error", gson.toJsonTree(errorMsg));
					    out_json.print( gson.toJson(obj));
					    NeoBankEnvironment.setComment(3, className, "Exception,  gson "+ gson.toJson(obj));
					    
					throw new Exception ("Problem with opseditprofile method");						
				}finally {
					if(out_json !=null) out_json.close();out_json= null ;if(gson !=null) gson = null; if(obj !=null) obj = null;
					if(langPref !=null) langPref = null; if(userId !=null) userId = null;if(userName !=null) userName = null;
					if(opsUserContact !=null) opsUserContact = null; if(opsUserType !=null) opsUserType = null; if(emailAddress !=null) emailAddress = null;
					if(createdOn !=null) createdOn = null; if(opsUserId !=null) opsUserId = null; if(userType !=null) userType = null;
					if(opsStatus !=null) opsStatus = null; if(expiry !=null) expiry = null; if(errorMsg !=null) errorMsg = null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in editing profile, Please try again letter");
			}
			break;
			
		case "opseditopsuser":
			try {
				NeoBankEnvironment.setComment(3, className, "insude opseditopsuser " );

				PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
				String langPref = null; String userId = null; String userName=null; String opsUserContact=null;
				String createdOn = null; String opsUserType = null; String emailAddress = null; String opsUserId = null;
				String userType = null; String opsStatus = null; String expiry = null; String errorMsg = "true";
				
				try {
					if(request.getParameter("hdnlangpref")!=null)			    langPref = request.getParameter("hdnlangpref").trim();
					if(request.getParameter("editopsuid")!=null)			    opsUserId = request.getParameter("editopsuid").trim();
					if(request.getParameter("editusername")!=null)		        userName = request.getParameter("editusername").trim();
					if(request.getParameter("editusercontact")!=null)		    opsUserContact = request.getParameter("editusercontact").trim();
					if(request.getParameter("hdneditusertype")!=null)			opsUserType = request.getParameter("hdneditusertype").trim();
					if(request.getParameter("edituseremail")!=null)		        emailAddress = request.getParameter("edituseremail").trim();
					if(request.getParameter("hdneditstatus")!=null)		   		opsStatus = request.getParameter("hdneditstatus").trim();
					if(request.getParameter("editdatofexpiry")!=null)	        expiry = request.getParameter("editdatofexpiry").trim();
					
					request.setAttribute("langPref", langPref);
					request.setAttribute("lastaction", "prf");
					request.setAttribute("lastrule", "Manage Ops Users");
					
					userId = ((User)session.getAttribute("SESS_USER")).getUserId();
					userType = ((User)session.getAttribute("SESS_USER")).getUserType();
					
					NeoBankEnvironment.setComment(3, className, "insude opsUserId " +opsUserId+ " opsUserType "+ opsUserType);
	 
					boolean result  = (boolean)OpsProfileManageDao.class.getConstructor().newInstance().UpdateOpsUsersDetails(opsUserId, userName,opsUserContact,emailAddress, 
							opsUserType, opsStatus, expiry );
					
					if(result) {
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",StringUtils.substring("Updated Ops User Details for "+ opsUserId , 0, 48));
						obj.add("error", gson.toJsonTree("false")); // no error
						errorMsg = "false";
					    obj.add("message", gson.toJsonTree("User details updated successful")); 

					}else {
						NeoBankEnvironment.setComment(1, className, "Error in Updating Ops User details");
						errorMsg = "Updating Ops User details failed. Try again";

					}
				    obj.add("error", gson.toJsonTree(errorMsg)); 
				    out_json.print( gson.toJson(obj));
					
				} catch (Exception e1) {
					NeoBankEnvironment.setComment(1, className, " User Problem with the login method "+e1.getMessage());
					   obj.add("error", gson.toJsonTree(errorMsg));
					    out_json.print( gson.toJson(obj));
					    NeoBankEnvironment.setComment(3, className, "Exception,  gson "+ gson.toJson(obj));
					    
					throw new Exception (" Problem with opseditopsuser method");				
					
				}finally {
					if(out_json !=null) out_json.close();out_json= null ;if(gson !=null) gson = null; if(obj !=null) obj = null;
					if(langPref !=null) langPref = null; if(userId !=null) userId = null;if(userName !=null) userName = null;
					if(opsUserContact !=null) opsUserContact = null; if(opsUserType !=null) opsUserType = null; if(emailAddress !=null) emailAddress = null;
					if(createdOn !=null) createdOn = null; if(opsUserId !=null) opsUserId = null; if(userType !=null) userType = null;
					if(opsStatus !=null) opsStatus = null; if(expiry !=null) expiry = null; if(errorMsg !=null) errorMsg = null;
					
				}
				
			} catch (Exception e) {
				
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in editing Ops User, Please try again letter");
			}
			break;
			
		case "opsaddopsuser":
			try {
				NeoBankEnvironment.setComment(3, className, "insude opsaddopsuser " );

				PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
				String langPref = null; String userId = null; String userName=null; String opsUserContact=null;
				String createdOn = null; String opsUserType = null; String emailAddress = null; String opsUserId = null;
				String userType = null; String opsStatus = null; String expiry = null; String errorMsg = "true";
				String temporaryPassWord=null;
				String subject=null; String content=null;
				
					if(request.getParameter("hdnlangpref")!=null)			    langPref = request.getParameter("hdnlangpref").trim();
					if(request.getParameter("addopsuid")!=null)			        opsUserId = request.getParameter("addopsuid").trim();
					if(request.getParameter("addusername")!=null)		        userName = request.getParameter("addusername").trim();
					if(request.getParameter("addusercontact")!=null)		    opsUserContact = request.getParameter("addusercontact").trim();
					if(request.getParameter("hdnaddusertype")!=null)		    opsUserType = request.getParameter("hdnaddusertype").trim();
					if(request.getParameter("adduseremail")!=null)		        emailAddress = request.getParameter("adduseremail").trim();
					if(request.getParameter("hdnaddstatus")!=null)		   		opsStatus = request.getParameter("hdnaddstatus").trim();
					if(request.getParameter("adddatofexpiry")!=null)	        expiry = request.getParameter("adddatofexpiry").trim();
					
					request.setAttribute("langPref", langPref);   
					
					request.setAttribute("lastaction", "prf");
					request.setAttribute("lastrule", "Manage Ops Users");
					
					userId = ((User)session.getAttribute("SESS_USER")).getUserId();
					userType = ((User)session.getAttribute("SESS_USER")).getUserType();
					
					temporaryPassWord = RandomStringUtils.randomAlphabetic(6);
					temporaryPassWord="test1234";// TODO:- Delete this line on production
					NeoBankEnvironment.setComment(3, className, "insude opsUserId " +opsUserId+ " opsUserType "+ opsUserType);
					if(opsUserType.equals("O")) {
						
					}
					boolean result  = (boolean)OpsProfileManageDao.class.getConstructor().newInstance().OpsAddNewOpsUser(opsUserId, userName,opsUserContact,emailAddress, 
							opsUserType, opsStatus, expiry,temporaryPassWord);

					if(result) {
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",StringUtils.substring("Created new Ops User : "+ opsUserId , 0, 48));
						//Email sent to ops user with Temporary password
						obj.add("error", gson.toJsonTree("false")); // no error
						errorMsg = "false";
					    obj.add("message", gson.toJsonTree("User Created successful")); 
					    
					    subject="WELCOME TO PAYMENT PORTE!";
						 content="You have been successfully onboarded to the system. Here is the one time password you will use to login into the system:- "+temporaryPassWord;
						// Send to user
						String sendto = emailAddress;
						String sendSubject = subject;
						String sendContent = content;
						String customerName = userName;
						
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
						NeoBankEnvironment.setComment(1, className, "Error in Creating new Ops User");
						errorMsg = "Creating new Ops User failed. Try again";

					}
				    obj.add("error", gson.toJsonTree(errorMsg)); 
				 
					try {
						 out_json.print( gson.toJson(obj));
				    }finally {
					if(out_json !=null) out_json.close();out_json= null ;if(gson !=null) gson = null; if(obj !=null) obj = null;
					if(langPref !=null) langPref = null; if(userId !=null) userId = null;if(userName !=null) userName = null;
					if(opsUserContact !=null) opsUserContact = null; if(opsUserType !=null) opsUserType = null; if(emailAddress !=null) emailAddress = null;
					if(createdOn !=null) createdOn = null; if(opsUserId !=null) opsUserId = null; if(userType !=null) userType = null;
					if(opsStatus !=null) opsStatus = null; if(expiry !=null) expiry = null; if(errorMsg !=null) errorMsg = null;
					if (temporaryPassWord!=null) temporaryPassWord=null;
					if (subject!=null);subject=null; if (content!=null);content=null;
					
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in Adding Operation User, Please try again letter");
			}
			break;
							
		default:
			throw new IllegalArgumentException("Rule not defined value: " + rulesaction);
		}
		
	}

	

}
