package com.pporte.rules;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.CustomerTokenizationDao;
import com.pporte.dao.CustomerWalletDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.dao.WalletAuthorizationDao;
import com.pporte.model.AssetTransaction;
import com.pporte.model.CardDetails;
import com.pporte.model.Wallet;
import com.pporte.utilities.Utilities;


import framework.v8.Rules;

public class CustomerTokenizationRulesImpl implements Rules{
	private static String className = CustomerTokenizationRulesImpl.class.getSimpleName();
	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response, ServletContext ctx)
			throws Exception {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null) Utilities.callException(request, response, ctx, "Session has expired, please log in again");
		switch (rulesaction) {
			case "Register Card":
				try {
					String langPref=null;
					if(request.getParameter("hdnlang")!=null)			langPref = request.getParameter("hdnlang").trim();
					request.setAttribute("lastaction", "card");
					request.setAttribute("lastrule", "Show Cards");
					request.setAttribute("langPref", langPref);
					response.setContentType("text/html");
					try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerRegisterCardPage()).forward(request, response);
					} finally {
						if (langPref!=null)langPref=null;
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
					Utilities.callException(request, response, ctx, e.getMessage());
				}
				break;
			case "Show Cards":
				try {
					String langPref=null;
					if(request.getParameter("hdnlang")!=null)			langPref = request.getParameter("hdnlang").trim();
					request.setAttribute("lastaction", "card");
					request.setAttribute("lastrule", "Show Cards");
					request.setAttribute("langPref", langPref);
					response.setContentType("text/html");
					try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerShowAllCards()).forward(request, response);
					} finally {
						if (langPref!=null)langPref=null;
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
			ServletContext ctx)throws Exception{
		
		switch (rulesaction) {
			
					case "regnewcard":
						try {
							PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
							String cardNumber=null; String fullName=null; String expiry=null; String cardAlias=null; String relationshipNo=null;
							String cvv=null; String tokenId; boolean success=false; String userType=null; String moduleCode=null;
							String TRANSACTION_AMOUNT_FOR_REGISTRATION =null;String flag="EXT";// External card tokenization 
							String externalRefNo = null;
							if(request.getParameter("number")!=null)			cardNumber = request.getParameter("number").trim();
							if(request.getParameter("name")!=null)			fullName = request.getParameter("name").trim();
							if(request.getParameter("expiry")!=null)			expiry = request.getParameter("expiry").trim();
							if(request.getParameter("cvc")!=null)			    cvv = request.getParameter("cvc").trim();
							if(request.getParameter("cardalias")!=null)			cardAlias = request.getParameter("cardalias").trim();
							if(request.getParameter("relno")!=null)			    relationshipNo = request.getParameter("relno").trim();
							
							HttpSession session = request.getSession(false); 
							if (session.getAttribute("SESS_USER") == null) {
								Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
								return;
							}
							
							cardNumber=cardNumber.replaceAll(" ", ""); //Removing space from the card number
							tokenId = Utilities.generateToken(cardNumber,flag);
							
							// Check if card number is valid
							
							boolean valid = Utilities.isValidCreditCardNumber(cardNumber);
							if (!valid) {
								Utilities.sendJsonResponse(response, "error", "Card Number is Invalid");
							}
							userType="C";
							moduleCode=NeoBankEnvironment.getTokenRegistrationCodeForCustomer();
							
							// Top up wallet with card to confirm if the card details is valid.
							
							// TODO Call the Payment Gateway interface and pass the card parameters with an
							// amount of $1. get the response code from the Payment gateway and pass
							// it to the Dao as response/reference code
							
							// TODO - Here call the Payment Gateway interface and pass the card transactions
							// to the gateway
							/*
							 * 
							 * Step 1: get the card details - card number in clear text, date of expiry and
							 * cvv2 (customer input) Step 2 - Send the card details to the payment gateway
							 * e.g. callPaymentGateway(cardNumber, DdateOfExpiry, cVV2, txnAmount, dateTime,
							 * anyOtherDetailsRequired) Step 3: Once the response is obtained from the
							 * payment gateway i.e. the authorization, then proceeed to storing the
							 * transaction details within the DB externalRefNo =
							 * callPaymentGateway(cardNumber, DdateOfExpiry, cVV2, txnAmount, dateTime,
							 * anyOtherDetailsRequired)
							 */
							// currently getting a dummy external response.
							// Note: Consider the external response as system reference only
							// Note: CVV2 is not stored in the system
							
							externalRefNo = RandomStringUtils.random(16, false, true); // External Sys REF NO
							TRANSACTION_AMOUNT_FOR_REGISTRATION = NeoBankEnvironment.getAmountForTokenRegistration();
							success = (boolean) CustomerTokenizationDao.class.getConstructor().newInstance().tokenizeExternalCards(userType, fullName, cardNumber, expiry, relationshipNo, externalRefNo,TRANSACTION_AMOUNT_FOR_REGISTRATION, tokenId, cardAlias);
							if (success) {
								obj.add("error", gson.toJsonTree("false"));
								SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, userType,
										moduleCode, StringUtils.substring(" New Card Registration " + externalRefNo, 0, 48));
								
							} else {
								obj.add("message", gson.toJsonTree("Card Registration failed.... Try again Later"));
								obj.add("error", gson.toJsonTree("true"));
							}
							
							try {
								NeoBankEnvironment.setComment(3, className," regnewcard String is " + gson.toJson(obj));
								out_json.print(gson.toJson(obj));
							} finally {
								if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
								if (userType != null)userType = null; if (cvv!=null); cvv=null;  if (expiry!=null); expiry=null;  if (cardAlias!=null); cardAlias=null;
								if (TRANSACTION_AMOUNT_FOR_REGISTRATION != null)TRANSACTION_AMOUNT_FOR_REGISTRATION = null; if (moduleCode!=null); moduleCode=null;
								 if (tokenId!=null); tokenId=null; if (flag!=null); flag=null; if (externalRefNo!=null)externalRefNo=null;if (obj != null)obj = null;
							   
							}
							
						}catch (Exception e){
							NeoBankEnvironment.setComment(1, className, " Overall Exception for case regnewcard is "+e.getMessage());
							Utilities.sendJsonResponse(response, "error", "Error in registering new card");
						}
					break;
					case "regnewcard_mbl":
						try {
							PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
							String cardNumber=null; String fullName=null; String expiry=null; String cardAlias=null; String relationshipNo=null;
							String cvv=null; String tokenId; boolean success=false; String userType=null; String moduleCode=null;
							String TRANSACTION_AMOUNT_FOR_REGISTRATION =null;String flag="EXT";// External card tokenization 
							String externalRefNo = null; String tokenValue = null;String langPref=null;
							List<CardDetails> cardLists =null;
							if(request.getParameter("number")!=null)			cardNumber = request.getParameter("number").trim();
							if(request.getParameter("name")!=null)			fullName = request.getParameter("name").trim();
							if(request.getParameter("expiry")!=null)			expiry = request.getParameter("expiry").trim();
							if(request.getParameter("cvc")!=null)			    cvv = request.getParameter("cvc").trim();
							if(request.getParameter("cardalias")!=null)			cardAlias = request.getParameter("cardalias").trim();
							if(request.getParameter("relno")!=null)			    relationshipNo = request.getParameter("relno").trim();
							if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
							if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

							//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
							if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
								NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
								if(langPref.equalsIgnoreCase("ES")) {
									Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es válido, vuelva a iniciar sesión");

								}else {
									Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
								}
								return;
							}
							
							cardNumber=cardNumber.replaceAll(" ", ""); //Removing space from the card number
							tokenId = Utilities.generateToken(cardNumber,flag);
							
							// Check if card number is valid
							
							boolean valid = Utilities.isValidCreditCardNumber(cardNumber);
							if (!valid) {
								if(langPref.equalsIgnoreCase("ES")) {
									Utilities.sendJsonResponse(response, "error", "El número de tarjeta no es válido");

								}else {
									Utilities.sendJsonResponse(response, "error", "Card Number is Invalid");
								}
							}
							userType="C";
							moduleCode=NeoBankEnvironment.getTokenRegistrationCodeForCustomer();
							
							// Top up wallet with card to confirm if the card details is valid.
							
							// TODO Call the Payment Gateway interface and pass the card parameters with an
							// amount of $1. get the response code from the Payment gateway and pass
							// it to the Dao as response/reference code
							
							// TODO - Here call the Payment Gateway interface and pass the card transactions
							// to the gateway
							/*
							 * 
							 * Step 1: get the card details - card number in clear text, date of expiry and
							 * cvv2 (customer input) Step 2 - Send the card details to the payment gateway
							 * e.g. callPaymentGateway(cardNumber, DdateOfExpiry, cVV2, txnAmount, dateTime,
							 * anyOtherDetailsRequired) Step 3: Once the response is obtained from the
							 * payment gateway i.e. the authorization, then proceeed to storing the
							 * transaction details within the DB externalRefNo =
							 * callPaymentGateway(cardNumber, DdateOfExpiry, cVV2, txnAmount, dateTime,
							 * anyOtherDetailsRequired)
							 */
							// currently getting a dummy external response.
							// Note: Consider the external response as system reference only
							// Note: CVV2 is not stored in the system
							
							externalRefNo = RandomStringUtils.random(16, false, true); // External Sys REF NO
							TRANSACTION_AMOUNT_FOR_REGISTRATION = NeoBankEnvironment.getAmountForTokenRegistration();
							success = (boolean) CustomerTokenizationDao.class.getConstructor().newInstance().tokenizeExternalCards(userType, fullName, cardNumber, expiry, relationshipNo, externalRefNo,TRANSACTION_AMOUNT_FOR_REGISTRATION, tokenId, cardAlias);
							if (success) {
								
								SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, userType,
										moduleCode, StringUtils.substring(" New Card Registration " + externalRefNo, 0, 48));
								cardLists= (List<CardDetails>)CustomerTokenizationDao.class.getConstructor().newInstance().getTokenizedCards(relationshipNo);
								obj.add("error", gson.toJsonTree("false"));
								obj.add("data",gson.toJsonTree(cardLists));
							} else {
								if(langPref.equalsIgnoreCase("ES")) {
									obj.add("message", gson.toJsonTree("Falló el registro de la tarjeta... Vuelva a intentarlo más tarde"));

								}else {
									obj.add("message", gson.toJsonTree("Card Registration failed.... Try again Later"));
								}
								obj.add("error", gson.toJsonTree("true"));
							}
							
							try {
								NeoBankEnvironment.setComment(3, className," regnewcard String is " + gson.toJson(obj));
								out_json.print(gson.toJson(obj));
							} finally {
								if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
								if (userType != null)userType = null; if (cvv!=null); cvv=null;  if (expiry!=null); expiry=null;  if (cardAlias!=null); cardAlias=null;
								if (TRANSACTION_AMOUNT_FOR_REGISTRATION != null)TRANSACTION_AMOUNT_FOR_REGISTRATION = null; if (moduleCode!=null); moduleCode=null;
								 if (tokenId!=null); tokenId=null; if (flag!=null); flag=null; if (externalRefNo!=null)externalRefNo=null;if (obj != null)obj = null;
								 if (tokenValue != null)tokenValue = null; if (cardLists!=null)cardLists=null;
							}
							
						}catch (Exception e){
							NeoBankEnvironment.setComment(1, className, " Overall Exception for case regnewcard is "+e.getMessage());
							Utilities.sendJsonResponse(response, "error", "Error in registering new card");
						}
					break;
		
					case "gettokenizedcards":
						try {
							PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
							String relationshipNo=null; ArrayList<CardDetails> cardLists =null;
							
							HttpSession session = request.getSession(false); 
							if (session.getAttribute("SESS_USER") == null) {
								Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
								return;
							}
							
							if(request.getParameter("relno")!=null)			relationshipNo = request.getParameter("relno").trim();
							
							cardLists= (ArrayList<CardDetails>)CustomerTokenizationDao.class.getConstructor().newInstance().getTokenizedCards(relationshipNo);
							
						    if (cardLists!=null) {
						    	obj.add("error",gson.toJsonTree("false"));
						    	obj.add("data",gson.toJsonTree(cardLists));
						    	
						    }else {
						    	obj.add("error",gson.toJsonTree("nodata"));
						    	obj.add("message",gson.toJsonTree("No tokenized card"));
						    }
						    
						    try {
						    	NeoBankEnvironment.setComment(3, className," gettokenizedcards String is " + gson.toJson(obj));
								out_json.print(gson.toJson(obj));
						    }finally {
						    	if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						    	if (obj != null)obj = null;if (cardLists!=null)cardLists=null;
						    }
						}catch (Exception e) {
							NeoBankEnvironment.setComment(1, className, " Overall Exception for case regnewcard is "+e.getMessage());
						}
						
					break;
					
					case "gettokenizedcards_mbl":
						try {
							PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
							String relationshipNo=null; List<CardDetails> cardLists =new ArrayList<CardDetails>();String langPref=null;

							String tokenValue = null;
							if(request.getParameter("relno")!=null)			relationshipNo = request.getParameter("relno").trim();
							if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
							if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

							//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
							if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
								NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
								if(langPref.equalsIgnoreCase("ES")) {
									Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es válido, vuelva a iniciar sesión");

								}else {
									Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
								}
							}
							
							cardLists= (List<CardDetails>)CustomerTokenizationDao.class.getConstructor().newInstance().getTokenizedCards(relationshipNo);
							
						    if (cardLists!=null) {
						    	obj.add("error",gson.toJsonTree("false"));
						    	obj.add("data",gson.toJsonTree(cardLists));
						    	
						    }else {
						    	obj.add("error",gson.toJsonTree("true"));
						    }
						    
						    try {
						    	NeoBankEnvironment.setComment(3, className," gettokenizedcards_mbl String is " + gson.toJson(obj));
								out_json.print(gson.toJson(obj));
						    }finally {
						    	if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
						    	if (obj != null)obj = null; if (tokenValue != null)tokenValue = null;
						    	if (cardLists!=null)cardLists=null;
						    }
						}catch (Exception e) {
							NeoBankEnvironment.setComment(1, className, " Overall Exception for case gettokenizedcards_mbl is "+e.getMessage());
						}
						
					break;
					
					case "topupbycard":
						try {
							PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
							String relationshipNo=null; String tokenId=null; String topupAmount=null; String cvv=null; CardDetails m_CardDetails =null;
							String externalRefNo=null; boolean success =false; String userType=null; String moduleCode=null; String txnMode=null;
							String authStatus=""; String authMessage=""; String walletId=null; String authResponse=null;
							boolean recordAuthorization=false; String currencyId = null; String transactionCode=null;
							String internalSystemReference=null;String langPref=null;
							
							if(request.getParameter("relno")!=null)			relationshipNo = request.getParameter("relno").trim();
							if(request.getParameter("tokenid")!=null)		tokenId = request.getParameter("tokenid").trim();
							if(request.getParameter("amount")!=null)		topupAmount = request.getParameter("amount").trim();
							if(request.getParameter("cvv")!=null)	      	cvv = request.getParameter("cvv").trim();
							if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
							
							NeoBankEnvironment.setComment(2,className,"Token id"+tokenId);
							
							HttpSession session = request.getSession(false); 
							if (session.getAttribute("SESS_USER") == null) {
								Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
								return;
							}
					
							
							
							
							userType="C";
							moduleCode=NeoBankEnvironment.getCodeTokenWalletTopup();
							txnMode="C";// Credit
							currencyId = NeoBankEnvironment.getUSDCurrencyId();
							SimpleDateFormat formatter1 = new SimpleDateFormat("yyMMddHHmmssSSS");
							transactionCode = formatter1.format(new java.util.Date()) + Utilities.genAlphaNumRandom(9);
							internalSystemReference=NeoBankEnvironment.getCodeTokenWalletTopup()+"-"+transactionCode;
						      // External Sys REF NO
							externalRefNo = NeoBankEnvironment.getTokenRegistrationCodeForCustomer() + "-" +  RandomStringUtils.random(16, false, true);
 							 //  
							// TODO:- 1. Do wallet authorization eg. checking wallet limits
							/****** Wallet Authorization******/
							
							authResponse=(String)WalletAuthorizationDao.class.getConstructor().newInstance().performWalletAuthorization(relationshipNo, topupAmount, txnMode);
							if (authResponse.isEmpty()) {
								if(langPref.equalsIgnoreCase("en")) {
									Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
									return;
								}else {
									Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo más tarde");
									return;
								}
								
							}
							authStatus=authResponse.substring(0,authResponse.indexOf(","));
							authMessage=authResponse.substring(authResponse.indexOf(",")+1,authResponse.indexOf("|"));
							walletId=authResponse.substring(authResponse.indexOf("|")+1,authResponse.length());
					
							if (authStatus.equals("S")==false) {
								//Authorization failed
								// Record failed authorization
								
								recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, topupAmount, txnMode, currencyId, 
										userType, internalSystemReference, walletId, authStatus, authMessage);
								if(!recordAuthorization) { 
									if(langPref.equalsIgnoreCase("en")) {
										Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again");
									}else {
										Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo");
									}
								return;
								}
								     Utilities.sendJsonResponse(response, "authfailed", authMessage);
								return;
							}else {
								// Record successful authorization
								recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, topupAmount, txnMode, currencyId, 
										userType, internalSystemReference, walletId, authStatus, authMessage);
								if(!recordAuthorization) { 
									if(langPref.equalsIgnoreCase("en")) {
										Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again");
									}else {
										Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo");
									}
									 
								return;
								}
							}
							/****** End of Wallet Authorization******/	
							
							// Get the tokenized card details from the blockchain
							
							if (NeoBankEnvironment.getBlockChainView().equals("true")) { // Will be enabled when calling the payment gateway 
								m_CardDetails = (CardDetails)CustomerTokenizationDao.class.getConstructor().newInstance().getCardDetailsbyTokenIdBlockchain(tokenId);
						    
							/*
							  For testing purposes 
							  TODO:- Remove this comment on prod
								if (m_CardDetails!=null) {
									NeoBankEnvironment.setComment(3,className, " Masked Card Number"+m_CardDetails.getMaskedCardNumber());
									NeoBankEnvironment.setComment(3,className,  "Card Number"+m_CardDetails.getCardNumber());
									NeoBankEnvironment.setComment(3,className,  "Date of expiry"+m_CardDetails.getDateOfExpiry());
								}
								
						      */
								
								
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
							 //4. Record the transaction and update the wallet
								success = (boolean) CustomerTokenizationDao.class.getConstructor().newInstance().toupWalletByToken(tokenId, topupAmount, relationshipNo, externalRefNo,
										currencyId,transactionCode,internalSystemReference,walletId);
								if (success) {
									obj.add("error", gson.toJsonTree("false"));
									SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, userType,
											moduleCode, StringUtils.substring(" Wallet Topup by Token " + externalRefNo, 0, 48));
									
								} else {
									if(langPref.equalsIgnoreCase("en")) {
										obj.add("message", gson.toJsonTree("Topup via card failed, Try again Later"));
									}else {
										obj.add("message", gson.toJsonTree("Recarga a través de tarjeta fallida, inténtalo de nuevo más tarde"));
									}
									
									obj.add("error", gson.toJsonTree("true"));
								}
								
								try {
									NeoBankEnvironment.setComment(3, className," regnewcard String is " + gson.toJson(obj));
									out_json.print(gson.toJson(obj));
								} finally {
									if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
									if (userType != null)userType = null; if (cvv!=null); cvv=null; if (moduleCode!=null); moduleCode=null;
									if (tokenId!=null); tokenId=null;   if (m_CardDetails!=null); m_CardDetails=null; if (obj != null)obj = null;
									if (currencyId!=null); currencyId=null;   if (walletId!=null); walletId=null; if (authResponse != null)authResponse = null;
									if (transactionCode!=null); transactionCode=null;   if (internalSystemReference!=null); internalSystemReference=null; 		
									 if (txnMode!=null); txnMode=null; 
								}

						}catch (Exception e) {
							NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+ "is "+e.getMessage());
							Utilities.sendJsonResponse(response, "true", "Error in topping up wallet via card, please try again");
						}
						
					break;	
					
					case "topupbycard_mbl":
						try {
							NeoBankEnvironment.setComment(3,className,"Inside topupbycard_mbl");
							PrintWriter out_json = response.getWriter();  Gson gson = new Gson();JsonObject obj = new JsonObject();
							String tokenValue = null;
							String relationshipNo=null; String tokenId=null; String topupAmount=null; String cvv=null; CardDetails m_CardDetails =null;
							String externalRefNo=null; boolean success =false; String userType=null; String moduleCode=null; String txnMode=null;
							String authStatus=""; String authMessage=""; String walletId=null; String authResponse=null;
							boolean recordAuthorization=false; String currencyId = null; String transactionCode=null;String langPref=null;
							String internalSystemReference=null;
							Wallet m_Wallet=null; List<AssetTransaction>lastFiveTransations=null;
							
							if(request.getParameter("relno")!=null)			relationshipNo = request.getParameter("relno").trim();
							if(request.getParameter("tokenid")!=null)		tokenId = request.getParameter("tokenid").trim();
							if(request.getParameter("amount")!=null)		topupAmount = request.getParameter("amount").trim();
							if(request.getParameter("cvv")!=null)	      	cvv = request.getParameter("cvv").trim();
							if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
							if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

							//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
							if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
								NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
								if(langPref.equalsIgnoreCase("ES")) {
									Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es válido, vuelva a iniciar sesión");

								}else {
									Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
								}
								return;
							}
							
							userType="C";
							moduleCode=NeoBankEnvironment.getCodeTokenWalletTopup();
							txnMode="C";// Credit
							currencyId = NeoBankEnvironment.getUSDCurrencyId();
							SimpleDateFormat formatter1 = new SimpleDateFormat("yyMMddHHmmssSSS");
							transactionCode = formatter1.format(new java.util.Date()) + Utilities.genAlphaNumRandom(9);
							internalSystemReference=NeoBankEnvironment.getCodeTokenWalletTopup()+"-"+transactionCode;
						      // External Sys REF NO
							externalRefNo = NeoBankEnvironment.getTokenRegistrationCodeForCustomer() + "-" +  RandomStringUtils.random(16, false, true);
 							 //  
							// TODO:- 1. Do wallet authorization eg. checking wallet limits
							/****** Wallet Authorization******/
							
							authResponse=(String)WalletAuthorizationDao.class.getConstructor().newInstance().performWalletAuthorization(relationshipNo, topupAmount, txnMode);
							if (authResponse.isEmpty()) {
								if(langPref.equalsIgnoreCase("ES")) {
									Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo más tarde");

								}else {
									Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
								}
								return;
							}
							authStatus=authResponse.substring(0,authResponse.indexOf(","));
							authMessage=authResponse.substring(authResponse.indexOf(",")+1,authResponse.indexOf("|"));
							walletId=authResponse.substring(authResponse.indexOf("|")+1,authResponse.length());
							NeoBankEnvironment.setComment(3, className, "walletId is "+walletId);
					
							if (authStatus.equals("S")==false) {
								//Authorization failed
								// Record failed authorization
								NeoBankEnvironment.setComment(3,className,"Authentication is successful");
								recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, topupAmount, txnMode, currencyId, 
										userType, internalSystemReference, walletId, authStatus, authMessage);
								if(!recordAuthorization) { 
									if(langPref.equalsIgnoreCase("ES")) {
										Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo más tarde");

									}else {
										Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
									}
								return;
								}
								     Utilities.sendJsonResponse(response, "authfailed", authMessage);
								return;
							}else {
								// Record successful authorization
								recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, topupAmount, txnMode, currencyId, 
										userType, internalSystemReference, walletId, authStatus, authMessage);
								if(!recordAuthorization) { 
									if(langPref.equalsIgnoreCase("ES")) {
										Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo más tarde");

									}else {
										Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
									}
								return;
								}
							}
							/****** End of Wallet Authorization******/	
							
							// 2. Get the card details using the tokenid
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
							 //4. Record the transaction and update the wallet
								success = (boolean) CustomerTokenizationDao.class.getConstructor().newInstance().toupWalletByToken(tokenId, topupAmount, relationshipNo, externalRefNo,
										currencyId,transactionCode,internalSystemReference,walletId);
								if (success) {
									
									SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, userType,
											moduleCode, StringUtils.substring(" Wallet Topup by Token " + externalRefNo, 0, 48));
									// Get wallet details and last 5 transactions
									
									m_Wallet= (Wallet)CustomerWalletDao.class.getConstructor().newInstance().getCustomerWalletDetails(relationshipNo,userType);
									// Get last five transaction
									lastFiveTransations= (List<AssetTransaction>)CustomerWalletDao.class.getConstructor().newInstance().getFiatWalletLastTenTxn(relationshipNo);
									obj.add("error", gson.toJsonTree("false"));
									obj.add("data", gson.toJsonTree(m_Wallet));
									obj.add("transactions", gson.toJsonTree(lastFiveTransations));
									
								} else {
									if(langPref.equalsIgnoreCase("ES")) {
										obj.add("message", gson.toJsonTree("Recarga a través de tarjeta fallida, inténtalo de nuevo más tarde"));

									}else {
										obj.add("message", gson.toJsonTree("Topup via card failed, Try again Later"));
									}
									obj.add("error", gson.toJsonTree("true"));
								}
								
								try {
									NeoBankEnvironment.setComment(3, className," topupbycard_mbl String is " + gson.toJson(obj));
									out_json.print(gson.toJson(obj));
								} finally {
									if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
									if (userType != null)userType = null; if (cvv!=null); cvv=null; if (moduleCode!=null); moduleCode=null;
									if (tokenId!=null); tokenId=null;   if (m_CardDetails!=null); m_CardDetails=null; if (obj != null)obj = null;
									if (currencyId!=null); currencyId=null;   if (walletId!=null); walletId=null; if (authResponse != null)authResponse = null;
									if (transactionCode!=null); transactionCode=null;   if (internalSystemReference!=null); internalSystemReference=null; 		
									if (txnMode!=null); txnMode=null; if (lastFiveTransations!=null)lastFiveTransations=null;
									if(m_Wallet!=null) m_Wallet=null;
								}

						}catch (Exception e) {
							NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+ "is "+e.getMessage());
							Utilities.sendJsonResponse(response, "true", "Error in topping up wallet via card, please try again");
						}
						
					break;	
	
	}
  }
}
