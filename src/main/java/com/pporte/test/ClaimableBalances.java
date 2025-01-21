package com.pporte.test;

import java.net.URI;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.stellar.sdk.Asset;
import org.stellar.sdk.AssetTypeNative;
import org.stellar.sdk.ClaimClaimableBalanceOperation;
import org.stellar.sdk.Claimant;
import org.stellar.sdk.CreateClaimableBalanceOperation;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Memo;
import org.stellar.sdk.Network;
import org.stellar.sdk.Predicate;
import org.stellar.sdk.Server;
import org.stellar.sdk.Transaction;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.SubmitTransactionResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pporte.model.ClaimableBalance;
import com.pporte.utilities.Utilities;

public class ClaimableBalances {
	
	public static void main(String[] args) throws Exception {
		//createClaimableBalance();
//		getClaimClaimableBalances();
//		claimClaimableBalance();
//		getClaimClaimableBalances();
		getStrictReceivePath();
	}
	
	public static void createClaimableBalance() {
		try {
			Server server = new Server("https://horizon-testnet.stellar.org");
			KeyPair sourceAccountKeyPair = KeyPair.fromSecretSeed("SDJVNK5XAG2TCCMU3JJHQBQIAWPWOANWA5PABW2NJRO5ZK2P7B4Q5DZ3");
			KeyPair destinationAccountKeyPair = KeyPair.fromAccountId("GDYCU6GOZZ7LTO53ZIZCM3W3XT6K5W6Y54NVJF5N7E7PJWUTXHZ4J6AO");
			server.accounts().account(destinationAccountKeyPair.getAccountId());
			AccountResponse sourceAccount = server.accounts().account(sourceAccountKeyPair.getAccountId());
			
			String amount = "10";
			//Load PORTE Asset
			String assetCode = "XLM";
	 		String issuerPublicKey = "GARE7JKLY572Q7VWOHVJXIH5AO4E3PEPCZVUMNCBRGPQLT32OWDBNF6M";
	 		Asset asset = null;
	 		if(assetCode.equals("XLM")) {
	 			asset = new AssetTypeNative();
	 		}else {
	 			asset = Asset.createNonNativeAsset(assetCode, issuerPublicKey);//Loading Custom Asset
	 		}
	 		
	 		
	 		
	 		Claimant claimant = new Claimant(destinationAccountKeyPair.getAccountId(), new Predicate.Unconditional() );
	 		ArrayList<Claimant> claimantList = new ArrayList<Claimant>();
	 		claimantList.add(claimant);
			CreateClaimableBalanceOperation operation = new CreateClaimableBalanceOperation
					.Builder(amount, asset, claimantList)
					.build();
			Transaction transaction = new Transaction.Builder(sourceAccount, Network.TESTNET)
			        .addOperation(operation)
			        .addMemo(Memo.text("Test claimable balances"))
			        .setTimeout(180)
			        .setBaseFee(Transaction.MIN_BASE_FEE)
			        .build();
			transaction.sign(sourceAccountKeyPair);
			try {
				SubmitTransactionResponse response = server.submitTransaction(transaction);
				System.out.println("Response is "+response.isSuccess());
			} finally {
				// TODO: handle finally clause
				server.close();
			}
			
			
		} catch (Exception e) {
			System.out.println("Error from createClaimableBalance "+e.getMessage());
		}
		
		
		
	}
	
	public static void claimClaimableBalance() {
		try {
			Server server = new Server("https://horizon-testnet.stellar.org");
			KeyPair claimantAccount = KeyPair.fromSecretSeed("SBXMLE2S6KX36OSH6RJXLPDKDEYPUD5K24EDFK6MSNKLP2L5JTBKPUUN");
			server.accounts().account(claimantAccount.getAccountId());
			AccountResponse account = server.accounts().account(claimantAccount.getAccountId());
			
			String claimableBalanceId = "000000008eeb96b62b91fa670c24b2cd04c28c26b5eb237857e4622e8b088f10a0033bfb";
			
			ClaimClaimableBalanceOperation operation = new ClaimClaimableBalanceOperation
					.Builder(claimableBalanceId)
					.build();
			
			Transaction transaction = new Transaction.Builder(account, Network.TESTNET)
			        .addOperation(operation)
			        .addMemo(Memo.text("Test claimable balances"))
			        .setTimeout(180)
			        .setBaseFee(Transaction.MIN_BASE_FEE)
			        .build();
			transaction.sign(claimantAccount);
			
			try {
				SubmitTransactionResponse response = server.submitTransaction(transaction);
				System.out.println("Response is "+response.isSuccess());
			} finally {
				// TODO: handle finally clause
				server.close();
			}
			
			
		} catch (Exception e) {
			System.out.println("Error from claimClaimableBalance "+e.getMessage());
		}
		
	}
	
	public static ArrayList<ClaimableBalance> getClaimClaimableBalances() {
		ArrayList<ClaimableBalance> clArrayList = null;
		try {
			Gson gson = new Gson();
			String path = "/claimable_balances/";
			String claimant = "GDYCU6GOZZ7LTO53ZIZCM3W3XT6K5W6Y54NVJF5N7E7PJWUTXHZ4J6AO";
			URIBuilder builder = new URIBuilder();
			builder.setScheme("https").setHost("horizon-testnet.stellar.org").setPath(path)
			.setParameter("claimant", claimant);
			URI uri = builder.build();
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(uri);
			CloseableHttpResponse response = client.execute(httpGet);
			System.out.println("Response "+response.getCode());
			String result = EntityUtils.toString(response.getEntity());			
			System.out.println("result "+result);
			JsonObject obj = gson.fromJson(result, JsonObject.class);
			System.out.println("obj "+obj);
			JsonObject embedded = obj.get("_embedded").getAsJsonObject();
			System.out.println("embedded "+embedded);
			JsonArray recordsArray = embedded.get("records").getAsJsonArray();
			System.out.println("recordsArray "+recordsArray);
			clArrayList = new ArrayList<ClaimableBalance>();
			JsonObject balanceObj = null;
			ClaimableBalance claimableBalance = null;
			String assetString = null;
			JsonArray climantArray = null;
			JsonObject climantObj = null;
			ArrayList<String> climantList = new ArrayList<String>();
			System.out.println("b4 loop");
			for(int i = 0; i<recordsArray.size(); i++) {
				claimableBalance = new ClaimableBalance();
				balanceObj = new JsonObject();
				balanceObj = (JsonObject) recordsArray.get(i);
				System.out.println("Step 1");
				claimableBalance.setClaimableBalanceId(balanceObj.get("id").getAsString());
				System.out.println("Step 2");
				assetString = balanceObj.get("asset").getAsString();
				System.out.println("Step 3");
				if(assetString.equalsIgnoreCase("native")) {
					claimableBalance.setAssetCode("XLM");
					claimableBalance.setAssetIssuer(assetString);
				}else {
					claimableBalance.setAssetCode(assetString.split(":")[0]);
					claimableBalance.setAssetIssuer(assetString.split(":")[1]);
				}
				
				System.out.println("Step 5");
				claimableBalance.setAmount(balanceObj.get("amount").getAsString());
				System.out.println("Step 6");
				claimableBalance.setSourceAccount((balanceObj.get("sponsor").getAsString()));
				System.out.println("Step 7");
				claimableBalance.setLedgerNo((balanceObj.get("last_modified_ledger").getAsInt()));
				System.out.println("Step 8");
				claimableBalance.setCreatedOn(
						StringUtils.trim(Utilities.getStellarDateConvertor(balanceObj.get("last_modified_time").getAsString())));
				System.out.println("Step 9");
				climantArray = balanceObj.get("claimants").getAsJsonArray();
				System.out.println("Step 10");
				for (int j = 0; j < climantArray.size(); j++) {
					climantObj =(JsonObject) climantArray.get(j);
					climantList.add(climantObj.get("destination").getAsString());
				}
				claimableBalance.setClaimants(climantList);
				clArrayList.add(claimableBalance);
			}
			try {
				
			}finally {
				
			}
			
		} catch (Exception e) {
			System.out.println("Error from getClaimClaimableBalances "+e.getMessage());
		}
		
		return clArrayList;
		
	}
	
	public static String  getStrictReceivePath() {
		String sourceAmount = null;
		try {
			String sourceAssetCode = "VESL";
			String sourceAssetIssuer = "GARE7JKLY572Q7VWOHVJXIH5AO4E3PEPCZVUMNCBRGPQLT32OWDBNF6M";
			String destinationAssetType = "credit_alphanum4";
			String destinationAssetIssuer = "GBOPFWZZJZUMTS6KVQAHUUNLMXO424ZF7IWHF6GONLAVCDSN4TBBKCLV";
			String destinationAssetCode = "BTC";
			String destinationAmount = "0.0000001";			
			
			Gson gson = new Gson();
			String path = "/paths/strict-receive";
			URIBuilder builder = new URIBuilder();
			builder.setScheme("https").setHost("horizon-testnet.stellar.org").setPath(path)
		 
			.setParameter("source_assets", sourceAssetCode+":"+sourceAssetIssuer )
			.setParameter("destination_asset_type", destinationAssetType )
			.setParameter("destination_asset_issuer", destinationAssetIssuer )
			.setParameter("destination_asset_code", destinationAssetCode )
			.setParameter("destination_amount", destinationAmount );
			URI uri = builder.build();
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(uri);
			CloseableHttpResponse response = client.execute(httpGet);
			System.out.println("Response "+response.getCode());
			String result = EntityUtils.toString(response.getEntity());			
			System.out.println("result "+result);
			JsonObject obj = gson.fromJson(result, JsonObject.class);
			System.out.println("obj "+obj);
			JsonObject embedded = obj.get("_embedded").getAsJsonObject();
			System.out.println("embedded "+embedded);
			JsonArray recordsArray = embedded.get("records").getAsJsonArray();
			JsonObject pathObj = null;
			
			for(int i = 0; i<recordsArray.size(); i++) {
				pathObj = (JsonObject) recordsArray.get(i);
				sourceAmount = pathObj.get("source_amount").getAsString();
			}
			
			System.out.println("sourceAmount is "+sourceAmount);
//			String totalPercertage = (100+)
//			String.valueOf(Double.parseDouble(destinationAmount)*(percentage+Double.parseDouble(destinationAmount))/100);
		} catch (Exception e) {
			System.out.println("Error from getStrictReceivePath "+e.getMessage());
		}
		return sourceAmount;
	}
	

}
