package com.pporte.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.pporte.NeoBankEnvironment;
import com.pporte.utilities.Utilities;

public class RegistrationDao extends HandleConnections{
	public static String className = RegistrationDao.class.getSimpleName();
	
	
	public boolean registerNewCustomer(String relationshipNo, String fullName, String contact, String emailAddress,
			String password, String licenseNo,String passportNo, String address, String dob, String gender, String filePath) throws Exception{
		
			PreparedStatement pstmt=null;
			Connection connection = null;
			ResultSet rs=null;
			String query = null;
			boolean result = false;
			String currentTime = null;
			String encryptedPassNumber="";
			String encryptedLicenseNumber="";

		try{
			// Encrypt Data
			
				// Either Licence Number or Passport is empty from the front end hence the check before encrypting. 
				if (!licenseNo.equals("")) {
					encryptedLicenseNumber=Utilities.tripleEncryptData(licenseNo);
				}
				if (!passportNo.equals("")) {
					encryptedPassNumber=Utilities.tripleEncryptData(passportNo);
				}
		
				connection = super.getConnection();	
				connection.setAutoCommit(false);
				currentTime=Utilities.getMYSQLCurrentTimeStampForInsert();
				
												        	//	1		2		    3			   4		     5		  6		   7     8             9       10        11        12      13         14        15  
				query = "insert into customer_details (relationshipno,customerid,customerpwd,customername,nationalid,passportno,custemail,gender,custcontact,address,dateofbirth,status,logintries,txntries,createdon,password_type)"
						+ " values (? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?) ";
								//  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, relationshipNo); 						
				pstmt.setString(2, Utilities.tripleEncryptData(emailAddress.toLowerCase()));// Customer login id is email address 
				pstmt.setString(3, DigestUtils.md5Hex(Utilities.encryptString(password)));						
				pstmt.setString(4, fullName);						
				pstmt.setString(5, encryptedLicenseNumber);					
				pstmt.setString(6, encryptedPassNumber);
				pstmt.setString(7,  Utilities.tripleEncryptData(emailAddress.toLowerCase()));
				pstmt.setString(8,  Utilities.tripleEncryptData(gender));
				pstmt.setString(9,  Utilities.tripleEncryptData(contact));
				pstmt.setString(10,  Utilities.tripleEncryptData(address));
				pstmt.setString(11,  Utilities.tripleEncryptData(dob));
				pstmt.setString(12, "P");// Pending
				pstmt.setInt(13, 0);// Login tries is zero during registration
				pstmt.setInt(14, 0); // Txn tries is zero during registration
				pstmt.setString(15,currentTime);
				pstmt.setString(16,"P"); //Permanent
				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}
				pstmt.close();
				
				
					
				query = "insert into customer_kyc_docs (relationshipno,document_location,createdon)"
						+ " values (? ,?, ?) ";
								//  1  2  3  
				pstmt = connection.prepareStatement(query);
				
				String [] filePathArray =  filePath.split(",");
				for(int i =0; i<filePathArray.length; i++) {
					pstmt.setString(1, relationshipNo); 						
					pstmt.setString(2,Utilities.tripleEncryptData(StringUtils.trim(filePathArray[i]))); // Remove white space from string before encrypting
					pstmt.setString(3,currentTime);	
					
					pstmt.addBatch();
		            if ((i + 1) % 1000 == 0 || (i + 1) == filePathArray.length) {
		            	try {
		                 pstmt.executeBatch();
		            	}catch (Exception e) {
		            		throw new Exception (" failed query "+query+" "+e.getMessage());
		            	}
		            }
				}
				pstmt.clearBatch();
				pstmt.close();
				
					
				connection.commit();
				result = true;
		}catch(Exception e){
			connection.rollback(); result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method registerNewCustomer  is  "+e.getMessage());
			//throw new Exception ("The exception in method registerNewCustomer  is  "+e.getMessage());			
		}finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(rs!=null) rs.close(); if(pstmt!=null) pstmt.close(); 
				if (relationshipNo!=null)relationshipNo=null; if (fullName!=null)fullName=null; if (contact!=null)contact=null;
				 if (emailAddress!=null)emailAddress=null; if (password!=null)password=null; 
				 if (address!=null)address=null; if (dob!=null)dob=null; if (gender!=null)gender=null; if (filePath!=null)filePath=null;
				 if (currentTime!=null)currentTime=null;  if(encryptedPassNumber!=null)encryptedPassNumber=null;
				 if (encryptedLicenseNumber!=null)encryptedLicenseNumber=null;
			}
		return result;	
	}


	public boolean registerNewPassword(String userId,String passwordType,String temporaryPassWord) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		@SuppressWarnings("unused")
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		String currentTime = null;
		try {
			connection = super.getConnection();	
			connection.setAutoCommit(false);
			currentTime=Utilities.getMYSQLCurrentTimeStampForInsert();

			query="update customer_details set password_type=?,customerpwd=? where customerid=?";
			pstmt = connection.prepareStatement(query);					
			pstmt.setString(1, passwordType ); 
			pstmt.setString(2, DigestUtils.md5Hex(Utilities.encryptString(temporaryPassWord))); 
			pstmt.setString(3, Utilities.tripleEncryptData(userId) ); 
			 try {
				 pstmt.executeUpdate();
				 }catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
			 }
			 connection.commit();
			 result = true;
		} catch (Exception e) {
			connection.rollback(); result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method registerNewPassword  is  "+e.getMessage());
			throw new Exception ("The exception in method registerNewPassword  is  "+e.getMessage());	
		}finally {
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
			if(pstmt!=null) pstmt.close();if (currentTime!=null)currentTime=null;
		}
		return result;
	}


	
	
	
	
	
	
	
	
}
