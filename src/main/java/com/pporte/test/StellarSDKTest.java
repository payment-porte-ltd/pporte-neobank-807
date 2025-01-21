package com.pporte.test;


import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.stellar.sdk.Asset;
import org.stellar.sdk.AssetTypeNative;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.ManageBuyOfferOperation;
import org.stellar.sdk.ManageSellOfferOperation;
import org.stellar.sdk.Memo;
import org.stellar.sdk.Network;
import org.stellar.sdk.PathPaymentStrictReceiveOperation;
import org.stellar.sdk.PathPaymentStrictSendOperation;
import org.stellar.sdk.Server;
import org.stellar.sdk.Transaction;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.OfferResponse;
import org.stellar.sdk.responses.Page;
import org.stellar.sdk.responses.SubmitTransactionResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.pporte.utilities.StellarSDKUtility;
import com.pporte.utilities.Utilities;

import shadow.com.google.common.collect.Lists;

public class StellarSDKTest {
	public static KeyPair keyPair = null;
	public static void main(String[] args) {
      	try {
      		KeyPair sourceAccount = KeyPair.fromSecretSeed("SCO2OLPBRLYYKZ2HJKEF7FWMXZU3HNVAPPJ43PAL3PZA7HJ5N2X2L4NF");
	 		//KeyPair destinationAccount = KeyPair.fromAccountId("GCLVJMQHLYKJKGZOCC5Z24NGAFMJTSSF4N4VF77CZICXTGYAE5XSGUBP");
	 		
	 		//CheckAccountIfExist(destinationAccount);
	 		//System.out.println(" account Exist "+CheckAccountIfExist(sourceAccount));
	 		//generateKeyPair() ;
      		//() ;
      		//getAccountBalance(sourceAccount) ;
	 		//createTrustline();
	 		getAccountOffers(sourceAccount);
	 		//getAccountTransactions(destinationAccount);
	 		//manageBuyOffer(null, null, null, null, null);
	 		//strictSend();
	 		
	 		//getAccountTransactions(destinationAccount);
	 		//pathPaymentStrictSend();
	 		//pathPaymentStrictReceive();
	 		//getPathStrictReceiveWithSourceAssets();
	 		//getPathStrictSendWithSourceAccount();
	 		//getPathStrictSendWithDestinationAssets();
	 		//manageSellOffer(null, null, null, null, null);
	 		//manageBuyOffer(null, null, null, null, null);

	 	
	 		
	 	//	getAccountBalance(destinationAccount);
//      		getAccountBalance();
	 		//String account = "GALBMMADCBZJOA77QXEFWZOZ3LHV6CIPRLFGYNQEHWQHJFJS3UC6HWDY";
      		//generateKeyPair();
      		//createAccount();
	 		//System.out.println((sourceAccount.getAccountId()));
		} catch (Exception e) {
			System.out.println("Error is : "+e.getMessage() );
		}
	}
	
	
	public static boolean CheckAccountIfExist(KeyPair account) throws Exception {
		boolean result = false;

		try {
			Server server = new Server("https://horizon-testnet.stellar.org");
		 	AccountResponse sourceAccountResponse = server.accounts().account(account.getAccountId());
		 	result = true;
				System.out.println("account exists "+ sourceAccountResponse.getAccountId());	 	
		} catch (Exception e) {
		 	result = false;
			throw new Exception("Account does not exist in Steller Network : "+e.getMessage());
		}
		return result;	
	}
	
	public static void generateKeyPair() {
	 	try {
			Server server = new Server("https://horizon-testnet.stellar.org");
			keyPair = KeyPair.random();
	 		char[] seceretKey = keyPair.getSecretSeed();
	 		String accountId = keyPair.getAccountId();
	 		System.out.println("Secret key "+seceretKey);
	 		System.out.println("accountId "+accountId);
	 		
	 		KeyPair sourceAccount = KeyPair.fromSecretSeed("SB7AJALFNBC62RFB7VWCB7EWBT47XA6IYW4I2XFSX5CKPEDMZZFT6XSD");
	 		KeyPair destinationAccount = KeyPair.fromAccountId("GCLVJMQHLYKJKGZOCC5Z24NGAFMJTSSF4N4VF77CZICXTGYAE5XSGUBP");
			
	 		AccountResponse accountExist = server.accounts().account(destinationAccount.getAccountId());
			System.out.println("destination accountID is : "+sourceAccount.getAccountId());
	 		
			AccountResponse sourceAccountX = server.accounts().account(sourceAccount.getAccountId());

			byte[] decoded = Base64.getDecoder().decode(server.accounts().account(sourceAccount.getAccountId()).toString());
			System.out.println("accountExist is: "+accountExist);
			//.out.println("AccountResponse is: "+AccountResponse);
			System.out.println("decoded is: "+decoded);
			System.out.println("encoded value is " + new String(accountExist.getAccountId()));

//	 		
	 		
		} catch (Exception e) {
			System.out.println("Error is: "+e.getMessage());
		}  
	  
	
}
	
	
	public static void createAccount() {
	 	try {
	 		String friendbotUrl = String.format(
	 				 "https://friendbot.stellar.org/?addr=%s",
	 		keyPair.getAccountId());
			InputStream response = new URL(friendbotUrl).openStream();
			String body = new Scanner(response, "UTF-8").useDelimiter("\\A").next();
			System.out.println("SUCCESS! You have a new account :)\n" + body);
	 		
		} catch (Exception e) {
			System.out.println("Error is: "+e.getMessage());
		}  
	   
	
}
	
	public static void getAccountBalance() {
	 	try {
	 		Server server = new Server("https://horizon-testnet.stellar.org");
	 		AccountResponse account = server.accounts().account("GBMVNI6VKKE3GDC76BN2T5GM2K2NXLEMQ3PH37UT3QGKAM253MFWGALO");
	 		//System.out.println("Balances for account " + accountId.getAccountId());
	 		
	 		
	 		System.out.println("Balances size " + account.getBalances().length);

	 		for (AccountResponse.Balance balance : account.getBalances()) {
//	 		 System.out.printf(
//	 		 "Type: %s, Code: %s, Balance: %s%n",
	 		 balance.getAssetIssuer();
//	 		 balance.getAssetCode().get(),
//	 		 balance.getBalance()
//	 		 );
	 		 
	 		 //Optional.of(PORTE)
	 		 if(!balance.getAssetCode().isPresent()){
	 			 System.out.println("XLM balance is "+ balance.getBalance());
	 		 }else if(balance.getAssetCode().isPresent()) {
	 			 System.out.println(balance.getAssetCode().get()+" balance is "+ balance.getBalance());

	 		 }
	 		 
//	 		 if(balance.getAssetType().equals("native")){
//	 			 System.out.println("XLM balance is "+ balance.getBalance());
//	 		 }
//	 		 if((balance.getAssetCode().toString()).contains("PORTE")){
//	 			 System.out.println("PORTE balance is "+ balance.getBalance());
//	 		 }
	 		}
	 		
	 		
		} catch (Exception e) {
			System.out.println("Error is: "+e.getMessage());
		}  
	}
	
	public static void loadAssetObject() {
	 	try {
	 		String assetCode = "PORTE";
	 		String issuerPublicKey = "GBBOY7RHGT4ABGVJZXPYLXZL56JG6AU2Z7OQAUHATGOCRJ7B4NDYOGWJ";
	 		Asset xlm = new AssetTypeNative(); //Loading native asset
	 		Asset customerAsset = Asset.createNonNativeAsset(assetCode, issuerPublicKey);//Loading Custom Asset
	 		
		} catch (Exception e) {
			System.out.println("Error is: "+e.getMessage());
		}  
	   	
	}
	
	public static void createTrustline() {
	 	try {
	 		Server server = new Server("https://horizon-testnet.stellar.org");
	 	// Keys for accounts to PORTE issuer and the account to create trustline
	 	KeyPair issuingKeys = KeyPair .fromAccountId("GDTFRISZF2HJPBDBSAIRHO4EZWUZUDOTBK5XMVRGUQN2XENO7J7EWGDI");
	 	KeyPair accountKeys = KeyPair .fromSecretSeed("SDUHANFNMZJMG3IY7ERHU5Q5AZU4DGQFP66NQE6ZJBPWK3TKNSZXPRVR");
	 	// Create an object to represent the asset
	 	Asset porte = Asset.createNonNativeAsset("PORTE", issuingKeys.getAccountId());
	 	// Create the trustline to the asset
	 	/*AccountResponse account = server.accounts().account(accountKeys.getAccountId());
	 	Transaction allowPORTE = new Transaction.Builder(account, Network.TESTNET)
	 	 .addOperation(new ChangeTrustOperation.Builder(porte, "1000").build())
	 	 .build();
	 	allowPORTE.sign(accountKeys);
	 	server.submitTransaction(allowPORTE);*/
		} catch (Exception e) {
			System.out.println("Error is: "+e.getMessage());
		}  
	   	
	}
	
	public static void getAccountOffers(KeyPair accountId)throws IOException {
        System.out.println("inside  here  ");
        try {
		Server server = new Server("https://horizon-testnet.stellar.org");
        KeyPair source = KeyPair.fromSecretSeed("SCLZPABBH62BBZSQQ5SYALSYNCCP5VL3I43CT27WPKCMIQCCS4D5Z4DH");
        Page<OfferResponse> offerResponse = server.offers().forSeller(source.getAccountId()).execute();
        
        for (OfferResponse offers: offerResponse.getRecords()) {
        	System.out.println("account  "+source.getAccountId());
        	System.out.println("selling  "+offers.getSelling());
        	System.out.println("buying  "+offers.getBuying());
            System.out.println("date  "+offers.getLastModifiedTime());
            System.out.println("price  "+offers.getPrice());
            System.out.println("amount  "+offers.getAmount());
            System.out.println("id  "+offers.getId());
            
        }
        }catch (Exception e) {
        	  System.out.println("error is:-  "+e.getMessage());
        }
    }
	
	public static boolean manageSellOffer( String liquiditySecretKey, String assetCode, String issuerPublicKey, 
			String amountToSell, String pricePerUnit) throws Exception {
		boolean result = false;
		liquiditySecretKey = "SCKOZGAMJ4ZKXWMBL3QH4UE6ASZCQ4VKIEXQAAMFFRIHMZUY5WYKF4JV";
		amountToSell = "30000";
		pricePerUnit  ="0.602182";
		assetCode = "HKD";
		issuerPublicKey = "GC3KTJHUQV4XVUTEJPUYLKFCHPYIRFDSBVVO3BHAMFADVRJJV6YWANSQ";
		try {
			Server server = new Server("https://horizon-testnet.stellar.org");

			KeyPair source = KeyPair.fromSecretSeed(liquiditySecretKey);
				System.out.println("source");
			// First, check to make sure that the destination account exists.
			// You could skip this, but if the account does not exist, you will be charged
			// the transaction fee when the transaction fails.
			// It will throw HttpResponseException if account does not exist or there was
			// another error.

			// If there was no error, load up-to-date information on your account.
			AccountResponse sourceAccount = server.accounts().account(source.getAccountId());

			// Load PORTE Asset
			Asset selling = Asset.createNonNativeAsset(assetCode, issuerPublicKey);// Loading Custom Asset
			Asset buying = new AssetTypeNative();
			System.out.println("buying "+ buying+ " selling "+ selling);

			// Start building the offer.
							
			Transaction transaction = new Transaction.Builder(sourceAccount, Network.TESTNET)
					.addOperation( new ManageSellOfferOperation.Builder(selling, buying, amountToSell, pricePerUnit).build())
					// A memo allows you to add your own metadata to a transaction. It's
					// optional and does not affect how Stellar treats the transaction.
					.addMemo(Memo.text("Create Sell Offer"))
				
					// Wait a maximum of three minutes for the transaction
					.setTimeout(180) //
					// Set the amount of lumens you're willing to pay per operation to submit your
					// transaction
					.setBaseFee(Transaction.MIN_BASE_FEE).build();
			// Sign the transaction to prove you are actually the person sending it.
			transaction.sign(source);
			
			
			System.out.println("after sign ");

			// And finally, send it off to Stellar!
			SubmitTransactionResponse response = null;
			try {
				response = server.submitTransaction(transaction);
				 if(response.getExtras()==null) {
					System.out.println("sell offer created ");
					  result = true;

				  }else {
					  
						System.out.println("transaction: "+ response.getExtras().getResultCodes().getTransactionResultCode());
						//System.out.println("operations: "+ response.getExtras().getResultCodes().getOperationsResultCodes());
						//System.out.println("Result XDR: "+ response.getEnvelopeXdr().get());
						//System.out.println("Result decoded "+ response.getDecodedTransactionResult());
						String operationError = response.getExtras().getResultCodes().getOperationsResultCodes().toString();
						System.out.println("operations "+ operationError);


						
						//byte[] decoded = Base64.getDecoder().decode(encoded);
						
						//byte[] decoded = Base64.getDecoder().decode(encoded);

//						XdrDataInputStream in = new XdrDataInputStream(new ByteArrayInputStream(decoded));
//						TransactionEnvelope.decode(in);
						
						//String helloAgain = new String(decoded, StandardCharsets.UTF_8) ;
					    //LOGGER.info(encodedHello + " decoded=> " + helloAgain);
						//System.out.println("decoded "+ helloAgain);

					  //System.out.println("Failed!");
					  //System.out.println("getExtras "+response.getExtras().getResultCodes());
					  //NeoBankEnvironment.setComment(3, className, "Error occured in Porte Transfer: " + e.getMessage());
 
						
						System.out.println("error : "+ StellarSDKUtility.getSellOfferResultCode(operationError));

				  }
			} finally {
				if (server != null)
					server.close();
				if (response != null)
					response = null;
				if (transaction != null)
					transaction = null;
				if (sourceAccount != null)
					sourceAccount = null;
				if (source != null)
					source = null;
			}

		} catch (Exception e) {
			result = false;
			throw new Exception(" Method manageSellOffer:  Error is " + e.getMessage());
		}
		return result;
	}
	
	public static boolean manageBuyOffer( String liquiditySecretKey, String assetCode, String issuerPublicKey, 
			String amountToSell, String pricePerUnit) throws Exception {
		boolean result = false;
		liquiditySecretKey = "SCKOZGAMJ4ZKXWMBL3QH4UE6ASZCQ4VKIEXQAAMFFRIHMZUY5WYKF4JV";
		amountToSell = "20000";
		pricePerUnit  ="0.3278688";
		assetCode = "HKD";
		issuerPublicKey = "GC3KTJHUQV4XVUTEJPUYLKFCHPYIRFDSBVVO3BHAMFADVRJJV6YWANSQ";
		try {
			Server server = new Server("https://horizon-testnet.stellar.org");

			KeyPair source = KeyPair.fromSecretSeed(liquiditySecretKey);
				System.out.println("source");
			
			AccountResponse sourceAccount = server.accounts().account(source.getAccountId());

			Asset selling = new AssetTypeNative();
			Asset buying = Asset.createNonNativeAsset(assetCode, issuerPublicKey);// Loading Custom Asset
			System.out.println("buying asset "+ buying+ " selling  asset"+ selling);
							
			Transaction transaction = new Transaction.Builder(sourceAccount, Network.TESTNET)
					.addOperation( new ManageBuyOfferOperation.Builder(selling, buying, amountToSell, pricePerUnit).build())
					.addMemo(Memo.text("Create Buy Offer"))
					.setTimeout(180) //
					.setBaseFee(Transaction.MIN_BASE_FEE).build();
			transaction.sign(source);

			// And finally, send it off to Stellar!
			SubmitTransactionResponse response = null;
			try {
				response = server.submitTransaction(transaction);
				 if(response.getExtras()==null) {
					System.out.println("Buy offer created ");
					  result = true;

				  }else {
					  
						System.out.println("transaction: "+ response.getExtras().getResultCodes().getTransactionResultCode());
						//System.out.println("operations: "+ response.getExtras().getResultCodes().getOperationsResultCodes());
						//System.out.println("Result XDR: "+ response.getEnvelopeXdr().get());
						//System.out.println("Result decoded "+ response.getDecodedTransactionResult());
						String encoded = response.getExtras().getResultXdr();
						String operationError = response.getExtras().getResultCodes().getOperationsResultCodes().toString();
						System.out.println("operations "+ operationError);


						
						//byte[] decoded = Base64.getDecoder().decode(encoded);
						
						//byte[] decoded = Base64.getDecoder().decode(encoded);

//						XdrDataInputStream in = new XdrDataInputStream(new ByteArrayInputStream(decoded));
//						TransactionEnvelope.decode(in);
						
						//String helloAgain = new String(decoded, StandardCharsets.UTF_8) ;
					    //LOGGER.info(encodedHello + " decoded=> " + helloAgain);
						//System.out.println("decoded "+ helloAgain);

					  //System.out.println("Failed!");
					  //System.out.println("getExtras "+response.getExtras().getResultCodes());
					  //NeoBankEnvironment.setComment(3, className, "Error occured in Porte Transfer: " + e.getMessage());
 
						
						System.out.println("error : "+ StellarSDKUtility.getBuyOfferResultCode(operationError));

				  }
			} finally {
				if (server != null)
					server.close();
				if (response != null)
					response = null;
				if (transaction != null)
					transaction = null;
				if (sourceAccount != null)
					sourceAccount = null;
				if (source != null)
					source = null;
			}

		} catch (Exception e) {
			result = false;
			throw new Exception(" Method manageBuyOffer:  Error is " + e.getMessage());
		}
		return result;
	}
	
	public static void getAccountTransactions(KeyPair accountId)throws IOException, JsonSyntaxException, ParseException, org.json.simple.parser.ParseException {
        System.out.println("ingetAccountTransactions  ");
/*
		Server server = new Server("https://horizon-testnet.stellar.org");
        KeyPair source = KeyPair.fromSecretSeed("SADDARHYGUQ6IZ2BSXBX4U72VSJ7T3MPB2IMQKC7CCVVXE3MQN7WJKXS");
        Page<TransactionResponse> transactionResponse = server.transactions().forAccount(source.getAccountId()).execute();
        Page<OperationResponse> operationResponse = server.operations().forAccount(source.getAccountId()).execute();
        System.out.println("transaction size  "+ transactionResponse.getRecords().size());
        
        System.out.println("operation size  "+ operationResponse.getRecords().size());
        for (TransactionResponse transaction: transactionResponse.getRecords()) {
        	System.out.println("txnHash  "+transaction.getHash());
        	System.out.println("ledger  "+transaction.getLedger());
        	System.out.println("age  "+transaction.getCreatedAt());
            System.out.println("account  "+transaction.getSourceAccount());
            System.out.println("operation  "+transaction.getLinks().getSelf().getHref());
            
            System.out.println("fee  "+transaction.getFeeCharged());
            System.out.println("memo  "+transaction.getMemo());
            
        }
        
        for (OperationResponse opreation: operationResponse.getRecords()) {
        	
            System.out.println("id  "+opreation.getId());
            System.out.println("success  "+opreation.isTransactionSuccessful());
            //System.out.println("sourceAccount  "+opreation.get);
            System.out.println("type  "+opreation.getType());
            System.out.println("links  "+opreation.getLinks().getSelf().getHref());
            
        }*/
        
    /*    OkHttpClient client = new OkHttpClient().newBuilder()
        		  .build();
        		Request request = new Request.Builder()
        		  .url("https://horizon-testnet.stellar.org/accounts/"
        		  		+ "GAEMTJSZFPJOB5ZFHWW5JHPRUHCIEJKAAK4VU33QGKPPEXMH3UAKK5P7/operations?"
        		  		+ "limit=10&order=desc&"
        		  		+ "join=transactions")
        		  .method("GET", null)
        		  .build();
        		Response response = client.newCall
        				(request).execute();
        				*/
               //System.out.println("response: "+response.toString());
        
         String account = "GALBMMADCBZJOA77QXEFWZOZ3LHV6CIPRLFGYNQEHWQHJFJS3UC6HWDY";
         String limit = "100";
         String uri = "https://horizon-testnet.stellar.org/accounts/"
    		  		+ account+"/operations?"
    		  		+ "limit="+limit+"&order=desc&"
    		  		+ "join=transactions";
         
         //System.out.println(" == uri: "+uri);

               CloseableHttpClient client = HttpClients.createDefault();
               HttpGet httpGet = new HttpGet(uri);
               CloseableHttpResponse response = client.execute(httpGet);
               if (response.getCode()==200) {
                   String result = null;
				try {
					result = EntityUtils.toString(response.getEntity());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JSONParser parser = new JSONParser();
			 	JSONObject   jsonObject = (JSONObject) parser.parse(result.toString());
			 	 JSONObject jsonObjectLinks = (JSONObject) parser.parse(jsonObject.get("_links").toString());
			 	 String jsonObjectLinksPrety = jsonPretyPrint(jsonObjectLinks);
                // System.out.println(" == _links: "+jsonObjectLinksPrety);
                   
  			 	 JSONObject jsonObjectEmbeded = (JSONObject) parser.parse(jsonObject.get("_embedded").toString());
			 	  jsonObjectLinksPrety = jsonPretyPrint(jsonObjectEmbeded);
	             // System.out.println(" == _embedded: "+jsonObjectLinksPrety);

				  String records = jsonObjectEmbeded.get("records").toString();		             
		         // System.out.println(" == records: "+records);

		          JSONArray jsonArray = (JSONArray) jsonObjectEmbeded.get("records");
		         System.out.println(" == jsonArray size "+jsonArray.size());

	              for (int i = 0; i < jsonArray.size(); i++) {

	   			 	 JSONObject transactionsObj = (JSONObject) parser.parse(jsonArray.get(i).toString());
				 	  jsonObjectLinksPrety = jsonPretyPrint(transactionsObj);
		            // System.out.println(" == transaction " +i+" "+jsonObjectLinksPrety);
		             String operationId = transactionsObj.get("id").toString();
	                 String created_at = transactionsObj.get("created_at").toString();
	                 String source_account = transactionsObj.get("source_account").toString();
	                 String type = transactionsObj.get("type").toString();
	                 String transactionSuccess = transactionsObj.get("transaction_successful").toString();
	                 System.out.println(" == type: "+type+" "+i);
	                 String txnmode  = null;
	                 if(type.equals("payment")) {
		                 String from = transactionsObj.get("from").toString();
		                 String to = transactionsObj.get("to").toString();
		                 String amount = transactionsObj.get("amount").toString();
		                 String asset_code = transactionsObj.get("asset_code").toString();
		                 if(source_account.equals(from)) {
		                	  txnmode = "D";		                	 
		                 }else  {
		                	  txnmode = "C";		                	 
		                 }
				         
		                 System.out.println(" == asset_code: "+asset_code+" "+i);
	                 }else if(type.equals("manage_sell_offer") ) {
		                 String sellAsset = transactionsObj.get("selling_asset_code").toString();
		                 String buyAsset = transactionsObj.get("buying_asset_type").toString();
		                 String price = transactionsObj.get("price").toString();
		                 //String offerId = transactionsObj.get("offer_id").toString();
		                 if(buyAsset.equals("native")) {
		                	 buyAsset = "XLM";
		                 }
		                 if(sellAsset.equals("native")) {
		                	 sellAsset = "XLM";
		                 }
		                 System.out.println(" == sellAsset: "+sellAsset+" "+i);
		                 txnmode = "D";


	                 }else if(type.equals("manage_buy_offer")) {
			                 String buyAsset = transactionsObj.get("buying_asset_code").toString();
			                 String sellAsset = transactionsObj.get("selling_asset_type").toString();
			                 String price = transactionsObj.get("price").toString();
			                // String offerId = transactionsObj.get("offer_id").toString();
			                 if(buyAsset.equals("native")) {
			                	 buyAsset = "XLM";
			                 }
			                 if(sellAsset.equals("native")) {
			                	 sellAsset = "XLM";
			                 }
			                 System.out.println(" == buyAsset: "+buyAsset+" "+i);
			                 txnmode = "D";

			                 //String asset_code = transactionsObj.get("asset_code").toString();

	                 } else if(type.equals("change_trust")) {
		                 String trustee = transactionsObj.get("trustee").toString();
		                 String trustor = transactionsObj.get("trustor").toString();
		                 String asset_code = transactionsObj.get("asset_code").toString();
		                 System.out.println(" == asset_code: "+asset_code +" "+i);
		                 txnmode = "D";

	                 } else if(type.equals("path_payment_strict_send")) {
	                	   String from = transactionsObj.get("from").toString();
			               String to = transactionsObj.get("to").toString();
			               String amount = transactionsObj.get("amount").toString();
			               String asset_code = transactionsObj.get("asset_code").toString();
			                txnmode = "C";		                	 
		                 System.out.println(" == asset_code: "+asset_code +" "+i);
		                 
		                 //Path payment 3.4194528 USDC to GALBMMADCB... [ source: 100.0000000 PORTE ]
		                 String description = "Path Payment "+amount + " "+ asset_code+" to "+ Utilities.ellipsis(transactionsObj.get("to").toString(), 13);
		                 System.out.println(" == description: "+description );
	                 }else if(type.equals("path_payment_strict_receive")) {
		                 System.out.println(" == path_payment_strict_receive: "+operationId);
	                	   String from = transactionsObj.get("from").toString();
			               String to = transactionsObj.get("to").toString();
			               String amount = transactionsObj.get("amount").toString();
			                txnmode = "C";		                	 
		                 
		                 //Path payment 3.4194528 USDC to GALBMMADCB... [ source: 100.0000000 PORTE ]
		                 System.out.println(" == txnmode: "+txnmode +" "+i);
		                 String asset_code = null;
		                 if (transactionsObj.get("asset_type").toString().equals("native")) {
		                	 asset_code = "XLM";
						}else {
				             asset_code = transactionsObj.get("asset_code").toString();							
						}
		                 System.out.println(" == asset_code: "+asset_code +" "+operationId);
		                 String description = "Path Payment "+amount + " "+ asset_code+" to "+ Utilities.ellipsis(transactionsObj.get("to").toString(), 13);
		                 System.out.println(" == description: "+description );

		                 
	                 }else if(type.equals("create_account")) {
		                 System.out.println(" == create_account: "+operationId);

	                	   String from = transactionsObj.get("funder").toString();
			               String to = transactionsObj.get("account").toString();
			               String amount = transactionsObj.get("starting_balance").toString();
			               txnmode = "D";		                	 
		                 
		                 //Path payment 3.4194528 USDC to GALBMMADCB... [ source: 100.0000000 PORTE ]
		                 System.out.println(" == txnmode: "+txnmode +" "+i);
		               
		                 //Create account GDSSSUO4X7... with 10000.0000000 XLM
		                  String description = "Create account "+Utilities.ellipsis(transactionsObj.get("account").toString(), 13) + " with  "+ amount+" XLM " ;
			                 System.out.println(" == description: "+description +" "+operationId);

		                 
	                 }
	                	                	 
	   			 	 JSONObject transactionsObj2 = (JSONObject) parser.parse(transactionsObj.get("transaction").toString());
	                 String fee = transactionsObj2.get("fee_charged").toString();
	                 String ledger = transactionsObj2.get("ledger").toString();
	                 System.out.println(" == txmMode : "+txnmode +" "+i);

	              }
	              
			 	 // JSONParser parser = new JSONParser();
			 	  //JSONObject   jsonObject = (JSONObject) parser.parse(responseJson.toString());
		             		             
    		    } else {
                    System.out.println("failed: "+response.toString());

    		    }
        
      }
	public static String jsonPretyPrint(JSONObject jsonObject)  {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
	 	String json = gson.toJson(jsonObject);
		return json;
	}
	
	public static void pathPaymentStrictSend() { 
		try {
			System.out.println("start pathPaymentStrictSend " +java.time.LocalDateTime.now());
			Server server = new Server("https://horizon-testnet.stellar.org");
		 				
			KeyPair sourceAccountKeyPair = KeyPair.fromSecretSeed("SAZ466WOZT4LAE7RDGMJGCNBHCA4WYYNJFCYDPBUDATRQJOEFBXL4XUL");
		 	String sourceAssetCode =  "PORTE";
		 	String destionAssetCode = "USDC";
			String sourceIssuierPublicKey = "GB64HJF3GAVGQAPOZ7WGDPBWLSXJHJOAIN5MWDKWZUACM263QEPQ4FS4";
			String destinationIssuierPublicKey = "GC5W3BH2MQRQK2H4A6LP3SXDSAAY2W2W64OWKKVNQIAOVWSAHFDEUSDC";

		 	server.accounts().account(sourceAccountKeyPair.getAccountId());
		 	AccountResponse sourceAccount = server.accounts().account(sourceAccountKeyPair.getAccountId());

		   //Create an object to represent the new asset
		 	Asset sourceAsset = Asset.createNonNativeAsset(sourceAssetCode, sourceIssuierPublicKey);
		 	Asset destinationAsset = Asset.createNonNativeAsset(destionAssetCode, destinationIssuierPublicKey);
		 		 			 	
		 	//Intermediate path asset
		 	AssetTypeNative nativeAsset = new AssetTypeNative();
		 	//Porte XLM USDC
		 	Asset[] path = {nativeAsset};
		 	String destMinAmount = "0.0226423";//USDC
		 	String sendAmount = "1";//PORTE
		 	
		 	Transaction transaction = new Transaction.Builder(sourceAccount, Network.TESTNET)
			        .addOperation(new PathPaymentStrictSendOperation.Builder(
			        		sourceAsset, sendAmount, sourceAccountKeyPair.getAccountId(), 
			        		destinationAsset, destMinAmount )
			        		.setPath(path)
			        		.setSourceAccount("GALBMMADCBZJOA77QXEFWZOZ3LHV6CIPRLFGYNQEHWQHJFJS3UC6HWDY")
			        		.build())
			        .addMemo(Memo.text("Path Payment Strict Send"))
			        .setTimeout(180)
			        .setBaseFee(Transaction.MIN_BASE_FEE)
			        .build();
			transaction.sign(sourceAccountKeyPair);
			
			SubmitTransactionResponse response = null;
			
			try {
				  response = server.submitTransaction(transaction);
				 
				  if(response.getExtras()==null) {
						System.out.println("Success ");
					  }else {
							String operationError = response.getExtras().getResultCodes().getOperationsResultCodes().toString();
							System.out.println(" operationError "+ StellarSDKUtility.getPathPaymentStrictSendResultCode(operationError));
					  }
				} catch (Exception e) {
				  System.out.println("Something went wrong!");
				  System.out.println(e.getMessage());
				 
				}
			System.out.println("end paymentStrictSend " +java.time.LocalDateTime.now());

		} catch (Exception e) {
			System.out.println("Error from strictSend is: "+e.getMessage());
		}
	}
	
	public static void pathPaymentStrictReceive() {
		try {
			System.out.println("start pathPaymentStrictReceive " +java.time.LocalDateTime.now());
			Server server = new Server("https://horizon-testnet.stellar.org");
		 				
			KeyPair sourceAccountKeyPair = KeyPair.fromSecretSeed("SAZ466WOZT4LAE7RDGMJGCNBHCA4WYYNJFCYDPBUDATRQJOEFBXL4XUL");
		 	String sourceAssetCode =  "PORTE";
		 	String destionAssetCode = "USDC";
			String sourceIssuierPublicKey = "GB64HJF3GAVGQAPOZ7WGDPBWLSXJHJOAIN5MWDKWZUACM263QEPQ4FS4";
			String destinationIssuierPublicKey = "GC5W3BH2MQRQK2H4A6LP3SXDSAAY2W2W64OWKKVNQIAOVWSAHFDEUSDC";

		 	server.accounts().account(sourceAccountKeyPair.getAccountId());
		 	AccountResponse sourceAccount = server.accounts().account(sourceAccountKeyPair.getAccountId());

		   //Create an object to represent the new asset
		 	Asset sourceAsset = Asset.createNonNativeAsset(sourceAssetCode, sourceIssuierPublicKey);
		 	Asset destinationAsset = Asset.createNonNativeAsset(destionAssetCode, destinationIssuierPublicKey);
		 	
			//Intermediate path asset
		 	AssetTypeNative nativeAsset = new AssetTypeNative();
		 	//Porte XLM USDC
		 	Asset[] path = {nativeAsset};
		 	String maximumSendAmount = "0.662163";//PORTE
		 	String destinationAmount = "0.0226423";//USDC
		 			 	
		 	Transaction transaction = new Transaction.Builder(sourceAccount, Network.TESTNET)
			        .addOperation(new PathPaymentStrictReceiveOperation.Builder(
			        		sourceAsset, maximumSendAmount, sourceAccountKeyPair.getAccountId(),
			        		destinationAsset, destinationAmount  )
			        		.setPath(path)
			        		.setSourceAccount(sourceAccountKeyPair.getAccountId())
			        		.build())
			        .addMemo(Memo.text("Test Transaction"))
			        .setTimeout(180)
			        .setBaseFee(Transaction.MIN_BASE_FEE)
			        .build();
			transaction.sign(sourceAccountKeyPair);
			
			SubmitTransactionResponse response = null;
			
			try {
				  response = server.submitTransaction(transaction);
				 
				  if(response.getExtras()==null) {
						System.out.println("Success ");
					  }else {
						  String operationError = response.getExtras().getResultCodes().getOperationsResultCodes().toString();
							System.out.println(" operationError "+ StellarSDKUtility.getPathPaymentStrictReceiveResultCode(operationError));
					  }
				} catch (Exception e) {
				  System.out.println("Something went wrong!");
				  System.out.println(e.getMessage());
				  // If the result is unknown (no response body, timeout etc.) we simply resubmit
				  // already built transaction:
				  // SubmitTransactionResponse response = server.submitTransaction(transaction);
				}
		 	
			
		} catch (Exception e) {
			System.out.println("Error from strictReceive is: "+e.getMessage());
		}
	}
	
	  public static void getPathStrictReceiveWithSourceAccount0() throws Exception {
		   // Server server = new Server("https://horizon-testnet.stellar.org");
		   // Asset sourceAssets = Asset.createNonNativeAsset("PORTE", "GABBOADJ4AHN7GDYACJB2CMKK45CKMCHKJEAWICWZTZK7GA3D4C7CCKN");
		    //Asset destinationAsset = Asset.createNonNativeAsset("USDC", "GC5W3BH2MQRQK2H4A6LP3SXDSAAY2W2W64OWKKVNQIAOVWSAHFDEUSDC");
		  
		    String sourceAccount = "GALBMMADCBZJOA77QXEFWZOZ3LHV6CIPRLFGYNQEHWQHJFJS3UC6HWDY";
		    String destAccount = "GC5W3BH2MQRQK2H4A6LP3SXDSAAY2W2W64OWKKVNQIAOVWSAHFDEUSDC";
		    String destAmount = "1";
		    String destAssetType = "credit_alphanum4";
		    String destAssetCode = "USDC";
		    String destAssetIssuer = "GC5W3BH2MQRQK2H4A6LP3SXDSAAY2W2W64OWKKVNQIAOVWSAHFDEUSDC";
		  
		    String cursor = "13537736921089";
		    String order = "asc";
		    String path = "native";
		    
		    String uri2 = "https://horizon-testnet.stellar.org/paths/strict-receive?"
		    		+ "source_account="+sourceAccount+"&destination_account="+destAccount+"&"
		    		+ "destination_amount="+destAmount+"&destination_asset_type="+destAssetType+"&destination_asset_code="+destAssetCode+"&"
		    		+ "destination_asset_issuer="+destAssetIssuer+"&cursor="+cursor+"&limit&order="+order+"";

		    System.out.println("uri2 is "+ uri2);

		  CloseableHttpClient client = HttpClients.createDefault();
          HttpGet httpGet = new HttpGet(uri2.toString());
          CloseableHttpResponse response = client.execute(httpGet);
          if (response.getCode()==200) {
  			System.out.println("response is "+response.getCode());

              String result = null;
				try {
					result = EntityUtils.toString(response.getEntity());
					System.out.println("result is "+result);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
								
				 JSONParser parser = new JSONParser();
			 	 JSONObject   jsonObject = (JSONObject) parser.parse(result.toString());
			 	 JSONObject jsonObjectEmbeded = (JSONObject) parser.parse(jsonObject.get("_embedded").toString());
				 JSONArray jsonArray = (JSONArray) jsonObjectEmbeded.get("records");
			 	  System.out.println("=====jsonArray size "+ jsonArray.size());

	              for (int i = 0; i < jsonArray.size(); i++) {
				 	  System.out.println("=== in  upper loop "+ i );

	   			 	 JSONObject jsonAssetToDestObj = (JSONObject) parser.parse(jsonArray.get(i).toString());
				 	  String sourceAssetCode = null;
				 	
				 	  if(jsonAssetToDestObj.get("source_asset_type").toString().equals("native")) {
				 		 sourceAssetCode = "XLM";
				 	  }else {
				         sourceAssetCode = jsonAssetToDestObj.get("source_asset_code").toString();
				 	  }

			           String destinationAssetCode = null;
				 	 if(jsonAssetToDestObj.get("destination_asset_type").toString().equals("native")) {
				 		 destinationAssetCode = "XLM";
				 	  }else {
				 		 destinationAssetCode = jsonAssetToDestObj.get("destination_asset_code").toString();
				 	  }
					 System.out.println("=== sourceAssetCode "+ sourceAssetCode + " destinationAssetCode "+ destinationAssetCode);
			          
					 String sourceAmount = jsonAssetToDestObj.get("source_amount").toString();
			         String destinationAmount = jsonAssetToDestObj.get("destination_amount").toString();
					 System.out.println("=== sourceAmount "+ sourceAmount + " destinationAmount "+ destinationAmount);
			           					 
			 		  JSONArray jsonArrayPath = (JSONArray) jsonAssetToDestObj.get("path");
				 	  System.out.println("=====path size "+ jsonArrayPath.size());
	
					    for (int j = 0; j < jsonArrayPath.size(); j++) {
			   			 	 JSONObject pathObj = (JSONObject) parser.parse(jsonArrayPath.get(j).toString());
					          String assetType = pathObj.get("asset_type").toString();
					          String pathAssetCode = null;
					           if(assetType.equals("native")) {
					        	   pathAssetCode = "XLM";
					           }else {
					        	   pathAssetCode = pathObj.get("asset_code").toString();
					           }
						 System.out.println("==== pathAssetType "+ assetType + " pathAssetCode "+ pathAssetCode );
					    }  
	              }
          }else {
  			System.out.println("getPathStrictReceive Failed: ");            	
          }
          		  
		  try {
			
		} catch (Exception e) {
			System.out.println("Exception in getPathStrictReceiveWithSourceAccount is "+response.getCode());
		}
	 } 
	  					
	  public static void getPathStrictSendWithDestinationAssets() throws Exception {		  
		  	String sourceAssetCode= "PORTE";
		    String destAccount = "GC5W3BH2MQRQK2H4A6LP3SXDSAAY2W2W64OWKKVNQIAOVWSAHFDEUSDC";
		    String sourceAmount = "1";
		    String sourceAssetType = "credit_alphanum12";
		    String sourceAssetIssuer = "GB64HJF3GAVGQAPOZ7WGDPBWLSXJHJOAIN5MWDKWZUACM263QEPQ4FS4";
		    String destAssetCode = "USDC";
		    String destAssetIssuer = "GC5W3BH2MQRQK2H4A6LP3SXDSAAY2W2W64OWKKVNQIAOVWSAHFDEUSDC";
		    String cursor = "13537736921089";
		    String order = "asc";
		    String limit = "3";
		    String path = "/paths/strict-send";
		  String exchangeRate = null;
		 // Asset customerAsset = Asset.createNonNativeAsset(destAssetCode, destAssetIssuer);
		  Asset customerAsset = new AssetTypeNative();
		 List<Asset> assets = Lists.newArrayList();
		 assets.add(customerAsset);
		 String destinationAsssets = Arrays.toString(assets.toArray()).replace("[", "").replace("]", "");

			URIBuilder builder = new URIBuilder();
			builder.setScheme("https").setHost("horizon-testnet.stellar.org").setPath(path)
		    .setParameter("destination_assets",destinationAsssets)
		    .setParameter("source_amount", sourceAmount)
		    .setParameter("source_asset_type", sourceAssetType)
		    .setParameter("source_asset_code", sourceAssetCode)
			.setParameter("source_asset_issuer", sourceAssetIssuer)
			.setParameter("cursor", cursor)
			.setParameter("limit", limit)
			.setParameter("order", order);
			URI uri = builder.build();
			
		 CloseableHttpClient client = HttpClients.createDefault();
         HttpGet httpGet = new HttpGet(uri);
         CloseableHttpResponse response = client.execute(httpGet);
         if (response.getCode()==200) {
 			//System.out.println("response is "+EntityUtils.toString(response.getEntity()));

             String result = null;
				try {
					result = EntityUtils.toString(response.getEntity());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
								
				 JSONParser parser = new JSONParser();
			 	 JSONObject   jsonObject = (JSONObject) parser.parse(result.toString());
			 	 JSONObject jsonObjectEmbeded = (JSONObject) parser.parse(jsonObject.get("_embedded").toString());
				 JSONArray jsonArray = (JSONArray) jsonObjectEmbeded.get("records");
	              for (int i = 0; i < jsonArray.size(); i++) {
	   			 	 JSONObject jsonAssetToDestObj = (JSONObject) parser.parse(jsonArray.get(i).toString());
				 	 /* String sourceAssetCode = null;

				 	  if(jsonAssetToDestObj.get("source_asset_type").toString().equals("native")) {
				 		 sourceAssetCode1 = "XLM";
				 	  }else {
				         sourceAssetCode1 = jsonAssetToDestObj.get("source_asset_code").toString();
				 	  }
			           String destinationAssetCode = null;
				 	 if(jsonAssetToDestObj.get("destination_asset_type").toString().equals("native")) {
				 		 destinationAssetCode = "XLM";
				 	  }else {
				 		 destinationAssetCode = jsonAssetToDestObj.get("destination_asset_code").toString();
				 	  }
					 System.out.println("=== sourceAssetCode "+ sourceAssetCode + " destinationAssetCode "+ destinationAssetCode);

					 String sourceAmount = jsonAssetToDestObj.get("source_amount").toString();
					*/
					 String destinationAmount = jsonAssetToDestObj.get("destination_amount").toString();
					 //System.out.println("=== sourceAmount "+ sourceAmount + " destinationAmount "+ destinationAmount);
		           
			 		  JSONArray jsonArrayPath = (JSONArray) jsonAssetToDestObj.get("path");
				 	  //System.out.println("=====path size "+ jsonArrayPath.size());
				 	  
				 	  if(jsonArrayPath.size() == 1) {
				 		   for (int j = 0; j < jsonArrayPath.size(); j++) {
				   			 	 JSONObject pathObj = (JSONObject) parser.parse(jsonArrayPath.get(j).toString());
						          String assetType = pathObj.get("asset_type").toString();
						          String pathAssetCode = null;
						           if(assetType.equals("native")) {
						        	   pathAssetCode = "XLM";
						           }else {
						        	   pathAssetCode = pathObj.get("asset_code").toString();
						           }
						           
						           if(pathAssetCode.equals("XLM")) {
						        	   exchangeRate = destinationAmount;
						        	   
						           }
								 	//  System.out.println("==== pathAssetType "+ assetType + " pathAssetCode "+ pathAssetCode );
						    }   
				 	  }
				 	  
				 	 if(jsonArrayPath.size() == 0) {
			        	   exchangeRate = destinationAmount;
				 	 }
				 	  
					 
	              }
	              	              
			System.out.println("=== sourceAmount "+ sourceAmount +" "+ sourceAssetCode+ " exchangeRate "+ exchangeRate + " "+destAssetCode);

	              
	  
         }else {
  			System.out.println("getPathStrictReceive Failed: response code: "+response.getCode());
         }
         		  
		  try {
			
		} catch (Exception e) {
			System.out.println("Exception in getPathStrictReceiveWithSourceAccount is "+response.getCode());
		}
	 } 
	  
	  public static void getPathStrictReceiveWithSourceAssets() throws Exception {
			String sourceAssetCode= "PORTE";
		    String sourceAssetIssuer = "GB64HJF3GAVGQAPOZ7WGDPBWLSXJHJOAIN5MWDKWZUACM263QEPQ4FS4";
		   // String destAccount = "GC5W3BH2MQRQK2H4A6LP3SXDSAAY2W2W64OWKKVNQIAOVWSAHFDEUSDC";
		    String destinationAmount = "1";
		    String destinationAssetType = "credit_alphanum4";
		    String destAssetCode = "USDC";
		    String destAssetIssuer = "GC5W3BH2MQRQK2H4A6LP3SXDSAAY2W2W64OWKKVNQIAOVWSAHFDEUSDC";
		    String cursor = "13537736921089";
		    String order = "asc";
		    String limit = "3";
		    String path = "/paths/strict-receive";
		    String exchangeRate = null;
		    String sourceAmount = null;
		  
		    Asset customerAsset = Asset.createNonNativeAsset(sourceAssetCode, sourceAssetIssuer);
		 List<Asset> assets = Lists.newArrayList();
		 assets.add(customerAsset);
		 String sourceAsssets = Arrays.toString(assets.toArray()).replace("[", "").replace("]", "");

			URIBuilder builder = new URIBuilder();
			builder.setScheme("https").setHost("horizon-testnet.stellar.org").setPath(path)
		    .setParameter("source_assets",sourceAsssets)
		    .setParameter("destination_amount", destinationAmount)
		    .setParameter("destination_asset_type", destinationAssetType)
		    .setParameter("destination_asset_code", destAssetCode)
			.setParameter("destination_asset_issuer", destAssetIssuer)
			.setParameter("cursor", cursor)
			.setParameter("limit", limit)
			.setParameter("order", order);
			URI uri = builder.build();
			
		    System.out.println("uri2 is "+ uri);

		  CloseableHttpClient client = HttpClients.createDefault();
         HttpGet httpGet = new HttpGet(uri.toString());
         CloseableHttpResponse response = client.execute(httpGet);
         if (response.getCode()==200) {
 			System.out.println("response is "+response.getCode());

             String result = null;
				try {
					result = EntityUtils.toString(response.getEntity());
					System.out.println("result is "+result);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
								
				 JSONParser parser = new JSONParser();
			 	 JSONObject   jsonObject = (JSONObject) parser.parse(result.toString());
			 	 JSONObject jsonObjectEmbeded = (JSONObject) parser.parse(jsonObject.get("_embedded").toString());
				 JSONArray jsonArray = (JSONArray) jsonObjectEmbeded.get("records");
			 	  System.out.println("=====jsonArray size "+ jsonArray.size());

	              for (int i = 0; i < jsonArray.size(); i++) {
				 	  System.out.println("=== in  upper loop "+ i );
		   			 	 JSONObject jsonAssetToDestObj = (JSONObject) parser.parse(jsonArray.get(i).toString());

/*
				 	
				 	  if(jsonAssetToDestObj.get("source_asset_type").toString().equals("native")) {
				 		 sourceAssetCode = "XLM";
				 	  }else {
				         sourceAssetCode = jsonAssetToDestObj.get("source_asset_code").toString();
				 	  }

				 	 if(jsonAssetToDestObj.get("destination_asset_type").toString().equals("native")) {
				 		 destinationAssetCode = "XLM";
				 	  }else {
				 		 destinationAssetCode = jsonAssetToDestObj.get("destination_asset_code").toString();
				 	  }
					 System.out.println("=== sourceAssetCode "+ sourceAssetCode + " destinationAssetCode "+ destinationAssetCode);
			          */
					  sourceAmount = jsonAssetToDestObj.get("source_amount").toString();
			          destinationAmount = jsonAssetToDestObj.get("destination_amount").toString();
					 System.out.println("=== sourceAmount "+ sourceAmount + " destinationAmount "+ destinationAmount);
			           					 
			 		  JSONArray jsonArrayPath = (JSONArray) jsonAssetToDestObj.get("path");
				 	  System.out.println("=====path size "+ jsonArrayPath.size());					    
					 	  if(jsonArrayPath.size() == 1) {
					 		   for (int j = 0; j < jsonArrayPath.size(); j++) {
					   			 	 JSONObject pathObj = (JSONObject) parser.parse(jsonArrayPath.get(j).toString());
							          String assetType = pathObj.get("asset_type").toString();
							          String pathAssetCode = null;
							           if(assetType.equals("native")) {
							        	   pathAssetCode = "XLM";
							           }else {
							        	   pathAssetCode = pathObj.get("asset_code").toString();
							           }
							           
							           if(pathAssetCode.equals("XLM")) {
							        	   exchangeRate = sourceAmount;
							        	   
							           }
									 	//  System.out.println("==== pathAssetType "+ assetType + " pathAssetCode "+ pathAssetCode );
							    }   
					 	  }
	              }
	  			System.out.println("=== destinationAmount "+ destinationAmount +" "+ destAssetCode+ " exchangeRate "+ exchangeRate + " "+sourceAssetCode);

         }else {
 			System.out.println("getPathStrictReceive Failed: "+ response.getCode());   
 			System.out.println("response : "+ EntityUtils.toString(response.getEntity()));   

         }
         		  
		  try {
			
		} catch (Exception e) {
			System.out.println("Exception in getPathStrictReceiveWithSourceAccount is "+response.getCode());
		}
	 } 
	  
}
