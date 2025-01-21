package com.pporte.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.commons.codec.binary.Hex;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import java.util.ArrayList;
import java.util.Date;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDPath;
import org.bitcoinj.core.Address;
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
import com.pporte.NeoBankEnvironment;
import com.pporte.model.BTCTransction;
import com.pporte.model.BitcoinDetails;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.DERSequenceGenerator;
import org.bouncycastle.asn1.sec.SECNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.crypto.signers.HMacDSAKCalculator;
import org.bouncycastle.util.encoders.DecoderException;

public class BitcoinUtility {
	private static String className = BitcoinUtility.class.getSimpleName();
	public static final X9ECParameters KOBLITZ_CURVE = SECNamedCurves.getByName("secp256k1");
	public static final BigInteger HALF_CURVE_ORDER = KOBLITZ_CURVE.getN().shiftRight(1);
	
	public static NetworkParameters getBitcoinEnviromentNetwork() throws Exception {
		NetworkParameters params = null;
		try {
			String enviromentVariable = NeoBankEnvironment.getBitCoinEnviroment();
			if (enviromentVariable.equals("testnet")) {
			    params = TestNet3Params.get();
			} else if (enviromentVariable.equals("regtest")) {
			    params = RegTestParams.get();
			} else {
			    params = MainNetParams.get();
			}
			try {
				
			}finally {
				if(enviromentVariable!=null) enviromentVariable = null;
			}
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}
		return params;
	}
	
	public static String bitcoinMasterKeyGenerationFromSeed(String seedCode, long creationtime ) throws Exception{
		String returnValue = "";
		try {
			NetworkParameters params = getBitcoinEnviromentNetwork();
			//Here we restore our wallet from a seed with no passphrase. Also have a look at the BackupToMnemonicSeed.java example that shows how to backup a wallet by creating a mnemonic sentence.
			 String passphrase = "";
			 DeterministicSeed seed = new DeterministicSeed(seedCode, null, passphrase, creationtime);
			 // The wallet class provides a easy fromSeed() function that loads a new wallet from a given seed.
			 Wallet wallet = Wallet.fromSeed(params, seed, Script.ScriptType.P2WPKH);
			 Address address = wallet.currentReceiveAddress();
			 String stringAddress = address.toString();
			 DeterministicKeyChain chain = DeterministicKeyChain.builder().seed(seed).build();
			 List<ChildNumber> keyPath = HDPath.parsePath("M/1H/0/0");
			 DeterministicKey key = chain.getKeyByPath(keyPath, true);
			 String privKey = key.getPrivateKeyAsHex();
			 String pubKey = key.getPublicKeyAsHex();
			try {
				//Create a CSV of address Public key, Private key and Address
				returnValue = stringAddress+","+privKey+","+pubKey;
			} finally {
				if(params!=null) params = null; if(passphrase!=null) passphrase = null; if(seed!=null) seed = null;
				if(wallet!=null) wallet = null; if(address!=null) address = null; if(stringAddress!=null) stringAddress = null;
				if(chain!=null) chain = null; if(keyPath!=null) keyPath = null; if(key!=null) key = null;
				if(privKey!=null) privKey = null; if(pubKey!=null) pubKey = null; 
			}
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}
		return returnValue;
		
	}
	
	public static JsonObject createBitcoinDeterministicWallet() throws Exception {
		JsonObject data = null;
		try {
			NetworkParameters params = getBitcoinEnviromentNetwork();
			Wallet deteministicWallet = Wallet.createDeterministic(params,Script.ScriptType.P2WPKH);
			Gson gson = new Gson();
			DeterministicSeed seed = deteministicWallet.getKeyChainSeed();
			NeoBankEnvironment.setComment(3,className,"seed: " + seed.toString());
			NeoBankEnvironment.setComment(3,className,"mnemonicCode "+seed.getMnemonicCode());
			List<String> nmemonicWords = seed.getMnemonicCode();
			Long creationTime = seed.getCreationTimeSeconds();
			
			// The wallet class provides a easy fromSeed() function that loads a new wallet
			// from a given seed.
			Wallet wallet = Wallet.fromSeed(params, seed, Script.ScriptType.P2WPKH);
			Address address = wallet.currentReceiveAddress();
			String stringAddress = address.toString();
	
			DeterministicKeyChain chain = DeterministicKeyChain.builder().seed(seed).build();
			List<ChildNumber> keyPath = HDPath.parsePath("M/1H/0/0");
			DeterministicKey key = chain.getKeyByPath(keyPath, true);
			String privKey = key.getPrivateKeyAsHex();
			String pubKey = key.getPublicKeyAsHex();
			try {
				data = new JsonObject();
				data.addProperty("creation_time", creationTime);
				data.addProperty("address", stringAddress);
				data.addProperty("public_key", pubKey);
				data.addProperty("private_key", privKey);
				data.add("mnemonic_code", gson.toJsonTree(nmemonicWords));
			} finally {
				if(params!=null) params = null; if(deteministicWallet!=null) deteministicWallet = null; if(seed!=null) seed = null;
				if(wallet!=null) wallet = null; if(address!=null) address = null; if(stringAddress!=null) stringAddress = null;
				if(chain!=null) chain = null; if(keyPath!=null) keyPath = null; if(key!=null) key = null;
				if(privKey!=null) privKey = null; if(pubKey!=null) pubKey = null; if(gson!=null) gson = null;
			}

		}catch (Exception e) {
			data = null;
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}
		return data;
	}
	
	
	public static JsonObject createTransaction(String sourceAccountBTCAddress, String destinationAccountAddress, String amount) throws Exception{
		JsonObject objReturn = null;
		NeoBankEnvironment.setComment(3, className, "sourceAccountBTCAddress "+sourceAccountBTCAddress);
		NeoBankEnvironment.setComment(3, className, "destinationAccountAddress "+destinationAccountAddress);
		NeoBankEnvironment.setComment(3, className, "amount "+amount);
		try {

			JsonArray addressJsonArray = new JsonArray();
			//addressJsonArray.add("tb1qsehy2rp6eehuuu9lvluh9j9qa4xyfq38s0twlj");
			addressJsonArray.add(destinationAccountAddress);
			JsonObject objOutputs = new JsonObject();

			objOutputs.addProperty("value", Math.round(Float.parseFloat(Utilities.convertBTCToSatoshi(amount))));//Convert BTC to Satoshi
			objOutputs.add("addresses", addressJsonArray);
			JsonArray jsonArrayOutputs = new JsonArray();
			jsonArrayOutputs.add(objOutputs);
	
			JsonArray addressJsonArrayInput= new JsonArray();
			//addressJsonArrayInput.add("tb1qhtsz7fng0ghlln335ykt3hzsyunaugcaz4z0l2");
			addressJsonArrayInput.add(sourceAccountBTCAddress);
			JsonObject objInputs = new JsonObject();
			objInputs.add("addresses", addressJsonArrayInput);
			JsonArray jsonArrayInputs = new JsonArray();
			jsonArrayInputs.add(objInputs);
	
			JsonObject obj = new JsonObject();
			obj.add("inputs", jsonArrayInputs);
			obj.add("outputs", jsonArrayOutputs);
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(NeoBankEnvironment.getBitCoinCreateTransactionBlockCypherUrl());
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("token", NeoBankEnvironment.getBitCoinBlockCypherAPIToken()));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			StringEntity entity = new StringEntity(String.valueOf(obj));
			httpPost.setEntity(entity);
			CloseableHttpResponse response = client.execute(httpPost);
			
			if(response.getCode()!=201) {
				NeoBankEnvironment.setComment(3,className,"response body is: \n "+ EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
				throw new Exception ("Problem in creating transaction API, Error code "+response.getCode());
			}
			NeoBankEnvironment.setComment(3,className,"Response code is "+response.getCode());
			
			String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			NeoBankEnvironment.setComment(3,className,"responseBody is "+responseBody);
		try {
			objReturn = new Gson().fromJson(responseBody, JsonObject.class);
		} finally {
			if(addressJsonArray!=null) addressJsonArray = null; if(objOutputs!=null) objOutputs = null; 
			if(jsonArrayOutputs!=null) jsonArrayOutputs = null; if(addressJsonArrayInput!=null) addressJsonArrayInput = null; 
			if(objInputs!=null) objInputs = null; if(jsonArrayInputs!=null) jsonArrayInputs = null; 
			if(obj!=null) obj = null; if(client!=null) client.close(); 
			if(httpPost!=null) httpPost = null; if(params!=null) params=null;
			if(entity!=null) entity.close(); if(response!=null) response.close();
		}
		
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}
			return objReturn;
	}
	public static JsonObject signTransaction(JsonObject obj, String privateKey, String publicKey ) throws Exception {
		JsonObject objReturn = null;
		try {
			JsonArray toSignJArray = obj.get("tosign").getAsJsonArray();
			JsonArray signatureJArray = new JsonArray();
			JsonArray publicKeyJArray = new JsonArray();
			for(int i =0; i<toSignJArray.size(); i++) {
				signatureJArray.add(sign(toSignJArray.get(i).toString().replaceAll("\"",""),privateKey)+"01");
				publicKeyJArray.add(publicKey);
			}
			
			
			obj.add("signatures", signatureJArray);
			obj.add("pubkeys", publicKeyJArray);

			//NeoBankEnvironment.setComment(3,className, "obj is "+obj);
			
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(NeoBankEnvironment.getBitCoinSignTransactionBlockCypherUrl());
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("token", NeoBankEnvironment.getBitCoinBlockCypherAPIToken()));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			StringEntity entity = new StringEntity(String.valueOf(obj));
			httpPost.setEntity(entity);
			CloseableHttpResponse response = client.execute(httpPost);
			NeoBankEnvironment.setComment(3,className, "Response code is "+response.getCode());
			if(response.getCode()!=201) {
				//NeoBankEnvironment.setComment(3,className, "response body is: \n"+EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
				throw new Exception ("Problem in Sign transaction API, Error code "+response.getCode());
			}
			String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			//NeoBankEnvironment.setComment(3,className, "response body is: \n"+responseBody);
			try {
				objReturn = new Gson().fromJson(responseBody, JsonObject.class);
			} finally {
				if(toSignJArray!=null) toSignJArray = null;  
				if(signatureJArray!=null) signatureJArray = null; 
				if(publicKeyJArray!=null) publicKeyJArray = null; if(responseBody!=null) responseBody = null; 
				if(obj!=null) obj = null; if(client!=null) client.close(); 
				if(httpPost!=null) httpPost = null; if(params!=null) params=null;
				if(entity!=null) entity.close(); if(response!=null) response.close();
			}
			
			
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}
		return objReturn;
	}
	/*public static String sign(String message, String privateKey)
			throws Exception {
		ByteArrayOutputStream byteArrayOutputStream = null;
		try {
			ECDomainParameters params = new ECDomainParameters(KOBLITZ_CURVE.getCurve(), KOBLITZ_CURVE.getG(), KOBLITZ_CURVE.getN());
			ECDSASigner signer = new ECDSASigner(new HMacDSAKCalculator(new SHA256Digest()));
			signer.init(true, new ECPrivateKeyParameters(new BigInteger(privateKey, 16), params));
			BigInteger[] signature = signer.generateSignature(Hex.decodeHex(message.toCharArray()));
			byteArrayOutputStream = new ByteArrayOutputStream();
			DERSequenceGenerator seq = new DERSequenceGenerator(byteArrayOutputStream);
			seq.addObject(new ASN1Integer(signature[0]));
			try {
				 seq.addObject(new ASN1Integer(toCanonicalS(signature[1])));
				 seq.close();
			}finally {
				 if(params!=null)params=null;  if(signer!=null)signer=null;  if(signature!=null)signature=null;
				 if(seq!=null)seq.close();
			}
				 
		}catch (Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}
		 
		 return new String(Hex.encodeHex(byteArrayOutputStream.toByteArray()));
	 }*/
	//TODO check which objects we can clear here
	public static String sign(String message, String privateKey)throws DecoderException, IOException, org.apache.commons.codec.DecoderException {
		 ECDomainParameters params = new ECDomainParameters(KOBLITZ_CURVE.getCurve(), KOBLITZ_CURVE.getG(), KOBLITZ_CURVE.getN());
		 ECDSASigner signer = new ECDSASigner(new HMacDSAKCalculator(new SHA256Digest()));
		 signer.init(true, new ECPrivateKeyParameters(new BigInteger(privateKey, 16), params));
		 BigInteger[] signature = signer.generateSignature(Hex.decodeHex(message.toCharArray()));
			 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			 DERSequenceGenerator seq = new DERSequenceGenerator(byteArrayOutputStream);
			 seq.addObject(new ASN1Integer(signature[0]));
			 seq.addObject(new ASN1Integer(toCanonicalS(signature[1])));
			 seq.close();
		 return new String(Hex.encodeHex(byteArrayOutputStream.toByteArray()));
	 }
	
	
	 private static BigInteger toCanonicalS(BigInteger s) {
		 if (s.compareTo(HALF_CURVE_ORDER) <= 0) {
		 return s;
		 } else {
		 return KOBLITZ_CURVE.getN().subtract(s);
		 }
	}
	public static BitcoinDetails getBTCAddressBalance(String btcAddress) throws Exception{
		BitcoinDetails m_BitcoinDetails=null; String sResponse = null;
		CloseableHttpClient client = null; HttpGet httpGet = null;  CloseableHttpResponse response = null;
		JsonObject jobj=null;
		try {
					
					client = HttpClients.createDefault();
					httpGet = new HttpGet(NeoBankEnvironment.getBlockCypherBitcoinTest3NetURL()+
							  "/addrs/"+btcAddress+"/balance?token="+NeoBankEnvironment.getBlockCypherAccessToken());
					response = client.execute(httpGet);
					// Response is Okay
					if(response.getCode()!=200)
						throw new Exception ("Problem in getting address balance API, Error code "+response.getCode());
						sResponse=EntityUtils.toString(response.getEntity());
						
						if (sResponse!=null) {
							m_BitcoinDetails= new BitcoinDetails();
							jobj = new Gson().fromJson(sResponse, JsonObject.class);
							m_BitcoinDetails.setAddress(jobj.get("address").toString().replaceAll("\"", ""));
							m_BitcoinDetails.setTotalReceived(jobj.get("total_received").toString());
							m_BitcoinDetails.setTotalSent(jobj.get("total_sent").toString());
							m_BitcoinDetails.setBalance(jobj.get("balance").toString());
							m_BitcoinDetails.setUnconfirmedBalance(jobj.get("unconfirmed_balance").toString());
							m_BitcoinDetails.setFinalBalance(jobj.get("final_balance").toString());
							m_BitcoinDetails.setNumberOfUnconfirmedTransactions(jobj.get("unconfirmed_n_tx").toString());
							m_BitcoinDetails.setNumberOfConfirmedTransactions(jobj.get("n_tx").toString());
							m_BitcoinDetails.setFinalNumberOfTransactions(jobj.get("final_n_tx").toString());
						}
					
		}catch (Exception e) {
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}finally {
			if (sResponse!=null)sResponse=null; if(client!=null)client.close();
			if(httpGet !=null) httpGet.clear();if(response !=null) response.close(); 
			if (jobj!=null)jobj=null;
		}
		return m_BitcoinDetails;
	}
	
	

	 
	 public static JsonObject getBTCAccountTxn(String btcAddress, String limit) throws Exception{
		 JsonObject obj = null;
			try {
				String baseURL = NeoBankEnvironment.getBlockCypherBaseUrl();
				//NeoBankEnvironment.setComment(3, className, "baseURL is "+baseURL);
				String path = "/v1/btc/test3/addrs/"+btcAddress+"/full";
				URIBuilder builder = new URIBuilder();
				builder.setScheme(baseURL.split("://")[0]).setHost(baseURL.split("://")[1]).setPath(path)
				.setParameter("limit", limit);
				URI uri = builder.build();
				CloseableHttpClient client = HttpClients.createDefault();
				HttpGet httpGet = new HttpGet(uri);
				CloseableHttpResponse response = client.execute(httpGet);
				NeoBankEnvironment.setComment(3,className,"Response "+response.getCode());
				String result = EntityUtils.toString(response.getEntity());			
				//NeoBankEnvironment.setComment(3,className,"result "+result);
				if(response.getCode()!=200) {
					//NeoBankEnvironment.setComment(3,className, "response body is: \n"+result);
					throw new Exception ("Problem in Getting transaction API, Error code "+response.getCode());
				}
				try {
					obj = new Gson().fromJson(result, JsonObject.class);
				} finally {
					if(baseURL!=null)baseURL=null; if(path!=null)path=null; if(builder!=null)builder=null;
					if(uri!=null)uri=null; if(client!=null)client.close(); if(httpGet!=null)httpGet.clear();
					if(response!=null)response.close(); if(result!=null)result=null; 
				}
			}catch (Exception e) {
				obj = null;
				NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
				//throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());

			}
			return obj;
		}
	 
	 public static JsonObject getBTCAccountBalance(String btcAddress) throws Exception{
		    JsonObject obj = null;
			try {
				String baseURL = NeoBankEnvironment.getBlockCypherBaseUrl();
				String path = "/v1/btc/test3/addrs/"+btcAddress+"/balance";
				URIBuilder builder = new URIBuilder();
				builder.setScheme(baseURL.split("://")[0]).setHost(baseURL.split("://")[1]).setPath(path);
				URI uri = builder.build();
				CloseableHttpClient client = HttpClients.createDefault();
				HttpGet httpGet = new HttpGet(uri);
				CloseableHttpResponse response = client.execute(httpGet);
				String result = EntityUtils.toString(response.getEntity());			
				NeoBankEnvironment.setComment(3,className,"Response "+response.getCode());
				//NeoBankEnvironment.setComment(3,className,"result "+result);
				if(response.getCode()!=200) {
					//NeoBankEnvironment.setComment(3,className, "response body is: \n"+result);
					throw new Exception ("Problem Getting Account Balance API, Error code "+response.getCode());
				}
				try {
					obj = new Gson().fromJson(result, JsonObject.class);
				} finally {
					if(baseURL!=null)baseURL=null; if(path!=null)path=null; if(builder!=null)builder=null;
					if(uri!=null)uri=null; if(client!=null)client.close(); if(httpGet!=null)httpGet.clear();
					if(response!=null)response.close(); if(result!=null)result=null; 
				}
			}catch (Exception e) {
				obj = null;
				NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
				throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			}
			return obj;
			
		}

	 
	 public static ArrayList<BTCTransction> processTrnsactions(JsonObject obj, String userBTCAddress) throws Exception{
		 NeoBankEnvironment.setComment(3, className, "obj "+obj+" userBTCAddress "+userBTCAddress);
		 ArrayList<BTCTransction> arrBTCTxn = null;
		 try {
			 //NeoBankEnvironment.setComment(3, className, "obj is "+obj.toString());
			 JsonArray txnArray = obj.get("txs").getAsJsonArray();
			 //NeoBankEnvironment.setComment(3, className, "txnArray is "+txnArray.toString());
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
				 NeoBankEnvironment.setComment(3,className,"txnArray is greater than 0");
				 arrBTCTxn = new ArrayList<BTCTransction>();
				 for(int i = 0; i<txnArray.size(); i++) {
					 btcTxn = new BTCTransction();
					 txnObj = txnArray.get(i).getAsJsonObject();	
					 btcTxn.setHash(txnObj.get("hash").getAsString());
					 btcTxn.setConfirmations(txnObj.get("confirmations").getAsInt());
					 btcTxn.setDoubleSpend(txnObj.get("double_spend").getAsBoolean());
					 btcTxn.setTxnDateTime(getBTCDateConvertor(txnObj.get("received").getAsString()));
					 btcTxn.setTxnFees(Utilities.convertSatoshiToBTC(txnObj.get("fees").getAsString()));
					 int blockIndex = txnObj.get("block_index").getAsInt();
					 if(blockIndex == -1) {
						 btcTxn.setConfirmedStatus("UNCONFIRMED");
					 }else {
						 btcTxn.setConfirmedStatus("CONFIRMED");
					 }
					 outputs = txnObj.get("outputs").getAsJsonArray();
					 inputs = txnObj.get("inputs").getAsJsonArray();
					 for(int j = 0; j<inputs.size(); j++) {
						 inputObj = inputs.get(j).getAsJsonObject();
						 inputsAddress=inputObj.get("addresses").getAsJsonArray();
						 sourceAddress = inputsAddress.get(0).getAsString();
					 }
					 if(userBTCAddress.equals(sourceAddress)) {
						 btcTxn.setTxnMode("D");
						 btcTxn.setTxnAmaount(Utilities.convertSatoshiToBTC(txnObj.get("total").getAsString()));
						 for(int k = 0; k<outputs.size(); k++) {
							 outputObj = outputs.get(k).getAsJsonObject();
							 outputAddress=outputObj.get("addresses").getAsJsonArray();
							 if(sourceAddress.equals(outputAddress.get(0).getAsString())) {
//								 NeoBankEnvironment.setComment(3, className, "Step 11 ");
							 }else {
								 btcTxn.setDestinatioAddress(outputAddress.get(0).getAsString());
							 }
						 }
					 }else {
						 btcTxn.setTxnMode("C");
						 for(int k = 0; k<outputs.size(); k++) {
							 outputObj = outputs.get(k).getAsJsonObject();
							 outputAddress=outputObj.get("addresses").getAsJsonArray();
							 if(userBTCAddress.equals(outputAddress.get(0).getAsString())) {
								 btcTxn.setTxnAmaount(Utilities.convertSatoshiToBTC(outputObj.get("value").getAsString()));
							 }else {
								 btcTxn.setSourceAddress((outputAddress.get(0).getAsString()));
							 }
						 }
					 }
					 NeoBankEnvironment.setComment(3,className,"Adding the transaction");
					 arrBTCTxn.add(btcTxn);
				 }
			 }
			 try {
				
			} finally{
				if(txnArray!=null)txnArray=null; if(txnObj!=null)txnObj=null; if(btcTxn!=null)btcTxn=null;
				if(outputs!=null)outputs=null; if(outputObj!=null)outputObj=null; if(inputs!=null)inputs=null;
				if(inputObj!=null)inputObj=null; if(inputsAddress!=null)inputsAddress=null; if(outputAddress!=null)outputAddress=null;
				if(sourceAddress!=null)sourceAddress=null;
			}
			
		} catch (Exception e) {
			arrBTCTxn = null;
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}
		 return arrBTCTxn;
	 }
	 
	 public static JsonObject filterAccountDetailsFromTransactionObj(JsonObject obj) throws Exception{
		 JsonObject returnObj= null;
		 try {

			 returnObj = new JsonObject();
			 returnObj.addProperty("address", obj.get("address").getAsString());
			 returnObj.addProperty("total_received", Utilities.convertSatoshiToBTC(obj.get("total_received").getAsString()));
			 returnObj.addProperty("total_sent", Utilities.convertSatoshiToBTC(obj.get("total_sent").getAsString()));
			 returnObj.addProperty("balance", Utilities.convertSatoshiToBTC(obj.get("balance").getAsString()));
			 returnObj.addProperty("unconfirmed_balance", Utilities.convertSatoshiToBTC(obj.get("unconfirmed_balance").getAsString()));
			 returnObj.addProperty("final_balance", Utilities.convertSatoshiToBTC(obj.get("final_balance").getAsString()));
			 returnObj.addProperty("n_tx", obj.get("n_tx").getAsString());
			 returnObj.addProperty("unconfirmed_n_tx", obj.get("unconfirmed_n_tx").getAsString());
			 returnObj.addProperty("final_n_tx", obj.get("final_n_tx").getAsString());

		} catch (Exception e) {
			returnObj = null;
			NeoBankEnvironment.setComment(1,className,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}
		return returnObj;
	 }
	 
	 public static String getBTCDateConvertor(String datestring) throws Exception{
		SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

		Date d = null;
		try 
		{
		   d = input.parse(datestring);
		} 
		catch (ParseException e) 
		{
		   e.printStackTrace();
		}
   	 return output.format(d);

   	}

}
