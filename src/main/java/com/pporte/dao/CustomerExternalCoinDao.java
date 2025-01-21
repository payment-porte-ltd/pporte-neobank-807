package com.pporte.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.pporte.NeoBankEnvironment;
import com.pporte.model.AssetTransaction;
import com.pporte.utilities.Utilities;

public class CustomerExternalCoinDao extends HandleConnections {
	public static String className = CustomerExternalCoinDao.class.getName();
	
	public String getExternalWalletBalance(String relationshipNo, String assetCode) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String walletbalance=null;
		String walletId=null;
		String walletDetails =null;
			try {
				
			connection = super.getConnection();	
				query=" select currbal, walletid from wallet_details where relationshipno = ? and currencyid = ? ";
				pstmt=connection.prepareStatement(query);
				pstmt.setString(1, relationshipNo);
				pstmt.setString(2, assetCode);//B
				rs= (ResultSet)pstmt.executeQuery();
				if (rs!=null) {
					while (rs.next()) {
						walletbalance= StringUtils.trim(rs.getString("currbal"));
						walletId= StringUtils.trim(rs.getString("walletid"));
					}
				}
				if(walletId!=null && walletbalance!=null ) {
					walletDetails=walletId.concat(",").concat(walletbalance);
				}
			}catch(Exception e){
				NeoBankEnvironment.setComment(1,className,"The exception in method getExternalWalletBalance  is  "+e.getMessage());
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
		return walletDetails;
	}
	public Boolean sellExternalCoin(String relationshipNo, String fiatWalletId, String amount,
			String payComments, String referenceNo, String txnUserCode, String txnPayMode, String assetCode,
			String extSystemRef, String assetWalletId, double newWalletBalanceFromStellar, String customerCharges, String assetAmount) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		String transactionDatetime = null;
		String currencyId= assetCode;
		String fiatWalletTxnMode = "C";
		String assetWalletTxnMode = "D";
		String fiatTransactionCode = null;
		String fiatWalletTransactionCode2 = null;
		String assetWalletTransactionCode = null;
		String userType = "C";
		String systemreference =referenceNo;
		String totalAccruedBalance=null;
		try {
			String customerChargesValue = customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|"));
			String customerPayType = customerCharges.substring(0, customerCharges.indexOf(","));

			amount = amount.replaceAll(",", "");
			transactionDatetime = Utilities.getMYSQLCurrentTimeStampForInsert();
			SimpleDateFormat formatter1 = new SimpleDateFormat ("yyMMddHHmmssSSS");
			fiatTransactionCode = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
			fiatWalletTransactionCode2 = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
			assetWalletTransactionCode = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
		    connection = super.getConnection();
		    connection.setAutoCommit(false);
		    
		    //********Step 1*********\\\\	
			 double fiatWalletCreditAmount =  Double.parseDouble(amount) - Double.parseDouble(customerChargesValue);
			
			 //*******Step 2: Update the Fiat wallet 
		     query = " update wallet_details set currbal= currbal + ?, lastupdated = ? where  walletid=? ";
			 pstmt = connection.prepareStatement(query);
			 pstmt.setBigDecimal(1,BigDecimal.valueOf(fiatWalletCreditAmount));
			 pstmt.setString(2, transactionDatetime); 
			 pstmt.setString(3, fiatWalletId); 
			 try {
				 NeoBankEnvironment.setComment(3,className,"update Fiat wallet amount" + fiatWalletCreditAmount );
				 pstmt.executeUpdate();
				 }catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
			 }					
			 if( pstmt!=null)	pstmt.close();
			 double receiverrCreditAmount =  Double.parseDouble(assetAmount); //Remove this after integration just update balance from stellar api
		
			 // *******Step 3: Update the Crypto asset coin wallet 
			query = " update wallet_details set currbal= currbal - ?, lastupdated = ? where  walletid=? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setBigDecimal(1, BigDecimal.valueOf( receiverrCreditAmount));
			pstmt.setString(2, transactionDatetime); 
			pstmt.setString(3, assetWalletId);
			try {
				NeoBankEnvironment.setComment(3,className,"update receiver wallet amount" + receiverrCreditAmount );
				pstmt.executeUpdate();
				}catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
				}					
			if( pstmt!=null)	pstmt.close();
			
			// **********Step 4: Record the wallet transaction ledger
			
													// 1		2			3			4			  5			  6		     7			  8            9
			query = "insert into txn_wallet_cust_bc (txncode, walletid, sysreference, txnamount, txncurrencyid, txnmode, txndatetime, txnusercode, pymtchannel) "
			+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?) ";
					// 1  2  3  4  5  6   7  8  9
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, fiatTransactionCode); 			
			pstmt.setString(2, fiatWalletId); 				
			pstmt.setString(3, systemreference );							
			pstmt.setBigDecimal(4, new BigDecimal(amount));  
			pstmt.setString(5, currencyId);
			pstmt.setString(6, fiatWalletTxnMode); // Debit as it is a payment by the sender
			pstmt.setString(7, transactionDatetime);
			pstmt.setString(8, txnUserCode);
			pstmt.setString(9, "C"); //Crypto
			try {
				NeoBankEnvironment.setComment(3,className,"Executed Sender wallet_tnx_bc amount"+"fiatTransactionCode is"+ fiatTransactionCode +"fiatWalletId is"+fiatWalletId 
				+"systemreference is"+systemreference +"payAmount is"+amount +"currencyId is"+ currencyId+ "transactionDatetime"+ transactionDatetime);
			pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}					
			if( pstmt!=null)	pstmt.close();
			
			//Step 5 Business commision
			if(Double.parseDouble(customerChargesValue)>0) {
				query = "insert into txn_wallet_cust_bc (txncode, walletid, sysreference, txnamount, txncurrencyid, txnmode, txndatetime, txnusercode, pymtchannel) "
						+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?) ";
								// 1  2  3  4  5  6   7  8
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, fiatWalletTransactionCode2); 			
				pstmt.setString(2, fiatWalletId); 				
				pstmt.setString(3, systemreference+"-AC" );							
				pstmt.setBigDecimal(4, new BigDecimal(Double.parseDouble(customerChargesValue)));  
				pstmt.setString(5, currencyId);
				pstmt.setString(6, customerPayType); 
				pstmt.setString(7, transactionDatetime);
				pstmt.setString(8, txnUserCode);
				pstmt.setString(9, "C"); //Crypto
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
				
             //												   1		2			3			4				5				6			7			8		   9          10            11
				query = "insert into txn_business_ledger_bc (txncode, paytype, custwalletid, merchwalletid, sysreference, txnamount, txncurrencyid, txnmode, txndatetime, accrued_balance, pymtchannel) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
								// 1  2  3  4  5  6  7  8  9  10 11
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, fiatWalletTransactionCode2); 
				pstmt.setString(2, txnPayMode);
				pstmt.setString(3, fiatWalletId);
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
				if( pstmt!=null)pstmt.close();
			}
			
			// **********Step 6: Record the wallet transaction ledger

														// 1		2			3			4			5				  6	    	7			8              9             10             11          12
			query = "insert into txn_crypto_wallet_cust (txncode, walletid, sysreference_int, txnamount, txncurrencyid, txnmode, txndatetime, txnusercode, pymtchannel, sysreference_ext, assetcode, asset_type) "
			+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?,  ? ) ";
			// 1  2  3  4  5  6  7  8  9  10  11  12
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, assetWalletTransactionCode); 			
			pstmt.setString(2, assetWalletId); 				
			pstmt.setString(3, systemreference );							
			pstmt.setBigDecimal(4, new BigDecimal(assetAmount));  
			pstmt.setString(5, currencyId);
			pstmt.setString(6, "D"); 
			pstmt.setString(7, transactionDatetime);
			pstmt.setString(8, txnUserCode);
			pstmt.setString(9, "C"); //Crypto
			pstmt.setString(10, extSystemRef);
			pstmt.setString(11, assetCode);
			pstmt.setString(12, "E"); //External
			try {
			NeoBankEnvironment.setComment(3,className,"Executed Sender txn_crypto_wallet_cust amount"+"assetWalletTransactionCode is "+ assetWalletTransactionCode 
			+"systemreference is"+systemreference +"payAmount is"+amount +"currencyId is"+ currencyId+ "transactionDatetime"+ transactionDatetime);
			pstmt.executeUpdate();
			}catch(Exception e) {
			throw new Exception (" failed query "+query+" "+e.getMessage());
			}					
			if (pstmt != null) pstmt.close();
			
		  //******** Step 7 START LOYAULTY MODULE
			String pointsConversion = null;
			String previousPoinsBalance = null;
			boolean firstime = false;
			//String pointsaccrued =null;
			 
		 query = "select pointsconversion from loyalty_rules where paymode=? and status = ? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, txnPayMode );
			pstmt.setString(2, "A");
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 	while(rs.next()){	 			 			
				 		pointsConversion = (StringUtils.trim(rs.getString("pointsconversion"))  );
				 		} // end of while
				 	} //end of if rs!=null check
			 pstmt.close();
		
			if(pointsConversion!=null) {
				
			//*** Step 7.1 Get loyalty balance for wallet
			 query = "select pointbalance balance from loyalty_points_bc where relationshipno=? and sequenceid = (select max(sequenceid) from  loyalty_points_bc where relationshipno = ? ) ";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, relationshipNo);
				pstmt.setString(2, relationshipNo);
				rs = (ResultSet)pstmt.executeQuery();		
				 if(rs!=null){
					 	while(rs.next()){	 			 			
					 		previousPoinsBalance = (StringUtils.trim(rs.getString("balance"))  );
						NeoBankEnvironment.setComment(3,className,"The pointbalance   is " + previousPoinsBalance );
					 		
					 		} // end of while
					 	} //end of if rs!=null check
				 pstmt.close();
			 if(previousPoinsBalance==null) {
				 firstime  = true;
				 NeoBankEnvironment.setComment(3,className,"No previous record present for assetWalletId: "+assetWalletId+" for userType    "+userType);
			 }else {
				 NeoBankEnvironment.setComment(3,className,"Previus Balance for assetWalletId : "+assetWalletId+" is "+previousPoinsBalance+" for userType    "+userType);
			 }
			 
			// Step 7.2: Insert loyalty points for the sender user  
			 								//		1		2			3			   4			5			6				7          8
		  query = "insert into loyalty_points_bc (walletid, usertype, relationshipno, paymode, txnreference, pointaccrued, pointbalance, txndatetime) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?) ";
						//		   1  2  3  4  5  6  7  8
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, assetWalletId); 					
				pstmt.setString(2, userType); 		
				pstmt.setString(3, relationshipNo); // 
				pstmt.setString(4, txnPayMode );	
				pstmt.setString(5, systemreference  ); // systemreference reference generated by the system
				pstmt.setBigDecimal(6, new BigDecimal(assetAmount) );  // pointaccrued
				if(firstime) {
					pstmt.setBigDecimal(7, new BigDecimal(assetAmount)    );  // pointbalance
				}else {
					pstmt.setBigDecimal(7, BigDecimal.valueOf(Double.parseDouble(assetAmount)  + Double.parseDouble(previousPoinsBalance ))    );  // pointbalance
				}
				pstmt.setString(8,transactionDatetime );	
					try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
				}	
			}
			//******END LOYALTY MODULE
			
			connection.commit();	
			result = true;			
		}catch(Exception e){
			result = false;
			connection.rollback();
			NeoBankEnvironment.setComment(1,className,"The exception in method sellExternalCoinViaWallet  is  "+e.getMessage());
			throw new Exception ("The exception in method sellExternalCoinViaWallet  is  "+e.getMessage());
		}finally{
		if(connection!=null)
			try {
				super.close();
			} catch (SQLException e) {
				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
	
				if (rs != null)	rs.close(); if (pstmt != null) pstmt.close();if(transactionDatetime!=null)  transactionDatetime=null; if(assetWalletTransactionCode!=null)  assetWalletTransactionCode=null;
				if(userType!=null)  userType=null; if(fiatTransactionCode!=null)  fiatTransactionCode=null; if(fiatWalletTransactionCode2!=null)  fiatWalletTransactionCode2=null; if(txnUserCode != null) txnUserCode = null;
				if(systemreference!=null)  systemreference=null; if(currencyId!=null)  currencyId=null; if ( totalAccruedBalance!=null) totalAccruedBalance=null;
				if(assetWalletTxnMode!=null)  assetWalletTxnMode=null; if ( fiatWalletTxnMode!=null) fiatWalletTxnMode=null;
		
			}
		
		return result;
		
	}
	public List<AssetTransaction> getExternalCoinTxnBtnDates(String dateFrom, String dateTo, String relationshipNo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		AssetTransaction transaction = null;
		List<AssetTransaction> transactionList=null;
		try{
			connection = super.getConnection();	
			query = " select a.txnusercode txnusercode, a.sysreference_int sysreference, a.pymtchannel pymtchannel, "
					+ "	a.txnamount txnamount, a.txncurrencyid txncurrencyid, a.txnmode txnmode, a.txndatetime txndatetime, b.walletid walletid, "
					+ "	b.wallettype wallettype, substr(a.sysreference_int,1, 3) paytype from txn_crypto_wallet_cust a, wallet_details b  where a.walletid = b.walletid "
					+ "	and b.relationshipno = ? and a.asset_type =? and a.txndatetime between ? and ? order by txndatetime desc limit 1000";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, "E"); //External
			pstmt.setString(3, dateFrom);
			pstmt.setString(4, dateTo);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 transactionList = new ArrayList<AssetTransaction>();
				 	while(rs.next()){	
				 		transaction = new AssetTransaction();
				 		transaction.setTxnUserCode(StringUtils.trim(rs.getString("txnusercode")));
				 		transaction.setTxnDateTime(StringUtils.trim(rs.getString("txndatetime")));
				 		transaction.setTxnMode(StringUtils.trim(rs.getString("txnmode")));
				 		transaction.setPymtChannel(StringUtils.trim(rs.getString("pymtchannel")));
				 		transaction.setSystemReferenceInt(StringUtils.trim(rs.getString("sysreference")));
				 		transaction.setTxnAmount(StringUtils.trim(rs.getString("txnamount")));
				 		transaction.setTxnType(StringUtils.trim(rs.getString("paytype")));
				 		transactionList.add(transaction);
				 	}
			 }
			 if(transactionList!=null)
				 if(transactionList.size()==0)
					 transactionList=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getExternalCoinTxnBtnDates  is  "+e.getMessage());
			throw new Exception ("The exception in method getExternalCoinTxnBtnDates  is  "+e.getMessage());			
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
		return transactionList;
	}
	
	public List<AssetTransaction> getExternalCoinLastFiveTxn(String relationshipNo)  throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		AssetTransaction transaction = null;
		List<AssetTransaction> transactionList=null;
		try{
			connection = super.getConnection();	
			query = " select a.txnusercode txnusercode, a.sysreference_int sysreference, a.pymtchannel pymtchannel, a.assetcode assetcode, "
					+ "	a.txnamount txnamount, a.txncurrencyid txncurrencyid, a.txnmode txnmode, a.txndatetime txndatetime, b.walletid walletid, "
					+ "	b.wallettype wallettype,substr(a.sysreference_int,1, 3) paytype from txn_crypto_wallet_cust a, wallet_details b  where a.walletid = b.walletid "
					+ "	and b.relationshipno = ? and a.asset_type =?  order by txndatetime desc limit 5 ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, "E");//E
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 transactionList = new ArrayList<AssetTransaction>();
				 	while(rs.next()){	
				 		transaction = new AssetTransaction();
				 		transaction.setTxnDateTime(Utilities.displayDateFormat( StringUtils.trim(rs.getString("txndatetime")), "dd MMM yy HH:mm a"));
				 		transaction.setTxnUserCode(StringUtils.trim(rs.getString("txnusercode")));
				 		transaction.setTxnMode(StringUtils.trim(rs.getString("txnmode")));
				 		transaction.setPymtChannel(StringUtils.trim(rs.getString("pymtchannel")));
				 		transaction.setSystemReferenceInt(StringUtils.trim(rs.getString("sysreference")));
				 		transaction.setTxnAmount(StringUtils.trim(rs.getString("txnamount")));
				 		transaction.setAssetCode(StringUtils.trim(rs.getString("assetcode")));
				 		transaction.setTxnType(StringUtils.trim(rs.getString("paytype")));
				 		transactionList.add(transaction);
				 	}
			 }
			 if(transactionList!=null)
				 if(transactionList.size()==0)
					 transactionList=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getFiatWalletLastTenTxn  is  "+e.getMessage());
			throw new Exception ("The exception in method getFiatWalletLastTenTxn  is  "+e.getMessage());			
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
		return transactionList;
	}
	

	public ConcurrentHashMap<String, String> getTransactionTypes() throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ConcurrentHashMap<String,String> hashTxnType=null;
		
		try {
			NeoBankEnvironment.setComment(3, className, "inside getTransactionTypes  ");

			connection = super.getConnection();
			query = "select paymode, rulesdesc from transaction_rules where status = ?";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, "A");
				rs = (ResultSet)pstmt.executeQuery();
				
				if(rs!=null){
					hashTxnType = new ConcurrentHashMap<String,String>();

					while(rs.next()){
				 		hashTxnType.put( StringUtils.trim(rs.getString("paymode")),   StringUtils.trim(rs.getString("rulesdesc")) );
				 	}
				}	
				pstmt.close();
				
				if (hashTxnType != null)	
					if (hashTxnType.size() == 0)			
						hashTxnType = null;
			
		}catch(Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getTransactionTypes  is  "+e.getMessage());
			throw new Exception ("The exception in method getTransactionTypes  is  "+e.getMessage());	
		}finally {
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(rs!=null) rs.close(); if(pstmt!=null) pstmt.close(); 
		}
		return hashTxnType;
	}
	
	public String getReceiverExternalWalletId(String receiverEmail, String assetCode) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String receiverWalletId=null;
			try {
			 connection = super.getConnection();	
			 query = " select a.walletid walletid from wallet_details a, customer_details b, wallet_assets c "
				 	   + " where a.relationshipno = b.relationshipno and b.custemail = ? and a.wallettype= c.wallettype and c.asset_code = ?";
			 pstmt = connection.prepareStatement(query);
			 pstmt.setString(1, receiverEmail);
			 pstmt.setString(2, assetCode);
			 rs = (ResultSet)pstmt.executeQuery();	
			 if(rs!=null){
			 	while(rs.next()){	 			 			
			 		receiverWalletId = (StringUtils.trim(rs.getString("walletid"))    );
			 		receiverWalletId = (StringUtils.trim(rs.getString("walletid"))    );
			 		NeoBankEnvironment.setComment(3,className,"receiverWalletId is " +receiverWalletId  );
					} // end of while
				} //end of if rs!=null check
			
			}catch(Exception e){
				receiverWalletId = null;
				NeoBankEnvironment.setComment(1,className,"The exception in method getReceiverPorteWalletId  is  "+e.getMessage());
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
		return receiverWalletId;
	}
	

}
