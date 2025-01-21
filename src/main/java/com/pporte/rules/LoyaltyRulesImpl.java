package com.pporte.rules;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.CustomerPorteCoinDao;
import com.pporte.dao.LoyaltyDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.model.CryptoAssetCoins;
import com.pporte.model.Loyalty;
import com.pporte.model.User;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;

public class LoyaltyRulesImpl implements Rules{
 	private static String className=  LoyaltyRulesImpl.class.getSimpleName();
	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		HttpSession session = request.getSession(false);
		
		switch (rulesaction) {
		
		case "View Points":
			try {
				
				String langPref = null;
				if(request.getParameter("hdnlang")!=null)			  langPref = request.getParameter("hdnlang").trim();

				request.setAttribute("langPref", langPref);
				request.setAttribute("loyaltypointsdetails", (ArrayList<Loyalty>)LoyaltyDao.class.getConstructor().newInstance()
						.getLoyaltyTransactionsForUser ( ((User) session.getAttribute("SESS_USER")).getRelationshipNo()   ) );
				request.setAttribute("loyaltyrulesdesc", (ConcurrentHashMap<String, String>)LoyaltyDao.class.getConstructor().newInstance().getLoyaltyDescription() );
				request.setAttribute("lastaction", "custltly");	
				request.setAttribute("lastrule", "View Points");
				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerLoyaltyViewPage()).forward(request, response);
				}finally {
					if (langPref!=null)langPref=null;
				}
			}catch (Exception e) {
				Utilities.callException(request, response, ctx, e.getMessage());

			}
		break;	
		case "Redeem Points":
			try {
				String langPref = null;
				if(request.getParameter("hdnlang")!=null)			  langPref = request.getParameter("hdnlang").trim();

				request.setAttribute("langPref", langPref);
				request.setAttribute("loyaltypointsdetails", (ArrayList<Loyalty>)LoyaltyDao.class.getConstructor().newInstance()
						.getLoyaltyTransactionsForUser ( ((User) session.getAttribute("SESS_USER")).getRelationshipNo()   ) );
				request.setAttribute("loyaltypointsbalance", LoyaltyDao.class.getConstructor().newInstance()
						.getLoyaltyPointsBalance (((User)session.getAttribute("SESS_USER")).getRelationshipNo() ));
				request.setAttribute("loyaltyrulesdesc", (ConcurrentHashMap<String, String>)LoyaltyDao.class.getConstructor().newInstance().getLoyaltyDescription() );
				request.setAttribute("lastaction", "custltly");	
				request.setAttribute("lastrule", "Redeem Points");
				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerLoyaltyRedeemPage()).forward(request, response);
				}finally {
					if (langPref!=null)langPref=null;
				}
			}catch (Exception e) {
				Utilities.callException(request, response, ctx, e.getMessage());

			}
		break;
		
		case "Redeem Rates":
			try {
				String langPref = null;
				if(request.getParameter("hdnlang")!=null)	langPref = request.getParameter("hdnlang").trim();

				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opslyt");
				request.setAttribute("lastrule", "Redeem Rates");
				
				ArrayList<Loyalty> arrLoyaltyPricing = (ArrayList<Loyalty>) LoyaltyDao.class .getConstructor().newInstance().getLoyaltyConversion();
				request.setAttribute("loyaltyconversion", arrLoyaltyPricing);
				ArrayList<CryptoAssetCoins> arrAssets = (ArrayList<CryptoAssetCoins>) CustomerPorteCoinDao.class .getConstructor().newInstance().getPorteAssetDetails();
				request.setAttribute("assets", arrAssets);
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewLoyaltyRatesPage()).forward(request, response);
				 } finally {
					 if(arrLoyaltyPricing != null) arrLoyaltyPricing = null; if(langPref !=null) langPref = null;
					 if(arrAssets != null) arrAssets = null;
					 
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
		break;			
		
		default:
			throw new IllegalArgumentException("Rules not defined value: " + rulesaction);
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
			case "get_loyalty_points_mbl":
				try {
					JsonObject object = new JsonObject();String langPref=null;
					Gson gson = new Gson(); String tokenValue = null;
					String relationshipNo= null; ArrayList<Loyalty> arrLoyalty=null; String totalLoyalty=null;
					String redeemRateAndAsset=null;String destinationAsset=null;String redeemRate=null;String veslConvert=null;;
					if(request.getParameter("relno")!=null)	relationshipNo = request.getParameter("relno").trim();
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
					 arrLoyalty= (ArrayList<Loyalty>)LoyaltyDao.class.getConstructor().newInstance() .getLoyaltyTransactionsForUser(relationshipNo);
					 totalLoyalty= (String)LoyaltyDao.class.getConstructor().newInstance() .getTotalLoyalty(relationshipNo);
					 
					 
					//Get the redemption rates
					    redeemRateAndAsset  = (String)LoyaltyDao.class.getConstructor().newInstance().getRedeemRateAndDestination();
					    if(redeemRateAndAsset != null) {
					    	 String [] arrRedeemRateAndAsset = redeemRateAndAsset.split(",");
							  destinationAsset = arrRedeemRateAndAsset[0];
							  redeemRate = arrRedeemRateAndAsset[1];
					    }else {
					    	if(langPref.equalsIgnoreCase("ES")) {
								throw new Exception("Tarifas de canje de lealtad no encontradas");

							}else {
								throw new Exception("Loyalty Redeem Rates not Found ");
							}
					    }
					    if (totalLoyalty!=null) {
						double  amountRedeemed  = Double.parseDouble(totalLoyalty) * Double.parseDouble(redeemRate);
						veslConvert=String.valueOf(amountRedeemed);
					    }
					 if (arrLoyalty!=null) {
						 	object.add("error", gson.toJsonTree("false"));
							object.add("data", gson.toJsonTree(arrLoyalty));
							object.add("totalloyalty", gson.toJsonTree(totalLoyalty));
							object.add("convertedloyalty", gson.toJsonTree(veslConvert));
					 }else {
						 object.add("error", gson.toJsonTree("nodata"));
					 }
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(object));
						response.getWriter().print(object);
					}finally {
						response.getWriter().close();
						if(object!=null) object = null;if (tokenValue != null)tokenValue = null;
						if(arrLoyalty!=null) arrLoyalty = null;
						if(totalLoyalty!=null) totalLoyalty = null;
						if(gson!=null) gson = null;
						if(langPref!=null) langPref = null;
						if(relationshipNo!=null) relationshipNo = null; if (destinationAsset!=null)destinationAsset=null;
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					//Utilities.sendJsonResponse(response, "error", " failed, Please try again letter");
				}	
		    break;
					
					
					
					
			case "opsaddloyaltyconvrates": 				
				try {
					JsonObject object = new JsonObject();
					Gson gson = new Gson(); String status= null;  String conversionRate= null; String errorMsg = "true";
					String destAsset= null; String userId = null;
					String userType = null; boolean success = false; PrintWriter out_json = response.getWriter();
					if(request.getParameter("hdnadddestasset")!=null)	destAsset = request.getParameter("hdnadddestasset").trim();
					if(request.getParameter("addconversionrate")!=null)	conversionRate = request.getParameter("addconversionrate").trim();
					if(request.getParameter("hdnaddconversionstatus")!=null)	status = request.getParameter("hdnaddconversionstatus").trim();
					 String [] arrAssetCode = destAsset.split(",");
					 destAsset = arrAssetCode[0]; 					
					userId=((User) session.getAttribute("SESS_USER")).getUserId();
					userType=((User) session.getAttribute("SESS_USER")).getUserType();

					success = (boolean)LoyaltyDao.class.getConstructor().newInstance().addNewLoyaltyRedeemConversion(destAsset, conversionRate, status);

					if(success) {
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",
								StringUtils.substring("Created new Loyalty Conversion to " + destAsset, 0, 48));
						
		        		errorMsg = "false";
		        		object.add("message", gson.toJsonTree("Loyalty Conversion Created successful")); 

		        	}else {
						errorMsg = "Failed to create Loyalty Conversion";
		        	}
					
					try {
						object.add("error", gson.toJsonTree(errorMsg)); 
						 out_json.print( gson.toJson(object));
					}finally {
						response.getWriter().close(); out_json.close();
						if(object!=null) object = null;  if(conversionRate!=null) conversionRate = null;
					     if(status!=null) status = null; if(userId!=null) userId = null;
						if(gson!=null) gson = null; if(errorMsg!=null) errorMsg = null;
						if(destAsset !=null) destAsset=null; if(userType !=null) userType=null;
												
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Create Redeem Rate failed, Please try again letter");

				}
				break;
				
			case "opseditloyaltyconversionrates": 				
				try {
					JsonObject object = new JsonObject();
					Gson gson = new Gson(); String status= null;  String conversionRate= null; String errorMsg = "true";
					String destAsset= null; String userId = null;  String seqNo= null; 
					String userType = null; boolean success = false; PrintWriter out_json = response.getWriter();
					if(request.getParameter("editdestasset")!=null)	destAsset = request.getParameter("editdestasset").trim();
					if(request.getParameter("editconversionrate")!=null)	conversionRate = request.getParameter("editconversionrate").trim();
					if(request.getParameter("hdneditconversionstatus")!=null)	status = request.getParameter("hdneditconversionstatus").trim();
					if(request.getParameter("hdnsequenceno")!=null)	seqNo = request.getParameter("hdnsequenceno").trim();
					 					
					NeoBankEnvironment.setComment(3, className, " destAsset "+destAsset+" conversionRate "+conversionRate + " status "+status +" seqNo "+ seqNo);

					userId=((User) session.getAttribute("SESS_USER")).getUserId();
					userType=((User) session.getAttribute("SESS_USER")).getUserType();

					success = (boolean)LoyaltyDao.class.getConstructor().newInstance().EditLoyaltyRedeemConversion(destAsset, conversionRate, status,seqNo);

					if(success) {
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",
								StringUtils.substring("Updated Loyalty Conversion " + destAsset, 0, 48));
						
		        		errorMsg = "false";
		        		object.add("message", gson.toJsonTree("Loyalty Conversion Updated successful")); 

		        	}else {
						errorMsg = "Failed to Update Loyalty Conversion";
		        	}
					
					try {
						object.add("error", gson.toJsonTree(errorMsg)); 
						 out_json.print( gson.toJson(object));
					}finally {
						response.getWriter().close(); out_json.close();
						if(object!=null) object = null;  if(conversionRate!=null) conversionRate = null;
					     if(status!=null) status = null; if(userId!=null) userId = null;
						if(gson!=null) gson = null; if(errorMsg!=null) errorMsg = null;
						if(destAsset !=null) destAsset=null; if(userType !=null) userType=null;
												
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Update Redeem Rate failed, Please try again letter");

				}
				break;
				
			case "custredeemallpoints": 				
				try {
					JsonObject object = new JsonObject();
					Gson gson = new Gson(); String status= null;  String pointsBalance= null; String errorMsg = "true";
					String relationshipNo= null; String userId = null;  String seqNo= null; String payMode= null; 
					String userType = null; boolean success = false; PrintWriter out_json = response.getWriter();
					String claimedTxnRef= null;  String redeemRateAndAsset = null; String destinationAsset = null;
					String redeemRate = null;String referenceNo = null; String txnPayMode = null; String customerCharges = null;
					String payComments = null; String txnUserCode = null; String walletId = null;
					String langPref=null;
					
					if(request.getParameter("hdnlang")!=null)			langPref = request.getParameter("hdnlang").trim();
					if(request.getParameter("relno")!=null)	relationshipNo = request.getParameter("relno").trim();
					if(request.getParameter("pointsbalance")!=null)	pointsBalance = request.getParameter("pointsbalance").trim();
					 
					relationshipNo=((User) session.getAttribute("SESS_USER")).getRelationshipNo();

					NeoBankEnvironment.setComment(3, className, "in custredeemallpoints relationshipNo "+relationshipNo+" pointsBalance "+pointsBalance );

					/**check if  the points are there*/
					String pointsAcrued  = (String)LoyaltyDao.class.getConstructor().newInstance().getLoyaltyPointsBalance(relationshipNo);
					
					if(Double.parseDouble(pointsAcrued) <= 0 ) {
						throw new Exception("No Points Accruered");
					}
					pointsBalance = pointsAcrued;

					//Get the redemption rates
				    redeemRateAndAsset  = (String)LoyaltyDao.class.getConstructor().newInstance().getRedeemRateAndDestination();
				    if(redeemRateAndAsset != null) {
				    	 String [] arrRedeemRateAndAsset = redeemRateAndAsset.split(",");
						  destinationAsset = arrRedeemRateAndAsset[0];
						  redeemRate = arrRedeemRateAndAsset[1];
				    }else {
						throw new Exception("Loyalty Redeem Rates not Found ");
				    }
				    		
				    //Select the wallet Id to claim loyalty
				    walletId  = (String)LoyaltyDao.class.getConstructor().newInstance().getWalletIdForAsset(destinationAsset,relationshipNo);				    
				    if(walletId ==null) {
				    	 if (langPref.equals("EN")) {
				    		 Utilities.sendJsonResponse(response, "error", "You do not have VESL wallet kindly create one and redeem again");
								return;
							}else if ((langPref.equalsIgnoreCase("ES"))) {
								Utilities.sendJsonResponse(response, "error", "No tiene una billetera VESL, cree una y vuelva a canjearla");
								return;
							}
						
				    }
				    //Get the Asset to send the converted points
					double  amountRedeemed  = Double.parseDouble(pointsBalance) * Double.parseDouble(redeemRate);
				    
					txnPayMode = NeoBankEnvironment.getCodeLoyaltyRedemption();
				    referenceNo = txnPayMode+ "-" + (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
							+ Utilities.genAlphaNumRandom(9);
					customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
							NeoBankEnvironment.getDefaultCustomerUserType(),txnPayMode, String.valueOf(amountRedeemed) );
					payComments = "Loyalty Redemption";
					txnUserCode = Utilities.generateTransactionCode(10);
				    
					success = (Boolean) LoyaltyDao.class.getConstructor().newInstance().custPerformLoyaltyClaimAllPoints(relationshipNo,String.valueOf(amountRedeemed), payComments, 
							 referenceNo, txnUserCode, customerCharges, txnPayMode, destinationAsset, pointsBalance, walletId);
					
					userId=((User) session.getAttribute("SESS_USER")).getRelationshipNo();
					userType=((User) session.getAttribute("SESS_USER")).getUserType();

					if(success) {
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",
								StringUtils.substring("Customer Redeem Loyalty " + relationshipNo, 0, 48));
						
		        		errorMsg = "false";
		        		
				    	if (langPref.equals("EN")) {
						    object.add("message", gson.toJsonTree("You have Redeemed "+pointsBalance +" Points to "+amountRedeemed+" VESL"));					
						}else if ((langPref.equalsIgnoreCase("ES"))) {
							object.add("message", gson.toJsonTree("Has canjeado "+pointsBalance +" Puntos a "+amountRedeemed+" VESL"));
						}		        		 
		        	}else {
		        		 if (langPref.equals("EN")) {
		        			    errorMsg = "Failed to Redeem Loyalty";
							}else if ((langPref.equalsIgnoreCase("ES"))) {
								errorMsg = "Failed to Redeem Loyalty";
							}
						
		        	}
					
					try {
						object.add("error", gson.toJsonTree(errorMsg)); 
						 out_json.print( gson.toJson(object));
					}finally {						
						response.getWriter().close(); out_json.close(); if(walletId !=null) walletId=null;
						if(object!=null) object = null;  if(pointsBalance!=null) pointsBalance = null;
					     if(status!=null) status = null; if(userId!=null) userId = null;if(payComments !=null) payComments=null;
						if(gson!=null) gson = null; if(errorMsg!=null) errorMsg = null;if(customerCharges !=null) customerCharges=null;
						if(relationshipNo !=null) relationshipNo=null; if(userType !=null) userType=null;
						if(seqNo !=null) seqNo=null; if(payMode !=null) payMode=null;if(txnUserCode !=null) txnUserCode=null;
						if(claimedTxnRef !=null) claimedTxnRef=null; if(redeemRateAndAsset !=null) redeemRateAndAsset=null;
						if(destinationAsset !=null) destinationAsset=null; if(redeemRate !=null) redeemRate=null;
						if(referenceNo !=null) referenceNo=null; if(txnPayMode !=null) txnPayMode=null;if (langPref!=null)langPref=null;
																		
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Redeem Loyalty failed, Please try again letter");

				}
				break;
				
			case "custredeemspecificpoints": 				
				try {
					JsonObject object = new JsonObject();
					Gson gson = new Gson(); String status= null;  String pointsBalance= null; String errorMsg = "true";
					String relationshipNo= null; String userId = null;  String seqNo= null; String payMode= null; 
					String userType = null; boolean success = false; PrintWriter out_json = response.getWriter();
					String claimedTxnRef= null;  String redeemRateAndAsset = null; String destinationAsset = null;
					String redeemRate = null;String referenceNo = null; String txnPayMode = null; String customerCharges = null;
					String payComments = null; String txnUserCode = null; String walletId = null;
					String langPref=null;
					if(request.getParameter("hdnlang")!=null)			langPref = request.getParameter("hdnlang").trim();
					if(request.getParameter("acruedpoints")!=null)	pointsBalance = request.getParameter("acruedpoints").trim();
					if(request.getParameter("seqno")!=null)	seqNo = request.getParameter("seqno").trim();
					if(request.getParameter("paymode")!=null)	payMode = request.getParameter("paymode").trim();
					if(request.getParameter("systemRef")!=null)	claimedTxnRef = request.getParameter("systemRef").trim();
					 	
					NeoBankEnvironment.setComment(3,className,"Lang pref is "+langPref);
					relationshipNo=((User) session.getAttribute("SESS_USER")).getRelationshipNo();

					NeoBankEnvironment.setComment(3, className, "in custredeemspecificpoints  relationshipNo "+relationshipNo+" pointsBalance "+pointsBalance
							+" seqNo "+seqNo + " payMode "+ payMode+ " claimedTxnRef "+ claimedTxnRef+ " walletId "+ walletId);

					/**check if  the txn loyalty has already been claimed*/
					String pointsStatus  = (String)LoyaltyDao.class.getConstructor().newInstance().checkTxnLoyaltyStatus(claimedTxnRef);
					if(pointsStatus == null) {
						throw new Exception("Loyalty points Status not found ");
					}
					if(pointsStatus.equals("C")) {
						throw new Exception("Loyalty points Already claimed......not allowed");

					}
					
					//Get the redemption rates
				    redeemRateAndAsset  = (String)LoyaltyDao.class.getConstructor().newInstance().getRedeemRateAndDestination();
				    if(redeemRateAndAsset != null) {
				    	 String [] arrRedeemRateAndAsset = redeemRateAndAsset.split(",");
						  destinationAsset = arrRedeemRateAndAsset[0];
						  redeemRate = arrRedeemRateAndAsset[1];
				    }else {
						throw new Exception("Loyalty Redeem Rates not Found ");
				    }
				    
				    walletId  = (String)LoyaltyDao.class.getConstructor().newInstance().getWalletIdForAsset(destinationAsset,relationshipNo);				    
				    if(walletId ==null) {
				    	if (langPref.equals("EN")) {
				    		 Utilities.sendJsonResponse(response, "error", "You do not have VESL wallet kindly create one and redeem again");
								return;
							}else if ((langPref.equalsIgnoreCase("ES"))) {
								Utilities.sendJsonResponse(response, "error", "No tiene una billetera VESL, cree una y vuelva a canjearla");
								return;
							}
				    }				    

				    //Get the Asset to send the converted points
					double  amountRedeemed  = Double.parseDouble(pointsBalance) * Double.parseDouble(redeemRate);
				    
					txnPayMode = NeoBankEnvironment.getCodeLoyaltyRedemption();
				    referenceNo = txnPayMode+ "-" + (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
							+ Utilities.genAlphaNumRandom(9);
					customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
							NeoBankEnvironment.getDefaultCustomerUserType(),txnPayMode, String.valueOf(amountRedeemed) );
					payComments = "Loyalty Redemption";
					txnUserCode = Utilities.generateTransactionCode(10);
				    
					success = (Boolean) LoyaltyDao.class.getConstructor().newInstance().custPerformLoyaltyClaimForEach(relationshipNo,String.valueOf(amountRedeemed), payComments, 
							 referenceNo, txnUserCode, customerCharges, txnPayMode, destinationAsset, seqNo, claimedTxnRef, pointsBalance, walletId);
					
					userId=((User) session.getAttribute("SESS_USER")).getRelationshipNo();
					userType=((User) session.getAttribute("SESS_USER")).getUserType();

					if(success) {
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",
								StringUtils.substring("Customer Redeem Loyalty " + relationshipNo, 0, 48));
						
		        		errorMsg = "false";
		        		if (langPref.equals("EN")) {
						    object.add("message", gson.toJsonTree("You have Redeemed "+pointsBalance +" Points to "+amountRedeemed+" VESL"));					
						}else if ((langPref.equalsIgnoreCase("ES"))) {
							object.add("message", gson.toJsonTree("Has canjeado "+pointsBalance +" Puntos a "+amountRedeemed+" VESL"));
						}	
		        	}else {
		        		 if (langPref.equals("EN")) {
		        			 errorMsg = "Failed to Redeem Loyalty";
							}else if ((langPref.equalsIgnoreCase("ES"))) {
								errorMsg = "Failed to Redeem Loyalty";
							}
		        	}
					
					try {
						object.add("error", gson.toJsonTree(errorMsg)); 
						 out_json.print( gson.toJson(object));
					}finally {						
						response.getWriter().close(); out_json.close(); if(walletId !=null) walletId=null;
						if(object!=null) object = null;  if(pointsBalance!=null) pointsBalance = null;
					     if(status!=null) status = null; if(userId!=null) userId = null;if(payComments !=null) payComments=null;
						if(gson!=null) gson = null; if(errorMsg!=null) errorMsg = null;if(customerCharges !=null) customerCharges=null;
						if(relationshipNo !=null) relationshipNo=null; if(userType !=null) userType=null;
						if(seqNo !=null) seqNo=null; if(payMode !=null) payMode=null;if(txnUserCode !=null) txnUserCode=null;
						if(claimedTxnRef !=null) claimedTxnRef=null; if(redeemRateAndAsset !=null) redeemRateAndAsset=null;
						if(destinationAsset !=null) destinationAsset=null; if(redeemRate !=null) redeemRate=null;
						if(referenceNo !=null) referenceNo=null; if(txnPayMode !=null) txnPayMode=null;if (langPref!=null)langPref=null;	
																		
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Redeem Loyalty failed, Please try again letter");

				}
				break;
			case"custmobileredeemspecificpoints":
				try {
					JsonObject object = new JsonObject(); String tokenValue = null;String langPref=null;
					Gson gson = new Gson(); String status= null;  String pointsBalance= null; String errorMsg = "true";
					String relationshipNo= null; String seqNo= null; String userId=null;
					String userType = "C"; boolean success = false; PrintWriter out_json = response.getWriter();
					 String redeemRateAndAsset = null; String destinationAsset = null;
					String redeemRate = null;String referenceNo = null; String txnPayMode = null; String customerCharges = null;
					String payComments = null; String txnUserCode = null; String walletId = null;
					String totalLoyalty=null;
					ArrayList<Loyalty> arrLoyalty=null; String veslConvert=null;
					
					if(request.getParameter("acruedpoints")!=null)	pointsBalance = request.getParameter("acruedpoints").trim();
					if(request.getParameter("seqno")!=null)	seqNo = request.getParameter("seqno").trim();
					if(request.getParameter("relno")!=null)	relationshipNo = request.getParameter("relno").trim();
					if(request.getParameter("userid")!=null)	userId = request.getParameter("userid").trim();
					if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
					//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
					if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

					//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
					if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
						NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v lido, vuelva a iniciar sesi n");

						}else {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
						}
						return;
					}
	
					NeoBankEnvironment.setComment(3, className, "in custredeemspecificpoints  relationshipNo "+relationshipNo+" pointsBalance "+pointsBalance
							+" seqNo "+seqNo + " walletId "+ walletId);

					/**check if  the txn loyalty has already been claimed*/
					String pointsStatus  = (String)LoyaltyDao.class.getConstructor().newInstance().checkMobileTxnLoyaltyStatus(seqNo);
					if(pointsStatus == null) {
						if(langPref.equalsIgnoreCase("ES")) {
							throw new Exception("Puntos de fidelidad Estado no encontrado ");

						}else {
							throw new Exception("Loyalty points Status not found ");
						}
					}
					if(pointsStatus.equals("C")) {
						if(langPref.equalsIgnoreCase("ES")) {
							throw new Exception("Puntos de fidelidad Ya reclamados......no permitidos");

						}else {
							throw new Exception("Loyalty points Already claimed......not allowed");
						}

					}
					//Get the redemption rates
					
				    redeemRateAndAsset  = (String)LoyaltyDao.class.getConstructor().newInstance().getRedeemRateAndDestination();
				    if(redeemRateAndAsset != null) {
				    	 String [] arrRedeemRateAndAsset = redeemRateAndAsset.split(",");
						  destinationAsset = arrRedeemRateAndAsset[0];
						  redeemRate = arrRedeemRateAndAsset[1];
				    }else {
				    	if(langPref.equalsIgnoreCase("ES")) {
							throw new Exception("Tarifas de canje de lealtad no encontradas ");

						}else {
							throw new Exception("Loyalty Redeem Rates not Found ");
						}
				    }
				    walletId  = (String)LoyaltyDao.class.getConstructor().newInstance().getWalletIdForAsset(destinationAsset,relationshipNo);				    
				    if(walletId ==null) {
				    	if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "error", "No tiene una billetera VESL, cree una y vuelva a canjearla");

						}else {
							Utilities.sendJsonResponse(response, "error", "You do not have VESL wallet kindly create one and redeem again");
						}
						return;
				    }				    
				    //Get the Asset to send the converted points
					double  amountRedeemed  = Double.parseDouble(pointsBalance) * Double.parseDouble(redeemRate);
				    
					txnPayMode = NeoBankEnvironment.getCodeLoyaltyRedemption();
				    referenceNo = txnPayMode+ "-" + (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
							+ Utilities.genAlphaNumRandom(9);
					customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
							NeoBankEnvironment.getDefaultCustomerUserType(),txnPayMode, String.valueOf(amountRedeemed) );
					payComments = "Loyalty Redemption";
					txnUserCode = Utilities.generateTransactionCode(10);
				    
					success = (Boolean) LoyaltyDao.class.getConstructor().newInstance().custMobilePerformLoyaltyClaimForEach(relationshipNo,String.valueOf(amountRedeemed), payComments, 
							 referenceNo, txnUserCode, customerCharges, txnPayMode, destinationAsset, seqNo, pointsBalance, walletId);
					

					if(success) {
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",
								StringUtils.substring("Customer Redeem Loyalty " + relationshipNo, 0, 48));
						
		        		errorMsg = "false";
		        		if(langPref.equalsIgnoreCase("ES")) {
			        		object.add("message", gson.toJsonTree("Redimido exitosamente "+pointsBalance +" Puntos a "+amountRedeemed + " " +destinationAsset)); 

						}else {
			        		object.add("message", gson.toJsonTree("Successfully Redeemed "+pointsBalance +" Points to "+amountRedeemed + " " +destinationAsset)); 
						}
		        		// Get redeemed loyalty
		        		
		        		arrLoyalty= (ArrayList<Loyalty>)LoyaltyDao.class.getConstructor().newInstance() .getLoyaltyTransactionsForUser(relationshipNo);
						totalLoyalty= (String)LoyaltyDao.class.getConstructor().newInstance() .getTotalLoyalty(relationshipNo);
						
					    if (totalLoyalty!=null) {
						double  newamountRedeemed  = Double.parseDouble(totalLoyalty) * Double.parseDouble(redeemRate);
						veslConvert=String.valueOf(newamountRedeemed);
					    }
					    if (arrLoyalty!=null) {
							object.add("data", gson.toJsonTree(arrLoyalty));
							object.add("totalloyalty", gson.toJsonTree(totalLoyalty));
							object.add("convertedloyalty", gson.toJsonTree(veslConvert));
							object.add("loyalty", gson.toJsonTree("present"));
						 }else {
							 object.add("loyalty", gson.toJsonTree("nodata"));
						 }

		        	}else {
		        		if(langPref.equalsIgnoreCase("ES")) {
							errorMsg = "No se pudo canjear la lealtad";

						}else {
							errorMsg = "Failed to Redeem Loyalty";
						}
		        	}
					
					try {
						object.add("error", gson.toJsonTree(errorMsg)); 
						 out_json.print( gson.toJson(object));
					}finally {						
						response.getWriter().close(); out_json.close(); if(walletId !=null) walletId=null;
						if(object!=null) object = null;  if(pointsBalance!=null) pointsBalance = null;
					     if(status!=null) status = null; if(userId!=null) userId = null;if(payComments !=null) payComments=null;
						if(gson!=null) gson = null; if(errorMsg!=null) errorMsg = null;if(customerCharges !=null) customerCharges=null;
						if(relationshipNo !=null) relationshipNo=null; if(userType !=null) userType=null;
						if(seqNo !=null) seqNo=null;if(txnUserCode !=null) txnUserCode=null;
						if(redeemRateAndAsset !=null) redeemRateAndAsset=null; if (tokenValue != null)tokenValue = null;
						if(destinationAsset !=null) destinationAsset=null; if(redeemRate !=null) redeemRate=null;
						if(referenceNo !=null) referenceNo=null; if(txnPayMode !=null) txnPayMode=null;
						if(langPref !=null) langPref=null;
						if (veslConvert!=null)veslConvert=null;
						if(arrLoyalty!=null) arrLoyalty = null;
						if(totalLoyalty!=null) totalLoyalty = null;											
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Redeem Loyalty failed, Please try again letter");

				}
				break;
			case "custmobileredeemallpoints": 				
				try {
					JsonObject object = new JsonObject();String tokenValue = null;String langPref=null;
					Gson gson = new Gson(); String status= null;  String pointsBalance= null; String errorMsg = "true";
					String relationshipNo= null; String userId = null;  String seqNo= null; String payMode= null; 
					String userType = "C"; boolean success = false; PrintWriter out_json = response.getWriter();
					String claimedTxnRef= null;  String redeemRateAndAsset = null; String destinationAsset = null;
					String redeemRate = null;String referenceNo = null; String txnPayMode = null; String customerCharges = null;
					String payComments = null; String txnUserCode = null; String walletId = null;String totalLoyalty=null;
					ArrayList<Loyalty> arrLoyalty=null; String veslConvert=null;
					
					if(request.getParameter("relno")!=null)	relationshipNo = request.getParameter("relno").trim();
					if(request.getParameter("pointsbalance")!=null)	pointsBalance = request.getParameter("pointsbalance").trim();
					if(request.getParameter("relno")!=null)	relationshipNo = request.getParameter("relno").trim();
					if(request.getParameter("userid")!=null)	userId = request.getParameter("userid").trim();
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

					NeoBankEnvironment.setComment(3, className, "in custredeemallpoints relationshipNo "+relationshipNo+" pointsBalance "+pointsBalance );

					/**check if  the points are there*/
					String pointsAcrued  = (String)LoyaltyDao.class.getConstructor().newInstance().getLoyaltyPointsBalance(relationshipNo);
					
					if(Double.parseDouble(pointsAcrued) <= 0 ) {
						if(langPref.equalsIgnoreCase("ES")) {
							throw new Exception("Sin puntos acumulados");

						}else {
							throw new Exception("No Points Accrued");
						}
					}
					pointsBalance = pointsAcrued;

					//Get the redemption rates
				    redeemRateAndAsset  = (String)LoyaltyDao.class.getConstructor().newInstance().getRedeemRateAndDestination();
				    if(redeemRateAndAsset != null) {
				    	 String [] arrRedeemRateAndAsset = redeemRateAndAsset.split(",");
						  destinationAsset = arrRedeemRateAndAsset[0];
						  redeemRate = arrRedeemRateAndAsset[1];
				    }else {
				    	if(langPref.equalsIgnoreCase("ES")) {
							throw new Exception("Tarifas de canje de lealtad no encontradas");

						}else {
							throw new Exception("Loyalty Redeem Rates not Found ");
						}
				    }
				    NeoBankEnvironment.setComment(3, className, "destinationAsset id is "+destinationAsset);
	
				    //Select the wallet Id to claim loyalty
				    walletId  = (String)LoyaltyDao.class.getConstructor().newInstance().getWalletIdForAsset(destinationAsset,relationshipNo);				    
				    if(walletId ==null) {
				    	if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "error", "No tiene una billetera VEAL, por favor cree una y canjee nuevamente");

						}else {
							Utilities.sendJsonResponse(response, "error", "You do not have VESL wallet kindly create one and redeem again");
						}
						return;
				    }	
				    //Get the Asset to send the converted points
					double  amountRedeemed  = Double.parseDouble(pointsBalance) * Double.parseDouble(redeemRate);
				    
					txnPayMode = NeoBankEnvironment.getCodeLoyaltyRedemption();
				    referenceNo = txnPayMode+ "-" + (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
							+ Utilities.genAlphaNumRandom(9);
					customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
							NeoBankEnvironment.getDefaultCustomerUserType(),txnPayMode, String.valueOf(amountRedeemed) );
					payComments = "Loyalty Redemption";
					txnUserCode = Utilities.generateTransactionCode(10);
				    
					success = (Boolean) LoyaltyDao.class.getConstructor().newInstance().custPerformLoyaltyClaimAllPoints(relationshipNo,String.valueOf(amountRedeemed), payComments, 
							 referenceNo, txnUserCode, customerCharges, txnPayMode, destinationAsset, pointsBalance, walletId);
					

					if(success) {
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, userType, "O",
								StringUtils.substring("Customer Redeem Loyalty " + relationshipNo, 0, 48));
						
		        		errorMsg = "false";
		        		if(langPref.equalsIgnoreCase("ES")) {
			        		object.add("message", gson.toJsonTree("Redimido exitosamente "+pointsBalance +" Puntos a"+amountRedeemed + " " +redeemRateAndAsset)); 

						}else {
			        		object.add("message", gson.toJsonTree("Successfully Redeemed "+pointsBalance +" Points to "+amountRedeemed + " " +redeemRateAndAsset)); 
						}

		        		// Get redeemed loyalty
		        		
		        		arrLoyalty= (ArrayList<Loyalty>)LoyaltyDao.class.getConstructor().newInstance() .getLoyaltyTransactionsForUser(relationshipNo);
						totalLoyalty= (String)LoyaltyDao.class.getConstructor().newInstance() .getTotalLoyalty(relationshipNo);
						
					    if (totalLoyalty!=null) {
						double  newamountRedeemed  = Double.parseDouble(totalLoyalty) * Double.parseDouble(redeemRate);
						veslConvert=String.valueOf(newamountRedeemed);
					    }
					    if (arrLoyalty!=null) {
							object.add("data", gson.toJsonTree(arrLoyalty));
							object.add("totalloyalty", gson.toJsonTree(totalLoyalty));
							object.add("convertedloyalty", gson.toJsonTree(veslConvert));
							object.add("loyalty", gson.toJsonTree("present"));
						 }else {
							 object.add("loyalty", gson.toJsonTree("nodata"));
						 }
		        	}else {
		        		if(langPref.equalsIgnoreCase("ES")) {
							errorMsg = "No se pudo canjear la lealtad";

						}else {
							errorMsg = "Failed to Redeem Loyalty";
						}
		        	}

					try {
						object.add("error", gson.toJsonTree(errorMsg)); 
						 out_json.print( gson.toJson(object));
					}finally {						
						response.getWriter().close(); out_json.close(); if(walletId !=null) walletId=null;
						if(object!=null) object = null;  if(pointsBalance!=null) pointsBalance = null;
					     if(status!=null) status = null; if(userId!=null) userId = null;if(payComments !=null) payComments=null;
						if(gson!=null) gson = null; if(errorMsg!=null) errorMsg = null;if(customerCharges !=null) customerCharges=null;
						if(relationshipNo !=null) relationshipNo=null; if(userType !=null) userType=null;
						if(seqNo !=null) seqNo=null; if(payMode !=null) payMode=null;if(txnUserCode !=null) txnUserCode=null;
						if(claimedTxnRef !=null) claimedTxnRef=null; if(redeemRateAndAsset !=null) redeemRateAndAsset=null;
						if(destinationAsset !=null) destinationAsset=null; if(redeemRate !=null) redeemRate=null;
						if(referenceNo !=null) referenceNo=null; if(txnPayMode !=null) txnPayMode=null; if (tokenValue != null)tokenValue = null;
						if(langPref !=null) langPref=null;
																		
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Redeem Loyalty failed, Please try again letter");

				}
				break;
				
			case"loyalty_points_conversion":
				try {
					String pointBalance=null;String redeemRate=null;String veslConvert=null;
					String redeemRateAndAsset=null;String destinationAsset=null;
					JsonObject object = new JsonObject();Gson gson = new Gson();
					if(request.getParameter("pointbalance")!=null)	pointBalance = request.getParameter("pointbalance").trim();
					
					//Get the redemption rates
				    redeemRateAndAsset  = (String)LoyaltyDao.class.getConstructor().newInstance().getRedeemRateAndDestination();
				    if(redeemRateAndAsset != null) {
				    	 String [] arrRedeemRateAndAsset = redeemRateAndAsset.split(",");
						  destinationAsset = arrRedeemRateAndAsset[0];
						  redeemRate = arrRedeemRateAndAsset[1];
				    }else {
						throw new Exception("Loyalty Redeem Rates not Found ");
				    }
				    if (pointBalance!=null) {
						double  amountRedeemed  = Double.parseDouble(pointBalance) * Double.parseDouble(redeemRate);
						veslConvert=String.valueOf(amountRedeemed);
						object.add("error", gson.toJsonTree("false"));
						object.add("data", gson.toJsonTree(veslConvert));
				    }else {
				    	object.add("error", gson.toJsonTree("nodata"));
				    }try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(object));
						response.getWriter().print(object);
					}finally {
						response.getWriter().close();
						if(object!=null) object = null;
						if(gson!=null) gson = null;
						if (destinationAsset!=null)destinationAsset=null;
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
					throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
				}
				break;
			default:
				throw new IllegalArgumentException("Rule not defined value: " + rulesaction);
					
					
		}
	}

}
