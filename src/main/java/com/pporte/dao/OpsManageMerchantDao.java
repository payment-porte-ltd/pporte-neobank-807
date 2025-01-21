package com.pporte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;


import com.pporte.NeoBankEnvironment;
import com.pporte.model.MccGroup;
import com.pporte.model.Merchant;
import com.pporte.model.Risk;
import com.pporte.utilities.Utilities;

public class OpsManageMerchantDao extends HandleConnections {
	private static String className = OpsManageMerchantDao.class.getSimpleName();

	
	public ArrayList<Merchant> getPendingMerchants() throws Exception {
		
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		String query = null;
		ArrayList<Merchant> arrMerchants = null;
		try {
			connection = super.getConnection();
			
			query = "select merchantcode, merch_ref, physical_address, companyname, bussiness_description, business_phoneno, mcccategoryid, "
					+ " merchant_type, status, createdon, expriry from merchant_details where status = ? order by createdon desc limit 1000 ";

			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "V");
			rs = (ResultSet) pstmt.executeQuery();
			if (rs != null) {
				arrMerchants = new ArrayList<Merchant>();
				while (rs.next()) {
					Merchant m_Merchant = new Merchant();
					m_Merchant.setMerchantCode(StringUtils.trim(rs.getString("merchantcode")));
					m_Merchant.setMerchRef(StringUtils.trim(rs.getString("merch_ref")));
					m_Merchant.setPhysicalAddress(StringUtils.trim(rs.getString("physical_address")));
					m_Merchant.setCompanyName(StringUtils.trim(rs.getString("companyname")));
					m_Merchant.setBusinessDescription(StringUtils.trim(rs.getString("bussiness_description")));
					m_Merchant.setBusinessPhoneNumber(StringUtils.trim(rs.getString("business_phoneno")));
					m_Merchant.setMccCategoryId(StringUtils.trim(rs.getString("mcccategoryid")));
					m_Merchant.setMerchantType(StringUtils.trim(rs.getString("merchant_type")));
					m_Merchant.setStatus(StringUtils.trim(rs.getString("status")));
					m_Merchant.setCreatedOn(StringUtils.trim(rs.getString("createdon")));
					m_Merchant.setExpiryDate(StringUtils.trim(rs.getString("expriry")));
					arrMerchants.add(m_Merchant);
				} // end of while

			} // end of if rs!=null check
			if (arrMerchants != null)
				if (arrMerchants.size() == 0)
					arrMerchants = null;
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1, className,
					"The exception in method getAllPendingMerchants is  " + e.getMessage());
			throw new Exception("The exception in method getAllPendingMerchants is  " + e.getMessage());
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
		}
		return arrMerchants;
	}

	public Merchant getSpecificMerchantDetails(String merchantCode) throws Exception {
			PreparedStatement pstmt=null;
			Connection connection = null;
			ResultSet rs=null;
			String query = null;
			Merchant merchant = null;
			try{
				connection = super.getConnection();	
				
				query = "select merchantcode, merch_ref, physical_address, companyname, bussiness_description, business_phoneno, mcccategoryid,"
						+ " merchant_type, status, approved_by, createdon, expriry from merchant_details where merchantcode = ? ";
				
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, merchantCode);
				rs = (ResultSet)pstmt.executeQuery();

				 if(rs!=null){
					 merchant =new Merchant();
					 	while(rs.next()){	
					 		merchant.setMerchantCode(StringUtils.trim(rs.getString("merchantcode")));
					 		merchant.setMerchRef(StringUtils.trim(rs.getString("merch_ref")));
					 		merchant.setPhysicalAddress(StringUtils.trim(rs.getString("physical_address")));
					 		merchant.setCompanyName(StringUtils.trim(rs.getString("companyname")));					 		
					 		merchant.setBusinessDescription(StringUtils.trim(rs.getString("bussiness_description")));
					 		merchant.setBusinessPhoneNumber(StringUtils.trim(rs.getString("business_phoneno")));
					 		merchant.setMccCategoryId(StringUtils.trim(rs.getString("mcccategoryid")));
					 		merchant.setApprovedBy(StringUtils.trim(rs.getString("approved_by")));
					 		merchant.setMerchantType(StringUtils.trim(rs.getString("merchant_type")));
					 		merchant.setStatus(StringUtils.trim(rs.getString("status")));
					 		merchant.setCreatedOn(StringUtils.trim(rs.getString("createdon")));
					 		merchant.setExpiryDate(StringUtils.trim(rs.getString("expriry")));
					 
					 	}
				 }		
			}catch(Exception e){
				NeoBankEnvironment.setComment(1,className,"The exception in method getSpecificMerchantDetails  is  "+e.getMessage());
				throw new Exception ("The exception in method getSpecificMerchantDetails  is  "+e.getMessage());			
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

	public ArrayList<MccGroup> getAllMCCRroupList() throws Exception{
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		String query = null;
		ArrayList<MccGroup> arrMCC = null;
		try {
			connection = super.getConnection();

			query = "select mcccategoryid, mcccategoryname, mccgeneric, mccfromrange, mcctorange from merch_mcc_group order by  mcccategoryid ";

			pstmt = connection.prepareStatement(query);
			rs = (ResultSet) pstmt.executeQuery();
			if (rs != null) {
				arrMCC = new ArrayList<MccGroup>();
				while (rs.next()) {
					MccGroup m_MCC = new MccGroup();
					m_MCC.setMccId(StringUtils.trim(rs.getString("mcccategoryid")));
					m_MCC.setMccName(StringUtils.trim(rs.getString("mcccategoryname")));
					m_MCC.setMccGeneric(StringUtils.trim(rs.getString("mccgeneric")));
					m_MCC.setMccFromRange(StringUtils.trim(rs.getString("mccfromrange")));
					m_MCC.setMccToRange(StringUtils.trim(rs.getString("mcctorange")));
					arrMCC.add(m_MCC);
				} // end of while

			} // end of if rs!=null check
			if (arrMCC != null)
				if (arrMCC.size() == 0)
					arrMCC = null;
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1, className, "The exception in method getAllMCCRroupList  is  " + e.getMessage());
			throw new Exception("The exception in method getAllMCCRroupList  is  " + e.getMessage());
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
		}
		return arrMCC;
	}
	
	public HashMap<String,String> gethashMccGroup() throws Exception{
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		String query = null;
		HashMap<String, String> hashmcc = null;
		try {
			connection = super.getConnection();

			query = "select mcccategoryid, mcccategoryname from merch_mcc_group ";

			pstmt = connection.prepareStatement(query);
			rs = (ResultSet) pstmt.executeQuery();
			if (rs != null) {
				hashmcc = new HashMap<String, String>();
				while (rs.next()) {
					
					if(StringUtils.trim(rs.getString("mcccategoryid")) !=null) {
						hashmcc.put( StringUtils.trim(rs.getString("mcccategoryid")),   StringUtils.trim(rs.getString("mcccategoryname")) );

					}
				} // end of while

			} // end of if rs!=null check
			if (hashmcc != null)
				if (hashmcc.size() == 0)
					hashmcc = null;
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1, className, "The exception in method getAllMCCRroupList  is  " + e.getMessage());
			throw new Exception("The exception in method getAllMCCRroupList  is  " + e.getMessage());
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
		}
		return hashmcc;
	}
	
	public ArrayList<String> getAllKYCDocsForMerchant(String merchantCode) throws  Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<String> arrKYCDocsPath = null;
		try{
			connection = super.getConnection();	

			query = "select document_location from merch_kyc_docs where merchantcode =?  ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, merchantCode);
			rs = (ResultSet)pstmt.executeQuery();			
			 if(rs!=null){
				 arrKYCDocsPath  = new ArrayList<String>();
				 	while(rs.next()){	 
				 		arrKYCDocsPath.add( StringUtils.trim(rs.getString("document_location"))  );
				 		} // end of while
				 	} //end of if rs!=null check
			 
			 if(arrKYCDocsPath!=null)
				 if(arrKYCDocsPath.size()==0)
					 arrKYCDocsPath=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllKYCDocsForMerchant()   is  "+e.getMessage());
			throw new Exception ("The exception in method getAllKYCDocsForMerchant()   is  "+e.getMessage());			
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
	
	public ArrayList<Merchant> getMerchantUserDetails(String merchantCode) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		Merchant m_Merchant = null;
		ArrayList <Merchant> arrMerchUserDetails=null; 
		try{
			connection = super.getConnection();	
			
			query = "select merchantid, merchname, merchantcontact, merchantemail, nationalid, merch_hiercharchy, pintries, login_tries, "
					+ " status, designation from merch_user_details where merchantcode=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, merchantCode);
			rs = (ResultSet)pstmt.executeQuery();			
			 if(rs!=null){
				 arrMerchUserDetails= new ArrayList<Merchant>();
				 	while(rs.next()){	 
						 m_Merchant  = new Merchant();
				 		m_Merchant.setMerchanUsertId( StringUtils.trim(rs.getString("merchantid"))  );
				 		m_Merchant.setMerchanUsertName(StringUtils.trim(rs.getString("merchname"))  );
				 		m_Merchant.setMerchanUsercontact( StringUtils.trim(rs.getString("merchantcontact"))  );
				 		m_Merchant.setMerchanUserEmail( StringUtils.trim(rs.getString("merchantemail"))  );
				 		m_Merchant.setMerchanUserNationalId( StringUtils.trim(rs.getString("nationalid"))    );
				 		m_Merchant.setMerchanUserHierarchy(StringUtils.trim(rs.getString("merch_hiercharchy"))  );
				 		m_Merchant.setLoginTries(StringUtils.trim(rs.getString("login_tries"))  );
				 		m_Merchant.setPinTries(StringUtils.trim(rs.getString("pintries"))  );
				 		m_Merchant.setStatus(StringUtils.trim(rs.getString("status"))  );
				 		m_Merchant.setMerchUserDesignation(StringUtils.trim(rs.getString("designation"))  );
				 		arrMerchUserDetails.add(m_Merchant);
				 		} // end of while
			 
				 	} //end of if rs!=null check
			 if(arrMerchUserDetails!=null)
				 if(arrMerchUserDetails.size()==0)
					 arrMerchUserDetails=null;

			 
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getMerchantUserDetails()   is  "+e.getMessage());
			throw new Exception ("The exception in method getMerchantUserDetails()   is  "+e.getMessage());			
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
	
		return arrMerchUserDetails;		
	
	}
	
	public ArrayList<Risk> getMerchantRiskProfiles() throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList <Risk> arrMerchRiskDetails=null; 
		try{
			connection = super.getConnection();	
	
			query = "select riskid, riskdesc, status, paymentaction, createdon from risk_category ";
			
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();			
			 if(rs!=null){
				 arrMerchRiskDetails= new ArrayList<Risk>();
				 	while(rs.next()){	 
				 		Risk m_Risk  = new Risk();
				 		m_Risk.setRiskId( StringUtils.trim(rs.getString("riskid"))  );
				 		m_Risk.setRiskDesc(StringUtils.trim(rs.getString("riskdesc"))  );
				 		m_Risk.setRiskStatus( StringUtils.trim(rs.getString("status"))  );
				 		m_Risk.setRiskPaymentAction( StringUtils.trim(rs.getString("paymentaction"))  );
				 		m_Risk.setCreatedon( StringUtils.trim(rs.getString("createdon"))    );
				 		arrMerchRiskDetails.add(m_Risk);
				 		} // end of while
			 
				 	} //end of if rs!=null check
			 if(arrMerchRiskDetails!=null)
				 if(arrMerchRiskDetails.size()==0)
					 arrMerchRiskDetails=null;

		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getMerchantRiskProfiles()   is  "+e.getMessage());
			throw new Exception ("The exception in method getMerchantRiskProfiles()   is  "+e.getMessage());			
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
	
		return arrMerchRiskDetails;		
	
	}

	public ArrayList<MccGroup> getMerchantMCC() throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList <MccGroup> arrMerchMcc=null; 
		try{
			connection = super.getConnection();	
	
			query = "select mcccategoryid, mcccategoryname, mccfromrange, mcctorange, mccgeneric, createdon from merch_mcc_group ";
			
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();			
			 if(rs!=null){
				 arrMerchMcc= new ArrayList<MccGroup>();
				 	while(rs.next()){	 
				 		MccGroup m_MccGroup  = new MccGroup();
				 		m_MccGroup.setMccId( StringUtils.trim(rs.getString("mcccategoryid"))  );
				 		m_MccGroup.setMccName(StringUtils.trim(rs.getString("mcccategoryname"))  );
				 		m_MccGroup.setMccFromRange( StringUtils.trim(rs.getString("mccfromrange"))  );
				 		m_MccGroup.setMccToRange( StringUtils.trim(rs.getString("mcctorange"))  );
				 		m_MccGroup.setMccGeneric( StringUtils.trim(rs.getString("mccgeneric"))  );
				 		m_MccGroup.setCreatedon( StringUtils.trim(rs.getString("createdon"))  );
				 		arrMerchMcc.add(m_MccGroup);
				 		} // end of while
			 
				 	} //end of if rs!=null check
			 if(arrMerchMcc!=null)
				 if(arrMerchMcc.size()==0)
					 arrMerchMcc=null;

		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getMerchantMCC()   is  "+e.getMessage());
			throw new Exception ("The exception in method getMerchantMCC()   is  "+e.getMessage());			
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
	
		return arrMerchMcc;	
	}

	public boolean AddNewMccGroup(String mccName, String mccFromRange, String mccToRange, String mccGeneric)throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
	
			        //                                     1                2            3            4       5
			 	query = "insert into merch_mcc_group (mcccategoryname, mccfromrange, mcctorange, mccgeneric, createdon ) "
						+ "values (?, ?, ?, ?, ? ) ";
			 	               //  1  2  3  4  5
		
			       	pstmt = connection.prepareStatement(query);
					pstmt.setString(1, mccName );
					pstmt.setString(2, mccFromRange );
					pstmt.setString(3, mccToRange );
					pstmt.setString(4, mccGeneric );
					pstmt.setString(5, Utilities.getMYSQLCurrentTimeStampForInsert() );
					
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
			NeoBankEnvironment.setComment(1,className,"The exception in method AddNewMccGroup  is  "+e.getMessage());
			throw new Exception ("The exception in method AddNewMccGroup  is  "+e.getMessage());
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

	public boolean UpdateMccGroup(String mccId, String mccName, String mccFromRange, String mccToRange, String mccGeneric) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 	
				query = "update merch_mcc_group set mcccategoryname = ?, mccfromrange = ?, mcctorange = ?, mccgeneric = ?   where mcccategoryid = ?";
		
			       	pstmt = connection.prepareStatement(query);
					pstmt.setString(1, mccName );
					pstmt.setString(2, mccFromRange );
					pstmt.setString(3, mccToRange );
					pstmt.setString(4, mccGeneric );
					pstmt.setString(5, mccId );
					
					try {
						pstmt.executeUpdate();
						NeoBankEnvironment.setComment(2,className,"Excecuted query is  "+query + " pstm "+pstmt.toString());

						}catch(Exception e) {
							throw new Exception (" failed query "+query+" "+e.getMessage());
						}
					pstmt.close();
					connection.commit();			 	
					result = true;
		}catch(Exception e){
			connection.rollback();
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method UpdateMccGroup  is  "+e.getMessage());
			throw new Exception ("The exception in method UpdateMccGroup  is  "+e.getMessage());
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

	public boolean AddNewMerchantRiskFactor(String riskDesc, String riskPaymentAction, String riskStatus) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
	
			        //                                 1            2            3        4      
			 	query = "insert into risk_category (riskdesc, paymentaction, status, createdon ) "
						+ "values (?, ?, ?, ? ) ";
			 	               //  1  2  3  4 
		
			       	pstmt = connection.prepareStatement(query);
					pstmt.setString(1, riskDesc );
					pstmt.setString(2, riskPaymentAction );
					pstmt.setString(3, riskStatus );
					pstmt.setString(4, Utilities.getMYSQLCurrentTimeStampForInsert() );
					
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
			NeoBankEnvironment.setComment(1,className,"The exception in method AddNewMerchantRiskFactor  is  "+e.getMessage());
			throw new Exception ("The exception in method AddNewMerchantRiskFactor  is  "+e.getMessage());
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

	public boolean UpdateMerchantRiskFactor(String riskId, String riskDesc, String riskPaymentAction, String riskStatus) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 	
				query = "update risk_category set riskdesc = ?, paymentaction = ?, status = ?   where riskid = ? ";
		
			       	pstmt = connection.prepareStatement(query);
					pstmt.setString(1, riskDesc );
					pstmt.setString(2, riskPaymentAction );
					pstmt.setString(3, riskStatus );
					pstmt.setString(4, riskId );
					
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
			NeoBankEnvironment.setComment(1,className,"The exception in method UpdateMerchantRiskFactor  is  "+e.getMessage());
			throw new Exception ("The exception in method UpdateMerchantRiskFactor  is  "+e.getMessage());
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

	public boolean UpdateMerchantUser(String merchUserId, String userName, String userContact, String userEmail, String userRole, 
			String status, String userDesignation, String merchUserNationalId) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 			 	
				query = "update merch_user_details set merchname = ?, merchantcontact = ?, merchantemail = ?, nationalid = ?, merch_hiercharchy = ?,"
						+ " status = ?, designation = ?    where merchantid = ? ";
		
			       	pstmt = connection.prepareStatement(query);
					pstmt.setString(1, userName );
					pstmt.setString(2, userContact );
					pstmt.setString(3, userEmail );
					pstmt.setString(4, merchUserNationalId );
					pstmt.setString(5, userRole );
					pstmt.setString(6, status );
					pstmt.setString(7, userDesignation );
					pstmt.setString(8, merchUserId );
					
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
			NeoBankEnvironment.setComment(1,className,"The exception in method UpdateMerchantUser  is  "+e.getMessage());
			throw new Exception ("The exception in method UpdateMerchantUser  is  "+e.getMessage());
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

	public boolean UpdateMerchantDetails(String merchantCode, String companyName, String businessPhone, String billRef,String busdDescription, String physicalAddress
			, String merchType, String merchStatus, String merchMcc, String approvedBy, String verifyMerchFlag) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			
			NeoBankEnvironment.setComment(3, className, "insude UpdateMerchantDetails merchantCode " +merchantCode+ " companyName  "+ companyName+" businessPhone "+businessPhone
					+" billRef "+ billRef + "busdDescription "+ busdDescription + " physicalAddress "+ physicalAddress +" merchType "+ merchType+ " merchStatus "+merchStatus
					+" merchMcc "+ merchMcc + " approvedBy "+ approvedBy +" verifyMerchFlag "+ verifyMerchFlag );

			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 			 	
				query = "update merchant_details set companyname = ?, physical_address = ?, bussiness_description = ?, business_phoneno = ?, mcccategoryid = ?,"
						+ " approved_by = ?, merchant_type = ?, status = ?  where merchantcode = ? ";
		
			       	pstmt = connection.prepareStatement(query);
					pstmt.setString(1, companyName );
					pstmt.setString(2, physicalAddress );
					pstmt.setString(3, busdDescription );
					pstmt.setString(4, businessPhone );
					pstmt.setString(5, merchMcc );
					pstmt.setString(6, approvedBy );
					pstmt.setString(7, merchType );
					pstmt.setString(8, merchStatus );
					pstmt.setString(9, merchantCode );
					
					try {
						pstmt.executeUpdate();
						}catch(Exception e) {
							throw new Exception (" failed query "+query+" "+e.getMessage());
						}
					pstmt.close();
					
					if(verifyMerchFlag.equals("Y")) {
						NeoBankEnvironment.setComment(3, className, "insude verifyMerchFlag and creating wallet " +verifyMerchFlag);

						//Create wallet
						
						SimpleDateFormat formatter1 = new SimpleDateFormat ("yyMMdd");  formatter1.setTimeZone(TimeZone.getTimeZone("UTC"));
						String walletId = (formatter1.format(new Date()))+( RandomStringUtils.random(10, false, true)).toString();
						 					
											              //	1		    2	         	3		  4		   5		6		  7           8             9        10            11
						query = "insert into wallet_details (walletid, relationshipno, walletdesc, usertype,  status, currbal, currencyid, lastupdated, createdon, blockcodeid, wallettype  )"
						+ " values (? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
								//  1  2  3  4  5  6  7  8  9 10  11
							pstmt = connection.prepareStatement(query);
							pstmt.setString(1, walletId); 						
							pstmt.setString(2, merchantCode); 
							pstmt.setString(3, merchantCode+ "-1");						
							pstmt.setString(4, "M");						
							pstmt.setString(5, "A");					
							pstmt.setFloat(6, Float.parseFloat("0"));
							pstmt.setString(7, NeoBankEnvironment.getUSDCurrencyId());
							pstmt.setString(8, Utilities.getMYSQLCurrentTimeStampForInsert());
							pstmt.setString(9, Utilities.getMYSQLCurrentTimeStampForInsert());
							pstmt.setString(10, "0");
							pstmt.setString(11, "F");//F= Fiat, B= Bitcoin, P= Payment Porte Token, V= Vessel Coin
						try {
							pstmt.executeUpdate();
						}catch(Exception e) {
							throw new Exception (" failed query "+query+" "+e.getMessage());
						}
							pstmt.close();
							
							
							//Change status for all users to active
							
							query = "update merch_user_details set status = ?  where merchantcode = ? ";
						 	pstmt = connection.prepareStatement(query);
							pstmt.setString(1, "A" );
							pstmt.setString(2, merchantCode );
							
							try {
								pstmt.executeUpdate();
								}catch(Exception e) {
									throw new Exception (" failed query "+query+" "+e.getMessage());
								}
							pstmt.close();
							//Risk profile TODO 
							
							/*
							query = "update merchant_risk_details set merchantcode = ?, riskid=?, ops_comment= ?, status=?,createdon=?  where merchantcode = ? ";
						 	pstmt = connection.prepareStatement(query);
							pstmt.setString(1, "A" );
							pstmt.setString(2, merchantCode );
							pstmt.setString(3, "" );
							pstmt.setString(4, "new merchant" );
							pstmt.setString(5, Utilities.getMYSQLCurrentTimeStampForInsert() );
							pstmt.setString(6, merchantCode );
							
							try {
								pstmt.executeUpdate();
								}catch(Exception e) {
									throw new Exception (" failed query "+query+" "+e.getMessage());
								}
							pstmt.close();
							*/
												
												
					}
								
					connection.commit();			 	
					result = true;
		}catch(Exception e){
			connection.rollback();
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method UpdateMerchantDetails  is  "+e.getMessage());
			throw new Exception ("The exception in method UpdateMerchantDetails  is  "+e.getMessage());
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
	
	public ArrayList<Merchant> getAllMerchants() throws Exception {
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		String query = null;
		ArrayList<Merchant> arrMerchants = null;
		try {
			connection = super.getConnection();
			
			query = "select merchantcode, merch_ref, physical_address, companyname, bussiness_description, business_phoneno, mcccategoryid, "
					+ " merchant_type, status, createdon, expriry from merchant_details where status != ? order by createdon desc limit 1000 ";

			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "V");
			rs = (ResultSet) pstmt.executeQuery();
			if (rs != null) {
				arrMerchants = new ArrayList<Merchant>();
				while (rs.next()) {
					Merchant m_Merchant = new Merchant();
					m_Merchant.setMerchantCode(StringUtils.trim(rs.getString("merchantcode")));
					m_Merchant.setMerchRef(StringUtils.trim(rs.getString("merch_ref")));
					m_Merchant.setPhysicalAddress(StringUtils.trim(rs.getString("physical_address")));
					m_Merchant.setCompanyName(StringUtils.trim(rs.getString("companyname")));
					m_Merchant.setBusinessDescription(StringUtils.trim(rs.getString("bussiness_description")));
					m_Merchant.setBusinessPhoneNumber(StringUtils.trim(rs.getString("business_phoneno")));
					m_Merchant.setMccCategoryId(StringUtils.trim(rs.getString("mcccategoryid")));
					m_Merchant.setMerchantType(StringUtils.trim(rs.getString("merchant_type")));
					m_Merchant.setStatus(StringUtils.trim(rs.getString("status")));
					m_Merchant.setCreatedOn(StringUtils.trim(rs.getString("createdon")));
					m_Merchant.setExpiryDate(StringUtils.trim(rs.getString("expriry")));
					arrMerchants.add(m_Merchant);
				} // end of while

			} // end of if rs!=null check
			if (arrMerchants != null)
				if (arrMerchants.size() == 0)
					arrMerchants = null;
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1, className,
					"The exception in method getAllMerchants is  " + e.getMessage());
			throw new Exception("The exception in method getAllMerchants is  " + e.getMessage());
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
		}
		return arrMerchants;
	}

	public ArrayList<Merchant> getMerchantsPricingPlan() throws Exception{ //TODO 
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		String query = null;
		ArrayList<Merchant> arrMerchantsPlans = null;
		try {
			connection = super.getConnection();
			
			query = "select merchantcode, merch_ref, physical_address, companyname, bussiness_description, business_phoneno, mcccategoryid, "
					+ " merchant_type, status, createdon, expriry from merchant_details where status != ? order by createdon desc limit 1000 ";

			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "A");
			rs = (ResultSet) pstmt.executeQuery();
			if (rs != null) {
				arrMerchantsPlans = new ArrayList<Merchant>();
				while (rs.next()) {
					Merchant m_Merchant = new Merchant();
					m_Merchant.setMerchantCode(StringUtils.trim(rs.getString("merchantcode")));
					m_Merchant.setMerchRef(StringUtils.trim(rs.getString("merch_ref")));
					m_Merchant.setPhysicalAddress(StringUtils.trim(rs.getString("physical_address")));
					m_Merchant.setCompanyName(StringUtils.trim(rs.getString("companyname")));
					m_Merchant.setBusinessDescription(StringUtils.trim(rs.getString("bussiness_description")));
					m_Merchant.setBusinessPhoneNumber(StringUtils.trim(rs.getString("business_phoneno")));
					m_Merchant.setMccCategoryId(StringUtils.trim(rs.getString("mcccategoryid")));
					m_Merchant.setMerchantType(StringUtils.trim(rs.getString("merchant_type")));
					m_Merchant.setStatus(StringUtils.trim(rs.getString("status")));
					m_Merchant.setCreatedOn(StringUtils.trim(rs.getString("createdon")));
					m_Merchant.setExpiryDate(StringUtils.trim(rs.getString("expriry")));
					arrMerchantsPlans.add(m_Merchant);
				} // end of while

			} // end of if rs!=null check
			if (arrMerchantsPlans != null)
				if (arrMerchantsPlans.size() == 0)
					arrMerchantsPlans = null;
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1, className,
					"The exception in method getMerchantsPricingPlan is " + e.getMessage());
			throw new Exception("The exception in method getMerchantsPricingPlan is  " + e.getMessage());
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
		}
		return arrMerchantsPlans;
	}
	

	public ArrayList<Risk> getMerchantRiskProfile() throws Exception{
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		String query = null;
		ArrayList<Risk> arrRisk = null;
		try {
			connection = super.getConnection();
			query = "select riskid, riskdesc  from risk_category  ";

			pstmt = connection.prepareStatement(query);
			rs = (ResultSet) pstmt.executeQuery();
			if (rs != null) {
				arrRisk = new ArrayList<Risk>();
				while (rs.next()) {
					Risk m_Risk = new Risk();
					m_Risk.setRiskId(StringUtils.trim(rs.getString("riskid")));
					m_Risk.setRiskDesc(StringUtils.trim(rs.getString("riskdesc")));
					arrRisk.add(m_Risk);
				} // end of while

			} // end of if rs!=null check
			if (arrRisk != null)
				if (arrRisk.size() == 0)
					arrRisk = null;
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1, className, "The exception in method getMerchantRiskProfile  is  " + e.getMessage());
			throw new Exception("The exception in method getMerchantRiskProfile  is  " + e.getMessage());
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
		}
		return arrRisk;
	}



}
