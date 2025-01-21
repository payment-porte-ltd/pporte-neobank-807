package com.pporte.rules;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.stellar.sdk.KeyPair;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.CustomerDao;
import com.pporte.dao.CustomerWalletDao;
import com.pporte.dao.CustomerFiatWalletDao;
import com.pporte.dao.CustomerTokenizationDao;
import com.pporte.dao.OpsManagePricingPlanDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.dao.WalletAuthorizationDao;
import com.pporte.utilities.Bip39Utility;
import com.pporte.utilities.BitcoinUtility;
import com.pporte.utilities.StellarSDKUtility;
import com.pporte.utilities.Utilities;
import com.pporte.model.AssetAccount;
import com.pporte.model.AssetTransaction;
import com.pporte.model.Customer;
import com.pporte.model.Days;
import com.pporte.model.CardDetails;
import com.pporte.model.PricingPlan;
import com.pporte.model.User;
import com.pporte.model.Wallet;

import framework.v8.Rules;

public class CustomerRulesImpl implements Rules {
	private static String className = CustomerRulesImpl.class.getSimpleName();
	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response, ServletContext ctx)
			throws Exception {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null) Utilities.callException(request, response, ctx, "Session has expired, please log in again");
		switch (rulesaction) {
				case "Dashboard":
					try {
						String langPref=null;
						if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
						NeoBankEnvironment.setComment(3, className, "====== start  Dashbord fetch "+ java.time.LocalTime.now());
						//response.sendRedirect("servlet2");
						User user = null; String relationshipNo = null; Wallet wallet = null;
						String userType = null; 
						request.setAttribute("lastaction", "dash");
						request.setAttribute("lastrule", "Dashboard");
						response.setContentType("text/html");
						//Get account balances
						user = (User) session.getAttribute("SESS_USER");
						userType = user.getUserType();
						relationshipNo = user.getRelationshipNo();
						wallet= (Wallet)CustomerWalletDao.class.getConstructor().newInstance().getCustomerWalletDetails(relationshipNo,userType);
						String publicKey = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
						KeyPair userAccount = null;
						ArrayList<AssetAccount> accountBalances = null;
						NeoBankEnvironment.setComment(3, className, " Public key is  "+publicKey);
						if(publicKey != null && publicKey != "") {
							userAccount = KeyPair.fromAccountId(publicKey);
							accountBalances = StellarSDKUtility.getAccountBalance(userAccount);
						}
						//Get the last 10 transactions for fiat
						
						
						NeoBankEnvironment.setComment(3, className, " Public key is  "+publicKey);

						request.setAttribute("wallet",wallet);
						request.setAttribute("externalwallets",accountBalances);
						request.setAttribute("publickey",publicKey);
						request.setAttribute("langpref", langPref);

						try {
								ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerDashboadPage()).forward(request, response);
						} finally {
						  if(wallet !=null) wallet = null;
							if(accountBalances !=null) accountBalances = null; if(publicKey !=null) publicKey = null;
														
						}
						
						NeoBankEnvironment.setComment(3, className, "====== finished  Dashbord fetch "+ java.time.LocalTime.now());

					}catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
						Utilities.callException(request, response, ctx, e.getMessage());
					}
				break;	
		
				case "View and Edit":
					try {
						String langPref=null;
						if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
						NeoBankEnvironment.setComment(3, className, "langPref "+langPref);
						request.setAttribute("langpref", langPref);
						request.setAttribute("lastaction", "prf");
						request.setAttribute("lastrule", "View and Edit");
						response.setContentType("text/html");
						try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerProfilePage()).forward(request, response);
						} finally {
							if (langPref!=null) langPref=null;
						}
					}catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
						Utilities.callException(request, response, ctx, e.getMessage());
					}
					break;
					
				case "Buy New Plan":
					try {
						request.setAttribute("lastaction", "plb");
						request.setAttribute("lastrule", rulesaction);
						response.setContentType("text/html");
						request.setAttribute("pricingplans", (ArrayList<PricingPlan>) OpsManagePricingPlanDao.class.getConstructor()
								.newInstance().getCustomerPricingPlan());
						///NeoBankEnvironment.setComment(3, className, "bip 39 "+Utilities.encryptString("view,bleak,candy,double,auction,pupil,catalog,pattern,alley,breeze,woman,adjust"));
						try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerBuyNewPlanPage()).forward(request, response);
						} finally {
							
						}
					}catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
						Utilities.callException(request, response, ctx, e.getMessage());
					}
					break;
					
				case "custconfirmbuyplan":
					try {
						String planId = null; String planDesc = null; String planDuration = null; String planPrice = null;
						User user = null; String originalPlanId = "";
						String langPref=null;
						if(request.getParameter("hdnlang")!=null)			langPref = request.getParameter("hdnlang").trim();
						if(request.getParameter("hdnplanid")!=null) planId = StringUtils.trim(request.getParameter("hdnplanid"));
						if(request.getParameter("hdnplandesc")!=null) planDesc = StringUtils.trim(request.getParameter("hdnplandesc"));
						if(request.getParameter("hdnplanduration")!=null) planDuration = StringUtils.trim(request.getParameter("hdnplanduration"));
						if(request.getParameter("hdnplanval")!=null) planPrice = StringUtils.trim(request.getParameter("hdnplanval"));
						
						if(request.getParameter("hdnoriginalplanid")!=null) originalPlanId = StringUtils.trim(request.getParameter("hdnoriginalplanid"));
						
						NeoBankEnvironment.setComment(3, className, "planId "+planId+" planDesc "+planDesc+" planDuration "+planDuration
								+" planPrice "+planPrice+" originalPlanId "+originalPlanId);
						
						user = (User)session.getAttribute("SESS_USER");
						if(user==null) throw new Exception ("Session expired");
						
						

						request.setAttribute("lastaction", "plb");
						request.setAttribute("lastrule", "Buy New Plan");
						request.setAttribute("langPref", langPref);
						request.setAttribute("confirmplanid", planId);
						request.setAttribute("confirmplandesc", planDesc);
						request.setAttribute("confirmplanduration", planDuration);
						request.setAttribute("confirmplanprice", planPrice);
						request.setAttribute("relno", user.getRelationshipNo());
						request.setAttribute("originalplanid", originalPlanId);
											
						request.setAttribute("custwallet", (Wallet)CustomerWalletDao.class.getConstructor().newInstance()
								.getCustomerWalletDetails(user.getRelationshipNo(), user.getUserType()));
						
						request.setAttribute("custcards",(ArrayList<CardDetails>)CustomerTokenizationDao.class.getConstructor()
								.newInstance().getTokenizedCards(user.getRelationshipNo()));
						
						response.setContentType("text/html");
						
						try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerConfirmBuyPlanPage()).forward(request, response);
						} finally {
							if (planId!=null)planId=null;if (planDesc!=null)planDesc=null;if (planDuration!=null)planDuration=null;
							if (planPrice!=null)planPrice=null;if (user!=null)user=null;if (originalPlanId!=null)originalPlanId=null;
							if(langPref!=null)langPref=null;
						}
					}catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
						Utilities.callException(request, response, ctx, e.getMessage());
					}
					break;	
				case "Change Your Plan":
					try {
						// Check if the user already has a plan
						User user = null;String currentPrice=null;String pricePlan=null;
						user = (User)session.getAttribute("SESS_USER");
						
						String langPref=null;
						if(request.getParameter("hdnlang")!=null)			langPref = request.getParameter("hdnlang").trim();
						
						currentPrice=(String)OpsManagePricingPlanDao.class.getConstructor()
								.newInstance().getCurrentPlanPrice(user.getRelationshipNo());
						pricePlan=(String)OpsManagePricingPlanDao.class.getConstructor()
								.newInstance().getPlanPrice();
						
						double result=Double.parseDouble(pricePlan)-Double.parseDouble(currentPrice);
						request.setAttribute("lastaction", "plu");
						request.setAttribute("lastrule", rulesaction);
						request.setAttribute("langPref", langPref);
						response.setContentType("text/html");
						request.setAttribute("pricingplans", (ArrayList<PricingPlan>) OpsManagePricingPlanDao.class.getConstructor()
								.newInstance().getCustomerPricingPlan());
						request.setAttribute("currentplanprice",result );
						try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerBuyNewPlanPage()).forward(request, response);
						} finally {
							if (user!=null)user=null; if (currentPrice!=null)currentPrice=null;
							if (pricePlan!=null)pricePlan=null; if (langPref!=null) langPref=null;
						}
					}catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
						Utilities.callException(request, response, ctx, e.getMessage());
					}
					break;
					
					

		}
		
	}
	@Override
	public void performJSONOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		
	}

	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {	
		HttpSession session = request.getSession(false);
		switch (rulesaction) {
		
			case "fetchcustdetails":
				try {
					String relationshipNo=null;  Customer m_Customer=null; String documentLocation=null;
					PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject(); 
					ArrayList<String> arrCustKYCDocs=null;
					if(request.getParameter("relno")!=null)				relationshipNo = request.getParameter("relno").trim();
					if (session.getAttribute("SESS_USER") == null) {
						Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
						return;
					}

					m_Customer= (Customer)CustomerDao.class.getConstructor().newInstance().getFullCustomerProfile(relationshipNo);
					// File for customer
					arrCustKYCDocs= (ArrayList<String>)CustomerDao.class.getConstructor().newInstance().getAllKYCDocsForCustomer(relationshipNo);
					
					
					if (m_Customer!=null) {
					
						obj.add("userid", gson.toJsonTree(m_Customer.getCustomerId()));
						obj.add("name", gson.toJsonTree(m_Customer.getName()));
						obj.add("email", gson.toJsonTree(m_Customer.getEmail()));
						obj.add("contact", gson.toJsonTree(m_Customer.getContact()));
						obj.add("address", gson.toJsonTree(m_Customer.getAddress()));
						obj.add("dob", gson.toJsonTree(m_Customer.getDateOfBirth()));
						obj.add("nationalid", gson.toJsonTree(m_Customer.getNationalId()));
						obj.add("passportno", gson.toJsonTree(m_Customer.getPassportNo()));
						obj.add("gender", gson.toJsonTree(m_Customer.getGender()));
						obj.add("status", gson.toJsonTree(m_Customer.getStatus()));
						obj.add("kycdocslist", gson.toJsonTree(arrCustKYCDocs));
						obj.add("error", gson.toJsonTree("false"));
					}else {
						obj.add("error", gson.toJsonTree("true")); 
						obj.add("msg", gson.toJsonTree("Customer Details not found")); 
					}
					try {
						NeoBankEnvironment.setComment(3, className," fetchcustdetails String is " + gson.toJson(obj));
						out_json.print(gson.toJson(obj));
					} finally {
						if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						if (documentLocation != null)documentLocation = null;  if (obj != null)obj = null; if (arrCustKYCDocs!=null)arrCustKYCDocs=null;
						if (m_Customer!=null)m_Customer=null;
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case fetchcustdetails is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error getting customer information, Try again");

				}
		
		    break;
		    
			case "fetchcustdetails_mbl":
				try {
					NeoBankEnvironment.setComment(3, className, "In fetchcustdetails_mbl");
					String relationshipNo=null;  Customer m_Customer=null; String documentLocation=null;
					PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject(); 
					String tokenValue = null;String langPref=null;
					 
					if(request.getParameter("relno")!=null)				relationshipNo = request.getParameter("relno").trim();
					if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
					if(request.getParameter("hdnlangpref")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlangpref"));
					NeoBankEnvironment.setComment(3, className, "langPref is "+langPref+" relationshipNo "+relationshipNo+" tokenValue "+tokenValue);
					if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v�lido, vuelva a iniciar sesi�n");

						}else {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
						}
						return;
					}

					m_Customer= (Customer)CustomerDao.class.getConstructor().newInstance().getFullCustomerProfile(relationshipNo);
					
					if (m_Customer!=null) {
						
						documentLocation= m_Customer.getDocumentLocation();
									
						obj.add("userid", gson.toJsonTree(m_Customer.getCustomerId()));
						obj.add("name", gson.toJsonTree(m_Customer.getName()));
						obj.add("email", gson.toJsonTree(m_Customer.getEmail()));
						obj.add("contact", gson.toJsonTree(m_Customer.getContact()));
						obj.add("address", gson.toJsonTree(m_Customer.getAddress()));
						obj.add("dob", gson.toJsonTree(m_Customer.getDateOfBirth()));
						obj.add("nationalid", gson.toJsonTree(m_Customer.getNationalId()));
						obj.add("passportno", gson.toJsonTree(m_Customer.getPassportNo()));
						obj.add("gender", gson.toJsonTree(m_Customer.getGender()));
						obj.add("status", gson.toJsonTree(m_Customer.getStatus()));
						obj.add("error", gson.toJsonTree("false"));
					}else {
						obj.add("error", gson.toJsonTree("true")); 
						if(langPref.equalsIgnoreCase("ES")) {
							obj.add("msg", gson.toJsonTree("Detalles del cliente no encontrados")); 
						}else {
							obj.add("msg", gson.toJsonTree("Customer Details not found")); 
						}
						
					}
					try {
						NeoBankEnvironment.setComment(3, className," fetchcustdetails String is " + gson.toJson(obj));
						out_json.print(gson.toJson(obj));
					} finally {
						if (tokenValue != null)tokenValue = null;
						if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						if (documentLocation != null)documentLocation = null;  if (obj != null)obj = null;
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case fetchcustdetails_mbl is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error getting customer information, Try again");

				}
		
		    break;
		    
			case "editcustdetails":
				try {
					String relationshipNo=null; 
					PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
					String address=null; boolean success=false; 
					
					if(request.getParameter("relno")!=null)				relationshipNo = request.getParameter("relno").trim();
					if(request.getParameter("editaddress")!=null)			address = request.getParameter("editaddress").trim();
					
					if (session.getAttribute("SESS_USER") == null) {
						Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
						return;
					}
		
					success= (boolean)CustomerDao.class.getConstructor().newInstance().updateCustomerDetails(relationshipNo,address);
					
					if (success) {
						// Customer Update Profile
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "CUP","Customer update profile" );
						obj.add("error", gson.toJsonTree("false"));
					}else {
						obj.add("error", gson.toJsonTree("true")); 
						obj.add("msg", gson.toJsonTree("Failed to update customer details")); 
					}
					try {
						NeoBankEnvironment.setComment(3, className," editcustdetails String is " + gson.toJson(obj));
						out_json.print(gson.toJson(obj));
					} finally {
						if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						if (obj != null)obj = null;if (address != null)address = null;
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case editcustdetails is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Failed to edit customer details, Try again");
				}
				break;
				
			case "editcustdetails_mbl":
				try {
					String relationshipNo=null; String tokenValue = null;
					PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
					boolean success=false; String address=null;String langPref=null;
					
					if(request.getParameter("relno")!=null)				relationshipNo = request.getParameter("relno").trim();
					if(request.getParameter("editaddress")!=null)			address = request.getParameter("editaddress").trim();
					if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
					if(request.getParameter("hdnlangpref")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlangpref"));
					//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
					if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v�lido, vuelva a iniciar sesi�n");

						}else {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
						}
						return;
					}
		
					success= (boolean)CustomerDao.class.getConstructor().newInstance().updateCustomerDetails(relationshipNo,address);
					
					if (success) {
						// Customer Update Profile
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "CUP","Customer update profile" );
						obj.add("error", gson.toJsonTree("false"));
						obj.add("msg", gson.toJsonTree(""));
						if(langPref.equalsIgnoreCase("ES")) {
							obj.add("msg", gson.toJsonTree("Perfil editado correctamente")); 
						}else {
							obj.add("msg", gson.toJsonTree("Profile Edited Successful")); 
						}
					}else {
						obj.add("error", gson.toJsonTree("true"));
						
						if(langPref.equalsIgnoreCase("ES")) {
							obj.add("msg", gson.toJsonTree("Error al actualizar los detalles del cliente")); 
						}else {
							obj.add("msg", gson.toJsonTree("Failed to update customer details")); 
						}
						
					}
					try {
						NeoBankEnvironment.setComment(3, className," editcustdetails String is " + gson.toJson(obj));
						out_json.print(gson.toJson(obj));
					} finally {
						if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						if (obj != null)obj = null; if (tokenValue != null)tokenValue = null; if(address!=null)address=null;
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case editcustdetails is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Failed to edit customer details, Try again");
				}
				break;
				
			case "getfiatweeklyforgraph":
				try {
					String relationshipNo=null;
					PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
					int howManyDays = 15;
					ArrayList<Days> arrTransactionSum = null;
					
					if(request.getParameter("relno")!=null)		relationshipNo = request.getParameter("relno").trim();
					NeoBankEnvironment.setComment(3, className," relationshipNo is " +relationshipNo);
						// TODO:- This method needs to be relooked;
					//hashSumOfTxns= (ConcurrentHashMap<String,String>)CustomerDao.class.getConstructor().newInstance().getLastXTxnsValuesForFiatWallet(relationshipNo,howManyDays);
					arrTransactionSum= (ArrayList<Days>)CustomerDao.class.getConstructor().newInstance().getLastFiveteenDaysTransactions(relationshipNo,howManyDays);
					
					NeoBankEnvironment.setComment(3, className," arrTransactionSum  size is " +arrTransactionSum.size());
						
					    obj.add("error", gson.toJsonTree("false"));
						obj.add("data", gson.toJsonTree(arrTransactionSum));
					
					try {
						NeoBankEnvironment.setComment(3, className," getfiatweeklyforgraph String is " + gson.toJson(obj));
						out_json.print(gson.toJson(obj)); 
					} finally {
						if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						if (obj != null)obj = null; if(arrTransactionSum !=null) arrTransactionSum=null;
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case getfiatweeklyforgraph is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Failed to get Fiat weekly transaction for graph, Try again");
				}
				break;
				
			case "getfiatmonthlyforgraph":
				try {
					String relationshipNo=null;
					PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
					int howManyDays = 15;
					ArrayList<String> arrMonthlyTransaction = null;
					
					if(request.getParameter("relno")!=null)		relationshipNo = request.getParameter("relno").trim();
					NeoBankEnvironment.setComment(3, className," relationshipNo is " +relationshipNo);

					//hashSumOfTxns= (ConcurrentHashMap<String,String>)CustomerDao.class.getConstructor().newInstance().getLastXTxnsValuesForFiatWallet(relationshipNo,howManyDays);
					arrMonthlyTransaction= (ArrayList<String>)CustomerDao.class.getConstructor().newInstance().getMonthlyTxnForFiatWallet(relationshipNo,howManyDays);
					
						
					    obj.add("error", gson.toJsonTree("false"));
						obj.add("data", gson.toJsonTree(arrMonthlyTransaction));
					
					try {
						NeoBankEnvironment.setComment(3, className," getfiatmonthlyforgraph String is " + gson.toJson(obj));
						out_json.print(gson.toJson(obj)); 
					} finally {
						if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						if (obj != null)obj = null; if(arrMonthlyTransaction !=null) arrMonthlyTransaction=null;
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case getfiatmonthlyforgraph is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Failed to get Fiat Monthly transaction for graph, Try again");
				}
				break;
				
			case "getmonthlytxnforgraph":
				try {
					String relationshipNo=null;
					PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
					ArrayList<String> arrPorteMonthlyTransaction = null; ArrayList<String> arrXLMMonthlyTransaction = null;
					ArrayList<String> arrUSDCMonthlyTransaction = null;ArrayList<String> arrVesselMonthlyTransaction = null;
				
		      		
					if(request.getParameter("relno")!=null)		relationshipNo = request.getParameter("relno").trim();
					NeoBankEnvironment.setComment(3, className," relationshipNo is " +relationshipNo);
					
					//TODO:- Should this rule be rethought?? The below database calls fetches the last 15 transactions whereby a user buys Porte Portfolio coins.
					//This is the only data that involves Stellar that is stored in our system. For now it should be kept this way or rethought?
					
					arrPorteMonthlyTransaction= (ArrayList<String>)CustomerDao.class.getConstructor().newInstance().getMonthlyTransactionForPorteCoins(relationshipNo);
					arrXLMMonthlyTransaction= (ArrayList<String>)CustomerDao.class.getConstructor().newInstance().getMonthlyTransactionForXLMCoins(relationshipNo);
					arrUSDCMonthlyTransaction= (ArrayList<String>)CustomerDao.class.getConstructor().newInstance().getMonthlyTransactionForUSDCCoins(relationshipNo);
					arrVesselMonthlyTransaction= (ArrayList<String>)CustomerDao.class.getConstructor().newInstance().getMonthlyTransactionForVesselCoins(relationshipNo);
						
					    obj.add("error", gson.toJsonTree("false"));
						obj.add("portetxns", gson.toJsonTree(arrPorteMonthlyTransaction));
						obj.add("xlmtxns", gson.toJsonTree(arrXLMMonthlyTransaction));
						obj.add("usdctxns", gson.toJsonTree(arrUSDCMonthlyTransaction));
						obj.add("vesltxns", gson.toJsonTree(arrVesselMonthlyTransaction));
						
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+"  String is " + gson.toJson(obj));
						out_json.print(gson.toJson(obj)); 
					} finally {
						if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						if (obj != null)obj = null; if(arrPorteMonthlyTransaction !=null) arrPorteMonthlyTransaction=null;
						if(arrXLMMonthlyTransaction !=null) arrXLMMonthlyTransaction=null; if(arrUSDCMonthlyTransaction !=null) arrUSDCMonthlyTransaction=null;
						if(arrVesselMonthlyTransaction !=null) arrVesselMonthlyTransaction=null;
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Failed to get Monthly transaction for graphs, Try again");

				}
				break;
						
			
				
			case "retrieval_private_key":
				try {
					char[] mnemonic = null; String relationshipNo = null;
					KeyPair keyPair = null;  long creationtime = 0;
					String password = null; String mnemoniStringFromUser = null; //input by the user
					String mnemoniStringFromDB = null; //From BC/DB 
					boolean passIsCorrect = false;
					boolean mnemonicMatches = false;
					JsonObject obj = new JsonObject();
					PrintWriter out_json = response.getWriter();
					User user = null;
					
					if(request.getParameter("password")!=null)		password = request.getParameter("password").trim();
					if(request.getParameter("mnemonic")!=null)		mnemoniStringFromUser = request.getParameter("mnemonic").trim();
					
					if (session.getAttribute("SESS_USER") == null) 
						throw new Exception ("Session has expired, please log in again");
					user = (User) session.getAttribute("SESS_USER");
					relationshipNo = user.getRelationshipNo();
					//
					passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
							.checkIfPasswordIsCorrect(relationshipNo, password);
					
					if(!passIsCorrect) {
						NeoBankEnvironment.setComment(1, className, "Password is not correct");
						Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
						return;
					}
					NeoBankEnvironment.setComment(3, className,"Step 1");
					mnemoniStringFromDB = (String) CustomerDao.class.getConstructor().newInstance()
							.getmnemonicCode(relationshipNo);
					//mnemoniStringFromUser = mnemoniStringFromUser.replaceAll(",", "");
					NeoBankEnvironment.setComment(3, className,"Step 2");
					//Compare mnemonic
					mnemonicMatches = Bip39Utility.compareMnemonic(mnemoniStringFromUser, mnemoniStringFromDB);
					
					if(!mnemonicMatches) {
						Utilities.sendJsonResponse(response, "error", "Mnemonic does not match");
						return;
					}
					//Get keys from bit 39
					keyPair= Bip39Utility.masterKeyGeneration(mnemoniStringFromDB.replaceAll(",", " "));
					if(keyPair==null) {
						throw new Exception("Error in getting keys");
					}
					NeoBankEnvironment.setComment(3, className,"Step 2");
					String masterKeyGenerationBTCResult = BitcoinUtility.bitcoinMasterKeyGenerationFromSeed(
							mnemoniStringFromDB.replaceAll(",", " "), creationtime);
					NeoBankEnvironment.setComment(3, className,"Step 3 "+masterKeyGenerationBTCResult);
					 //senderPrivateKey = masterKeyGenerationResult.split(",")[1];
					 //senderPublicKey = masterKeyGenerationResult.split(",")[2];
					 //senderBTCAddress = masterKeyGenerationResult.split(",")[0];
					//send response
					obj.addProperty("public_key", keyPair.getAccountId());
					obj.addProperty("error", "false");
					obj.addProperty("private_key", String.valueOf(keyPair.getSecretSeed()));
					obj.addProperty("btc_public_key", masterKeyGenerationBTCResult.split(",")[2]);
					obj.addProperty("btc_private_key",  masterKeyGenerationBTCResult.split(",")[1]);
					obj.addProperty("btc_address", masterKeyGenerationBTCResult.split(",")[0]);
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + (obj.toString()));
						out_json = response.getWriter();
						out_json.print((obj));
					} finally {
						if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; 
						if (mnemoniStringFromDB != null)mnemoniStringFromDB = null; if (obj != null) obj = null;
						if (password != null) password = null; if (mnemoniStringFromUser != null) mnemoniStringFromUser = null; 
						if (mnemonic != null) mnemonic = null; if (masterKeyGenerationBTCResult != null)masterKeyGenerationBTCResult = null;
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Failed to retreve keys, Try again");
				}
			
			break;
			
			case "retrieval_private_key_mbl":
				try {
					char[] mnemonic = null; String relationshipNo = null;String tokenValue = null;
					KeyPair keyPair = null;  long creationtime = 0;String langPref=null;
					String password = null; String mnemoniStringFromUser = null; //input by the user
					String mnemoniStringFromDB = null; //From BC/DB 
					boolean passIsCorrect = false;
					boolean mnemonicMatches = false;
					JsonObject obj = new JsonObject();
					PrintWriter out_json = response.getWriter();
					
					if(request.getParameter("password")!=null)		password = request.getParameter("password").trim();
					if(request.getParameter("mnemonic")!=null)		mnemoniStringFromUser = request.getParameter("mnemonic").trim();
					if(request.getParameter("relno")!=null)		relationshipNo = request.getParameter("relno").trim();
					if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
					if(request.getParameter("hdnlangpref")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlangpref"));
					//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
					if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v�lido, vuelva a iniciar sesi�n");

						}else {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
						}
						return;
					}
					
					//
					passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
							.checkIfPasswordIsCorrect(relationshipNo, password);
					
					if(!passIsCorrect) {
						NeoBankEnvironment.setComment(1, className, "Password is not correct");
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "error", "Por favor ingrese la contrase�a correcta");

						}else {
							Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
						}
						return;
					}
					mnemoniStringFromDB = (String) CustomerDao.class.getConstructor().newInstance()
							.getStellarMnemonicCode(relationshipNo);
					//mnemoniStringFromUser = mnemoniStringFromUser.replaceAll(",", "");
					
					//Compare mnemonic
					mnemonicMatches = Bip39Utility.compareMnemonic(mnemoniStringFromUser, mnemoniStringFromDB);
					
					if(!mnemonicMatches) {
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "error", "Mnem�nico no coincide");

						}else {
							Utilities.sendJsonResponse(response, "error", "Mnemonic does not match");
						}
						return;
					}
					//Get keys from bit 39
					keyPair= Bip39Utility.masterKeyGeneration(mnemoniStringFromDB.replaceAll(",", " "));
					if(keyPair==null) {
						throw new Exception("Error in getting keys");
					}
					String masterKeyGenerationBTCResult = BitcoinUtility.bitcoinMasterKeyGenerationFromSeed(
							mnemoniStringFromDB.replaceAll(",", " "), creationtime);
					//send response
					obj.addProperty("public_key", keyPair.getAccountId());
					obj.addProperty("error", "false");
					obj.addProperty("private_key", String.valueOf(keyPair.getSecretSeed()));
					obj.addProperty("btc_public_key", masterKeyGenerationBTCResult.split(",")[2]);
					obj.addProperty("btc_private_key",  masterKeyGenerationBTCResult.split(",")[1]);
					obj.addProperty("btc_address", masterKeyGenerationBTCResult.split(",")[0]);
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + (obj.toString()));
						out_json = response.getWriter();
						out_json.print((obj));
					} finally {
						if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; 
						if (mnemoniStringFromDB != null)mnemoniStringFromDB = null; if (obj != null) obj = null;
						if (password != null) password = null; if (mnemoniStringFromUser != null) mnemoniStringFromUser = null; 
						if (mnemonic != null) mnemonic = null; if (tokenValue != null)tokenValue = null;
						if (masterKeyGenerationBTCResult != null)masterKeyGenerationBTCResult = null;
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Failed to retreve keys, Try again");
				}
			
			break;
			case "custbuyplanwithtoken":
				try {
					PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();					
					
					String relationshipNo=null; String tokenId=null; String payAmount=null; String cvv=null; CardDetails m_CardDetails =null;
					String externalRefNo=null; boolean success =false;  String planId = null;
					String originalPlanId = "";
					
					if (session.getAttribute("SESS_USER") == null) {
						Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
						return;
					}
					
					String langPref=null;
					if(request.getParameter("hdnlang")!=null)			langPref = request.getParameter("hdnlang").trim();
					if(request.getParameter("hdnplanidbycard")!=null)		planId = request.getParameter("hdnplanidbycard").trim();
					if(request.getParameter("hdntokenidbycard")!=null)		tokenId = request.getParameter("hdntokenidbycard").trim();
					if(request.getParameter("tokenplanprice")!=null)		payAmount = request.getParameter("tokenplanprice").trim();
					if(request.getParameter("tokencvv2")!=null)	      		cvv = request.getParameter("tokencvv2").trim();
					if(request.getParameter("hdntokenrelno")!=null)	      	relationshipNo = request.getParameter("hdntokenrelno").trim();
					if(request.getParameter("hdncardoriginalplanid")!=null)	      	originalPlanId = request.getParameter("hdncardoriginalplanid").trim();
					 //  
					// TODO:- 1. Do wallet authorization eg. checking wallet limits
					// 2. Get the card details using the tokenid
					NeoBankEnvironment.setComment(3,className,"payAmount "+payAmount+"cvv "+cvv+"originalPlanId "+originalPlanId);
					
					if (NeoBankEnvironment.getBlockChainView().equals("true")) { // Will be enabled when calling the payment gateway 
							m_CardDetails = (CardDetails)CustomerTokenizationDao.class.getConstructor().newInstance().getCardDetailsbyTokenIdBlockchain(tokenId);
					}
					// -// TODO - 3. Here call the Payment Gateway interface and pass the card transactions
						// to the gateway
						/*
						  Send the card details to the payment gateway
						 * e.g. callPaymentGateway(cardNumber, DdateOfExpiry, cVV2, txnAmount, dateTime,
						 * anyOtherDetailsRequired) Step 3: Once the response is obtained from the
						 * payment gateway i.e. the authorization, then proceeed to storing the
						 * transaction details within the DB externalRefNo =
						 * callPaymentGateway(cardNumber, DdateOfExpiry, cVV2, txnAmount, dateTime,
						 * anyOtherDetailsRequired)\
						 */
						// currently getting a dummy external response.
						// Note: Consider the external response as system reference only
						
					 externalRefNo = RandomStringUtils.random(16, false, true); // External Sys REF NO
					 externalRefNo = NeoBankEnvironment.getCodeCustomerBuyPlanViaToken() + "-" + externalRefNo;

					 //4. Record the transaction and update the wallet
						success = (boolean) CustomerTokenizationDao.class.getConstructor().newInstance()
								.buyPricingPlanByToken(tokenId, payAmount, relationshipNo, externalRefNo, planId, originalPlanId);
					if (success) {
						
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, "C",
								NeoBankEnvironment.getCodeCustomerBuyPlanViaToken(), "Customer buy Plan Id "+planId+" via token" );// CBT - CUstomer Buy Plan Via token
						obj.add("error", gson.toJsonTree("false"));
						if (langPref.equals("EN")) {
							obj.add("message", gson.toJsonTree("You have successfully paid " + Utilities.getMoneyinDecimalFormat(payAmount) + 
									" to new plan. Reference Number " +externalRefNo)); 
							}else if ((langPref.equalsIgnoreCase("ES"))) {
								obj.add("message", gson.toJsonTree("Ha pagado con éxito " + Utilities.getMoneyinDecimalFormat(payAmount) +
										" al nuevo plan. Número de referencia " +externalRefNo)); 
							}
						
					}else {
						obj.add("error", gson.toJsonTree("true")); 
						 if (langPref.equals("EN")) {
							     obj.add("message", gson.toJsonTree("Failed to buy plan using card,try again later"));
							 }else if ((langPref.equalsIgnoreCase("ES"))) {
								obj.add("message", gson.toJsonTree("No se pudo comprar el plan con la tarjeta, intente nuevamente más tarde"));	
							}
					}
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						out_json.print(gson.toJson(obj));
					} finally {
						if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						if (obj != null)obj = null; if(m_CardDetails!=null) m_CardDetails = null; if (cvv!=null)cvv=null;
						if (langPref!=null)langPref=null;	
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Failed to send wallet money, Try again");

				}
				break;	
			case "custbuyplanwithtoken_mbl":
				try {
					PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();					
					
					String relationshipNo=null; String tokenId=null; String payAmount=null; String cvv=null; CardDetails m_CardDetails =null;
					String externalRefNo=null; boolean success =false;  String planId = null;
					String originalPlanId = ""; String tokenValue = null;String langPref=null;
					
					
					if(request.getParameter("hdnplanidbycard")!=null)		planId = request.getParameter("hdnplanidbycard").trim();
					if(request.getParameter("hdntokenidbycard")!=null)		tokenId = request.getParameter("hdntokenidbycard").trim();
					if(request.getParameter("tokenplanprice")!=null)		payAmount = request.getParameter("tokenplanprice").trim();
					if(request.getParameter("tokencvv2")!=null)	      		cvv = request.getParameter("tokencvv2").trim();
					if(request.getParameter("hdntokenrelno")!=null)	      	relationshipNo = request.getParameter("hdntokenrelno").trim();
					if(request.getParameter("hdncardoriginalplanid")!=null)	      	originalPlanId = request.getParameter("hdncardoriginalplanid").trim();
					if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
					if(request.getParameter("hdnlangpref")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlangpref"));
					//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
					if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v�lido, vuelva a iniciar sesi�n");

						}else {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
						}
						return;
					}
					 //  
					// TODO:- 1. Do wallet authorization eg. checking wallet limits
					// 2. Get the card details using the tokenid
					NeoBankEnvironment.setComment(3,className,"payAmount "+payAmount+"cvv "+cvv+"originalPlanId "+originalPlanId);
					
					if (NeoBankEnvironment.getBlockChainView().equals("true")) { // Will be enabled when calling the payment gateway 
						m_CardDetails = (CardDetails)CustomerTokenizationDao.class.getConstructor().newInstance().getCardDetailsbyTokenIdBlockchain(tokenId);
				}					
					// -// TODO - 3. Here call the Payment Gateway interface and pass the card transactions
						// to the gateway
						/*
						  Send the card details to the payment gateway
						 * e.g. callPaymentGateway(cardNumber, DdateOfExpiry, cVV2, txnAmount, dateTime,
						 * anyOtherDetailsRequired) Step 3: Once the response is obtained from the
						 * payment gateway i.e. the authorization, then proceeed to storing the
						 * transaction details within the DB externalRefNo =
						 * callPaymentGateway(cardNumber, DdateOfExpiry, cVV2, txnAmount, dateTime,
						 * anyOtherDetailsRequired)
						 */
						// currently getting a dummy external response.
						// Note: Consider the external response as system reference only
						
					 externalRefNo = RandomStringUtils.random(16, false, true); // External Sys REF NO
					 externalRefNo = NeoBankEnvironment.getTokenRegistrationCodeForCustomer() + "-" + externalRefNo;

					 //4. Record the transaction and update the wallet
						success = (boolean) CustomerTokenizationDao.class.getConstructor().newInstance()
								.buyPricingPlanByToken(tokenId, payAmount, relationshipNo, externalRefNo, planId, originalPlanId);
					if (success) {
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, "C",
								NeoBankEnvironment.getCodeCustomerBuyPlanViaToken(), "Customer buy Plan Id "+planId+" via token" );// CBT - CUstomer Buy Plan Via token
						obj.add("error", gson.toJsonTree("false"));
						if(langPref.equalsIgnoreCase("ES")) {
							obj.add("msg", gson.toJsonTree("Has pagado correctamente" + Utilities.getMoneyinDecimalFormat(payAmount) + 
									" al nuevo plano. N�mero de referencia " +externalRefNo)); 
						}else {
							obj.add("msg", gson.toJsonTree("You have succesfully paid " + Utilities.getMoneyinDecimalFormat(payAmount) + 
									" to new plan. Reference Number " +externalRefNo)); 						
							}
					}else {
						obj.add("error", gson.toJsonTree("true")); 
						if(langPref.equalsIgnoreCase("ES")) {
							obj.add("msg", gson.toJsonTree("Transacci�n fallida")); 

						}else {
							obj.add("msg", gson.toJsonTree("Transaction failed")); 
						}
					}
					try {
						NeoBankEnvironment.setComment(3, className," editcustdetails String is " + gson.toJson(obj));
						out_json.print(gson.toJson(obj));
					} finally {
						if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						 if (langPref != null)langPref = null;if (obj != null)obj = null; if(m_CardDetails!=null) m_CardDetails = null;
						 if (cvv!=null)cvv=null; if (tokenValue != null)tokenValue = null;
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Failed to send wallet money, Try again");

				}
				break;	
				
			case "custbuyplanwithwallet":
				try {
					PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();	
					boolean success = false;	String relationshipNo = null; String walletId = null; String amount = null; String payComments = null;
					String referenceNo = null; String txnUserCode = null; 	String planPrice = null; String planDuration = null; 
					String authStatus=""; String authMessage=""; String authResponse=null;
					boolean recordAuthorization=false; String currencyId = null; String transactionCode=null;
					String txnMode=null; String userType="C";
					String planId = null; String originalPlanId = "";
					if (session.getAttribute("SESS_USER") == null) {
						Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
						return;
					}
					String langPref=null;
					if(request.getParameter("hdnlang")!=null)			langPref = request.getParameter("hdnlang").trim();
					if(request.getParameter("hdnplanidbywallet")!=null)		planId = request.getParameter("hdnplanidbywallet").trim();
					if(request.getParameter("walletplanprice")!=null)		planPrice = request.getParameter("walletplanprice").trim();
					if(request.getParameter("walletplanduration")!=null)	planDuration = request.getParameter("walletplanduration").trim();
					if(request.getParameter("walletplanprice")!=null)		amount = request.getParameter("walletplanprice").trim();
					if(request.getParameter("paywalletcomments")!=null)		payComments = request.getParameter("paywalletcomments").trim();
					if(request.getParameter("paywalletid")!=null)			walletId = request.getParameter("paywalletid").trim();
					if(request.getParameter("hdnwalletrelno")!=null)		relationshipNo = request.getParameter("hdnwalletrelno").trim();
					if(request.getParameter("hdnwalletoriginalplanid")!=null)		originalPlanId = request.getParameter("hdnwalletoriginalplanid").trim();
					txnUserCode = Utilities.generateTransactionCode(10);
					
					/* 
					 * We are NOT CHARGING Transaction price here
					 */
					
					txnMode="D";// Debit
					currencyId = NeoBankEnvironment.getUSDCurrencyId();
					SimpleDateFormat formatter1 = new SimpleDateFormat("yyMMddHHmmssSSS");
					transactionCode = formatter1.format(new java.util.Date()) + Utilities.genAlphaNumRandom(9);
					referenceNo = NeoBankEnvironment.getCodeCustomerBuyPlanViaWallet()+ "-" +transactionCode;
					
					/****** Wallet Authorization******/
					
					authResponse=(String)WalletAuthorizationDao.class.getConstructor().newInstance().performWalletAuthorization(relationshipNo, amount, txnMode);
					if (authResponse.isEmpty()) {
						Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
						return;
					}
					authStatus=authResponse.substring(0,authResponse.indexOf(","));
					authMessage=authResponse.substring(authResponse.indexOf(",")+1,authResponse.indexOf("|"));
					walletId=authResponse.substring(authResponse.indexOf("|")+1,authResponse.length());
			
					if (authStatus.equals("S")==false) {
						//Authorization failed
						// Record failed authorization
						
						recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, amount, txnMode, currencyId, 
								userType, referenceNo, walletId, authStatus, authMessage);
						if(!recordAuthorization) { 
							 Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again");
						return;
						}
						     Utilities.sendJsonResponse(response, "authfailed", authMessage);
						return;
					}else {
						// Record successful authorization
						recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, amount, txnMode, currencyId, 
								userType, referenceNo, walletId, authStatus, authMessage);
						if(!recordAuthorization) { 
							 Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again");
						return;
						}
					}
					/****** End of Wallet Authorization******/	
					
					success = (boolean) CustomerFiatWalletDao.class.getConstructor().newInstance().payPlanWithWallet(relationshipNo, walletId,  amount, payComments, 
							 referenceNo, txnUserCode, planId, originalPlanId,transactionCode);
					
					if (success) {
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, "C",
								NeoBankEnvironment.getCodeCustomerBuyPlanViaWallet(), "Customer buy Plan Id "+planId+" via wallet" );// CBP - CUstomer Buy Plan Via wallet
						
						obj.add("error", gson.toJsonTree("false"));
						 if (langPref.equals("EN")) {
							    obj.add("message", gson.toJsonTree("You have succesfully paid " + Utilities.getMoneyinDecimalFormat(amount) + 
										" to new plan. Reference Number " +txnUserCode)); 
							}else if ((langPref.equalsIgnoreCase("ES"))) {
								obj.add("message", gson.toJsonTree("Ha pagado correctamente " + Utilities.getMoneyinDecimalFormat(amount) +
										" al nuevo plan. Número de referencia " +txnUserCode));
							}
					}else {
						obj.add("error", gson.toJsonTree("true")); 
						 if (langPref.equals("EN")) {
						     obj.add("message", gson.toJsonTree("Failed to buy plan using your wallet,try again later"));
						 }else if ((langPref.equalsIgnoreCase("ES"))) {
							obj.add("message", gson.toJsonTree("No se pudo comprar el plan usando tu billetera, inténtalo de nuevo más tarde"));	
						} 
					}
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						out_json = response.getWriter();
						out_json.print(gson.toJson(obj));
					} finally {
						if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						if (obj != null) obj = null;
						if (walletId != null) walletId = null; if (payComments != null) payComments = null; 
						if (amount != null) amount = null; if (planDuration != null) planDuration = null; if (planPrice != null) planPrice = null; if (planId != null) planId = null;
						if (currencyId!=null); currencyId=null;  if (authResponse != null)authResponse = null;
						if (transactionCode!=null); transactionCode=null;   if (referenceNo!=null); referenceNo=null;
						if (txnMode!=null); txnMode=null;  if (userType!=null); userType=null; if (langPref!=null)langPref=null;	
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Failed to buy plans, Try again");
				}
				break;	
				
			case "custbuyplanwithwallet_mbl":
				try {
					PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();	
					boolean success = false;	String relationshipNo = null; String walletId = null; String amount = null; String payComments = null;
					String referenceNo = null; String txnUserCode = null; 	String planPrice = null; String planDuration = null;
					String planId = null; String originalPlanId = "";	String tokenValue = null;		
					String authStatus=""; String authMessage=""; String authResponse=null;String langPref=null;
					boolean recordAuthorization=false; String currencyId = null; String transactionCode=null;
					String txnMode=null; String userType="C";
					
					if(request.getParameter("hdnplanidbywallet")!=null)		planId = request.getParameter("hdnplanidbywallet").trim();
					if(request.getParameter("walletplanprice")!=null)		planPrice = request.getParameter("walletplanprice").trim();
					if(request.getParameter("walletplanduration")!=null)	planDuration = request.getParameter("walletplanduration").trim();
					if(request.getParameter("walletplanprice")!=null)		amount = request.getParameter("walletplanprice").trim();
					if(request.getParameter("paywalletcomments")!=null)		payComments = request.getParameter("paywalletcomments").trim();
					if(request.getParameter("paywalletid")!=null)			walletId = request.getParameter("paywalletid").trim();
					if(request.getParameter("hdnwalletrelno")!=null)		relationshipNo = request.getParameter("hdnwalletrelno").trim();
					if(request.getParameter("hdnwalletoriginalplanid")!=null)		originalPlanId = request.getParameter("hdnwalletoriginalplanid").trim();
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
					txnMode="D";// Debit
					currencyId = NeoBankEnvironment.getUSDCurrencyId();
					SimpleDateFormat formatter1 = new SimpleDateFormat("yyMMddHHmmssSSS");
					transactionCode = formatter1.format(new java.util.Date()) + Utilities.genAlphaNumRandom(9);
					referenceNo = NeoBankEnvironment.getCodeCustomerBuyPlanViaWallet()+ "-" +transactionCode;
					NeoBankEnvironment.setComment(3,className,"transactionCode is"+transactionCode);
					txnUserCode = Utilities.generateTransactionCode(10);
					/* 
					 * We are NOT CHARGING Transaction price here
					 */
					
						/****** Wallet Authorization******/
					
							authResponse=(String)WalletAuthorizationDao.class.getConstructor().newInstance().performWalletAuthorization(relationshipNo, amount, txnMode);
							if (authResponse.isEmpty()) {
								if(langPref.equalsIgnoreCase("ES")) {
									Utilities.sendJsonResponse(response, "authfailed", "Error en la autorizaci�n, int�ntalo de nuevo m�s tarde");

								}else {
									Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
								}
								return;
							}
							authStatus=authResponse.substring(0,authResponse.indexOf(","));
							authMessage=authResponse.substring(authResponse.indexOf(",")+1,authResponse.indexOf("|"));
							walletId=authResponse.substring(authResponse.indexOf("|")+1,authResponse.length());
					
							if (authStatus.equals("S")==false) {
								//Authorization failed
								// Record failed authorization
								
								recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, amount, txnMode, currencyId, 
										userType, referenceNo, walletId, authStatus, authMessage);
								if(!recordAuthorization) { 
									if(langPref.equalsIgnoreCase("ES")) {
										Utilities.sendJsonResponse(response, "authfailed", "Error en la autorizaci�n, int�ntalo de nuevo m�s tarde");

									}else {
										Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
									}
								return;
								}
								     Utilities.sendJsonResponse(response, "authfailed", authMessage);
								return;
							}else {
								// Record successful authorization
								recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, amount, txnMode, currencyId, 
										userType, referenceNo, walletId, authStatus, authMessage);
								if(!recordAuthorization) { 
									if(langPref.equalsIgnoreCase("ES")) {
										Utilities.sendJsonResponse(response, "authfailed", "Error en la autorizaci�n, int�ntalo de nuevo m�s tarde");

									}else {
										Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
									}
								return;
								}
							}
					/****** End of Wallet Authorization******/	
					
					success = (boolean) CustomerFiatWalletDao.class.getConstructor().newInstance().payPlanWithWallet(relationshipNo, walletId,  amount, payComments, 
							 referenceNo, txnUserCode, planId, originalPlanId,transactionCode);
					
					if (success) {
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, "C",
								NeoBankEnvironment.getCodeCustomerBuyPlanViaWallet(), "Customer buy Plan Id "+planId+" via wallet" );// CBP - CUstomer Buy Plan Via wallet
						
						obj.add("error", gson.toJsonTree("false"));
						if(langPref.equalsIgnoreCase("ES")) {
							obj.add("message", gson.toJsonTree("Has pagado correctamente" + Utilities.getMoneyinDecimalFormat(amount) + 
									" al nuevo plano. N�mero de referencia " +txnUserCode)); 
						}else {
							obj.add("message", gson.toJsonTree("You have succesfully paid " + Utilities.getMoneyinDecimalFormat(amount) + 
									" to new plan. Reference Number " +txnUserCode)); 						}
						
					}else {
						obj.add("error", gson.toJsonTree("true")); 
						if(langPref.equalsIgnoreCase("ES")) {
							obj.add("message", gson.toJsonTree("Transacci�n fallida")); 

						}else {
							obj.add("message", gson.toJsonTree("Transaction failed")); 
						}
					}
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						out_json = response.getWriter();
						out_json.print(gson.toJson(obj));
					} finally {
						if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						if (obj != null) obj = null; if (tokenValue != null)tokenValue = null;
						if (walletId != null) walletId = null; if (payComments != null) payComments = null; 
						if (amount != null) amount = null; if (planDuration != null) planDuration = null; if (planPrice != null) planPrice = null; if (planId != null) planId = null;
						if (currencyId!=null); currencyId=null;  if (authResponse != null)authResponse = null;
						if (transactionCode!=null); transactionCode=null;   if (referenceNo!=null); referenceNo=null;
						if (txnMode!=null); txnMode=null;  if (userType!=null); userType=null; 
						if (langPref!=null); langPref=null;
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Failed to buy plans, Try again");

				}
				break;				
				
			case "checkifuserexists":
				try {
					PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
					String email=null; boolean emailExist = false;
					
					if(request.getParameter("email")!=null) email = request.getParameter("email").trim();
					
					emailExist= (boolean)CustomerDao.class.getConstructor().newInstance().checkIfEmailExist(email);
					
					if (emailExist) {
						obj.add("error", gson.toJsonTree("userexist"));
					}else {
						obj.add("error", gson.toJsonTree("doesnotexist")); 
					}
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+ "String is " + gson.toJson(obj));
						out_json.print(gson.toJson(obj));
					} finally {
						if (out_json != null)out_json.close(); if (email != null)email = null; if (gson != null)gson = null;
						if (obj != null)obj = null;
					}
					
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Failed to check if user exists, Try again");
				}
			break;
	
			case "check_if_customer_has_mnemonic_code":
				try {
					String relationshipNo = null;
					String hasMnemonicCode = null;
					JsonObject obj = new JsonObject();
					PrintWriter out_json = response.getWriter();
					User user = null;					
					if (session.getAttribute("SESS_USER") == null) 
						throw new Exception ("Session has expired, please log in again");
					user = (User) session.getAttribute("SESS_USER");
					relationshipNo = user.getRelationshipNo();
					
					hasMnemonicCode = (String) CustomerDao.class.getConstructor().newInstance()
							.checkIfUserHasMnemonicCode(relationshipNo);
					
					if(hasMnemonicCode.equalsIgnoreCase("Y")) {
						obj.addProperty("error", "false");
						obj.addProperty("hasmnemonic", "true");
					}else {
						obj.addProperty("error", "false");
						obj.addProperty("hasmnemonic", "false");
					}
					
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + (obj.toString()));
						out_json = response.getWriter();
						out_json.print((obj));
					} finally {
						if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; 
						if (hasMnemonicCode != null)hasMnemonicCode = null; if (obj != null) obj = null;
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Check failed , Try again");
				}
				break;
				
			case "check_if_customer_has_mnemonic_code_mbl":
				try {
					String relationshipNo = null; String fingrePrintFlag = null;
					String hasMnemonicCode = null; String tokenValue = null;
					JsonObject obj = new JsonObject();String langPref=null;
					PrintWriter out_json = response.getWriter();
					if(request.getParameter("relno")!=null)		relationshipNo = request.getParameter("relno").trim();
					if(request.getParameter("token")!=null)	    tokenValue = StringUtils.trim(request.getParameter("token"));
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
					
					hasMnemonicCode = (String) CustomerDao.class.getConstructor().newInstance()
							.checkIfUserHasMnemonicCode(relationshipNo);
					
					if(hasMnemonicCode.equalsIgnoreCase("Y")) {
						fingrePrintFlag = (String) CustomerDao.class.getConstructor().newInstance()
								.checkIfUserHasEnabledFingrePrint(relationshipNo);
						obj.addProperty("error", "false");
						obj.addProperty("hasmnemonic", "true");
						obj.addProperty("fingre_print", fingrePrintFlag);
					}else {
						obj.addProperty("error", "false");
						obj.addProperty("hasmnemonic", "false");
					}
					
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + (obj.toString()));
						out_json = response.getWriter();
						out_json.print((obj));
					} finally {
						if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; 
						if (fingrePrintFlag != null)fingrePrintFlag = null;if (tokenValue != null)tokenValue = null;
						if (hasMnemonicCode != null)hasMnemonicCode = null; if (obj != null) obj = null; 
						 if (langPref != null) langPref = null;
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Check failed , Try again");
				}
				break;
			 case "getallwalletdetails_mbl":
				  try {
					
					String tokenValue = null;String langPref=null;
					String relationshipNo=null;  Wallet  m_Wallet= null; String userType=null;
					PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject(); 
					List <AssetTransaction> lastFiveTransations=null;
					if(request.getParameter("relno")!=null)				relationshipNo = request.getParameter("relno").trim();
					userType="C";
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
					
					m_Wallet= (Wallet)CustomerWalletDao.class.getConstructor().newInstance().getCustomerWalletDetails(relationshipNo,userType);
					// Get last five transaction
					lastFiveTransations= (List<AssetTransaction>)CustomerWalletDao.class.getConstructor().newInstance().getFiatWalletLastTenTxn(relationshipNo);
					
					if (m_Wallet!=null) {
						obj.add("error", gson.toJsonTree("false"));
						obj.add("data", gson.toJsonTree(m_Wallet));
						obj.add("transactions", gson.toJsonTree(lastFiveTransations));
					}else {
						obj.add("error", gson.toJsonTree("true"));
						obj.add("message", gson.toJsonTree("nodata"));
					}
					
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						out_json.print(gson.toJson(obj));
					} finally {
						if (tokenValue != null)tokenValue = null;
						if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null; 
						if (lastFiveTransations!=null)lastFiveTransations=null;
						if (userType != null)userType = null; if (obj != null)obj = null; if(m_Wallet!=null) m_Wallet=null;
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				}
				break;
				
			   case "check_password":
				  try {
					  String password=null;String relationshipNo = null; JsonObject obj = new JsonObject(); 
					  Gson gson = new Gson(); PrintWriter out_json = response.getWriter();
					  if(request.getParameter("security")!=null)password = request.getParameter("security").trim();
					  if(request.getParameter("relno")!=null)relationshipNo = request.getParameter("relno").trim();
					  boolean passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance().
							  checkIfPasswordIsCorrect(relationshipNo, password);
					  try {
						obj.add("error", gson.toJsonTree("false"));
						obj.add("passIsCorrect", gson.toJsonTree(passIsCorrect));
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
						out_json.print(gson.toJson(obj));
					  }finally {
						  if (password != null)password = null; if (relationshipNo != null)relationshipNo = null;
						  if (obj != null)obj = null; if (gson != null)gson = null;
					}
				  }catch (Exception e) {
					    NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				  }
			 break;
			

		}
	}

	

}
