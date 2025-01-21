package com.pporte;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import com.pporte.utilities.Utilities;

public class JsonRead {
	 public static void main(String[] args) {
	        JSONParser parser = new JSONParser();
	        JSONObject jsonObjectRead = null;
	       // Properties jvm = System.getProperties();
	        //jvm.list(System.out);

	        try (FileReader reader = new FileReader("D:/apache-tomcat-10.1.18/PPApplicationParameters.json")) {
	            Object objRead = parser.parse(reader);
	            jsonObjectRead = (JSONObject) objRead;
	            System.out.println("\n WALLETLEDGERCHAIN_MULTIURLPORT: " + Utilities.getSysParam((String) jsonObjectRead.get("WALLETLEDGERCHAIN_MULTIURLPORT")));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
//private static String testMethod() throws Exception {
//	return "returning method name  "+StackWalker.getInstance().walk(frames -> frames.findFirst().map(StackWalker.StackFrame::getMethodName)).get(); 
//	
//}
}


