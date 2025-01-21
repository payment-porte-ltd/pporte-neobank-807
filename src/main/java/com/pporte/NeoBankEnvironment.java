package com.pporte;

import java.io.FileReader;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.LoggerFactory;

import com.pporte.NeoBankEnvironment;
import com.pporte.utilities.Utilities;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;

public class NeoBankEnvironment extends ExtensionEnvironment {
	private static String className = NeoBankEnvironment.class.getSimpleName();
	private static JSONObject JSON_LOCALE = null;
    protected static  String KEYVALUE = null;
    protected static  String FILE_UPLOAD_PATH = null; 
    protected static  String FILE_DOWNLOAD_PATH = null; 
  	private static  String LOGBACK_CONFIG_FILE_PATH = null; 
  	protected static  String EMAIL_TEMPLATE_PATH = null; 
  	protected static  String TEMP_FILE_PATH = null; 
    private static String OS = System.getProperty("os.name").toLowerCase();    
    private static  Logger logger = null;
  	private static  String PROPERTY_FILE_NAME="PPApplicationParameters.json";

     
    protected static final String CURRENCY_USD = "USD"; 
    protected static final String CURRENCY_AUD = "AUD"; 
    protected static final String CURRENCY_HKD = "HKD"; 
    protected static final String TOKEN_REGISTRATION_AMOUNT = "1"; 
    protected static final String BITCOIN_CODE =  "BTC"; 
    protected static final String ETHEREUM_CODE = "ETH"; 
    protected static final String LITECOIN_CODE = "LTC";     
    protected static final String PORTE_TOKEN_INITIAL_BALANCE = "-0.5"; 
    protected static final String STELLARLUMENS = "XLM"; 
    protected static final String PORTE_TOKEN_CODE = "PORTE"; 
    protected static final String VESSEL_COIN_CODE = "VESL"; 
    protected static final String STELLAR_BITCOIN_CODE = "BTCX"; 
    protected static final String USDC_CODE = "USDC"; 
    protected static final String DEFAULT_CUSTMOMER_USERTYPE = "C0";
    protected static final String DEFAULT_MERCHANT_USERTYPE = "M0";
    protected static final String STELLAR_CUSTOMER_TRANSACTION_LIMIT = "15";
    protected static final String STELLAR_TDA_TRANSACTION_LIMIT = "200";
    protected static final String BUSSINESS_LEDGER_THRESHOLD = "1000";
    protected static final String XLM_FUND_ACCOUNT_THRESHOLD = "10000";
    protected static final String XLM_FUND_ACCOUNT_AMOUNT = "1.5";
    protected static final String DEFAULT_PLAN_ALLOCATION_REASON_AFTER_OPS_APPROVAL = "Default allocation of plan by system after Operation approval";
    protected static final String PLAN_ALLOCATION_REASON_AFTER_PLAN_PURCHASE_BY_CUSTOMER = "Customer made a purchase of Plan Id:- ";

    public static synchronized JSONObject getInstance(){ 
    	return JSON_LOCALE; 
    	}
    public static synchronized void setInstance(JSONObject JLocale){ 
    	JSON_LOCALE = JLocale;
    	}
    public static synchronized void init() throws Exception {
    	JSONParser parser = null;
    	try {
    		if(JSON_LOCALE==null) {
       		 	parser = new JSONParser();
    		       //Properties jvm = System.getProperties();
    		       // jvm.list(System.out);
	 					System.out.print(className+ " *** catalina.home is**** "+System.getProperty("catalina.home")+"\n");
	 					try {
	 			         System.out.println(className+ " *** Now Reading Properties file**** ");	 			         	 			         
	 			         Object objRead = parser.parse(new FileReader
	 			        		 (StringUtils.replace(System.getProperty("catalina.home"), "\\", "/")+"/"+PROPERTY_FILE_NAME));
	 			         JSON_LOCALE = (JSONObject) objRead;
		 				} catch (Exception e) {
		 					System.out.println(className+ "  Exception in reading Resourcebundle "+e.getMessage());
		 				}
			     			//debugOn = Boolean.parseBoolean(Utilities.getSysParam(JSON_LOCALE.get("debugOn").toString())); 
			     			//debugOn = Boolean.parseBoolean(getParameters("debugOn")); 
		     			if(isWindows()){
		     				FILE_UPLOAD_PATH = getParameters("UPLOAD_PATH_WIN");
		     				FILE_DOWNLOAD_PATH = getParameters("DOWNLOAD_PATH_WIN");
		     				LOGBACK_CONFIG_FILE_PATH = StringUtils.replace(System.getProperty("catalina.home"), "\\", "/") + getParameters("ERROR_LOG_WIN");
		     			}else if(isUnix()){
		     				FILE_UPLOAD_PATH = getParameters("UPLOAD_PATH_LIN");
		     				FILE_DOWNLOAD_PATH = getParameters("DOWNLOAD_PATH_LIN");
		     	 			LOGBACK_CONFIG_FILE_PATH = StringUtils.replace(System.getProperty("catalina.home"), "\\", "/") + getParameters("ERROR_LOG_LIN");
		     			}else if(isSolaris()){
		     				FILE_UPLOAD_PATH = getParameters("UPLOAD_PATH_LIN");
		     				FILE_DOWNLOAD_PATH = getParameters("DOWNLOAD_PATH_LIN");
		     	 			LOGBACK_CONFIG_FILE_PATH = StringUtils.replace(System.getProperty("catalina.home"), "\\", "/") + getParameters("ERROR_LOG_LIN");
		     			}
		     				//**** Now forming the logger file
		     	 			LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		     	 			PatternLayoutEncoder ple = new PatternLayoutEncoder();
		     	 			ple.setPattern("%-12date{dd-MM-YYYY HH:mm:ss.SSS} - %msg%n");
		     	 			ple.setContext(lc);
		     	 			ple.start();
		     	 			FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
		     	 			fileAppender.setFile(LOGBACK_CONFIG_FILE_PATH);
		     	 			fileAppender.setEncoder(ple);
		     	 			fileAppender.setContext(lc);
		     	 			fileAppender.start();
		     	 			logger = (Logger) LoggerFactory.getLogger("");
		     	 			logger.addAppender(fileAppender);
		     	 			logger.setLevel(Level.WARN);
		     	 			logger.setAdditive(false);
					
		      			//if(DBPASS==null){ DBPASS= getDBpass1().trim(); }
		     	 			if(KEYVALUE==null){ KEYVALUE= getKey(); }
		     	 			setComment(3, className, "starting Environment .. "+className);
		 			
    		}else {
    			setComment(3, className, "Environment already formed...");
    		}
    		
    	}catch(Exception e) {
    		System.out.println(className+"  ==> Exception in the init() method -- > "+e.getMessage());
    	}finally {
    		if(parser!=null) parser = null;
    	}
    	
    }

    public static void setComment(int level,String className, String msg) { 
		  try{
			  if(getDebugFlag()) {
				  switch(level) {
				  case 1:   logger.warn("WARN:  ["+className+"]: "+msg);				  break;
				  case 2:   logger.warn("DEBUG: ["+className+"]: "+msg);				  break;
				  case 3:   logger.warn("INFO:  ["+className+"]: "+msg);				  break;
				  }
				  }else {
					  if(level==1)
						    logger.warn("WARN:  ["+className+"] --- "+msg);	
				  }
				  
	      	} catch (Exception e){
	      		System.out.println(className+"  ==> Exception in the setComment() method -- > "+e.getMessage());
	      	}
	}
   
 	public static boolean isWindows() { return (OS.indexOf("win") >= 0); }
 	public static boolean isMac() { return (OS.indexOf("mac") >= 0); 	}
 	public static boolean isUnix() { return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ); 	}
 	public static boolean isSolaris() { return (OS.indexOf("sunos") >= 0); 	}
 	protected static String getKey() throws Exception{return getKey_01().trim();}
 	protected static String getKey_01()  throws Exception{return   Utilities.getKey_02(StringUtils.reverse(StringUtils.substring(className, 0,4)));	}
	protected static String getParameters(String paramName) throws Exception{		return Utilities.getSysParam(JSON_LOCALE.get(paramName).toString());	}
	
}
