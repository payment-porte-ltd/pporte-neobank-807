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

public class CustomerMobileBitcoinRulesImpl implements Rules {
	private static String className = CustomerMobileBitcoinRulesImpl.class.getSimpleName();
	@Override
	public void performJSONOperation(String arg0, HttpServletRequest arg1, HttpServletResponse arg2,
			ServletContext arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unused")
	@Override
	public void performMultiPartOperation(String rulesaction, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		HttpSession session = request.getSession(false); 
		switch (rulesaction) {
		case "link_mbl_account_with_mnemonic_code":
			try {
				User user = null; 
				String btcPublicKey = null; String btcAddress = null; 
				JsonObject obj = new JsonObject();
				String relationshipNo = null;String password=null;
				PrintWriter output = null; String firstMnemonic=null;
				String thirdMnemonic=null;String secMnemonic=null;String fourhMnemonic=null;String fifthMnemonic=null;
				String sixthMnemonic=null;String seventhMnemonic=null;String eithMnemonic=null;String nineMnemonic=null;
				String tenMnemonic=null;String eleventMnemonic=null;String twelveMnemonic=null;
			    long btcCreationTime = 0;String tokenValue=null;
				String btcAddressFromSeed = null; String btcPubKeyFromSeed = null;
				
				
				
				if(request.getParameter("relno")!=null)relationshipNo = request.getParameter("relno").trim();
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
				if(request.getParameter("btc_public_key")!=null)btcPublicKey = request.getParameter("btc_public_key").trim();
				if(request.getParameter("input_btc_address")!=null)btcAddress = request.getParameter("input_btc_address").trim();
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
				
				if(BitcoinDao.class.getConstructor().newInstance().checkIfBitcoinHasBeenLinkedByCustomer(relationshipNo)) {
					NeoBankEnvironment.setComment(3, className, "Has a BTC account");
					Utilities.sendJsonResponse(response, "error", "You have already linked your BTC account in our system");
					return;
				}
				
				String nmemonic=firstMnemonic+" "+secMnemonic+" "+thirdMnemonic+" "+fourhMnemonic+" "+fifthMnemonic+" "+sixthMnemonic+" "+
						seventhMnemonic+" "+eithMnemonic+" "+nineMnemonic+" "+tenMnemonic+" "+eleventMnemonic+" "+twelveMnemonic;	
				
				NeoBankEnvironment.setComment(3,className,"mnemonic code is "+nmemonic);
				String resultString = BitcoinUtility.bitcoinMasterKeyGenerationFromSeed(nmemonic, btcCreationTime);
//				returnValue = stringAddress+","+privKey+","+pubKey;
				btcAddressFromSeed = resultString.split(",")[0];
				btcPubKeyFromSeed = resultString.split(",")[2];
				nmemonic=nmemonic.replaceAll(" ", ",");
//				NeoBankEnvironment.setComment(3, className, " nmemonic csv is "+nmemonic);
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
				
				
				nmemonic = Utilities.tripleEncryptData(nmemonic);
				//NeoBankEnvironment.setComment(3, className, " nmemonic en is "+nmemonic);
				boolean result  = (boolean)BitcoinDao.class.getConstructor().newInstance().insertMnemonicCode(relationshipNo,  nmemonic);
				if(!result) 
					throw new Exception ("Unable to insert mnemonic code");
				boolean success = (Boolean) BitcoinDao.class.getConstructor().newInstance().registerBitcoinAccount(Utilities.tripleEncryptData(btcPublicKey), 
						Utilities.tripleEncryptData(btcAddress), relationshipNo, "Y", btcCreationTime);
				if(!success) 
					throw new Exception ("Unable to insert BTC Details");
				//CBA - Customer Register
				SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "CRB","Customer linked new BTC account with Seed Code" );
				obj.addProperty("error", "false");
				obj.addProperty("message", "You have successfully Linked Bitcoin Account ");
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
					if(resultString!=null) resultString=null; if(nmemonic!=null) nmemonic=null;if(tokenValue!=null) tokenValue=null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error In linking Bitcoin account, Please try again letter");
			}
			break;
		case "create_mbl_bitcoin_mnemocic_code":
			try {
				String hasAccount = null; User user = null;
				JsonObject obj = new JsonObject();String tokenValue=null;
				String relationshipNo = null; PrintWriter output = null; 
				JsonObject data = new JsonObject(); Gson gson = new Gson();
				if(request.getParameter("has_account")!=null)hasAccount = request.getParameter("has_account").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				if(request.getParameter("relno")!=null)relationshipNo = request.getParameter("relno").trim();

				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					return;
				}
				// Check if has already linked his account to stellar using our system.
				if(BitcoinDao.class.getConstructor().newInstance().checkIfBitcoinHasBeenLinkedByCustomer(relationshipNo)) {
					Utilities.sendJsonResponse(response, "error", "You have already linked your Bitcoin account in our system");
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
					if(output!=null) output.close();if(tokenValue!=null) tokenValue=null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error In creating bitcoin mneumonic code, Please try again letter");
			}
			break;

		case "add_bitcoin_new_account_mbl":
		    try {
		    	StopWatch stopWatch = new StopWatch();
		    	// Start the watch, do some task and stop the watch.
		    	stopWatch.start();
		        String hasAccount = null;
		        boolean success = false; String relationshipNo = null;
		       String password=null;String mnemonicCode=null;String langPref=null;
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
		         String tokenValue=null;  
		            
		            if(request.getParameter("data")!=null)data = request.getParameter("data").trim();
		            if(request.getParameter("relno")!=null)relationshipNo = request.getParameter("relno").trim();
					if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
					if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

					//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
					if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
						NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es válido, vuelva a iniciar sesión");

						}else {
							Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
						}
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
		            	if(langPref.equalsIgnoreCase("ES")) {
		            		 Utilities.sendJsonResponse(response, "error", "Ya has vinculado tus Wallets en nuestro sistema");

						}else {
							 Utilities.sendJsonResponse(response, "error", "You have already linked your Wallets in our system");
						}		               
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
		                success = true;

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
		            

		        try {
		        	success = (Boolean) CustomerCoinsDao.class.getConstructor().newInstance().registerAndFundStellarAccount(Utilities.tripleEncryptData(keyPair.getAccountId())
		                    , "", relationshipNo, assetCode,"Y","A");
		        	stopWatch.stop();
		        	NeoBankEnvironment.setComment(3, className, "Time taken is: " + stopWatch.getTime());
		        	SystemUtilsDao.class.getConstructor().newInstance().addAuditTrail(relationshipNo,"C", "CRB","Customer created new BTC and Stellar account" );
		        } finally {
		            if(hasAccount!=null) hasAccount=null;
		            if(btcAddress!=null) btcAddress=null; if(mnemonicArray!=null) mnemonicArray=null;
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
		        NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
		        Utilities.sendJsonResponse(response, "error", "Error In creating Bitcoin account, Please try again letter");
		        }
		    break;	
		
		case "check_if_customer_has_bitcoin_mnemonic_code_mbl":
			try {
				String relationshipNo = null; String fingrePrintFlag = null;
				String hasMnemonicCode = null; String tokenValue = null;
				JsonObject obj = new JsonObject();String langPref=null;
				PrintWriter out_json = response.getWriter();
				if(request.getParameter("relno")!=null)		relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("token")!=null)	    tokenValue = StringUtils.trim(request.getParameter("token"));
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

				//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es válido, vuelva a iniciar sesión");

					}else {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					}
					return;
				}
				
				hasMnemonicCode = (String) BitcoinDao.class.getConstructor().newInstance()
						.checkIfUserHasBitcoinMnemonicCode(relationshipNo);
				
				if(hasMnemonicCode.equalsIgnoreCase("Y")) {
					fingrePrintFlag = (String) CustomerDao.class.getConstructor().newInstance()
							.checkIfUserHasEnabledFingrePrint(relationshipNo);
					obj.addProperty("error", "false");
					obj.addProperty("hasmnemonic", "true");
					obj.addProperty("fingre_print", fingrePrintFlag);
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
					if (fingrePrintFlag != null)fingrePrintFlag = null;if (tokenValue != null)tokenValue = null;
					if (hasMnemonicCode != null)hasMnemonicCode = null; if (obj != null) obj = null; 
					if(tokenValue!=null) tokenValue=null;if(langPref!=null) langPref=null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Check failed , Try again");
			}
			break;

		case "buy_btc_using_fiat_mbl":
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
				String txnMode=null; String userType=null;String tokenValue=null;String langPref=null;
				List<Transaction> listTxn = null;

				if(request.getParameter("buy_coin_asset")!=null) assetCode = request.getParameter("buy_coin_asset").trim();
				if(request.getParameter("buy_amount")!=null) amount = request.getParameter("buy_amount").trim();
				if(request.getParameter("receivedamountbuy")!=null)	 	amountBTC = request.getParameter("receivedamountbuy").trim();
				if(request.getParameter("comment")!=null)	 payComments = request.getParameter("comment").trim();
				if(request.getParameter("buy_payment_method")!=null) channel = request.getParameter("buy_payment_method").trim();
				if(request.getParameter("relno")!=null) relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

				if(channel.equalsIgnoreCase("T")) {
				if(request.getParameter("tokenid")!=null)	tokenId = request.getParameter("tokenid").trim();
				if(request.getParameter("cvv")!=null)cvv = request.getParameter("cvv").trim();
				}			
				NeoBankEnvironment.setComment(1, className, "amountBTCx "+amountBTC);
				NeoBankEnvironment.setComment(1, className, "amount "+amount);
				NeoBankEnvironment.setComment(3, className, "tokenValue "+tokenValue);
				NeoBankEnvironment.setComment(3, className, "assetCode "+assetCode);
				NeoBankEnvironment.setComment(3, className, "channel "+channel);
				

				//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es válido, vuelva a iniciar sesión");

					}else {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					}
					return;
				}
				
				walletDetails = (String)CustomerPorteCoinDao.class.getConstructor()
						.newInstance().getAssetWalletDetails(relationshipNo, assetCode, "");
				if(walletDetails==null) {
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "error", "Estimado cliente, no tiene la billetera correspondiente, "
								+ "cree una billetera e intente nuevamente");
					}else {
						Utilities.sendJsonResponse(response, "error", "Dear customer you don't have coresponding wallet, "
								+ "please create wallet and try again");					}
					
					return;
				}
				btcWalletId = walletDetails.split(",")[0];
				txnUserCode = Utilities.generateTransactionCode(10);
				if(channel.equalsIgnoreCase("T")) {
					//Debit card here
					//Call Gate way here
					//Get charges 
					NeoBankEnvironment.setComment(3,className,"Channel is "+channel);
					txnPayMode = NeoBankEnvironment.getCodeBuyBTCViaToken();
					
					referenceNo = txnPayMode+ "-" + (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new java.util.Date())
							+ Utilities.genAlphaNumRandom(9);
					customerCharges = (String) SystemUtilsDao.class.getConstructor().newInstance().getChargesApplicable(
							NeoBankEnvironment.getDefaultCustomerUserType(),txnPayMode, amount);
					 minimumTxnAmount=customerCharges.substring(customerCharges.indexOf("|")+1, customerCharges.length());

					
					if ( Double.parseDouble(amount)< Double.parseDouble(minimumTxnAmount)) { 
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "error", "El monto de la transacción no puede ser inferior a "+minimumTxnAmount);
							throw new Exception("El monto de la transacción no puede ser inferior a:- "+minimumTxnAmount+"Amount inputed is"+amount);
						}else {
							Utilities.sendJsonResponse(response, "error", "Transaction amount can not be less than "+minimumTxnAmount);
							throw new Exception("minimumAmount amount inputed:- Minimum exptected is:- "+minimumTxnAmount+"Amount inputed is"+amount);						}
						
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
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "error", "El monto de la transacción no puede ser inferior a "+minimumTxnAmount);

						}else {
							Utilities.sendJsonResponse(response, "error", "Transaction amount can not be less than "+minimumTxnAmount);
						}
						return;
					}
					//Check balance // 
					double senderDebitAmount =  Double.parseDouble(amount) + 
							Double.parseDouble(customerCharges.substring(customerCharges.indexOf(",")+1, customerCharges.indexOf("|")));
					if ( senderDebitAmount> Double.parseDouble(walletBalance)) {
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "error", "Estimado cliente, no tiene fondos suficientes. Por favor, recargue y vuelva a intentarlo.");

						}else {
							Utilities.sendJsonResponse(response, "error", "Dear customer you have insufficient funds, Kindly top up and try again");
						}
						return;
					}
					/****** Wallet Authorization******/
					
					authResponse=(String)WalletAuthorizationDao.class.getConstructor().newInstance().performWalletAuthorization(relationshipNo, amount, txnMode);
					if (authResponse.isEmpty()) {
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo más tarde");

						}else {
							Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
						}
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
							if(langPref.equalsIgnoreCase("ES")) {
								Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo más tarde");

							}else {
								Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
							}
						return;
						}
						     Utilities.sendJsonResponse(response, "authfailed", authMessage);
						return;
					}else {
						// Record successful authorization
						recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, amount, txnMode, currencyId, 
								userType, referenceNo, walletId, authStatus, authMessage);
						if(!recordAuthorization) { 
							if(langPref.equalsIgnoreCase("ES")) {
								Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo más tarde");

							}else {
								Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
							}
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
							txnPayMode," Buy BTCx Using fiat money ");
					listTxn = (List<Transaction>)BitcoinDao.class.getConstructor().
							newInstance().getPendingMobileBTCxTransactions(relationshipNo);
					
					obj.add("error", gson.toJsonTree("false"));
					if(langPref.equalsIgnoreCase("ES")) {
						obj.add("message", gson.toJsonTree("Su transacción de "+assetCode +":"+ amountBTC + 
								"se está procesando te avisaremos una vez finalizada la operación ")); 
					}else {
						obj.add("message", gson.toJsonTree(" Your Transaction of "+assetCode +":"+ amountBTC + 
								" is being processed we will notify you once the operation is done ")); 					}
					
					obj.add("data", gson.toJsonTree(listTxn));

				}else {
					obj.add("error", gson.toJsonTree("true")); 
					if(langPref.equalsIgnoreCase("ES")) {
						obj.add("message", gson.toJsonTree("Transacción fallida")); 

					}else {
						obj.add("message", gson.toJsonTree("Transaction failed")); 

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
					if (user != null)user = null;if (langPref != null)langPref = null; 
					if (walletBalance != null)walletBalance = null; 
					if (walletDetails != null)walletDetails = null; 
					if (fiatWalletId != null)fiatWalletId = null; 
					if (payComments != null)payComments = null; 
					if (referenceNo != null)referenceNo = null; 
					if (cvv != null)cvv = null; if(tokenValue!=null) tokenValue=null;
					if (userType != null)userType = null; 
					if (currencyId!=null); currencyId=null;   if (walletId!=null); walletId=null; if (authResponse != null)authResponse = null;
					if (transactionCode!=null); transactionCode=null;    if (txnMode!=null); txnMode=null; 
					if (listTxn != null)listTxn = null;
				}								
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in coin purchase, Please try again letter");
			}
		break;

		case "transfer_bitcoin_mbl":
			NeoBankEnvironment.setComment(3, className, "Inside transfer_bitcoin_mbl");
			try {
				NeoBankEnvironment.setComment(3,className,"Inside transfer_bitcoin_mbl");
				JsonObject obj = new JsonObject(); boolean recordAuthorization = false; String userType = "C";
				User user = null;  String txnMode = null; String currencyId = null; String transactionCode = null;
				String relationshipNo= null; String  customerCharges = null; String minimumTxnAmount = null;
				PrintWriter output = null; String customerChargesValue = null; String walletDetails = null;
				Gson gson = new Gson(); String walletId = null; String walletBalance = null; String authStatus = null;
				String amount= null; // in BTC 
				String receiverAddress = null;String authMessage = null; boolean success = false; String txnUserCode = null; 
				String payComments="";  String referenceNo=""; String authMethod=null;
				String assetCode = null;  //BTC
				String txnPayMode = NeoBankEnvironment.getBTCP2PTxnCode(); 
				String extSystemRef = null; //BTC Hash
				String senderAdress = null; String hasMnemonic = null;  String password  = null; String mnemoniStringFromDB  = null;
				boolean passIsCorrect = false;  String masterKeyGenerationResult = null; long creationtime = 0; String senderPrivateKey = null;
				String senderPublicKey = null; String senderBTCAddress = null; String btcDetails = null;
				JsonObject createBTCTransactionObj = null; JsonObject signBTCTransactionObj = null;String tokenValue=null;
				String langPref=null;
				if(request.getParameter("sender_asset")!=null) assetCode = request.getParameter("sender_asset").trim();
				if(request.getParameter("input_receiver_address")!=null) receiverAddress = request.getParameter("input_receiver_address").trim();
				if(request.getParameter("sendamount")!=null) amount = request.getParameter("sendamount").trim();
				if(request.getParameter("commet")!=null)	 payComments = request.getParameter("commet").trim();				
				if(request.getParameter("hasMnemonic")!=null)	 hasMnemonic = request.getParameter("hasMnemonic").trim();
				if(request.getParameter("relno")!=null) relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

				//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es válido, vuelva a iniciar sesión");

					}else {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					}
					return;
				}
				if(hasMnemonic.equals("true")) {
					if(request.getParameter("auth_method")!=null)	 authMethod = request.getParameter("auth_method").trim();
					if(authMethod.equals("P")) {
						if(request.getParameter("security")!=null)	 password = request.getParameter("security").trim();
						passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
								.checkIfPasswordIsCorrect(relationshipNo, password);
						if(!passIsCorrect) {
							NeoBankEnvironment.setComment(1, className, "Password is not correct");
							if(langPref.equalsIgnoreCase("ES")) {
								Utilities.sendJsonResponse(response, "error", "Por favor ingrese la contraseña correcta");

							}else {
								Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
							}
							return;
						}
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
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "error", "No tiene suficiente saldo en su billetera Fiat para completar esta transacción. Recargue para continuar");

					}else {
						Utilities.sendJsonResponse(response, "error", "You do not have enough balance In Your Fiat Wallet to complete this transaction. Please top up to proceed");
					}
					return;
				}
					
				/****** Wallet Authorization******/
				String authResponse=(String)WalletAuthorizationDao.class.getConstructor().newInstance().performWalletAuthorization(relationshipNo, customerChargesValue, txnMode);
				if (authResponse.isEmpty()) {
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo más tarde");

					}else {
						Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
					}
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
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo más tarde");

						}else {
							Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
						}
					return;
					}
					     Utilities.sendJsonResponse(response, "authfailed", authMessage);
					return;
				}else {
					// Record successful authorization
					recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, amount, txnMode, currencyId, 
							userType, referenceNo, walletId, authStatus, authMessage);
					if(!recordAuthorization) { 
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo más tarde");

						}else {
							Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
						}
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
					if(langPref.equalsIgnoreCase("ES")) {
						obj.add("message", gson.toJsonTree(" Su transacción de "+assetCode +":"+ amount +  " se está procesando, verifique después de 10 a 15 minutos ")); 

					}else {
						obj.add("message", gson.toJsonTree(" Your Transaction of "+assetCode +":"+ amount +  " is being processed, Please check after 10 to 15 minutes ")); 
					}
				}else {
					obj.add("error", gson.toJsonTree("true")); 
					if(langPref.equalsIgnoreCase("ES")) {
						obj.add("message", gson.toJsonTree("Transacción fallida")); 

					}else {
						obj.add("message", gson.toJsonTree("Transaction failed")); 
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
				if (extSystemRef != null)extSystemRef = null; if(authMethod!=null) authMethod=null;
				if (user != null)user = null;  if (walletBalance != null)walletBalance = null; 
				if (walletDetails != null)walletDetails = null;  if (senderAdress != null)senderAdress = null; 
				if (payComments != null)payComments = null;  if (hasMnemonic != null)hasMnemonic = null; 
				if (password != null)password = null;  if (authStatus != null)authStatus = null; 
				if (customerChargesValue != null)customerChargesValue = null;  if (txnUserCode != null)txnUserCode = null; 
				if (minimumTxnAmount != null)minimumTxnAmount = null;  if (referenceNo != null)referenceNo = null; 
				if (txnMode != null) txnMode = null;  if (currencyId != null)currencyId = null; if (txnPayMode != null)txnPayMode = null;
				if (transactionCode!=null) transactionCode=null;if (userType!=null)userType=null;if(tokenValue!=null) tokenValue=null;
				if (df!=null) df=null;if (amountInUSD!=null)amountInUSD=null;if (langPref!=null)langPref=null;
			}								
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in transfer coin, Please try again letter");
			}
			break;
		case "exchange_btc_with_btcx_mbl":
			try {
				JsonObject obj = new JsonObject(); boolean recordAuthorization = false; String userType = "C";
				User user = null;  String txnMode = null; String currencyId = null; String transactionCode = null;
				String relationshipNo= null; String  customerCharges = null; String minimumTxnAmount = null;
				PrintWriter output = null; String customerChargesValue = null; String walletDetails = null;
				Gson gson = new Gson(); String walletId = null; String walletBalance = null; String authStatus = null;
				String amount= null; // in BTC 
				String tdaBTCAddress = null;String authMessage = null; boolean success = false; String txnUserCode = null; 
				String referenceNo=""; String tokenValue=null;String authMethod=null;
				String sourceAssetCode = null;  //BTC
				String destinatioAssetCode = null;  //BTCx
				String txnPayType = NeoBankEnvironment.getBTCToBTCxSwapCode(); 
				String extSystemRef = null; //BTC Hash
				String senderAdress = null; String hasMnemonic = null;  String password  = null; String mnemoniStringFromDB  = null;
				boolean passIsCorrect = false;  String masterKeyGenerationResult = null; long creationtime = 0; String userPrivateKey = null;
				String userPublicKey = null; String userBTCAddress = null; String btcDetails = null;
				JsonObject createBTCTransactionObj = null; JsonObject signBTCTransactionObj = null;
				String langPref=null;
				List<Transaction> listTxn = null;

				if(request.getParameter("source_asset")!=null) sourceAssetCode = request.getParameter("source_asset").trim();
				if(request.getParameter("destination_asset")!=null) destinatioAssetCode = request.getParameter("destination_asset").trim();
				if(request.getParameter("source_amount")!=null) amount = request.getParameter("source_amount").trim();
				if(request.getParameter("hasMnemonic")!=null)	 hasMnemonic = request.getParameter("hasMnemonic").trim();
				if(request.getParameter("relno")!=null) relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("hasMnemonic")!=null)	 hasMnemonic = request.getParameter("hasMnemonic").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

				//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es válido, vuelva a iniciar sesión");

					}else {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					}
					return;
				}
				
				walletDetails = (String)CustomerPorteCoinDao.class.getConstructor()
						.newInstance().getAssetWalletDetails(relationshipNo, sourceAssetCode, "");
				if(walletDetails==null) {
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "error", "Estimado cliente, no tiene una billetera "+sourceAssetCode+", cree una billetera e intente nuevamente");
					}else {
						Utilities.sendJsonResponse(response, "error", "Dear customer your dont have "+sourceAssetCode+" wallet, "
								+ "please create wallet and try again");					}
					
					return;
				}
				walletDetails = (String)CustomerPorteCoinDao.class.getConstructor()
						.newInstance().getAssetWalletDetails(relationshipNo, destinatioAssetCode, "");
				if(walletDetails==null) {
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "error", "Estimado cliente, no tiene una billetera "+destinatioAssetCode+", cree una billetera e intente nuevamente");
					}else {
						Utilities.sendJsonResponse(response, "error", "Dear customer you don't have "+destinatioAssetCode+" wallet, "
								+ "please create wallet and try again");					}
					return;
				}
				
				if(hasMnemonic.equals("true")) {
					if(request.getParameter("auth_method")!=null)	 authMethod = request.getParameter("auth_method").trim();

					if(authMethod.equals("P")) {
						if(request.getParameter("security")!=null)	 password = request.getParameter("security").trim();
						passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
								.checkIfPasswordIsCorrect(relationshipNo, password);
						if(!passIsCorrect) {
							NeoBankEnvironment.setComment(1, className, "Password is not correct");
							if(langPref.equalsIgnoreCase("ES")) {
								Utilities.sendJsonResponse(response, "error", "Por favor ingrese la contraseña correcta");

							}else {
								Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
							}
							return;
						}
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
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "error", "No tiene suficiente saldo en su billetera Fiat para completar esta transacción. Recargue para continuar");

					}else {
						Utilities.sendJsonResponse(response, "error", "You do not have enough balance In Your Fiat Wallet to complete this transaction. Please topup to proceed");
					}
					return;
				}
					
				/****** Wallet Authorization******/
				String authResponse=(String)WalletAuthorizationDao.class.getConstructor().newInstance().performWalletAuthorization(relationshipNo, customerChargesValue, txnMode);
				if (authResponse.isEmpty()) {
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo más tarde");

					}else {
						Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
					}
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
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo más tarde");

						}else {
							Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
						}
					return;
					}
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo más tarde");

					}else {
						Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
					}
					return;
				}else {
					// Record successful authorization
					recordAuthorization=(boolean)WalletAuthorizationDao.class.getConstructor().newInstance().recordWalletAuthorizationResult(relationshipNo, amount, txnMode, currencyId, 
							userType, referenceNo, walletId, authStatus, authMessage);
					if(!recordAuthorization) { 
						if(langPref.equalsIgnoreCase("ES")) {
							Utilities.sendJsonResponse(response, "authfailed", "Error en la autorización, inténtalo de nuevo más tarde");

						}else {
							Utilities.sendJsonResponse(response, "authfailed", "Error in authorization, please try again later");
						}
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
					listTxn = (List<Transaction>)BitcoinDao.class.getConstructor().newInstance().getPendingBTCToBTCxSwapping(relationshipNo);

					obj.add("error", gson.toJsonTree("false"));
					if(langPref.equalsIgnoreCase("ES")) {
						obj.add("message", gson.toJsonTree(" Su transacción de "+sourceAssetCode +":"+ amount +  " se está procesando, le notificaremos una vez que se realice la transacción")); 

					}else {
						obj.add("message", gson.toJsonTree(" Your Transaction of "+sourceAssetCode +":"+ amount +  " is being processed, We will notify you once the transaction is done")); 
					}
					obj.add("data", gson.toJsonTree(listTxn));

				}else {
					obj.add("error", gson.toJsonTree("true")); 
					if(langPref.equalsIgnoreCase("ES")) {
						obj.add("message", gson.toJsonTree("Transacción fallida")); 

					}else {
						obj.add("message", gson.toJsonTree("Transaction failed")); 
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
				if (transactionCode!=null) transactionCode=null;if (userType!=null)userType=null; if(authMethod!=null) authMethod=null;
				if (destinatioAssetCode!=null) destinatioAssetCode=null;if(tokenValue!=null) tokenValue=null;
				if (df!=null) df=null;if (amountInUSD!=null)amountInUSD=null;if (langPref!=null)langPref=null;
				if (listTxn != null)listTxn = null;
			}								
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in transfer coin, Please try again letter");
			}
			
			break;

		case "retrieval_btc_private_key_mbl":
			try {
				NeoBankEnvironment.setComment(3, className, "retrieval_btc_private_key_mbl");
				char[] mnemonic = null; String relationshipNo = null;String tokenValue = null;
				String keyPair = null;  String langPref=null;
				String password = null; String mnemoniStringFromUser = null; //input by the user
				String mnemoniStringFromDB = null; //From BC/DB 
				boolean passIsCorrect = false;
				boolean mnemonicMatches = false;
				JsonObject obj = new JsonObject();
				PrintWriter out_json = response.getWriter();
				long time = System.currentTimeMillis();
				if(request.getParameter("password")!=null)		password = request.getParameter("password").trim();
				if(request.getParameter("mnemonic")!=null)		mnemoniStringFromUser = request.getParameter("mnemonic").trim();
				if(request.getParameter("relno")!=null)		relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue+"mnemoniStringFromUser "+mnemoniStringFromUser);
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

				//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es válido, vuelva a iniciar sesión");

					}else {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					}
					return;
				}
				//
				passIsCorrect = (boolean) CustomerDao.class.getConstructor().newInstance()
						.checkIfPasswordIsCorrect(relationshipNo, password);
				
				if(!passIsCorrect) {
					NeoBankEnvironment.setComment(1, className, "Password is not correct");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "error", "Por favor ingrese la contraseña correcta");

					}else {
						Utilities.sendJsonResponse(response, "error", "Please enter the correct password");
					}
					return;
				}
				NeoBankEnvironment.setComment(3,className,"Comparinng mnemonic code");
				mnemoniStringFromDB = (String) BitcoinDao.class.getConstructor().newInstance()
						.getmnemonicCode(relationshipNo);
				//mnemoniStringFromUser = mnemoniStringFromUser.replaceAll(",", "");
				NeoBankEnvironment.setComment(3,className,"mnemoniStringFromDB is "+mnemoniStringFromDB);
				//Compare mnemonic
				mnemonicMatches = Bip39Utility.compareMnemonic(mnemoniStringFromUser, mnemoniStringFromDB);
				
				if(!mnemonicMatches) {
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponse(response, "error", "Mnemónico no coincide");

					}else {
						Utilities.sendJsonResponse(response, "error", "Mnemonic does not match");
					}
					return;
				}
				//Get keys from bit 39
				//keyPair=BitcoinUtility.bitcoinMasterKeyGenerationFromSeed(mnemoniStringFromDB.replaceAll(",", " "));
				//keyPair= Bip39Utility.masterKeyGeneration(mnemoniStringFromDB.replaceAll(",", " "));
				keyPair=BitcoinUtility.bitcoinMasterKeyGenerationFromSeed(mnemoniStringFromDB.replaceAll(",", " "), time);
				NeoBankEnvironment.setComment(3, className, "keyPair is "+keyPair);
				String[] keypairValues = keyPair.split(",");
				if(keyPair==null) {
					if(langPref.equalsIgnoreCase("ES")) {
						throw new Exception("Error al obtener claves");

					}else {
						throw new Exception("Error in getting keys");
					}
				}
				//send response
				obj.addProperty("public_key",keypairValues[2] );
				obj.addProperty("private_key",keypairValues[1] );
				obj.addProperty("btc_address",keypairValues[0] );
				obj.addProperty("error", "false");
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + (obj.toString()));
					out_json = response.getWriter();
					out_json.print((obj));
				} finally {
					if (out_json != null)out_json.close(); if (relationshipNo != null)relationshipNo = null; 
					if (mnemoniStringFromDB != null)mnemoniStringFromDB = null; if (obj != null) obj = null;
					if (password != null) password = null; if (mnemoniStringFromUser != null) mnemoniStringFromUser = null; 
					if (mnemonic != null) mnemonic = null; if (tokenValue != null)tokenValue = null;
					if (langPref != null) langPref = null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Failed to retreve keys, Try again");
			}
		
		break;
		case "get_last_x_bitcoin_txn":
			try {
				JsonObject obj = new JsonObject();
				User user = null; String tokenValue = null;
				String relationshipNo= null;
				PrintWriter output = null;
				Gson gson = new Gson();String langPref=null;
				String txnDesc= null;
				if(request.getParameter("relno")!=null)	 relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

				//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es válido, vuelva a iniciar sesión");

					}else {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					}
					return;
				}
				String btcAddress = (String) BitcoinDao.class.getConstructor().newInstance()
						.getBTCAddress(relationshipNo);
				JsonObject txnObj = BitcoinUtility.getBTCAccountTxn(btcAddress, "20");
				ArrayList<BTCTransction> arrTransactions = BitcoinUtility.processTrnsactions(txnObj, btcAddress);
				NeoBankEnvironment.setComment(3,className,"arrTransactions is "+arrTransactions.size());
				
				obj.add("data", gson.toJsonTree(arrTransactions));
				obj.add("error", gson.toJsonTree("false"));
				
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));
				} finally {
					if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
					if (obj != null)obj = null; if (tokenValue != null)tokenValue = null;
					if (arrTransactions != null)arrTransactions = null; 
					if (user != null)user = null; if(tokenValue!=null) tokenValue=null;
					if (txnDesc != null)txnDesc = null; 
					if (langPref != null)langPref = null; 
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in geting Transactions, Please try again letter");
			}
			break;
			
		case "get_mbl_bitcoin_wallet_details":
			try {
				NeoBankEnvironment.setComment(3,className,"Inside get_mbl_bitcoin_wallet_details");
				JsonObject obj = new JsonObject();
				PrintWriter output = null;
				Gson gson = new Gson();String langPref=null;
				User user = null;
				String relationshipNo = null;KeyPair assetKeyPair = null;
				ArrayList<AssetAccount> assetCoinDetails = null;
				String tokenValue = null;
				if(request.getParameter("relno")!=null)	 relationshipNo = request.getParameter("relno").trim();
				if(request.getParameter("token")!=null)	tokenValue = StringUtils.trim(request.getParameter("token"));
				if(request.getParameter("hdnlangpref")!=null)			langPref = request.getParameter("hdnlangpref").trim();

				//NeoBankEnvironment.setComment(3, className, "Token is "+tokenValue);
				if(!Utilities.compareMobileToken(relationshipNo, tokenValue)) {
					NeoBankEnvironment.setComment(1, className, "Error in rule: "+rulesaction+" is invalid token");
					if(langPref.equalsIgnoreCase("ES")) {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "El valor del token no es válido, vuelva a iniciar sesión");

					}else {
						Utilities.sendJsonResponseOfInvalidToken(response, "invalid", "Token value is invalid, please login again");
					}
					return;
				}
				String btcAddress = (String) BitcoinDao.class.getConstructor().newInstance()
						.getBTCAddress(relationshipNo);
				if (btcAddress!=null) {
					
				
				JsonObject txnObj = BitcoinUtility.getBTCAccountTxn(btcAddress, "120");

				obj.add("publickey", gson.toJsonTree(btcAddress));
				obj.add("data", gson.toJsonTree(BitcoinUtility.filterAccountDetailsFromTransactionObj(txnObj) ));
				obj.add("error", gson.toJsonTree("false"));
				}else {
					obj.add("error", gson.toJsonTree("doesnotexist"));
				}
				
				try {
					NeoBankEnvironment.setComment(3, className,rulesaction+" String is " + gson.toJson(obj));
					output = response.getWriter();
					output.print(gson.toJson(obj));
				} finally {
					if (output != null)output.close(); if (relationshipNo != null)relationshipNo = null; if (gson != null)gson = null;
					if (obj != null)obj = null; if (tokenValue != null)tokenValue = null;
					if (assetCoinDetails != null)assetCoinDetails = null; 
					if (user != null)user = null; if(tokenValue!=null) tokenValue=null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rulesaction+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Error in geting porte coins, Please try again letter");
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
		
		
		
		}
		
	}

}
