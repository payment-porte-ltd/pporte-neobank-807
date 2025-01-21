package com.pporte.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pporte.NeoBankEnvironment;
import com.pporte.utilities.Utilities;
import com.pporte.model.CardDetails;


public class CustomerTokenizationDao extends HandleConnections {
	public static String className = CustomerTokenizationDao.class.getSimpleName();
	
	public boolean tokenizeExternalCards(String userType, String fullName, String cardNumber, String expiry,
			String relationshipNo, String externalRefNo, String TRANSACTION_AMOUNT_FOR_REGISTRATION,
			String tokenId, String cardAlias) throws Exception {
		
					boolean result = false;
					PreparedStatement pstmt = null;
					Connection connection = null;
					String query = null;
					ResultSet rs = null;
					String transactionDatetime = null; 
					String txnMode = "C";
					String txnCurrencyId = null;
					double balanceafterTopup = 0;
					String transactionCode=null;
					String walletId = null;
					String cardType = "E";
					String txnUserCode = null;
					String totalAccruedBalance=null;
					String internalReferenceNo = null;
					String walletType=null;
					String pymtChannel=null;
					
					try {
		
							connection = super.getConnection();
							connection.setAutoCommit(false);
							transactionDatetime = Utilities.getMYSQLCurrentTimeStampForInsert();
							txnUserCode = Utilities.generateTransactionCode(10);
							txnCurrencyId=NeoBankEnvironment.getUSDCurrencyId();
							SimpleDateFormat formatter1 = new SimpleDateFormat("yyMMddHHmmssSSS");
							transactionCode=formatter1.format(new java.util.Date()) + Utilities.genAlphaNumRandom(9);// our system generated transaction
							internalReferenceNo=NeoBankEnvironment.getTokenRegistrationCodeForCustomer()+"-"+transactionCode;
							walletType="F";
							pymtChannel="T"; //Token
						
						
							query = "select  walletid from wallet_details where relationshipno = ? and wallettype=?";
							pstmt = connection.prepareStatement(query);
							pstmt.setString(1, relationshipNo);
							pstmt.setString(2, walletType);
							rs = (ResultSet) pstmt.executeQuery();
							if (rs != null) {
								while (rs.next()) {
									walletId = (StringUtils.trim(rs.getString("walletid")));
								} // end of while
							} // end of if rs!=null check
							pstmt.close(); if (rs!=null) rs.close();
							
				//                                                      1         2             3          4          5             6         7			8            9
							query = "insert into txn_wallet_cust_bc (txncode, walletid, sysreference, txnamount, txncurrencyid, txnmode, txndatetime, txnusercode, pymtchannel)  "
									+ " values (?, ?, ?, ?, ?, ?, ?, ? ,?)  ";
											//  1  2  3  4  5  6  7  8  9
						
								pstmt = connection.prepareStatement(query);
								pstmt.setString(1, transactionCode); 
								pstmt.setString(2, walletId);
								pstmt.setString(3, internalReferenceNo  );
								pstmt.setBigDecimal(4, new BigDecimal(TRANSACTION_AMOUNT_FOR_REGISTRATION));
								pstmt.setString(5, txnCurrencyId);
								pstmt.setString(6, txnMode);
								pstmt.setString(7, transactionDatetime);
								pstmt.setString(8, txnUserCode);
								pstmt.setString(9, pymtChannel);
								try {
							
									pstmt.executeUpdate();
								} catch (Exception e) {
									throw new Exception(" failed query " + query + " " + e.getMessage());
								}
								pstmt.close();
		
		
						        query = "update wallet_details set currbal= currbal + ? , lastupdated = ? where  walletid=? ";    
								pstmt = connection.prepareStatement(query);
								pstmt.setBigDecimal(1,new BigDecimal(TRANSACTION_AMOUNT_FOR_REGISTRATION)); //
								pstmt.setString(2,transactionDatetime); //
								pstmt.setString(3, walletId);
								try {
								  pstmt.executeUpdate();
								} catch (Exception e) {
									throw new Exception(" failed query " + query + " " + e.getMessage());
								}
								  pstmt.close();
		
																//    1        2         3         4              5                    6             7       8         9          10                 11        
							 query = "insert into txn_cardtoken_bc (txncode, tokenid, txnusercode, sysreference_ext, sysreference_int, txnamount, custrelno, paymode, txndatetime, txncurrencyid ) "
										+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?, ?  ) ";
												// 1  2  3  4  5  6   7	 8  9  10 11 
								pstmt = connection.prepareStatement(query);
								pstmt.setString(1, transactionCode); 			
								pstmt.setString(2, tokenId); 										
								pstmt.setString(3, txnUserCode);							
								pstmt.setString(4, externalRefNo);  // External 
								pstmt.setString(5, internalReferenceNo); // Internal generated
								pstmt.setBigDecimal(6, new BigDecimal(TRANSACTION_AMOUNT_FOR_REGISTRATION));
								pstmt.setString(7, relationshipNo); 
								pstmt.setString(8,  NeoBankEnvironment.getTokenRegistrationCodeForCustomer());
								pstmt.setString(9,  transactionDatetime );
								pstmt.setString(10,  txnCurrencyId );
								try {
									pstmt.executeUpdate();
								}catch(Exception e) {
									throw new Exception (" failed query "+query+" "+e.getMessage());
													}					
								if( pstmt!=null)		pstmt.close();	
						
		
								//		     				 		          		1		  2		  		3		   4		    5      	   6			7			 8			  
							query = "insert into card_tokenization_acquiring_bc (tokenid, relationshipno,  usertype, cardnumber, cardalias, cardname, dateofexpiry,  createdon ) "
										+ "values (?, ?, ?, ?, ?, ?, ?, ?) ";
										//		   1  2  3  4  5  6  7	8  
								pstmt = connection.prepareStatement(query);
								pstmt.setString(1, (tokenId)); 					// tokenid
								pstmt.setString(2, relationshipNo); 						// relationshipno
								pstmt.setString(3, userType);							// usertype
								pstmt.setString(4, Utilities.tripleEncryptData(cardNumber));  // cardnumber								//cardtype
								pstmt.setString(5, cardAlias );					 //  cardalias
								pstmt.setString(6, Utilities.tripleEncryptData(fullName));   // cardname
								pstmt.setString(7, Utilities.tripleEncryptData(expiry));   // dateofexpiry
								pstmt.setString(8, transactionDatetime ); //  createdon
					
								try {
										pstmt.executeUpdate();
									}catch(Exception e) {
										throw new Exception (" failed query "+query+" "+e.getMessage());
									}
								
								pstmt.close();
							
					
								if(insertIntoCardVault(tokenId, cardNumber, fullName, expiry, relationshipNo, userType, cardAlias, transactionDatetime ) == false) {
									throw new Exception("Blockchain not updated");
								}
								
								if(	CustomerWalletDao.insertGenericWalletTransactions(transactionCode, walletId, txnUserCode, internalReferenceNo ,  TRANSACTION_AMOUNT_FOR_REGISTRATION , txnCurrencyId, txnMode, transactionDatetime,userType  ) == false) {
									throw new Exception ("Blockchain insert problem");
								}
								
								connection.commit();
								result = true;
			
				}catch(Exception e) {
					connection.rollback();
					result = false;
					NeoBankEnvironment.setComment(1,className,"The exception in method tokenizeExternalCards  is  "+e.getMessage());	
				}finally {	
					if (connection != null)
					try {
						super.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				if (rs != null)	rs.close(); if (pstmt != null) pstmt.close();if(transactionDatetime!=null)  transactionDatetime=null; if(txnMode!=null)  txnMode=null;
					if(userType!=null)  userType=null;	if(txnCurrencyId!=null)  txnCurrencyId=null;if(internalReferenceNo!=null)  internalReferenceNo=null;
					if(balanceafterTopup!=0)  balanceafterTopup=0; if(cardType != null) cardType = null; if (totalAccruedBalance!=null)totalAccruedBalance=null;
					if (tokenId!=null) tokenId=null; if (pymtChannel!=null) pymtChannel=null; 
				}
			
			return result;
	}
	public List <CardDetails> getTokenizedCards(String relationshipNo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		List <CardDetails> cardsList = null;
		String userType=null;
		
		try{			
			connection = super.getConnection();	
			userType="C";
			              //   1          2        3     4      5
			query = "select a.tokenid tokenid, a.cardalias cardalias,a.dateofexpiry dateofexpiry,a.cardname cardname, a.cardnumber cardnumber,b.customername customername"
					+ " from card_tokenization_acquiring_bc a,customer_details b where a.relationshipno=? and a.relationshipno=b.relationshipno "
					+ " and a.usertype=? order by a.createdon desc ";
		  
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, userType);
			
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){
				 cardsList = new ArrayList<CardDetails>();
				 	while(rs.next()){	 
				 		CardDetails m_CardDetails= new CardDetails();
				 		m_CardDetails.setTokenId( StringUtils.trim(rs.getString("tokenid"))    );
				 		m_CardDetails.setCardAlias( StringUtils.trim(rs.getString("cardalias"))  );
				 		m_CardDetails.setCustomerName(StringUtils.trim(rs.getString("customername")));
				 		m_CardDetails.setCardName(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("cardname")) )  );
				 		m_CardDetails.setDateOfExpiry(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("dateofexpiry")) )  );
				 		m_CardDetails.setMaskedCardNumber(Utilities.maskCardNumber( Utilities.tripleDecryptData( StringUtils.trim(rs.getString("cardnumber")) )   ) );
				 		cardsList.add(m_CardDetails);
				 		} 	
				 	if(cardsList !=null)
				 	NeoBankEnvironment.setComment(2,className," arrCards size is "+cardsList.size());
				 	} 
			 if(cardsList!=null)
				 if(cardsList.size()==0)
					 cardsList=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getTokenizedCards  is  "+e.getMessage());
			throw new Exception ("The exception in method getTokenizedCards  is  "+e.getMessage());			
		}finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close(); if (userType!=null) userType=null;
			}
		return cardsList;
	}
	public CardDetails getCardDetailsbyTokenIdDatabase(String tokenId) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		CardDetails m_cardDetails = null;
		
		try{			
			connection = super.getConnection();	
			
			                 //   1          2          3           4      
			query = "select cardalias, cardnumber, dateofexpiry from card_tokenization_acquiring_bc  where tokenid=?";
		  
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, tokenId);
			
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){
				 m_cardDetails = new CardDetails();
				 	while(rs.next()){	 
				 		m_cardDetails.setCardAlias( StringUtils.trim(rs.getString("cardalias"))  );
				 		m_cardDetails.setCardNumber(( Utilities.tripleDecryptData( StringUtils.trim(rs.getString("cardnumber")) )   ) );
				 		m_cardDetails.setMaskedCardNumber(Utilities.maskCardNumber( Utilities.tripleDecryptData( StringUtils.trim(rs.getString("cardnumber")) )   ) );
				 		m_cardDetails.setDateOfExpiry(Utilities.tripleDecryptData( StringUtils.trim(rs.getString("dateofexpiry")) )   );
				 		
				 		} 	
				 	} 
	
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getCardDetailsbyTokenId  is  "+e.getMessage());
			throw new Exception ("The exception in method getCardDetailsbyTokenId  is  "+e.getMessage());			
		}finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close(); 
			}
		return m_cardDetails;
	}
	
	public boolean toupWalletByToken(String tokenId,   String topUpAmount, String relationshipNo, String externalRefNo, String txnCurrencyId, 
			String transactionCode,String internalSystemReference, String walletId) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		ResultSet rs = null;
		String transactionDatetime = null;
		String txnMode = "C";
		String userType = "C";
		String walletType="F";
		String pymtChannel="T"; //Token
		String txnUserCode=null;
		boolean result =false;
		try {

		    topUpAmount = topUpAmount.replaceAll(",", "");
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 transactionDatetime = Utilities.getMYSQLCurrentTimeStampForInsert();
			
			 txnUserCode = Utilities.generateTransactionCode(10);
			 internalSystemReference=NeoBankEnvironment.getCodeTokenWalletTopup()+"-"+transactionCode;
				
				
	           query = "update wallet_details set currbal= currbal + ? , lastupdated = ? where  walletid=? ";    
								pstmt = connection.prepareStatement(query);
								pstmt.setBigDecimal(1,new BigDecimal(topUpAmount)); //
								pstmt.setString(2,transactionDatetime); //
								pstmt.setString(3, walletId);
								try {
								  pstmt.executeUpdate();
								} catch (Exception e) {
									throw new Exception(" failed query " + query + " " + e.getMessage());
								}
								  pstmt.close();
     
			//                                                 1         2             3          4          5             6         7			8            9
					query = "insert into txn_wallet_cust_bc (txncode, walletid, sysreference, txnamount, txncurrencyid, txnmode, txndatetime, txnusercode, pymtchannel)  "
							+ " values (?, ?, ?, ?, ?, ?, ?, ? ,?)  ";
									//  1  2  3  4  5  6  7  8  9
				
						pstmt = connection.prepareStatement(query);
						pstmt.setString(1, transactionCode); 
						pstmt.setString(2, walletId);
						pstmt.setString(3, internalSystemReference  );
						pstmt.setBigDecimal(4, new BigDecimal(topUpAmount));
						pstmt.setString(5, txnCurrencyId);
						pstmt.setString(6, txnMode);
						pstmt.setString(7, transactionDatetime);
						pstmt.setString(8, txnUserCode);
						pstmt.setString(9, pymtChannel);
						try {
					
							pstmt.executeUpdate();
						} catch (Exception e) {
							throw new Exception(" failed query " + query + " " + e.getMessage());
						}
						pstmt.close();
			          
					// insert into  txn_cardtoken_bc
														//    1        2           3           4                5                  6         7         8         9          10              11        
			     	 query = "insert into txn_cardtoken_bc (txncode, tokenid, txnusercode, sysreference_ext, sysreference_int, txnamount, custrelno, paymode, txndatetime, txncurrencyid ) "
							+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?, ? ) ";
									// 1  2  3  4  5  6   7	 8  9  10  11
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, transactionCode); 			
					pstmt.setString(2, tokenId); 										
					pstmt.setString(3, txnUserCode);							
					pstmt.setString(4, externalRefNo);  // External 
					pstmt.setString(5, internalSystemReference); // Internal generated
					pstmt.setBigDecimal(6, new BigDecimal(topUpAmount));
					pstmt.setString(7, relationshipNo); 
					pstmt.setString(8,  NeoBankEnvironment.getCodeTokenWalletTopup());
					pstmt.setString(9,  transactionDatetime );
					pstmt.setString(10,  txnCurrencyId );
					
					try {
						pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
										}					
					if( pstmt!=null)		pstmt.close();	

			
					if(	CustomerWalletDao.insertGenericWalletTransactions(transactionCode, walletId, txnUserCode, internalSystemReference , topUpAmount, txnCurrencyId, txnMode, transactionDatetime ,userType ) == false) {
						throw new Exception ("Problem with Blockchain insert");
					}
					
					connection.commit();
					result=true;

		}catch(Exception e) {
			connection.rollback();
			result=false;
			throw new Exception("The exception in method toupWalletByToken  is  " + e.getMessage());
			
		}finally {	
			if (connection != null)
			try {
				super.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		if (rs != null)	rs.close(); if (pstmt != null) pstmt.close();if(transactionDatetime!=null)  transactionDatetime=null; if(txnMode!=null)  txnMode=null;
			if(userType!=null)  userType=null;	if(txnCurrencyId!=null)  txnCurrencyId=null;if(internalSystemReference!=null)  internalSystemReference=null;
			if(walletId!=null)  walletId=null; if(txnUserCode != null) txnUserCode = null;   if(pymtChannel!=null) pymtChannel=null;
			if(walletType!=null) walletType=null;if(transactionCode!=null)  transactionCode=null;	
		}
	
		return result;
	}

	public static synchronized boolean insertIntoCardVault(String tokenId, String cardNumber, String cardName, String dateOfExpiry, String relationshipNo, //TODO
			String userType, String cardAlias, String dateTime ) throws Exception{
		boolean result = false;	
		CloseableHttpClient client =null;
		HttpPost httpPost=null;
		String streamName = null;
		String chainName =null;
		String blockchainResult = null; String blockerror = null;   JsonObject cardTokenDetails =null; CloseableHttpResponse response=null;
		JsonObject tokenDetails=null; JsonObject responseJson=null; JsonObject responseJson2=null;
		try {
			
					if (NeoBankEnvironment.getBlockChainInsert().equals("false")) {
						 NeoBankEnvironment.setComment(3,className,"==================Card Vault Blockchain insert is off.================");
						 return true;
					}
				
				     NeoBankEnvironment.setComment(3,className,"in insertIntoCardVault Blockchain   ");
		          	 client = HttpClients.createDefault();
		 		     httpPost = new HttpPost(NeoBankEnvironment.getMultiChainCardVaultChainRPCURLPORT());
		 		     
				    chainName = NeoBankEnvironment.getCardVaultBlockChainName();
					streamName = NeoBankEnvironment.getBlockChainCardVaultStreamName();
		 		    
		 		     cardTokenDetails = new JsonObject();  tokenDetails= new JsonObject();
		 		    
		 		    tokenDetails.addProperty("tokenid", tokenId);
		 		    tokenDetails.addProperty("relationshipno", relationshipNo);
		 		    tokenDetails.addProperty("usertype", userType);
		 		    tokenDetails.addProperty("cardnumber", Utilities.tripleEncryptData(cardNumber) );
		 		    tokenDetails.addProperty("cardalias", cardAlias);
		 		    tokenDetails.addProperty("cardname", Utilities.tripleEncryptData(cardName));
		 		    tokenDetails.addProperty("dateofexpiry", Utilities.tripleEncryptData(dateOfExpiry));
		 		    tokenDetails.addProperty("createdon", dateTime);


		 		    cardTokenDetails.add("tokendetails", tokenDetails);
		 		    
		 		    NeoBankEnvironment.setComment(3,className," card Token Details "+cardTokenDetails.toString());
		 		    String cardDetailsHex = Utilities.asciiToHex(cardTokenDetails.toString());
		 		    // The key for the card tokenized when inserting to the blockchain is the relationshipNo so that when retrieving the data you can use relationshipNo to get data details for specific customer
		 		    String json ="{\"method\":\"publish\",\"params\":[\""+streamName+"\",\""+ tokenId+"\",\""+cardDetailsHex+"\"],\"id\":1,\"chain_name\":\""+chainName+"\"}";
		 		    StringEntity entity = new StringEntity(json);
		 		    httpPost.setEntity(entity);
		 		    httpPost.setHeader("Accept", "application/json");
		 		    httpPost.setHeader("authorization", Utilities.getBasicAuthHeader(NeoBankEnvironment.getCardVaultChainMultiChainUser(),NeoBankEnvironment.getCardVaultChainRPCAuthKey()));
		 		    httpPost.setHeader("Content-type", "application/json");

		 		    response = client.execute(httpPost);
		 		    NeoBankEnvironment.setComment(3,className,"Response code "+response.getCode());
		 		    HttpEntity entityResponse = response.getEntity();
		 		    String data = EntityUtils.toString(entityResponse);
		 		    responseJson =  new Gson().fromJson(data, JsonObject.class);
		 	
					blockchainResult= responseJson.get("result").toString();
		 		    
		 		    if (response.getCode()==200) {
		 		    	NeoBankEnvironment.setComment(3,className,"Blockchain success  "  + blockchainResult);
				        result = true;	
		 		    } else {
		 		    	 
							  blockerror = responseJson.get("error").toString();
							  responseJson2 = new Gson().fromJson(blockerror, JsonObject.class);
							  String code = responseJson2.get("code").toString();
							  String message = responseJson2.get("message").toString();
							
							  NeoBankEnvironment.setComment(1,className,"The exception in method insertIntoCardVault  is  Problem  in the Blockchain  code: "+  code + "  message : "+ message  );
						        result = false;	
		 		    }   
 			
		}catch(Exception e){
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method insertIntoCardVault  is  "+e.getMessage());
			throw new Exception ("The exception in method insertIntoCardVault  is  "+e.getMessage());
		}finally{
			try {
				
				if(client!=null)client.close(); if(httpPost!=null) httpPost.clear();if (streamName!=null) streamName=null;
				if (chainName!=null)chainName=null; if (blockchainResult!=null) blockchainResult=null;
				if (blockerror!=null) blockerror=null; if (cardTokenDetails!=null) cardTokenDetails=null; if (response!=null) response.close();
				if (responseJson!=null) responseJson=null;if (responseJson2!=null) responseJson2=null;
			}catch (Exception ee) {
				NeoBankEnvironment.setComment(1,className,"The exception in method insertIntoCardVault, finally block is  "+ee.getMessage());
			}
		}
		return result;
	}
	
	public boolean buyPricingPlanByToken(String tokenId, String payAmount, String relationshipNo,
			String externalRefNo, String planId, String originalPlanId) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		ResultSet rs = null;
		String transactionDatetime = null;

		String txnCurrencyId = NeoBankEnvironment.getUSDCurrencyId();
		String internalSystemReference = null;
		String transactionCode = null;
		String walletId=null;
		String txnUserCode=null;
		boolean result =false;  	
		
		try {

			 payAmount = payAmount.replaceAll(",", "");
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 transactionDatetime = Utilities.getMYSQLCurrentTimeStampForInsert();
			 SimpleDateFormat formatter1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			 transactionCode = formatter1.format(new java.util.Date()) + Utilities.genAlphaNumRandom(9);
			 txnUserCode = Utilities.generateTransactionCode(10);
			 internalSystemReference=NeoBankEnvironment.getCodeCustomerBuyPlanViaToken()+"-"+transactionCode;


			 query = "update customer_price_plan_allocation set  plan_id = ?, plan_start_date=?,reason=? where plan_id = ? and customerid = ? ";
				
				pstmt = connection.prepareStatement(query);
				pstmt.setInt(1, Integer.parseInt(planId)); 
				pstmt.setString(2, transactionDatetime); 
				pstmt.setString(3, NeoBankEnvironment.getPlanReasonAfterPurchasebyCustomer()+planId); 
				pstmt.setInt(4, Integer.parseInt(originalPlanId)); 	
				pstmt.setString(5, relationshipNo); 	
				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}
				pstmt.close();
		          
					// Step 2 - insert into  txn_cardtoken_bc
														//    1        2           3           4                5                  6         7         8         9          10              11        
			     	 query = "insert into txn_cardtoken_bc (txncode, tokenid, txnusercode, sysreference_ext, sysreference_int, txnamount, custrelno, paymode, txndatetime, txncurrencyid ) "
							+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?, ? ) ";
									// 1  2  3  4  5  6   7	 8  9  10  11
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, transactionCode); 			
					pstmt.setString(2, tokenId); 										
					pstmt.setString(3, txnUserCode);							
					pstmt.setString(4, externalRefNo);  // External 
					pstmt.setString(5, internalSystemReference); // Internal generated
					pstmt.setBigDecimal(6, new BigDecimal(payAmount));
					pstmt.setString(7, relationshipNo); 
					pstmt.setString(8,  NeoBankEnvironment.getCodeCustomerBuyPlanViaToken());
					pstmt.setString(9,  transactionDatetime );
					pstmt.setString(10,  txnCurrencyId );
					
					try {
						pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
										}					
					if( pstmt!=null)		pstmt.close();	

					connection.commit();
					result=true;

		}catch(Exception e) {
			connection.rollback();
			result=false;
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			
		}finally {	
			if (connection != null)
			try {
				super.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		if (rs != null)	rs.close(); if (pstmt != null) pstmt.close();if(transactionDatetime!=null)  transactionDatetime=null;
			if(txnCurrencyId!=null)  txnCurrencyId=null;if(internalSystemReference!=null)  internalSystemReference=null;
			if(walletId!=null)  walletId=null; if(txnUserCode != null) txnUserCode = null;  
			if(transactionCode!=null)  transactionCode=null;	
		}
	
		return result;
	}
	
	
	public static synchronized JsonArray getCardDetailsForCustomerFromBlockchain(String tokenId) throws Exception{

		CloseableHttpClient client =null;
		CloseableHttpResponse jresponse=null;
		 HttpPost jrequest = null;
		 JsonArray jsonarray =null;
		 HttpPost httpPost=null;
		 String streamName = null;
		 String chainName = null;
		 JsonObject responseJson=null; 
		try {
			 NeoBankEnvironment.setComment(3,className,"in getCardDetailsForCustomer Blockchain   ");
          	 client = HttpClients.createDefault();
 		     httpPost = new HttpPost(NeoBankEnvironment.getMultiChainCardVaultChainRPCURLPORT());
			
				 	chainName = NeoBankEnvironment.getCardVaultBlockChainName();
					streamName=NeoBankEnvironment.getBlockChainCardVaultStreamName();
			
		 		    String json ="{\"method\":\"liststreamkeyitems\",\"params\":[\""+streamName+"\",\""+tokenId+"\"],\"id\":1,\"chain_name\":\""+chainName+"\"}";
		 		    StringEntity entity = new StringEntity(json);
		 		    httpPost.setEntity(entity);
		 		    httpPost.setHeader("Accept", "application/json");
		 		    httpPost.setHeader("authorization", Utilities.getBasicAuthHeader(NeoBankEnvironment.getCardVaultChainMultiChainUser(),NeoBankEnvironment.getCardVaultChainRPCAuthKey()));
		 		    httpPost.setHeader("Content-type", "application/json");

		 		    
				    jresponse = client.execute(httpPost);
				    NeoBankEnvironment.setComment(3,className,"Response code "+jresponse.getCode());
				     if (jresponse.getCode()!=200)
				    	 throw new Exception( "Failed to fetch transaction");
				    HttpEntity entityResponse = jresponse.getEntity();
				    String data = EntityUtils.toString(entityResponse);
				    NeoBankEnvironment.setComment(3,className,"data is  "  + data);
				    responseJson =  new Gson().fromJson(data, JsonObject.class);
				    NeoBankEnvironment.setComment(3,className,"data is  "  + data);
			 		jsonarray = responseJson.getAsJsonArray("result");
			 		NeoBankEnvironment.setComment(3,className,"Blockchain success  "  + jsonarray);

		}catch (Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getTransactionsInBlockchain  is  "+e.getMessage());
			throw new Exception( "Transactions does not exist");
			
		}finally {
			try {
				client.close();	jresponse.close(); if (jrequest!=null)jrequest=null; if (streamName!=null) streamName=null;
				if (responseJson!=null) responseJson=null; if (httpPost!=null) httpPost.clear(); if (chainName!=null) chainName=null;
			} catch (Exception e1) {
				NeoBankEnvironment.setComment(1,className,"The exception in closing response  is  "+e1.getMessage());
			}
		}
		return jsonarray;
	}
	
	
	public CardDetails getCardDetailsbyTokenIdBlockchain(String tokenId){
				JsonArray jsonarray=null; CardDetails m_CardDetails=null;
		String datagson=null;JsonObject linejsonobj=null;
		try {
			jsonarray= getCardDetailsForCustomerFromBlockchain(tokenId);
			NeoBankEnvironment.setComment(3, className," jsonArray "+jsonarray.toString());
			
			if (jsonarray!=null) {
				for(int i=0;i<jsonarray.size();i++){ 
				 	 datagson = Utilities.hexToASCII(jsonarray.get(i).getAsJsonObject().get("data").getAsString());
					 linejsonobj = JsonParser.parseString(datagson).getAsJsonObject();
					 m_CardDetails = new CardDetails();
					 m_CardDetails.setTokenId(linejsonobj.get("tokendetails").getAsJsonObject().get("tokenid").getAsString());
					 m_CardDetails.setCardNumber(Utilities.tripleDecryptData(linejsonobj.get("tokendetails").getAsJsonObject().get("cardnumber").getAsString()));// To be decrypted when sending to the payment gateway
					 m_CardDetails.setMaskedCardNumber(Utilities.maskCardNumber(Utilities.tripleDecryptData(linejsonobj.get("tokendetails").getAsJsonObject().get("cardnumber").getAsString())));//Masked Card Number
					 m_CardDetails.setCardAlias(linejsonobj.get("tokendetails").getAsJsonObject().get("cardalias").getAsString());
					 m_CardDetails.setCardName(Utilities.tripleDecryptData(linejsonobj.get("tokendetails").getAsJsonObject().get("cardname").getAsString()));// To be decrypted when sending to the payment gateway
					 m_CardDetails.setDateOfExpiry(Utilities.tripleDecryptData(linejsonobj.get("tokendetails").getAsJsonObject().get("dateofexpiry").getAsString()));// To be decrypted when sending to the payment gateway
					 m_CardDetails.setCreatedOn(linejsonobj.get("tokendetails").getAsJsonObject().get("createdon").getAsString());
					}
			}
		}catch (Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}finally {
			if (jsonarray!=null) jsonarray=null; 
			if (datagson!=null) datagson=null; if (linejsonobj!=null)linejsonobj=null;
		}
		return m_CardDetails;
	}
}
