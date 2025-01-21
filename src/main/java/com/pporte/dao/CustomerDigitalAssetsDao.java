package com.pporte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.pporte.NeoBankEnvironment;
import com.pporte.model.AssetAccount;
import com.pporte.utilities.Utilities;

public class CustomerDigitalAssetsDao extends HandleConnections{
	public static String className = CustomerDigitalAssetsDao.class.getName();
	
	public boolean insertBTCExchange(String payType, String relationshipNo, String sourceAsset,
			String sourceAmount, String destinationAsset, String destinationAmount,
			String internalReference, String externalReference  ) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		String txnCode = null;
		SimpleDateFormat formatter1 = null;
		try{
			connection = super.getConnection();	
			connection.setAutoCommit(false);
			formatter1 = new SimpleDateFormat ("yyMMddHHmmssSSS");
			txnCode = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);		 
			 
			 query = " insert into txn_btc_exchange (txncode, paytype, custrelno,  source_asset, "
			 		+ " source_amount, destination_amount, destination_asset, sysreference_ext, sysreference_int,"
			 		+ " status,  txndate  ) "
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
						//		   1  2  3  4  5  7  8  9  10 11
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, (txnCode)); 
				pstmt.setString(2, ( payType)); 
				pstmt.setString(3, ( relationshipNo)); 				
				pstmt.setString(4, (sourceAsset)); 
				pstmt.setString(5, (sourceAmount)); 
				pstmt.setString(6, (destinationAmount)); 
				pstmt.setString(7, (destinationAsset)); 
				pstmt.setString(8, (externalReference)); 
				pstmt.setString(9, (internalReference)); 
				pstmt.setString(10, ("N")); 
				pstmt.setString(11,  Utilities.getMYSQLCurrentTimeStampForInsert() ); 

				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}	
			connection.commit();
			result = true;

		}catch(Exception e){
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
				if(rs!=null) rs.close(); if(formatter1!=null) formatter1= null; if(txnCode != null)txnCode = null;
				if(pstmt!=null) pstmt.close();
			}
		return result;
	}
	
	public String getExChangeRatesMarkUp(String assetCode) throws Exception{                       
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String markUpRate = null;
		try{
			connection = super.getConnection();	
			query =   " select markup_rate from porte_assets_to_btcx_markup where asset_code=? and status=? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, assetCode);
			pstmt.setString(2, "A");
			rs = (ResultSet)pstmt.executeQuery();
	
			 if(rs!=null){
			 	while(rs.next()){	
			 		markUpRate = StringUtils.trim(rs.getString("markup_rate"));
			 		
			 	}
			 }	
				
		}catch(Exception e){
			markUpRate = null;
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
		return markUpRate;
	}
	
	public String getAssetDistributionAccount(String assetCode) throws Exception{                       
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String distributionAccount = null;
		try{
			connection = super.getConnection();	
			query =   " select public_key from wallet_assets_account where asset_code=? and account_type=? and  status=? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, assetCode);
			pstmt.setString(2, "DA");
			pstmt.setString(3, "A");
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
			 	while(rs.next()){	
			 		distributionAccount = StringUtils.trim(rs.getString("public_key"));
			 	}
			 }	
				
		}catch(Exception e){
			distributionAccount = null;
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
		return distributionAccount;
	}
	
		
	public String getDistributionAccountPublicKey(String assetCode) throws Exception{                       
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String publicKey = null;
		try{			
			connection = super.getConnection();	
			query =   " select public_key from wallet_assets_account where asset_code=? and status=? and account_type=? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, assetCode);
			pstmt.setString(2, "A");
			pstmt.setString(3, "DA");
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
			 	while(rs.next()){	
			 		publicKey = StringUtils.trim(rs.getString("public_key"));
			 	}
			 }	
				
		}catch(Exception e){
			publicKey = null;
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
		return publicKey;
	}
	
	public String getIssueingAccountPublicKey(String assetCode) throws Exception{                       
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String publicKey = null;
		try{			
			connection = super.getConnection();	
			query =   "select public_key from wallet_assets_account where asset_code=? and status=? and account_type=? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, assetCode);
			pstmt.setString(2, "A");
			pstmt.setString(3, "IA");
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
			 	while(rs.next()){	
			 		publicKey = StringUtils.trim(rs.getString("public_key"));
			 	}
			 }	
				
		}catch(Exception e){
			publicKey = null;
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
		return publicKey;
	}
	
	public String getLiquidityAccountPublicKey(String assetCode) throws Exception{                       
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String publicKey = null;
		try{			
			connection = super.getConnection();	
			query =   " select public_key from wallet_assets_account where asset_code=? and status=? and account_type=? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, assetCode);
			pstmt.setString(2, "A");
			pstmt.setString(3, "LA");
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
			 	while(rs.next()){	
			 		publicKey = StringUtils.trim(rs.getString("public_key"));
			 	}
			 }	
				
		}catch(Exception e){
			publicKey = null;
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
		return publicKey;
	}

	public ArrayList<AssetAccount> getAllRegisteredAccounts() throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<AssetAccount> arrPrivateKeys = null;
		try{
			connection = super.getConnection();	
			
			
			query="select private_key from fund_new_accounts_automation_ac";
 			
 			pstmt = connection.prepareStatement(query);
			//pstmt.setString(1, "P");
			rs = (ResultSet)pstmt.executeQuery();			
			 if(rs!=null){
				 arrPrivateKeys = new ArrayList<AssetAccount>();
				 	while(rs.next()){	 
				 		AssetAccount m_StellarKey=new AssetAccount();
				 		m_StellarKey.setPublicKey(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("private_key"))));
				 		
				 		arrPrivateKeys.add(m_StellarKey);
				 		} // end of while
				 	
				 	} //end of if rs!=null check
			 if(arrPrivateKeys!=null)
				 if(arrPrivateKeys.size()==0) 
					 arrPrivateKeys=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllRegisteredAccounts  is  "+e.getMessage());
			throw new Exception ("The exception in method getAllRegisteredAccounts  is  "+e.getMessage());			
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
		return arrPrivateKeys;
	}
	
		public ArrayList<AssetAccount> getAllAccounts() throws Exception {
			PreparedStatement pstmt=null;
			Connection connection = null;
			ResultSet rs=null;
			String query = null;
			ArrayList<AssetAccount> arrKeys = null;
			try{
				connection = super.getConnection();					
				query="select public_key,status,datetime from fund_new_accounts_automation_ac";
	 			pstmt = connection.prepareStatement(query);
				//pstmt.setString(1, "P");
				rs = (ResultSet)pstmt.executeQuery();			
				 if(rs!=null){
					 arrKeys = new ArrayList<AssetAccount>();
					 	while(rs.next()){	 
					 		AssetAccount m_StellarKey=new AssetAccount();
					 		m_StellarKey.setPublicKey(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("public_key"))));
					 		m_StellarKey.setStatus(StringUtils.trim(rs.getString("status")));
					 		m_StellarKey.setCreatedOn(StringUtils.trim(rs.getString("datetime")));
					 		
					 		
					 		arrKeys.add(m_StellarKey);
					 		NeoBankEnvironment.setComment(3,className,"Account is "+
					 		Utilities.tripleDecryptData(StringUtils.trim(rs.getString("public_key"))));
					 		} // end of while
					 	
					 	} //end of if rs!=null check
				 if(arrKeys!=null)
					 if(arrKeys.size()==0) 
						 arrKeys=null;
			}catch(Exception e){
				NeoBankEnvironment.setComment(1,className,"The exception in method getAllAccounts  is  "+e.getMessage());
				throw new Exception ("The exception in method getAllAccounts  is  "+e.getMessage());			
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
			return arrKeys;
		}
	
		public AssetAccount getActiveXLMAccounts() throws Exception {
				PreparedStatement pstmt=null;
				Connection connection = null;
				ResultSet rs=null;
				String query = null;
				AssetAccount m_StellarKey = null;
			try{
				connection = super.getConnection();
				query="select public_key,private_key from fund_new_accounts_automation_ac where status=? "; // Add limit to one
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, "A");
				rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				while(rs.next()){
					m_StellarKey=new AssetAccount();
					m_StellarKey.setPublicKey(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("public_key"))));
					m_StellarKey.setPrivateKey(StringUtils.trim(rs.getString("private_key")));
				} // end of while

					} //end of if rs!=null check
				}catch(Exception e){
					m_StellarKey = null;
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
				return m_StellarKey;
			}

}
