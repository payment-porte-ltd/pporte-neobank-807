package com.pporte;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.Enumeration;
import java.util.Properties;

import org.json.simple.JSONObject;

import com.pporte.utilities.Utilities;

import framework.v8.security.EncryptionHandler;

public class JsonWrite {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		File file = null;
		FileWriter filewriter = null;
		JSONObject obj = null;
		Properties prop = null;
		InputStream input = null;
		Enumeration<?> en = null;
		Enumeration<?> enFile = null;
		System.out.print("PPorte New Framework***********"+System.getProperty("java.home") + "**********\n"); // put it as catalina.home or the tomcat path
		try {
			Instant start = Instant.now();
			// CODE HERE        
			
	       input = new FileInputStream("D:/eclipse/workspace1/pporte807/propertyfiles/pportejson.properties");
	       
	         prop = new Properties();
	            // load a properties file
	            prop.load(input);
    
			file = new File("D:/apache-tomcat-10.1.18/PPApplicationParameters.json");// TODO
			
			file.setWritable(true);
			file.setReadable(true);
			filewriter = new FileWriter(file);
			obj = new JSONObject();			
			System.out.println("\n *** Now Writing the file at " + file.getAbsolutePath());
            if (prop != null) {
                enFile = prop.propertyNames();
                while (enFile.hasMoreElements()) {
                  String name = (String) enFile.nextElement();
                  obj.put(name, setParam(prop.getProperty(name)));
                }
              }
		    filewriter.write(obj.toJSONString());
		    filewriter.flush();
		    Instant finish = Instant.now();
			long timeElapsed = Duration.between(start, finish).toMillis();
		    System.out.println("\n ***Completed in "+timeElapsed +" milliseconds");
		} catch (Exception e) {
			System.out.println("Exception is " + e.getMessage());
		} finally {
			if (obj != null)				obj.clear();
			if (file != null)				file = null;
			if (filewriter != null)				filewriter = null;
			if(prop!=null) { prop.clear(); prop = null;}
			if(input!=null) input.close();
			if(en!=null) en=null;
			if(enFile!=null) enFile = null;
		}
		
		
	}

	private static String setParam(String param) throws Exception {
		//return Utilities.convertStringToHex(param);
		if(param!=null) { 			param = org.apache.commons.lang3.StringUtils.trim(param);  		
		param = Utilities.convertStringToHex(param);
		}
		return EncryptionHandler.encryptJson(param);
		
	}
}
