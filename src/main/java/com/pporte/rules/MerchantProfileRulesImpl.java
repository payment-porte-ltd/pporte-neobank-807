package com.pporte.rules;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.MerchantDao;
import com.pporte.model.Merchant;
import com.pporte.model.User;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;

public class MerchantProfileRulesImpl implements Rules {
	private static String className = MerchantProfileRulesImpl.class.getSimpleName();
	@Override
	public void performJSONOperation(String arg0, HttpServletRequest arg1, HttpServletResponse arg2,
			ServletContext arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performMultiPartOperation(String rules, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		HttpSession session	= request.getSession(false);
		switch (rules){
			case "getmerchkycdocs":
				try {
					JsonObject object = new JsonObject();
					Gson gson = new Gson();
					User user = null;
					String mercherntCode = null;
					if (session.getAttribute("SESS_USER") == null) {
						throw new Exception ("Session has expired, please log in again");
					}
					user = (User) session.getAttribute("SESS_USER");
					mercherntCode = user.getRelationId();
					List<String> listFileLocation =
							(List<String>)
							MerchantDao.class.getConstructor().newInstance().getMerchantKycDocs(mercherntCode);
					object.add("data", gson.toJsonTree(listFileLocation));
					object.add("error", gson.toJsonTree("false"));
					try {
						response.getWriter().print(object);
					}finally {
						response.getWriter().close();
						if(object!=null) object = null;
						if(listFileLocation!=null) listFileLocation = null;
						if(mercherntCode!=null) mercherntCode = null;
						if(user!=null) user = null;
						if(gson!=null) gson = null;
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rules+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", e.getMessage());
				}
				
			break;
			case "getmerchmccdata":
				try {
					JsonObject object = new JsonObject();
					Gson gson = new Gson();
					User user = null;
					String mercherntCode = null;
					if (session.getAttribute("SESS_USER") == null) {
						throw new Exception ("Session has expired, please log in again");
					}
					user = (User) session.getAttribute("SESS_USER");
					mercherntCode = user.getRelationId();
					ConcurrentHashMap<String, String> hashMccData =
							(ConcurrentHashMap<String, String>)
							MerchantDao.class.getConstructor().newInstance().getSpecificMerchantCategory(mercherntCode);
					object.add("data", gson.toJsonTree(hashMccData));
					object.add("error", gson.toJsonTree("false"));
					try {
						response.getWriter().print(object);
					}finally {
						response.getWriter().close();
						if(object!=null) object = null;
						if(hashMccData!=null) hashMccData = null;
						if(mercherntCode!=null) mercherntCode = null;
						if(user!=null) user = null;
						if(gson!=null) gson = null;
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rules+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", e.getMessage());
				}
			break;
			case "addbranch":
				try {
					JsonObject obj = new JsonObject();
					String companyName = null;
					String businessDesc = null;
					String physicalAddress = null;
					String bsnPhoneNumber = null;
					String mccId = null;
					String planValue = null;
					String email = null;
					String nationalId = null;
					String assetDocFile = "NA";
					List<String> fileNames = new ArrayList<String>();
					JsonObject dmJsonResponse = new JsonObject();
					PrintWriter output = null;
					String filePath = null;
					String message = null;
					Boolean success = false;
					String branchMerchantCode =null;
					String billerRef= null;
					String fullName = null;
					String phoneNo = null;
					String haveBranches = null;
					String fileExtension = null;
					
					if(request.getParameter("companyname")!=null)	
						companyName = request.getParameter("companyname").trim();
					if(request.getParameter("bsndesc")!=null)	
						businessDesc = request.getParameter("bsndesc").trim();
					if(request.getParameter("physicaladd")!=null)	
						physicalAddress = request.getParameter("physicaladd").trim();
					if(request.getParameter("bsnphoneno")!=null)	
						bsnPhoneNumber = request.getParameter("bsnphoneno").trim();
					if(request.getParameter("hdnplan")!=null)	
						planValue = request.getParameter("hdnplan").trim();
					if(request.getParameter("hdnhavebranches")!=null)	
						haveBranches = request.getParameter("hdnhavebranches").trim();
					if(request.getParameter("nationalId")!=null)	
						nationalId = request.getParameter("nationalId").trim();
					
					if(request.getParameter("selectmcc")!=null)	
						mccId = request.getParameter("selectmcc").trim();
					
					if(request.getParameter("merchphoneno")!=null)	
						phoneNo = request.getParameter("merchphoneno").trim();
					if(request.getParameter("fullname")!=null)	
						fullName = request.getParameter("fullname").trim();
					
					if(request.getParameter("email")!=null)	
						email = request.getParameter("email").trim();
					
					String mainMercherntCode = null;
					if (session.getAttribute("SESS_USER") == null) {
						throw new Exception ("Session has expired, please log in again");
					}
					User user = null;
					if (session.getAttribute("SESS_USER") == null) {
						throw new Exception ("Session has expired, please log in again");
					}
					user = (User) session.getAttribute("SESS_USER");
					mainMercherntCode = user.getRelationId();
					
					NeoBankEnvironment.setComment(3,className,
							" companyName "+companyName+" businessDesc "+ businessDesc
							+" physicalAddress "+physicalAddress+" bsnPhoneNumber "+bsnPhoneNumber
							+" mccId "+mccId+" planValue "+planValue+" email "+email+"nationalId"
							+nationalId+" fullName "+fullName+" phoneNo "+phoneNo+" haveBranches "+haveBranches);
					
					List<Part> parts = (List<Part>) request.getParts();
					NeoBankEnvironment.setComment(3,className,
							"inside performMultiPartOperation --> 2 parts is "+parts);
					for (Part part : parts) {
						if (part.getName().equalsIgnoreCase("file1")) {
							NeoBankEnvironment.setComment(3,className,
									"inside performMultiPartOperation --> 3  ");
							String fileName = Utilities.getFileName(part);
							fileExtension = FilenameUtils.getExtension(fileName);
							NeoBankEnvironment.setComment(3,className,
									"inside performMultiPartOperation --> fileName:  "+fileName);
							fileNames.add(fileName);
							//String applicationPath = request.getServletContext().getRealPath("");
							@SuppressWarnings("unused")
							String basePath =  
							NeoBankEnvironment.getFileUploadPath() + File.separator;
							String basePathTemp =  
									NeoBankEnvironment.getFileUploadPath() 
									+ File.separator +"temp" + File.separator;
							InputStream inputStream = null;
							OutputStream outputStream = null;
							try {
								File outputFilePath = new File( StringUtils.replace(basePathTemp, "\\", "/") + fileName);
								inputStream = part.getInputStream();
								outputStream = new FileOutputStream(outputFilePath);
								int read = 0;
								final byte[] bytes = new byte[1024];
								while ((read = inputStream.read(bytes)) != -1) {
									outputStream.write(bytes, 0, read);
								}
							} catch (Exception ex) {
								fileName = null;
								throw new Exception ("Exception in file upload "+ex.getMessage());
							} finally {
								if (outputStream != null) outputStream.close();	
								if (inputStream != null) inputStream.close();
							}
						}
					}
					if(fileNames.size()>0) {
						// tempoary path in the app server
					assetDocFile = NeoBankEnvironment.getFileUploadPath() + File.separator +"temp" + File.separator + fileNames.get(0); // only one file present/allowed to upload here, else run a loop
					assetDocFile = StringUtils.replace(assetDocFile, "\\", "/");
					}
					branchMerchantCode = String.valueOf((new Date().getTime() / 10L) % Integer.MAX_VALUE).
							concat(String.valueOf(RandomUtils.nextInt(10, 99)));
					List<String> fileArrayEncoded = new ArrayList<String>();
					File fileBase64Encoded = new File(assetDocFile);
					if(fileBase64Encoded.exists() == false) throw new Exception ("File does not exists");
	                byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(fileBase64Encoded));
	                String base64fileString = new String(encoded, StandardCharsets.US_ASCII); // in case of multiple file it will be in array
	                NeoBankEnvironment.setComment(3,className,"encoding base64fileString--> base64fileString size:  "+base64fileString.length());                

	                HttpPost post = new HttpPost(NeoBankEnvironment.getFDocServerURL());
	                @SuppressWarnings("unused")
					int count = fileArrayEncoded.size(); //Array Size
			        // add request parameter, form parameters and only the file details.
			        List<NameValuePair> urlParameters = new ArrayList<>();
			        urlParameters.add(new BasicNameValuePair("qs", "fud"));
			        urlParameters.add(new BasicNameValuePair("rules", "uploadfile"));
			        urlParameters.add(new BasicNameValuePair("filename", 
			        		branchMerchantCode.concat(".").concat(fileExtension)));
				    urlParameters.add(new BasicNameValuePair("fileraw", base64fileString)); // not using the Array as only 1 file is uploaded
			        post.setEntity(new UrlEncodedFormEntity(urlParameters));

			        CloseableHttpClient httpClient = HttpClients.createDefault();
			        CloseableHttpResponse urlresponse = httpClient.execute(post); 
			        String	sbResponse = EntityUtils.toString(urlresponse.getEntity());
			        NeoBankEnvironment.setComment(3, className,"***** after POST response is "+ sbResponse);
			        if(sbResponse!=null) {
			        	dmJsonResponse = new Gson().fromJson(sbResponse, JsonObject.class);
			        	if(dmJsonResponse.get("error").getAsBoolean() == false) {
			        		filePath = dmJsonResponse.get("filepath").getAsString();
			        		NeoBankEnvironment.setComment(3, className,"File path is "+ filePath);
			        	}else {
			        		message = dmJsonResponse.get("message").getAsString();
			        		NeoBankEnvironment.setComment(3, className,"File path is "+ message);
						}
			        }else {
			        	throw new Exception ("Problem in the external file update process");
			        }
			        		        
			        billerRef = Utilities.generatingRandomAlphanumericString();
			        
			        success = (Boolean)MerchantDao.class.getConstructor().newInstance().addNewBranch( companyName,  businessDesc, 
			        		 physicalAddress,
			    			 bsnPhoneNumber,  email,    nationalId, 
			    			 planValue,  filePath,  billerRef,  mainMercherntCode,   mccId,  fullName,
			    			 phoneNo,  branchMerchantCode);	
			        try {	
			        	if(success == false) {
			        		obj.addProperty("error", "true"); 
			        		obj.addProperty("message", "Adding branch failed"); 
			        	}else {
			        		obj.addProperty("error", "false"); 
			        		obj.addProperty("message", "Branch added suceessfully");
			        	}
						output = response.getWriter();
						output.print(obj);
					}finally {
						if( companyName !=null) companyName = null; if( businessDesc != null) businessDesc = null; if( physicalAddress != null) physicalAddress = null;
						if( bsnPhoneNumber != null) bsnPhoneNumber = null;  if( assetDocFile != null) assetDocFile = null; 	 if( email != null) email = null;
						if( nationalId != null) nationalId = null;  if( planValue != null) planValue = null; if( haveBranches != null) haveBranches = null;
						if(fileNames.isEmpty()==false) {fileNames.clear(); fileNames = null;}
						if(fileArrayEncoded.isEmpty()==false) {fileArrayEncoded.clear(); fileArrayEncoded = null;}	
						if(base64fileString!=null) base64fileString = null; if(sbResponse!=null) sbResponse = null;
						if(httpClient!=null) httpClient.close(); if(urlresponse!=null) urlresponse.close(); if(urlParameters.isEmpty()==false) urlParameters.clear();
						if(encoded!=null) encoded = null;
						if(filePath!=null) filePath = null;
						if(message!=null) message = null;
						if(fileBase64Encoded!=null && fileBase64Encoded.exists()) fileBase64Encoded.delete();
						if(output != null) output.flush();
						if(obj!=null) obj = null;
						if(user!=null) user = null;
						if(mainMercherntCode!=null) mainMercherntCode = null;
						if(branchMerchantCode!=null) branchMerchantCode = null;
						if(output!=null) output.close();
					}
					
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rules+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Adding branch failed, Please try again letter");
				}
			break;
			
			case "addmerchantuser":
				try {
					JsonObject obj = new JsonObject();
					Gson gson = new Gson();
					User user = null;
					String mercherntCode = null;
					String password = null;
					String fullName= null;
					String userLevel= null;
		 			String emailId= null;
		 			String userContact= null;
		 			String nationalId= null;
		 			String designation= null;
		 			Boolean success = false;
		 			PrintWriter output = null;
		 			
		 			password=RandomStringUtils.randomAlphabetic(6);
		 			
		 			//TODO disable this after sending email
		 			NeoBankEnvironment.setComment(1, className, "password "+password);
		 			
		 			if(request.getParameter("fname")!=null)	
		 				fullName = request.getParameter("fname").trim();
		 			
		 			if(request.getParameter("seluserlevel")!=null)	
		 				userLevel = request.getParameter("seluserlevel").trim();
		 			
		 			if(request.getParameter("uemail")!=null)	
		 				emailId = request.getParameter("uemail").trim();
		 			
		 			if(request.getParameter("phoneno")!=null)	
		 				userContact = request.getParameter("phoneno").trim();
		 			
		 			if(request.getParameter("idno")!=null)	
		 				nationalId = request.getParameter("idno").trim();
		 			
		 			if(request.getParameter("designation")!=null)	
		 				designation = request.getParameter("designation").trim();
		 			
					if (session.getAttribute("SESS_USER") == null) {
						throw new Exception ("Session has expired, please log in again");
					}
					user = (User) session.getAttribute("SESS_USER");
					mercherntCode = user.getRelationId();
					NeoBankEnvironment.setComment(3, className, "mercherntCode "+mercherntCode);
					success = (Boolean)MerchantDao.class.getConstructor().
							newInstance().addNewMerchantUser(password, fullName,
									userLevel, emailId, userContact, mercherntCode, nationalId, designation);
					if(success == false) {
		        		obj.addProperty("error", "true"); 
		        		obj.addProperty("message", "Adding User failed"); 
		        	}else {
		        		//TODO Send Email to the user
		        		
		        		obj.addProperty("error", "false"); 
		        		obj.addProperty("message", "User added suceessfully");
		        	}
					try {
						output = response.getWriter();
						output.print(obj);
					}finally {
						if(obj!=null) obj = null;
						if(output!=null) output.close();
						if(mercherntCode!=null) mercherntCode = null;
						if(user!=null) user = null; if(gson!=null) gson = null;
						if(password!=null) password = null; if(fullName!=null) fullName = null;
						if(emailId!=null) emailId = null; 
						if(nationalId!=null) nationalId = null; if(designation!=null) designation = null;
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rules+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Adding user failed, Please try again letter");
				}
			
			break;
			
			case "editmerchantuser":
				try {
					String fullName= null;  String userAccess= null;  
					String emailId= null;  String userContact= null; 
					String userStatus= null; 
					String designation= null;  String merchantCode= null;
					JsonObject obj = new JsonObject();
					Gson gson = new Gson();
					User user = null;
					Boolean success = false;
					PrintWriter output = null;
					String nationalId = null;
					 
					 if(request.getParameter("efullname")!=null)	
			 				fullName = request.getParameter("efullname").trim();
					 
					 if(request.getParameter("euseraccess")!=null)	
						 userAccess = request.getParameter("euseraccess").trim();
					 
					 if(request.getParameter("eemail")!=null)	
						 emailId = request.getParameter("eemail").trim();
					 
					 if(request.getParameter("eidno")!=null)	
			 				nationalId = request.getParameter("eidno").trim();
					 
					 if(request.getParameter("ephoneno")!=null)	
						 userContact = request.getParameter("ephoneno").trim();
					 
					 if(request.getParameter("estatus")!=null)	
						 userStatus = request.getParameter("estatus").trim();
					 
					 if(request.getParameter("edesignation")!=null)	
						 designation = request.getParameter("edesignation").trim();
					 
					 if (session.getAttribute("SESS_USER") == null) 
							throw new Exception ("Session has expired, please log in again");
					 
					user = (User) session.getAttribute("SESS_USER");
					merchantCode = user.getRelationId();
					
					NeoBankEnvironment.setComment(1, className, "mercherntCode "+merchantCode+" userAccess "+userAccess
							+" emailId "+emailId+" userContact "+userContact+" userStatus "+userStatus
							+" designation "+designation+" nationalId "+nationalId);
						
					success = (Boolean)MerchantDao.class.getConstructor().
							newInstance().updateMerchUserDetails(fullName, userAccess, emailId, 
									userContact, userStatus, designation, merchantCode, nationalId);
					if(success == false) {
		        		obj.addProperty("error", "true"); 
		        		obj.addProperty("message", "Edit User failed"); 
		        	}else {
		        		//TODO Send Email to the user
		        		obj.addProperty("error", "false"); 
		        		obj.addProperty("message", "User Edited suceessfully");
		        	}
					try {
						output = response.getWriter();
						output.print(obj);
					}finally {
						if(obj!=null) obj = null;
						if(output!=null) output.close();
						if(merchantCode!=null) merchantCode = null;
						if(user!=null) user = null; if(gson!=null) gson = null;
						if(fullName!=null) fullName = null;
						if(emailId!=null) emailId = null; 
						if(designation!=null) designation = null;
					}
	
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rules+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Edit user user failed, Please try again letter");
				}
			break;
			
			
					
					
				
		}
		
	}

	@Override
	public void performOperation(String rules, HttpServletRequest request, HttpServletResponse response, 
			ServletContext ctx)
			throws Exception {
		HttpSession session	= request.getSession(false);
		switch (rules){
		case "View and Update":
			try {
				if (session.getAttribute("SESS_USER") == null)
					Utilities.callException(request, response, ctx, "Session has expired, please log in again");
				User user = null;
				String merchID = null;
				user = (User) session.getAttribute("SESS_USER");
				merchID = user.getUserId();
				request.setAttribute("lastaction", "merchprf");
				request.setAttribute("lastrule", "View and Update");
				response.setContentType("text/html");
				try {
					request.setAttribute("merchfullprofile", 
							(Merchant) MerchantDao.class.getConstructor().newInstance().getMerchantProfile(merchID));
					ctx.getRequestDispatcher(NeoBankEnvironment.getMerchantProfilePage()).forward(request, response);
				}finally {
					if (user!=null)user=null;
					if (merchID!=null)merchID=null;
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
		break;
		case "Manage branches":
			try {
				if (session.getAttribute("SESS_USER") == null)
					Utilities.callException(request, response, ctx, "Session has expired, please log in again");
				User user = null;
				String merchID = null;
				ArrayList<Merchant> arrBranchesList = null;
				user = (User) session.getAttribute("SESS_USER");
				merchID = user.getRelationId();
				request.setAttribute("lastaction", "merchprf");
				request.setAttribute("lastrule", "Manage branches");
				response.setContentType("text/html");
				arrBranchesList = (ArrayList<Merchant>) MerchantDao.class.getConstructor().newInstance().getMerchantBranchData(merchID);
				NeoBankEnvironment.setComment(3, className, "Array size is "+arrBranchesList.size());
				try {
					request.setAttribute("branchdata", arrBranchesList);
					ctx.getRequestDispatcher(NeoBankEnvironment.getMerchantManageBranchesPage()).forward(request, response);
				}finally {
					if (user!=null)user=null;
					if (merchID!=null)merchID=null;
					if(arrBranchesList!=null)arrBranchesList=null;
				}
		}catch (Exception e) {
			NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
			Utilities.callException(request, response, ctx, e.getMessage());
		}
		break;
		
		case "Manage users":
			try {
				if (session.getAttribute("SESS_USER") == null)
					Utilities.callException(request, response, ctx, "Session has expired, please log in again");
				User user = null;
				String merchID = null;
				user = (User) session.getAttribute("SESS_USER");
				merchID = user.getRelationId();
				request.setAttribute("lastaction", "merchprf");
				request.setAttribute("lastrule", "Manage users");
				response.setContentType("text/html");
				try {
					request.setAttribute("usersdata", 
							(List<Merchant>) MerchantDao.class.getConstructor().newInstance().getMerchantUsersData(merchID));
					ctx.getRequestDispatcher(NeoBankEnvironment.getMerchantManageUsersPage()).forward(request, response);
				}finally {
					if (user!=null)user=null;
					if (merchID!=null)merchID=null;
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
		break;
		
		case "getaddbranchpage":
			try {
				if (session.getAttribute("SESS_USER") == null)
					Utilities.callException(request, response, ctx, "Session has expired, please log in again");
				request.setAttribute("lastaction", "merchprf");
				request.setAttribute("lastrule", "Manage branches");
				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getMerchantAddBranchPage()).forward(request, response);
				}finally {
					
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			break;
	
		
		}
			
			
	}

}
