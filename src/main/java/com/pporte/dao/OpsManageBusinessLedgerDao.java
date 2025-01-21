package com.pporte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.pporte.NeoBankEnvironment;
import com.pporte.model.AssetTransaction;
import com.pporte.model.AuditTrail;
import com.pporte.utilities.Utilities;

public class OpsManageBusinessLedgerDao extends HandleConnections{
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 2L;
	private static String className = OpsManageBusinessLedgerDao.class.getSimpleName();


	public List<AssetTransaction> getBusinessLedgerTxn() throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		AssetTransaction transaction = null;
		List<AssetTransaction> transactionList=null;
		try {
			
			connection = super.getConnection();
			query="select txncode, paytype,custwalletid,pymtchannel,accrued_balance,sysreference,txnamount "
					+ " ,txnmode,txndatetime from txn_business_ledger_bc";
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				 transactionList = new ArrayList<AssetTransaction>();
				 	while(rs.next()){	
				 		transaction = new AssetTransaction();
				 		transaction.setTxnCode(StringUtils.trim(rs.getString("txncode")));
				 		transaction.setPayType(StringUtils.trim(rs.getString("paytype")));
				 		transaction.setCustomerWalletId(StringUtils.trim(rs.getString("custwalletid")));
				 		transaction.setPymtChannel(StringUtils.trim(rs.getString("pymtchannel")));
				 		transaction.setAccruedBalance(StringUtils.trim(rs.getString("accrued_balance")));
				 		transaction.setSystemReferenceExt(StringUtils.trim(rs.getString("sysreference")));
				 		transaction.setTxnAmount(Utilities.getMoneyinDecimalFormat(StringUtils.trim(rs.getString("txnamount"))));
				 		transaction.setTxnMode(StringUtils.trim(rs.getString("txnmode")));
				 		transaction.setTxnDateTime(StringUtils.trim(rs.getString("txndatetime")));
				 		transactionList.add(transaction);
				 	}
			 }
			 if(transactionList!=null)
				 if(transactionList.size()==0)
					 transactionList=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getFiatWalletTxn  is  "+e.getMessage());
			throw new Exception ("The exception in method getFiatWalletTxn  is  "+e.getMessage());			
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

	public AssetTransaction getAccruedBalance() throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		AssetTransaction m_BusinessLedger=null;
		try {
			connection = super.getConnection();				
			//Total customers balance
			query ="select accrued_balance from txn_business_ledger_bc order by sequenceid desc limit 1";
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 	while(rs.next()){	 
				 		m_BusinessLedger=new AssetTransaction();
				 		m_BusinessLedger.setTotalAmount( StringUtils.trim(rs.getString("accrued_balance"))    );
				 		
				 		} // end of while
				 	} //end of if rs!=null check
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getAccruedBalance  is  "+e.getMessage());
			throw new Exception ("The exception in method getAccruedBalance  is  "+e.getMessage());		
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
		return m_BusinessLedger;
	}

	public List<AssetTransaction> getOpsBusinessTxnBtnDates(String dateFrom, String dateTo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		AssetTransaction transaction = null;
		List<AssetTransaction> transactionList=null;
		try{
			connection = super.getConnection();	

			query="select txncode, paytype,custwalletid,pymtchannel,accrued_balance,sysreference,txnamount "
					+ " ,txnmode,txndatetime from txn_business_ledger_bc where txndatetime between ? and ?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dateFrom);
			pstmt.setString(2, dateTo);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 transactionList = new ArrayList<AssetTransaction>();
				 	while(rs.next()){	
				 		transaction = new AssetTransaction();
				 		transaction.setTxnCode(StringUtils.trim(rs.getString("txncode")));
				 		transaction.setPayType(StringUtils.trim(rs.getString("paytype")));
				 		transaction.setCustomerWalletId(StringUtils.trim(rs.getString("custwalletid")));
				 		transaction.setPymtChannel(StringUtils.trim(rs.getString("pymtchannel")));
				 		transaction.setAccruedBalance(StringUtils.trim(rs.getString("accrued_balance")));
				 		transaction.setSystemReferenceExt(StringUtils.trim(rs.getString("sysreference")));
				 		transaction.setTxnAmount(Utilities.getMoneyinDecimalFormat(StringUtils.trim(rs.getString("txnamount"))));
				 		transaction.setTxnMode(StringUtils.trim(rs.getString("txnmode")));
				 		transaction.setTxnDateTime(StringUtils.trim(rs.getString("txndatetime")));
				 		transactionList.add(transaction);
				 	}
			 }
			 if(transactionList!=null)
				 if(transactionList.size()==0)
					 transactionList=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getOpsBusinessTxnBtnDates  is  "+e.getMessage());
			throw new Exception ("The exception in method getOpsBusinessTxnBtnDates  is  "+e.getMessage());			
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

	public ArrayList<AuditTrail> getAuditTrailDetails() throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<AuditTrail> arrAuditDetails = null;
		try {
			connection = super.getConnection();	

			query="select trailid,userid,usertype,modulecode,comment,trailtime from audit_trail order by trailid desc limit 1000";
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();	
			 if(rs!=null){
				 arrAuditDetails = new ArrayList<AuditTrail>(); 
				 while(rs.next()){	
					 AuditTrail m_AuditDetails=new AuditTrail(); 
					 m_AuditDetails.setTraiId( StringUtils.trim(rs.getString("trailid")));
					 m_AuditDetails.setUserId( StringUtils.trim(rs.getString("userid")));
					 m_AuditDetails.setUserType(StringUtils.trim(rs.getString("usertype")));
					 m_AuditDetails.setModuleCode(StringUtils.trim(rs.getString("modulecode")));
					 m_AuditDetails.setComment(StringUtils.trim(rs.getString("comment")));
					 m_AuditDetails.setTrailTime(StringUtils.trim(rs.getString("trailtime")));
					 arrAuditDetails.add(m_AuditDetails);
				 }
			 }
			 if(arrAuditDetails!=null)
				 if(arrAuditDetails.size()==0) 
					 arrAuditDetails=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getAuditTrailDetails  is  "+e.getMessage());
			throw new Exception ("The exception in method getAuditTrailDetails  is  "+e.getMessage());			
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
		return arrAuditDetails;
	}

	public List<AuditTrail> getFilteredAuditTrailDetails(String dateFrom, String dateTo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<AuditTrail> arrAuditDetails = null;
		try{
			connection = super.getConnection();	
			query="select trailid,userid,usertype,modulecode,comment,trailtime from audit_trail where trailtime between ? and ? order by trailid desc limit 1000 ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dateFrom);
			pstmt.setString(2, dateTo);
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				 arrAuditDetails = new ArrayList<AuditTrail>(); 
				 while(rs.next()){	
					 AuditTrail m_AuditDetails=new AuditTrail(); 
					 m_AuditDetails.setTraiId( StringUtils.trim(rs.getString("trailid")));
					 m_AuditDetails.setUserId( StringUtils.trim(rs.getString("userid")));
					 m_AuditDetails.setUserType(StringUtils.trim(rs.getString("usertype")));
					 m_AuditDetails.setModuleCode(StringUtils.trim(rs.getString("modulecode")));
					 m_AuditDetails.setComment(StringUtils.trim(rs.getString("comment")));
					 m_AuditDetails.setTrailTime(StringUtils.trim(rs.getString("trailtime")));
					 arrAuditDetails.add(m_AuditDetails);
				 }
			 }
			if(arrAuditDetails!=null)
				 if(arrAuditDetails.size()==0) 
					 arrAuditDetails=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getFilteredAuditTrailDetails  is  "+e.getMessage());
			throw new Exception ("The exception in method getFilteredAuditTrailDetails  is  "+e.getMessage());			
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
		return arrAuditDetails;
	}
}
