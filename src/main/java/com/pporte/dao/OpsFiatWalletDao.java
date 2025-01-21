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
import com.pporte.model.Customer;
import com.pporte.model.Transaction;
import com.pporte.utilities.Utilities;

public class OpsFiatWalletDao extends HandleConnections{
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 2L;
	private static String className = OpsFiatWalletDao.class.getSimpleName();
	
	public List<AssetTransaction> getFiatWalletTxn(String walletType)  throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		AssetTransaction transaction = null;
		List<AssetTransaction> transactionList=null;
		try{
			
			connection = super.getConnection();	
			query = "select a.txnusercode txnusercode, a.sysreference sysreference, a.pymtchannel pymtchannel,"
					+ "	a.txnamount txnamount, a.txncurrencyid txncurrencyid, a.txnmode txnmode, a.txndatetime txndatetime, b.walletid walletid,"
					+ "	b.wallettype wallettype, c.relationshipno relationshipno,c.customername customername from txn_wallet_cust_bc a, wallet_details b,customer_details c  where a.walletid = b.walletid "
					+ "	and b.wallettype=? and b.relationshipno = c.relationshipno order by txndatetime desc limit 1000";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, walletType);
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
				 		transaction.setTxnAmount(Utilities.getMoneyinDecimalFormat(StringUtils.trim(rs.getString("txnamount"))));
				 		transaction.setRelationshipNo(StringUtils.trim(rs.getString("relationshipno")));
				 		transaction.setCustomerName(StringUtils.trim(rs.getString("customername")));
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
	
	public List<AssetTransaction> getOpsFiatWalletTxnBtnDates(String dateFrom, String dateTo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		AssetTransaction transaction = null;
		List<AssetTransaction> transactionList=null;
		try{
			connection = super.getConnection();	

			
			query = "select a.txnusercode txnusercode, a.sysreference sysreference, a.pymtchannel pymtchannel, "
					+ " a.txnamount txnamount, a.txncurrencyid txncurrencyid, a.txnmode txnmode, a.txndatetime txndatetime, b.walletid walletid, "
					+ "	b.wallettype wallettype,c.relationshipno relationshipno,c.customername customername from txn_wallet_cust_bc a, "
					+ "	wallet_details b, customer_details c where a.walletid = b.walletid  and b.relationshipno = c.relationshipno and "
					+ " a.txndatetime between ? and  ? order by txndatetime desc limit 1000";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dateFrom);
			pstmt.setString(2, dateTo);
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
				 		transaction.setCustomerName(StringUtils.trim(rs.getString("customername")));
				 		transaction.setRelationshipNo(StringUtils.trim(rs.getString("relationshipno")));
				 		transactionList.add(transaction);
				 	}
			 }
			 if(transactionList!=null)
				 if(transactionList.size()==0)
					 transactionList=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getOpsFiatWalletTxnBtnDates  is  "+e.getMessage());
			throw new Exception ("The exception in method getOpsFiatWalletTxnBtnDates  is  "+e.getMessage());			
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
	public ArrayList<AssetTransaction> getOpsCardTxnBtnDates(String dateFrom, String dateTo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		AssetTransaction transaction = null;
		ArrayList<AssetTransaction> transactionList=null;
		try{
			connection = super.getConnection();	
			
			query="select a.txnusercode txnusercode,a.tokenid tokenid,a.txnusercode txnusercode,a.custrelno custrelno,a.paymode paymode, "
					+ " a.sysreference_ext sysreference_ext,a.sysreference_int sysreference_int,a.txnamount txnamount, "
					+ " a.txncurrencyid txncurrencyid,b.walletid walletid,a.txndatetime txndatetime, c.customername customername from txn_cardtoken_bc a,wallet_details b,customer_details c "
					+ "	where a.custrelno=b.relationshipno AND b.relationshipno = c.relationshipno and "
					+ " a.txndatetime between ? and  ? order by txndatetime desc limit 1000";

			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dateFrom);
			pstmt.setString(2, dateTo);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 transactionList = new ArrayList<AssetTransaction>();
				 	while(rs.next()){	
				 		transaction = new AssetTransaction();
				 		transaction.setTxnUserCode(StringUtils.trim(rs.getString("txnusercode")));
				 		transaction.setTxnDateTime(StringUtils.trim(rs.getString("txndatetime")));
				 		transaction.setSystemReferenceInt(StringUtils.trim(rs.getString("sysreference_int")));
				 		transaction.setTxnAmount(Utilities.getMoneyinDecimalFormat(StringUtils.trim(rs.getString("txnamount"))));
				 		transaction.setCustomerName(StringUtils.trim(rs.getString("customername")));
				 		transactionList.add(transaction);
				 	}
			 }
			 if(transactionList!=null)
				 if(transactionList.size()==0)
					 transactionList=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getOpsCardTxnBtnDates  is  "+e.getMessage());
			throw new Exception ("The exception in method getOpsCardTxnBtnDates  is  "+e.getMessage());			
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
	public ArrayList<Transaction> getWalletAuthorizationsTransactionsBtwDates(String dateFrom, String dateTo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<Transaction>arrAuthorizedWalletTransactions =null;
		try{
			connection = super.getConnection();	
			query="select a.customerid customerid, b.walletid walletid,b.authamount authamount,b.authcurrency authcurrency, b.sysreference sysreference,"
					+ "b.authstatus authstatus,b.reason reason,b.authdate authdate from customer_details a, wallet_authorization_details b where a.relationshipno=b.relationshipno and b.authdate between ? and ? order by b.authdate desc limit 1000";
		
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dateFrom);
			pstmt.setString(2, dateTo);
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				arrAuthorizedWalletTransactions = new ArrayList<Transaction>();
				while(rs.next()){	
					Transaction m_Transaction = new Transaction();
					m_Transaction.setCustomerId(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("customerid"))));
					m_Transaction.setCustomerWalletId(StringUtils.trim(rs.getString("walletid")));
					m_Transaction.setTxnCurrencyId(StringUtils.trim(rs.getString("authcurrency")));
					m_Transaction.setTxnAmount(Utilities.getMoneyinDecimalFormat(StringUtils.trim(rs.getString("authamount"))));
					m_Transaction.setSystemReferenceInt(StringUtils.trim(rs.getString("sysreference")));
					m_Transaction.setStatus(StringUtils.trim(rs.getString("authstatus")));
					m_Transaction.setComment(StringUtils.trim(rs.getString("reason")));
					m_Transaction.setTxnDateTime(StringUtils.trim(rs.getString("authdate")));
					arrAuthorizedWalletTransactions.add(m_Transaction);
				}
			}
			if(arrAuthorizedWalletTransactions!=null)
				if(arrAuthorizedWalletTransactions.size()==0)
					arrAuthorizedWalletTransactions=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getWalletAuthorizationsTransactionsBtwDates  is  "+e.getMessage());
			throw new Exception ("The exception in method getWalletAuthorizationsTransactionsBtwDates  is  "+e.getMessage());			
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
		return arrAuthorizedWalletTransactions;
	}
	
	public List<AssetTransaction> getCryptoWalletTxn() throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		AssetTransaction transaction = null;
		List<AssetTransaction> transactionList=null;
		try{
			connection = super.getConnection();	
			
			query="select a.txnusercode txnusercode, a.sysreference_int sysreference_int, a.sysreference_ext sysreference_ext,"
					+ "	a.assetcode assetcode, a.pymtchannel pymtchannel, a.txnamount txnamount, a.txncurrencyid txncurrencyid,"
					+ "	a.txnmode txnmode, b.walletid walletid, a.txndatetime txndatetime  from txn_crypto_wallet_cust a,wallet_details b order by"
					+ " txndatetime desc limit 1000";

			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 transactionList = new ArrayList<AssetTransaction>();
				 	while(rs.next()){	
				 		transaction = new AssetTransaction();
				 		transaction.setTxnUserCode(StringUtils.trim(rs.getString("txnusercode")));
				 		transaction.setTxnDateTime(StringUtils.trim(rs.getString("txndatetime")));
				 		transaction.setTxnMode(StringUtils.trim(rs.getString("txnmode")));
				 		transaction.setPymtChannel(StringUtils.trim(rs.getString("pymtchannel")));
				 		transaction.setSystemReferenceInt(StringUtils.trim(rs.getString("sysreference_int")));
				 		transaction.setTxnAmount(Utilities.getMoneyinDecimalFormat(StringUtils.trim(rs.getString("txnamount"))));
				 		transactionList.add(transaction);
				 	}
			 }
			 if(transactionList!=null)
				 if(transactionList.size()==0)
					 transactionList=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getCryptoWalletTxn  is  "+e.getMessage());
			throw new Exception ("The exception in method getCryptoWalletTxn  is  "+e.getMessage());			
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
	public List<AssetTransaction> getCardWalletTxn() throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		AssetTransaction transaction = null;
		List<AssetTransaction> transactionList=null;
		try{
			connection = super.getConnection();	
			
			query="select a.txnusercode txnusercode,a.tokenid tokenid,a.txnusercode txnusercode,a.custrelno custrelno,a.paymode paymode, "
					+ " a.sysreference_ext sysreference_ext,a.sysreference_int sysreference_int,a.txnamount txnamount, "
					+ " a.txncurrencyid txncurrencyid,b.walletid walletid,a.txndatetime txndatetime, c.customername customername from txn_cardtoken_bc a,wallet_details b,customer_details c "
					+ "	where a.custrelno=b.relationshipno AND b.relationshipno = c.relationshipno order by txndatetime desc limit 1000";

			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 transactionList = new ArrayList<AssetTransaction>();
				 	while(rs.next()){	
				 		transaction = new AssetTransaction();
				 		transaction.setTxnUserCode(StringUtils.trim(rs.getString("txnusercode")));
				 		transaction.setTxnDateTime(StringUtils.trim(rs.getString("txndatetime")));
				 		transaction.setSystemReferenceInt(StringUtils.trim(rs.getString("sysreference_int")));
				 		transaction.setTxnAmount(Utilities.getMoneyinDecimalFormat(StringUtils.trim(rs.getString("txnamount"))));
				 		transaction.setCustomerName(StringUtils.trim(rs.getString("customername")));
				 		transactionList.add(transaction);
				 	}
			 }
			 if(transactionList!=null)
				 if(transactionList.size()==0)
					 transactionList=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getCardWalletTxn  is  "+e.getMessage());
			throw new Exception ("The exception in method getCardWalletTxn  is  "+e.getMessage());			
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

	public ArrayList<Customer> getSearchOpsSpecificPorteWalDetails(String customerName, String relationshipNo,
			String customerId, String mobileNo) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<Customer> arrCustomer = null;
		try {
			NeoBankEnvironment.setComment(3,className,"in  getOpsSpecificCustomerDetails searched relationshipNo is "+
					relationshipNo+ "searched customerId is  "+customerId+ "searched custName  "+ customerName + "phoneNumber is  "+ mobileNo            );

			connection = super.getConnection();	  
			query = "select a.relationshipno relationshipno, a.customername customername, a.customerid customerid,a.custcontact custcontact,"
					+ "b.public_key public_key  from stellar_account_relation b, customer_details a where a.relationshipno = b.relationshipno and";

			if(relationshipNo.equals("")==false) {	
				query+= "  a.relationshipno = ? and  ";
			}else {
					if(customerName.equals("")==false) {
						query+= "  a.customername like '%"+customerName+"%' and  ";
					}
					if(customerId.equals("")==false) {
						query+= "  a.customerid like '%"+customerId+"%' and  ";
					}
					if(mobileNo.equals("")==false) {
						query+= "  a.custcontact like '%"+mobileNo+"%' and  ";
					}
			}
			query+= "  1=1 ";
			
			NeoBankEnvironment.setComment(1,className,"search query being executed  is  "+query);


			pstmt = connection.prepareStatement(query);
			if(relationshipNo.equals("")==false) {
				pstmt.setString(1, relationshipNo);
				
			}
			
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){
				 arrCustomer = new ArrayList<Customer>();
				 	while(rs.next()){	 
				 		Customer m_Customer = new Customer();
				 		m_Customer.setCustomerName(StringUtils.trim(rs.getString("customername")));
						m_Customer.setRelationshipNo(StringUtils.trim(rs.getString("relationshipno")));
//						m_Customer.setPublicKey(StringUtils.trim(rs.getString("public_key")));
						m_Customer.setPublicKey(Utilities.tripleDecryptData( StringUtils.trim(rs.getString("public_key"))));
						arrCustomer.add(m_Customer);
				 		} 

				 	} 

			 if(arrCustomer!=null)
				 if(arrCustomer.size()==0)
					 arrCustomer=null;	
			
		}catch(Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getSearchOpsSpecificPorteWalDetails  is  "+e.getMessage());
			throw new Exception ("The exception in method getSearchOpsSpecificPorteWalDetails  is  "+e.getMessage());			
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
	
		return arrCustomer;
}

	public ArrayList<Customer> getAllWalletsDetails()  throws Exception{

		PreparedStatement pstmt =null;
		Connection connection= null;
		ResultSet rs=null;
		String query=null;
		ArrayList<Customer>arrWalletDetails =null;
		Customer m_Customer=null;
		try {
			connection = super.getConnection();
			query="select a.customerid customerid, a.relationshipno relationshipno, c.walletid walletid, c.walletdesc walletdesc, c.blockcodeid blockcodeid,"
					+ " b.blockcode_desc blockcode_desc from customer_details a , wallet_details c , wallet_block_codes b where  a.relationshipno = c.relationshipno and c.blockcodeid=b.blockcode_id order by c.createdon desc limit 1000";
			
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				arrWalletDetails = new ArrayList<Customer>();
				while(rs.next()){
				m_Customer = new Customer();
		 		m_Customer.setCustomerId(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("customerid"))));
		 		m_Customer.setRelationshipNo(StringUtils.trim(rs.getString("relationshipno")));
		 		m_Customer.setWalletId(StringUtils.trim(rs.getString("walletid")));
		 		m_Customer.setWalletDesc(StringUtils.trim(rs.getString("walletdesc")));
		 		m_Customer.setBlockCodeId(StringUtils.trim(rs.getString("blockcodeid")));
		 		m_Customer.setBlockCodeDesc(StringUtils.trim(rs.getString("blockcode_desc")));
		 		arrWalletDetails.add(m_Customer);
				}
			}
			
			 if(arrWalletDetails!=null) if(arrWalletDetails.size()==0) arrWalletDetails=null;
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllWalletsDetails  is  "+e.getMessage());
			throw new Exception ("The exception in method getAllWalletsDetails  is  "+e.getMessage());
		}finally {
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(rs!=null)rs.close(); if (m_Customer!=null)m_Customer=null;
			if(pstmt!=null)pstmt.close();
		}
		return arrWalletDetails;
	}
	public ArrayList<Transaction> getWalletAuthorizationsTransactions()  throws Exception{
		PreparedStatement pstmt =null;
		Connection connection= null;
		ResultSet rs=null;
		String query=null;
		ArrayList<Transaction>arrAuthorizedWalletTransactions =null;
		try {
			connection = super.getConnection();
			
			query="select a.customerid customerid, b.walletid walletid,b.authamount authamount,b.authcurrency authcurrency, b.sysreference sysreference,"
					+ "b.authstatus authstatus,b.reason reason,b.authdate authdate from customer_details a, wallet_authorization_details b where a.relationshipno=b.relationshipno order by b.authdate desc limit 1000";
			
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				arrAuthorizedWalletTransactions = new ArrayList<Transaction>();
				while(rs.next()){
					Transaction m_Transaction = new Transaction();
					m_Transaction.setCustomerId(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("customerid"))));
					m_Transaction.setCustomerWalletId(StringUtils.trim(rs.getString("walletid")));
					m_Transaction.setTxnCurrencyId(StringUtils.trim(rs.getString("authcurrency")));
					m_Transaction.setTxnAmount(Utilities.getMoneyinDecimalFormat(StringUtils.trim(rs.getString("authamount"))));
					m_Transaction.setSystemReferenceInt(StringUtils.trim(rs.getString("sysreference")));
					m_Transaction.setStatus(StringUtils.trim(rs.getString("authstatus")));
					m_Transaction.setComment(StringUtils.trim(rs.getString("reason")));
					m_Transaction.setTxnDateTime(StringUtils.trim(rs.getString("authdate")));
					arrAuthorizedWalletTransactions.add(m_Transaction);
				}
			}
			
			if(arrAuthorizedWalletTransactions!=null) if(arrAuthorizedWalletTransactions.size()==0) arrAuthorizedWalletTransactions=null;
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method"+ Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}finally {
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(rs!=null)rs.close();
			if(query!=null)query=null;
			if(pstmt!=null)pstmt.close();
		}
		return arrAuthorizedWalletTransactions;
	}

	public Customer getSpecificWalletDetails(String relationshipNo, String blockCodeId) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		Customer m_Customer = null;
		try{
			connection = super.getConnection();	
			
			query = "select a.relationshipno relationshipno, a.customerid customerid, a.customername customername, b.blockcode_desc blockcode_desc,"
					+ "b.blockcode_id blockcode_id,from customer_details a, wallet_block_codes b where a.relationshipno=?";

			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			rs = (ResultSet)pstmt.executeQuery();

			 if(rs!=null){
			 		 m_Customer = new Customer();
				 	while(rs.next()){	
				 		m_Customer.setRelationshipNo(StringUtils.trim(rs.getString("relationshipno")));
						m_Customer.setCustomerId(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("customerid"))));
						m_Customer.setCustomerName(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("customername"))));
						m_Customer.setBlockCodeId(StringUtils.trim(rs.getString("blockcode_id")));
						m_Customer.setBlockCodeDesc(StringUtils.trim(rs.getString("blockcode_desc")));

				 
				 	}
			 }		
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getSpecificWalletDetails  is  "+e.getMessage());
			throw new Exception ("The exception in method getSpecificWalletDetails  is  "+e.getMessage());			
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
		return m_Customer;
}

	public boolean OpsUpdateBlockId(String relationshipNo, String walletId, String blockCodeId) throws Exception{
		PreparedStatement pstmt =null;
		Connection connection =null;
		String query =null;
		boolean result =false;
		NeoBankEnvironment.setComment(3, className, "Block code id is "+blockCodeId +" walletId id is "+walletId);
		try {
			connection = super.getConnection();
			connection.setAutoCommit(false);
			//									1
			query = "update wallet_details set blockcodeid= ? where walletid = ? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, blockCodeId);
			pstmt.setString(2, walletId);
			try {
				pstmt.executeUpdate();
			} catch (Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}
			connection.commit();
			result = true;
		}catch(Exception e) {
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method updateCustomerProfile  is  "+e.getMessage());
			throw new Exception ("The exception in method updateCustomerProfile  is  "+e.getMessage());
		}finally {
			if(connection !=null)
				try {
					super.close();
				}catch (SQLException e) {
					NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
				}
			if(pstmt!=null) pstmt.close(); 
		}
		return result;
	}

	public ArrayList<Customer> getAllBlockDetails() throws Exception{
		PreparedStatement pstmt =null;
		Connection connection= null;
		ResultSet rs=null;
		String query=null;
		ArrayList<Customer>arrBlockDetails =null;
		Customer m_Customer=null;
		try {
			connection = super.getConnection();
			query="select blockcode_id, blockcode_desc from wallet_block_codes";
			
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				arrBlockDetails = new ArrayList<Customer>();
				while(rs.next()){
				m_Customer = new Customer();
				m_Customer.setBlockCodeId(StringUtils.trim(rs.getString("blockcode_id")));
				m_Customer.setBlockCodeDesc(StringUtils.trim(rs.getString("blockcode_desc")));
		 		arrBlockDetails.add(m_Customer);
				}
			}
			 if(arrBlockDetails!=null) {
				 NeoBankEnvironment.setComment(3,className," is arrBlockDetails size is  "+arrBlockDetails.size());
			 }
			 if(arrBlockDetails!=null) if(arrBlockDetails.size()==0) arrBlockDetails=null;
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllBlockDetails  is  "+e.getMessage());
			throw new Exception ("The exception in method getAllBlockDetails  is  "+e.getMessage());
		}finally {
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();if (m_Customer!=null)m_Customer=null;
		}
		
		return arrBlockDetails;
	}


	public List<Transaction> getPatnerRemittanceTransactions() throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		List<Transaction> transactions = null;
		Transaction transaction = null;
		try{
			connection = super.getConnection();
			query =" select a.txncode txncode, a.sysreference_int sysreference_int, a.txnusercode txnusercode, a.custrelno custrelno, "
					+ "	 a.paytype paymode, a.source_assetcode source_assetcode, a.destination_currency destination_currency, "
					+ "	 a.source_amount source_amount, a.destination_amount destination_amount, a.partner_userid partner_userid, "
					+ "	 a.partner_stellar_id partner_stellar_id, a.stellar_txnhash stellar_txnhash, a.sender_comment sender_comment, a.partners_comment partners_comment,  "
					+ "	 a.receiver_name receiver_name, a.receiver_bankname receiver_bankname, a.receiver_bankcode receiver_bankcode, a.receiver_accountno receiver_accountno, "
					+ "	a.receiver_email receiver_email, a.status status, a.txndatetime txndatetime  from txn_currency_remittance a "
					+ "  order by txndatetime desc limit 1000";
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 transactions = new ArrayList<Transaction>();
				 	while(rs.next()){
				 		transaction = new Transaction(); 
				 		transaction.setTxnCode(StringUtils.trim(rs.getString("txncode")));
				 		transaction.setSystemReferenceInt(StringUtils.trim(rs.getString("sysreference_int")));
				 		transaction.setTxnUserCode(StringUtils.trim(rs.getString("txnusercode")));
				 		transaction.setRelationshipNo(StringUtils.trim(rs.getString("custrelno")));
				 		transaction.setPayMode(StringUtils.trim(rs.getString("custrelno")));
				 		transaction.setPayMode(StringUtils.trim(rs.getString("paymode")));
				 		transaction.setSourceAssetCode(StringUtils.trim(rs.getString("source_assetcode")));
				 		transaction.setDestinationAssetCode(StringUtils.trim(rs.getString("destination_currency")));
				 		transaction.setSourceAmount(StringUtils.trim(rs.getString("source_amount")));
				 		transaction.setDestinationAmount(StringUtils.trim(rs.getString("destination_amount")));
				 		transaction.setCustomerId(StringUtils.trim(rs.getString("partner_userid")));
				 		transaction.setPublicKey(StringUtils.trim(rs.getString("partner_stellar_id")));
				 		transaction.setSystemReferenceExt(StringUtils.trim(rs.getString("stellar_txnhash")));
				 		transaction.setSenderComment(StringUtils.trim(rs.getString("sender_comment")));
				 		transaction.setPartnersComment(StringUtils.trim(rs.getString("partners_comment")));
				 		transaction.setReceiverName(StringUtils.trim(rs.getString("receiver_name")));
				 		transaction.setReceiverBankName(StringUtils.trim(rs.getString("receiver_bankname")));
				 		transaction.setReceiverBankCode(StringUtils.trim(rs.getString("receiver_bankcode")));
				 		transaction.setReceiverAccountNo(StringUtils.trim(rs.getString("receiver_accountno")));
				 		transaction.setReceiverEmail(StringUtils.trim(rs.getString("receiver_email")));
				 		transaction.setStatus(StringUtils.trim(rs.getString("status")));
				 		transaction.setTxnDateTime(StringUtils.trim(rs.getString("txndatetime")));
				 		transactions.add(transaction);
				 	}
			 }
			 if (transactions != null)
					if (transactions.size() == 0)
						transactions = null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getPatnerRemittanceTransactions  is  "+e.getMessage());
			throw new Exception ("The exception in method getPatnerRemittanceTransactions  is  "+e.getMessage());			
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
		return transactions;
	}
	public List<Transaction> getOpsRemitanceTxnBtnDates(String dateFrom, String dateTo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		List<Transaction> transactions = null;
		Transaction transaction = null;
		try{
			connection = super.getConnection();	

			query =" select a.txncode txncode, a.sysreference_int sysreference_int, a.txnusercode txnusercode, a.custrelno custrelno, "
					+ "	 a.paytype paymode, a.source_assetcode source_assetcode, a.destination_currency destination_currency, "
					+ "	 a.source_amount source_amount, a.destination_amount destination_amount, a.partner_userid partner_userid, "
					+ "	 a.partner_stellar_id partner_stellar_id, a.stellar_txnhash stellar_txnhash, a.sender_comment sender_comment, a.partners_comment partners_comment,  "
					+ "	 a.receiver_name receiver_name, a.receiver_bankname receiver_bankname, a.receiver_bankcode receiver_bankcode, a.receiver_accountno receiver_accountno, "
					+ "	a.receiver_email receiver_email, a.status status, a.txndatetime txndatetime  from txn_currency_remittance a "
					+ "  where a.txndatetime between? and ? order by txndatetime desc limit 1000";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dateFrom);
			pstmt.setString(2, dateTo);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 transactions = new ArrayList<Transaction>();
				 	while(rs.next()){	
				 		transaction = new Transaction(); 
				 		transaction.setTxnCode(StringUtils.trim(rs.getString("txncode")));
				 		transaction.setSystemReferenceInt(StringUtils.trim(rs.getString("sysreference_int")));
				 		transaction.setTxnUserCode(StringUtils.trim(rs.getString("txnusercode")));
				 		transaction.setRelationshipNo(StringUtils.trim(rs.getString("custrelno")));
				 		transaction.setPayMode(StringUtils.trim(rs.getString("custrelno")));
				 		transaction.setPayMode(StringUtils.trim(rs.getString("paymode")));
				 		transaction.setSourceAssetCode(StringUtils.trim(rs.getString("source_assetcode")));
				 		transaction.setDestinationAssetCode(StringUtils.trim(rs.getString("destination_currency")));
				 		transaction.setSourceAmount(StringUtils.trim(rs.getString("source_amount")));
				 		transaction.setDestinationAmount(StringUtils.trim(rs.getString("destination_amount")));
				 		transaction.setCustomerId(StringUtils.trim(rs.getString("partner_userid")));
				 		transaction.setPublicKey(StringUtils.trim(rs.getString("partner_stellar_id")));
				 		transaction.setSystemReferenceExt(StringUtils.trim(rs.getString("stellar_txnhash")));
				 		transaction.setSenderComment(StringUtils.trim(rs.getString("sender_comment")));
				 		transaction.setPartnersComment(StringUtils.trim(rs.getString("partners_comment")));
				 		transaction.setReceiverName(StringUtils.trim(rs.getString("receiver_name")));
				 		transaction.setReceiverBankName(StringUtils.trim(rs.getString("receiver_bankname")));
				 		transaction.setReceiverBankCode(StringUtils.trim(rs.getString("receiver_bankcode")));
				 		transaction.setReceiverAccountNo(StringUtils.trim(rs.getString("receiver_accountno")));
				 		transaction.setReceiverEmail(StringUtils.trim(rs.getString("receiver_email")));
				 		transaction.setStatus(StringUtils.trim(rs.getString("status")));
				 		transaction.setTxnDateTime(StringUtils.trim(rs.getString("txndatetime")));
				 		transactions.add(transaction);
				 	}
			 }
			 if(transactions!=null)
				 if(transactions.size()==0)
					 transactions=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getOpsFiatWalletTxnBtnDates  is  "+e.getMessage());
			throw new Exception ("The exception in method getOpsFiatWalletTxnBtnDates  is  "+e.getMessage());			
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
		return transactions;
	}

	public ArrayList<Customer> getAllFilteredWalletsDetails(String customerName, String relationshipNo, String customerId,
			String mobileNo) throws Exception{

		PreparedStatement pstmt =null;
		Connection connection= null;
		ResultSet rs=null;
		String query=null;
		ArrayList<Customer>arrWalletDetails =null;
		Customer m_Customer=null;
		try {
			connection = super.getConnection();
			
			
			NeoBankEnvironment.setComment(3,className,"in  getOpsSpecificCustomerDetails searched relationshipNo is "+
					relationshipNo+ "searched customerId is  "+customerId+ "searched custName  "+ customerName + "phoneNumber is  "+ mobileNo );
			
			query="select a.customerid customerid, a.relationshipno relationshipno, c.walletid walletid, c.walletdesc walletdesc, c.blockcodeid blockcodeid,"
					+ " b.blockcode_desc blockcode_desc from customer_details a , wallet_details c , wallet_block_codes b where  a.relationshipno = c.relationshipno and c.blockcodeid=b.blockcode_id and ";
			
			
			if(relationshipNo.equals("")==false) {	
				query+= "  a.relationshipno = ? and  ";
			}else {
				if(customerName.equals("")==false) {
					query+= "  a.customername like '%"+customerName+"%' and  ";
				}
					if(customerId.equals("")==false) {
						query+= "  a.customerid =? and  ";
					}
					if(mobileNo.equals("")==false) {
						query+= "a.custcontact =? and  ";
					}
			}
			query+= "  1=1 ";
			
			NeoBankEnvironment.setComment(3,className,"search query being executed  is  "+query);
			
			
			pstmt = connection.prepareStatement(query);
			if(relationshipNo.equals("")==false) {
				pstmt.setString(1, relationshipNo);
			}
			if(customerId.equals("")==false) {
				pstmt.setString(1, Utilities.tripleEncryptData(customerId));
			}
			if(mobileNo.equals("")==false) {
				pstmt.setString(1, Utilities.tripleEncryptData(mobileNo));
			}
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				arrWalletDetails = new ArrayList<Customer>();
				while(rs.next()){
				m_Customer = new Customer();
		 		m_Customer.setCustomerId(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("customerid"))));
		 		m_Customer.setRelationshipNo(StringUtils.trim(rs.getString("relationshipno")));
		 		m_Customer.setWalletId(StringUtils.trim(rs.getString("walletid")));
		 		m_Customer.setWalletDesc(StringUtils.trim(rs.getString("walletdesc")));
		 		m_Customer.setBlockCodeId(StringUtils.trim(rs.getString("blockcodeid")));
		 		m_Customer.setBlockCodeDesc(StringUtils.trim(rs.getString("blockcode_desc")));
		 		arrWalletDetails.add(m_Customer);
				}
			}
			
			 if(arrWalletDetails!=null) if(arrWalletDetails.size()==0) arrWalletDetails=null;
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllWalletsDetails  is  "+e.getMessage());
			throw new Exception ("The exception in method getAllWalletsDetails  is  "+e.getMessage());
		}finally {
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close(); if (m_Customer!=null)m_Customer=null;
		}
		return arrWalletDetails;
	}
	
	public ArrayList<AssetTransaction> searchForWallet(String custName, String customerRelNo, String customerId,String custPhoneNo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		AssetTransaction transaction = null;
		ArrayList<AssetTransaction> transactionList=null;
		String walletType="F";// Fiat
		try{
			
			connection = super.getConnection();	
			
			query = "select a.txnusercode txnusercode, a.sysreference sysreference, a.pymtchannel pymtchannel,"
					+ "	a.txnamount txnamount, a.txncurrencyid txncurrencyid, a.txnmode txnmode, a.txndatetime txndatetime, b.walletid walletid,"
					+ "	b.wallettype wallettype, c.relationshipno relationshipno,c.customername customername from txn_wallet_cust_bc a, wallet_details b,customer_details c  where a.walletid = b.walletid "
					+ "	and b.wallettype=? and b.relationshipno=c.relationshipno and ";
			
			if(customerRelNo.equals("")==false) {	
				query+= "c.relationshipno = ? and  ";
			}else {
				if(custName.equals("")==false) {
					query+= "c.customername like '%"+custName+"%' and  ";
				}
					if(customerId.equals("")==false) {
						query+= "c.customerid =? and  ";
					}
					if(custPhoneNo.equals("")==false) {
						query+= "c.custcontact =? and  ";
					}
			}
			query+= "  1=1 ";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, walletType);
			
			if(customerRelNo.equals("")==false) {
				pstmt.setString(2, customerRelNo);
			}
			if(customerId.equals("")==false) {
				pstmt.setString(2, Utilities.tripleEncryptData(customerId));
			}
			if(custPhoneNo.equals("")==false) {
				pstmt.setString(2, Utilities.tripleEncryptData(custPhoneNo));
			}
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
				 		transaction.setTxnAmount(Utilities.getMoneyinDecimalFormat(StringUtils.trim(rs.getString("txnamount"))));
				 		transaction.setRelationshipNo(StringUtils.trim(rs.getString("relationshipno")));
				 		transaction.setCustomerName(StringUtils.trim(rs.getString("customername")));
				 		transactionList.add(transaction);
				 	}
			 }
			 if(transactionList!=null)
				 if(transactionList.size()==0)
					 transactionList=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method searchForWallet  is  "+e.getMessage());
			throw new Exception ("The exception in method searchForWallet  is  "+e.getMessage());			
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
