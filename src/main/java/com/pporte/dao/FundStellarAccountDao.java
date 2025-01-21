package com.pporte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import com.pporte.NeoBankEnvironment;
import com.pporte.model.StellarAccount;
import com.pporte.utilities.Utilities;

public class FundStellarAccountDao extends HandleConnections{
	public static String className = FundStellarAccountDao.class.getName();

	public List<StellarAccount> getAllUnfundedAccounts() throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		StellarAccount m_unfundedAccounts = null;
		List<StellarAccount> unfundedAccounts = null;
		try {
			connection = super.getConnection();	
			query = "select a.customername customername, b.public_key public_key, b.status status,"
					+ "b.createdon createdon from customer_details a, stellar_account_relation b where a.relationshipno = b.relationshipno and b.status=?";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "P");
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 unfundedAccounts = new ArrayList<StellarAccount>();
				 	while(rs.next()){	
				 		
				 		m_unfundedAccounts = new StellarAccount();
				 		
				 		m_unfundedAccounts.setCustomerName(StringUtils.trim(rs.getString("customername")));
				 		m_unfundedAccounts.setPublicKey(Utilities.tripleDecryptData( StringUtils.trim(rs.getString("public_key"))));
				 		//m_fundedAccounts.setPublicKey(Utilities.decryptString( StringUtils.trim(rs.getString("public_key"))));
				 		m_unfundedAccounts.setStatus(StringUtils.trim(rs.getString("status")));
				 		m_unfundedAccounts.setCreatedOn(Utilities.getMySQLDateTimeConvertor(StringUtils.trim(rs.getString("createdon"))));
				 		unfundedAccounts.add(m_unfundedAccounts);
				 	}
			 }
			 if(unfundedAccounts!=null)
				 if(unfundedAccounts.size()==0)
					 unfundedAccounts=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllUnfundedAccounts  is  "+e.getMessage());
			throw new Exception ("The exception in method getAllUnfundedAccounts  is  "+e.getMessage());			
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
		return unfundedAccounts;
	}

	public boolean updateStellarAccRel(String status, String publicKey, String amount) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		
		NeoBankEnvironment.setComment(3,className,"status  is  "+status);
		
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 
			 query = "update stellar_account_relation set  status=?,stellar_amount=? where public_key=?";
			 pstmt = connection.prepareStatement(query);
			pstmt.setString(1, status);
			pstmt.setString(2, amount);
			pstmt.setString(3, Utilities.tripleEncryptData(publicKey));
				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}
				connection.commit();
				result = true;
		}catch(Exception e){
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
	

	public List<StellarAccount> getAllfundedAccounts() throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		StellarAccount m_fundedAccounts = null;
		List<StellarAccount> fundedAccounts = null;
		try {
			connection = super.getConnection();	
			query = "select a.customername customername, b.public_key public_key, b.status status,"
					+ "b.createdon createdon from customer_details a, stellar_account_relation b where a.relationshipno = b.relationshipno and b.status=?";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "A");
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 fundedAccounts = new ArrayList<StellarAccount>();
				 	while(rs.next()){	
				 		m_fundedAccounts = new StellarAccount();
				 		
				 		m_fundedAccounts.setCustomerName(StringUtils.trim(rs.getString("customername")));
				 		m_fundedAccounts.setPublicKey(Utilities.tripleDecryptData( StringUtils.trim(rs.getString("public_key"))));
				 		m_fundedAccounts.setStatus(StringUtils.trim(rs.getString("status")));
				 		m_fundedAccounts.setCreatedOn(Utilities.getMySQLDateTimeConvertor(StringUtils.trim(rs.getString("createdon"))));
				 		fundedAccounts.add(m_fundedAccounts);
				 	}
			 }
			 if(fundedAccounts!=null)
				 if(fundedAccounts.size()==0)
					 fundedAccounts=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllfundedAccounts  is  "+e.getMessage());
			throw new Exception ("The exception in method getAllfundedAccounts  is  "+e.getMessage());			
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
		return fundedAccounts;
	}

	public String getMnemonicKey(String relationshipNo) throws Exception {
		NeoBankEnvironment.setComment(3,className," Inside getMnemonicKey method");
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String nmemonicKey= null;
		
		try {
			connection = super.getConnection();	                    
			query = " select mnemonic_code from mnemonic_cust_relation_bc where relationshipno = ? ";
			
			NeoBankEnvironment.setComment(3,className,"After query");

			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){
			 	while(rs.next()){	 
			 		nmemonicKey = Utilities.decryptString(StringUtils.trim(rs.getString("mnemonic_code")));
			 		} 
			 } 
	
		} catch (Exception e) {
			nmemonicKey = null;
			NeoBankEnvironment.setComment(1,className,"The exception in method getMnemonicKey  is  "+e.getMessage());	
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
		return nmemonicKey;
	}

	public String getPlanId(String relationshipNo) throws Exception {
		NeoBankEnvironment.setComment(3, className, "Inside getPlanId method");
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String planId= "";
		try {
			connection = super.getConnection();
			query = " select plan_id from customer_price_plan_allocation where customerid = ? and status=? ";
			NeoBankEnvironment.setComment(3,className,"After query");
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, "A");
			
			rs = (ResultSet)pstmt.executeQuery();
			
			if(rs!=null){
			 	while(rs.next()){	 
			 		planId = StringUtils.trim(rs.getString("plan_id"));
			 		} 
			 	NeoBankEnvironment.setComment(1,className,"planId dao "+planId);	
			 }
		} catch (Exception e) {
			planId = "";
			NeoBankEnvironment.setComment(1,className,"The exception in method getPlanId  is  "+e.getMessage());	
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
		return planId;
	}

	public String getWalletBalance( String relationshipNo) throws Exception{
		NeoBankEnvironment.setComment(3, className, "Inside getWalletBalance method");
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String walletBalance= null;
		try {
			
			connection = super.getConnection();
			query = " select a.currbal currbal from wallet_details a where relationshipno = ? and wallettype=? ";
			NeoBankEnvironment.setComment(3,className,"After query");
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, "F");
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
			 	while(rs.next()){	 
			 		walletBalance=Utilities.getMoneyinDecimalFormat(StringUtils.trim(rs.getString("currbal")));
			 		} 
			 	NeoBankEnvironment.setComment(3,className,"walletBalance dao "+walletBalance);	
			 }
		} catch (Exception e) {
			walletBalance = null;
			NeoBankEnvironment.setComment(1,className,"The exception in method getWalletBalance  is  "+e.getMessage());	
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
		return walletBalance;
	}

}
