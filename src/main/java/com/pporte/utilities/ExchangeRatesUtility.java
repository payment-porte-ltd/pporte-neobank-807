package com.pporte.utilities;

import java.net.URI;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;

public class ExchangeRatesUtility {
	private static String className = ExchangeRatesUtility.class.getSimpleName();
	
	public static JsonObject getFiatExchangeRatesFromCoingecko() throws Exception {
		JsonObject obj = null;
		try {
			Gson gson = new Gson();
			String path = "/api/v3/simple/price";
			String currentSupportedDigitalAssets="porte-token,usd-coin,bitcoin,stellar";
			String currentSupportedFiatCurrencies="usd,aud,hkd";
			URIBuilder builder = new URIBuilder();
			builder.setScheme("https").setHost(NeoBankEnvironment.getCoingeckoBaseUrlForApacheClientRequest()).setPath(path)
			.setParameter("ids", currentSupportedDigitalAssets)
			.setParameter("vs_currencies",currentSupportedFiatCurrencies)
			.setParameter("include_market_cap", "false")
			.setParameter("include_24hr_vol", "false")
			.setParameter("include_24hr_change", "false")
			.setParameter("include_last_updated_at", "false");
			URI uri = builder.build();
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(uri);
			CloseableHttpResponse response = client.execute(httpGet);
			NeoBankEnvironment.setComment(3, className,"Response "+response.getCode());
			String result = EntityUtils.toString(response.getEntity());
			NeoBankEnvironment.setComment(3, className,"result "+result);
			if (response.getCode()!=200) {
				NeoBankEnvironment.setComment(1, className, "Coingecko Api failed, response is "+result);
				throw new Exception("Error from Coingecko "+result);
			}
			
			try {
				obj = gson.fromJson(result, JsonObject.class);
			} finally {
				if(gson!=null)gson=null;if(path!=null)path=null;
				if(builder!=null)builder=null;if(client!=null)client.close();
				if(uri!=null)uri=null;if(httpGet!=null)httpGet.clear();
				if(response!=null)response.close();if(result!=null)result=null;
				if(client!=null)client.close(); if (currentSupportedFiatCurrencies!=null)currentSupportedFiatCurrencies=null;
				if (currentSupportedDigitalAssets!=null)currentSupportedDigitalAssets=null;
			}
		} catch (Exception e) {
			obj = null;
			NeoBankEnvironment.setComment(3, className, "Error occured in getFiatExchangeRatesFromCoingecko: " + e.getMessage());
			throw new Exception("Error occured in getFiatExchangeRatesFromCoingecko: " + e.getMessage());
		}
		return obj;
		
	}
	
	public static JsonObject getBTCFiatExchangeRatesFromCoingecko() throws Exception {
		JsonObject obj = null;
		try {
			Gson gson = new Gson();
			String path = "/api/v3/simple/price";
			URIBuilder builder = new URIBuilder();
			builder.setScheme("https").setHost(NeoBankEnvironment.getCoingeckoBaseUrlForApacheClientRequest()).setPath(path)
			.setParameter("ids", "bitcoin")
			.setParameter("vs_currencies", "usd")
			.setParameter("include_market_cap", "false")
			.setParameter("include_24hr_vol", "false")
			.setParameter("include_24hr_change", "false")
			.setParameter("include_last_updated_at", "false");
			URI uri = builder.build();
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(uri);
			CloseableHttpResponse response = client.execute(httpGet);
			NeoBankEnvironment.setComment(3, className,"Response "+response.getCode());
			String result = EntityUtils.toString(response.getEntity());
			NeoBankEnvironment.setComment(3, className,"result "+result);
			if (response.getCode()!=200) {
				NeoBankEnvironment.setComment(1, className, "Coingecko Api failed, response is "+result);
				throw new Exception("Error from Coingecko "+result);
			}
			
			try {
				obj = gson.fromJson(result, JsonObject.class);
			} finally {
				if(gson!=null)gson=null;if(path!=null)path=null;
				if(builder!=null)builder=null;if(client!=null)client.close();
				if(uri!=null)uri=null;if(httpGet!=null)httpGet.clear();
				if(response!=null)response.close();if(result!=null)result=null;
				if(client!=null)client.close();
			}
		} catch (Exception e) {
			obj = null;
			NeoBankEnvironment.setComment(3, className, "Error occured in getFiatExchangeRatesFromCoingecko: " + e.getMessage());
			throw new Exception("Error occured in getFiatExchangeRatesFromCoingecko: " + e.getMessage());
		}
		return obj;
		
	}

}
