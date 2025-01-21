package com.pporte.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import com.pporte.NeoBankEnvironment;
import com.pporte.model.Customer;
import com.pporte.model.DisputeTracker;
import com.pporte.model.AssetTransaction;
import com.pporte.model.Wallet;
import com.pporte.utilities.Utilities;

public class OpsManageCustomerDao extends HandleConnections {
	private static String className = OpsManageCustomerDao.class.getSimpleName();
	
	public ArrayList<Customer> getPendingCustomers() throws Exception {
		
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		String query = null;
		ArrayList<Customer> arrCustomer = null;
		String idNumber="";
		String passportNo="";
		try {
			connection = super.getConnection();
			
			query = "select relationshipno, customerid, customername, nationalid, passportno, custemail, custcontact, gender, address, "
					+ " dateofbirth, status, expiry, createdon from customer_details where status = ? order by createdon desc limit 1000 ";

			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "P");
			rs = (ResultSet) pstmt.executeQuery();
			if (rs != null) {
				arrCustomer = new ArrayList<Customer>();
				while (rs.next()) {
					Customer m_Customer = new Customer();
					m_Customer.setRelationshipNo(StringUtils.trim(rs.getString("relationshipno")));
					m_Customer.setCustomerId(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("customerid"))));
					m_Customer.setCustomerName(StringUtils.trim(rs.getString("customername")));
					idNumber=StringUtils.trim(rs.getString("nationalid"));
					passportNo=StringUtils.trim(rs.getString("passportno"));
					if (idNumber.equals("")) {
						m_Customer.setNationalId(idNumber);
					}else {
						m_Customer.setNationalId(Utilities.tripleDecryptData(idNumber));
					}
					if (passportNo.equals("")) {
						m_Customer.setPassportNo(passportNo);
					}else {
						m_Customer.setPassportNo(Utilities.tripleDecryptData(passportNo));
					}
					m_Customer.setEmail(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("custemail"))));
					m_Customer.setContact(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("custcontact"))));
					m_Customer.setGender(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("gender"))));
					m_Customer.setAddress(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("address"))));
					m_Customer.setDateOfBirth(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("dateofbirth"))));
					m_Customer.setStatus(StringUtils.trim(rs.getString("status")));
					m_Customer.setExpiry(StringUtils.trim(rs.getString("expiry")));
					m_Customer.setCreatedOn(StringUtils.trim(rs.getString("createdon")));
					arrCustomer.add(m_Customer);
				} // end of while

			} // end of if rs!=null check
			if (arrCustomer != null)
				if (arrCustomer.size() == 0)
					arrCustomer = null;
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1, className, "The exception in method getPendingCustomers is  " + e.getMessage());
			//throw new Exception("The exception in method getPendingCustomers is  " + e.getMessage());
		} finally {
			if (connection != null) {
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (passportNo!=null)passportNo=null;
			if (idNumber!=null)idNumber=null;
		}
		return arrCustomer;
	}
	
	

	public Customer getSpecificCustomerDetails(String relationshipNo) throws Exception {
			PreparedStatement pstmt=null;
			Connection connection = null;
			ResultSet rs=null;
			String query = null;
			Customer m_Customer = null;
			String passportNo="";
			String idNumber="";
			try{
				connection = super.getConnection();	
				
				query = "select relationshipno, customerid, customerid, customername, nationalid, passportno, custemail, custcontact, gender, address, "
						+ " dateofbirth, status, expiry, createdon, activated_on, approvedby from customer_details where relationshipno = ? ";

				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, relationshipNo);
				rs = (ResultSet)pstmt.executeQuery();

				 if(rs!=null){
				 		 m_Customer = new Customer();
					 	while(rs.next()){	
					 		m_Customer.setRelationshipNo(StringUtils.trim(rs.getString("relationshipno")));
							m_Customer.setCustomerId(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("customerid"))));
							m_Customer.setCustomerName(StringUtils.trim(rs.getString("customername")));
							idNumber=StringUtils.trim(rs.getString("nationalid"));
							passportNo=StringUtils.trim(rs.getString("passportno"));
							if (idNumber.equals("")) {
								m_Customer.setNationalId(idNumber);
							}else {
								m_Customer.setNationalId(Utilities.tripleDecryptData(idNumber));
							}
							if (passportNo.equals("")) {
								m_Customer.setPassportNo(passportNo);
							}else {
								m_Customer.setPassportNo(Utilities.tripleDecryptData(passportNo));
							}
							m_Customer.setEmail(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("custemail"))));
							m_Customer.setContact(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("custcontact"))));
							m_Customer.setGender(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("gender"))));
							m_Customer.setAddress(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("address"))));
							m_Customer.setDateOfBirth(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("dateofbirth"))));
							m_Customer.setStatus(StringUtils.trim(rs.getString("status")));
							m_Customer.setExpiry(StringUtils.trim(rs.getString("expiry")));
							m_Customer.setCreatedOn(StringUtils.trim(rs.getString("createdon")));
							m_Customer.setActivatedOn(StringUtils.trim(rs.getString("activated_on")));
							m_Customer.setApprovedBy(StringUtils.trim(rs.getString("approvedby")));

					 
					 	}
				 }		
			}catch(Exception e){
				NeoBankEnvironment.setComment(1,className,"The exception in method getSpecificCustomerDetails  is  "+e.getMessage());
				throw new Exception ("The exception in method getSpecificCustomerDetails  is  "+e.getMessage());			
			}finally{
				if(connection!=null)
					try {
						super.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if(rs!=null) rs.close();
					if(pstmt!=null) pstmt.close(); if (passportNo!=null)passportNo=null;
					if (idNumber!=null)idNumber=null;
				}		
			return m_Customer;
	}
	
	public ArrayList<String> getAllKYCDocsForCustomer(String relationshipNo) throws  Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<String> arrKYCDocsPath = null;
		try{
			connection = super.getConnection();	

			query = "select document_location from customer_kyc_docs where relationshipno =?  ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			rs = (ResultSet)pstmt.executeQuery();			
			 if(rs!=null){
				 arrKYCDocsPath  = new ArrayList<String>();
				 	while(rs.next()){	 
				 		arrKYCDocsPath.add(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("document_location"))))  ;
				 		} // end of while
				 	} //end of if rs!=null check
			 
			 if(arrKYCDocsPath!=null)
				 if(arrKYCDocsPath.size()==0)
					 arrKYCDocsPath=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllKYCDocsForCustomer()   is  "+e.getMessage());
			throw new Exception ("The exception in method getAllKYCDocsForCustomer()   is  "+e.getMessage());			
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
		return arrKYCDocsPath;		
	}
	
	public ArrayList<Customer> getCustomersDetails() throws Exception {
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		String query = null;
		ArrayList<Customer> arrCustomer = null;
		String idNumber="";
		String passportNo="";
		try {
			connection = super.getConnection();
			
			query = "select relationshipno, customerid, customername, nationalid, passportno, custemail, custcontact, gender, address, "
					+ " dateofbirth, status, expiry,logintries, txntries, createdon, approvedby from customer_details where status != ? order by createdon desc limit 1000 ";

			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "P");
			rs = (ResultSet) pstmt.executeQuery();
			if (rs != null) {
				arrCustomer = new ArrayList<Customer>();
				while (rs.next()) {
					Customer m_Customer = new Customer();
					m_Customer.setRelationshipNo(StringUtils.trim(rs.getString("relationshipno")));
					m_Customer.setCustomerId(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("customerid"))));
					m_Customer.setCustomerName(StringUtils.trim(rs.getString("customername")));
					idNumber=StringUtils.trim(rs.getString("nationalid"));
					passportNo=StringUtils.trim(rs.getString("passportno"));
					if (idNumber.equals("")) {
						m_Customer.setNationalId(idNumber);
					}else {
						m_Customer.setNationalId(Utilities.tripleDecryptData(idNumber));
					}
					if (passportNo.equals("")) {
						m_Customer.setPassportNo(passportNo);
					}else {
						m_Customer.setPassportNo(Utilities.tripleDecryptData(passportNo));
					}
					m_Customer.setEmail(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("custemail"))));
					m_Customer.setContact(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("custcontact"))));
					m_Customer.setGender(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("gender"))));
					m_Customer.setAddress(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("address"))));
					m_Customer.setDateOfBirth(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("dateofbirth"))));
					m_Customer.setStatus(StringUtils.trim(rs.getString("status")));
					m_Customer.setExpiry(StringUtils.trim(rs.getString("expiry")));
					m_Customer.setCreatedOn(StringUtils.trim(rs.getString("createdon")));
					m_Customer.setApprovedBy(StringUtils.trim(rs.getString("approvedby")));
					m_Customer.setLoginTries(StringUtils.trim(rs.getString("logintries")));
					m_Customer.setPinTries(StringUtils.trim(rs.getString("txntries")));
					arrCustomer.add(m_Customer);
				} // end of while

			} // end of if rs!=null check
			if (arrCustomer != null)
				if (arrCustomer.size() == 0)
					arrCustomer = null;
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1, className, "The exception in method getCustomersDetails is  " + e.getMessage());
			throw new Exception("The exception in method getCustomersDetails is  " + e.getMessage());
		} finally {
			if (connection != null) {
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close(); if (passportNo!=null)passportNo=null;
				if (idNumber!=null)idNumber=null;
		}
		return arrCustomer;
	}
	public boolean UpdateCustomerDetails(String customerCode, String natioanlId, String custPhoneNo, String custName, 
		String custEmail, String physicalAddress, String custDateOfBirth, String expiryDate, String approvedBy,
		String gender,String status,String custNewPassword) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 	//		 	                             1                  2             3                4             4
				query = "update customer_details set customername = ?, custcontact = ?, custemail = ?, nationalid = ?, gender = ?,"
						+ " address = ?, dateofbirth = ?,status = ?,expiry = ?, activated_on = ?, approvedby = ?,customerpwd=? where relationshipno = ? ";
		
			       	pstmt = connection.prepareStatement(query);
					pstmt.setString(1, custName);
					pstmt.setString(2, Utilities.tripleEncryptData(custPhoneNo) );
					pstmt.setString(3, Utilities.tripleEncryptData(custEmail.toLowerCase()) );
					pstmt.setString(4, Utilities.tripleEncryptData(natioanlId) );
					pstmt.setString(5,Utilities.tripleEncryptData(gender)  );
					pstmt.setString(6, Utilities.tripleEncryptData(physicalAddress) );
					pstmt.setString(7, Utilities.tripleEncryptData(custDateOfBirth) );
					pstmt.setString(8, status );
					pstmt.setString(9, expiryDate );
					pstmt.setString(10, Utilities.getMYSQLCurrentTimeStampForInsert());
					pstmt.setString(11, approvedBy);
					pstmt.setString(12, Utilities.encryptString(custNewPassword ));
					pstmt.setString(13,customerCode );
					
					try {
						pstmt.executeUpdate();
						}catch(Exception e) {
							throw new Exception (" failed query "+query+" "+e.getMessage());
						}
					pstmt.close();
					connection.commit();			 	
					result = true;
		}catch(Exception e){
			connection.rollback();
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method UpdateCustomerDetails  is  "+e.getMessage());
			throw new Exception ("The exception in method UpdateCustomerDetails  is  "+e.getMessage());
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



	public ArrayList<Wallet> getWalletDetails(String relationId) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<Wallet> arrWallet = null;
		
		try{
			connection = super.getConnection();	
			                    //  1         2           3          4        5        6           7              8
				query = "select walletid, relationshipno, walletdesc, usertype,  status, currbal, currencyid,  lastupdated "
						+ " from wallet_details  where relationshipno=? limit ? ";
				
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationId);
			pstmt.setInt(2, 1);
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){
				 arrWallet = new ArrayList<Wallet>();
				 	while(rs.next()){	 
				 		Wallet wallet=new Wallet();
				 		wallet.setWalletId( StringUtils.trim(rs.getString("walletid"))    );
				 		wallet.setUserType( StringUtils.trim(rs.getString("usertype"))  );
				 		wallet.setRelationshipNo( StringUtils.trim(rs.getString("relationshipno"))  );
				 		wallet.setWalletDesc(StringUtils.trim(rs.getString("walletdesc")) );
				 		wallet.setCurrentBalance(  StringUtils.trim(rs.getString("currbal"))    );
				 		wallet.setCurrencyId(StringUtils.trim(rs.getString("currencyid"))  );
				 		wallet.setStatus(StringUtils.trim(rs.getString("status"))  );
				 		wallet.setLastUpdated(StringUtils.trim(rs.getString("lastupdated"))  );
				 		arrWallet.add(wallet);
				 		} 					 	
				 	} 
			 if(arrWallet!=null)
				 if(arrWallet.size()==0)
					 arrWallet=null;
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getWalletDetails  is  "+e.getMessage());
			throw new Exception ("The exception in method getWalletDetails  is  "+e.getMessage());			
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
		return arrWallet;
	}

	public ArrayList<AssetTransaction> getAllTransactionsForWallet(String walletId) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<AssetTransaction> arrTransaction = null;
		try{
			connection = super.getConnection();	
			
			query = "select  txncode, walletid, sysreference, txnamount, txncurrencyid,  txnmode,txndatetime, txnusercode "
					+ " from txn_wallet_cust_bc where walletid=? order by txndatetime desc limit 100  ";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, walletId);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 arrTransaction = new ArrayList<AssetTransaction>();
				 
				 	while(rs.next()){
				 		AssetTransaction m_Transaction=new AssetTransaction();
				 		
				 		m_Transaction.setTxnCode( StringUtils.trim(rs.getString("txncode"))  );
				 		m_Transaction.setMerchantWalletId(StringUtils.trim(rs.getString("walletid"))  );
				 		m_Transaction.setSystemReferenceInt(StringUtils.trim(rs.getString("sysreference"))  );
				 		m_Transaction.setTxnAmount(StringUtils.trim(rs.getString("txnamount"))  );
				 		m_Transaction.setTxnCurrencyId(StringUtils.trim(rs.getString("txncurrencyid"))  );
				 		m_Transaction.setTxnMode(StringUtils.trim(rs.getString("txnmode"))  );
				 		m_Transaction.setTxnDateTime(StringUtils.trim(rs.getString("txndatetime"))  );
				 		m_Transaction.setTxnUserCode(StringUtils.trim(rs.getString("txnusercode")));
				 		arrTransaction.add(m_Transaction);
				 		} 			 	
				 	} 
			 if(arrTransaction!=null)
				 if(arrTransaction.size()==0)
					 arrTransaction=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllTransactionsForWallet  is  "+e.getMessage());
			throw new Exception ("The exception in method getAllTransactionsForWallet  is  "+e.getMessage());			
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
		
		return arrTransaction;
	}



	public ArrayList<Customer> getOpsSpecificCustomerDetails(String customerName, String relationshipNo, String customerId,
			String mobileNo)throws Exception {
		    PreparedStatement pstmt=null;
			Connection connection = null;
			ResultSet rs=null;
			String query = null;
			ArrayList<Customer> arrCustomer = null;
			String idNumber="";
			String passportNo="";
			try {
				NeoBankEnvironment.setComment(3,className,"in  getOpsSpecificCustomerDetails searched relationshipNo is "+
						relationshipNo+ "searched customerId is  "+customerId+ "searched custName  "+ customerName + "phoneNumber is  "+ mobileNo            );

				connection = super.getConnection();	                    

				query = "select relationshipno, customerid, customername, nationalid, passportno, custemail, custcontact, gender, address, "
						+ " dateofbirth, status, expiry,logintries, txntries, createdon, approvedby from customer_details where status != ? and ";

				if(relationshipNo.equals("")==false) {	
					query+= "  relationshipno = ? and  ";
				}else {
					if(customerName.equals("")==false) {
						query+= "  customername like '%"+customerName+"%' and  ";
					}
						if(customerId.equals("")==false) {
							query+= "  customerid =? and  ";
						}
						if(mobileNo.equals("")==false) {
							query+= "custcontact =? and  ";
						}
				}
				query+= "  1=1 ";
				
				NeoBankEnvironment.setComment(3,className,"search query being executed  is  "+query);


				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, "P");

				if(relationshipNo.equals("")==false) {
					pstmt.setString(2, relationshipNo);
				}
				if(customerId.equals("")==false) {
					pstmt.setString(2, Utilities.tripleEncryptData(customerId));
				}
				if(mobileNo.equals("")==false) {
					pstmt.setString(2, Utilities.tripleEncryptData(mobileNo));
				}
				
				rs = (ResultSet)pstmt.executeQuery();
				
				 if(rs!=null){
					 arrCustomer = new ArrayList<Customer>();
					 	while(rs.next()){	 
					 		Customer m_Customer = new Customer();
							m_Customer.setRelationshipNo(StringUtils.trim(rs.getString("relationshipno")));
							m_Customer.setCustomerId(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("customerid"))));
							m_Customer.setCustomerName(StringUtils.trim(rs.getString("customername")));
							idNumber=StringUtils.trim(rs.getString("nationalid"));
							passportNo=StringUtils.trim(rs.getString("passportno"));
							if (idNumber.equals("")) {
								m_Customer.setNationalId(idNumber);
							}else {
								m_Customer.setNationalId(Utilities.tripleDecryptData(idNumber));
							}
							if (passportNo.equals("")) {
								m_Customer.setPassportNo(passportNo);
							}else {
								m_Customer.setPassportNo(Utilities.tripleDecryptData(passportNo));
							}
							m_Customer.setEmail(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("custemail"))));
							m_Customer.setContact(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("custcontact"))));
							m_Customer.setGender(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("gender"))));
							m_Customer.setAddress(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("address"))));
							m_Customer.setDateOfBirth(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("dateofbirth"))));
							m_Customer.setStatus(StringUtils.trim(rs.getString("status")));
							m_Customer.setExpiry(StringUtils.trim(rs.getString("expiry")));
							m_Customer.setCreatedOn(StringUtils.trim(rs.getString("createdon")));
							m_Customer.setApprovedBy(StringUtils.trim(rs.getString("approvedby")));
							m_Customer.setLoginTries(StringUtils.trim(rs.getString("logintries")));
							m_Customer.setPinTries(StringUtils.trim(rs.getString("txntries")));
							arrCustomer.add(m_Customer);
							
							//if(arrCustomer != null) { NeoBankEnvironment.setComment(3,className,"arrCustomer found  is  "+arrCustomer.size()); }
					 		} 

					 	} 
				 if(arrCustomer!=null)
					 if(arrCustomer.size()==0)
						 arrCustomer=null;	
				
			}catch(Exception e) {
				NeoBankEnvironment.setComment(1,className,"The exception in method getOpsSpecificCustomerDetails  is  "+e.getMessage());
				throw new Exception ("The exception in method getOpsSpecificCustomerDetails  is  "+e.getMessage());			
			}finally{
				if(connection!=null)
					try {
						super.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if(rs!=null) rs.close();
					if(pstmt!=null) pstmt.close();
					if (passportNo!=null); passportNo=null; if (idNumber!=null)idNumber=null;
				}
		
			return arrCustomer;
	}

	public boolean OpsUnblockedCustPin(String customerCode) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 								//		   1			  		2		
			 query = " update customer_details set  txntries=? where relationshipno = ?";
			 
					pstmt = connection.prepareStatement(query);
					pstmt.setInt(1,0); 					
					pstmt.setString(2,customerCode); 					
					try {
						pstmt.executeUpdate();
						}catch(Exception e) {
							throw new Exception (" failed query "+query+" "+e.getMessage());
						}
					connection.commit();
					result = true;
		}catch(Exception e){
			connection.rollback(); result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method OpsUnblockedCustPin  is  "+e.getMessage());
			throw new Exception ("The exception in method OpsUnblockedCustPin  is  "+e.getMessage());
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
	
	public boolean OpsUnlockCustPass(String customerCode) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 								//		   1			  		2		
			 query = " update customer_details set  logintries=? where relationshipno = ?";
			 
					pstmt = connection.prepareStatement(query);
					pstmt.setInt(1,0); 					
					pstmt.setString(2,customerCode); 					
					try {
						pstmt.executeUpdate();
						}catch(Exception e) {
							throw new Exception (" failed query "+query+" "+e.getMessage());
						}
					connection.commit();
					result = true;
		}catch(Exception e){
			connection.rollback(); result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method OpsUnlockCustPass  is  "+e.getMessage());
			throw new Exception ("The exception in method OpsUnlockCustPass  is  "+e.getMessage());
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

	public ArrayList<DisputeTracker> getActiveDisputes() throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<DisputeTracker> arrDisputes = null;
		
		try{
			connection = super.getConnection();	
			query = "select a.disputeid disputeid, a.reasonid reasonid,  a.raisedbyentityno raisedbyentityno, a.usertype usertype, a.status status, a.raisedondate raisedondate, a.referenceno referenceno," + 
					"b.reasondesc reasondesc from dispute_details  a, dispute_reason b where a.reasonid = b.reasonid  and a.status = ? order by raisedondate desc LIMIT 10";
			
			pstmt = connection.prepareStatement(query); 
			pstmt.setString(1, "A"); 
			rs = (ResultSet)pstmt.executeQuery();	
			
			 if(rs!=null){
				 arrDisputes = new ArrayList<DisputeTracker>();
				 	while(rs.next()){	 
				 		DisputeTracker m_DispReason=new DisputeTracker();
				 		m_DispReason.setDisputeId( StringUtils.trim(rs.getString("disputeid"))    );
				 		m_DispReason.setRaisedbyUserId( StringUtils.trim(rs.getString("raisedbyentityno"))  );
				 		m_DispReason.setUserType(StringUtils.trim(rs.getString("usertype"))    );
						m_DispReason.setStatus( StringUtils.trim(rs.getString("status"))    );	
				 		m_DispReason.setReasonDesc( StringUtils.trim(rs.getString("reasondesc")) );
				 		m_DispReason.setRaisedOn(StringUtils.trim(rs.getString("raisedondate"))    );
				 		m_DispReason.setRefNo(StringUtils.trim(rs.getString("referenceno")) );
				 		arrDisputes.add(m_DispReason);
				}
			}
			 pstmt.close();
			 if(arrDisputes!=null) if(arrDisputes.size()==0) arrDisputes=null;
		}catch(Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getActiveDisputes  is  "+e.getMessage());
			//throw new Exception ("The exception in method getActiveDisputes  is  "+e.getMessage());
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
		return arrDisputes;
	}
	public boolean UpdateCustomerWithoutPasswordDetails(String customerCode, String natioanlId, String custPhoneNo, String custName, 
			String custEmail, String physicalAddress, String custDateOfBirth, String expiryDate, String approvedBy,
			String gender,String status, String passportNo) throws Exception{
			PreparedStatement pstmt=null;
			Connection connection = null;
			String query = null;
			boolean result = false;
			try{
				 connection = super.getConnection();
				 connection.setAutoCommit(false);
				 	//		 	                             1                  2             3                4             4
					query = "update customer_details set customername = ?, custcontact = ?, custemail = ?, nationalid = ?, gender = ?,"
							+ " address = ?, dateofbirth = ?,status = ?,expiry = ?, activated_on = ?, approvedby = ?, passportno=? where relationshipno = ? ";
			
				       	pstmt = connection.prepareStatement(query);
						pstmt.setString(1, custName );
						pstmt.setString(2, Utilities.tripleEncryptData(custPhoneNo ));
						pstmt.setString(3, Utilities.tripleEncryptData(custEmail ) );
						if (natioanlId.equals("")) {
							pstmt.setString(4, natioanlId );
						}else {
							pstmt.setString(4, Utilities.tripleEncryptData(natioanlId ) );
						}
						pstmt.setString(5, Utilities.tripleEncryptData(gender)  );
						pstmt.setString(6, Utilities.tripleEncryptData(physicalAddress ) );
						pstmt.setString(7, Utilities.tripleEncryptData(custDateOfBirth ) );
						pstmt.setString(8, status );
						pstmt.setString(9, expiryDate );
						pstmt.setString(10, Utilities.getMYSQLCurrentTimeStampForInsert());
						pstmt.setString(11, approvedBy);
						if (passportNo.equals("")) {
							pstmt.setString(12, passportNo );
						}else {
							pstmt.setString(12, Utilities.tripleEncryptData(passportNo ) );
						}
						pstmt.setString(13,customerCode );
						
						try {
							pstmt.executeUpdate();
							}catch(Exception e) {
								throw new Exception (" failed query "+query+" "+e.getMessage());
							}
						pstmt.close();
						connection.commit();			 	
						result = true;
			}catch(Exception e){
				connection.rollback();
				result = false;
				NeoBankEnvironment.setComment(1,className,"The exception in method UpdateCustomerWithoutPasswordDetails  is  "+e.getMessage());
				throw new Exception ("The exception in method UpdateCustomerWithoutPasswordDetails  is  "+e.getMessage());
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

	public boolean approveNewCustomer(String relationshipNo, String userId) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		String currentTime = null;
		String walletId =null;
		SimpleDateFormat formatter1=null;
		String walletType=null;
		String planId=null;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 currentTime=Utilities.getMYSQLCurrentTimeStampForInsert();
			 walletType= "F"; //Fiat wallet
			 planId="0"; // Default Plan for a new customer is 0
			 
			 	//		 	                              1              2             3                      4          
				query = "update customer_details set status = ?, activated_on = ?, approvedby = ?  where relationshipno = ? ";
		
			       	pstmt = connection.prepareStatement(query);
					pstmt.setString(1, "A" );
					pstmt.setString(2, Utilities.getMYSQLCurrentTimeStampForInsert());
					pstmt.setString(3, userId );
					pstmt.setString(4, relationshipNo);
					
					try {
						pstmt.executeUpdate();
						
						}catch(Exception e) {
							throw new Exception (" failed query "+query+" "+e.getMessage());
						}
					pstmt.close();
					
					formatter1 = new SimpleDateFormat ("yyMMdd");
					formatter1.setTimeZone(TimeZone.getTimeZone("UTC"));
					
					walletId = (formatter1.format(new Date()))+( RandomStringUtils.random(10, false, true)).toString();
				    walletType= "F"; //Fiat wallet
										                 //		1         2				3		 		 4			5		    6            7         8          9           10        11          
					query = "insert into wallet_details (walletid, relationshipno, walletdesc, 	usertype, 	status,   currbal,  currencyid, lastupdated, createdon, wallettype, blockcodeid)  "
					+ "  values (?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?)  ";
					         //  1  2  3  4  5  6  7  8  9  10 11
					pstmt = connection.prepareStatement(query);					
					pstmt.setString(1, walletId); 
					pstmt.setString(2, relationshipNo ); 
					pstmt.setString(3, relationshipNo +"-"+walletType );
					pstmt.setString(4, "C" ); // Customer
					pstmt.setString(5, "A" ); // Active
					pstmt.setBigDecimal(6,  new BigDecimal("0")     );
					pstmt.setString(7, NeoBankEnvironment.getUSDCurrencyId()); 
					pstmt.setString(8, currentTime ); 
					pstmt.setString(9, currentTime   ); 
					pstmt.setString(10, walletType); 
					pstmt.setInt(11, 1   ); //Default BlockCode is 1; 
					try {
					pstmt.executeUpdate();
					
					}catch (Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}
					// Set default plan for newly registered user
				
					  //                                                      1			2			3				4	   5
					 query = "insert into customer_price_plan_allocation (plan_id, customerid,  plan_start_date,  status, reason ) "
								+ "values (?, ?, ?, ?, ?) ";												
						pstmt = connection.prepareStatement(query);
						pstmt.setInt(1, Integer.parseInt(planId)); 
						pstmt.setString(2, relationshipNo); 
						pstmt.setString(3, Utilities.getMYSQLCurrentTimeStampForInsert()); 	
						pstmt.setString(4, "A"); // status A by default with allocation of the plan to the customer  
						pstmt.setString(5, NeoBankEnvironment.getDefaultPlanReason());   
						
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
			NeoBankEnvironment.setComment(1,className,"The exception in method" +Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());		
		}finally{
		if(connection!=null)
			try {
				super.close();
			} catch (SQLException e) {
				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
			if(pstmt!=null) pstmt.close();
			if (currentTime!=null)currentTime=null; if (walletType!=null)walletType=null; if (walletId!=null)walletId=null;
			if (formatter1!=null) formatter1=null;
		}
		return result;	
	}



	public boolean checkIfDocumentsHasBeenReviewedByOps(String relationshipNo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		Customer m_CustDetails=null;
		ArrayList<Customer> arrCustDetails=null;
		boolean result = false;
		try{
			connection = super.getConnection();
			query = "select status from customer_kyc_docs where relationshipno = ? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				arrCustDetails= new ArrayList<Customer>();
				while(rs.next()){	
					m_CustDetails=new Customer();
					m_CustDetails.setStatus(StringUtils.trim(rs.getString("status"))); // Download status
					arrCustDetails.add(m_CustDetails);
				 	} // end of while
				 } //end of if

			 if(arrCustDetails != null) {
				 // Check if arraylist contains the status "N" or not
				 int count=arrCustDetails.size();
				 NeoBankEnvironment.setComment(3,className,"Count is "+count);
				 for (int i=0;i<count;i++){
					  if(((Customer)arrCustDetails.get(i)).getStatus().equals("N")){
						  // Has not been renewed
					   return false;
					  }
					 }
					return true;
			 }
		}catch(Exception e){
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method" +Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());	
		}finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if (arrCustDetails!=null)arrCustDetails=null;
				if (m_CustDetails!=null)m_CustDetails=null;
			}
		return result;
	}
}
