package com.pporte.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import org.apache.hc.core5.net.URIBuilder;
import org.stellar.sdk.Asset;
import org.stellar.sdk.AssetTypeNative;
import org.stellar.sdk.ChangeTrustAsset;
import org.stellar.sdk.ChangeTrustOperation;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Memo;
import org.stellar.sdk.Network;
import org.stellar.sdk.PaymentOperation;
import org.stellar.sdk.Server;
import org.stellar.sdk.Transaction;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.Page;
import org.stellar.sdk.responses.SubmitTransactionResponse;
import org.stellar.sdk.responses.TransactionResponse;
import org.stellar.sdk.responses.operations.OperationResponse;
import org.stellar.sdk.xdr.TransactionEnvelope;
import org.stellar.sdk.xdr.XdrDataInputStream;

import shadow.com.google.common.collect.Lists;


public class StellarSDKTest1 {
	
	public static void main(String[] args) {
      	try {
      		//sendNativeCoinPayment();
      		//sendNoNNativeCoinPayment();
      		//accountRecords();
      		//trustIssuerAccount();
		    //Server server = new Server("https://horizon-testnet.stellar.org");
      		//testStrictSendWithDestinationAssets();
      		//sendNoNNativeCoinPayment();
      		//trustIssuerAccount();
      		//sendNoNNativeCoinPayment();
      		accountRecords();

		} catch (Exception e) {
			System.out.println("Error is : "+e.getMessage() );
		}
	}
	
	public static void sendNativeCoinPayment() {
		try {
			Server server = new Server("https://horizon-testnet.stellar.org");

			KeyPair source = KeyPair.fromSecretSeed("SBFGAOWDJCSTREBHVWBWCWNMSWQSO4BX3436MQM2Q524NPJ55ATUJFYS");
			KeyPair destination = KeyPair.fromAccountId("GDI5CDGAIJ5QQNTZ6D5JZYCKLZKXRR6K2JCZBBJWGRCP6MGW6KJ6CI6A");
			String amount ="11";
			// First, check to make sure that the destination account exists.
			// You could skip this, but if the account does not exist, you will be charged
			// the transaction fee when the transaction fails.
			// It will throw HttpResponseException if account does not exist or there was another error.
			server.accounts().account(destination.getAccountId());

			// If there was no error, load up-to-date information on your account.
			AccountResponse sourceAccount = server.accounts().account(source.getAccountId());

			// Start building the transaction.
			Transaction transaction = new Transaction.Builder(sourceAccount, Network.TESTNET)
			        .addOperation(new PaymentOperation.Builder(destination.getAccountId(), new AssetTypeNative(), amount).build())
			        // A memo allows you to add your own metadata to a transaction. It's
			        // optional and does not affect how Stellar treats the transaction.
			        .addMemo(Memo.text("Test Transaction"))
			        // Wait a maximum of three minutes for the transaction
			        .setTimeout(180)
			        // Set the amount of lumens you're willing to pay per operation to submit your transaction
			        .setBaseFee(Transaction.MIN_BASE_FEE+1)
			        .build();
			// Sign the transaction to prove you are actually the person sending it.
			transaction.sign(source);

			// And finally, send it off to Stellar!
			try {
			  SubmitTransactionResponse response = server.submitTransaction(transaction);
			  System.out.println("Success!");
			  System.out.println(response.toString());
			} catch (Exception e) {
			  System.out.println("Something went wrong!");
			  System.out.println(e.getMessage());
			  // If the result is unknown (no response body, timeout etc.) we simply resubmit
			  // already built transaction:
			  // SubmitTransactionResponse response = server.submitTransaction(transaction);
			}
		} catch (Exception e) {
			System.out.println("Error from sendNativeCoinPayment is: "+e.getMessage());
		}
		
	}
	
	public static void accountRecords()throws IOException {
        System.out.println("in  ");

		Server server = new Server("https://horizon-testnet.stellar.org");
        KeyPair source = KeyPair.fromSecretSeed("SAZ466WOZT4LAE7RDGMJGCNBHCA4WYYNJFCYDPBUDATRQJOEFBXL4XUL");
        Page<TransactionResponse> responsePage = server.transactions().forAccount(source.getAccountId()).execute();
        Page<OperationResponse> responsePage2 = server.operations().forAccount(source.getAccountId()).execute();
        for (TransactionResponse resp: responsePage.getRecords()) {
            System.out.println("source "+resp.getEnvelopeXdr());
        }
        for (OperationResponse resp: responsePage2.getRecords()) {
            System.out.println("operation  "+resp.getTransaction());
        }
       

    }
	
	/*This method will create Trustline to the Issuer account and Fund the Distributor account
	 */
	public static void trustIssuerAccount() {
		try {
			Server server = new Server("https://horizon-testnet.stellar.org");
			String limit = "1000000000";
			String amount = "1000000000";
			System.out.println("inside trustIssuerAccount");
			// Keys for accounts to issue and receive the new asset
			KeyPair issuingKeys = KeyPair .fromAccountId("GC3KTJHUQV4XVUTEJPUYLKFCHPYIRFDSBVVO3BHAMFADVRJJV6YWANSQ");
		 	KeyPair receivingKeys = KeyPair .fromSecretSeed("SCKOZGAMJ4ZKXWMBL3QH4UE6ASZCQ4VKIEXQAAMFFRIHMZUY5WYKF4JV");
		 	String assetCode = "HKD";
			// Create an object to represent the new asset
			Asset porte = Asset.createNonNativeAsset(assetCode, issuingKeys.getAccountId());

			// First, the receiving account must trust the asset
			AccountResponse receiving = server.accounts().account(receivingKeys.getAccountId());
			Transaction allowPorteDollars = new Transaction.Builder(receiving, Network.TESTNET).addOperation(
					// The `ChangeTrust` operation creates (or alters) a trustline
					// The second parameter limits the amount the account can hold
					new ChangeTrustOperation.Builder(new ChangeTrustAsset.Wrapper(porte), limit).build())
					// optional and does not affect how Stellar treats the transaction.
					.setBaseFee(Transaction.MIN_BASE_FEE + 1).addMemo(Memo.text("Test Transaction"))
					// Wait a maximum of three minutes for the transaction
					.setTimeout(180).build();
			allowPorteDollars.sign(receivingKeys);
			SubmitTransactionResponse response1 = server.submitTransaction(allowPorteDollars);
			System.out.println(response1.isSuccess());

			// Second, the issuing account actually sends a payment using the asset
			AccountResponse issuing = server.accounts().account(issuingKeys.getAccountId());
			Transaction sendPorteDollars = new Transaction.Builder(issuing, Network.TESTNET)
					.addOperation(new PaymentOperation.Builder(receivingKeys.getAccountId(), porte, amount).build())
					// optional and does not affect how Stellar treats the transaction.
					.setBaseFee(Transaction.MIN_BASE_FEE + 1).addMemo(Memo.text("Test Transaction"))
					// Wait a maximum of three minutes for the transaction
					.setTimeout(180).build();
			sendPorteDollars.sign(issuingKeys);
			SubmitTransactionResponse response2 = server.submitTransaction(sendPorteDollars);
			System.out.println(response2.isSuccess());
			if(response2.getExtras()==null) {
				System.out.println("TRUSTLINE FOR ASSET "+assetCode+" created ");
			  }
		} catch (Exception e) {
			System.out.println("Error from trustIssuerAccount is: " + e.getMessage());
		}

	}
	
	public static void sendNoNNativeCoinPayment() {
		try {
			Server server = new Server("https://horizon-testnet.stellar.org");

			KeyPair source = KeyPair.fromSecretSeed("SBF4PH7RYZH6IBI74KZ2PQVFUB7K64KK4P3YOWZSJ2OOVQ4JZPVQM7MG");
			KeyPair destination = KeyPair.fromAccountId("GANYSU7JKUWPAQ3AYGT3FWJMPOTSUR3SOZUQ2DVBME2LKBYM43IAMBPW");
			
			//KeyPair source = KeyPair.fromSecretSeed("SAYUHKDMXIYVW2TSSUIAMDIXY6D37UAB2HHKYE6X7TIZ552BTVASV2LO");
			//KeyPair destination = KeyPair.fromAccountId("GALBMMADCBZJOA77QXEFWZOZ3LHV6CIPRLFGYNQEHWQHJFJS3UC6HWDY");

			// First, check to make sure that the destination account exists.
			// You could skip this, but if the account does not exist, you will be charged
			// the transaction fee when the transaction fails.
			// It will throw HttpResponseException if account does not exist or there was another error.
			server.accounts().account(destination.getAccountId());

			// If there was no error, load up-to-date information on your account.
			AccountResponse sourceAccount = server.accounts().account(source.getAccountId());
			
			//Load PORTE Asset
			String assetCode = "HKD";
	 		String issuerPublicKey = "GC3KTJHUQV4XVUTEJPUYLKFCHPYIRFDSBVVO3BHAMFADVRJJV6YWANSQ";
	 		Asset porteAsset = Asset.createNonNativeAsset(assetCode, issuerPublicKey);//Loading Custom Asset
	 		
			// Start building the transaction.
			Transaction transaction = new Transaction.Builder(sourceAccount, Network.TESTNET)
			        .addOperation(new PaymentOperation.Builder(destination.getAccountId(), porteAsset, "700000").build())
			        // A memo allows you to add your own metadata to a transaction. It's
			        // optional and does not affect how Stellar treats the transaction.
			        .addMemo(Memo.text("Test Transaction"))
			        // Wait a maximum of three minutes for the transaction
			        .setTimeout(180)
			        // Set the amount of lumens you're willing to pay per operation to submit your transaction
			        .setBaseFee(Transaction.MIN_BASE_FEE)
			        .build();
			// Sign the transaction to prove you are actually the person sending it.
			transaction.sign(source);

			// And finally, send it off to Stellar!
			try {
			  SubmitTransactionResponse response = server.submitTransaction(transaction);
			 
			  if(response.getExtras()==null) {
				  System.out.println("Success!");
			  }else {
				  System.out.println("Failed!");
				  System.out.println("getExtras "+response.getExtras().getResultCodes());
			  }
			  
			  System.out.println("getDecodedTransactionResult "+response.getDecodedTransactionResult().toString());
			  System.out.println("getHash "+response.getHash());
			  System.out.println("getLedger "+response.getLedger());
			  System.out.println("getEnvelopeXdr "+response.getEnvelopeXdr());
			  try {
				  String transactionEnvelopeToDecode = "AAAAAgAAAABuXEfmeGKtujzpU70ygS965ZzvkePu6gHvZyTzcBPKLAAAAGQAB4OKAAAAIQAAAAEAAAAAAAAAAAAAAABhcUDoAAAAAQAAABBUZXN0IFRyYW5zYWN0aW9uAAAAAQAAAAAAAAABAAAAAJdUsgdeFJUbLhC7nXGmAViZykXjeVL/4soFeZsAJ28jAAAAAlBPUlRFAAAAAAAAAAAAAAAD1gP2z5QhbTuoo4gJXuuKXCaLRUGFwF940sNv61yr7AAAAAApuScAAAAAAAAAAAFwE8osAAAAQPLH1iQHj6mdioMb++sg0HPxEtXUuE//VwQ/0HIV8ih1lDKBkGzsNc1QjdZw01HdZgbWA2I+G8DOoDUWV5kfCwU=";
					 
				  byte[] bytes = Base64.getDecoder().decode(transactionEnvelopeToDecode);
				  XdrDataInputStream in = new XdrDataInputStream(new ByteArrayInputStream(bytes));
				  TransactionEnvelope.decode(in);
//				  String result = CharStreams.toString(new InputStreamReader(
//					       inputStream, Charsets.UTF_8));
				  String resultw = IOUtils.toString(in, StandardCharsets.UTF_8);

				  System.out.println("decoded resultw "+resultw);

//				  for (int length; (length = inputStream.read(buffer)) != -1; ) {
//					     result.write(buffer, 0, length);
//					 }
//					 // StandardCharsets.UTF_8.name() > JDK 7
//					 return result.toString("UTF-8");
				  //System.out.println("decoded EnvelopeXdr "+TransactionEnvelope.decode(in));

			} catch (Exception e) {
				  System.out.println(" error in decrept!"+ e.getMessage());

			}
			 
//			  Byte[] bytes = Base64.getDecoder().decode(transactionEnvelopeToDecode);
//			  XdrDataInputStream in = new XdrDataInputStream(new ByteArrayInputStream(bytes));
//			  TransactionEnvelope.decode(in)


			} catch (Exception e) {
			  System.out.println("Something went wrong!");
			  System.out.println(e.getMessage());
			  // If the result is unknown (no response body, timeout etc.) we simply resubmit
			  // already built transaction:
			  // SubmitTransactionResponse response = server.submitTransaction(transaction);
			}
		} catch (Exception e) {
			System.out.println("Error from sendNoNNativeCoinPayment is: "+e.getMessage());
		}
		
	}
	

	
	public static void opsFundAccount() {
		try {
			Server server = new Server("https://horizon-testnet.stellar.org");

			KeyPair source = KeyPair.fromSecretSeed("SB7AJALFNBC62RFB7VWCB7EWBT47XA6IYW4I2XFSX5CKPEDMZZFT6XS");
			KeyPair destination = KeyPair.fromAccountId("GCLVJMQHLYKJKGZOCC5Z24NGAFMJTSSF4N4VF77CZICXTGYAE5XSGUB");
			String amount ="10";
			// First, check to make sure that the destination account exists.
			// You could skip this, but if the account does not exist, you will be charged
			// the transaction fee when the transaction fails.
			// It will throw HttpResponseException if account does not exist or there was another error.
			server.accounts().account(destination.getAccountId());

			// If there was no error, load up-to-date information on your account.
			AccountResponse sourceAccount = server.accounts().account(source.getAccountId());

			// Start building the transaction.
			Transaction transaction = new Transaction.Builder(sourceAccount, Network.TESTNET)
			        .addOperation(new PaymentOperation.Builder(destination.getAccountId(), new AssetTypeNative(), amount).build())
			        // A memo allows you to add your own metadata to a transaction. It's
			        // optional and does not affect how Stellar treats the transaction.
			        .addMemo(Memo.text("Test Transaction"))
			        // Wait a maximum of three minutes for the transaction
			        .setTimeout(180)
			        // Set the amount of lumens you're willing to pay per operation to submit your transaction
			        .setBaseFee(Transaction.MIN_BASE_FEE+1)
			        .build();
			// Sign the transaction to prove you are actually the person sending it.
			transaction.sign(source);

			// And finally, send it off to Stellar!
			try {
			  SubmitTransactionResponse response = server.submitTransaction(transaction);
			  System.out.println("Success!");
			  System.out.println(response.toString());
			} catch (Exception e) {
			  System.out.println("Something went wrong!");
			  System.out.println(e.getMessage());
			  // If the result is unknown (no response body, timeout etc.) we simply resubmit
			  // already built transaction:
			  // SubmitTransactionResponse response = server.submitTransaction(transaction);
			}
		} catch (Exception e) {
			System.out.println("Error from sendNativeCoinPayment is: "+e.getMessage());
		}
		
	}
	
	public static void testStrictSendWithDestinationAssets() throws IOException, ParseException {
		try {
			
	 		Asset customerAsset = Asset.createNonNativeAsset("USDC", "GC5W3BH2MQRQK2H4A6LP3SXDSAAY2W2W64OWKKVNQIAOVWSAHFDEUSDC");
			 List<Asset> assets = Lists.newArrayList();
			 assets.add(customerAsset);
			 String destinationAsssets = Arrays.toString(assets.toArray()).replace("[", "").replace("]", "");

				System.out.println("==== assets "+assets.toString());

			 //native,USD:GAYSHLG75RPSMXWJ5KX7O7STE6RSZTD6NE4CTWAXFZYYVYIFRUVJIBJH,EUR:GAYSHLG75RPSMXWJ5KX7O7STE6RSZTD6NE4CTWAXFZYYVYIFRUVJIBJH
			URIBuilder builder = new URIBuilder();
			builder.setScheme("https").setHost("horizon-testnet.stellar.org").setPath("/paths/strict-send")
		    .setParameter("destination_assets",destinationAsssets)
		    .setParameter("source_amount", "1")
		    .setParameter("source_asset_type", "credit_alphanum12")
		    .setParameter("source_asset_code", "PORTE")
			.setParameter("source_asset_issuer", "GB64HJF3GAVGQAPOZ7WGDPBWLSXJHJOAIN5MWDKWZUACM263QEPQ4FS4")
			.setParameter("cursor", "13537736921089")
			.setParameter("limit", "3")
			.setParameter("order", "asc");
			URI uri = builder.build();
			System.out.println("uri "+uri.toURL());
			HttpGet getMethod = new HttpGet(uri);
			
		    CloseableHttpClient httpclient = HttpClients.createDefault();

		    CloseableHttpResponse response = null;

		    try {
		        response = httpclient.execute(getMethod);
		        String sbResponse = EntityUtils.toString(response.getEntity());
		        System.out.println("status "+response.getCode() + " sbResponse "+ sbResponse);	

		    } catch (IOException e) {
		        //handle this IOException properly in the future
		    } catch (Exception e) {
		        //handle this IOException properly in the future
		    }
			
		
			
			/*CloseableHttpClient httpClient=null;CloseableHttpResponse urlresponse=null; String sbResponse=null; 
			 List<NameValuePair> urlParameters = new ArrayList<>();
		     urlParameters.add(new BasicNameValuePair("destination_assets", "native%2CUSD%3AGAYSHLG75RPSMXWJ5KX7O7STE6RSZTD6NE4CTWAXFZYYVYIFRUVJIBJH%2CEUR%3AGAYSHLG75RPSMXWJ5KX7O7STE6RSZTD6NE4CTWAXFZYYVYIFRUVJIBJH"));
		     urlParameters.add(new BasicNameValuePair("source_amount", "20.50"));
		     urlParameters.add(new BasicNameValuePair("source_asset_type", "credit_alphanum4"));
		     urlParameters.add(new BasicNameValuePair("source_asset_code", "USD"));
		     urlParameters.add(new BasicNameValuePair("source_asset_issuer", "GAYSHLG75RPSMXWJ5KX7O7STE6RSZTD6NE4CTWAXFZYYVYIFRUVJIBJH"));
		     urlParameters.add(new BasicNameValuePair("cursor", "13537736921089"));
		     urlParameters.add(new BasicNameValuePair("limit", "3"));
		     urlParameters.add(new BasicNameValuePair("order", "asc"));
		     
		     request.setEntity(new UrlEncodedFormEntity(urlParameters));
		     System.out.println("request "+request.toString());
	         httpClient = HttpClients.createDefault();
	         urlresponse = httpClient.execute(request); 
	         sbResponse = EntityUtils.toString(urlresponse.getEntity());
	         System.out.println("sbResponse "+sbResponse+" status "+urlresponse.getCode());	
	         HttpGet request = new HttpGet("https://horizon-testnet.stellar.org/paths/strict-send?");*/
	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
