package com.pporte.rules;


import java.util.ArrayList;


import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.pporte.NeoBankEnvironment;
import com.pporte.dao.OpsManageBlockCodesDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.model.BlockCodes;
import com.pporte.model.User;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;

public class OpsManageBlockCodesRulesImpl implements Rules{
private static String className = OpsManageBlockCodesRulesImpl.class.getSimpleName();

	@Override
	public void performOperation(String ruleaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception{
		HttpSession session = request.getSession(false);
		switch (ruleaction) {
		case "Manage Block Codes":
			try {

				request.setAttribute("lastaction", "opswal");
				request.setAttribute("lastrule", "Manage Block Codes");
				response.setContentType("text/html");
				request.setAttribute("blockcodes", (ArrayList<BlockCodes>)OpsManageBlockCodesDao.class.getConstructor().newInstance().getAllBlockCodes());
			   try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getOpsBlockCodePage()).forward(request,
							response);
				} finally {
				}

			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + ruleaction + " is " + e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			break;
			
		case "opsnewblockcode":
			try {
				String langPref = null;String blockCodeDesc=null; String blockStatus=null;
				String blockAuthentication = null;String opsUserType=null;String userId=null;
				 				
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				if(request.getParameter("blockcodedesc")!=null)			  blockCodeDesc = request.getParameter("blockcodedesc").trim();
				if(request.getParameter("hdnaddblockstatus")!=null)			  blockStatus = request.getParameter("hdnaddblockstatus").trim();
				if(request.getParameter("hdnaddselauthentication")!=null)			  blockAuthentication = request.getParameter("hdnaddselauthentication").trim();
				
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opswal");
				request.setAttribute("lastrule", "View Block Codes");
				
				
				userId = ((User)session.getAttribute("SESS_USER")).getUserId();
				opsUserType = ((User)session.getAttribute("SESS_USER")).getUserType();
				
				boolean result  = (boolean)OpsManageBlockCodesDao.class.getConstructor().newInstance().AddBlockCodes(blockCodeDesc, blockStatus, blockAuthentication);
				
				if(result) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, opsUserType, "O",StringUtils.substring("Created new block codes " + userId , 0, 48));
				}else {
					NeoBankEnvironment.setComment(1, className, "Error in adding new block code");

				}
									
				request.setAttribute("blockcodes", (ArrayList<BlockCodes>)OpsManageBlockCodesDao.class.getConstructor().newInstance().getAllBlockCodes());
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsBlockCodePage()).forward(request, response);
				} finally {
					if(langPref !=null) langPref=null; if(blockCodeDesc !=null) blockCodeDesc=null; if(blockStatus !=null) blockStatus=null;
					if(userId !=null) userId=null; if(blockAuthentication !=null) blockAuthentication=null;
					if(opsUserType !=null) opsUserType=null;

				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + ruleaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		case "opseditblockcodes":
			try {
				NeoBankEnvironment.setComment(2, className, "inside opseditblockcodes rule");

				String langPref = null; String editBlockId = null; String editBlockCodeDesc=null; String authenticate=null;
				String createdOn = null; String status = null; String userId=null; String opsUserType=null;
				
	
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				if(request.getParameter("editblockid")!=null)			  editBlockId = request.getParameter("editblockid").trim();
				if(request.getParameter("editblockcodedesc")!=null)			  editBlockCodeDesc = request.getParameter("editblockcodedesc").trim();
				if(request.getParameter("editcreatedon")!=null)			  createdOn = request.getParameter("editcreatedon").trim();
				if(request.getParameter("hdnauthenticate")!=null)			  authenticate = request.getParameter("hdnauthenticate").trim();
				if(request.getParameter("hdneditstatus")!=null)			  status = request.getParameter("hdneditstatus").trim();
				
				NeoBankEnvironment.setComment(3, className, "editBlockId "+ editBlockId+" editBlockCodeDesc "+editBlockCodeDesc+" createdOn " +createdOn+""
						+ "authenticate  "+authenticate+" status "+status);

				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opswal");
				request.setAttribute("lastrule", "Dispute Reasons");
				
				userId = ((User)session.getAttribute("SESS_USER")).getUserId();
				opsUserType = ((User)session.getAttribute("SESS_USER")).getUserType();
				
				boolean result  = (boolean)OpsManageBlockCodesDao.class.getConstructor().newInstance().updateBlockCodes( editBlockId, editBlockCodeDesc, createdOn, authenticate, status );
				NeoBankEnvironment.setComment(1, className, "After updating the data base" );

				if(result) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(userId, opsUserType, "O",StringUtils.substring("Updated block code reason" + editBlockCodeDesc , 0, 48));
				}else {
					NeoBankEnvironment.setComment(1, className, "Error in updating block code id"+ editBlockId );

				}
				request.setAttribute("blockcodes", (ArrayList<BlockCodes>)OpsManageBlockCodesDao.class.getConstructor().newInstance().getAllBlockCodes());
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsBlockCodePage()).forward(request, response);
				} finally {
					if(langPref !=null) langPref=null; if(editBlockId !=null) editBlockId=null; if(editBlockCodeDesc !=null) editBlockCodeDesc=null;
					if(userId !=null) userId=null; if(authenticate !=null) authenticate=null; if(status !=null) status=null;
					if(opsUserType !=null) opsUserType=null;if(createdOn !=null) createdOn=null;


				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + ruleaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		default:
			throw new IllegalArgumentException("Rule not defined value: " + ruleaction);
		}


		
	}

	@Override
	public void performJSONOperation(String arg0, HttpServletRequest arg1, HttpServletResponse arg2,
			ServletContext arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unused")
	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null) Utilities.callException(request, response, ctx, "Session has expired, please log in again");
		
		switch (rulesaction) {
		default:
			throw new IllegalArgumentException("Rule not defined value: " + rulesaction);
		}
		
		
		
	}



}
