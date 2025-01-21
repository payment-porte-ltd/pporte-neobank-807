package com.pporte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;
import com.pporte.NeoBankEnvironment;
import com.pporte.model.Customer;
import com.pporte.model.LoyaltyRules;
import com.pporte.utilities.Utilities;

public class OpsSystemManageLoyaltyDao extends HandleConnections {
	private static String className = OpsSystemManageLoyaltyDao.class.getSimpleName();

	
	public ArrayList<LoyaltyRules> getAllLoyaltyRules() throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<LoyaltyRules> arrLoyaltyRules = null;
		try{
			connection = super.getConnection();	

		    query =	"select paymode, rulesdesc, pointsconversion, cryptoconversion, status, usertype, createdon from loyalty_rules order by " 
		    		+ "createdon desc ";
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();			
			 if(rs!=null){
				 arrLoyaltyRules = new ArrayList<LoyaltyRules>();
				 	while(rs.next()){	 
				 		LoyaltyRules m_LoyaltyRules=new LoyaltyRules();
				 		m_LoyaltyRules.setPayMode( StringUtils.trim(rs.getString("paymode"))   ) ;
				 		m_LoyaltyRules.setRuleDesc(StringUtils.trim(rs.getString("rulesdesc")) );
				 		m_LoyaltyRules.setPointsConvertRatio( StringUtils.trim(rs.getString("pointsconversion"))    );
				 		m_LoyaltyRules.setCryptoConvertRatio( StringUtils.trim(rs.getString("cryptoconversion"))            );
				 		m_LoyaltyRules.setStatus( StringUtils.trim(rs.getString("status"))    );				 		
				 		m_LoyaltyRules.setCreatedon( StringUtils.trim(rs.getString("createdon"))    );				 		
				 		m_LoyaltyRules.setUserType( StringUtils.trim(rs.getString("usertype"))    );				 		
				 		arrLoyaltyRules.add(m_LoyaltyRules);
				 		} // end of while
				 	
				 	} //end of if rs!=null check
			 				
			 if(arrLoyaltyRules!=null)
				 if(arrLoyaltyRules.size()==0) 
					 arrLoyaltyRules=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(3,className,"The exception in method getAllLoyaltyRules  is  "+e.getMessage());
			throw new Exception ("The exception in method getAllLoyaltyRules  is  "+e.getMessage());			
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
		return arrLoyaltyRules;
	}

	public boolean UpdateLoyaltyRules(String payMode, String ruleDesc, String pointConversion, String crytoConversion,
	    String loyaltyStatus, String lyltyUserType) throws Exception{
				PreparedStatement pstmt=null;
				Connection connection = null;
				String query = null;
				boolean result = false;
				try{
					 connection = super.getConnection();
					 connection.setAutoCommit(false);
					 	
			 			query = " update loyalty_rules set rulesdesc = ?, pointsconversion = ?, cryptoconversion = ?, usertype = ?, status = ? where paymode = ?";

					       	pstmt = connection.prepareStatement(query);
							pstmt.setString(1, ruleDesc );
							pstmt.setString(2, pointConversion );
							pstmt.setString(3, crytoConversion );
							pstmt.setString(4, lyltyUserType );
							pstmt.setString(5, loyaltyStatus );
							pstmt.setString(6, payMode );
							
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
					NeoBankEnvironment.setComment(1,className,"The exception in method UpdateLoyaltyRules  is  "+e.getMessage());
					throw new Exception ("The exception in method UpdateLoyaltyRules  is  "+e.getMessage());
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

	public boolean AddNewLoyaltyRules(String payMode, String ruleDesc, String pointConversion, String crytoConversion,
			String loyaltyStatus, String lyltyUserType) throws Exception{
			PreparedStatement pstmt=null;
			Connection connection = null;
			String query = null;
			boolean result = false;
			try{
				 connection = super.getConnection();
				 connection.setAutoCommit(false);
				 
	                   //                                 1        2               3                4             5        6       7             
				   query = "insert into loyalty_rules (paymode, rulesdesc, pointsconversion, cryptoconversion, usertype, status, createdon ) "
							+ "values (?, ?, ?, ?, ?, ?, ? ) ";
				 	               //  1  2  3  4  5  6  7   
		 				
				       	pstmt = connection.prepareStatement(query);
						pstmt.setString(1, payMode );
						pstmt.setString(2, ruleDesc );
						pstmt.setString(3, pointConversion );
						pstmt.setString(4, crytoConversion );
						pstmt.setString(5, lyltyUserType );
						pstmt.setString(6, loyaltyStatus );
						pstmt.setString(7, Utilities.getMYSQLCurrentTimeStampForInsert() );
						
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
				NeoBankEnvironment.setComment(1,className,"The exception in method AddNewLoyaltyRules  is  "+e.getMessage());
				throw new Exception ("The exception in method AddNewLoyaltyRules  is  "+e.getMessage());
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
	
	public ArrayList<Customer> getAllCustomerLoyalty() throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<Customer> arrAllCustLoyalty = null;
		try{
			connection = super.getConnection();	

			query = " select a.relationshipno relationshipno, a.customerid customerid, a.customername customername, "
					+" a.custemail custemail, a.custcontact custcontact, a.status status, b.pointbalance pointbalance  from customer_details a, loyalty_points_bc b " 
					+" where b.sequenceid=(select max(b.sequenceid) from loyalty_points_bc b where b.relationshipno = a.relationshipno) ";
			
						
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();			
			 if(rs!=null){
				 arrAllCustLoyalty = new ArrayList<Customer>();
				 	while(rs.next()){	 
				 		Customer c_CustomerDetails=new Customer();
				 		c_CustomerDetails.setRelationshipNo( StringUtils.trim(rs.getString("relationshipno"))    );
				 		c_CustomerDetails.setCustomerId( Utilities.tripleDecryptData (StringUtils.trim(rs.getString("customerid"))   ) );			 	
				 		c_CustomerDetails.setCustomerName(StringUtils.trim(rs.getString("customername"))  );
				 		c_CustomerDetails.setEmail( Utilities.tripleDecryptData (StringUtils.trim(rs.getString("custemail"))  )  );
				 		c_CustomerDetails.setContact(Utilities.tripleDecryptData ( StringUtils.trim(rs.getString("custcontact"))   ) );
				 		c_CustomerDetails.setStatus( StringUtils.trim(rs.getString("status"))    );
				 		c_CustomerDetails.setPointsBalance(StringUtils.trim(rs.getString("pointbalance")));
				 		arrAllCustLoyalty.add(c_CustomerDetails);
				 		} // end of while
				 	
				 	} //end of if rs!=null check
			 if(arrAllCustLoyalty!=null)
				 if(arrAllCustLoyalty.size()==0) 
					 arrAllCustLoyalty=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllCustomerLoyalty  is  "+e.getMessage());
			throw new Exception ("The exception in method getAllCustomerLoyalty  is  "+e.getMessage());			
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
		return arrAllCustLoyalty;
       }

	public boolean checkifLoyaltyRuleExist(String payMode, String ruleDesc, String userType) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String tempPaymode=null;
		boolean isExist = false;
			try {
			NeoBankEnvironment.setComment(2,className,"payMode  "+payMode+ " ruleDesc "+ruleDesc + " userType "+ userType );

			connection = super.getConnection();	
				query=" select paymode from loyalty_rules where paymode = ? and usertype = ? and status = ? ";
				pstmt=connection.prepareStatement(query);
				pstmt.setString(1, payMode);
				pstmt.setString(2, userType);
				pstmt.setString(3, "A");
				rs= (ResultSet)pstmt.executeQuery();
				if (rs!=null) {
					while (rs.next()) {
						tempPaymode= StringUtils.trim(rs.getString("paymode"));
					}
				}
				if(tempPaymode!=null && !tempPaymode.isEmpty()) {
					isExist  = true;
				}
			}catch(Exception e){
				NeoBankEnvironment.setComment(1,className,"The exception in method CheckifLoyaltyRuleExist  is  "+e.getMessage());
			}finally{
				if(connection!=null)
					try {
						super.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if(rs!=null) rs.close();
					if(pstmt!=null) pstmt.close(); 
					if(tempPaymode!=null) tempPaymode = null; 
					
				}
		return isExist;
	}

	public boolean initiateOpsCustLoyaltyRedeem(String reletionshipNo, String pointsAccrude) {

		//TODO Redeem Loyalty for customer

		
		
		
		return false;
	}

	
	
 }
	
