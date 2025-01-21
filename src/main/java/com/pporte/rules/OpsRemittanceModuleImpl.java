package com.pporte.rules;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.stellar.sdk.KeyPair;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.BitcoinDao;
import com.pporte.dao.CustomerCoinsDao;
import com.pporte.dao.CustomerDao;
import com.pporte.dao.CustomerDigitalAssetsDao;
import com.pporte.dao.CustomerWalletDao;
import com.pporte.dao.OpsFiatWalletDao;
import com.pporte.dao.RemittanceDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.model.AssetAccount;
import com.pporte.model.AssetCoin;
import com.pporte.model.Customer;
import com.pporte.model.Partner;
import com.pporte.model.Transaction;
import com.pporte.model.User;
import com.pporte.utilities.Bip39Utility;
import com.pporte.utilities.BitcoinUtility;
import com.pporte.utilities.CurrencyTradeUtility;
import com.pporte.utilities.ReceiptInvoiceUtility;
import com.pporte.utilities.SendEmailUtility;
import com.pporte.utilities.StellarSDKUtility;
import com.pporte.utilities.Utilities;
import framework.v8.Rules;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class OpsRemittanceModuleImpl implements Rules {
	private static String className = OpsRemittanceModuleImpl.class.getSimpleName();
	@Override
	public void performJSONOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
	
		
	}

	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			 ServletContext ctx) throws Exception {
		switch (rulesaction) {
		case "check_if_digital_assets_are_created_partner":
			try {
				String userId = null;JsonObject obj = new JsonObject(); PrintWriter output = null;
				String tokenizedMnemonicFlag =null; KeyPair keyPair = null;
				String mnemoniStringFromDB = null; String masterKeyGenerationBTCResult = null;
				long creationtime = 0;
				HttpSession session = request.getSession(false);
				if (session.getAttribute("SESS_USER") == null) {
					Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
					return;
				}
				User user = (User) session.getAttribute("SESS_USER");
				userId = user.getUserId();
				//if(request.getParameter("relno")!=null)relationshipNo = request.getParameter("relno").trim();
				tokenizedMnemonicFlag = (String) CustomerCoinsDao.class.getConstructor().newInstance().checkIfPartnerHasAddedMnemonic(userId);
				if(tokenizedMnemonicFlag==null) {
					obj.addProperty("error","false");
					obj.addProperty("process_status","N"); //N-Not Complete C-Complete
				}else if (tokenizedMnemonicFlag.equals("Y")) {
					obj.addProperty("error","false");
					obj.addProperty("mnemonic_flag",tokenizedMnemonicFlag);
					obj.addProperty("process_status","C"); //N-Not Complete C-Complete
					mnemoniStringFromDB = (String) CustomerDao.class.getConstructor().newInstance()
							.getPartnerMnemonicCode(userId);
					//Get keys from bit 39
					keyPair= Bip39Utility.masterKeyGeneration(mnemoniStringFromDB.replaceAll(",", " "));
					if(keyPair==null) {
						throw new Exception("Error in getting keys");
					}
					masterKeyGenerationBTCResult = BitcoinUtility.bitcoinMasterKeyGenerationFromSeed(
							mnemoniStringFromDB.replaceAll(",", " "), creationtime);
					NeoBankEnvironment.setComment(3, className,"Step 3 "+masterKeyGenerationBTCResult);
					obj.addProperty("public_key", keyPair.getAccountId());
					obj.addProperty("private_key", String.valueOf(keyPair.getSecretSeed()));
					
				}else if (tokenizedMnemonicFlag.equals("N")) {
					//
					obj.addProperty("error","false");
					obj.addProperty("process_status","C"); //N-Not Complete C-Complete
					obj.addProperty("mnemonic_flag",tokenizedMnemonicFlag);
				}
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + (obj.toString()));
					output = response.getWriter();
					output.print((obj));
				} finally {
					if (output != null)output.close(); if (userId != null)userId = null; 
					if (mnemoniStringFromDB != null)mnemoniStringFromDB = null; if (obj != null) obj = null;
					if (keyPair != null) keyPair = null;  if (masterKeyGenerationBTCResult != null)masterKeyGenerationBTCResult = null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error In checking if process is complete, Please try again letter");
			}
			
			
			break;
		
		case "addpartner":
			try {
				JsonObject obj = new JsonObject(); String fullName= null; String email = null;
				PrintWriter output = null; boolean success = false;
				User user = null; String userType = null;
				String phoneNo= null; String location = null;
				String publicKey= null; String currency = null;
				String password = null;
				String subject=null; String content=null;
				if(request.getParameter("fname")!=null)
					fullName = StringUtils.trim( request.getParameter("fname") );
				if(request.getParameter("uemail")!=null)
					email = StringUtils.trim( request.getParameter("uemail") );
				if(request.getParameter("phoneno")!=null)
					phoneNo = StringUtils.trim( request.getParameter("phoneno") );
				if(request.getParameter("location")!=null)
					location = StringUtils.trim( request.getParameter("location") );
				if(request.getParameter("publickey")!=null)
					publicKey = StringUtils.trim( request.getParameter("publickey") );
				if(request.getParameter("addselcurrency")!=null)
					currency = StringUtils.trim( request.getParameter("addselcurrency") );
				HttpSession session = request.getSession(false);
				if (session.getAttribute("SESS_USER") == null) {
					Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
					return;
				}
				
				
				password = RandomStringUtils.randomAlphabetic(6);  
				password="test1234"; // TODO:- Delete line for production
				user = (User) session.getAttribute("SESS_USER");
				userType="O";
				success = RemittanceDao.class.getConstructor().newInstance().addPartner(fullName, 
						email, phoneNo, location, publicKey, currency, password );
				
		
				NeoBankEnvironment.setComment(3, className, "Password is "+password);
				if(success) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(user.getUserId(), userType,
							"P"," Added new partner ");
					obj.addProperty("error", "false"); 
	        		obj.addProperty("message", "Partner added suceessfully");
	        		
	        		 subject="WELCOME TO PAYMENT PORTE!";
					 content="You have been successfully onboarded to the system. Here is the one time password you will use to login into the system:- "+password;
					// Send to user
					String sendto = email;
					String sendSubject = subject;
					String sendContent = content;
					String customerName = email;
					
					ExecutorService executor = (ExecutorService) ctx.getAttribute("EMAIL_EXECUTOR");
					NeoBankEnvironment.setComment(3, className, "executor is  " + executor);
					@SuppressWarnings({ "unused", "rawtypes" })
					Future future = (Future) executor.submit(new Runnable() {
						public void run() {
							
							try {
								SendEmailUtility.sendMail(sendto, sendSubject, sendContent,customerName);
							} catch (Exception e) {
								NeoBankEnvironment.setComment(1, className, "Exception in sending mail " + e.getMessage());
							}
						}
					});
				    
				}else {
	        		obj.addProperty("error", "true"); 
	        		obj.addProperty("message", "Problem in adding new partner "); 
				}
				
				try {
					NeoBankEnvironment.setComment(3, className, rulesaction+ " String is " + (obj.toString()));
					output = response.getWriter();
					output.print(obj);
				}finally {
					if(fullName!=null) fullName=null; if(email!=null) email=null;
					if(user!=null) user=null; if(userType!=null) userType=null;
					if(phoneNo!=null) phoneNo=null; if(location!=null) location=null;
					if(currency!=null) currency=null; if(password!=null) password=null;
					if(obj!=null) obj = null;if (publicKey!=null) publicKey=null;
					if(output!=null) output.close();
					if (subject!=null);subject=null; if (content!=null);content=null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Adding adding new partner failed, Please try again letter");
			}
			
			break;
			
			case "editpartner":
				try {
					JsonObject obj = new JsonObject(); String fullName= null; String email = null;
					PrintWriter output = null; boolean success = false;
					User user = null; String userType = "O"; String status = null;
					String phoneNo= null; String location = null;
					String publicKey= null; String currency = null;
					
					if(request.getParameter("efname")!=null)
						fullName = StringUtils.trim( request.getParameter("efname") );
					if(request.getParameter("euemail")!=null)
						email = StringUtils.trim( request.getParameter("euemail") );
					if(request.getParameter("ephoneno")!=null)
						phoneNo = StringUtils.trim( request.getParameter("ephoneno") );
					if(request.getParameter("elocation")!=null)
						location = StringUtils.trim( request.getParameter("elocation") );
					if(request.getParameter("epublickey")!=null)
						publicKey = StringUtils.trim( request.getParameter("epublickey") );
					if(request.getParameter("selcurrency")!=null)
						currency = StringUtils.trim( request.getParameter("selcurrency") );
					if(request.getParameter("status")!=null)
						status = StringUtils.trim( request.getParameter("status") );
					
					NeoBankEnvironment.setComment(3, className,"fullName "+fullName+" email "
					+email+" phoneNo "+phoneNo+" location "+location+" publicKey "+publicKey+" currency "+currency+" status "+status);
					
					HttpSession session = request.getSession(false);
					if (session.getAttribute("SESS_USER") == null) {
						Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
						return;
					}
					user = (User) session.getAttribute("SESS_USER");
					
					success = RemittanceDao.class.getConstructor().newInstance().editPartner(fullName, 
							email, phoneNo, location, publicKey, currency, status  );
					
					if(success) {
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(user.getUserId(), userType,
								"P"," Edited partner "+email);
						obj.addProperty("error", "false"); 
		        		obj.addProperty("message", "Partner edited suceessfully");
					}else {
		        		obj.addProperty("error", "true"); 
		        		obj.addProperty("message", "Problem in editing partner "); 
					}
					try {
						NeoBankEnvironment.setComment(3, className, rulesaction+ " String is " + (obj.toString()));
						output = response.getWriter();
						output.print(obj);
					}finally {
						if(fullName!=null) fullName=null; if(email!=null) email=null;
						if(user!=null) user=null; if(userType!=null) userType=null;
						if(phoneNo!=null) phoneNo=null; if(location!=null) location=null;
						if(currency!=null) currency=null; if(status!=null) status=null;
						if(obj!=null) obj = null;if (publicKey!=null) publicKey=null;
						if(output!=null) output.close();
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
					Utilities.sendJsonResponse(response, "error", "Editing partner failed, Please try again letter");
				}
				
				break;
				
				case "editpartnerdetails":
					try {
						JsonObject obj = new JsonObject(); String fullName= null; 
						PrintWriter output = null; boolean success = false;
						User user = null; String userType = "P"; 
						String phoneNo= null; String location = null;
						String publicKey= null; 
						
						if(request.getParameter("editname")!=null)
							fullName = StringUtils.trim( request.getParameter("editname") );
						if(request.getParameter("editcontact")!=null)
							phoneNo = StringUtils.trim( request.getParameter("editcontact") );
						if(request.getParameter("location")!=null)
							location = StringUtils.trim( request.getParameter("location") );
						if(request.getParameter("editpublickey")!=null)
							publicKey = StringUtils.trim( request.getParameter("editpublickey") );
						
						HttpSession session = request.getSession(false);
						if (session.getAttribute("SESS_USER") == null) {
							Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
							return;
						}
						user = (User) session.getAttribute("SESS_USER");
						
						success = RemittanceDao.class.getConstructor().newInstance().editPartnerProfile(fullName, 
								 phoneNo, location, publicKey, user.getUserId()  );
						
						if(success) {
							SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(user.getUserId(), userType,
									"P"," Updated profile "+user.getUserId());
							obj.addProperty("error", "false"); 
			        		obj.addProperty("message", "Profile successfully edited");
						}else {
			        		obj.addProperty("error", "true"); 
			        		obj.addProperty("message", "Problem in editing profile "); 
						}
						try {
							NeoBankEnvironment.setComment(3, className, rulesaction+ " String is " + (obj.toString()));
							output = response.getWriter();
							output.print(obj);
						}finally {
							if(fullName!=null) fullName=null; 
							if(user!=null) user=null; if(userType!=null) userType=null;
							if(phoneNo!=null) phoneNo=null; if(location!=null) location=null;
							if(obj!=null) obj = null;if (publicKey!=null) publicKey=null;
							if(output!=null) output.close();
						}
						
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "Error in updating, Please try again letter");
					}
				
				break;
				
				case "partner_change_txn_status":
					try {
						JsonObject obj = new JsonObject(); 
						PrintWriter output = null; boolean success = false;String bankCode=null;
						User user = null; String userType = "P"; String receiverName=null;
						String systemRef= null; String comment = null;String destAmount=null;
						String status= null; String srcAmount=null;String stellarHash=null;
						String receiverBank=null;String receiverBankAcc=null;String receiverEmail=null;
						Partner partner = null; String partnerName=null;String emailId=null;
						String partnerContact=null;String date=null;
						String subject=null; String content=null; String senderRelationshipNo=null; Customer c_Details=null;
						 
						if(request.getParameter("system_ref")!=null)systemRef = StringUtils.trim( request.getParameter("system_ref") );
						if(request.getParameter("partner_comment")!=null)comment = StringUtils.trim( request.getParameter("partner_comment") );
						if(request.getParameter("select_status")!=null)status = StringUtils.trim( request.getParameter("select_status") );
						if(request.getParameter("sp_source_amount")!=null)srcAmount = StringUtils.trim( request.getParameter("sp_source_amount") );
						if(request.getParameter("sp_txn_hash")!=null)stellarHash = StringUtils.trim( request.getParameter("sp_txn_hash") );
						if(request.getParameter("sp_des_amount")!=null)destAmount = StringUtils.trim( request.getParameter("sp_des_amount") );
						if(request.getParameter("sp_receiver_name")!=null)receiverName = StringUtils.trim( request.getParameter("sp_receiver_name") );
						if(request.getParameter("sp_bank_name")!=null)receiverBank = StringUtils.trim( request.getParameter("sp_bank_name") );
						if(request.getParameter("sp_bank_acount_no")!=null)receiverBankAcc = StringUtils.trim( request.getParameter("sp_bank_acount_no") );
						if(request.getParameter("sp_receiver_email")!=null)receiverEmail = StringUtils.trim( request.getParameter("sp_receiver_email") );
						if(request.getParameter("sp_bank_code")!=null)bankCode = StringUtils.trim( request.getParameter("sp_bank_code") );
						if(request.getParameter("sender_rel_no")!=null)senderRelationshipNo = StringUtils.trim( request.getParameter("sender_rel_no") );
						
						HttpSession session = request.getSession(false);
						if (session.getAttribute("SESS_USER") == null) {
							Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
							return;
						}
						user = (User) session.getAttribute("SESS_USER");
						
						partner=(Partner)RemittanceDao.class.getConstructor().newInstance().getPatnerDetails(user.getUserId());
						partnerName=partner.getParnerName();
						emailId=partner.getParnerEmail();
						partnerContact=partner.getParnerContact();
						date=Utilities.getMYSQLCurrentTimeStampForInsert();
						success = RemittanceDao.class.getConstructor().newInstance().editUpdateTxnStatus(systemRef, 
								comment, status,date);
						
						if(success) {
							NeoBankEnvironment.setComment(3,className,"After the dao");
							String result=ReceiptInvoiceUtility.printInvoice(partnerName, emailId, partnerContact, date, receiverEmail, receiverName,
									receiverBank, bankCode, receiverBankAcc, systemRef, stellarHash, srcAmount, destAmount);
							if(result.startsWith("true")) {
								NeoBankEnvironment.setComment(3,className,"Downloading receipt functionality");
								
								SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(user.getUserId(), userType,
										"P"," Partner changed transaction status to  "+status+"systemRef: "+systemRef);
								obj.addProperty("error", "false"); 
				        		obj.addProperty("message", "Transaction successfully updated.\n Invoice has been sent to your email");
				        		//Send an email to the partner
				        		NeoBankEnvironment.setComment(3,className,"result from dao is "+result);
				        		
				        		
				        		if (status.equals("C")) {
					        		
					        		 subject="TRANSACTION COMPLETED!-HERE'S YOUR INVOICE";
									 content="You have changed the transaction status to complete for the System Reference:- "+systemRef+"."+ System.lineSeparator()
												+ "We have attached the invoice and sent a notification to the receiver. ";
									// Send to user
									String sendto = emailId;
									String sendSubject = subject;
									String sendContent = content;
									String customerName = partnerName;
									String nameOfFile=result.substring(result.indexOf("|")+1, result.length()).concat(".pdf");
									String pathName=NeoBankEnvironment.getInvoiceFilePath().concat(nameOfFile);
									
									NeoBankEnvironment.setComment(3,className,"nameOfFile "+nameOfFile+" pathName "+pathName);
									
									ExecutorService executor = (ExecutorService) ctx.getAttribute("EMAIL_EXECUTOR");
									NeoBankEnvironment.setComment(3, className, "executor is  " + executor);
									@SuppressWarnings({ "unused", "rawtypes" })
									Future future = (Future) executor.submit(new Runnable() {
										public void run() {
											
											try {
												SendEmailUtility.sendMailWithAttachment(sendto, sendSubject, sendContent,customerName,nameOfFile,pathName);
											} catch (Exception e) {
												NeoBankEnvironment.setComment(1, className, "Exception in sending mail " + e.getMessage());
											}
										}
									});
									
									// Get Sender Details
									// Send to user
									 c_Details=(Customer)CustomerDao.class.getConstructor().newInstance().getFullCustomerProfile(senderRelationshipNo);
									
									 if (c_Details.getUnmaskedEmail().equals(receiverEmail)) {
										 subject="MONEY HAS BEEN SENT TO YOUR BANK";
										 content=destAmount+" has been sent to your bank "+receiverBank+"."+System.lineSeparator()+"Thank you for transacting with us. ";
										// Send to user
										String sendtoReceiver = receiverEmail;
										String sendSubjectReceiver = subject;
										String sendContentReceiver = content;
										String customerNameReceiver = receiverName;
										
										ExecutorService executor2 = (ExecutorService) ctx.getAttribute("EMAIL_EXECUTOR");
										NeoBankEnvironment.setComment(3, className, "executor is  " + executor2);
										@SuppressWarnings({ "unused", "rawtypes" })
										Future future2 = (Future) executor2.submit(new Runnable() {
											public void run() {
												
												try {
													SendEmailUtility.sendMail(sendtoReceiver, sendSubjectReceiver, sendContentReceiver,customerNameReceiver);
												} catch (Exception e) {
													NeoBankEnvironment.setComment(1, className, "Exception in sending mail " + e.getMessage());
												}
											}
										});
									 } else {
										 subject="MONEY HAS BEEN SENT TO THE RECEIVER'S BANK";
										 content=destAmount+" has been sent to the receiver's bank "+receiverBank+"."+System.lineSeparator()+"Thank you for transacting with us. ";
										// Send to user
										String sendtoSender = c_Details.getUnmaskedEmail();
										String sendSubjectSender = subject;
										String sendContentSender = content;
										String customerNameSender=  c_Details.getName();
										
										ExecutorService executor2 = (ExecutorService) ctx.getAttribute("EMAIL_EXECUTOR");
										NeoBankEnvironment.setComment(3, className, "executor is  " + executor2);
										@SuppressWarnings({ "unused", "rawtypes" })
										Future future2 = (Future) executor2.submit(new Runnable() {
											public void run() {
												try {
													SendEmailUtility.sendMail(sendtoSender, sendSubjectSender, sendContentSender,customerNameSender);
												} catch (Exception e) {
													NeoBankEnvironment.setComment(1, className, "Exception in sending mail " + e.getMessage());
												}
											}
										});
									 
										 subject="MONEY HAS BEEN SENT TO YOUR BANK";
										 content=destAmount+" has been sent to your bank "+receiverBank+"."+System.lineSeparator()+"Thank you for transacting with us. ";
										// Send to user
										String sendtoReceiver = receiverEmail;
										String sendSubjectReceiver = subject;
										String sendContentReceiver = content;
										String customerNameReceiver = receiverName;
										
										ExecutorService executor3 = (ExecutorService) ctx.getAttribute("EMAIL_EXECUTOR");
										NeoBankEnvironment.setComment(3, className, "executor is  " + executor3);
										@SuppressWarnings({ "unused", "rawtypes" })
										Future future3 = (Future) executor3.submit(new Runnable() {
											public void run() {
												try {
													SendEmailUtility.sendMail(sendtoReceiver, sendSubjectReceiver, sendContentReceiver,customerNameReceiver);
												} catch (Exception e) {
													NeoBankEnvironment.setComment(1, className, "Exception in sending mail " + e.getMessage());
												}
											}
										});
									 }
				        		}
							}else {
								obj.addProperty("error", "true"); 
				        		obj.addProperty("message", "Error in downloading receipt.");
							}
														
						}else {
			        		obj.addProperty("error", "true"); 
			        		obj.addProperty("message", "Transaction failed to updated"); 
						}
						try {
							NeoBankEnvironment.setComment(3, className, rulesaction+ " String is " + (obj.toString()));
							output = response.getWriter();
							output.print(obj);
						}finally {
							if(comment!=null) comment=null; if(stellarHash!=null) stellarHash=null;
							if(user!=null) user=null; if(userType!=null) userType=null;if(receiverName!=null) receiverName=null;
							if(systemRef!=null) systemRef=null;if (status!=null) status=null;if(receiverBankAcc!=null) receiverBankAcc=null;
							if(obj!=null) obj = null;if (srcAmount!=null) srcAmount=null;if(receiverBank!=null) receiverBank=null;
							if(output!=null) output.close();if(destAmount!=null) destAmount=null;if(receiverEmail!=null) receiverEmail=null;
							if(bankCode!=null) bankCode=null;
							if (subject!=null);subject=null; if (content!=null);content=null; if (c_Details!=null)c_Details=null;
							
						}
						
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "Error in updating transaction, Please try again letter");
					}
					break;
					
				case "partner_change_txn_status_pending":
					try {
						
						JsonObject obj = new JsonObject(); 
						PrintWriter output = null; boolean success = false;
						User user = null; String userType = "P"; 
						String systemRef= null; String comment = null;
						String status= null; 
						String receiverName=null; String receiverEmail=null; String custRelationshipNo=null;
						String bankName=null; String amount=null;
						
						
						if(request.getParameter("system_ref")!=null)
							systemRef = StringUtils.trim( request.getParameter("system_ref") );
						if(request.getParameter("partner_comment")!=null)
							comment = StringUtils.trim( request.getParameter("partner_comment") );
						if(request.getParameter("select_status")!=null)
							status = StringUtils.trim( request.getParameter("select_status") );
						if(request.getParameter("receiver_name")!=null)
							receiverName = StringUtils.trim( request.getParameter("receiver_name") );
						if(request.getParameter("receiver_email")!=null)
							receiverEmail = StringUtils.trim( request.getParameter("receiver_email") );
						if(request.getParameter("cust_relno")!=null)
							custRelationshipNo = StringUtils.trim( request.getParameter("cust_relno") );
						if(request.getParameter("bank_name")!=null)
							bankName = StringUtils.trim( request.getParameter("bank_name") );
						if(request.getParameter("amount")!=null)
							amount = StringUtils.trim( request.getParameter("amount") );
						
						HttpSession session = request.getSession(false);
						if (session.getAttribute("SESS_USER") == null) {
							Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
							return;
						}
						user = (User) session.getAttribute("SESS_USER");
						
						success = RemittanceDao.class.getConstructor().newInstance().updateTxnToStatus(systemRef, 
								comment, status);
						
						if(success) {
							SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(user.getUserId(), userType,
									"P"," Partner changed transaction status to  "+status+"systemRef: "+systemRef);
							obj.addProperty("error", "false"); 
			        		obj.addProperty("message", "Transaction successfully updated");
			        		
						}else {
			        		obj.addProperty("error", "true"); 
			        		obj.addProperty("message", "Transaction failed to updated"); 
						}
						try {
							NeoBankEnvironment.setComment(3, className, rulesaction+ " String is " + (obj.toString()));
							output = response.getWriter();
							output.print(obj);
						}finally {
							if(comment!=null) comment=null; 
							if(user!=null) user=null; if(userType!=null) userType=null;
							if(systemRef!=null) systemRef=null; ;
							if(obj!=null) obj = null;if (status!=null) status=null;
							if(output!=null) output.close();
							if(receiverName!=null) receiverName = null;if (receiverEmail!=null) receiverEmail=null;
							if(bankName!=null) bankName = null;if (amount!=null) amount=null;
							if(custRelationshipNo!=null) custRelationshipNo = null;
						}
						
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "Error in updating transaction, Please try again letter");
					}
					
					break;
					
				case "partner_create_trustline":
					try {
						JsonObject obj = new JsonObject(); 
						PrintWriter output = null; 
						User user = null; String userType = "P"; 
						String issuers = null;
						String privateKey= null; 
						HttpSession session = request.getSession(false);
						if (session.getAttribute("SESS_USER") == null) {
							Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
							return;
						}
						user = (User) session.getAttribute("SESS_USER");
						String currency = user.getCurrency();
						
						if(request.getParameter("values")!=null)
							issuers = StringUtils.trim( request.getParameter("values") );
						if(request.getParameter("input_private_key")!=null)
							privateKey = StringUtils.trim( request.getParameter("input_private_key") );
						if(issuers == null||issuers.isEmpty())
							throw new  Exception("No issuers");
						String issuersArray[] = issuers.split(",");
						for(int i = 0; i<issuersArray.length; i++) {
							StellarSDKUtility.createTrustline(issuersArray[i], privateKey, org.stellar.sdk.Transaction.MIN_BASE_FEE,
									"922337203685",  currency);
						}
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(user.getUserId(), userType,
								"P"," Partner created trustline");
						obj.addProperty("error", "false"); 
		        		obj.addProperty("message", "Trustline succcessfuly created");
		        		try {
							NeoBankEnvironment.setComment(3, className, rulesaction+ " String is " + (obj.toString()));
							output = response.getWriter();
							output.print(obj);
						}finally {
							if(issuers!=null) issuers=null; 
							if(currency!=null) currency=null; 
							if(user!=null) user=null; if(userType!=null) userType=null;
							if(privateKey!=null) privateKey=null; ;
							if(obj!=null) obj = null;if (issuersArray!=null) issuersArray=null;
							if(output!=null) output.close();
						}
						
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "Error in Creating Trustline, Please try again letter");
					}
					break;
					
				case "set_password":
					try {
						JsonObject obj = new JsonObject(); 
						PrintWriter output = null; 
						User user = null; String userType = "P"; 
						String oldPassword = null;
						String newPassword= null; 
						String userId=null;
						boolean success = false;
						boolean checkOldPassword = false;
						HttpSession session = request.getSession(false);
						if (session.getAttribute("SESS_USER") == null) {
							Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
							return;
						}
						user = (User) session.getAttribute("SESS_USER");
						userId = user.getUserId();
						if(request.getParameter("old_password")!=null)
							oldPassword = StringUtils.trim( request.getParameter("old_password") );
						if(request.getParameter("new_password")!=null)
							newPassword = StringUtils.trim( request.getParameter("new_password") );
						checkOldPassword = RemittanceDao.class.getConstructor().newInstance().checkUserPassword(userId,  oldPassword);
						if(!checkOldPassword) {
							Utilities.sendJsonResponse(response, "error", "Incorect password try again");
							return;
						}
						//String password, String userId
						success = RemittanceDao.class.getConstructor().newInstance().updatePassword(newPassword, 
								userId);
						if(success) {
							SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(user.getUserId(), userType,
									"P","Partner updated password on first time to login");
							obj.addProperty("error", "false"); 
			        		obj.addProperty("message", "Password updated successfuly, Please login again");
			        		
						}else {
			        		obj.addProperty("error", "true"); 
			        		obj.addProperty("message", "Password update failed"); 
						}
						try {
							NeoBankEnvironment.setComment(3, className, rulesaction+ " String is " + (obj.toString()));
							output = response.getWriter();
							output.print(obj);
						}finally {
							if(oldPassword!=null) oldPassword=null; 
							if(user!=null) user=null; if(userType!=null) userType=null;
							if(newPassword!=null) newPassword=null; ;
							if(obj!=null) obj = null;if (userId!=null) userId=null;
							if(output!=null) output.close();
						}
						
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "Error in Changing Password, Please try again letter");
					}
					
					break;
					
					
				case"get_remittance_txn":
					try {
						JsonObject obj = new JsonObject();User user = null; String walletType ="F";
						PrintWriter output = null;List<Transaction> transactionList = null;ConcurrentHashMap<String, String> hashTxnRules = null;
						Gson gson = new Gson();String txnDesc= null;ConcurrentHashMap<String, String> hashStatus = null;
						HttpSession session = request.getSession(false);String status=null;
						if (session.getAttribute("SESS_USER") == null) {
							Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
							return;
						}
						user = (User) session.getAttribute("SESS_USER");
						
						
						transactionList = (List<Transaction>)OpsFiatWalletDao.class.getConstructor().newInstance().getPatnerRemittanceTransactions();
						hashTxnRules = (ConcurrentHashMap<String, String>)CustomerWalletDao.class.getConstructor().newInstance().getTransactionRules();
						NeoBankEnvironment.setComment(3, className," hashTxnRules is "+hashTxnRules.size());
						hashStatus = new ConcurrentHashMap<String, String>();
						hashStatus.put("P", "Pending");
						hashStatus.put("C", "Completed");
						hashStatus.put("PP", "Pending Partner");
						hashStatus.put("PT", "Pending TDA");

						if (transactionList!=null) {
							for (int i = 0; i <transactionList.size(); i++) {
								if (transactionList.get(i).getSystemReferenceInt()!=null) {
									txnDesc= hashTxnRules.get(transactionList.get(i).getSystemReferenceInt().split("-")[0]);
									transactionList.get(i).setTxnDescription(txnDesc);
									
									status = hashStatus.get(transactionList.get(i).getStatus());
									//NeoBankEnvironment.setComment(3,className,"status is "+status);
									transactionList.get(i).setStatus(status);
									
								}
							}
							obj.add("data", gson.toJsonTree(transactionList));
							obj.add("error", gson.toJsonTree("false"));
						}else {
							obj.add("error", gson.toJsonTree("nodata"));
						}
	
						try {
							//NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
							output = response.getWriter();
							output.print(gson.toJson(obj));
						} finally {
							if (output != null)output.close();if (gson != null)gson = null;
							if (obj != null)obj = null;
							if (transactionList != null)transactionList = null; 
							if (user != null)user = null; 
							if (hashTxnRules != null)hashTxnRules = null; 
							if (txnDesc != null)txnDesc = null; 
							if (walletType != null)walletType = null; 
						}
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "Error in geting Fiat Transactions, Please try again letter");
					}
					break;
				case "get_remittance_txn_btn_dates":
					try {
						JsonObject obj = new JsonObject();
						User user = null;
						PrintWriter output = null;
						List<Transaction> transactionList = null;
						ConcurrentHashMap<String, String> hashTxnRules = null;
						Gson gson = new Gson();
						String txnDesc= null;
						String dateTo= null;
						String dateFrom =null;
						String oldFormat = "MM/dd/yyyy";
						String newFormat = "yyyy-MM-dd";
						SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
						
						if(request.getParameter("datefrom")!=null)	
							dateFrom = request.getParameter("datefrom").trim();
						if(request.getParameter("dateto")!=null)	
							dateTo = request.getParameter("dateto").trim();
						
						HttpSession session = request.getSession(false);
						if (session.getAttribute("SESS_USER") == null) {
							Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
							return;
						}
						user = (User) session.getAttribute("SESS_USER");
						hashTxnRules = (ConcurrentHashMap<String, String>)CustomerWalletDao.class.getConstructor()
								.newInstance().getTransactionRules();
						
						
						Date d = sdf.parse(dateFrom); Date d2 = sdf.parse(dateTo);
						sdf.applyPattern(newFormat);  sdf.applyPattern(newFormat);
						dateFrom = sdf.format(d);     dateTo = sdf.format(d2);
						
						//NeoBankEnvironment.setComment(3, className, "dateFrom "+ dateFrom);
						//NeoBankEnvironment.setComment(3, className, "dateTo "+ dateTo);
						
						transactionList = (List<Transaction>)OpsFiatWalletDao.class.getConstructor()
								.newInstance().getOpsRemitanceTxnBtnDates(dateFrom, dateTo);
						
						NeoBankEnvironment.setComment(3, className, "After fetch ");
						if (transactionList!=null) {
							for (int i = 0; i <transactionList.size(); i++) {
								if (transactionList.get(i).getSystemReferenceInt()!=null) {
									txnDesc= hashTxnRules.get(transactionList.get(i).getSystemReferenceInt().split("-")[0]);
									transactionList.get(i).setTxnDescription(txnDesc);
								}
							}
							obj.add("data", gson.toJsonTree(transactionList));
							obj.add("error", gson.toJsonTree("false"));
						}else {
							obj.add("error", gson.toJsonTree("nodata"));
						}
						//NeoBankEnvironment.setComment(3, className, "After loop ");
						
						try {
							//NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
							output = response.getWriter();
							output.print(gson.toJson(obj));
						} finally {
							if (output != null)output.close(); if (gson != null)gson = null;
							if (obj != null)obj = null; if (dateFrom != null)dateFrom = null;
							if (transactionList != null)transactionList = null; 
							if (user != null)user = null; 
							if (dateTo != null)dateTo = null; 
							if (hashTxnRules != null)hashTxnRules = null; 
							if (txnDesc != null)txnDesc = null; 
							if (sdf != null)sdf = null; if (d != null)d = null; 
							if (d2 != null)d2  = null; 
						}								
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "Error in geting FiatTransactions by date, Please try again letter");
					}
					break;
				case "partner_create_bitcoin_mnemocic_code":
					try {
						String hasAccount = null; User user = null;
						JsonObject obj = new JsonObject();
						String partnerUser = null; PrintWriter output = null; 
						JsonObject data = new JsonObject(); Gson gson = new Gson();
						HttpSession session = request.getSession(false);
						if(request.getParameter("has_account")!=null)hasAccount = request.getParameter("has_account").trim();
						if (session.getAttribute("SESS_USER") == null) {
							Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
							return;
						}
						
						user = (User) session.getAttribute("SESS_USER");
						partnerUser = user.getUserId();
						// Check if has already linked his account to BTC and Stellar using our system.
						if(BitcoinDao.class.getConstructor().newInstance().checkIfPartnerStellarHasBeenLinkedByCustomer(partnerUser)) {
							Utilities.sendJsonResponse(response, "error", "You have already linked your Wallets in our system");
							return;
						}
						NeoBankEnvironment.setComment(3, className, "hasAccount is "+hasAccount);
						if(!Boolean.parseBoolean(hasAccount)) {
							data = BitcoinUtility.createBitcoinDeterministicWallet();
							SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(partnerUser,"P", "PRS","Partner stellar mnemonic code generated" );
							obj.addProperty("error", "false");
							obj.add("data", gson.toJsonTree(data));
						}else {
							obj.addProperty("error", "true");
							obj.addProperty("message", "Error in creating mnemonic code");
						}
						try {
							NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
							output = response.getWriter();
							output.print(obj);
						} finally {
							if(user!=null) user=null; if(data!=null) data=null; if(gson!=null)gson=null;
							if(obj!=null) obj=null; if(hasAccount!=null) hasAccount=null;
							if(output!=null) output.close();
						}
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "Error In creating bitcoin mneumonic code, Please try again letter");
					}
					break;
				case "partner_add_stellar_new_account":
					try {
						String hasAccount = null; User user = null;int baseFee  = org.stellar.sdk.Transaction.MIN_BASE_FEE;
						JsonObject obj = new JsonObject();String issuerPorteAccountId = null; String issuerVeslAccountId=null;
						String issuerUsdcAccountId=null;String limit = null;
						boolean success = false; String partnerID = null;String veslAssetCode= NeoBankEnvironment.getVesselCoinCode();
						PrintWriter output = null;String password=null;String mnemonicCode=null;
						String encryptedMnemonic=null;String usdcAssetCode=NeoBankEnvironment.getUSDCCode();
						JsonArray mnemonicArray = new JsonArray();String porteAssetCode=NeoBankEnvironment.getPorteTokenCode();
						String mnemonicString = null; String assetCode = NeoBankEnvironment.getXLMCode(); 
						String data = null; JsonObject objBtcData = new JsonObject(); JsonArray jsonArrayMnemonic = null;
						HttpSession session = request.getSession(false);
						if (session.getAttribute("SESS_USER") == null) {
							Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
							return;
						}
						
						if(request.getParameter("has_account")!=null)hasAccount = request.getParameter("has_account").trim();
						//if(request.getParameter("security")!=null)password = request.getParameter("security").trim();
						if(request.getParameter("json_string")!=null)data = request.getParameter("json_string").trim();
						NeoBankEnvironment.setComment(3,className,"data is "+password);
						NeoBankEnvironment.setComment(3,className,"data is "+password);
						user = (User) session.getAttribute("SESS_USER");
						partnerID = user.getUserId();
						NeoBankEnvironment.setComment(3,className,"partnerID is "+partnerID);
						NeoBankEnvironment.setComment(3,className,"partnerID is "+Utilities.tripleEncryptData(partnerID));
						/*
						boolean passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
								.checkIfPartnerPasswordIsCorrect(partnerID, password);
						if(!passIsCorrect) {
							NeoBankEnvironment.setComment(1, className, "Password is not correct");
							Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
							return;
						}*/
						
						objBtcData = new Gson().fromJson(data, JsonObject.class);
						mnemonicArray = objBtcData.get("mnemonic_code").getAsJsonArray();
						mnemonicString = Bip39Utility.createCSVForMnemonic(mnemonicArray);

						encryptedMnemonic= Utilities.tripleEncryptData(mnemonicString);
						
						if(BitcoinDao.class.getConstructor().newInstance().checkIfPartnerStellarHasBeenLinkedByCustomer(partnerID)) {
							Utilities.sendJsonResponse(response, "error", "You have already linked your Wallets  in our system");
							return;
						}
						
						
						mnemonicString = mnemonicString.replaceAll(",", " ");
						NeoBankEnvironment.setComment(3, className, "mnemonicString "+mnemonicString);
						char [] mnemonicCharArray = mnemonicString.toCharArray();
						KeyPair keyPair = Bip39Utility.generateKeyPairs(mnemonicCharArray);
						
						// Fund account from Friendbot 
						// TODO Disable this in production
						StellarSDKUtility.createAccount(keyPair);
						
						//Insert To Stellar mnemonic
						boolean result  = (boolean)CustomerCoinsDao.class.getConstructor().newInstance().insertPartnerMnemonicCode(partnerID, encryptedMnemonic);
						if(!result) 
							throw new Exception ("Unable to insert Stellar mnemonic code");		
						
						//CBA - Customer Register
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(partnerID,"C", "CRB","Customer created Stellar account" );
						
						//Wallet Creation
						
						issuerPorteAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(porteAssetCode);
						issuerVeslAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(veslAssetCode);
						issuerUsdcAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(usdcAssetCode);
						limit = NeoBankEnvironment.getMaxStellarAssetWalletLimit();
						
						StellarSDKUtility.createTrustline(issuerPorteAccountId, String.valueOf( keyPair.getSecretSeed()),
								 baseFee, limit,  porteAssetCode );
						StellarSDKUtility.createTrustline(issuerVeslAccountId, String.valueOf( keyPair.getSecretSeed()),
								 baseFee, limit,  veslAssetCode );
						StellarSDKUtility.createTrustline(issuerUsdcAccountId, String.valueOf( keyPair.getSecretSeed()),
								 baseFee, limit,  usdcAssetCode);
						
//						obj.addProperty("error", "false");
//						obj.addProperty("message", "Wallet is set up successfully");
//						obj.addProperty("stellar_pub_key", keyPair.getAccountId());
//						obj.addProperty("stellar_piv_key", String.valueOf( keyPair.getSecretSeed()));
						try {
							success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerPartnerStellarAccount(Utilities.tripleEncryptData(keyPair.getAccountId())
									, "", partnerID, assetCode,"Y");

							if (!success) {
								// TODO- Do what?
							}
						} finally {
							if(user!=null) user=null; if(hasAccount!=null) hasAccount=null;
							if(obj!=null) obj=null;if(mnemonicArray!=null) mnemonicArray=null;
							if(output!=null) output.close();
							if(mnemonicCode!=null) mnemonicCode=null;
							if(encryptedMnemonic!=null) encryptedMnemonic=null;if(mnemonicString!=null) mnemonicString=null;
							if(partnerID!=null) partnerID=null;if(data!=null) data=null; 
							if(objBtcData!=null) objBtcData=null;if(jsonArrayMnemonic!=null) jsonArrayMnemonic=null;
							if(mnemonicCharArray!=null) mnemonicCharArray = null; 
							if(keyPair!=null) keyPair = null; if(assetCode!=null) assetCode = null;
						}
						
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "Error In creating Bitcoin account, Please try again letter");
					}
					break;
				case "link_partner_account_with_mnemonic_code":
					try {
						
						User user = null; 
						
						JsonObject obj = new JsonObject(); String encryptedMnemonic = null;
						String partnerID = null;String password=null;
						PrintWriter output = null; String firstMnemonic=null;
						String thirdMnemonic=null;String secMnemonic=null;String fourhMnemonic=null;String fifthMnemonic=null;
						String sixthMnemonic=null;String seventhMnemonic=null;String eithMnemonic=null;String nineMnemonic=null;
						String tenMnemonic=null;String eleventMnemonic=null;String twelveMnemonic=null;
					    boolean success = false; String assetCode = NeoBankEnvironment.getXLMCode();
					    HttpSession session = request.getSession(false);
						String btcAddressFromSeed = null; String btcPubKeyFromSeed = null;
						String veslAssetCode= NeoBankEnvironment.getVesselCoinCode();String usdcAssetCode=NeoBankEnvironment.getUSDCCode();
						String porteAssetCode=NeoBankEnvironment.getPorteTokenCode();String issuerPorteAccountId = null; 
						String limit = null;int baseFee  = org.stellar.sdk.Transaction.MIN_BASE_FEE;
						String issuerVeslAccountId=null;String issuerUsdcAccountId=null;
						if (session.getAttribute("SESS_USER") == null) {
							Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
							return;
						}
						
						if(request.getParameter("first_code")!=null)firstMnemonic = request.getParameter("first_code").trim();
						if(request.getParameter("second_code")!=null)secMnemonic = request.getParameter("second_code").trim();
						if(request.getParameter("third_code")!=null)thirdMnemonic = request.getParameter("third_code").trim();
						if(request.getParameter("fourth_code")!=null)fourhMnemonic = request.getParameter("fourth_code").trim();
						if(request.getParameter("fifth_code")!=null)fifthMnemonic = request.getParameter("fifth_code").trim();
						if(request.getParameter("sixth_code")!=null)sixthMnemonic = request.getParameter("sixth_code").trim();
						if(request.getParameter("seventh_code")!=null)seventhMnemonic = request.getParameter("seventh_code").trim();
						if(request.getParameter("eight_code")!=null)eithMnemonic = request.getParameter("eight_code").trim();
						if(request.getParameter("nineth_code")!=null)nineMnemonic = request.getParameter("nineth_code").trim();
						if(request.getParameter("tenth_code")!=null)tenMnemonic = request.getParameter("tenth_code").trim();
						if(request.getParameter("eleventh_code")!=null)eleventMnemonic = request.getParameter("eleventh_code").trim();
						if(request.getParameter("twelve_code")!=null)twelveMnemonic = request.getParameter("twelve_code").trim();
						if(request.getParameter("security")!=null)password = request.getParameter("security").trim();
						
						
						user = (User) session.getAttribute("SESS_USER");
						partnerID = user.getUserId();
						boolean passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
								.checkIfPartnerPasswordIsCorrect(partnerID, password);
						if(!passIsCorrect) {
							NeoBankEnvironment.setComment(1, className, "Password is not correct");
							Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
							return;
						}
						
						if(BitcoinDao.class.getConstructor().newInstance().checkIfPartnerStellarHasBeenLinkedByCustomer(partnerID)) {
							Utilities.sendJsonResponse(response, "error", "You have already linked your Stellar account in our system");
							return;
						}
						
						String nmemonic=firstMnemonic+" "+secMnemonic+" "+thirdMnemonic+" "+fourhMnemonic+" "+fifthMnemonic+" "+sixthMnemonic+" "+
								seventhMnemonic+" "+eithMnemonic+" "+nineMnemonic+" "+tenMnemonic+" "+eleventMnemonic+" "+twelveMnemonic;	
						
						
							
						/**
						 * Pass mnemonic code generated by BitcoinJ to Stellar Key Pair Generator here
						 */
						
						nmemonic = nmemonic.replaceAll(",", " ");
						NeoBankEnvironment.setComment(3, className, "nmemonic "+nmemonic);
						char [] mnemonicCharArray = nmemonic.toCharArray();
						KeyPair keyPair = Bip39Utility.generateKeyPairs(mnemonicCharArray);
						// Fund account from Friendbot 
						// TODO Disable this in production
						StellarSDKUtility.createAccount(keyPair);
						
						nmemonic =  nmemonic.replaceAll(" ", ",");
						
						// Check if has already linked his account to BTC and Stellar using our system.
						encryptedMnemonic= Utilities.tripleEncryptData(nmemonic);
												
						//Insert To Stellar mnemonic
						Boolean result  = (boolean)CustomerCoinsDao.class.getConstructor().newInstance().insertPartnerMnemonicCode(partnerID, encryptedMnemonic);
						if(!result) 
							throw new Exception ("Unable to insert Stellar mnemonic code");
						
						success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerPartnerStellarAccount(Utilities.tripleEncryptData(keyPair.getAccountId())
								, "", partnerID, assetCode,"Y");
						
						if(!success) 
							throw new Exception ("Unable to insert Stellar Details");
						
						
						//CBA - Customer Register
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(partnerID,"P", "PLA","Customer created new Stellar account" );
						
						//Wallet Creation
						
						issuerPorteAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(porteAssetCode);
						issuerVeslAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(veslAssetCode);
						issuerUsdcAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(usdcAssetCode);
						limit = NeoBankEnvironment.getMaxStellarAssetWalletLimit();
						
						StellarSDKUtility.createTrustline(issuerPorteAccountId, String.valueOf( keyPair.getSecretSeed()),
								 baseFee, limit,  porteAssetCode );
						
						StellarSDKUtility.createTrustline(issuerVeslAccountId, String.valueOf( keyPair.getSecretSeed()),
								 baseFee, limit,  veslAssetCode );
						
						StellarSDKUtility.createTrustline(issuerUsdcAccountId, String.valueOf( keyPair.getSecretSeed()),
								 baseFee, limit,  usdcAssetCode);
						
						obj.addProperty("error", "false");
						obj.addProperty("message", "Wallet is set up successfully");
						obj.addProperty("stellar_pub_key", keyPair.getAccountId());
						obj.addProperty("stellar_piv_key", String.valueOf( keyPair.getSecretSeed()));
						try {
							NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
							output = response.getWriter();
							output.print(obj);
						} finally {
							if(output!=null) output.close();if(user!=null) user=null;if(partnerID!=null) partnerID=null;
							if(password!=null) password=null;if(obj!=null) obj=null;
							if(firstMnemonic!=null) firstMnemonic=null;if(thirdMnemonic!=null) thirdMnemonic=null;
							if(secMnemonic!=null) secMnemonic=null;if(fourhMnemonic!=null) fourhMnemonic=null;if(fifthMnemonic!=null) fifthMnemonic=null;
							if(sixthMnemonic!=null) sixthMnemonic=null;if(seventhMnemonic!=null) seventhMnemonic=null;if(eithMnemonic!=null) eithMnemonic=null;
							if(nineMnemonic!=null) nineMnemonic=null;if(tenMnemonic!=null) tenMnemonic=null;if(eleventMnemonic!=null) eleventMnemonic=null;
							if(twelveMnemonic!=null) twelveMnemonic=null; if(btcAddressFromSeed!=null) btcAddressFromSeed=null; if(btcPubKeyFromSeed!=null) btcPubKeyFromSeed=null;
//							if(resultString!=null) resultString=null; 
							if(nmemonic!=null) nmemonic=null; if(encryptedMnemonic!=null) encryptedMnemonic=null; if(keyPair!=null) keyPair=null;
							if(mnemonicCharArray!=null) mnemonicCharArray=null; if(assetCode!=null) assetCode=null; 
						}
						
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "Error In linking Bitcoin account, Please try again letter");
					}
					
					break;
				case "link_partner_without_mnemonic":
					try {
						String password = null;
						String partnerID = null; User user = null; 
						JsonObject obj = new JsonObject(); PrintWriter output = null;
						String publicKey = null;String privateKey=null;
						String veslAssetCode= NeoBankEnvironment.getVesselCoinCode();String usdcAssetCode=NeoBankEnvironment.getUSDCCode();
						String porteAssetCode=NeoBankEnvironment.getPorteTokenCode();String issuerPorteAccountId = null; 
						String limit = null;int baseFee  = org.stellar.sdk.Transaction.MIN_BASE_FEE;
						String issuerVeslAccountId=null;String issuerUsdcAccountId=null;
						 HttpSession session = request.getSession(false);
						if (session.getAttribute("SESS_USER") == null) {
							Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
							return;
						}
						
						if(request.getParameter("security")!=null)password = request.getParameter("security").trim();
						if(request.getParameter("stellar_public_key")!=null)publicKey = request.getParameter("stellar_public_key").trim();
						if(request.getParameter("stellar_private_key")!=null)privateKey = request.getParameter("stellar_private_key").trim();
						user = (User) session.getAttribute("SESS_USER");
						partnerID = user.getUserId();
						boolean passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
								.checkIfPartnerPasswordIsCorrect(partnerID, password);
						if(!passIsCorrect) {
							NeoBankEnvironment.setComment(1, className, "Password is not correct");
							Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
							return;
						}
						
						if(BitcoinDao.class.getConstructor().newInstance().checkIfPartnerStellarHasBeenLinkedByCustomer(partnerID)) {
							Utilities.sendJsonResponse(response, "error", "You have already linked your Stellar account in our system");
							return;
						}
						
						KeyPair keyPair =  KeyPair.fromAccountId(publicKey);
						boolean accountExist = StellarSDKUtility.CheckAccountIfExist(keyPair);
						if(!accountExist) {
							Utilities.sendJsonResponse(response, "error", " Account Does not exist in stellar ");
							return;
						}
						boolean success = (boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerPartnerStellarAccount(Utilities.tripleEncryptData(keyPair.getAccountId())
								, "", partnerID, NeoBankEnvironment.getXLMCode(),"N" );
						if(!success) 
							throw new Exception ("Unable to insert Stellar Details");
						//CRB - Customer Register Bitcoin
						SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(partnerID,"P", "PLA","Partner Linked Stellar account without Seed Code" );
						
						//Wallet Creation
						issuerPorteAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(porteAssetCode);
						issuerVeslAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(veslAssetCode);
						issuerUsdcAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(usdcAssetCode);
						limit = NeoBankEnvironment.getMaxStellarAssetWalletLimit();
						
						
						StellarSDKUtility.createTrustline(issuerPorteAccountId, privateKey,
								 baseFee, limit,  porteAssetCode);
						StellarSDKUtility.createTrustline(issuerVeslAccountId, privateKey,
								 baseFee, limit,  veslAssetCode );
						StellarSDKUtility.createTrustline(issuerUsdcAccountId, privateKey,
								 baseFee, limit,  usdcAssetCode);
						
						obj.addProperty("error", "false");
						obj.addProperty("message", "You have successfully linked Bitcoin and Stellar Accounts");
					try {
						NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
						output = response.getWriter();
						output.print(obj);
					} finally {
						if(output!=null) output.close();if(user!=null) user=null;if(partnerID!=null) partnerID=null;
						if(password!=null) password=null;if(obj!=null) obj=null;
						if(keyPair!=null) keyPair=null;if(publicKey!=null) publicKey=null;
					}
						
					} catch (Exception e) {
						NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
						Utilities.sendJsonResponse(response, "error", "Error In linking Stellar account, Please try again letter");
					}
					
					break;

		}
		
	}

	@Override
	public void performOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx)
			throws Exception {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null)
			Utilities.callOpsException(request, response, ctx, "Session has expired, please log in again");
		switch (rulesaction) {
		case "View Partners":
			try {
				String langPref = null; 
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opsremit");
				request.setAttribute("lastrule", "View Partners");
				request.setAttribute("arrpartnerslist", (List<Partner>)RemittanceDao.class.getConstructor().newInstance().getAllPatners());
				request.setAttribute("digitalcurrencies", (ArrayList<AssetCoin>)RemittanceDao.class.getConstructor().newInstance().getDigitalCurrencyCodes());
				
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsViewPartnersPage()).forward(request, response);
				 } finally {
					 if (langPref != null)langPref = null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
		break;
		
		case "Partner Profile":
			try {
				
				String langPref = null; 
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "partdash");
				request.setAttribute("lastrule", "Partner Profile");
				User user = (User) session.getAttribute("SESS_USER");
								
				request.setAttribute("profiledetails", (Partner)RemittanceDao.class.getConstructor().newInstance().getPatnerDetails(user.getUserId()));
				
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getPartnersProfilePage()).forward(request, response);
				 } finally {
					 if (langPref != null)langPref = null;
					 if (user != null)user = null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
		
		break;
		
		case "Pending Transactions":
			try {
				String langPref = null; 
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "partdash");
				request.setAttribute("lastrule", "Pending Transactions");
				User user = (User) session.getAttribute("SESS_USER");
				request.setAttribute("transactions", (List<Transaction>)RemittanceDao.class.getConstructor().newInstance().getPatnersPendingTransactions(user.getUserId()));
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getPartnersPendingTransactionPage()).forward(request, response);
				 } finally {
					 if (langPref != null)langPref = null;
					 if (user != null)user = null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
			
		case "Completed Transactions":
			try {
				String langPref = null; 
				if(request.getParameter("hdnlangpref")!=null)	langPref = request.getParameter("hdnlangpref").trim();
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "partdash");
				request.setAttribute("lastrule", "Completed Transactions");
				User user = (User) session.getAttribute("SESS_USER");
				request.setAttribute("transactions", (List<Transaction>)RemittanceDao.class.getConstructor().newInstance().getPatnersCompleteTransactions(user.getUserId()));
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getPartnersCompleteTransactionPage()).forward(request, response);
				 } finally {
					 if (langPref != null)langPref = null;
					 if (user != null)user = null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
		break;
		
		case "Create Trustline":
			try {
				String langPref = null; 
				JsonObject issuersObj = null;
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "partdash");
				request.setAttribute("lastrule", "Create Trustline");
				User user = (User) session.getAttribute("SESS_USER");
				String currency = user.getCurrency();
				issuersObj = (JsonObject)CurrencyTradeUtility.getIssuersDataJson(currency);
//				for(int i=0; i<issuers.size(); i++) {
//					NeoBankEnvironment.setComment(3, className, "Public Key " +issuers.get(i));
//				}
				request.setAttribute("issuers", issuersObj);
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getPartnersViewIssuesPage()).forward(request, response);
				 } finally {
					 if (langPref != null)langPref = null;
					 if (currency != null)currency = null;
					 if (user != null)user = null;
					 if (issuersObj != null)issuersObj = null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			
			break;
			
		case "View Remittance Transactions":
			try {
				String langPref = null; 
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "opsremit");
				request.setAttribute("lastrule", "View Remittance Transactions");
				User user = (User) session.getAttribute("SESS_USER");
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getRemittanceTransactionPage()).forward(request, response);
				 } finally {
					 if (langPref != null)langPref = null;
					 if (user != null)user = null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		case "partner_stellar_acc_reg":
			try {
				request.setAttribute("lastaction", "lgt");
				request.setAttribute("lastrule", "partner_stellar_acc_reg");
				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getPartenerCreateStellarAcPage()).forward(request, response);
				} finally {
				}

			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			break;
		case "createtrustline_page":
			try {
				String langPref = null; 
				JsonObject issuersObj = null;
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "partdash");
				request.setAttribute("lastrule", "createtrustline_page");
				User user = (User) session.getAttribute("SESS_USER");
				String currency = user.getCurrency();
				issuersObj = (JsonObject)CurrencyTradeUtility.getIssuersDataJson(currency);
//				for(int i=0; i<issuers.size(); i++) {
//					NeoBankEnvironment.setComment(3, className, "Public Key " +issuers.get(i));
//				}
				request.setAttribute("issuers", issuersObj);
				response.setContentType("text/html");
				 try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getCreateTrustlinePage()).forward(request, response);
				 } finally {
					 if (langPref != null)langPref = null;
					 if (currency != null)currency = null;
					 if (user != null)user = null;
					 if (issuersObj != null)issuersObj = null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			break;
		case "Partner Home":
			try {
				NeoBankEnvironment.setComment(3, className, " Inside Partner Home");
				String langPref = null; 
				if(request.getParameter("hdnlangpref")!=null)			  langPref = request.getParameter("hdnlangpref").trim();
				request.setAttribute("langPref", langPref);
				request.setAttribute("lastaction", "partdash");
				request.setAttribute("lastrule", "Partner Home");
				User user = (User) session.getAttribute("SESS_USER");
								
				String publicKey = (String)RemittanceDao.class.getConstructor().newInstance().getPublicKey(user.getUserId());
				KeyPair userAccount = null;
				ArrayList<AssetAccount> accountBalances = null;
				NeoBankEnvironment.setComment(3, className, " Public key is  "+publicKey);
				if(publicKey != null) {
					userAccount = KeyPair.fromAccountId(publicKey);
					accountBalances = StellarSDKUtility.getAccountBalance(userAccount);
					NeoBankEnvironment.setComment(3, className, "accountBalances is "+accountBalances);
				}
				
				request.setAttribute("transactions", (List<Transaction>)RemittanceDao.class.getConstructor().newInstance().getLastTenPendingTxn(user.getUserId()));
				request.setAttribute("completedtransactions", (List<Transaction>)RemittanceDao.class.getConstructor().newInstance().getPatnersCompleteTenTransactions(user.getUserId()));
				request.setAttribute("externalwallets",accountBalances);
				request.setAttribute("publickey",publicKey);				
				response.setContentType("text/html");
				 try {
					 ctx.getRequestDispatcher(NeoBankEnvironment.getPartnersDashboardPage()).forward(request, response);
				 } finally {
					 if (langPref != null)langPref = null;
					 if (user != null)user = null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
		
		break;
		
		case "create_digital_asset_partner_page":
			try {
				String data = null;
				if(request.getParameter("json_string")!=null) data = StringUtils.trim(request.getParameter("json_string"));
//				request.setAttribute("lastaction", "porte");
//				request.setAttribute("lastrule", rules);
				request.setAttribute("data", data);
				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getPartnerCreateAssetWaitingPage()).forward(request, response);
				} finally {
					if(data!=null)data=null;
				}
				
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rulesaction + " is " + e.getMessage());
				Utilities.callOpsException(request, response, ctx, e.getMessage());
			}
			
			break;
		
		}
		
		
		
		
		
	}

}
