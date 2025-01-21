package com.pporte.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import com.pporte.NeoBankEnvironment;
import com.pporte.model.User;
import com.pporte.utilities.Utilities;

public class UserLoginDao extends HandleConnections{
	private static String className = UserLoginDao.class.getSimpleName();
	
	public boolean registerNewUser(String regUserId, String regUserPassword, String regUserFirstName,
			String regUserLastName, String regUserEmail, String regUserAddress1, String regUserAddress2,
			String regUserContact, String regUserCity, String regUserType) throws Exception{
		
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		long currentTime = System.currentTimeMillis();
		BigInteger bi = new BigInteger(String.valueOf(currentTime));
		
		try{
			
			connection = super.getConnection();	
			connection.setAutoCommit(false);
												//	1			2		3			4		5		6		7
			query = "insert into temp_cust_list (userid, userpwd, user_name, usertype, status, createdon, relationid )"
					+ " values (? ,?, ?, ?, ?, ?, ?) ";
							//  1  2  3  4  5  6  7
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, regUserId); 						
			pstmt.setString(2, Utilities.encryptString(regUserPassword)); 
			pstmt.setString(3, regUserFirstName + " "+regUserLastName);						
			pstmt.setString(4, regUserType);						
			pstmt.setString(5, "A");					
			pstmt.setString(6, Utilities.getMYSQLCurrentTimeStampForInsert());
			pstmt.setString(7, bi.toString(16));
				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}
				pstmt.close();
				//									1			2			3			4			5			6				7				8		9
				query = "insert into temp_cust_profile (userid, user_fname, user_lname, user_contact, user_email, user_address1, user_address2, user_city, updatedon )"
						+ " values (? ,?, ?, ?, ?, ?, ?, ?, ?) ";
								//  1  2  3  4  5  6  7  8  9
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, regUserId); 						
				pstmt.setString(2, regUserFirstName); 
				pstmt.setString(3, regUserLastName);						
				pstmt.setString(4, regUserContact);						
				pstmt.setString(5, regUserEmail);					
				pstmt.setString(6, regUserAddress1);						
				pstmt.setString(7, regUserAddress2);						
				pstmt.setString(8, regUserCity);						
				pstmt.setString(9, Utilities.getMYSQLCurrentTimeStampForInsert());
					try {
						pstmt.executeUpdate();
						}catch(Exception e) {
							throw new Exception (" failed query "+query+" "+e.getMessage());
						}
				connection.commit();
				result = true;
		}catch(Exception e){
			connection.rollback(); result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method registerNewUser  is  "+e.getMessage());
			//throw new Exception ("The exception in method registerNewUser  is  "+e.getMessage());			
		}finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close(); bi = null;
			}
		return result;	
	}


	public User validateUserLogin(String userId, String userPwd, String userType) throws Exception{
		NeoBankEnvironment.setComment(3, className, "Inside validateUserLogin dao");
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		User user = null;
		try{
			connection = super.getConnection();	
			if (userType.equals("C")) {
				
			query = "select customerid, customername, status,customerpwd, logintries, relationshipno,password_type from customer_details where customerid = ?";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, Utilities.tripleEncryptData(userId.toLowerCase()));
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){ 
				 	while(rs.next()){	
				 		 user = new User(); 
						// This will get only 1 record
						user.setPassword( StringUtils.trim(rs.getString("customerpwd")) );
						user.setUserStatus( StringUtils.trim(rs.getString("status")) );
						user.setRelationshipNo( StringUtils.trim(rs.getString("relationshipno")) );
						user.setPasswordType(StringUtils.trim(rs.getString("password_type")));
						user.setName(StringUtils.trim(rs.getString("customername")));
				 		} // end of while
				 	} //end of if rs!=null check		
		  }else if(userType.equals("M")) {
			  query = " select merchantid userid, merchantpwd userpwd, status status, createdon createdon,  "
						+ " merchantcode relationid from merch_user_details where merchantid=? and status=? ";
			  pstmt = connection.prepareStatement(query);
			  pstmt.setString(1, userId);
			  pstmt.setString(2, "A");
			  rs = (ResultSet)pstmt.executeQuery();
			  if(rs!=null){
				 while(rs.next()){	
						user = new User(); // This will get only 1 record
				 		user.setUserId( StringUtils.trim(rs.getString("userid")) );
				 		user.setPassword( StringUtils.trim(rs.getString("userpwd")) );
				 		user.setUserStatus( StringUtils.trim(rs.getString("status")) );
				 		user.setUserCreatedOn( StringUtils.trim(rs.getString("createdon")) );
				 		user.setRelationId(StringUtils.trim(rs.getString("relationid")));
				 	} // end of while
				 } //end of if rs!=null check			
		  }
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method validateUserLogin  is  "+e.getMessage());
			throw new Exception ("The exception in method validateUserLogin  is  "+e.getMessage());			
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
	public User getUserDetails(String userId, String userType) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		//Wallet wallet = null;
		User user = null;
		try{
			connection = super.getConnection();	

			if (userType.equals("C")) {
				
			query = "select customerid, customername, status, logintries, relationshipno from customer_details where customerid = ? and status=?";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, Utilities.tripleEncryptData(userId));
			pstmt.setString(2, "A"); // Active
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){ 
				 user = new User(); 
				 	while(rs.next()){	
						// This will get only 1 record
						user.setCustomerId(Utilities.tripleDecryptData( StringUtils.trim(rs.getString("customerid"))) );
						user.setMaskedEmailId(Utilities.maskEmailAddress(Utilities.tripleDecryptData( StringUtils.trim(rs.getString("customerid")))));
						user.setName(StringUtils.trim(rs.getString("customername")) );
						user.setUserStatus( StringUtils.trim(rs.getString("status")) );
						user.setRelationshipNo( StringUtils.trim(rs.getString("relationshipno")) );
						user.setUserType( StringUtils.trim("C" ) );
				 		} // end of while
				 	} //end of if rs!=null check		
		  }
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method validateUserLogin  is  "+e.getMessage());
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
		return user;
	}
	public User getUserDetailsMobile(String relationshipNo, String userType) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		//Wallet wallet = null;
		User user = null;
		try{
			connection = super.getConnection();	
			
			if (userType.equals("C")) {
				
				query = "select customerid, customername, status, logintries, relationshipno from customer_details where relationshipno = ? and status=?";
				
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, relationshipNo);
				pstmt.setString(2, "A"); // Active
				rs = (ResultSet)pstmt.executeQuery();
				
				if(rs!=null){ 
					user = new User(); 
					while(rs.next()){	
						// This will get only 1 record
						user.setCustomerId(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("customerid"))) );
						user.setName(StringUtils.trim(rs.getString("customername")) );
						user.setUserStatus( StringUtils.trim(rs.getString("status")) );
						user.setRelationshipNo( StringUtils.trim(rs.getString("relationshipno")) );
						user.setUserType( StringUtils.trim("C" ) );
					} // end of while
				} //end of if rs!=null check		
			}
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getUserDetailsMobile is  "+e.getMessage());
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
		return user;
	}

	public User getMerchantUserDetails(String userId) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		User user = null;
		try{
			connection = super.getConnection();	

			query = "select a.merchantid userid, b.companyname companyname,  a.merchname username, a.merchantemail useremail, "
					+ " a.merchantcontact usercontact, a.merchantcode merchantcode, "
					+ " a.password_type password_type, a.merch_hiercharchy merch_hiercharchy, b.merchant_type merchant_type, "
					+ "	b.expriry expirydate, a.status status from merch_user_details a, "
					+ " merchant_details b where a.merchantid= ? and a.merchantcode=b.merchantcode ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, userId);
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){
				 user = new User();
				 	while(rs.next()){	
				 		user.setUserId( StringUtils.trim(rs.getString("userid")));
				 		user.setCompanyName( StringUtils.trim(rs.getString("companyname")));
				 		user.setUserName( StringUtils.trim(rs.getString("username")));
				 		user.setUserEmail( StringUtils.trim(rs.getString("useremail")));
				 		user.setUserContact( StringUtils.trim(rs.getString("usercontact")));
				 		user.setRelationId( StringUtils.trim(rs.getString("merchantcode")));
				 		user.setPasswordType( StringUtils.trim(rs.getString("password_type")));
				 		user.setMerchantHiercharchy( StringUtils.trim(rs.getString("merch_hiercharchy")));
				 		user.setMerchantType( StringUtils.trim(rs.getString("merchant_type")));
				 		user.setUserStatus( StringUtils.trim(rs.getString("status")));
				 		user.setUserType( "M");
				 		} // end of while
				 	} //end of if rs!=null check			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getMerchantUserDetails  is  "+e.getMessage());
				
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
	
	public String validateUser(String userId, String userPasswd, String userType) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String message="true";
		String dbUserId=null;
		String dbUserPasswd=null;
		try{
			connection = super.getConnection();	
		
			if(userType.equalsIgnoreCase("O")) {
				query = "select adminid userid, adminpwd userpassword,password_type from admin_details where adminid=?";

			}
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, Utilities.tripleEncryptData(userId));
			rs = (ResultSet)pstmt.executeQuery();

			 if(rs!=null){ 
				 	while(rs.next()){
				 		dbUserId =  StringUtils.trim(rs.getString("userid"));
				 		dbUserPasswd =  StringUtils.trim(rs.getString("userpassword"));
				 		
				 	} // end of while
				} 
			 if(dbUserId ==null || dbUserPasswd ==null ) {
				 message = "Incorrect UserID";
				 throw new  Exception("Incorrect UserID");
				 
			 }
			 			 
			 if(dbUserPasswd.equals(DigestUtils.md5Hex(Utilities.encryptString(userPasswd)))==false) {
				 message = "Incorrect Password";
				 throw new  Exception("Incorrect Password");
			 }else {
				 message = "success";
			}
	
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method validateOpsUser  is  "+e.getMessage());
			// Don't throw exception here
			//throw new Exception ("The exception in method registerNewUser  is  "+e.getMessage());			
		}finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if (dbUserId!=null) dbUserId=null; if(dbUserPasswd!=null) dbUserPasswd=null;
			}

		return message;
	}

	public User getOpsUserDetails(String userId) throws Exception {
	PreparedStatement pstmt=null;
	Connection connection = null;
	ResultSet rs=null;
	String query = null;
	User m_User = null;

	try{
		connection = super.getConnection();	
		
		query = "select adminid userid, adminname username, accesstype accesstype, adminemail useremail, admincontact usercontact, "
				+ " expiry expirydate, status from admin_details where adminid=?  ";
		
		pstmt = connection.prepareStatement(query);
		pstmt.setString(1, Utilities.tripleEncryptData(userId));
		rs = (ResultSet)pstmt.executeQuery();

		 if(rs!=null){ 
			 	while(rs.next()){
			 		m_User = new User();
			 		m_User.setUserId( Utilities.tripleDecryptData( StringUtils.trim(rs.getString("userid"))  ));
			 		m_User.setUserName( Utilities.tripleDecryptData( StringUtils.trim(rs.getString("username"))   ) );
			 		m_User.setUserType(  StringUtils.trim(rs.getString("accesstype"))   );
			 		m_User.setUserEmail(Utilities.tripleDecryptData(  StringUtils.trim(rs.getString("useremail"))  )  );
			 		m_User.setUserContact( Utilities.tripleDecryptData( StringUtils.trim(rs.getString("usercontact"))  )  );
			 		m_User.setExpiryDate(  StringUtils.trim(rs.getString("expirydate"))    );
			 		m_User.setUserStatus(  StringUtils.trim(rs.getString("status"))    );

			 	} // end of while
			} 

	}catch(Exception e){
		NeoBankEnvironment.setComment(1,className,"The exception in method getOpsUserDetails  is  "+e.getMessage());
		throw new Exception ("The exception in method getOpsUserDetails  is  "+e.getMessage());			
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

		return m_User;
	}


	public User validate(String relationshipno, String userPwd, String userType) throws Exception{
		NeoBankEnvironment.setComment(3, className, "inside validate");
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		User user = null;
		try{
			connection = super.getConnection();	


				
			query = "select customerpwd, logintries from customer_details where relationshipno = ?";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipno);
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){ 
				 	while(rs.next()){	
				 		 user = new User(); 
						// This will get only 1 record
				 		user.setPassword( StringUtils.trim(rs.getString("customerpwd")) );
				 		} // end of while
				 	} //end of if rs!=null check		
		
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method validate  is  "+e.getMessage());
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
	public User validateUserForgotPassword(String userId, String userType) throws Exception {
		NeoBankEnvironment.setComment(3, className, "inside validate");
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		User user = null;
		try{
			connection = super.getConnection();	

			if (userType.equals("C")) {
				
			query = "select customerid,relationshipno from customer_details where customerid = ?";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, Utilities.tripleEncryptData(userId));
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){ 
				 	while(rs.next()){	
				 		 user = new User(); 
						// This will get only 1 record
						user.setCustomerId( StringUtils.trim(rs.getString("customerid")) );
						user.setRelationshipNo( StringUtils.trim(rs.getString("relationshipno")) );
				 		} // end of while
				 	} //end of if rs!=null check		
		  }
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method validateUserForgotPassword  is  "+e.getMessage());
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


	public boolean updatePassword(String password, String userId,String passwordType)  throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);			 
			query = " update customer_details set  customerpwd=?,password_type=? where customerid=? "; 
		    pstmt = connection.prepareStatement(query);
			pstmt.setString(1, DigestUtils.md5Hex(Utilities.encryptString(password ))); 						 
			pstmt.setString(2, passwordType);						 
			pstmt.setString(3, Utilities.tripleEncryptData(userId));						 
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


	public boolean checkUserPassword(String userId, String password) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String enPassword = null;
		boolean result = false;
		try{
			connection = super.getConnection();					
			query = "select customerpwd from customer_details where customerid = ?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, Utilities.tripleEncryptData(userId));
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){ 
				 	while(rs.next()){	
						// This will get only 1 record
				 		enPassword = ( StringUtils.trim(rs.getString("customerpwd")) );
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
	public String validateOpsUser(String userId, String userPasswd, String userType) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String message="true";
		String dbUserId=null;
		String dbUserPasswd=null;
		String dbPasswordType=null;
		try{
			connection = super.getConnection();	

			if(userType.equalsIgnoreCase("O")) {
				query = "select adminid userid, adminpwd userpassword,password_type password_type from admin_details where adminid=?";

			}
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, Utilities.tripleEncryptData(userId));
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){ 
				 	while(rs.next()){
				 		dbUserId =  StringUtils.trim(rs.getString("userid"));
				 		dbUserPasswd =  StringUtils.trim(rs.getString("userpassword"));
				 		dbPasswordType= StringUtils.trim(rs.getString("password_type"));
				 		
				 	} // end of while
				} 
			 message = "success";
			 if(dbUserId ==null || dbUserPasswd ==null ) {
				 message = "Incorrect UserID";			 
			 }else {
				 //User exists
				 if(dbUserPasswd.equals(DigestUtils.md5Hex(Utilities.encryptString(userPasswd)))==false) {
					 message = "Incorrect Password";
				 }else {
					 // Password is correct
					 if(dbPasswordType.equals("T")){
						 message = "T";
					 }
				 }
				 
			 }
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method validateOpsUser  is  "+e.getMessage());
			// Don't throw exception here
			//throw new Exception ("The exception in method registerNewUser  is  "+e.getMessage());			
		}finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();if(dbUserPasswd!=null) dbUserPasswd=null;
				if (dbUserId!=null) dbUserId=null; 
			}

		return message;
	}
	public User validateOpsUserLogin(String userId, String userPwd, String userType) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		User user = null;
		try{
			connection = super.getConnection();	

			query = "select adminid userid, adminpwd userpassword,password_type password_type from admin_details where adminid=?";				
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, Utilities.tripleEncryptData(userId));
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){ 
				 	while(rs.next()){	
				 		 user = new User(); 
						// This will get only 1 record
						user.setCustomerId( StringUtils.trim(rs.getString("userid")) );
						user.setPassword( StringUtils.trim(rs.getString("userpassword")) );
						user.setPasswordType(StringUtils.trim(rs.getString("password_type")));
				 		} // end of while
				 	} //end of if rs!=null check		
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method validateUserLogin  is  "+e.getMessage());
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
		return user;
	}

	public boolean updateOpsPassword(String password, String userId,String passwordType)  throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			
			 connection = super.getConnection();
			 connection.setAutoCommit(false);			 
			query = " update admin_details set  adminpwd=?,password_type=? where adminid=? "; 
		    pstmt = connection.prepareStatement(query);
			pstmt.setString(1, DigestUtils.md5Hex(Utilities.encryptString(password ))); 						 
			pstmt.setString(2, passwordType);						 
			pstmt.setString(3, Utilities.tripleEncryptData(userId));						 
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
	
	public boolean checkOpsUserPassword(String userId, String password) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String enPassword = null;
		boolean result = false;
		try{
			connection = super.getConnection();					
			query = "select adminpwd from admin_details where adminid = ?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, Utilities.tripleEncryptData(userId));
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){ 
				 	while(rs.next()){	
						// This will get only 1 record
				 		enPassword = ( StringUtils.trim(rs.getString("adminpwd")) );
				 } // end of while
			 } //end of if rs!=null check	
			 
			 result = enPassword.equals(DigestUtils.md5Hex(Utilities.encryptString(password )));
			 
		}catch(Exception e){
			result=false;
			NeoBankEnvironment.setComment(1,className,"The exception in method checkOpsUserPassword  is  "+e.getMessage());
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
}
