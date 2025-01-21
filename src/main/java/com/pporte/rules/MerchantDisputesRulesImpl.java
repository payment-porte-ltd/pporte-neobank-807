package com.pporte.rules;

import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.MerchDisputeDao;
import com.pporte.model.DisputeReasons;
import com.pporte.model.DisputeTracker;
import com.pporte.model.Disputes;
import com.pporte.model.User;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;

public class MerchantDisputesRulesImpl implements Rules {
	private static String className = MerchantDisputesRulesImpl.class.getSimpleName();
	@Override
	public void performJSONOperation(String rules, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		
		
	}

	@Override
	public void performMultiPartOperation(String rules, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		HttpSession session	= request.getSession(false);
		switch (rules) {	
		case "merchadd_dispute":
			try {
				User user = null;
				String merchantCode = null;
				String comment = "";	String reasonId = null; 
				String transactionId = "";	String status = "A";
				String userType = null; 
				Boolean success = false;
				JsonObject obj = new JsonObject();
				PrintWriter output = null;

				 if (session.getAttribute("SESS_USER") == null) 
						throw new Exception ("Session has expired, please log in again");
		
				user = (User) session.getAttribute("SESS_USER");
				merchantCode = user.getRelationId();
				userType = user.getUserType();
				if(request.getParameter("dsptcomment")!=null)	
					comment = StringUtils.trim( request.getParameter("dsptcomment") );
				if(request.getParameter("hdnreasonid")!=null)
					reasonId = StringUtils.trim( request.getParameter("hdnreasonid") );
				if(request.getParameter("inputtransactionid")!=null)
					transactionId = StringUtils.trim( request.getParameter("inputtransactionid") );

				success = MerchDisputeDao.class.getConstructor().newInstance().addNewDispute(
						transactionId, merchantCode, userType, comment, reasonId,  status);
				if(success == false) {
	        		obj.addProperty("error", "true"); 
	        		obj.addProperty("message", "Raising Dispute failed"); 
	        	}else {
	        		//TODO Send Email to the user
	        		obj.addProperty("error", "false"); 
	        		obj.addProperty("message", "Dispute Raised suceessfully");
	        	}
				try {
					output = response.getWriter();
					output.print(obj);
				}finally {
					if(comment!=null) comment=null; if(userType!=null) userType=null; if(reasonId!=null) reasonId=null; 
					if(user!=null) user=null; if(merchantCode!=null) merchantCode=null; 
					if(transactionId!=null) transactionId=null; if(status!=null) status=null; 
					if(obj!=null) obj = null;
					if(output!=null) output.close();
				}
				
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rules+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Raising Dispute failed, Please try again letter");
			}
			break;
		case "add_dispute_commet":
			try {
				JsonObject obj = new JsonObject();
				PrintWriter output = null;
				User user = null;
				String merchantCode = null;
				String disputeId = null;  String comment = null;
				String userType = null; String userId = null; 
				Boolean success = false;
				if (session.getAttribute("SESS_USER") == null) 
					throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				merchantCode = user.getRelationId();
				if(request.getParameter("hdndispid")!=null)
					disputeId = StringUtils.trim( request.getParameter("hdndispid") );
				if(request.getParameter("comment")!=null)
					comment = StringUtils.trim( request.getParameter("comment") );
				success = MerchDisputeDao.class.getConstructor().newInstance().addCommentOnADispute(disputeId, 
				merchantCode, user.getUserType(), comment );
				
				if(success == false) {
	        		obj.addProperty("error", "true"); 
	        		obj.addProperty("message", "Problem in adding a new comment on the disputeid : "+disputeId); 
	        	}else {
	        		//TODO Send Email to the user
	        		obj.addProperty("error", "false"); 
	        		obj.addProperty("message", "Dispute comment added suceessfully");
	        	}
				try {
					output = response.getWriter();
					output.print(obj);
				}finally {
					if(disputeId!=null) disputeId=null; if(comment!=null) comment=null;
					if(userType!=null) userType=null; if(userId!=null) userId=null;
					if(obj!=null) obj = null; if(merchantCode!=null) merchantCode=null;
					if(output!=null) output.close();
				}
				
				
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rules+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Adding Dispute comment failed, Please try again letter");
			}
			break;
		case "update_dispute_status":
			try {
				JsonObject obj = new JsonObject();
				PrintWriter output = null;
				User user = null;
				String merchantCode = null;
				String disputeid = null;	String status = "A";
				String userId = null; String userType = null;
				boolean success = false;
				if (session.getAttribute("SESS_USER") == null) 
					throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				merchantCode = user.getRelationId();
				
				if(request.getParameter("hdndispid")!=null)		disputeid = StringUtils.trim( request.getParameter("hdndispid") );
				if(request.getParameter("selstatus")!=null)		status = StringUtils.trim( request.getParameter("selstatus") );
				success = MerchDisputeDao.class.getConstructor().newInstance().updateDisputeStatus(disputeid, status);

				if(success == false) {
	        		obj.addProperty("error", "true"); 
	        		obj.addProperty("message", "Problem Updating Dispute status on the disputeid : "+disputeid); 
	        	}else {
	        		//TODO Send Email to the user
	        		obj.addProperty("error", "false"); 
	        		obj.addProperty("message", "Dispute status Updated suceessfully");
	        	}
				try {
					output = response.getWriter();
					output.print(obj);
				}finally {
					if(disputeid!=null) disputeid=null; if(merchantCode!=null) merchantCode=null;
					if(status!=null) status=null; if(userId!=null) userId=null; if(userType!=null) userType=null;
					if(obj!=null) obj = null;
					if(output!=null) output.close();
				}
				
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rules+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Updating Dispute status failed, Please try again letter");
			}
			break;
		}
		
		
	}

	@Override
	public void performOperation(String rules, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx)throws Exception {
		HttpSession session	= request.getSession(false);
		if (session.getAttribute("SESS_USER") == null)
			Utilities.callException(request, response, ctx, "Session has expired, please log in again");
		switch (rules) {		
		case "Raise Dispute":
			try {
				
				request.setAttribute("lastaction", "merchdspt");
				request.setAttribute("lastrule", "Raise Dispute");
				response.setContentType("text/html");
				String userType = "M";
				try {
					request.setAttribute("dsptreason", 
							(List<DisputeReasons>)MerchDisputeDao.class.getConstructor().newInstance().getAllDisputeReasons(userType));
					ctx.getRequestDispatcher(NeoBankEnvironment.getMerchantRaiseDisputePage()).forward(request, response);
				}finally {
					if(userType!=null) userType=null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
		break;
		case "View Disputes":
			try {
				request.setAttribute("lastaction", "opsdspt");
				request.setAttribute("lastrule", "View Customer Disputes");
				response.setContentType("text/html");
				String merchantCode = null;
				merchantCode = ((User)session.getAttribute("SESS_USER")).getRelationId();
				request.setAttribute("alldisputes", 
						(List<Disputes>)MerchDisputeDao.class.getConstructor().newInstance().getAllDisputes(merchantCode));
				
				try{
					ctx.getRequestDispatcher(NeoBankEnvironment.getMerchantViewDisputePage()).forward(request, response);
				}finally {
					if(merchantCode!=null) merchantCode=null; 
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
		break;
		case "view_specific_dispute":
			try {
				request.setAttribute("lastaction", "merchdspt");
				request.setAttribute("lastrule", "View Disputes"); 
				String disputeId = null;	
				if(request.getParameter("hdnreqid")!=null)
					disputeId = StringUtils.trim( request.getParameter("hdnreqid") );

				request.setAttribute("showdispute", (Disputes)MerchDisputeDao.class.getConstructor().newInstance().getDisputeDetail(disputeId));
				request.setAttribute("disputethreads", (List<DisputeTracker>)MerchDisputeDao.class.getConstructor().newInstance().getAllDisputeTrackers(disputeId));
				try{
					ctx.getRequestDispatcher(NeoBankEnvironment.getMerchantViewSpecificDisputePage()).forward(request, response);
				}finally {
					if(disputeId!=null) disputeId=null; 
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			break;
		}
		
	}

}
