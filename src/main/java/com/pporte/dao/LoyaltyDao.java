package com.pporte.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.pporte.NeoBankEnvironment;
import com.pporte.model.Loyalty;
import com.pporte.utilities.Utilities;

public class LoyaltyDao extends HandleConnections {
	
	public static String className = LoyaltyDao.class.getName();

	public ArrayList<Loyalty> getLoyaltyTransactionsForUser(String relationshipNo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<Loyalty> arrLoyalty = null;
	
		try {
			connection = super.getConnection();	
			 
			query = "select sequenceid, walletid, paymode, txnreference, pointaccrued, status, pointbalance, txndatetime from loyalty_points_bc where "
					+ " relationshipno= ? and status =? order by txndatetime desc limit 0, 1000 ";
	
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, "U");
			rs = (ResultSet)pstmt.executeQuery();
	
			 if(rs!=null){
				 arrLoyalty = new ArrayList<Loyalty>();
				 
				 
				 	while(rs.next()){	 
					Loyalty m_Loyalty=new Loyalty();					
				 		m_Loyalty.setSequenceId(  StringUtils.trim(rs.getString("sequenceid")) );
				 		m_Loyalty.setWalletId( StringUtils.trim(rs.getString("walletid")) );
				 		m_Loyalty.setPayMode( StringUtils.trim(rs.getString("paymode")) );
				 		m_Loyalty.setTxnReference ( StringUtils.trim(rs.getString("txnreference")) );
				 		m_Loyalty.setPointAccrued( StringUtils.trim(rs.getString("pointaccrued")) );
				 		m_Loyalty.setPointBalance( StringUtils.trim(rs.getString("pointbalance")) );
				 		m_Loyalty.setTxnDate(Utilities.getDateTimeFormatInFullForDisplay( StringUtils.trim(rs.getString("txndatetime")) ));
				 		//m_Loyalty.setTxnDate( Utilities.displayDateFormat( StringUtils.trim(rs.getString("txndatetime")), "dd MMM yy HH:mm a") );
				 		m_Loyalty.setStatus( StringUtils.trim(rs.getString("status")) );
				 		arrLoyalty.add(m_Loyalty);	
				 		}
				 	
				 	} 
			 if(arrLoyalty!=null)
				 if(arrLoyalty.size()==0)
					 arrLoyalty=null;
			
		}catch(Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getLoyaltyTransactionsForUser  is  "+e.getMessage());
			throw new Exception ("The exception in method getLoyaltyTransactionsForUser  is  "+e.getMessage());			
			
		}finally {
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
			
		}
		return arrLoyalty;
	   }
	
	public ConcurrentHashMap<String, String> getLoyaltyDescription() throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ConcurrentHashMap<String, String> hashPaymode = null;
	
		try {
			connection = super.getConnection();	
			 
			query = "select  paymode, rulesdesc from loyalty_rules where status = ? ";
	
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "A");
			rs = (ResultSet)pstmt.executeQuery();
	
			 if(rs!=null){
				 hashPaymode = new ConcurrentHashMap<String, String>();
				 	while(rs.next()){	 
					Loyalty m_Loyalty=new Loyalty();					
				 		m_Loyalty.setPayMode( StringUtils.trim(rs.getString("paymode")) );
				 		m_Loyalty.setRuleDesc( StringUtils.trim(rs.getString("rulesdesc")) );
				 		//m_Loyalty.setStatus( StringUtils.trim(rs.getString("status")) );
				 		hashPaymode.put(m_Loyalty.getPayMode(), m_Loyalty.getRuleDesc());
				 		}
				 	
				 	} 
			 if(hashPaymode!=null)
				 if(hashPaymode.size()==0)
					 hashPaymode=null;
			
		}catch(Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getLoyaltyDescription  is  "+e.getMessage());
			throw new Exception ("The exception in method getLoyaltyDescription  is  "+e.getMessage());			
			
		}finally {
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
			
		}
		return hashPaymode;
	   }
	
	public String getLoyaltyPointsBalance (String relationshipNo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String pointsBalance = null;
		try{
			connection = super.getConnection();	
	
			query = " select pointbalance as pointbalance from loyalty_points_bc where relationshipno= ?"
					+ " order by txndatetime desc limit 1 " ;
					
			pstmt = connection.prepareStatement(query);			
			pstmt.setString(1, relationshipNo); 
			rs = (ResultSet)pstmt.executeQuery();
	
			 if(rs!=null){
				
				 	while(rs.next()){
				 		pointsBalance = StringUtils.trim(rs.getString("pointbalance") );
				 		
				 		} // end of while
				 	} //end of if rs!=null check
			
			 if(pointsBalance != null) {
				 if ( Double.valueOf( pointsBalance)  < 0) {
					 throw new Exception(" Not allowed:pointbalance is less than 0 ");
				 } 
				 NeoBankEnvironment.setComment(2,className,"ponts balance is  "+pointsBalance);
			 }else {
				 pointsBalance = "0";
			 }
			 
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getLoyaltyPointsBalance  is  "+e.getMessage());
			throw new Exception ("The exception in method getLoyaltyPointsBalance  is  "+e.getMessage());			
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
		return pointsBalance;
	}
	
	
	public ArrayList<Loyalty> getLoyaltyConversion () throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<Loyalty> arrPointConversion = null;
		try{
			connection = super.getConnection();	
	
			query = "select sequeno, conversion_rate, destination_assetcode,status, createdon, expiry from loyalty_redemption_rate order by status asc " ;
					
			pstmt = connection.prepareStatement(query);			
			//pstmt.setString(1, "A"); 
			rs = (ResultSet)pstmt.executeQuery();
	
			 if(rs!=null){
				 arrPointConversion = new ArrayList<Loyalty>();
				 	while(rs.next()){
				 		Loyalty m_Loyalty = new Loyalty();
				 		//m_Loyalty.setPayMode(StringUtils.trim(rs.getString("paytype"))); 	
				 		m_Loyalty.setDestinationAsset(StringUtils.trim(rs.getString("destination_assetcode"))); 			 		
				 		m_Loyalty.setConversionRate(StringUtils.trim(rs.getString("conversion_rate"))); 			 		
				 		m_Loyalty.setStatus(StringUtils.trim(rs.getString("status"))); 			 		
				 		m_Loyalty.setCreatedon(StringUtils.trim(rs.getString("createdon"))); 	
				 		m_Loyalty.setSequenceId(StringUtils.trim(rs.getString("sequeno"))); 	
				 		m_Loyalty.setExpiry(StringUtils.trim(rs.getString("expiry"))); 	
				 		arrPointConversion.add(m_Loyalty);
				 		} // end of while
				 	} //end of if rs!=null check
					 
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getLoyaltyConversion  is  "+e.getMessage());
			throw new Exception ("The exception in method getLoyaltyConversion  is  "+e.getMessage());			
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
		return arrPointConversion;
	}

	public boolean addNewLoyaltyRedeemConversion(String destAsset, String conversionRate, String status) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 
			 
			//In activate any other rates for the same destination asset
			 query = " update loyalty_redemption_rate set  status=?, expiry=? where destination_assetcode=? ";
				
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, "I"); 						 
				pstmt.setString(2, Utilities.getMYSQLCurrentTimeStampForInsert()); 						 
				pstmt.setString(3, destAsset);	
				try {
					pstmt.executeUpdate();
				    }catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
				    }
				pstmt.close();
			
			 									 //		             1		              2			   3	     4		  	   
			 query = " insert into loyalty_redemption_rate 	(destination_assetcode, conversion_rate, status, createdon ) "
							+ "values ( ?,  ?,  ?,  ? ) ";
							 //		    1   2   3   4    
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, destAsset); 					
					pstmt.setString(2, conversionRate); 					
					pstmt.setString(3, status);					
					pstmt.setString(4, Utilities.getMYSQLCurrentTimeStampForInsert());														
					try {
						pstmt.executeUpdate();
						}catch(Exception e) {
							throw new Exception (" failed query "+query+" "+e.getMessage());
						}
					connection.commit();
					result = true;
		}catch(Exception e){
			connection.rollback(); result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method addNewLoyaltyRedeemConversion  is  "+e.getMessage());
			throw new Exception ("The exception in method addNewLoyaltyRedeemConversion  is  "+e.getMessage());
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

	public boolean EditLoyaltyRedeemConversion(String destAsset, String conversionRate, String status, String seqNo) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			
			  //                                                    1          2                 3                      4             
			 query = " update loyalty_redemption_rate set conversion_rate=?, status=? where sequeno=? and destination_assetcode =? ";
			 
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, conversionRate); 					
					pstmt.setString(2, status); 					
					pstmt.setString(3, seqNo);					
					pstmt.setString(4, destAsset);														
					try {
						pstmt.executeUpdate();
						}catch(Exception e) {
							throw new Exception (" failed query "+query+" "+e.getMessage());
						}
					connection.commit();
					result = true;
		}catch(Exception e){
			connection.rollback(); result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method EditLoyaltyRedeemConversion  is  "+e.getMessage());
			throw new Exception ("The exception in method EditLoyaltyRedeemConversion  is  "+e.getMessage());
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
	public String checkTxnLoyaltyStatus(String claimedTxnRef) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		 String loyaltyPiontstatus = null;
		try{
			connection = super.getConnection();	
			query = " select status from loyalty_points_bc where txnreference = ? " ;
					
			pstmt = connection.prepareStatement(query);			
			pstmt.setString(1, claimedTxnRef); 
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 	while(rs.next()){
				 		loyaltyPiontstatus =  StringUtils.trim(rs.getString("status"));
				 		} // end of while
				 	} //end of if rs!=null check
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method checkTxnLoyaltyStatus  is  "+e.getMessage());
			throw new Exception ("The exception in method checkTxnLoyaltyStatus  is  "+e.getMessage());			
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
		return loyaltyPiontstatus;
	}

	public String getRedeemRateAndDestination() throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
	    String destinationAsset = null;
		String conversionRate = null;
		String redeemRateDestination = null;
		try{
			connection = super.getConnection();	
			query = "select sequeno, conversion_rate, destination_assetcode,status, createdon, expiry from loyalty_redemption_rate"
					+ " where status = ? order by sequeno desc limit 1" ;
					
			pstmt = connection.prepareStatement(query);			
			pstmt.setString(1, "A"); 
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 	while(rs.next()){
				 		conversionRate =  StringUtils.trim(rs.getString("conversion_rate"));
				 		destinationAsset =  StringUtils.trim(rs.getString("destination_assetcode"));
				 		} // end of while
				 	} //end of if rs!=null check
			
			 if(conversionRate !=null && destinationAsset!=null) {
				 redeemRateDestination = destinationAsset+","+conversionRate; 
			 }
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getRedeemRateAndDestination  is  "+e.getMessage());
			throw new Exception ("The exception in method getRedeemRateAndDestination  is  "+e.getMessage());			
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
		return redeemRateDestination;
	}
	
	public Boolean custPerformLoyaltyClaimForEach(String relationshipNo, String amountRedeemed, String payComments,
			String referenceNo, String txnUserCode, String customerCharges, String txnPayMode, String destinationAsset,
			String seqNo, String claimedTxnRef, String pointsBalance, String claimedWalletId) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		String transactionDatetime = null;
		String transactionCode = null;
		String userType = "C";
		String systemreference =referenceNo;
		String senderRefNo = null;
		String totalAccruedBalance=null;
		try {
			customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|"));
			//String customerPayType = customerCharges.substring(0, customerCharges.indexOf(","));
			amountRedeemed = amountRedeemed.replaceAll(",", "");
			transactionDatetime = Utilities.getMYSQLCurrentTimeStampForInsert();
			SimpleDateFormat formatter1 = new SimpleDateFormat ("yyMMddHHmmssSSS");
			transactionCode = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
			connection = super.getConnection();
			connection.setAutoCommit(false);	
			 //Update the loyalty bc
			    //                                    1                  2                 3                              
			 query = " update loyalty_points_bc set status=?  where sequenceid=? and txnreference =? ";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, "C"); //Claimed					
				pstmt.setString(2, seqNo); 					
				pstmt.setString(3, claimedTxnRef);					
				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}
				pstmt.close();
				//  Insert loyalty points for the sender user  
		
				query = "select pointbalance as pointbalance from loyalty_points_bc where relationshipno= ? "
						+ " order by txndatetime desc limit 1 ";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, relationshipNo);
				rs = (ResultSet)pstmt.executeQuery();
				if(rs!=null){
					while(rs.next()){	 			 			
						 totalAccruedBalance= ( StringUtils.trim(rs.getString("pointbalance"))  );
						} // end of while
					NeoBankEnvironment.setComment(3,className,"step 1   totalLoyaltyBalance is  "+totalAccruedBalance   );
				} 
				if(pstmt!=null) pstmt.close();	if(rs!=null) rs.close();
										
				 								//		1		2			3			   4			5			6				7          8       9
			 query = "insert into loyalty_points_bc (walletid, usertype, relationshipno, paymode, txnreference, pointaccrued, pointbalance, status,  txndatetime) "
							+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
							//		   1  2  3  4  5  6  7  8  9';
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, (claimedWalletId)); 					
					pstmt.setString(2, "C"); 		
					pstmt.setString(3, relationshipNo); // 
					pstmt.setString(4,txnPayMode );	
					pstmt.setString(5, referenceNo); 
					pstmt.setBigDecimal(6, BigDecimal.valueOf((-Double.parseDouble(pointsBalance))) );  // pointaccrued
					
					pstmt.setBigDecimal(7,  BigDecimal.valueOf( Double.parseDouble(totalAccruedBalance ) -  Double.parseDouble(pointsBalance) )    );  // pointbalance
					pstmt.setString(8,"C" );	
					pstmt.setString(9,transactionDatetime );	
		
						try {
						pstmt.executeUpdate();
						}catch(Exception e) {
							throw new Exception (" failed query "+query+" "+e.getMessage());
					}	
						
						//Insert into the queue to await signing
			//			                           1		2			3			4		  5				6			   7			   8		 9          10      11
			query = "insert into txn_buy_coins (txn_code, walletid, custrelno, asset_code, txnamount, sysreference_ext, sysreference_int, status, txndate, coin_amount, commet ) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
							// 1  2  3  4  5  6  7  8  9  10 11
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, transactionCode); 
					pstmt.setString(2, claimedWalletId);
					pstmt.setString(3, relationshipNo);
					pstmt.setString(4, destinationAsset);
					pstmt.setBigDecimal(5, BigDecimal.valueOf( Double.parseDouble(amountRedeemed)));
					pstmt.setString(6, "");								
					pstmt.setString(7, systemreference);								
					pstmt.setString(8, "N"); 
					pstmt.setString(9, transactionDatetime);
					pstmt.setString(10, amountRedeemed);
					pstmt.setString(11, payComments);
					try {
						pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query +" "+e.getMessage());
					}					
					if( pstmt!=null)pstmt.close();
	
			connection.commit();
			result = true;
			
		}catch(Exception e){
			result = false;
			connection.rollback();
			NeoBankEnvironment.setComment(1,className,"The exception in method custPerformLoyaltyClaimForEach is  "+e.getMessage());
			throw new Exception ("The exception in method custPerformLoyaltyClaimForEach  is  "+e.getMessage());
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

	public Boolean custPerformLoyaltyClaimAllPoints(String relationshipNo, String amountRedeemed, String payComments,String referenceNo,
			 String txnUserCode, String customerCharges, String txnPayMode, String destinationAsset,
			 String pointsBalance, String walletId)  throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		String transactionDatetime = null;
		String transactionCode = null;
		String userType = "C";
		String systemreference =referenceNo;
		String senderRefNo = null;
		String totalAccruedBalance=null;
		try {
			amountRedeemed = amountRedeemed.replaceAll(",", "");
			transactionDatetime = Utilities.getMYSQLCurrentTimeStampForInsert();
			SimpleDateFormat formatter1 = new SimpleDateFormat ("yyMMddHHmmssSSS");
			transactionCode = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
			connection = super.getConnection();
			connection.setAutoCommit(false);	
			 //Update the loyalty bc
			    //                                    1                  2                 3                              
			 query = " update loyalty_points_bc set status=?  where relationshipno=?  ";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, "C"); //Claimed All					
				pstmt.setString(2, relationshipNo); 					
				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}
				pstmt.close();
				//  Insert loyalty points for the sender user  
		
				query = "select pointbalance as pointbalance from loyalty_points_bc where relationshipno= ? "
						+ " order by txndatetime desc limit 1 ";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, relationshipNo);
				rs = (ResultSet)pstmt.executeQuery();
				if(rs!=null){
					while(rs.next()){	 			 			
						 totalAccruedBalance= ( StringUtils.trim(rs.getString("pointbalance"))  );
						} // end of while
					NeoBankEnvironment.setComment(3,className,"step 1   totalLoyaltyBalance is  "+totalAccruedBalance   );
				} 
				if(pstmt!=null) pstmt.close();	if(rs!=null) rs.close();
										
				 								//		1		2			3			   4			5			6				7          8       9
			 query = "insert into loyalty_points_bc (walletid, usertype, relationshipno, paymode, txnreference, pointaccrued, pointbalance, status,  txndatetime) "
							+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
							//		   1  2  3  4  5  6  7  8  9';
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, (walletId)); 					
					pstmt.setString(2, "C"); 		
					pstmt.setString(3, relationshipNo); // 
					pstmt.setString(4,txnPayMode );	
					pstmt.setString(5, referenceNo); 
					pstmt.setBigDecimal(6, BigDecimal.valueOf((-Double.parseDouble(pointsBalance))) );  // pointaccrued
					
					pstmt.setBigDecimal(7,  BigDecimal.valueOf( Double.parseDouble(totalAccruedBalance ) -  Double.parseDouble(pointsBalance) )    );  // pointbalance
					pstmt.setString(8,"C" );	
					pstmt.setString(9,transactionDatetime );	
		
						try {
						pstmt.executeUpdate();
						}catch(Exception e) {
							throw new Exception (" failed query "+query+" "+e.getMessage());
					}	
						
						//Insert into the queue to await signing
			//			                           1		2			3			4		  5				6			   7			   8		 9          10      11
			query = "insert into txn_buy_coins (txn_code, walletid, custrelno, asset_code, txnamount, sysreference_ext, sysreference_int, status, txndate, coin_amount, commet ) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
							// 1  2  3  4  5  6  7  8  9  10 11
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, transactionCode); 
					pstmt.setString(2, walletId);
					pstmt.setString(3, relationshipNo);
					pstmt.setString(4, destinationAsset);
					pstmt.setBigDecimal(5, BigDecimal.valueOf( Double.parseDouble(amountRedeemed)));
					pstmt.setString(6, "");								
					pstmt.setString(7, systemreference);								
					pstmt.setString(8, "N"); 
					pstmt.setString(9, transactionDatetime);
					pstmt.setString(10, amountRedeemed);
					pstmt.setString(11, payComments);
					try {
						pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query +" "+e.getMessage());
					}					
					if( pstmt!=null)pstmt.close();
	
			connection.commit();
			result = true;
			
		}catch(Exception e){
			result = false;
			connection.rollback();
			NeoBankEnvironment.setComment(1,className,"The exception in method custPerformLoyaltyClaimAllPoints is  "+e.getMessage());
			throw new Exception ("The exception in method custPerformLoyaltyClaimAllPoints  is  "+e.getMessage());
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

	public String getWalletIdForAsset(String destinationAsset, String relationshipNo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String walletId = null;
		try{
			connection = super.getConnection();	
			query = "select walletid from wallet_details_external where assetcode = ? and relationshipno =? and status = ?" ;
					
			pstmt = connection.prepareStatement(query);			
			pstmt.setString(1, destinationAsset); 
			pstmt.setString(2, relationshipNo); 
			pstmt.setString(3, "A"); 
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 	while(rs.next()){
				 		walletId =  StringUtils.trim(rs.getString("walletid"));
				 		} // end of while
				 	} //end of if rs!=null check
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getWalletIdForAsset  is  "+e.getMessage());
			throw new Exception ("The exception in method getWalletIdForAsset  is  "+e.getMessage());			
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
		return walletId;
	}
	public String checkMobileTxnLoyaltyStatus(String claimedTxnRef) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		 String loyaltyPiontstatus = null;
		try{
			connection = super.getConnection();	
			query = " select status from loyalty_points_bc where sequenceid = ? " ;
					
			pstmt = connection.prepareStatement(query);			
			pstmt.setString(1, claimedTxnRef); 
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 	while(rs.next()){
				 		loyaltyPiontstatus =  StringUtils.trim(rs.getString("status"));
				 		} // end of while
				 	} //end of if rs!=null check
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method checkTxnLoyaltyStatus  is  "+e.getMessage());
			throw new Exception ("The exception in method checkTxnLoyaltyStatus  is  "+e.getMessage());			
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
		return loyaltyPiontstatus;
	}
	public Boolean custMobilePerformLoyaltyClaimForEach(String relationshipNo, String amountRedeemed, String payComments,
			String referenceNo, String txnUserCode, String customerCharges, String txnPayMode, String destinationAsset,
			String seqNo,String pointsBalance, String claimedWalletId) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		String transactionDatetime = null;
		String transactionCode = null;
		String userType = "C";
		String systemreference =referenceNo;
		String senderRefNo = null;
		String totalAccruedBalance=null;
		try {
			customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|"));
			//String customerPayType = customerCharges.substring(0, customerCharges.indexOf(","));
			amountRedeemed = amountRedeemed.replaceAll(",", "");
			transactionDatetime = Utilities.getMYSQLCurrentTimeStampForInsert();
			SimpleDateFormat formatter1 = new SimpleDateFormat ("yyMMddHHmmssSSS");
			transactionCode = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
			connection = super.getConnection();
			connection.setAutoCommit(false);	
			 //Update the loyalty bc
			    //                                    1                  2                 3                              
			 query = " update loyalty_points_bc set status=?  where sequenceid=?";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, "C"); //Claimed					
				pstmt.setString(2, seqNo); 					
				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}
				pstmt.close();
				//  Insert loyalty points for the sender user  
		
				query = "select pointbalance as pointbalance from loyalty_points_bc where relationshipno= ? "
						+ " order by txndatetime desc limit 1 ";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, relationshipNo);
				rs = (ResultSet)pstmt.executeQuery();
				if(rs!=null){
					while(rs.next()){	 			 			
						 totalAccruedBalance= ( StringUtils.trim(rs.getString("pointbalance"))  );
						} // end of while
					NeoBankEnvironment.setComment(3,className,"step 1   totalLoyaltyBalance is  "+totalAccruedBalance   );
				} 
				if(pstmt!=null) pstmt.close();	if(rs!=null) rs.close();
										
				 								//		1		2			3			   4			5			6				7          8       9
			 query = "insert into loyalty_points_bc (walletid, usertype, relationshipno, paymode, txnreference, pointaccrued, pointbalance, status,  txndatetime) "
							+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
							//		   1  2  3  4  5  6  7  8  9';
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, (claimedWalletId)); 					
					pstmt.setString(2, "C"); 		
					pstmt.setString(3, relationshipNo); // 
					pstmt.setString(4,txnPayMode );	
					pstmt.setString(5, referenceNo); 
					pstmt.setBigDecimal(6, BigDecimal.valueOf((-Double.parseDouble(pointsBalance))) );  // pointaccrued
					
					pstmt.setBigDecimal(7,  BigDecimal.valueOf( Double.parseDouble(totalAccruedBalance ) -  Double.parseDouble(pointsBalance) )    );  // pointbalance
					pstmt.setString(8,"C" );	
					pstmt.setString(9,transactionDatetime );	
		
						try {
						pstmt.executeUpdate();
						}catch(Exception e) {
							throw new Exception (" failed query "+query+" "+e.getMessage());
					}	
						
						//Insert into the queue to await signing
			//			                           1		2			3			4		  5				6			   7			   8		 9          10      11
			query = "insert into txn_buy_coins (txn_code, walletid, custrelno, asset_code, txnamount, sysreference_ext, sysreference_int, status, txndate, coin_amount, commet ) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
							// 1  2  3  4  5  6  7  8  9  10 11
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, transactionCode); 
					pstmt.setString(2, claimedWalletId);
					pstmt.setString(3, relationshipNo);
					pstmt.setString(4, destinationAsset);
					pstmt.setBigDecimal(5, BigDecimal.valueOf( Double.parseDouble(amountRedeemed)));
					pstmt.setString(6, "");								
					pstmt.setString(7, systemreference);								
					pstmt.setString(8, "N"); 
					pstmt.setString(9, transactionDatetime);
					pstmt.setString(10, amountRedeemed);
					pstmt.setString(11, payComments);
					try {
						pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query +" "+e.getMessage());
					}					
					if( pstmt!=null)pstmt.close();
	
			connection.commit();
			result = true;
			
		}catch(Exception e){
			result = false;
			connection.rollback();
			NeoBankEnvironment.setComment(1,className,"The exception in method custPerformLoyaltyClaimForEach is  "+e.getMessage());
			throw new Exception ("The exception in method custPerformLoyaltyClaimForEach  is  "+e.getMessage());
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
	
	public String getTotalLoyalty(String relationshipNo) throws Exception {
		NeoBankEnvironment.setComment(3,className," Inside getTotalLoyalty method");
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String totalLoyalty = null;
		try {
			connection = super.getConnection();	                    
			query="select (select sum(pointbalance) from loyalty_points_bc where relationshipNo	= ? and status =?) as pointbalance";
			
			NeoBankEnvironment.setComment(3,className,"After query");

			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, "U");
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){
			 	while(rs.next()){	 
			 		totalLoyalty = StringUtils.trim(rs.getString("pointbalance"));
			 		} 
			 } 
	
		}catch(Exception e) {
			totalLoyalty = null;
			NeoBankEnvironment.setComment(1,className,"The exception in method getTotalLoyalty  is  "+e.getMessage());		
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
	
		return totalLoyalty;
	}

	
	
}


