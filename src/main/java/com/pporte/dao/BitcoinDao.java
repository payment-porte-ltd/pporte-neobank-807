package com.pporte.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.pporte.NeoBankEnvironment;
import com.pporte.model.CryptoAssetCoins;
import com.pporte.model.Transaction;
import com.pporte.utilities.Utilities;

public class BitcoinDao extends HandleConnections {
	
	public static String className = BitcoinDao.class.getName();
	/**
	 * This method will also check if Customer has Stellar Account
	 * @param relationshipNo
	 * @return
	 * @throws Exception
	 */
	public boolean checkIfBitcoinHasBeenLinkedByCustomer(String relationshipNo)  throws Exception{
		NeoBankEnvironment.setComment(3, className, "Rel number is "+relationshipNo);
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String btcAddress = null;
		String stellarPublicKey = null;
		//PPWalletEnvironment.setComment(3,className,"emailId  is  "+emailId);
		boolean result = false;
		try{
			connection = super.getConnection();
			query = " select btc_address from bitcoin_account_relation where relationshipno = ? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				while(rs.next()){	
					btcAddress = StringUtils.trim(rs.getString("btc_address"));
				 	} // end of while
				 } //end of if
			// NeoBankEnvironment.setComment(3, className, "btcAddress in checkIfBitcoinHasBeenLinkedByCustomer is "+btcAddress);
			if(rs!=null) rs.close(); if(pstmt!=null) pstmt.close();
			query = " select public_key from stellar_account_relation where relationshipno = ? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				while(rs.next()){	
					stellarPublicKey = StringUtils.trim(rs.getString("public_key"));
				 	} // end of while
				 } //end of if
			
			 if( btcAddress != null && stellarPublicKey!=null ) {
				 result = true;
			 }
			// NeoBankEnvironment.setComment(3, className, "result in checkIfBitcoinHasBeenLinkedByCustomer is "+result);
		}catch(Exception e){
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close(); if (btcAddress!=null)btcAddress=null;
			}
		
		return result;
	}
	
	public boolean insertMnemonicCode(String relationshipNo, String encryptedMnemonic) throws Exception{
		NeoBankEnvironment.setComment(3, className, "relationshipNo is "+relationshipNo+" Encyripted mnemonic is "+encryptedMnemonic);
		PreparedStatement pstmt=null;
		String query=null;
		Connection connection=null;
		boolean result=false;
		try {
			connection = super.getConnection();
			connection.setAutoCommit(false);
				//													1			2		3			4	   5
			query ="insert into mnemonic_cust_relation_bc (relationshipno,mnemonic_code,status,createdon, network)"
					+ "values(?,?,?,?, ?)";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo );
			pstmt.setString(2, encryptedMnemonic );
			pstmt.setString(3, "A" );
			pstmt.setString(4,  Utilities.getMYSQLCurrentTimeStampForInsert() );
			pstmt.setString(5,  "B" );
			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}pstmt.close();
			connection.commit();			 	
			result = true;
			
		}catch(Exception e) {
			connection.rollback();
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
			if(pstmt!=null) pstmt.close();
		}

		return result;
	}
	
	public boolean registerBitcoinAccount(String publicKey, String btcAddress, String relationshipNo, 
			String tokenizedMnemonic, long btcCreationTime) throws Exception {
		//NeoBankEnvironment.setComment(3, className, "In registerBitcoinAccount");
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		String walletId =null;
		SimpleDateFormat formatter1=null;
		try{

			connection = super.getConnection();	
			connection.setAutoCommit(false);
			 
			  //                                               1             2            3                          4         5				6       7
			 query = " insert into bitcoin_account_relation (btc_address, public_key, relationshipno, tokenized_mnemonic , btc_creation_time, status, createdon ) "
						+ " values (?, ?, ?, ? , ?, ?, ?) ";
						 //		    1  2  3  4   5  6  7 
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, (btcAddress)); 
			pstmt.setString(2, ( publicKey)); 
			pstmt.setString(3, ( relationshipNo)); 				
			pstmt.setString(4, (tokenizedMnemonic)); 
			pstmt.setString(5, (String.valueOf(btcCreationTime)) ); 
			pstmt.setString(6, "A" ); 
			pstmt.setString(7,Utilities.getMYSQLCurrentTimeStampForInsert());
			try {
				pstmt.executeUpdate();
				}catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
				}	
			if(pstmt!=null) pstmt.close();
			
			formatter1 = new SimpleDateFormat ("yyMMdd");  formatter1.setTimeZone(TimeZone.getTimeZone("UTC"));
			walletId = (formatter1.format(new Date()))+( RandomStringUtils.random(10, false, true)).toString();
			  //                                               1             2            3         4         5        6          7           8
			 query = "insert into wallet_details_external (walletid, relationshipno, walletdesc, usertype , status, assetcode, lastupdated, createdon  ) "
						+ "values (?, ?, ?, ? , ?, ?, ?, ?) ";
						//		   1  2  3  4  5   6  7  8
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, (walletId)); 
			pstmt.setString(2, ( relationshipNo)); 
			pstmt.setString(3, ( "External wallet")); 				
			pstmt.setString(4, ("C")); 
			pstmt.setString(5, ("A")); 
			pstmt.setString(6, (NeoBankEnvironment.getBitcoinCode())); 
			pstmt.setString(7, (Utilities.getMYSQLCurrentTimeStampForInsert())); 
			pstmt.setString(8,  Utilities.getMYSQLCurrentTimeStampForInsert() ); 

			try {
				pstmt.executeUpdate();
				}catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
				}	

			connection.commit();
			result = true;

		}catch(Exception e){
			result = false;
			connection.rollback(); result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());			
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
		return result;
	}
	public ArrayList<CryptoAssetCoins> getBTCDetails() throws Exception{                       
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		CryptoAssetCoins porteCoin = null;
		ArrayList<CryptoAssetCoins> porteCoinsList = null;
		try{
			connection = super.getConnection();	
			query =   " select  asset_code, asset_desc, status, asset_type, wallettype from wallet_assets"
					+ " where asset_type=? and status=? and asset_code=? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "E");
			pstmt.setString(2, "A");
			pstmt.setString(3, "BTC");
			rs = (ResultSet)pstmt.executeQuery();
	
			 if(rs!=null){
				 porteCoinsList = new ArrayList<CryptoAssetCoins>();
				 	while(rs.next()){	
				 		porteCoin = new CryptoAssetCoins();
				 		porteCoin.setWalletType(StringUtils.trim(rs.getString("wallettype")));
				 		porteCoin.setAssetCode(StringUtils.trim(rs.getString("asset_code")));
				 		porteCoin.setAssetDescription(StringUtils.trim(rs.getString("asset_desc")));
				 		porteCoin.setAssetType(StringUtils.trim(rs.getString("asset_type")));
				 		porteCoinsList.add(porteCoin);
				 	}
			 }	
			 
			 if(porteCoinsList!=null)
			 if(porteCoinsList.size()==0)
				 porteCoinsList=null;
				
		}catch(Exception e){
			porteCoinsList = null;
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());		
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
		return porteCoinsList;
	}
	
	public Boolean buyBTCCoinViaToken(String relationshipNo, String tokenId, String amountInUSD, String payComments,
			String referenceNo, String txnUserCode, String customerCharges, String txnPayMode, String assetCode,
			String extSystemRef, String btcWalletId, String amountInBTC) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		String transactionDatetime = null;
		String currencyId= assetCode;
		String transactionCode = null;
		String transactionCode2 = null;
		String userType = "C";
		String systemreference =referenceNo;
		String senderRefNo = null;
		String totalAccruedBalance=null;
		try {
			String customerChargesValue = customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|"));
			//String customerPayType = customerCharges.substring(0, customerCharges.indexOf(","));
			amountInUSD = amountInUSD.replaceAll(",", "");
			transactionDatetime = Utilities.getMYSQLCurrentTimeStampForInsert();
			SimpleDateFormat formatter1 = new SimpleDateFormat ("yyMMddHHmmssSSS");
			transactionCode = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
			transactionCode2 = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
			connection = super.getConnection();
			connection.setAutoCommit(false);
			//********Step 1 update wallet ballance*********\\\\	
						
												//    1        2         3         4              5                    6             7       8         9          10                 11        
			query = "insert into txn_cardtoken_bc (txncode, tokenid, txnusercode, sysreference_ext, sysreference_int, txnamount, custrelno, paymode, txndatetime, txncurrencyid ) "
			+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?, ?  ) ";
			// 1  2  3  4  5  6   7	 8  9  10 11 
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, transactionCode); 			
			pstmt.setString(2, tokenId); 										
			pstmt.setString(3, txnUserCode);							
			pstmt.setString(4, extSystemRef);  // External 
			pstmt.setString(5, systemreference); // Internal generated
			pstmt.setBigDecimal(6, new BigDecimal(amountInUSD));
			pstmt.setString(7, relationshipNo); 
			pstmt.setString(8,  txnPayMode);
			pstmt.setString(9,  transactionDatetime );
			pstmt.setString(10,  NeoBankEnvironment.getUSDCurrencyId() );

			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}					
			if( pstmt!=null)		pstmt.close();	

			// **********Step 5.1: credit to business ledger

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
			//Credit to business ledger
			//											   1		2			3			4				5				6			7			8		   9          10            11

			query = " insert into txn_business_ledger_bc (txncode, paytype, custwalletid, merchwalletid, sysreference, txnamount, txncurrencyid, txnmode, txndatetime, accrued_balance, pymtchannel) "

					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
							// 1  2  3  4  5  6  7  8  9  10 11
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, transactionCode); 
			pstmt.setString(2, txnPayMode);
			pstmt.setString(3, btcWalletId);
			pstmt.setString(4, "");
			pstmt.setString(5, systemreference);			// Additional charges for customer						
			pstmt.setBigDecimal(6, BigDecimal.valueOf( Double.parseDouble(amountInUSD)));  
			pstmt.setString(7, NeoBankEnvironment.getUSDCurrencyId());
			pstmt.setString(8, "C"); // Credit 
			pstmt.setString(9, transactionDatetime);
			
			if (totalAccruedBalance!=null) {
				pstmt.setBigDecimal(10,BigDecimal.valueOf( (Double.parseDouble(totalAccruedBalance)+Double.parseDouble(amountInUSD) )));
			}else {
				pstmt.setBigDecimal(10,BigDecimal.valueOf( Double.parseDouble(amountInUSD) ));
			}
			pstmt.setString(11, "W");
			try {
					pstmt.executeUpdate();
				}catch(Exception e) {
					throw new Exception (" failed query "+query +" "+e.getMessage());
				}					
			if( pstmt!=null)pstmt.close();
			
			
			// **********Step 5.2 add to Buy coins transaction table
			//			                           1		2			3			4		  5				6			   7			   8		 9          10      11
			query = "insert into txn_buy_coins (txn_code, walletid, custrelno, asset_code, txnamount, sysreference_ext, sysreference_int, status, txndate, coin_amount, commet ) "
			+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
					// 1  2  3  4  5  6  7  8  9  10 11
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, transactionCode); 
			pstmt.setString(2, btcWalletId);
			pstmt.setString(3, relationshipNo);
			pstmt.setString(4, assetCode);
			pstmt.setBigDecimal(5, BigDecimal.valueOf( Double.parseDouble(amountInUSD)));
			pstmt.setString(6, "");								
			pstmt.setString(7, systemreference);								
			pstmt.setString(8, "N"); 
			pstmt.setString(9, transactionDatetime);
			pstmt.setString(10, amountInBTC);
			pstmt.setString(11, payComments);
			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query +" "+e.getMessage());
			}					
			if( pstmt!=null)pstmt.close();
			
			//**********Step 4: Record the charges to transaction ledger
			if(Double.parseDouble(customerChargesValue) > 0 ) {

								//    1        2         3         4              5                    6             7       8         9          10                 11        
				query = "insert into txn_cardtoken_bc (txncode, tokenid, txnusercode, sysreference_ext, sysreference_int, txnamount, custrelno, paymode, txndatetime, txncurrencyid ) "
				+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?, ?  ) ";
				// 1  2  3  4  5  6   7	 8  9  10 11 
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, transactionCode2); 			
				pstmt.setString(2, tokenId); 										
				pstmt.setString(3, txnUserCode);							
				pstmt.setString(4, extSystemRef);  // External 
				pstmt.setString(5, systemreference+"-AC"); // Internal generated
				pstmt.setBigDecimal(6, new BigDecimal(customerChargesValue));
				pstmt.setString(7, relationshipNo); 
				pstmt.setString(8,  txnPayMode);
				pstmt.setString(9,  transactionDatetime );
				pstmt.setString(10,  currencyId );
				try {
					pstmt.executeUpdate();
				}catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
				}					
				if( pstmt!=null)		pstmt.close();	
								
				query = "select accrued_balance, sequenceid from txn_business_ledger_bc order by sequenceid desc limit 1";
				pstmt = connection.prepareStatement(query);
				rs = (ResultSet)pstmt.executeQuery();
				if(rs!=null){
					while(rs.next()){	 			 			
						 totalAccruedBalance= ( StringUtils.trim(rs.getString("accrued_balance"))  );
						} // end of while
					NeoBankEnvironment.setComment(3,className,"step 1   total Business AccruedBalance is  "+totalAccruedBalance   );
				} 
				if(pstmt!=null) pstmt.close();	if(rs!=null) rs.close();
				
				
             //												   1		2			3			4				5				6			7			8		   9          10            11
				query = "insert into txn_business_ledger_bc (txncode, paytype, custwalletid, merchwalletid, sysreference, txnamount, txncurrencyid, txnmode, txndatetime, accrued_balance, pymtchannel) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
								// 1  2  3  4  5  6  7  8  9  10 11
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, transactionCode2); 
				pstmt.setString(2, txnPayMode);
				pstmt.setString(3, btcWalletId);
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
				if( pstmt!=null) pstmt.close();
		}
			
		// Step 6.1: Get the Loyalty Rules based on the type of transaction and conversion
			String pointsConversion = null;
			String previousPoinsBalance = null;
			boolean firstime = false;
			String pointsaccrued =null;
			query = "select pointsconversion from loyalty_rules where paymode=? and status = ? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, txnPayMode);
			pstmt.setString(2, "A");
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				 while(rs.next()){	 			 			
				 	pointsConversion = (StringUtils.trim(rs.getString("pointsconversion"))  );
				 } // end of while
			} //end of if rs!=null check 
			if( pstmt!=null)	pstmt.close();
			if(pointsConversion!=null) {
				query = "select pointbalance balance from loyalty_points_bc where relationshipno=? and sequenceid = (select max(sequenceid) from  loyalty_points_bc where relationshipno = ? ) ";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, relationshipNo);
				pstmt.setString(2, relationshipNo);
				rs = (ResultSet)pstmt.executeQuery();
				 if(rs!=null){
				 	while(rs.next()){	 			 			
				 		previousPoinsBalance = (StringUtils.trim(rs.getString("balance"))  );
				 		} // end of while
				} //end of if rs!=null check
				 if( pstmt!=null)	pstmt.close();
				NeoBankEnvironment.setComment(3,className,"************ Points Balance for relationshipNo   "+ relationshipNo + "  is " + previousPoinsBalance );
				pointsaccrued = Double.toString((Double.parseDouble(amountInUSD) * Double.parseDouble(pointsConversion)));
				NeoBankEnvironment.setComment(3,className,"******** pointsaccrued   "+ pointsaccrued +" amountInUSD "+ amountInUSD + " pointsConversion "+ pointsConversion);
			   if(previousPoinsBalance==null) { // For the first time 
				  firstime =true;
				  NeoBankEnvironment.setComment(3,	className,"***LOYALTY FIRST TIME***No previous record present for user: " +btcWalletId); 
			 
			   }else {
				   NeoBankEnvironment.setComment(3,	className," Previous Point balance for user : " +btcWalletId+ " is  " +previousPoinsBalance); 
			   }	 

				//***** Step 6.2****: Insert loyalty points for the sender user		
				 
						                             //	  	1		2				3			4			5			6				7        8        9
				query = "insert into loyalty_points_bc (walletid, relationshipno, usertype, paymode, txnreference, pointaccrued, pointbalance, status, txndatetime) "
							+ "values (?, ?, ?, ?, ?, ?, ?, ?,?) ";
							//		   1  2  3  4  5  6  7  8 9
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, btcWalletId); 	
				pstmt.setString(2, relationshipNo);
				pstmt.setString(3, "C"); 						// 
				pstmt.setString(4, txnPayMode);	
				pstmt.setString(5,systemreference ); // transaction reference generated by the system
				pstmt.setBigDecimal(6, new BigDecimal(pointsaccrued));  // pointaccrued
				if(firstime)
					pstmt.setBigDecimal(7, new BigDecimal(pointsaccrued));  // pointaccrued
				else {
					pstmt.setBigDecimal(7, BigDecimal.valueOf(  (Double.parseDouble(pointsaccrued) + Double.parseDouble(previousPoinsBalance ) ) )  );  // pointbalance
				}
				pstmt.setString(8,"U" );	
				pstmt.setString(9,transactionDatetime );	
				try {
				pstmt.executeUpdate();
				}catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
				}							 
			 }
			 NeoBankEnvironment.setComment(3,	className,"inserting into loyalyty  pointsaccrued " + pointsaccrued ); 
			 
			 
			
			connection.commit();
			result = true;
			
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
			if(userType!=null)  userType=null; if(txnUserCode != null) txnUserCode = null;
			if(systemreference!=null)  systemreference=null; if(senderRefNo!=null)  senderRefNo=null; 
			if ( totalAccruedBalance!=null) totalAccruedBalance=null;
		}
		return result;
	}
	
	public Boolean buyBTCViaFiatWallet(String relationshipNo, String fiatWalletId, String amountInUSD,
			String payComments, String referenceNo, String txnUserCode, String txnPayMode, String assetCode,
			String extSystemRef, String BTCxWalletId,
			String customerCharges, String BTCxAmount,String fiatTransactionCode) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		String transactionDatetime = null;
		String currencyId= assetCode;
		String fiatWalletTxnMode = "D";
		String btcWalletTxnMode = "C";
		String fiatWalletTransactionCode2 = null;
		String btcWalletTransactionCode = null;
		String userType = "C";
		String systemreference =referenceNo;
		String totalAccruedBalance=null;
		try {
			String customerChargesValue = customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|"));
			String customerPayType = customerCharges.substring(0, customerCharges.indexOf(","));

			amountInUSD = amountInUSD.replaceAll(",", "");
			transactionDatetime = Utilities.getMYSQLCurrentTimeStampForInsert();
			SimpleDateFormat formatter1 = new SimpleDateFormat ("yyMMddHHmmssSSS");
			fiatWalletTransactionCode2 = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
			btcWalletTransactionCode = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
		    connection = super.getConnection();
		    connection.setAutoCommit(false);
		    
		    //********Step 1*********\\\\	
			 double fiatWalletDebitAmount =  Double.parseDouble(amountInUSD) + Double.parseDouble(customerChargesValue);
			 
			 //*******Step 2: Update the Fiat wallet 
		     query = " update wallet_details set currbal= currbal - ?, lastupdated = ? where  walletid=? ";
			 pstmt = connection.prepareStatement(query);
			 pstmt.setBigDecimal(1,BigDecimal.valueOf(fiatWalletDebitAmount));
			 pstmt.setString(2, transactionDatetime); 
			 pstmt.setString(3, fiatWalletId); 
			 try {
				 NeoBankEnvironment.setComment(3,className,"update Fiat wallet amount" + fiatWalletDebitAmount );
				 pstmt.executeUpdate();
				 }catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
			 }					
			 if( pstmt!=null)	pstmt.close();
			
			// **********Step 3: Record the wallet transaction ledger
			
													// 1		2			3			4			5				6		7			8
			query = "insert into txn_wallet_cust_bc (txncode, walletid, sysreference, txnamount, txncurrencyid, txnmode, txndatetime, txnusercode, pymtchannel) "
			+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?) ";
					// 1  2  3  4  5  6   7  8
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, fiatTransactionCode); 			
			pstmt.setString(2, fiatWalletId); 				
			pstmt.setString(3, systemreference );							
			pstmt.setBigDecimal(4, new BigDecimal(amountInUSD));  
			pstmt.setString(5, NeoBankEnvironment.getUSDCurrencyId());

			pstmt.setString(6, fiatWalletTxnMode); // Debit as it is a payment by the sender
			pstmt.setString(7, transactionDatetime);
			pstmt.setString(8, txnUserCode);
			pstmt.setString(9, "W");
			try {
				NeoBankEnvironment.setComment(3,className,"Executed Sender wallet_tnx_bc amount"+"fiatTransactionCode is"+ fiatTransactionCode +"fiatWalletId is"+fiatWalletId 
				+"systemreference is"+systemreference +"payAmount is"+amountInUSD +"currencyId is"+ currencyId+ "transactionDatetime"+ transactionDatetime);
			pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}					
			if( pstmt!=null)	pstmt.close();
			
			// **********Step 3: credit to business ledger

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
			//Credit to business ledger
			//											   1		2			3			4				5				6			7			8		   9          10            11
			query = "insert into txn_business_ledger_bc (txncode, paytype, custwalletid, merchwalletid, sysreference, txnamount, txncurrencyid, txnmode, txndatetime, accrued_balance, pymtchannel) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
							// 1  2  3  4  5  6  7  8  9  10 11
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, btcWalletTransactionCode); 
			pstmt.setString(2, txnPayMode);
			pstmt.setString(3, fiatWalletId);
			pstmt.setString(4, "");
			pstmt.setString(5, systemreference);			// Additional charges for customer						
			pstmt.setBigDecimal(6, BigDecimal.valueOf( Double.parseDouble(amountInUSD)));  
			pstmt.setString(7, NeoBankEnvironment.getUSDCurrencyId());

			pstmt.setString(8, "C"); // Credit 
			pstmt.setString(9, transactionDatetime);
			
			if (totalAccruedBalance!=null) {
				pstmt.setBigDecimal(10,BigDecimal.valueOf( (Double.parseDouble(totalAccruedBalance)+Double.parseDouble(amountInUSD) )));
			}else {
				pstmt.setBigDecimal(10,BigDecimal.valueOf( Double.parseDouble(amountInUSD) ));
			}
			pstmt.setString(11, "W");
			try {
					pstmt.executeUpdate();
				}catch(Exception e) {
					throw new Exception (" failed query "+query +" "+e.getMessage());
				}					
			if( pstmt!=null)pstmt.close();
			
			
			// **********Step 4 add to Buy coins transaction table
			//			                           1		2			3			4		  5				6			   7			   8		 9          10      11
			query = "insert into txn_buy_coins (txn_code, walletid, custrelno, asset_code, txnamount, sysreference_ext, sysreference_int, status, txndate, coin_amount, commet ) "
			+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
					// 1  2  3  4  5  6  7  8  9  10 11
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, btcWalletTransactionCode); 
			pstmt.setString(2, BTCxWalletId);
			pstmt.setString(3, relationshipNo);
			pstmt.setString(4, assetCode);
			pstmt.setBigDecimal(5, BigDecimal.valueOf( Double.parseDouble(amountInUSD)));
			pstmt.setString(6, "");								
			pstmt.setString(7, systemreference);								
			pstmt.setString(8, "N"); 
			pstmt.setString(9, transactionDatetime);
			pstmt.setString(10, BTCxAmount);
			pstmt.setString(11, payComments);
			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query +" "+e.getMessage());
			}					
			if( pstmt!=null)pstmt.close();
						
			// **********Step 5 customer charges
			if(Double.parseDouble(customerChargesValue)>0) {
				query = "insert into txn_wallet_cust_bc (txncode, walletid, sysreference, txnamount, txncurrencyid, txnmode, txndatetime, txnusercode, pymtchannel) "
						+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?) ";
								// 1  2  3  4  5  6   7  8
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, fiatWalletTransactionCode2); 			
				pstmt.setString(2, fiatWalletId); 				
				pstmt.setString(3, systemreference+"-AC" );							
				pstmt.setBigDecimal(4, new BigDecimal(Double.parseDouble(customerChargesValue)));  
				pstmt.setString(5, NeoBankEnvironment.getUSDCurrencyId());
				pstmt.setString(6, customerPayType); 
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
				pstmt.setString(1, fiatWalletTransactionCode2); 
				pstmt.setString(2, txnPayMode);
				pstmt.setString(3, fiatWalletId);
				pstmt.setString(4, "");
				pstmt.setString(5, systemreference+"-AC" );			// Additional charges for customer						
				pstmt.setBigDecimal(6, BigDecimal.valueOf( Double.parseDouble(customerChargesValue)));  
				pstmt.setString(7, NeoBankEnvironment.getUSDCurrencyId());

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
			
			
			// Step 6.1: Get the Loyalty Rules based on the type of transaction and conversion
			String pointsConversion = null;
			String previousPoinsBalance = null;
			boolean firstime = false;
			String pointsaccrued =null;
			query = "select pointsconversion from loyalty_rules where paymode=? and status = ? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, txnPayMode);
			pstmt.setString(2, "A");
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				 while(rs.next()){	 			 			
				 	pointsConversion = (StringUtils.trim(rs.getString("pointsconversion"))  );
				 } // end of while
			} //end of if rs!=null check 
			if( pstmt!=null)	pstmt.close();
			if(pointsConversion!=null) {
				query = "select pointbalance balance from loyalty_points_bc where relationshipno=? and sequenceid = (select max(sequenceid) from  loyalty_points_bc where relationshipno = ? ) ";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, relationshipNo);
				pstmt.setString(2, relationshipNo);
				rs = (ResultSet)pstmt.executeQuery();
				 if(rs!=null){
				 	while(rs.next()){	 			 			
				 		previousPoinsBalance = (StringUtils.trim(rs.getString("balance"))  );
				 		} // end of while
				} //end of if rs!=null check
				 if( pstmt!=null)	pstmt.close();
				NeoBankEnvironment.setComment(3,className,"*****Points Balance for relationshipNo   "+ relationshipNo + "  is " + previousPoinsBalance );
				
				pointsaccrued = Double.toString((Double.parseDouble(amountInUSD) * Double.parseDouble(pointsConversion)));
				NeoBankEnvironment.setComment(3,className,"********* pointsaccrued   "+ pointsaccrued + " amount "+ amountInUSD  +" pointsConversion "+pointsConversion);
			   if(previousPoinsBalance==null) { // For the first time 
				  firstime =true;
				  NeoBankEnvironment.setComment(3,	className,"***LOYALTY FIRST TIME***No previous record present for user: " +fiatWalletId); 
			 
			   }else {
				   NeoBankEnvironment.setComment(3,	className," Previous Point balance for user : " +fiatWalletId+ " is  " +previousPoinsBalance); 
			   }	 

				//***** Step 6.2****: Insert loyalty points for the sender user		
				 
						                             //	  	1		2				3			4			5			6				7        8        9
				query = "insert into loyalty_points_bc (walletid, relationshipno, usertype, paymode, txnreference, pointaccrued, pointbalance, status, txndatetime) "
							+ "values (?, ?, ?, ?, ?, ?, ?, ?,?) ";
							//		   1  2  3  4  5  6  7  8 9
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, fiatWalletId); 	
				pstmt.setString(2, relationshipNo);
				pstmt.setString(3, "C"); 						// 
				pstmt.setString(4, txnPayMode);	
				pstmt.setString(5,systemreference ); // transaction reference generated by the system
				pstmt.setBigDecimal(6, new BigDecimal(pointsaccrued));  // pointaccrued
				if(firstime)
				pstmt.setBigDecimal(7, new BigDecimal(pointsaccrued));  // pointaccrued
				else {
					pstmt.setBigDecimal(7, BigDecimal.valueOf(  (Double.parseDouble(pointsaccrued) + Double.parseDouble(previousPoinsBalance ) ) )  );  // pointbalance
				}
				pstmt.setString(8,"U" );	
				pstmt.setString(9,transactionDatetime );	
				try {
				pstmt.executeUpdate();
				}catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
				}							 
			 }
			 NeoBankEnvironment.setComment(3,	className,"inserting into loyalyty  pointsaccrued " + pointsaccrued ); 
			 
			 
		 	if(CustomerWalletDao.insertGenericWalletTransactions(fiatTransactionCode, fiatWalletId,txnUserCode, systemreference, amountInUSD, NeoBankEnvironment.getUSDCurrencyId(),fiatWalletTxnMode, transactionDatetime ,userType) == false) {
				throw new Exception ("Blockchain insert issue");
			}
			if(Double.parseDouble(customerChargesValue)>0) {
				if(	CustomerWalletDao.insertGenericWalletTransactions(fiatWalletTransactionCode2, fiatWalletId,txnUserCode, systemreference+"-AC", customerChargesValue, NeoBankEnvironment.getUSDCurrencyId(),customerPayType, transactionDatetime ,userType) == false) {
					throw new Exception ("Blockchain insert issue");
				}
			}

			connection.commit();
			result = true;
			
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
	
			if (rs != null)	rs.close(); if (pstmt != null) pstmt.close();if(transactionDatetime!=null)  transactionDatetime=null; if(btcWalletTransactionCode!=null)  btcWalletTransactionCode=null;
			if(userType!=null)  userType=null; if(fiatTransactionCode!=null)  fiatTransactionCode=null; if(fiatWalletTransactionCode2!=null)  fiatWalletTransactionCode2=null; if(txnUserCode != null) txnUserCode = null;
			if(systemreference!=null)  systemreference=null; if(currencyId!=null)  currencyId=null; if ( totalAccruedBalance!=null) totalAccruedBalance=null;
			if(btcWalletTxnMode!=null)  btcWalletTxnMode=null; if ( fiatWalletTxnMode!=null) fiatWalletTxnMode=null;
		
			}
		
		return result;
		
	}
	
	public List<Transaction> getPendingBTCxTransactions(String realationshipNo)  throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		Transaction transaction = null;
		List<Transaction> transactionList=null;
		try{
			
			connection = super.getConnection();	
			query = " select txn_code,  walletid,  custrelno,  asset_code, "
					+ " coin_amount,  sysreference_int, txndate, status from txn_buy_coins "
					+ " where custrelno = ? and status = ? and asset_code=? order by txndate desc ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, realationshipNo);
			pstmt.setString(2, "N");
			pstmt.setString(3, "BTC");
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 transactionList = new ArrayList<Transaction>();
				 	while(rs.next()){	
				 		transaction = new Transaction();
				 		transaction.setTxnDateTime(Utilities.displayDateFormat( StringUtils.trim(rs.getString("txndate")), "dd MMM yy HH:mm a"));
				 		transaction.setTxnCode(StringUtils.trim(rs.getString("txn_code")));
				 		transaction.setCustomerWalletId(StringUtils.trim(rs.getString("walletid")));
				 		transaction.setRelationshipNo(StringUtils.trim(rs.getString("custrelno")));
				 		transaction.setAssetCode(StringUtils.trim(rs.getString("asset_code")));
				 		transaction.setCoinAmount(StringUtils.trim(rs.getString("coin_amount")));
				 		transaction.setStatus(StringUtils.trim(rs.getString("status")));
				 		transactionList.add(transaction);
				 	}
			 }
			 if(transactionList!=null)
				 if(transactionList.size()==0)
					 transactionList=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
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
	
	public String getBTCMnemonicCode(String relationshipNo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String mnemonicCode = null;
		try {	
			connection = super.getConnection();	                    
			query = " select mnemonic_code from mnemonic_cust_relation_bc where relationshipno = ? and network = ? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, "B");
			
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){
			 	while(rs.next()){	 
			 		mnemonicCode = Utilities.tripleDecryptData( StringUtils.trim(rs.getString("mnemonic_code")));
			 		} 
			 	//NeoBankEnvironment.setComment(3, className, "Mnemonic from db is "+mnemonicCode);
			 } 
	
		}catch(Exception e) {
			mnemonicCode = null;
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
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
	
		return mnemonicCode;
	}
	
	public String checkIfUserHasBitcoinMnemonicCode(String relationshipNo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String mnemonicFlag = null;
		try {
			connection = super.getConnection();	  			
			query = " select tokenized_mnemonic from bitcoin_account_relation where relationshipno = ? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
			 	while(rs.next()){	 
			 		mnemonicFlag = StringUtils.trim(rs.getString("tokenized_mnemonic"));
			 	} 
			 } 
			 NeoBankEnvironment.setComment(3, className, "Mnemonic flag is "+mnemonicFlag);
		}catch(Exception e) {
			mnemonicFlag = null;
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
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
	
		return mnemonicFlag;
	}
	public String getBTCDetails(String relationshipNo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String btcDetails = null;
		String btcAddress = null;
		String btcPublicKey = null;
		try {
			
			connection = super.getConnection();	  			
			query = " select btc_address, public_key from bitcoin_account_relation where relationshipno = ? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
			 	while(rs.next()){	 
			 		btcAddress = Utilities.tripleDecryptData(StringUtils.trim(rs.getString("btc_address")));
			 		btcPublicKey = Utilities.tripleDecryptData(StringUtils.trim(rs.getString("public_key")));
			 	} 
			 } 
			 if(btcAddress!=null && btcPublicKey!=null) {
				 btcDetails = btcAddress+","+btcPublicKey;
			 }
		}catch(Exception e) {
			btcDetails = null;
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
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
	
		return btcDetails;
	}
	
	public boolean debitP2PTransactionCharges(String relationshipNo, String walletId, 
			String payAmount, String payComments,  String referenceNo, 
			String txnUserCode, String customerCharges, String transactionCode) throws Exception {
			PreparedStatement pstmt=null;
			Connection connection = null;
			ResultSet rs=null;
			String query = null;
			boolean result = false;
			String transactionDatetime = null;
			String currencyId= NeoBankEnvironment.getUSDCurrencyId();
			String userType = "C";
			String systemreference =referenceNo;
			String senderRefNo = null;
			String receiverRefNo = null;
			String totalAccruedBalance=null;
			try {	
				 connection = super.getConnection();
				 connection.setAutoCommit(false);

				String customerChargesValue = customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|"));
				String customerPayType = customerCharges.substring(0, customerCharges.indexOf(","));

				payAmount = payAmount.replaceAll(",", "");
				transactionDatetime = Utilities.getMYSQLCurrentTimeStampForInsert();

				  
				
				 double debitAmount =   Double.parseDouble(customerChargesValue);
				 //*******Step 1: Update the wallet Ledger
			     query = " update wallet_details set currbal= currbal - ?, lastupdated = ? where  walletid=? ";
				 pstmt = connection.prepareStatement(query);
				 pstmt.setBigDecimal(1,BigDecimal.valueOf(debitAmount));
				 pstmt.setString(2, transactionDatetime); 
				 pstmt.setString(3, walletId); 
				 try {
					 NeoBankEnvironment.setComment(3,className,"update  wallet amount" + debitAmount );
					 pstmt.executeUpdate();
					 }catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
				 }					
				 if(pstmt!=null)pstmt.close();
				
				 //*******Step 2: Update the wallet Ledger
				if(Double.parseDouble(customerChargesValue)>0) {
					query = "insert into txn_wallet_cust_bc (txncode, walletid, sysreference, txnamount, txncurrencyid, txnmode, txndatetime, txnusercode, pymtchannel) "
							+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?) ";
									// 1  2  3  4  5  6   7  8
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, transactionCode); 			
					pstmt.setString(2, walletId); 				
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
						
					if(pstmt!=null) pstmt.close();	
									
					
					//Select the balance to be updated. 
					query = " select accrued_balance, sequenceid from txn_business_ledger_bc order by sequenceid desc limit 1 ";
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
					pstmt.setString(1, transactionCode); 
					pstmt.setString(2, NeoBankEnvironment.getBTCP2PTxnCode());
					pstmt.setString(3, walletId);
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
					 
				// call the Blockchain method here and pass the values within the method. Here we are inserting in the walletledger stream of Blockchain having chainame ppwallet
				// inserting block data for sender
				 
				if(Double.parseDouble(customerChargesValue)>0) {
					if(	CustomerWalletDao.insertGenericWalletTransactions(transactionCode, walletId,txnUserCode, systemreference+"-AC", customerChargesValue, currencyId,customerPayType, transactionDatetime ,userType) == false) {
						throw new Exception ("Blockchain insert issue");
					}
				}
					 
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
					if (rs != null)	rs.close(); if (pstmt != null) pstmt.close();if(transactionDatetime!=null)  transactionDatetime=null; if(currencyId!=null)  currencyId=null; 
					if(userType!=null)  userType=null; if(receiverRefNo!=null)  receiverRefNo=null; if(txnUserCode != null) txnUserCode = null;
					if(systemreference!=null)  systemreference=null; if(senderRefNo!=null)  senderRefNo=null; if ( totalAccruedBalance!=null) totalAccruedBalance=null;
			
				}
			
			return result;
	}
	
	public String getTDABTCAddress() throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String tdaBTCAddress = null;
		try {		
			connection = super.getConnection();	  			
			query = " select public_key from wallet_assets_account where asset_code = ? and account_type =? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, NeoBankEnvironment.getBitcoinCode());
			pstmt.setString(2, "DA");
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
			 	while(rs.next()){	 
			 		tdaBTCAddress = StringUtils.trim(rs.getString("public_key"));
			 	} 
			 } 
		}catch(Exception e) {
			tdaBTCAddress = null;
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
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
	
		return tdaBTCAddress;
	}
	
	public boolean insertBTCToBTCxSwapTxn(String relationshipNo, String walletId, 
			String payAmount, String referenceNo, 
			String txnUserCode, String customerCharges, String transactionCode, String sourceAssetCode, 
			String destinationAssetCode, String externalReference, String payType ) throws Exception {
			PreparedStatement pstmt=null;
			Connection connection = null;
			ResultSet rs=null;
			String query = null;
			boolean result = false;
			String transactionDatetime = null;
			String currencyId= NeoBankEnvironment.getUSDCurrencyId();
			String userType = "C";
			String systemreference =referenceNo;
			String senderRefNo = null;
			String receiverRefNo = null;
			String totalAccruedBalance=null;
			try {	
				 connection = super.getConnection();
				 connection.setAutoCommit(false);

				String customerChargesValue = customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|"));
				String customerPayType = customerCharges.substring(0, customerCharges.indexOf(","));

				payAmount = payAmount.replaceAll(",", "");
				transactionDatetime = Utilities.getMYSQLCurrentTimeStampForInsert();

				  
				
				 double debitAmount =   Double.parseDouble(customerChargesValue);
				 //*******Step 1: Update the wallet Ledger
			     query = " update wallet_details set currbal= currbal - ?, lastupdated = ? where  walletid=? ";
				 pstmt = connection.prepareStatement(query);
				 pstmt.setBigDecimal(1,BigDecimal.valueOf(debitAmount));
				 pstmt.setString(2, transactionDatetime); 
				 pstmt.setString(3, walletId); 
				 try {
					 NeoBankEnvironment.setComment(3,className,"update  wallet amount" + debitAmount );
					 pstmt.executeUpdate();
					 }catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
				 }					
				 if(pstmt!=null)pstmt.close();
				 
				//*******Step 2: Update Txn Exchange Table
				 //                                          1      2         3            4          5                   6                   7                    8              9            10       11      
				 query = " insert into txn_btc_exchange (txncode, paytype, custrelno,  source_asset, source_amount, destination_amount, destination_asset, sysreference_ext, sysreference_int, status, txndate  ) "
							+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
								//		1  2  3  4  5  6  7  8  9  10 11 
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, (transactionCode)); 
				pstmt.setString(2, ( payType)); 
				pstmt.setString(3, ( relationshipNo)); 				
				pstmt.setString(4, (sourceAssetCode)); 
				pstmt.setString(5, (payAmount)); 
				pstmt.setString(6, (payAmount)); 
				pstmt.setString(7, (destinationAssetCode)); 
				pstmt.setString(8, (externalReference)); 
				pstmt.setString(9, (systemreference)); 
				pstmt.setString(10, ("N")); 
				pstmt.setString(11,  Utilities.getMYSQLCurrentTimeStampForInsert());
				try {
					pstmt.executeUpdate();
				}catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
				}					
				if(pstmt!=null)pstmt.close();
				
				 //*******Step 3: Update the wallet Ledger
				if(Double.parseDouble(customerChargesValue)>0) {
					query = "insert into txn_wallet_cust_bc (txncode, walletid, sysreference, txnamount, txncurrencyid, txnmode, txndatetime, txnusercode, pymtchannel) "
							+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?) ";
									// 1  2  3  4  5  6   7  8
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, transactionCode); 			
					pstmt.setString(2, walletId); 				
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
						
					if(pstmt!=null) pstmt.close();	
									
					
					//Select the balance to be updated. 
					query = " select accrued_balance, sequenceid from txn_business_ledger_bc order by sequenceid desc limit 1 ";
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
					pstmt.setString(1, transactionCode); 
					pstmt.setString(2, payType);
					pstmt.setString(3, walletId);
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
					 
				// call the Blockchain method here and pass the values within the method. Here we are inserting in the walletledger stream of Blockchain having chainame ppwallet
				// inserting block data for sender
				 
				if(Double.parseDouble(customerChargesValue)>0) {
					if(	CustomerWalletDao.insertGenericWalletTransactions(transactionCode, walletId,txnUserCode, systemreference+"-AC", customerChargesValue, currencyId,customerPayType, transactionDatetime ,userType) == false) {
						throw new Exception ("Blockchain insert issue");
					}
				}
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
					if (rs != null)	rs.close(); if (pstmt != null) pstmt.close();if(transactionDatetime!=null)  transactionDatetime=null; if(currencyId!=null)  currencyId=null; 
					if(userType!=null)  userType=null; if(receiverRefNo!=null)  receiverRefNo=null; if(txnUserCode != null) txnUserCode = null;
					if(systemreference!=null)  systemreference=null; if(senderRefNo!=null)  senderRefNo=null; if ( totalAccruedBalance!=null) totalAccruedBalance=null;
			
				}
			
			return result;
	}
	
	public List<Transaction> getPendingBTCToBTCxSwapping(String realationshipNo)  throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		Transaction transaction = null;
		List<Transaction> transactionList=null;
		try{
			
			connection = super.getConnection();	
			query = " select txncode,  paytype,  custrelno,  source_asset, "
					+ " source_amount,  destination_amount, destination_asset, sysreference_ext, "
					+ " sysreference_int, txndate from txn_btc_exchange "
					+ " where custrelno = ? and status = ? and source_asset=? and destination_asset=? order by txndate desc ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, realationshipNo);
			pstmt.setString(2, "N");
			pstmt.setString(3, NeoBankEnvironment.getBitcoinCode());
			pstmt.setString(4, NeoBankEnvironment.getStellarBTCxCode());
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 transactionList = new ArrayList<Transaction>();
				 	while(rs.next()){	
				 		transaction = new Transaction();
				 		transaction.setTxnDateTime(Utilities.displayDateFormat( StringUtils.trim(rs.getString("txndate")), "dd MMM yy HH:mm a"));
				 		transaction.setSourceAssetCode(StringUtils.trim(rs.getString("source_asset")));
				 		transaction.setTxnCode(StringUtils.trim(rs.getString("txncode")));
				 		transaction.setSourceAmount(StringUtils.trim(rs.getString("source_amount")));
				 		transaction.setDestinationAmount(StringUtils.trim(rs.getString("destination_amount")));
				 		transaction.setDestinationAssetCode(StringUtils.trim(rs.getString("destination_asset")));
				 		transaction.setSystemReferenceExt(StringUtils.trim(rs.getString("sysreference_ext")));
				 		transaction.setSystemReferenceInt(StringUtils.trim(rs.getString("sysreference_int")));
				 		transaction.setRelationshipNo(StringUtils.trim(rs.getString("custrelno")));
				 		transactionList.add(transaction);
				 		//NeoBankEnvironment.setComment(3, className, "TXN amount is "+transaction.getSourceAmount());
				 		//NeoBankEnvironment.setComment(3, className, "TXN amount is "+transaction.getDestinationAmount());
				 	}
			 }
				//NeoBankEnvironment.setComment(3,className,"listTxn size is "+transactionList.size());

			 if(transactionList!=null)
				 if(transactionList.size()==0)
					 transactionList=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());			
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

	
	public String getmnemonicCode(String relationshipNo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String mnemonicCode = null;
		try {
		
			connection = super.getConnection();	                    
			query = " select mnemonic_code from mnemonic_cust_relation_bc where relationshipno = ? and network=? ";
			
			//NeoBankEnvironment.setComment(3,className,"After query");

			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, "B");
			
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){
			 	while(rs.next()){	
			 		mnemonicCode=Utilities.tripleDecryptData(( StringUtils.trim(rs.getString("mnemonic_code"))));
			 		//mnemonicCode = Utilities.decryptString( StringUtils.trim(rs.getString("mnemonic_code")));
			 		} 
			 	//NeoBankEnvironment.setComment(3, className, "Mnemonic from db is "+mnemonicCode);
			 } 
	
		}catch(Exception e) {
			mnemonicCode = null;
			NeoBankEnvironment.setComment(1,className,"The exception in method getmnemonicCode  is  "+e.getMessage());		
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
	
		return mnemonicCode;
	}
	
	public String getBTCAddress(String relationshipNo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String btcAddress = null;
		try {
			
			connection = super.getConnection();	  			
			query = " select btc_address, public_key from bitcoin_account_relation where relationshipno = ? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
			 	while(rs.next()){	 
			 		btcAddress = Utilities.tripleDecryptData(StringUtils.trim(rs.getString("btc_address")));
			 	} 
			 } 
			
		}catch(Exception e) {
			btcAddress = null;
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			//throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
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
	
		return btcAddress;
	}

		public boolean checkIfPartnerStellarHasBeenLinkedByCustomer(String partnerUser)  throws Exception{
			NeoBankEnvironment.setComment(3, className, "partnerUser "+partnerUser);
			PreparedStatement pstmt=null;
			Connection connection = null;
			ResultSet rs=null;
			String query = null;
			String stellarPublicKey = null;
			//PPWalletEnvironment.setComment(3,className,"emailId  is  "+emailId);
			boolean result = false;
			try{				
				connection = super.getConnection();
				query = " select public_key from stellar_partner_account_relation where userid = ? ";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, partnerUser);
				rs = (ResultSet)pstmt.executeQuery();
				if(rs!=null){
					while(rs.next()){	
						stellarPublicKey = StringUtils.trim(rs.getString("public_key"));
					 	} // end of while
					 } //end of if
				
				 if(stellarPublicKey!=null ) {
					 result = true;
				 }
				// NeoBankEnvironment.setComment(3, className, "result in checkIfBitcoinHasBeenLinkedByCustomer is "+result);
			}catch(Exception e){
				result = false;
				NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			}finally{
				if(connection!=null)
					try {
						super.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if(rs!=null) rs.close();
					if(pstmt!=null) pstmt.close(); if (stellarPublicKey!=null)stellarPublicKey=null;
				}
			
			return result;
		}
		
		public double getConversionInUSD(String assetCode, String amount) throws Exception {
			PreparedStatement pstmt=null;
			Connection connection = null;
			ResultSet rs=null;
			String query = null;
			String sellRate = null;
			String onramptMarkupRate=null;
			double onramptMarkupRateDB=0;
			double amountInUSD = 0;
			try {
				connection = super.getConnection();	                    
				query = " select gecko_rate from asset_pricing where assetcode= ? and status = ? ";
				
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, assetCode);
				pstmt.setString(2, "A");
				
				rs = (ResultSet)pstmt.executeQuery();
				
				 if(rs!=null){
				 	while(rs.next()){	 
				 		sellRate = StringUtils.trim(rs.getString("gecko_rate"));
				 		} 
				 } 
				 if(pstmt!=null) pstmt.close();
				 
				 query = " select onramp_markup_rate from asset_pricing_markup_rate where asset_code= ? and status = ? ";
					
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, assetCode);
					pstmt.setString(2, "A");
					
					rs = (ResultSet)pstmt.executeQuery();
					
					 if(rs!=null){
					 	while(rs.next()){	 
					 		onramptMarkupRate = StringUtils.trim(rs.getString("onramp_markup_rate"));
					 		} 
					 } 
				 
				 if(pstmt!=null) pstmt.close();
				 if(onramptMarkupRate !=null) {
					 onramptMarkupRateDB= Double.parseDouble(onramptMarkupRate);
				 }else {
					 onramptMarkupRateDB = 1;
				 }
				 NeoBankEnvironment.setComment(3,className,"sellRate "+sellRate+" onramptMarkupRate "+onramptMarkupRate);
				 if(sellRate!= null ) {
					 amountInUSD = (Double.parseDouble(amount))* (Double.parseDouble(sellRate)*(onramptMarkupRateDB));
				 }
		
			}catch(Exception e) {
				amountInUSD = 0;
				NeoBankEnvironment.setComment(1,className,"The exception in method getConversionInUSD  is  "+e.getMessage());		
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
		
			return amountInUSD;
		}
		public List<Transaction> getPendingMobileBTCxTransactions(String realationshipNo)  throws Exception {
			PreparedStatement pstmt=null;
			Connection connection = null;
			ResultSet rs=null;
			String query = null;
			Transaction transaction = null;
			List<Transaction> transactionList=null;
			try{
				
				connection = super.getConnection();	
				query = " select txn_code,  walletid,  custrelno,  asset_code, "
						+ " coin_amount,  sysreference_int, txndate, status from txn_buy_coins "
						+ " where custrelno = ? and status = ? and asset_code=? order by txndate desc ";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, realationshipNo);
				pstmt.setString(2, "N");
				pstmt.setString(3, "BTC");
				rs = (ResultSet)pstmt.executeQuery();
				 if(rs!=null){
					 transactionList = new ArrayList<Transaction>();
					 	while(rs.next()){	
					 		transaction = new Transaction();
					 		transaction.setTxnDateTime(Utilities.displayDateFormat( StringUtils.trim(rs.getString("txndate")), "dd MMM yy HH:mm a"));
					 		transaction.setTxnCode(StringUtils.trim(rs.getString("txn_code")));
					 		transaction.setCustomerWalletId(StringUtils.trim(rs.getString("walletid")));
					 		transaction.setRelationshipNo(StringUtils.trim(rs.getString("custrelno")));
					 		transaction.setAssetCode(StringUtils.trim(rs.getString("asset_code")));
					 		transaction.  setCoinAmount(StringUtils.trim(new BigDecimal(rs.getString("coin_amount").toString()).toPlainString()));
					 		transaction.setStatus(StringUtils.trim(rs.getString("status")));
					 		transactionList.add(transaction);
					 	}
				 }
				 if(transactionList!=null)
					 if(transactionList.size()==0)
						 transactionList=null;
			}catch(Exception e){
				NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
				throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
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

}
