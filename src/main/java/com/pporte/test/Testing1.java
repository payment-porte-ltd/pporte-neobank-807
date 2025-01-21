package com.pporte.test;

import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.apache.commons.lang3.RandomStringUtils;
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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pporte.utilities.SendEmailUtility;
import com.pporte.NeoBankEnvironment;
import com.pporte.utilities.StellarSDKUtility;
import com.pporte.utilities.Utilities;
import com.soneso.stellarmnemonics.Wallet;


@SuppressWarnings("unused")
public class Testing1 {
	public static void main(String[] args) throws Exception {


		//String date ="2022-02-23T12:44:21.42Z";
		//System.out.println(getStellarDateConvertor(date));

		
		//createBitcoinDeterministicWallet();
		//pathPaymentStrictSend(  );
		
//		int j = 1;
//		while(j <=15 ) {
//			System.out.println("j is "+ j);
//			System.out.println("j is "+ (j+1));
//
//			j =j+2;
//		}

		/*
		 * int count = 15; int j = 1; int x = 0; while(j <=count ) { if(j==1){ x = 0; }
		 * System.out.println("-----loop "+ j);
		 * 
		 * for(int n=1; n<=2; n++) { //System.out.println("x is "+ x);
		 * 
		 * x=x+1; }
		 * 
		 * System.out.println("first is "+ (x-1)); System.out.println("second  is "+ x);
		 * 
		 * j =j+1; }
		 * 
		 * String query = "select "; String sep = ""; for(int i = 1; i <= 15; i++) { sep
		 * = ""; if(i!= 15) { sep = ","; } query+
		 * ="ifnull((select sum(txnamount)  from txn_wallet_cust_bc where date(txndatetime) = '2021-10-31' AND walletid "
		 * +
		 * "IN (SELECT walletid FROM wallet_details WHERE relationshipno = '2111012657355700') ),0)as day"
		 * +i+sep; } //query+= " 1=1 ";
		 * 
		 * 
		 * System.out.println(query);
		 */

		

		
				
				
//		String date ="2021-11-02T05:51:31Z";
//		
//	    //System.out.println("formated date "+new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX").parse("2010-04-05T17:16:00Z"));
//
//		
//		String input = "2010-04-05T17:16:00Z";
//		//System.out.println("converted date is "+ instant);
//
//		System.out.println("**converted date is "+getStellarDateConvertor(date));
//		int i = 0;
/*		while(i <10) {
		String strTime  = String.valueOf(Instant.now().toEpochMilli()) +  RandomStringUtils.random(3, false, true);
		System.out.println("epoch time is : "+strTime +" and length is "+strTime.length());
		i++;
		}*/
		
      	 //SimpleDateFormat formatter1 = new SimpleDateFormat ("dd-MM-yyyy HH:mm:ssSSS"); 
     	 
      	try {
      		
			/*
			 * String masterCardNumber=
			 * "PORTE,GD3KCV5E7KWYSKDW7SJ7CGMM7D22OGTAMICUJOSP4R6XD7GRKLTCMGUG,20.0000000,HKD,GDOR5N7WYIEJL3JXEY76ZRNRVYFDELTG6B4UPMA6UM6HZMC5PTYISTQJ|4.1338257|[object Object]"
			 * ; System.out.println("data is "+masterCardNumber.split(",")[3]); boolean
			 * valid = Utilities.isValidCreditCardNumber(masterCardNumber);
			 * 
			 * if (!valid) { System.out.println("Iko bie"); }else {
			 * System.out.println("Acha mchezo"); }
			 */
      		/*String date="2021-11-23 09:30:27";
    		System.out.println("Date is "+Utilities.getDateTimeFormatInFullForDisplay(date));*/
      		
      	
//      		LocalDate now = LocalDate.now(); // 2015-11-23
//      		LocalDate firstDay = now.with(firstDayOfYear()); // 2015-01-01
//      		LocalDate lastDay = now.with(lastDayOfYear()); // 2015-12-31
      		
      		//String random = Utilities.genAlphaNumRandom(25);
     /* 		Double percentageChange=0.00;
      		Double previousWalletBalance=20.00;
      		String currentBalance="50";
      		 DecimalFormat df = new DecimalFormat("0.00");
      		 String formatString="";
      		
      		 percentageChange = ((1 - previousWalletBalance / Double.valueOf(currentBalance))) * 100;  
      		formatString=df.format(percentageChange).toString();
      		if (!formatString.startsWith("-")) {
      			formatString="+"+formatString;
<<<<<<< HEAD
      		}*/
      		//String password="qE8HSK3QQmk0fSjkrogHGA==";
      		//String admin="admin";
 
      		//String maskedPhoneNumber=Utilities.maskMobileNumber(phoneNumber);
      		//String encryptPassword = DigestUtils .md5Hex(StringUtils.reverse( Utilities.asciiToHex(password)));
      		//System.out.println(" admin is : "+Utilities.tripleEncryptData(admin));
      		
      		//String nge = "DFaS32w3TdUx7OlQ";
      		
      		
			/*
			 * String accountId="GASJZLTNLSXDOUKUDZL3J4E64OHEZPRLAIRL6CUFONOFJRNSFOS2RXJN";
			 * JSONObject jsonObjectLinks =StellarSDKUtility.getObjectLinks(accountId, "5");
			 * JSONParser parser = null; parser=new JSONParser(); JSONArray nextArray =
			 * (JSONArray) jsonObjectLinks.get("next"); JSONObject transactionsObj = null;
			 * for (int i=0;i<nextArray.size();i++) { transactionsObj = (JSONObject)
			 * parser.parse(nextArray.get(i).toString()); String nextLink =
			 * transactionsObj.get("href").toString();
			 * System.out.println("nextLink  is"+nextLink); }
			 */
      				
      		//byte[] b = jsonString.getBytes("UTF-8");
      	//	System.out.println(b.length);
      		String address="GDO6RLJDDBTIHFAV3OPI2Z5GFXX3KT26GOIW2IYECVZBLT7Z3Z2SYVMD";
			 //System.out.print("String is"+ (Utilities.shortenPublicKey(address)));
			 
			 /*
      		String  btcAddress="tb1q8k5s95pr4qjcz7nucyyh5nv0a2dzxf9w0azvuc";
      		ArrayList<BitcoinDetails> arrBitcoinTransaction =null; 
    		BitcoinDetails m_BitcoinDetails=null; String sResponse = null;
    		JsonObject transactionsObj=null; JsonArray transactions=null;
    		CloseableHttpClient client = null; HttpGet httpGet = null;  CloseableHttpResponse response = null;
      		client = HttpClients.createDefault();
      		String url="https://api.blockcypher.com/v1/btc/test3";
      		String token="eeb923acd325422883003b64a914938b";
      		int limit=10;
			httpGet = new HttpGet(url+
					"/addrs/"+btcAddress+"/full?limit="+limit+"&token="+token);
			response = client.execute(httpGet);
			// Response is Okay
			//System.out.println("Response Code is"+response.getCode());
			//System.out.println("Response Reason Phrase is"+response.getReasonPhrase());
			sResponse=EntityUtils.toString(response.getEntity());
			JsonObject jobj = new Gson().fromJson(sResponse, JsonObject.class);
			
			 System.out.println("jobj "+jobj);

			if (response.getCode()==200) {
					transactions = jobj.getAsJsonArray("txs");
					int count =transactions.size();
					arrBitcoinTransaction= new ArrayList<BitcoinDetails>();
					for (int i=0; i<count;i++) {
						 m_BitcoinDetails= new BitcoinDetails();
						 transactionsObj = transactions.get(i).getAsJsonObject();
						 m_BitcoinDetails.setBlockHash(transactionsObj.get("block_hash").getAsString());
						 m_BitcoinDetails.setBlockHeight(transactionsObj.get("block_height").getAsString());
						 m_BitcoinDetails.setHash(transactionsObj.get("hash").getAsString());
						 m_BitcoinDetails.setTotal(transactionsObj.get("total").getAsString());
						 m_BitcoinDetails.setFees(transactionsObj.get("fees").getAsString());
						 m_BitcoinDetails.setSize(transactionsObj.get("size").getAsString());
						 m_BitcoinDetails.setVsize(transactionsObj.get("vsize").getAsString());
						 m_BitcoinDetails.setConfirmations(transactionsObj.get("confirmations").getAsString());
						 m_BitcoinDetails.setConfidence(transactionsObj.get("confidence").getAsString());
						 m_BitcoinDetails.setConfirmedTime(transactionsObj.get("confirmed").getAsString());
						 m_BitcoinDetails.setReceivedTime(transactionsObj.get("received").getAsString());
						 arrBitcoinTransaction.add(m_BitcoinDetails);
					}
			}
			
			if (arrBitcoinTransaction!=null) {
				for (int i=0;i<arrBitcoinTransaction.size();i++) {
	      			
	      			System.out.println("Hash of "+i+" "+((BitcoinDetails)arrBitcoinTransaction.get(i)).getHash());
	      			System.out.println("received of "+i+" "+((BitcoinDetails)arrBitcoinTransaction.get(i)).getReceivedTime());
	      			
	      		}
			}
      		
		//	System.out.println("sResponse is "+sResponse);
//			if (response.getCode()==200) {
//				System.out.println("Response is ok");
//				
//			
//				if (sResponse!=null) {
//					m_BitcoinDetails= new BitcoinDetails();
//					
//					//JsonObject jobj = new Gson().fromJson(sResponse, JsonObject.class);
//					m_BitcoinDetails.setAddress(jobj.get("address").toString().replaceAll("\"", ""));
//					m_BitcoinDetails.setTotalReceived(jobj.get("total_received").toString());
//					m_BitcoinDetails.setTotalSent(jobj.get("total_sent").toString());
//					m_BitcoinDetails.setBalance(jobj.get("balance").toString());
//					m_BitcoinDetails.setUnconfirmedBalance(jobj.get("unconfirmed_balance").toString());
//					m_BitcoinDetails.setFinalBalance(jobj.get("final_balance").toString());
//					m_BitcoinDetails.setNumberOfUnconfirmedTransactions(jobj.get("unconfirmed_n_tx").toString());
//					m_BitcoinDetails.setNumberOfConfirmedTransactions(jobj.get("n_tx").toString());
//					m_BitcoinDetails.setFinalNumberOfTransactions(jobj.get("final_n_tx").toString());
//				}
//			}
      		
//      		 if (m_BitcoinDetails!=null) {
//		      		System.out.println("Address is "+m_BitcoinDetails.getAddress());
//		      		System.out.println("Balance is "+m_BitcoinDetails.getBalance());
//		      		System.out.println("Final Balance is "+m_BitcoinDetails.getFinalBalance());
//		      		System.out.println("FinalNumberOfTransactions "+m_BitcoinDetails.getFinalNumberOfTransactions());
//		      		
//      		 }else {
//      			System.out.println("No balance");
//      		 } 
      		//System.out.println("Benjamin is : " );
      		
      		//2345224765488645	
      		//2345227341284107
      		
      		/*
      		String s = "2022-02-11T07:03:06Z";
      	    TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(s);
      	    Instant i = Instant.from(ta);
      	    Date d = Date.from(i);
      	    
      	    System.out.print("Date is"+d);*/
      		
      		// SimpleDateFormat formatter1 = new SimpleDateFormat ("yyMMdd");
     
      		//String subject="New Look";
      	   //  SendEmailUtility.sendMailWithHtmlExample(email, subject);
      		String result="true"+"|"+"Benjamin";
      		String arrResult=result.substring(result.indexOf("|")+1, result.length());;
      		
      		//
      		//System.out.println("converted : "+new BigDecimal(val).toPlainString());
      		//long val = bd.longValue();
			/*
			 * String val="2207152504991253";
			 * System.out.println("converted : "+val.length()); //long val = bd.longValue();
			 * 
			 * String jsonString = "{\"name\":\"Mahesh\", \"age\":21}";
			 * 
			 * GsonBuilder builder = new GsonBuilder(); builder.setPrettyPrinting();
			 * 
			 * Gson gson = builder.create(); Student student = gson.fromJson(jsonString,
			 * Student.class); System.out.println(student);
			 * 
			 * jsonString = gson.toJson(student); System.out.println(jsonString);
			 */
      		
      		char[] mnemonic = "bench hurt jump file august wise shallow faculty impulse spring exact slush thunder author capable act festival slice deposit sauce coconut afford frown better".toCharArray();
			KeyPair keyPair0 = Wallet.createKeyPair(mnemonic, null, 0);
			KeyPair keyPair1 = Wallet.createKeyPair(mnemonic, null, 1);
			
			System.out.println("Public key: " + keyPair0.getAccountId());
			System.out.println("keyPair0 : " + keyPair0.toString());
			System.out.println("keyPair1 : " + keyPair1.getSecretSeed().toString());
		} catch (Exception e) {
			System.out.println("Error is : "+e.getMessage() );
		}


		//getFiatExchangeRatesFromCoingecko();

	}
	
	class Student { 
		   private String name; 
		   private int age; 
		   public Student(){} 
		   
		   public String getName() { 
		      return name; 
		   }
		   
		   public void setName(String name) { 
		      this.name = name; 
		   } 
		   
		   public int getAge() { 
		      return age; 
		   }
		   
		   public void setAge(int age) { 
		      this.age = age; 
		   }
		   
		   public String toString() { 
		      return "Student [ name: "+name+", age: "+ age+ " ]"; 
		   }  
		}
	
	public static JsonObject getFiatExchangeRatesFromCoingecko() throws Exception {
		JsonObject obj = null;
		try {
			Gson gson = new Gson();
			String path = "/api/v3/simple/price";
			URIBuilder builder = new URIBuilder();
			builder.setScheme("https").setHost("api.coingecko.com").setPath(path)
			.setParameter("ids", "porte-token,usd-coin,bitcoin,stellar")
			.setParameter("vs_currencies", "usd")
			.setParameter("include_market_cap", "false")
			.setParameter("include_24hr_vol", "false")
			.setParameter("include_24hr_change", "false")
			.setParameter("include_last_updated_at", "false");
			URI uri = builder.build();
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(uri);
			CloseableHttpResponse response = client.execute(httpGet);
			System.out.println("Response "+response.getCode());
			String result = EntityUtils.toString(response.getEntity());
			System.out.println("result "+result);
			if (response.getCode()!=200) {
				System.out.println("Coingecko Api failed, response is "+result);
				throw new Exception("Error from Coingecko "+result);
			}
			obj = gson.fromJson(result, JsonObject.class);
		} catch (Exception e) {
			obj = null;
			System.out.println("Error occured in getFiatExchangeRatesFromCoingecko: " + e.getMessage());
			throw new Exception("Error occured in getFiatExchangeRatesFromCoingecko: " + e.getMessage());
		}
		return obj;
		
	}
	
}

	

