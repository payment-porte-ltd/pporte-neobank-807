package com.pporte.rules;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.CustomerDao;
import com.pporte.dao.OpsManageDisputesDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.model.Customer;
import com.pporte.model.DisputeReasons;
import com.pporte.model.DisputeTracker;
import com.pporte.model.Disputes;
import com.pporte.model.User;
import com.pporte.utilities.SendEmailUtility;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;

public class OpsDisputesManageRulesImpl implements Rules{

private static String className = OpsDisputesManageRulesImpl.class.getSimpleName();

	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx)
			throws Exception {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null)
			Utilities.callOpsException(request, response, ctx, "Session has expired, please log in again");

		switch (rulesaction) {
		case "Dispute Reasons":
			try {
				
				String langPref = null;
				if(request.getParameter("hdnlangpref")!=null)langPref = request.getParameter("hdnlangpref").trim();
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opsdspt");
				request.setAttribute("lastrule", "Dispute Reasons");
				request.setAttribute("disputes", (ArrayList<Disputes>)OpsManageDisputesDao.class.getConstructor().newInstance().getAllDisputes());
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsAllDisputeReasonsPage()).forward(request, response);
				} finally {
					if(langPref !=null) langPref=null;

				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		
			
		case "Ops Raise Disputes":
			try {
				request.setAttribute("lastaction", "opsdspt");
				request.setAttribute("lastrule", "Ops Raise Disputes");
				response.setContentType("text/html");
				 try {
					 request.setAttribute("dsptreason", (List<DisputeReasons>) OpsManageDisputesDao.class.getConstructor()
							.newInstance().getAllDisputeReasons());
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsRaiseDisputePage()).forward(request, response);
				} finally {
				}
				 
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		case "View Raised Disputes":
			try {
				request.setAttribute("lastaction", "opsdspt");
				request.setAttribute("lastrule", "View Raised Disputes");
				response.setContentType("text/html");
				request.setAttribute("alldisputes", 
						(List<Disputes>)OpsManageDisputesDao.class.getConstructor().newInstance().getAllRaisedDisputes());
				
				try{
					ctx.getRequestDispatcher(NeoBankEnvironment.getViewCustomerDisputePageOps()).forward(request, response);
				}finally { 
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
		break;
		case "ops_view_specific_cust_dispute":
			try {
				request.setAttribute("lastaction", "opsdspt");
				request.setAttribute("lastrule", "View Raised Disputes"); 
				String disputeId = null;	
				if(request.getParameter("hdnreqid")!=null)
					disputeId = StringUtils.trim( request.getParameter("hdnreqid") );

				request.setAttribute("showdispute", (Disputes)OpsManageDisputesDao.class.getConstructor().newInstance().getDisputeDetail(disputeId));
				request.setAttribute("disputethreads", (List<DisputeTracker>)OpsManageDisputesDao.class.getConstructor().newInstance().getAllDisputeTrackers(disputeId));
				try{
					ctx.getRequestDispatcher(NeoBankEnvironment.getOpsSpecificCustomerDisputePage()).forward(request, response);
				}finally {
					if(disputeId!=null) disputeId=null; 
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			break;
		case "opsnewdispute":
			try {
				
				String langPref = null;String disputeReasonDesc=null; String payMode=null;
				String userId = null; String userType = null; String disputeStatus=null;String opsUserType=null;
				 
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				if(request.getParameter("disputereasondesc")!=null)			  disputeReasonDesc = request.getParameter("disputereasondesc").trim();
				if(request.getParameter("hdnaddusertype")!=null)			  userType = request.getParameter("hdnaddusertype").trim();
				if(request.getParameter("hdnaddpaymode")!=null)			  payMode = request.getParameter("hdnaddpaymode").trim();
				if(request.getParameter("hdnadddisputestatus")!=null)			  disputeStatus = request.getParameter("hdnadddisputestatus").trim();
				
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opsdspt");
				request.setAttribute("lastrule", "Dispute Reasons");
				
				NeoBankEnvironment.setComment(3, className, "User type is " + userType );
				
				userId = ((User)session.getAttribute("SESS_USER")).getUserId();
				opsUserType = ((User)session.getAttribute("SESS_USER")).getUserType();
				
				boolean result  = (boolean)OpsManageDisputesDao.class.getConstructor().newInstance().AddNewDisputeReasons(disputeReasonDesc, userType, payMode, disputeStatus);
				
				if(result) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, opsUserType, "O",StringUtils.substring("Created new Dispute Reason " + userId , 0, 48));
				}else {
					NeoBankEnvironment.setComment(1, className, "Error in creting new Dispute Reason");

				}
									
				request.setAttribute("disputes", (ArrayList<Disputes>)OpsManageDisputesDao.class.getConstructor().newInstance().getAllDisputes());
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsAllDisputeReasonsPage()).forward(request, response);
				} finally {
					if(langPref !=null) langPref=null; if(disputeReasonDesc !=null) disputeReasonDesc=null; if(payMode !=null) payMode=null;
					if(userId !=null) userId=null; if(userType !=null) userType=null; if(disputeStatus !=null) disputeStatus=null;
					if(opsUserType !=null) opsUserType=null;

				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		case "opseditdispute":
			try {
				
				String langPref = null; String disputeReasonId = null; String disputeReasonDesc=null; String userType=null;
				String payMode = null; String status = null; String userId=null; String opsUserType=null;
				
				NeoBankEnvironment.setComment(2, className, "inside opseditdispute rule");

				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				if(request.getParameter("editdisputereason")!=null)			  disputeReasonId = request.getParameter("editdisputereason").trim();
				if(request.getParameter("editreasondesc")!=null)			  disputeReasonDesc = request.getParameter("editreasondesc").trim();
				if(request.getParameter("hdneditusertype")!=null)			  userType = request.getParameter("hdneditusertype").trim();
				if(request.getParameter("hdneditpaymode")!=null)			  payMode = request.getParameter("hdneditpaymode").trim();
				if(request.getParameter("hdneditstatus")!=null)			  status = request.getParameter("hdneditstatus").trim();
				
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opsdspt");
				request.setAttribute("lastrule", "Dispute Reasons");
				
				userId = ((User)session.getAttribute("SESS_USER")).getUserId();
				opsUserType = ((User)session.getAttribute("SESS_USER")).getUserType();
				
				boolean result  = (boolean)OpsManageDisputesDao.class.getConstructor().newInstance().UpdateDisputeReasons( disputeReasonId, disputeReasonDesc, userType, payMode, status );
				
				if(result) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",StringUtils.substring("Updated dispute Reason Id" + disputeReasonId , 0, 48));
				}else {
					NeoBankEnvironment.setComment(1, className, "Error in updating dispute reasons id"+ disputeReasonId );

				}
				request.setAttribute("disputes", (ArrayList<Disputes>)OpsManageDisputesDao.class.getConstructor().newInstance().getAllDisputes());					
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsAllDisputeReasonsPage()).forward(request, response);
				} finally {
					if(langPref !=null) langPref=null; if(disputeReasonDesc !=null) disputeReasonDesc=null; if(payMode !=null) payMode=null;
					if(userId !=null) userId=null; if(userType !=null) userType=null; if(status !=null) status=null;
					if(opsUserType !=null) opsUserType=null;


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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			 ServletContext ctx) throws Exception {
		
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null)
			Utilities.callOpsException(request, response, ctx, "Session has expired, please log in again");
		
		switch (rulesaction) {
		
		
		case "opsadd_dispute":
			try {
				User user = null;
				String operationId = null;
				String comment = "";	String reasonId = null; 
				String transactionId = "";	String status = "A";
				String userType = "O";  String referenceNo=null;
				Boolean success = false;
				JsonObject obj = new JsonObject();
				PrintWriter output = null;
				String subject=null; String content=null;
				Customer c_Details=null;
				user = (User) session.getAttribute("SESS_USER");
				operationId = user.getUserId();
				userType = user.getUserType();
				if(request.getParameter("dsptcomment")!=null)	
					comment = StringUtils.trim( request.getParameter("dsptcomment") );
				if(request.getParameter("hdnreasonid")!=null)
					reasonId = StringUtils.trim( request.getParameter("hdnreasonid") );
				if(request.getParameter("inputtransactionid")!=null)
					transactionId = StringUtils.trim( request.getParameter("inputtransactionid") );
				if(request.getParameter("inputreferenceno")!=null)
					referenceNo = StringUtils.trim( request.getParameter("inputreferenceno") );
				
				 c_Details=(Customer)CustomerDao.class.getConstructor().newInstance().getFullCustomerProfile(referenceNo);
				 
				 if(c_Details.getName()==null) {
					 NeoBankEnvironment.setComment(2, className, "User does not exist");
					   Utilities.sendJsonResponse(response, "userdoesnotexists", "User of that Relationship No does not exist");
						return;
				 }

				success = OpsManageDisputesDao.class.getConstructor().newInstance().addNewDispute(
						transactionId, operationId, userType, comment, reasonId,  status, referenceNo);
				if(success == false) {
	        		obj.addProperty("error", "true"); 
	        		obj.addProperty("message", "Raising Dispute failed"); 
	        	}else {
	        		//TODO Send Email to the user
	        		// Module Code- OAD- Ops Add Disputes
	        		SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(operationId, userType,
							"OAD"," Ops Add Dispute ");
	        		obj.addProperty("error", "false"); 
	        		obj.addProperty("message", "Dispute Raised suceessfully");
	        		

					// Email to user
	        		 subject="NEW DISPUTE RAISED!";
	        		 if (transactionId.equals("")==false||transactionId==null) {
							content="A dispute for the transaction:" +transactionId +", with the message:- " +comment+", was raised on your behalf. "+System.lineSeparator()+". We are working to resolve on your issue as soon as possible.";
						}else {
							content="A dispute with the message " +comment+", was raised on your behalf. "+System.lineSeparator()+". We are working to resolve on your issue as soon as possible.";
						}
					// Send to user
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
	        		
	        		// Email to Operations Team
					    subject="NEW DISPUTE RAISED!";
					    if (transactionId.equals("")||transactionId==null) {
							content="User of relationship no:- "+Utilities.maskedRelationshipNumber(referenceNo)+" has raised dispute for the transaction:" +transactionId +", with the message." +comment+System.lineSeparator()+"Please have a look it.";
						}else {
							content="User of relationship no:- "+Utilities.maskedRelationshipNumber(referenceNo)+"has raised dispute with the message." +comment+System.lineSeparator()+"Please have a look at it.";
						}
					    
					    String operationsEmail =NeoBankEnvironment.getOPsNewDisputeRaisedEmailAddressNotifier() ;
						String opsSubject = subject;
						String opsContent = content;
						String opsName = "Operations Team";
						
						ExecutorService executor2 = (ExecutorService) ctx.getAttribute("EMAIL_EXECUTOR");
						NeoBankEnvironment.setComment(3, className, "executor is  " + executor2);
						@SuppressWarnings({ "unused", "rawtypes" })
						Future future2 = (Future) executor2.submit(new Runnable() {
							public void run() {
								
								try {
									SendEmailUtility.sendMail(operationsEmail, opsSubject, opsContent,opsName);
								} catch (Exception e) {
									NeoBankEnvironment.setComment(1, className, "Exception in sending mail " + e.getMessage());
								}
							}
						});
	        	}
				try {
					output = response.getWriter();
					output.print(obj);
				}finally {
					if(comment!=null) comment=null; if(userType!=null) userType=null; if(reasonId!=null) reasonId=null; 
					if(user!=null) user=null; if(operationId!=null) operationId=null; if(referenceNo!=null) referenceNo=null;
					if(transactionId!=null) transactionId=null; if(status!=null) status=null; 
					if(obj!=null) obj = null;
					if(output!=null) output.close();
					if (c_Details!=null)c_Details=null; if (subject!=null);subject=null; if (content!=null);content=null;
				}
				
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Raising Dispute failed, Please try again letter");
			}
			break;
		case "ops_add_dispute_comment":
			try {
				JsonObject obj = new JsonObject();
				PrintWriter output = null;
				User user = null;
				String operationId = null;
				String disputeId = null;  String comment = null;
				String userType = null; String userId = null; 
				String referenceNo=null;
				Boolean success = false;
				Customer c_Details=null;
				String subject=null; String content=null;
				if (session.getAttribute("SESS_USER") == null) 
					throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				operationId = user.getUserId();
				userType = user.getUserType();
				if(request.getParameter("hdndispid")!=null)
					disputeId = StringUtils.trim( request.getParameter("hdndispid") );
				if(request.getParameter("comment")!=null)
					comment = StringUtils.trim( request.getParameter("comment") );
				if(request.getParameter("referenceno")!=null)
					referenceNo = StringUtils.trim( request.getParameter("referenceno") );
				success = OpsManageDisputesDao.class.getConstructor().newInstance().addCommentOnADispute(disputeId, 
						operationId, user.getUserType(), comment );
				NeoBankEnvironment.setComment(3, className, referenceNo);
				if(success == false) {
	        		obj.addProperty("error", "true"); 
	        		obj.addProperty("message", "Problem in adding a new comment on the disputeid : "+disputeId); 
	        	}else {
	        		SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(operationId, userType,
							"OAC"," Ops Add Dispute Comment");
	        		obj.addProperty("error", "false"); 
	        		obj.addProperty("message", "Dispute comment added suceessfully");
	        		
	        		// Send Email to user
	        		c_Details=(Customer)CustomerDao.class.getConstructor().newInstance().getFullCustomerProfile(referenceNo);
	        		
	        		subject = "DISPUTE ID :- #"+disputeId+" Updated!";
					content= "Our team has updated the Dispute Id:- #"+disputeId+" with the following message " +comment +".";
	        		// Send to user
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
	        		
	        		// Email to Operations Team

	        		subject = "DISPUTE ID :- #"+disputeId+" Updated!";
					content= "The Dispute Id:- #"+disputeId+" for user "+Utilities.maskedRelationshipNumber(referenceNo)+" has been updated with the following message " +comment +". ";
				   
				    
				    String operationsEmail =NeoBankEnvironment.getOPsNewDisputeRaisedEmailAddressNotifier() ;
					String opsSubject = subject;
					String opsContent = content;
					String opsName = "Operations Team";
					//
					ExecutorService executor2 = (ExecutorService) ctx.getAttribute("EMAIL_EXECUTOR");
					NeoBankEnvironment.setComment(3, className, "executor is  " + executor2);
					@SuppressWarnings({ "unused", "rawtypes" })
					Future future2 = (Future) executor2.submit(new Runnable() {
						public void run() {
							
							try {
								SendEmailUtility.sendMail(operationsEmail, opsSubject, opsContent,opsName);
							} catch (Exception e) {
								NeoBankEnvironment.setComment(1, className, "Exception in sending mail " + e.getMessage());
							}
						}
					});
	        	}
				try {
					output = response.getWriter();
					output.print(obj);
				}finally {
					if(disputeId!=null) disputeId=null; if(comment!=null) comment=null;
					if(userType!=null) userType=null; if(userId!=null) userId=null;
					if(obj!=null) obj = null; if(operationId!=null) operationId=null;
					if(output!=null) output.close(); if (referenceNo!=null)referenceNo=null;
					if (c_Details!=null)c_Details=null; if (subject!=null);subject=null; if (content!=null);content=null;
				}
				
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Adding Dispute comment failed, Please try again letter");
			}
			break;
		case "ops_update_dispute_status":
			try {
				JsonObject obj = new JsonObject();
				PrintWriter output = null;
				User user = null;
				String operationId = null;
				String disputeid = null;	String status = "A";
				String userId = null; String userType = null;
				Customer c_Details=null;
				String referenceNo=null;
				boolean success = false;
				if (session.getAttribute("SESS_USER") == null) 
					throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				operationId = user.getUserId();
				userType="O"; 	ConcurrentHashMap<String, String> hashStatus = new ConcurrentHashMap<String,String>();	
				String subject=null; String content=null;
				
				hashStatus.put("A","Active");
				hashStatus.put("P","In Progress"); 
				hashStatus.put("C","Closed");
				
				if(request.getParameter("hdndispid")!=null)		disputeid = StringUtils.trim( request.getParameter("hdndispid") );
				if(request.getParameter("selstatus")!=null)		status = StringUtils.trim( request.getParameter("selstatus") );
				if(request.getParameter("referenceno")!=null)	referenceNo = StringUtils.trim( request.getParameter("referenceno") );
				
				success = OpsManageDisputesDao.class.getConstructor().newInstance().updateDisputeStatus(disputeid, status);

				if(success == false) {
	        		obj.addProperty("error", "true"); 
	        		obj.addProperty("message", "Problem Updating Dispute status on the disputeid : "+disputeid); 
	        	}else {
	        		SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(operationId, userType,
							"OUS"," Ops Update Dispute Status ");
	        		
	        		obj.addProperty("error", "false"); 
	        		obj.addProperty("message", "Dispute status Updated suceessfully");

	        		// Send Email to user
	        		c_Details=(Customer)CustomerDao.class.getConstructor().newInstance().getFullCustomerProfile(referenceNo);
	        		subject = "DISPUTE #"+disputeid+" Status Update!";
					content="Our team has updated the Dispute Id:- "+disputeid+" status to " +hashStatus.get(status) +".";
	        		// Send to user
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
	        		
	        		// Email to Operations Team
					    subject = "DISPUTE #"+disputeid+" Status Update!";
						content="The Dispute Id:- #"+disputeid+" for user "+Utilities.maskedRelationshipNumber(referenceNo)+" status has been updated to " +hashStatus.get(status)+". ";
						
					    String operationsEmail =NeoBankEnvironment.getOPsNewDisputeRaisedEmailAddressNotifier() ;
						String opsSubject = subject;
						String opsContent = content;
						String opsName = "Operations Team";
						
						ExecutorService executor2 = (ExecutorService) ctx.getAttribute("EMAIL_EXECUTOR");
						NeoBankEnvironment.setComment(3, className, "executor is  " + executor2);
						@SuppressWarnings({ "unused", "rawtypes" })
						Future future2 = (Future) executor2.submit(new Runnable() {
							public void run() {
								
								try {
									SendEmailUtility.sendMail(operationsEmail, opsSubject, opsContent,opsName);
								} catch (Exception e) {
									NeoBankEnvironment.setComment(1, className, "Exception in sending mail " + e.getMessage());
								}
							}
						});
	        	}
				try {
					output = response.getWriter();
					output.print(obj);
				}finally {
					if(disputeid!=null) disputeid=null; if(operationId!=null) operationId=null;
					if(status!=null) status=null; if(userId!=null) userId=null; if(userType!=null) userType=null;
					if(obj!=null) obj = null;
					if(output!=null) output.close();
					if (c_Details!=null)c_Details=null; if (subject!=null);subject=null; if (content!=null);content=null;
					if (referenceNo!=null)referenceNo=null; if (hashStatus!=null)hashStatus=null;
				}
				
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Updating Dispute status failed, Please try again letter");
			}
			break;
		
		default:
			throw new IllegalArgumentException("Rule not defined value: " + rulesaction);
		}

	}

	

}
