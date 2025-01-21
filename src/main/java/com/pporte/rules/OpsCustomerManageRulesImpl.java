package com.pporte.rules;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;

import com.pporte.dao.OpsManageCustomerDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.model.Customer;
import com.pporte.model.AssetTransaction;
import com.pporte.model.User;
import com.pporte.model.Wallet;
import com.pporte.utilities.SendEmailUtility;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;

public class OpsCustomerManageRulesImpl implements Rules{

private static String className = OpsCustomerManageRulesImpl.class.getSimpleName();

	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx)
			throws Exception {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null)
			Utilities.callOpsException(request, response, ctx, "Session has expired, please log in again");

		switch (rulesaction) {
		case "Pending Customers":
			try {
				
				String langPref = null;
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();

				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opscust");
				request.setAttribute("lastrule", "Pending Customers");
				ArrayList<Customer> arrPendingCustomers = (ArrayList<Customer>)OpsManageCustomerDao.class.getConstructor().newInstance().getPendingCustomers();
				request.setAttribute("pendingcustomers", arrPendingCustomers);
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsPendingCustomerPage()).forward(request, response);
				} finally {
					if(arrPendingCustomers !=null) arrPendingCustomers = null;	
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
			
		case "opsshowspecificcustomer":
			try {
								
				String relationshipNo = null; String langPref = null;
				if (request.getParameter("hdncustomercode") != null)  	  relationshipNo = request.getParameter("hdncustomercode").trim();
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				
				NeoBankEnvironment.setComment(3, className, "inside opsshowspecificcustomer relationshipNo  " + relationshipNo );

				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opscust");
				request.setAttribute("lastrule", "Pending Customers");
				response.setContentType("text/html");
				
				Customer m_CustomerDetails = (Customer)OpsManageCustomerDao.class.getConstructor().newInstance().getSpecificCustomerDetails(relationshipNo);
				request.setAttribute("specificcustomer", m_CustomerDetails);
				
				
				request.setAttribute("kycdocslist", (ArrayList<String>)OpsManageCustomerDao.class.getConstructor().newInstance().getAllKYCDocsForCustomer(relationshipNo));
				
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getOpsEditSpecificCustomerPage()).forward(request, response);
				} finally {
					if (m_CustomerDetails != null) m_CustomerDetails = null; if(relationshipNo !=null) relationshipNo = null;
					if(langPref !=null) langPref = null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;	
			
		case "View Customers":
			try {
				
				String langPref = null;
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opscust");
				request.setAttribute("lastrule", "View Customers");
				ArrayList<Customer> arrCustomerDetails = (ArrayList<Customer>)OpsManageCustomerDao.class.getConstructor().newInstance().getCustomersDetails();
				request.setAttribute("allcustomers", arrCustomerDetails);
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsAllCustomerDetailsPage()).forward(request, response);
				} finally {
					if(arrCustomerDetails !=null) arrCustomerDetails = null;	
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		case "opsshowwalletforcustomer":
			try {
				
				String langPref = null; String customerCode = null;
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				if(request.getParameter("hdncustomercode")!=null)			  customerCode = request.getParameter("hdncustomercode").trim();
				NeoBankEnvironment.setComment(3, className, "customerCode " + customerCode );

				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opscust");
				request.setAttribute("lastrule", "View Customers");
				
				ArrayList<Wallet> arrWallet = null;
				arrWallet = (ArrayList<Wallet>) OpsManageCustomerDao.class.getConstructor().newInstance().getWalletDetails(customerCode);
				
				request.setAttribute("custwallet", arrWallet);
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsCustViewWalletPage()).forward(request, response);
				} finally {
					//if(arrCustomerDetails !=null) arrCustomerDetails = null;
					if(arrWallet !=null) arrWallet = null;	
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		case "ViewmorecustomersSearch": 
			try {
				NeoBankEnvironment.setComment(3, className, "insude ViewmorecustomersSearch ");

				String langPref = null;
				String CustomerCode = ""; String customerId = ""; String custPhoneNo = ""; String custName=""; 
			   // String userId = null; String userType = null; 
			    ArrayList <Customer> arrCustomerDetails = null;

				if (request.getParameter("hdnlangpref") != null)    	langPref = request.getParameter("hdnlangpref").trim();
				if (request.getParameter("searchrelnno") != null) 		CustomerCode = request.getParameter("searchrelnno").trim();
				if (request.getParameter("searchcustid") != null) 		customerId = request.getParameter("searchcustid").trim();
				if (request.getParameter("searchmobileno") != null) 		custPhoneNo = request.getParameter("searchmobileno").trim();
				if (request.getParameter("searchcustname") != null) 		custName = request.getParameter("searchcustname").trim();
				
					if(CustomerCode.equals("") && customerId.equals("") &&custPhoneNo.equals("") && custName.equals("")) {
						throw new Exception("All Search criteria cannot be blank");
					}
					
					request.setAttribute("langPref", langPref);
					request.setAttribute("lastaction", "opscust");
					request.setAttribute("lastrule", "View Customer");
					
					//userId=((User) session.getAttribute("SESS_USER")).getUserId();
					//userType=((User) session.getAttribute("SESS_USER")).getUserType();
					
					NeoBankEnvironment.setComment(3, className, "insude CustomerCode " + CustomerCode + " customerId " + customerId + 
							" custPhoneNo "+ custPhoneNo + " custName "+ custName);
					
					arrCustomerDetails = (ArrayList<Customer>)OpsManageCustomerDao.class.getConstructor().newInstance().getOpsSpecificCustomerDetails(
							custName, CustomerCode,customerId,custPhoneNo);

					if(arrCustomerDetails !=null && arrCustomerDetails.size() > 0) {
						NeoBankEnvironment.setComment(3, className, "customer found "+ arrCustomerDetails.size());											
					}
					
										
					request.setAttribute("allcustomers", arrCustomerDetails);
					response.setContentType("text/html");
					
				try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsAllCustomerDetailsPage()).forward(request, response);
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Exception in calling RequestDispatcher is "+e.getMessage());
				}finally {
					if(arrCustomerDetails !=null) arrCustomerDetails = null; if(CustomerCode != null) CustomerCode=null;	
					if(customerId != null) customerId=null;	if(custPhoneNo != null) custPhoneNo=null;
					if(custName != null) custName = null; 
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

		switch (rulesaction) {

		case "opseditcustdetails":
			try {
				NeoBankEnvironment.setComment(3, className, "insude opseditcustdetails ");
	
				PrintWriter out_json = response.getWriter();
				JsonObject obj = new JsonObject(); String langPref = null;
				String CustomerCode = null; String natioanlId = null; String custPhoneNo = null; String custName=null;
				String custEmail=null; String physicalAddress=null;String custDateOfBirth=null; String expiryDate=null;
				String approvedBy=null; String userId=null;String userType=null;String gender=null;String status=null;
				String errorMsg = "false"; String passportNo=null;
	
				if (request.getParameter("hdnlangpref") != null) langPref = request.getParameter("hdnlangpref").trim();
				if (request.getParameter("relno") != null) CustomerCode = request.getParameter("relno").trim();
				if (request.getParameter("nationalid") != null) natioanlId = request.getParameter("nationalid").trim();
				if (request.getParameter("custphoneno") != null) custPhoneNo = request.getParameter("custphoneno").trim();
				if (request.getParameter("custname") != null) custName = request.getParameter("custname").trim();
				if (request.getParameter("custemail") != null) custEmail = request.getParameter("custemail").trim();
				if (request.getParameter("physicaladdress") != null) physicalAddress = request.getParameter("physicaladdress").trim();
				if (request.getParameter("custdob") != null) custDateOfBirth = request.getParameter("custdob").trim();
				if (request.getParameter("expiry") != null) expiryDate = request.getParameter("expiry").trim();
				if (request.getParameter("passportno") != null) passportNo = request.getParameter("passportno").trim();
				if (request.getParameter("hdncustgender") != null) gender = request.getParameter("hdncustgender").trim();
				if (request.getParameter("hdncuststatus") != null) status = request.getParameter("hdncuststatus").trim();
	
	
				userId=((User) session.getAttribute("SESS_USER")).getUserId();
				userType=((User) session.getAttribute("SESS_USER")).getUserType();
	
	
				boolean result=(boolean)OpsManageCustomerDao.class.getConstructor().newInstance().UpdateCustomerWithoutPasswordDetails(CustomerCode,natioanlId,
				custPhoneNo,custName,custEmail,physicalAddress,custDateOfBirth,expiryDate,userId,gender,status,passportNo);
				if (result) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",
					"Ops Updated Customer Details " + CustomerCode);
		
					obj.addProperty("error", "false");
					obj.addProperty("message", "Customer details updated successful");
				} else {
					NeoBankEnvironment.setComment(1, className, "Error in Updating Customer user");
					obj.addProperty("error", "true");
					obj.addProperty("message", "Error in updating customer details");
				}
	
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
					out_json.print(obj);
				}finally {
					if(out_json !=null) out_json.close(); out_json = null; if(obj !=null) obj =null; if(langPref !=null) langPref =null;
					if(CustomerCode !=null) CustomerCode =null; if(natioanlId !=null) natioanlId =null; if(custPhoneNo !=null) custPhoneNo =null;
					if(custName !=null) custName =null; if(custEmail !=null) custEmail =null; if(physicalAddress !=null) physicalAddress =null;
					if(approvedBy !=null) approvedBy =null; if(userId !=null) userId =null; if(gender !=null) gender =null;if(errorMsg !=null) errorMsg =null;
					if(custDateOfBirth !=null) custDateOfBirth =null; if(status !=null) status =null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error In opseditcustdetailst, Please try again letter");
			}
			break;
			
		case "opsapprovecustomer":
			try{
				PrintWriter out_json = response.getWriter();
				JsonObject obj = new JsonObject(); String langPref = null;String relationshipNo = null; 
				String custName = null; String custEmail=null; String userId=null; String userType=null;
				boolean result=false; String subject=null; String content=null;boolean success=false;
	
				if (request.getParameter("hdnlangpref") != null) langPref = request.getParameter("hdnlangpref").trim();
				if (request.getParameter("relno") != null) relationshipNo = request.getParameter("relno").trim();
				if (request.getParameter("custname") != null) custName = request.getParameter("custname").trim();
				if (request.getParameter("custemail") != null) custEmail = request.getParameter("custemail").trim();
			
	
				userId=((User) session.getAttribute("SESS_USER")).getUserId();
				userType=((User) session.getAttribute("SESS_USER")).getUserType();
				
			/* TODO:- Uncomment this code on 8th Iteration and test it 	
=======
>>>>>>> d968d11af81b19fe154325f0e7d679958b8c4be6
				// Check if OPS has viewed the customer's document
				success=(boolean)OpsManageCustomerDao.class.getConstructor().newInstance().checkIfDocumentsHasBeenReviewedByOps(relationshipNo);
				
				NeoBankEnvironment.setComment(3,className,"success is "+success);
				if (!success) {
					// Not been reviewed
					Utilities.sendJsonResponse(response, "notreviewed", "Please download the user's document and review them, before approving the customer.");
					return;
				}
<<<<<<< HEAD
				*/

				result=(boolean)OpsManageCustomerDao.class.getConstructor().newInstance().approveNewCustomer(relationshipNo,userId);	
				
				if (result) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "OAC",
					"Ops Approved Customer " + relationshipNo);
		
					obj.addProperty("error", "false");
					obj.addProperty("message", "Customer approved successful");
					// Send Email to User
					 subject="WELCOME TO PAYMENT PORTE!";
					 content="Your credentials have been verified. You can now login using your registered credentials to our platfrom, to transact easily and securely."+ System.lineSeparator()
						+" PAYMENTS MADE EASY, WELCOME! ";
					// Send to user
					String sendto = custEmail;
					String sendSubject = subject;
					String sendContent = content;
					String customerName = custName;
					
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
				} else {
					NeoBankEnvironment.setComment(1, className, "Error in approving Customer ");
					obj.addProperty("error", "true");
					obj.addProperty("message", "Error in approving Customer");
				}
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
					out_json.print(obj);
				}finally {
					if(out_json !=null) out_json.close(); out_json = null; if(obj !=null) obj =null; if(langPref !=null) langPref =null;
					if(relationshipNo !=null) relationshipNo =null; 
					if(custName !=null) custName =null; if(custEmail !=null) custEmail =null; if(userId !=null) userId =null; 
				}
			}catch(Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error In approving customer, Please try again letter");
			}
		break;
		
		case "ViewmorecustomersSearch": 
			try {
				NeoBankEnvironment.setComment(3, className, "insude ViewmorecustomersSearch ");

				PrintWriter out_json = response.getWriter();
				Gson gson = new Gson(); JsonObject obj = new JsonObject(); String langPref = null;
				String CustomerCode = ""; String customerId = ""; String custPhoneNo = ""; String custName=""; 
				String errorMsg = "false";  String userId = null; String userType = null;

				if (request.getParameter("hdnlangpref") != null)    	langPref = request.getParameter("hdnlangpref").trim();
				if (request.getParameter("searchrelnno") != null) 		CustomerCode = request.getParameter("searchrelnno").trim();
				if (request.getParameter("searchcustid") != null) 		customerId = request.getParameter("searchcustid").trim();
				if (request.getParameter("searchmobileno") != null) 		custPhoneNo = request.getParameter("searchmobileno").trim();
				if (request.getParameter("searchcustname") != null) 		custName = request.getParameter("searchcustname").trim();
				
				try {
					if(CustomerCode.equals("") && customerId.equals("") &&custPhoneNo.equals("") && custName.equals("")) {
						errorMsg = "Enter atleast one field";
						throw new Exception("All Search criteria cannot be blank");
				
					}
					
					request.setAttribute("langPref", langPref);
					request.setAttribute("lastaction", "opscust");
					request.setAttribute("lastrule", "View Customer");
					
					userId=((User) session.getAttribute("SESS_USER")).getUserId();
					userType=((User) session.getAttribute("SESS_USER")).getUserType();
					
					NeoBankEnvironment.setComment(3, className, "insude CustomerCode " + CustomerCode + " customerId " + customerId + 
							" custPhoneNo "+ custPhoneNo + " custName "+ custName);
					
					ArrayList <Customer> arrCustomerDetails = (ArrayList<Customer>)OpsManageCustomerDao.class.getConstructor().newInstance().getOpsSpecificCustomerDetails(custName, CustomerCode,customerId,custPhoneNo);

					if(arrCustomerDetails !=null && arrCustomerDetails.size() > 0) {
						NeoBankEnvironment.setComment(3, className, "customer found "+ arrCustomerDetails.size());

						request.setAttribute("allcustomers", arrCustomerDetails);
						response.setContentType("text/html");
						 try {
								ctx.getRequestDispatcher(NeoBankEnvironment.getOpsAllCustomerDetailsPage()).forward(request, response);
						} catch (Exception e) {
							NeoBankEnvironment.setComment(1, className, "Exception in calling RequestDispatcher inside multipart json request is "+e.getMessage());
						}finally {
							if(arrCustomerDetails !=null) arrCustomerDetails = null;	
						}
						errorMsg = "false";						
						
					}else {
						NeoBankEnvironment.setComment(3, className, "customer not found ");
						errorMsg = "User not found";						
						out_json.print(gson.toJson(obj));

					}
					obj.add("error", gson.toJsonTree(errorMsg));
					//out_json.print(gson.toJson(obj));
									
				} catch (Exception e1) {
					NeoBankEnvironment.setComment(1, className, "Problem with the ViewmorecustomersSearch method "+e1.getMessage());
					   obj.add("error", gson.toJsonTree(errorMsg));
					    out_json.print( gson.toJson(obj));
					    NeoBankEnvironment.setComment(1, className, "Exception,  gson "+ gson.toJson(obj));
				}finally {
					
					if(out_json !=null) out_json.close(); out_json = null; if(obj !=null) obj =null; if(langPref !=null) langPref =null;
					if(CustomerCode !=null) CustomerCode =null; if(customerId !=null) customerId =null; if(custPhoneNo !=null) custPhoneNo =null;
					if(custName !=null) custName =null; if(userId !=null) userId =null; if(errorMsg !=null) errorMsg =null; if(userType!=null) userType=null;
										
				}
		
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className,
						"Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
			
		case "opsgetcustwallettxns":
			try {
				NeoBankEnvironment.setComment(3, className, "insude opseditcustdetails ");

				PrintWriter out_json = response.getWriter();
				Gson gson = new Gson(); JsonObject obj = new JsonObject(); String langPref = null; String walletId = null;
				String errorMsg = "false"; ArrayList<AssetTransaction> arrTransactions =null;

				try {
					if (request.getParameter("hdnlangpref") != null)    	langPref = request.getParameter("hdnlangpref").trim();
					if (request.getParameter("hdnwalletid") != null) 		walletId = request.getParameter("hdnwalletid").trim();

					request.setAttribute("langPref", langPref);
					request.setAttribute("lastaction", "opscust");
					request.setAttribute("lastrule", "View Customer");
			
					NeoBankEnvironment.setComment(3, className, "insude walletId " + walletId );
					
					arrTransactions = (ArrayList<AssetTransaction>)OpsManageCustomerDao.class.getConstructor().newInstance().getAllTransactionsForWallet(walletId);

					if(arrTransactions !=null && arrTransactions.size() > 0) {
						errorMsg = "false";
						obj.add("arrtxns", gson.toJsonTree(arrTransactions));
					}else {
						NeoBankEnvironment.setComment(3, className, "Not Transactions Present for customer wallet "+walletId);
						errorMsg = "Not Transactions Present";
						
					}					
					obj.add("error", gson.toJsonTree(errorMsg));
					out_json.print(gson.toJson(obj));
					
				} catch (Exception e1) {
					NeoBankEnvironment.setComment(1, className, "Problem with the opsgetcustwallettxns method "+e1.getMessage());
					   obj.add("error", gson.toJsonTree(errorMsg));
					    out_json.print( gson.toJson(obj));
					    NeoBankEnvironment.setComment(1, className, "Exception,  gson "+ gson.toJson(obj));
					    
					throw new Exception ("Problem occured in Ops get wallet transactions");
				}finally {
					if(out_json !=null) out_json.close(); out_json = null; if(obj !=null) obj =null; 
					if(langPref !=null) langPref =null; if(walletId !=null) walletId=null; if(arrTransactions!=null) arrTransactions=null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
			
		case "opsunblockcustpin":
			try {
				NeoBankEnvironment.setComment(3, className, "insude opsunblockcustpin ");

				PrintWriter out_json = response.getWriter(); boolean result = false;
				Gson gson = new Gson(); JsonObject obj = new JsonObject(); String langPref = null; String customerCode = null;
				String errorMsg = "false";  String phoneNumber = null;  String custEmail = null; String userId = null;
				String userType = null;

				try {
					if (request.getParameter("hdnlangpref") != null)    	langPref = request.getParameter("hdnlangpref").trim();
					if (request.getParameter("hdncustomercode") != null) 		customerCode = request.getParameter("hdncustomercode").trim();
					if (request.getParameter("hdncustphone") != null) 		phoneNumber = request.getParameter("hdncustphone").trim();
					if (request.getParameter("hdncustemail") != null) 		custEmail = request.getParameter("hdncustemail").trim();

					request.setAttribute("langPref", langPref);
					request.setAttribute("lastaction", "opscust");
					request.setAttribute("lastrule", "View Customer");
					
					userId=((User) session.getAttribute("SESS_USER")).getUserId();
					userType=((User) session.getAttribute("SESS_USER")).getUserType();
					
					NeoBankEnvironment.setComment(3, className, "insude customerCode " + customerCode );
					
					result=(boolean)OpsManageCustomerDao.class.getConstructor().newInstance().OpsUnblockedCustPin(customerCode);
					
					if(result) {
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, "O", "O",StringUtils.substring("Unblocked pin for " + customerCode, 0, 48));

						//TODO Send email/sms to notify the customer for the action
						errorMsg = "false";
						obj.add("message", gson.toJsonTree("Customer account PIN unblocked successful"));

					}else {
						NeoBankEnvironment.setComment(3, className, "Failed to unblock customer account PIN for  "+customerCode);

						errorMsg = "Failed to unblock customer account PIN";

					}
								
					obj.add("error", gson.toJsonTree(errorMsg));
					out_json.print(gson.toJson(obj));
					
				} catch (Exception e1) {
					NeoBankEnvironment.setComment(1, className, "Problem with the opsunblockcustpin method "+e1.getMessage());
					   obj.add("error", gson.toJsonTree(errorMsg));
					    out_json.print( gson.toJson(obj));
					    NeoBankEnvironment.setComment(1, className, "Exception,  gson "+ gson.toJson(obj));
					    
					throw new Exception ("Problem occured in unblocking customer Account PIN");
				}finally {
					if(out_json !=null) out_json.close(); out_json = null; if(obj !=null) obj =null; 
					if(langPref !=null) langPref =null; if(custEmail !=null) custEmail=null; if(phoneNumber !=null) phoneNumber=null;
					if(customerCode !=null) customerCode=null; if(errorMsg !=null) errorMsg=null;if(userType!=null) userType=null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
			
		case "opsunlockcustassword":
			try {
				NeoBankEnvironment.setComment(3, className, "insude opsunlockcustassword ");

				PrintWriter out_json = response.getWriter(); boolean result = false;
				Gson gson = new Gson(); JsonObject obj = new JsonObject(); String langPref = null; String customerCode = null;
				String errorMsg = "false";  String phoneNumber = null;  String custEmail = null; String userId = null;
				String userType = null;

				try {
					if (request.getParameter("hdnlangpref") != null)    	langPref = request.getParameter("hdnlangpref").trim();
					if (request.getParameter("hdncustomercode") != null) 		customerCode = request.getParameter("hdncustomercode").trim();
					if (request.getParameter("hdncustphone") != null) 		phoneNumber = request.getParameter("hdncustphone").trim();
					if (request.getParameter("hdncustemail") != null) 		custEmail = request.getParameter("hdncustemail").trim();

					request.setAttribute("langPref", langPref);
					request.setAttribute("lastaction", "opscust");
					request.setAttribute("lastrule", "View Customer");
					
					userId=((User) session.getAttribute("SESS_USER")).getUserId();
					userType=((User) session.getAttribute("SESS_USER")).getUserType();
					
					NeoBankEnvironment.setComment(3, className, "insude customerCode " + customerCode );
					
					result=(boolean)OpsManageCustomerDao.class.getConstructor().newInstance().OpsUnlockCustPass(customerCode);
					
					if(result) {
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, "O", "O",StringUtils.substring("Unblocked pin for " + customerCode, 0, 48));

						//TODO Send email/sms to notify the customer for the action
						errorMsg = "false";
						obj.add("message", gson.toJsonTree("Customer account unlocked successful"));

					}else {
						NeoBankEnvironment.setComment(3, className, "Failed to unlock customer account for  "+customerCode);

						errorMsg = "Failed to unlock customer account";

					}
								
					obj.add("error", gson.toJsonTree(errorMsg));
					out_json.print(gson.toJson(obj));
					
				} catch (Exception e1) {
					NeoBankEnvironment.setComment(1, className, "Problem with the opsunlockcustassword method "+e1.getMessage());
					   obj.add("error", gson.toJsonTree(errorMsg));
					    out_json.print( gson.toJson(obj));
					    NeoBankEnvironment.setComment(1, className, "Exception,  gson "+ gson.toJson(obj));
					    
					throw new Exception ("Problem occured in unlocking customer Account");
				}finally {
					if(out_json !=null) out_json.close(); out_json = null; if(obj !=null) obj =null; 
					if(langPref !=null) langPref =null; if(custEmail !=null) custEmail=null; if(phoneNumber !=null) phoneNumber=null;
					if(customerCode !=null) customerCode=null; if(errorMsg !=null) errorMsg=null; if(userType!=null) userType=null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
			
		
			
		default:
			throw new IllegalArgumentException("Rule not defined, value: " + rulesaction);
		}

	}

	

}
