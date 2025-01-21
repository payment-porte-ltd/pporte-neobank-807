package com.pporte.rules;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.CustomerDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.model.User;
import com.pporte.utilities.Utilities;
import java.nio.file.Files;

import framework.v8.Rules;

public class FileUploadRulesImpl implements Rules{
	private static String className = FileUploadRulesImpl.class.getSimpleName();
	@Override
	public void performJSONOperation(String rules, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
	}

	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		//HttpSession session = request.getSession(false);
		switch (rulesaction) {
		case "assetupload":
			try {
				String assetType = null; String assetDesc = null; String assetDocFile = "NA"; String langPref = "en"; 
				String userId = null; String fileMD5Hash = null;		List<String> fileNames = new ArrayList<String>();
				userId = "123456789"; //Please use user Id from session
				NeoBankEnvironment.setComment(3,className,"inside performMultiPartOperation --> 1  ");
				PrintWriter output = null;
				String filePath = null;
				String message = null;
				JsonObject dmJsonResponse = new JsonObject();

				if(request.getParameter("hdnasset")!=null) 	assetType = request.getParameter("hdnasset").trim();
				if(request.getParameter("assetdesc")!=null) 	assetDesc = request.getParameter("assetdesc").trim();
				if(request.getParameter("hdnlang")!=null) 	langPref = request.getParameter("hdnlang").trim();
				
				List<Part> parts = (List<Part>) request.getParts();
				NeoBankEnvironment.setComment(3,className,"inside performMultiPartOperation --> 2 parts is "+parts);
				for (Part part : parts) {
					if (part.getName().equalsIgnoreCase("filedrop")) {
						NeoBankEnvironment.setComment(3,className,"inside performMultiPartOperation --> 3  ");
						String fileName = Utilities.getFileName(part);
						NeoBankEnvironment.setComment(3,className,"inside performMultiPartOperation --> fileName:  "+fileName);
						fileNames.add(fileName);
						//String applicationPath = request.getServletContext().getRealPath("");
						@SuppressWarnings("unused")
						String basePath =  NeoBankEnvironment.getFileUploadPath() + File.separator;
						String basePathTemp =  NeoBankEnvironment.getFileUploadPath() + File.separator +"temp" + File.separator;
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
				// Create the MD5 Hash. There may be a possibilty that you may have to create this hash on the actual storage location
				// and return the hash to this server to store in Blockchain. Creating hash at temporary location is not ideal. It may lead to mismatch
				if(assetDocFile.equals("NA")==false) {
					// get MD5 hash of the uploaded file
					try (InputStream is = Files.newInputStream(Paths.get(assetDocFile))) {
						fileMD5Hash = org.apache.commons.codec.digest.DigestUtils.md5Hex(is);
						is.close();
					}
				}else {
					fileMD5Hash = "NA";
				}
				request.setAttribute("langpref", langPref);
				
				//******here push the file to the External App Servlet and get the confirmation
				/* Step 1: get the file from JSP using Parts
				 * Step 2: base64 encode the file
				 * Step 3: push the file to the external servlet
				 * Step 4: (External servlet) - get the base64 String
				 * Step 5: (External Servlet and Rules) - decode the file and save into the local storage
				 * Step 6: (External Servlet and RUles) - send the full path and file details back to the response
				 * Step 7: Check the response and get the full path of file
				 * Step 8: Store the file path detais in the DB
				 * 
				 */
				// *** Step 2
				ArrayList<String> fileArrayEncoded = new ArrayList<String>();
				File fileBase64Encoded = new File(assetDocFile);
				if(fileBase64Encoded.exists() == false) throw new Exception ("File does not exists");
                byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(fileBase64Encoded));
                // long long long String
                // *** Step 3
                String base64fileString = new String(encoded, StandardCharsets.US_ASCII); // in case of multiple file it will be in array
                NeoBankEnvironment.setComment(3,className,"encoding base64fileString--> base64fileString size:  "+base64fileString.length());                

                HttpPost post = new HttpPost(NeoBankEnvironment.getFDocServerURL());
                @SuppressWarnings("unused")
				int count = fileArrayEncoded.size(); //Array Size
		        // add request parameter, form parameters and only the file details.
		        List<NameValuePair> urlParameters = new ArrayList<>();
		        urlParameters.add(new BasicNameValuePair("qs", "fud"));
		        urlParameters.add(new BasicNameValuePair("rules", "uploadfile"));
		        urlParameters.add(new BasicNameValuePair("filename", fileNames.get(0)));
		        urlParameters.add(new BasicNameValuePair("userid", userId));
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
				
				try {					
					output = response.getWriter();
					response.setContentType("text/plain");
					output.print("Uploaded successfully");
					
				}finally {
					if( userId !=null) userId = null; if( assetType != null) assetType = null; if( assetDesc != null) assetDesc = null;
					if( langPref != null) langPref = null;  if( assetDocFile != null) assetDocFile = null; 	 if( fileMD5Hash != null) fileMD5Hash = null;
					if(fileNames.isEmpty()==false) {fileNames.clear(); fileNames = null;}
					if(fileArrayEncoded.isEmpty()==false) {fileArrayEncoded.clear(); fileArrayEncoded = null;}	
					if(base64fileString!=null) base64fileString = null; if(sbResponse!=null) sbResponse = null;
					if(httpClient!=null) httpClient.close(); if(urlresponse!=null) urlresponse.close(); if(urlParameters.isEmpty()==false) urlParameters.clear();
					if(encoded!=null) encoded = null;
					if(filePath!=null) filePath = null;
					if(message!=null) message = null;
					if(fileBase64Encoded!=null && fileBase64Encoded.exists()) fileBase64Encoded.delete();
					if(output != null) output.flush();
				}
				
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1,className,"The exception in rules assetupload is  "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			break;
		
		}
		
		
	}

	@Override
	public void performOperation(String rules, HttpServletRequest request, HttpServletResponse response, 
			ServletContext ctx)
			throws Exception {
		HttpSession session = request.getSession(false);
		switch (rules){
			case "assetdownload":
				/*
				 * Step 1: Get the filepath from the JSP
				 * Step 2: Send the filepath to the external servlet 
				 * Step 3: From External Servlet get the file from the external server disk, convert ftke into base26 String ( coded in the external server)
				 * Step 4: Send the file back in base24 String to this program (coded in external server)
				 * Step 5: Get the response. This response is the base64 String of the file. The asumption always is one file downloaded at a time.
				 * Step 6: One you have the base64 String, unravel the base64 string, convert into file and store into a temporary folder.
				 * Step 7: Read the file that you have saved into the tempoary folder using PrintWriter Stream and write into the respose
				 * Step 8 : Delete the temporary file at the final stage.
				 */
				try {
					String originalFileNameWithPath = null;String originalFileName=null;
					String basePath =  null; String relationshipNo = null; String userType=null;
					//PrintWriter out_download1 = null;
					//** Step 1
					NeoBankEnvironment.setComment(3, className,"Step 1");
 					if (request.getParameter("hdnassetpath") != null)	originalFileName = request.getParameter("hdnassetpath").trim();
 					if (request.getParameter("relno") != null)	relationshipNo = request.getParameter("relno").trim();
 					if (request.getParameter("usertype") != null)	userType = request.getParameter("usertype").trim();
 					NeoBankEnvironment.setComment(3, className, "Before Download the fileName " + originalFileName);
 					
 					basePath =  NeoBankEnvironment.getFileUploadPath() + File.separator;
 					basePath = StringUtils.replace(basePath, "\\", "/") ;
 					
 					if (session.getAttribute("SESS_USER") == null) 
						throw new Exception ("Session has expired, please log in again");
				    User user = (User) session.getAttribute("SESS_USER");
 					
 					
 					originalFileNameWithPath = basePath + originalFileName; //just in case
 					NeoBankEnvironment.setComment(3,className,"originalFileNameWithPath"+originalFileNameWithPath);
 					//** now get the file from the dummy external server using servlet calling into a temporary directory. Don't forget to DELETE the temp file in finally block
 	                HttpPost post = new HttpPost(NeoBankEnvironment.getFDocServerURL());
 			        // add request parameter, form parameters and only the file details.
 			        List<NameValuePair> urlParameters = new ArrayList<>();
 			        urlParameters.add(new BasicNameValuePair("qs", "fud"));
 			        urlParameters.add(new BasicNameValuePair("rules", "downloadspecificfile"));
 			        urlParameters.add(new BasicNameValuePair("fullqualifiedname", originalFileNameWithPath));
 			        post.setEntity(new UrlEncodedFormEntity(urlParameters));
 			        NeoBankEnvironment.setComment(3, className,"***** after POST urlParameters "+ urlParameters);
 			     //** Step 2. Step 3 and 4 to be done at the external servlet process
 			        CloseableHttpClient httpClient = HttpClients.createDefault();
 			        CloseableHttpResponse urlresponse = httpClient.execute(post); 
 			     //** Step 5 
 			        String	base64FileString = EntityUtils.toString(urlresponse.getEntity());
 			        if(base64FileString!=null) {
 			        	if(base64FileString.equals("error") == true) {
 			        		throw new Exception ("No file received.");
 			        	}
 			        }
 			       //NeoBankEnvironment.setComment(3, className, "inside assetdownload : response base64FileString is " + base64FileString);


 					String basePathTemp =  NeoBankEnvironment.getFileUploadPath()+ File.separator +"temp"+ File.separator;
					basePathTemp = StringUtils.replace(basePathTemp, "\\", "/");
 			        String justFileName = originalFileNameWithPath.substring(originalFileNameWithPath.lastIndexOf("/")+1, originalFileNameWithPath.length());

  			       NeoBankEnvironment.setComment(3, className, "inside assetdownload : justFileName is " + justFileName);

				  //** Step 6 
					File fileTemp = new File(basePathTemp + justFileName);// this 
					NeoBankEnvironment.setComment(3, className, "inside assetdownload : fileTemp fully qualified name  is " + fileTemp.getAbsolutePath());
					
					
					//Decode the file and store
		        	try ( FileOutputStream fos = new FileOutputStream(fileTemp); ) {
						byte[] decoder = Base64.decodeBase64(base64FileString); //apche common
						fos.write(decoder);
						 if(fos!=null) fos.close();

					 }catch(Exception e) {
						 NeoBankEnvironment.setComment(2, className,"Exception in download file "+ e.getMessage());
					throw new Exception ("Problem in decoding and writing images :"+e.getMessage());
					 }finally {
					}
					
					NeoBankEnvironment.setComment(3, className, "==>  file detected : "+fileTemp.isFile() +" at path "+fileTemp.getAbsolutePath());
					//response.setCharacterEncoding("UTF-8");
					response.setContentType("application/octet-stream");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + justFileName + "\"");
					//response.setContentLength( (int) fileTemp.length() );

		        	FileInputStream fileInputStream = new FileInputStream(fileTemp );
					
					  NeoBankEnvironment.setComment(3, className, "Total path is : " + fileTemp);
			       //out_download2 = response.getWriter(); This doesn't Working
					ServletOutputStream sos = response.getOutputStream();
					 
			       //Working code Option 1
						int size = (int) fileTemp.length();
							byte[] buffer = new byte[size];
							while((fileInputStream.read(buffer, 0, size)) != -1){
								sos.write(buffer, 0, size);
							}
						if (fileInputStream != null) fileInputStream.close();
						if(sos!=null) sos.close();
							
					
						try {
					if(fileTemp.exists()) { 
						@SuppressWarnings("unused")
						boolean result = fileTemp.delete();
							if (userType!=null) {
	
							     SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "KYC","Customer has downloaded KYC "+relationshipNo );
							}else {
								CustomerDao.class.getConstructor().newInstance().changeKYCStatus(relationshipNo, originalFileName);
								 NeoBankEnvironment.setComment(3, className, "status changed to reviewed");
							     SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(user.getUserId(),"O", "KYC","Operator has Downloaded KYC for "+relationshipNo );
							}
						
						}
					
					}finally {
						if( base64FileString != null) base64FileString = null;	if( justFileName != null) justFileName = null;	if( basePathTemp != null) basePathTemp = null;						
						if(urlParameters.isEmpty() == false) urlParameters.clear();if( user != null) user = null;
						if( originalFileNameWithPath != null) originalFileNameWithPath = null;
						if( fileTemp != null) { if(fileTemp.exists()) fileTemp.delete(); } if (userType!=null)userType=null;
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, "Error for rules assetdownload is : " + e.getMessage());
					
				} 			
			break;
			
			case "assetdownload2":
				
				  String sbResponse1 = null;  File downloadedFile1 = null;  
					PrintWriter out_download2 = null; String originalFileNameWithPath = null;				

					try {
						 String filePath = null; 
	 					if (request.getParameter("hdnassetpath") != null)	originalFileNameWithPath = request.getParameter("hdnassetpath").trim();

						JSONParser jsonParser1 = null; JSONObject jsonObject = null; String base64File = null;CloseableHttpClient httpClient = null;
						CloseableHttpResponse urlresponse = null; HttpPost post = null; List<NameValuePair> urlParameters = null; String message = null;
	 					originalFileNameWithPath = StringUtils.replace(originalFileNameWithPath, "\\", "/"); //just in case

		 			       NeoBankEnvironment.setComment(3, className, " originalFileNameWithPath "+ originalFileNameWithPath);

						try {
		 	                 post = new HttpPost(NeoBankEnvironment.getFDocServerURL());
					        // add request parameter, form parameters
					        urlParameters = new ArrayList<>();
					        urlParameters.add(new BasicNameValuePair("qs", "fud"));
					        urlParameters.add(new BasicNameValuePair("rules", "downloadspecificfile"));
					        urlParameters.add(new BasicNameValuePair("fullqualifiedname", originalFileNameWithPath));				     
					       
					        post.setEntity(new UrlEncodedFormEntity(urlParameters));
				        	//PPWalletEnvironment.setComment(3, className,"***** after POST urlParameters "+ urlParameters);

					         httpClient = HttpClients.createDefault();
					         urlresponse = httpClient.execute(post); 
					         sbResponse1 = EntityUtils.toString(urlresponse.getEntity());
			 			       NeoBankEnvironment.setComment(3, className, " sbResponse1 "+ sbResponse1);

			 			        if(sbResponse1!=null) {
			 			        	if(sbResponse1.equals("error") == true) {
			 			        		throw new Exception ("No file received.");
			 			        	}
			 			        }						        
					        	//out_download2 = response.getWriter();
				 			       NeoBankEnvironment.setComment(3, className, " after sbResponse1 ");

					        	if(sbResponse1 != null) {
						        	//PPWalletEnvironment.setComment(3, className,"***** after receiving  post for download "+ sbResponse1);
  					        		NeoBankEnvironment.setComment(3, className,"before ");

						        	  jsonParser1 = new JSONParser();
									  jsonObject = (JSONObject) jsonParser1.parse(sbResponse1);
						        	  message= (String) jsonObject.get("message");
	  					        	NeoBankEnvironment.setComment(3, className,"after ");
						        	  
						        	  if(message.equals("success")) {
		    							  base64File= (String) jsonObject.get("base64file");
		    							  String justFileName = originalFileNameWithPath.substring(originalFileNameWithPath.lastIndexOf("/")+1, originalFileNameWithPath.length());
		    			 			       NeoBankEnvironment.setComment(3, className, "inside assetdownload : justFileName is " + justFileName);

		    			 				   String basePathTemp =  NeoBankEnvironment.getFileUploadPath()+ File.separator +"temp"+ File.separator;
		    								basePathTemp = StringUtils.replace(basePathTemp, "\\", "/");
			    			 			       NeoBankEnvironment.setComment(3, className, "full path of downloaded stream " + basePathTemp+justFileName);
				    							 downloadedFile1 = new File(basePathTemp+justFileName );
		    								
		    						        	try ( FileOutputStream fos = new FileOutputStream(downloadedFile1); ) {
		    										byte[] decoder = Base64.decodeBase64(base64File); //apche common

		    										fos.write(decoder);
		    									 }catch(Exception e) {
		    										 NeoBankEnvironment.setComment(2, className,"Exception in download file "+ e.getMessage());
		    									throw new Exception ("Problem in decoding and writing images :"+e.getMessage());
		    									 }finally {
		    										 	
		    									}
		    						        	NeoBankEnvironment.setComment(2, className,"filepath is "+ StringUtils.replace(downloadedFile1.getAbsolutePath(), "\\", "/") );
						        		  
	  					        	  }else {
	  					        		NeoBankEnvironment.setComment(1, className,"File download Failed:");
								                throw new Exception("File download Failed");
	  					        	  }	
					        	}else {
					        		NeoBankEnvironment.setComment(1, className,"File system server not responding "  );

					        	}
							}catch(Exception e) {
								NeoBankEnvironment.setComment(1, className, "Exception in File download is : "+e.getMessage());
								
							}finally {
								if(httpClient!=null) httpClient.close(); if(urlresponse!=null) urlresponse.close();
								 //if(sbResponse1 !=null) sbResponse = null; if(jsonParser1 != null) jsonParser1=null;
								 //if(urlParameters != null) urlParameters = null; if(post != null) post.releaseConnection();
								 if(jsonObject != null) jsonObject=null; if(base64File != null) base64File=null; if(message != null) message=null;
							}
						String basePathTemp =  NeoBankEnvironment.getFileUploadPath()+ File.separator +"temp"+ File.separator;
						basePathTemp = StringUtils.replace(basePathTemp, "\\", "/");
	 			        String justFileName = originalFileNameWithPath.substring(originalFileNameWithPath.lastIndexOf("/")+1, originalFileNameWithPath.length());
						File fileTemp = new File(basePathTemp + justFileName);// this 
							
						//out_download11 = response.getWriter();
						//String filePath2 = PPWalletEnvironment.getFileDownloadPath();
						response.setContentType("APPLICATION/OCTET-STREAM");
						response.setHeader("Content-Disposition", "attachment; filename=\"" + originalFileNameWithPath.substring(originalFileNameWithPath.lastIndexOf("/")+1, originalFileNameWithPath.length()) );
			        	out_download2 = response.getWriter();
						FileInputStream fileInputStream = new FileInputStream(filePath);
						ServletOutputStream sos = response.getOutputStream();
			        	int size = (int)fileTemp.length();
						byte[] buffer = new byte[size];
						while((fileInputStream.read(buffer, 0, size)) != -1){
							sos.write(buffer, 0, size);
						}
						if (fileInputStream != null) fileInputStream.close();
						if(sos!=null) sos.close();
				        					        	
						/*NeoBankEnvironment.setComment(3, className, "Total path is : " + filePath);
							int i;
								while ((i = fileInputStream.read()) != -1) {
									out_download2.write(i);
								}
							fileInputStream.close();*/
						
						} catch (Exception e) {
							NeoBankEnvironment.setComment(3, className, "Failed to download file : "+ e.getMessage());
						} finally {
							if (out_download2 != null) 	out_download2.close();
							 // if(downloadedFile1 != null);  downloadedFile1.delete();
						}
					break;
			
			
		}
		
	}

}
