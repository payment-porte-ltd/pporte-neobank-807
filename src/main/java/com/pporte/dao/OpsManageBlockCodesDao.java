package com.pporte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;
import com.pporte.NeoBankEnvironment;
import com.pporte.model.BlockCodes;
import com.pporte.utilities.Utilities;

public class OpsManageBlockCodesDao  extends HandleConnections{
	public static String className = OpsManageBlockCodesDao.class.getName();

	public ArrayList<BlockCodes> getAllBlockCodes() throws Exception{
		PreparedStatement psmt =null;
		Connection connection = null;
		ResultSet rs=null;
		String query=null;
		
		ArrayList<BlockCodes>arrBlockCodes =null;
		try {
			
			connection = super.getConnection();
			query="select blockcode_id,blockcode_desc,status,authaction,createdon from wallet_block_codes order by createdon desc";
			psmt=connection.prepareStatement(query);
			rs=(ResultSet)psmt.executeQuery();
			if(rs!=null) {
				arrBlockCodes= new ArrayList<BlockCodes>();
				while(rs.next()) {
					BlockCodes all_BlockCodes = new BlockCodes();
					all_BlockCodes.setBlockCodeId(StringUtils.trim(rs.getString("blockcode_id")));
					all_BlockCodes.setBlockCodeDesc((StringUtils.trim(rs.getString("blockcode_desc"))));
					all_BlockCodes.setStatus((StringUtils.trim(rs.getString("status"))));
					all_BlockCodes.setAuthAction((StringUtils.trim(rs.getString("authaction"))));
					all_BlockCodes.setCreatedOn(Utilities.getMySQLDateTimeConvertor(StringUtils.trim(rs.getString("createdon"))));
					arrBlockCodes.add(all_BlockCodes);
				}
			}
		}
		catch(Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getAllBlockCodes()   is  "+e.getMessage());
			throw new Exception ("The exception in method getAllBlockCodes()   is  "+e.getMessage());	
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
		return arrBlockCodes;
	}

	public boolean AddBlockCodes(String blockCodeDesc, String blockStatus, String blockAuthentication)throws Exception {
		PreparedStatement pstmt=null;
		String query=null;
		Connection connection=null;
		boolean result=false;
		try {
			connection = super.getConnection();
			connection.setAutoCommit(false);
			

					
				//										1			2		3			4
			query ="insert into wallet_block_codes (blockcode_desc,status,authaction,createdon)"
					+ "values(?,?,?,?)";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, blockCodeDesc );
			pstmt.setString(2, blockStatus );
			pstmt.setString(3, blockAuthentication );
			pstmt.setString(4, Utilities.getMYSQLCurrentTimeStampForInsert() );
			
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
			NeoBankEnvironment.setComment(1,className,"The exception in method AddBlockCodes  is  "+e.getMessage());
			throw new Exception ("The exception in method AddBlockCodes  is  "+e.getMessage());
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
	public boolean updateBlockCodes(String editBlockId, String editBlockCodeDesc, String createdOn,
			String authenticate, String status) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try {
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 		//										1				2					3			4				
				query = "update wallet_block_codes set  blockcode_desc = ?, status = ? ,authaction = ?  where blockcode_id = ? ";
			 	pstmt = connection.prepareStatement(query);
				pstmt.setString(1, editBlockCodeDesc );
				pstmt.setString(2, status );
				pstmt.setString(3, authenticate );
				pstmt.setString(4, editBlockId );
				try {
					pstmt.executeUpdate();					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}
				pstmt.close();
				connection.commit();			 	
				result = true;
		
		}catch(Exception e){
			connection.rollback();
			result = false;
			NeoBankEnvironment.setComment(1,className,"The exception in method updateBlockCodes  is  "+e.getMessage());
			throw new Exception ("The exception in method updateBlockCodes  is  "+e.getMessage());
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
