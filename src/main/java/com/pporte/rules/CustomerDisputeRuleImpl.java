package com.pporte.rules;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import framework.v8.Rules;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.CustomerDao;
import com.pporte.dao.CustomerDisputesDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.model.Customer;
import com.pporte.model.DisputeReasons;
import com.pporte.model.DisputeTracker;
import com.pporte.model.Disputes;
import com.pporte.model.User;
import com.pporte.utilities.SendEmailUtility;
import com.pporte.utilities.Utilities;

public class CustomerDisputeRuleImpl implements Rules {
	private static String className = CustomerDisputeRuleImpl.class.getSimpleName();

	@Override
	public void performOperation(String ruleaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null) Utilities.callException(request, response, ctx, "Session has expired, please log in again");
		
		switch (ruleaction) {
		
			case "View Dispute":
				try {
					String langPref=null;
					if(request.getParameter("hdnlang")!=null)			langPref = request.getParameter("hdnlang").trim();
					request.setAttribute("lastaction", "custdspt");
					request.setAttribute("langPref", langPref);
					request.setAttribute("lastrule", ruleaction);
					response.setContentType("text/html");
					try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerViewDisputePage()).forward(request,
								response);
					} finally {
						if (langPref!=null)langPref=null;
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className,
							"Error for  rules: " + ruleaction + " is " + e.getMessage());
					Utilities.callException(request, response, ctx, e.getMessage());
				}
				break;
	
			case "Raise Disputes":
				try {
					String langPref=null;
					if(request.getParameter("hdnlang")!=null)			langPref = request.getParameter("hdnlang").trim();
					NeoBankEnvironment.setComment(3,className,"lang pref"+langPref);
					request.setAttribute("lastaction", "custdspt");
					request.setAttribute("lastrule", "View Dispute");
					request.setAttribute("langPref", langPref);
					response.setContentType("text/html");
					String userType = "C";
					try {
						request.setAttribute("dsptreason", (List<DisputeReasons>) CustomerDisputesDao.class.getConstructor()
								.newInstance().getAllDisputeReasons(userType));
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerRaiseDisputePage()).forward(request,
								response);
					} finally {
						if (userType != null)
							userType = null; if (langPref!=null)langPref=null;
					}
	
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: " + ruleaction + " is " + e.getMessage());
					Utilities.callException(request, response, ctx, e.getMessage());
				}
				break;
	
			case "view_cust_specific_dispute":
				try {
					String langPref=null;
					if(request.getParameter("hdnlang")!=null)			langPref = request.getParameter("hdnlang").trim();
					request.setAttribute("lastaction", "custdspt");
					request.setAttribute("lastrule", ruleaction); 
					request.setAttribute("langPref", langPref);
					String disputeId = null;	
					if(request.getParameter("hdnreqid")!=null)
						disputeId = StringUtils.trim( request.getParameter("hdnreqid") );
	
					request.setAttribute("showdispute", (Disputes)CustomerDisputesDao.class.getConstructor().newInstance().getDisputeDetail(disputeId));
					request.setAttribute("disputethreads", (List<DisputeTracker>)CustomerDisputesDao.class.getConstructor().newInstance().getAllDisputeTrackers(disputeId));
					try{
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerViewSpecificDisputePage()).forward(request, response);
					}finally {
						if(disputeId!=null) disputeId=null; if (langPref!=null)langPref=null;
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: "+ruleaction+" is "+e.getMessage());
					Utilities.callException(request, response, ctx, e.getMessage());
				}
				break;
			}
	

	}

	@Override
	public void performJSONOperation(String ruleaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {

	}

	@Override
	public void performMultiPartOperation(String ruleaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		
		
		switch (ruleaction) {
		
		case "fetchcustdisputes":
		
			try {
				String relationshipNo=null; String userType=null; List <Disputes> disputesList=null;
				PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject(); 
				if(request.getParameter("relno")!=null)	relationshipNo = StringUtils.trim(request.getParameter("relno"));
				userType="C";
				HttpSession session = request.getSession(false); 
				if (session.getAttribute("SESS_USER") == null) {
					Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
					return;
				}
				
				disputesList =(List<Disputes>)CustomerDisputesDao.class.getConstructor().newInstance().getAllDisputes(relationshipNo);
				
				if (disputesList!=null) {
					obj.add("data", gson.toJsonTree(disputesList));
					obj.add("error", gson.toJsonTree("false"));
				}else {
					obj.add("error", gson.toJsonTree("nodata")); 
					obj.add("msg", gson.toJsonTree("No Disputes")); 
				}
				try {
					NeoBankEnvironment.setComment(3, className," fetchcustdisputes String is " + gson.toJson(obj));
					out_json.print(gson.toJson(obj));
				} finally {
					if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
					if (userType != null)userType = null; if(obj!=null) obj = null; if (disputesList!=null)disputesList=null;
				}
			}catch(Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+ruleaction+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
		break;
		case "fetchcustdisputes_mbl":
			
			try {
				String relationshipNo=null; String userType=null; List <Disputes> disputesList=null; String tokenValue = null;
				PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject(); 
				String langPref=null;
				if(request.getParameter("relno")!=null)	relationshipNo = StringUtils.trim(request.getParameter("relno"));
				userType="C";
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

				//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v�lido, vuelva a iniciar sesi�n");

					}else {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					}
					return;
				}
				
				disputesList =(List<Disputes>)CustomerDisputesDao.class.getConstructor().newInstance().getAllDisputes(relationshipNo);
				
				if (disputesList!=null) {
					obj.add("data", gson.toJsonTree(disputesList));
					obj.add("error", gson.toJsonTree("false"));
				}else {
					obj.add("error", gson.toJsonTree("true")); 
					if(langPref.equalsIgnoreCase("ES")) {
						obj.add("msg", gson.toJsonTree("Sin disputas")); 

					}else {
						obj.add("msg", gson.toJsonTree("No Disputes")); 
					}
				}
				try {
					NeoBankEnvironment.setComment(3, className," fetchcustdisputes String is " + gson.toJson(obj));
					out_json.print(gson.toJson(obj));
				} finally {
					if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
					if (userType != null)userType = null; if(obj!=null) obj = null; if (tokenValue != null)tokenValue = null;
					if (disputesList!=null)disputesList=null;
				}
			}catch(Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+ruleaction+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
		break;
		
		case "getdisputereasonmbl":
			try {
				String userType=null; List<DisputeReasons> disputeReasons=null;
				PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject(); 
				userType="C";
				disputeReasons =(List<DisputeReasons>) CustomerDisputesDao.class.getConstructor().newInstance().getAllDisputeReasons(userType);
				
				if (disputeReasons!=null) {
					obj.add("data", gson.toJsonTree(disputeReasons));
					obj.add("error", gson.toJsonTree("false"));
				}else {
					obj.add("error", gson.toJsonTree("nodata")); 
				}
				try {
					NeoBankEnvironment.setComment(3, className, ruleaction+" String is " + gson.toJson(obj));
					out_json.print(gson.toJson(obj));
				} finally {
					if (out_json != null)out_json.close(); if (gson != null)gson = null;
					if (userType != null)userType = null; if(obj!=null) obj = null; 
					if(disputeReasons!=null)disputeReasons=null;
				}
			}catch(Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+ruleaction+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			break;
		
		
		case "custadd_dispute":
			try {
				User user = null;
				String relationshipNo = null;
				String comment = "";	String reasonId = null; 
				String transactionId = "";	String status = "A";
				String userType = null; 
				Boolean success = false;
				JsonObject obj = new JsonObject();
				PrintWriter output = null;
				HttpSession session = request.getSession(false);
				 String subject=null; String content=null;
				if (session.getAttribute("SESS_USER") == null) {
					Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
					return;
				}
				user = (User) session.getAttribute("SESS_USER");
				relationshipNo = user.getRelationshipNo();
				userType = "C";
				String langPref=null;
				if(request.getParameter("hdnlang")!=null)			langPref = request.getParameter("hdnlang").trim();
				if(request.getParameter("dsptcomment")!=null)	
					comment = StringUtils.trim( request.getParameter("dsptcomment") );
				if(request.getParameter("hdnreasonid")!=null)
					reasonId = StringUtils.trim( request.getParameter("hdnreasonid") );
				if(request.getParameter("inputtransactionid")!=null)
					transactionId = StringUtils.trim( request.getParameter("inputtransactionid") );

				success = CustomerDisputesDao.class.getConstructor().newInstance().addNewDispute(transactionId, relationshipNo, userType, comment, reasonId,  status);
				if(success == false) {
	        		obj.addProperty("error", "true"); 
	        		 if (langPref.equals("EN")) {
	        			    obj.addProperty("message", "Raising Dispute failed");
						}else if ((langPref.equalsIgnoreCase("ES"))) {
							obj.addProperty("message", "No se ha podido generar la disputa");
						}
	        		 
	        	}else {
	        		
	        		// Customer Add Dispute
	        		SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, userType,
							"CAD"," Customer Add Dispute ");
	        		obj.addProperty("error", "false"); 
	        		 if (langPref.equals("EN")) {
	        			    obj.addProperty("message", "Dispute Raised suceessfully");
						}else if ((langPref.equalsIgnoreCase("ES"))) {
						   obj.addProperty("message", "Disputa planteada con éxito");
						}
	        		
	        		
	        		// Email to user
	        		 if (langPref.equals("EN")) {
	        			 subject="NEW DISPUTE RAISED!";
						}else if ((langPref.equalsIgnoreCase("ES"))) {
						 subject="¡NUEVA DISPUTA PLANTADA!";
						}
	        		 
	        		 if (transactionId.equals("")==false||transactionId==null) {
	        			 if (langPref.equals("EN")) {
	        				   content="You have successfully raised a dispute for the transaction:" +transactionId +", with the message " +comment+System.lineSeparator()+". We are working to resolve on your issue as soon as possible.";
							}else if ((langPref.equalsIgnoreCase("ES"))) {
								content="Ha planteado con éxito una disputa por la transacción:" +transactionId +", con el mensaje " +comment+System.lineSeparator()+". Estamos trabajando para resolver su problema lo antes posible."; 
							}
						}else {
							 if (langPref.equals("EN")) {
								 content="You have successfully raised a dispute with the message " +comment+System.lineSeparator()+". We are working to resolve on your issue as soon as possible.";
								}else if ((langPref.equalsIgnoreCase("ES"))) {
								 content="Ha planteado correctamente una disputa con el mensaje " +comment+System.lineSeparator()+". Estamos trabajando para resolver su problema lo antes posible.";
								}
							
						}
					// Send to user
					String sendto = user.getCustomerId();
					String sendSubject = subject;
					String sendContent = content;
					String customerName = user.getName();
					
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
					    if (transactionId.equals("")==false||transactionId==null) {
							content="User of relationship no:- "+Utilities.maskedRelationshipNumber(relationshipNo)+" has raised dispute for the transaction:" +transactionId +", with the message." +comment+System.lineSeparator()+"Please have a look it.";
						}else {
							content="User of relationship no:- "+Utilities.maskedRelationshipNumber(relationshipNo)+"has raised dispute with the message." +comment+System.lineSeparator()+"Please have a look at it.";
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
					if(user!=null) user=null; if(relationshipNo!=null) relationshipNo=null; 
					if(transactionId!=null) transactionId=null; if(status!=null) status=null; 
					if(obj!=null) obj = null;
					if(output!=null) output.close();
					if (subject!=null);subject=null; if (content!=null);content=null;if (langPref!=null)langPref=null;
				}
				
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+ruleaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Raising Dispute failed, Please try again letter");
			}
			break;
		case "custadd_dispute_mbl":
			NeoBankEnvironment.setComment(3, className, "inside custadd_dispute_mbl");
			try {
				
				String relationshipNo = null;
				String comment = "";	String reasonId = null; 
				String transactionId = "";	String status = "A";
				String userType = null; String tokenValue = null;
				Boolean success = false;String langPref=null;
				JsonObject obj = new JsonObject();
				PrintWriter output = null;
				String subject=null; String content=null;
				Customer c_Details=null;
				Gson gson=new Gson();
				userType = "C";
				List <Disputes> disputesList=null;
				if(request.getParameter("dsptcomment")!=null)	
					comment = StringUtils.trim( request.getParameter("dsptcomment") );
				if(request.getParameter("hdnreasonid")!=null)
					reasonId = StringUtils.trim( request.getParameter("hdnreasonid") );
				if(request.getParameter("inputtransactionid")!=null)
					transactionId = StringUtils.trim( request.getParameter("inputtransactionid") );
				if(request.getParameter("relno")!=null)
					relationshipNo = StringUtils.trim( request.getParameter("relno") );
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

				//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v�lido, vuelva a iniciar sesi�n");

					}else {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					}
					return;
				}
				NeoBankEnvironment.setComment(3,className," comment "+comment+"reasonId "+reasonId);
				
				success = CustomerDisputesDao.class.getConstructor().newInstance().addNewDispute(transactionId, relationshipNo, userType, comment, reasonId,  status);
				
				if(success == false) {
					obj.addProperty("error", "true"); 
					if(langPref.equalsIgnoreCase("ES")) {
						obj.addProperty("message", "No se ha podido generar la disputa"); 

					}else {
						obj.addProperty("message", "Raising Dispute failed"); 
					}
				}else {
					// Get all disputes
					disputesList =(List<Disputes>)CustomerDisputesDao.class.getConstructor().newInstance().getAllDisputes(relationshipNo);
					
					// Customer Add Dispute
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, userType,
							"CAD"," Customer Add Dispute ");
					obj.addProperty("error", "false"); 
					if(langPref.equalsIgnoreCase("ES")) {
						obj.addProperty("message", "Disputa planteada con �xito");

					}else {
						obj.addProperty("message", "Dispute Raised successfully");
					}
					obj.add("data", gson.toJsonTree(disputesList)); 

					
					// Email to user
	        		 subject="NEW DISPUTE RAISED!";
	        		 if (transactionId.equals("")==false||transactionId==null) {
	        			 	if(langPref.equalsIgnoreCase("ES")) {
								content="Has presentado correctamente una disputa por la transacci�n:" +transactionId +", con el mensaje " +comment+System.lineSeparator()+". Estamos trabajando para resolver su problema lo antes posible.";

		 					}else {
								content="You have successfully raised a dispute for the transaction:" +transactionId +", with the message " +comment+System.lineSeparator()+". We are working to resolve on your issue as soon as possible.";
		 					}
						}else {
							if(langPref.equalsIgnoreCase("ES")) {
								content="Has presentado correctamente una disputa por la transacci�n:" +comment+System.lineSeparator() +". Estamos trabajando para resolver su problema lo antes posible.";

		 					}else {
								content="You have successfully raised a dispute for the transaction:" +comment+System.lineSeparator() +". We are working to resolve on your issue as soon as possible.";
		 					}
						}
					// Send to user
	        		 c_Details=(Customer)CustomerDao.class.getConstructor().newInstance().getFullCustomerProfile(relationshipNo);
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
						
					    if (transactionId.equals("")==false||transactionId==null) {
					    	content="User of relationship no:- "+Utilities.maskedRelationshipNumber(relationshipNo)+" has raised dispute for the transaction:" +transactionId +", with the message." +comment+System.lineSeparator()+"Please have a look it.";
						}else {
							content="User of relationship no:- "+Utilities.maskedRelationshipNumber(relationshipNo)+"has raised dispute with the message." +comment+System.lineSeparator()+"Please have a look at it.";
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
					NeoBankEnvironment.setComment(3, className, ruleaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(obj);
				}finally {
					if(comment!=null) comment=null; if(userType!=null) userType=null; if(reasonId!=null) reasonId=null; 
					 if(relationshipNo!=null) relationshipNo=null; 
					if(transactionId!=null) transactionId=null; if(status!=null) status=null; 
					if(obj!=null) obj = null; if (tokenValue != null)tokenValue = null;
					if(output!=null) output.close();
					if (c_Details!=null)c_Details=null;if (subject!=null);subject=null; if (content!=null);content=null;
					if(disputesList!=null)disputesList=null; if (gson!=null)gson=null;
				}
				
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+ruleaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Raising Dispute failed, Please try again letter");
			}
			break;
		case "cust_add_dispute_comment":
			try {
				JsonObject obj = new JsonObject();
				PrintWriter output = null;
				User user = null;
				String relationshipNo = null;
				String disputeId = null;  String comment = null;
				String userType = null; String userId = null; 
				Boolean success = false;
				HttpSession session = request.getSession(false);
				String subject=null; String content=null;
				Customer c_Details=null;
				if (session.getAttribute("SESS_USER") == null) {
					Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
					return;
				}
				user = (User) session.getAttribute("SESS_USER");
				userType="C";
				String langPref=null;
				if(request.getParameter("hdnlang")!=null)			langPref = request.getParameter("hdnlang").trim();
				relationshipNo = user.getRelationshipNo();
				if(request.getParameter("hdndispid")!=null)
					disputeId = StringUtils.trim( request.getParameter("hdndispid") );
				if(request.getParameter("comment")!=null)
					comment = StringUtils.trim( request.getParameter("comment") );
				success = CustomerDisputesDao.class.getConstructor().newInstance().addCommentOnADispute(disputeId, 
						relationshipNo, userType, comment );
				
				if(success == false) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, userType,
							"CAC"," Customer Add Dispute Comment ");
	        		obj.addProperty("error", "true");
	        		 if (langPref.equals("EN")) {
	        			 obj.addProperty("message", "Problem in adding a new comment on the disputeid : "+disputeId);
						}else if ((langPref.equalsIgnoreCase("ES"))) {
							obj.addProperty("message", "Problema al agregar un nuevo comentario en la disputad : "+disputeId);
						}
	        		 
	        	}else {
	        		
	        		obj.addProperty("error", "false"); 
	        		 if (langPref.equals("EN")) {
	        			    obj.addProperty("message", "Dispute comment added suceessfully");
						}else if ((langPref.equalsIgnoreCase("ES"))) {
							obj.addProperty("message", "Comentario de disputa añadido con éxito");	
						}
	        		
	        		 if (langPref.equals("EN")) {
	        			 	subject = "DISPUTE #"+disputeId+" COMMENT!";
	 					 	content="You have added a comment for the Dispute ID:- #"+disputeId+" with the message: " +comment +System.lineSeparator()+". We are working to resolve on your issue as soon as possible.";
						}else if ((langPref.equalsIgnoreCase("ES"))) {
							subject = "DISPUTA #"+disputeId+" ¡COMENTARIO!";
							content="Ha agregado un comentario para el ID de disputa:- #"+disputeId+" con el mensaje: " +comment +System.lineSeparator()+". Estamos trabajando para resolver su problema lo antes posible.";
						}
	        		
	        		// Send to user
	        		c_Details=(Customer)CustomerDao.class.getConstructor().newInstance().getFullCustomerProfile(relationshipNo);
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
					    subject = "DISPUTE #"+disputeId+" COMMENT!";
						content="User of relationship no:- "+Utilities.maskedRelationshipNumber(relationshipNo)+" has added a comment for the Dispute Id:- #" +disputeId +", with the message:- " +comment+"."+System.lineSeparator()+"Please have a look it.";
						
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
					if(disputeId!=null) disputeId=null; if(comment!=null) comment=null;
					if(userType!=null) userType=null; if(userId!=null) userId=null;
					if(obj!=null) obj = null; if(user!=null) user=null; if (relationshipNo!=null) relationshipNo=null;
					if(output!=null) output.close();
					if (subject!=null);subject=null; if (content!=null);content=null;if (langPref!=null)langPref=null;	
					if (c_Details!=null)c_Details=null;
				}
				
				
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+ruleaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Adding Dispute comment failed, Please try again letter");
			}
			break;
		case "cust_update_dispute_status":
			try {
				JsonObject obj = new JsonObject();
				PrintWriter output = null;
				User user = null;
				String relationshipNo = null;
				String disputeid = null;	String status = null;
				String userId = null; String userType = null;
				boolean success = false;
				HttpSession session = request.getSession(false);
				Customer c_Details=null;
				
				ConcurrentHashMap<String, String> hashENStatus = new ConcurrentHashMap<String,String>();	
				ConcurrentHashMap<String, String> hashESStatus = new ConcurrentHashMap<String,String>();	
				 String subject=null; String content=null;
				 
				if (session.getAttribute("SESS_USER") == null) {
					Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
					return;
				}
				user = (User) session.getAttribute("SESS_USER");
				relationshipNo = user.getRelationshipNo();
				userType="C";
				String langPref=null;
				if(request.getParameter("hdnlang")!=null)			langPref = request.getParameter("hdnlang").trim();
				if(request.getParameter("hdndispid")!=null)		disputeid = StringUtils.trim( request.getParameter("hdndispid") );
				if(request.getParameter("selstatus")!=null)		status = StringUtils.trim( request.getParameter("selstatus") );
				success = CustomerDisputesDao.class.getConstructor().newInstance().updateDisputeStatus(disputeid, status);
				
				hashENStatus.put("A","Active");
				hashENStatus.put("P","In Progress"); 
				hashENStatus.put("C","Closed");
				
				hashESStatus.put("A","Activo");
				hashESStatus.put("P","En progreso");
				hashESStatus.put("C","Cerrado");
				
				if(success == false) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, userType,
							"CUS"," Customer Update Dispute Status ");
	        		obj.addProperty("error", "true"); 
	        		 if (langPref.equals("EN")) {
	        			 obj.addProperty("message", "Problem Updating Dispute status on the disputeid : "+disputeid);
						}else if ((langPref.equalsIgnoreCase("ES"))) {
							obj.addProperty("message", "Problema al actualizar el estado de la disputa en el ID de disputa : "+disputeid);	
						}
	        		 
	        	}else {
	        		
	        		obj.addProperty("error", "false"); 
	        		 if (langPref.equals("EN")) {
	        			     obj.addProperty("message", "Dispute status Updated successfully");
						}else if ((langPref.equalsIgnoreCase("ES"))) {
							obj.addProperty("message", "Estado de la disputa Actualizado con éxito");
						}
	        		
	        		 if (langPref.equals("EN")) {
	        			 subject = "DISPUTE #"+disputeid+" Status Update!";
	 					content="You have updated status for the Dispute ID:- #"+disputeid+" to: " +hashENStatus.get(status)+".";
						}else if ((langPref.equalsIgnoreCase("ES"))) {
							subject = "DISPUTA #"+disputeid+" ¡Actualización de estado!";
							content="Ha actualizado el estado de la Disputa ID:- #"+disputeid+" a: " +hashESStatus.get(status)+".";
						}
	        		
	        		// Send to user
	        		c_Details=(Customer)CustomerDao.class.getConstructor().newInstance().getFullCustomerProfile(relationshipNo);
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
						content="User of relationship no:- "+Utilities.maskedRelationshipNumber(relationshipNo)+" has updated the status for the Dispute Id:- #" +disputeid+" to:- " +hashENStatus.get(status)+".";
						
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
					if(disputeid!=null) disputeid=null; if(relationshipNo!=null) relationshipNo=null;
					if(status!=null) status=null; if(userId!=null) userId=null; if(userType!=null) userType=null;
					if(obj!=null) obj = null;
					if(output!=null) output.close(); if (hashESStatus!=null)hashESStatus=null;if (hashENStatus!=null)hashENStatus=null;
					if (subject!=null);subject=null; if (content!=null);content=null;if (langPref!=null)langPref=null;	
					if (c_Details!=null)c_Details=null;
				}
				
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+ruleaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Updating Dispute status failed, Please try again letter");
			}
			break;
			
			case "getdisputethreadsmbl":
				try {
					PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject(); 
					String disputeId = null;  Disputes m_Disputes= null; String relationshipNo = null;
					List<DisputeTracker> disputeComments= null; String tokenValue = null;
					String langPref=null;
					NeoBankEnvironment.setComment(3, className," Inside getdisputethreadsmbl " );
					if(request.getParameter("disputeid")!=null)		disputeId = StringUtils.trim( request.getParameter("disputeid") );
					if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
					if(request.getParameter("relno")!=null)	relationshipNo = StringUtils.trim(request.getParameter("relno"));
					//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
					if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

					//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
					if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v�lido, vuelva a iniciar sesi�n");

						}else {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
						}
						return;
					}
					
					m_Disputes = (Disputes)CustomerDisputesDao.class.getConstructor().newInstance().getDisputeDetail(disputeId);
					disputeComments=  (List<DisputeTracker>)CustomerDisputesDao.class.getConstructor().newInstance().getAllDisputeTrackers(disputeId);
					// TODO:- Format date and time sent front end especially on the mobile side
					if (disputeComments!=null) {
						obj.add("error", gson.toJsonTree("false"));
						obj.add("dispute", gson.toJsonTree(m_Disputes));
						obj.add("comments", gson.toJsonTree(disputeComments));
					}else {
						obj.add("error", gson.toJsonTree("nocomments"));
						obj.add("dispute", gson.toJsonTree(m_Disputes));
					}
					try {
						NeoBankEnvironment.setComment(3, className," getdisputethreadsmbl String is " + gson.toJson(obj));
						out_json.print(gson.toJson(obj));
					} finally {
						if (out_json != null)out_json.close(); if (m_Disputes != null)m_Disputes = null; if (gson != null)gson = null;
						if (disputeComments != null)disputeComments = null; if(obj!=null) obj = null;if (disputeId != null)disputeId = null;
						if (tokenValue != null)tokenValue = null; if (relationshipNo != null)relationshipNo = null;
						if (langPref != null)langPref = null; 

					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+ruleaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Failed to get Dispute Comments, Please try again letter");
				}
			break;
			
			case "cust_add_dispute_comment_mbl":
				try {
					NeoBankEnvironment.setComment(3,className,"inside cust_add_dispute_comment_mbl");
					JsonObject obj = new JsonObject();String langPref=null;
					PrintWriter output = null;
					String relationshipNo = null; String tokenValue = null;
					String disputeId = null;  String comment = null;
					String userType = null; String userId = null; 
					Boolean success = false;
					userType="C";
					String subject=null; String content=null;
					Customer c_Details=null; 
					List<DisputeTracker>disputeComments=null;
					Gson gson=new Gson();
					if(request.getParameter("hdndispid")!=null)
						disputeId = StringUtils.trim( request.getParameter("hdndispid") );
					if(request.getParameter("comment")!=null)
						comment = StringUtils.trim( request.getParameter("comment") );
					if(request.getParameter("relno")!=null)
						relationshipNo = StringUtils.trim( request.getParameter("relno") );
					if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
					//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
					if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

					//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
					if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v�lido, vuelva a iniciar sesi�n");

						}else {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
						}
						return;
					}
					NeoBankEnvironment.setComment(3,className,"comment is "+comment+" relationshipNo "+relationshipNo);
					success = CustomerDisputesDao.class.getConstructor().newInstance().addCommentOnADispute(disputeId, 
							relationshipNo, userType, comment );
					
					if(success == false) {
						
		        		obj.addProperty("error", "true"); 
		        		if(langPref.equalsIgnoreCase("ES")) {
			        		obj.addProperty("message", "Problema al agregar un nuevo comentario en la disputad: "+disputeId); 

						}else {
			        		obj.addProperty("message", "Problem in adding a new comment on the disputeid : "+disputeId); 
						}
		        	}else {
		        		SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, userType,
								"CAC"," Customer Add Dispute Comment ");
		        		
						disputeComments=  (List<DisputeTracker>)CustomerDisputesDao.class.getConstructor().newInstance().getAllDisputeTrackers(disputeId);
			
		        		obj.addProperty("error", "false"); 
		        		if(langPref.equalsIgnoreCase("ES")) {
			        		obj.addProperty("message", "Comentario de disputa a�adido con �xito");

						}else {
			        		obj.addProperty("message", "Dispute comment added successfully");
						}
						obj.add("comments", gson.toJsonTree(disputeComments));

		        		
		        		// Email to user
		        		if(langPref.equalsIgnoreCase("ES")) {
			        		subject = "DISPUTAR #"+disputeId+" COMENTARIO!";
							content="Ha agregado un comentario para el ID de disputa:- #"+disputeId+" con el mensaje: " +comment +System.lineSeparator()+". Estamos trabajando para resolver su problema lo antes posible.";

						}else {
			        		subject = "DISPUTE #"+disputeId+" COMMENT!";
							content="You have added a comment for the Dispute ID:- #"+disputeId+" with the message: " +comment +System.lineSeparator()+". We are working to resolve on your issue as soon as possible.";

						}
						// Send to user
		        		 c_Details=(Customer)CustomerDao.class.getConstructor().newInstance().getFullCustomerProfile(relationshipNo);
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
						if(langPref.equalsIgnoreCase("ES")) {
			        		subject = "DISPUTAR #"+disputeId+" COMENTARIO!";
							content="User of relationship no:- "+Utilities.maskedRelationshipNumber(relationshipNo)+" has added a comment for the Dispute Id:- #" +disputeId +", with the message:- " +comment+"."+System.lineSeparator()+"Please have a look it.";

						}else {
			        		subject = "DISPUTE #"+disputeId+" COMMENT!";
							content="Usuario de relaci�n no:-"+Utilities.maskedRelationshipNumber(relationshipNo)+" ha agregado un comentario para el ID de disputa:- #" +disputeId +", con el mensaje:-" +comment+"."+System.lineSeparator()+"Por favor, eche un vistazo.";

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
						NeoBankEnvironment.setComment(3, className,ruleaction+"  String is " + gson.toJson(obj));
						output = response.getWriter();
						output.print(obj);
					}finally {
						if(disputeId!=null) disputeId=null; if(comment!=null) comment=null;
						if(userType!=null) userType=null; if(userId!=null) userId=null;
						if(obj!=null) obj = null; if(relationshipNo!=null) relationshipNo = null;
						if(output!=null) output.close();if (tokenValue != null)tokenValue = null;
						if (c_Details!=null)c_Details=null; if (subject!=null);subject=null; if (content!=null);content=null;
						if (gson!=null)gson=null; if (disputeComments!=null)disputeComments=null;
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+ruleaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Adding Dispute comment failed, Please try again letter");
				}
				break;
		}
		
	   }
	
}
