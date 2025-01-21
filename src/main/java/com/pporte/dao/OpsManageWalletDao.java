package com.pporte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.pporte.NeoBankEnvironment;
import com.pporte.model.CryptoAssetCoins;
import com.pporte.model.Customer;
import com.pporte.model.AssetTransaction;
import com.pporte.utilities.Utilities;

public class OpsManageWalletDao extends HandleConnections {
	private static String className = OpsManageWalletDao.class.getSimpleName();
	
	public List<CryptoAssetCoins> getWalletAssets() throws Exception{       
		
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		CryptoAssetCoins walletAsset = null;
		List<CryptoAssetCoins> walletAssetsList = null;
		try{
			connection = super.getConnection();	
			query =   " select  sequence_id, asset_code, asset_desc, status, asset_type, wallettype, createdon "
					+ " from wallet_assets ";
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 walletAssetsList = new ArrayList<CryptoAssetCoins>();
				 	while(rs.next()){	
				 		walletAsset = new CryptoAssetCoins();
				 		walletAsset.setWalletType(StringUtils.trim(rs.getString("wallettype")));
				 		walletAsset.setAssetCode(StringUtils.trim(rs.getString("asset_code")));
				 		walletAsset.setAssetDescription(StringUtils.trim(rs.getString("asset_desc")));
				 		walletAsset.setAssetType(StringUtils.trim(rs.getString("asset_type")));
				 		walletAsset.setCreatedOn(StringUtils.trim(rs.getString("createdon")));
				 		walletAsset.setStatus(StringUtils.trim(rs.getString("status")));
				 		walletAsset.setSequenceId(StringUtils.trim(rs.getString("sequence_id")));
				 		
				 		walletAssetsList.add(walletAsset);
				 	}
			 }	
			
			 if(walletAssetsList!=null)
				 if(walletAssetsList.size()==0)
					 walletAssetsList=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getWalletAssets  is  "+e.getMessage());
			throw new Exception ("The exception in method getWalletAssets  is  "+e.getMessage());			
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
		return walletAssetsList;
	}

	public Boolean addWalletAssets(String assetCode, String assetDesc, String status, String assetType,
			String walletType, String issuerAccountId, String distributionAccountId, String liquidityAccountId) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);	
			 
			        //                                     1       2          3         4          5           6         
			 	query = "insert into wallet_assets (asset_code, asset_desc, status, asset_type, wallettype, createdon ) "
						+ "values (?, ?, ?, ?, ?, ? ) ";
			 	               //  1  2  3  4  5  6  
		
			       	pstmt = connection.prepareStatement(query);
					pstmt.setString(1, assetCode );
					pstmt.setString(2, assetDesc );
					pstmt.setString(3, status );
					pstmt.setString(4, assetType );
					pstmt.setString(5, walletType );
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
			NeoBankEnvironment.setComment(1,className,"The exception in method addWalletAssets  is  "+e.getMessage());
			throw new Exception ("The exception in method addWalletAssets  is  "+e.getMessage());
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

	public Boolean editWalletAssets(String assetCode, String assetDesc, String status, String assetType,
			String walletType, String assetId, String issuerAccountId, String distributionAccountId, String liquidityAccountId) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		boolean result = false;
		try{	
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
			 
				query = " update wallet_assets set asset_code = ?, asset_desc = ?, status = ?, asset_type = ?, "
						+ " wallettype = ? where sequence_id = ?";
			       	pstmt = connection.prepareStatement(query);
					pstmt.setString(1, assetCode );
					pstmt.setString(2, assetDesc );
					pstmt.setString(3, status );
					pstmt.setString(4, assetType );
					pstmt.setString(5, walletType );
					
					pstmt.setString(6, assetId );
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
			NeoBankEnvironment.setComment(1,className,"The exception in method editWalletAssets  is  "+e.getMessage());
			throw new Exception ("The exception in method editWalletAssets  is  "+e.getMessage());
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


	
	
	public List<AssetTransaction> geFiatWalletTransactions()  throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		AssetTransaction transaction = null;
		List<AssetTransaction> transactionList=null;
		try{
			connection = super.getConnection();	
			query = " select a.txnusercode txnusercode, a.sysreference sysreference, a.pymtchannel pymtchannel, "
				  + " a.txnamount txnamount, a.txncurrencyid txncurrencyid, a.txnmode txnmode, a.txndatetime txndatetime, b.walletid walletid, "
				  + " b.wallettype wallettype from txn_wallet_cust_bc a, wallet_details b  where a.walletid = b.walletid "
				  + " order by txndatetime desc limit 1000 ";
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 transactionList = new ArrayList<AssetTransaction>();
				 	while(rs.next()){	
				 		transaction = new AssetTransaction();
				 		transaction.setTxnUserCode(StringUtils.trim(rs.getString("txnusercode")));
				 		transaction.setTxnDateTime(StringUtils.trim(rs.getString("txndatetime")));
				 		transaction.setTxnMode(StringUtils.trim(rs.getString("txnmode")));
				 		transaction.setPymtChannel(StringUtils.trim(rs.getString("pymtchannel")));
				 		transaction.setSystemReferenceInt(StringUtils.trim(rs.getString("sysreference")));
				 		transaction.setTxnAmount(StringUtils.trim(rs.getString("txnamount")));
				 		transactionList.add(transaction);
				 	}
			 }
			 if(transactionList!=null)
				 if(transactionList.size()==0)
					 transactionList=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getFiatWalletLastTenTxn  is  "+e.getMessage());
			throw new Exception ("The exception in method getFiatWalletLastTenTxn  is  "+e.getMessage());			
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
		return transactionList;
	}
	
	public List<Customer> getStellarCustomers()  throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		Customer customer = null;
		List<Customer> listCustomers=null;
		try{
			connection = super.getConnection();	
			query = " select a.public_key public_key, a.relationshipno relationshipno, "
				  + " b.customername customername from stellar_account_relation a, customer_details b  where a.relationshipno = b.relationshipno "
				  + " order by sequenceno desc limit 1000 ";
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 listCustomers = new ArrayList<Customer>();
				 	while(rs.next()){	
				 		customer = new Customer();
				 		customer.setCustomerName(StringUtils.trim(rs.getString("customername")));
				 		//customer.setPublicKey(StringUtils.trim(rs.getString("public_key")));
				 		customer.setPublicKey(Utilities.decryptString( StringUtils.trim(rs.getString("public_key"))));
				 		customer.setRelationshipNo(StringUtils.trim(rs.getString("relationshipno")));
				 		listCustomers.add(customer);
				 	}
			 }
			 if(listCustomers!=null)
				 if(listCustomers.size()==0)
					 listCustomers=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getStellarCustomers  is  "+e.getMessage());
			throw new Exception ("The exception in method getStellarCustomers  is  "+e.getMessage());			
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
		return listCustomers;
	}

	public ArrayList<Customer> getSearchOpsSpecificPorteTxnDetails(String customerName, String relationshipNo,
			String customerId, String mobileNo) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<Customer> arrCustomer = null;
		try {
			NeoBankEnvironment.setComment(3,className,"in  getOpsSpecificCustomerDetails searched relationshipNo is "+
					relationshipNo+ "searched customerId is  "+customerId+ "searched custName  "+ customerName + "phoneNumber is  "+ mobileNo            );

			connection = super.getConnection();	 
			
			query = "select a.relationshipno relationshipno, a.customername customername, a.customerid customerid,a.custcontact custcontact,"
					+ "b.public_key public_key  from stellar_account_relation b, customer_details a where a.relationshipno = b.relationshipno and";

			if(relationshipNo.equals("")==false) {	
				query+= "  a.relationshipno = ? and  ";
			}else {
					if(customerName.equals("")==false) {
						query+= "  a.customername like '%"+customerName+"%' and  ";
					}
					if(customerId.equals("")==false) {
						query+= "  a.customerid like '%"+customerId+"%' and  ";
					}
					if(mobileNo.equals("")==false) {
						query+= "  a.custcontact like '%"+mobileNo+"%' and  ";
					}
			}
			query+= "  1=1 ";
			
			NeoBankEnvironment.setComment(3,className,"search query being executed  is  "+query);


			pstmt = connection.prepareStatement(query);
			if(relationshipNo.equals("")==false) {
				pstmt.setString(1, relationshipNo);
				
			}
			
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){
				 arrCustomer = new ArrayList<Customer>();
				 	while(rs.next()){	 
				 		Customer m_Customer = new Customer();
				 		m_Customer.setCustomerName(StringUtils.trim(rs.getString("customername")));
						m_Customer.setRelationshipNo(StringUtils.trim(rs.getString("relationshipno")));
//						m_Customer.setPublicKey(StringUtils.trim(rs.getString("public_key")));
						m_Customer.setPublicKey(Utilities.tripleDecryptData( StringUtils.trim(rs.getString("public_key"))));
						arrCustomer.add(m_Customer);
				 		} 

				 	} 

			 if(arrCustomer!=null)
				 if(arrCustomer.size()==0)
					 arrCustomer=null;	
			
		}catch(Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method getSearchOpsSpecificPorteTxnDetails  is  "+e.getMessage());
			throw new Exception ("The exception in method getSearchOpsSpecificPorteTxnDetails  is  "+e.getMessage());			
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
	
		return arrCustomer;
	}

	public ArrayList<Customer> getAllPorteWaletsDetails() throws Exception{
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		String query = null;
		ArrayList<Customer> arrPorteWallets = null;
		try {
			connection = super.getConnection();
			query = "select a.relationshipno relationshipno, a.customername customername, a.customerid customerid,a.custcontact custcontact,"
					+ "b.public_key public_key  from stellar_account_relation b, customer_details a where a.relationshipno = b.relationshipno order by b.createdon desc limit 1000";
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet) pstmt.executeQuery();
			if(rs!=null){
				arrPorteWallets = new ArrayList<Customer>();
				 	while(rs.next()){	 
				 		Customer m_Customer = new Customer();
				 		m_Customer.setCustomerName(StringUtils.trim(rs.getString("customername")));
						m_Customer.setRelationshipNo(StringUtils.trim(rs.getString("relationshipno")));
						//m_Customer.setPublicKey(StringUtils.trim(rs.getString("public_key")));
						m_Customer.setPublicKey(Utilities.tripleDecryptData( StringUtils.trim(rs.getString("public_key"))));
						arrPorteWallets.add(m_Customer);
				 		} 

				 	} 
			
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1, className, "The exception in method getAllPorteWaletsDetails is  " + e.getMessage());
			throw new Exception("The exception in method getAllPorteWaletsDetails is  " + e.getMessage());
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
		return arrPorteWallets;
	}

	public ArrayList<Customer> getViewAllPorteTxnDetails()  throws Exception{
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		String query = null;
		ArrayList<Customer> arrPorteTxn = null;
		try {
			connection = super.getConnection();
			query = "select a.relationshipno relationshipno, a.customername customername, a.customerid customerid,a.custcontact custcontact,"
					+ "b.public_key public_key  from stellar_account_relation b, customer_details a where a.relationshipno = b.relationshipno order by b.createdon desc limit 1000";
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet) pstmt.executeQuery();
			if(rs!=null){
				arrPorteTxn = new ArrayList<Customer>();
				 	while(rs.next()){	 
				 		Customer m_Customer = new Customer();
				 		m_Customer.setCustomerName(StringUtils.trim(rs.getString("customername")));
						m_Customer.setRelationshipNo(StringUtils.trim(rs.getString("relationshipno")));
//						m_Customer.setPublicKey(StringUtils.trim(rs.getString("public_key")));
//						m_Customer.setPublicKey(StringUtils.trim(rs.getString("public_key")));
						m_Customer.setPublicKey(Utilities.tripleDecryptData( StringUtils.trim(rs.getString("public_key"))));
						arrPorteTxn.add(m_Customer);
				 		} 

				 	} 
			
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1, className, "The exception in method getViewAllPorteTxnDetails is  " + e.getMessage());
			throw new Exception("The exception in method getViewAllPorteTxnDetails is  " + e.getMessage());
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
		
		return arrPorteTxn;
	}

	

}
