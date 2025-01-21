package com.pporte.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


import org.apache.commons.lang3.StringUtils;

import com.pporte.NeoBankEnvironment;
import com.pporte.utilities.Utilities;

public class CustomerFiatWalletDao extends HandleConnections{
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 2L;
	private static String className = CustomerFiatWalletDao.class.getSimpleName();
	
	public boolean fiatWalletP2P(String senderRelationship, String senderWalletId, 
			String payAmount, String payComments, String receiverEmail, String referenceNo, String txnUserCode, String customerCharges, String senderTransactionCode) throws Exception {
			PreparedStatement pstmt=null;
			Connection connection = null;
			ResultSet rs=null;
			String query = null;
			boolean result = false;
			String transactionDatetime = null;
			String currencyId= NeoBankEnvironment.getUSDCurrencyId();
			String senderTxnMode = "D";
			String receiverTxnMode = "C";
			String senderTransactionCode2 = null;
			String receiverTransactionCode = null;
			String userType = "C";
			String systemreference =referenceNo;
			String receiverSystemreference =null;
			String senderRefNo = null;
			String receiverRefNo = null;
			String totalAccruedBalance=null;
			String receiverWalletId = null;
			try {	
				 connection = super.getConnection();
				 connection.setAutoCommit(false);

				String customerChargesValue = customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|"));
				String customerPayType = customerCharges.substring(0, customerCharges.indexOf(","));

				payAmount = payAmount.replaceAll(",", "");
				transactionDatetime = Utilities.getMYSQLCurrentTimeStampForInsert();
				SimpleDateFormat formatter1 = new SimpleDateFormat ("yyMMddHHmmssSSS");
				senderTransactionCode2 = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
				receiverTransactionCode = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
				receiverSystemreference = NeoBankEnvironment.getCodeFiatWalletP2P() + "-"
							+ (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
							+ Utilities.genAlphaNumRandom(9);
				 
				 
				 //********Step 0*********\\\\
				
				 query = " select a.walletid walletid from wallet_details a, "
				 		+ "	customer_details b where a.relationshipno = b.relationshipno and b.custemail = ? and wallettype= ? ";
				 pstmt = connection.prepareStatement(query);
				 pstmt.setString(1, Utilities.tripleEncryptData(receiverEmail));
				 pstmt.setString(2, "F");
				 rs = (ResultSet)pstmt.executeQuery();	
				 if(rs!=null){
				 	while(rs.next()){	 			 			
				 		receiverWalletId = (StringUtils.trim(rs.getString("walletid"))    );
				 		NeoBankEnvironment.setComment(3,className,"receiverWalletId is " +receiverWalletId  );
						} // end of while
					} //end of if rs!=null check
					 pstmt.close();	rs.close();					 
					 NeoBankEnvironment.setComment(3,className,"receiverWalletId is " +receiverWalletId  );
				 
			
				 //***step 1 getsender the wallet ballance
					/*String senderpreviouswalletBalance = null;
					query = "select currbal, relationshipno  from wallet_details where walletid=?  ";
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, senderWalletId);
						rs = (ResultSet)pstmt.executeQuery();	
						 if(rs!=null){
						 	while(rs.next()){	 			 			
					   senderpreviouswalletBalance = (StringUtils.trim(rs.getString("currbal"))    );
					   senderRefNo = (StringUtils.trim(rs.getString("relationshipno"))    );
						PPWalletEnvironment.setComment(3,className,"The Sender wallet Previous ballance for walletid!" +senderWalletId +"for userrelationshipno"+ senderRelationship +"is "+ senderpreviouswalletBalance );
								} // end of while
							} //end of if rs!=null check
					pstmt.close(); rs.close(); */
					
					//***********Check if sender has sufficient balance in the wallet************changes here
					/*if((Double.parseDouble(senderpreviouswalletBalance) >= Double.parseDouble(payAmount)) == false) {
						throw new Exception ("Insufficient balance in your account.  Your balance is" + senderpreviouswalletBalance );
					}
						
						PPWalletEnvironment.setComment(3,className,"Balance is enough to send money. Current Balance is"+ senderpreviouswalletBalance );
					 */
				
						//**********step  2 get receiver the wallet ballance
				 /*
					String receiverpreviouswalletBalance = null;
					 query = "select currbal, relationshipno  from wallet_details where walletid=?  ";
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, receiverWalletId);
						rs = (ResultSet)pstmt.executeQuery();	
						 if(rs!=null){
						 	while(rs.next()){	 			 			
						 		receiverpreviouswalletBalance = (StringUtils.trim(rs.getString("currbal"))  );
						 		receiverRefNo = (StringUtils.trim(rs.getString("relationshipno"))    );
								PPWalletEnvironment.setComment(3,className,"The Receiver wallet Previous ballance for walletid!" +receiverWalletId +"is "+ receiverpreviouswalletBalance );

								} // end of while
							} //end of if rs!=null check
					pstmt.close();
					
					double senderbalanceafterDebit = Double.parseDouble(senderpreviouswalletBalance) - Double.parseDouble(payAmount) - Double.parseDouble(customerChargesValue);
					double receiverrbalanceafterCredit = Double.parseDouble(receiverpreviouswalletBalance) + Double.parseDouble(payAmount);
					PPWalletEnvironment.setComment(3,className,"The Sender wallet ballance after Debit is" + senderbalanceafterDebit );
					PPWalletEnvironment.setComment(3,className,"The Receiver wallet ballance after Credit is" + receiverrbalanceafterCredit );
				*/
				 double senderDebitAmount =  Double.parseDouble(payAmount) + Double.parseDouble(customerChargesValue);
				 double receiverrCreditAmount =  Double.parseDouble(payAmount);
				 //*******Step 3: Update the Sender wallet Ledger
			     query = " update wallet_details set currbal= currbal - ?, lastupdated = ? where  walletid=? ";
				 pstmt = connection.prepareStatement(query);
				 pstmt.setBigDecimal(1,BigDecimal.valueOf(senderDebitAmount));
				 pstmt.setString(2, transactionDatetime); 
				 pstmt.setString(3, senderWalletId); 
				 try {
					 NeoBankEnvironment.setComment(3,className,"update sender wallet amount" + senderDebitAmount );
					 pstmt.executeUpdate();
					 }catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
				}					
				pstmt.close();
						// *******Step 4: Update the Receiver wallet Ledger
				query = " update wallet_details set currbal= currbal + ?, lastupdated = ? where  walletid=? ";
				pstmt = connection.prepareStatement(query);
				pstmt.setBigDecimal(1, BigDecimal.valueOf( receiverrCreditAmount));
				pstmt.setString(2, transactionDatetime); 
				pstmt.setString(3, receiverWalletId);
				try {
					NeoBankEnvironment.setComment(3,className,"update receiver wallet amount" + receiverrCreditAmount );
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}					
				pstmt.close();	
					
					
					
				// **********Step 5: Record the wallet transaction ledger
				
				NeoBankEnvironment.setComment(3,className,"************ payComments is  "+payComments+" senderWalletId is"+senderWalletId);
								// 1		2			3			4			5		6		7				8      		 9				10
				query = "insert into txn_wallet_cust_bc (txncode, walletid, sysreference, txnamount, txncurrencyid, txnmode,txncomment, txndatetime, txnusercode, pymtchannel) "
						+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?,?) ";
								// 1  2  3  4  5  6   7  8, 9,10
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, senderTransactionCode); 			
				pstmt.setString(2, senderWalletId); 				
				pstmt.setString(3, systemreference );							
				pstmt.setBigDecimal(4, new BigDecimal(payAmount));  
				pstmt.setString(5, currencyId);
				pstmt.setString(6, senderTxnMode); // Debit as it is a payment by the sender
				pstmt.setString(7, payComments); 
				pstmt.setString(8, transactionDatetime);
				pstmt.setString(9, txnUserCode);
				pstmt.setString(10, "W");
				try {
					NeoBankEnvironment.setComment(3,className,"Executed Sender wallet_tnx_bc amount"+"senderTransactioncode is"+ senderTransactionCode +"senderWalletId is"+senderWalletId 
							+"systemreference is"+systemreference +"payAmount is"+payAmount +"currencyId is"+ currencyId+ "transactionDatetime"+ transactionDatetime);
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}					
					pstmt.close();	
				if(pstmt!=null)			
					pstmt=null;
				
				if(Double.parseDouble(customerChargesValue)>0) {
					query = "insert into txn_wallet_cust_bc (txncode, walletid, sysreference, txnamount, txncurrencyid, txnmode, txndatetime, txnusercode, pymtchannel) "
							+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?) ";
									// 1  2  3  4  5  6   7  8
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, senderTransactionCode2); 			
					pstmt.setString(2, senderWalletId); 				
					pstmt.setString(3, systemreference+"-AC" );							
					pstmt.setBigDecimal(4, new BigDecimal(Double.parseDouble(customerChargesValue)));  
					pstmt.setString(5, currencyId);
					pstmt.setString(6, customerPayType); // Debit as it is a payment by the sender
					pstmt.setString(7, transactionDatetime);
					pstmt.setString(8, txnUserCode);
					pstmt.setString(9, "W");
					try {
						pstmt.executeUpdate();
						}catch(Exception e) {
							throw new Exception (" failed query "+query+" "+e.getMessage());
						}					
						pstmt.close();	
					if(pstmt!=null)			
						pstmt=null;					
					
					   //Select the balance to be updated. 
					
					query = "select accrued_balance, sequenceid from txn_business_ledger_bc order by sequenceid desc limit 1";
					pstmt = connection.prepareStatement(query);
					rs = (ResultSet)pstmt.executeQuery();
					if(rs!=null){
						while(rs.next()){	 			 			
							 totalAccruedBalance= ( StringUtils.trim(rs.getString("accrued_balance"))  );
							} // end of while
						NeoBankEnvironment.setComment(3,className,"step 1   totalLoyaltyBalance is  "+totalAccruedBalance   );
					} 
					if(pstmt!=null) pstmt.close();	if(rs!=null) rs.close();
					

					//
                 //												   1		2			3			4				5				6			7			8		   9          10            11
					query = "insert into txn_business_ledger_bc (txncode, paytype, custwalletid, merchwalletid, sysreference, txnamount, txncurrencyid, txnmode, txndatetime, accrued_balance, pymtchannel) "
							+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
									// 1  2  3  4  5  6  7  8  9  10 11
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, senderTransactionCode2); 
					pstmt.setString(2, NeoBankEnvironment.getCodeFiatWalletP2P());
					pstmt.setString(3, senderWalletId);
					pstmt.setString(4, "");
					pstmt.setString(5, systemreference+"-AC" );			// Additional charges for customer						
					pstmt.setBigDecimal(6, BigDecimal.valueOf( Double.parseDouble(customerChargesValue)));  
					pstmt.setString(7, currencyId);
					pstmt.setString(8, "C"); // Credit 
					pstmt.setString(9, transactionDatetime);
					
					if (totalAccruedBalance!=null) {
						pstmt.setBigDecimal(10,BigDecimal.valueOf( (Double.parseDouble(totalAccruedBalance)+Double.parseDouble(customerChargesValue) )));
					}else {
						pstmt.setBigDecimal(10,BigDecimal.valueOf( Double.parseDouble(customerChargesValue) ));
					}
					pstmt.setString(11, "W");
					try {
							pstmt.executeUpdate();
						}catch(Exception e) {
							throw new Exception (" failed query "+query +" "+e.getMessage());
						}					
					if( pstmt!=null)				
						pstmt.close();
				}
				
				// *********5.2*****Receiver Wallet 
				NeoBankEnvironment.setComment(3,className,"************* Receiver Wallet payComments is  "+payComments   );

														//   1		 2			 3			 4			 5				6		 7			8 			9				10
				query = " insert into txn_wallet_cust_bc (txncode, walletid, sysreference, txnamount, txncurrencyid, txnmode,txncomment, txndatetime, txnusercode, pymtchannel) "
					  + " values (?, ?, ?, ?, ?, ?,  ?, ?, ?,?) ";
								// 1  2  3  4  5  6   7  8
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, receiverTransactionCode); 			
				pstmt.setString(2, receiverWalletId); 				
				pstmt.setString(3, receiverSystemreference );		  					
				pstmt.setBigDecimal(4, new BigDecimal(payAmount));  
				pstmt.setString(5, currencyId);
				pstmt.setString(6, receiverTxnMode); // Debit as it is a payment by the sender
				pstmt.setString(7, payComments);
				pstmt.setString(8, transactionDatetime);
				pstmt.setString(9, txnUserCode);
				pstmt.setString(10, "W");
				try {
					NeoBankEnvironment.setComment(3,className,"Executed receiver wallet_tnx_bc amount "+ payAmount   );
					pstmt.executeUpdate();
				}catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
				}					
				pstmt.close();	if(pstmt!=null)	pstmt=null;
				
	        
					 
				// call the Blockchain method here and pass the values within the method. Here we are inserting in the walletledger stream of Blockchain having chainame ppwallet
				// inserting block data for sender
				 
				if(	CustomerWalletDao.insertGenericWalletTransactions(senderTransactionCode, senderWalletId,txnUserCode, systemreference, payAmount, currencyId,senderTxnMode, transactionDatetime ,userType) == false) {
					throw new Exception ("Blockchain insert issue");
				}
				if(Double.parseDouble(customerChargesValue)>0) {
					if(	CustomerWalletDao.insertGenericWalletTransactions(senderTransactionCode2, senderWalletId,txnUserCode, systemreference+"-AC", customerChargesValue, currencyId,customerPayType, transactionDatetime ,userType) == false) {
						throw new Exception ("Blockchain insert issue");
					}
				}
					 
				if(	CustomerWalletDao.insertGenericWalletTransactions(receiverTransactionCode, receiverWalletId,txnUserCode, systemreference, payAmount, currencyId, receiverTxnMode,transactionDatetime,userType ) == false) {	
					throw new Exception ("Blockchain insert issue");
				}
				connection.commit();	result = true;
			
		
			}catch(Exception e){
				result = false;
				connection.rollback();
				NeoBankEnvironment.setComment(1,className,"The exception in method updateWalletLedgers  is  "+e.getMessage());
				throw new Exception ("The exception in method updateWalletLedgers  is  "+e.getMessage());
			}finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
				}
					if (rs != null)	rs.close(); if (pstmt != null) pstmt.close();if(transactionDatetime!=null)  transactionDatetime=null; if(receiverTransactionCode!=null)  receiverTransactionCode=null;
					if(userType!=null)  userType=null; if(senderTransactionCode!=null)  senderTransactionCode=null; if(receiverRefNo!=null)  receiverRefNo=null; if(txnUserCode != null) txnUserCode = null;
					if(systemreference!=null)  systemreference=null; if(senderRefNo!=null)  senderRefNo=null; if ( totalAccruedBalance!=null) totalAccruedBalance=null;
			
				}
			
			return result;
	}	
	
	
	
	
	public boolean payPlanWithWallet(String relationshipNo, String senderWalletId, String payAmount, String payComments,
			String referenceNo, String txnUserCode, String planId, String originalPlanId,String senderTransactionCode) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		String transactionDatetime = null;
		String currencyId= NeoBankEnvironment.getUSDCurrencyId();
		String senderTxnMode = "D";
		String senderTransactionCode2 = null;
		//String receiverTransactionCode = null;
		String userType = "C";
		String systemreference =referenceNo;
		//String receiverSystemreference =null;
		String senderRefNo = null;
		String receiverRefNo = null;
		String totalAccruedBalanceBusiness=null;
				
		
		NeoBankEnvironment.setComment(3, className, "txnUserCode is "+txnUserCode);
		try {	

			//String customerChargesValue = customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|"));
			//String customerPayType = customerCharges.substring(0, customerCharges.indexOf(","));

			payAmount = payAmount.replaceAll(",", "");
			transactionDatetime = Utilities.getMYSQLCurrentTimeStampForInsert();
			SimpleDateFormat formatter1 = new SimpleDateFormat ("yyMMddHHmmssSSS");
			senderTransactionCode2 = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
			//receiverTransactionCode = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);

			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 		 
			 double senderDebitAmount =  Double.parseDouble(payAmount) ;
			 //double receiverCreditAmount =  Double.parseDouble(payAmount);
			 
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
			
			 
			 //*******Step 1: Update the Sender wallet Ledger
		     query = " update wallet_details set currbal= currbal - ?, lastupdated = ? where  walletid=? ";
			 pstmt = connection.prepareStatement(query);
			 pstmt.setBigDecimal(1,BigDecimal.valueOf(senderDebitAmount));
			 pstmt.setString(2, transactionDatetime); 
			 pstmt.setString(3, senderWalletId); 
			 try {
				 NeoBankEnvironment.setComment(3,className,"update sender wallet amount" + senderDebitAmount );
				 pstmt.executeUpdate();
				 }catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
			}					
			pstmt.close();


			// **********Step 2: debit the wallet transaction ledger of the customer
			
			NeoBankEnvironment.setComment(3,className,"************ payComments is  "+payComments   );
													// 1		2			3			4			5		6		7				8      		 9				10
			query = "insert into txn_wallet_cust_bc (txncode, walletid, sysreference, txnamount, txncurrencyid, txnmode,txncomment, txndatetime, txnusercode, pymtchannel) "
					+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?,?) ";
							// 1  2  3  4  5  6   7  8, 9
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, senderTransactionCode); 			
			pstmt.setString(2, senderWalletId); 				
			pstmt.setString(3, systemreference );							
			pstmt.setBigDecimal(4, new BigDecimal(payAmount));  
			pstmt.setString(5, currencyId);
			pstmt.setString(6, senderTxnMode); // Debit as it is a payment by the sender
			pstmt.setString(7, payComments); 
			pstmt.setString(8, transactionDatetime);
			pstmt.setString(9, txnUserCode);
			pstmt.setString(10, "W");
			try {
				NeoBankEnvironment.setComment(3,className,"Executed Sender wallet_tnx_bc amount"+"senderTransactioncode is"+ senderTransactionCode +"senderWalletId is"+senderWalletId 
						+"systemreference is"+systemreference +"payAmount is"+payAmount +"currencyId is"+ currencyId+ "transactionDatetime"+ transactionDatetime);
				pstmt.executeUpdate();
				}catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
				}					
				pstmt.close();	
			if(pstmt!=null)			
				pstmt=null;
				   // **********Step 3: Get the accrued balance from business ledger so that it can be updated (increased). 
				
				query = "select accrued_balance, sequenceid from txn_business_ledger_bc order by sequenceid desc limit 1";
				pstmt = connection.prepareStatement(query);
				rs = (ResultSet)pstmt.executeQuery();
				if(rs!=null){
					while(rs.next()){	 			 			
						totalAccruedBalanceBusiness= ( StringUtils.trim(rs.getString("accrued_balance"))  );
						} // end of while
					NeoBankEnvironment.setComment(3,className,"step 1   totalLoyaltyBalance is  "+totalAccruedBalanceBusiness   );
				} 
				if(pstmt!=null) pstmt.close();	if(rs!=null) rs.close();
				
				//		**********Step 4: Now update the business ledgetr with credit of the amount
             
				//		 										 1		2			3			4				5				6			7			8		   9          10            11
				query = "insert into txn_business_ledger_bc (txncode, paytype, custwalletid, merchwalletid, sysreference, txnamount, txncurrencyid, txnmode, txndatetime, accrued_balance, pymtchannel) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
								// 1  2  3  4  5  6  7  8  9  10 11
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, senderTransactionCode2); 
				pstmt.setString(2, NeoBankEnvironment.getCodeCustomerBuyPlanViaWallet()); // CBP = CUstomer Buy Plan
				pstmt.setString(3, senderWalletId);
				pstmt.setString(4, "");
				pstmt.setString(5, systemreference+"-AC" );			// Additional charges for customer						
				pstmt.setBigDecimal(6, BigDecimal.valueOf( Double.parseDouble(payAmount)));  
				pstmt.setString(7, currencyId);
				pstmt.setString(8, "C"); // Credit 
				pstmt.setString(9, transactionDatetime);
				
				if (totalAccruedBalanceBusiness!=null) {
					pstmt.setBigDecimal(10,BigDecimal.valueOf( (Double.parseDouble(totalAccruedBalanceBusiness)+Double.parseDouble(payAmount) )));
				}else {
					pstmt.setBigDecimal(10,BigDecimal.valueOf( Double.parseDouble(payAmount) ));
				}
				pstmt.setString(11, "W");
				try {
						pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query +" "+e.getMessage());
					}					
				if( pstmt!=null)				
					pstmt.close();
			
				NeoBankEnvironment.setComment(3,className,"************* Receiver Wallet payComments is  "+payComments   );
			// *********Step 5 *****Update Blockchain ledgers , as of now only the customer ledger
			
			// call the Blockchain method here and pass the values within the method. Here we are inserting in the walletledger stream of Blockchain having chainame ppwallet
			// inserting block data for sender
			 
			if(	CustomerWalletDao.insertGenericWalletTransactions(senderTransactionCode, senderWalletId,txnUserCode, systemreference, payAmount, currencyId,senderTxnMode, transactionDatetime ,userType) == false) {
				throw new Exception ("Blockchain insert issue");
			}
			// *********Step 6 **** Commit all transactions into the database
			connection.commit();	result = true;
		}catch(Exception e){
			result = false;
			connection.rollback();
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}finally{
		if(connection!=null)
			try {
				super.close();
			} catch (SQLException e) {
				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
				if (rs != null)	rs.close(); if (pstmt != null) pstmt.close();if(transactionDatetime!=null)  transactionDatetime=null; 
				if(userType!=null)  userType=null; if(senderTransactionCode!=null)  senderTransactionCode=null; if(receiverRefNo!=null)  receiverRefNo=null; if(txnUserCode != null) txnUserCode = null;
				if(systemreference!=null)  systemreference=null; if(senderRefNo!=null)  senderRefNo=null; if ( totalAccruedBalanceBusiness!=null) totalAccruedBalanceBusiness=null;
		
			}
		
		return result;	}	

}
