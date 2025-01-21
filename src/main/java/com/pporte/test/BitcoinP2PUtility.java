package com.pporte.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class BitcoinP2PUtility {
	public static String className = BitcoinP2PUtility.class.getSimpleName();
	public static final X9ECParameters KOBLITZ_CURVE = SECNamedCurves.getByName("secp256k1");
	public static final BigInteger HALF_CURVE_ORDER = KOBLITZ_CURVE.getN().shiftRight(1);
	
	public static void main(String[] args) throws Exception {
		//createTransaction();
		String privateKey = "6e5d76d98ba3d7a2d712814917639de72be2f62281048c6e1854b78b351701b8";
		String toSign = "859441cc1e47799f7053e31a3f1725a28d9d8a5efb6cf8fa87fdef3fb351711d";
		System.out.println("Sinatures are "+sign(toSign, privateKey)+"01");
		
	}
	public static JsonObject createTransaction() throws Exception{
		JsonObject objReturn = null;
		try {
			JsonArray addressJsonArray = new JsonArray();
			addressJsonArray.add("tb1qt3qzcat9f66zd3y65ashkxdt9ds4rm90ynt7mr");
			JsonObject objOutputs = new JsonObject();
			objOutputs.addProperty("value", 17);
			objOutputs.add("addresses", addressJsonArray);
			JsonArray jsonArrayOutputs = new JsonArray();
			jsonArrayOutputs.add(objOutputs);
	
			JsonArray addressJsonArrayInput= new JsonArray();
			addressJsonArrayInput.add("tb1q6rcdc2fezd0le3nucp8tgfgrvxnl0mg4qtjc44");
			JsonObject objInputs = new JsonObject();
			objInputs.add("addresses", addressJsonArrayInput);
			JsonArray jsonArrayInputs = new JsonArray();
			jsonArrayInputs.add(objInputs);
	
			JsonObject obj = new JsonObject();
			obj.add("inputs", jsonArrayInputs);
			obj.add("outputs", jsonArrayOutputs);
				
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("https://api.blockcypher.com/v1/btc/test3/txs/new");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("token", "b174fa86a4a143f9b2fd571de5f8f0a3"));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			StringEntity entity = new StringEntity(String.valueOf(obj));
			httpPost.setEntity(entity);
			CloseableHttpResponse response = client.execute(httpPost);
			
			System.out.println("Response code is "+response.getCode());
			String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			System.out.println("response body is: \n"+responseBody);

		try {
			objReturn = new Gson().fromJson(responseBody, JsonObject.class);
			
			signTransaction(objReturn);
		} finally {
		
		}
		} catch (Exception e) {
		System.out.println("Error in createTransaction is "+e.getMessage());
		throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}
		return objReturn;
		}
	public static JsonObject signTransaction(JsonObject obj) {
		JsonObject objReturn = null;
		try {
			String privateKey="6e5d76d98ba3d7a2d712814917639de72be2f62281048c6e1854b78b351701b8";
			String publicKey="0265cbb812cb56f8791b3ce40252a17d8a515da63251cfda55d0dec06fe7f812e2";
			
			JsonArray toSignJArray = obj.get("tosign").getAsJsonArray();
			
			String toSign = toSignJArray.get(0).toString();
			System.out.println("to sign is "+toSign);
			String signature = sign(toSignJArray.get(0).toString().replaceAll("\"",""),privateKey);
			JsonArray signatureJArray = new JsonArray();
			signatureJArray.add(signature+"01");
			JsonArray publicKeyJArray = new JsonArray();
			publicKeyJArray.add(publicKey);
			obj.add("signatures", signatureJArray);
			obj.add("pubkeys", publicKeyJArray);

			System.out.println("obj is "+obj);
			
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("https://api.blockcypher.com/v1/btc/test3/txs/send");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("token", "b174fa86a4a143f9b2fd571de5f8f0a3"));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			StringEntity entity = new StringEntity(String.valueOf(obj));
			httpPost.setEntity(entity);
			CloseableHttpResponse response = client.execute(httpPost);
			
			System.out.println("Response code is "+response.getCode());
			// System.out.println("response body is "+response.);
			String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			System.out.println("response body is: \n"+responseBody);
			
			
		} catch (Exception e) {
			System.out.println("Error "+e.getMessage());
		}
		return objReturn;
	}
	 
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
}
