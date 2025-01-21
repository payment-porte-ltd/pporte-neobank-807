package com.pporte.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.pporte.NeoBankEnvironment;
import com.pporte.model.PricingPlan;
import com.pporte.model.PricingPlanRules;
import com.pporte.utilities.Utilities;

public class OpsManagePricingPlanDao extends HandleConnections {
	public static String className = OpsManagePricingPlanDao.class.getName();

	public ArrayList<PricingPlan> getCustomerPricingPlan() throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<PricingPlan> arrPricingPlan = null;
		try{
		  connection = super.getConnection();	
		  query = " select planid, plan_name, plan_duration_days, status, plan_price, plandesc1, plandesc2, "
		  		+ " plandesc3, plandesc4 from customer_price_plan ";		  
		 pstmt = connection.prepareStatement(query);
		 rs = pstmt.executeQuery();
		  if(rs!=null){	
			  arrPricingPlan = new ArrayList<PricingPlan>();
			 	while(rs.next()){	
			 		PricingPlan m_PricingPlan = new PricingPlan();
			 		m_PricingPlan.setPlanId( StringUtils.trim(rs.getString("planid")));
			 		m_PricingPlan.setPlanName( StringUtils.trim(rs.getString("plan_name")));
			 		m_PricingPlan.setPlanDuration( StringUtils.trim(rs.getString("plan_duration_days")));
			 		m_PricingPlan.setStatus( StringUtils.trim(rs.getString("status")));
			 		m_PricingPlan.setPlanPrice( StringUtils.trim(rs.getString("plan_price")));
			 		m_PricingPlan.setPlanDesc1(StringUtils.trim(rs.getString("plandesc1")));
			 		m_PricingPlan.setPlanDesc2( StringUtils.trim(rs.getString("plandesc2")));
			 		m_PricingPlan.setPlanDesc3( StringUtils.trim(rs.getString("plandesc3")));
			 		m_PricingPlan.setPlanDesc4( StringUtils.trim(rs.getString("plandesc4")));
			 		arrPricingPlan.add(m_PricingPlan);
		 		} // end of while						 	
		 	} //end of if 
		  	if(arrPricingPlan!=null)
		  		if(arrPricingPlan.size()==0)
		  			arrPricingPlan=null;			  
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}finally{
		if(connection!=null)
			try {
				super.close();
			} catch (SQLException e) {
				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
			if(pstmt!=null) pstmt.close(); if(rs!=null) rs.close();
		}
		return arrPricingPlan;	
		}

	public boolean addPlanDetails(String addPlanName,String addPlanStatus, String addPlanPrice, 
			String addPlanDesc1, String addPlanDesc2, String addPlanDesc3, String addPlanDesc4,String addPlanID) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		try{
			connection = super.getConnection();	
			 connection.setAutoCommit(false);
			  //                                          1				2				3			4		5          6  		7			8			9        10
			 query = "insert into customer_price_plan (plan_name,status, plan_price,plandesc1, plandesc2, plandesc3, plandesc4, createdon,planid) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?,?) ";	
			 //                    1  2  3  4  5  6  7   8 
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, addPlanName); 
				pstmt.setString(2, addPlanStatus); 	
				pstmt.setBigDecimal(3, new BigDecimal(addPlanPrice));  
				pstmt.setString(4, addPlanDesc1); 	
				pstmt.setString(5, addPlanDesc2); 	
				pstmt.setString(6, addPlanDesc3); 	
				pstmt.setString(7, addPlanDesc4); 	
				pstmt.setString(8, Utilities.getMYSQLCurrentTimeStampForInsert()); 	
				pstmt.setString(9, addPlanID); 	
				
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
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
			}
		return result;	
	}

	public boolean updatePricingPlanDetails(String editPlanId, String editPlanName,
			String editPlanStatus, String editPlanPrice, String editPlanDesc1, String editPlanDesc2, String editPlanDesc3, String editPlanDesc4) throws Exception{
		NeoBankEnvironment.setComment(3,className,"editPlanId "+editPlanId+" editPlanName "
			+editPlanName+" editPlanStatus "+editPlanStatus+" editPlanPrice "+editPlanPrice+" editPlanDesc3 "+editPlanDesc3+" editPlanDesc4 ");

		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		try{
			connection = super.getConnection();	
			 connection.setAutoCommit(false);
			  //                                        1					2					3			4				5    			6				7			8					9
			 query = "update customer_price_plan set plan_name = ?, status = ?, plan_price = ? , plandesc1 = ?, plandesc2 = ?, plandesc3 = ?, plandesc4 = ? where planid = ?";						
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, editPlanName); 
				pstmt.setString(2, editPlanStatus); 	
				pstmt.setFloat(3, Float.parseFloat(editPlanPrice)); 
				
				pstmt.setString(4, editPlanDesc1);
				pstmt.setString(5, editPlanDesc2);
				pstmt.setString(6, editPlanDesc3);
				pstmt.setString(7, editPlanDesc4);
				pstmt.setInt(8, Integer.parseInt(editPlanId)  ); 

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
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
			}
		return result;	
	}

	public PricingPlan getPricingPlanForCustomer(String customerId,String relationshipNo,String custPhoneNo) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		PricingPlan m_PricingPlan = null;
		try{
		  connection = super.getConnection();
		  
		  query = " select a.plan_id plan_id, a.customerid customerid, a.plan_start_date plan_start_date,  "
		  		+ "a.status status, b.plan_name plan_name, b.plan_price plan_price ,a.reason reason from customer_price_plan_allocation a, customer_price_plan b,customer_details c"
		  		+ "  where a.plan_id = b.planid and a.customerid=c.relationshipno and a.status = ? and  ";		  
		  
					if(relationshipNo.equals("")==false) {	
						query+= "c. relationshipno = ? and  ";
					}
					if(customerId.equals("")==false) {
						query+= "c.customerid =? and  ";
					}
					if(custPhoneNo.equals("")==false) {
						query+= "c. custcontact =? and  ";
					}
			query+= "  1=1 ";
			
			NeoBankEnvironment.setComment(3,className,"search query being executed  is  "+query);


			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "A");

			if(relationshipNo.equals("")==false) {
				pstmt.setString(2, relationshipNo);
			}
			if(customerId.equals("")==false) {
				pstmt.setString(2, Utilities.tripleEncryptData(customerId));
			}
			if(custPhoneNo.equals("")==false) {
				pstmt.setString(2, Utilities.tripleEncryptData(custPhoneNo));
			}
		
		 rs = pstmt.executeQuery();
		  if(rs!=null){	
			 	while(rs.next()){	
			 		 m_PricingPlan = new PricingPlan();
			 		m_PricingPlan.setPlanId( StringUtils.trim(rs.getString("plan_id")));
			 		m_PricingPlan.setCustomerid(StringUtils.trim(rs.getString("customerid"))); // RelationshipNo
			 		m_PricingPlan.setStartDate(StringUtils.trim(rs.getString("plan_start_date")));
			 		m_PricingPlan.setStatus( StringUtils.trim(rs.getString("status")));
			 		m_PricingPlan.setPlanName( StringUtils.trim(rs.getString("plan_name")));			 		
			 		m_PricingPlan.setPlanPrice( StringUtils.trim(rs.getString("plan_price")));
			 		m_PricingPlan.setReason( StringUtils.trim(rs.getString("reason")));
		 		} // end of while						 	
		 	} //end of if 
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}finally{
		if(connection!=null)
			try {
				super.close();
			} catch (SQLException e) {
				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
			if(pstmt!=null) pstmt.close(); if(rs!=null) rs.close();
		}
		return m_PricingPlan;
		}

	public boolean updatePricingPlanForCustomer(String planId, String customerId, String status) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		try{
			connection = super.getConnection();	
			 connection.setAutoCommit(false);
			  // CHeck if the status is inactive - then make the plan end-date = current date too  
			 if(status.equals("I")) {
				 
				 SimpleDateFormat formatter2 = new SimpleDateFormat ("yyyy-MM-dd"); java.util.Date date = new Date();
				 String currentDate = formatter2.format(date);	
				 query = "update customer_price_plan_allocation set  plan_end_date = ?, status = ? where plan_id = ? and customerid = ? ";
					
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, currentDate); // Expires on the current date
					pstmt.setString(2, status); // Inactive
					pstmt.setInt(3, Integer.parseInt(planId)); 	
					pstmt.setString(4, customerId); 	// relationshipno
					try {
						pstmt.executeUpdate();
						}catch(Exception e) {
							throw new Exception (" failed query "+query+" "+e.getMessage());
						}					 
				 
			 }else {
			 // this situation will not arise at this stage
			 query = "update customer_price_plan_allocation set status = ? where plan_id = ? and customerid = ?";											
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, status); 
				pstmt.setInt(2,  Integer.parseInt(planId)); 
				pstmt.setString(3, customerId); 	
				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}	
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
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
			}
		return result;	
		}

	public boolean changePricingPlanForCustomer(String planId, String customerId,String reason, String existingPlanId) throws Exception{
		PreparedStatement pstmt=null;	Connection connection = null;	ResultSet rs=null;	String query = null;
		boolean result = false;  
		String currentDate = null;
		try{
			connection = super.getConnection();	
			 connection.setAutoCommit(false);
			 currentDate=Utilities.getMYSQLCurrentTimeStampForInsert();
		    
			  //                                                  1		     	2			      3				     4		     5	
			 query = "update customer_price_plan_allocation set plan_id=?,  plan_start_date=?, reason=? where customerid=? and plan_id=? ";
																		
				pstmt = connection.prepareStatement(query);
				pstmt.setInt(1, Integer.parseInt(planId)); 
				pstmt.setString(2, currentDate); 
				pstmt.setString(3, reason); 	
				pstmt.setString(4, customerId); 
				pstmt.setInt(5,Integer.parseInt(existingPlanId));
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
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if (currentDate!=null)currentDate=null;
			}
		return result;	
		}
	//TODO Remove this function, not required
	public ArrayList<PricingPlanRules> getRulesByPricingPlan(String planId) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<PricingPlanRules> arrPricingPlanRules = null;
		try{
		  connection = super.getConnection();	
		  query = " select rule_id, sequenceno  from customer_price_plan_rules where planid = ? and status = ? order by sequenceno desc ";		  
		 pstmt = connection.prepareStatement(query);
		 pstmt.setInt(1, Integer.parseInt(planId));	
		 pstmt.setString(2, "A");
		 rs = pstmt.executeQuery();
		  if(rs!=null){	
			  arrPricingPlanRules = new ArrayList<PricingPlanRules>();
			 	while(rs.next()){	
			 		PricingPlanRules m_PricingPlanRules = new PricingPlanRules();
			 		m_PricingPlanRules.setRuleId( StringUtils.trim(rs.getString("rule_id")));
			 		m_PricingPlanRules.setSequenceId( StringUtils.trim(rs.getString("sequenceno")));
			 		arrPricingPlanRules.add(m_PricingPlanRules);
		 		} // end of while						 	
		 	} //end of if 
		  	if(arrPricingPlanRules!=null)
		  		if(arrPricingPlanRules.size()==0)
		  			arrPricingPlanRules=null;			  
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}finally{
		if(connection!=null)
			try {
				super.close();
			} catch (SQLException e) {
				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
			if(pstmt!=null) pstmt.close(); if(rs!=null) rs.close();
		}
		return arrPricingPlanRules;		
	}

	public String getCustomerPricingPlanId(String relationshipNo) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String planId = "";
		SimpleDateFormat formatter1 = null; java.util.Date date = null; String currentDate = null;
		try{
		  connection = super.getConnection();
		  formatter1 = new SimpleDateFormat ("yyyy-MM-dd"); date = new Date();
		  currentDate = formatter1.format(date);
		  
		  query = " select plan_id from customer_price_plan_allocation where customerid = ? and  status = ? ";		  
		 pstmt = connection.prepareStatement(query);
		 pstmt.setString(1, relationshipNo);
		 pstmt.setString(2, "A");
		 rs = pstmt.executeQuery();
		  if(rs!=null){	
			 	while(rs.next()){	
			 		planId =  StringUtils.trim(rs.getString("plan_id"));
		 		} // end of while						 	
		 	} //end of if 
		//  NeoBankEnvironment.setComment(3, className, "planId "+planId+" relationshipNo "+relationshipNo+" currentDate "+currentDate);
		  //TODO We have to make a check if there plan_end_date has gone past the current_date even though the plan is active.
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}finally{
		if(connection!=null)
			try {
				super.close();
			} catch (SQLException e) {
				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
			if(pstmt!=null) pstmt.close(); if(rs!=null) rs.close(); if(currentDate!=null) currentDate = null;
			if(formatter1!=null) formatter1 = null; if(date!=null) date = null;
		}
		return planId;
	}
	public ArrayList<PricingPlan> getCustomerSilverPricingPlan() throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<PricingPlan> arrPricingPlan = null;
		try{
		  connection = super.getConnection();	
		  query = " select planid, plan_name, plan_duration_days, status, plan_price, plandesc1, plandesc2, "
		  		+ " plandesc3, plandesc4 from customer_price_plan where planid=? ";		  
		 pstmt = connection.prepareStatement(query);
		 pstmt.setInt(1, 1);
		 rs = pstmt.executeQuery();
		  if(rs!=null){	
			  arrPricingPlan = new ArrayList<PricingPlan>();
			 	while(rs.next()){	
			 		PricingPlan m_PricingPlan = new PricingPlan();
			 		m_PricingPlan.setPlanId( StringUtils.trim(rs.getString("planid")));
			 		m_PricingPlan.setPlanName( StringUtils.trim(rs.getString("plan_name")));
			 		m_PricingPlan.setPlanDuration( StringUtils.trim(rs.getString("plan_duration_days")));
			 		m_PricingPlan.setStatus( StringUtils.trim(rs.getString("status")));
			 		m_PricingPlan.setPlanPrice( StringUtils.trim(rs.getString("plan_price")));
			 		m_PricingPlan.setPlanDesc1(StringUtils.trim(rs.getString("plandesc1")));
			 		m_PricingPlan.setPlanDesc2( StringUtils.trim(rs.getString("plandesc2")));
			 		m_PricingPlan.setPlanDesc3( StringUtils.trim(rs.getString("plandesc3")));
			 		m_PricingPlan.setPlanDesc4( StringUtils.trim(rs.getString("plandesc4")));
			 		arrPricingPlan.add(m_PricingPlan);
		 		} // end of while						 	
		 	} //end of if 
		  	if(arrPricingPlan!=null)
		  		if(arrPricingPlan.size()==0)
		  			arrPricingPlan=null;			  
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}finally{
		if(connection!=null)
			try {
				super.close();
			} catch (SQLException e) {
				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
			if(pstmt!=null) pstmt.close(); if(rs!=null) rs.close();
		}
		return arrPricingPlan;	
		}

	public String getCurrentPlanPrice( String relNo) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String currentPricePlan=null;
		try {
			 connection = super.getConnection();	
			  query = " select a.plan_id plan_id,a.status status,b.plan_price plan_price "
			  		+ "from customer_price_plan_allocation a,customer_price_plan b where a.plan_id=b.planid and a.status=? and a.customerid=?";	
			  pstmt = connection.prepareStatement(query);
				 pstmt.setString(1, "A");
				 pstmt.setString(2, relNo);
				 rs = pstmt.executeQuery();
				  if(rs!=null){	
					 	while(rs.next()){
					 		currentPricePlan=StringUtils.trim(rs.getString("plan_price"));
				 		} // end of while						 	
				 	} //end of if 
				  	
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}
		finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
				}
				if(pstmt!=null) pstmt.close(); if(rs!=null) rs.close();
			}
			return currentPricePlan;
	}

	public String getPlanPrice() throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String pricePlan=null;
		try {
			connection = super.getConnection();	
			query="select plan_price from customer_price_plan where planid=?";
			  
			  pstmt = connection.prepareStatement(query);
				 pstmt.setInt(1, 2);
				 rs = pstmt.executeQuery();
				  if(rs!=null){	
					 	while(rs.next()){
					 		pricePlan=StringUtils.trim(rs.getString("plan_price"));
				 		} // end of while						 	
				 	} //end of if 
				  	
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}
		finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
				}
				if(pstmt!=null) pstmt.close(); if(rs!=null) rs.close();
			}
			return pricePlan;
	}
	


}
