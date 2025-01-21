package com.pporte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.model.CryptoAssetCoins;
import com.pporte.model.AssetTransaction;
import com.pporte.model.Wallet;
import com.pporte.utilities.Utilities;

public class CustomerWalletDao extends HandleConnections{
	public static String className = CustomerWalletDao.class.getSimpleName();
	
	public Wallet getCustomerWalletDetails(String relationshipNo, String userType) throws Exception{
		
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		Wallet m_Wallet=null;
			try {
				connection = super.getConnection();	
				query=" select a.walletid walletid,a.relationshipno relationshipno,a.walletdesc walletdesc,"
					+ " a.usertype usertype, a.wallettype wallettype,a.status status,a.currbal currbal,"
					+ " a.currencyid currencyid,a.lastupdated lastupdated,a.createdon createdon,"
					+ " a.blockcodeid blockcodeid, b.asset_code asset_code, b.asset_desc asset_desc, b.asset_type asset_type"
					+ "  from wallet_details a, wallet_assets b where a.relationshipno=? and a.usertype=? and a.wallettype = b.wallettype and a.wallettype=?";
				pstmt=connection.prepareStatement(query);
				pstmt.setString(1, relationshipNo);
				pstmt.setString(2, userType);
				pstmt.setString(3, "F");
				rs= (ResultSet)pstmt.executeQuery();
				if (rs!=null) {
					while (rs.next()) {
						m_Wallet= new Wallet();
						m_Wallet.setWalletId( StringUtils.trim(rs.getString("walletid")));
						m_Wallet.setRelationshipNo( StringUtils.trim(rs.getString("relationshipno")));
						m_Wallet.setWalletDesc( StringUtils.trim(rs.getString("walletdesc")));
						m_Wallet.setUserType( StringUtils.trim(rs.getString("usertype")));
						m_Wallet.setWalletType( StringUtils.trim(rs.getString("wallettype")));
						m_Wallet.setStatus( StringUtils.trim(rs.getString("status")));
						m_Wallet.setCurrentBalance(Utilities.getMoneyinDecimalFormat(StringUtils.trim(rs.getString("currbal"))));
						m_Wallet.setCurrencyId( StringUtils.trim(rs.getString("currencyid")));
						m_Wallet.setLastUpdated( StringUtils.trim(rs.getString("lastupdated")));
						m_Wallet.setCreatedOn( StringUtils.trim(rs.getString("createdon")));
						m_Wallet.setBlockCodeId( StringUtils.trim(rs.getString("blockcodeid")));
						m_Wallet.setAssetCode( StringUtils.trim(rs.getString("asset_code")));
						m_Wallet.setAssetDescription( StringUtils.trim(rs.getString("asset_desc")));
					}
				}

			}catch(Exception e){
				NeoBankEnvironment.setComment(1,className,"The exception in method getCustomerWalletDetails  is  "+e.getMessage());
				throw new Exception ("The exception in method getCustomerWalletDetails  is  "+e.getMessage());			
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
			return m_Wallet;
	}
	

	//**********************BlockChain******************************************************************************************************
	public static synchronized boolean insertGenericWalletTransactions(String txnCode, String walletId,String txnUserCode,
			String systemReference, String txnAmount, String currencyId,String txnmode, String transactionDatetime, String userType)  throws Exception  {
		    boolean result = false;	
		    CloseableHttpClient client =null;
			HttpPost httpPost=null;
			String streamName = null;
			String chainName =null;
			String blockchainResult = null; String blockerror = null;    CloseableHttpResponse response=null;
			JsonObject responseJson=null; JsonObject responseJson2=null; 
			JsonObject txnDetails=null; JsonObject tranasctionDetails=null;
			try {
				
			if (NeoBankEnvironment.getBlockChainInsert().equals("false")) {
				 NeoBankEnvironment.setComment(3,className,"==================Wallet Ledger Blockchain insert is off.================");
				 return true;
			}

			 NeoBankEnvironment.setComment(3,className,"in insertGenericWalletTransactions Blockchain   ");
          	 client = HttpClients.createDefault();
 		     httpPost = new HttpPost(NeoBankEnvironment.getMultiChainWalletLedgerChainRPCURLPORT());
 		     
		    chainName = NeoBankEnvironment.getWalletLedgerBlockChainName();
			if (userType.equals("C")) {
				 streamName = NeoBankEnvironment.getBlockChainCustomerWalletStreamName();	
			}
			if (userType.equals("M")) {
				 streamName = NeoBankEnvironment.getBlockChainMerchantWalletStreamName();
			}
			
			txnDetails = new JsonObject();  tranasctionDetails= new JsonObject();
 		    
			tranasctionDetails.addProperty("txncode", txnCode);
			tranasctionDetails.addProperty("walletid", walletId);
			tranasctionDetails.addProperty("txnusercode", txnUserCode);
			tranasctionDetails.addProperty("sysreference", systemReference);
			tranasctionDetails.addProperty("txnamount", txnAmount );
			tranasctionDetails.addProperty("txncurrencyid", currencyId);
			tranasctionDetails.addProperty("txnmode", txnmode);
			tranasctionDetails.addProperty("txndatetime", transactionDatetime);
 		 
			txnDetails.add("txndetails", tranasctionDetails);
 		    
 		    NeoBankEnvironment.setComment(3,className," txndetails "+txnDetails.toString());
 		    String txnDetailsHex = Utilities.asciiToHex(txnDetails.toString());
 		    String json ="{\"method\":\"publish\",\"params\":[\""+streamName+"\",\""+ txnCode+"\",\""+txnDetailsHex+"\"],\"id\":1,\"chain_name\":\""+chainName+"\"}";
 		    StringEntity entity = new StringEntity(json);
 		    httpPost.setEntity(entity);
 		    httpPost.setHeader("Accept", "application/json");
 		    httpPost.setHeader("authorization", Utilities.getBasicAuthHeader(NeoBankEnvironment.getWalletLedgerChainMultiChainUser(),NeoBankEnvironment.getWalletLedgerChainRPCAuthKey()));
 		    httpPost.setHeader("Content-type", "application/json");

 		    response = client.execute(httpPost);
		    NeoBankEnvironment.setComment(3,className,"Response code "+response.getCode());
		    HttpEntity entityResponse = response.getEntity();
		    String data = EntityUtils.toString(entityResponse);
		    responseJson =  new Gson().fromJson(data, JsonObject.class);
		    blockchainResult= responseJson.get("result").toString();
 		    if (response.getCode()==200) {
 		    	NeoBankEnvironment.setComment(3,className,"Blockchain success  "  + blockchainResult);
		        result = true;	
 		    } else {
 		    	 
					  blockerror = responseJson.get("error").toString();
					  responseJson2 =  new Gson().fromJson(blockerror, JsonObject.class);
					  String code = responseJson2.get("code").toString();
					  String message = responseJson2.get("message").toString();
					
					  NeoBankEnvironment.setComment(1,className,"The exception in method insert GenericWalletTransactions  is  Problem  in the Blockchain  code: "+  code + "  message : "+ message  );
				        result = false;	
 		    }
				 
			}catch(Exception e){
				result = false;
				NeoBankEnvironment.setComment(1,className,"The exception in method insertGenericWalletTransactions  is  "+e.getMessage());
				throw new Exception ("The exception in method insertGenericWalletTransactions  is  "+e.getMessage());
			}finally{
				try {
					if(client!=null)client.close(); if(httpPost!=null) httpPost.clear();if (streamName!=null) streamName=null;
					if (chainName!=null)chainName=null; if (blockchainResult!=null) blockchainResult=null; if(tranasctionDetails!=null)tranasctionDetails=null;
					if (blockerror!=null) blockerror=null; if (txnDetails!=null) txnDetails=null; if (response!=null) response.close();
					if (responseJson!=null) responseJson=null;if (responseJson2!=null) responseJson2=null;
				}catch (Exception ee) {
					NeoBankEnvironment.setComment(1,className,"The exception in method insertGenericWalletTransactions, finally block is  "+ee.getMessage());
				}
			}
			return result;
			
		}
	
	public List<CryptoAssetCoins> getCryptoAssetPorteCoins() throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		List<CryptoAssetCoins> aryCryptoAssetCoins = null;
		try{

			 connection = super.getConnection();	
			 query = " select asset_code, asset_desc, status, asset_type, "
			 		+" wallettype, createdon from wallet_assets where asset_type=? and asset_code != ? and status=? ";
			 pstmt = connection.prepareStatement(query);
			 pstmt.setString(1, "P");
			 pstmt.setString(2, "XLM");
			 pstmt.setString(3, "A");

			 rs = pstmt.executeQuery();
			  if(rs!=null){	
				  aryCryptoAssetCoins = new ArrayList<CryptoAssetCoins>();
				 	while(rs.next()){	
				 		CryptoAssetCoins cryptoAssetCoins = new CryptoAssetCoins();
				 		cryptoAssetCoins.setAssetCode( StringUtils.trim(rs.getString("asset_code"))   ); 
				 		cryptoAssetCoins.setAssetDescription( StringUtils.trim(rs.getString("asset_desc"))   ); 
				 		cryptoAssetCoins.setStatus( StringUtils.trim(rs.getString("status"))   ); 
				 		cryptoAssetCoins.setAssetType( StringUtils.trim(rs.getString("asset_type"))   ); 
				 		cryptoAssetCoins.setWalletType( StringUtils.trim(rs.getString("wallettype"))   ); 
				 		aryCryptoAssetCoins.add(cryptoAssetCoins);	
			 		} // end of while						 	
			 	} //end of if
			  if(aryCryptoAssetCoins!=null)
				  if(aryCryptoAssetCoins.size()==0)
					  aryCryptoAssetCoins=null;			  
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getCryptoAssetPorteCoins  is  "+e.getMessage());
		}finally{
		if(connection!=null)
			try {
				super.close();
			} catch (SQLException e) {
				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
			if(pstmt!=null) pstmt.close(); if(rs!=null) rs.close();
		}

		return aryCryptoAssetCoins;	
		
	}
	
	public ConcurrentHashMap<String, String> getCustomerWallets(String relationshipNo) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ConcurrentHashMap<String, String> hashWallets = null;
			try {
				connection = super.getConnection();	
				query=" select walletid, assetcode from wallet_details_external where relationshipno=? and usertype=? ";
				pstmt=connection.prepareStatement(query);
				pstmt.setString(1, relationshipNo);
				pstmt.setString(2, "C");
				rs= (ResultSet)pstmt.executeQuery();
				if (rs!=null) {
					hashWallets = new ConcurrentHashMap<String, String>();
					while (rs.next()) {
						hashWallets.put(StringUtils.trim(rs.getString("walletid")),
								StringUtils.trim(rs.getString("assetcode")));
					}
				}
			}catch(Exception e){
				NeoBankEnvironment.setComment(1,className,"The exception in method getCustomerWalletDetails  is  "+e.getMessage());
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
			return hashWallets;
	}



	public Boolean createWallet( String relationshipNo, String walletDesc2, String publicKey, String assetCode) throws Exception{
		PreparedStatement pstmt=null;
 		Connection connection = null;
 		String query = null;
 		boolean result = false;
 		String walletId =null;
 		SimpleDateFormat formatter1=null;
 		
 		try{
 			connection = super.getConnection();
 			connection.setAutoCommit(false);
 			formatter1 = new SimpleDateFormat ("yyMMdd");  formatter1.setTimeZone(TimeZone.getTimeZone("UTC"));
			walletId = (formatter1.format(new Date()))+( RandomStringUtils.random(10, false, true)).toString();
			
								                 		//		1         2				3		 		 4			5		    6            7         8                         
			query = " insert into wallet_details_external (walletid, relationshipno, walletdesc, 	usertype, 	status,   assetcode,  lastupdated, createdon )  "
			+ "  values (?, ?, ?, ?, ?, ? ,?, ? )  ";
			         //  1  2  3  4  5  6  7  8  
			pstmt = connection.prepareStatement(query);					
			pstmt.setString(1,  walletId); 
			pstmt.setString(2, relationshipNo ); 
			pstmt.setString(3, walletDesc2 );
			pstmt.setString(4, "C" ); // Customer
			pstmt.setString(5, "A" ); // Active
			pstmt.setString(6, assetCode ); // Active
			pstmt.setString(7, Utilities.getMYSQLCurrentTimeStampForInsert() ); 
			pstmt.setString(8, Utilities.getMYSQLCurrentTimeStampForInsert()   ); 
			try {
				pstmt.executeUpdate();
			}catch (Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}
			connection.commit();
			result = true;
 		}catch(Exception e){
 			connection.rollback(); result = false;
 			NeoBankEnvironment.setComment(1,className,"The exception in method createWallet  is  "+e.getMessage());
 			throw new Exception ("The exception in method createWallet  is  "+e.getMessage());
 		}finally{
 		if(connection!=null)
 			try {
 				super.close();
 			} catch (SQLException e) {
 				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
 			}
 			if(pstmt!=null) pstmt.close();
 			if(walletId!=null) walletId= null;
 			if(formatter1!=null) formatter1= null;
 		}
 		return result;
	}

	public Wallet getWalletBalance(String walletId) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		Wallet m_Wallet=null;
			try {
				connection = super.getConnection();	
				query=" select walletid,relationshipno,walletdesc,usertype,wallettype,status,currbal, "
						+ " currencyid,lastupdated,createdon,blockcodeid from wallet_details where walletid = ? ";
				pstmt=connection.prepareStatement(query);
				pstmt.setString(1, walletId);
				rs= (ResultSet)pstmt.executeQuery();
				if (rs!=null) {
					m_Wallet= new Wallet();
					while (rs.next()) {
						m_Wallet.setWalletType( StringUtils.trim(rs.getString("wallettype")));
						m_Wallet.setStatus( StringUtils.trim(rs.getString("status")));
						m_Wallet.setCurrentBalance( StringUtils.trim(rs.getString("currbal")));
						m_Wallet.setCurrencyId( StringUtils.trim(rs.getString("currencyid")));
						m_Wallet.setLastUpdated( StringUtils.trim(rs.getString("lastupdated")));
				
					}
				}
			}catch(Exception e){
				NeoBankEnvironment.setComment(1,className,"The exception in method getWalletBalance  is  "+e.getMessage());
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
		return m_Wallet;
	}



	public List<AssetTransaction> getFiatWalletLastTenTxn(String relationshipNo)  throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		AssetTransaction transaction = null;
		List<AssetTransaction> transactionList=null;
		try{
			connection = super.getConnection();	
			query = " select a.txncode txncode, a.txnusercode txnusercode, a.sysreference sysreference, a.pymtchannel pymtchannel, "
				  + " a.txnamount txnamount, a.txncurrencyid txncurrencyid, a.txnmode txnmode, a.txndatetime txndatetime, b.walletid walletid, "
				  + " b.wallettype wallettype from txn_wallet_cust_bc a, wallet_details b  where a.walletid = b.walletid "
				  + " and b.relationshipno = ? order by txndatetime desc limit 5 ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 transactionList = new ArrayList<AssetTransaction>();
				 	while(rs.next()){	
				 		transaction = new AssetTransaction();
				 		transaction.setTxnUserCode(StringUtils.trim(rs.getString("txnusercode")));
				 		transaction.setTxnDateTime(Utilities.getDateTimeFormatInFullForDisplay(StringUtils.trim(rs.getString("txndatetime"))));
				 		transaction.setTxnMode(StringUtils.trim(rs.getString("txnmode")));
				 		transaction.setPymtChannel(StringUtils.trim(rs.getString("pymtchannel")));
				 		transaction.setSystemReferenceInt(StringUtils.trim(rs.getString("sysreference")));
				 		transaction.setTxnAmount(Utilities.getMoneyinDecimalFormat(StringUtils.trim(rs.getString("txnamount"))));
				 		transactionList.add(transaction);
				 	}
			 }
			 
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



	public ConcurrentHashMap<String, String> getTransactionRules() throws Exception  {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		//Wallet wallet = null;
		ConcurrentHashMap<String, String> hashTxnRules = null;
		try{
			connection = super.getConnection();	

			query = " select paymode, rulesdesc  from transaction_rules  ";
			
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 hashTxnRules = new ConcurrentHashMap<String, String>();
				 	while(rs.next()){	 			 			
				 		hashTxnRules.put(StringUtils.trim(rs.getString("paymode")) , StringUtils.trim(rs.getString("rulesdesc")) );
				 		} // end of while
				 	//arr_Product.add(m_Product);
				 	} //end of if rs!=null check
			 // validate the password
			 if(hashTxnRules!=null)
				 if(hashTxnRules.size()==0)
					 hashTxnRules=null;
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getTransactionRules  is  "+e.getMessage());
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
		return hashTxnRules;
		
	}



	public List<AssetTransaction> getFiatWalletTxnBtnDates(String dateFrom, String dateTo,
			String relationshipNo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		AssetTransaction transaction = null;
		List<AssetTransaction> transactionList=null;
		try{
			connection = super.getConnection();	
			query = " select a.txncode txncode, a.txnusercode txnusercode, a.sysreference sysreference, a.pymtchannel pymtchannel, "
				+ "	  a.txnamount txnamount, a.txncurrencyid txncurrencyid, a.txnmode txnmode, a.txndatetime txndatetime, b.walletid walletid, "
				+ "	  b.wallettype wallettype from txn_wallet_cust_bc a, wallet_details b  where a.walletid = b.walletid "
				+ "	  and b.relationshipno = ?  and a.txndatetime between ? and ? order by txndatetime desc limit 1000 ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, dateFrom);
			pstmt.setString(3, dateTo);
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 transactionList = new ArrayList<AssetTransaction>();
				 	while(rs.next()){	
				 		transaction = new AssetTransaction();
				 		transaction.setTxnUserCode(StringUtils.trim(rs.getString("txncode")));
				 		transaction.setTxnDateTime(Utilities.getDateTimeFormatInFullForDisplay(StringUtils.trim(rs.getString("txndatetime"))));
				 		transaction.setTxnMode(StringUtils.trim(rs.getString("txnmode")));
				 		transaction.setPymtChannel(StringUtils.trim(rs.getString("pymtchannel")));
				 		transaction.setSystemReferenceInt(StringUtils.trim(rs.getString("sysreference")));
				 		transaction.setTxnAmount(Utilities.getMoneyinDecimalFormat(StringUtils.trim(rs.getString("txnamount"))));
				 		transactionList.add(transaction);
				 	}
			 }
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getFiatWalletTxnBtnDates  is  "+e.getMessage());
			throw new Exception ("The exception in method getFiatWalletTxnBtnDates  is  "+e.getMessage());			
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
	public String getFiatWalletBalance(String relationshipNo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		String walletbalance=null;
		String walletId=null;
		String walletDetails =null;
			try {
				connection = super.getConnection();	
				query=" select currbal, walletid from wallet_details where relationshipno = ? and wallettype = ? ";
				pstmt=connection.prepareStatement(query);
				pstmt.setString(1, relationshipNo);
				pstmt.setString(2, "F");
				rs= (ResultSet)pstmt.executeQuery();
				if (rs!=null) {
					while (rs.next()) {
						walletbalance= StringUtils.trim(rs.getString("currbal"));
						walletId= StringUtils.trim(rs.getString("walletid"));
					}
				}
				if(walletId!=null && walletbalance!=null ) {
					walletDetails=walletId.concat(",").concat(walletbalance);
				}
			}catch(Exception e){
				NeoBankEnvironment.setComment(1,className,"The exception in method getFiatWalletBalance  is  "+e.getMessage());
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
		return walletDetails;
	}


	public ArrayList<Wallet> getWalletDetails(String relationshipNo) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		ArrayList<Wallet> arrWallet = null;
		
		try{
			connection = super.getConnection();	
			                    //  1         2           3          4        5        6           7              8
				query = "select walletid, relationshipno, walletdesc, usertype,  status, currbal, currencyid,  lastupdated "
						+ " from wallet_details   "
						+ "  where relationshipno=? and wallettype= ? ";
				
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, "F");
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){
				 arrWallet = new ArrayList<Wallet>();
				 	while(rs.next()){	 
				 		Wallet wallet=new Wallet();
				 		wallet.setWalletId( StringUtils.trim(rs.getString("walletid"))    );
				 		wallet.setUserType( StringUtils.trim(rs.getString("usertype"))  );
				 		wallet.setRelationshipNo( StringUtils.trim(rs.getString("relationshipno"))  );
				 		wallet.setWalletDesc(StringUtils.trim(rs.getString("walletdesc")) );
				 		wallet.setCurrentBalance(  StringUtils.trim(rs.getString("currbal"))    );
				 		wallet.setCurrencyId(StringUtils.trim(rs.getString("currencyid"))  );
				 		wallet.setStatus(StringUtils.trim(rs.getString("status"))  );
				 		wallet.setLastUpdated(StringUtils.trim(rs.getString("lastupdated"))  );
				 		arrWallet.add(wallet);
				 		} 	
				 	//PPWalletEnvironment.setComment(2,className,"Array wallet size is "+arrWallet.size());
				 	
				 	} 
			 if(arrWallet!=null)
				 if(arrWallet.size()==0)
					 arrWallet=null;
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getWalletDetails  is  "+e.getMessage());
			throw new Exception ("The exception in method getWalletDetails  is  "+e.getMessage());			
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
		return arrWallet;
		
	}
	
		public Boolean createPartnerWallet( String relationshipNo,String publicKey, String assetCode) throws Exception{
			PreparedStatement pstmt=null;
	 		Connection connection = null;
	 		String query = null;
	 		boolean result = false;
	 		String walletId =null;
	 		SimpleDateFormat formatter1=null;
	 		
	 		try{
	 			connection = super.getConnection();
	 			connection.setAutoCommit(false);
	 			formatter1 = new SimpleDateFormat ("yyMMdd");  formatter1.setTimeZone(TimeZone.getTimeZone("UTC"));
				walletId = (formatter1.format(new Date()))+( RandomStringUtils.random(10, false, true)).toString();
				
									                 		//		1         2				3		 		 4			5		    6            7         8                         
				query = " insert into wallet_details_external (walletid, relationshipno, walletdesc, 	usertype, 	status,   assetcode,  lastupdated, createdon )  "
				+ "  values (?, ?, ?, ?, ?, ? ,?, ? )  ";
				         //  1  2  3  4  5  6  7  8  
				pstmt = connection.prepareStatement(query);					
				pstmt.setString(1,  walletId); 
				pstmt.setString(2, relationshipNo ); 
				pstmt.setString(3, "External wallet");
				pstmt.setString(4, "P" ); // Customer
				pstmt.setString(5, "A" ); // Active
				pstmt.setString(6, assetCode ); // Active
				pstmt.setString(7, Utilities.getMYSQLCurrentTimeStampForInsert() ); 
				pstmt.setString(8, Utilities.getMYSQLCurrentTimeStampForInsert()   ); 
				try {
					pstmt.executeUpdate();
				}catch (Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
				}
				connection.commit();
				result = true;
	 		}catch(Exception e){
	 			connection.rollback(); result = false;
	 			NeoBankEnvironment.setComment(1,className,"The exception in method createWallet  is  "+e.getMessage());
	 			throw new Exception ("The exception in method createWallet  is  "+e.getMessage());
	 		}finally{
	 		if(connection!=null)
	 			try {
	 				super.close();
	 			} catch (SQLException e) {
	 				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
	 			}
	 			if(pstmt!=null) pstmt.close();
	 			if(walletId!=null) walletId= null;
	 			if(formatter1!=null) formatter1= null;
	 		}
	 		return result;
		}
			
	
}
