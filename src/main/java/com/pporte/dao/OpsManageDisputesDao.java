package com.pporte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import com.pporte.NeoBankEnvironment;
import com.pporte.model.DisputeReasons;
import com.pporte.model.DisputeTracker;
import com.pporte.model.Disputes;
import com.pporte.utilities.Utilities;

public class OpsManageDisputesDao extends HandleConnections {
	private static String className = OpsManageCustomerDao.class.getSimpleName();

	public ArrayList<Disputes> getAllDisputes() throws Exception {
		
		PreparedStatement psmt =null;
		Connection connection = null;
		ResultSet rs=null;
		String query=null;
		ArrayList<Disputes> arrDisputes=null;
		try {
			connection = super.getConnection();
			query="select reasonid, reasondesc, usertype, paymode, status, createdon from dispute_reason order by createdon desc";
			psmt=connection.prepareStatement(query);
			rs=(ResultSet)psmt.executeQuery();
			
			if(rs!=null) {
				arrDisputes= new ArrayList<Disputes>();
				while(rs.next()) {
					Disputes all_Disputes = new Disputes();
					all_Disputes.setReasonId(StringUtils.trim(rs.getString("reasonid")));
					all_Disputes.setReasonDesc(StringUtils.trim(rs.getString("reasondesc")));
					all_Disputes.setUserType(StringUtils.trim(rs.getString("usertype")));
					all_Disputes.setPayMode(StringUtils.trim(rs.getString("paymode")));
					all_Disputes.setStatus(StringUtils.trim(rs.getString("status")));
					all_Disputes.setCreatedon(StringUtils.trim(rs.getString("createdon")));
					arrDisputes.add(all_Disputes);
				}
			}
			if(arrDisputes!=null)
				 if(arrDisputes.size()==0)
					 arrDisputes=null;
		}catch(Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllDisputes()   is  "+e.getMessage());
			throw new Exception ("The exception in method getAllDisputes()   is  "+e.getMessage());	
		}finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(psmt!=null) psmt.close();
			}
		return arrDisputes;
	}
	
	public List<Disputes> getAllRaisedDisputes() throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		List<Disputes> aryDisputes = null;
		try{
			 connection = super.getConnection();	
			  query = " select a.disputeid disputeid, a.transactioncode transactioncode, a.reasonid reasonid, "
			  		 + " a.raisedbyentityno raisedbyentityno, a.usertype usertype, a.status status, "
			  		 + " a.raisedondate raisedondate, a.usercomment, b.reasondesc reasondesc from dispute_details a, "
			  		 + " dispute_reason b where a.reasonid=b.reasonid order by a.disputeid desc ";
			  
			 pstmt = connection.prepareStatement(query);
			 rs = pstmt.executeQuery();
			  if(rs!=null){	
				  aryDisputes = new ArrayList<Disputes>();
				 	while(rs.next()){	
				 		Disputes m_Disputes = new Disputes();
				 		m_Disputes.setDisputeId( StringUtils.trim(rs.getString("disputeid"))   );
				 		m_Disputes.setTransactionId(StringUtils.trim(rs.getString("transactioncode"))   );
				 		m_Disputes.setReasonId(StringUtils.trim(rs.getString("reasonid"))   );
				 		m_Disputes.setRaisedbyUserId(StringUtils.trim(rs.getString("raisedbyentityno"))   );
				 		String userType = StringUtils.trim(rs.getString("usertype"));
				 		
				 		if(userType.equalsIgnoreCase("M")) {
				 			m_Disputes.setUserType("Merchant");
				 		}if (userType.equalsIgnoreCase("C")) {
							m_Disputes.setUserType("Customer");
				 		}if (userType.equalsIgnoreCase("O")) {
				 			m_Disputes.setUserType("Operator");
				 		}
				 		String status = StringUtils.trim(rs.getString("status"));
				 		if(status.equalsIgnoreCase("A")) {
				 			m_Disputes.setStatus("Active");
				 		}else if (status.equalsIgnoreCase("C")) {
							m_Disputes.setStatus("Closed");
						}else if(status.equalsIgnoreCase("P")) {
							m_Disputes.setStatus("In Progress");
						}
				 		
				 		m_Disputes.setRaisedOn(StringUtils.trim(rs.getString("raisedondate"))   );
				 		m_Disputes.setReasonDesc(StringUtils.trim(rs.getString("reasondesc")));
				 		m_Disputes.setUserComment(StringUtils.trim(rs.getString("usercomment")));
				 		aryDisputes.add(m_Disputes);
			 		} // end of while						 	
			 	} //end of if 
			  	if(aryDisputes!=null)
			  		if(aryDisputes.size()==0)
			  			aryDisputes=null;			  
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllDisputes  is  "+e.getMessage());
		}finally{
		if(connection!=null)
			try {
				super.close();
			} catch (SQLException e) {
				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
			if(pstmt!=null) pstmt.close(); if(rs!=null) rs.close();
		}
		return aryDisputes;
	}
	
	public boolean addNewDispute(String transactionId, String operationId, String userType, String comment,
			String reasonId, String status, String referenceNo) throws Exception{
			PreparedStatement pstmt=null;
			Connection connection = null;
			String query = null;
			boolean result = false;
			try{
				 connection = super.getConnection();
				 connection.setAutoCommit(false); 
				 
				 // query                                 1               2          3            4          5              6        7        8
				query = "insert into dispute_details (transactioncode, reasonid, raisedbyentityno, usertype, referenceno, usercomment, status, raisedondate ) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?) ";
						//		   1  2  3  4  5  6  7 8
				pstmt = connection.prepareStatement(query);
				if(transactionId.length()==0)
					pstmt.setInt(1, 0);
				else
				pstmt.setString(1, transactionId);
				pstmt.setString(2, reasonId);
				pstmt.setString(3, operationId);
				pstmt.setString(4, userType);
				pstmt.setString(5, referenceNo);
				pstmt.setString(6, comment);
				pstmt.setString(7, status);
				pstmt.setString(8, Utilities.getMYSQLCurrentTimeStampForInsert());
				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
				}				connection.commit();			 	
				result = true;
					
			}catch(Exception e){
				connection.rollback();
				result = false;
				NeoBankEnvironment.setComment(1,className,"The exception in method addNewDispute  is  "+e.getMessage());
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

	public boolean AddNewDisputeReasons( String disputeReasonDesc, String userType, String payMode,
			String disputeStatus) throws Exception {
		NeoBankEnvironment.setComment(3,className,"AddNewDisputeReasons User Type is "+userType);
		PreparedStatement pstmt=null;
		String query=null;
		Connection connection=null;
		boolean result=false;
		try {
			connection = super.getConnection();
			connection.setAutoCommit(false);
			
							//						1			2		3		4		5		6
			query ="insert into dispute_reason (reasondesc,usertype,paymode,status,createdon)"
					+ "values(?,?,?,?,?)";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, disputeReasonDesc );
			pstmt.setString(2, userType );
			pstmt.setString(3, payMode );
			pstmt.setString(4, disputeStatus );
			pstmt.setString(5, Utilities.getMYSQLCurrentTimeStampForInsert() );
			
			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}pstmt.close();
			connection.commit();			 	
			result = true;
			}catch(Exception e) {
				connection.rollback();
				result = false;
				NeoBankEnvironment.setComment(1,className,"The exception in method AddNewDisputeReasons  is  "+e.getMessage());
				throw new Exception ("The exception in method AddNewDisputeReasons  is  "+e.getMessage());
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

	public boolean UpdateDisputeReasons(String disputeReasonId, String disputeReasonDesc, String userType, String payMode,
			String status) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 	 
			 //											1				2			3			4					5
				query = "update dispute_reason set reasondesc = ?, usertype = ?, paymode = ? ,status = ?  where reasonid = ? ";
		
			       	pstmt = connection.prepareStatement(query);
					pstmt.setString(1, disputeReasonDesc );
					pstmt.setString(2, userType );
					pstmt.setString(3, payMode );
					pstmt.setString(4, status );
					pstmt.setString(5, disputeReasonId );
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
			NeoBankEnvironment.setComment(1,className,"The exception in method UpdateDisputeReasons  is  "+e.getMessage());
			throw new Exception ("The exception in method UpdateDisputeReasons  is  "+e.getMessage());
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

	public ArrayList<DisputeTracker> getAllCustomerDisputes(String userType) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<DisputeTracker> arrayDisputes = null;
		try{
			connection = super.getConnection();	
			
			query = " select a.disputeid, a.transactioncode, a.reasonid, a.raisedbyentityno, a.usertype, a.status, a.raisedondate, a.referenceno, "
					+ " a.usercomment, b.reasondesc, b.reasonid from dispute_details a, dispute_reason b where a.reasonid = b.reasonid and  "
					+ " a.userType= ? order by a.raisedondate desc ";

			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, userType); 
			rs = (ResultSet)pstmt.executeQuery();			
			 if(rs!=null){
				 arrayDisputes = new ArrayList<DisputeTracker>();
				 	while(rs.next()){	 
				 		DisputeTracker m_DispReason=new DisputeTracker();
				 		
				 		m_DispReason.setDisputeId( StringUtils.trim(rs.getString("disputeid"))    );
				 		m_DispReason.setTransactionId( StringUtils.trim(rs.getString("transactioncode"))    );
				 		m_DispReason.setReasonId( StringUtils.trim(rs.getString("reasonid"))  );
				 		m_DispReason.setRaisedbyUserId( StringUtils.trim(rs.getString("raisedbyentityno"))  );
				 		m_DispReason.setUserType(StringUtils.trim(rs.getString("usertype"))    );
				 		m_DispReason.setStatus( StringUtils.trim(rs.getString("status"))    );	
				 		m_DispReason.setRaisedOn(StringUtils.trim(rs.getString("raisedondate"))    );
				 		m_DispReason.setRefNo(StringUtils.trim(rs.getString("referenceno")) );
				 		m_DispReason.setUserComment(StringUtils.trim(rs.getString("usercomment")) );
				 		m_DispReason.setReasonDesc(StringUtils.trim(rs.getString("reasondesc")) );				 		
				 		
				 		arrayDisputes.add(m_DispReason);
				 		} // end of while
				 	
				 	} //end of if rs!=null check
			 if(arrayDisputes!=null) if(arrayDisputes.size()==0) arrayDisputes=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllCustomerDisputes is  "+e.getMessage());
			throw new Exception ("The exception in method getAllCustomerDisputes is  "+e.getMessage());			
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
		return arrayDisputes;
	}
	
	public Disputes getDisputeDetail(String disputeId) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		Disputes m_Disputes = null;
		try{
			 connection = super.getConnection();	
			 
			 query = " select a.disputeid disputeid, a.transactioncode transactioncode, a.reasonid reasonid,"
			 		+ "	a.raisedbyentityno raisedbyentityno, a.usertype usertype, a.status status, "
			 		+ "	a.raisedondate raisedondate, a.usercomment usercomment, a.referenceno, b.reasondesc reasondesc from dispute_details a, "
			 		+ "	dispute_reason b where a.disputeid=? and a.reasonid=b.reasonid  ";
			 pstmt = connection.prepareStatement(query);
			 pstmt.setString(1, disputeId);
			 rs = pstmt.executeQuery();
				  if(rs!=null){	
					 	while(rs.next()){	
					 		m_Disputes = new Disputes();
					 		m_Disputes.setDisputeId( StringUtils.trim(rs.getString("disputeid"))   );
					 		m_Disputes.setTransactionId(StringUtils.trim(rs.getString("transactioncode"))   );
					 		m_Disputes.setReasonId(StringUtils.trim(rs.getString("reasonid"))   );
					 		m_Disputes.setRaisedbyUserId(StringUtils.trim(rs.getString("raisedbyentityno"))   );
					 		String userType = StringUtils.trim(rs.getString("usertype"));
					 		if(userType.equalsIgnoreCase("M")) {
					 			m_Disputes.setUserType("Merchant");
					 		}else if(userType.equalsIgnoreCase("C")) {
					 			m_Disputes.setUserType("Customer");
					 		}
					 		m_Disputes.setStatus(StringUtils.trim(rs.getString("status"))   );
					 		m_Disputes.setUserComment(StringUtils.trim(rs.getString("usercomment"))   );
					 		m_Disputes.setRaisedOn(StringUtils.trim(rs.getString("raisedondate")));
					 		m_Disputes.setReasonDesc(StringUtils.trim(rs.getString("reasondesc")));
					 		m_Disputes.setReferenceNo(StringUtils.trim(rs.getString("referenceno")));
				 		} 						 	
				 	} 			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getDisputeDetail  is  "+e.getMessage());
		}finally{
		if(connection!=null)
			try {
				super.close();
			} catch (SQLException e) {
				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
			if(pstmt!=null) pstmt.close();if(rs!=null) rs.close();
		}
		return m_Disputes;

	}
	
	public List<DisputeTracker> getAllDisputeTrackers(String disputeId) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		List<DisputeTracker> aryDisputeTracker = null;
		try{
			 connection = super.getConnection();	
			  query = "select trackid, disputeid, updaterentityno, updatertype, updatercomment, lastupdate "
			  		+ " from dispute_tracking where disputeid=?	";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(disputeId));
			rs = pstmt.executeQuery();
			if(rs!=null){	
				 aryDisputeTracker = new ArrayList<DisputeTracker>();
				 while(rs.next()){	
				 	DisputeTracker m_DisputeTracker = new DisputeTracker();
				 	m_DisputeTracker.setTrackingId( StringUtils.trim(rs.getString("trackid")));
				 	m_DisputeTracker.setDisputeId(StringUtils.trim(rs.getString("disputeid")));
				 	m_DisputeTracker.setUpdaterId(StringUtils.trim(rs.getString("updaterentityno")));
				 	String updaterType = StringUtils.trim(rs.getString("updatertype"));
				 	if(StringUtils.equalsIgnoreCase(updaterType, "M")) {
				 		m_DisputeTracker.setUpdaterType("Merchant");
				 	}else if(StringUtils.equalsIgnoreCase(updaterType, "C")){
						m_DisputeTracker.setUpdaterType("Customer");
					}
				 	m_DisputeTracker.setUpdaterComment(StringUtils.trim(rs.getString("updatercomment"))   );
				 	m_DisputeTracker.setLastUpdated(StringUtils.trim(rs.getString("lastupdate"))   );
				 	aryDisputeTracker.add(m_DisputeTracker);
			 		} // end of while						 	
			 	} //end of if 
			  if(aryDisputeTracker!=null)
				  if(aryDisputeTracker.size()==0)
					  aryDisputeTracker=null;			  
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllDisputeTrackers  is  "+e.getMessage());
		}finally{
		if(connection!=null)
			try {
				super.close();
			} catch (SQLException e) {
				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
			if(pstmt!=null) pstmt.close(); if(rs!=null) rs.close();
		}
		return aryDisputeTracker;
	}

		public List<DisputeReasons> getAllDisputeReasons() throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		List<DisputeReasons> aryReasons = null;
		try{
			 connection = super.getConnection();	
			 query = "select reasonid, reasondesc, status from dispute_reason  order by reasonid desc";
			 pstmt = connection.prepareStatement(query);
			 rs = pstmt.executeQuery();
			  if(rs!=null){	
				  aryReasons = new ArrayList<DisputeReasons>();
				 	while(rs.next()){	
				 		DisputeReasons m_DisputeReasons = new DisputeReasons();
				 		m_DisputeReasons.setDisputeReasonId( StringUtils.trim(rs.getString("reasonid"))   ); 
				 		m_DisputeReasons.setDisputeReasonDesc(StringUtils.trim(rs.getString("reasondesc"))   );
				 		m_DisputeReasons.setStatus(StringUtils.trim(rs.getString("status"))   );
				 		aryReasons.add(m_DisputeReasons);	
			 		} // end of while						 	
			 	} //end of if
			  if(aryReasons!=null)
				  if(aryReasons.size()==0)
					  aryReasons=null;			  
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllDisputeReasons  is  "+e.getMessage());
		}finally{
		if(connection!=null)
			try {
				super.close();
			} catch (SQLException e) {
				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
			if(pstmt!=null) pstmt.close(); if(rs!=null) rs.close();
		}

		return aryReasons;	
		
	}


			public boolean addCommentOnADispute(String disputeId, String operationId, String userType, String comment) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 query = " insert into dispute_tracking (disputeid, updaterentityno, updatertype, updatercomment, lastupdate ) "
							+ " values (?, ?, ?, ?, ?) ";
							 //		   1  2  3  4  5
					pstmt = connection.prepareStatement(query);
					pstmt.setInt(1, Integer.parseInt(disputeId));
					pstmt.setString(2, operationId);
					pstmt.setString(3, userType);
					
					pstmt.setString(4, comment);
					pstmt.setString(5, Utilities.getMYSQLCurrentTimeStampForInsert());
					try {
					pstmt.executeUpdate();
					}catch (Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());

					}
					connection.commit();			 	
					result = true;
		}catch(Exception e){
			connection.rollback();
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method addCommentOnADispute  is  "+e.getMessage());
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


			public boolean updateDisputeStatus(String disputeid, String status) throws Exception{
				PreparedStatement pstmt=null;
				Connection connection = null;
				String query = null;
				boolean result = false;
				try{
					 connection = super.getConnection();
					 connection.setAutoCommit(false);
					 query = "update dispute_details set status=? where disputeid=? ";
									//		               1               2   
					 pstmt = connection.prepareStatement(query);
					 pstmt.setString(1, status );
					 pstmt.setInt(2, Integer.parseInt(disputeid));
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
					NeoBankEnvironment.setComment(1,className,"The exception in method updateDisputeStatus  is  "+e.getMessage());
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
