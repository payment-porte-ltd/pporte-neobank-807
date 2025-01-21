package com.pporte.rules;

import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.CustomerDao;
import com.pporte.dao.CustomerRetrivePasswordDao;
import com.pporte.dao.RegistrationDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.dao.UserLoginDao;
import com.pporte.model.Customer;
import com.pporte.model.User;
import com.pporte.utilities.SendEmailUtility;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;

public class CustomerRetrivePasswordRulesImpl implements Rules{
	private static String className = CustomerRetrivePasswordRulesImpl.class.getSimpleName();

	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx)	throws Exception {
		switch (rulesaction) {
		case "cust_fetch_password_page":
			try {
				String userId=null;
				if(request.getParameter("hdnuserid")!=null)	userId = StringUtils.trim(request.getParameter("hdnuserid"));
				request.setAttribute("lastaction", "lgt");
				request.setAttribute("lastrule", "cust_fetch_password_page");
				request.setAttribute("userid", userId);
				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerSetPasswordPage()).forward(request, response);
				} finally {
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			break;
		case "ops_fetch_password_page":
			try {
				String userId=null;
				if(request.getParameter("hdnuserid")!=null)	userId = StringUtils.trim(request.getParameter("hdnuserid"));
				request.setAttribute("lastaction", "lgt");
				request.setAttribute("lastrule", "ops_fetch_password_page");
				request.setAttribute("userid", userId);
				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getOpsSetPasswordPage()).forward(request, response);
				} finally {
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		case"Transfer Bitcoin":
			try {
				String langPref=null;
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "porte");
				request.setAttribute("lastrule", "Transfer Bitcoin");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getBitcoinP2PTransferPage()).forward(request, response);
				} finally {
					if(langPref !=null) langPref=null;
				}
				response.setContentType("text/html");
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
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
		//HttpSession session = request.getSession(false);
		switch (rulesaction) {
		case "lgnforgotpassword":
			NeoBankEnvironment.setComment(3, className, "Inside lgnforgotpassword");
			try {
				String userId=null; String userPwd=null;String userType=null;User user = null; boolean result = false;
				User userRel = null;JsonObject obj = new JsonObject();PrintWriter output = null;String passwordType="T";
				String temporaryPassWord=null; String subject=null; String content=null;String langPref=null;
				if(request.getParameter("username")!=null)				userId = request.getParameter("username").trim();
				if(request.getParameter("hdnpasswprdusertype")!=null)				userType = request.getParameter("hdnpasswprdusertype").trim();
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();
				
				NeoBankEnvironment.setComment(3,className,"langPref is "+langPref);
				
				//Where to get the relationship number or is the user id unique
				//RandomStringUtils.randomAlphabetic(6); 
				temporaryPassWord=RandomStringUtils.randomAlphabetic(6);
				temporaryPassWord="test1234"; // TODO:- Delete this line after testing
				NeoBankEnvironment.setComment(3,className,"user id is "+userId);
				 user = (User)UserLoginDao.class.getConstructor().newInstance().validateUserForgotPassword(userId, userType );	
				 if(user!=null) {
					 result = (boolean)RegistrationDao.class.getConstructor().newInstance().registerNewPassword(userId,passwordType,temporaryPassWord);
					 if(result) {
						 //CFP - Customer Forgot Password Request
						 SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(user.getRelationshipNo(),userType,"CFP","Forgot Password Request" );
							// TODO:- Send password to email
						 if(langPref.equals("EN")) {
							 obj.addProperty("error", "false");
							 obj.addProperty("message", "Temporary password has been sent to your email id");
							 
							 subject="PASSWORD RESET";
							 content="You have requested for a reset of password. Here is the temporary password you will use to reset your password:- "+temporaryPassWord; 
						 }else {
							 obj.addProperty("error", "false");
							 obj.addProperty("message", "Se ha enviado una contrase�a temporal a su direcci�n de correo electr�nico");
							 
							 subject="RESTABLECIMIENTO DE CONTRASE�A";
							 content="Ha solicitado un restablecimiento de contrase�a. Aqu� est� la contrase�a temporal que usar� para restablecer su contrase�a:- "+temporaryPassWord;
						 }
						 
						// Send to user
						String sendto = userId;
						String sendSubject = subject;
						String sendContent = content;
						String customerName = "User";
						
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
						 obj.addProperty("error", "true");
						 if(langPref.equalsIgnoreCase("EN")) {
							 obj.addProperty("message", "Failed to update new password");
						 }else {
							 obj.addProperty("message", "No se pudo actualizar la nueva contrase�a");
						 }
						
					 }
				 }else {
					 // Send consistent message even if user does not exists
					 if(langPref.equalsIgnoreCase("EN")) {
						 Utilities.sendJsonResponse(response, "error", "Temporary password has been sent to your email id"); 
					 }
					 Utilities.sendJsonResponse(response, "error", "Se ha enviado una contrase�a temporal a su direcci�n de correo electr�nico");
				 }
				 try {
					
					 output = response.getWriter();
					 output.print(obj);
					 NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
				} finally {
					if(output!=null) output.close();if(user!=null) user=null;
					if(userId!=null) userId=null;if(userPwd!=null) userPwd=null;if(userType!=null) userType=null;
					if(userRel!=null) userRel=null;if(obj!=null) obj=null;
					if (subject!=null);subject=null; if (content!=null);content=null;
				}
				 
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case lgnforgotpassword is "+e.getMessage());
			}
			break;
		case "cust_set_password":
			try {
				JsonObject obj = new JsonObject(); 
				PrintWriter output = null; 
				User user = null; String userType = "C"; 
				String oldPassword = null;String passwordType="P";
				String newPassword= null; 
				String userId=null;
				boolean success = false;
				boolean checkOldPassword = false;
				String subject=null; String content=null;
				
				if(request.getParameter("old_password")!=null)oldPassword = StringUtils.trim( request.getParameter("old_password") );
				if(request.getParameter("new_password")!=null)newPassword = StringUtils.trim( request.getParameter("new_password") );
				if(request.getParameter("userid")!=null)userId = StringUtils.trim( request.getParameter("userid") );
				
				NeoBankEnvironment.setComment(3, className, "userid is "+userId);
				user = (User)UserLoginDao.class.getConstructor().newInstance().validateUserLogin(userId, oldPassword, userType );	
				if(user==null) {
					Utilities.sendJsonResponse(response, "error", "User does not exist");
					return;
				}
				checkOldPassword = UserLoginDao.class.getConstructor().newInstance().checkUserPassword(userId,  oldPassword);
				if(!checkOldPassword) {
					Utilities.sendJsonResponse(response, "error", "Incorrect password try again");
					return;
				}
				//String password, String userId
				success = UserLoginDao.class.getConstructor().newInstance().updatePassword(newPassword, 
						userId,passwordType);
				if(success) {
					//CSP - Customer Set New Password
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(user.getRelationshipNo(), userType,
							"CSP","Customer set new password.");
					obj.addProperty("error", "false"); 
	        		obj.addProperty("message", "Password updated successfuly, Please login again");
	        		//TODO To the partner
				}else {
	        		obj.addProperty("error", "true"); 
	        		obj.addProperty("message", "Password update failed"); 
	        		
	        		 subject="PASSWORD RESET SUCCESSFUL!";
					 content="You have successfully reset your password."+System.lineSeparator()
					        +"Please make sure you do not share with anyone";
					// Send to user
					String sendto = userId;
					String sendSubject = subject;
					String sendContent = content;
					String customerName = "User";
					
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
				}
				try {
					NeoBankEnvironment.setComment(3, className, rulesaction+ " String is " + (obj.toString()));
					output = response.getWriter();
					output.print(obj);
				}finally {
					if(oldPassword!=null) oldPassword=null; 
					if(user!=null) user=null; if(userType!=null) userType=null;
					if(newPassword!=null) newPassword=null; ;
					if(obj!=null) obj = null;if (userId!=null) userId=null;
					if(output!=null) output.close();
					if (subject!=null);subject=null; if (content!=null);content=null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in Changing Password, Please try again letter");
			}
			
			break;
		case "ops_set_password":
			try {
				JsonObject obj = new JsonObject(); 
				PrintWriter output = null; 
				User user = null; String userType = "C"; 
				String oldPassword = null;String passwordType="P";
				String newPassword= null; 
				String userId=null;
				boolean success = false;
				boolean checkOldPassword = false;
				String subject=null; String content=null;
				
				if(request.getParameter("old_password")!=null)oldPassword = StringUtils.trim( request.getParameter("old_password") );
				if(request.getParameter("new_password")!=null)newPassword = StringUtils.trim( request.getParameter("new_password") );
				if(request.getParameter("userid")!=null)userId = StringUtils.trim( request.getParameter("userid") );
				
				NeoBankEnvironment.setComment(3, className, "userid is "+userId);
				user = (User)UserLoginDao.class.getConstructor().newInstance().validateOpsUserLogin(userId, oldPassword, userType );	
				if(user==null) {
					Utilities.sendJsonResponse(response, "error", "User does not exist");
					return;
				}
				checkOldPassword = UserLoginDao.class.getConstructor().newInstance().checkOpsUserPassword(userId,  oldPassword);
				if(!checkOldPassword) {
					Utilities.sendJsonResponse(response, "error", "Incorect password try again");
					return;
				}
				//String password, String userId
				success = UserLoginDao.class.getConstructor().newInstance().updateOpsPassword(newPassword, 
						userId,passwordType);
				if(success) {
					//OUP - Ops Update Password
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType,
							"OUP","Ops updated password on first time to login");
					obj.addProperty("error", "false"); 
	        		obj.addProperty("message", "Password updated successfuly, Please login again");
	        		//TODO To the partner
	        		
	        		 subject="PASSWORD RESET SUCCESSFUL!";
					 content="You have successfully reset your password."+System.lineSeparator()
					        +"Please make sure you do not share with anyone";
					// Send to user
					String sendto = userId;
					String sendSubject = subject;
					String sendContent = content;
					String customerName = "User";
					
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
	        		obj.addProperty("error", "true"); 
	        		obj.addProperty("message", "Password update failed"); 
				}
				try {
					NeoBankEnvironment.setComment(3, className, rulesaction+ " String is " + (obj.toString()));
					output = response.getWriter();
					output.print(obj);
				}finally {
					if(oldPassword!=null) oldPassword=null; 
					if(user!=null) user=null; if(userType!=null) userType=null;
					if(newPassword!=null) newPassword=null; ;
					if(obj!=null) obj = null;if (userId!=null) userId=null;
					if(output!=null) output.close();
					if (subject!=null);subject=null; if (content!=null);content=null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in Changing Password, Please try again letter");
			}
			
			break;
		case "cust_mobile_set_password":
			try {
				JsonObject obj = new JsonObject(); 
				PrintWriter output = null; 
				User user = null; String userType = "C"; 
				String oldPassword = null;String passwordType="P";
				String newPassword= null; String langPref=null;
				String relationshipNo=null;
				boolean success = false;
				boolean checkOldPassword = false;
				String subject=null; String content=null;
				Customer c_Details=null;
				if(request.getParameter("old_password")!=null)oldPassword = StringUtils.trim( request.getParameter("old_password") );
				if(request.getParameter("new_password")!=null)newPassword = StringUtils.trim( request.getParameter("new_password") );
				if(request.getParameter("relno")!=null)relationshipNo = StringUtils.trim( request.getParameter("relno") );
				if(request.getParameter("hdnlangpref")!=null)langPref = StringUtils.trim( request.getParameter("hdnlangpref") );
				
				NeoBankEnvironment.setComment(3, className, "relationshipNo is "+relationshipNo);
				user = (User)CustomerRetrivePasswordDao.class.getConstructor().newInstance().validateUserLogin(relationshipNo, oldPassword, userType );	
				if(user==null) {
					 if(langPref.equalsIgnoreCase("ES")) {
						 Utilities.sendJsonResponse(response, "error", "El usuario no existe");
					 }else {
						 Utilities.sendJsonResponse(response, "error", "User does not exist");
					 }
					
					return;
				}
				checkOldPassword = CustomerRetrivePasswordDao.class.getConstructor().newInstance().checkUserPassword(relationshipNo,  oldPassword);
				if(!checkOldPassword) {
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "error", "Contraseña incorrecta inténtalo de nuevo");
					 }else {
							Utilities.sendJsonResponse(response, "error", "Incorrect password try again");
					 }
					return;
				}
				//String password, String userId
				success = CustomerRetrivePasswordDao.class.getConstructor().newInstance().updateMobilePassword(newPassword, 
						relationshipNo,passwordType);
				if(success) {
					//CSP - Customer Set New Password
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(user.getRelationshipNo(), userType,
							"CSP","Customer set new password.");
					obj.addProperty("error", "false"); 
					if(langPref.equalsIgnoreCase("ES")) {
		        		obj.addProperty("message", "Contraseña actualizada con éxito, vuelva a iniciar sesión");
					 }else {
			        		obj.addProperty("message", "Password updated successfully, Please login again");
					 }
	        		//TODO To the partner
	        		 subject="PASSWORD RESET SUCCESSFUL!";
	        		 if(langPref.equalsIgnoreCase("ES")) {
	        			 content="Ha restablecido con éxito su contraseña."+System.lineSeparator()
					        +"Por favor, asegúrese de no compartir con nadie";
						 }else {
							 content="You have successfully reset your password."+System.lineSeparator()
						        +"Please make sure you do not share with anyone";
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
				}else {
	        		obj.addProperty("error", "true"); 
	        		if(langPref.equalsIgnoreCase("EN")) {
		        		obj.addProperty("message", "Error al actualizar la contraseña"); 
					 }else {
			        		obj.addProperty("message", "Password update failed"); 
					 }
				}
				try {
					NeoBankEnvironment.setComment(3, className, rulesaction+ " String is " + (obj.toString()));
					output = response.getWriter();
					output.print(obj);
				}finally {
					if(oldPassword!=null) oldPassword=null; 
					if(user!=null) user=null; if(userType!=null) userType=null;
					if(newPassword!=null) newPassword=null; ;
					if(obj!=null) obj = null;if (relationshipNo!=null) relationshipNo=null;
					if(output!=null) output.close();
					if (subject!=null);subject=null; if (content!=null);content=null;
					if (c_Details!=null)c_Details=null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in Changing Password, Please try again letter");
			}
			
			break;
		case "cust_change_password":
			try {
				JsonObject obj = new JsonObject(); 
				PrintWriter output = null; 
				User user = null; String userType = "C"; 
				String oldPassword = null;String passwordType="T";
				String newPassword= null; 
				String relationshipNo=null;
				boolean success = false;String temporaryPassWord=null;
				boolean checkOldPassword = false;
				 String subject=null; String content=null;
				 Customer c_Details=null;
				 String langPref = null;
				
				if(request.getParameter("old_password")!=null)oldPassword = StringUtils.trim( request.getParameter("old_password") );
				if(request.getParameter("relno")!=null)relationshipNo = StringUtils.trim( request.getParameter("relno") );
				if(request.getParameter("hdnlang")!=null)langPref = StringUtils.trim( request.getParameter("hdnlang") );
				
				NeoBankEnvironment.setComment(3, className, "relationshipNo is "+relationshipNo);
				user = (User)CustomerRetrivePasswordDao.class.getConstructor().newInstance().validateUserLogin(relationshipNo, oldPassword, userType );	
				if(user==null) {
					Utilities.sendJsonResponse(response, "error", "User does not exist");
					return;
				}
				checkOldPassword = CustomerRetrivePasswordDao.class.getConstructor().newInstance().checkUserPassword(relationshipNo,  oldPassword);
				if(!checkOldPassword) {
					Utilities.sendJsonResponse(response, "error", "Incorect password try again");
					return;
				}
				//String password, String userId
				//TODO
				//Where to get the relationship number or is the user id unique
				// 
				temporaryPassWord=RandomStringUtils.randomAlphabetic(6);
				temporaryPassWord= "test1234";// TODO:- Delete this line after testing
				success = CustomerRetrivePasswordDao.class.getConstructor().newInstance().updateMobilePassword(temporaryPassWord, 
						relationshipNo,passwordType);
				if(success) {
					//CCP - Customer Change Password
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(user.getRelationshipNo(), userType,
							"CCP","Customer changed Password");
					obj.addProperty("error", "false"); 
					if(langPref.equalsIgnoreCase("en")) {
						obj.addProperty("message", "Temporary Password has been send to your email.Please log in again.");
		        		
		        		subject="PASSWORD RESET";
						content="You have requested for a reset of password. Here is the temporary password you will use to reset your password:- "+temporaryPassWord;
					}else {
						obj.addProperty("message", "Se ha enviado una contraseña temporal a su correo electrónico. Vuelva a iniciar sesión.");
		        		subject="Se ha enviado una contraseña temporal a su correo electrónico. Vuelva a iniciar sesión.";
						content="Ha solicitado un restablecimiento de contraseña. Aquí está la contraseña temporal que usará para restablecer su contraseña:-"+temporaryPassWord;
					}
	        		
	        		// Send email to user
	        		c_Details=(Customer)CustomerDao.class.getConstructor().newInstance().getFullCustomerProfile(relationshipNo);
					String sendto = c_Details.getUnmaskedEmail();
					String sendSubject = subject;
					String sendContent = content;
					String customerName = c_Details.getName();;
					
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
	        		obj.addProperty("error", "true"); 
	        		if(langPref.equalsIgnoreCase("en")) {
	        			obj.addProperty("message", "Password update failed"); 
	        		}else {
	        			obj.addProperty("message", "Error al actualizar la contraseña"); 
	        		}
				}
				try {
					NeoBankEnvironment.setComment(3, className, rulesaction+ " String is " + (obj.toString()));
					output = response.getWriter();
					output.print(obj);
				}finally {
					if(oldPassword!=null) oldPassword=null; 
					if(user!=null) user=null; if(userType!=null) userType=null;
					if(newPassword!=null) newPassword=null; ;
					if(obj!=null) obj = null;if (relationshipNo!=null) relationshipNo=null;
					if(output!=null) output.close();
					if (c_Details!=null)c_Details=null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in Changing Password, Please try again letter");
			}
			
			break;
		case "cust_mbl_change_password":
			try {
				JsonObject obj = new JsonObject(); 
				PrintWriter output = null; 
				User user = null; String userType = "C"; 
				String oldPassword = null;String passwordType="T";
				String newPassword= null; 
				String relationshipNo=null;
				boolean success = false;String temporaryPassWord=null;
				boolean checkOldPassword = false;
				 String subject=null; String content=null;
				 Customer c_Details=null;
				 String langPref = null;String tokenValue=null;
				
				if(request.getParameter("old_password")!=null)oldPassword = StringUtils.trim( request.getParameter("old_password") );
				if(request.getParameter("relno")!=null)relationshipNo = StringUtils.trim( request.getParameter("relno") );
				if(request.getParameter("hdnlangpref")!=null)langPref = StringUtils.trim( request.getParameter("hdnlangpref") );
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));

				NeoBankEnvironment.setComment(3, className, "relationshipNo is "+relationshipNo);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es válido, vuelva a iniciar sesión");

					}else {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					}
					return;
				}
				user = (User)CustomerRetrivePasswordDao.class.getConstructor().newInstance().validateUserLogin(relationshipNo, oldPassword, userType );	
				if(user==null) {
					Utilities.sendJsonResponse(response, "error", "User does not exist");
					return;
				}
				checkOldPassword = CustomerRetrivePasswordDao.class.getConstructor().newInstance().checkUserPassword(relationshipNo,  oldPassword);
				if(!checkOldPassword) {
					Utilities.sendJsonResponse(response, "error", "Incorect password try again");
					return;
				}
				//String password, String userId
				//TODO
				//Where to get the relationship number or is the user id unique
				// 
				temporaryPassWord=RandomStringUtils.randomAlphabetic(6);
				temporaryPassWord= "test1234";// TODO:- Delete this line after testing
				success = CustomerRetrivePasswordDao.class.getConstructor().newInstance().updateMobilePassword(temporaryPassWord, 
						relationshipNo,passwordType);
				if(success) {
					//CCP - Customer Change Password
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(user.getRelationshipNo(), userType,
							"CCP","Customer changed Password");
					obj.addProperty("error", "false"); 
					if(langPref.equalsIgnoreCase("es")) {
												
						obj.addProperty("message", "Se ha enviado una contraseña temporal a su correo electrónico. Vuelva a iniciar sesión.");
		        		subject="Se ha enviado una contraseña temporal a su correo electrónico. Vuelva a iniciar sesión.";
						content="Ha solicitado un restablecimiento de contraseña. Aquí está la contraseña temporal que usará para restablecer su contraseña:-"+temporaryPassWord;
					}else {
						obj.addProperty("message", "Temporary Password has been send to your email.Please log in again.");
		        		
		        		subject="PASSWORD RESET";
						content="You have requested for a reset of password. Here is the temporary password you will use to reset your password:- "+temporaryPassWord;
					}
	        		
	        		// Send email to user
	        		c_Details=(Customer)CustomerDao.class.getConstructor().newInstance().getFullCustomerProfile(relationshipNo);
					String sendto = c_Details.getUnmaskedEmail();
					String sendSubject = subject;
					String sendContent = content;
					String customerName = c_Details.getName();;
					
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
	        		obj.addProperty("error", "true"); 
	        		if(langPref.equalsIgnoreCase("es")) {
	        			obj.addProperty("message", "Error al actualizar la contraseña"); 

	        		}else {
	        			obj.addProperty("message", "Password update failed"); 
	        		}
				}
				try {
					NeoBankEnvironment.setComment(3, className, rulesaction+ " String is " + (obj.toString()));
					output = response.getWriter();
					output.print(obj);
				}finally {
					if(oldPassword!=null) oldPassword=null; 
					if(user!=null) user=null; if(userType!=null) userType=null;
					if(newPassword!=null) newPassword=null; ;
					if(obj!=null) obj = null;if (relationshipNo!=null) relationshipNo=null;
					if(output!=null) output.close();
					if (c_Details!=null)c_Details=null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in Changing Password, Please try again letter");
			}
			
			break;
			
			default:
				throw new IllegalArgumentException("Rule not defined value: " + rulesaction);

			
			}
		}
}
