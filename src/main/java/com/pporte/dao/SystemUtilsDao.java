package com.pporte.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.model.AssetCoin;
import com.pporte.model.AuditTrail;
import com.pporte.model.PricingDetails;
import com.pporte.model.PricingSlabRates;
import com.pporte.model.TransactionLimitDetails;
import com.pporte.model.TransactionRules;
import com.pporte.utilities.Utilities;

public class SystemUtilsDao extends HandleConnections {

	private static String className = SystemUtilsDao.class.getSimpleName();
	
	public boolean addAuditTrail(String userId, String userType, String moduleCode, String comment) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 if(!userId.equals("")) {
				                            	//		1		2			3			4		5				
				 query = "insert into audit_trail 	(userid, usertype, modulecode, comment, trailtime) "
							+ "values (?, ?, ?, ?, ?) ";
							//		   1  2  3  4  5	  
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, userId); 					
					pstmt.setString(2, userType); 					
					pstmt.setString(3, moduleCode);					
					pstmt.setString(4, comment);					
					pstmt.setString(5, Utilities.getMYSQLCurrentTimeStampForInsert());					
					try {
						pstmt.executeUpdate();
						}catch(Exception e) {
							throw new Exception (" failed query "+query+" "+e.getMessage());
						}
					
					connection.commit();
					result = true;
			 }
		}catch(Exception e){
			connection.rollback(); result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method addAuditTrail  is  "+e.getMessage());
			throw new Exception ("The exception in method addAuditTrail  is  "+e.getMessage());
		}finally{
		if(connection!=null)
			try {
				super.close();
			} catch (SQLException e) {
				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
			if(pstmt!=null) pstmt.close(); if (userId!=null) userId=null;if (userType!=null) userType=null; if (moduleCode!=null) moduleCode=null;
			if (comment!=null) comment=null;
		}
		return result;
	}
	
	public ArrayList<AuditTrail> getAllAuditTrails() throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<AuditTrail> arrAudittrail = null;
		try{
			connection = super.getConnection();	

			query = "select trailid, userid, usertype, modulecode, comment, trailtime from audit_trail order by  trailtime desc limit 1000 ";
			
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();			
			 if(rs!=null){
				 arrAudittrail = new ArrayList<AuditTrail>();
				 	while(rs.next()){	 
				 		AuditTrail m_AuditTrail=new AuditTrail();
				 		m_AuditTrail.setTraiId( StringUtils.trim(rs.getString("trailid"))    );
				 		m_AuditTrail.setUserId( StringUtils.trim(rs.getString("userid"))  );
				 		m_AuditTrail.setUserType( StringUtils.trim(rs.getString("usertype"))  );
				 		m_AuditTrail.setModuleCode( StringUtils.trim(rs.getString("modulecode"))    );
				 		m_AuditTrail.setComment( StringUtils.trim(rs.getString("comment"))    );
				 		m_AuditTrail.setTrailTime( StringUtils.trim(rs.getString("trailtime"))    );
				 		
				 		arrAudittrail.add(m_AuditTrail);
				 		} // end of while
				 	
				 	} //end of if rs!=null check
			 if(arrAudittrail!=null) if(arrAudittrail.size()==0) arrAudittrail=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllAuditTrails  is  "+e.getMessage());
			throw new Exception ("The exception in method getAllAuditTrails  is  "+e.getMessage());			
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
		return arrAudittrail;
	}
	
	
	public ArrayList<AuditTrail> searchAuditLogs(String userId, String userType, String comment, String date)   throws Exception {
		   PreparedStatement pstmt=null;
			Connection connection = null;
			ResultSet rs=null;
			String query = null;
			ArrayList<AuditTrail> arrAudittrail = null;
			try {
				NeoBankEnvironment.setComment(3,className,"in  searchAuditLogs searched userId is "+
						userId+ "searched userType is  "+userType+ "searched comment  "+ comment         );

				connection = super.getConnection();	                    
	                                //  1             2           3             4      5             6         7       8
				query = "select trailid, userid, usertype, modulecode, comment, trailtime from audit_trail where   ";
				if(userId.equals("")==false) {	
					query+= "  userid = ? and  ";
				}else {
						if(userType.equals("")==false) {
							query+= "  usertype like '%"+userType+"%' and  ";
						}
						if(comment.equals("")==false) {
							query+= " comment like '%"+comment+"%' and  ";
						}
						if(date.equals("")==false) {
							query+= " trailtime like '%"+date+"%' and  ";
						}
						
				}
				query+= "  1=1 ";
				
				//PPWalletEnvironment.setComment(3,className," Final query is" + arrAllCust.size());

				pstmt = connection.prepareStatement(query);
				//pstmt.setString(1, userId);
				if(userId.equals("")==false) {
					pstmt.setString(1, userId);
				}
				
				
			
				rs = (ResultSet)pstmt.executeQuery();
				
				 if(rs!=null){
					 arrAudittrail = new ArrayList<AuditTrail>();
					 	while(rs.next()){	 
					 		AuditTrail m_AuditTrail=new AuditTrail();
					 		m_AuditTrail.setTraiId( StringUtils.trim(rs.getString("trailid"))    );
					 		m_AuditTrail.setUserId( StringUtils.trim(rs.getString("userid"))  );
					 		m_AuditTrail.setUserType( StringUtils.trim(rs.getString("usertype"))  );
					 		m_AuditTrail.setModuleCode( StringUtils.trim(rs.getString("modulecode"))    );
					 		m_AuditTrail.setComment( StringUtils.trim(rs.getString("comment"))    );
					 		m_AuditTrail.setTrailTime( StringUtils.trim(rs.getString("trailtime"))    );
					 		
					 		arrAudittrail.add(m_AuditTrail);
					 		} 
					 	NeoBankEnvironment.setComment(3,className," Total search Results is" + arrAudittrail.size());

					 	} 
				 if(arrAudittrail!=null)
					 if(arrAudittrail.size()==0)
						 arrAudittrail=null;	
				
			}catch(Exception e) {
				NeoBankEnvironment.setComment(1,className,"The exception in method searchAuditLogs  is  "+e.getMessage());
				throw new Exception ("The exception in method searchAuditLogs  is  "+e.getMessage());			
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
		
			return arrAudittrail;	
			}
	public ArrayList<PricingDetails> getPricingPlanDetails() throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<PricingDetails> arrPricingDetails = null;
		try{
			connection = super.getConnection();	
			
 			query = " select planid, usertype, paymode, planvalue, plandesc, varfee, fixedfee, slabapplicable, minimum_txn_amount, paytype,"
 					+ " isdefault, status from pricing_table order by planid desc ";
 			pstmt = connection.prepareStatement(query);
			//pstmt.setString(1, "P");
			rs = (ResultSet)pstmt.executeQuery();			
			 if(rs!=null){
				 arrPricingDetails = new ArrayList<PricingDetails>();
				 	while(rs.next()){	 
				 		PricingDetails m_PricingDetails=new PricingDetails();
				 		m_PricingDetails.setPlanId( StringUtils.trim(rs.getString("planid"))    );
				 		m_PricingDetails.setUsetType( StringUtils.trim(rs.getString("usertype"))    );
				 		m_PricingDetails.setPayMode( StringUtils.trim(rs.getString("paymode"))    );
				 		m_PricingDetails.setPlanValue( StringUtils.trim(rs.getString("planvalue"))    );
				 		m_PricingDetails.setPlanDesc( StringUtils.trim(rs.getString("plandesc"))    );
				 		m_PricingDetails.setVariableFee( StringUtils.trim(rs.getString("varfee"))    );
				 		m_PricingDetails.setFixedFee( StringUtils.trim(rs.getString("fixedfee"))    );
				 		m_PricingDetails.setSlabApplicable( StringUtils.trim(rs.getString("slabapplicable"))    );
				 		m_PricingDetails.setMinimumTxnAmount( StringUtils.trim(rs.getString("minimum_txn_amount"))    );
				 		m_PricingDetails.setPayType( StringUtils.trim(rs.getString("paytype"))    );
				 		m_PricingDetails.setStatus( StringUtils.trim(rs.getString("status"))    );	
				 		m_PricingDetails.setIsDefault( StringUtils.trim(rs.getString("isdefault"))    );
				 		arrPricingDetails.add(m_PricingDetails);
				 		} // end of while
				 	
				 	} //end of if rs!=null check
			 if(arrPricingDetails!=null)
				 if(arrPricingDetails.size()==0) 
					 arrPricingDetails=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getPricingPlanDetails  is  "+e.getMessage());
			throw new Exception ("The exception in method getPricingPlanDetails  is  "+e.getMessage());			
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
		return arrPricingDetails;
	}
	
	public boolean updatePricingTable(String planId, String planValue, String varFee, String minimumAmount,
			String isDefault, String planUserType, String planDesc, String fixedFee, String payType, String status, 
			String payMode, String slabApplicable) throws Exception {
			PreparedStatement pstmt=null;
			Connection connection = null;
			String query = null;
			boolean result = false;
			try{
				 connection = super.getConnection();
				 connection.setAutoCommit(false);
				 	
		 			query = " update pricing_table set usertype = ?, paymode = ?, planvalue = ?, plandesc = ?, varfee = ?, fixedfee = ?,"
		 					+ " slabapplicable = ?, minimum_txn_amount = ?, paytype = ?, isdefault = ?, status = ? where planid = ? ";

				       	pstmt = connection.prepareStatement(query);
						pstmt.setString(1, planUserType );
						pstmt.setString(2, payMode );
						pstmt.setString(3, planValue );
						pstmt.setString(4, planDesc );
						pstmt.setString(5, varFee );
						pstmt.setString(6, fixedFee );
						pstmt.setString(7, slabApplicable );
						pstmt.setString(8, minimumAmount );
						pstmt.setString(9, payType );
						pstmt.setString(10, isDefault );
						pstmt.setString(11, status );
						pstmt.setString(12, planId );
						
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
				NeoBankEnvironment.setComment(1,className,"The exception in method updatePricingTable  is  "+e.getMessage());
				throw new Exception ("The exception in method updatePricingTable  is  "+e.getMessage());
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
	public boolean addNewPricingPlan(String planValue, String varFee, String minimumAmount,
		String isDefault, String planUserType, String planDesc, String fixedFee, String payType, String status,
	    String payMode, String slabApplicable) throws Exception{
		
		NeoBankEnvironment.setComment(1,className,"payMode is  "+payMode);

		
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
	 			
                   //                                 1            2    3        4        5       6            7             8            9  
			   query = "insert into pricing_table (usertype, paymode, status, planvalue, plandesc, varfee, fixedfee, slabapplicable,minimum_txn_amount, "
			   		+ " paytype, isdefault  ) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			 	               //  1  2  3  4  5  6  7  8  9 10 11  
	 				
			       	pstmt = connection.prepareStatement(query);
					pstmt.setString(1, planUserType );
					pstmt.setString(2, payMode );
					pstmt.setString(3, status );
					pstmt.setString(4, planValue );
					pstmt.setString(5, planDesc );
					pstmt.setString(6, varFee );
					pstmt.setString(7, fixedFee );
					pstmt.setString(8, slabApplicable );
					pstmt.setString(9, minimumAmount );
					pstmt.setString(10, payType );
					pstmt.setString(11, isDefault );
					
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
			NeoBankEnvironment.setComment(1,className,"The exception in method addNewPricingPlan  is  "+e.getMessage());
			throw new Exception ("The exception in method addNewPricingPlan  is  "+e.getMessage());
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

	 public ArrayList<TransactionLimitDetails> getAllTxnLimitsDetails() throws Exception{
			PreparedStatement pstmt=null;
			Connection connection = null;
			ResultSet rs=null;
			String query = null;
			ArrayList<TransactionLimitDetails> arrTxnLimitDetails = null;
			try{
				connection = super.getConnection();	

				query = "select txn_limit_id,limit_type, limit_description, limit_amount, usertype, status, "
						+ " createdon from system_txn_limit order by createdon desc ";
	
				pstmt = connection.prepareStatement(query);
				rs = (ResultSet)pstmt.executeQuery();			
				 if(rs!=null){
					 arrTxnLimitDetails = new ArrayList<TransactionLimitDetails>();
					 	while(rs.next()){	 
					 		TransactionLimitDetails m_txtLimitDetails=new TransactionLimitDetails();
					 		m_txtLimitDetails.setTxnLimitId( StringUtils.trim(rs.getString("txn_limit_id"))    );
					 		m_txtLimitDetails.setLimitType( StringUtils.trim(rs.getString("limit_type"))    );
					 		m_txtLimitDetails.setLimitDescription( StringUtils.trim(rs.getString("limit_description"))    );
					 		m_txtLimitDetails.setLimitAmount( StringUtils.trim(rs.getString("limit_amount"))  );
					 		m_txtLimitDetails.setStatus(StringUtils.trim(rs.getString("status"))  );
					 		m_txtLimitDetails.setCreatedOn( StringUtils.trim(rs.getString("createdon"))  );
					 		m_txtLimitDetails.setUserType( StringUtils.trim(rs.getString("usertype"))  );
					 		
					 		arrTxnLimitDetails.add(m_txtLimitDetails);
					 		} // end of while
					 	
					 	} //end of if rs!=null check
				 if(arrTxnLimitDetails!=null) if(arrTxnLimitDetails.size()==0) arrTxnLimitDetails=null;
			}catch(Exception e){
				NeoBankEnvironment.setComment(1,className,"The exception in method getAllTxnLimiDetails  is  "+e.getMessage());
				throw new Exception ("The exception in method getAllTxnLimiDetails  is  "+e.getMessage());			
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
			return arrTxnLimitDetails;
		}
	public boolean updateOpsTransactionLimits(String txnLimitId,String limitType, String limitAmount, String limitDescription,
		String userType, String status) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			NeoBankEnvironment.setComment(3,className,"txnLimitId is  "+txnLimitId);

			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 	

	 			query = " update system_txn_limit set limit_type = ?, limit_description = ?, limit_amount = ?, "
	 					+ "usertype = ?, status = ?, createdon = ? where txn_limit_id = ? ";

			       	pstmt = connection.prepareStatement(query);
					pstmt.setString(1, limitType );
					pstmt.setString(2, limitDescription );
					pstmt.setString(3, limitAmount );
					pstmt.setString(4, userType );
					pstmt.setString(5, status );
					pstmt.setString(6, Utilities.getMYSQLCurrentTimeStampForInsert() );
					pstmt.setString(7, txnLimitId );
				
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
			NeoBankEnvironment.setComment(1,className,"The exception in method udateOpsTransactionLimits  is  "+e.getMessage());
			throw new Exception ("The exception in method udateOpsTransactionLimits  is  "+e.getMessage());
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
	
	public boolean addOpsTransactionLimits(String limitType,  String limitDescription, String limitAmount,
			String userType, String status) throws Exception {
			PreparedStatement pstmt=null;
			Connection connection = null;
			String query = null;
			boolean result = false;
			try{
				 connection = super.getConnection();
				 connection.setAutoCommit(false);
				 	
				 
		 		             //                                 1              2                     3         4          5         6        
					   query = "insert into system_txn_limit (limit_type, limit_description, limit_amount,  usertype, status, createdon  )"
								+ " values (?, ?, ?, ?, ?, ?) ";
					 	                //  1  2  3  4  5  6 

				       	pstmt = connection.prepareStatement(query);
						pstmt.setString(1, limitType );
						pstmt.setString(2, limitDescription );
						pstmt.setString(3, limitAmount );
						pstmt.setString(4, userType );
						pstmt.setString(5, status );
						pstmt.setString(6, Utilities.getMYSQLCurrentTimeStampForInsert() );
					
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
				NeoBankEnvironment.setComment(1,className,"The exception in method addOpsTransactionLimits  is  "+e.getMessage());
				throw new Exception ("The exception in method addOpsTransactionLimits  is  "+e.getMessage());
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
	
	public ArrayList<TransactionRules> getAllTransactionRules() throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<TransactionRules> arrTransactionRules = null;
		try{
			connection = super.getConnection();	

		    query =	"select paymode, rulesdesc, usertype, status, createdon from transaction_rules order by createdon desc";
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();			
			 if(rs!=null){
				 arrTransactionRules = new ArrayList<TransactionRules>();
				 	while(rs.next()){	 
				 		TransactionRules m_TransactionRules=new TransactionRules();
				 		m_TransactionRules.setPayMode( StringUtils.trim(rs.getString("paymode"))   ) ;
				 		m_TransactionRules.setRuleDesc(StringUtils.trim(rs.getString("rulesdesc")) );
				 		m_TransactionRules.setStatus( StringUtils.trim(rs.getString("status"))    );				 		
				 		m_TransactionRules.setCreatedOn( StringUtils.trim(rs.getString("createdon"))    );				 		
				 		m_TransactionRules.setUserType( StringUtils.trim(rs.getString("usertype"))    );				 		
				 		arrTransactionRules.add(m_TransactionRules);
				 		} // end of while
				 	
				 	} //end of if rs!=null check
			 				
			 if(arrTransactionRules!=null)
				 if(arrTransactionRules.size()==0) 
					 arrTransactionRules=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(3,className,"The exception in method getAllTransactionRules  is  "+e.getMessage());
			throw new Exception ("The exception in method getAllTransactionRules  is  "+e.getMessage());			
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
		return arrTransactionRules;
	}

	
	public boolean updateTransactionRules(String payMode, String ruleDesc, String loyaltyStatus, String lyltyUserType) throws Exception{
					PreparedStatement pstmt=null;
					Connection connection = null;
					String query = null;
					boolean result = false;
					try{
						 connection = super.getConnection();
						 connection.setAutoCommit(false);
						 	
				 			query = " update transaction_rules set rulesdesc  = ?, usertype = ?, status = ? where paymode = ?";

						       	pstmt = connection.prepareStatement(query);
								pstmt.setString(1, ruleDesc );
								pstmt.setString(2, lyltyUserType );
								pstmt.setString(3, loyaltyStatus );
								pstmt.setString(4, payMode );
								
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
						NeoBankEnvironment.setComment(1,className,"The exception in method updateTransactionRules  is  "+e.getMessage());
						throw new Exception ("The exception in method updateTransactionRules  is  "+e.getMessage());
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
	
	public boolean addNewTransactionRules(String payMode, String ruleDesc, String loyaltyStatus, String lyltyUserType) throws Exception{
			PreparedStatement pstmt=null;
			Connection connection = null;
			String query = null;
			boolean result = false;
			try{
				 connection = super.getConnection();
				 connection.setAutoCommit(false);
				 
	                   //                                 1        2          3        4          5                   
				   query = "insert into transaction_rules (paymode, rulesdesc, usertype, status, createdon ) "
							+ "values (?, ?, ?, ?, ?) ";
				 	               //  1  2  3  4  5   
		 				
				       	pstmt = connection.prepareStatement(query);
						pstmt.setString(1, payMode );
						pstmt.setString(2, ruleDesc );
						pstmt.setString(3, lyltyUserType );
						pstmt.setString(4, loyaltyStatus );
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
				NeoBankEnvironment.setComment(1,className,"The exception in method addNewTransactionRules  is  "+e.getMessage());
				throw new Exception ("The exception in method addNewTransactionRules  is  "+e.getMessage());
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
	
	public ArrayList<PricingSlabRates> getSlabRates(String planId) throws Exception{
		
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<PricingSlabRates> arrSlabRates = null;
		
		try{
			connection = super.getConnection();	
 			query = "  select sequenceid, planid, fromrange, torange, rate, status from pricing_slab_rate where planid=? order by fromrange asc  ";
 			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, planId);
			rs = (ResultSet)pstmt.executeQuery();			
			 if(rs!=null){
				 arrSlabRates = new ArrayList<PricingSlabRates>();
				 	while(rs.next()){	 
				 		PricingSlabRates m_PricingSlabRates=new PricingSlabRates();
				 		
				 		m_PricingSlabRates.setSequenceId( StringUtils.trim(rs.getString("sequenceid"))    );
				 		m_PricingSlabRates.setPlanId( StringUtils.trim(rs.getString("planid"))    );
				 		m_PricingSlabRates.setFromRange( StringUtils.trim(rs.getString("fromrange"))    );
				 		m_PricingSlabRates.setToRange( StringUtils.trim(rs.getString("torange"))    );
				 		m_PricingSlabRates.setRate( StringUtils.trim(rs.getString("rate"))    );
				 		m_PricingSlabRates.setStatus( StringUtils.trim(rs.getString("status"))    );
				 		arrSlabRates.add(m_PricingSlabRates);
				 		} // end of while
				 	
				 	} //end of if rs!=null check
			 if(arrSlabRates!=null)
				 if(arrSlabRates.size()==0) 
					 arrSlabRates=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getSlabRates  is  "+e.getMessage());
			throw new Exception ("The exception in method getSlabRates  is  "+e.getMessage());			
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
		return arrSlabRates;
	}
	
	public boolean editExistingSlab(String slabId, String planId, String fromRange, String toRange, String rate,
			String status) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
		 
			  //                                            1           2         3          4         5              6         
				 query = " update pricing_slab_rate set  planid=?, fromrange=?, torange=?, rate=?, status=? where sequenceid=? ";
				
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, planId); 						 
					pstmt.setString(2, fromRange);						 
					pstmt.setString(3, toRange);						 
					pstmt.setString(4, rate);					 
					pstmt.setString(5, status);	
					pstmt.setInt(6,  Integer.parseInt(slabId));	
				
					NeoBankEnvironment.setComment(3,className,"Integer.parseInt(slabId)  "+Integer.parseInt(slabId));

				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}

					connection.commit();
					result = true;
		}catch(Exception e){
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method editExistingSlab  is  "+e.getMessage());
			throw new Exception ("The exception in method editExistingSlab  is  "+e.getMessage());
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
	public boolean addNewSlabRate(String planId, String fromRange, 
			String toRange, String rate, String status) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			
			 									//		   1		 2			3	  4		  5			   
			 query = " insert into pricing_slab_rate 	(planid, fromrange, torange, rate,  status ) "
							+ "values ( ?,  ?,  ?,  ?,  ? ) ";
							//		   1   2   3   4   5   
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, planId); 					
					pstmt.setString(2, fromRange); 					
					pstmt.setString(3, toRange);					
					pstmt.setString(4, rate);					
					pstmt.setString(5, status);					
									
					try {
						pstmt.executeUpdate();
						}catch(Exception e) {
							throw new Exception (" failed query "+query+" "+e.getMessage());
						}
					connection.commit();
					result = true;
		}catch(Exception e){
			connection.rollback(); result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method addNewSlabRate  is  "+e.getMessage());
			throw new Exception ("The exception in method addNewSlabRate  is  "+e.getMessage());
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
	
	public String getChargesApplicable(String userType, String transactionMode, String amount) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String charges = null;
		PricingDetails m_PricingDetails = null;
		NeoBankEnvironment.setComment(3,className,"in getChargesApplicable userType : "+ userType + " transactionMode "+ transactionMode + " amount "+amount);

		try{
			connection = super.getConnection();	

 		query = "select planid, usertype, paymode,minimum_txn_amount, varfee, slabapplicable, paytype from pricing_table where paymode=? and usertype = ? and status  =?";
 			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, transactionMode);
			pstmt.setString(2, userType);
			pstmt.setString(3, "A");
			rs = (ResultSet)pstmt.executeQuery();			
			 if(rs!=null){
				 m_PricingDetails=new PricingDetails();
				 	while(rs.next()){	
				 		m_PricingDetails.setPlanId( StringUtils.trim(rs.getString("planid"))    );
				 		m_PricingDetails.setUsetType( StringUtils.trim(rs.getString("usertype"))    );
				 		m_PricingDetails.setPlanType( StringUtils.trim(rs.getString("paymode"))    );
				 		m_PricingDetails.setVariableFee( StringUtils.trim(rs.getString("varfee"))    );
				 		m_PricingDetails.setSlabApplicable( StringUtils.trim(rs.getString("slabapplicable"))    );
				 		m_PricingDetails.setPayType( StringUtils.trim(rs.getString("paytype"))    );
				 		m_PricingDetails.setMinimumTxnAmount( StringUtils.trim(rs.getString("minimum_txn_amount"))    );
				 		
				 		} // end of while
				 	
				 	} //end of if rs!=null check
			 if(pstmt.isClosed()==false) 	 pstmt.close(); if (rs!=null) rs.close();

			 if(m_PricingDetails.getPlanId() !=null) {
				if(  Double.parseDouble(m_PricingDetails.getVariableFee()) == 0 &&  m_PricingDetails.getSlabApplicable().equalsIgnoreCase("Y")) {
					
					query =  " select rate from pricing_slab_rate where ? between fromrange  and torange  and planid = ? ";
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, amount);
					pstmt.setString(2,  m_PricingDetails.getPlanId());
					rs = (ResultSet)pstmt.executeQuery();	
					if (rs!=null)
					{
						while(rs.next()){
							charges= StringUtils.trim(rs.getString("rate"));

						}
					}
					 if(pstmt.isClosed()==false) 	 pstmt.close(); if (rs!=null) rs.close();

				}else {
					charges =  Double.toString( Double.parseDouble(m_PricingDetails.getVariableFee()) * Double.parseDouble(amount) )   ;
				}
			 }
			 if(charges!=null) {
			
				 charges = m_PricingDetails.getPayType()+","+charges+"|"+m_PricingDetails.getMinimumTxnAmount();
			 }else {
				 charges = "D,0.0|0.00";
			 }
			 NeoBankEnvironment.setComment(3,className,"The inside charges  " + charges);
			 
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getChargesApplicable  is  "+e.getMessage());
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
		return charges;
	}

	public ArrayList<TransactionRules> getTransactionRules() throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<TransactionRules> arrTransactionRules = null;	
		try {
			connection = super.getConnection();	
			query = " select paymode, rulesdesc  from transaction_rules order by createdon desc  ";
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				arrTransactionRules =  new ArrayList<TransactionRules>();
				while(rs.next()){
					TransactionRules m_TransactionRules=new TransactionRules();
					m_TransactionRules.setPayMode(StringUtils.trim(rs.getString("paymode")));
					m_TransactionRules.setRuleDesc(StringUtils.trim(rs.getString("rulesdesc")));
					arrTransactionRules.add(m_TransactionRules);
				}
				if(arrTransactionRules!=null)
					 if(arrTransactionRules.size()==0) 
						 arrTransactionRules=null;
			}			 
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getTransactionRules  is  "+e.getMessage());
			throw new Exception ("The exception in method getTransactionRules  is  "+e.getMessage());			
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
		return arrTransactionRules;
	}

	public ArrayList<AssetCoin> getPorteAssetPricing() throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<AssetCoin> arrAssetPricing = null;	
		try {
			connection = super.getConnection();	
			query = " select a.assetcode assetcode, a.seq_no seq_no,  a.gecko_rate gecko_rate, "
					+ "	a.status status, a.createdon createdon,a.currency currency, b.asset_code asset_code, b.onramp_markup_rate onramp_markup_rate, "
					+ "	b.offramp_markup_rate offramp_markup_rate, b.status m_status from asset_pricing a, asset_pricing_markup_rate b "
					+ "	where b.status =? and a.assetcode=b.asset_code and  a.currency=b.currency order by seq_no desc limit 200";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "A");

			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				arrAssetPricing =  new ArrayList<AssetCoin>();
				while(rs.next()){
					AssetCoin m_AssetCoin=new AssetCoin();
					m_AssetCoin.setAssetCode(StringUtils.trim(rs.getString("assetcode")));
					m_AssetCoin.setSellingRate(StringUtils.trim(rs.getString("gecko_rate")));
					m_AssetCoin.setStatus(StringUtils.trim(rs.getString("status")));
					m_AssetCoin.setCreatedOn(StringUtils.trim(rs.getString("createdon")));
					m_AssetCoin.setSequenceNo(StringUtils.trim(rs.getString("seq_no")));
					m_AssetCoin.setOffMarkupRate(StringUtils.trim(rs.getString("offramp_markup_rate")));
					m_AssetCoin.setOnMarkupRate(StringUtils.trim(rs.getString("onramp_markup_rate")));
					m_AssetCoin.setCurrency(StringUtils.trim(rs.getString("currency")));
					arrAssetPricing.add(m_AssetCoin);
				}
				if(arrAssetPricing!=null)
					 if(arrAssetPricing.size()==0) 
						 arrAssetPricing=null;
			}			 
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getPorteAssetPricing  is  "+e.getMessage());
			throw new Exception ("The exception in method getPorteAssetPricing  is  "+e.getMessage());			
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
		return arrAssetPricing;
	}

	public boolean editPorteAssetPricing(String assetCode, String assetSellRate,
			String priceStatus, String sequencId, String fiatCurrency)throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
		 
			            //                                            1           2               3              4                
				// query = " update asset_distribution_pricing set  assetcode=?, selling_rate=?, status=? where seq_no=? ";
				 query = " update asset_pricing set status=? where seq_no=? ";
				
					pstmt = connection.prepareStatement(query);
					//pstmt.setString(1, assetCode); 						 
					//pstmt.setString(2, assetSellRate);						 
					pstmt.setString(1, priceStatus);					 
					pstmt.setInt(2,  Integer.parseInt(sequencId));	
				
				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}

					connection.commit();
					result = true;
		}catch(Exception e){
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method editPorteAssetPricing  is  "+e.getMessage());
			throw new Exception ("The exception in method editPorteAssetPricing  is  "+e.getMessage());
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
	public boolean addNewPorteAssetPricing(String assetCode, String assetSellRate,
			String priceStatus,String fiatCurrency)throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 
			//  any other active offer
			 query = " update asset_pricing set  status=? where assetcode=? and currency=? ";
				
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, "I"); 						 
				pstmt.setString(2, assetCode);	
				pstmt.setString(3, fiatCurrency);	
				try {
					pstmt.executeUpdate();
				    }catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
				    }
				pstmt.close();
		 
				                               //		 1		 2			    3       4	     5  		   
				query = " insert into asset_pricing (assetcode, gecko_rate, status, createdon,currency  ) "
						+ "values ( ?,  ?,  ?, ?, ?) ";
						  //		1   2   3  4  5
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, assetCode); 						 
					pstmt.setString(2, assetSellRate);						 
					pstmt.setString(3, "A");					 
					pstmt.setString(4, Utilities.getMYSQLCurrentTimeStampForInsert());					 
					pstmt.setString(5, fiatCurrency);					 

				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}
				pstmt.close();
							
					connection.commit();
					result = true;
		}catch(Exception e){
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method addNewPorteAssetPricing  is  "+e.getMessage());
			throw new Exception ("The exception in method addNewPorteAssetPricing  is  "+e.getMessage());
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

	
	//********************** Get BlockChain Data*****************************************************************//

	public JsonArray getBlockchainRecordInfoCustomerLedger() throws Exception{
		NeoBankEnvironment.setComment(3, className," Inside getBlockchainRecordInfoCustomerLedger ");

		CloseableHttpClient client =null;
		CloseableHttpResponse jresponse=null;
		 HttpPost jrequest = null;
		 JsonArray jsonarray =null;
		 HttpPost httpPost=null;
		 String streamName = null;
		 JsonObject responseJson=null; JsonObject responseJson2=null; 
		 String blockerror = null;
		
		try {
			 NeoBankEnvironment.setComment(3,className,"in getBlockchainRecordInfoCustomerLedger Blockchain   ");
         	 client = HttpClients.createDefault();
		     httpPost = new HttpPost(NeoBankEnvironment.getMultiChainWalletLedgerChainRPCURLPORT());
		     
				String chainName = NeoBankEnvironment.getWalletLedgerBlockChainName();
				streamName=NeoBankEnvironment.getBlockChainCustomerWalletStreamName();
				
				String json ="{\"method\":\"liststreams\",\"params\":[\""+streamName+"\"],\"id\":1,\"chain_name\":\""+chainName+"\"}";
	 		    StringEntity entity = new StringEntity(json);
	 		    httpPost.setEntity(entity);
	 		    httpPost.setHeader("Accept", "application/json");
	 		    httpPost.setHeader("authorization", Utilities.getBasicAuthHeader(NeoBankEnvironment.getWalletLedgerChainMultiChainUser(),NeoBankEnvironment.getWalletLedgerChainRPCAuthKey()));
	 		    httpPost.setHeader("Content-type", "application/json");
				
	 		   jresponse = client.execute(httpPost);
			    NeoBankEnvironment.setComment(3,className,"Response code "+jresponse.getCode());
			    HttpEntity entityResponse = jresponse.getEntity();
			    String data = EntityUtils.toString(entityResponse);
			    responseJson =  new Gson().fromJson(data, JsonObject.class);
		 		jsonarray = responseJson.getAsJsonArray("result");
		 		NeoBankEnvironment.setComment(3,className,"jsonarray success  "  + jsonarray);
	 		    if (jresponse.getCode()==200) {
	 		    	NeoBankEnvironment.setComment(3,className,"jsonarray success  "  + jsonarray);
	 		    } else {
	 		    	 
						  blockerror = responseJson.get("error").toString();
						  responseJson2 =  new Gson().fromJson(blockerror, JsonObject.class);
						  String code = responseJson2.get("code").toString();
						  String message = responseJson2.get("message").toString();
						
						  NeoBankEnvironment.setComment(1,className,"The exception in method insert getBlockchainRecordInfoCustomerLedger  is  Problem  in the Blockchain  code: "+  code + "  message : "+ message  );
	 		    }
				 			
		}catch (Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getBlockchainRecordInfoCustomerLedger  is  "+e.getMessage());
			throw new Exception( "Transactions does not exist");
			
		}finally {
			try {
				client.close();	jresponse.close(); if (jrequest!=null)jrequest=null; if (streamName!=null) streamName=null;
				if (responseJson!=null) responseJson=null; if (responseJson2!=null) responseJson2=null;
				if (httpPost!=null) httpPost.clear();
			} catch (Exception e1) {
				NeoBankEnvironment.setComment(1,className,"The exception in closing response  is  "+e1.getMessage());
			}
		}
		return jsonarray;
	}

	public static synchronized  JsonArray getTransactionsInBlockchain(String buttonValue, String userType, String countValue) throws Exception{

		CloseableHttpClient client =null;
		CloseableHttpResponse jresponse=null;
		 HttpPost jrequest = null;
		 JsonArray jsonarray =null;
		 HttpPost httpPost=null;
		 String streamName = null;
		 JsonObject responseJson=null; 
		try {
			 NeoBankEnvironment.setComment(3,className,"in insertGenericWalletTransactions Blockchain   ");
          	 client = HttpClients.createDefault();
 		     httpPost = new HttpPost(NeoBankEnvironment.getMultiChainWalletLedgerChainRPCURLPORT());
			
			
				NeoBankEnvironment.setComment(3,className,"in getTransactionsInBlockchain Blockchain   "+"Button value"+buttonValue);
				String chainName = NeoBankEnvironment.getWalletLedgerBlockChainName();
				if (userType.equals("C")) {
					streamName=NeoBankEnvironment.getBlockChainCustomerWalletStreamName();
				}
				
					int count=Integer.valueOf(countValue);
					int start=-Integer.valueOf(buttonValue);
					
		 		    String json ="{\"method\":\"liststreamitems\",\"params\":[\""+streamName+"\",false,"+count+","+start+",false],\"id\":1,\"chain_name\":\""+chainName+"\"}";
		 		    StringEntity entity = new StringEntity(json);
		 		    httpPost.setEntity(entity);
		 		    httpPost.setHeader("Accept", "application/json");
		 		    httpPost.setHeader("authorization", Utilities.getBasicAuthHeader(NeoBankEnvironment.getWalletLedgerChainMultiChainUser(),NeoBankEnvironment.getWalletLedgerChainRPCAuthKey()));
		 		    httpPost.setHeader("Content-type", "application/json");


				
				    jresponse = client.execute(httpPost);
				    NeoBankEnvironment.setComment(3,className,"Response code "+jresponse.getCode());
				    HttpEntity entityResponse = jresponse.getEntity();
				    String data = EntityUtils.toString(entityResponse);
				    responseJson =  new Gson().fromJson(data, JsonObject.class);
			 		jsonarray = responseJson.getAsJsonArray("result");
			 		NeoBankEnvironment.setComment(3,className,"Blockchain success  "  + jsonarray);
//		 		    if (jresponse.getCode()==200) {
//		 		    	NeoBankEnvironment.setComment(3,className,"Blockchain success  "  + jsonarray);
//		 		    } else {
//		 		    	 
//							  blockerror = responseJson.get("error").toString();
//							  responseJson2 =  new Gson().fromJson(blockerror, JsonObject.class);
//							  String code = responseJson2.get("code").toString();
//							  String message = responseJson2.get("message").toString();
//							
//							  NeoBankEnvironment.setComment(1,className,"The exception in method insert GenericWalletTransactions  is  Problem  in the Blockchain  code: "+  code + "  message : "+ message  );
//		 		    }
				    
//						HttpEntity entity = jresponse.getEntity();
//				 		JsonObject responseJson =  JsonParser.parseString(EntityUtils.toString(entity)).getAsJsonObject();
//				 		NeoBankEnvironment.setComment(3,className,"Response after Blockchain addition is "+responseJson.toString());
//				 		jsonarray = responseJson.getAsJsonArray("result");
				 			
		}catch (Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getTransactionsInBlockchain  is  "+e.getMessage());
			throw new Exception( "Transactions does not exist");
			
		}finally {
			try {
				client.close();	jresponse.close(); if (jrequest!=null)jrequest=null; if (streamName!=null) streamName=null;
				if (responseJson!=null) responseJson=null; if (httpPost!=null) httpPost.clear();
			} catch (Exception e1) {
				NeoBankEnvironment.setComment(1,className,"The exception in closing response  is  "+e1.getMessage());
			}
		}
		return jsonarray;
	}
	
	public boolean updateAssetPricesFromCoingecko(String [] assets, double [] assetUSDRates, 
			double [] assetHKDRates, double [] assetAUDRates)throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 NeoBankEnvironment.setComment(3,className,"assets:-  "+Arrays.toString(assets));
			 //Inactivate any other USD Price
			 query = " update asset_pricing set  status=? where assetcode=? and currency=? ";
			 pstmt = connection.prepareStatement(query);
			 for (int i=0;i<assets.length;i++) {
				 pstmt.setString(1, "I"); 						 
				 pstmt.setString(2, assets[i]);	
				 pstmt.setString(3,NeoBankEnvironment.getUSDCurrencyId());	
				 
				 pstmt.addBatch();
				 if ((i + 1) % 1000 == 0 || (i + 1) == assets.length) {
					 try {
						 pstmt.executeBatch();
					 }catch (Exception e) {
						 throw new Exception (" failed query "+query+" "+e.getMessage());
					 }
				 }
			 }
			 if(pstmt!=null) { pstmt.clearBatch();pstmt.close();}
			 //Inactivate any other HKD Price
			 query = " update asset_pricing set  status=? where assetcode=? and currency=? ";
			 pstmt = connection.prepareStatement(query);
			 for (int i=0;i<assets.length;i++) {
				 pstmt.setString(1, "I"); 						 
				 pstmt.setString(2, assets[i]);	
				 pstmt.setString(3,NeoBankEnvironment.getHKDCurrencyId());
				 pstmt.addBatch();
				 if ((i + 1) % 1000 == 0 || (i + 1) == assets.length) {
					 try {
						 pstmt.executeBatch();
					 }catch (Exception e) {
						 throw new Exception (" failed query "+query+" "+e.getMessage());
					 }
				 }
			 }
			 if(pstmt!=null) { pstmt.clearBatch();pstmt.close();}
			 //Inactivate any other AUD Price
			 query = " update asset_pricing set  status=? where assetcode=? and currency=?";
			 pstmt = connection.prepareStatement(query);
			 for (int i=0;i<assets.length;i++) {
				 pstmt.setString(1, "I"); 						 
				 pstmt.setString(2, assets[i]);	
				 pstmt.setString(3,NeoBankEnvironment.getAUDCurrencyId());
				 pstmt.addBatch();
				 if ((i + 1) % 1000 == 0 || (i + 1) == assets.length) {
					 try {
						 pstmt.executeBatch();
					 }catch (Exception e) {
						 throw new Exception (" failed query "+query+" "+e.getMessage());
					 }
				 }
			 }
			 if(pstmt!=null) { pstmt.clearBatch();pstmt.close();}
		 
			 	// Insert new USD price
				                                //		 1		  2			  3       4	      	 5   
				query = " insert into asset_pricing (assetcode, gecko_rate, status, createdon, currency) "
						+ "values ( ?,  ?,  ?, ?, ?) ";
						  //		1   2   3  4  5
				pstmt = connection.prepareStatement(query);
				for (int i=0;i<assets.length;i++) {
					pstmt.setString(1, assets[i]); 						 
					pstmt.setBigDecimal(2, BigDecimal.valueOf( assetUSDRates[i])); 
					pstmt.setString(3, "A");					 
					pstmt.setString(4, Utilities.getMYSQLCurrentTimeStampForInsert());	
					pstmt.setString(5, NeoBankEnvironment.getUSDCurrencyId());	
					 
					 pstmt.addBatch();
			            if ((i + 1) % 1000 == 0 || (i + 1) == assets.length) {
			            	try {
			                 pstmt.executeBatch();
			            	}catch (Exception e) {
			            		throw new Exception (" failed query "+query+" "+e.getMessage());
			            	}
			            }
				 }
				if(pstmt!=null) { pstmt.clearBatch();pstmt.close();}
				
				// Insert new AUD to USDC price
				
												//				 1		  2			  3       4	      	 5   
						query = " insert into asset_pricing (assetcode, gecko_rate, status, createdon, currency) "
								+ "values ( ?,  ?,  ?, ?, ?) ";
					            	//		1   2   3  4  5
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, assets[2]); // USDC Price 						 
					pstmt.setBigDecimal(2, BigDecimal.valueOf( assetAUDRates[2])); 
					pstmt.setString(3, "A");					 
					pstmt.setString(4, Utilities.getMYSQLCurrentTimeStampForInsert());	
					pstmt.setString(5, NeoBankEnvironment.getAUDCurrencyId());				 

				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}
				pstmt.close();
				 NeoBankEnvironment.setComment(3,className,"AUD to USDC Rate:-  "+assets[2]+" rate"+assetAUDRates[2]);
				// Insert new HKD to USDC price
				
				//				 1		  2			  3       4	      	 5   
				query = " insert into asset_pricing (assetcode, gecko_rate, status, createdon, currency) "
						+ "values ( ?,  ?,  ?, ?, ?) ";
				//		1   2   3  4  5
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, assets[2]); // USDC Price 						 
				pstmt.setBigDecimal(2, BigDecimal.valueOf( assetHKDRates[2])); 
				pstmt.setString(3, "A");					 
				pstmt.setString(4, Utilities.getMYSQLCurrentTimeStampForInsert());	
				pstmt.setString(5, NeoBankEnvironment.getHKDCurrencyId());				 
				
				try {
					pstmt.executeUpdate();
				}catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
				}
				pstmt.close();
				 NeoBankEnvironment.setComment(3,className,"HKD to USDC Rate:-  "+assets[2]+" rate"+assetAUDRates[2]);
							
				connection.commit();
				result = true;
		}catch(Exception e){
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method updateAssetPricesFromCoingecko  is  "+e.getMessage());
			throw new Exception ("The exception in method updateAssetPricesFromCoingecko  is  "+e.getMessage());
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
	public boolean tdaUpdateAssetPricesFromCoingecko(String [] assets, double [] assetRates)throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			connection = super.getConnection();
			connection.setAutoCommit(false);
			
			//Inactivate any other active offer
			query = " update asset_pricing set  status=? where assetcode=? and currency=?";
			pstmt = connection.prepareStatement(query);
			for (int i=0;i<assets.length;i++) {
				pstmt.setString(1, "I"); 						 
				pstmt.setString(2, assets[i]);	
				pstmt.setString(3,NeoBankEnvironment.getHKDCurrencyId());	
				
				pstmt.addBatch();
				if ((i + 1) % 1000 == 0 || (i + 1) == assets.length) {
					try {
						pstmt.executeBatch();
					}catch (Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}
				}
			}
			if(pstmt!=null) { pstmt.clearBatch();pstmt.close();}
			
			//		 1		  2			  3       4	      		   
			query = " insert into asset_pricing (assetcode, gecko_rate, status, createdon,currency  ) "
					+ "values ( ?,  ?,  ?, ?, ?) ";
			          //		1   2   3  4  5
			pstmt = connection.prepareStatement(query);
			for (int i=0;i<assets.length;i++) {
				pstmt.setString(1, assets[i]); 						 
				pstmt.setBigDecimal(2, BigDecimal.valueOf( assetRates[i])); 
				pstmt.setString(3, "A");					 
				pstmt.setString(4, Utilities.getMYSQLCurrentTimeStampForInsert());	
				pstmt.setString(5,NeoBankEnvironment.getHKDCurrencyId());
				pstmt.addBatch();
				if ((i + 1) % 1000 == 0 || (i + 1) == assets.length) {
					try {
						pstmt.executeBatch();
					}catch (Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}
				}
			}
			if(pstmt!=null) { pstmt.clearBatch();pstmt.close();}
			
			connection.commit();
			result = true;
		}catch(Exception e){
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method tdaUpdateAssetPricesFromCoingecko  is  "+e.getMessage());
			throw new Exception ("The exception in method tdaUpdateAssetPricesFromCoingecko  is  "+e.getMessage());
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
	
	public String getAssetPrice(String assetCode, String fiatCurrency) throws Exception{
		NeoBankEnvironment.setComment(3,className,"assetCode  "+assetCode+" fiatCurrency "+fiatCurrency );
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String assetPrice = null;	
		try {
			connection = super.getConnection();	
			
			query = " select gecko_rate from asset_pricing where assetcode = ? and status = ? and currency=? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, assetCode);
			pstmt.setString(2, "A");
			pstmt.setString(3, fiatCurrency);

			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				while(rs.next()){
					assetPrice = StringUtils.trim(rs.getString("gecko_rate"));
				}
				
			}			 
			
		}catch(Exception e){
			assetPrice = null;
			NeoBankEnvironment.setComment(1,className,"The exception in method getAssetPrice  is  "+e.getMessage());
			throw new Exception ("The exception in method getAssetPrice  is  "+e.getMessage());			
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
		return assetPrice;
	}
	
	public boolean addAssetPriceMarkupRate( String assetCode, String onRampRate, String offRampRate, String status, String fiatCurrency)throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 
			 /**/
			 
			//Inactivate any other active offer
			 query = " update asset_pricing_markup_rate set  status=? where asset_code=? and currency=?";
				
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, "I"); 						 
				pstmt.setString(2, assetCode);	
				pstmt.setString(3, fiatCurrency);	
				try {
					pstmt.executeUpdate();
				    }catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
				    }
				pstmt.close();
		 
				                                          //		 1	        	  2			    3                   4	      5		   6
				query = " insert into asset_pricing_markup_rate (asset_code, onramp_markup_rate, offramp_markup_rate, status, createdon, currency ) "
						+ "values ( ?,  ?,  ?, ?, ?, ?) ";
						  //		1   2   3  4  5  6
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, assetCode); 						 
					pstmt.setBigDecimal(2, new BigDecimal(onRampRate)); 						 
					pstmt.setBigDecimal(3, new BigDecimal(offRampRate)); 						 
					pstmt.setString(4, "A");					 
					pstmt.setString(5, Utilities.getMYSQLCurrentTimeStampForInsert());					 
					pstmt.setString(6, fiatCurrency);					 

				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}
				pstmt.close();
							
					connection.commit();
					result = true;
		}catch(Exception e){
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method addAssetPriceMarkupRate  is  "+e.getMessage());
			throw new Exception ("The exception in method addAssetPriceMarkupRate  is  "+e.getMessage());
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
