package com.pporte.rules;

import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.stellar.sdk.KeyPair;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.BitcoinDao;
import com.pporte.dao.CustomerCoinsDao;
import com.pporte.dao.CustomerDao;
import com.pporte.dao.CustomerDigitalAssetsDao;
import com.pporte.dao.CustomerPorteCoinDao;
import com.pporte.dao.CustomerWalletDao;
import com.pporte.dao.SystemUtilsDao;
import com.pporte.dao.WalletAuthorizationDao;
import com.pporte.model.AssetAccount;
import com.pporte.model.BTCTransction;
import com.pporte.model.CryptoAssetCoins;
import com.pporte.model.Transaction;
import com.pporte.model.User;
import com.pporte.utilities.Bip39Utility;
import com.pporte.utilities.BitcoinUtility;
import com.pporte.utilities.StellarSDKUtility;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CustomerBitcoinRulesImpl implements Rules {
	private static String className = CustomerBitcoinRulesImpl.class.getSimpleName();
	@Override
	public void performJSONOperation(String arg0, HttpServletRequest arg1, HttpServletResponse arg2,
			ServletContext arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		HttpSession session = request.getSession(false); 
		switch (rulesaction) {
		/**
		 * This Rule will also cater Stellar Since We are now generating one Seed Phase
		 */
		case "create_bitcoin_mnemocic_code":
			try {
				String hasAccount = null; User user = null;
				JsonObject obj = new JsonObject();
				String relationshipNo = null; PrintWriter output = null; 
				JsonObject data = new JsonObject(); Gson gson = new Gson();
				if(request.getParameter("has_account")!=null)hasAccount = request.getParameter("has_account").trim();
				if (session.getAttribute("SESS_USER") == null) {
					Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
					return;
				}
				
				user = (User) session.getAttribute("SESS_USER");
				relationshipNo = user.getRelationshipNo();
				// Check if has already linked his account to BTC and Stellar using our system.
				if(BitcoinDao.class.getConstructor().newInstance().checkIfBitcoinHasBeenLinkedByCustomer(relationshipNo)) {
					Utilities.sendJsonResponse(response, "error", "You have already linked your Wallets in our system");
					return;
				}
				NeoBankEnvironment.setComment(3, className, "hasAccount is "+hasAccount);
				if(!Boolean.parseBoolean(hasAccount)) {
					data = BitcoinUtility.createBitcoinDeterministicWallet();
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "CCB","Bitcoin seed mnemonic code generated" );
					obj.addProperty("error", "false");
					obj.add("data", gson.toJsonTree(data));
				}else {
					obj.addProperty("error", "true");
					obj.addProperty("message", "Error in creating mnemonic code");
				}
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
					output = response.getWriter();
					output.print(obj);
				} finally {
					if(user!=null) user=null; if(data!=null) data=null; if(gson!=null)gson=null;
					if(obj!=null) obj=null; if(hasAccount!=null) hasAccount=null;
					if(output!=null) output.close();
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error In creating bitcoin mneumonic code, Please try again letter");
			}
			break;
		case "check_if_digital_assets_are_created":
			try {
				String relationshipNo = null;JsonObject obj = new JsonObject(); PrintWriter output = null;
				String tokenizedMnemonicFlag =null; KeyPair keyPair = null;
				String mnemoniStringFromDB = null; String masterKeyGenerationBTCResult = null;
				long creationtime = 0;
				if(request.getParameter("relno")!=null)relationshipNo = request.getParameter("relno").trim();
				tokenizedMnemonicFlag = (String) CustomerCoinsDao.class.getConstructor().newInstance().checkIfCustomerHasAddedMnemonic(relationshipNo);
				if(tokenizedMnemonicFlag==null) {
					obj.addProperty("error","false");
					obj.addProperty("process_status","N"); //N-Not Complete C-Complete
				}else if (tokenizedMnemonicFlag.equals("Y")) {
					obj.addProperty("error","false");
					obj.addProperty("mnemonic_flag",tokenizedMnemonicFlag);
					obj.addProperty("process_status","C"); //N-Not Complete C-Complete
					mnemoniStringFromDB = (String) CustomerDao.class.getConstructor().newInstance()
							.getmnemonicCode(relationshipNo);
					//Get keys from bit 39
					keyPair= Bip39Utility.masterKeyGeneration(mnemoniStringFromDB.replaceAll(",", " "));
					if(keyPair==null) {
						throw new Exception("Error in getting keys");
					}
					masterKeyGenerationBTCResult = BitcoinUtility.bitcoinMasterKeyGenerationFromSeed(
							mnemoniStringFromDB.replaceAll(",", " "), creationtime);
					NeoBankEnvironment.setComment(3, className,"Step 3 "+masterKeyGenerationBTCResult);
					obj.addProperty("public_key", keyPair.getAccountId());
					obj.addProperty("private_key", String.valueOf(keyPair.getSecretSeed()));
					obj.addProperty("btc_public_key", masterKeyGenerationBTCResult.split(",")[2]);
					obj.addProperty("btc_private_key",  masterKeyGenerationBTCResult.split(",")[1]);
					obj.addProperty("btc_address", masterKeyGenerationBTCResult.split(",")[0]);
				}else if (tokenizedMnemonicFlag.equals("N")) {
					//
					obj.addProperty("error","false");
					obj.addProperty("process_status","C"); //N-Not Complete C-Complete
					obj.addProperty("mnemonic_flag",tokenizedMnemonicFlag);
				}
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + (obj.toString()));
					output = response.getWriter();
					output.print((obj));
				} finally {
					if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; 
					if (mnemoniStringFromDB != null)mnemoniStringFromDB = null; if (obj != null) obj = null;
					if (keyPair != null) keyPair = null;  if (masterKeyGenerationBTCResult != null)masterKeyGenerationBTCResult = null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error In checking if process is complete, Please try again letter");
			}
			
			
			break;
			
		case "add_bitcoin_new_account":
		    try {
		        String hasAccount = null; User user = null;
		        JsonObject obj = new JsonObject();
		        boolean success = false; String relationshipNo = null;
		        PrintWriter output = null;String password=null;String mnemonicCode=null;
		        String encryptedMnemonic=null; long btcCreationTime = 0;
		        String btcAddress = null; String btcPubKey = null; String btcPivKey = null;
		        JsonArray mnemonicArray = new JsonArray();
		        String mnemonicString = null; String assetCode = NeoBankEnvironment.getXLMCode();
		        String data = null; JsonObject objBtcData = new JsonObject(); JsonArray jsonArrayMnemonic = null;
		        String veslAssetCode= NeoBankEnvironment.getVesselCoinCode();String usdcAssetCode=NeoBankEnvironment.getUSDCCode();
		        String porteAssetCode=NeoBankEnvironment.getPorteTokenCode();String issuerPorteAccountId = null;
		        String limit = null;int baseFee = org.stellar.sdk.Transaction.MIN_BASE_FEE;
		        String issuerVeslAccountId=null;String issuerUsdcAccountId=null;boolean createTrustline = false;
		        String issuerBtcxAccountId=null;String btcxAssetCode=NeoBankEnvironment.getStellarBTCxCode();
		        String xlmBalance=null;ArrayList<AssetAccount> accountBalance=null;String res=null;
		        StopWatch timer = new StopWatch();
		        timer.start();
		            if (session.getAttribute("SESS_USER") == null) {
		                Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
		                return;
		            }

		            if(request.getParameter("has_account")!=null)hasAccount = request.getParameter("has_account").trim();
		            if(request.getParameter("security")!=null)password = request.getParameter("security").trim();
		            if(request.getParameter("data")!=null)data = request.getParameter("data").trim();
		            user = (User) session.getAttribute("SESS_USER");
		            relationshipNo = user.getRelationshipNo();
		            boolean passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance().checkIfPasswordIsCorrect(relationshipNo, password);
		                if(!passIsCorrect) {
		                NeoBankEnvironment.setComment(1, className, "Password is not correct");
		                Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
		                return;
		                }

		            objBtcData = new Gson().fromJson(data, JsonObject.class);
		            btcCreationTime = objBtcData.get("creation_time").getAsLong();
		            btcAddress = objBtcData.get("address").getAsString();
		            btcPubKey = objBtcData.get("public_key").getAsString();
		            btcPivKey = objBtcData.get("private_key").getAsString();
		            mnemonicArray = objBtcData.get("mnemonic_code").getAsJsonArray();
		            mnemonicString = Bip39Utility.createCSVForMnemonic(mnemonicArray);

		            // Check if has already linked his account to BTC and Stellar using our system.
		            encryptedMnemonic= Utilities.tripleEncryptData(mnemonicString);

		            if(BitcoinDao.class.getConstructor().newInstance().checkIfBitcoinHasBeenLinkedByCustomer(relationshipNo)) {
		                Utilities.sendJsonResponse(response, "error", "You have already linked your Wallets in our system");
		                return;
		            }
		            /**
		            * Pass mnemonic code generated by BitcoinJ to Stellar Key Pair Generator here
		            */

		            mnemonicString = mnemonicString.replaceAll(",", " ");
		            NeoBankEnvironment.setComment(3, className, "mnemonicString "+mnemonicString);
		            char [] mnemonicCharArray = mnemonicString.toCharArray();
		            KeyPair keyPair = Bip39Utility.generateKeyPairs(mnemonicCharArray);

		            // Fund account from Friendbot
		            // TODO Disable this in production
		            StellarSDKUtility.createAccount(keyPair);

		            //Insert To BTC mnemonic
		            boolean result = (boolean)BitcoinDao.class.getConstructor().newInstance().insertMnemonicCode(relationshipNo, encryptedMnemonic);
		            if(!result)
		                throw new Exception ("Unable to insert BTC mnemonic code");
		            //Insert To Stellar mnemonic
		            result = (boolean)CustomerCoinsDao.class.getConstructor().newInstance().insertMnemonicCode(relationshipNo, encryptedMnemonic);
		            if(!result)
		                throw new Exception ("Unable to insert Stellar mnemonic code");
		            //Insert To Stellar mnemonic
		            success = (boolean) BitcoinDao.class.getConstructor().newInstance().registerBitcoinAccount(Utilities.tripleEncryptData(btcPubKey),
		            Utilities.tripleEncryptData(btcAddress), relationshipNo, "Y", btcCreationTime);

		            if(!success)
		                throw new Exception ("Unable to insert BTC Adress code");
		            //Fund Accounts
		            //Step 1 - get partner keys
		            /****/
		            AssetAccount xlmFundAccount =(AssetAccount)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getActiveXLMAccounts();

		                //Step 2: Check Threshhold
		                NeoBankEnvironment.setComment(3,className,"Checking threshold");

		                KeyPair accounts = KeyPair.fromAccountId((xlmFundAccount.getPublicKey()));
		                accountBalance=StellarSDKUtility.getAccountBalance(accounts);
		                NeoBankEnvironment.setComment(3, className, "Checking balance");
		                for(int i=0; i<accountBalance.size(); i++) {
					    	if(accountBalance.get(i).getAssetCode().equals("XLM")) {
					    		xlmBalance = accountBalance.get(i).getAssetBalance();
					    		NeoBankEnvironment.setComment(3, className, "xlmBalance "+xlmBalance);
		                		if(Float.parseFloat(xlmBalance)<= Float.parseFloat(NeoBankEnvironment.getXLMFundAccThreshold().toString())) {
		                			//TODO Sent email to TDA to fund account
		                		}
					    	}
					    		
					    }
		                
		                
		                
		            NeoBankEnvironment.setComment(3, className, "after checking threshold");
		            res=StellarSDKUtility.sendNativeCoinPayment(keyPair.getAccountId(),
		            Utilities.tripleDecryptData(xlmFundAccount.getPrivateKey()),NeoBankEnvironment.getXLMFundAccountAmount());
		            if(res.equals("success")) {
		                NeoBankEnvironment.setComment(3,className,"After success ");
		                success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerAndFundStellarAccount(Utilities.tripleEncryptData(keyPair.getAccountId())
		                    , "", relationshipNo, assetCode,"Y","A");

		            if(!success)
		                throw new Exception ("Unable to insert Stellar Details");
		            //Create Wallets
		            issuerPorteAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(porteAssetCode);
		            issuerVeslAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(veslAssetCode);
		            issuerUsdcAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(usdcAssetCode);
					issuerBtcxAccountId=(String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(btcxAssetCode);
		            limit = NeoBankEnvironment.getMaxStellarAssetWalletLimit();

		            //PORTE Wallet
		            createTrustline = StellarSDKUtility.createTrustline(issuerPorteAccountId, String.valueOf( keyPair.getSecretSeed()),
		            baseFee, limit, NeoBankEnvironment.getPorteTokenCode() );
		            if(createTrustline) {
		            success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
		            relationshipNo, "Porte Wallet",keyPair.getAccountId(), porteAssetCode);
		            createTrustline=false;
		            }
		            //VESL Wallet
		            createTrustline = StellarSDKUtility.createTrustline(issuerVeslAccountId, String.valueOf( keyPair.getSecretSeed()),
		                baseFee, limit, NeoBankEnvironment.getVesselCoinCode() );
		            if(createTrustline) {
		                    success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
		                            relationshipNo, "VESL Wallet",keyPair.getAccountId(), veslAssetCode);
		                                createTrustline=false;
		                }
		            //USDC Wallet
		            createTrustline = StellarSDKUtility.createTrustline(issuerUsdcAccountId, String.valueOf( keyPair.getSecretSeed()),
		                baseFee, limit, NeoBankEnvironment.getUSDCCode());
		            if(createTrustline) {
		                    success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
		                        relationshipNo, "USDC Wallet",keyPair.getAccountId(), usdcAssetCode);
		            createTrustline=false;
		            }//Btcx Wallet
					createTrustline = StellarSDKUtility.createTrustline(issuerBtcxAccountId, String.valueOf( keyPair.getSecretSeed()),
							 baseFee, limit,  NeoBankEnvironment.getBitcoinCode());
					if(createTrustline) {
						success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
								relationshipNo, "BTCx Wallet",keyPair.getAccountId(), btcxAssetCode);
						createTrustline=false;
					}else {
		                throw new Exception("Error in creating Trustline");
		        }

		        }else {
		            //Create a queue for unfunded accounts
		            success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerAndFundStellarAccount(Utilities.tripleEncryptData(keyPair.getAccountId())
		                , "", relationshipNo, assetCode,"Y","P");
		        }
		        
		            //CBA - Customer Register
		            SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "CRB","Customer created new BTC and Stellar account" );
		            obj.addProperty("error", "false");
		            obj.addProperty("message", "Wallet is set up successfully");
		            obj.addProperty("btc_pub_key", btcPubKey);
		            obj.addProperty("btc_piv_key", btcPivKey);
		            obj.addProperty("btc_address", btcAddress);
		            obj.addProperty("stellar_pub_key", keyPair.getAccountId());
		            obj.addProperty("stellar_piv_key", String.valueOf( keyPair.getSecretSeed()));
		        timer.stop();
		        NeoBankEnvironment.setComment(1, className, " time elapsed is"+timer.getTime());
		        try {
		            NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
		            output = response.getWriter();
		            output.print(obj);
		        } finally {
		            if(user!=null) user=null; if(hasAccount!=null) hasAccount=null;
		            if(btcAddress!=null) btcAddress=null; if(obj!=null) obj=null;if(mnemonicArray!=null) mnemonicArray=null;
		            if(btcPubKey!=null) btcPubKey=null;if(output!=null) output.close();
		            if(btcPivKey!=null) btcPivKey=null;if(mnemonicCode!=null) mnemonicCode=null;
		            if(encryptedMnemonic!=null) encryptedMnemonic=null;if(mnemonicString!=null) mnemonicString=null;
		            if(relationshipNo!=null) relationshipNo=null;if(data!=null) data=null;
		            if(objBtcData!=null) objBtcData=null;if(jsonArrayMnemonic!=null) jsonArrayMnemonic=null;
		            if(mnemonicCharArray!=null) mnemonicCharArray = null;
		            if(keyPair!=null) keyPair = null; if(assetCode!=null) assetCode = null;
		            if(limit!=null) limit=null; if(res!=null) res=null; 
					if(xlmBalance!=null) xlmBalance=null; if(issuerPorteAccountId!=null) issuerPorteAccountId=null;if(issuerVeslAccountId!=null) issuerVeslAccountId=null; 
					if(issuerUsdcAccountId!=null) issuerUsdcAccountId=null; if(issuerBtcxAccountId!=null) issuerBtcxAccountId=null;if(accountBalance!=null) accountBalance=null;
		        }

		        } catch (Exception e) {
		        NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
		        Utilities.sendJsonResponse(response, "error", "Error In creating Bitcoin account, Please try again letter");
		        }
		    break;
			
		case "link_btc_without_mnemonic":
			try {
				String btcAddress = null; String btcPublicKey = null; String password = null;
				String relationshipNo = null; User user = null; long btcCreationTime = 0;
				JsonObject obj = new JsonObject(); PrintWriter output = null;
				String publicKey = null;String privateKey=null;
				String veslAssetCode= NeoBankEnvironment.getVesselCoinCode();String usdcAssetCode=NeoBankEnvironment.getUSDCCode();
				String porteAssetCode=NeoBankEnvironment.getPorteTokenCode();String issuerPorteAccountId = null; 
				String limit = null;int baseFee  = org.stellar.sdk.Transaction.MIN_BASE_FEE;
				String issuerVeslAccountId=null;String issuerUsdcAccountId=null;boolean createTrustline = false;
				String issuerBtcxAccountId=null;String btcxAssetCode=NeoBankEnvironment.getStellarBTCxCode();
				 String xlmBalance=null;ArrayList<AssetAccount> accountBalance=null;String res=null;
				if (session.getAttribute("SESS_USER") == null) {
					Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
					return;
				}
				
				if(request.getParameter("btc_public_key")!=null)btcPublicKey = request.getParameter("btc_public_key").trim();
				if(request.getParameter("btc_address")!=null)btcAddress = request.getParameter("btc_address").trim();
				if(request.getParameter("security")!=null)password = request.getParameter("security").trim();
				if(request.getParameter("stellar_public_key")!=null)publicKey = request.getParameter("stellar_public_key").trim();
				if(request.getParameter("stellar_private_key")!=null)privateKey = request.getParameter("stellar_private_key").trim();
				user = (User) session.getAttribute("SESS_USER");
				relationshipNo = user.getRelationshipNo();
				boolean passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
						.checkIfPasswordIsCorrect(relationshipNo, password);
				if(!passIsCorrect) {
					NeoBankEnvironment.setComment(1, className, "Password is not correct");
					Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
					return;
				}
				//TODO Check if we have an api if BTC address exist
				//Check if customer has linked their BTC account
				if(BitcoinDao.class.getConstructor().newInstance().checkIfBitcoinHasBeenLinkedByCustomer(relationshipNo)) {
					Utilities.sendJsonResponse(response, "error", "You have already linked your BTC account in our system");
					return;
				}				
				boolean success = (Boolean) BitcoinDao.class.getConstructor().newInstance().registerBitcoinAccount(Utilities.tripleEncryptData(btcPublicKey), 
						Utilities.tripleEncryptData(btcAddress), relationshipNo, "N", btcCreationTime);
				if(!success) 
					throw new Exception ("Unable to insert BTC Details");
				
				KeyPair keyPair =  KeyPair.fromAccountId(publicKey);
				boolean accountExist = StellarSDKUtility.CheckAccountIfExist(keyPair);
				if(!accountExist) {
					Utilities.sendJsonResponse(response, "error", " Account Does not exist in stellar ");
					return;
				}
				//Fund Accounts
				//Step 1 - get partner keys
	            AssetAccount xlmFundAccount =(AssetAccount)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getActiveXLMAccounts();
					//Step 2: Check Threshhold

		                KeyPair accounts = KeyPair.fromAccountId((xlmFundAccount.getPublicKey()));
		                accountBalance=StellarSDKUtility.getAccountBalance(accounts);
		                NeoBankEnvironment.setComment(3, className, "Checking balance");
		                for(int i=0; i<accountBalance.size(); i++) {
					    	if(accountBalance.get(i).getAssetCode().equals("XLM")) {
					    		xlmBalance = accountBalance.get(i).getAssetBalance();
					    		NeoBankEnvironment.setComment(3, className, "xlmBalance "+xlmBalance);
		                		if(Float.parseFloat(xlmBalance)<= Float.parseFloat(NeoBankEnvironment.getXLMFundAccThreshold().toString())) {
		                			//TODO Sent email to TDA to fund account
		                		}
					    	}
					    		
					    }
					 res=StellarSDKUtility.sendNativeCoinPayment(publicKey, 
							Utilities.tripleDecryptData(xlmFundAccount.getPrivateKey()),NeoBankEnvironment.getXLMFundAccountAmount());
					if(res.equals("success")) {
						success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerAndFundStellarAccount(Utilities.tripleEncryptData(keyPair.getAccountId())
								, "", relationshipNo, NeoBankEnvironment.getXLMCode(),"N","A" );
						if(!success) 
							throw new Exception ("Unable to insert Stellar Details");
						//Create Wallets
						issuerPorteAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(porteAssetCode);
						issuerVeslAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(veslAssetCode);
						issuerUsdcAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(usdcAssetCode);
						issuerBtcxAccountId=(String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(btcxAssetCode);

						limit = NeoBankEnvironment.getMaxStellarAssetWalletLimit();
						
						//PORTE Wallet
						
						createTrustline = StellarSDKUtility.createTrustline(issuerPorteAccountId, privateKey,
								 baseFee, limit,  NeoBankEnvironment.getPorteTokenCode() );
						if(createTrustline) {
							success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
									relationshipNo, "Porte Wallet",publicKey, porteAssetCode);
							createTrustline=false;
						}
						//VESL Wallet 
						createTrustline = StellarSDKUtility.createTrustline(issuerVeslAccountId,privateKey,
								 baseFee, limit,  NeoBankEnvironment.getVesselCoinCode() );
						if(createTrustline) {
							success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
									relationshipNo, "VESL Wallet",publicKey, veslAssetCode);
							createTrustline=false;
						}
						//USDC Wallet 
						createTrustline = StellarSDKUtility.createTrustline(issuerUsdcAccountId, privateKey,
								 baseFee, limit,  NeoBankEnvironment.getUSDCCode() );
						if(createTrustline) {
							success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
									relationshipNo, "USDC Wallet",publicKey, usdcAssetCode);
							createTrustline=false;
						}
						//Btcx Wallet
						createTrustline = StellarSDKUtility.createTrustline(issuerBtcxAccountId,privateKey,
								 baseFee, limit,  NeoBankEnvironment.getBitcoinCode());
						if(createTrustline) {
							success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
									relationshipNo, "BTCx Wallet",publicKey, btcxAssetCode);
							createTrustline=false;
						}else {
							throw new Exception("Error in creating Trustline");
						}
					}else {
						//Create a queue for unfunded accounts
						success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerAndFundStellarAccount(Utilities.tripleEncryptData(publicKey)
								, "", relationshipNo, NeoBankEnvironment.getXLMCode(),"N","P");
						if(!success) 
							throw new Exception ("Unable to insert Stellar Details");
					}
				
				//CRB - Customer Register Bitcoin
				SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "CRB","Customer  link BTC and Stellar account without Seed Code" );
				obj.addProperty("error", "false");
				obj.addProperty("message", "You have successfully linked Bitcoin and Stellar Accounts");
			try {
				NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
				output = response.getWriter();
				output.print(obj);
			} finally {
				if(output!=null) output.close();if(user!=null) user=null;if(relationshipNo!=null) relationshipNo=null;
				if(btcPublicKey!=null) btcPublicKey=null;if(password!=null) password=null;if(obj!=null) obj=null;
				if(keyPair!=null) keyPair=null;if(publicKey!=null) publicKey=null;
				if(limit!=null) limit=null; if(res!=null) res=null; 
				if(xlmBalance!=null) xlmBalance=null; if(issuerPorteAccountId!=null) issuerPorteAccountId=null;if(issuerVeslAccountId!=null) issuerVeslAccountId=null; 
				if(issuerUsdcAccountId!=null) issuerUsdcAccountId=null; if(issuerBtcxAccountId!=null) issuerBtcxAccountId=null;if(accountBalance!=null) accountBalance=null;
			}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error In linking Bitcoin account, Please try again letter");
			}
			
			break;
		case "link_account_with_mnemonic_code":
			try {
				
				User user = null; 
				String btcPublicKey = null; String btcAddress = null; 
				JsonObject obj = new JsonObject(); String encryptedMnemonic = null;
				String relationshipNo = null;String password=null;
				PrintWriter output = null; String firstMnemonic=null;
				String thirdMnemonic=null;String secMnemonic=null;String fourhMnemonic=null;String fifthMnemonic=null;
				String sixthMnemonic=null;String seventhMnemonic=null;String eithMnemonic=null;String nineMnemonic=null;
				String tenMnemonic=null;String eleventMnemonic=null;String twelveMnemonic=null;
			    long btcCreationTime = 0; boolean success = false; String assetCode = NeoBankEnvironment.getXLMCode();
			    String xlmBalance=null;ArrayList<AssetAccount> accountBalance=null;String res=null;
				String btcAddressFromSeed = null; String btcPubKeyFromSeed = null;
				
				String veslAssetCode= NeoBankEnvironment.getVesselCoinCode();String usdcAssetCode=NeoBankEnvironment.getUSDCCode();
				String porteAssetCode=NeoBankEnvironment.getPorteTokenCode();String issuerPorteAccountId = null; 
				String limit = null;int baseFee  = org.stellar.sdk.Transaction.MIN_BASE_FEE;
				String issuerVeslAccountId=null;String issuerUsdcAccountId=null;boolean createTrustline = false;
				String issuerBtcxAccountId=null;String btcxAssetCode=NeoBankEnvironment.getStellarBTCxCode();
				if (session.getAttribute("SESS_USER") == null) {
					Utilities.sendSessionExpiredResponse(response, "error", "Session has expired, please log in again");
					return;
				}
				
				if(request.getParameter("first_code")!=null)firstMnemonic = request.getParameter("first_code").trim();
				if(request.getParameter("second_code")!=null)secMnemonic = request.getParameter("second_code").trim();
				if(request.getParameter("third_code")!=null)thirdMnemonic = request.getParameter("third_code").trim();
				if(request.getParameter("fourth_code")!=null)fourhMnemonic = request.getParameter("fourth_code").trim();
				if(request.getParameter("fifth_code")!=null)fifthMnemonic = request.getParameter("fifth_code").trim();
				if(request.getParameter("sixth_code")!=null)sixthMnemonic = request.getParameter("sixth_code").trim();
				if(request.getParameter("seventh_code")!=null)seventhMnemonic = request.getParameter("seventh_code").trim();
				if(request.getParameter("eight_code")!=null)eithMnemonic = request.getParameter("eight_code").trim();
				if(request.getParameter("nineth_code")!=null)nineMnemonic = request.getParameter("nineth_code").trim();
				if(request.getParameter("tenth_code")!=null)tenMnemonic = request.getParameter("tenth_code").trim();
				if(request.getParameter("eleventh_code")!=null)eleventMnemonic = request.getParameter("eleventh_code").trim();
				if(request.getParameter("twelve_code")!=null)twelveMnemonic = request.getParameter("twelve_code").trim();
				if(request.getParameter("input_btc_pubkey")!=null)btcPublicKey = request.getParameter("input_btc_pubkey").trim();
				if(request.getParameter("input_btc_address")!=null)btcAddress = request.getParameter("input_btc_address").trim();
				if(request.getParameter("security")!=null)password = request.getParameter("security").trim();
				
				
				user = (User) session.getAttribute("SESS_USER");
				relationshipNo = user.getRelationshipNo();
				boolean passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
						.checkIfPasswordIsCorrect(relationshipNo, password);
				if(!passIsCorrect) {
					NeoBankEnvironment.setComment(1, className, "Password is not correct");
					Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
					return;
				}
				
				if(BitcoinDao.class.getConstructor().newInstance().checkIfBitcoinHasBeenLinkedByCustomer(relationshipNo)) {
					NeoBankEnvironment.setComment(3, className, "Has a account Stellar and BTC account Linked");
					Utilities.sendJsonResponse(response, "error", "You have already linked your BTC and Stellar accounts in our system");
					return;
				}
				
				String nmemonic=firstMnemonic+" "+secMnemonic+" "+thirdMnemonic+" "+fourhMnemonic+" "+fifthMnemonic+" "+sixthMnemonic+" "+
						seventhMnemonic+" "+eithMnemonic+" "+nineMnemonic+" "+tenMnemonic+" "+eleventMnemonic+" "+twelveMnemonic;	
				
				/**
				 * Steps
				 * Check if user has account Bitcoin account
				 * if yes add the public key and add address
				 * if no generate keys using the given mnemonic
				 *  
				 * 
				 */
				
				NeoBankEnvironment.setComment(3,className,"nmemonic "+nmemonic);
				String resultString = BitcoinUtility.bitcoinMasterKeyGenerationFromSeed(nmemonic, btcCreationTime);
//					returnValue = stringAddress+","+privKey+","+pubKey;
				btcAddressFromSeed = resultString.split(",")[0];
				btcPubKeyFromSeed = resultString.split(",")[2];
				nmemonic=nmemonic.replaceAll(" ", ",");
				NeoBankEnvironment.setComment(3, className, "btcAddressFromSeed is "+btcAddressFromSeed);
				NeoBankEnvironment.setComment(3, className, "btcPubKeyFromSeed is "+btcPubKeyFromSeed);
				NeoBankEnvironment.setComment(3, className, "nmemonic is "+nmemonic);
				//Compare Public keys
				if(!btcAddressFromSeed.equals(btcAddress)) {
					NeoBankEnvironment.setComment(1, className, "Invalid Address or Seed Code");
					Utilities.sendJsonResponse(response, "error", "Invalid Address or Seed Code");
					return;
				}
				if(!btcPubKeyFromSeed.equals(btcPublicKey)) {
					NeoBankEnvironment.setComment(1, className, "Invalid Public Key or Seed Code");
					Utilities.sendJsonResponse(response, "error", "Invalid Public Key or Seed Code");
					return;
				}
					
				/**
				 * Pass mnemonic code generated by BitcoinJ to Stellar Key Pair Generator here
				 */
				
				nmemonic = nmemonic.replaceAll(",", " ");
				NeoBankEnvironment.setComment(3, className, "nmemonic "+nmemonic);
				char [] mnemonicCharArray = nmemonic.toCharArray();
				KeyPair keyPair = Bip39Utility.generateKeyPairs(mnemonicCharArray);
				// Fund account from Friendbot 
				// TODO Disable this in production
				StellarSDKUtility.createAccount(keyPair);
				nmemonic =  nmemonic.replaceAll(" ", ",");
				// Check if has already linked his account to BTC and Stellar using our system.
				encryptedMnemonic= Utilities.tripleEncryptData(nmemonic);
				
				//Insert To BTC mnemonic
				boolean result  = (boolean)BitcoinDao.class.getConstructor().newInstance().insertMnemonicCode(relationshipNo, encryptedMnemonic);
				if(!result) 
					throw new Exception ("Unable to insert BTC mnemonic code");
				//Insert To Stellar mnemonic
				result  = (boolean)CustomerCoinsDao.class.getConstructor().newInstance().insertMnemonicCode(relationshipNo, encryptedMnemonic);
				if(!result) 
					throw new Exception ("Unable to insert Stellar mnemonic code");
				//Insert To Stellar mnemonic
				success = (Boolean) BitcoinDao.class.getConstructor().newInstance().registerBitcoinAccount(Utilities.tripleEncryptData(btcPublicKey), 
						Utilities.tripleEncryptData(btcAddress), relationshipNo, "Y", btcCreationTime);	
				
				if(!success) 
					throw new Exception ("Unable to insert BTC Adress code");
				//Fund Accounts
				//Step 1 - get partner keys
	            AssetAccount xlmFundAccount =(AssetAccount)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getActiveXLMAccounts();
					//Step 2: Check Threshhold

	                
	                KeyPair accounts = KeyPair.fromAccountId((xlmFundAccount.getPublicKey()));
	                accountBalance=StellarSDKUtility.getAccountBalance(accounts);
	                NeoBankEnvironment.setComment(3, className, "Checking balance");
	               
	                for(int i=0; i<accountBalance.size(); i++) {
				    	if(accountBalance.get(i).getAssetCode().equals("XLM")) {
				    		xlmBalance = accountBalance.get(i).getAssetBalance();
				    		NeoBankEnvironment.setComment(3, className, "xlmBalance "+xlmBalance);
	                		if(Float.parseFloat(xlmBalance)<= Float.parseFloat(NeoBankEnvironment.getXLMFundAccThreshold().toString())) {
	                			//TODO Sent email to TDA to fund account
	                		}
				    	}
				    		
				    }
	                res=StellarSDKUtility.sendNativeCoinPayment(keyPair.getAccountId(), 
							Utilities.tripleDecryptData(xlmFundAccount.getPrivateKey()),NeoBankEnvironment.getXLMFundAccountAmount());
					if(res.equals("success")) {
						
						success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerAndFundStellarAccount(Utilities.tripleEncryptData(keyPair.getAccountId())
								, "", relationshipNo, assetCode,"Y","A");
						
						if(!success) 
							throw new Exception ("Unable to insert Stellar Details");
						//Create Wallets
						issuerPorteAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(porteAssetCode);
						issuerVeslAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(veslAssetCode);
						issuerUsdcAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(usdcAssetCode);
						issuerBtcxAccountId=(String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(btcxAssetCode);

						limit = NeoBankEnvironment.getMaxStellarAssetWalletLimit();
						
						NeoBankEnvironment.setComment(3,className,"issuerPorteAccountId "+issuerPorteAccountId+"Private Key "+String.valueOf( keyPair.getSecretSeed())+
								"baseFee "+baseFee+" limit "+limit+" porteAssetCode "+NeoBankEnvironment.getPorteTokenCode() );
						//PORTE Wallet						
						createTrustline = StellarSDKUtility.createTrustline(issuerPorteAccountId, String.valueOf( keyPair.getSecretSeed()),
								 baseFee, limit,  NeoBankEnvironment.getPorteTokenCode());
						if(createTrustline) {
							success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
									relationshipNo, "Porte Wallet",keyPair.getAccountId(), porteAssetCode);
							createTrustline=false;
						}
						//VESL Wallet 
						createTrustline = StellarSDKUtility.createTrustline(issuerVeslAccountId, String.valueOf( keyPair.getSecretSeed()),
								 baseFee, limit,  NeoBankEnvironment.getVesselCoinCode() );
						if(createTrustline) {
							success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
									relationshipNo, "VESL Wallet",keyPair.getAccountId(), veslAssetCode);
							createTrustline=false;
						}
						//USDC Wallet 
						createTrustline = StellarSDKUtility.createTrustline(issuerUsdcAccountId, String.valueOf( keyPair.getSecretSeed()),
								 baseFee, limit,  NeoBankEnvironment.getUSDCCode() );
						if(createTrustline) {
							success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
									relationshipNo, "USDC Wallet",keyPair.getAccountId(), usdcAssetCode);
							createTrustline=false;
						}//Btcx Wallet
						createTrustline = StellarSDKUtility.createTrustline(issuerBtcxAccountId, String.valueOf( keyPair.getSecretSeed()),
								 baseFee, limit,  NeoBankEnvironment.getBitcoinCode());
						if(createTrustline) {
							success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
									relationshipNo, "BTCx Wallet",keyPair.getAccountId(), btcxAssetCode);
							createTrustline=false;
						}else {
							throw new Exception("Error in creating Trustline");
						}
						
					}else {
						//Create a queue for unfunded accounts
						success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerAndFundStellarAccount(Utilities.tripleEncryptData(keyPair.getAccountId())
								, "", relationshipNo, assetCode,"Y","P");
						if(!success) 
							throw new Exception ("Unable to insert Stellar Details");
					}
				
				
				//CBA - Customer Register
				SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "CRB","Customer created new BTC and Stellar account" );
				obj.addProperty("error", "false");
				obj.addProperty("message", "Wallet is set up successfully");
				obj.addProperty("btc_pub_key", btcPublicKey);
				obj.addProperty("btc_address", btcAddress);
				obj.addProperty("stellar_pub_key", keyPair.getAccountId());
				obj.addProperty("stellar_piv_key", String.valueOf( keyPair.getSecretSeed()));
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
					output = response.getWriter();
					output.print(obj);
				} finally {
					if(output!=null) output.close();if(user!=null) user=null;if(relationshipNo!=null) relationshipNo=null;
					if(btcPublicKey!=null) btcPublicKey=null;if(password!=null) password=null;if(obj!=null) obj=null;
					if(btcAddress!=null) btcAddress=null;if(firstMnemonic!=null) firstMnemonic=null;if(thirdMnemonic!=null) thirdMnemonic=null;
					if(secMnemonic!=null) secMnemonic=null;if(fourhMnemonic!=null) fourhMnemonic=null;if(fifthMnemonic!=null) fifthMnemonic=null;
					if(sixthMnemonic!=null) sixthMnemonic=null;if(seventhMnemonic!=null) seventhMnemonic=null;if(eithMnemonic!=null) eithMnemonic=null;
					if(nineMnemonic!=null) nineMnemonic=null;if(tenMnemonic!=null) tenMnemonic=null;if(eleventMnemonic!=null) eleventMnemonic=null;
					if(twelveMnemonic!=null) twelveMnemonic=null; if(btcAddressFromSeed!=null) btcAddressFromSeed=null; if(btcPubKeyFromSeed!=null) btcPubKeyFromSeed=null;
//					if(resultString!=null) resultString=null; 
					if(nmemonic!=null) nmemonic=null; if(encryptedMnemonic!=null) encryptedMnemonic=null; if(keyPair!=null) keyPair=null;
					if(mnemonicCharArray!=null) mnemonicCharArray=null; if(assetCode!=null) assetCode=null; 
					if(limit!=null) limit=null; if(res!=null) res=null; 
					if(xlmBalance!=null) xlmBalance=null; if(issuerPorteAccountId!=null) issuerPorteAccountId=null;if(issuerVeslAccountId!=null) issuerVeslAccountId=null; 
					if(issuerUsdcAccountId!=null) issuerUsdcAccountId=null; if(issuerBtcxAccountId!=null) issuerBtcxAccountId=null;if(accountBalance!=null) accountBalance=null; 
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error In linking Bitcoin account, Please try again letter");
			}
			
			break;
			
		case "get_btc_details":
			try {
				NeoBankEnvironment.setComment(3,className,"get_btc_details");
				JsonObject obj = new JsonObject();  PrintWriter output = null;
				Gson gson = new Gson(); String langPref=null;
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

				
				ArrayList<CryptoAssetCoins> btcDetails = (ArrayList<CryptoAssetCoins>)
						BitcoinDao.class.getConstructor().newInstance().getBTCDetails();
				if(btcDetails == null) {
					obj.add("error", gson.toJsonTree("true")); 
					if(langPref.equalsIgnoreCase("ES")) {
						obj.add("message", gson.toJsonTree("Sin detalles de BTC"));
					}else {
						obj.add("message", gson.toJsonTree("No BTC details"));
					}					
				}else {
					obj.add("error", gson.toJsonTree("false")); 
					obj.add("data", gson.toJsonTree(btcDetails));
				}
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));

				} finally {
					if (output != null)output.close(); if (btcDetails != null)btcDetails = null; if (gson != null)gson = null;
					if (obj != null)obj = null;if (langPref != null)langPref = null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in getting BTC details, Please try again letter");
			}	
		break;
		
		case "get_btc_details_mbl":
			try {
				NeoBankEnvironment.setComment(3,className,"get_btc_details_mbl");
				JsonObject obj = new JsonObject();  PrintWriter output = null;
				Gson gson = new Gson(); 
				
				ArrayList<CryptoAssetCoins> btcDetails = (ArrayList<CryptoAssetCoins>)
						BitcoinDao.class.getConstructor().newInstance().getBTCDetails();
				if(btcDetails == null) {
					obj.add("error", gson.toJsonTree("true")); 
					obj.add("message", gson.toJsonTree("No BTC details"));
				}else {
					obj.add("error", gson.toJsonTree("false")); 
					obj.add("data", gson.toJsonTree(btcDetails));
					obj.add("senderasset", gson.toJsonTree(btcDetails));
				}
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));

				} finally {
					if (output != null)output.close(); if (btcDetails != null)btcDetails = null; if (gson != null)gson = null;
					if (obj != null)obj = null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in getting BTC details, Please try again letter");
			}	
		break;
		
		case "buy_btc_using_fiat":
			try {
				JsonObject obj = new JsonObject(); String customerCharges = null;
				User user = null; String minimumTxnAmount = null; PrintWriter output = null;
				String relationshipNo= null; 
				Gson gson = new Gson(); String amount= null; String channel =null; // T= Tokenized Card, F=Fiat Wallet, B =Bank  
				Boolean success = false; 	String walletBalance = null; String txnUserCode = null;
				String walletDetails =null; String fiatWalletId=null; 	String btcWalletId=null; //This is either porte token or vessel coin
				String payComments=""; 	String referenceNo=""; String tokenId = null;
				String assetCode = null; String txnPayMode = null; String extSystemRef = null;
				String cvv = null; String amountBTC=null; //Get this from stellar
				String authStatus=""; String authMessage=""; String walletId=null; String authResponse=null;
				boolean recordAuthorization=false; String currencyId = null; String transactionCode=null;
				String txnMode=null; String userType=null;String langPref=null;
				
				if(request.getParameter("receivedamounte_buy_btc")!=null) amount = request.getParameter("receivedamounte_buy_btc").trim();
				if(request.getParameter("amounte_buy_btc")!=null)	 	amountBTC = request.getParameter("amounte_buy_btc").trim();
				if(request.getParameter("comment")!=null)	 payComments = request.getParameter("comment").trim();
				if(request.getParameter("payment_method")!=null) channel = request.getParameter("payment_method").trim();
				if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
				if(channel.equalsIgnoreCase("T")) {
				if(request.getParameter("tokenid")!=null)	
						tokenId = request.getParameter("tokenid").trim();
					if(request.getParameter("cvv")!=null)	
						cvv = request.getParameter("cvv").trim();
				}	
				assetCode=NeoBankEnvironment.getBitcoinCode();
				NeoBankEnvironment.setComment(1, className, "amountBTCx "+amountBTC);
				NeoBankEnvironment.setComment(1, className, "amount "+amount);
				NeoBankEnvironment.setComment(1, className, "assetCode "+assetCode);

				if (session.getAttribute("SESS_USER") == null) 
					throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				relationshipNo = user.getRelationshipNo();
				walletDetails = (String)CustomerPorteCoinDao.class.getConstructor()
						.newInstance().getAssetWalletDetails(relationshipNo, assetCode, "");
				if(walletDetails==null) {
					
					if(langPref.equalsIgnoreCase("en")) {
						Utilities.sendJsonResponse(response, "error", "Dear customer your dont have coresponding wallet, "
								+ "please create wallet and try again");
					}else {
						Utilities.sendJsonResponse(response, "error", "Estimado cliente, no tiene billetera coresponding, "
								+ "por favor crea una billetera e int�ntalo de nuevo");
					}
					return;
				}
				btcWalletId = walletDetails.split(",")[0];
				txnUserCode = Utilities.generateTransactionCode(10);
				if(channel.equalsIgnoreCase("T")) {
					//Debit card here
					//Call Gate way here
					//Get charges 
					txnPayMode = NeoBankEnvironment.getCodeBuyBTCViaToken();
					
					referenceNo = txnPayMode+ "-" + (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
							+ Utilities.genAlphaNumRandom(9);
					customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
							NeoBankEnvironment.getDefaultCustomerUserType(),txnPayMode, amount);
					 minimumTxnAmount=customerCharges.substring(customerCharges.indexOf("|")+1, customerCharges.length());

					
					if ( Double.parseDouble(amount)< Double.parseDouble(minimumTxnAmount)) { 
						Utilities.sendJsonResponse(response, "error", "Transaction amount can not be less than "+minimumTxnAmount);
						throw new Exception("minimumAmount amount inputed:- Minimum exptected is:- "+minimumTxnAmount+"Amount inputed is"+amount);
					}
					extSystemRef=Utilities.genAlphaNumRandom(24).toUpperCase(); //Remove this after integration
			
					success = (Boolean) BitcoinDao.class.getConstructor().newInstance().
							buyBTCCoinViaToken(relationshipNo, tokenId, amount, payComments, referenceNo, 
									txnUserCode, customerCharges, txnPayMode, assetCode, extSystemRef, btcWalletId, amountBTC);
					
				}
				if(channel.equalsIgnoreCase("F")) {
					walletDetails = (String)CustomerWalletDao.class.getConstructor()
							.newInstance().getFiatWalletBalance(relationshipNo);
					fiatWalletId = walletDetails.split(",")[0];
					walletBalance = walletDetails.split(",")[1];
					txnMode="D";// Debit
					userType="C";
					currencyId = NeoBankEnvironment.getUSDCurrencyId();
					transactionCode=(new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
							+ Utilities.genAlphaNumRandom(9);
					txnPayMode = NeoBankEnvironment.getCodeBuyBTCViaFiatWallet();
					referenceNo = txnPayMode+ "-" + transactionCode;
					customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
							NeoBankEnvironment.getDefaultCustomerUserType(),txnPayMode, amount);
					 minimumTxnAmount=customerCharges.substring(customerCharges.indexOf("|")+1, customerCharges.length());
					
					if ( Double.parseDouble(amount)< Double.parseDouble(minimumTxnAmount)) { 
						Utilities.sendJsonResponse(response, "error", "Transaction amount can not be less than "+minimumTxnAmount);
						return;
					}
					//Check balance // 
					double senderDebitAmount =  Double.parseDouble(amount) + 
							Double.parseDouble(customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|")));
					if ( senderDebitAmount> Double.parseDouble(walletBalance)) {
						if(langPref.equalsIgnoreCase("en")) {
							Utilities.sendJsonResponse(response, "error", "Dear customer you have insufficient funds, Kindly top up and try again");
						}else {
							Utilities.sendJsonResponse(response, "error", "Estimado cliente, no tiene fondos suficientes. Por favor, recargue y vuelva a intentarlo.");
						}
							return;
					}
					/****** Wallet Authorization******/
					
					authResponse=(String)WalletAuthorizationDao.class.getConstructor().newInstance().performWalletAuthorization(relationshipNo, amount, txnMode);
					if (authResponse.isEmpty()) {
						Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
						return;
					}
					authStatus=authResponse.substring(0,authResponse.indexOf(","));
					authMessage=authResponse.substring(authResponse.indexOf(",")+1,authResponse.indexOf("|"));
					walletId=authResponse.substring(authResponse.indexOf("|")+1,authResponse.length());
			
					if (authStatus.equals("S")==false) {
						//Authorization failed
						// Record failed authorization
						
						recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, amount, txnMode, currencyId, 
								userType, referenceNo, walletId, authStatus, authMessage);
						if(!recordAuthorization) { 
							 Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again");
						return;
						}
						     Utilities.sendJsonResponse(response, "authfailed", authMessage);
						return;
					}else {
						// Record successful authorization
						recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, amount, txnMode, currencyId, 
								userType, referenceNo, walletId, authStatus, authMessage);
						if(!recordAuthorization) { 
							 Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again");
						return;
						}
					}
					/****** End of Wallet Authorization******/
					
					success = (Boolean) BitcoinDao.class.getConstructor().newInstance().buyBTCViaFiatWallet(
							relationshipNo, fiatWalletId, amount, payComments, referenceNo, txnUserCode, txnPayMode,
							assetCode, extSystemRef, btcWalletId, customerCharges, amountBTC,transactionCode);
				}
				if (success) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, "C",
							txnPayMode," Buy BTC Using fiat money ");
					obj.add("error", gson.toJsonTree("false"));
					if(langPref.equalsIgnoreCase("en")) {
						obj.add("message", gson.toJsonTree(" Your Transaction of "+assetCode +":"+ amountBTC + 
								" is being processed we will notify you once the operation is done ")); 
					}else {
						obj.add("message", gson.toJsonTree(" Su transacci�n de "+assetCode +":"+ amountBTC + 
								" se est� procesando te avisaremos una vez finalizada la operaci�n ")); 
					}
					
				}else {
					obj.add("error", gson.toJsonTree("true"));
					if(langPref.equalsIgnoreCase("en")) {
						obj.add("message", gson.toJsonTree("Transaction failed")); 
					}else {
						obj.add("message", gson.toJsonTree("Transacci�n fallida")); 
					}
				}
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));
				} finally {
					if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
					if (obj != null)obj = null; if (minimumTxnAmount != null)minimumTxnAmount = null;
					if (txnUserCode != null)txnUserCode = null; if (channel != null)channel = null;
					if (btcWalletId != null)btcWalletId = null; if (tokenId != null)tokenId = null;
					if (assetCode != null)assetCode = null; 
					if (txnPayMode != null)txnPayMode = null; if (extSystemRef != null)extSystemRef = null;
					if (amount != null)amount = null; if (amountBTC != null)amountBTC = null; 
					if (user != null)user = null; if (langPref!=null) langPref=null;
					if (walletBalance != null)walletBalance = null; 
					if (walletDetails != null)walletDetails = null; 
					if (fiatWalletId != null)fiatWalletId = null; 
					if (payComments != null)payComments = null; 
					if (referenceNo != null)referenceNo = null; 
					if (cvv != null)cvv = null; 
					if (userType != null)userType = null; 
					if (currencyId!=null); currencyId=null;   if (walletId!=null); walletId=null; if (authResponse != null)authResponse = null;
					if (transactionCode!=null); transactionCode=null;    if (txnMode!=null); txnMode=null; 
				}								
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in coin purchase, Please try again letter");
			}
		break;
		
		
		case "link_mbl_btc_without_mnemonic":
			try {
				String btcAddress = null; String btcPublicKey = null; String password = null;
				String relationshipNo = null; User user = null; long btcCreationTime = 0;
				JsonObject obj = new JsonObject(); PrintWriter output = null;String tokenValue=null;
				
				
				if(request.getParameter("relno")!=null)relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("btc_public_key")!=null)btcPublicKey = request.getParameter("btc_public_key").trim();
				if(request.getParameter("btc_address")!=null)btcAddress = request.getParameter("btc_address").trim();
				if(request.getParameter("security")!=null)password = request.getParameter("security").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					return;
				}
				boolean passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
						.checkIfPasswordIsCorrect(relationshipNo, password);
				if(!passIsCorrect) {
					NeoBankEnvironment.setComment(1, className, "Password is not correct");
					Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
					return;
				}
				
				//TODO Check if we have an api if BTC address exist
				//Check if customer has linked their BTC account
				if(BitcoinDao.class.getConstructor().newInstance().checkIfBitcoinHasBeenLinkedByCustomer(relationshipNo)) {
					Utilities.sendJsonResponse(response, "error", "You have already linked your BTC account in our system");
					return;
				}
				
				boolean success = (Boolean) BitcoinDao.class.getConstructor().newInstance().registerBitcoinAccount(Utilities.encryptString(btcPublicKey), 
						Utilities.encryptString(btcAddress), relationshipNo, "N", btcCreationTime);
				if(!success) 
					throw new Exception ("Unable to insert BTC Details");
				//CRB - Customer Register Bitcoin
				SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "CRB","Customer  link BTC account without Seed Code" );
				obj.addProperty("error", "false");
				obj.addProperty("message", "You have successfully linked Bitcoin Account ");
			try {
				NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
				output = response.getWriter();
				output.print(obj);
			} finally {
				if(output!=null) output.close();if(user!=null) user=null;if(relationshipNo!=null) relationshipNo=null;
				if(btcPublicKey!=null) btcPublicKey=null;if(password!=null) password=null;if(obj!=null) obj=null;
			}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error In linking Bitcoin account, Please try again letter");
			}
			
			break;


		case "transfer_bitcoin":
			try {
				JsonObject obj = new JsonObject(); boolean recordAuthorization = false; String userType = "C";
				User user = null;  String txnMode = null; String currencyId = null; String transactionCode = null;
				String relationshipNo= null; String  customerCharges = null; String minimumTxnAmount = null;
				PrintWriter output = null; String customerChargesValue = null; String walletDetails = null;
				Gson gson = new Gson(); String walletId = null; String walletBalance = null; String authStatus = null;
				String amount= null; // in BTC 
				String receiverAddress = null;String authMessage = null; boolean success = false; String txnUserCode = null; 
				String payComments="";  String referenceNo=""; 
				String assetCode = null;  //BTC
				String txnPayMode = NeoBankEnvironment.getBTCP2PTxnCode(); 
				String extSystemRef = null; //BTC Hash
				String senderAdress = null; String hasMnemonic = null;  String password  = null; String mnemoniStringFromDB  = null;
				boolean passIsCorrect = false;  String masterKeyGenerationResult = null; long creationtime = 0; String senderPrivateKey = null;
				String senderPublicKey = null; String senderBTCAddress = null; String btcDetails = null;
				JsonObject createBTCTransactionObj = null; JsonObject signBTCTransactionObj = null; String langPref=null;

				if(request.getParameter("coin_asset_trans_btc")!=null) assetCode = request.getParameter("coin_asset_trans_btc").trim();
				if(request.getParameter("input_receiver_address_trans_btc")!=null) receiverAddress = request.getParameter("input_receiver_address_trans_btc").trim();
				if(request.getParameter("sendamount_trans_btc")!=null) amount = request.getParameter("sendamount_trans_btc").trim();
				if(request.getParameter("commet_trans_btc")!=null)	 payComments = request.getParameter("commet_trans_btc").trim();				
				if(request.getParameter("hasMnemonic")!=null)	 hasMnemonic = request.getParameter("hasMnemonic").trim();
				if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
				NeoBankEnvironment.setComment(3, className, "langPref "+langPref); 
				//NeoBankEnvironment.setComment(3, className, "receiverAddress is "+receiverAddress); 
				//NeoBankEnvironment.setComment(3, className, "amount is "+amount); 
				//NeoBankEnvironment.setComment(3, className, "payComments is "+payComments); 
				//NeoBankEnvironment.setComment(3, className, "hasMnemonic is "+hasMnemonic); 
				
				if (session.getAttribute("SESS_USER") == null) 
					throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				relationshipNo = user.getRelationshipNo();
				
				if(hasMnemonic.equals("true")) {
					if(request.getParameter("security")!=null)	 password = request.getParameter("security").trim();
					passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
							.checkIfPasswordIsCorrect(relationshipNo, password);
					
					if(!passIsCorrect) {
						NeoBankEnvironment.setComment(1, className, "Password is not correct");
						if(langPref.equalsIgnoreCase("en")) {
							Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
						}else {
							Utilities.sendJsonResponse(response, "error", "Por favor ingrese la contrase�a correcta");
						}
						return;
					}
					mnemoniStringFromDB = (String) BitcoinDao.class.getConstructor().newInstance()
							.getBTCMnemonicCode(relationshipNo);
					
					masterKeyGenerationResult = BitcoinUtility.bitcoinMasterKeyGenerationFromSeed(
							mnemoniStringFromDB.replaceAll(",", " "), creationtime);
					 senderPrivateKey = masterKeyGenerationResult.split(",")[1];
					 senderPublicKey = masterKeyGenerationResult.split(",")[2];
					 senderBTCAddress = masterKeyGenerationResult.split(",")[0];
				}else {
					if(request.getParameter("security")!=null)	 senderPrivateKey = request.getParameter("security").trim();
					btcDetails = (String) BitcoinDao.class.getConstructor().newInstance()
							.getBTCDetails(relationshipNo);
					senderBTCAddress = btcDetails.split(",")[0];
					senderPublicKey = btcDetails.split(",")[1];
				}
				
				//Get fees here
				
				//Get Fiat wallet details and transaction charges
				walletDetails = (String)CustomerWalletDao.class.getConstructor().newInstance().getFiatWalletBalance(relationshipNo);
				
				if(walletDetails!=null) {
					walletId = walletDetails.split(",")[0];
					walletBalance = walletDetails.split(",")[1];
				}
				txnMode="D";
				currencyId = NeoBankEnvironment.getUSDCurrencyId(); // USD Code
				txnUserCode = Utilities.generateTransactionCode(10);
				transactionCode=(new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
						+ Utilities.genAlphaNumRandom(9);
				referenceNo = NeoBankEnvironment.getBTCP2PTxnCode()+ "-" +transactionCode;
				double destinationAmount = (double)BitcoinDao.class.getConstructor().
						newInstance().getConversionInUSD(assetCode, amount);
				DecimalFormat df = null;
				df = new DecimalFormat("#.##");
				df.setRoundingMode(RoundingMode.CEILING);
				String amountInUSD  = df.format(destinationAmount);
				customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
						NeoBankEnvironment.getDefaultCustomerUserType(),NeoBankEnvironment.getBTCP2PTxnCode(), amountInUSD);

				minimumTxnAmount=customerCharges.substring(customerCharges.indexOf("|")+1, customerCharges.length());
				// Check Wallet Balance
				

				customerChargesValue = customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|"));
				// Check Wallet Balance
				 NeoBankEnvironment.setComment(3,className,"customerChargesValue is "+customerChargesValue);
				 
				if ((Double.parseDouble(customerChargesValue)) >Double.parseDouble(walletBalance)) {
					if(langPref.equalsIgnoreCase("en")) {
						Utilities.sendJsonResponse(response, "error", "You do not have enough balance In Your Fiat Wallet to complete this transaction. Please topup to proceed");
					}else {
						Utilities.sendJsonResponse(response, "error", "No tiene suficiente saldo en su billetera Fiat para completar esta transacci�n. Recargue para continuar");
					}
					return;
				}
					
				/****** Wallet Authorization******/
				String authResponse=(String)WalletAuthorizationDao.class.getConstructor().newInstance().performWalletAuthorization(relationshipNo, customerChargesValue, txnMode);
				if (authResponse.isEmpty()) {
					Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
					return;
				}
				authStatus=authResponse.substring(0,authResponse.indexOf(","));
				 authMessage=authResponse.substring(authResponse.indexOf(",")+1,authResponse.indexOf("|"));
				 walletId=authResponse.substring(authResponse.indexOf("|")+1,authResponse.length());
		
				if (authStatus.equals("S")==false) {
					//Authorization failed
					// Record failed authorization
					recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, customerChargesValue, txnMode, currencyId, 
							userType, referenceNo, walletId, authStatus, authMessage);
					if(!recordAuthorization) { 
						 Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again");
					return;
					}
					     Utilities.sendJsonResponse(response, "authfailed", authMessage);
					return;
				}else {
					// Record successful authorization
					recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, amount, txnMode, currencyId, 
							userType, referenceNo, walletId, authStatus, authMessage);
					if(!recordAuthorization) { 
						 Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again");
					return;
					}
				}
				
				/****** End of Wallet Authorization******/	
				//Do BTC Transaction
				createBTCTransactionObj = BitcoinUtility.createTransaction(senderBTCAddress, receiverAddress, amount);
				signBTCTransactionObj = BitcoinUtility.signTransaction(createBTCTransactionObj, senderPrivateKey, senderPublicKey);
				extSystemRef = signBTCTransactionObj.get("tx").getAsJsonObject().get("hash").getAsString(); //Get BTC TXN hash
				
				success = (boolean)BitcoinDao.class.getConstructor().newInstance().debitP2PTransactionCharges(
						relationshipNo, walletId,  amount, payComments, referenceNo, txnUserCode, customerCharges,transactionCode);
				if(success) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, "C", txnPayMode," Sent BTC peer to peer "+extSystemRef);
					obj.add("error", gson.toJsonTree("false"));
					if(langPref.equalsIgnoreCase("en")) {
						obj.add("message", gson.toJsonTree(" Your Transaction of "+assetCode +":"+ amount +  " is being processed, Please check after 10 to 15 minutes ")); 
					}else {
						obj.add("message", gson.toJsonTree("Su transacción de"+assetCode +":"+ amount +  " se está procesando, verifique después de 10 a 15 minutos")); 
					}
				}else {
					obj.add("error", gson.toJsonTree("true")); 
					if(langPref.equalsIgnoreCase("en")) {
						obj.add("message", gson.toJsonTree("Transaction failed")); 
					}else {
						obj.add("message", gson.toJsonTree("Transacci�n fallida")); 
					}
					
				}	
			try {
				NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
				output = response.getWriter();
				output.print(gson.toJson(obj));
			} finally {	
				if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
				if (obj != null)obj = null; if (signBTCTransactionObj != null)signBTCTransactionObj = null;
				if (amount != null)amount = null;  if (createBTCTransactionObj != null)createBTCTransactionObj = null;
				if (btcDetails != null)btcDetails = null;  if (senderBTCAddress != null)senderBTCAddress = null;
				if (senderPublicKey != null)senderPublicKey = null;  if (senderPrivateKey != null)senderPrivateKey = null;
				if (masterKeyGenerationResult != null)masterKeyGenerationResult = null;  if (mnemoniStringFromDB != null)mnemoniStringFromDB = null;
				if (receiverAddress != null)receiverAddress = null;  if (walletId != null)walletId = null;
				if (extSystemRef != null)extSystemRef = null; 
				if (user != null)user = null;  if (walletBalance != null)walletBalance = null; 
				if (walletDetails != null)walletDetails = null;  if (senderAdress != null)senderAdress = null; 
				if (payComments != null)payComments = null;  if (hasMnemonic != null)hasMnemonic = null; 
				if (password != null)password = null;  if (authStatus != null)authStatus = null; 
				if (customerChargesValue != null)customerChargesValue = null;  if (txnUserCode != null)txnUserCode = null; 
				if (minimumTxnAmount != null)minimumTxnAmount = null;  if (referenceNo != null)referenceNo = null; 
				if (txnMode != null) txnMode = null;  if (currencyId != null)currencyId = null; if (txnPayMode != null)txnPayMode = null;
				if (transactionCode!=null) transactionCode=null;if (userType!=null)userType=null;
				if (df!=null) df=null;if (amountInUSD!=null)amountInUSD=null; if (langPref!=null) langPref=null;
			}								
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in transfer coin, Please try again letter");
			}
			
			break;
		case "check_if_customer_has_bitcoin_mnemonic_code":
			NeoBankEnvironment.setComment(3, className, "Inside check_if_customer_has_bitcoin_mnemonic_code");
			try {
				String relationshipNo = null;
				String hasMnemonicCode = null;
				JsonObject obj = new JsonObject();
				PrintWriter out_json = response.getWriter();
				User user = null;					
				if (session.getAttribute("SESS_USER") == null) 
					throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				relationshipNo = user.getRelationshipNo();
				
				hasMnemonicCode = (String) BitcoinDao.class.getConstructor().newInstance()
						.checkIfUserHasBitcoinMnemonicCode(relationshipNo);
				
				if(hasMnemonicCode.equalsIgnoreCase("Y")) {
					obj.addProperty("error", "false");
					obj.addProperty("hasmnemonic", "true");
				}else {
					obj.addProperty("error", "false");
					obj.addProperty("hasmnemonic", "false");
				}
				
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + (obj.toString()));
					out_json = response.getWriter();
					out_json.print((obj));
				} finally {
					if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; 
					if (hasMnemonicCode != null)hasMnemonicCode = null; if (obj != null) obj = null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Check failed , Try again");
			}
			break;

		case "exchange_btc_with_btcx":
			try {
				JsonObject obj = new JsonObject(); boolean recordAuthorization = false; String userType = "C";
				User user = null;  String txnMode = null; String currencyId = null; String transactionCode = null;
				String relationshipNo= null; String  customerCharges = null; String minimumTxnAmount = null;
				PrintWriter output = null; String customerChargesValue = null; String walletDetails = null;
				Gson gson = new Gson(); String walletId = null; String walletBalance = null; String authStatus = null;
				String amount= null; // in BTC 
				String tdaBTCAddress = null;String authMessage = null; boolean success = false; String txnUserCode = null; 
				String referenceNo=""; 
				String sourceAssetCode = null;  //BTC
				String destinatioAssetCode = null;  //BTCx
				String txnPayType = NeoBankEnvironment.getBTCToBTCxSwapCode(); 
				String extSystemRef = null; //BTC Hash
				String senderAdress = null; String hasMnemonic = null;  String password  = null; String mnemoniStringFromDB  = null;
				boolean passIsCorrect = false;  String masterKeyGenerationResult = null; long creationtime = 0; String userPrivateKey = null;
				String userPublicKey = null; String userBTCAddress = null; String btcDetails = null;String langPref=null;
				JsonObject createBTCTransactionObj = null; JsonObject signBTCTransactionObj = null;
				if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
				if(request.getParameter("source_asset")!=null) sourceAssetCode = request.getParameter("source_asset").trim();
				if(request.getParameter("destination_asset")!=null) destinatioAssetCode = request.getParameter("destination_asset").trim();
				if(request.getParameter("destination_amount_swap_btc")!=null) amount = request.getParameter("destination_amount_swap_btc").trim();
				if(request.getParameter("hasMnemonic")!=null)	 hasMnemonic = request.getParameter("hasMnemonic").trim();
				NeoBankEnvironment.setComment(3, className, "sourceAssetCode is "+sourceAssetCode); 
				NeoBankEnvironment.setComment(3, className, "destinatioAssetCode is "+destinatioAssetCode); 
				NeoBankEnvironment.setComment(3, className, "amount is "+amount); 
				NeoBankEnvironment.setComment(3, className, "hasMnemonic is "+hasMnemonic); 
				
				if (session.getAttribute("SESS_USER") == null) 
					throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				relationshipNo = user.getRelationshipNo();
				
				walletDetails = (String)CustomerPorteCoinDao.class.getConstructor()
						.newInstance().getAssetWalletDetails(relationshipNo, sourceAssetCode, "");
				if(walletDetails==null) {
					if(langPref.equalsIgnoreCase("en")) {
						Utilities.sendJsonResponse(response, "error", "Dear customer your dont have "+sourceAssetCode+" wallet, "
								+ "please create wallet and try again");
					}else {
						Utilities.sendJsonResponse(response, "error", "Estimado cliente no tienes "+sourceAssetCode+" cartera, "
								+ " por favor crea una billetera e int�ntalo de nuevo ");
					}
					return;
				}
				walletDetails = (String)CustomerPorteCoinDao.class.getConstructor()
						.newInstance().getAssetWalletDetails(relationshipNo, destinatioAssetCode, "");
				if(walletDetails==null) {
					if(langPref.equalsIgnoreCase("en")) {
						Utilities.sendJsonResponse(response, "error", "Dear customer your dont have "+destinatioAssetCode+" wallet, "
								+ "please create wallet and try again");
					}else {
						Utilities.sendJsonResponse(response, "error", "Estimado cliente no tienes "+sourceAssetCode+" cartera, "
								+ " por favor crea una billetera e int�ntalo de nuevo ");
					}
					return;
				}
				
				if(hasMnemonic.equals("true")) {
					if(request.getParameter("security")!=null)	 password = request.getParameter("security").trim();
					passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
							.checkIfPasswordIsCorrect(relationshipNo, password);
					
					if(!passIsCorrect) {
						NeoBankEnvironment.setComment(1, className, "Password is not correct");
						if(langPref.equalsIgnoreCase("en")) {
							Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
						}else {
							Utilities.sendJsonResponse(response, "error", "Por favor ingrese la contrase�a correcta");
						}
					
						return;
					}
					mnemoniStringFromDB = (String) BitcoinDao.class.getConstructor().newInstance()
							.getBTCMnemonicCode(relationshipNo);
					
					masterKeyGenerationResult = BitcoinUtility.bitcoinMasterKeyGenerationFromSeed(
							mnemoniStringFromDB.replaceAll(",", " "), creationtime);
					 userPrivateKey = masterKeyGenerationResult.split(",")[1];
					 userPublicKey = masterKeyGenerationResult.split(",")[2];
					 userBTCAddress = masterKeyGenerationResult.split(",")[0];
				}else {
					if(request.getParameter("security")!=null)	 userPrivateKey = request.getParameter("security").trim();
					btcDetails = (String) BitcoinDao.class.getConstructor().newInstance()
							.getBTCDetails(relationshipNo);
					userBTCAddress = btcDetails.split(",")[0];
					userPublicKey = btcDetails.split(",")[1];
				}
				
				//Get fees here
				
				//Get Fiat wallet details and transaction charges
				walletDetails = (String)CustomerWalletDao.class.getConstructor().newInstance().getFiatWalletBalance(relationshipNo);
				
				if(walletDetails!=null) {
					walletId = walletDetails.split(",")[0];
					walletBalance = walletDetails.split(",")[1];
				}
				txnMode="D";
				currencyId = NeoBankEnvironment.getUSDCurrencyId(); // USD Code
				txnUserCode = Utilities.generateTransactionCode(10);
				transactionCode=(new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
						+ Utilities.genAlphaNumRandom(9);
				referenceNo = txnPayType+ "-" +transactionCode;
				double destinationAmount = (double)BitcoinDao.class.getConstructor().
						newInstance().getConversionInUSD(sourceAssetCode, amount);
				DecimalFormat df = null;
				df = new DecimalFormat("#.##");
				df.setRoundingMode(RoundingMode.CEILING);
				String amountInUSD  = df.format(destinationAmount);

				customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
						NeoBankEnvironment.getDefaultCustomerUserType(),NeoBankEnvironment.getBTCP2PTxnCode(), amountInUSD);
				
				minimumTxnAmount=customerCharges.substring(customerCharges.indexOf("|")+1, customerCharges.length());
				// Check Wallet Balance

				customerChargesValue = customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|"));
				// Check Wallet Balance
				 NeoBankEnvironment.setComment(3,className,"customerChargesValue is "+customerChargesValue);
				 
				if ((Double.parseDouble(customerChargesValue)) >Double.parseDouble(walletBalance)) {
					Utilities.sendJsonResponse(response, "error", "You do not have enough balance In Your Fiat Wallet to complete this transaction. Please topup to proceed");
					return;
				}
					
				/****** Wallet Authorization******/
				String authResponse=(String)WalletAuthorizationDao.class.getConstructor().newInstance().performWalletAuthorization(relationshipNo, customerChargesValue, txnMode);
				if (authResponse.isEmpty()) {
					Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
					return;
				}
				authStatus=authResponse.substring(0,authResponse.indexOf(","));
				 authMessage=authResponse.substring(authResponse.indexOf(",")+1,authResponse.indexOf("|"));
				 walletId=authResponse.substring(authResponse.indexOf("|")+1,authResponse.length());
		
				if (authStatus.equals("S")==false) {
					//Authorization failed
					// Record failed authorization
					recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, customerChargesValue, txnMode, currencyId, 
							userType, referenceNo, walletId, authStatus, authMessage);
					if(!recordAuthorization) { 
						 Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again");
					return;
					}
					     Utilities.sendJsonResponse(response, "authfailed", authMessage);
					return;
				}else {
					// Record successful authorization
					recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, amount, txnMode, currencyId, 
							userType, referenceNo, walletId, authStatus, authMessage);
					if(!recordAuthorization) { 
						 Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again");
					return;
					}
				}
				
				/****** End of Wallet Authorization******/	
				//Get TDA Address
				tdaBTCAddress = (String) BitcoinDao.class.getConstructor().newInstance().getTDABTCAddress();
				//Do BTC Transaction
				createBTCTransactionObj = BitcoinUtility.createTransaction(userBTCAddress, tdaBTCAddress, amount);
				signBTCTransactionObj = BitcoinUtility.signTransaction(createBTCTransactionObj,userPrivateKey, userPublicKey);
				extSystemRef = signBTCTransactionObj.get("tx").getAsJsonObject().get("hash").getAsString(); //Get BTC TXN hash
				
				success = (boolean)BitcoinDao.class.getConstructor().newInstance().insertBTCToBTCxSwapTxn(
						relationshipNo, walletId,  amount,  referenceNo, txnUserCode, customerCharges,
						transactionCode, sourceAssetCode, destinatioAssetCode, extSystemRef, txnPayType);
				if(success) {
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo, "C", txnPayType," BTC to BTCx Swap Via TDA ");
					obj.add("error", gson.toJsonTree("false"));
					if(langPref.equalsIgnoreCase("en")) {
						obj.add("message", gson.toJsonTree(" Your Transaction of "+sourceAssetCode +":"+ amount +  " is being processed, We will notify you once the transaction is done")); 

					}else {
						obj.add("message", gson.toJsonTree(" Su transacci�n de "+sourceAssetCode +":"+ amount +  " se est� procesando, le notificaremos una vez que se realice la transacci�n")); 
					}
				}else {
					obj.add("error", gson.toJsonTree("true")); 
					if(langPref.equalsIgnoreCase("en")) {
						obj.add("message", gson.toJsonTree("Transaction failed")); 
					}else {
						obj.add("message", gson.toJsonTree("Transacci�n fallida")); 
					}
					
				}	
					
			try {
				NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
				output = response.getWriter();
				output.print(gson.toJson(obj));
			} finally {	
				if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
				if (obj != null)obj = null; if (signBTCTransactionObj != null)signBTCTransactionObj = null;
				if (amount != null)amount = null;  if (createBTCTransactionObj != null)createBTCTransactionObj = null;
				if (btcDetails != null)btcDetails = null;  if (userBTCAddress != null)userBTCAddress = null;
				if (userPublicKey != null)userPublicKey = null;  if (userPrivateKey != null)userPrivateKey = null;
				if (masterKeyGenerationResult != null)masterKeyGenerationResult = null;  if (mnemoniStringFromDB != null)mnemoniStringFromDB = null;
				if (tdaBTCAddress != null)tdaBTCAddress = null;  if (walletId != null)walletId = null;
				if (extSystemRef != null)extSystemRef = null; 
				if (user != null)user = null;  if (walletBalance != null)walletBalance = null; 
				if (walletDetails != null)walletDetails = null;  if (senderAdress != null)senderAdress = null; 
				if (sourceAssetCode != null)sourceAssetCode = null;  if (hasMnemonic != null)hasMnemonic = null; 
				if (password != null)password = null;  if (authStatus != null)authStatus = null; 
				if (customerChargesValue != null)customerChargesValue = null;  if (txnUserCode != null)txnUserCode = null; 
				if (minimumTxnAmount != null)minimumTxnAmount = null;  if (referenceNo != null)referenceNo = null; 
				if (txnMode != null) txnMode = null;  if (currencyId != null)currencyId = null; if (txnPayType != null)txnPayType = null;
				if (transactionCode!=null) transactionCode=null;if (userType!=null)userType=null;
				if (destinatioAssetCode!=null) destinatioAssetCode=null;if (langPref!=null) langPref=null;
				if (df!=null) df=null;if (amountInUSD!=null)amountInUSD=null;
			}								
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in transfer coin, Please try again letter");
			}
			
			break;
		
		case "link_btc_without_mnemonic_mbl":
			try {
				String btcAddress = null; String btcPublicKey = null; String password = null;
				String relationshipNo = null;long btcCreationTime = 0;String langPref=null;
				JsonObject obj = new JsonObject(); PrintWriter output = null;
				String publicKey = null;String privateKey=null;String tokenValue=null;
				String veslAssetCode= NeoBankEnvironment.getVesselCoinCode();String usdcAssetCode=NeoBankEnvironment.getUSDCCode();
				String porteAssetCode=NeoBankEnvironment.getPorteTokenCode();String issuerPorteAccountId = null; 
				String limit = null;int baseFee  = org.stellar.sdk.Transaction.MIN_BASE_FEE;
				String issuerVeslAccountId=null;String issuerUsdcAccountId=null;boolean createTrustline = false;
				String issuerBtcxAccountId=null;String btcxAssetCode=NeoBankEnvironment.getStellarBTCxCode();
				 String xlmBalance=null;ArrayList<AssetAccount> accountBalance=null;String res=null;
				
				if(request.getParameter("btc_public_key")!=null)btcPublicKey = request.getParameter("btc_public_key").trim();
				if(request.getParameter("btc_address")!=null)btcAddress = request.getParameter("btc_address").trim();
				if(request.getParameter("security")!=null)password = request.getParameter("security").trim();
				if(request.getParameter("stellar_public_key")!=null)publicKey = request.getParameter("stellar_public_key").trim();
				if(request.getParameter("stellar_private_key")!=null)privateKey = request.getParameter("stellar_private_key").trim();
				 if(request.getParameter("relno")!=null)relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

				//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v�lido, vuelva a iniciar sesi�n");

					}else {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					}
					return;
				}
				
				boolean passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
						.checkIfPasswordIsCorrect(relationshipNo, password);
				if(!passIsCorrect) {
					NeoBankEnvironment.setComment(1, className, "Password is not correct");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "error", "Por favor ingrese la contrase�a correcta");

					}else {
						Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
					}
					return;
				}
				//TODO Check if we have an api if BTC address exist
				//Check if customer has linked their BTC account
				if(BitcoinDao.class.getConstructor().newInstance().checkIfBitcoinHasBeenLinkedByCustomer(relationshipNo)) {
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "error", "Ya ha vinculado sus cuentas BTC y Stellar en nuestro sistema");

					}else {
						Utilities.sendJsonResponse(response, "error", "You have already linked your BTC and Stellar accounts in our system");
					}
					return;
				}				
				boolean success = (Boolean) BitcoinDao.class.getConstructor().newInstance().registerBitcoinAccount(Utilities.tripleEncryptData(btcPublicKey), 
						Utilities.tripleEncryptData(btcAddress), relationshipNo, "N", btcCreationTime);
				if(!success) 
					throw new Exception ("Unable to insert BTC Details");
				
				KeyPair keyPair =  KeyPair.fromAccountId(publicKey);
				boolean accountExist = StellarSDKUtility.CheckAccountIfExist(keyPair);
				if(!accountExist) {
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "error", "Cuenta No existe en estelar");

					}else {
						Utilities.sendJsonResponse(response, "error", " Account Does not exist in stellar ");
					}
					return;
				}
				//Fund Accounts
				//Step 1 - get partner keys
	            AssetAccount xlmFundAccount =(AssetAccount)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getActiveXLMAccounts();
					//Step 2: Check Threshhold

		                KeyPair accounts = KeyPair.fromAccountId((xlmFundAccount.getPublicKey()));
		                accountBalance=StellarSDKUtility.getAccountBalance(accounts);
		                NeoBankEnvironment.setComment(3, className, "Checking balance");
		                for(int i=0; i<accountBalance.size(); i++) {
					    	if(accountBalance.get(i).getAssetCode().equals("XLM")) {
					    		xlmBalance = accountBalance.get(i).getAssetBalance();
					    		NeoBankEnvironment.setComment(3, className, "xlmBalance "+xlmBalance);
		                		if(Float.parseFloat(xlmBalance)<= Float.parseFloat(NeoBankEnvironment.getXLMFundAccThreshold().toString())) {
		                			//TODO Sent email to TDA to fund account
		                		}
					    	}
					    		
					    }
					 res=StellarSDKUtility.sendNativeCoinPayment(publicKey, 
							Utilities.tripleDecryptData(xlmFundAccount.getPrivateKey()),NeoBankEnvironment.getXLMFundAccountAmount());
					if(res.equals("success")) {
						success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerAndFundStellarAccount(Utilities.tripleEncryptData(keyPair.getAccountId())
								, "", relationshipNo, NeoBankEnvironment.getXLMCode(),"N","A" );
						if(!success) 
							throw new Exception ("Unable to insert Stellar Details");
						//Create Wallets
						issuerPorteAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(porteAssetCode);
						issuerVeslAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(veslAssetCode);
						issuerUsdcAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(usdcAssetCode);
						issuerBtcxAccountId=(String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(btcxAssetCode);

						limit = NeoBankEnvironment.getMaxStellarAssetWalletLimit();
						
						//PORTE Wallet
						
						createTrustline = StellarSDKUtility.createTrustline(issuerPorteAccountId, privateKey,
								 baseFee, limit,  NeoBankEnvironment.getPorteTokenCode() );
						if(createTrustline) {
							success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
									relationshipNo, "Porte Wallet",publicKey, porteAssetCode);
							createTrustline=false;
						}
						//VESL Wallet 
						createTrustline = StellarSDKUtility.createTrustline(issuerVeslAccountId,privateKey,
								 baseFee, limit,  NeoBankEnvironment.getVesselCoinCode() );
						if(createTrustline) {
							success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
									relationshipNo, "VESL Wallet",publicKey, veslAssetCode);
							createTrustline=false;
						}
						//USDC Wallet 
						createTrustline = StellarSDKUtility.createTrustline(issuerUsdcAccountId, privateKey,
								 baseFee, limit,  NeoBankEnvironment.getUSDCCode() );
						if(createTrustline) {
							success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
									relationshipNo, "USDC Wallet",publicKey, usdcAssetCode);
							createTrustline=false;
						}
						//Btcx Wallet
						createTrustline = StellarSDKUtility.createTrustline(issuerBtcxAccountId,privateKey,
								 baseFee, limit,  NeoBankEnvironment.getBitcoinCode());
						if(createTrustline) {
							success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
									relationshipNo, "BTCx Wallet",publicKey, btcxAssetCode);
							createTrustline=false;
						}else {
							throw new Exception("Error in creating Trustline");
						}
					}else {
						//Create a queue for unfunded accounts
						success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerAndFundStellarAccount(Utilities.tripleEncryptData(publicKey)
								, "", relationshipNo, NeoBankEnvironment.getXLMCode(),"N","P");
						if(!success) 
							throw new Exception ("Unable to insert Stellar Details");
					}
				
				//CRB - Customer Register Bitcoin
				SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "CRB","Customer  link BTC and Stellar account without Seed Code" );
				obj.addProperty("error", "false");
				if(langPref.equalsIgnoreCase("ES")) {
					obj.addProperty("message", "Has vinculado con �xito las cuentas de Bitcoin y Stellar");

				}else {
					obj.addProperty("message", "You have successfully linked Bitcoin and Stellar Accounts");
				}
				
			try {
				NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
				output = response.getWriter();
				//output.print(obj);
			} finally {
				if(output!=null) output.close();if(relationshipNo!=null) relationshipNo=null;
				if(btcPublicKey!=null) btcPublicKey=null;if(password!=null) password=null;if(obj!=null) obj=null;
				if(keyPair!=null) keyPair=null;if(publicKey!=null) publicKey=null;
				if(limit!=null) limit=null; if(res!=null) res=null; 
				if(xlmBalance!=null) xlmBalance=null; if(issuerPorteAccountId!=null) issuerPorteAccountId=null;if(issuerVeslAccountId!=null) issuerVeslAccountId=null; 
				if(issuerUsdcAccountId!=null) issuerUsdcAccountId=null; if(issuerBtcxAccountId!=null) issuerBtcxAccountId=null;if(accountBalance!=null) accountBalance=null;
			}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error In linking Bitcoin account, Please try again letter");
			}
			
			break;
		case "link_account_with_mnemonic_code_mbl":
			try {
				NeoBankEnvironment.setComment(3,className,"inside link_account_with_mnemonic_code_mbl");
				String btcPublicKey = null; String btcAddress = null; String langPref=null;
				JsonObject obj = new JsonObject(); String encryptedMnemonic = null;
				String relationshipNo = null;String password=null;
				PrintWriter output = null; String firstMnemonic=null;
				String thirdMnemonic=null;String secMnemonic=null;String fourhMnemonic=null;String fifthMnemonic=null;
				String sixthMnemonic=null;String seventhMnemonic=null;String eithMnemonic=null;String nineMnemonic=null;
				String tenMnemonic=null;String eleventMnemonic=null;String twelveMnemonic=null;
			    long btcCreationTime = 0; boolean success = false; String assetCode = NeoBankEnvironment.getXLMCode();
			    String xlmBalance=null;ArrayList<AssetAccount> accountBalance=null;String res=null;
				String btcAddressFromSeed = null; String btcPubKeyFromSeed = null;String mnemoniStringFromUser=null;
				String tokenValue=null;
				String veslAssetCode= NeoBankEnvironment.getVesselCoinCode();String usdcAssetCode=NeoBankEnvironment.getUSDCCode();
				String porteAssetCode=NeoBankEnvironment.getPorteTokenCode();String issuerPorteAccountId = null; 
				String limit = null;int baseFee  = org.stellar.sdk.Transaction.MIN_BASE_FEE;
				String issuerVeslAccountId=null;String issuerUsdcAccountId=null;boolean createTrustline = false;
				String issuerBtcxAccountId=null;String btcxAssetCode=NeoBankEnvironment.getStellarBTCxCode();

				if(request.getParameter("mnemonic")!=null)		mnemoniStringFromUser = request.getParameter("mnemonic").trim();
				if(request.getParameter("input_btc_pubkey")!=null)btcPublicKey = request.getParameter("input_btc_pubkey").trim();
				if(request.getParameter("input_btc_address")!=null)btcAddress = request.getParameter("input_btc_address").trim();
				if(request.getParameter("security")!=null)password = request.getParameter("security").trim();
				if(request.getParameter("relno")!=null)		relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

				//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v�lido, vuelva a iniciar sesi�n");

					}else {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					}
					return;
				}
				NeoBankEnvironment.setComment(3, className, "mnemoniStringFromUser "+mnemoniStringFromUser+"password "+password);
				boolean passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
						.checkIfPasswordIsCorrect(relationshipNo, password);
				if(!passIsCorrect) {
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "error", "Por favor ingrese la contrase�a correcta");

					}else {
						Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
					}
					return;
				}
				
				if(BitcoinDao.class.getConstructor().newInstance().checkIfBitcoinHasBeenLinkedByCustomer(relationshipNo)) {
					NeoBankEnvironment.setComment(3, className, "Has a account Stellar and BTC account Linked");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "error", "Ya ha vinculado sus cuentas BTC y Stellar en nuestro sistema");

					}else {
						Utilities.sendJsonResponse(response, "error", "You have already linked your BTC and Stellar accounts in our system");
					}
					
					return;
				}

				
				/**
				 * Steps
				 * Check if user has account Bitcoin account
				 * if yes add the public key and add address
				 * if no generate keys using the given mnemonic
				 *  
				 * 
				 */
				
				String resultString = BitcoinUtility.bitcoinMasterKeyGenerationFromSeed(mnemoniStringFromUser, btcCreationTime);
//					returnValue = stringAddress+","+privKey+","+pubKey;
				btcAddressFromSeed = resultString.split(",")[0];
				btcPubKeyFromSeed = resultString.split(",")[2];
				mnemoniStringFromUser=mnemoniStringFromUser.replaceAll(" ", ",");
				NeoBankEnvironment.setComment(3, className, "btcAddressFromSeed is "+btcAddressFromSeed);
				NeoBankEnvironment.setComment(3, className, "btcPubKeyFromSeed is "+btcPubKeyFromSeed);
				NeoBankEnvironment.setComment(3, className, "nmemonic is "+mnemoniStringFromUser);
				//Compare Public keys
				if(!btcAddressFromSeed.equals(btcAddress)) {
					NeoBankEnvironment.setComment(1, className, "Invalid Address or Seed Code");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "error", "Direcci�n o c�digo semilla no v�lido");

					}else {
						Utilities.sendJsonResponse(response, "error", "Invalid Address or Seed Code");
					}
					
					return;
				}
				if(!btcPubKeyFromSeed.equals(btcPublicKey)) {
					NeoBankEnvironment.setComment(1, className, "Invalid Public Key or Seed Code");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "error", "Clave p�blica o c�digo semilla no v�lido");

					}else {
						Utilities.sendJsonResponse(response, "error", "Invalid Public Key or Seed Code");
					}
					
					return;
				}
					
				/**
				 * Pass mnemonic code generated by BitcoinJ to Stellar Key Pair Generator here
				 */
				
				mnemoniStringFromUser = mnemoniStringFromUser.replaceAll(",", " ");
				NeoBankEnvironment.setComment(3, className, "nmemonic "+mnemoniStringFromUser);
				char [] mnemonicCharArray = mnemoniStringFromUser.toCharArray();
				KeyPair keyPair = Bip39Utility.generateKeyPairs(mnemonicCharArray);
				// Fund account from Friendbot 
				// TODO Disable this in production
				StellarSDKUtility.createAccount(keyPair);
				mnemoniStringFromUser =  mnemoniStringFromUser.replaceAll(" ", ",");
				// Check if has already linked his account to BTC and Stellar using our system.
				encryptedMnemonic= Utilities.tripleEncryptData(mnemoniStringFromUser);
				
				//Insert To BTC mnemonic
				boolean result  = (boolean)BitcoinDao.class.getConstructor().newInstance().insertMnemonicCode(relationshipNo, encryptedMnemonic);
				if(!result) 
					throw new Exception ("Unable to insert BTC mnemonic code");
				//Insert To Stellar mnemonic
				result  = (boolean)CustomerCoinsDao.class.getConstructor().newInstance().insertMnemonicCode(relationshipNo, encryptedMnemonic);
				if(!result) 
					throw new Exception ("Unable to insert Stellar mnemonic code");
				//Insert To Stellar mnemonic
				success = (Boolean) BitcoinDao.class.getConstructor().newInstance().registerBitcoinAccount(Utilities.tripleEncryptData(btcPublicKey), 
						Utilities.tripleEncryptData(btcAddress), relationshipNo, "Y", btcCreationTime);	
				
				if(!success) 
					throw new Exception ("Unable to insert BTC Adress code");
				//Fund Accounts
				//Step 1 - get partner keys
	            AssetAccount xlmFundAccount =(AssetAccount)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getActiveXLMAccounts();
					//Step 2: Check Threshhold

	                
	                KeyPair accounts = KeyPair.fromAccountId((xlmFundAccount.getPublicKey()));
	                accountBalance=StellarSDKUtility.getAccountBalance(accounts);
	                NeoBankEnvironment.setComment(3, className, "Checking balance");
	               
	                for(int i=0; i<accountBalance.size(); i++) {
				    	if(accountBalance.get(i).getAssetCode().equals("XLM")) {
				    		xlmBalance = accountBalance.get(i).getAssetBalance();
				    		NeoBankEnvironment.setComment(3, className, "xlmBalance "+xlmBalance);
	                		if(Float.parseFloat(xlmBalance)<= Float.parseFloat(NeoBankEnvironment.getXLMFundAccThreshold().toString())) {
	                			//TODO Sent email to TDA to fund account
	                		}
				    	}
				    		
				    }
	                res=StellarSDKUtility.sendNativeCoinPayment(keyPair.getAccountId(), 
							Utilities.tripleDecryptData(xlmFundAccount.getPrivateKey()),NeoBankEnvironment.getXLMFundAccountAmount());
					if(res.equals("success")) {
		                NeoBankEnvironment.setComment(3, className, "res is success");
						success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerAndFundStellarAccount(Utilities.tripleEncryptData(keyPair.getAccountId())
								, "", relationshipNo, assetCode,"Y","A");
						
						NeoBankEnvironment.setComment(3,className,"Public key is "+keyPair.getAccountId());
						if(!success) 
							throw new Exception ("Unable to insert Stellar Details");
						//Create Wallets
						issuerPorteAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(porteAssetCode);
						issuerVeslAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(veslAssetCode);
						issuerUsdcAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(usdcAssetCode);
						issuerBtcxAccountId=(String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(btcxAssetCode);

						limit = NeoBankEnvironment.getMaxStellarAssetWalletLimit();
						 //Create Wallets
			            issuerPorteAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(porteAssetCode);
			            issuerVeslAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(veslAssetCode);
			            issuerUsdcAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(usdcAssetCode);
						issuerBtcxAccountId=(String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(btcxAssetCode);
			            limit = NeoBankEnvironment.getMaxStellarAssetWalletLimit();

			        	NeoBankEnvironment.setComment(3,className,"issuerPorteAccountId "+issuerPorteAccountId+" Private Key "+String.valueOf( keyPair.getSecretSeed())+
								" baseFee "+baseFee+" limit "+limit+" porteAssetCode "+NeoBankEnvironment.getPorteTokenCode() );
			            //PORTE Wallet
			            createTrustline = StellarSDKUtility.createTrustline(issuerPorteAccountId, String.valueOf( keyPair.getSecretSeed()),
			            baseFee, limit, NeoBankEnvironment.getPorteTokenCode() );
			            if(createTrustline) {
			            success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
			            relationshipNo, "Porte Wallet",keyPair.getAccountId(), porteAssetCode);
			            createTrustline=false;
			            }
			            //VESL Wallet
			            createTrustline = StellarSDKUtility.createTrustline(issuerVeslAccountId, String.valueOf( keyPair.getSecretSeed()),
			                baseFee, limit, NeoBankEnvironment.getVesselCoinCode() );
			            if(createTrustline) {
			                    success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
			                            relationshipNo, "VESL Wallet",keyPair.getAccountId(), veslAssetCode);
			                                createTrustline=false;
			                }
			            //USDC Wallet
			            createTrustline = StellarSDKUtility.createTrustline(issuerUsdcAccountId, String.valueOf( keyPair.getSecretSeed()),
			                baseFee, limit, NeoBankEnvironment.getUSDCCode());
			            if(createTrustline) {
			                    success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
			                        relationshipNo, "USDC Wallet",keyPair.getAccountId(), usdcAssetCode);
			            createTrustline=false;
			            }
			            //Btcx Wallet
						createTrustline = StellarSDKUtility.createTrustline(issuerBtcxAccountId, String.valueOf( keyPair.getSecretSeed()),
								 baseFee, limit,  NeoBankEnvironment.getBitcoinCode());
						if(createTrustline) {
							success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
									relationshipNo, "BTCx Wallet",keyPair.getAccountId(), btcxAssetCode);
							createTrustline=false;
						}else {
			                throw new Exception("Error in creating Trustline");
						}
						
					}else {
						//Create a queue for unfunded accounts
						success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerAndFundStellarAccount(Utilities.tripleEncryptData(keyPair.getAccountId())
								, "", relationshipNo, assetCode,"Y","P");
						if(!success) 
							throw new Exception ("Unable to insert Stellar Details");
					}
				
				
				//CBA - Customer Register
				SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "CRB","Customer created new BTC and Stellar account" );
				obj.addProperty("error", "false");
				obj.addProperty("message", "Wallet is set up successfully");
				obj.addProperty("btc_pub_key", btcPublicKey);
				obj.addProperty("btc_address", btcAddress);
				obj.addProperty("stellar_pub_key", keyPair.getAccountId());
				obj.addProperty("stellar_piv_key", String.valueOf( keyPair.getSecretSeed()));
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
					output = response.getWriter();
					output.print(obj);
				} finally {
					if(output!=null) output.close();if(relationshipNo!=null) relationshipNo=null;
					if(btcPublicKey!=null) btcPublicKey=null;if(password!=null) password=null;if(obj!=null) obj=null;
					if(btcAddress!=null) btcAddress=null;if(firstMnemonic!=null) firstMnemonic=null;if(thirdMnemonic!=null) thirdMnemonic=null;
					if(secMnemonic!=null) secMnemonic=null;if(fourhMnemonic!=null) fourhMnemonic=null;if(fifthMnemonic!=null) fifthMnemonic=null;
					if(sixthMnemonic!=null) sixthMnemonic=null;if(seventhMnemonic!=null) seventhMnemonic=null;if(eithMnemonic!=null) eithMnemonic=null;
					if(nineMnemonic!=null) nineMnemonic=null;if(tenMnemonic!=null) tenMnemonic=null;if(eleventMnemonic!=null) eleventMnemonic=null;
					if(twelveMnemonic!=null) twelveMnemonic=null; if(btcAddressFromSeed!=null) btcAddressFromSeed=null; if(btcPubKeyFromSeed!=null) btcPubKeyFromSeed=null;
//					if(resultString!=null) resultString=null; 
					if(mnemoniStringFromUser!=null) mnemoniStringFromUser=null; if(encryptedMnemonic!=null) encryptedMnemonic=null; if(keyPair!=null) keyPair=null;
					if(mnemonicCharArray!=null) mnemonicCharArray=null; if(assetCode!=null) assetCode=null; 
					if(limit!=null) limit=null; if(res!=null) res=null; 
					if(xlmBalance!=null) xlmBalance=null; if(issuerPorteAccountId!=null) issuerPorteAccountId=null;if(issuerVeslAccountId!=null) issuerVeslAccountId=null; 
					if(issuerUsdcAccountId!=null) issuerUsdcAccountId=null; if(issuerBtcxAccountId!=null) issuerBtcxAccountId=null;if(accountBalance!=null) accountBalance=null; 
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error In linking Bitcoin account, Please try again letter");
			}
			
			break;
		case "create_bitcoin_mnemocic_code_mbl":
			try {
				String hasAccount = null;
				JsonObject obj = new JsonObject();String tokenValue=null;
				String relationshipNo = null; PrintWriter output = null; String langPref=null;
				JsonObject data = new JsonObject(); Gson gson = new Gson();
				if(request.getParameter("has_account")!=null)hasAccount = request.getParameter("has_account").trim();
				if(request.getParameter("relno")!=null)relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

				NeoBankEnvironment.setComment(3, className, "langPref is "+langPref);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es v�lido, vuelva a iniciar sesi�n");

					}else {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					}
					return;
				}
				
				if(BitcoinDao.class.getConstructor().newInstance().checkIfBitcoinHasBeenLinkedByCustomer(relationshipNo)) {
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "error", "Ya has vinculado tus Wallets en nuestro sistema");
					}else {
						Utilities.sendJsonResponse(response, "error", "You have already linked your Wallets in our system");
					}
					return;
				}
				NeoBankEnvironment.setComment(3, className, "hasAccount is "+hasAccount);
				if(!Boolean.parseBoolean(hasAccount)) {
					data = BitcoinUtility.createBitcoinDeterministicWallet();
					SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "CCB","Bitcoin seed mnemonic code generated" );
					obj.addProperty("error", "false");
					obj.add("data", gson.toJsonTree(data));
				}else {
					obj.addProperty("error", "true");
					if(langPref.equalsIgnoreCase("ES")) {
						obj.addProperty("message", "Error al crear c�digo mnemot�cnico");
					}else {
						obj.addProperty("message", "Error in creating mnemonic code");
					}
					
				}
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " +(obj.toString()));
					output = response.getWriter();
					output.print(obj);
				} finally {
					if(data!=null) data=null; if(gson!=null)gson=null;
					if(obj!=null) obj=null; if(hasAccount!=null) hasAccount=null;
					if(output!=null) output.close();
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error In creating bitcoin mneumonic code, Please try again letter");
			}
			break;
		case "view_pending_btc_fiat_txn_mbl":
			NeoBankEnvironment.setComment(3, className, "Inside view_pending_btc_fiat_txn_mbl");
			try {
				JsonObject obj = new JsonObject();
				User user = null; String tokenValue = null;
				String relationshipNo= null;
				PrintWriter output = null;
				Gson gson = new Gson();
				String txnDesc= null;List<Transaction> listTxn = null;
				if(request.getParameter("relno")!=null)	 relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));


				listTxn = (List<Transaction>)BitcoinDao.class.getConstructor().
						newInstance().getPendingMobileBTCxTransactions(relationshipNo);
				if(listTxn!=null) {
					obj.add("data", gson.toJsonTree(listTxn));
					obj.add("error", gson.toJsonTree("false"));
				}else {
					obj.add("error", gson.toJsonTree("true"));
				}				
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));
				} finally {
					if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
					if (obj != null)obj = null; if (tokenValue != null)tokenValue = null;
					if (listTxn != null)listTxn = null; 
					if (user != null)user = null; 
					if (txnDesc != null)txnDesc = null; 
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in geting Transactions, Please try again letter");
			}
			break;
		case "view_pending_btc_swapping_txn_mbl":
			NeoBankEnvironment.setComment(3, className, "Inside view_pending_btc_fiat_txn_mbl");
			try {
				JsonObject obj = new JsonObject();
				User user = null; String tokenValue = null;
				String relationshipNo= null;
				PrintWriter output = null;
				Gson gson = new Gson();
				String txnDesc= null;List<Transaction> listTxn = null;
				if(request.getParameter("relno")!=null)	 relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));

				listTxn = (List<Transaction>)BitcoinDao.class.getConstructor().newInstance().getPendingBTCToBTCxSwapping(relationshipNo);
				
				if(listTxn!=null) {
					obj.add("data", gson.toJsonTree(listTxn));
					obj.add("error", gson.toJsonTree("false"));
				}else {
					obj.add("error", gson.toJsonTree("nodata"));
				}
				
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));
				} finally {
					if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
					if (obj != null)obj = null; if (tokenValue != null)tokenValue = null;
					if (listTxn != null)listTxn = null; 
					if (user != null)user = null; 
					if (txnDesc != null)txnDesc = null; 
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in geting Transactions, Please try again letter");
			}
		break;
		case "create_digital_asset":
			try {		
				StopWatch stopWatch = new StopWatch();
				// Start the watch, do some task and stop the watch.
				stopWatch.start();
				 String data = null; JsonObject objBtcData = new JsonObject(); JsonArray jsonArrayMnemonic = null;
				 long btcCreationTime = 0; String btcPubKey = null; String btcPivKey = null;
				 JsonArray mnemonicArray = new JsonArray(); String mnemonicString = null;
				 String btcAddress = null; String encryptedMnemonic=null;
				 User user = null; String relationshipNo = null;
				 String hasAccount = null; 
			        JsonObject obj = new JsonObject();
			        boolean success = false; 
			    String mnemonicCode=null;
		        String assetCode = NeoBankEnvironment.getXLMCode();
		        String veslAssetCode= NeoBankEnvironment.getVesselCoinCode();String usdcAssetCode=NeoBankEnvironment.getUSDCCode();
		        String porteAssetCode=NeoBankEnvironment.getPorteTokenCode();String issuerPorteAccountId = null;
		        String limit = null;int baseFee = org.stellar.sdk.Transaction.MIN_BASE_FEE;
		        String issuerVeslAccountId=null;String issuerUsdcAccountId=null;boolean createTrustline = false;
		        String issuerBtcxAccountId=null;String btcxAssetCode=NeoBankEnvironment.getStellarBTCxCode();
		        String xlmBalance=null;ArrayList<AssetAccount> accountBalance=null;String res=null;
				 
				 if(request.getParameter("json_string")!=null) data = StringUtils.trim(request.getParameter("json_string"));
				 
				 user = (User) session.getAttribute("SESS_USER");
		         relationshipNo = user.getRelationshipNo();
				 objBtcData = new Gson().fromJson(data, JsonObject.class);
				 btcCreationTime = objBtcData.get("creation_time").getAsLong();
	             btcAddress = objBtcData.get("address").getAsString();
	             btcPubKey = objBtcData.get("public_key").getAsString();
	             btcPivKey = objBtcData.get("private_key").getAsString();
	             mnemonicArray = objBtcData.get("mnemonic_code").getAsJsonArray();
	             mnemonicString = Bip39Utility.createCSVForMnemonic(mnemonicArray);
		         encryptedMnemonic= Utilities.tripleEncryptData(mnemonicString);
	             if(BitcoinDao.class.getConstructor().newInstance().checkIfBitcoinHasBeenLinkedByCustomer(relationshipNo)) {
	                 throw new Exception("User has Bitcoin account already created");
	             }
	             /**
		            * Pass mnemonic code generated by BitcoinJ to Stellar Key Pair Generator here
		            */

		            mnemonicString = mnemonicString.replaceAll(",", " ");
		            NeoBankEnvironment.setComment(3, className, "mnemonicString "+mnemonicString);
		            char [] mnemonicCharArray = mnemonicString.toCharArray();
		            KeyPair keyPair = Bip39Utility.generateKeyPairs(mnemonicCharArray);

		            // Fund account from Friendbot
		            // TODO Disable this in production
		            StellarSDKUtility.createAccount(keyPair);

		            //Insert To BTC mnemonic
		            boolean result = (boolean)BitcoinDao.class.getConstructor().newInstance().insertMnemonicCode(relationshipNo, encryptedMnemonic);
		            if(!result)
		                throw new Exception ("Unable to insert BTC mnemonic code");
		            //Insert To Stellar mnemonic
		            result = (boolean)CustomerCoinsDao.class.getConstructor().newInstance().insertMnemonicCode(relationshipNo, encryptedMnemonic);
		            if(!result)
		                throw new Exception ("Unable to insert Stellar mnemonic code");
		            //Insert To Stellar mnemonic
		            success = (boolean) BitcoinDao.class.getConstructor().newInstance().registerBitcoinAccount(Utilities.tripleEncryptData(btcPubKey),
		            Utilities.tripleEncryptData(btcAddress), relationshipNo, "Y", btcCreationTime);

		            if(!success)
		                throw new Exception ("Unable to insert BTC Adress code");
		            //Fund Accounts
		            //Step 1 - get partner keys
		            //Sleep thread for 30 seconds 
		            //TimeUnit.SECONDS.sleep(30);
		            
		            /****/
		            AssetAccount xlmFundAccount =(AssetAccount)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getActiveXLMAccounts();

		            NeoBankEnvironment.setComment(3,className,"xlmFundAccount "+xlmFundAccount);
		                //Step 2: Check Threshhold
		                NeoBankEnvironment.setComment(3,className,"Checking threshold");

		                KeyPair accounts = KeyPair.fromAccountId((xlmFundAccount.getPublicKey()));
		                accountBalance=StellarSDKUtility.getAccountBalance(accounts);
		                NeoBankEnvironment.setComment(3, className, "Checking balance");
		                for(int i=0; i<accountBalance.size(); i++) {
					    	if(accountBalance.get(i).getAssetCode().equals("XLM")) {
					    		xlmBalance = accountBalance.get(i).getAssetBalance();
					    		NeoBankEnvironment.setComment(3, className, "xlmBalance "+xlmBalance);
		                		if(Float.parseFloat(xlmBalance)<= Float.parseFloat(NeoBankEnvironment.getXLMFundAccThreshold().toString())) {
		                			//TODO Sent email to TDA to fund account
		                		}
					    	}
					    		
					    }
		                NeoBankEnvironment.setComment(3, className, "after checking threshold");
			            res=StellarSDKUtility.sendNativeCoinPayment(keyPair.getAccountId(),
			            Utilities.tripleDecryptData(xlmFundAccount.getPrivateKey()),NeoBankEnvironment.getXLMFundAccountAmount());
			            if(res.equals("success")) {
			                NeoBankEnvironment.setComment(3,className,"After success ");
//			                success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerAndFundStellarAccount(Utilities.tripleEncryptData(keyPair.getAccountId())
//			                    , "", relationshipNo, assetCode,"Y","A");
			                success =true;

			            if(!success)
			                throw new Exception ("Unable to insert Stellar Details");
			            //Create Wallets
			            issuerPorteAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(porteAssetCode);
			            issuerVeslAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(veslAssetCode);
			            issuerUsdcAccountId = (String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(usdcAssetCode);
						issuerBtcxAccountId=(String)CustomerDigitalAssetsDao.class.getConstructor().newInstance().getIssueingAccountPublicKey(btcxAssetCode);
			            limit = NeoBankEnvironment.getMaxStellarAssetWalletLimit();

			            //PORTE Wallet
			            createTrustline = StellarSDKUtility.createTrustline(issuerPorteAccountId, String.valueOf( keyPair.getSecretSeed()),
			            baseFee, limit, NeoBankEnvironment.getPorteTokenCode() );
			            if(createTrustline) {
			            success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
			            relationshipNo, "Porte Wallet",keyPair.getAccountId(), porteAssetCode);
			            createTrustline=false;
			            }
			            //VESL Wallet
			            createTrustline = StellarSDKUtility.createTrustline(issuerVeslAccountId, String.valueOf( keyPair.getSecretSeed()),
			                baseFee, limit, NeoBankEnvironment.getVesselCoinCode() );
			            if(createTrustline) {
			                    success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
			                            relationshipNo, "VESL Wallet",keyPair.getAccountId(), veslAssetCode);
			                                createTrustline=false;
			                }
			            //USDC Wallet
			            createTrustline = StellarSDKUtility.createTrustline(issuerUsdcAccountId, String.valueOf( keyPair.getSecretSeed()),
			                baseFee, limit, NeoBankEnvironment.getUSDCCode());
			            if(createTrustline) {
			                    success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
			                        relationshipNo, "USDC Wallet",keyPair.getAccountId(), usdcAssetCode);
			            createTrustline=false;
			            }//Btcx Wallet
						createTrustline = StellarSDKUtility.createTrustline(issuerBtcxAccountId, String.valueOf( keyPair.getSecretSeed()),
								 baseFee, limit,  NeoBankEnvironment.getBitcoinCode());
						if(createTrustline) {
							success = (Boolean)CustomerWalletDao.class.getConstructor().newInstance().createWallet(
									relationshipNo, "BTCx Wallet",keyPair.getAccountId(), btcxAssetCode);
							createTrustline=false;
						}else {
			                throw new Exception("Error in creating Trustline");
			        }

			        }else {
			        	//Sleep thread for 30 seconds 
			            //TimeUnit.SECONDS.sleep(30);
			            //Create a queue for unfunded accounts
//			            success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerAndFundStellarAccount(Utilities.tripleEncryptData(keyPair.getAccountId())
//			                , "", relationshipNo, assetCode,"Y","P");
			            
	             
			        }
		            try {
		            	success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerAndFundStellarAccount(Utilities.tripleEncryptData(keyPair.getAccountId())
			                    , "", relationshipNo, assetCode,"Y","A");
		            	stopWatch.stop();
		            	NeoBankEnvironment.setComment(3, className, "Time taken is: " + stopWatch.getTime());
			            SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "CRB","Customer created new BTC and Stellar account" );
			        } finally {
			            if(user!=null) user=null; if(hasAccount!=null) hasAccount=null;
			            if(btcAddress!=null) btcAddress=null; if(obj!=null) obj=null;if(mnemonicArray!=null) mnemonicArray=null;
			            if(btcPubKey!=null) btcPubKey=null;
			            if(btcPivKey!=null) btcPivKey=null;if(mnemonicCode!=null) mnemonicCode=null;
			            if(encryptedMnemonic!=null) encryptedMnemonic=null;if(mnemonicString!=null) mnemonicString=null;
			            if(relationshipNo!=null) relationshipNo=null;if(data!=null) data=null;
			            if(objBtcData!=null) objBtcData=null;if(jsonArrayMnemonic!=null) jsonArrayMnemonic=null;
			            if(mnemonicCharArray!=null) mnemonicCharArray = null;
			            if(keyPair!=null) keyPair = null; if(assetCode!=null) assetCode = null;
			            if(limit!=null) limit=null; if(res!=null) res=null; 
						if(xlmBalance!=null) xlmBalance=null; if(issuerPorteAccountId!=null) issuerPorteAccountId=null;if(issuerVeslAccountId!=null) issuerVeslAccountId=null; 
						if(issuerUsdcAccountId!=null) issuerUsdcAccountId=null; if(issuerBtcxAccountId!=null) issuerBtcxAccountId=null;if(accountBalance!=null) accountBalance=null;
			        }
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rulesaction+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			break;
		
		}
		
		
		
	}

	@Override
	public void performOperation(String rules, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx)
			throws Exception {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("SESS_USER") == null) 
			Utilities.callException(request, response, ctx, "Session has expired, please log in again");
		switch (rules) {
		case "Set up Wallet":
			try {
				String langPref=null;
				if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
				request.setAttribute("langpref", langPref);

				request.setAttribute("lastaction", "porte");
				request.setAttribute("lastrule", rules);
				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerCreateBitcoinAcPage()).forward(request, response);
				} finally {
					if (langPref!=null) langPref=null;
				}

			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rules + " is " + e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			break;
		case "Buy Bitcoin":
			try {
				request.setAttribute("lastaction", "porte");
				request.setAttribute("lastrule", rules);
				response.setContentType("text/html");
				String relationshipNo = ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
				String btcAddress = (String) BitcoinDao.class.getConstructor().newInstance()
						.getBTCAddress(relationshipNo);
				JsonObject btcBalanceObj = null;
				if(btcAddress!=null) 
					btcBalanceObj = BitcoinUtility.getBTCAccountBalance(btcAddress);
				try {
					request.setAttribute("btc_balance", btcBalanceObj);
					ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerBuyBTCPage()).forward(request, response);
				} finally {
					if(relationshipNo != null) relationshipNo =null; if(btcBalanceObj != null) btcBalanceObj =null;
					if(btcAddress != null) btcAddress =null;
				}

			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: " + rules + " is " + e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			break;
		case "view_pending_btc_fiat_txn":
			try {
				String langPref=null;
				if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
				request.setAttribute("langpref", langPref);
				request.setAttribute("lastaction", "porte");
				request.setAttribute("lastrule", "Bitcoin");
				response.setContentType("text/html");
				String realationshipNo = null;
				List<Transaction> listTxn = null;
				realationshipNo = ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
				listTxn = (List<Transaction>)BitcoinDao.class.getConstructor().
				newInstance().getPendingBTCxTransactions(realationshipNo);
				try {
					request.setAttribute("transactions", listTxn );
					ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerViewBTCRequest()).forward(request, response);
				} finally {
					if(listTxn != null) listTxn =null;if (langPref!=null) langPref=null;
					if(realationshipNo != null) realationshipNo =null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			
			break;
		case "Transfer Bitcoin":
			try {
				request.setAttribute("lastaction", "porte");
				request.setAttribute("lastrule", rules);
				response.setContentType("text/html");
				String relationshipNo = ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
				String btcAddress = (String) BitcoinDao.class.getConstructor().newInstance()
						.getBTCAddress(relationshipNo);
				JsonObject btcBalanceObj = null;
				if(btcAddress!=null) 
					btcBalanceObj = BitcoinUtility.getBTCAccountBalance(btcAddress);
				
				try {
					request.setAttribute("btc_balance", btcBalanceObj );
					ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerTransferBitCoinPage()).forward(request, response);
				} finally {
					if(relationshipNo != null) relationshipNo =null; if(btcBalanceObj != null) btcBalanceObj =null;
					if(btcAddress != null) btcAddress =null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			break;
			
		case "View Transactions":
			try {
				String langPref=null;
				if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
				request.setAttribute("langpref", langPref);
				request.setAttribute("lastaction", "porte");
				request.setAttribute("lastrule", rules);
				response.setContentType("text/html");
				String relationshipNo = null;
				relationshipNo = ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
				String btcAddress = (String) BitcoinDao.class.getConstructor().newInstance()
						.getBTCAddress(relationshipNo);
				JsonObject txnObj = null;
				//NeoBankEnvironment.setComment(3, className," btcAddress "+btcAddress+" relationshipNo "+relationshipNo );
				if(btcAddress!=null ) {
					//NeoBankEnvironment.setComment(3, className, "Address is null");
					txnObj = BitcoinUtility.getBTCAccountTxn(btcAddress, "100");
				}
				ArrayList<BTCTransction> arrBTCTransaction = null;
				JsonObject accountDetails = null;
				if(txnObj!=null) {
					arrBTCTransaction = BitcoinUtility.processTrnsactions(txnObj, btcAddress);
					accountDetails = BitcoinUtility.filterAccountDetailsFromTransactionObj(txnObj);
				}
				try {
					request.setAttribute("transactions", arrBTCTransaction );
					request.setAttribute("accountDetails", accountDetails );
					ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerViewBTCTransactionsPage()).forward(request, response);
				} finally {
					if(relationshipNo != null) relationshipNo =null;if(btcAddress != null) btcAddress =null;
					if(txnObj != null) txnObj =null; if(arrBTCTransaction != null) arrBTCTransaction =null;
					if(accountDetails != null) accountDetails =null;if (langPref!=null) langPref=null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			break;
		case "Swap BTC To BTCx":
			try {
				request.setAttribute("lastaction", "btc");
				request.setAttribute("lastrule", rules);
				response.setContentType("text/html");
				String relationshipNo = ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
				String btcAddress = (String) BitcoinDao.class.getConstructor().newInstance()
						.getBTCAddress(relationshipNo);
				JsonObject btcBalanceObj = null;
				if(btcAddress!=null) 
					btcBalanceObj = BitcoinUtility.getBTCAccountBalance(btcAddress);
				try {
					request.setAttribute("btc_balance", btcBalanceObj );
					ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerSwapBTCToBTCxPage()).forward(request, response);
				} finally {
					if(relationshipNo != null) relationshipNo =null; if(btcBalanceObj != null) btcBalanceObj =null;
					if(btcAddress != null) btcAddress =null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
		break;
		
		case "view_pending_btc_swapping_txn":
			try {
				String langPref=null;
				if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
				request.setAttribute("langpref", langPref);
				request.setAttribute("lastaction", "porte");
				request.setAttribute("lastrule", rules);
				response.setContentType("text/html");
				String realationshipNo = null;
				List<Transaction> listTxn = null;
				realationshipNo = ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
				//NeoBankEnvironment.setComment(3, className, "realationshipNo "+realationshipNo);
				listTxn = (List<Transaction>)BitcoinDao.class.getConstructor().newInstance().getPendingBTCToBTCxSwapping(realationshipNo);
				try {
					request.setAttribute("transactions", listTxn );
					ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerViewBTCToBTCxSwapTxnPage()).forward(request, response);
				} finally {
					if(listTxn != null) listTxn =null;if (langPref!=null) langPref=null;
					if(realationshipNo != null) realationshipNo =null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			
			break;
			
		case "Bitcoin":
			try {
				String langPref=null;
				if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
				request.setAttribute("lastaction", "porte");
				request.setAttribute("lastrule", rules);
				response.setContentType("text/html");
				request.setAttribute("langpref", langPref);
				String relationshipNo = ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
				String btcAddress = (String) BitcoinDao.class.getConstructor().newInstance()
						.getBTCAddress(relationshipNo);
				JsonObject btcBalanceObj = null;
				
				String publicKey = (String)CustomerDao.class.getConstructor().newInstance().getPublicKey(relationshipNo);
				KeyPair userAccount = null;
				NeoBankEnvironment.setComment(3, className, " Public key is  "+publicKey);
				ArrayList<AssetAccount> accountBalances = null;
				if(publicKey != null && publicKey != "") {
					userAccount = KeyPair.fromAccountId(publicKey);
					accountBalances = StellarSDKUtility.getAccountBalance(userAccount);
				}
				if(btcAddress!=null) 
					btcBalanceObj = BitcoinUtility.getBTCAccountBalance(btcAddress);
				JsonObject txnObj = null;
				//NeoBankEnvironment.setComment(3, className," btcAddress "+btcAddress+" relationshipNo "+relationshipNo );
				if(btcAddress!=null ) {
					//NeoBankEnvironment.setComment(3, className, "Address is null");
					txnObj = BitcoinUtility.getBTCAccountTxn(btcAddress, "100");
				}
				ArrayList<BTCTransction> arrBTCTransaction = null;
				JsonObject accountDetails = null;
				if(txnObj!=null) {
					arrBTCTransaction = BitcoinUtility.processTrnsactions(txnObj, btcAddress);
					accountDetails = BitcoinUtility.filterAccountDetailsFromTransactionObj(txnObj);
				}
				try {
					request.setAttribute("transactions", arrBTCTransaction );
					request.setAttribute("accountDetails", accountDetails );
					request.setAttribute("btc_balance", btcBalanceObj);
					request.setAttribute("externalwallets", accountBalances);
					ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerBitcoinPage()).forward(request, response);
				} finally {
					if(relationshipNo != null) relationshipNo =null; if(btcBalanceObj != null) btcBalanceObj =null;
					if(btcAddress != null) btcAddress =null;if (langPref!=null) langPref=null;
					if(txnObj != null) txnObj =null; if(arrBTCTransaction != null) arrBTCTransaction =null;
					if(accountDetails != null) accountDetails =null; if(publicKey != null) publicKey =null;
					if(accountBalances != null) accountBalances =null; if(userAccount != null) userAccount =null;
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			
			break;
		case "create_digital_asset_page":
			try {
				String data = null; String langPref=null;
				if(request.getParameter("json_string")!=null) data = StringUtils.trim(request.getParameter("json_string"));
				if(request.getParameter("hdnlangpref")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlangpref"));
				request.setAttribute("lastaction", "porte");
				request.setAttribute("lastrule", "Set up Wallet");
				request.setAttribute("langpref", langPref);

				request.setAttribute("data", data);
				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getCustomerCreateAssetWaitingPage()).forward(request, response);
				} finally {
					if(data!=null)data=null;
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			
			break;

		
		}
		
	}

}
