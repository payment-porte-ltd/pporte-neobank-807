package com.pporte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.pporte.NeoBankEnvironment;
import com.pporte.model.AssetCoin;
import com.pporte.model.Partner;
import com.pporte.model.Transaction;
import com.pporte.model.User;
import com.pporte.utilities.Utilities;

public class RemittanceDao extends HandleConnections  {
	public static String className = RemittanceDao.class.getName();
	
	public List<Partner> getAllPatners()  throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		Partner partner = null;
		List<Partner> partnerList=null;
		try{
			
			connection = super.getConnection();	
			query =" select a.adminid adminid, a.adminname adminname, a.adminemail adminemail, a.admincontact admincontact, "
					+ " b.userid userid, b.location location, b.currency currency,  b.stellarid stellarid, b.status status, "
					+ " b.createdon createdon from admin_details a, partner_details b where a.adminid = b.userid  order by createdon desc ";
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 partnerList = new ArrayList<Partner>();
				 	while(rs.next()){
				 		partner = new Partner(); 
				 		partner.setUserId(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("adminid"))));
				 		partner.setParnerName(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("adminname"))));
				 		partner.setParnerEmail(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("adminemail"))));
				 		partner.setParnerContact(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("admincontact"))));
				 		partner.setParnerLocation(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("location"))));
				 		partner.setParnerCurrency(StringUtils.trim(rs.getString("currency")));
				 		partner.setParnerPublicKey(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("stellarid"))));
				 		partner.setStatus(StringUtils.trim(rs.getString("status")));
				 		partner.setCreatedOn(StringUtils.trim(rs.getString("createdon")));
				 		partnerList.add(partner);
				 	}
			 }
			 if(partnerList!=null)
				 if(partnerList.size()==0)
					 partnerList=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllPatners  is  "+e.getMessage());
			throw new Exception ("The exception in method getAllPatners  is  "+e.getMessage());			
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
		return partnerList;
	}
	
	public ArrayList<AssetCoin> getDigitalCurrencyCodes() throws Exception {
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		String query = null;
		ArrayList<AssetCoin> arrAssetCoins = null;
		try {
			connection = super.getConnection();
			query = " select asset_code, asset_desc, status, asset_type, wallettype from  wallet_assets where asset_type= ? and status=? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "C"); 
			pstmt.setString(2, "A"); 
			rs = (ResultSet) pstmt.executeQuery();
			if (rs != null) {
				arrAssetCoins = new ArrayList<AssetCoin>();
				while (rs.next()) {
					AssetCoin m_AssetCoin = new AssetCoin();
					m_AssetCoin.setAssetCode(StringUtils.trim(rs.getString("asset_code")));
					m_AssetCoin.setAssetDescription(StringUtils.trim(rs.getString("asset_desc")));
					m_AssetCoin.setStatus(StringUtils.trim(rs.getString("status")));
					m_AssetCoin.setAssetType(StringUtils.trim(rs.getString("asset_type")));
					m_AssetCoin.setWalletType(StringUtils.trim(rs.getString("wallettype")));
					arrAssetCoins.add(m_AssetCoin);
				} // end of while

			} // end of if rs!=null check
			if (arrAssetCoins != null)
				if (arrAssetCoins.size() == 0)
					arrAssetCoins = null;
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1, className, "The exception in method getDigitalCurrencyCodes is  " + e.getMessage());
			throw new Exception("The exception in method getDigitalCurrencyCodes is  " + e.getMessage());
		} finally {
			if (connection != null) {
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
		}
		return arrAssetCoins;
	}

	public boolean addPartner(String fullName, String email, String phoneNo, String location, String publicKey,
			String currency, String password) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		try{
			
			connection = super.getConnection();	
			connection.setAutoCommit(false);
			  //                                     1       2        3                  4         5           6          7        8
			 query = " insert into admin_details (adminid, adminpwd, accesstype,  adminname , adminemail, admincontact, status, createdon  ) "
						+ "values (?, ?, ?, ? , ?, ?, ?, ? ) ";
						//		   1  2  3  4   5  6  7  8  
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, (Utilities.tripleEncryptData(email))); 
			pstmt.setString(2, ( DigestUtils.md5Hex(Utilities.encryptString(password)))); 
			pstmt.setString(3, ( "P")); 				
			pstmt.setString(4,  (Utilities.tripleEncryptData(fullName))); 
			pstmt.setString(5,  (Utilities.tripleEncryptData(email))); 
			pstmt.setString(6,  (Utilities.tripleEncryptData(phoneNo))); 
			pstmt.setString(7, ("A")); 
			pstmt.setString(8, (Utilities.getMYSQLCurrentTimeStampForInsert())); 
			
			try {
					pstmt.executeUpdate();
				}catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
			}	
			if(pstmt!=null) pstmt.close();
			
			                              //		1         2		  3		 		4			5		    6                            
			query = "insert into partner_details (userid, location, currency, 	stellarid, 	status,   createdon, password_type  )  "
				+ "  values (?, ?, ?, ?, ?, ?, ? )  ";
						//   1  2  3  4  5  6  7
			pstmt = connection.prepareStatement(query);					
			pstmt.setString(1,   (Utilities.tripleEncryptData(email))); 
			pstmt.setString(2,  (Utilities.tripleEncryptData(location ))); 
			pstmt.setString(3, currency );
			pstmt.setString(4,  "");
			pstmt.setString(5, "A" ); // Active
			pstmt.setString(6, (Utilities.getMYSQLCurrentTimeStampForInsert())); 
			pstmt.setString(7, "T"); 
			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}				
			
			connection.commit();
			result = true;

		}catch(Exception e){
			connection.rollback(); result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method addPartner  is  "+e.getMessage());
			throw new Exception ("The exception in method addPartner  is  "+e.getMessage());			
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

	public boolean editPartner(String fullName, String email, String phoneNo, String location, String publicKey,
			String currency, String status) throws Exception {
		
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			//                                   1                 2             3          
			query = " update admin_details set  adminname=?, admincontact=?, status=? where adminid=? "; 
		    pstmt = connection.prepareStatement(query);
			pstmt.setString(1,  (Utilities.tripleEncryptData(fullName))); 						 
			pstmt.setString(2,  (Utilities.tripleEncryptData(phoneNo)));						 
			pstmt.setString(3, status);						 
			pstmt.setString(4,  (Utilities.tripleEncryptData(email)));						 
								 
			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}
			
			if(pstmt!=null) pstmt.close();
			//                                       1           2            3          4              5				
			query = " update partner_details set  location=?, currency=?, status=? where userid=? "; 
		    pstmt = connection.prepareStatement(query);
			pstmt.setString(1,  (Utilities.tripleEncryptData(location))); 						 
			pstmt.setString(2, currency);						 				 
			pstmt.setString(3, status);	
			pstmt.setString(4, (Utilities.tripleEncryptData( email)));
			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}
				
			connection.commit();
			result = true;
		}catch(Exception e){
			connection.rollback();
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method editPartner  is  "+e.getMessage());
			throw new Exception ("The exception in method editPartner  is  "+e.getMessage());
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
	
	
	public String getAccessType(String userId) throws Exception {
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		String query = null;
		String accessType = null;
		try {
			connection = super.getConnection();
			query = " select accesstype from admin_details where adminid = ?  ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, Utilities.tripleEncryptData(userId)); 
			rs = (ResultSet) pstmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					accessType = StringUtils.trim(rs.getString("accesstype"));
				} // end of while

			} // end of if rs!=null check
			
		} catch (Exception e) {
			accessType = null;
			NeoBankEnvironment.setComment(1, className, "The exception in method getAccessType is  " + e.getMessage());
			throw new Exception("The exception in method getAccessType is  " + e.getMessage());
		} finally {
			if (connection != null) {
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
		}
		return accessType;
	}


	public User getPartnerUserDetails(String userId)  throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		User user = null;
		try{
			connection = super.getConnection();	
			query =" select a.adminid adminid, a.adminname adminname, a.adminemail adminemail, "
					+ " a.accesstype accesstype, a.admincontact admincontact, a.expiry expirydate, "
					+ " b.userid userid, b.location location, b.currency currency,  b.stellarid stellarid, b.status status, b.password_type password_type, "
					+ " b.createdon createdon from admin_details a, partner_details b where a.adminid = b.userid  and a.adminid =? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1,  (Utilities.tripleEncryptData(userId))); 
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 	while(rs.next()){
				 		user = new User(); 
				 		user.setUserId(Utilities.tripleDecryptData(  StringUtils.trim(rs.getString("adminid")) ));
				 		user.setUserName(Utilities.tripleDecryptData(  StringUtils.trim(rs.getString("adminname"))  ));
				 		user.setUserType(  StringUtils.trim(rs.getString("accesstype"))    );
				 		user.setUserEmail(Utilities.tripleDecryptData(  StringUtils.trim(rs.getString("adminemail")) )  );
				 		user.setUserContact(Utilities.tripleDecryptData(  StringUtils.trim(rs.getString("admincontact")))    );
				 		user.setExpiryDate(  StringUtils.trim(rs.getString("expirydate"))    );
				 		user.setUserStatus(  StringUtils.trim(rs.getString("status"))    );
				 		user.setLocation(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("location"))));
				 		user.setCurrency(StringUtils.trim(rs.getString("currency")));
				 		user.setStellarId(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("stellarid"))));
				 		user.setPasswordType(StringUtils.trim(rs.getString("password_type")));
				 	}
			 }
		}catch(Exception e){
			user=null;
			NeoBankEnvironment.setComment(1,className,"The exception in method getPartnerUserDetails  is  "+e.getMessage());
			throw new Exception ("The exception in method getPartnerUserDetails  is  "+e.getMessage());			
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
		return user;
	}

	public Partner getPatnerDetails(String userId) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		Partner partner = null;
		try{
			connection = super.getConnection();	
			query =" select a.adminid adminid, a.adminname adminname, a.adminemail adminemail, a.admincontact admincontact, "
					+ " b.userid userid, b.location location, b.currency currency,  b.stellarid stellarid, b.status status, "
					+ " b.createdon createdon from admin_details a, partner_details b where a.adminid = b.userid  and a.adminid = ? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, Utilities.tripleEncryptData(userId)); 
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 	while(rs.next()){
				 		partner = new Partner(); 
				 		partner.setUserId(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("adminid"))));
				 		partner.setParnerName(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("adminname"))));
				 		partner.setParnerEmail(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("adminemail"))));
				 		partner.setParnerContact(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("admincontact"))));
				 		partner.setParnerLocation(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("location"))));
				 		partner.setParnerCurrency(StringUtils.trim(rs.getString("currency")));
				 		partner.setParnerPublicKey(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("stellarid"))));
				 		partner.setStatus(StringUtils.trim(rs.getString("status")));
				 		partner.setCreatedOn(StringUtils.trim(rs.getString("createdon")));
				 	}
			 }
		}catch(Exception e){
			partner = null;
			NeoBankEnvironment.setComment(1,className,"The exception in method getPatnerDetails  is  "+e.getMessage());
			throw new Exception ("The exception in method getAllPatnerDetails  is  "+e.getMessage());			
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
		return partner;
	}
	
	
	public List<Transaction> getPatnersPendingTransactions(String userId) throws Exception {
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
					+ "	a.receiver_email receiver_email, a.status status, a.txndatetime txndatetime  "
					+ " from txn_currency_remittance a where a.status = ?  and a.partner_userid = ? order by txndatetime desc ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "PP"); 
			pstmt.setString(2, (userId)); 
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
				 		transaction.setCustomerId((StringUtils.trim(rs.getString("partner_userid"))));
				 		transaction.setPublicKey((StringUtils.trim(rs.getString("partner_stellar_id"))));
				 		transaction.setSystemReferenceExt(StringUtils.trim(rs.getString("stellar_txnhash")));
				 		transaction.setSenderComment((StringUtils.trim(rs.getString("sender_comment"))));
				 		transaction.setPartnersComment((StringUtils.trim(rs.getString("partners_comment"))));
				 		transaction.setReceiverName((StringUtils.trim(rs.getString("receiver_name"))));
				 		transaction.setReceiverBankName((StringUtils.trim(rs.getString("receiver_bankname"))));
				 		transaction.setReceiverBankCode((StringUtils.trim(rs.getString("receiver_bankcode"))));
				 		transaction.setReceiverAccountNo(StringUtils.trim(rs.getString("receiver_accountno")));
				 		transaction.setReceiverEmail((StringUtils.trim(rs.getString("receiver_email"))));
				 		transaction.setStatus(StringUtils.trim(rs.getString("status")));
				 		transaction.setTxnDateTime(StringUtils.trim(rs.getString("txndatetime")));
				 		transactions.add(transaction);
				 	}
			 }
			 if (transactions != null)
					if (transactions.size() == 0)
						transactions = null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getPatnersTransactions  is  "+e.getMessage());
			throw new Exception ("The exception in method getPatnersTransactions  is  "+e.getMessage());			
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
	
	
	public List<Transaction> getPatnersCompleteTransactions(String userId) throws Exception {
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
					+ "	a.receiver_email receiver_email, a.status status, a.txndatetime txndatetime  from txn_currency_remittance a where a.status = ?  and a.partner_userid = ? "
					+ "  order by txndatetime desc ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "C"); 
			pstmt.setString(2, (userId)); 
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
				 		transaction.setCustomerId((StringUtils.trim(rs.getString("partner_userid"))));
				 		transaction.setPublicKey((StringUtils.trim(rs.getString("partner_stellar_id"))));
				 		transaction.setSystemReferenceExt(StringUtils.trim(rs.getString("stellar_txnhash")));
				 		transaction.setSenderComment((StringUtils.trim(rs.getString("sender_comment"))));
				 		transaction.setPartnersComment((StringUtils.trim(rs.getString("partners_comment"))));
				 		transaction.setReceiverName((StringUtils.trim(rs.getString("receiver_name"))));
				 		transaction.setReceiverBankName((StringUtils.trim(rs.getString("receiver_bankname"))));
				 		transaction.setReceiverBankCode((StringUtils.trim(rs.getString("receiver_bankcode"))));
				 		transaction.setReceiverAccountNo((StringUtils.trim(rs.getString("receiver_accountno"))));
				 		transaction.setReceiverEmail((StringUtils.trim(rs.getString("receiver_email"))));
				 		transaction.setStatus(StringUtils.trim(rs.getString("status")));
				 		transaction.setTxnDateTime(StringUtils.trim(rs.getString("txndatetime")));
				 		transactions.add(transaction);
				 	}
			 }
			 if (transactions != null)
					if (transactions.size() == 0)
						transactions = null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getPatnersTransactions  is  "+e.getMessage());
			throw new Exception ("The exception in method getPatnersTransactions  is  "+e.getMessage());			
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

	public boolean editPartnerProfile(String fullName, String phoneNo, String location, String publicKey,
			String userId) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			//                                    1                 2                3          
			query = " update admin_details set  adminname=?, admincontact=? where adminid=? "; 
		    pstmt = connection.prepareStatement(query);
			pstmt.setString(1, Utilities.tripleEncryptData(fullName)); 						 
			pstmt.setString(2, Utilities.tripleEncryptData(phoneNo));						 
			pstmt.setString(3, Utilities.tripleEncryptData(userId));						 
								 
			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}
			
			if(pstmt!=null) pstmt.close();
			//                                       1           2               3          				
			query = " update partner_details set  location=?, stellarid=? where userid=? "; 
		    pstmt = connection.prepareStatement(query);
			pstmt.setString(1,  Utilities.tripleEncryptData(location)); 						 
			pstmt.setString(2,  Utilities.tripleEncryptData(publicKey));						 
			pstmt.setString(3,  Utilities.tripleEncryptData(userId));
			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}
				
			connection.commit();
			result = true;
		}catch(Exception e){
			connection.rollback();
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method editPartnerProfile  is  "+e.getMessage());
			throw new Exception ("The exception in method editPartnerProfile  is  "+e.getMessage());
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

	public boolean editUpdateTxnStatus(String systemRef, String comment, String status,String date) throws Exception {
		NeoBankEnvironment.setComment(3, className, "systemRef "+systemRef+" comment "+comment+" status "+status+"date "+date);
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			//                                               1                 2                3          
			query = " update txn_currency_remittance set  status=?, partners_comment=?,updatetxndatetime=? where sysreference_int=? "; 
		    pstmt = connection.prepareStatement(query);
			pstmt.setString(1, status); 						 
			pstmt.setString(2,  (comment));						 
			pstmt.setString(3, date);						 					 
			pstmt.setString(4, systemRef);
	 
			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}			
			connection.commit();
			result = true;
		}catch(Exception e){
			connection.rollback();
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method editUpdateTxnStatus  is  "+e.getMessage());
			throw new Exception ("The exception in method editUpdateTxnStatus  is  "+e.getMessage());
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

	public boolean updateTxnToStatus(String systemRef, String comment, String status)  throws Exception {
		NeoBankEnvironment.setComment(3, className, "systemRef "+systemRef+" comment "+comment+" status "+status);
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 
			//                                               1                 2                3          
			query = " update txn_currency_remittance set  status=?, partners_comment=? where sysreference_int=? "; 
		    pstmt = connection.prepareStatement(query);
			pstmt.setString(1, status); 						 
			pstmt.setString(2,  Utilities.tripleEncryptData(comment));						 
			pstmt.setString(3, systemRef);						 
								 
			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}
							
			connection.commit();
			result = true;
		}catch(Exception e){
			connection.rollback();
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method editUpdateTxnStatus  is  "+e.getMessage());
			throw new Exception ("The exception in method editUpdateTxnStatus  is  "+e.getMessage());
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
	
	public boolean checkUserPassword(String userId, String password) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String enPassword = null;
		boolean result = false;
		try{
			connection = super.getConnection();					
			query = "select adminpwd from admin_details where adminid = ? and accesstype = ?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1,  Utilities.tripleEncryptData(userId));
			pstmt.setString(2, "P");
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){ 
				 	while(rs.next()){	
						// This will get only 1 record
				 		enPassword = ( StringUtils.trim(rs.getString("adminpwd")) );
				 } // end of while
			 } //end of if rs!=null check	
			 
			 result = enPassword.equals( DigestUtils.md5Hex(Utilities.encryptString(password )));
			 
		}catch(Exception e){
			result=false;
			NeoBankEnvironment.setComment(1,className,"The exception in method checkUserPassword  is  "+e.getMessage());
			//throw new Exception ("The exception in method validateUserLogin  is  "+e.getMessage());			
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
	
	public boolean updatePassword(String password, String userId)  throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);			 
			query = " update admin_details set  adminpwd=? where adminid=? "; 
		    pstmt = connection.prepareStatement(query);
			pstmt.setString(1,DigestUtils.md5Hex( Utilities.encryptString(password ))); 						 
			pstmt.setString(2, Utilities.tripleEncryptData(userId));						 
			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}
			
			if(pstmt!=null) pstmt.close();
			//                                       1           2               3          				
			query = " update partner_details set  password_type=? where userid=? "; 
		    pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "P"); 						 
			pstmt.setString(2, Utilities.tripleEncryptData(userId));						 
			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}
							
			connection.commit();
			result = true;
		}catch(Exception e){
			connection.rollback();
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method updatePassword  is  "+e.getMessage());
			throw new Exception ("The exception in method updatePassword  is  "+e.getMessage());
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
	
	public List<Transaction> getTDAPendingTransactions() throws Exception {
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
					+ "	a.receiver_email receiver_email, a.status status, a.txndatetime txndatetime  "
					+ " from txn_currency_remittance a where a.status = ? order by txndatetime desc ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "PT"); 
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
				 		transaction.setCustomerId((StringUtils.trim(rs.getString("partner_userid"))));
				 		transaction.setPublicKey((StringUtils.trim(rs.getString("partner_stellar_id"))));
				 		transaction.setSystemReferenceExt(StringUtils.trim(rs.getString("stellar_txnhash")));
				 		transaction.setSenderComment((StringUtils.trim(rs.getString("sender_comment"))));
				 		transaction.setPartnersComment((StringUtils.trim(rs.getString("partners_comment"))));
				 		transaction.setReceiverName((StringUtils.trim(rs.getString("receiver_name"))));
				 		transaction.setReceiverBankName((StringUtils.trim(rs.getString("receiver_bankname"))));
				 		transaction.setReceiverBankCode((StringUtils.trim(rs.getString("receiver_bankcode"))));
				 		transaction.setReceiverAccountNo(StringUtils.trim(rs.getString("receiver_accountno")));
				 		transaction.setReceiverEmail((StringUtils.trim(rs.getString("receiver_email"))));
				 		transaction.setStatus(StringUtils.trim(rs.getString("status")));
				 		transaction.setTxnDateTime(StringUtils.trim(rs.getString("txndatetime")));
				 		transactions.add(transaction);
				 	}
			 }
			 if (transactions != null)
					if (transactions.size() == 0)
						transactions = null;
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
		return transactions;
	}
	
	public String totalAmountOfUnProcessedRemit() throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String totalSum = null;
		try{
			connection = super.getConnection();					
			query = " select sum(source_amount) from txn_currency_remittance where remittance_type = ? "
					+ " and status = ? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1,  "D");
			pstmt.setString(2, "PT");
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){ 
				 	while(rs.next()){	
						// This will get only 1 record
				 		totalSum = ( StringUtils.trim(rs.getString("sum(source_amount)")) );
				 } // end of while
			 } //end of if rs!=null check	
			 
		}catch(Exception e){
			totalSum=null;
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
		return totalSum;
	}	
	public boolean tdaUpadteRemittanceTxn(String systemRef, String txnHash, String tdaComment)  throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 
			 	//                                             1               2                3             4                       5
			query = " update txn_currency_remittance set  status=?, tda_comment=?, updatetxndatetime=?, stellar_txnhash=?  where sysreference_int=? "; 
		    pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "PP"); 						 
			pstmt.setString(2,  (tdaComment));						 
			pstmt.setString(3, Utilities.getMYSQLCurrentTimeStampForInsert());						 
			pstmt.setString(4, txnHash);						 
			pstmt.setString(5, systemRef);						 
								 
			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}
							
			connection.commit();
			result = true;
		}catch(Exception e){
			connection.rollback();
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method tdaUpadteRemittanceTxn  is  "+e.getMessage());
			throw new Exception ("The exception in method tdaUpadteRemittanceTxn  is  "+e.getMessage());
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
		public String getPublicKey(String userId) throws Exception {
			NeoBankEnvironment.setComment(3,className," Inside getPublicKey method");
			PreparedStatement pstmt=null;
			Connection connection = null;
			ResultSet rs=null;
			String query = null;
			String publicKey = null;
			try {
				connection = super.getConnection();	                    
				query = " select public_key from stellar_partner_account_relation where userid = ? ";
				
				NeoBankEnvironment.setComment(3,className,"After query");

				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, Utilities.tripleEncryptData(userId));
				
				rs = (ResultSet)pstmt.executeQuery();
				
				 if(rs!=null){
				 	while(rs.next()){	 
				 		publicKey = Utilities.tripleDecryptData(StringUtils.trim(rs.getString("public_key")));
				 		} 
				 } 
				 
				 NeoBankEnvironment.setComment(3,className,"After decrypt");
		
			}catch(Exception e) {
				publicKey = null;
				NeoBankEnvironment.setComment(1,className,"The exception in method getPublicKey  is  "+e.getMessage());		
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

			public List<Transaction> getLastTenPendingTxn(String partnerId) throws Exception {
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
							+ "	a.receiver_email receiver_email, a.status status, a.txndatetime txndatetime  "
							+ " from txn_currency_remittance a where a.status = ?  and a.partner_userid = ? order by txndatetime desc limit 5";
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, "PP"); 
					pstmt.setString(2, (partnerId)); 
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
						 		transaction.setCustomerId((StringUtils.trim(rs.getString("partner_userid"))));
						 		transaction.setPublicKey((StringUtils.trim(rs.getString("partner_stellar_id"))));
						 		transaction.setSystemReferenceExt(StringUtils.trim(rs.getString("stellar_txnhash")));
						 		transaction.setSenderComment((StringUtils.trim(rs.getString("sender_comment"))));
						 		transaction.setPartnersComment((StringUtils.trim(rs.getString("partners_comment"))));
						 		transaction.setReceiverName((StringUtils.trim(rs.getString("receiver_name"))));
						 		transaction.setReceiverBankName((StringUtils.trim(rs.getString("receiver_bankname"))));
						 		transaction.setReceiverBankCode((StringUtils.trim(rs.getString("receiver_bankcode"))));
						 		transaction.setReceiverAccountNo(StringUtils.trim(rs.getString("receiver_accountno")));
						 		transaction.setReceiverEmail((StringUtils.trim(rs.getString("receiver_email"))));
						 		transaction.setStatus(StringUtils.trim(rs.getString("status")));
						 		transaction.setTxnDateTime(StringUtils.trim(rs.getString("txndatetime")));
						 		transactions.add(transaction);
						 	}
					 }
					 if (transactions != null)
							if (transactions.size() == 0)
								transactions = null;
				}catch(Exception e){
					NeoBankEnvironment.setComment(1,className,"The exception in method getLastTenPendingTxn  is  "+e.getMessage());
					throw new Exception ("The exception in method getLastTenPendingTxn  is  "+e.getMessage());			
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
			
			public List<Transaction> getPatnersCompleteTenTransactions(String userId) throws Exception {
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
							+ "	a.receiver_email receiver_email, a.status status, a.txndatetime txndatetime  from txn_currency_remittance a where a.status = ?  and a.partner_userid = ? "
							+ "  order by txndatetime desc limit 5";
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, "C"); 
					pstmt.setString(2, (userId)); 
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
						 		transaction.setCustomerId((StringUtils.trim(rs.getString("partner_userid"))));
						 		transaction.setPublicKey((StringUtils.trim(rs.getString("partner_stellar_id"))));
						 		transaction.setSystemReferenceExt(StringUtils.trim(rs.getString("stellar_txnhash")));
						 		transaction.setSenderComment((StringUtils.trim(rs.getString("sender_comment"))));
						 		transaction.setPartnersComment((StringUtils.trim(rs.getString("partners_comment"))));
						 		transaction.setReceiverName((StringUtils.trim(rs.getString("receiver_name"))));
						 		transaction.setReceiverBankName((StringUtils.trim(rs.getString("receiver_bankname"))));
						 		transaction.setReceiverBankCode((StringUtils.trim(rs.getString("receiver_bankcode"))));
						 		transaction.setReceiverAccountNo((StringUtils.trim(rs.getString("receiver_accountno"))));
						 		transaction.setReceiverEmail((StringUtils.trim(rs.getString("receiver_email"))));
						 		transaction.setStatus(StringUtils.trim(rs.getString("status")));
						 		transaction.setTxnDateTime(StringUtils.trim(rs.getString("txndatetime")));
						 		transactions.add(transaction);
						 	}
					 }
					 if (transactions != null)
							if (transactions.size() == 0)
								transactions = null;
				}catch(Exception e){
					NeoBankEnvironment.setComment(1,className,"The exception in method getPatnersTransactions  is  "+e.getMessage());
					throw new Exception ("The exception in method getPatnersTransactions  is  "+e.getMessage());			
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


}
