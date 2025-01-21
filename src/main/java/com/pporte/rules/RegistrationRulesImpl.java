package com.pporte.rules;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.stellar.sdk.KeyPair;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.CustomerDao;
import com.pporte.dao.CustomerWalletDao;
import com.pporte.dao.FundStellarAccountDao;
import com.pporte.dao.MerchantDao;
import com.pporte.dao.OpsManageCustomerDao;
import com.pporte.dao.OpsManageDashboardDao;
import com.pporte.dao.OpsManagePricingPlanDao;
import com.pporte.dao.RegistrationDao;
import com.pporte.dao.RemittanceDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.dao.TDAManagementDao;
import com.pporte.dao.UserLoginDao;
import com.pporte.model.Customer;
import com.pporte.model.DisputeTracker;
import com.pporte.model.AssetAccount;
import com.pporte.model.BitcoinDetails;
import com.pporte.model.MccGroup;
import com.pporte.model.OpsDashboard;
import com.pporte.model.Transaction;
import com.pporte.model.User;
import com.pporte.model.Wallet;
import com.pporte.utilities.BitcoinUtility;
import com.pporte.utilities.SendEmailUtility;
import com.pporte.utilities.StellarSDKUtility;
import com.pporte.utilities.Utilities;


import framework.v8.Rules;

public class RegistrationRulesImpl implements Rules{
	private static String className = RegistrationRulesImpl.class.getSimpleName();

	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx)	throws Exception {
		switch (rulesaction) {
		case "lgtdefault": case "Logout":
			try {
				HttpSession session	= request.getSession(false);	
				if(session!=null) {
					if(session.getAttribute("SESS_USER") !=null)
						session.invalidate(); 
				}
				response.setContentType("text/html");
				ctx.getRequestDispatcher(NeoBankEnvironment.getLoginPage()).forward(request, response);
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
		break;			
		
		case "lgnfetch":
			try {
				HttpSession session	= request.getSession(false); String relationshipNo = null;
				if(session ==null) session	= request.getSession(true); Wallet wallet = null;
				String userId = null; String langPref = null;	String tokenTime = "";  String userType=null;
				if(request.getParameter("hdnuserid")!=null)	userId = StringUtils.trim(request.getParameter("hdnuserid"));
				if(request.getParameter("hdnlangpref")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlangpref"));
				if(request.getParameter("lgtoken")!=null)	tokenTime = StringUtils.trim(request.getParameter("lgtoken"));
				if(request.getParameter("hdnlgusertype")!=null)	userType = StringUtils.trim(request.getParameter("hdnlgusertype"));
				
				//NeoBankEnvironment.setComment(3, className, "tokenTime: "+tokenTime);

				if(Utilities.validateTimeToken(tokenTime) == true) { 
					session.invalidate();
					throw new Exception ("token has expired");
				}
				NeoBankEnvironment.setComment(3, className, "User type is "+userType);
				User user = null;
				if(userType.equalsIgnoreCase("C")) {
					user = (User)UserLoginDao.class.getConstructor().newInstance().getUserDetails(userId ,userType);
				}else if (userType.equalsIgnoreCase("M")) {
					user = (User)UserLoginDao.class.getConstructor().newInstance().getMerchantUserDetails(userId);
				}
				if(user!=null) {
				
						user.setPricingPlanid((String)OpsManagePricingPlanDao.class.getConstructor().newInstance().getCustomerPricingPlanId(user.getRelationshipNo()));
						session.setAttribute("SESS_USER", user);
						request.setAttribute("langpref", langPref);// to check! whether it will return null if we put it as null in the finally block
						request.setAttribute("lastaction", "dash"); 
						request.setAttribute("lastrule", "Home"); 
					
				}else {
					// THis case has been already handled in other call, in this case user exists
				}
				
				response.setContentType("text/html");
				try {
					// Switch other users depending with the selected userType
					
					if (userType.equals("C")) {
						//Get account balances
						user = (User) session.getAttribute("SESS_USER");
						relationshipNo = user.getRelationshipNo();
						wallet= (Wallet)CustomerWalletDao.class.getConstructor().newInstance().getCustomerWalletDetails(relationshipNo,userType);
						String publicKey = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
						KeyPair userAccount = null;
						ArrayList<AssetAccount> accountBalances = null;
						NeoBankEnvironment.setComment(3, className, " Public key is  "+publicKey);
						if(publicKey != null) {
							userAccount = KeyPair.fromAccountId(publicKey);
							accountBalances = StellarSDKUtility.getAccountBalance(userAccount);
						}
						request.setAttribute("wallet",wallet);
						request.setAttribute("externalwallets",accountBalances);
						request.setAttribute("publickey",publicKey);
						
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerDashboadPage()).forward(request, response);
					}else if (userType.equalsIgnoreCase("M")) {
						ctx.getRequestDispatcher(NeoBankEnvironment.getMerchantDashboardPage()).forward(request, response);
					}
					
				} finally {
					if (userId!=null)userId = null;	if (langPref!=null)langPref = null;	if (tokenTime!=null)tokenTime = null; if (userType!=null)userType = null; 
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}	
		break;
			
		case "loadregistrationpage":
			try {
				String userType = null;
				if(request.getParameter("hdnregusertype")!=null)
					userType = StringUtils.trim(request.getParameter("hdnregusertype"));
				NeoBankEnvironment.setComment(3, className, "User type is "+userType );
				try {
					if(userType.equalsIgnoreCase("C")) {
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerRegistrationPage()).forward(request, response);
					}else {
						ctx.getRequestDispatcher(NeoBankEnvironment.getMerchantRegistrationPage()).forward(request, response);
					}
				} finally {
					if (userType!=null)userType=null; 
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
		break;
		
			case "opslgnfetch":
				try {
					//NeoBankEnvironment.setComment(3, className, "In "+rulesaction);
					HttpSession session	= request.getSession(false);OpsDashboard totalUsers=null;
					OpsDashboard fiatWalletBalance=null;OpsDashboard totaldisputes=null;
					String userType = null; User user = null;
					ArrayList<BitcoinDetails> arrBitcoinDetails=null;
					String bitcoinAddress=null;
					String createdOn=null; 
					String status=null;
					BitcoinDetails m_BitcoinDetails=null;
					if(session ==null) session	= request.getSession(true);
					String userId = null; String langPref = null;	String tokenTime = "";  
					if(request.getParameter("hdnuserid")!=null)	userId = StringUtils.trim(request.getParameter("hdnuserid"));
					if(request.getParameter("hdnlangpref")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlangpref"));
					if(request.getParameter("lgtoken")!=null)	tokenTime = StringUtils.trim(request.getParameter("lgtoken"));
					userType = (String) RemittanceDao.class.getConstructor().newInstance().getAccessType(userId);
					//NeoBankEnvironment.setComment(3, className, "userType is "+userType);
					if(userType.equalsIgnoreCase("P")) {
						user = (User)RemittanceDao.class.getConstructor().newInstance().getPartnerUserDetails(userId);
						if(user!=null) {
							if(user.getUserStatus().equals("A") == false) {
								throw new Exception ("partner not active");
							}else {
								if(userType.equalsIgnoreCase("C")) {
								    // check the user having pricing plan
								    	user.setPricingPlanid((String)OpsManagePricingPlanDao.class.getConstructor().newInstance().getCustomerPricingPlanId(user.getRelationshipNo()));// planId can have "" value if there is no plan allocated to the customer
									}
								session.setAttribute("SESS_USER", user);
								request.setAttribute("langpref", langPref);// to check! whether it will return null if we put it as null in the finally block
								request.setAttribute("lastaction", "partdash"); 
							}
						}else {
							NeoBankEnvironment.setComment(1, className, "Error fetching user details for  "+userId );
							throw new Exception ("Error in Login, Try again");	
						}
						response.setContentType("text/html");	
						request.setAttribute("lastaction", "dash"); 
						//NeoBankEnvironment.setComment(3, className, "login success " );
						if(user.getPasswordType().equalsIgnoreCase("P")) {
							String publicKey = (String)RemittanceDao.class.getConstructor().newInstance().getPublicKey(userId);
							KeyPair userAccount = null;
							ArrayList<AssetAccount> accountBalances = null;
							NeoBankEnvironment.setComment(3, className, " Public key is  "+publicKey);
							//TODO Call stellar registration page if publicKey is null
							if(publicKey != null) {
								userAccount = KeyPair.fromAccountId(publicKey);
								accountBalances = StellarSDKUtility.getAccountBalance(userAccount);
								NeoBankEnvironment.setComment(3, className, "accountBalances is "+accountBalances);
							}
							request.setAttribute("transactions", (List<Transaction>)RemittanceDao.class.getConstructor().newInstance().getLastTenPendingTxn(user.getUserId()));
							request.setAttribute("completedtransactions", (List<Transaction>)RemittanceDao.class.getConstructor().newInstance().getPatnersCompleteTenTransactions(user.getUserId()));
							request.setAttribute("externalwallets",accountBalances);
							request.setAttribute("publickey",publicKey);
							
							ctx.getRequestDispatcher(NeoBankEnvironment.getPartnersDashboardPage()).forward(request, response);
						}else {
							ctx.getRequestDispatcher(NeoBankEnvironment.getPartnerSetPasswordPage()).forward(request, response);
						}
						
					}else {

	
						if(Utilities.validateTimeToken(tokenTime) == true) { 
							session.invalidate();
							throw new Exception ("Token has expired");
						}
					
						 user = (User)UserLoginDao.class.getConstructor().newInstance().getOpsUserDetails(userId );
						
						 if(user!=null) {
							
								if(user.getUserStatus().equals("A") == false) {
									throw new Exception ("user not active");
								}else {
									session.setAttribute("SESS_USER", user);
									request.setAttribute("langpref", langPref);// to check! whether it will return null if we put it as null in the finally block
									request.setAttribute("lastaction", "dash"); 
								}
							}else {
								NeoBankEnvironment.setComment(1, className, "Error fetching user details for  "+userId );
								throw new Exception ("Error in Login, Try again");	
	
							}
						 
						 boolean proceed = true;
					       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					        Date expiryDate = sdf.parse(user.getExpiryDate());
					        Date todayDate = new Date();
					        if((user.getExpiryDate().trim().equals("0000-00-00")) == false) {
					        	if(todayDate.after(expiryDate)) {
					        		NeoBankEnvironment.setComment(1, className, "User Expired "+user.getUserId());
									if(session!=null) 	session.invalidate();
									proceed = false;
									session.setAttribute("errormsg", "Account is Expired");
					        		ctx.getRequestDispatcher(NeoBankEnvironment.getOpsErrorPage()).forward(request, response);
					        	}		        		
					        }
						 
						 if((user.getUserType().equals("O") || user.getUserType().equals("S") ) && proceed == true ) {

								totalUsers =	(OpsDashboard) OpsManageDashboardDao.class.getConstructor().newInstance().getTotalUsers();
								fiatWalletBalance=(OpsDashboard) OpsManageDashboardDao.class.getConstructor().newInstance().getFiatBalanceDetails();
								totaldisputes=(OpsDashboard) OpsManageDashboardDao.class.getConstructor().newInstance().getTotalDisputes();
								//NeoBankEnvironment.setComment(1, className, "totalUsers are  "+totalUsers.getTotalUsers() );
								
								ArrayList<Customer> arrPendingCustomers = (ArrayList<Customer>)OpsManageCustomerDao.class.getConstructor().newInstance().getPendingCustomers();
								ArrayList<DisputeTracker>arrDisputes = (ArrayList<DisputeTracker>) OpsManageCustomerDao.class.getConstructor().newInstance().getActiveDisputes();
								
								request.setAttribute("pendingcustomers",arrPendingCustomers );
								request.setAttribute("tenlatestactivedisputes", arrDisputes);
								request.setAttribute("fiatwalletbal",fiatWalletBalance );
								request.setAttribute("totaldisputes",totaldisputes );
								request.setAttribute("totalusers",totalUsers );
								request.setAttribute("lastaction", "dash");
								request.setAttribute("lastrule", "Ops Dashboard");
								
								NeoBankEnvironment.setComment(3, className, "tokenTime: "+tokenTime);
							 response.setContentType("text/html");	
							 request.setAttribute("lastaction", "dash"); 
							 NeoBankEnvironment.setComment(3, className, "login success " );
							 ctx.getRequestDispatcher(NeoBankEnvironment.getOperationsDashboardPage()).forward(request, response);
							
						 }
						 if((user.getUserType().equals("T")  && proceed == true )) {
							 /*
							 response.setContentType("text/html");	
							 request.setAttribute("lastaction", "tdadash"); 
							 NeoBankEnvironment.setComment(3, className, "login success " );
							 ctx.getRequestDispatcher(NeoBankEnvironment.getTDADashboardPage()).forward(request, response);
							 */
							 arrBitcoinDetails=(ArrayList<BitcoinDetails>)TDAManagementDao.class.getConstructor().newInstance().getBTCAddressDetails();
								if (arrBitcoinDetails!=null) {
									int count =arrBitcoinDetails.size();
									for (int i=0; i<count;i++) {
										createdOn=((BitcoinDetails)arrBitcoinDetails.get(i)).getCreatedOn();
										status=((BitcoinDetails)arrBitcoinDetails.get(i)).getStatus();	
										bitcoinAddress=((BitcoinDetails)arrBitcoinDetails.get(i)).getAddress();
									}
									// Call BlockCypher API to get Address Balance
									m_BitcoinDetails= BitcoinUtility.getBTCAddressBalance(bitcoinAddress);
									m_BitcoinDetails.setStatus(status);
									m_BitcoinDetails.setCreatedOn(createdOn);
								}
								request.setAttribute("lastaction", "tdadash");
								//request.setAttribute("lastrule", "Account Information");
								request.setAttribute("btcaccountdetails",m_BitcoinDetails);
								response.setContentType("text/html");
								ctx.getRequestDispatcher(NeoBankEnvironment.getTDABTCAccountInformationPage()).forward(request,response);
							
						 }
					}
					try {
						
					} finally {
						if (userId!=null) userId = null;	if (langPref!=null)langPref = null;	 if (tokenTime!=null)tokenTime = null; 
						if (userType!=null)userType=null; if (totalUsers!=null)totalUsers=null; if (fiatWalletBalance!=null)fiatWalletBalance=null; 
						if (totaldisputes!=null)totaldisputes=null;  if (user!=null)user=null; 
						 if (arrBitcoinDetails!=null) arrBitcoinDetails=null; if (bitcoinAddress!=null)bitcoinAddress=null;
						 if (createdOn!=null)createdOn=null; if (status!=null)status=null; 
						 if (m_BitcoinDetails!=null) m_BitcoinDetails=null;
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
				}			
			break;
			case "opslgtdefault": case "Sign Out":
				try {
					HttpSession session	= request.getSession(false);	
					if(session!=null) {
						if(session.getAttribute("SESS_USER") !=null)
							session.invalidate();
					}
					response.setContentType("text/html");
					ctx.getRequestDispatcher(NeoBankEnvironment.getOPSLoginPage()).forward(request, response);
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
				}
			break;
			
			
		}
	}
	
	
	@Override
	public void performJSONOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext context) throws Exception {
		
		
	}

	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			 ServletContext ctx) throws Exception {
		switch (rulesaction) {
		case "regnewuserms":
			try {
				PrintWriter jsonOutput_1 = response.getWriter(); Gson gson = new Gson(); 	JsonObject obj = new JsonObject(); //Json Object
				//StringUtils.replace(rulesaction, rulesaction, rulesaction)
				String regUserId = null; String regUserPassword = null; String regUserFirstName = null; String regUserLastName = null;
				String regUserEmail = null; String regUserAddress1 = null; String regUserAddress2 = null; String regUserContact = null;
				String regUserCity = null; String regUserType = null; boolean success = false;		

				if(request.getParameter("reguserid")!=null)		regUserId = request.getParameter("reguserid").trim();
				if(request.getParameter("reguserpwd")!=null)		regUserPassword = request.getParameter("reguserpwd").trim();
				if(request.getParameter("regfname")!=null)		regUserFirstName = request.getParameter("regfname").trim();
				if(request.getParameter("reglname")!=null)		regUserLastName = request.getParameter("reglname").trim();
				if(request.getParameter("regemail")!=null)		regUserEmail = request.getParameter("regemail").trim();
				if(request.getParameter("regaddress1")!=null)		regUserAddress1 = request.getParameter("regaddress1").trim();
				if(request.getParameter("regaddress2")!=null)		regUserAddress2 = request.getParameter("regaddress2").trim();
				if(request.getParameter("regcontact")!=null)		regUserContact = request.getParameter("regcontact").trim();
				if(request.getParameter("regcity")!=null)		regUserCity = request.getParameter("regcity").trim();
				if(request.getParameter("hdnusertype")!=null)		regUserType = request.getParameter("hdnusertype").trim();

				try {
					//call Dao file to register the user. NOTE: Not throwing exception in the DAO files.manually catch issue here
					success  =  (boolean)UserLoginDao.class.getConstructor().newInstance().registerNewUser(regUserId, regUserPassword,  regUserFirstName, regUserLastName,
							regUserEmail,  regUserAddress1, regUserAddress2, regUserContact, regUserCity, regUserType);
					
					if(success) // true=success, false = failure
						obj.add("error", gson.toJsonTree("false")); // no error
					else
						obj.add("error", gson.toJsonTree("true")); // error is present
					jsonOutput_1.print(gson.toJson(obj));
					NeoBankEnvironment.setComment(3, className, "gson is "+gson.toJson(obj));
					} catch(Exception e) {
						obj.add("error", gson.toJsonTree("true"));	jsonOutput_1.print(gson.toJson(obj));
						throw new Exception(e.getMessage());
					}finally {
					//close all objects here
					if(jsonOutput_1!=null) jsonOutput_1.close();
					regUserId = null; regUserPassword = null; regUserFirstName = null; regUserLastName = null;
					regUserEmail = null; regUserAddress1 = null;  regUserAddress2 = null;  regUserContact = null;
					regUserCity = null;  regUserType = null; 	
				}
			}catch(Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: regnewuser is "+e.getMessage());
			}
		break;
		
		case "lgnvalidate":
			try {
			
				NeoBankEnvironment.setComment(3,className,"Inside lgnvalidate");
				String userId=null; String userPwd = null; String userType = null;
				User user = null; String langPref = "en";  String userCaptcha = null; String errorMsg = "true";
				PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
				String captchaCode = null;

				if(request.getParameter("uname")!=null)				userId = request.getParameter("uname").trim();
				if(request.getParameter("upwd")!=null)				userPwd = request.getParameter("upwd").trim();
				if(request.getParameter("ucaptcha")!=null)			userCaptcha = request.getParameter("ucaptcha").trim();
				if(request.getParameter("hdnusertype")!=null)		userType = request.getParameter("hdnusertype").trim();
				if(request.getParameter("hdnlang")!=null)			langPref = request.getParameter("hdnlang").trim();
				if(request.getParameter("capmain")!=null)			captchaCode = request.getParameter("capmain").trim();   		       		    
				request.setAttribute("langpref", langPref);
				
					if ((userCaptcha!=null) && captchaCode!=null) {
						if(userCaptcha.equalsIgnoreCase(captchaCode) == false) {
							obj.addProperty("error", "true");

							if(langPref.equalsIgnoreCase("en")) {
								obj.addProperty("message", "Captcha does not match");
							}else {
								obj.addProperty("message", "El c�digo de imagen no concuerda");
							}
							out_json.print( gson.toJson(obj));
							return;
						}
					}else {
						obj.addProperty("error", "true");

						if(langPref.equalsIgnoreCase("en")) {
							obj.addProperty("message", "Captcha absent");
						}else {
							obj.addProperty("message", "Captcha ausente");
						}
						out_json.print( gson.toJson(obj));

						return;
					}
					    user = (User)UserLoginDao.class.getConstructor().newInstance().validateUserLogin(userId, userPwd, userType );					    
					    if(user!=null) {
							//check if password is temporary
					    	NeoBankEnvironment.setComment(3, className, "user exists");
					    	// Check status
					    	  if(user.getUserStatus().equals( "A") == false) {
							    	NeoBankEnvironment.setComment(3,className,"User is inactive");
							    	Utilities.sendJsonResponse(response, "error", "Incorrect UserId/Password");// Same error message should be displayed to user
									return;
							    	}
					    	// check password
					    	if(user.getPassword().equals(DigestUtils.md5Hex(Utilities.encryptString(userPwd))) == false) {
					    		NeoBankEnvironment.setComment(3,className,"Wrong password");
						    	Utilities.sendJsonResponse(response, "error", "Incorrect UserId/Password");// Same error message should be displayed to user
								return;
					    		}else {
					    			//password is correct
					    			if(user.getPasswordType().equals("T")) {
							    		NeoBankEnvironment.setComment(3, className, "Password type is T");
							    		 obj.addProperty("error", "true");
							    		 obj.addProperty("passwordtype", user.getPasswordType());
										 out_json.print( gson.toJson(obj));
										return;
							    	}
					    		}
						  
						    // All good at this stage
						   NeoBankEnvironment.setComment(3,className,"Getting here");
						    obj.addProperty("error", "false");
							obj.addProperty("token", Utilities.issueTimeToken());
					    }else {
					    	obj.addProperty("error", "true");

					    	if(langPref.equalsIgnoreCase("en")) {
					    		obj.addProperty("message", "Incorrect UserId/Password");// Same error message should be displayed to user
					    	}else {
					    		obj.addProperty("message", "ID de usuario/contraseña incorrectos");// Same error message should be displayed to user
					    	}

					    }
					    try {
							 out_json.print( gson.toJson(obj));
							 NeoBankEnvironment.setComment(3, className, "lgnvalidate string is,  gson "+ gson.toJson(obj));
						}finally {
							if (userId!=null)userId = null;  if (gson!=null)userPwd = null; if (userType!=null)userType = null;
							if (langPref!=null)langPref = null;  if (userCaptcha!=null)userCaptcha = null; if(out_json!=null) out_json.close();
							if (obj!=null)obj = null; if (gson!=null)gson = null;if (errorMsg!=null) errorMsg=null;
						}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in logging in");
			}
		break;
		
		case "lgnfetchmbl":
			try {
				Wallet wallet = null; String tokenValue = null;String langPref=null;
					  String userType=null; User user = null;String relationshipNo=null;
				PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
				
				if(request.getParameter("hdnlgusertype")!=null)	userType = StringUtils.trim(request.getParameter("hdnlgusertype"));
				if(request.getParameter("relno")!=null)	relationshipNo = StringUtils.trim(request.getParameter("relno"));
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

				//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v�lido, vuelva a iniciar sesi�n");

					}else {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					}
					return;
				}
				NeoBankEnvironment.setComment(3, className, "User type is "+userType);
				wallet= (Wallet)CustomerWalletDao.class.getConstructor().newInstance().getCustomerWalletDetails(relationshipNo,userType);
				if(userType.equalsIgnoreCase("C")) {
					user = (User)UserLoginDao.class.getConstructor().newInstance().getUserDetailsMobile(relationshipNo ,userType);
				}
				if (user!=null) {
						obj.add("data", gson.toJsonTree(user));
						obj.add("walletdetails", gson.toJsonTree(wallet));
						obj.add("error", gson.toJsonTree("false"));
				}else {
					obj.add("error", gson.toJsonTree("true")); 
					if(langPref.equalsIgnoreCase("ES")) {
						obj.add("msg", gson.toJsonTree("user details not found")); 
					}else {
						obj.add("msg", gson.toJsonTree("detalles del usuario no encontrados")); 
					}
					
				}
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					out_json.print(gson.toJson(obj));
					
				} finally {
					if (userType!=null) userType = null; if (relationshipNo!=null) relationshipNo = null; 
					if (out_json != null)out_json.close();  if (gson != null)gson = null;
					if (obj != null)obj = null; if(user!=null) user=null; if (tokenValue != null)tokenValue = null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in fetching customer details");
			}	
		break;	
		
		case "lgnvalidatembl":
			try {

				String userId=null; String userPwd = null; String userType = null;String langPref=null;
				User user = null; String errorMsg = "true"; String planId=null;
				PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
		
				if(request.getParameter("uname")!=null)				userId = request.getParameter("uname").trim();
				if(request.getParameter("upwd")!=null)				userPwd = request.getParameter("upwd").trim(); 
				if(request.getParameter("usertype")!=null)			userType = request.getParameter("usertype").trim(); 
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim(); 
				
				NeoBankEnvironment.setComment(3, className, "langPref"+langPref);
				
				user = (User)UserLoginDao.class.getConstructor().newInstance().validateUserLogin(userId, userPwd, userType );	
				
				if(user!=null) {

					NeoBankEnvironment.setComment(3, className, "User exits");
					// check password
					if(user.getPasswordType().equals("T")) {
						NeoBankEnvironment.setComment(3,className,"Password type is "+user.getPasswordType());
						obj.add("passwordtype", gson.toJsonTree(user.getPasswordType()));
						obj.add("relno", gson.toJsonTree(user.getRelationshipNo()));
					}else if(user.getPassword().equals( DigestUtils.md5Hex((Utilities.encryptString(userPwd)))) == false) {
						if(langPref.equalsIgnoreCase("ES")) {
							errorMsg = "ID de usuario/contraseña incorrectos";// Same error message should be displayed to user
						}else {
							errorMsg = "Incorrect UserId/Password";// Same error message should be displayed to user
						}
						obj.add("langpref", gson.toJsonTree(langPref));
						obj.add("error", gson.toJsonTree(errorMsg));
					} else if(user.getUserStatus().equals( "A") == false) {
						if(langPref.equalsIgnoreCase("ES")) {
							errorMsg = "ID de usuario/contraseña incorrectos";// Same error message should be displayed to user
						}else {
							errorMsg = "Incorrect UserId/Password";// Same error message should be displayed to user
						}
						obj.add("langpref", gson.toJsonTree(langPref));
						obj.add("error", gson.toJsonTree(errorMsg));
					}else {
						// All good at this stage
						// Get the Plan Id
						planId=(String)FundStellarAccountDao.class.getConstructor().newInstance().getPlanId(user.getRelationshipNo());
						if (planId.equals("0")){
							planId="noplan";
						}
						errorMsg = "false";
						obj.add("error", gson.toJsonTree(errorMsg)); 
						obj.add("relno", gson.toJsonTree(user.getRelationshipNo()));
						obj.add("username", gson.toJsonTree(user.getName()));
						obj.add("planid", gson.toJsonTree(planId));
						obj.add("langpref", gson.toJsonTree(langPref));
						obj.add("token", gson.toJsonTree(Utilities.generateMobileToken(user.getRelationshipNo())));
					}
				}else {
					if(langPref.equalsIgnoreCase("ES")) {
						errorMsg = "ID de usuario/contraseña incorrectos";// Same error message should be displayed to user
					}else {
						errorMsg = "Incorrect UserId/Password";// Same error message should be displayed to user
					}
					obj.add("langpref", gson.toJsonTree(langPref));
					obj.add("error", gson.toJsonTree(errorMsg));
				}
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+ "String is " + gson.toJson(obj));
					out_json.print(gson.toJson(obj));
				} finally {
					if (userId!=null)userId = null;if (userPwd!=null) userPwd = null; if (userType!=null) userType = null; if(out_json!=null) out_json.close();
					if (obj!=null)obj = null; if (gson!=null)gson = null; if (errorMsg!=null) errorMsg=null;
					if (langPref!=null) langPref=null;
					
				}

		}catch (Exception e) {
			NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
			Utilities.sendJsonResponse(response, "error", "Error in logging in");
		}
		break;
		
		case "regnewcust":
			 try {
				 	String fullName=null;  String contact = null; String emailAddress=null; String password=null;
				 	String licenseNo=null; String dob=null; String address=null; String gender=null; String uploadedFile=null; 
				 	List<String> fileNames = new ArrayList<String>(); String passportNo=null; 
					String message = null; JsonObject dmJsonResponse = new JsonObject(); SimpleDateFormat formatter1 = new SimpleDateFormat ("yyMMdd");
					String relationshipNo=null; boolean result = false;	ArrayList<String> fileArrayEncoded=null; File fileBase64Encoded=null;
					Gson gson = new Gson();  String base64fileString=null;
					CloseableHttpClient httpClient=null;CloseableHttpResponse urlresponse=null; String sbResponse=null; 
					List<Part> parts=null; String subject=null; String content=null;
					String fileName=null;
					String language=null;
				
					if(request.getParameter("hdncustname")!=null)				fullName = request.getParameter("hdncustname").trim();
					if(request.getParameter("hdncustcontact")!=null)			contact = request.getParameter("hdncustcontact").trim();
					if(request.getParameter("hdncustemailadd")!=null)			emailAddress = request.getParameter("hdncustemailadd").trim();
					if(request.getParameter("hdncustpass")!=null)				password = request.getParameter("hdncustpass").trim();
					if(request.getParameter("hdncustidno")!=null)				licenseNo = request.getParameter("hdncustidno").trim();
					if(request.getParameter("hdncustaddress")!=null)			address = request.getParameter("hdncustaddress").trim();
					if(request.getParameter("hdncustdob")!=null)				dob = request.getParameter("hdncustdob").trim();
					if(request.getParameter("hdngender")!=null)				gender = request.getParameter("hdngender").trim();
					if(request.getParameter("hdncustpassno")!=null)				passportNo = request.getParameter("hdncustpassno").trim();
					if(request.getParameter("hdnlang")!=null)				language = request.getParameter("hdnlang").trim();
					
					NeoBankEnvironment.setComment(3,className,"fullName "+fullName+"||"+" gender "+gender+" contact "+contact+"||"+"address"+new String(address.getBytes(), "UTF-8"));
					
					// Converting Arabic names to UTF 8
					address=new String(address.getBytes(), "UTF-8");
					
					NeoBankEnvironment.setComment(3,className," converted address "+address);
					// Generating Unique ID for newly registered customer
					relationshipNo = formatter1.format(new java.util.Date())+ RandomStringUtils.random(10, false, true);
						
							
				     parts = (List<Part>) request.getParts();
					for (Part part : parts) {
						if (part.getName().equalsIgnoreCase("custfile")) { // Can be customer DL or Passport
							fileName= Utilities.getFileName(part);
							String extension = "";
							int index = fileName.lastIndexOf('.');
							if (index > 0) {
							    extension = fileName.substring(index + 1);
							}
							fileName = relationshipNo+"_identity_doc."+extension;
							NeoBankEnvironment.setComment(3,className," fileName:  "+fileName);
							fileNames.add(fileName);
							String basePathTemp =   NeoBankEnvironment.getFileUploadPath() + File.separator +"temp" + File.separator;
							InputStream inputStream = null;
							OutputStream outputStream = null;
							try {
								File outputFilePath = new File( StringUtils.replace(basePathTemp, "\\", "/") + fileName);
								inputStream = part.getInputStream();
								outputStream = new FileOutputStream(outputFilePath);
								int read = 0;
								final byte[] bytes = new byte[1024];
								while ((read = inputStream.read(bytes)) != -1) {
									outputStream.write(bytes, 0, read);
								}
							} catch (Exception ex) {
								fileName = null;
								throw new Exception ("Exception in file upload "+ex.getMessage());
							} finally {
								if (outputStream != null) outputStream.close();	
								if (inputStream != null) inputStream.close();
							}
						}
						if (part.getName().equalsIgnoreCase("custfile_1")) { //Can Utility bill
							fileName= Utilities.getFileName(part);
							String extension = "";
							int index = fileName.lastIndexOf('.');
							if (index > 0) {
							    extension = fileName.substring(index + 1);
							}
							fileName = relationshipNo+"_utility_bill."+extension;
							NeoBankEnvironment.setComment(3,className," fileName:  "+fileName);
							fileNames.add(fileName);
							String basePathTemp =   NeoBankEnvironment.getFileUploadPath() + File.separator +"temp" + File.separator;
							InputStream inputStream = null;
							OutputStream outputStream = null;
							try {
								File outputFilePath = new File( StringUtils.replace(basePathTemp, "\\", "/") + fileName);
								inputStream = part.getInputStream();
								outputStream = new FileOutputStream(outputFilePath);
								int read = 0;
								final byte[] bytes = new byte[1024];
								while ((read = inputStream.read(bytes)) != -1) {
									outputStream.write(bytes, 0, read);
								}
							} catch (Exception ex) {
								fileName = null;
								throw new Exception ("Exception in file upload "+ex.getMessage());
							} finally {
								if (outputStream != null) outputStream.close();	
								if (inputStream != null) inputStream.close();
							}
						}
					}
					JsonArray arrayFiles = new JsonArray();
					JsonObject objFileDetails = null;
					JsonObject objRequest = new JsonObject();
					
					if(fileNames.size()>0) {
						for( int i =0; i<fileNames.size(); i++) {
							objFileDetails = new JsonObject();
							uploadedFile = NeoBankEnvironment.getFileUploadPath() + File.separator +"temp" + File.separator +fileNames.get(i); // only one file present/allowed to upload here, else run a loop
							uploadedFile = StringUtils.replace(uploadedFile, "\\", "/");
							NeoBankEnvironment.setComment(3,className,"uploadedFile "+i+" "+uploadedFile);
							// *** Step 2
//							fileArrayEncoded = new ArrayList<String>();
							fileBase64Encoded = new File(uploadedFile);
							if(fileBase64Encoded.exists() == false) throw new Exception ("File does not exists");
			                byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(fileBase64Encoded));
			                // long long long String
			                // *** Step 3
			                 base64fileString = new String(encoded, StandardCharsets.US_ASCII); // in case of multiple file it will be in array
			                //NeoBankEnvironment.setComment(3,className,"encoding base64fileString--> base64fileString size:  "+base64fileString.length());
			                 objFileDetails.addProperty("filename", fileNames.get(i));
			                 objFileDetails.addProperty("image_base64", base64fileString);
			                 arrayFiles.add(objFileDetails);
			                 if (fileBase64Encoded != null) fileBase64Encoded.delete();
						}
						objRequest.add("data", gson.toJsonTree(arrayFiles));
					}
//					NeoBankEnvironment.setComment(3,className,"objRequest "+objRequest.toString());      
					
	                HttpPost post = new HttpPost(NeoBankEnvironment.getFDocServerURL());
			        // add request parameter, form parameters and only the file details.
			        List<NameValuePair> urlParameters = new ArrayList<>();
			        urlParameters.add(new BasicNameValuePair("qs", "fud"));
			        urlParameters.add(new BasicNameValuePair("rules", "uploadfile"));
			        urlParameters.add(new BasicNameValuePair("file_obj", String.valueOf(objRequest) ));
			        urlParameters.add(new BasicNameValuePair("userid", relationshipNo));
			        NeoBankEnvironment.setComment(3,className,"Before post ");      
			        post.setEntity(new UrlEncodedFormEntity(urlParameters));
			        String filePathStringArray = null;
		            httpClient = HttpClients.createDefault();
		            urlresponse = httpClient.execute(post); 
		            sbResponse = EntityUtils.toString(urlresponse.getEntity());
			         	NeoBankEnvironment.setComment(3, className,"***** after POST response is "+ sbResponse);
			        if(sbResponse!=null) {
			        	dmJsonResponse = new Gson().fromJson(sbResponse, JsonObject.class);
			        	if(dmJsonResponse.get("error").getAsBoolean() == false) {
			        		filePathStringArray  = dmJsonResponse.get("filepath").getAsString();
//			        		NeoBankEnvironment.setComment(3, className,"filePathStringArray is "+ filePathStringArray);
			        		filePathStringArray = filePathStringArray.replace("[", "").replace("]", "");
//			        		filePathStringArray = filePathStringArray.replaceAll("[", "");
//			        		NeoBankEnvironment.setComment(3, className,"File path is "+ filePathStringArray);
			        	}else {
			        		message = dmJsonResponse.get("message").getAsString();
			        		NeoBankEnvironment.setComment(3, className,"File path is "+ message);
						}
			        }else {
			        	throw new Exception ("Problem in the external file update process");
			        }
			        //NeoBankEnvironment.setComment(3,className,"file name is "+fileName);
			        //NeoBankEnvironment.setComment(3,className,"file name is "+fileNames.toString());
			        result = (boolean)RegistrationDao.class.getConstructor().newInstance().registerNewCustomer(relationshipNo,fullName,contact,emailAddress, password,
			        		licenseNo, passportNo,address,dob,gender,filePathStringArray);
			        
					if(result) {
							//Module code for customer registration is NCR- New Customer Registration
							SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "NCR","New customer registration "+relationshipNo );
							if(language.equals("en")) {
								 subject="THANK YOU FOR REGISTRATION!";
								 content="Thank you for registering to Payment Porte Digital Wallet. Your credentials are being verified"+ System.lineSeparator()
										+ " and you will get a notification once approved.";
							}else {
								subject="�GRACIAS POR REGISTRARTE!";
								 content="Gracias por registrarse en Payment Porte Digital Wallet. Sus credenciales est�n siendo verificadas"+ System.lineSeparator()
										+ " y recibir� una notificaci�n una vez aprobado.";
							}
							// Send to user
							String sendto = emailAddress;
							String sendSubject = subject;
							String sendContent = content;
							String customerName = fullName;
							
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
							
							// Send to Operation
							 subject="NEW USER REGISTRATION!";
							 content="A new user by the name "+fullName+" with the relationship no "+Utilities.maskedRelationshipNumber(relationshipNo)+" has onboarded to the system."+System.lineSeparator()
										+ " Please verify the users details."+System.lineSeparator()
										+" Thank you.";
							 
							    String operationsEmail =NeoBankEnvironment.getOPsNewRegistrationEmailAddressNotifier() ;
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
							
						}else {
							throw new Exception("There was a problem in registration,please try again");
					}
					response.setContentType("text/html");
					try {
						request.setAttribute("lang",language);																						
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerSuccessfulRegistrationPage()).forward(request, response);
					} finally {
						 if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						if (fullName != null)fullName = null; if (emailAddress != null)emailAddress = null; if (password != null)password = null;
						if (licenseNo != null)licenseNo = null; if (dob != null)dob = null; if (address != null)address = null;
						if (gender != null)gender = null; if (uploadedFile != null)uploadedFile = null; if (fileNames != null)fileNames = null;
						if (message != null)message = null; if (dmJsonResponse != null)dmJsonResponse = null; if (formatter1 != null)formatter1 = null;
						if (fileArrayEncoded != null)fileArrayEncoded = null; if (fileBase64Encoded != null) fileBase64Encoded.delete(); fileBase64Encoded = null;  
						if (base64fileString != null)base64fileString = null; if (httpClient != null) httpClient.close(); if (urlresponse != null)urlresponse.close();
						if (sbResponse!=null) sbResponse=null; if (contact!=null) contact=null; if(parts!=null) parts=null;
						if (urlParameters!=null);urlParameters=null; if (post!=null) { post.clear();post=null;}
						if (subject!=null);subject=null; if (content!=null) content=null; if (language!=null) language=null;
						if (arrayFiles!=null)arrayFiles=null; if (objFileDetails!=null) objFileDetails=null;
						if (objRequest!=null)objRequest=null; if (filePathStringArray!=null) filePathStringArray=null;
						
					}
			 }catch (Exception e) {
				 NeoBankEnvironment.setComment(1, className, " Overall Exception for case regnewcust is "+e.getMessage());
				Utilities.callException(request, response, ctx,  e.getMessage());
			 }
		 break;	 
		 
		 	case "regnewcustmbl":
				 try {
					 	String fullName=null;  String contact = null; String emailAddress=null; String password=null;
					 	String licenseNo=null; String dob=null; String address=null; String gender=null; String uploadedFile=null; 
					 	String filePath = null; String passportNo=null; String fileName=null;
						String message = null; JsonObject dmJsonResponse = new JsonObject(); SimpleDateFormat formatter1 = new SimpleDateFormat ("yyMMdd");
						String relationshipNo=null; boolean result = false;	 String subject=null; String content=null;
						PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject(); 
						CloseableHttpClient httpClient=null;CloseableHttpResponse urlresponse=null; String sbResponse=null; 
						List<Part> parts=null; List<String> fileNames = new ArrayList<String>();
						File fileBase64Encoded=null;String langPref=null;
						String base64fileString=null;
						if(request.getParameter("custname")!=null)				fullName = request.getParameter("custname").trim();
						if(request.getParameter("customercontact")!=null)			contact = request.getParameter("customercontact").trim();
						if(request.getParameter("custemailadd")!=null)			emailAddress = request.getParameter("custemailadd").trim();
						if(request.getParameter("custpass")!=null)				password = request.getParameter("custpass").trim();
						if(request.getParameter("custidno")!=null)				licenseNo = request.getParameter("custidno").trim();
						if(request.getParameter("custaddress")!=null)			address = request.getParameter("custaddress").trim();
						if(request.getParameter("custdob")!=null)				dob = request.getParameter("custdob").trim();
						if(request.getParameter("gender")!=null)				gender = request.getParameter("gender").trim();
						if(request.getParameter("custpassno")!=null)			passportNo = request.getParameter("custpassno").trim();
						 if(request.getParameter("hdnlangpref")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlangpref"));

						NeoBankEnvironment.setComment(3, className, " Step 1 ");
						NeoBankEnvironment.setComment(3,className,"contact is "+contact);
						// Generating Unique ID for newly registered customer
						relationshipNo = formatter1.format(new java.util.Date())	+ RandomStringUtils.random(10, false, true);
					    parts = (List<Part>) request.getParts();
					    NeoBankEnvironment.setComment(3, className, " Step 2 ");
						for (Part part : parts) {
							//NeoBankEnvironment.setComment(3, className, " In loop ");
							//NeoBankEnvironment.setComment(3, className, " file name is "+part.getName());
							if (part.getName().equalsIgnoreCase("custfile")) { // Can be customer DL or Passport
								fileName= Utilities.getFileName(part);
								String extension = "";
								int index = fileName.lastIndexOf('.');
								if (index > 0) {
								    extension = fileName.substring(index + 1);
								}
								fileName = relationshipNo+ "_identity_doc."+extension;
								
								NeoBankEnvironment.setComment(3,className," fileName:  "+fileName);
								fileNames.add(fileName);
								String basePathTemp =   NeoBankEnvironment.getFileUploadPath() + File.separator +"temp" + File.separator;
								InputStream inputStream = null;
								OutputStream outputStream = null;
								try {
									File outputFilePath = new File( StringUtils.replace(basePathTemp, "\\", "/") + fileName);
									inputStream = part.getInputStream();
									outputStream = new FileOutputStream(outputFilePath);
									int read = 0;
									final byte[] bytes = new byte[1024];
									while ((read = inputStream.read(bytes)) != -1) {
										outputStream.write(bytes, 0, read);
									}
								} catch (Exception ex) {
									fileName = null;
									if(langPref.equalsIgnoreCase("ES")) {
										throw new Exception ("Excepci�n en la carga de archivos "+ex.getMessage());
									}else {
										throw new Exception ("Exception in file upload "+ex.getMessage());
									}
								} finally {
									if (outputStream != null) outputStream.close();	
									if (inputStream != null) inputStream.close();
								}
							}
							if (part.getName().equalsIgnoreCase("utility-bill")) { //Can Utility bill
								fileName= Utilities.getFileName(part);
								String extension = "";
								int index = fileName.lastIndexOf('.');
								if (index > 0) {
								    extension = fileName.substring(index + 1);
								}
								fileName = relationshipNo+"_utility_bill."+extension;
								NeoBankEnvironment.setComment(3,className," fileName:  "+fileName);
								fileNames.add(fileName);
								String basePathTemp =   NeoBankEnvironment.getFileUploadPath() + File.separator +"temp" + File.separator;
								InputStream inputStream = null;
								OutputStream outputStream = null;
								try {
									File outputFilePath = new File( StringUtils.replace(basePathTemp, "\\", "/") + fileName);
									inputStream = part.getInputStream();
									outputStream = new FileOutputStream(outputFilePath);
									int read = 0;
									final byte[] bytes = new byte[1024];
									while ((read = inputStream.read(bytes)) != -1) {
										outputStream.write(bytes, 0, read);
									}
								} catch (Exception ex) {
									fileName = null;
									if(langPref.equalsIgnoreCase("ES")) {
										throw new Exception ("Excepci�n en la carga de archivos "+ex.getMessage());
									}else {
										throw new Exception ("Exception in file upload "+ex.getMessage());
									}
								} finally {
									if (outputStream != null) outputStream.close();	
									if (inputStream != null) inputStream.close();
								}
							}
						}
						JsonArray arrayFiles = new JsonArray();
						JsonObject objFileDetails = null;
						JsonObject objRequest = new JsonObject();
						NeoBankEnvironment.setComment(3,className," Size is :  "+fileNames.size());
						if(fileNames.size()>0) {
							for( int i =0; i<fileNames.size(); i++) {
								objFileDetails = new JsonObject();
								uploadedFile = NeoBankEnvironment.getFileUploadPath() + File.separator +"temp" + File.separator +fileNames.get(i); // only one file present/allowed to upload here, else run a loop
								uploadedFile = StringUtils.replace(uploadedFile, "\\", "/");
								NeoBankEnvironment.setComment(3,className,"uploadedFile "+i+" "+uploadedFile);
								// *** Step 2
//								fileArrayEncoded = new ArrayList<String>();
								fileBase64Encoded = new File(uploadedFile);
								if(fileBase64Encoded.exists() == false) throw new Exception ("File does not exists");
				                byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(fileBase64Encoded));
				                // long long long String
				                // *** Step 3
				                 base64fileString = new String(encoded, StandardCharsets.US_ASCII); // in case of multiple file it will be in array
				                NeoBankEnvironment.setComment(3,className,"encoding base64fileString--> base64fileString size:  "+base64fileString.length());
				                 objFileDetails.addProperty("filename", fileNames.get(i));
				                 objFileDetails.addProperty("image_base64", base64fileString);
				                 arrayFiles.add(objFileDetails);
				                 if (fileBase64Encoded != null) fileBase64Encoded.delete();
							}
							objRequest.add("data", gson.toJsonTree(arrayFiles));
						}
						   
		                HttpPost post = new HttpPost(NeoBankEnvironment.getFDocServerURL());
				        // add request parameter, form parameters and only the file details.
				        List<NameValuePair> urlParameters = new ArrayList<>();
		                urlParameters.add(new BasicNameValuePair("qs", "fud"));
				        urlParameters.add(new BasicNameValuePair("rules", "uploadfile"));
				        urlParameters.add(new BasicNameValuePair("file_obj", String.valueOf(objRequest) ));
				        urlParameters.add(new BasicNameValuePair("userid", relationshipNo));
				        post.setEntity(new UrlEncodedFormEntity(urlParameters));

			            httpClient = HttpClients.createDefault();
			            urlresponse = httpClient.execute(post); 
			            sbResponse = EntityUtils.toString(urlresponse.getEntity());
				         	NeoBankEnvironment.setComment(3, className,"***** after POST response is "+ sbResponse);
				        if(sbResponse!=null) {
				        	dmJsonResponse = new Gson().fromJson(sbResponse, JsonObject.class);
				        	if(dmJsonResponse.get("error").getAsBoolean() == false) {
				        	 filePath  = dmJsonResponse.get("filepath").getAsString();
				        	 filePath  = filePath.replace("[", "").replace("]", "");
				        		NeoBankEnvironment.setComment(3, className,"File path is "+ filePath);
				        	}else {
				        		message = dmJsonResponse.get("message").getAsString();
				        		NeoBankEnvironment.setComment(3, className,"File path is "+ message);
							}
				        }else {
				        	if(langPref.equalsIgnoreCase("ES")) {
					        	throw new Exception ("Problema en el proceso de actualizaci�n de archivos externos");
							}else {
					        	throw new Exception ("Problem in the external file update process");
							}
				        }
				        
				        result = (boolean)RegistrationDao.class.getConstructor().newInstance().registerNewCustomer(relationshipNo,fullName,contact,emailAddress, 
				        		password,licenseNo, passportNo,address,dob,gender,filePath);
				        
						if(result) {
								//Module code for customer registration is NCM- New Customer Registration Mobile
								SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "NCM","New customer registration from mobile"+relationshipNo );
								obj.add("error", gson.toJsonTree("false"));
								
								if(langPref.equalsIgnoreCase("ES")) {
									subject="GRACIAS POR EL REGISTRO";
									 content="Gracias por registrarse en Payment Porte Digital Wallet. Sus credenciales est�n siendo verificadas"+ System.lineSeparator()
												+ " y recibir� una notificaci�n una vez aprobado.";
								}else {
									subject="THANK YOU FOR REGISTRATION";
									 content="Thank you for registering to Payment Porte Digital Wallet. Your credentials are being verified"+ System.lineSeparator()
												+ " and you will get a notification once approved.";
								}
								
								
								// Send to user
								String sendto = emailAddress;
								String sendSubject = subject;
								String sendContent = content;
								String customerName = fullName;
								
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
								
								// Send to Operation
								if(langPref.equalsIgnoreCase("ES")) {
									 subject="�REGISTRO DE NUEVO USUARIO!";
									 content="Un nuevo usuario por el nombre"+fullName+" con la relacion no"+Utilities.maskedRelationshipNumber(relationshipNo)+"se ha incorporado al sistema."+System.lineSeparator()
												+ " Verifique los detalles de los usuarios."+System.lineSeparator()
												+" Gracias.";
								}else {
									 subject="NEW USER REGISTRATION!";
									 content="A new user by the name "+fullName+" with the relationship no "+Utilities.maskedRelationshipNumber(relationshipNo)+" has onboarded to the system."+System.lineSeparator()
												+ " Please verify the users details."+System.lineSeparator()
												+" Thank you.";
								}
								
								 
								    String operationsEmail =NeoBankEnvironment.getOPsNewRegistrationEmailAddressNotifier() ;
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
									
									
								
							}else {
								obj.add("error", gson.toJsonTree("true"));
						}
						try {
							NeoBankEnvironment.setComment(3, className," regnewcust String is " + gson.toJson(obj));
							out_json.print(gson.toJson(obj));
						} finally {
							if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
							if (fullName != null)fullName = null; if (emailAddress != null)emailAddress = null; if (password != null)password = null;
							if (licenseNo != null)licenseNo = null; if (dob != null)dob = null; if (address != null)address = null;
							if (gender != null)gender = null; if (uploadedFile != null)uploadedFile = null; 
							if (message != null)message = null; if (dmJsonResponse != null)dmJsonResponse = null; if (formatter1 != null)formatter1 = null;
							if (httpClient != null) httpClient.close(); if (urlresponse != null)urlresponse.close();
							if (sbResponse!=null) sbResponse=null; if (contact!=null) contact=null;if (fileBase64Encoded != null) fileBase64Encoded.delete();
							if (subject!=null);subject=null; if (content!=null);content=null;
							if (urlParameters!=null);urlParameters=null; if (post!=null) { post.clear();post=null;}
								
						}
				 }catch (Exception e) {
					 NeoBankEnvironment.setComment(1, className, " Overall Exception for case regnewcust is "+e.getMessage());
					 Utilities.sendJsonResponse(response, "error", "Problem in submitting registration details, Please try again letter");

				 }
			 break;	 
		 
		 	case "opslogin":
				try {
					String userId=null; String userPwd = null; String userType = null;
					String langPref = "en";  String userCaptcha = null; String errorMsg = "true";
					PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
					String captchaCode = null; String message = null;

					if(request.getParameter("uname")!=null)				userId = request.getParameter("uname").trim();
					if(request.getParameter("upwd")!=null)				userPwd = request.getParameter("upwd").trim();
					if(request.getParameter("ucaptcha")!=null)			userCaptcha = request.getParameter("ucaptcha").trim();
					if(request.getParameter("hdnusertype")!=null)		userType = request.getParameter("hdnusertype").trim();
					if(request.getParameter("hdnlang")!=null)			langPref = request.getParameter("hdnlang").trim();
					if(request.getParameter("capmain")!=null)			captchaCode = request.getParameter("capmain").trim();   		       		    
					request.setAttribute("langpref", langPref);
					//	NeoBankEnvironment.setComment(3, className,"captchaCode is " + captchaCode+ " and userCaptcha is "+userCaptcha);
						if ((userCaptcha!=null) && captchaCode!=null) {
							if(userCaptcha.equalsIgnoreCase(captchaCode) == false) {
								errorMsg = "Captcha does not match"; 
								throw new Exception ("Captcha does not match");
							}
						}else {
							errorMsg = "Captcha absent"; 
							throw new Exception ("Captcha absent");
						}
						
						 message = (String)UserLoginDao.class.getConstructor().newInstance().validateOpsUser(userId, userPwd, userType );
						 NeoBankEnvironment.setComment(3, className, " message is "+message);
						 if(message.equals("success")) {
							    errorMsg = "false";
							    obj.add("token", gson.toJsonTree(  Utilities.issueTimeToken()  ));
						 }else {
							 errorMsg = "temp";
							 if(message.equals("T")) {
								 NeoBankEnvironment.setComment(3,className,"Equal T");
								 obj.add("passwordtype", gson.toJsonTree("T")); 
								 out_json.print( gson.toJson(obj));
								 NeoBankEnvironment.setComment(3, className, "Exception,  gson "+ gson.toJson(obj));
								 return;
							 }else {
								 errorMsg = message;
								 Utilities.sendJsonResponse(response, "error", "Incorrect UserId/Password");// Same error message should be displayed to user
								return;
							 }							 
						 }
						  //  NeoBankEnvironment.setComment(3, className,"errorMsg is " + errorMsg); 
						    obj.add("error", gson.toJsonTree(errorMsg)); 
						  
						  
						
						    
					try {
						 NeoBankEnvironment.setComment(3, className, " String in "+rulesaction+" is "+ obj.toString());
						  out_json.print( gson.toJson(obj));
					
					}finally {
						if(userId!=null)userId = null; if(userPwd!=null)userPwd = null; if(userType!=null)userType = null;
						if(langPref!=null)langPref = null; if(userCaptcha!=null) userCaptcha = null; if(out_json!=null) out_json.close();
						if(obj!=null)obj = null; if(gson!=null)gson = null;
					}
					
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case opslogin is "+e.getMessage());
					 Utilities.sendJsonResponse(response, "error", "Error in logging in, Please try again letter");

				}
			break;
				case "regmerchant":
				JsonObject obj = new JsonObject();
				try {
					String companyName = null;
					String businessDesc = null;
					String physicalAddress = null;
					String bsnPhoneNumber = null;
					String mccId = null;
					String planValue = null;
					String email = null;
					String nationalId = null;
					String assetDocFile = "NA";
					List<String> fileNames = new ArrayList<String>();
					JsonObject dmJsonResponse = new JsonObject();
					PrintWriter output = null;
					String filePath = null;
					String message = null;
					Boolean success = false;
					String merchantCode =null;
					String billerRef= null;
					String fullName = null;
					String phoneNo = null;
					String haveBranches = null;
					String fileExtension = null;
					List<Part> parts=null;
					if(request.getParameter("companyname")!=null)	
						companyName = request.getParameter("companyname").trim();
					if(request.getParameter("bsndesc")!=null)	
						businessDesc = request.getParameter("bsndesc").trim();
					if(request.getParameter("physicaladd")!=null)	
						physicalAddress = request.getParameter("physicaladd").trim();
					if(request.getParameter("bsnphoneno")!=null)	
						bsnPhoneNumber = request.getParameter("bsnphoneno").trim();
					if(request.getParameter("hdnplan")!=null)	
						planValue = request.getParameter("hdnplan").trim();
					if(request.getParameter("hdnhavebranches")!=null)	
						haveBranches = request.getParameter("hdnhavebranches").trim();
					if(request.getParameter("nationalId")!=null)	
						nationalId = request.getParameter("nationalId").trim();
					
					if(request.getParameter("selectmcc")!=null)	
						mccId = request.getParameter("selectmcc").trim();
					
					if(request.getParameter("merchphoneno")!=null)	
						phoneNo = request.getParameter("merchphoneno").trim();
					if(request.getParameter("fullname")!=null)	
						fullName = request.getParameter("fullname").trim();
					
					if(request.getParameter("email")!=null)	
						email = request.getParameter("email").trim();
					
					NeoBankEnvironment.setComment(3,className,
							" companyName "+companyName+" businessDesc "+ businessDesc
							+" physicalAddress "+physicalAddress+" bsnPhoneNumber "+bsnPhoneNumber
							+" mccId "+mccId+" planValue "+planValue+" email "+email+"nationalId"
							+nationalId+" fullName "+fullName+" phoneNo "+phoneNo+" haveBranches "+haveBranches);
					
				     parts = (List<Part>) request.getParts();
					NeoBankEnvironment.setComment(3,className,
							"inside performMultiPartOperation --> 2 parts is "+parts);
					for (Part part : parts) {
						if (part.getName().equalsIgnoreCase("file1")) {
							NeoBankEnvironment.setComment(3,className,
									"inside performMultiPartOperation --> 3  ");
							String fileName = Utilities.getFileName(part);
							fileExtension = FilenameUtils.getExtension(fileName);
							NeoBankEnvironment.setComment(3,className,
									"inside performMultiPartOperation --> fileName:  "+fileName);
							fileNames.add(fileName);
							//String applicationPath = request.getServletContext().getRealPath("");
							@SuppressWarnings("unused")
							String basePath =  
							NeoBankEnvironment.getFileUploadPath() + File.separator;
							String basePathTemp =  
									NeoBankEnvironment.getFileUploadPath() 
									+ File.separator +"temp" + File.separator;
							InputStream inputStream = null;
							OutputStream outputStream = null;
							try {
								File outputFilePath = new File( StringUtils.replace(basePathTemp, "\\", "/") + fileName);
								inputStream = part.getInputStream();
								outputStream = new FileOutputStream(outputFilePath);
								int read = 0;
								final byte[] bytes = new byte[1024];
								while ((read = inputStream.read(bytes)) != -1) {
									outputStream.write(bytes, 0, read);
								}
							} catch (Exception ex) {
								fileName = null;
								throw new Exception ("Exception in file upload "+ex.getMessage());
							} finally {
								if (outputStream != null) outputStream.close();	
								if (inputStream != null) inputStream.close();
							}
						}
					}
					if(fileNames.size()>0) {
						// tempoary path in the app server
					assetDocFile = NeoBankEnvironment.getFileUploadPath() + File.separator +"temp" + File.separator + fileNames.get(0); // only one file present/allowed to upload here, else run a loop
					assetDocFile = StringUtils.replace(assetDocFile, "\\", "/");
					}
					merchantCode = String.valueOf((new Date().getTime() / 10L) % Integer.MAX_VALUE).
							concat(String.valueOf(RandomUtils.nextInt(10, 99)));
					List<String> fileArrayEncoded = new ArrayList<String>();
					File fileBase64Encoded = new File(assetDocFile);
					if(fileBase64Encoded.exists() == false) throw new Exception ("File does not exists");
	                byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(fileBase64Encoded));
	                String base64fileString = new String(encoded, StandardCharsets.US_ASCII); // in case of multiple file it will be in array
	                NeoBankEnvironment.setComment(3,className,"encoding base64fileString--> base64fileString size:  "+base64fileString.length());                

	                HttpPost post = new HttpPost(NeoBankEnvironment.getFDocServerURL());
	                @SuppressWarnings("unused")
					int count = fileArrayEncoded.size(); //Array Size
			        // add request parameter, form parameters and only the file details.
			        List<NameValuePair> urlParameters = new ArrayList<>();
			        urlParameters.add(new BasicNameValuePair("qs", "fud"));
			        urlParameters.add(new BasicNameValuePair("rules", "uploadfile"));
			        urlParameters.add(new BasicNameValuePair("filename", 
			        		merchantCode.concat(".").concat(fileExtension)));
				    urlParameters.add(new BasicNameValuePair("fileraw", base64fileString)); // not using the Array as only 1 file is uploaded
			        post.setEntity(new UrlEncodedFormEntity(urlParameters));

			        CloseableHttpClient httpClient = HttpClients.createDefault();
			        CloseableHttpResponse urlresponse = httpClient.execute(post); 
			        String	sbResponse = EntityUtils.toString(urlresponse.getEntity());
			        NeoBankEnvironment.setComment(3, className,"***** after POST response is "+ sbResponse);
			        if(sbResponse!=null) {
			        	dmJsonResponse = new Gson().fromJson(sbResponse, JsonObject.class);
			        	if(dmJsonResponse.get("error").getAsBoolean() == false) {
			        		filePath = dmJsonResponse.get("filepath").getAsString();
			        		NeoBankEnvironment.setComment(3, className,"File path is "+ filePath);
			        	}else {
			        		message = dmJsonResponse.get("message").getAsString();
			        		NeoBankEnvironment.setComment(3, className,"File path is "+ message);
						}
			        }else {
			        	throw new Exception ("Problem in the external file update process");
			        }
			        
			        billerRef = Utilities.generatingRandomAlphanumericString();
			        success = (Boolean)MerchantDao.class.getConstructor().newInstance().registerNewMerchant(companyName, businessDesc, physicalAddress,
			        		bsnPhoneNumber,email,  nationalId, haveBranches, planValue, 
			        		 filePath,billerRef,merchantCode, mccId, fullName, phoneNo);	
			        try {	
			        	if(success == false) {
			        		obj.addProperty("error", "true"); 
			        		obj.addProperty("message", "Merchant registration failed"); 
			        	}else {
			        		obj.addProperty("error", "false"); 
			        		obj.addProperty("message", "Thank you for registering with us our operation team is working on your verification."
			        				+ " You will receive an email once the process is done");
			        	}
						output = response.getWriter();
						output.print(obj);
					}finally {
						if( companyName !=null) companyName = null; if( businessDesc != null) businessDesc = null; if( physicalAddress != null) physicalAddress = null;
						if( bsnPhoneNumber != null) bsnPhoneNumber = null;  if( assetDocFile != null) assetDocFile = null; 	 if( email != null) email = null;
						if( nationalId != null) nationalId = null;  if( planValue != null) planValue = null; if( haveBranches != null) haveBranches = null;
						if(fileNames.isEmpty()==false) {fileNames.clear(); fileNames = null;}
						if(fileArrayEncoded.isEmpty()==false) {fileArrayEncoded.clear(); fileArrayEncoded = null;}	
						if(base64fileString!=null) base64fileString = null; if(sbResponse!=null) sbResponse = null;
						if(httpClient!=null) httpClient.close(); if(urlresponse!=null) urlresponse.close(); if(urlParameters.isEmpty()==false) urlParameters.clear();
						if(encoded!=null) encoded = null;
						if(filePath!=null) filePath = null; if (parts!=null) parts=null;
						if(message!=null) message = null;
						if(fileBase64Encoded!=null && fileBase64Encoded.exists()) fileBase64Encoded.delete();
						if(output != null) output.flush();
						if(obj!=null) obj = null;
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Merchant registration failed, Please try again letter");
				}
			break;
			
			case "getmcccategory": 
				try {
					JsonObject object = new JsonObject();
					Gson gson = new Gson();
					List<MccGroup> listMccGroups =
							(List<MccGroup>)
							MerchantDao.class.getConstructor().newInstance().getMerchantCategories();
					object.add("data", gson.toJsonTree(listMccGroups));
					try {
						response.getWriter().print(object);
					}finally {
						response.getWriter().close();
						if(object!=null) object = null;
						if(listMccGroups!=null) listMccGroups = null;
						if(gson!=null) gson = null;
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Failed to get MCC Data");
				}
			break;
			
			default:
				throw new IllegalArgumentException("Rule not defined value: " + rulesaction);

			
			}
		}
}
