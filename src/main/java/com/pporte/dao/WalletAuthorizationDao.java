package com.pporte.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import com.pporte.NeoBankEnvironment;
import com.pporte.model.TransactionLimitDetails;
import com.pporte.utilities.Utilities;


public class WalletAuthorizationDao extends HandleConnections{
	
private String className=WalletAuthorizationDao.class.getSimpleName();
	
	public String performWalletAuthorization(String relationshipNo, String transactionAmount, String txnMode) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String tempBlockCodeId = ""; String tempCurrentBal ="";  String tempWalletId = "";
		String tempAuthStatus = ""; String authStatus=null;  String tempStatus=null; String tempBlockCodeDesc="";
		String authMessage=""; String authResponse="" ; String custWalletSpentInaDay="";String  custCurrentBalance ="";
		ArrayList<TransactionLimitDetails> arrTxnLimitDetails = null;
		try {
			// THIS METHODS HANDLES ALL THE AUTHORIZATION REQUIRED FOR WALLET, IT CHECKS ON THE BLOCK CODE ON THE WALLETS, WHETHER THE WALLET IS ACTIVE OR NOT
			// WALLET BALANCE AND TRANSACTION LIMITS
			
			connection = super.getConnection();	
			// If checks are all met, authorization is successful else the auth status will be changed and error thrown  
			 authStatus="S";
			 authMessage="Authorization successful";
			
			query = "select currbal,  walletid, status,blockcodeid from wallet_details where relationshipno=? and wallettype=?";
		
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, "F");// Fiat
			rs = (ResultSet)pstmt.executeQuery();
		
		 if(rs!=null){
			 while(rs.next()){	
				 tempCurrentBal =  StringUtils.trim(rs.getString("currbal"));
				 tempBlockCodeId =  StringUtils.trim(rs.getString("blockcodeid"));
				 tempWalletId =  StringUtils.trim(rs.getString("walletid"));
				 tempStatus =  StringUtils.trim(rs.getString("status"));
			 		} // end of while
			 	} //end of if rs!=null check
		 pstmt.close(); rs.close();
			
		 query="select blockcode_id, authaction,blockcode_desc from wallet_block_codes where blockcode_id =?";
		 
		 pstmt=connection.prepareStatement(query);
		 pstmt.setString(1, tempBlockCodeId);
		 rs = (ResultSet)pstmt.executeQuery();
		 if (rs!=null) {
			 while(rs.next()) {
				 tempAuthStatus=StringUtils.trim(rs.getString("authaction"));
				 tempBlockCodeDesc=StringUtils.trim(rs.getString("blockcode_desc"));
			 }
		 }
		 
		 if(tempStatus.equals("I")) {
			 // Check if wallet is inactive
			 authStatus="F";
			 authMessage="Wallet inactive and cannot transact. Please contact our team to resolve the issue";
			 authResponse=authStatus+","+authMessage+"|"+tempWalletId;
			 throw new Exception (authMessage);
		 }
		 if(tempAuthStatus.equals("F")) {
			 // Check if wallet has a block code
			 authStatus="F";
			 authMessage="Wallet has been blocked and cannot transact. Please contact our team to resolve the issue";
			 authResponse=authStatus+","+authMessage+"|"+tempWalletId;
			 throw new Exception (authMessage);
		 }
		 if (txnMode.equals("D")) {
			 // Debit
			 //Check if balance is sufficient
			 if(Double.parseDouble(transactionAmount)> Double.parseDouble(tempCurrentBal)) {
				 authStatus="F";
				 authMessage="Insufficient funds";
				 authResponse=authStatus+","+authMessage+"|"+tempWalletId;
				 throw new Exception (authMessage);
			 }
		 }
		 // Do limit checks
		 
		 //  Get the sum of transactions done in a day
			
		 String timeAtMidnight =(Utilities.getCurrentDateAtMidnight());
		 String timeNow=(Utilities.getMYSQLCurrentTimeStampForInsert());  
		 
		 query = "select IFNULL(( select SUM(txnamount) txnamount  from txn_wallet_cust_bc where date(txndatetime) between  date(?) and date(?) and walletid = ? and txnmode=? and pymtchannel=? ),'0') as txnamount ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, timeNow);
			pstmt.setString(2, timeAtMidnight);
			pstmt.setString(3, tempWalletId);
			pstmt.setString(4, "D");// We will check the sum of debited transactions not credited 
			pstmt.setString(5, "W");// Payment Channel is W
			
			rs = (ResultSet) pstmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					custWalletSpentInaDay = (StringUtils.trim(rs.getString("txnamount")));
				
				} // end of while
			} // end of if rs!=null check
			pstmt.close(); if( rs!=null)	rs.close();
		 
			double custNewBalance = Double.parseDouble(tempCurrentBal) + Double.parseDouble(transactionAmount);
			double custNewTotalDaySpent=  Double.parseDouble(custWalletSpentInaDay) + Double.parseDouble(transactionAmount);
			
			 query = "select txn_limit_id,limit_type, limit_description, limit_amount, usertype, status, "
						+ " createdon from system_txn_limit order by createdon desc ";
	
				pstmt = connection.prepareStatement(query);
				rs = (ResultSet)pstmt.executeQuery();			
				 if(rs!=null){
					 arrTxnLimitDetails = new ArrayList<TransactionLimitDetails>();
					 	while(rs.next()){	 
					 		TransactionLimitDetails m_txtLimitDetails=new TransactionLimitDetails();
					 		m_txtLimitDetails.setTxnLimitId( StringUtils.trim(rs.getString("txn_limit_id"))    );
					 		m_txtLimitDetails.setLimitType( StringUtils.trim(rs.getString("limit_type"))    );
					 		m_txtLimitDetails.setLimitDescription( StringUtils.trim(rs.getString("limit_description"))    );
					 		m_txtLimitDetails.setLimitAmount( StringUtils.trim(rs.getString("limit_amount"))  );
					 		m_txtLimitDetails.setStatus(StringUtils.trim(rs.getString("status"))  );
					 		m_txtLimitDetails.setCreatedOn( StringUtils.trim(rs.getString("createdon"))  );
					 		m_txtLimitDetails.setUserType( StringUtils.trim(rs.getString("usertype"))  );
					 		
					 		arrTxnLimitDetails.add(m_txtLimitDetails);
					 		} // end of while
					 	
					 	} //end of if rs!=null check
	
				 // NOTE:- When a new type of limit is added, you have to code the logic of checking the limit
			
				 if (arrTxnLimitDetails.size()>0) {
					 int count =arrTxnLimitDetails.size();
					 for (int i= 0; i<count;i++) {
						 //Checking Minimum Transaction Amount
						 if (((TransactionLimitDetails) arrTxnLimitDetails.get(i)).getLimitType().equals("MTA")) {
							 if (Double.parseDouble(transactionAmount)<Double.parseDouble(((TransactionLimitDetails) arrTxnLimitDetails.get(i)).getLimitAmount())  && txnMode.equals("C")) {
								 authMessage="Minimum Transaction Amount allowed is "+((TransactionLimitDetails) arrTxnLimitDetails.get(i)).getLimitAmount();
								 authStatus="F";
								 authResponse=authStatus+","+authMessage+"|"+tempWalletId;
								 throw new Exception ("Minimum Transaction Amount Attained");
							 }
						 }
						//Checking Limit Per Transaction
						 if (((TransactionLimitDetails) arrTxnLimitDetails.get(i)).getLimitType().equals("LPT")) {
							 if (Double.parseDouble(transactionAmount)>Double.parseDouble(((TransactionLimitDetails) arrTxnLimitDetails.get(i)).getLimitAmount()) ) {
								 authMessage="Limit Per Transaction Attained";
								 authStatus="F";
								 authResponse=authStatus+","+authMessage+"|"+tempWalletId;
								 throw new Exception (authMessage);
							 }
						 }
						 //Checking Daily Transaction Limit
						 if (((TransactionLimitDetails) arrTxnLimitDetails.get(i)).getLimitType().equals("DTL")) {
							 if (custNewTotalDaySpent>Double.parseDouble(((TransactionLimitDetails) arrTxnLimitDetails.get(i)).getLimitAmount())  && txnMode.equals("D") ) {
								 authMessage="Daily Limit Attained";
								 authStatus="F";
								 authResponse=authStatus+","+authMessage+"|"+tempWalletId;
								  throw new Exception ("Daily Limit Attained");  
							  }
						 }
						 // Checking Wallet limit
						 if (((TransactionLimitDetails) arrTxnLimitDetails.get(i)).getLimitType().equals("WL")) {
							 if (custNewBalance>Double.parseDouble(((TransactionLimitDetails) arrTxnLimitDetails.get(i)).getLimitAmount())  && txnMode.equals("C")) {
								 authMessage="Wallet Limit Attained";
								 authStatus="F";
								 authResponse=authStatus+","+authMessage+"|"+tempWalletId;
								throw new Exception ("Wallet Limit Attained");
								 
							 }
						 }
					 }
				 }
			 
			authResponse=authStatus+","+authMessage+"|"+tempWalletId;
			NeoBankEnvironment.setComment(3,className,"Auth Message is :- "+authMessage);
		}catch (Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			//throw new Exception ("The exception in method performWalletAuthorization  is  "+e.getMessage());	
			
		}finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if (tempBlockCodeId!=null)tempBlockCodeId=null; if (tempCurrentBal!=null)tempCurrentBal=null; if (tempWalletId!=null)tempWalletId=null;
				if (tempAuthStatus!=null)tempAuthStatus=null; if (authStatus!=null)authStatus=null; if (tempStatus!=null)tempStatus=null;
				if (tempBlockCodeDesc!=null)tempBlockCodeDesc=null; if (custWalletSpentInaDay!=null)custWalletSpentInaDay=null;
				if (custCurrentBalance!=null)custCurrentBalance=null; if (arrTxnLimitDetails!=null) arrTxnLimitDetails=null;if(authMessage!=null)authMessage=null;
			}
		return authResponse;
	}
	
	
	public boolean recordWalletAuthorizationResult(String relationshipNo, String transactionAmount, String txnMode,String currencyId, String userType,
			String referenceNo, String walletId, String authStatus, String authMessage) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection=null;
		String query=null;
		boolean result=false;
		
		try {
			connection=super.getConnection();
			connection.setAutoCommit(false);
			
			query = "insert into wallet_authorization_details (relationshipno, customertype, walletid, authamount, authcurrency,  sysreference, authstatus,authdate, reason ) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
					//		   1  2  3  4	5  6 7  8  9 
			pstmt = connection.prepareStatement(query);

			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, userType);
			pstmt.setString(3, walletId);
			pstmt.setBigDecimal(4, new BigDecimal(transactionAmount));
			pstmt.setString(5, currencyId);
			pstmt.setString(6, referenceNo);
			pstmt.setString(7, authStatus);
			pstmt.setString(8, Utilities.getMYSQLCurrentTimeStampForInsert());
			pstmt.setString(9, authMessage);
			try {
				pstmt.executeUpdate();
			} catch (Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}
			connection.commit();	
			result=true;
		}catch (Exception e) {
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}finally {
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(pstmt!=null) pstmt.close();
		}
		return result;
	}
	
	public String getBusinessLedgerBalance() throws Exception {
		
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String businessLedgerBalance = null;
		try {
			connection = super.getConnection();	                    
			query = "select accrued_balance, sequenceid from txn_business_ledger_bc order by sequenceid desc limit 1";
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				while(rs.next()){	 			 			
					businessLedgerBalance= ( StringUtils.trim(rs.getString("accrued_balance"))  );
					} // end of while
				NeoBankEnvironment.setComment(3,className,"step 1   totalLoyaltyBalance is  "+businessLedgerBalance   );
			} 
			
		}catch(Exception e) {
			businessLedgerBalance = null;
			NeoBankEnvironment.setComment(1,className,"The exception in method getBusinessLedgerBalance  is  "+e.getMessage());		
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
		return businessLedgerBalance;
	}
}
