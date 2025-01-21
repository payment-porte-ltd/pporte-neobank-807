package com.pporte.test;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDPath;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pporte.model.BTCTransction;
import com.pporte.utilities.BitcoinUtility;

public class BitcoinTest {
	
	
	public static void main(String[] args) throws Exception {
		//createBitcoinDeterministicWallet();
//		getBTCAccountBalance();
//		bitcoinMasterKeyGenerationFromSeed();
//		createTransaction();
//		processTrnsactions(getBTCAccountTxn("tb1q6rcdc2fezd0le3nucp8tgfgrvxnl0mg4qtjc44", "100"),
//				"tb1q6rcdc2fezd0le3nucp8tgfgrvxnl0mg4qtjc44");
		
		String tosignParam="d909657345ebe01dd2339f0d7b8f7bb69d7fbb0a089d751930f49b548f4729dd";
		String privateKey="6257d27afe2b1297d8430bfe5d57b46e919e24aa3cc1a7fd19fb3d8702bf1eae";
		String signedKey=BitcoinUtility.sign(tosignParam, privateKey);
		System.out.print(signedKey);
	}
	
	public static void createBitcoinDeterministicWallet() {
		try {
			NetworkParameters params = TestNet3Params.get();
			Wallet deteministicWallet = Wallet.createDeterministic(params,Script.ScriptType.P2WPKH);
	
			DeterministicSeed seed = deteministicWallet.getKeyChainSeed();
			System.out.println("seed: " + seed.toString());
	
			System.out.println("creation time: " + seed.getCreationTimeSeconds());
			System.out.println("mnemonicCode "+seed.getMnemonicCode());
			List<String> nmemonicWords = seed.getMnemonicCode();
			//System.out.println("mnemonicCode: " + Utils.SPACE_JOINER(seed.getMnemonicCode()));
			//Utils.SPACE_JOINER
			// The wallet class provides a easy fromSeed() function that loads a new wallet
			// from a given seed.
			Wallet wallet = Wallet.fromSeed(params, seed, Script.ScriptType.P2WPKH);
			System.out.println("Receiver address is " + wallet.currentReceiveAddress());
			
			// BitcoinJ
			DeterministicKeyChain chain = DeterministicKeyChain.builder().seed(seed).build();
			List<ChildNumber> keyPath = HDPath.parsePath("M/1H/0/0");
			DeterministicKey key = chain.getKeyByPath(keyPath, true);
			String privKey = key.getPrivateKeyAsHex();
			String pubKey = key.getPublicKeyAsHex();
			//System.out.println("public key not hex: "+key.getPubKey());
			//System.out.println("private key not hex: "+key.getPrivKey());
	
			System.out.println("Private key:- " + privKey);
			System.out.println("Public Key Hex "+key.getPublicKeyAsHex());
	
			System.out.println("Wallet details are "+wallet.toString());

		}catch (Exception e) {
			System.out.println("Error in creating wallet");
		}
	}
	
	public static void bitcoinMasterKeyGenerationFromSeed(){
		try {
			NetworkParameters params = TestNet3Params.get();
			//Here we restore our wallet from a seed with no passphrase. Also have a look at the BackupToMnemonicSeed.java example that shows how to backup a wallet by creating a mnemonic sentence.
			 String seedCode = "want number detail movie nut street connect famous gloom profit provide weather";
			 String passphrase = "";
			 Long creationtime = 1409478661L;
			 
			 DeterministicSeed seed = new DeterministicSeed(seedCode, null, passphrase, 0);
			 
			 // The wallet class provides a easy fromSeed() function that loads a new wallet from a given seed.
			 Wallet wallet = Wallet.fromSeed(params, seed, Script.ScriptType.P2WPKH);
			 System.out.println("Receiver address is " + wallet.currentReceiveAddress().toString());
			 
			 DeterministicKeyChain chain = DeterministicKeyChain.builder().seed(seed).build();
			 List<ChildNumber> keyPath = HDPath.parsePath("M/1H/0/0");
			 DeterministicKey key = chain.getKeyByPath(keyPath, true);
			 String privKey = key.getPrivateKeyAsHex();
//			 //System.out.println("public key not hex: "+key.getPubKey());
//			 //System.out.println("private key not hex: "+key.getPrivKey());
//	
			 System.out.println("Private key:- " + privKey);
			 System.out.println("Public Key Hex "+key.getPublicKeyAsHex());
			 
			
		} catch (Exception e) {
			System.out.println("Error in Master Wallet Generation");
		}
		
	}
	
	public static NetworkParameters getBitcoinEnviromentNetwork() {
		NetworkParameters params = null;
		try {
			String enviromentVariable = "";
			if (enviromentVariable.equals("testnet")) {
			    params = TestNet3Params.get();
			} else if (enviromentVariable.equals("regtest")) {
			    params = RegTestParams.get();
			} else {
			    params = MainNetParams.get();
			}
		} catch (Exception e) {
			System.out.println("Error in Master Wallet Generation");
		}
		return params;
	}
	
	public static void createNewTransaction() {
		try {
			JsonArray addressJsonArray = new JsonArray();
			addressJsonArray.add("bc1qaw07s7kg4pq2gfjka4fdt04e4eftnq6y5frv39");
			JsonObject objOutputs = new JsonObject();
			objOutputs.addProperty("value", 1000);
			objOutputs.add("addresses", addressJsonArray);
			JsonArray jsonArrayOutputs = new JsonArray();
			jsonArrayOutputs.add(jsonArrayOutputs);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public static String createTransaction() throws Exception{
		String result="false";
		try {
			JsonArray addressJsonArray = new JsonArray();
			addressJsonArray.add("tb1qsehy2rp6eehuuu9lvluh9j9qa4xyfq38s0twlj");
			JsonObject objOutputs = new JsonObject();
			objOutputs.addProperty("value", 10);
			objOutputs.add("addresses", addressJsonArray);
			JsonArray jsonArrayOutputs = new JsonArray();
			jsonArrayOutputs.add(objOutputs);
	
			JsonArray addressJsonArrayInput= new JsonArray();
			addressJsonArrayInput.add("tb1qhtsz7fng0ghlln335ykt3hzsyunaugcaz4z0l2");
			JsonObject objInputs = new JsonObject();
			objInputs.add("addresses", addressJsonArrayInput);
			JsonArray jsonArrayInputs = new JsonArray();
			jsonArrayInputs.add(objInputs);
	
			JsonObject obj = new JsonObject();
			obj.add("inputs", jsonArrayInputs);
			obj.add("outputs", jsonArrayOutputs);
			
			System.out.println("obj "+obj);
//		String path = "/claimable_balances/";
//			String path = "/v1/btc/test3/txs/new";
			CloseableHttpClient client = HttpClients.createDefault();
		    HttpPost httpPost = new HttpPost("https://api.blockcypher.com/v1/btc/test3/txs/new");
		    List<NameValuePair> params = new ArrayList<NameValuePair>();
		    params.add(new BasicNameValuePair("token", "eeb923acd325422883003b64a914938b"));
		    httpPost.setEntity(new UrlEncodedFormEntity(params));
		    StringEntity entity = new StringEntity(String.valueOf(obj));
		    httpPost.setEntity(entity);
		    CloseableHttpResponse response = client.execute(httpPost);
		    System.out.println("Response code is "+response.getCode());
//		    System.out.println("response body is "+response.);
		    String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
		    System.out.println("response body is "+responseBody);
			
		try {

		} finally {
		// TODO: handle finally clause
		}
		} catch (Exception e) {
			System.out.println("Error in createTransaction is "+e.getMessage());
//		NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
//		throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}
		return result;
		}
	
	public static void getBTCTxn() throws Exception{
		try {
			String path = "/v1/btc/test3/addrs/tb1q6rcdc2fezd0le3nucp8tgfgrvxnl0mg4qtjc44";
			URIBuilder builder = new URIBuilder();
			builder.setScheme("https").setHost("api.blockcypher.com").setPath(path)
			.setParameter("limit", "100");
			URI uri = builder.build();
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(uri);
			CloseableHttpResponse response = client.execute(httpGet);
			System.out.println("Response "+response.getCode());
			String result = EntityUtils.toString(response.getEntity());			
			System.out.println("result "+result);
			
		}catch (Exception e) {
			System.out.println("Error in getBTCTxn is "+e.getMessage());
		}
		
	}
	
	public static void getBTCAccountBalance() throws Exception{
		try {
			String path = "/v1/btc/test3/addrs/tb1q6rcdc2fezd0le3nucp8tgfgrvxnl0mg4qtjc44/balance";
			URIBuilder builder = new URIBuilder();
			builder.setScheme("https").setHost("api.blockcypher.com").setPath(path);
			URI uri = builder.build();
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(uri);
			CloseableHttpResponse response = client.execute(httpGet);
			System.out.println("Response "+response.getCode());
			String result = EntityUtils.toString(response.getEntity());			
			System.out.println("result "+result);
			
		}catch (Exception e) {
			System.out.println("Error in getBTCTxn is "+e.getMessage());
		}
		
	}
	
	 public static ArrayList<BTCTransction> processTrnsactions(JsonObject obj, String userBTCAddress) throws Exception{
		 ArrayList<BTCTransction> arrBTCTxn = null;
		 try {
			 System.out.println( "obj is "+obj.toString());
			 JsonArray txnArray = obj.get("txs").getAsJsonArray();
			 System.out.println( "txnArray is "+txnArray.toString());
			 JsonObject txnObj = null;
			 BTCTransction btcTxn = null;
			 JsonArray outputs = null;
			 JsonObject outputObj = null;
			 JsonArray inputs = null;
			 JsonObject inputObj = null;
			 JsonArray inputsAddress = null;
			 JsonArray outputAddress = null;
			 String sourceAddress = null;
//			 String destinationAddress = null;
			 if(txnArray.size()>0) {
				 arrBTCTxn = new ArrayList<BTCTransction>();
				 for(int i = 0; i<txnArray.size(); i++) {
					 btcTxn = new BTCTransction();
					 txnObj = txnArray.get(i).getAsJsonObject();	
					 btcTxn.setHash(txnObj.get("hash").getAsString());
					 System.out.println( "Step 1 ");
					 btcTxn.setConfirmations(txnObj.get("confirmations").getAsInt());
					 System.out.println( "Step 2 ");
					 btcTxn.setDoubleSpend(txnObj.get("double_spend").getAsBoolean());
					 System.out.println( "Step 3 ");
					 btcTxn.setTxnDateTime(txnObj.get("received").getAsString());
					 System.out.println( "Step 4 ");
					 btcTxn.setTxnFees(txnObj.get("fees").getAsString());
					 System.out.println( "Step 5 ");
					 int blockIndex = txnObj.get("block_index").getAsInt();
					 System.out.println(" blockIndex "+blockIndex);
					 if(blockIndex == -1) {
						 btcTxn.setConfirmedStatus("UNCONFIRMED");
					 }else {
						 btcTxn.setConfirmedStatus(txnObj.get("confirmations").getAsInt()+" Confirmations");
					 }
					 System.out.println( "Step 6 ");
					 outputs = txnObj.get("outputs").getAsJsonArray();
					 System.out.println( "Step 7 ");
					 inputs = txnObj.get("inputs").getAsJsonArray();
					 System.out.println( "Step 8 ");
					 for(int j = 0; j<inputs.size(); j++) {
						 inputObj = inputs.get(j).getAsJsonObject();
						 inputsAddress=inputObj.get("addresses").getAsJsonArray();
						 System.out.println("Step 9 ");
						 sourceAddress = inputsAddress.get(0).getAsString();
					 }
					 if(userBTCAddress.equals(sourceAddress)) {
						 btcTxn.setTxnMode("D");
						 for(int k = 0; k<outputs.size(); k++) {
							 outputObj = outputs.get(k).getAsJsonObject();
							 outputAddress=outputObj.get("addresses").getAsJsonArray();
							 System.out.println( "Step 10 ");
							 if(sourceAddress.equals(outputAddress.get(0).getAsString())) {
								 btcTxn.setTxnAmaount(outputObj.get("value").getAsString());
								 System.out.println( "Step 11 ");
							 }else {
								 btcTxn.setDestinatioAddress(outputAddress.get(0).getAsString());
							 }
						 }
					 }else {
						 btcTxn.setTxnMode("C");
						 for(int k = 0; k<outputs.size(); k++) {
							 outputObj = outputs.get(k).getAsJsonObject();
							 outputAddress=outputObj.get("addresses").getAsJsonArray();
							 System.out.println( "Step 12 ");
							 if(userBTCAddress.equals(outputAddress.get(0).getAsString())) {
								 btcTxn.setTxnAmaount(outputObj.get("value").getAsString());
								 System.out.println( "Step 13 ");
							 }else {
								 btcTxn.setSourceAddress((outputAddress.get(0).getAsString()));
							 }
						 }
					 }
					 System.out.println("Transction mode is "+btcTxn.getTxnMode());
					 arrBTCTxn.add(btcTxn);
				 }
			 }
			 try {
				
			} finally{
				// TODO: Clear all objects here
			}
			
		} catch (Exception e) {
			arrBTCTxn = null;
			System.out.println("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}
		 return arrBTCTxn;
	 }
	 
	 public static JsonObject getBTCAccountTxn(String btcAddress, String limit) throws Exception{
		 JsonObject obj = null;
			try {
				//String baseURL = NeoBankEnvironment.getBlockCypherBaseUrl();
				String path = "/v1/btc/test3/addrs/"+btcAddress+"/full";
				URIBuilder builder = new URIBuilder();
				builder.setScheme("https").setHost("api.blockcypher.com").setPath(path)
				.setParameter("limit", limit);
				URI uri = builder.build();
				CloseableHttpClient client = HttpClients.createDefault();
				HttpGet httpGet = new HttpGet(uri);
				CloseableHttpResponse response = client.execute(httpGet);
				System.out.println("Response "+response.getCode());
				String result = EntityUtils.toString(response.getEntity());			
				System.out.println("result "+result);
				if(response.getCode()!=200) {
					System.out.println( "response body is: \n"+result);
					throw new Exception ("Problem in Getting transaction API, Error code "+response.getCode());
				}
				try {
					obj = new Gson().fromJson(result, JsonObject.class);
				} finally {
					 if(path!=null)path=null; if(builder!=null)builder=null;
					if(uri!=null)uri=null; if(client!=null)client.close(); if(httpGet!=null)httpGet.clear();
					if(response!=null)response.close(); if(result!=null)result=null; 
				}
			}catch (Exception e) {
				obj = null;
				System.out.println("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
				throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			}
			return obj;
		}
	
	

}
