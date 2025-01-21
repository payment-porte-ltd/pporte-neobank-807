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
import com.pporte.model.TransactionRules;

public class MerchantPaymentsDao extends HandleConnections {
	
	private static String className = MerchantPaymentsDao.class.getSimpleName();
	
	public List<AssetTransaction> getMerchantTransactions(String merchantCode) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		List<AssetTransaction> transactionList = new ArrayList<>();
		AssetTransaction transaction = null;
		try{
			connection = super.getConnection();	
			String walletId = null;
			query = " select walletid from wallet_details where relationshipno=? and usertype =? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, merchantCode);
			pstmt.setString(2, "M");
			rs = pstmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					walletId = (StringUtils.trim(rs.getString("walletid")));
				} // end of while
			} // end of if
			
			if(pstmt!=null) pstmt.close();
			if(rs!=null) rs.close();
			
			query = " select a.txncode txncode, a.merchwalletid merchwalletid, a.custwalletid custwalletid, a.sysreference sysreference, "
				 + " a.usertxncode usertxncode, a.txnamount txnamount, a.txncurrencyid txncurrencyid, "
				 + " a.txnmode txnmode, a.txndatetime txndatetime, b.relationshipno relationshipno, "
				 + " c.customername customername from txn_wallet_merch_bc a, wallet_details b, "
				 + " customer_details c where a.merchwalletid=? and a.custwalletid=b.walletid and b.relationshipno = c.relationshipno ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, walletId);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 	while(rs.next()){	
				 		transaction = new AssetTransaction();
				 		transaction.setTxnCode(rs.getString("txncode"));
				 		transaction.setMerchantWalletId(rs.getString("merchwalletid"));
				 		transaction.setCustomerWalletId(rs.getString("custwalletid"));
				 		transaction.setSystemReferenceInt(rs.getString("sysreference"));
				 		transaction.setTxnUserCode(rs.getString("usertxncode"));
				 		transaction.setTxnAmount(rs.getString("txnamount"));
				 		transaction.setTxnCurrencyId(rs.getString("txncurrencyid"));
				 		transaction.setTxnMode(rs.getString("txnmode"));
				 		transaction.setTxnDateTime(rs.getString("txndatetime"));
				 		transaction.setTxnDateTime(rs.getString("txndatetime"));
				 		transaction.setRelationshipNo(rs.getString("relationshipno"));
				 		transaction.setCustomerName(rs.getString("customername"));
				 		transactionList.add(transaction);
				 		} // end of while
				 	} //end of if rs!=null check
			 // validate the password
			 if(transactionList!=null)
				 if(transactionList.size()==0)
					 transactionList=null;
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getMerchantTransactions  is  "+e.getMessage());
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
	public List<TransactionRules> getMerchantTransactionRules() throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		List<TransactionRules> transactionRulesList = new ArrayList<>();
		TransactionRules transactionRules = null;
		try{
			connection = super.getConnection();	
			query = " select  paymode, rulesdesc from  transaction_rules where usertype=? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "M");
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 	while(rs.next()){	
				 		transactionRules = new TransactionRules();
				 		transactionRules.setPayMode(rs.getString("paymode"));
				 		transactionRules.setRuleDesc(rs.getString("rulesdesc"));
				 		transactionRulesList.add(transactionRules);
				 		} // end of while
				 	} //end of if 
			 if(transactionRulesList!=null)
				 if(transactionRulesList.size()==0)
					 transactionRulesList=null;
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getMerchantTransactionRules  is  "+e.getMessage());
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
		return transactionRulesList;
		
	}
	
	public List<AssetTransaction> getLastTenMerchantTransactions(String merchantCode) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		List<AssetTransaction> transactionList = new ArrayList<>();
		AssetTransaction transaction = null;
		try{
			connection = super.getConnection();	
			String walletId = null;
			query = " select walletid from wallet_details where relationshipno=? and usertype =? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, merchantCode);
			pstmt.setString(2, "M");
			rs = pstmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					walletId = (StringUtils.trim(rs.getString("walletid")));
				} // end of while
			} // end of if
			if(pstmt!=null) pstmt.close();
			if(rs!=null) rs.close();
			
			query = " select a.txncode txncode, a.merchwalletid merchwalletid, a.custwalletid custwalletid, a.sysreference sysreference, "
				 + " a.usertxncode usertxncode, a.txnamount txnamount, a.txncurrencyid txncurrencyid, "
				 + " a.txnmode txnmode, a.txndatetime txndatetime, b.relationshipno relationshipno, "
				 + " c.customername customername from txn_wallet_merch_bc a, wallet_details b, "
				 + " customer_details c where a.merchwalletid=? and a.custwalletid=b.walletid and"
				 + " b.relationshipno = c.relationshipno order by txndatetime desc limit 10 ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, walletId);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 	while(rs.next()){	
				 		transaction = new AssetTransaction();
				 		transaction.setTxnCode(rs.getString("txncode"));
				 		transaction.setMerchantWalletId(rs.getString("merchwalletid"));
				 		transaction.setCustomerWalletId(rs.getString("custwalletid"));
				 		transaction.setSystemReferenceInt(rs.getString("sysreference"));
				 		transaction.setTxnUserCode(rs.getString("usertxncode"));
				 		transaction.setTxnAmount(rs.getString("txnamount"));
				 		transaction.setTxnCurrencyId(rs.getString("txncurrencyid"));
				 		transaction.setTxnMode(rs.getString("txnmode"));
				 		transaction.setTxnDateTime(rs.getString("txndatetime"));
				 		transaction.setTxnDateTime(rs.getString("txndatetime"));
				 		transaction.setRelationshipNo(rs.getString("relationshipno"));
				 		transaction.setCustomerName(rs.getString("customername"));
				 		transactionList.add(transaction);
				 		} // end of while
				 	} //end of if rs!=null check
			 // validate the password
			 if(transactionList!=null)
				 if(transactionList.size()==0)
					 transactionList=null;
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getMerchantTransactions  is  "+e.getMessage());
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
	
	public List<AssetTransaction> getMerchantTransactionsFromDateTo(
			String merchantCode, String fromDate, String toDate) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		List<AssetTransaction> transactionList = new ArrayList<>();
		AssetTransaction transaction = null;
		try{
			connection = super.getConnection();	
			String walletId = null;
			query = " select walletid from wallet_details where relationshipno=? and usertype =? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, merchantCode);
			pstmt.setString(2, "M");
			rs = pstmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					walletId = (StringUtils.trim(rs.getString("walletid")));
				} // end of while
			} // end of if
			if(pstmt!=null) pstmt.close();
			if(rs!=null) rs.close();
			
			query = " select a.txncode txncode, a.merchwalletid merchwalletid, a.custwalletid custwalletid, a.sysreference sysreference, "
				 + " a.usertxncode usertxncode, a.txnamount txnamount, a.txncurrencyid txncurrencyid, "
				 + " a.txnmode txnmode, a.txndatetime txndatetime, b.relationshipno relationshipno, "
				 + " c.customername customername from txn_wallet_merch_bc a, wallet_details b, "
				 + " customer_details c where a.merchwalletid=? and a.txndatetime between ? and ? and a.custwalletid=b.walletid and"
				 + " b.relationshipno = c.relationshipno order by txndatetime desc ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, walletId);
			pstmt.setString(2, fromDate);
			pstmt.setString(3, toDate);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 	while(rs.next()){	
				 		transaction = new AssetTransaction();
				 		transaction.setTxnCode(rs.getString("txncode"));
				 		transaction.setMerchantWalletId(rs.getString("merchwalletid"));
				 		transaction.setCustomerWalletId(rs.getString("custwalletid"));
				 		transaction.setSystemReferenceInt(rs.getString("sysreference"));
				 		transaction.setTxnUserCode(rs.getString("usertxncode"));
				 		transaction.setTxnAmount(rs.getString("txnamount"));
				 		transaction.setTxnCurrencyId(rs.getString("txncurrencyid"));
				 		transaction.setTxnMode(rs.getString("txnmode"));
				 		transaction.setTxnDateTime(rs.getString("txndatetime"));
				 		transaction.setTxnDateTime(rs.getString("txndatetime"));
				 		transaction.setRelationshipNo(rs.getString("relationshipno"));
				 		transaction.setCustomerName(rs.getString("customername"));
				 		transactionList.add(transaction);
				 		} // end of while
				 	} //end of if rs!=null check
			 // validate the password
			 if(transactionList!=null)
				 if(transactionList.size()==0)
					 transactionList=null;
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getMerchantTransactions  is  "+e.getMessage());
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
