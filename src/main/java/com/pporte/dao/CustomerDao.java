package com.pporte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import com.pporte.NeoBankEnvironment;
import com.pporte.model.Customer;
import com.pporte.model.Days;
import com.pporte.utilities.Utilities;

public class CustomerDao extends HandleConnections{
	public static String className = CustomerDao.class.getSimpleName();
	
	
	public Customer getFullCustomerProfile(String relationshipNo) throws Exception {
		
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		Customer m_Customer=null;
		String status=null;
		String gender=null;
		String licenseNo="";
		String passportNo="";
		try {
				connection = super.getConnection();	
			 	                                  
				query = "select relationshipno , customername ,custemail, custcontact,address, dateofbirth,"
						+ "nationalid,passportno,gender,createdon,status,customerid "
						+ "from customer_details where relationshipno=?";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			rs = (ResultSet)pstmt.executeQuery();

			 if(rs!=null){
				 m_Customer=new Customer();
				 	while(rs.next()){
				 		m_Customer.setRelationshipNo( StringUtils.trim(rs.getString("relationshipno")));
				 		m_Customer.setName( StringUtils.trim(rs.getString("customername")));
				 		m_Customer.setEmail(Utilities.maskEmailAddress( Utilities.tripleDecryptData( StringUtils.trim(rs.getString("custemail"))))); 
				 		m_Customer.setUnmaskedEmail(( Utilities.tripleDecryptData( StringUtils.trim(rs.getString("custemail"))))); 
				 		m_Customer.setContact(Utilities.maskMobileNumber(Utilities.tripleDecryptData(  StringUtils.trim(rs.getString("custcontact"))))); 
				 		m_Customer.setAddress(Utilities.tripleDecryptData(  StringUtils.trim(rs.getString("address")))); 
				 		m_Customer.setDateOfBirth(Utilities.tripleDecryptData(  StringUtils.trim(rs.getString("dateofbirth"))));
					 		licenseNo=StringUtils.trim(rs.getString("nationalid"));
					 		passportNo=StringUtils.trim(rs.getString("passportno"));
					 		if(licenseNo.equals("")) {
						 		m_Customer.setNationalId(licenseNo);
					 		}else {
						 		m_Customer.setNationalId(Utilities.tripleDecryptData( licenseNo));
					 		}
					 		if(passportNo.equals("")) {
					 			m_Customer.setPassportNo(passportNo);
					 		}else {
					 			m_Customer.setPassportNo(Utilities.tripleDecryptData(  passportNo));
					 		}
				 		m_Customer.setCustomerId(Utilities.maskEmailAddress( Utilities.tripleDecryptData( StringUtils.trim(rs.getString("customerid")))));
				 		status = StringUtils.trim(rs.getString("status"));
				 		    if(status.contains("A")){
				 			m_Customer.setStatus("Active");
				 		}else if(status.contains("I")) {
				 			m_Customer.setStatus("Inactive");
				 		}else if(status.contains("P")) {
				 			m_Customer.setStatus("Pending");
				 		}else if(status.contains("S")) {
				 			m_Customer.setStatus("Suspended");
				 		}
			 		   gender = Utilities.tripleDecryptData( StringUtils.trim(rs.getString("gender")));
				 		    if(gender.contains("M")){
				 		    	m_Customer.setGender("Male");
				 		    }else if(gender.contains("F")) {
				 		    	m_Customer.setGender("Female");
				 		    }else if(gender.contains("O")) {
				 		    	m_Customer.setGender("Other");
				 		    }
			
				 		} // end of while
				 	} //end of if rs!=null check
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getFullCustomerProfile  is  "+e.getMessage());
			throw new Exception ("The exception in method getFullCustomerProfile  is  "+e.getMessage());			
		}finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close(); if (licenseNo!=null)licenseNo=null;
				if (passportNo!=null)passportNo=null;
			}
		return m_Customer;
	}

	public boolean updateCustomerDetails(String relationshipNo, String address) throws Exception {
		
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);

				 //                                        1                 2           
				 query = "update customer_details set   address=? where relationshipno=? "; 
				    pstmt = connection.prepareStatement(query);					 
					pstmt.setString(1, Utilities.tripleEncryptData(address));					 
					pstmt.setString(2, relationshipNo);	
										 
				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}

					connection.commit();
					result = true;
		}catch(Exception e){
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method updateCustomerProfile  is  "+e.getMessage());
			throw new Exception ("The exception in method updateCustomerProfile  is  "+e.getMessage());
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
	
	

	
	
	public boolean checkIfEmailExist(String emailId)  throws Exception{
		 NeoBankEnvironment.setComment(3,className,"email id is "+emailId);
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String tmpEmail = null;
		//PPWalletEnvironment.setComment(3,className,"emailId  is  "+emailId);
		boolean result = false;
		try{
			connection = super.getConnection();
			query = " select  custemail from customer_details   where custemail = ? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, Utilities.tripleEncryptData(emailId.toLowerCase()));
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				while(rs.next()){	
				 	tmpEmail = StringUtils.trim(rs.getString("custemail"));
				 	} // end of while
				 } //end of if

			 if(tmpEmail != null) {
				 result = true;
			 }
		}catch(Exception e){
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method checkIfEmailExist  is  "+e.getMessage());	
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
	
	public boolean checkIfHasWallet(String assetCode, String relationshipNo)  throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String walletId = null;
		//PPWalletEnvironment.setComment(3,className,"emailId  is  "+emailId);
		boolean result = false;
		try{
			connection = super.getConnection();
			query = " select  walletid from wallet_details_external   where relationshipno = ? and assetcode = ? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, assetCode);
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				while(rs.next()){	
					walletId = StringUtils.trim(rs.getString("walletid"));
				 	} // end of while
				 } //end of if
			 
			 if(walletId != null) {
				 result = true;
			 }
		}catch(Exception e){
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method checkIfHasWallet  is  "+e.getMessage());	
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

	public ArrayList<Customer> getAllCustDetailsForRegistration(String customerEmail) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<Customer> arrCustomer=null;
		try {
			connection = super.getConnection();	
			 	       
			query= "select a.customername customername,  a.custemail custemail,a.relationshipno relationshipno, b. walletid walletid from customer_details a,"
					+ " wallet_details b where a.relationshipno=b.relationshipno and a.custemail=? and b.wallettype=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, Utilities.tripleEncryptData(customerEmail.toLowerCase()));
			pstmt.setString(2, ("F"));
			rs = (ResultSet)pstmt.executeQuery();

			 if(rs!=null){
				 arrCustomer = new ArrayList<Customer>();
				 	while(rs.next()){	 
				 		Customer m_Customer=new Customer();
				 		m_Customer.setCustomerName( StringUtils.trim(rs.getString("customername")));
				 		m_Customer.setEmail( Utilities.tripleDecryptData( StringUtils.trim(rs.getString("custemail"))));
				 		m_Customer.setRelationshipNo( StringUtils.trim(rs.getString("relationshipno")));
				 		m_Customer.setWalletId( StringUtils.trim(rs.getString("walletid")));
				 		arrCustomer.add(m_Customer);
				 		} // end of while
				 	} //end of if rs!=null check
			 if(arrCustomer!=null) {
				 NeoBankEnvironment.setComment(3,className," is arrCustomer size is  "+arrCustomer.size());
			 }
			 if(arrCustomer!=null) if(arrCustomer.size()==0) arrCustomer=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllCustDetailsForRegistration  is  "+e.getMessage());
			throw new Exception ("The exception in method getAllCustDetailsForRegistration  is  "+e.getMessage());			
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

	public boolean insertReceiverWalletForRegistration(String receiverWalletId, String senderRegNo, String receiverRelationNo) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		try{

			connection = super.getConnection();	
			 connection.setAutoCommit(false);
			  //                                                       1             2                     3                  4         5
			 query = "insert into customer_receiver_wallet_rel (relationshipno, receiverwalletid, receiver_relationshipno,  status , createdon) "
						+ "values (?, ?, ?, ? , ?) ";
						//		   1  2  3  4  5
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, (senderRegNo)); 
				pstmt.setString(2, ( receiverWalletId)); 
				pstmt.setString(3, ( receiverRelationNo)); 				
				pstmt.setString(4, ("A")); 
				pstmt.setString(5,  Utilities.getMYSQLCurrentTimeStampForInsert() ); 

				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}	
			connection.commit();
			result = true;

		}catch(Exception e){
			connection.rollback(); result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method insertReceiverWalletForRegistration  is  "+e.getMessage());
			throw new Exception ("The exception in method insertReceiverWalletForRegistration  is  "+e.getMessage());			
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

	public ArrayList<Customer> getAllRegisteredWalletsForASender(String relationshipNo) throws Exception {
		NeoBankEnvironment.setComment(3,className," Inside getAllRegisteredWalletsForSender method");
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<Customer> arrRegisteredrReceiverDetails = null;
		try {
			connection = super.getConnection();	                    
			query = " select a.relationshipno receiverrelationshipno , a.customername receivercustomername, a.custemail receiveremail, a.custcontact receivercontact, b.walletid receiverwalletid "
					+ " from customer_details a, wallet_details b where a.relationshipno=b.relationshipno and b.walletid in "
					+ " (select receiverwalletid from customer_receiver_wallet_rel where relationshipno = ? and status=?)";
			
			NeoBankEnvironment.setComment(3,className,"After query");

			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, "A");
			
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){
				 arrRegisteredrReceiverDetails = new ArrayList<Customer>();
				 	while(rs.next()){	 
				 		Customer m_CustomerDetails=new Customer();
				 		m_CustomerDetails.setRelationshipNo( StringUtils.trim(rs.getString("receiverrelationshipno"))    );
				 		m_CustomerDetails.setWalletId( StringUtils.trim(rs.getString("receiverwalletid"))    );
				 		m_CustomerDetails.setCustomerName(StringUtils.trim(rs.getString("receivercustomername"))  );
				 		m_CustomerDetails.setEmail(Utilities.tripleDecryptData( StringUtils.trim(rs.getString("receiveremail"))    ));
				 		m_CustomerDetails.setContact( Utilities.tripleDecryptData( StringUtils.trim(rs.getString("receivercontact"))    ));
				 		arrRegisteredrReceiverDetails.add(m_CustomerDetails);
				 		} 
				 	//NeoBankEnvironment.setComment(3,className," arrRegisteredrReceiverDetails " + arrRegisteredrReceiverDetails.size()  );
				 	} 
		
			
		}catch(Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllRegisteredWalletsForASender  is  "+e.getMessage());
			throw new Exception ("The exception in method getAllRegisteredWalletsForASender  is  "+e.getMessage());			
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
	
		return arrRegisteredrReceiverDetails;
	}
	public ArrayList<Customer> getAllRegisteredWalletsForASenderWeb(String relationshipNo) throws Exception {
		NeoBankEnvironment.setComment(3,className," Inside getAllRegisteredWalletsForSender method");
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<Customer> arrRegisteredrReceiverDetails = null;
		try {
			connection = super.getConnection();	                    
			query = " select a.relationshipno receiverrelationshipno , a.customername receivercustomername, a.custemail receiveremail, a.custcontact receivercontact, b.walletid receiverwalletid "
					+ " from customer_details a, wallet_details b where a.relationshipno=b.relationshipno and b.walletid in "
					+ " (select receiverwalletid from customer_receiver_wallet_rel where relationshipno = ? and status=?)";
			
			NeoBankEnvironment.setComment(3,className,"After query");
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, "A");
			
			rs = (ResultSet)pstmt.executeQuery();
			
			if(rs!=null){
				arrRegisteredrReceiverDetails = new ArrayList<Customer>();
				while(rs.next()){	 
					Customer m_CustomerDetails=new Customer();
					m_CustomerDetails.setRelationshipNo( StringUtils.trim(rs.getString("receiverrelationshipno"))    );
					m_CustomerDetails.setWalletId( StringUtils.trim(rs.getString("receiverwalletid"))    );
					m_CustomerDetails.setCustomerName(StringUtils.trim(rs.getString("receivercustomername"))  );
					m_CustomerDetails.setEmail(Utilities.tripleDecryptData( StringUtils.trim(rs.getString("receiveremail"))    ));
					m_CustomerDetails.setContact( Utilities.tripleDecryptData( StringUtils.trim(rs.getString("receivercontact"))    ));
					arrRegisteredrReceiverDetails.add(m_CustomerDetails);
				} 
				//NeoBankEnvironment.setComment(3,className," arrRegisteredrReceiverDetails " + arrRegisteredrReceiverDetails.size()  );
			} 
			
			 if(arrRegisteredrReceiverDetails!=null) if(arrRegisteredrReceiverDetails.size()==0) arrRegisteredrReceiverDetails=null;
		}catch(Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllRegisteredWalletsForASenderWeb  is  "+e.getMessage());
			throw new Exception ("The exception in method getAllRegisteredWalletsForASenderWeb  is  "+e.getMessage());			
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
		
		return arrRegisteredrReceiverDetails;
	}
	

	public String getPublicKey(String relationshipNo) throws Exception {
		NeoBankEnvironment.setComment(3,className," Inside getAllRegisteredWalletsForSender method");
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String publicKey = null;
		try {
			connection = super.getConnection();	                    
			query = " select public_key from stellar_account_relation where relationshipno = ? ";
			
			NeoBankEnvironment.setComment(3,className,"After query");

			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){
			 	while(rs.next()){	 
			 		publicKey = Utilities.tripleDecryptData(StringUtils.trim(rs.getString("public_key")));
			 		} 
			 } 
			 
			 NeoBankEnvironment.setComment(3,className,"inside getPublicKey public key is "+publicKey);
	
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
	public ArrayList<Days>  getLastFiveteenDayFiatGraphOps(String relationshipNo )throws Exception {
		//get retailpay transactions for all users for the past 7 days
		
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		Days daysOfTheWeek = null;
		ArrayList<Days> arrWeeklyTransactions = null;
		String tomorrowsDate= null;		String dayOne = null;		String dayTwo= null;		String dayThree = null;
		String dayFour = null;		String dayFive = null;		String daySix = null;		String daySeven = null;
		String dayEight = null; String dayNine = null; String dayTen = null; String dayEleven = null; String dayTwelve = null;
		String dayThirteen = null; String dayFourteen = null; String dayFifteen = null;
		
		try{
			connection = super.getConnection();	
			//todayMidnight=Utilities.getUTCTimeMidnight();
			tomorrowsDate= Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), +1, "yyyy-MM-dd");
			dayOne = Utilities.getMYSQLCurrentTimeStampForDashGraphs();
			dayTwo = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -1, "yyyy-MM-dd");
			dayThree = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -2, "yyyy-MM-dd");
			dayFour = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -3, "yyyy-MM-dd");
			dayFive = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -4, "yyyy-MM-dd");
			daySix = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -5, "yyyy-MM-dd");
			daySeven = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -6, "yyyy-MM-dd");
			dayEight = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -7, "yyyy-MM-dd");
			dayNine = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -8, "yyyy-MM-dd");
			dayTen = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -9, "yyyy-MM-dd");
			dayEleven = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -10, "yyyy-MM-dd");
			dayTwelve = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -11, "yyyy-MM-dd");
			dayThirteen = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -12, "yyyy-MM-dd");
			dayFourteen = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -13, "yyyy-MM-dd");
			dayFifteen = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -14, "yyyy-MM-dd");

			NeoBankEnvironment.setComment(3,className,"*******days of the week in fiat transactions pay "+tomorrowsDate +" : "+dayOne+" : "+dayTwo+" : "+dayThree  +" : "+dayFour  +" : "+dayFive +" : "+ daySix  +" : "+daySeven);

			query = "select ifnull((select sum(txnamount)  from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as dayone, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as daytwo, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as daythree,  " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as dayfour,  " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as dayfive, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as daysix, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as dayseven, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as dayeight, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as daynine, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as dayten, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as dayeleven, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as daytwelve, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as daythirteen, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as dayfourteen, " + 					
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as dayfifteen ";
			
			    NeoBankEnvironment.setComment(3,className,"*******query executed "+query);

				pstmt = connection.prepareStatement(query);
				
				pstmt.setString(1, dayOne); 			
				pstmt.setString(2, dayTwo);
				pstmt.setString(3, dayThree);			
				pstmt.setString(4, dayFour);
				pstmt.setString(5, dayFive);			
				pstmt.setString(6, daySix);
				pstmt.setString(7, daySeven);	
				pstmt.setString(8, dayEight);	
				pstmt.setString(9, dayNine);	
				pstmt.setString(10, dayTen);	
				pstmt.setString(11, dayEleven);	
				pstmt.setString(12, dayTwelve);	
				pstmt.setString(13, dayThirteen);	
				pstmt.setString(14, dayFourteen);	
				pstmt.setString(15, dayFifteen);	
				//pstmt.setString(16, relationshipNo);	
	
				rs = (ResultSet)pstmt.executeQuery();
	
				 if(rs!=null){
					 arrWeeklyTransactions =  new ArrayList<Days>();
					 	while(rs.next()){	
					 		daysOfTheWeek = new Days();
					 		
					 		daysOfTheWeek.setDayOne(StringUtils.trim(rs.getString("dayone")));
					 		daysOfTheWeek.setDayTwo(StringUtils.trim(rs.getString("daytwo")));
					 		daysOfTheWeek.setDayThree(StringUtils.trim(rs.getString("daythree")));
					 		daysOfTheWeek.setDayFour(StringUtils.trim(rs.getString("dayfour")));
					 		daysOfTheWeek.setDayFive(StringUtils.trim(rs.getString("dayfive")));
					 		daysOfTheWeek.setDaySix(StringUtils.trim(rs.getString("daysix")));
					 		daysOfTheWeek.setDaySeven(StringUtils.trim(rs.getString("dayseven")));
					 		daysOfTheWeek.setDayEight(StringUtils.trim(rs.getString("dayeight")));
					 		daysOfTheWeek.setDayNine(StringUtils.trim(rs.getString("daynine")));
					 		daysOfTheWeek.setDayTen(StringUtils.trim(rs.getString("dayten")));
					 		daysOfTheWeek.setDayEleven(StringUtils.trim(rs.getString("dayeleven")));
					 		daysOfTheWeek.setDayTwelve(StringUtils.trim(rs.getString("daytwelve")));
					 		daysOfTheWeek.setDayThirteen(StringUtils.trim(rs.getString("daythirteen")));
					 		daysOfTheWeek.setDayFourteen(StringUtils.trim(rs.getString("dayfourteen")));
					 		daysOfTheWeek.setDayFifteen(StringUtils.trim(rs.getString("dayfifteen")));					 		
					 		arrWeeklyTransactions.add(daysOfTheWeek);
					 	}
					}
				 pstmt.close();
				 			
		}catch(Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getLastFiveteenDayFiatGraph  is  "+e.getMessage());
			throw new Exception ("The exception in method getLastFiveteenDayFiatGraph  is  "+e.getMessage());		
			
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
		return arrWeeklyTransactions;
	}
	
	public ArrayList<Days>  getLastFiveteenDayFiatGraph(String relationshipNo )throws Exception {
		//get retailpay transactions for all users for the past 7 days
		
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		Days daysOfTheWeek = null;
		ArrayList<Days> arrWeeklyTransactions = null;
		String tomorrowsDate= null;		String dayOne = null;		String dayTwo= null;		String dayThree = null;
		String dayFour = null;		String dayFive = null;		String daySix = null;		String daySeven = null;
		String dayEight = null; String dayNine = null; String dayTen = null; String dayEleven = null; String dayTwelve = null;
		String dayThirteen = null; String dayFourteen = null; String dayFifteen = null;
		
		try{
			connection = super.getConnection();	
			//todayMidnight=Utilities.getUTCTimeMidnight();
			tomorrowsDate= Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), +1, "yyyy-MM-dd");
			dayOne = Utilities.getMYSQLCurrentTimeStampForDashGraphs();
			dayTwo = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -1, "yyyy-MM-dd");
			dayThree = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -2, "yyyy-MM-dd");
			dayFour = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -3, "yyyy-MM-dd");
			dayFive = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -4, "yyyy-MM-dd");
			daySix = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -5, "yyyy-MM-dd");
			daySeven = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -6, "yyyy-MM-dd");
			dayEight = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -7, "yyyy-MM-dd");
			dayNine = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -8, "yyyy-MM-dd");
			dayTen = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -9, "yyyy-MM-dd");
			dayEleven = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -10, "yyyy-MM-dd");
			dayTwelve = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -11, "yyyy-MM-dd");
			dayThirteen = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -12, "yyyy-MM-dd");
			dayFourteen = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -13, "yyyy-MM-dd");
			dayFifteen = Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -14, "yyyy-MM-dd");

			NeoBankEnvironment.setComment(3,className,"*******days of the week in fiat transactions pay "+tomorrowsDate +" : "+dayOne+" : "+dayTwo+" : "+dayThree  +" : "+dayFour  +" : "+dayFive +" : "+ daySix  +" : "+daySeven);

			query = "select "+
					"        ifnull((select sum(txnamount)  from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as dayone, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as daytwo, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as daythree,  " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as dayfour,  " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as dayfive, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as daysix, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as dayseven, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as dayeight, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as daynine, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as dayten, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as dayeleven, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as daytwelve, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as daythirteen, " + 
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as dayfourteen, " + 					
					"		 ifnull((select sum(txnamount) from txn_wallet_cust_bc where date(txndatetime) = ? ),0)as dayfifteen ";
			
			
			query = "   select ifnull((select sum(txnamount)  from txn_wallet_cust_bc where date(txndatetime) = '2021-10-31' AND walletid IN (SELECT "
					+ " walletid FROM wallet_details WHERE relationshipno = '2111012657355700') ),0)as dayone, "
					+ " ifnull((select sum(txnamount)  from txn_wallet_cust_bc where date(txndatetime) = '2021-10-31' AND walletid IN (SELECT "
					+ " walletid FROM wallet_details WHERE relationshipno = '2111012657355700') ),0)as daytwo, "
					+ " ifnull((select sum(txnamount)  from txn_wallet_cust_bc where date(txndatetime) = '2021-10-31' AND walletid IN (SELECT "
					+ " walletid FROM wallet";
			
			
			query = "select ";
			for(int i = 0; i< 15; i++) {
				String sep = "";
				if(i!= 15) {
				   sep = ",";
				}
				query+="ifnull((select sum(txnamount)  from txn_wallet_cust_bc where date(txndatetime) = '2021-10-31' AND walletid "
						+ "IN (SELECT walletid FROM wallet_details WHERE relationshipno = '2111012657355700') ),0)as day"+i+sep;
			}


			
			
			    NeoBankEnvironment.setComment(3,className,"*******query executed "+query);

				pstmt = connection.prepareStatement(query);
				
				pstmt.setString(1, dayOne); 			
				pstmt.setString(2, dayTwo);
				pstmt.setString(3, dayThree);			
				pstmt.setString(4, dayFour);
				pstmt.setString(5, dayFive);			
				pstmt.setString(6, daySix);
				pstmt.setString(7, daySeven);	
				pstmt.setString(8, dayEight);	
				pstmt.setString(9, dayNine);	
				pstmt.setString(10, dayTen);	
				pstmt.setString(11, dayEleven);	
				pstmt.setString(12, dayTwelve);	
				pstmt.setString(13, dayThirteen);	
				pstmt.setString(14, dayFourteen);	
				pstmt.setString(15, dayFifteen);	
				//pstmt.setString(16, relationshipNo);	
	
				rs = (ResultSet)pstmt.executeQuery();
	
				 if(rs!=null){
					 arrWeeklyTransactions =  new ArrayList<Days>();
					 	while(rs.next()){	
					 		daysOfTheWeek = new Days();
					 		
					 		daysOfTheWeek.setDayOne(StringUtils.trim(rs.getString("dayone")));
					 		daysOfTheWeek.setDayTwo(StringUtils.trim(rs.getString("daytwo")));
					 		daysOfTheWeek.setDayThree(StringUtils.trim(rs.getString("daythree")));
					 		daysOfTheWeek.setDayFour(StringUtils.trim(rs.getString("dayfour")));
					 		daysOfTheWeek.setDayFive(StringUtils.trim(rs.getString("dayfive")));
					 		daysOfTheWeek.setDaySix(StringUtils.trim(rs.getString("daysix")));
					 		daysOfTheWeek.setDaySeven(StringUtils.trim(rs.getString("dayseven")));
					 		daysOfTheWeek.setDayEight(StringUtils.trim(rs.getString("dayeight")));
					 		daysOfTheWeek.setDayNine(StringUtils.trim(rs.getString("daynine")));
					 		daysOfTheWeek.setDayTen(StringUtils.trim(rs.getString("dayten")));
					 		daysOfTheWeek.setDayEleven(StringUtils.trim(rs.getString("dayeleven")));
					 		daysOfTheWeek.setDayTwelve(StringUtils.trim(rs.getString("daytwelve")));
					 		daysOfTheWeek.setDayThirteen(StringUtils.trim(rs.getString("daythirteen")));
					 		daysOfTheWeek.setDayFourteen(StringUtils.trim(rs.getString("dayfourteen")));
					 		daysOfTheWeek.setDayFifteen(StringUtils.trim(rs.getString("dayfifteen")));					 		
					 		arrWeeklyTransactions.add(daysOfTheWeek);
					 	}
					}
				 pstmt.close();
				 			
		}catch(Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getLastFiveteenDayFiatGraph  is  "+e.getMessage());
			throw new Exception ("The exception in method getLastFiveteenDayFiatGraph  is  "+e.getMessage());		
			
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
		return arrWeeklyTransactions;
	}
	
	public ArrayList<Days>  getLastFiveteenDaysTransactions(String relationshipNo, int howManyDays )throws Exception {		
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		Days daysOfTheWeek = null;
		ArrayList<Days> arrWeeklyTransactions = null;
	
		try{
			connection = super.getConnection();	
			
			query = "select ";
			for(int i = 1; i<= howManyDays; i++) {
				String sep = "";
				if(i!= howManyDays) {
				   sep = ",";
				}
				query+="ifnull((select sum(txnamount)  from txn_wallet_cust_bc where date(txndatetime) = ? and walletid "
						+ " in (select walletid from wallet_details where relationshipno = ?) ),0)as day"+i+sep;
			 }
			
			//NeoBankEnvironment.setComment(3, className,"query is  " +query);
			  //  NeoBankEnvironment.setComment(3,className,"**** query executed "+query);
				pstmt = connection.prepareStatement(query);
				int count = howManyDays;
				int j = 1;
				int x = 0;
				while(j <=count ) {
					if(j==1){
						x =	0;
					}
					for(int n=1; n<=2; n++) {
					 x=x+1;
					}
					pstmt.setString((x-1), Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -(j-1), "yyyy-MM-dd")); 
					pstmt.setString(x,relationshipNo ); 
					j =j+1;
				}
				rs = (ResultSet)pstmt.executeQuery();
				 if(rs!=null){
					 arrWeeklyTransactions =  new ArrayList<Days>();
					 	while(rs.next()){	
					 		daysOfTheWeek = new Days();
					 		daysOfTheWeek.setDayOne(Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -0, "yyyy-MM-dd")+","+StringUtils.trim(rs.getString("day1")));
					 		daysOfTheWeek.setDayTwo(Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -1, "yyyy-MM-dd")+","+StringUtils.trim(rs.getString("day2")));
					 		daysOfTheWeek.setDayThree(Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -2, "yyyy-MM-dd")+","+StringUtils.trim(rs.getString("day3")));
					 		daysOfTheWeek.setDayFour(Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -3, "yyyy-MM-dd")+","+StringUtils.trim(rs.getString("day4")));
					 		daysOfTheWeek.setDayFive(Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -4, "yyyy-MM-dd")+","+StringUtils.trim(rs.getString("day5")));
					 		daysOfTheWeek.setDaySix(Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -6, "yyyy-MM-dd")+","+StringUtils.trim(rs.getString("day6")));
					 		daysOfTheWeek.setDaySeven(Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -7, "yyyy-MM-dd")+","+StringUtils.trim(rs.getString("day7")));
					 		daysOfTheWeek.setDayEight(Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -8, "yyyy-MM-dd")+","+StringUtils.trim(rs.getString("day8")));
					 		daysOfTheWeek.setDayNine(Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -9, "yyyy-MM-dd")+","+StringUtils.trim(rs.getString("day9")));
					 		daysOfTheWeek.setDayTen(Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -10, "yyyy-MM-dd")+","+StringUtils.trim(rs.getString("day10")));
					 		daysOfTheWeek.setDayEleven(Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -11, "yyyy-MM-dd")+","+StringUtils.trim(rs.getString("day11")));
					 		daysOfTheWeek.setDayTwelve(Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -12, "yyyy-MM-dd")+","+StringUtils.trim(rs.getString("day12")));
					 		daysOfTheWeek.setDayThirteen(Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -13, "yyyy-MM-dd")+","+StringUtils.trim(rs.getString("day13")));
					 		daysOfTheWeek.setDayFourteen(Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -14, "yyyy-MM-dd")+","+StringUtils.trim(rs.getString("day14")));
					 		daysOfTheWeek.setDayFifteen(Utilities.getDateCalculate(Utilities.getMYSQLCurrentTimeStampForDashGraphs(), -15, "yyyy-MM-dd")+","+StringUtils.trim(rs.getString("day15")));					 		
					 		arrWeeklyTransactions.add(daysOfTheWeek);
					 	
					 	}
					}
				 pstmt.close();
				 			
		}catch(Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getLastFiveteenDaysTransactions  is  "+e.getMessage());
			throw new Exception ("The exception in method getLastFiveteenDaysTransactions  is  "+e.getMessage());		
			
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
		return arrWeeklyTransactions;
	}
	
	public ConcurrentHashMap<String,String> getLastXTxnsValuesForFiatWallet(String userUniqueNo, int howManyDays ) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ConcurrentHashMap<String,String> hashSumOfTxns = null;
		try{
			connection = super.getConnection();	
			 
				query = "select  sum(a.txnamount) amountbydate , date(a.txndatetime) dateoftxn from txn_wallet_cust_bc a, wallet_details b where  "
						+ "a.walletid=b.walletid and b.relationshipno=?	group by date(a.txndatetime) order by date(a.txndatetime) desc limit ?";				
				
			pstmt = connection.prepareStatement(query);			
			pstmt.setString(1, userUniqueNo); // pass relationshioNo for customer and billerCode for Merchant from xxxRulesImpl
			pstmt.setInt(2, howManyDays); // last how many days the sum total amount transaction will be recorded
			rs = (ResultSet)pstmt.executeQuery();

			 if(rs!=null){
				 hashSumOfTxns = new ConcurrentHashMap<String,String>();
				 	while(rs.next()){
				 		hashSumOfTxns.put(StringUtils.trim(rs.getString("dateoftxn")), StringUtils.trim(rs.getString("amountbydate")) );
				 		} // end of while
				 	} //end of if rs!=null check
			 if(hashSumOfTxns!=null) 
				 if(hashSumOfTxns.size()==0)
					 hashSumOfTxns=null;
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getLastXTxnsValuesForFiatWallet  is  "+e.getMessage());
			throw new Exception ("The exception in method getLastXTxnsValuesForFiatWallet  is  "+e.getMessage());			
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
		return hashSumOfTxns;
	}
	
	
	public ArrayList<String> getMonthlyTxnForFiatWallet(String userUniqueNo, int howManyDays ) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<String> arrMonthlyTransaction = null;
		try{
			connection = super.getConnection();	
			 
				query = " select sum(a.txnamount) txnamount, date_format(a.txndatetime, \"%M\") txndatetime from txn_wallet_cust_bc a where date(txndatetime) between ? and ? and walletid "
						+ " in (select walletid from wallet_details where relationshipno = ?) group  by date_format(a.txndatetime, \"%M\") order by txndatetime asc   ";				
				
			pstmt = connection.prepareStatement(query);			
			pstmt.setString(1, "2021-01-01");
			pstmt.setString(2, "2021-12-31");
			pstmt.setString(3,userUniqueNo );
			rs = (ResultSet)pstmt.executeQuery();

			 if(rs!=null){
				 arrMonthlyTransaction = new ArrayList<String>();
				 	while(rs.next()){
				 		arrMonthlyTransaction.add(StringUtils.trim(rs.getString("txndatetime"))+","+ StringUtils.trim(rs.getString("txnamount")));
				 		
				 		} // end of while
				 	} //end of if rs!=null check
			 if(arrMonthlyTransaction!=null) 
				 if(arrMonthlyTransaction.size()==0)
					 arrMonthlyTransaction=null;
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getMonthlyTxnForFiatWallet  is  "+e.getMessage());
			throw new Exception ("The exception in method getMonthlyTxnForFiatWallet  is  "+e.getMessage());			
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
		return arrMonthlyTransaction;
	}
	public ArrayList<String> getMonthlyTransactionForPorteCoins(String relationshipNo ) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<String> arrPorteMonthlyTransaction = null;
		try{
			connection = super.getConnection();	
							
			query = "select sum(a.coin_amount) coin_amount, date_format(a.txndate, \"%a %d %M %Y\") txndate from txn_buy_coins a  where custrelno=? and asset_code=? and STATUS=? group  by date_format(a.txndate, \"%a %d %M %Y\") order by txndate desc limit 15";				
			
			pstmt = connection.prepareStatement(query);			
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, "PORTE");
			pstmt.setString(3, "P" );// Processed
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				arrPorteMonthlyTransaction = new ArrayList<String>();
				while(rs.next()){
					arrPorteMonthlyTransaction.add(StringUtils.trim(rs.getString("txndate"))+","+ StringUtils.trim(rs.getString("coin_amount")));
				} // end of while
			} //end of if rs!=null check
		}catch(Exception e){
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
		return arrPorteMonthlyTransaction;
	}
	public ArrayList<String> getMonthlyTransactionForXLMCoins(String relationshipNo ) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<String> arrXLMMonthlyTransaction = null;
		try{
			connection = super.getConnection();	
			
			query = "select sum(a.coin_amount) coin_amount, date_format(a.txndate, \"%a %d %M %Y\") txndate from txn_buy_coins a  where custrelno=? and asset_code=? and STATUS=? group  by date_format(a.txndate, \"%a %d %M %Y\") order by txndate desc limit 15";				
			
			pstmt = connection.prepareStatement(query);			
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, "XLM");
			pstmt.setString(3, "P" );// Processed
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				arrXLMMonthlyTransaction = new ArrayList<String>();
				while(rs.next()){
					arrXLMMonthlyTransaction.add(StringUtils.trim(rs.getString("txndate"))+","+ StringUtils.trim(rs.getString("coin_amount")));
				} // end of while
			} //end of if rs!=null check
		}catch(Exception e){
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
		return arrXLMMonthlyTransaction;
	}
	public ArrayList<String> getMonthlyTransactionForUSDCCoins(String relationshipNo ) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<String> arrUSDCMonthlyTransaction = null;
		try{
			connection = super.getConnection();	
			
			query = "select sum(a.coin_amount) coin_amount, date_format(a.txndate, \"%a %d %M %Y\") txndate from txn_buy_coins a  where custrelno=? and asset_code=? and STATUS=? group  by date_format(a.txndate, \"%a %d %M %Y\") order by txndate desc limit 15";				
			
			pstmt = connection.prepareStatement(query);			
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, "USDC");
			pstmt.setString(3, "P" );// Processed
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				arrUSDCMonthlyTransaction = new ArrayList<String>();
				while(rs.next()){
					arrUSDCMonthlyTransaction.add(StringUtils.trim(rs.getString("txndate"))+","+ StringUtils.trim(rs.getString("coin_amount")));
				} // end of while
			} //end of if rs!=null check
		}catch(Exception e){
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
		return arrUSDCMonthlyTransaction;
	}  
	public ArrayList<String> getMonthlyTransactionForVesselCoins(String relationshipNo ) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<String> arrVesselMonthlyTransaction = null;
		try{
			connection = super.getConnection();	
			
			query = "select sum(a.coin_amount) coin_amount, date_format(a.txndate, \"%a %d %M %Y\") txndate from txn_buy_coins a  where custrelno=? and asset_code=? and STATUS=? group  by date_format(a.txndate, \"%a %d %M %Y\") order by txndate desc limit 15";				
			
			pstmt = connection.prepareStatement(query);			
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, "VESL");
			pstmt.setString(3, "P" );// Processed
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				arrVesselMonthlyTransaction = new ArrayList<String>();
				while(rs.next()){
					arrVesselMonthlyTransaction.add(StringUtils.trim(rs.getString("txndate"))+","+ StringUtils.trim(rs.getString("coin_amount")));
				} // end of while
			} //end of if rs!=null check
		}catch(Exception e){
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
		return arrVesselMonthlyTransaction;
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
			
			NeoBankEnvironment.setComment(3,className,"After query in getmnemonicCode");

			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, "S");
			
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){
			 	while(rs.next()){	 
			 		mnemonicCode = Utilities.tripleDecryptData( StringUtils.trim(rs.getString("mnemonic_code")));
			 		} 
			 	NeoBankEnvironment.setComment(3, className, "Mnemonic from db is "+mnemonicCode);
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
	
	public boolean checkIfPasswordIsCorrect(String relationshipNo, String password) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String enPassword = null;
		boolean isCorrect = false;
		try {
			connection = super.getConnection();	                    
			query = " select customerpwd from customer_details where relationshipno = ? ";
			
			NeoBankEnvironment.setComment(3,className,"After query");

			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){
			 	while(rs.next()){	 
			 		enPassword = StringUtils.trim(rs.getString("customerpwd"));
			 		} 
			 } 
			 if(DigestUtils.md5Hex(Utilities.encryptString(password)).equals(enPassword)) {
				 isCorrect = true;
			 }
	
		}catch(Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method checkIfPasswordIsCorrect  is  "+e.getMessage());		
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
	
		return isCorrect;
	}
	
	public String checkIfUserHasMnemonicCode (String relationshipNo) throws Exception {
		NeoBankEnvironment.setComment(3, className, "Relno "+relationshipNo);
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String mnemonicFlag = null;
		try {
			connection = super.getConnection();	  			
			query = " select tokenized_mnemonic from stellar_account_relation where relationshipno = ? ";
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
			NeoBankEnvironment.setComment(1,className,"The exception in method checkIfUserHasMnemonicCode  is  "+e.getMessage());		
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

	public boolean enableFigurePrint(String relationshipNo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 //                                        1                            2             
			 query = "update customer_details set  enable_fingreprint=? where relationshipno=? "; 
			 pstmt = connection.prepareStatement(query);
			 pstmt.setString(1, "Y"); 						 					 
			 pstmt.setString(2, relationshipNo);	
									 
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
				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
			if(pstmt!=null) pstmt.close(); 
		}
		return result;
			
		
	}
	
	public String checkIfUserHasEnabledFingrePrint (String relationshipNo) throws Exception {
		NeoBankEnvironment.setComment(3, className, "Relno "+relationshipNo);
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String fingrePrintFlag = null;
		try {
			
			connection = super.getConnection();	  			
			query = " select enable_fingreprint from customer_details where relationshipno = ? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
			 	while(rs.next()){	 
			 		fingrePrintFlag = StringUtils.trim(rs.getString("enable_fingreprint"));
			 	} 
			 } 
			 NeoBankEnvironment.setComment(3, className, "fingrePrintFlag "+fingrePrintFlag);
		}catch(Exception e) {
			fingrePrintFlag = null;
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
	
		return fingrePrintFlag;
	}
	public String getStellarMnemonicCode(String relationshipNo) throws Exception {
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
			pstmt.setString(2, "S");
			
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){
			 	while(rs.next()){
			 		mnemonicCode=Utilities.tripleDecryptData(( StringUtils.trim(rs.getString("mnemonic_code"))));
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
		public boolean checkIfPartnerPasswordIsCorrect(String partnerID, String password) throws Exception {
			PreparedStatement pstmt=null;
			Connection connection = null;
			ResultSet rs=null;
			String query = null;
			String enPassword = null;
			boolean isCorrect = false;
			try {
				connection = super.getConnection();	

				
				query = " select adminpwd from admin_details where adminid = ? and  accesstype=?";
				
				NeoBankEnvironment.setComment(3,className,"After query");

				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, Utilities.tripleEncryptData(partnerID) );
				pstmt.setString(2, "P");
				rs = (ResultSet)pstmt.executeQuery();
				 if(rs!=null){
				 	while(rs.next()){	 
				 		enPassword = StringUtils.trim(rs.getString("adminpwd"));
				 		 NeoBankEnvironment.setComment(3, className, "Database passoword is "+enPassword);
				 		} 
				 } 
				 NeoBankEnvironment.setComment(3, className, "Database passoword is "+enPassword);
				 NeoBankEnvironment.setComment(3, className, "Triple encrypted password is "+Utilities.encryptString(password));
				 if(DigestUtils.md5Hex(Utilities.encryptString(password)).equals(enPassword)) {
					 isCorrect = true;
				 }
		
			}catch(Exception e) {
				NeoBankEnvironment.setComment(1,className,"The exception in method checkIfPartnerPasswordIsCorrect  is  "+e.getMessage());		
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
		
			return isCorrect;
		}
		
		public boolean changeKYCStatus(String relationshipNo, String fileName) throws Exception {
			PreparedStatement pstmt=null;
			Connection connection = null;
			String query = null;
			boolean result = false;
			try{
				 connection = super.getConnection();
				 connection.setAutoCommit(false);
				 NeoBankEnvironment.setComment(3, className,"File Name "+fileName+" rel no "+relationshipNo);
				 //                                1                      2                       3
				 query = "update customer_kyc_docs set status=? where relationshipno=? and document_location=?"; 

				 pstmt = connection.prepareStatement(query);
				 pstmt.setString(1, "R"); 						 					 
				 pstmt.setString(2, relationshipNo);	
				 pstmt.setString(3, Utilities.tripleEncryptData(fileName));	
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
					NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
				}
				if(pstmt!=null) pstmt.close(); 
			}
			return result;
				
			
		}
		
		public String getPartnerMnemonicCode(String userId) throws Exception {
			PreparedStatement pstmt=null;
			Connection connection = null;
			ResultSet rs=null;
			String query = null;
			String mnemonicCode = null;
			try {
			
				connection = super.getConnection();	                    
				query = " select mnemonic_code from mnemonic_partner_relation_bc where userid = ? and network=? ";
				
				NeoBankEnvironment.setComment(3,className,"After query in getmnemonicCode");

				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, Utilities.tripleEncryptData(userId));
				pstmt.setString(2, "S");
				
				rs = (ResultSet)pstmt.executeQuery();
				
				 if(rs!=null){
				 	while(rs.next()){	 
				 		mnemonicCode = Utilities.tripleDecryptData( StringUtils.trim(rs.getString("mnemonic_code")));
				 		} 
				 	NeoBankEnvironment.setComment(3, className, "Mnemonic from db is "+mnemonicCode);
				 } 
		
			}catch(Exception e) {
				mnemonicCode = null;
				NeoBankEnvironment.setComment(1,className,"The exception in method getPartnerMnemonicCode  is  "+e.getMessage());		
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
			return arrKYCDocsPath;		
		}
	
	
}
