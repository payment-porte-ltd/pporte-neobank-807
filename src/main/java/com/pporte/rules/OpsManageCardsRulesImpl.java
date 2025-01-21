package com.pporte.rules;

import java.util.ArrayList;
import java.util.List;

import com.pporte.NeoBankEnvironment;
import com.pporte.dao.OpsManageCardsDao;
import com.pporte.model.CardDetails;
import com.pporte.model.Customer;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class OpsManageCardsRulesImpl implements Rules {
	private static String className = OpsManageCardsRulesImpl.class.getSimpleName();
	@Override
	public void performJSONOperation(String arg0, HttpServletRequest arg1, HttpServletResponse arg2,
			ServletContext arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response, 
			ServletContext ctx)
			throws Exception {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null) 
			Utilities.callException(request, response, ctx, "Session has expired, please log in again");
		switch (rulesaction){
		case "View Cards":
			try {
				String langPref = null; ArrayList <Customer> arrCustomerDetails = null;
				if(request.getParameter("hdnlangpref")!=null) langPref = request.getParameter("hdnlangpref").trim();
				
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opscrd");
				request.setAttribute("lastrule", "View Cards");
				NeoBankEnvironment.setComment(3, className, "Before dao ");
				
				arrCustomerDetails = (ArrayList<Customer>)OpsManageCardsDao.class.getConstructor().newInstance().getCustomersWithCards();
				
				//List<Customer> customers = (List<Customer>)OpsManageCardsDao.class.getConstructor().newInstance().getCustomersWithCards();
				NeoBankEnvironment.setComment(3, className, "After dao");

				request.setAttribute("allcustomers", arrCustomerDetails);
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsVieCustomerTokenizedCardsPage()).forward(request, response);
				} finally {
					if(arrCustomerDetails !=null) arrCustomerDetails = null;	
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
			case "gettokinezedcards":
				try {
					String langPref = null; String relationshipNo = null;
					if(request.getParameter("hdnlangpref")!=null) langPref = request.getParameter("hdnlangpref").trim();
					if(request.getParameter("hdnrelno")!=null) relationshipNo = request.getParameter("hdnrelno").trim();
					
					request.setAttribute("langPref", langPref);
					request.setAttribute("lastaction", "opscrd");
					request.setAttribute("lastrule", "View Cards");
					List<CardDetails> listCardDetails = (List<CardDetails>)OpsManageCardsDao.class.getConstructor().newInstance().getTokenizedCards(relationshipNo);
					request.setAttribute("listCardDetails", listCardDetails);
					response.setContentType("text/html");
					 try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewSpecificCustomerTokenizedCardsPage()).forward(request, response);
					} finally {
						if(listCardDetails !=null) listCardDetails = null;	
						if(relationshipNo !=null) relationshipNo = null;	
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
				}
			
			break;
			case"view_more_card_details":
				try {
					NeoBankEnvironment.setComment(3, className, "insude view_more_card_details ");

					String langPref = null;
					String customerRelNo = ""; String customerId = ""; String custPhoneNo = ""; String custName=""; 
				   // String userId = null; String userType = null; 
				    ArrayList <Customer> arrCustomerDetails = null;
				    

					if (request.getParameter("hdnlangpref") != null)    	langPref = request.getParameter("hdnlangpref").trim();
					if (request.getParameter("searchbyrelno") != null) 		customerRelNo = request.getParameter("searchbyrelno").trim();
					if (request.getParameter("searchbycustid") != null) 		customerId = request.getParameter("searchbycustid").trim();
					if (request.getParameter("searchmobileno") != null) 		custPhoneNo = request.getParameter("searchmobileno").trim();
					if (request.getParameter("searchbycustname") != null) 		custName = request.getParameter("searchbycustname").trim();
					
					NeoBankEnvironment.setComment(3, className, "insude customerRelNo " + customerRelNo + " customerId " + customerId + 
							" custPhoneNo "+ custPhoneNo + " custName "+ custName);

					try {
						if(customerRelNo.equals("") && customerId.equals("") &&custPhoneNo.equals("") && custName.equals("")) {
							throw new Exception("All Search criteria cannot be blank");
						}
												
						//userId=((User) session.getAttribute("SESS_USER")).getUserId();
						//userType=((User) session.getAttribute("SESS_USER")).getUserType();
						
						NeoBankEnvironment.setComment(3, className, "insude customerRelNo " + customerRelNo + " customerId " + customerId + 
								" custPhoneNo "+ custPhoneNo + " custName "+ custName);


						arrCustomerDetails = (ArrayList<Customer>)OpsManageCardsDao.class.getConstructor().newInstance().getSearchOpsSpecificCardCustomerDetails(
								custName, customerRelNo,customerId,custPhoneNo);


						if(arrCustomerDetails !=null && arrCustomerDetails.size()>0 ) {
							NeoBankEnvironment.setComment(3, className, "customer found "+ arrCustomerDetails.size());											
						}else {
							NeoBankEnvironment.setComment(3, className, "Exception is customer details not available ");										

						}
						
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, "Exception is customer search "+e.getMessage());											
					}
						request.setAttribute("langPref", langPref);
						request.setAttribute("lastaction", "opscrd");
						request.setAttribute("lastrule", "View Cards");
						request.setAttribute("allcustomers", arrCustomerDetails);
						response.setContentType("text/html");
						
					try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getOpsVieCustomerTokenizedCardsPage()).forward(request, response);
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, "Exception in calling RequestDispatcher is "+e.getMessage());
					}finally {
						if(arrCustomerDetails !=null) arrCustomerDetails = null; if(customerRelNo != null) customerRelNo=null;	
						if(customerId != null) customerId=null;	if(custPhoneNo != null) custPhoneNo=null;
						if(custName != null) custName = null; 
					}
												
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
					Utilities.callOpsException(request, response, ctx, e.getMessage());
				}
				break;	
				
			case "opsshowallcards":
				try {
					
					String langPref = null; String customerRelNo = null;String tokenId=null;
					if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
					if(request.getParameter("hdncustomerrelno")!=null)			  customerRelNo = request.getParameter("hdncustomerrelno").trim();
					if(request.getParameter("hdntokenid")!=null)			  tokenId = request.getParameter("hdntokenid").trim();
					NeoBankEnvironment.setComment(3, className, "customerRelNo " + customerRelNo );
					NeoBankEnvironment.setComment(3, className, "tokenId opsshowallcards" + tokenId );

					request.setAttribute("langPref", langPref);
					request.setAttribute("lastaction", "opscrd");
					request.setAttribute("lastrule", "View Cards");
					
					List<CardDetails> listCardDetails = (List<CardDetails>)OpsManageCardsDao.class.getConstructor().newInstance().getSpecificTokenizedCards(customerRelNo,tokenId);

					request.setAttribute("listCardDetails", listCardDetails);
					response.setContentType("text/html");
					 try {
							ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewSpecificCustomerTokenizedCardsPage()).forward(request, response);
					} finally {
						if(listCardDetails !=null) listCardDetails = null;	
						if(customerRelNo !=null) customerRelNo = null;	
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

}
