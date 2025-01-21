package com.pporte.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import com.pporte.NeoBankEnvironment;
import com.pporte.model.AssetCoin;
import com.pporte.model.Wallet;
import com.pporte.utilities.Utilities;

public class CustomerCryptoCoinDao  extends HandleConnections{
	
	public static String className = CustomerCryptoCoinDao.class.getSimpleName();

	public ArrayList<AssetCoin> getExternalAssetCoins() throws Exception {
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		String query = null;
		ArrayList<AssetCoin> arrAssetCoins = null;
		try {
			connection = super.getConnection();
			
			query = "select asset_code, asset_desc, status, asset_type, wallettype from  wallet_assets where asset_type= ? and asset_code !=?  and status=?";

			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "E"); //External Crypto
			pstmt.setString(2, "USD"); //For Fiat wallet 
			pstmt.setString(3, "A"); //Status 
			rs = (ResultSet) pstmt.executeQuery();
			if (rs != null) {
				arrAssetCoins = new ArrayList<AssetCoin>();
				while (rs.next()) {
					AssetCoin m_AssetCoin = new AssetCoin();
					m_AssetCoin.setAssetCode(StringUtils.trim(rs.getString("asset_code")));
					m_AssetCoin.setAssetDescription(StringUtils.trim(rs.getString("asset_desc")));
					m_AssetCoin.setStatus(StringUtils.trim(rs.getString("status")));
					m_AssetCoin.setAssetType(StringUtils.trim(rs.getString("asset_type")));
					m_AssetCoin.setWalletType(StringUtils.trim(rs.getString("wallettype")));
					arrAssetCoins.add(m_AssetCoin);
				} // end of while

			} // end of if rs!=null check
			if (arrAssetCoins != null)
				if (arrAssetCoins.size() == 0)
					arrAssetCoins = null;
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1, className, "The exception in method getAssetCoins is  " + e.getMessage());
			throw new Exception("The exception in method getAssetCoins is  " + e.getMessage());
		} finally {
			if (connection != null) {
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
		}
		return arrAssetCoins;
	}

	public boolean registerCoinForUser(String assetCode, String assetDesc, String relationshipNo, String externalWalletId) throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		SimpleDateFormat formatter1=null;
		String walletId = null;
		boolean result = false;
		ResultSet rs=null;
		String walletType = null;

		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
		
			query = "select wallettype from wallet_assets where asset_code = ? ";
				 pstmt = connection.prepareStatement(query);
				 pstmt.setString(1, assetCode);
				 rs = pstmt.executeQuery();
				  if(rs!=null){	
					 	while(rs.next()){	
					 		walletType  =  StringUtils.trim(rs.getString("wallettype")  ); 
				 		} // end of while						 	
				 	} //end of if
				  
				  if(rs != null) rs =null; if(pstmt!=null) pstmt.close();
				  
					NeoBankEnvironment.setComment(2,className,"The exception in after query 1 assetCode "+ assetCode+ " assetDesc "+assetDesc
							+" relationshipNo "+ relationshipNo + " walletType "+ walletType);

		     	formatter1 = new SimpleDateFormat ("yyMMdd");  formatter1.setTimeZone(TimeZone.getTimeZone("UTC"));
				walletId = (formatter1.format(new Date()))+( RandomStringUtils.random(10, false, true)).toString();
			 
				    //                                 1         2               3            4         5        6      7         8             9         10           11       
			   query = "insert into wallet_details (walletid, relationshipno, walletdesc, usertype, wallettype, status, currbal, currencyid, lastupdated, createdon, blockcodeid) "
						+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
			 	               //  1  2  3  4  5  6  7  8  9  10  11
				pstmt = connection.prepareStatement(query);
			    pstmt.setString(1,  walletId); 
				pstmt.setString(2, relationshipNo );
				
				if(assetCode.equals("BTC")) {
					pstmt.setString(3, "Bitcoin Wallet" );
				}else if(assetCode.equals("ETH")) {
					pstmt.setString(3, "Ethereum Wallet" );
				}else if(assetCode.equals("LTC")) {
					pstmt.setString(3, "Litecoin Wallet" );
				}else {
					pstmt.setString(3, "Wallet" );
				}
				
				pstmt.setString(4, "C" ); // Customer
				pstmt.setString(5, walletType ); // Active
				pstmt.setString(6, "A" ); // Active
				pstmt.setBigDecimal(7,  new BigDecimal("0")     );
				pstmt.setString(8, assetCode); 
				pstmt.setString(9, Utilities.getMYSQLCurrentTimeStampForInsert() ); 
				pstmt.setString(10, Utilities.getMYSQLCurrentTimeStampForInsert()    ); 
				pstmt.setInt(11, 0   ); //default blockcodeid is 0; 
					
					try {
						pstmt.executeUpdate();
						}catch(Exception e) {
							throw new Exception (" failed query "+query+" "+e.getMessage());
						}
					pstmt.close();
					
			    					//                                 1             2         3         4                
				   query = "insert into wallet_asset_relation (external_walletid, walletid, assetcode, createdon) "
							+ " values (?, ?, ?, ? ) ";
				 	                //  1  2  3  4 
					pstmt = connection.prepareStatement(query);
				    pstmt.setString(1,  externalWalletId); 
					pstmt.setString(2, walletId ); 
					pstmt.setString(3, assetCode );
					pstmt.setString(4, Utilities.getMYSQLCurrentTimeStampForInsert()); 
						
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
			NeoBankEnvironment.setComment(1,className,"The exception in method registerCoinForUser  is  "+e.getMessage());
			throw new Exception ("The exception in method registerCoinForUser  is  "+e.getMessage());
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

	public List<Wallet> getAssetCoinsWallets(String relationshipNo) throws Exception {
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		String query = null;
		ArrayList<Wallet> arrWalletCoins = null;
		try {
			connection = super.getConnection();
			
			/*query = "select a.walletid, a.wallettype, a.currbal, a.status, a.walletdesc, b.assetcode from  wallet_details a, wallet_asset_relation b  where relationshipno =? and status =?"
					+ " and a.walletid = b.walletid ";*/
			
			query = "select a.walletid, a.wallettype, a.currbal, a.status, a.walletdesc,a.lastupdated, b.assetcode, c.asset_desc from  wallet_details a, wallet_asset_relation b,"
			+" wallet_assets c where relationshipno =? and a.status =? and a.walletid = b.walletid and b.assetcode = c.asset_code";

			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo); //External Crypto
			pstmt.setString(2, "A"); //For Fiat wallet 
			rs = (ResultSet) pstmt.executeQuery();
			if (rs != null) {
				arrWalletCoins = new ArrayList<Wallet>();
				while (rs.next()) {
					Wallet m_WalletCoin = new Wallet();
					m_WalletCoin.setWalletId(StringUtils.trim(rs.getString("walletid")));
					m_WalletCoin.setWalletType(StringUtils.trim(rs.getString("wallettype")));
					m_WalletCoin.setCurrentBalance(StringUtils.trim(rs.getString("currbal")));
					m_WalletCoin.setStatus(StringUtils.trim(rs.getString("status")));
					m_WalletCoin.setWalletDesc(StringUtils.trim(rs.getString("walletdesc")));
					m_WalletCoin.setWalletAssetCode(StringUtils.trim(rs.getString("assetcode")));
					m_WalletCoin.setWalletAssetDesc(StringUtils.trim(rs.getString("asset_desc")));
					m_WalletCoin.setLastUpdated(Utilities.displayDateFormat(StringUtils.trim(rs.getString("lastupdated")),"dd MMM yy HH:mm a"));
					arrWalletCoins.add(m_WalletCoin);
				} // end of while

			} // end of if rs!=null check
			if (arrWalletCoins != null)
				if (arrWalletCoins.size() == 0)
					arrWalletCoins = null;
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1, className, "The exception in method getAssetCoinsWallets is  " + e.getMessage());
			throw new Exception("The exception in method getAssetCoinsWallets is  " + e.getMessage());
		} finally {
			if (connection != null) {
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
		}
		return arrWalletCoins;
	}

	public String getReceiverWalletDetails(String receiverWallet) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		ResultSet rs=null;
		String externalWalletId = null;
		String externalWalletCode = null;
		String receiverWalletDetailed = null;

		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
		
			query = "select b.external_walletid, b.assetcode, a.walletid  from  wallet_details a, wallet_asset_relation b where "
					+ " a.walletid = b.walletid and a.walletid = ? ";
				 pstmt = connection.prepareStatement(query);
				 pstmt.setString(1, receiverWallet);
				 rs = pstmt.executeQuery();
				  if(rs!=null){	
					 	while(rs.next()){	
					 		externalWalletId  =  StringUtils.trim(rs.getString("external_walletid")  ); 
					 		externalWalletCode  =  StringUtils.trim(rs.getString("assetcode")  ); 

				 		} // end of while						 	
				 	} //end of if
				  
				  if(externalWalletId != null) {
						receiverWalletDetailed = externalWalletId+","+externalWalletCode;
						NeoBankEnvironment.setComment(2,className,"in getReceiverWalletDetails receiverWalletDetailed  is  "+ receiverWalletDetailed);

				  }
				  if(rs != null) rs =null; if(pstmt!=null) pstmt.close();
							
				connection.commit();			 	
		}catch(Exception e){
			connection.rollback();
			NeoBankEnvironment.setComment(1,className,"The exception in method getReceiverWalletDetails  is  "+e.getMessage());
			throw new Exception ("The exception in method getReceiverWalletDetails  is  "+e.getMessage());
		}finally{
		if(connection!=null)
			try {
				super.close();
			} catch (SQLException e) {
				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
			if(pstmt!=null) pstmt.close();
		}
		return receiverWalletDetailed;	
	}
	
	public String getSenderWalletDetails(String receiverWallet, String relationshipNo, String assetCode) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		String query = null;
		ResultSet rs=null;
		String ExternalSenderWalletId = null;
		String walletBalance = null;
		String walletDetails = null;
		//String assetCode = null;

		try{
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
		
			query = "select  b.external_walletid, b.assetcode, a.walletid, a.currbal  from  wallet_details a, wallet_asset_relation b where "
					+ " a.walletid = b.walletid and a.walletid = ? and a.relationshipno =?  ";
				 pstmt = connection.prepareStatement(query);
				 pstmt.setString(1, receiverWallet);
				 pstmt.setString(2, relationshipNo);
				 rs = pstmt.executeQuery();
				  if(rs!=null){	
					 	while(rs.next()){	
					 		ExternalSenderWalletId  =  StringUtils.trim(rs.getString("external_walletid")  ); 
					 		walletBalance  =  StringUtils.trim(rs.getString("currbal")  );
							 
				 		} // end of while						 	
				 	} //end of if
				  
				  if(ExternalSenderWalletId !=null) {
				 		walletDetails = ExternalSenderWalletId+","+walletBalance;
						NeoBankEnvironment.setComment(2,className,"in getSenderWalletDetails ExternalSenderWalletId  is  "+ walletDetails);

				  }
				  
				  if(rs != null) rs =null; if(pstmt!=null) pstmt.close();
						
				  
				connection.commit();			 	
		}catch(Exception e){
			connection.rollback();
			NeoBankEnvironment.setComment(1,className,"The exception in method getSenderWalletDetails  is  "+e.getMessage());
			throw new Exception ("The exception in method getSenderWalletDetails  is  "+e.getMessage());
		}finally{
		if(connection!=null)
			try {
				super.close();
			} catch (SQLException e) {
				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
			if(pstmt!=null) pstmt.close();
		}
		return walletDetails;	
	}
	public Boolean externalCoinP2P(String relationshipNo, String senderWalletId, String payAmount, String payComments,
			String receiverEmail, String referenceNo, String txnUserCode, String customerCharges, String txnPayMode,
			String assetCode, String extSystemRef,String receiverWalletId ) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		String transactionDatetime = null;
		String currencyId= assetCode;
		String senderTxnMode = "D";
		String receiverTxnMode = "C";
		String senderTransactionCode = null;
		String senderTransactionCode2 = null;
		String receiverTransactionCode = null;
		String userType = "C";
		String systemreference =referenceNo;
		String receiverSystemreference =null;
		String senderRefNo = null;
		String receiverRefNo = null;
		String totalAccruedBalance=null;
		try {	

			String customerChargesValue = customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|"));
			String customerPayType = customerCharges.substring(0, customerCharges.indexOf(","));

			payAmount = payAmount.replaceAll(",", "");
			transactionDatetime = Utilities.getMYSQLCurrentTimeStampForInsert();
			SimpleDateFormat formatter1 = new SimpleDateFormat ("yyMMddHHmmssSSS");
			senderTransactionCode = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
			senderTransactionCode2 = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
			receiverTransactionCode = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
			receiverSystemreference = txnPayMode + "-" + (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
						+ Utilities.genAlphaNumRandom(9);
			 connection = super.getConnection();
			 connection.setAutoCommit(false);
		 
			 //********Step 1*********\\\\	
			 double senderDebitAmount =  Double.parseDouble(payAmount) + Double.parseDouble(customerChargesValue);
			 double receiverrCreditAmount =  Double.parseDouble(payAmount);
			 
			 //*******Step 2: Update the Sender wallet Ledger
		     query = " update wallet_details set currbal= currbal - ?, lastupdated = ? where  walletid=? ";
			 pstmt = connection.prepareStatement(query);
			 pstmt.setBigDecimal(1,BigDecimal.valueOf(senderDebitAmount));
			 pstmt.setString(2, transactionDatetime); 
			 pstmt.setString(3, senderWalletId); 
			 try {
				 NeoBankEnvironment.setComment(3,className,"update sender wallet amount" + senderDebitAmount );
				 pstmt.executeUpdate();
				 }catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
			}					
			pstmt.close();
			
			// *******Step 3: Update the Receiver wallet Ledger
			query = " update wallet_details set currbal= currbal + ?, lastupdated = ? where  walletid=? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setBigDecimal(1, BigDecimal.valueOf( receiverrCreditAmount));
			pstmt.setString(2, transactionDatetime); 
			pstmt.setString(3, receiverWalletId);
			try {
				NeoBankEnvironment.setComment(3,className,"update receiver wallet amount" + receiverrCreditAmount );
				pstmt.executeUpdate();
				}catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
				}					
			pstmt.close();	
			
			// **********Step 4: Record the wallet transaction ledger
														//  1		2			3			     4			 5			  6		    7			 8             9              10             11         12
			query = "insert into txn_crypto_wallet_cust (txncode, walletid, sysreference_int, txnamount, txncurrencyid, txnmode, txndatetime, txnusercode, pymtchannel, sysreference_ext, assetcode, asset_type) "
					+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?) ";
							// 1  2  3  4  5  6   7  8  9 10 11  12
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, senderTransactionCode); 			
			pstmt.setString(2, senderWalletId); 				
			pstmt.setString(3, systemreference );							
			pstmt.setBigDecimal(4, new BigDecimal(payAmount));  
			pstmt.setString(5, currencyId);
			pstmt.setString(6, senderTxnMode); // Debit as it is a payment by the sender
			pstmt.setString(7, transactionDatetime);
			pstmt.setString(8, txnUserCode);
			pstmt.setString(9, "C"); // W- Wallet, T- Token, C-Crypto
			pstmt.setString(10, extSystemRef);
			pstmt.setString(11, assetCode);
			pstmt.setString(12, "E");
			try {
				NeoBankEnvironment.setComment(3,className,"Executed Sender txn_crypto_wallet_cust amount"+"senderTransactioncode is"+ senderTransactionCode +"senderWalletId is"+senderWalletId 
						+"systemreference is"+systemreference +"payAmount is"+payAmount +"currencyId is"+ currencyId+ "transactionDatetime"+ transactionDatetime);

				pstmt.executeUpdate();
				}catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
				}					
				pstmt.close();	
			if(pstmt!=null)			
				pstmt=null;
			// Step 5 Customer charges
			if(Double.parseDouble(customerChargesValue)>0) {
				                                       //       1         2          3               4            5           6          7             8            9           10              11          12
				query = "insert into txn_crypto_wallet_cust (txncode, walletid, sysreference_int, txnamount, txncurrencyid, txnmode, txndatetime, txnusercode, pymtchannel, sysreference_ext, assetcode, asset_type) "
						+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?) ";
								// 1  2  3  4  5  6   7  8  9  10 11 12
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, senderTransactionCode2); 			
				pstmt.setString(2, senderWalletId); 				
				pstmt.setString(3, systemreference+"-AC" );							
				pstmt.setBigDecimal(4, new BigDecimal(Double.parseDouble(customerChargesValue)));  
				pstmt.setString(5, currencyId);
				pstmt.setString(6, customerPayType); // Debit as it is a payment by the sender
				pstmt.setString(7, transactionDatetime);
				pstmt.setString(8, txnUserCode);
				pstmt.setString(9, "C"); // W- Wallet, T- Token, C-Crypto
				pstmt.setString(10, extSystemRef);
				pstmt.setString(11, assetCode);
				pstmt.setString(12, "E");
				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}					
					pstmt.close();	
				if(pstmt!=null)			
					pstmt=null;					
				
				   //Select the balance to be updated. 
				
				query = "select accrued_balance, sequenceid from txn_business_ledger_bc order by sequenceid desc limit 1";
				pstmt = connection.prepareStatement(query);
				rs = (ResultSet)pstmt.executeQuery();
				if(rs!=null){
					while(rs.next()){	 			 			
						 totalAccruedBalance= ( StringUtils.trim(rs.getString("accrued_balance"))  );
						} // end of while
					NeoBankEnvironment.setComment(3,className,"step 1   totalLoyaltyBalance is  "+totalAccruedBalance   );
				} 
				if(pstmt!=null) pstmt.close();	if(rs!=null) rs.close();
				

             //												   1		2			3			4				5				6			7			8		   9          10            11
				query = "insert into txn_business_ledger_bc (txncode, paytype, custwalletid, merchwalletid, sysreference, txnamount, txncurrencyid, txnmode, txndatetime, accrued_balance, pymtchannel) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
								// 1  2  3  4  5  6  7  8  9  10 11
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, senderTransactionCode2); 
				pstmt.setString(2, txnPayMode);
				pstmt.setString(3, senderWalletId);
				pstmt.setString(4, "");
				pstmt.setString(5, systemreference+"-AC" );			// Additional charges for customer						
				pstmt.setBigDecimal(6, BigDecimal.valueOf( Double.parseDouble(customerChargesValue)));  
				pstmt.setString(7, currencyId);
				pstmt.setString(8, "C"); // Credit 
				pstmt.setString(9, transactionDatetime);
				
				if (totalAccruedBalance!=null) {
					pstmt.setBigDecimal(10,BigDecimal.valueOf( (Double.parseDouble(totalAccruedBalance)+Double.parseDouble(customerChargesValue) )));
				}else {
					pstmt.setBigDecimal(10,BigDecimal.valueOf( Double.parseDouble(customerChargesValue) ));
				}
				pstmt.setString(11, "C"); //Crypto
				try {
						pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query +" "+e.getMessage());
					}					
				if( pstmt!=null)				
					pstmt.close();
			}
			
			// *********5.2*****Receiver Wallet 
														//   1		 2			  3			   4			 5				6		 7			8              9            10            11             12
			query = " insert into txn_crypto_wallet_cust (txncode, walletid, sysreference_int, txnamount, txncurrencyid, txnmode, txndatetime, txnusercode, pymtchannel, sysreference_ext, assetcode, asset_type ) "
				  + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
						   // 1  2  3  4  5  6  7  8  9  10 11 12 
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, receiverTransactionCode); 			
			pstmt.setString(2, receiverWalletId); 				
			pstmt.setString(3, receiverSystemreference );		  					
			pstmt.setBigDecimal(4, new BigDecimal(payAmount));  
			pstmt.setString(5, currencyId);
			pstmt.setString(6, receiverTxnMode); // Debit as it is a payment by the sender
			pstmt.setString(7, transactionDatetime);
			pstmt.setString(8, txnUserCode);
			pstmt.setString(9, "C"); // W- Wallet, T- Token, C-Crypto
			pstmt.setString(10, extSystemRef);
			pstmt.setString(11, assetCode);
			pstmt.setString(12, "E");
			try {
				NeoBankEnvironment.setComment(3,className,"Executed receiver txn_crypto_wallet_cust amount"+ payAmount   );
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}					
			pstmt.close();	if(pstmt!=null)	pstmt=null;
			
			  //******** Step 7 START LOYAULTY MODULE
			String pointsConversion = null;
			String previousPoinsBalance = null;
			boolean firstime = false;
			//String pointsaccrued =null;
			 
		 query = "select pointsconversion from loyalty_rules where paymode=? and status = ? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, txnPayMode );
			pstmt.setString(2, "A");
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 	while(rs.next()){	 			 			
				 		pointsConversion = (StringUtils.trim(rs.getString("pointsconversion"))  );
				 		} // end of while
				 	} //end of if rs!=null check
			 pstmt.close();
		
			if(pointsConversion!=null) {
				
			//*** Step 7.1 Get loyalty balance for wallet
			 query = "select pointbalance balance from loyalty_points_bc where relationshipno=? and sequenceid = (select max(sequenceid) from  loyalty_points_bc where relationshipno = ? ) ";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, relationshipNo);
				pstmt.setString(2, relationshipNo);
				rs = (ResultSet)pstmt.executeQuery();		
				 if(rs!=null){
					 	while(rs.next()){	 			 			
					 		previousPoinsBalance = (StringUtils.trim(rs.getString("balance"))  );
						NeoBankEnvironment.setComment(3,className,"The pointbalance   is " + previousPoinsBalance );
					 		
					 		} // end of while
					 	} //end of if rs!=null check
				 pstmt.close();
			 if(previousPoinsBalance==null) {
				 firstime  = true;
				 NeoBankEnvironment.setComment(3,className,"No previous record present for assetWalletId: "+senderWalletId+" for userType    "+userType);
			 }else {
				 NeoBankEnvironment.setComment(3,className,"Previus Balance for assetWalletId : "+senderWalletId+" is "+previousPoinsBalance+" for userType    "+userType);
			 }
			 
			// Step 7.2: Insert loyalty points for the sender user  
			 								//		1		2			3			   4			5			6				7          8
		  query = "insert into loyalty_points_bc (walletid, usertype, relationshipno, paymode, txnreference, pointaccrued, pointbalance, txndatetime) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?) ";
						//		   1  2  3  4  5  6  7  8
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, senderWalletId); 					
				pstmt.setString(2, userType); 		
				pstmt.setString(3, relationshipNo); // 
				pstmt.setString(4, txnPayMode );	
				pstmt.setString(5, systemreference  ); // systemreference reference generated by the system
				pstmt.setBigDecimal(6, new BigDecimal(payAmount) );  // pointaccrued
				if(firstime) {
					pstmt.setBigDecimal(7, new BigDecimal(payAmount)    );  // pointbalance
				}else {
					pstmt.setBigDecimal(7, BigDecimal.valueOf(Double.parseDouble(payAmount)  + Double.parseDouble(previousPoinsBalance ))    );  // pointbalance
				}
				pstmt.setString(8,transactionDatetime );	
					try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
				}	
			}
			//******END LOYALTY MODULE
				 
			// Please note that we are not calling block-chain on this transaction since is already on public block-chain

			connection.commit();	
			result = true;
	
		}catch(Exception e){
			result = false;
			connection.rollback();
			NeoBankEnvironment.setComment(1,className,"The exception in method ExternalCoinP2P  is  "+e.getMessage());
			throw new Exception ("The exception in method ExternalCoinP2P  is  "+e.getMessage());
		}finally{
		if(connection!=null)
			try {
				super.close();
			} catch (SQLException e) {
				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
				if (rs != null)	rs.close(); if (pstmt != null) pstmt.close();if(transactionDatetime!=null)  transactionDatetime=null; if(receiverTransactionCode!=null)  receiverTransactionCode=null;
				if(userType!=null)  userType=null; if(senderTransactionCode!=null)  senderTransactionCode=null; if(receiverRefNo!=null)  receiverRefNo=null; if(txnUserCode != null) txnUserCode = null;
				if(systemreference!=null)  systemreference=null; if(senderRefNo!=null)  senderRefNo=null; if ( totalAccruedBalance!=null) totalAccruedBalance=null;
		
			}
		
		return result;
	}
	

	public Boolean buyExternalCoinViaToken(String relationshipNo, String tokenId, String amount, String payComments,
			String referenceNo, String txnUserCode, String customerCharges, String txnPayMode, String assetCode,
			String extSystemRef, String assetWalletId, String assetAmount, double newWalletBalanceFromStellar ) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		String transactionDatetime = null;
		String currencyId= assetCode;
		String transactionCode = null;
		String transactionCode2 = null;
		String userType = "C";
		String systemreference =referenceNo;
		double newWalletBalance = newWalletBalanceFromStellar;
		String senderRefNo = null;
		String totalAccruedBalance=null;
		try {
			String customerChargesValue = customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|"));
			//String customerPayType = customerCharges.substring(0, customerCharges.indexOf(","));
			amount = amount.replaceAll(",", "");
			transactionDatetime = Utilities.getMYSQLCurrentTimeStampForInsert();
			SimpleDateFormat formatter1 = new SimpleDateFormat ("yyMMddHHmmssSSS");
			transactionCode = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
			transactionCode2 = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
			connection = super.getConnection();
			connection.setAutoCommit(false);
			//********Step 1 update wallet ballance*********\\\\	
			
			//Please remove this code after integration, just 
			 String previousWalletBalance = null;
			 query = "select currbal  from wallet_details where walletid=?  ";

			 pstmt = connection.prepareStatement(query);
			 pstmt.setString(1, assetWalletId);
			 rs = (ResultSet)pstmt.executeQuery();	
			 if(rs!=null){
			 	while(rs.next()){	 			 			
					 	previousWalletBalance = (StringUtils.trim(rs.getString("currbal"))  );
					 	NeoBankEnvironment.setComment(3,className,"The pointbalance   is " + previousWalletBalance );
					} // end of while
				} //end of if rs!=null check
			 if(pstmt!=null)  pstmt.close(); if(rs!=null)  rs.close();
			// newWalletBalance  = Double.parseDouble(previousWalletBalance) + Double.parseDouble(amount)  - Double.parseDouble(customerChargesValue);
			 newWalletBalance  = Double.parseDouble(previousWalletBalance) + Double.parseDouble(assetAmount); // Asset Balance
						 
			 //***update wallet ballance
			query = "update wallet_details set currbal =  ? , lastupdated = ?  where walletid=?  ";

			pstmt = connection.prepareStatement(query);
			pstmt.setBigDecimal(1, new BigDecimal(newWalletBalance));   
			pstmt.setString(2, transactionDatetime);  	
			pstmt.setString(3, assetWalletId);
			try {
				pstmt.executeUpdate();
				}catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
				}
			if(pstmt!=null)  pstmt.close();		if(rs!=null)  rs.close();
			NeoBankEnvironment.setComment(3,className,"The currbal after Topup is " +newWalletBalance );
			
			// **********Step 2: Record the txn_cardtoken_bc
		/*										//    1        2         3           4              5                 6             7          8         9             10     		11           12
			query = "insert into txn_cardtoken_bc (txncode, tokenid, custrelno, sysreference_ext, sysreference_int, txnamount, merchantcode, txn_type, txndatetime, txncurrencyid, txnusercode, paymode) "
			+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?, ?,  ?, ?) ";
					// 1  2  3  4  5  6   7	 8  9  10 11  12
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, transactionCode); 			
			pstmt.setString(2, tokenId); 				
			pstmt.setString(3, relationshipNo);							
			pstmt.setString(4, extSystemRef);  
			pstmt.setString(5, systemreference);
			pstmt.setBigDecimal(6, new BigDecimal(amount));
			pstmt.setString(7, ""); 
			pstmt.setString(8,  "C" );  //Debit
			pstmt.setString(9,  transactionDatetime );
			pstmt.setString(10,  currencyId );
			pstmt.setString(11, txnUserCode);
			pstmt.setString(12, txnPayMode);
			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}					
			if( pstmt!=null) pstmt.close();
			*/
			//    1        2           3           4                5                  6         7         8         9          10              11        
	     	 query = "insert into txn_cardtoken_bc (txncode, tokenid, txnusercode, sysreference_ext, sysreference_int, txnamount, custrelno, paymode, txndatetime, txncurrencyid ) "
					+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?, ? ) ";
							// 1  2  3  4  5  6   7	 8  9  10  11
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, transactionCode); 			
			pstmt.setString(2, tokenId); 										
			pstmt.setString(3, txnUserCode);							
			pstmt.setString(4, extSystemRef);  // External 
			pstmt.setString(5, systemreference); // Internal generated
			pstmt.setBigDecimal(6, new BigDecimal(amount));
			pstmt.setString(7, relationshipNo); 
			pstmt.setString(8,  NeoBankEnvironment.getCodeTokenWalletTopup());
			pstmt.setString(9,  transactionDatetime );
			pstmt.setString(10,  currencyId );
			
			try {
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
								}					
			if( pstmt!=null)		pstmt.close();	
			
			// **********Step 2: Record the Crypto transaction ledger
														// 1		2			3			4			5				  6	    	7			8              9             10             11          12
			query = "insert into txn_crypto_wallet_cust (txncode, walletid, sysreference_int, txnamount, txncurrencyid, txnmode, txndatetime, txnusercode, pymtchannel, sysreference_ext, assetcode, asset_type) "
			+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?,  ? ) ";
					// 1  2  3  4  5  6  7  8  9  10  11  12
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, transactionCode); 			
			pstmt.setString(2, assetWalletId); 				
			pstmt.setString(3, systemreference );							
			pstmt.setBigDecimal(4, new BigDecimal(assetAmount));  
			pstmt.setString(5, currencyId);
			pstmt.setString(6, "C"); 
			pstmt.setString(7, transactionDatetime);
			pstmt.setString(8, txnUserCode);
			pstmt.setString(9, "T"); //Token
			pstmt.setString(10, extSystemRef);
			pstmt.setString(11, assetCode);
			pstmt.setString(12, "E"); //External
			try {
				NeoBankEnvironment.setComment(3,className,"Executed Sender txn_crypto_wallet_cust amount"+"TransactionCode is "+ transactionCode 
				+"systemreference is"+systemreference +"payAmount is"+amount +"currencyId is"+ currencyId+ "transactionDatetime"+ transactionDatetime);
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}					
			if (pstmt != null) pstmt.close();
			
			//**********Step 4: Record the charges to transaction ledger
			if(Double.parseDouble(customerChargesValue) > 0 ) {
													//    1        2         3           4              5                    6             7       8         9             10     		11
				query = "insert into txn_cardtoken_bc (txncode, tokenid, walletid, sysreference_ext, sysreference_int, tnmamount, merchantcode, txn_type, txndatetime, txncurrencyid, txnusercode) "
				+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?, ?,  ?) ";
						// 1  2  3  4  5  6   7	 8  9  10  11
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, transactionCode2); 			
				pstmt.setString(2, tokenId); 				
				pstmt.setString(3, assetWalletId);							
				pstmt.setString(4, systemreference);  // Additional charge for customer
				pstmt.setString(5, systemreference+"-AC");
				pstmt.setBigDecimal(6, new BigDecimal(Double.parseDouble(customerChargesValue)));
				pstmt.setString(7, ""); 
				pstmt.setString(8,  txnPayMode);
				pstmt.setString(9,  transactionDatetime );
				pstmt.setString(10,  currencyId );
				pstmt.setString(11, txnUserCode);
				try {
				pstmt.executeUpdate();
				}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
					}					
				if( pstmt!=null)	pstmt.close();	
								
				query = "select accrued_balance, sequenceid from txn_business_ledger_bc order by sequenceid desc limit 1";
				pstmt = connection.prepareStatement(query);
				rs = (ResultSet)pstmt.executeQuery();
				if(rs!=null){
					while(rs.next()){	 			 			
						 totalAccruedBalance= ( StringUtils.trim(rs.getString("accrued_balance"))  );
						} // end of while
					NeoBankEnvironment.setComment(3,className,"step 1   totalLoyaltyBalance is  "+totalAccruedBalance   );
				} 
				if(pstmt!=null) pstmt.close();	if(rs!=null) rs.close();
				
				
             //												   1		2			3			4				5				6			7			8		   9          10            11
				query = "insert into txn_business_ledger_bc (txncode, paytype, custwalletid, merchwalletid, sysreference, txnamount, txncurrencyid, txnmode, txndatetime, accrued_balance, pymtchannel) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
								// 1  2  3  4  5  6  7  8  9  10 11
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, transactionCode2); 
				pstmt.setString(2, txnPayMode);
				pstmt.setString(3, assetWalletId);
				pstmt.setString(4, "");
				pstmt.setString(5, systemreference+"-AC" );	 // Additional charges for customer						
				pstmt.setBigDecimal(6, BigDecimal.valueOf( Double.parseDouble(customerChargesValue)));  
				pstmt.setString(7, currencyId);
				pstmt.setString(8, "C"); // Credit 
				pstmt.setString(9, transactionDatetime);
				if (totalAccruedBalance!=null) {
					pstmt.setBigDecimal(10,BigDecimal.valueOf( (Double.parseDouble(totalAccruedBalance)+Double.parseDouble(customerChargesValue) )));
				}else {
					pstmt.setBigDecimal(10,BigDecimal.valueOf( Double.parseDouble(customerChargesValue) ));
				}
				pstmt.setString(11, "T"); //Token
				try {
						pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query +" "+e.getMessage());
					}					
				if( pstmt!=null) pstmt.close();
		}

			  //******** Step 7 START LOYAULTY MODULE
				String pointsConversion = null;
				String previousPoinsBalance = null;
				boolean firstime = false;
				//String pointsaccrued =null;
				 
			 query = "select pointsconversion from loyalty_rules where paymode=? and status = ? ";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, txnPayMode );
				pstmt.setString(2, "A");
				rs = (ResultSet)pstmt.executeQuery();
				 if(rs!=null){
					 	while(rs.next()){	 			 			
					 		pointsConversion = (StringUtils.trim(rs.getString("pointsconversion"))  );
					 		} // end of while
					 	} //end of if rs!=null check
				 pstmt.close();
			
				if(pointsConversion!=null) {
					
				//*** Step 7.1 Get loyalty balance for wallet
				 query = "select pointbalance balance from loyalty_points_bc where relationshipno=? and sequenceid = (select max(sequenceid) from  loyalty_points_bc where relationshipno = ? ) ";
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, relationshipNo);
					pstmt.setString(2, relationshipNo);
					rs = (ResultSet)pstmt.executeQuery();		
					 if(rs!=null){
						 	while(rs.next()){	 			 			
						 		previousPoinsBalance = (StringUtils.trim(rs.getString("balance"))  );
							NeoBankEnvironment.setComment(3,className,"The pointbalance   is " + previousPoinsBalance );
						 		
						 		} // end of while
						 	} //end of if rs!=null check
					 pstmt.close();
				 if(previousPoinsBalance==null) {
					 firstime  = true;
					 NeoBankEnvironment.setComment(3,className,"No previous record present for assetWalletId: "+assetWalletId+" for userType    "+userType);
				 }else {
					 NeoBankEnvironment.setComment(3,className,"Previus Balance for assetWalletId : "+assetWalletId+" is "+previousPoinsBalance+" for userType    "+userType);
				 }
				 
				// Step 7.2: Insert loyalty points for the sender user  
				 								//		1		2			3			   4			5			6				7          8
			  query = "insert into loyalty_points_bc (walletid, usertype, relationshipno, paymode, txnreference, pointaccrued, pointbalance, txndatetime) "
							+ "values (?, ?, ?, ?, ?, ?, ?, ?) ";
							//		   1  2  3  4  5  6  7  8
					pstmt = connection.prepareStatement(query);
					pstmt.setString(1, assetWalletId); 					
					pstmt.setString(2, userType); 		
					pstmt.setString(3, relationshipNo); // 
					pstmt.setString(4, txnPayMode );	
					pstmt.setString(5, systemreference  ); // systemreference reference generated by the system
					pstmt.setBigDecimal(6, new BigDecimal(assetAmount) );  // pointaccrued
					if(firstime) {
						pstmt.setBigDecimal(7, new BigDecimal(assetAmount)    );  // pointbalance
					}else {
						pstmt.setBigDecimal(7, BigDecimal.valueOf(Double.parseDouble(assetAmount)  + Double.parseDouble(previousPoinsBalance ))    );  // pointbalance
					}
					pstmt.setString(8,transactionDatetime );	
						try {
						pstmt.executeUpdate();
						}catch(Exception e) {
							throw new Exception (" failed query "+query+" "+e.getMessage());
					}	
				}
				//******END LOYALTY MODULE
			connection.commit();	
			result = true;
			
		}catch(Exception e){
			result = false;
			connection.rollback();
			NeoBankEnvironment.setComment(1,className,"The exception in method buyExternalCoinViaToken is  "+e.getMessage());
			throw new Exception ("The exception in method buyExternalCoinViaToken  is  "+e.getMessage());
		}finally{
		if(connection!=null)
			try {
				super.close();
			} catch (SQLException e) {
				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
			if (rs != null)	rs.close(); if (pstmt != null) pstmt.close();if(transactionDatetime!=null)  transactionDatetime=null; 
			if(userType!=null)  userType=null; if(txnUserCode != null) txnUserCode = null;
			if(systemreference!=null)  systemreference=null; if(senderRefNo!=null)  senderRefNo=null; 
			if ( totalAccruedBalance!=null) totalAccruedBalance=null;
		}
		return result;
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
				pstmt.setString(2, "F");//Fiat
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
	public Boolean buyExternalCoinViaWallet(String relationshipNo, String fiatWalletId, String amount,
			String payComments, String referenceNo, String txnUserCode, String txnPayMode, String assetCode,
			String extSystemRef, String assetWalletId, double newWalletBalanceFromStellar, String customerCharges, String assetAmount) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		boolean result = false;
		String transactionDatetime = null;
		String currencyId= assetCode;
		String fiatWalletTxnMode = "D";
		String porteWalletTxnMode = "C";
		String fiatTransactionCode = null;
		String fiatWalletTransactionCode2 = null;
		String assetWalletTransactionCode = null;
		String userType = "C";
		String systemreference =referenceNo;
		String totalAccruedBalance=null;
		try {
			String customerChargesValue = customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|"));
			String customerPayType = customerCharges.substring(0, customerCharges.indexOf(","));

			amount = amount.replaceAll(",", "");
			transactionDatetime = Utilities.getMYSQLCurrentTimeStampForInsert();
			SimpleDateFormat formatter1 = new SimpleDateFormat ("yyMMddHHmmssSSS");
			fiatTransactionCode = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
			fiatWalletTransactionCode2 = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
			assetWalletTransactionCode = formatter1.format(new java.util.Date())+Utilities.genAlphaNumRandom(9);
		    connection = super.getConnection();
		    connection.setAutoCommit(false);
		    
		    //********Step 1*********\\\\	
			 double fiatWalletDebitAmount =  Double.parseDouble(amount) + Double.parseDouble(customerChargesValue);
			
			 //*******Step 2: Update the Fiat wallet 
		     query = " update wallet_details set currbal= currbal - ?, lastupdated = ? where  walletid=? ";
			 pstmt = connection.prepareStatement(query);
			 pstmt.setBigDecimal(1,BigDecimal.valueOf(fiatWalletDebitAmount));
			 pstmt.setString(2, transactionDatetime); 
			 pstmt.setString(3, fiatWalletId); 
			 try {
				 NeoBankEnvironment.setComment(3,className,"update Fiat wallet amount" + fiatWalletDebitAmount );
				 pstmt.executeUpdate();
				 }catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
			 }					
			 if( pstmt!=null)	pstmt.close();
			 double receiverrCreditAmount =  Double.parseDouble(assetAmount); //Remove this after integration just update balance from stellar api
		
			 // *******Step 3: Update the Crypto asset coin wallet 
			query = " update wallet_details set currbal= currbal + ?, lastupdated = ? where  walletid=? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setBigDecimal(1, BigDecimal.valueOf( receiverrCreditAmount));
			pstmt.setString(2, transactionDatetime); 
			pstmt.setString(3, assetWalletId);
			try {
				NeoBankEnvironment.setComment(3,className,"update receiver wallet amount" + receiverrCreditAmount );
				pstmt.executeUpdate();
				}catch(Exception e) {
					throw new Exception (" failed query "+query+" "+e.getMessage());
				}					
			if( pstmt!=null)	pstmt.close();
			
			// **********Step 5: Record the wallet transaction ledger
			
													// 1		2			3			4			  5			  6		     7			  8            9
			query = "insert into txn_wallet_cust_bc (txncode, walletid, sysreference, txnamount, txncurrencyid, txnmode, txndatetime, txnusercode, pymtchannel) "
			+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?) ";
					// 1  2  3  4  5  6   7  8  9
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, fiatTransactionCode); 			
			pstmt.setString(2, fiatWalletId); 				
			pstmt.setString(3, systemreference );							
			pstmt.setBigDecimal(4, new BigDecimal(amount));  
			pstmt.setString(5, currencyId);
			pstmt.setString(6, fiatWalletTxnMode); // Debit as it is a payment by the sender
			pstmt.setString(7, transactionDatetime);
			pstmt.setString(8, txnUserCode);
			pstmt.setString(9, "W");
			try {
				NeoBankEnvironment.setComment(3,className,"Executed Sender wallet_tnx_bc amount"+"fiatTransactionCode is"+ fiatTransactionCode +"fiatWalletId is"+fiatWalletId 
				+"systemreference is"+systemreference +"payAmount is"+amount +"currencyId is"+ currencyId+ "transactionDatetime"+ transactionDatetime);
			pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception (" failed query "+query+" "+e.getMessage());
			}					
			if( pstmt!=null)	pstmt.close();

						// 1		2			3			4			5				  6	    	7			8              9             10             11          12
			query = "insert into txn_crypto_wallet_cust (txncode, walletid, sysreference_int, txnamount, txncurrencyid, txnmode, txndatetime, txnusercode, pymtchannel, sysreference_ext, assetcode, asset_type) "
			+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?,  ? ) ";
			// 1  2  3  4  5  6  7  8  9  10  11  12
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, assetWalletTransactionCode); 			
			pstmt.setString(2, assetWalletId); 				
			pstmt.setString(3, systemreference );							
			pstmt.setBigDecimal(4, new BigDecimal(assetAmount));  
			pstmt.setString(5, currencyId);
			pstmt.setString(6, "C"); 
			pstmt.setString(7, transactionDatetime);
			pstmt.setString(8, txnUserCode);
			pstmt.setString(9, "W"); //Wallet
			pstmt.setString(10, extSystemRef);
			pstmt.setString(11, assetCode);
			pstmt.setString(12, "E"); //External
			try {
			NeoBankEnvironment.setComment(3,className,"Executed Sender txn_crypto_wallet_cust amount"+"assetWalletTransactionCode is "+ assetWalletTransactionCode 
			+"systemreference is"+systemreference +"payAmount is"+amount +"currencyId is"+ currencyId+ "transactionDatetime"+ transactionDatetime);
			pstmt.executeUpdate();
			}catch(Exception e) {
			throw new Exception (" failed query "+query+" "+e.getMessage());
			}					
			if (pstmt != null) pstmt.close();
			
			if(Double.parseDouble(customerChargesValue)>0) {
				query = "insert into txn_wallet_cust_bc (txncode, walletid, sysreference, txnamount, txncurrencyid, txnmode, txndatetime, txnusercode, pymtchannel) "
						+ "values (?, ?, ?, ?, ?, ?,  ?, ?, ?) ";
								// 1  2  3  4  5  6   7  8
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, fiatWalletTransactionCode2); 			
				pstmt.setString(2, fiatWalletId); 				
				pstmt.setString(3, systemreference+"-AC" );							
				pstmt.setBigDecimal(4, new BigDecimal(Double.parseDouble(customerChargesValue)));  
				pstmt.setString(5, currencyId);
				pstmt.setString(6, customerPayType); 
				pstmt.setString(7, transactionDatetime);
				pstmt.setString(8, txnUserCode);
				pstmt.setString(9, "W");
				try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
					}					
					pstmt.close();	
				if(pstmt!=null)			
					pstmt=null;					
				
				//Select the balance to be updated. 	
				query = "select accrued_balance, sequenceid from txn_business_ledger_bc order by sequenceid desc limit 1";
				pstmt = connection.prepareStatement(query);
				rs = (ResultSet)pstmt.executeQuery();
				if(rs!=null){
					while(rs.next()){	 			 			
						 totalAccruedBalance= ( StringUtils.trim(rs.getString("accrued_balance"))  );
						} // end of while
					NeoBankEnvironment.setComment(3,className,"step 1   totalLoyaltyBalance is  "+totalAccruedBalance   );
				} 
				if(pstmt!=null) pstmt.close();	if(rs!=null) rs.close();
				
             //												   1		2			3			4				5				6			7			8		   9          10            11
				query = "insert into txn_business_ledger_bc (txncode, paytype, custwalletid, merchwalletid, sysreference, txnamount, txncurrencyid, txnmode, txndatetime, accrued_balance, pymtchannel) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
								// 1  2  3  4  5  6  7  8  9  10 11
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, fiatWalletTransactionCode2); 
				pstmt.setString(2, txnPayMode);
				pstmt.setString(3, fiatWalletId);
				pstmt.setString(4, "");
				pstmt.setString(5, systemreference+"-AC" );			// Additional charges for customer						
				pstmt.setBigDecimal(6, BigDecimal.valueOf( Double.parseDouble(customerChargesValue)));  
				pstmt.setString(7, currencyId);
				pstmt.setString(8, "C"); // Credit 
				pstmt.setString(9, transactionDatetime);
				
				if (totalAccruedBalance!=null) {
					pstmt.setBigDecimal(10,BigDecimal.valueOf( (Double.parseDouble(totalAccruedBalance)+Double.parseDouble(customerChargesValue) )));
				}else {
					pstmt.setBigDecimal(10,BigDecimal.valueOf( Double.parseDouble(customerChargesValue) ));
				}
				pstmt.setString(11, "W");
				try {
						pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query +" "+e.getMessage());
					}					
				if( pstmt!=null)pstmt.close();
			}
			
			//******** Step 7 START LOYAULTY MODULE
			String pointsConversion = null;
			String previousPoinsBalance = null;
			boolean firstime = false;
			//String pointsaccrued =null;
			 
		 query = "select pointsconversion from loyalty_rules where paymode=? and status = ? ";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, txnPayMode );
			pstmt.setString(2, "A");
			rs = (ResultSet)pstmt.executeQuery();
			 if(rs!=null){
				 	while(rs.next()){	 			 			
				 		pointsConversion = (StringUtils.trim(rs.getString("pointsconversion"))  );
				 		} // end of while
				 	} //end of if rs!=null check
			 pstmt.close();
		
			if(pointsConversion!=null) {
				
			//*** Step 7.1 Get loyalty balance for wallet
			 query = "select pointbalance balance from loyalty_points_bc where relationshipno=? and sequenceid = (select max(sequenceid) from  loyalty_points_bc where relationshipno = ? ) ";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, relationshipNo);
				pstmt.setString(2, relationshipNo);
				rs = (ResultSet)pstmt.executeQuery();		
				 if(rs!=null){
					 	while(rs.next()){	 			 			
					 		previousPoinsBalance = (StringUtils.trim(rs.getString("balance"))  );
						NeoBankEnvironment.setComment(3,className,"The pointbalance   is " + previousPoinsBalance );
					 		
					 		} // end of while
					 	} //end of if rs!=null check
				 pstmt.close();
			 if(previousPoinsBalance==null) {
				 firstime  = true;
				 NeoBankEnvironment.setComment(3,className,"No previous record present for assetWalletId: "+assetWalletId+" for userType    "+userType);
			 }else {
				 NeoBankEnvironment.setComment(3,className,"Previus Balance for assetWalletId : "+assetWalletId+" is "+previousPoinsBalance+" for userType    "+userType);
			 }
			 
			// Step 7.2: Insert loyalty points for the sender user  
			 								//		1		2			3			   4			5			6				7          8
		  query = "insert into loyalty_points_bc (walletid, usertype, relationshipno, paymode, txnreference, pointaccrued, pointbalance, txndatetime) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?) ";
						//		   1  2  3  4  5  6  7  8
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, assetWalletId); 					
				pstmt.setString(2, userType); 		
				pstmt.setString(3, relationshipNo); // 
				pstmt.setString(4, txnPayMode );	
				pstmt.setString(5, systemreference  ); // systemreference reference generated by the system
				pstmt.setBigDecimal(6, new BigDecimal(assetAmount) );  // pointaccrued
				if(firstime) {
					pstmt.setBigDecimal(7, new BigDecimal(assetAmount)    );  // pointbalance
				}else {
					pstmt.setBigDecimal(7, BigDecimal.valueOf(Double.parseDouble(assetAmount)  + Double.parseDouble(previousPoinsBalance ))    );  // pointbalance
				}
				pstmt.setString(8,transactionDatetime );	
					try {
					pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception (" failed query "+query+" "+e.getMessage());
				}	
			}
			//******END LOYALTY MODULE

			connection.commit();	
			result = true;			
		}catch(Exception e){
			result = false;
			connection.rollback();
			NeoBankEnvironment.setComment(1,className,"The exception in method buyExternalCoinViaWallet  is  "+e.getMessage());
			throw new Exception ("The exception in method buyExternalCoinViaWallet  is  "+e.getMessage());
		}finally{
		if(connection!=null)
			try {
				super.close();
			} catch (SQLException e) {
				NeoBankEnvironment.setComment(1,className,"SQL Exception is  "+e.getMessage());
			}
	
				if (rs != null)	rs.close(); if (pstmt != null) pstmt.close();if(transactionDatetime!=null)  transactionDatetime=null; if(assetWalletTransactionCode!=null)  assetWalletTransactionCode=null;
				if(userType!=null)  userType=null; if(fiatTransactionCode!=null)  fiatTransactionCode=null; if(fiatWalletTransactionCode2!=null)  fiatWalletTransactionCode2=null; if(txnUserCode != null) txnUserCode = null;
				if(systemreference!=null)  systemreference=null; if(currencyId!=null)  currencyId=null; if ( totalAccruedBalance!=null) totalAccruedBalance=null;
				if(porteWalletTxnMode!=null)  porteWalletTxnMode=null; if ( fiatWalletTxnMode!=null) fiatWalletTxnMode=null;
		
			}
		
		return result;
		
	}

	
}
