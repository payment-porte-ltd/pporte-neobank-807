package com.pporte.test;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.stellar.sdk.Asset;
import org.stellar.sdk.AssetTypeCreditAlphaNum12;
import org.stellar.sdk.AssetTypeCreditAlphaNum4;
import org.stellar.sdk.AssetTypeNative;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Memo;
import org.stellar.sdk.Network;
import org.stellar.sdk.PathPaymentStrictSendOperation;
import org.stellar.sdk.Server;
import org.stellar.sdk.Transaction;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.SubmitTransactionResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pporte.model.PaymentOffer;
import com.pporte.model.PaymentPath;
import com.pporte.utilities.StellarSDKUtility;

public class StellarApi {

	
	
//	testnet.stellar.org/assets?asset_code=HKD
	public static void main(String[] args) throws Exception {
		System.out.println("time is "+java.time.LocalTime.now());
		List<Asset> issuersList = null;
		issuersList = getIssuersData();
//		 Gson gson = new Gson();
//		String sourceAssetCode = "PORTE";
//		String sourceAmount = "1000000";
//		String sourceAssetIssuer = "GD3KCV5E7KWYSKDW7SJ7CGMM7D22OGTAMICUJOSP4R6XD7GRKLTCMGUG";
//		String pathStrings="[{\"assetType\":\"native\",\"assetCode\":\"XLM\"}]";
//		JsonArray paths = gson.fromJson(pathStrings, JsonArray.class);
//		String destinationAssetCode = "HKD";
//		String destinationIssuer = "GDOR5N7WYIEJL3JXEY76ZRNRVYFDELTG6B4UPMA6UM6HZMC5PTYISTQJ";
//		String destinationAmount = "20.6691285";
//		String sourceAcountPrivateKey = "SB7XBDKPWIZR4JJSZABR3X5ZSYXGJ527OD3LX67HTSWNV7QVJR6LQUJC";
		//SB7XBDKPWIZR4JJSZABR3X5ZSYXGJ527OD3LX67HTSWNV7QVJR6LQUJC
//		KeyPair sourceAccount = KeyPair.fromSecretSeed("SB7XBDKPWIZR4JJSZABR3X5ZSYXGJ527OD3LX67HTSWNV7QVJR6LQUJC");
//		System.out.println("Account is "+sourceAccount.getAccountId());
		//sortListByDestinationAmount(offers);
//		System.out.println("time is "+java.time.LocalTime.now());
//		checkTrustLine("HKD", "GDVUE3UDXUUEMWW6ETVFBBYED3WU3VQMDOVWKGMSSIXPEEVLNXDIK6AU");
		
//		pathPaymentStrictSend(sourceAssetCode, sourceAssetIssuer,  sourceAmount, 
//				 destinationAssetCode,  destinationIssuer,  destinationAmount, sourceAcountPrivateKey,  paths  );
		if (issuersList!=null);issuersList=null;
	}
	
	public static boolean checkTrustLine(String assetCode, String assetIssuer) {
		boolean hasTrustLine = false;
		try {
			 Gson gson = new Gson();
			String accountId= "GAKXWAYGXLEPMGLKQSMU7TVZM7MDQFJ4M5AZ6A5UTP2MDYHHMPV3VLO7";
			String path = "/accounts/"+accountId;
			URIBuilder builder = new URIBuilder();
			builder.setScheme("https").setHost("horizon-testnet.stellar.org").setPath(path);
			URI uri = builder.build();
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(uri);
			CloseableHttpResponse response = client.execute(httpGet);
			System.out.println("Response "+response.getCode());
			String result = EntityUtils.toString(response.getEntity());
			JsonObject obj = gson.fromJson(result, JsonObject.class);
			//System.out.println("obj "+obj);
			JsonArray balances = obj.get("balances").getAsJsonArray();
			JsonObject balanceObj = null;
			System.out.println("balances "+balances);
			for (int i = 0; i < balances.size(); i++) {
				balanceObj = (JsonObject) balances.get(i);
				if(!balanceObj.get("asset_type").getAsString().equals("native")) {
					if(balanceObj.get("asset_code").getAsString().equals(assetCode)
							&&balanceObj.get("asset_issuer").getAsString().equals(assetIssuer)) {
						hasTrustLine = true;
					}
				}
				
			}
			
		} catch (Exception e) {
			hasTrustLine = false;
			System.out.println("Error is "+e.getMessage());
		}
		return hasTrustLine;
		
	}
	
	
	public static List<Asset> getIssuersData() {
		Asset asset = null;
		List<Asset> assetIssuers = null;
		try {
			 Gson gson = new Gson();
			String path = "/assets/";
			URIBuilder builder = new URIBuilder();
			builder.setScheme("https").setHost("horizon-testnet.stellar.org").setPath(path)
			.setParameter("asset_code", "HKD");
			URI uri = builder.build();
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(uri);
			CloseableHttpResponse response = client.execute(httpGet);
			System.out.println("Response "+response.getCode());
			String result = EntityUtils.toString(response.getEntity());
			JsonObject obj = gson.fromJson(result, JsonObject.class);
			System.out.println("obj "+obj);
			JsonObject embedded = obj.get("_embedded").getAsJsonObject();
			System.out.println("embedded "+embedded);
			JsonArray recordsArray = embedded.get("records").getAsJsonArray();
			System.out.println("recordsArray "+recordsArray);
			assetIssuers = new ArrayList<Asset>();
			JsonObject assetObj = null;
			for(int i = 0; i<recordsArray.size(); i++) {
				assetObj = new JsonObject();
				assetObj = (JsonObject) recordsArray.get(i);
				System.out.println("Asset code is "+assetObj.get("asset_code").getAsString());
				System.out.println("Asset issuer is "+assetObj.get("asset_issuer").getAsString());
				asset = Asset.createNonNativeAsset(assetObj.get("asset_code").getAsString(),assetObj.get("asset_issuer").getAsString());
				assetIssuers.add(asset);
			}
			System.out.println("Size is "+assetIssuers.size());
		} catch (Exception e) {
			System.out.println("Error is "+e.getMessage());
		}
		return assetIssuers;
	}
	
	public static String getOffers() {
		try {
			 Gson gson = new Gson();
			String accountId= "GA7XM2CT3VMCK3WI74OEKVZYW6DLJSGRDVVPODDVQBSO6IBN4LFKAT46";
			String path = "/accounts/"+accountId+"/offers";
			URIBuilder builder = new URIBuilder();
			builder.setScheme("https").setHost("horizon-testnet.stellar.org").setPath(path);
			URI uri = builder.build();
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(uri);
			CloseableHttpResponse response = client.execute(httpGet);
			System.out.println("Response "+response.getCode());
			String result = EntityUtils.toString(response.getEntity());
			JsonObject obj = gson.fromJson(result, JsonObject.class);
			System.out.println("obj "+obj);
		} catch (Exception e) {
			
		}
		return null;
	}
	
	public static String getOffersFromDex() {
		try {
			String accountId= "GA7XM2CT3VMCK3WI74OEKVZYW6DLJSGRDVVPODDVQBSO6IBN4LFKAT46";
			String path = "/accounts/"+accountId+"/offers";
			URIBuilder builder = new URIBuilder();
			builder.setScheme("https").setHost("horizon-testnet.stellar.org").setPath(path);
			URI uri = builder.build();
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(uri);
			CloseableHttpResponse response = client.execute(httpGet);
			System.out.println("Response "+response.getCode());
			String result = EntityUtils.toString(response.getEntity());
			System.out.println("result "+result);
		} catch (Exception e) {
			
		}
		return null;
	}
	
	public static List<PaymentOffer> getPathStrictSendWithDestinationAssets( List<Asset> issuersList, String sourceAssetCode, 
			String sourceAmount, String sourceAssetType, String sourceAssetIssuer) throws Exception {
		List<PaymentOffer> paymentOffers = null;
		try {
			Gson gson = new Gson();			
			String cursor = "13537736921089";
			String order = "asc";
			//String limit = "3";
			String path = "/paths/strict-send";			
			String destinationAsssets = Arrays.toString(issuersList.toArray()).replace("[", "").replace("]", "");
			destinationAsssets = destinationAsssets.replaceAll(" ", "");
			System.out.println("destinationAsssets "+destinationAsssets);
			URIBuilder builder = new URIBuilder();
			builder.setScheme("https").setHost("horizon-testnet.stellar.org").setPath(path)
					.setParameter("destination_assets", destinationAsssets).setParameter("source_amount", sourceAmount)
					.setParameter("source_asset_type", sourceAssetType)
					.setParameter("source_asset_code", sourceAssetCode)
					.setParameter("source_asset_issuer", sourceAssetIssuer).setParameter("cursor", cursor)
					.setParameter("order", order);
			URI uri = builder.build();
			
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(uri);
			CloseableHttpResponse response = client.execute(httpGet);
			System.out.println("Result is "+response.getCode());
			String result = EntityUtils.toString(response.getEntity());
			JsonObject obj = gson.fromJson(result, JsonObject.class);
			System.out.println("Offer are "+obj);
			JsonObject obj_embedded = new JsonObject();
			obj_embedded = obj.get("_embedded").getAsJsonObject();
			JsonArray records = obj_embedded.get("records").getAsJsonArray();
			List<PaymentPath> paymentPaths = null;
			paymentOffers = new ArrayList<PaymentOffer>();
			for(int i = 0; i<records.size(); i++) {
				paymentPaths = new ArrayList<PaymentPath>();
				PaymentOffer offer = new PaymentOffer();
				JsonObject objOffer = new JsonObject();
				JsonArray paths = new JsonArray();
				objOffer = (JsonObject) records.get(i);
				paths = objOffer.get("path").getAsJsonArray();
				PaymentPath paymentPath = null;
				for(int j = 0; j<paths.size(); j++) {
					JsonObject pathObj = paths.get(j).getAsJsonObject();
					paymentPath = new PaymentPath();
					if(pathObj.get("asset_type").getAsString().equals("native")) {
						paymentPath.setAssetType("native");
						paymentPath.setAssetCode("XLM");
					}else {
						paymentPath.setAssetType(pathObj.get("asset_type").getAsString());
						paymentPath.setAssetCode(pathObj.get("asset_code").getAsString());
						paymentPath.setAssetIssuers(pathObj.get("asset_issuer").getAsString());
					}
					paymentPaths.add(paymentPath);
				}
				offer.setPaymentPaths(paymentPaths);
				offer.setSourceAsset(objOffer.get("source_asset_code").getAsString());
				offer.setSourceAmount(objOffer.get("source_amount").getAsString());
				offer.setSourceAssetIssuer(objOffer.get("source_asset_issuer").getAsString());
				offer.setDestinationAsset(objOffer.get("destination_asset_code").getAsString());
				offer.setDestinationIssuer(objOffer.get("destination_asset_issuer").getAsString());
				offer.setDestinationAmount(Double.parseDouble(objOffer.get("destination_amount").getAsString()));
				paymentOffers.add(offer);
			}
			System.out.println("Offers Size is "+paymentOffers.size());
		
		} catch (Exception e) {
			paymentOffers = null;
			System.out.println( "getPathStrictSendWithDestinationAssets is "+e.getMessage());
		}
	
		return paymentOffers;
	}
	
	
	public static List<PaymentOffer> sortListByDestinationAmount(  List<PaymentOffer> offers) throws Exception {
		List<PaymentOffer> sortedOffers = null;
		try {
			sortedOffers = offers.stream()
			        .sorted(Comparator.comparingDouble(PaymentOffer::getDestinationAmount).reversed())
			        .collect(Collectors.toList());
			sortedOffers.forEach(offer -> {
			    System.out.println(offer.getDestinationAmount());
			});
		} catch (Exception e) {
			System.out.println( "sortListAndGetBestThree is "+e.getMessage());
		}
		return null;
	}
	
	
	
	public static String pathPaymentStrictSend(String sourceAssetCode,String sourceAssetIssuer, String sourceAmount, 
			String destinationAssetCode, String destinationIssuer, String destinationAmount,String sourceAcountPrivateKey, JsonArray paths  ) throws Exception{
		
		
		String result = "false";
		try {
			System.out.println();
			System.out.println("====inside pathPaymentStrictSend : " + java.time.LocalTime.now());
			System.out.println( "sourceAssetCode " + sourceAssetCode+" sourceAssetIssuer "+sourceAssetIssuer
					+" sourceAmount "+sourceAmount+" destinationAssetCode "+destinationAssetCode+" destinationIssuer "+destinationIssuer
					+" destinationAmount "+destinationAmount+" sourceAcountPrivateKey "+sourceAcountPrivateKey+" paths "+paths.toString());
			Server server = new Server("https://horizon-testnet.stellar.org");
			
		 	KeyPair sourceAccountKeyPair = KeyPair.fromSecretSeed(sourceAcountPrivateKey);
		 	server.accounts().account(sourceAccountKeyPair.getAccountId());
		 	AccountResponse sourceAccount = server.accounts().account(sourceAccountKeyPair.getAccountId());

		   //Create an object to represent the new asset
		 	
		 	Asset sourceAsset = Asset.createNonNativeAsset(sourceAssetCode, sourceAssetIssuer);
		 	Asset destinationAsset = Asset.createNonNativeAsset(destinationAssetCode, destinationIssuer);
		 	JsonObject pathObj = null;
		 	Asset pathAsset = null;
		 	List<Asset> assetsList = new ArrayList<Asset>();
		 	for(int i = 0; i<paths.size(); i++) {
		 		pathAsset = null;
		 		pathObj = (JsonObject) paths.get(i);
		 		String assetType = pathObj.get("assetType").getAsString();
		 		switch (assetType) {
		 			case "native":
		 				pathAsset = new AssetTypeNative();
		 			break;
		 			case "credit_alphanum4":
		 				pathAsset = new AssetTypeCreditAlphaNum4(pathObj.get("assetCode").getAsString(),
		 						pathObj.get("assetIssuers").getAsString());
			 		break;
		 			case "credit_alphanum12":
		 				pathAsset = new AssetTypeCreditAlphaNum12(pathObj.get("assetCode").getAsString(),
		 						pathObj.get("assetIssuers").getAsString());
				 	break;
				 	
		 		}
		 		assetsList.add(pathAsset);		 		
		 		
		 	}
		 	//Intermediate path asset
		 	Asset[] path = assetsList.toArray(new Asset[0]);
		 	Transaction transaction = null;
		 	if(sourceAssetCode.equals("XLM")) {
		 	 	 transaction = new Transaction.Builder(sourceAccount, Network.TESTNET)
				        .addOperation(new PathPaymentStrictSendOperation.Builder(
				        		new AssetTypeNative(), sourceAmount, sourceAccountKeyPair.getAccountId(), 
				        		destinationAsset, destinationAmount )
				        		.setPath(path)
				        		.setSourceAccount(sourceAccountKeyPair.getAccountId())
				        		.build())
				        .addMemo(Memo.text("Path Payment Strict Send"))
				        .setTimeout(180)
				        .setBaseFee(Transaction.MIN_BASE_FEE)
				        .build();
		 	}else {
		 	 	 transaction = new Transaction.Builder(sourceAccount, Network.TESTNET)
				        .addOperation(new PathPaymentStrictSendOperation.Builder(
				        		sourceAsset, sourceAmount, sourceAccountKeyPair.getAccountId(), 
				        		destinationAsset, destinationAmount )
				        		.setPath(path)
				        		.setSourceAccount(sourceAccountKeyPair.getAccountId())
				        		.build())
				        .addMemo(Memo.text("Path Payment Strict Send"))
				        .setTimeout(180)
				        .setBaseFee(Transaction.MIN_BASE_FEE)
				        .build();
		 	}
		
			transaction.sign(sourceAccountKeyPair);
			
			SubmitTransactionResponse response = null;
			
			try {
				  response = server.submitTransaction(transaction);
				 
				  if(response.getExtras()==null) {
						result = "true";
						System.out.println("result is "+result);
					  }else {							
							String operationError = response.getExtras().getResultCodes().getOperationsResultCodes().toString();
							result = StellarSDKUtility.getPathPaymentStrictSendResultCode(operationError);
							System.out.println( "==== Failed in Path Payment Strict Send error: "+operationError
									+" description: "+result+" "+  java.time.LocalTime.now());
					  }
				  System.out.println( "====end pathPaymentStrictSend : " + java.time.LocalTime.now());

				} finally {
					if (server != null)server.close();if (response != null)response = null;
					if (transaction != null)transaction = null;if (sourceAccount != null)sourceAccount = null;
					//TODO clear all objects here
				 
				}
		} catch (Exception e) {
			result = "false";
			System.out.println("Error from pathPaymentStrictSend is: "+e.getMessage());		}
		return result;
	}
	
	
	
	

	
	
	
	
	
	
	
	
}
