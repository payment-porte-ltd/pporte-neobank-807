package com.pporte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.pporte.NeoBankEnvironment;
import com.pporte.model.User;
import com.pporte.utilities.Utilities;

public class OpsProfileManageDao extends HandleConnections {
	private static String className = OpsProfileManageDao.class.getSimpleName();

	public User getSpecificOpsUser(String userId) throws Exception{
		
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		User m_User = null;
		try{
			connection = super.getConnection();	
			query = "select adminid, accesstype, adminname, adminemail, admincontact, status, createdon, expiry, tries from admin_details where  adminid=?";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, Utilities.tripleEncryptData(userId));
			rs = (ResultSet)pstmt.executeQuery();			
			 if(rs!=null){
				 
				 	while(rs.next()){	 
				 		m_User = new User();
				 		m_User.setUserId( Utilities.tripleDecryptData(StringUtils.trim(rs.getString("adminid"))   ) );
				 		m_User.setAccessType( StringUtils.trim(rs.getString("accesstype"))  );
				 		m_User.setUserName( Utilities.tripleDecryptData (StringUtils.trim(rs.getString("adminname"))  ));
				 		m_User.setUserEmail(Utilities.tripleDecryptData( StringUtils.trim(rs.getString("adminemail"))    ));
				 		m_User.setUserContact( Utilities.tripleDecryptData(StringUtils.trim(rs.getString("admincontact"))   ) );
				 		m_User.setUserStatus( StringUtils.trim(rs.getString("status"))  );
				 		m_User.setUserCreatedOn( StringUtils.trim(rs.getString("createdon"))  );
				 		m_User.setExpiryDate( StringUtils.trim(rs.getString("expiry"))  );
				 		m_User.setPasswdTries( StringUtils.trim(rs.getString("tries"))  );
				 		} // end of while			 	
				 	} //end of if rs!=null check
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getSpecificOpsUser  is  "+e.getMessage());
			throw new Exception ("The exception in method getSpecificOpsUser  is  "+e.getMessage());			
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

	public boolean UpdateOpsUserProfileDetails(String opsUserId, String userName, String opsUserContact,
		String emailAddress) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 
				query = "update admin_details set adminname = ?, adminemail = ?, admincontact = ? where adminid = ? ";
		
			       	pstmt = connection.prepareStatement(query);
					pstmt.setString(1, Utilities.tripleEncryptData(userName) );
					pstmt.setString(2, Utilities.tripleEncryptData(emailAddress) );
					pstmt.setString(3, Utilities.tripleEncryptData(opsUserContact) );
					pstmt.setString(4, Utilities.tripleEncryptData(opsUserId) );
					
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
			NeoBankEnvironment.setComment(1,className,"The exception in method UpdateOpsUserProfileDetails  is  "+e.getMessage());
			throw new Exception ("The exception in method UpdateOpsUserProfileDetails  is  "+e.getMessage());
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
	public ArrayList<User> getAllOperationUsers() throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<User> arrAllOpsUser = null;
		try{
			connection = super.getConnection();	

			query = " select adminid, accesstype, adminname, adminemail, admincontact, "
					+ " status, tries, createdon, expiry from admin_details where accesstype != ? order by createdon ";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "P" );
			rs = (ResultSet)pstmt.executeQuery();			
			 if(rs!=null){
				 arrAllOpsUser = new ArrayList<User>();
				 	while(rs.next()){	 
				 		User m_User=new User();
				 		m_User.setUserId(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("adminid"))  ));
				 		m_User.setAccessType( StringUtils.trim(rs.getString("accesstype"))  );
				 		m_User.setUserName( Utilities.tripleDecryptData(StringUtils.trim(rs.getString("adminname"))  ));
				 		m_User.setUserEmail(Utilities.tripleDecryptData( StringUtils.trim(rs.getString("adminemail"))    ));
				 		m_User.setUserContact( Utilities.tripleDecryptData(StringUtils.trim(rs.getString("admincontact"))    ));
				 		m_User.setUserStatus( StringUtils.trim(rs.getString("status"))  );
				 		m_User.setUserCreatedOn( StringUtils.trim(rs.getString("createdon"))  );
				 		m_User.setExpiryDate( StringUtils.trim(rs.getString("expiry"))  );
				 		m_User.setPasswdTries( StringUtils.trim(rs.getString("tries"))  );
				 		arrAllOpsUser.add(m_User);
				 		} // end of while
				 	
				 	} //end of if rs!=null check
			 if(arrAllOpsUser!=null) if(arrAllOpsUser.size()==0) arrAllOpsUser=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllOperationUsers()   is  "+e.getMessage());
			throw new Exception ("The exception in method getAllOperationUsers()   is  "+e.getMessage());			
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
		return arrAllOpsUser;
	}

	public boolean UpdateOpsUsersDetails(String opsUserId, String userName, String opsUserContact, String emailAddress,
			String opsUserType, String opsStatus, String expiry) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 
				query = "update admin_details set adminname = ?, adminemail = ?, admincontact = ?, accesstype = ?, status = ?,expiry = ?  where adminid = ? ";
		
			       	pstmt = connection.prepareStatement(query);
					pstmt.setString(1, Utilities.tripleEncryptData(userName) );
					pstmt.setString(2,  Utilities.tripleEncryptData(emailAddress) );
					pstmt.setString(3,  Utilities.tripleEncryptData(opsUserContact) );
					pstmt.setString(4, opsUserType );
					pstmt.setString(5, opsStatus );
					pstmt.setString(6, expiry );
					pstmt.setString(7,  Utilities.tripleEncryptData(opsUserId) );
					
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
			NeoBankEnvironment.setComment(1,className,"The exception in method UpdateOpsUsersDetails  is  "+e.getMessage());
			throw new Exception ("The exception in method UpdateOpsUsersDetails  is  "+e.getMessage());
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

	public boolean OpsAddNewOpsUser(String opsUserId, String userName, String opsUserContact, String emailAddress,
		String opsUserType, String opsStatus, String expiry, String temporaryPassWord) throws Exception{

		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 
                             //                        1        2          3            4           5          6      7        8        9             10
			   query = "insert into admin_details (adminid, adminname, adminemail, admincontact, accesstype, status, tries, createdon,password_type,adminpwd ) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?,?,? ) ";
			 	               //  1  2  3  4  5  6  7  8 9 10  
				
			       	pstmt = connection.prepareStatement(query);
			       	pstmt.setString(1, Utilities.tripleEncryptData(opsUserId ) );
					pstmt.setString(2, Utilities.tripleEncryptData(userName ) );
					pstmt.setString(3,  Utilities.tripleEncryptData(emailAddress ) );
					pstmt.setString(4, Utilities.tripleEncryptData(opsUserContact ) );
					pstmt.setString(5, opsUserType );
					pstmt.setString(6, opsStatus );
					pstmt.setString(7, "0" );
					pstmt.setString(8, Utilities.getMYSQLCurrentTimeStampForInsert() );
					pstmt.setString(9, "T");
					pstmt.setString(10, DigestUtils.md5Hex(Utilities.encryptString(temporaryPassWord)) );
					
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
			NeoBankEnvironment.setComment(1,className,"The exception in method OpsAddNewOpsUser  is  "+e.getMessage());
			throw new Exception ("The exception in method OpsAddNewOpsUser  is  "+e.getMessage());
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

	
	

}
