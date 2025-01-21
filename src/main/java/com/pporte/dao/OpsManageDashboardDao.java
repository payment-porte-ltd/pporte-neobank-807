package com.pporte.dao;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.pporte.NeoBankEnvironment;
import com.pporte.model.BlockchainDetails;
import com.pporte.model.OpsDashboard;
import com.pporte.utilities.Utilities;

public class OpsManageDashboardDao extends HandleConnections {
	private static String className = OpsManageDashboardDao.class.getSimpleName();

	public OpsDashboard getTotalUsers() throws Exception {
		OpsDashboard mOpsDashboard = null;
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		try {
			connection = super.getConnection();
			query="select (select count(relationshipno) from customer_details) as totalcustomers";
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				while(rs.next()){
					mOpsDashboard= new OpsDashboard();
					mOpsDashboard.setTotalUsers(StringUtils.trim(rs.getString("totalcustomers")));
				}
			}
		} catch(Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getTotalUsers  is  "+e.getMessage());
			//throw new Exception ("The exception in method getTotalUsers  is  "+e.getMessage());
		} finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(rs!=null) rs.close();
			if(pstmt!=null) pstmt.close();
		}
		return mOpsDashboard;
	}

	public OpsDashboard getFiatBalanceDetails() throws Exception {
		OpsDashboard mOpsDashboard = null;
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String walletType="F";
		String fiatWalletBalance=null;
		try {
			connection = super.getConnection();
			query="select (select sum(currbal) from wallet_details where wallettype	= ?	) as fiatwalletbalance";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, walletType);
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				while(rs.next()){
					mOpsDashboard= new OpsDashboard();
					fiatWalletBalance=StringUtils.trim(rs.getString("fiatwalletbalance"));
					if (fiatWalletBalance!=null) {
						mOpsDashboard.setFiatWalBal(Utilities.getMoneyinDecimalFormat(fiatWalletBalance));
					}else {
						mOpsDashboard.setFiatWalBal(fiatWalletBalance);
					}					
				}
			}		
		} catch(Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getFiatBalanceDetails  is  "+e.getMessage());
			//throw new Exception ("The exception in method getFiatBalanceDetails  is  "+e.getMessage());
		} finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(rs!=null) rs.close();
			if(pstmt!=null) pstmt.close(); if(fiatWalletBalance!=null)fiatWalletBalance=null;
		}
		return mOpsDashboard;
	}

	public OpsDashboard getTotalDisputes() throws Exception{
		OpsDashboard mOpsDashboard = null;
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String status="A";
		try {
			connection = super.getConnection();
			query="select (select count(disputeid) from dispute_details where status =?) as totaldisputes";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, status);
			rs = (ResultSet)pstmt.executeQuery();
			if(rs!=null){
				while(rs.next()){
					mOpsDashboard= new OpsDashboard();
					mOpsDashboard.setTotalDisputes(StringUtils.trim(rs.getString("totaldisputes")));
				}
			}
		} catch(Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getTotalDisputes  is  "+e.getMessage());
			//throw new Exception ("The exception in method getTotalDisputes  is  "+e.getMessage());
		} finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(rs!=null) rs.close();
			if(pstmt!=null) pstmt.close();
		}
		return mOpsDashboard;
	}
	
	public ArrayList<String> getBussinessLedgerTxnBtwnDates() throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<String> arrDateAndAmount = null; 
		LocalDate now = LocalDate.now(); // 2015-11-23
  		LocalDate firstDayOfTheYear = now.with(firstDayOfYear()); // 2021-01-01
  		LocalDate lastDayOfTheYear = now.with(lastDayOfYear()); // 2020-12-31
  		
		try{
			connection = super.getConnection();	
		
				query = "select  sum(accrued_balance) accrued_balance,   date_format(txndatetime,\"%M\") txndatetime from txn_business_ledger_bc "
						+ "	where txndatetime between ?  and ? group by date_format(txndatetime, \"%M\") order by txndatetime  desc ";
			
			pstmt = connection.prepareStatement(query);			
			pstmt.setString(1, firstDayOfTheYear.toString());
			pstmt.setString(2, lastDayOfTheYear.toString());
			rs = (ResultSet)pstmt.executeQuery();

			 if(rs!=null){
				 arrDateAndAmount = new ArrayList<String>();
				 	while(rs.next()){
				 		arrDateAndAmount.add(StringUtils.trim(rs.getString("txndatetime"))+","+ StringUtils.trim(rs.getString("accrued_balance")));
				 		}
				 	} //end of if rs!=null check
			 NeoBankEnvironment.setComment(3,className,"arrDateAndAmount  is  "+arrDateAndAmount.size());
			 if(arrDateAndAmount!=null) 
				 if(arrDateAndAmount.size()==0)
					 arrDateAndAmount=null;
		}catch(Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getBussinessLedgerTxnBtwnDates  is  "+e.getMessage());
			throw new Exception ("The exception in method getBussinessLedgerTxnBtwnDates  is  "+e.getMessage());		
			
		}finally {
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();

		}
		return arrDateAndAmount;

	}

	public ArrayList<BlockchainDetails> getBlockchainDataTxn() {
		// TODO Auto-generated method stub
		return null;
	}

}
