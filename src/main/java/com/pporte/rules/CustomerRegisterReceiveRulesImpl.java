package com.pporte.rules;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.CustomerDao;
import com.pporte.dao.CustomerWalletDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.model.Customer; 
import com.pporte.model.User;
import com.pporte.model.Wallet;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;

public class CustomerRegisterReceiveRulesImpl implements Rules{

private static String className = CustomerRegisterReceiveRulesImpl.class.getSimpleName();

	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx)throws Exception {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null) 
			Utilities.callException(request, response, ctx, "Session has expired, please log in again");
		switch (rulesaction) {
		case "custreceiversearch":
			try {
				String customerEmail=null; String langPref=null;
				if (request.getParameter("input_register_receiver") != null)  	  customerEmail = request.getParameter("input_register_receiver").trim();
				if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
				NeoBankEnvironment.setComment(3,className,"LangPref is "+langPref);
				request.setAttribute("langpref", langPref);
				request.setAttribute("lastaction", "wal");
				request.setAttribute("lastrule", "Send Money");
				request.setAttribute("searchedcustomer",  (ArrayList<Customer>) CustomerDao.class.getConstructor().newInstance().getAllCustDetailsForRegistration(customerEmail));
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerRegisterReceiver()).forward(request, response);
				} finally {
					if(langPref !=null) langPref=null; if(customerEmail !=null) customerEmail=null;
				}
			}catch(Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
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
		/*
		if (session.getAttribute("SESS_USER") == null) 
			Utilities.callException(request, response, ctx, "Session has expired, please log in again");*/
		switch (rulesaction) {
		case"customeradduser":
			try {
				NeoBankEnvironment.setComment(3, className, " Inside customer add user");

				String receiverWalletId = null; String senderRelNo =null; String receiverRelNo=null;  
				List<Customer> customerListReg = null;
				ArrayList<Wallet> arrWallet = null;
				JsonObject obj = new JsonObject();
				PrintWriter output = null;String langPref=null;
				Gson gson = new Gson();

				if(request.getParameter("hdnwalletid")!=null)	receiverWalletId = StringUtils.trim(request.getParameter("hdnwalletid"));
				if(request.getParameter("hdnreceiverrelno")!=null)	receiverRelNo = StringUtils.trim(request.getParameter("hdnreceiverrelno"));
				if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
				//TODO check if session is null here
				NeoBankEnvironment.setComment(3, className, "Receiver wallet id is "+receiverWalletId +" Receiver Relationship no is "+receiverRelNo +"langPref "+langPref);
				
				senderRelNo = ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
				arrWallet = (ArrayList<Wallet>) CustomerWalletDao.class.getConstructor().newInstance()
						.getWalletDetails(((User) session.getAttribute("SESS_USER")).getRelationshipNo());
				boolean result = (boolean) CustomerDao.class.getConstructor().newInstance()
						.insertReceiverWalletForRegistration(receiverWalletId, senderRelNo, receiverRelNo);
				NeoBankEnvironment.setComment(3, className, "After insertReceiverWalletForRegistration CustomerDao  ");
				
				if(result == false) {
	        		obj.addProperty("error", "true"); 
	        		if(langPref.equalsIgnoreCase("en")) {
	        			obj.addProperty("message", "Failed to register user"); 
	        		}else {
	        			obj.addProperty("message", "No se pudo registrar el usuario"); 
	        		}
	        		
	        	}else {
	        		SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(senderRelNo, "C",
							"CAB"," Customer Added Beneficiary "+receiverRelNo);
	        		
	        		customerListReg =(List<Customer>)CustomerDao.class.getConstructor().newInstance().getAllRegisteredWalletsForASender(senderRelNo);
	        		if(customerListReg!=null) {
	        			obj.addProperty("error", "false"); 
		        		obj.add("data", gson.toJsonTree(customerListReg));
		        		if(langPref.equalsIgnoreCase("en")) {
		        			obj.addProperty("message", "Successfuly registered user");
		        		}else {
		        			obj.addProperty("message", "Usuario registrado correctamente");
		        		}
		        		
	        		}else {
	        			obj.addProperty("error", "true"); 
	        			if(langPref.equalsIgnoreCase("en")) {
		        			obj.addProperty("message", "Failed to register user"); 
		        		}else {
		        			obj.addProperty("message", "No se pudo registrar el usuario"); 
		        		}
		        		SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(senderRelNo, "C",
								"C", StringUtils.substring("User registered user "+receiverRelNo, 0, 48));
	        		}
	        	}
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + obj);
					output = response.getWriter();
					output.print(obj);
				}finally {
					if (receiverWalletId != null)receiverWalletId = null;if (senderRelNo != null)senderRelNo = null;if (receiverRelNo != null)receiverRelNo = null;
					if (customerListReg != null)customerListReg = null;if (output != null)output.close();
					if (arrWallet != null)arrWallet = null;if (gson != null)gson = null;if (obj != null)obj = null;
				}
			}catch(Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in adding user please try again");
			}
			break;
			
			case "enable_fingre_print":
				try {
					String relationshipNo =null; JsonObject obj = new JsonObject(); PrintWriter output = null; Gson gson = new Gson();
					String tokenValue = null;String langPref=null;
					if(request.getParameter("relno")!=null)			    relationshipNo = request.getParameter("relno").trim();
					if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
					if(request.getParameter("hdnlangpref")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlangpref"));
					
					if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
						NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v�lido, vuelva a iniciar sesi�n");

						}else {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
						}
						return;
					}
					boolean result = (boolean) CustomerDao.class.getConstructor().newInstance().enableFigurePrint(relationshipNo);
					
					if (result) {
						obj.add("error", gson.toJsonTree("false"));
						if(langPref.equalsIgnoreCase("ES")) {
							obj.add("message", gson.toJsonTree("Huella digital habilitada con �xito, ahora puede usarla para autorizar transacciones"));
						}else {
							obj.add("message", gson.toJsonTree("Fingerprint enabled successfully, You can now use it to authorize transactions "));
						}
						
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, "C",
								"C", StringUtils.substring("User enabled Fingerprint in mobile ", 0, 48));
					} else {
						if(langPref.equalsIgnoreCase("ES")) {
							obj.add("message", gson.toJsonTree("El registro de huellas dactilares fall�... Vuelva a intentarlo m�s tarde"));
						}else {
							obj.add("message", gson.toJsonTree("Fingerprint Registration failed.... Try again Later"));
						}
						obj.add("error", gson.toJsonTree("true"));
					}
					
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + obj);
						output = response.getWriter();
						output.print(obj);
					}finally {
						if (relationshipNo != null)relationshipNo = null;if (tokenValue != null)tokenValue = null;
						if (gson != null)gson = null;if (obj != null)obj = null;if(output!=null)output.close();
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Error in adding user please try again");
				}
				
			break;
		default:
			throw new IllegalArgumentException("Rule not defined value: " + rulesaction);
		}

	}

	

}
