package com.pporte.rules;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.stellar.sdk.KeyPair;

import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.CustomerDigitalAssetsDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.dao.TDAManagementDao;
import com.pporte.model.AssetAccount;
import com.pporte.model.User;
import com.pporte.utilities.StellarSDKUtility;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class TDAStellarAccountsRulesImpl implements Rules{
	private String className=TDAStellarAccountsRulesImpl.class.getSimpleName();
	@Override
	public void performJSONOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		
	}

	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		HttpSession session=request.getSession(false);
		if (session.getAttribute("SESS_USER") == null) 
			Utilities.callOpsException(request, response, ctx, "Session has expired, please log in again");
		switch (rulesaction) {
		
		case"addnewxlmaccount":
			try {
				String amount=null; String privateKey=null;String publicKey=null;
				String hasAccount = null;KeyPair keyPair = null;Boolean accountExist = false;
				Boolean success = false;String moduleCode=null;String userId=null;
				JsonObject obj = new JsonObject();PrintWriter output = null;String status=null;
				
				if(request.getParameter("addamount")!=null)amount=request.getParameter("addamount").trim();
				if(request.getParameter("addpublickey")!=null)publicKey=request.getParameter("addpublickey").trim();
				if(request.getParameter("addprivatekey")!=null)privateKey=request.getParameter("addprivatekey").trim();
				if(request.getParameter("hdnaddseladdtatus")!=null)status=request.getParameter("hdnaddseladdtatus").trim();
				
				userId = ((User)session.getAttribute("SESS_USER")).getUserId();				
					//Check if account exist
					keyPair =  KeyPair.fromAccountId(publicKey);
					accountExist = StellarSDKUtility.CheckAccountIfExist(keyPair);
					if(!accountExist) {
						NeoBankEnvironment.setComment(1, className, " Account Does not exist in stellar ");
						Utilities.sendJsonResponse(response, "error", " Account Does not exist in stellar ");
						return;
					}
					success = (Boolean) TDAManagementDao.class.getConstructor().newInstance().registerAccount
							(Utilities.tripleEncryptData(publicKey), Utilities.tripleEncryptData(privateKey),amount,status);
					if(success) {
						moduleCode="TRA";// TDA Registered XLM Subsidiary Account
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId,"T", moduleCode,"TDA Registered XLM Subsidiary Account" );
							obj.addProperty("error", "false");
							obj.addProperty("message", "You have successfully registered XLM Subsidiary Account");
					}else {
						obj.addProperty("error", "true");
						obj.addProperty("message", "Registering account failed");
					}try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
						output = response.getWriter();
						output.print(obj);
					} finally {
						if(userId!=null) userId=null; if(publicKey!=null) publicKey=null;
						if(amount!=null) amount=null; if(obj!=null) obj=null;if (moduleCode!=null) moduleCode=null;
						if (accountExist!=null)accountExist=null;if (privateKey!=null)privateKey=null; 
						if(keyPair!=null) keyPair=null;if(output!=null) output.close(); if (hasAccount!=null) hasAccount=null;
						if(success!=null) success=null;
					}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for rules: "+rulesaction+" is "+e.getMessage());
			}
			break;
		case"editxlmaccount":
			try {
				String publicKey=null;
				Boolean success = false;String moduleCode=null;String userId=null;
				JsonObject obj = new JsonObject();PrintWriter output = null;String status=null;
				
				if(request.getParameter("editpublickey")!=null)publicKey=request.getParameter("editpublickey").trim();
				if(request.getParameter("hdneditseleditstatus")!=null)status=request.getParameter("hdneditseleditstatus").trim();
				
				NeoBankEnvironment.setComment(3, className, "status is "+status+" publicKey "+publicKey);

				userId = ((User)session.getAttribute("SESS_USER")).getUserId();				
					success = (Boolean) TDAManagementDao.class.getConstructor().newInstance().editXLMAccount
							(Utilities.tripleEncryptData(publicKey),status);
					if(success) {
						moduleCode="TEA";// TDA Edited XLM Subsidiary Account
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId,"T", moduleCode,"TDA Edited XLM Subsidiary Account" );
							obj.addProperty("error", "false");
							obj.addProperty("message", "You have successfully edited XLM Subsidiary Account");
					}else {
						obj.addProperty("error", "true");
						obj.addProperty("message", "Registering account failed");
					}try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
						output = response.getWriter();
						output.print(obj);
					} finally {
						if(userId!=null) userId=null; if(publicKey!=null) publicKey=null;
						if(obj!=null) obj=null;if (moduleCode!=null) moduleCode=null;
						if(output!=null) output.close();if(success!=null) success=null;
					}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for rules: "+rulesaction+" is "+e.getMessage());
			}
			break;
		}	
	}

	@Override
	public void performOperation (String rulesaction, HttpServletRequest request, HttpServletResponse response,
	ServletContext ctx)
			throws Exception {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null)
			Utilities.callOpsException(request, response, ctx, "Session has expired, please log in again");
		switch (rulesaction) {
		case"Fund Accounts":
			try {
				ArrayList<AssetAccount> accountBalances=null;
				ArrayList<AssetAccount> allAccounts=null;
				allAccounts = (ArrayList<AssetAccount>)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getAllAccounts();	
				if(allAccounts!=null) {
					for(int i=0; i<allAccounts.size(); i++) {
						KeyPair accounts = KeyPair.fromAccountId((allAccounts.get(i).getPublicKey()));
							
						 accountBalances=StellarSDKUtility.getAccountBalance(accounts);
						 if(accountBalances!=null) {
							 for(int j=0;j<accountBalances.size();j++) {
									if(accountBalances.get(j).getAssetCode().equalsIgnoreCase(NeoBankEnvironment.getXLMCode())) {
										allAccounts.get(i).setAssetBalance(accountBalances.get(j).getAssetBalance());
									} 
							}
						 }
					 }
				}
				request.setAttribute("lastaction", "fundacc");	
				request.setAttribute("lastrule", "Fund Accounts");
				request.setAttribute("allaccounts", allAccounts);
				response.setContentType("text/html");
				
			try {
				ctx.getRequestDispatcher(NeoBankEnvironment.getTDAFundAccountsPage()).forward(request, response);
				}finally {
					if(accountBalances!=null) accountBalances = null;if(allAccounts!=null) allAccounts = null;
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());	
			}
		break;
		}	
	}

}
