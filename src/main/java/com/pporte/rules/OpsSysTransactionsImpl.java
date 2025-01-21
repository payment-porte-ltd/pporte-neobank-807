package com.pporte.rules;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.CustomerPorteCoinDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.model.AssetCoin;
import com.pporte.model.CryptoAssetCoins;
import com.pporte.model.PricingDetails;
import com.pporte.model.PricingSlabRates;
import com.pporte.model.TransactionLimitDetails;
import com.pporte.model.TransactionRules;
import com.pporte.model.User;
import com.pporte.utilities.ExchangeRatesUtility;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;

public class OpsSysTransactionsImpl implements Rules{
	private static String className = OpsSysTransactionsImpl.class.getSimpleName();


	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null)
			Utilities.callOpsException(request, response, ctx, "Session has expired, please log in again");

		switch (rulesaction) {
		
		case "Set Pricing":
			try {
				
				String langPref = null; 
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opsprc");
				request.setAttribute("lastrule", "Set Pricing");
				request.setAttribute("arrpricingplans", (ArrayList<PricingDetails>)SystemUtilsDao.class.getConstructor().newInstance().getPricingPlanDetails());

				request.setAttribute("arrtxnrules", (ArrayList<TransactionRules>)SystemUtilsDao.class.getConstructor().newInstance().getTransactionRules());
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsSetPricingPage()).forward(request, response);
				 } finally {
					 if (langPref != null)langPref = null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
			
		case "opseditpricing":
			try {
				
				String langPref = null; String planId = null; String planValue=null; String varFee=null;
				String minimumAmount = null; String userId = null; String userType = null; String isDefault = null;
				String planUserType = null; String planDesc = null; String fixedFee = null; String payType = null; 
				String status = null; String payMode = null; String slabApplicable = null;
				NeoBankEnvironment.setComment(2, className, "inside opseditpricing rule");

				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				if(request.getParameter("editplanid")!=null)			  planId = request.getParameter("editplanid").trim();
				if(request.getParameter("editplanvalue")!=null)			  planValue = request.getParameter("editplanvalue").trim();
				if(request.getParameter("editvarfee")!=null)			  varFee = request.getParameter("editvarfee").trim();
				if(request.getParameter("editminamount")!=null)			  minimumAmount = request.getParameter("editminamount").trim();
				if(request.getParameter("hdneditisdefault")!=null)			  isDefault = request.getParameter("hdneditisdefault").trim();
				if(request.getParameter("editplandesc")!=null)			  planDesc = request.getParameter("editplandesc").trim();
				if(request.getParameter("editfixedfee")!=null)			  fixedFee = request.getParameter("editfixedfee").trim();
				if(request.getParameter("hdnpaytype")!=null)			  payType = request.getParameter("hdnpaytype").trim();
				if(request.getParameter("hdnpricingstatus")!=null)		  status = request.getParameter("hdnpricingstatus").trim();
				if(request.getParameter("hdnslabapllicable")!=null)		  slabApplicable = request.getParameter("hdnslabapllicable").trim();
				if(request.getParameter("hdnusertype")!=null)			  planUserType = request.getParameter("hdnusertype").trim();
				if(request.getParameter("hdnseleditpaymode")!=null)			  payMode = request.getParameter("hdnseleditpaymode").trim();

				NeoBankEnvironment.setComment(2, className, "inside isDefault isDefault "+ isDefault);

				
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opsprc");
				request.setAttribute("lastrule", "Set Pricing");
				
				userId = ((User)session.getAttribute("SESS_USER")).getUserId();
				userType = ((User)session.getAttribute("SESS_USER")).getUserType();
				
				boolean result  = (boolean)SystemUtilsDao.class.getConstructor().newInstance().updatePricingTable( planId, planValue, 
						varFee, minimumAmount, isDefault, planUserType, planDesc, fixedFee, payType, status, payMode, slabApplicable );
				
				if(result) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",StringUtils.substring("Updated Pricing plan planId " + planId , 0, 48));
				}else {
					NeoBankEnvironment.setComment(1, className, "Error in updating Pricing details planId "+ planId );

				}
									
				request.setAttribute("arrpricingplans", (ArrayList<PricingDetails>)SystemUtilsDao.class.getConstructor().newInstance().getPricingPlanDetails());
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsSetPricingPage()).forward(request, response);
				} finally {
					if(langPref !=null) langPref=null;if(planId !=null) planId=null;if(planValue !=null) planValue=null;
					if(varFee !=null) varFee=null;if(minimumAmount !=null) minimumAmount=null;if(userType !=null) userType=null;
					if(isDefault !=null) isDefault=null;if(userId !=null) userId=null;if(fixedFee !=null) fixedFee=null;
					if(planUserType !=null) planUserType=null;if(planDesc !=null) planDesc=null;if(payType !=null) payType=null;
					if(slabApplicable !=null) slabApplicable=null;if(payMode !=null) payMode=null;if(status !=null) status=null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
			
		case "opsaddpricingplan":
			try {
				
				String langPref = null; String planValue=null; String varFee=null;
				String minimumAmount = null; String userId = null; String userType = null; String isDefault = null;
				String planUserType = null; String planDesc = null; String fixedFee = null; String payType = null; 
				String status = null; String payMode = null; String slabApplicable = null;
				
				NeoBankEnvironment.setComment(2, className, "inside opsaddpricingplan rule");

				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				if(request.getParameter("addplanvalue")!=null)			  planValue = request.getParameter("addplanvalue").trim();
				if(request.getParameter("addvarfee")!=null)			  	  varFee = request.getParameter("addvarfee").trim();
				if(request.getParameter("addminamount")!=null)			  minimumAmount = request.getParameter("addminamount").trim();
				if(request.getParameter("hdnselpaymode")!=null)			  payMode = request.getParameter("hdnselpaymode").trim();
				if(request.getParameter("addplandesc")!=null)			  planDesc = request.getParameter("addplandesc").trim();
				if(request.getParameter("addfixedfee")!=null)			  fixedFee = request.getParameter("addfixedfee").trim();
				if(request.getParameter("hdnpricingstatus")!=null)		  status = request.getParameter("hdnpricingstatus").trim();
				if(request.getParameter("hdnslabapllicable")!=null)		   slabApplicable = request.getParameter("hdnslabapllicable").trim();
				if(request.getParameter("hdnpaytype")!=null)			  payType = request.getParameter("hdnpaytype").trim();
				if(request.getParameter("hdnusertype")!=null)			  planUserType = request.getParameter("hdnusertype").trim();
				if(request.getParameter("hdnisdefault")!=null)			  isDefault = request.getParameter("hdnisdefault").trim();
				
				NeoBankEnvironment.setComment(2, className, "inside isDefault isDefault "+ isDefault);
				NeoBankEnvironment.setComment(2, className, "inside payMode "+ payMode);

				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opsprc");
				request.setAttribute("lastrule", "Set Pricing");
				
				userId = ((User)session.getAttribute("SESS_USER")).getUserId();
				userType = ((User)session.getAttribute("SESS_USER")).getUserType();
				
				boolean result  = (boolean)SystemUtilsDao.class.getConstructor().newInstance().addNewPricingPlan(planValue, 
						varFee, minimumAmount, isDefault, planUserType, planDesc, fixedFee, payType, status, payMode, slabApplicable );
				
				if(result) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",StringUtils.substring("Added new Pricing plan planValue " + planValue , 0, 48));
				}else {
					NeoBankEnvironment.setComment(1, className, "Error in adding Pricing details planDesc "+ planDesc );

				}
									
				request.setAttribute("arrpricingplans", (ArrayList<PricingDetails>)SystemUtilsDao.class.getConstructor().newInstance().getPricingPlanDetails());
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsSetPricingPage()).forward(request, response);
				} finally {
					if(langPref !=null) langPref=null;if(planValue !=null) planValue=null;
					if(varFee !=null) varFee=null;if(minimumAmount !=null) minimumAmount=null;if(userType !=null) userType=null;
					if(isDefault !=null) isDefault=null;if(userId !=null) userId=null;if(fixedFee !=null) fixedFee=null;
					if(planUserType !=null) planUserType=null;if(planDesc !=null) planDesc=null;if(payType !=null) payType=null;
					if(slabApplicable !=null) slabApplicable=null;if(payMode !=null) payMode=null;if(status !=null) status=null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
			
			case "Set Transaction Limits":
				try {
					
					String langPref = null;
					if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
	
					request.setAttribute("langPref", langPref);
					request.setAttribute("lastaction", "opsprc");
					request.setAttribute("lastrule", "Set Transaction Limits");
					
					request.setAttribute("arrtxnlimits", (ArrayList<TransactionLimitDetails>)SystemUtilsDao.class.getConstructor().newInstance().getAllTxnLimitsDetails());
					response.setContentType("text/html");
					 try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getOpsTransactionLimitsPage()).forward(request, response);
					 } finally {
						 
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
				}
				break;	
			case "opsedittxnlimits":
				try {
					
					String langPref = null; String txnLimitId = null; String limitType=null; String limitDescription=null;
					String limitAmount = null; String userId = null; String userType = null;
					String limitStatus = null;  String limitUserType = null;
					
					
					if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
					if(request.getParameter("txnlimitid")!=null)			  txnLimitId = request.getParameter("txnlimitid").trim();
					if(request.getParameter("limit_type")!=null)			  limitType = request.getParameter("limit_type").trim();
					if(request.getParameter("limitdesc")!=null)				  limitDescription = request.getParameter("limitdesc").trim();
					if(request.getParameter("limitamount")!=null)			  limitAmount = request.getParameter("limitamount").trim();
					if(request.getParameter("hdnlimitstatus")!=null)		  limitStatus = request.getParameter("hdnlimitstatus").trim();
					if(request.getParameter("hdnusertype")!=null)		      limitUserType = request.getParameter("hdnusertype").trim();

					userId = ((User)session.getAttribute("SESS_USER")).getUserId();
					userType = ((User)session.getAttribute("SESS_USER")).getUserType();
			
					boolean result  = (boolean)SystemUtilsDao.class.getConstructor().newInstance().updateOpsTransactionLimits( txnLimitId, limitType, 
							limitAmount, limitDescription, limitUserType, limitStatus );
					
					if(result) {
						// ModuleCode:- UTL- Updating Transaction Limit
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "UTL",StringUtils.substring("Updated Tranaction Limit txnLimitId " + txnLimitId , 0, 48));
					}else {
						NeoBankEnvironment.setComment(1, className, "Error in updating Pricing details txnLimitId "+ txnLimitId );

					}
					
					request.setAttribute("langPref", langPref);
					request.setAttribute("lastaction", "opsprc");
					request.setAttribute("lastrule", "Set Transaction Limits");					
					request.setAttribute("arrtxnlimits", (ArrayList<TransactionLimitDetails>)SystemUtilsDao.class.getConstructor().newInstance().getAllTxnLimitsDetails());
					response.setContentType("text/html");
					 try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getOpsTransactionLimitsPage()).forward(request, response);
					} finally {
												
						if(langPref !=null) langPref=null;if(txnLimitId !=null) txnLimitId=null;if(limitAmount !=null) limitAmount=null;
						if(limitType !=null) limitType=null;if(limitDescription !=null) limitDescription=null;if(userType !=null) userType=null;
						if(userId !=null) userId=null;  if(limitUserType !=null) limitUserType=null;
						if(limitStatus !=null) limitStatus=null;
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
				}
				break;
				
			 case "opsaddtxnlimitdetails":
				try {
					
					String langPref = null; String txnLimitId = null;String userId = null; String userType = null; 
					String limitStatus = null;  String limitUserType = null;String limitType=null; String limitDescription=null;
					String limitAmount = null;
					
	
					if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();
					if(request.getParameter("addlimit_type")!=null)	   		limitType = request.getParameter("addlimit_type").trim();
					if(request.getParameter("addlimitdesc")!=null)			limitDescription = request.getParameter("addlimitdesc").trim();
					if(request.getParameter("addlimitamount")!=null)		limitAmount = request.getParameter("addlimitamount").trim();
					if(request.getParameter("hdnaddlimitstatus")!=null)		limitStatus = request.getParameter("hdnaddlimitstatus").trim();
					if(request.getParameter("hdnaddusertype")!=null)		limitUserType = request.getParameter("hdnaddusertype").trim();

					request.setAttribute("langPref", langPref);
					request.setAttribute("lastaction", "opsprc");
					request.setAttribute("lastrule", "Set Transaction Limits");
					
					userId = ((User)session.getAttribute("SESS_USER")).getUserId();
					userType = ((User)session.getAttribute("SESS_USER")).getUserType();
					
					boolean result  = (boolean)SystemUtilsDao.class.getConstructor().newInstance().addOpsTransactionLimits( limitType, limitDescription, 
							limitAmount, limitUserType, limitStatus );
					if(result) {
						// Module Code- ATL:- Adding New Transaction Limit
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "ATL",StringUtils.substring("Added new Transaction Limit " + limitType , 0, 48));
					}else {
						NeoBankEnvironment.setComment(1, className, "Error in adding Transaction Limit details  ");

					}
			
					request.setAttribute("arrtxnlimits", (ArrayList<TransactionLimitDetails>)SystemUtilsDao.class.getConstructor().newInstance().getAllTxnLimitsDetails());
					response.setContentType("text/html");
					 try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getOpsTransactionLimitsPage()).forward(request, response);
					} finally {
						if(langPref !=null) langPref=null;if(txnLimitId !=null) txnLimitId=null;
						if(userType !=null) userType=null;	if(userId !=null) userId=null; if(limitUserType !=null) limitUserType=null;
						if(limitStatus !=null) limitStatus=null;if(limitType !=null) limitType=null;if(limitDescription !=null) limitDescription=null;
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
				}
				break;
				
			 case "Transaction Rules":
					try {
						
						String langPref = null;
						if(request.getParameter("hdnlang")!=null)	langPref = request.getParameter("hdnlang").trim();

						request.setAttribute("langPref", langPref);
						request.setAttribute("lastaction", "opsprc");
						request.setAttribute("lastrule", "Transaction Rules");
						
						ArrayList<TransactionRules> arrTranactionRules = (ArrayList<TransactionRules>) SystemUtilsDao.class .getConstructor().newInstance().getAllTransactionRules();
						request.setAttribute("alltransactionrules", arrTranactionRules);
						response.setContentType("text/html");
						 try {
								ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewTransactionRulePage()).forward(request, response);
						 } finally {
							 if(arrTranactionRules != null) arrTranactionRules = null; if(langPref !=null) langPref = null;
							 
						}
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
						Utilities.callOpsException(request, response, ctx, e.getMessage());
					}
					break;	
					
				case "opsedittranactionrules":
					try {
						
						String langPref = null; String payMode = null; String ruleDesc=null;
						String userId = null; String userType = null; String LoyaltyStatus = null;
						String planUserType = null; String lyltyUserType = null;
						
						NeoBankEnvironment.setComment(2, className, "inside opseditloyaltyrules rule");

						if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
						if(request.getParameter("editpaymode")!=null)			  payMode = request.getParameter("editpaymode").trim();
						if(request.getParameter("editruledesc")!=null)			  ruleDesc = request.getParameter("editruledesc").trim();
						if(request.getParameter("hdnloyaltystatus")!=null)		  LoyaltyStatus = request.getParameter("hdnloyaltystatus").trim();
						if(request.getParameter("hdnusertype")!=null)			  lyltyUserType = request.getParameter("hdnusertype").trim();
						
						NeoBankEnvironment.setComment(2, className, "inside  payMode "+payMode);

						request.setAttribute("langPref", langPref);
						request.setAttribute("lastaction", "opsprc");
						request.setAttribute("lastrule", "Transaction Rules");
						
						userId = ((User)session.getAttribute("SESS_USER")).getUserId();
						userType = ((User)session.getAttribute("SESS_USER")).getUserType();
						
						boolean result  = (boolean)SystemUtilsDao.class.getConstructor().newInstance().updateTransactionRules( payMode, ruleDesc, 
								 LoyaltyStatus, lyltyUserType );
						
						if(result) {
							SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",StringUtils.substring("Updated Transaction Rule payMode " + payMode , 0, 48));
						}else {
							NeoBankEnvironment.setComment(1, className, "Error in updating Transaction Rules payMode "+ payMode );

						}
											
						ArrayList<TransactionRules> arrTransactionRules = (ArrayList<TransactionRules>) SystemUtilsDao.class .getConstructor().newInstance().getAllTransactionRules();
						request.setAttribute("alltransactionrules", arrTransactionRules);
						response.setContentType("text/html");
						 try {
								ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewTransactionRulePage()).forward(request, response);
						 } finally {
												
							if(langPref !=null) langPref=null;if(arrTransactionRules !=null) arrTransactionRules=null;
							if(payMode !=null) payMode=null; if(ruleDesc !=null) ruleDesc=null;
							if(LoyaltyStatus !=null) LoyaltyStatus=null;if(userType !=null) userType=null;
							if(planUserType !=null) planUserType=null;if(lyltyUserType !=null) lyltyUserType=null;
						}
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
						Utilities.callOpsException(request, response, ctx, e.getMessage());
					}
					break;
					
				case "opsaddtransactionrules":
					try {
						
						String langPref = null; String payMode = null; String ruleDesc=null; String pointConversion=null;
						String crytoConversion = null; String userId = null; String userType = null; String LoyaltyStatus = null;
						String planUserType = null; String lyltyUserType = null;
						
						NeoBankEnvironment.setComment(2, className, "inside opsaddloyaltyrules rule");

						if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
						if(request.getParameter("addpaymode")!=null)			  payMode = request.getParameter("addpaymode").trim();
						if(request.getParameter("addruledesc")!=null)			  ruleDesc = request.getParameter("addruledesc").trim();
						if(request.getParameter("hdnaddloyaltystatus")!=null)	  LoyaltyStatus = request.getParameter("hdnaddloyaltystatus").trim();
						if(request.getParameter("hdnaddusertype")!=null)		  lyltyUserType = request.getParameter("hdnaddusertype").trim();
						
						NeoBankEnvironment.setComment(2, className, "inside payMode "+ payMode);

						request.setAttribute("langPref", langPref);
						request.setAttribute("lastaction", "opsprc");
						request.setAttribute("lastrule", "Transaction Rules");
						
						userId = ((User)session.getAttribute("SESS_USER")).getUserId();
						userType = ((User)session.getAttribute("SESS_USER")).getUserType();
						
						boolean result  = (boolean)SystemUtilsDao.class.getConstructor().newInstance().addNewTransactionRules( payMode, ruleDesc, 
								 LoyaltyStatus, lyltyUserType );
						
						if(result) {
							SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",StringUtils.substring("Added new Transaction Rules payMode " + payMode , 0, 48));
						}else {
							NeoBankEnvironment.setComment(1, className, "Error in adding Transaction payMode "+ payMode );

						}
											
						ArrayList<TransactionRules> arrTransactionRules = (ArrayList<TransactionRules>) SystemUtilsDao.class .getConstructor().newInstance().getAllTransactionRules();
						request.setAttribute("alltransactionrules", arrTransactionRules);
						response.setContentType("text/html");
						 try {
								ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewTransactionRulePage()).forward(request, response);
						 } finally {
							
							if(langPref !=null) langPref=null;if(arrTransactionRules !=null) arrTransactionRules=null;
							if(payMode !=null) payMode=null; if(ruleDesc !=null) ruleDesc=null;if(pointConversion !=null) pointConversion=null;
							if(LoyaltyStatus !=null) LoyaltyStatus=null;if(crytoConversion !=null) crytoConversion=null;if(userType !=null) userType=null;
							if(planUserType !=null) planUserType=null;if(lyltyUserType !=null) lyltyUserType=null;
						}
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
						Utilities.callOpsException(request, response, ctx, e.getMessage());
					}
					break;
				case "viewslabrateforspecificplan":
					try {
						
						String langPref = null; String planId = null;
						if(request.getParameter("hdnlang")!=null)	langPref = request.getParameter("hdnlang").trim();
						if(request.getParameter("hdnplanid")!=null)	planId = StringUtils.trim( request.getParameter("hdnplanid") );

						NeoBankEnvironment.setComment(2, className, "inside viewslabrateforspecificplan ");

						request.setAttribute("langPref", langPref);
						request.setAttribute("lastaction", "opsprc");
						request.setAttribute("lastrule", "Set Pricing");
						request.setAttribute("planid", planId);

						
						ArrayList<PricingSlabRates> arrSlabRates = (ArrayList<PricingSlabRates>)SystemUtilsDao.class.getConstructor().newInstance().getSlabRates(planId);
						request.setAttribute("arrslabrates",arrSlabRates); 
						response.setContentType("text/html");
						 try {
								ctx.getRequestDispatcher(NeoBankEnvironment.getSlabRatesForSpecificPlan()).forward(request, response);
						 } finally {
							 if(arrSlabRates != null) arrSlabRates = null; if(langPref !=null) langPref = null;
							 
						}
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
						Utilities.callOpsException(request, response, ctx, e.getMessage());
					}
				break;
				case "Asset Pricing":
					try {
						
						String langPref = null;
						if(request.getParameter("hdnlang")!=null)	langPref = request.getParameter("hdnlang").trim();

						request.setAttribute("langPref", langPref);
						request.setAttribute("lastaction", "opscrypto");
						request.setAttribute("lastrule", "Asset Pricing");
						
						ArrayList<AssetCoin> arrAssetPricing = (ArrayList<AssetCoin>) SystemUtilsDao.class 
								.getConstructor().newInstance().getPorteAssetPricing();
						request.setAttribute("assetpricing", arrAssetPricing);
						ArrayList<CryptoAssetCoins> arrAssets = (ArrayList<CryptoAssetCoins>) CustomerPorteCoinDao.class.
								getConstructor().newInstance().getPorteAssetDetails();
						request.setAttribute("assets", arrAssets);
						ArrayList<CryptoAssetCoins> arrFiatAssets = (ArrayList<CryptoAssetCoins>) CustomerPorteCoinDao.class.
								getConstructor().newInstance().getFiatWalletAssets();
						request.setAttribute("fiatassets", arrFiatAssets);
						response.setContentType("text/html");
						 try {
								ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewAssetPricingPage()).forward(request, response);
						 } finally {
							 if(arrAssetPricing != null) arrAssetPricing = null; if(langPref !=null) langPref = null;
							 if(arrAssets != null) arrAssets = null; if(arrFiatAssets!=null)arrFiatAssets=null;
							 
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
		// TODO Auto-generated method stub
		
	}



	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			 ServletContext ctx) throws Exception {
		HttpSession session = request.getSession(false);
		switch (rulesaction) {
		case "editpricingslabrate":
			try {
				String slabId = null; String userId = null; String userType = null; 	String planId = null;	String fromRange = null; 
				String toRange = null;  	Boolean success = false; String rate = null;	
				JsonObject obj = new JsonObject(); PrintWriter out_json = response.getWriter(); String status = null;
				String errorMsg = "false"; Gson gson = new Gson();
				
				 if(request.getParameter("editslabid")!=null)	slabId = StringUtils.trim( request.getParameter("editslabid") );
				 if(request.getParameter("editplanid")!=null)	planId = StringUtils.trim( request.getParameter("editplanid") );
				 if(request.getParameter("editfromrange")!=null)	fromRange = StringUtils.trim( request.getParameter("editfromrange") );
				 if(request.getParameter("edittorange")!=null)	toRange = StringUtils.trim( request.getParameter("edittorange") );
				 if(request.getParameter("editrate")!=null)	rate = StringUtils.trim( request.getParameter("editrate") );
				 if(request.getParameter("hdneditstatus")!=null)	status = StringUtils.trim( request.getParameter("hdneditstatus") );
				 				
				NeoBankEnvironment.setComment(3, className, "inside editpricingslabrate slabId " + slabId+ " planId "+planId+" fromRange "
						+ fromRange +" toRange "+toRange +" rate "+rate + " status "+ status);
				
				userId=((User) session.getAttribute("SESS_USER")).getUserId();
				userType=((User) session.getAttribute("SESS_USER")).getUserType();

				success = (boolean)SystemUtilsDao.class.getConstructor().newInstance().editExistingSlab(slabId, planId, fromRange, toRange, rate, status );

				if(success) {
					
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",
							StringUtils.substring("Ops Updated slab rate for " + userType, 0, 48));
					
	        		errorMsg = "false";
				    obj.add("message", gson.toJsonTree("Pricing Slab rate updated successful")); 

	        	}else {
					errorMsg = "Error in updating pricing slab rate";

	        	}
				try {
					 obj.add("error", gson.toJsonTree(errorMsg)); 
					 out_json.print( gson.toJson(obj));
				}finally {
					if(slabId!=null) slabId=null; if(userType!=null) userType=null; if(planId!=null) planId=null; 
					if(fromRange!=null) fromRange=null; if(userId!=null) userId=null; if(rate !=null) rate=null;
					if(toRange!=null) toRange=null; if(status!=null) status=null; 
					if(obj!=null) obj = null; if(out_json!=null) out_json.close();
				}
				
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Ops edit slab rate failed, Please try again letter");
			}
			break;
			
		case "addnewpricingslabrate":
			try {
				String slabId = null; String userId = null; String userType = null; 	String planId = null;	String fromRange = null; 
				String toRange = null;  	Boolean success = false; String rate = null;	
				JsonObject obj = new JsonObject(); PrintWriter out_json = response.getWriter(); String status = null;
				String errorMsg = "false"; Gson gson = new Gson();
				
				// if(request.getParameter("editslabid")!=null)	slabId = StringUtils.trim( request.getParameter("editslabid") );
				 if(request.getParameter("addslabrateplanid")!=null)	planId = StringUtils.trim( request.getParameter("addslabrateplanid") );
				 if(request.getParameter("addfromrange")!=null)	fromRange = StringUtils.trim( request.getParameter("addfromrange") );
				 if(request.getParameter("addtorange")!=null)	toRange = StringUtils.trim( request.getParameter("addtorange") );
				 if(request.getParameter("addrate")!=null)	rate = StringUtils.trim( request.getParameter("addrate") );
				 if(request.getParameter("hdnnewstatus")!=null)	status = StringUtils.trim( request.getParameter("hdnnewstatus") );
				 				
				NeoBankEnvironment.setComment(3, className, "inside opsraisedispute slabId " + slabId+ " planId "+planId+" fromRange "
						+ fromRange +" toRange "+toRange +" rate "+rate + " status "+ status);
				
				userId=((User) session.getAttribute("SESS_USER")).getUserId();
				userType=((User) session.getAttribute("SESS_USER")).getUserType();

				 success = (boolean)SystemUtilsDao.class.getConstructor().newInstance().addNewSlabRate(planId, fromRange, toRange, rate, status );

				if(success) {
					
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",
							StringUtils.substring("Ops Updated slab rate for " + userType, 0, 48));
					
	        		errorMsg = "false";
				    obj.add("message", gson.toJsonTree("Pricing Slab rate updated successful")); 

	        	}else {
					errorMsg = "Error in updating pricing slab rate";

	        	}
				try {
					 obj.add("error", gson.toJsonTree(errorMsg)); 
					 out_json.print( gson.toJson(obj));
				}finally {
					if(slabId!=null) slabId=null; if(userType!=null) userType=null; if(planId!=null) planId=null; 
					if(fromRange!=null) fromRange=null; if(userId!=null) userId=null; if(rate !=null) rate=null;
					if(toRange!=null) toRange=null; if(status!=null) status=null; 
					if(obj!=null) obj = null; if(out_json!=null) out_json.close();
				}
				
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Ops edit slab rate failed, Please try again letter");
			}
			break;
	
		case "opsaddassetpricing":
			try {
				String assetCode = null; String assetSellRate = null; 	String priceStatus = null;	
				String userId = null; String userType = null; 	Boolean success = false; String rate = null;	
				JsonObject obj = new JsonObject(); PrintWriter out_json = response.getWriter(); String status = null;
				String errorMsg = "false"; Gson gson = new Gson(); String sequencId = null; String fiatCurrency=null;
				
				 if(request.getParameter("hdnaddassetcode")!=null)	assetCode = StringUtils.trim( request.getParameter("hdnaddassetcode") );
				 if(request.getParameter("addexhangerate")!=null)	assetSellRate = StringUtils.trim( request.getParameter("addexhangerate") );
				 if(request.getParameter("hdnaddsellratetystatus")!=null)	priceStatus = StringUtils.trim( request.getParameter("hdnaddsellratetystatus") );
				 if(request.getParameter("selfiatcurrency")!=null)	fiatCurrency = StringUtils.trim( request.getParameter("selfiatcurrency") );
				 				
				 String [] arrAssetCode = assetCode.split(",");
				 String [] arrFiatCurrency = fiatCurrency.split(",");
				 assetCode = arrAssetCode[0];
				 fiatCurrency = arrFiatCurrency[0];
				NeoBankEnvironment.setComment(3, className, "inside opsaddassetpricing slabId " + assetCode+ " assetSellRate "
						+ assetSellRate +" priceStatus "+priceStatus+" fiatCurrency "+fiatCurrency );
				
				userId=((User) session.getAttribute("SESS_USER")).getUserId();
				userType=((User) session.getAttribute("SESS_USER")).getUserType();

				success = (boolean)SystemUtilsDao.class.getConstructor().newInstance().addNewPorteAssetPricing(assetCode, assetSellRate, priceStatus,fiatCurrency);

				if(success) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",
							StringUtils.substring("Created new Asset Pricing" + assetCode, 0, 48));
					
	        		errorMsg = "false";
				    obj.add("message", gson.toJsonTree("Asset Pricing Created successful")); 

	        	}else {
					errorMsg = "Error in creating asset pricing";

	        	}
				try {
					 obj.add("error", gson.toJsonTree(errorMsg)); 
					 NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
					 out_json.print( gson.toJson(obj));
				}finally {
					if(assetCode!=null) assetCode=null; if(userType!=null) userType=null; if(assetSellRate!=null) assetSellRate=null; 
					if(priceStatus!=null) priceStatus=null; if(userId!=null) userId=null; if(rate !=null) rate=null;
					if(sequencId!=null) sequencId=null; if(status!=null) status=null; 
					if(obj!=null) obj = null; if(out_json!=null) out_json.close(); 
					if (fiatCurrency!=null)fiatCurrency=null; if (arrAssetCode!=null) arrAssetCode=null;
					if (arrFiatCurrency!=null)arrFiatCurrency=null;
				}
				
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Ops Create Asset Pricing failed, Please try again letter");
			}
			break;
			
			case "opseditassetpricing":
				try {
					String assetCode = null; String assetSellRate = null; 	String priceStatus = null;	
					String userId = null; String userType = null; 	Boolean success = false; String rate = null;	
					JsonObject obj = new JsonObject(); PrintWriter out_json = response.getWriter(); String status = null;
					String errorMsg = "false"; Gson gson = new Gson(); String sequencId = null; String fiatCurrency=null;
					
					 if(request.getParameter("editassetcode")!=null)	assetCode = StringUtils.trim( request.getParameter("editassetcode") );
					 if(request.getParameter("editfiatcurrency")!=null)	fiatCurrency = StringUtils.trim( request.getParameter("editfiatcurrency") );
					 if(request.getParameter("editsellrate")!=null)	assetSellRate = StringUtils.trim( request.getParameter("editsellrate") );
					 if(request.getParameter("hdnsellratetystatus")!=null)	priceStatus = StringUtils.trim( request.getParameter("hdnsellratetystatus") );
					 if(request.getParameter("hdnsequenceno")!=null)	sequencId = StringUtils.trim( request.getParameter("hdnsequenceno") );
					 				
					NeoBankEnvironment.setComment(3, className, "inside opseditassetpricing slabId " + assetCode+ " assetSellRate "
							+ assetSellRate +" priceStatus "+priceStatus +" sequencId "+ sequencId +" fiatCurrency "+fiatCurrency );
					
					userId=((User) session.getAttribute("SESS_USER")).getUserId();
					userType=((User) session.getAttribute("SESS_USER")).getUserType();

					success = (boolean)SystemUtilsDao.class.getConstructor().newInstance().editPorteAssetPricing(assetCode, assetSellRate, priceStatus, sequencId,fiatCurrency);

					if(success) {
						
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",
								StringUtils.substring("Ops Updated Asset Pricing" + assetCode, 0, 48));
						
		        		errorMsg = "false";
					    obj.add("message", gson.toJsonTree("Asset Pricing updated successful")); 

		        	}else {
						errorMsg = "Error in updating asset pricing";

		        	}
					try {
						 obj.add("error", gson.toJsonTree(errorMsg)); 
						 out_json.print( gson.toJson(obj));
					}finally {
						if(assetCode!=null) assetCode=null; if(userType!=null) userType=null; if(assetSellRate!=null) assetSellRate=null; 
						if(priceStatus!=null) priceStatus=null; if(userId!=null) userId=null; if(rate !=null) rate=null;
						if(sequencId!=null) sequencId=null; if(status!=null) status=null; if (fiatCurrency!=null)fiatCurrency=null;
						if(obj!=null) obj = null; if(out_json!=null) out_json.close();
					}
					
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Ops Edit Asset Pricing failed, Please try again letter");
				}
				break;
			case"ops_update_asset_prices_from_coingecko":
				try {
					JsonObject obj = new JsonObject(); Gson gson = new Gson(); PrintWriter out_json = response.getWriter();
					// Update the rates, with the currently supported rates. Currently we have three supported
					//  rates:- USD, AUD, HKD
					double bitcoinUSDRate = 0; double porteUSDRate = 0; double usdcUSDRate = 0; double xlmUSDRate = 0; 
					double bitcoinAUDRate = 0; double porteAUDRate = 0; double usdcAUDRate = 0; double xlmAUDRate = 0; 
					double bitcoinHKDRate = 0; double porteHKDRate = 0; double usdcHKDRate = 0; double xlmHKDRate = 0; 
					boolean success = false;
					String userId=((User) session.getAttribute("SESS_USER")).getUserId(); 
					String userType=((User) session.getAttribute("SESS_USER")).getUserType();
					//Call coin Gecko API
					JsonObject rateResponseObj = ExchangeRatesUtility.getFiatExchangeRatesFromCoingecko();
					NeoBankEnvironment.setComment(3, className, " rateResponseObj "+rateResponseObj );
					if(rateResponseObj.equals(null)) throw new Exception("Failed to get rates from Coingecko");
					// USD
					bitcoinUSDRate = rateResponseObj.get("bitcoin").getAsJsonObject().get("usd").getAsDouble();
					porteUSDRate = rateResponseObj.get("porte-token").getAsJsonObject().get("usd").getAsDouble();
					usdcUSDRate = rateResponseObj.get("usd-coin").getAsJsonObject().get("usd").getAsDouble();
					xlmUSDRate = rateResponseObj.get("stellar").getAsJsonObject().get("usd").getAsDouble();
					// HKD
					bitcoinHKDRate = rateResponseObj.get("bitcoin").getAsJsonObject().get("hkd").getAsDouble();
					porteHKDRate = rateResponseObj.get("porte-token").getAsJsonObject().get("hkd").getAsDouble();
					usdcHKDRate = rateResponseObj.get("usd-coin").getAsJsonObject().get("hkd").getAsDouble();
					xlmHKDRate = rateResponseObj.get("stellar").getAsJsonObject().get("hkd").getAsDouble();
					// AUD
					bitcoinAUDRate = rateResponseObj.get("bitcoin").getAsJsonObject().get("aud").getAsDouble();
					porteAUDRate = rateResponseObj.get("porte-token").getAsJsonObject().get("aud").getAsDouble();
					usdcAUDRate = rateResponseObj.get("usd-coin").getAsJsonObject().get("aud").getAsDouble();
					xlmAUDRate = rateResponseObj.get("stellar").getAsJsonObject().get("aud").getAsDouble();
					
					String[] assetsToUpdate = {NeoBankEnvironment.getBitcoinCode(), 
							NeoBankEnvironment.getPorteTokenCode(), 
							NeoBankEnvironment.getUSDCCode(), NeoBankEnvironment.getXLMCode(),NeoBankEnvironment.getStellarBTCxCode()};
					
					double [] assetUSDRates = {bitcoinUSDRate,porteUSDRate, usdcUSDRate, xlmUSDRate, bitcoinUSDRate };
					double [] assetHKDRates = {bitcoinHKDRate,porteHKDRate, usdcHKDRate, xlmHKDRate, bitcoinHKDRate };
					double [] assetAUDRates = {bitcoinAUDRate,porteAUDRate, usdcAUDRate, xlmAUDRate, bitcoinAUDRate };
					
					NeoBankEnvironment.setComment(3,className,"assetUSDRates:- "+Arrays.toString(assetUSDRates));
					NeoBankEnvironment.setComment(3,className,"assetHKDRates:-  "+Arrays.toString(assetHKDRates));
					NeoBankEnvironment.setComment(3,className,"assetAUDRates:-  "+Arrays.toString(assetAUDRates));
					
					success = (boolean)SystemUtilsDao.class.getConstructor().newInstance().updateAssetPricesFromCoingecko(assetsToUpdate, assetUSDRates,assetHKDRates,assetAUDRates);
					
					if(success) {
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",
								StringUtils.substring("Ops Updated Asset Pricing From Coingecko", 0, 48));
						obj.add("message", gson.toJsonTree("Asset Pricing updated successful")); 
						obj.add("error", gson.toJsonTree("false")); 
					}else {
						obj.add("message", gson.toJsonTree("Error in updating asset pricing")); 
						obj.add("error", gson.toJsonTree("false")); 
					}
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
						 out_json.print( gson.toJson(obj));
					}finally {
						if(obj!=null) obj=null;  if(gson!=null) gson=null; if(assetsToUpdate!=null) assetsToUpdate=null; 
						if(assetUSDRates!=null) assetUSDRates=null;	if(assetHKDRates!=null) assetHKDRates=null;	if(assetAUDRates!=null) assetAUDRates=null; 
						if(rateResponseObj!=null) rateResponseObj=null; if(userType!=null) userType=null; if(out_json!=null) out_json.close(); 
					}
									
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Ops Updating Asset Pricing failed, Please try again letter");
				}
				
				
				break;
			case "ops_get_asset_price":
				try {
					String assetCode = null;	 	
					JsonObject obj = new JsonObject(); PrintWriter out_json = response.getWriter();  String fiatCurrency=null;
					Gson gson = new Gson(); String price = null;
					if(request.getParameter("asset")!=null)	assetCode = StringUtils.trim( request.getParameter("asset") );
					if(request.getParameter("fiat_currency")!=null)	fiatCurrency = StringUtils.trim( request.getParameter("fiat_currency") );
					price = (String)SystemUtilsDao.class.getConstructor().newInstance().getAssetPrice(assetCode,fiatCurrency);
					
					
					obj.add("error", gson.toJsonTree("false")); 
					obj.add("price", gson.toJsonTree(price)); 
					
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
						 out_json.print( gson.toJson(obj));
					}finally {
						if(obj!=null) obj=null;  if(gson!=null) gson=null; 
						if(price!=null) price=null; 
						if(out_json!=null) out_json.close(); if (fiatCurrency!=null)fiatCurrency=null;
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Ops get Asset Pricing failed, Please try again letter");
				}
				
				break;
			case "ops_add_markup_rate":
				try {
					String assetCode = null; String status = null;	
					String userId = null; String userType = null; 	boolean success = false; 
					JsonObject obj = new JsonObject(); PrintWriter out_json = response.getWriter(); 
					String errorMsg = "false"; Gson gson = new Gson(); 
					String onRampRate = null; String offRampRate = null; String fiatCurrency=null;
					
					 if(request.getParameter("selmarkupassetcode")!=null)	assetCode = StringUtils.trim( request.getParameter("selmarkupassetcode") );
					 if(request.getParameter("selmarkupfiatcurrency")!=null)	fiatCurrency = StringUtils.trim( request.getParameter("selmarkupfiatcurrency") );
					 if(request.getParameter("onramp_markuprate")!=null)	onRampRate = StringUtils.trim( request.getParameter("onramp_markuprate") );
					 if(request.getParameter("markup_burning_rate")!=null)	offRampRate = StringUtils.trim( request.getParameter("markup_burning_rate") );
					 if(request.getParameter("selmarkupstatus")!=null)	status = StringUtils.trim( request.getParameter("selmarkupstatus") );
					 
					
					 
					 String [] arrAssetCode = assetCode.split(",");
					 String [] arrFiatCurrency = fiatCurrency.split(",");
					 assetCode = arrAssetCode[0];
					 fiatCurrency = arrFiatCurrency[0];
					NeoBankEnvironment.setComment(3, className, "inside ops_add_markup_rate assetCode " + assetCode+ " onRampRate "
							+ onRampRate +" offRampRate "+offRampRate+" status "+status+ " fiatCurrency  "+fiatCurrency );
					
					userId=((User) session.getAttribute("SESS_USER")).getUserId();
					userType=((User) session.getAttribute("SESS_USER")).getUserType();

					success = (boolean)SystemUtilsDao.class.getConstructor().newInstance().addAssetPriceMarkupRate(
							assetCode,  onRampRate,  offRampRate,  status,fiatCurrency);

					if(success) {
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",
								StringUtils.substring("Created new Asset Price Markup " + assetCode, 0, 48));
						
		        		errorMsg = "false";
					    obj.add("message", gson.toJsonTree("Asset Pricing Markup rate Created successful")); 

		        	}else {
						errorMsg = "Error in creating asset pricing";

		        	}
					try {
						 obj.add("error", gson.toJsonTree(errorMsg)); 
						 NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
						 out_json.print( gson.toJson(obj));
					}finally {
						if(assetCode!=null) assetCode=null; if(userType!=null) userType=null; 
						if(status!=null) status=null; if(userId!=null) userId=null; 
						if(offRampRate!=null) offRampRate=null; if(onRampRate!=null) onRampRate=null; 
						if(obj!=null) obj = null; if(out_json!=null) out_json.close();
						if (fiatCurrency!=null)fiatCurrency=null;
						if (arrAssetCode!=null) arrAssetCode=null;
						if (arrFiatCurrency!=null)arrFiatCurrency=null;
					}
					
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Ops Create Asset Pricing failed, Please try again letter");
				}
				break;
						
		default:
			throw new IllegalArgumentException("Rules not defined value: " + rulesaction);
		}

		
	}
	
	



	

}
