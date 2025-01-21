package com.pporte.dao;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.StringUtils;

import com.pporte.NeoBankEnvironment;
import com.pporte.model.MccGroup;
import com.pporte.model.Merchant;
import com.pporte.utilities.Utilities;

public class MerchantDao extends HandleConnections {
	
	private static String className = MerchantDao.class.getSimpleName();
	
	
	public List<MccGroup> getMerchantCategories() throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		List<MccGroup> mccGroupList = new ArrayList<MccGroup>();
		MccGroup mccGroup = null;
		try{
			connection = super.getConnection();	
			query = "select mcccategoryid, mcccategoryname  from merch_mcc_group  ";
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 	while(rs.next()){	
				 		mccGroup = new MccGroup();
				 		mccGroup.setMccId(rs.getString("mcccategoryid"));
				 		mccGroup.setMccName(rs.getString("mcccategoryname"));
				 		mccGroupList.add(mccGroup);
				 		} // end of while
				 	} //end of if rs!=null check
			
			 // validate the password
			 if(mccGroupList!=null)
				 if(mccGroupList.size()==0)
					 mccGroupList=null;
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getMerchantCategories  is  "+e.getMessage());
			throw new Exception ("The exception in method getMerchantCategories  is  "+e.getMessage());			
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
		return mccGroupList;
		
	}
	public List<String> getMerchantKycDocs(String merchantCode) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String fileLocation = null;
		List<String> listFileLocation = new ArrayList<String>();
		
		try{
			connection = super.getConnection();	
			query = " select document_location  from merch_kyc_docs where merchantcode =?  ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, merchantCode);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 	while(rs.next()){	
				 		fileLocation = null;
				 		fileLocation =rs.getString("document_location");
				 		listFileLocation.add(fileLocation);
				 		} // end of while
				 	} //end of if 
			 if(listFileLocation!=null)
				 if(listFileLocation.size()==0)
					 listFileLocation=null;
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getMerchantKycDocs  is  "+e.getMessage());
			throw new Exception ("The exception in method getMerchantKycDocs  is  "+e.getMessage());			
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
		return listFileLocation;
		
	}
	

	public Boolean registerNewMerchant(String companyName, String businessDesc, String physicalAddress,
			String bsnPhoneNumber, String email,  String nationalId, String haveBranches,
			String planValue, String filePath, String billerRef, String merchBillercode, String mccId, String fullName,
			String phoneNo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		try{
			connection = super.getConnection();	
			connection.setAutoCommit(false);
			
			/*Merchant dtls*/
			query = "insert into merchant_details (merchantcode, merch_ref, physical_address, companyname, bussiness_description, "
					+ " business_phoneno, mcccategoryid, merchant_type, status, createdon)"
					+ " values (? ,?, ?, ?, ?, ?, ?, ?, ?, ?) ";
							//  1  2  3  4  5  6  7  8  9  10
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, merchBillercode); 						
			pstmt.setString(2, billerRef); 						
			pstmt.setString(3, physicalAddress); 						
			pstmt.setString(4, companyName); 						
			pstmt.setString(5, businessDesc); 						
			pstmt.setString(6, bsnPhoneNumber); 						
			pstmt.setInt(7, Integer.parseInt(mccId)); 	
			if(Boolean.getBoolean(haveBranches)) {
				pstmt.setString(8, "PM");
			}else {
				pstmt.setString(8, "SA");
			}
			pstmt.setString(9, "V");
			pstmt.setString(10, Utilities.getMYSQLCurrentTimeStampForInsert()); 
				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}
		   pstmt.close();
		   
		   /*merchant kyc*/
			query = "insert into merch_kyc_docs (merchantcode, document_location, createdon )"
			+ " values (? ,?, ?) ";
					//  1  2  3  
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, merchBillercode); 						
			pstmt.setString(2, filePath); 
			pstmt.setString(3, Utilities.getMYSQLCurrentTimeStampForInsert());						
			try {
			pstmt.executeUpdate();
			}catch(Exception e) {
			throw new Exception (" failed query "+query+" "+e.getMessage());
			}
			pstmt.close();
			
			/*merch_user_details*/
			query = "insert into merch_user_details (merchantcode, merchantid, merchname, merchantpin,"
					+ " merchantpwd, merchantcontact, merchantemail, nationalid, pintries, login_tries,"
					+ " password_type,  merch_hiercharchy, createdon, status, designation )"
					+ " values (? ,?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?,  ?,  ?, ? ) ";
							//  1  2  3  4  5  6  7  8   9  10 11 12  13  14 15
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, merchBillercode); 						
			pstmt.setString(2, email); 
			pstmt.setString(3, fullName); 
			pstmt.setString(4, "0");						
			pstmt.setString(5, "0");						
			pstmt.setString(6, phoneNo);						
			pstmt.setString(7, email);						
			pstmt.setString(8, nationalId);						
			pstmt.setInt(9, 0);						
			pstmt.setInt(10, 0);						
			pstmt.setString(11, "TP");						
			pstmt.setString(12, "S");	
			pstmt.setString(13, Utilities.getMYSQLCurrentTimeStampForInsert());	
			pstmt.setString(14, "P");	
			pstmt.setString(15, "Admin User");	
			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}
			pstmt.close();
			
			String planId = null;
			query = " select planid from pricing_table where usertype=? and planvalue = ? and isdefault = ? and status =? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "M");
			pstmt.setString(2, planValue );
			pstmt.setString(3,  "Y");
			pstmt.setString(4,  "A");
			rs = (ResultSet) pstmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					planId = (StringUtils.trim(rs.getString("planid")));
				} // end of while
			} // end of if rs!=null check
			pstmt.close();
			
			/*Merchant plan*/
			query = "insert into merchant_pricing_plan (pricing_planid, merchantcode, status, createdon )"
					+ " values (?, ?, ?, ? ) ";
							//  1  2  3  4  
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, planId); 						
			pstmt.setString(2, merchBillercode); 
			pstmt.setString(3, "A"); 	
			pstmt.setString(4, Utilities.getMYSQLCurrentTimeStampForInsert());	
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
			NeoBankEnvironment.setComment(1,className,"The exception in method registerNewMerchant is  "+e.getMessage());
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
	
	public Merchant getMerchantProfile(String merchID) throws Exception{                       
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		Merchant merchant = null;
		try{
			connection = super.getConnection();	

			query = " select a.merchname merchname, a.nationalid nationalid, a.merchantemail merchantemail, a.merchantcontact "
					+ " merchantcontact, b.companyname companyname, b.merchantcode merchantcode, a.merchantid merchantid, "
					+ "	b.merch_ref merch_ref, b.physical_address physical_address, b.business_phoneno business_phoneno, "
					+ " b.bussiness_description bussiness_description,  b.merchant_type merchant_type "
					+ "	from merch_user_details a, merchant_details b where a.merchantid = ? and b.merchantcode = a.merchantcode  ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, merchID);
			rs = (ResultSet)pstmt.executeQuery();

			 if(rs!=null){
				 merchant =new Merchant();
				 	while(rs.next()){	
				 		merchant.setMerchantCode(StringUtils.trim(rs.getString("merchantcode")));
				 		merchant.setMerchanUsertId(StringUtils.trim(rs.getString("merchantid")));
				 		merchant.setMerchantName(StringUtils.trim(rs.getString("merchname")));
				 		merchant.setNationalId(StringUtils.trim(rs.getString("nationalid")));
				 		merchant.setEmail(StringUtils.trim(rs.getString("merchantemail")));
				 		merchant.setContact(StringUtils.trim(rs.getString("merchantcontact")));
				 		merchant.setCompanyName(StringUtils.trim(rs.getString("companyname")));					 		
				 		merchant.setBillerRef(StringUtils.trim(rs.getString("merch_ref")));
				 		merchant.setPhysicalAddress(StringUtils.trim(rs.getString("physical_address")));
				 		merchant.setBusinessPhoneNumber(StringUtils.trim(rs.getString("business_phoneno")));
				 		merchant.setBusinessDescription(StringUtils.trim(rs.getString("bussiness_description")));
				 		merchant.setMerchantType(StringUtils.trim(rs.getString("merchant_type")));
				 	}
			 }		
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getMerchantProfile  is  "+e.getMessage());
			throw new Exception ("The exception in method getMerchantProfile  is  "+e.getMessage());			
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
		return merchant;
	}
	
	public ConcurrentHashMap<String, String> getSpecificMerchantCategory(String merchantCode) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ConcurrentHashMap<String, String> hash_Categories = null;
		try{
			connection = super.getConnection();	
			query = " select a.mcccategoryid mcccategoryid, b.mcccategoryname mcccategoryname "
					+ " from merchant_details a, merch_mcc_group b "
					+ "where a.merchantcode = ? and b.mcccategoryid = a.mcccategoryid ";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, merchantCode);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 hash_Categories = new ConcurrentHashMap<String, String>();
				 	while(rs.next()){	 			 			
				 		hash_Categories.put(StringUtils.trim(rs.getString("mcccategoryid")) , StringUtils.trim(rs.getString("mcccategoryname")) );
				 		} // end of while
				 	//arr_Product.add(m_Product);
				 	} //end of if 
			 if(hash_Categories!=null)
				 if(hash_Categories.size()==0)
					 hash_Categories=null;
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getSpecificMerchantCategory  is  "+e.getMessage());
			throw new Exception ("The exception in method getSpecificMerchantCategory  is  "+e.getMessage());			
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
		return hash_Categories;
		
	}
	
	public Boolean addNewBranch(String companyName, String businessDesc, String physicalAddress,
			String bsnPhoneNumber, String email,  String nationalId, 
			String planValue, String filePath, String billerRef, String mainMerchantCode,  String mccId, String fullName,
			String phoneNo, String branchMerchantCode) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		try{
			connection = super.getConnection();	
			connection.setAutoCommit(false);
			
			/*Merchant dtls*/
			query = "insert into merchant_details (merchantcode, merch_ref, physical_address, companyname, bussiness_description, "
					+ " business_phoneno, mcccategoryid, merchant_type, status, createdon)"
					+ " values (? ,?, ?, ?, ?, ?, ?, ?, ?, ?) ";
							//  1  2  3  4  5  6  7  8  9  10
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, branchMerchantCode); 						
			pstmt.setString(2, billerRef); 						
			pstmt.setString(3, physicalAddress); 						
			pstmt.setString(4, companyName); 						
			pstmt.setString(5, businessDesc); 						
			pstmt.setString(6, bsnPhoneNumber); 						
			pstmt.setInt(7, Integer.parseInt(mccId)); 	
			pstmt.setString(8, "SM");
			pstmt.setString(9, "V");
			pstmt.setString(10, Utilities.getMYSQLCurrentTimeStampForInsert()); 
				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}
		   pstmt.close();
		   
		   /*merchant kyc*/
			query = "insert into merch_kyc_docs (merchantcode, document_location, createdon )"
			+ " values (? ,?, ?) ";
					//  1  2  3  
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, branchMerchantCode); 						
			pstmt.setString(2, filePath); 
			pstmt.setString(3, Utilities.getMYSQLCurrentTimeStampForInsert());						
			try {
			pstmt.executeUpdate();
			}catch(Exception e) {
			throw new Exception (" failed query "+query+" "+e.getMessage());
			}
			pstmt.close();
			
			/*merch_user_details*/
			query = "insert into merch_user_details (merchantcode, merchantid, merchname, merchantpin,"
					+ " merchantpwd, merchantcontact, merchantemail, nationalid, pintries, login_tries,"
					+ " password_type,  merch_hiercharchy, createdon, status, designation )"
					+ " values (? ,?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?,  ?,  ?, ? ) ";
							//  1  2  3  4  5  6  7  8   9  10 11 12  13  14 15
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, branchMerchantCode); 						
			pstmt.setString(2, email); 
			pstmt.setString(3, fullName); 
			pstmt.setString(4, "0");						
			pstmt.setString(5, "0");						
			pstmt.setString(6, phoneNo);						
			pstmt.setString(7, email);						
			pstmt.setString(8, nationalId);						
			pstmt.setInt(9, 0);						
			pstmt.setInt(10, 0);						
			pstmt.setString(11, "TP");						
			pstmt.setString(12, "S");	
			pstmt.setString(13, Utilities.getMYSQLCurrentTimeStampForInsert());	
			pstmt.setString(14, "P");	
			pstmt.setString(15, "Admin User");	
			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}
			pstmt.close();
			
			String planId = null;
			query = " select planid from pricing_table where usertype=? and planvalue = ? and isdefault = ? and status =? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "M");
			pstmt.setString(2, planValue );
			pstmt.setString(3,  "Y");
			pstmt.setString(4,  "A");
			rs = (ResultSet) pstmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					planId = (StringUtils.trim(rs.getString("planid")));
				} // end of while
			} // end of if rs!=null check
			pstmt.close();
			
			/*Merchant plan*/
			query = "insert into merchant_pricing_plan (pricing_planid, merchantcode, status, createdon )"
					+ " values (?, ?, ?, ? ) ";
							//  1  2  3  4  
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, planId); 						
			pstmt.setString(2, branchMerchantCode); 
			pstmt.setString(3, "A"); 	
			pstmt.setString(4, Utilities.getMYSQLCurrentTimeStampForInsert());	
			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}
			pstmt.close();
			

			/*Merchant branch relation*/
			query = "insert into merchant_relation (super_merchantcode, branch_merchantcode, createdon )"
					+ " values (?, ?, ? ) ";
							//  1  2  3   
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, mainMerchantCode); 						
			pstmt.setString(2, branchMerchantCode);  	
			pstmt.setString(3, Utilities.getMYSQLCurrentTimeStampForInsert());	
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
			NeoBankEnvironment.setComment(1,className,"The exception in method registerNewMerchant is  "+e.getMessage());
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
	
	public ArrayList<Merchant> getMerchantBranchData(String merchID) throws Exception{                       
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		Merchant merchant = null;
		ArrayList<Merchant> branchesList = new ArrayList<Merchant>();
		try{
			connection = super.getConnection();	
			query = " select a.branch_merchantcode branch_merchantcode, a.createdon createdon, "
				 + "  b.companyname companyname, b.status status from merchant_relation a, "
				 + "  merchant_details b  where a.super_merchantcode = ? and b.merchantcode = a.branch_merchantcode  order by createdon desc";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, merchID);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 	while(rs.next()){	
				 	    merchant =new Merchant();
				 		merchant.setCompanyName(StringUtils.trim(rs.getString("companyname")));
				 		merchant.setStatus(StringUtils.trim(rs.getString("status")));
				 		merchant.setMerchantCode(StringUtils.trim(rs.getString("branch_merchantcode")));
				 		merchant.setCreatedOn(StringUtils.trim(rs.getString("createdon")));
				 		branchesList.add(merchant);
				 	}
			 }
			 if(branchesList!=null)
				 if(branchesList.size()==0)
					 branchesList=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getMerchantBranchData  is  "+e.getMessage());
			throw new Exception ("The exception in method getMerchantBranchData  is  "+e.getMessage());			
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
		return branchesList;
	}
	
	public List<Merchant> getMerchantUsersData(String merchID) throws Exception{                       
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		Merchant merchant = null;
		List<Merchant> merchantUserList = new ArrayList<Merchant>();
		try{
			connection = super.getConnection();	
			query = " select merchname, merchantemail, merchantcontact, nationalid, merch_hiercharchy, "
				  + " createdon, status, designation from merch_user_details where merchantcode =? order by createdon desc" ;
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, merchID);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 	while(rs.next()){	
				 		merchant =new Merchant();
				 		merchant.setMerchantName(StringUtils.trim(rs.getString("merchname")));
				 		merchant.setEmail(StringUtils.trim(rs.getString("merchantemail")));
				 		merchant.setContact(StringUtils.trim(rs.getString("merchantcontact")));
				 		merchant.setNationalId(StringUtils.trim(rs.getString("nationalid")));
				 		merchant.setHierchachyLevel(StringUtils.trim(rs.getString("merch_hiercharchy")));
				 		merchant.setCreatedOn(StringUtils.trim(rs.getString("createdon")));
				 		merchant.setStatus(StringUtils.trim(rs.getString("status")));
				 		merchant.setMerchUserDesignation(StringUtils.trim(rs.getString("designation")));
				 		merchantUserList.add(merchant);
				 	}
			 }
			 if(merchantUserList!=null)
				 if(merchantUserList.size()==0)
					 merchantUserList=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getMerchantUsersData  is  "+e.getMessage());
			throw new Exception ("The exception in method getMerchantUsersData  is  "+e.getMessage());			
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
		return merchantUserList;
	}
	
	
	public boolean addNewMerchantUser(String password, String userName, String userLevel,
 			String emailId, String userContact,  String merchantCode,  
 			String nationalId, String designation) throws Exception {
		
 		PreparedStatement pstmt=null;
 		Connection connection = null;
 		String query = null;
 		boolean result = false;
 		try{
 			 connection = super.getConnection();
 			 connection.setAutoCommit(false);
 			 query = " insert into merch_user_details (merchantcode, merchantid, merchname, merchantpwd, merchantpin, "
 			 	    + " merchantcontact, merchantemail, nationalid, pintries, login_tries, password_type,  merch_hiercharchy, "
 			 	    + " createdon, status, designation  ) "
					+ "values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
				    	//		1  2  3  4  5  6  7  8  9 10  11 12 13 14 15
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1,  merchantCode); 					
			pstmt.setString(2,  emailId); 					
			pstmt.setString(3,  userName);
			pstmt.setString(4,  Utilities.encryptString(password)); 					
			pstmt.setString(5,  "0"); 					
			pstmt.setString(6,  userContact); 					
			pstmt.setString(7,  emailId); 					
			pstmt.setString(8,  nationalId); 					
			pstmt.setInt(9,  0); 					
			pstmt.setInt(10,  0); 					
			pstmt.setString(11,  "TP"); 					
			pstmt.setString(12, userLevel); 			
			pstmt.setString(13, Utilities.getMYSQLCurrentTimeStampForInsert());				
			pstmt.setString(14, "A");				
			pstmt.setString(15, designation);				
			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}
			connection.commit();
			result = true;
 		}catch(Exception e){
 			connection.rollback(); result = false;
 			NeoBankEnvironment.setComment(1,className,"The exception in method addNewMerchantUser  is  "+e.getMessage());
 			throw new Exception ("The exception in method addNewMerchantUser  is  "+e.getMessage());
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
	
	 public boolean updateMerchUserDetails(String userName, String userAccess, 
			 String emailId, String userContact, String userStatus, 
			 String designation, String merchantCode, String nationalId) throws Exception{
			PreparedStatement pstmt=null;
			Connection connection = null;
			String query = null;
			boolean result = false;
			try{
				 connection = super.getConnection();
				 connection.setAutoCommit(false);
				 query = " update merch_user_details set merch_hiercharchy=?, merchname=?, merchantemail=?, "
				 		+" merchantcontact=?, status=?, designation=?, nationalid=? where merchantid=? and merchantcode = ?	";
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1,  userAccess); 					
					pstmt.setString(2,  userName); 					
					pstmt.setString(3,  emailId); 					
					pstmt.setString(4,  userContact); 					
					pstmt.setString(5,  userStatus); 					
					pstmt.setString(6,  designation);
					pstmt.setString(7,  nationalId);
					pstmt.setString(8,  emailId); 	
					pstmt.setString(9,  merchantCode); 	
					try {
						pstmt.executeUpdate();
						}catch(Exception e) {
							throw new Exception (" failed query "+query+" "+e.getMessage());
						}
					connection.commit();
					result = true;
			}catch(Exception e){
				connection.rollback(); result = false;
				NeoBankEnvironment.setComment(1,className,"The exception in method updateMerchUserDetails  is  "+e.getMessage());
				throw new Exception ("The exception in method updateMerchUserDetails  is  "+e.getMessage());
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
	 
	 public Merchant getMerchantDetailsForQR(String merchID) throws Exception{                       
			PreparedStatement pstmt=null;
			Connection connection = null;
			ResultSet rs=null;
			String query = null;
			Merchant merchant = null;
			try{
				connection = super.getConnection();	
				query = "select  a.merchantcontact merchantcontact, b.companyname companyname,b.physical_address physical_address, "
						+ " c.mccgeneric mccgeneric from merch_user_details a, merchant_details b, "
						+ " merch_mcc_group c where a.merchantid = ? and b.merchantcode = a.merchantcode and b.mcccategoryid=c.mcccategoryid";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, merchID);
				rs = (ResultSet)pstmt.executeQuery();
				 if(rs!=null){
					 merchant =new Merchant();
					 	while(rs.next()){	
					 		merchant.setContact(StringUtils.trim(rs.getString("merchantcontact")));
					 		merchant.setCompanyName(StringUtils.trim(rs.getString("companyname")));					 		
					 		merchant.setPhysicalAddress(StringUtils.trim(rs.getString("physical_address")));
					 		merchant.setMccCategoryId(StringUtils.trim(rs.getString("mccgeneric")));
					 	}
				 }
				 rs.close(); pstmt.close();
			}catch(Exception e){
				NeoBankEnvironment.setComment(1,className,"The exception in method getMobileMerchantProfile  is  "+e.getMessage());
				throw new Exception ("The exception in method getMobileMerchantProfile  is  "+e.getMessage());			
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
			
			
			return merchant;
		}
	

}
