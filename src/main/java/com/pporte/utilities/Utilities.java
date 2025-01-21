package com.pporte.utilities;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.json.simple.JSONObject;

import com.google.gson.JsonObject;
import com.pporte.NeoBankEnvironment;
import com.pporte.security.AESEncrypter;
import com.pporte.utilities.Utilities;

import framework.v8.security.EncryptionHandler;

public class Utilities {
	private static String classname = Utilities.class.getSimpleName();
	
	static final String RNDSTRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static SecureRandom rnd = new SecureRandom();

      public static String getMYSQLCurrentTimeStampForInsert() throws Exception{
          	 SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");     	        
          	 java.util.Date date = new Date();      	             	        
           	return formatter1.format(date);
          	}
    	public static String getMySQLDateTimeConvertor(String datetimestring) throws Exception{
    	 SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");     	        
    	 java.util.Date date = formatter1.parse(datetimestring);      	        
    	 SimpleDateFormat formatter2 = new SimpleDateFormat ("dd-MMM-yyyy HH:mm:ss");      	        
    	return formatter2.format(date);

    	}
    	public static String getDateTimeFormatInFullForDisplay(String datetimestring) throws Exception{
    		SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");     	        
    		java.util.Date date = formatter1.parse(datetimestring);      	        
    		SimpleDateFormat formatter2 = new SimpleDateFormat ("dd-MMMM-yyyy HH:mm:ss");      	        
    		return formatter2.format(date);
    		
    	}
    	public static String getStellarDateConvertor(String datestring) throws Exception{
    	   	 SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ssX");// 2021-11-02T05:51:31Z
    	   	 java.util.Date date = formatter1.parse(datestring);
    	   	 SimpleDateFormat formatter2 = new SimpleDateFormat ("dd-MMM-yyyy HH:mm:ss");
    	   	 return formatter2.format(date);

    	   	}

    	public static String getMySQLDateConvertor(String datestring) throws Exception{
    	 SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd");    
    	 java.util.Date date = formatter1.parse(datestring);
    	 SimpleDateFormat formatter2 = new SimpleDateFormat ("dd-MMM-yyyy");
    	 return formatter2.format(date);

    	}
    	public static String getMySQLDateConvertorForCardDoe(String datestring) throws Exception{
       	 SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd");    
       	 java.util.Date date = formatter1.parse(datestring);
       	 SimpleDateFormat formatter2 = new SimpleDateFormat ("MM-yy");
       	 return formatter2.format(date);

       	}
    	public static String getMYSQLCurrentTimeStampForDashGraphs() throws Exception{
          	 SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd");     	        
          	 java.util.Date date = new Date();      	             	        
          	return formatter1.format(date);
          }

  	public static String getMoneyinDecimalFormat(String toformat) throws ParseException{
  		DecimalFormat moneyFormat = new DecimalFormat("#,###,##0.00");
  		return moneyFormat.format(Double.parseDouble(toformat)).toString();
        	}
  	public static String getMoneyinSimpleDecimalFormat(String toformat) throws ParseException{
  		DecimalFormat moneyFormat = new DecimalFormat("######0.00");
  		return moneyFormat.format(Double.parseDouble(toformat)).toString();
        	}
  	public static String formatToSevenDecimalPlace(String toformat) throws ParseException{
  		DecimalFormat moneyFormat = new DecimalFormat("######0.0000000");
  		return moneyFormat.format(Double.parseDouble(toformat)).toString();
  	}

  	public static String getMoneyinNoDecimalFormat(String toformat) throws ParseException{
  		DecimalFormat moneyFormat = new DecimalFormat("#,###,##0");
  		return moneyFormat.format(Double.parseDouble(toformat)).toString();
        	}	
  	public static String getUTCtoISTDateTimeConvertor(String dateTimeInUTCFormat) throws Exception{

  		DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  		utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

  		Date date = utcFormat.parse(dateTimeInUTCFormat);

  		DateFormat pstFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
  		pstFormat.setTimeZone(TimeZone.getTimeZone("IST"));

  		return (pstFormat.format(date));

       	} 	
  	


	//public static String getPass(String trim) {	return  StringUtils.reverse(StringUtils.replace(EncryptionHandler.unRavel(trim.trim()),"&",""));}
	//public static  String getKey_02(String input_1) {return input_1+Integer.toString(10-1)+(EncryptionHandler.getKey_03((StringUtils.reverse(StringUtils.substring(classname, 0,4))), "AESEncrypter")  );}

	
	public static  String getKey_02(String input_1) {return EncryptionHandler.getKey_02(input_1, classname, AESEncrypter.getClassName()) ;}

	public static synchronized int getRandomNumber(int min, int max) throws Exception{
		return (ThreadLocalRandom.current().nextInt(min, max + 1));	
	}

	public static synchronized boolean isValidCreditCardNumber(String creditCardNumber) {
		boolean isValid = false;
		try {
			//String reversedNumber = new StringBuffer(creditCardNumber).reverse().toString();
			String reversedNumber = StringUtils.reverse(creditCardNumber);
			int mod10Count = 0;
			for (int i = 0; i < reversedNumber.length(); i++) {
				int augend = Integer.parseInt(String.valueOf(reversedNumber.charAt(i)));
				if (((i + 1) % 2) == 0) {
					String productString = String.valueOf(augend * 2);
					augend = 0;
					for (int j = 0; j < productString.length(); j++) {
						augend += Integer.parseInt(String.valueOf(productString.charAt(j)));
					}
				}
				mod10Count += augend;
			}
			if ((mod10Count % 10) == 0) {
				isValid = true;
			}
		} catch (NumberFormatException e) {
		}
		return isValid;
	}

   	/**
 	 * Applies the specified mask to the card number.
 	 *
 	 * @param cardNumber The card number in plain format
 	 * @param mask The number mask pattern. Use # to include a digit from the
 	 * card number at that position, use x to skip the digit at that position
 	 *
 	 * @return The masked card number
 	 *  for card number "1234123412341234" > 1234-xxxx-xxxx-xx34
 	 */
 	public static String maskCardNumber(String cardNumber) {
 	    // format the number	SYTXkq38KSVQS6rN6wzRFrLyeXSCs7XsHq2Aht9E1Po=
 		String mask="##xx-xx##-xx##-xx##";
 	    int index = 0;
 	    StringBuilder maskedNumber = new StringBuilder();
 	    for (int i = 0; i < mask.length(); i++) {
 	        char c = mask.charAt(i);
 	        if (c == '#') {
 	            maskedNumber.append(cardNumber.charAt(index));
 	            index++;
 	        } else if (c == 'x') {
 	            maskedNumber.append(c);
 	            index++;
 	        } else {
 	            maskedNumber.append(c);
 	        }
 	    }
 	    // return the masked number
 	    return maskedNumber.toString();
 	}

 	public static String createSingleCard (String strbin) throws Exception{
		StringBuilder builder = null;
		int  totalcardlength = 16;
		Random random = new Random(System.currentTimeMillis());
	        try {
						builder = null;
							 int randomNumberLength = totalcardlength - (strbin.length() + 1);
							 builder = new StringBuilder(strbin);
								for (int i = 0; i < randomNumberLength; i++) {
									int digit = random.nextInt(10);
									builder.append(digit);
								}
							int checkDigit = getCheckDigit(builder.toString());
				            builder.append(checkDigit);
							 
	        } catch (Exception e) {
	            throw new Exception(" Method createSingleCard: Error in creating card number",e);
	        }

 		return builder.toString();
 	}
 	public static ArrayList<String> createMultipleCards (String strbin,String totalcards) throws Exception{
 		ArrayList<String> arrCards = null;
		StringBuilder builder = null;
		int  totalcardlength = 16;
		int totalcardsgenerated  = Integer.parseInt(totalcards);
		Random random = new Random(System.currentTimeMillis());
	        try {
	        	arrCards = new ArrayList<String>();
					for (int j=0;j<totalcardsgenerated;j++){
						builder = null;
							 int randomNumberLength = totalcardlength - (strbin.length() + 1);
							 builder = new StringBuilder(strbin);
								for (int i = 0; i < randomNumberLength; i++) {
									int digit = random.nextInt(10);
									builder.append(digit);
								}
							int checkDigit = getCheckDigit(builder.toString());
				            builder.append(checkDigit);
				            arrCards.add(builder.toString());
					}		 
	        } catch (Exception e) {
	            throw new Exception(" Method createMultipleCards:  Error in creating card number",e);
	        }

 		return arrCards;
 	}
	  	 private static int getCheckDigit(String number) {
      int sum = 0;
        for (int i = 0; i < number.length(); i++) {

            // Get the digit at the current position.
            int digit = Integer.parseInt(number.substring(i, (i + 1)));

            if ((i % 2) == 0) {
                digit = digit * 2;
                if (digit > 9) {
                    digit = (digit / 10) + (digit % 10);
                }
            }
            sum += digit;
        }
        int mod = sum % 10;
        return ((mod == 0) ? 0 : 10 - mod);
 }

	  	public static void wipeString(String stringToWipe) throws Exception {
	  		try {
	  		Field stringValue = String.class.getDeclaredField("value");
	  		stringValue.setAccessible(true);
	  		Arrays.fill((char[]) stringValue.get(stringToWipe), '*');
	  		} catch (IllegalAccessException e) {
	  		throw new Exception("Can't wipe string data");
	  		}
	  	}
	  	public static String encryptString(String stringToEncrypt) throws Exception {
	  		return AESEncrypter.encrypt(stringToEncrypt);
	  	}
	  	public static String decryptString(String stringToDecrypt) throws Exception {
	  		return AESEncrypter.decrypt(stringToDecrypt);

	  	}
	  	public static synchronized String generateCVV2(int length) throws Exception {
	  		int randomNumberLength = length ;	Random random = null;	StringBuilder builder = null;
	  		random = new Random(System.currentTimeMillis());
			 builder = new StringBuilder("");
				try {
					for (int i = 0; i < randomNumberLength; i++) {
						int digit = random.nextInt(10);
						if(digit==0)
						builder.append(digit+1);
						else
							builder.append(digit);
					}
				} catch (Exception e) {
					throw new Exception("Can't generate CVV");
				}finally{
					random=null;
				}
	  		return builder.toString();  		
	  	}

	  	public static synchronized Vector<String> createCVV2(int totalcards, int length) throws Exception{
			Vector<String> vectoralCVV2 =new Vector<String>();	Random random = null;StringBuilder builder = null;
			random = new Random(System.currentTimeMillis());
			try{
			for(int count=0;count<totalcards;count++){
	   	  		int randomNumberLength = length ;
    	  		
				 builder = new StringBuilder("");						
						for (int i = 0; i < randomNumberLength; i++) {
							int digit = random.nextInt(10);
							if(digit==0)
							builder.append(digit+1);
							else
								builder.append(digit);
						}
						vectoralCVV2.add(builder.toString());
				}
			} catch (Exception e) {
				throw new Exception("Can't generate CVV");
			}
			return vectoralCVV2;
		}
	  	

	    public static int getBusDaysBetweenDuration(String startDateString, String endDateString) throws Exception{
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        //String dateInString = "01-07-2016";
	        Date startDate = sdf.parse(startDateString);
	        //String dateInString2 = "31-07-2016";
	        Date endDate = sdf.parse(endDateString);
	       // calculateDuration(startDate,endDate);
	    	
	    	
	      Calendar startCal = Calendar.getInstance();
	      startCal.setTime(startDate);

	      Calendar endCal = Calendar.getInstance();
	      endCal.setTime(endDate);

	      int workDays = 0;

	      if (startCal.getTimeInMillis() > endCal.getTimeInMillis()){
	        startCal.setTime(endDate);
	        endCal.setTime(startDate);
	      }

	      while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()) {
	    	    if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY &&
	    	        startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
	    	        workDays++;
	    	    }
	    	    startCal.add(Calendar.DAY_OF_MONTH, 1);
	    	}

	      return workDays;
	    }

		public static String generateToken(String acode, String flag) throws Exception {
			//TODO Later put it into the Parameters json file.
				final String internalTokenBIN = "2123";
				final String externalTokenBIN = "2345";
				
				SimpleDateFormat formatter1 = new SimpleDateFormat ("yy");
				String finalToken = null;
				// Token is BIN+YY+Random(10)
				if(flag.startsWith("EXT") ) {
					finalToken = externalTokenBIN + formatter1.format(new java.util.Date()) + generateSecureToken(10);
				}else {
					finalToken = internalTokenBIN + formatter1.format(new java.util.Date()) + generateSecureToken(10);
				}
					return finalToken;
			}

		public static String getDateCalculate(String dateString, int days, String dateFormat) {
		    Calendar cal = Calendar.getInstance();
		    SimpleDateFormat s = new SimpleDateFormat(dateFormat);
		    try {
		        cal.setTime(s.parse(dateString));
		    } catch (ParseException e) {
		        e.printStackTrace();
		    }
		    cal.add(Calendar.DATE, days);
		    return s.format(cal.getTime());
		}
		
		public static String formartDateMpesa(String mDate) {
			// TODO Auto-generated method stub
			SimpleDateFormat inSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat outSDF = new SimpleDateFormat("dd-MM-yyyy");

			 String outDate = "";
			 if (mDate != null) {
			    try {
			        Date date = inSDF.parse(mDate);
			        outDate = outSDF.format(date);
			        } catch (Exception  ex){ 
			        	ex.printStackTrace();
			        }
			    }
			    return outDate;
		}
		
		
		

		
		//New addition Ben
		//Convert dates from MMDDYYY to YYYYMMDD
		
		public static String formartDateforGraph(String mDate) {
			// TODO Auto-generated method stub
			
			SimpleDateFormat inSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat outSDF = new SimpleDateFormat("yyyy-MM-dd");

			 
			  String outDate = "";
			  
			    if (mDate != null) {
			        try {
			            Date date = inSDF.parse(mDate);
			            outDate = outSDF.format(date);
			            
			            
			        } catch (Exception  ex){ 
			        	ex.printStackTrace();
			        }
			    }
			    return outDate;
		}

		//ADDED
		 public static String generateSecureToken(int size) {

		        StringBuilder generatedToken = new StringBuilder();
		        try {
		            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
		            // Generate 20 integers 0..20
		            for (int i = 0; i < size; i++) {
		                generatedToken.append(number.nextInt(9));
		            }
		        } catch (NoSuchAlgorithmException e) {
		            e.printStackTrace();
		        }

		        return generatedToken.toString();
		 }
		 
		 public static String getMYSQLCurrentTimeStampForOTPValidTo() throws Exception{
	      	 SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");     	        
	      	 java.util.Date validFrom = new Date(); 
				int addMinuteTime = 5;
				java.util.Date validTo =null;
				validTo = DateUtils.addMinutes(validFrom, addMinuteTime); 				

	      	return formatter1.format(validTo );
	      	}

			  
			public static BufferedImage resize(BufferedImage img, int height, int width) throws Exception{
				BufferedImage resized = null;
				try{
					Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				
		         resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		        Graphics2D g2d = resized.createGraphics();
		        g2d.drawImage(tmp, 0, 0, null);
		        g2d.dispose();
			}catch (Exception e) {
				throw new Exception (e.getMessage());
			}
		        return resized;
		    }
			
			public static BufferedImage resized(BufferedImage img, int scaledWidth, int scaledHeight)
		            throws Exception {

		        // creates output image
		        BufferedImage outputImage = new BufferedImage(scaledWidth,
		                scaledHeight, BufferedImage.TYPE_INT_ARGB);
				try {
					 Image tmp = img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

					    Graphics2D g2d = outputImage.createGraphics();
				        g2d.drawImage(tmp, 0, 0, scaledWidth, scaledHeight, null);
				        g2d.dispose();
						//System.out.println("Good");

				}catch(Exception e) {
					//System.out.println("Exception is "+e.getMessage());
				}
		        return outputImage;
		    }
		  	
			public static synchronized void callException(HttpServletRequest request, HttpServletResponse response, ServletContext ctx,
					String msg) throws Exception {
			try {
					if(msg!=null) {
					//NeoBankEnvironment.setComment(1, classname, "Error is "+msg);
						request.setAttribute("errormsg", msg);
					}else {
						msg="Undefined Error";
					}
					response.setContentType("text/html");
					ctx.getRequestDispatcher(NeoBankEnvironment.getErrorPage()).forward(request, response);
				} catch (Exception e1) { 
						NeoBankEnvironment.setComment(1, classname, "Problem in forwarding to Error Page, error : "+e1.getMessage());
				}
				
			}

			
			@SuppressWarnings("unchecked")
			public static void jsonResponse(String message, HttpServletResponse response) throws Exception{
				try {
					PrintWriter jsonOutput_1 = null; jsonOutput_1 = response.getWriter();  JSONObject jsonResponse = new JSONObject();
					
					jsonResponse.put("message", message);
					try {
						jsonOutput_1.print(jsonResponse);
					}finally {
						if(jsonOutput_1!=null) {jsonOutput_1.flush(); jsonOutput_1.close();} 
						 if(message!=null)message = null; if(jsonResponse!=null) jsonResponse =null;
					}
				}catch (Exception e1) { 
					NeoBankEnvironment.setComment(1, classname, "Problem in forwarding jsonresponse from method jsonResponse : "+e1.getMessage());
				}
			}
			
			public static String getFileName(Part part) {
				for (String content : part.getHeader("content-disposition").split(";")) {
					if (content.trim().startsWith("filename")) {
						return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
					}
				}
				return null;
			}
			
			public static synchronized String issueTimeToken() throws Exception{
	      		long currentTime = System.currentTimeMillis();
	      		int randomNumber = RandomUtils.nextInt(1001, 9999);
	      		BigInteger bi = null;
				try {
				bi = new BigInteger(String.valueOf(randomNumber)+String.valueOf(currentTime));
				}finally {
					currentTime = 0; randomNumber = 0;
				}
				return StringUtils.reverse(bi.toString(16));
			}
			public static synchronized boolean validateTimeToken(String tokenTime) throws Exception{
				boolean iserror = true;
				if (tokenTime == null) return true;
				if(tokenTime.equals("") == false) {
					String decryptedNumber = new BigInteger(StringUtils.reverse(tokenTime), 16).toString();
					decryptedNumber = decryptedNumber.substring(4, decryptedNumber.length());
					long end = System.currentTimeMillis();
					float diffinsec = (end - Long.parseLong(decryptedNumber))/1000F;
					//NeoBankEnvironment.setComment(3, classname, "diffinsec: "+diffinsec);
					if(diffinsec >2) {
						iserror = true;
					}else {
						iserror = false;
					}					
				}					
				return iserror;
			}	
			
			public static String hexToASCII(String hexValue)
		 	{
		 	    StringBuilder output = new StringBuilder("");
		 	    for (int i = 0; i < hexValue.length(); i += 2)
		 	    {
		 	        String str = hexValue.substring(i, i + 2);
		 	        output.append((char) Integer.parseInt(str, 16));
		 	    }
		 	    return output.toString();
		 	}
			
			//Genarate Transaction code for user
			public static String generateTransactionCode(int count) {
				int count2 = 9;
				String txnCode = RandomStringUtils.randomAlphanumeric(count2).toUpperCase();

			return txnCode;

		     }
			
			public static synchronized String genAlphaNumRandom(int len) throws Exception {
		  		StringBuilder sb = null;
					try {
						sb = new StringBuilder( len );
						for( int i = 0; i < len; i++ ) {
						      sb.append( RNDSTRING.charAt( rnd.nextInt(RNDSTRING.length()) ) );
						}

					} catch (Exception e) {
						throw new Exception("Can't generate Random Number");
					}
		  		return sb.toString();  		
		  	}	  
			
			public static String asciiToHex(String asciiValue)
		 	{
		 	    char[] chars = asciiValue.toCharArray();
		 	    StringBuffer hex = new StringBuffer();
		 	    for (int i = 0; i < chars.length; i++)
		 	    {
		 	        hex.append(Integer.toHexString((int) chars[i]));
		 	    }
		 	    return hex.toString();
		 	}
			
			// TODO:- Check how others are consuming this on the frontend
			public static void sendJsonResponse(HttpServletResponse response, 
					String status, String message) {
				try {
					JsonObject obj = new JsonObject( );
					obj.addProperty("error",status);
					obj.addProperty("message",message);
					try {
						NeoBankEnvironment.setComment(3, "Method Send JSON Response","  String is " + obj.toString());
						response.getWriter().print(obj);
					}finally {
						response.getWriter().close();
						if(obj!=null) obj = null;
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, classname, "Problem from method sendJsonResponse : "+e.getMessage());
				}
				
			}
			public static void sendJsonResponseOfInvalidToken(HttpServletResponse response, 
					String status, String message) {
				try {
					JsonObject obj = new JsonObject( );
					obj.addProperty("error",status);
					obj.addProperty("message",message);
					try {
						NeoBankEnvironment.setComment(3, classname,"sendJsonResponseOfInvalidToken "+" Response is " +obj.toString());
						response.getWriter().print(obj);
					}finally {
						response.getWriter().close();
						if(obj!=null) obj = null;
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, classname, "Problem from method sendJsonResponse : "+e.getMessage());
				}
				
			}
			public static void sendSessionExpiredResponse(HttpServletResponse response, 
					String status, String message) {
				try {
					JsonObject obj = new JsonObject( );
					obj.addProperty("error","sess_expired");
					obj.addProperty("message",message);
					try {
						response.getWriter().print(obj);
					}finally {
						response.getWriter().close();
						if(obj!=null) obj = null;
					}
				}catch (Exception e) {
					NeoBankEnvironment.setComment(1, classname, "Problem from method sendJsonResponse : "+e.getMessage());
				}
				
			}
			
			public static String getBasicAuthHeader( String userName, String password) {
				try {
					String plainCredentials = userName.concat(":").concat(password);
					String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
			        // Create authorization header
			         String authorizationHeader = "Basic " + base64Credentials;
			         return authorizationHeader;  
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, classname, "Problem from method getBasicAuthHeader : "+e.getMessage());
					return null;
				}
			}
			
			public static String generatingRandomAlphanumericString() {
			    int leftLimit = 48; // numeral '0'
			    int rightLimit = 122; // letter 'z'
			    int targetStringLength = 6;
			    Random random = new Random();

			    String generatedString = random.ints(leftLimit, rightLimit + 1)
			      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
			      .limit(targetStringLength)
			      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			      .toString();
			    return generatedString.toUpperCase();
			}
			
			public static synchronized void callOpsException(HttpServletRequest request, HttpServletResponse response, ServletContext ctx,
					String msg) throws Exception {
			try {
					if(msg!=null) {
					//NeoBankEnvironment.setComment(1, classname, "Error is "+msg);
						request.setAttribute("errormsg", msg);
					}else {
						msg="Undefined Error";
					}
					response.setContentType("text/html");
					try {
						ctx.getRequestDispatcher(NeoBankEnvironment.getOpsErrorPage()).forward(request, response);
					}finally {
						if (msg!=null)msg=null;
					}
				} catch (Exception e1) { 
						NeoBankEnvironment.setComment(1, classname, "Problem in forwarding to Error Page, error : "+e1.getMessage());
				}
				
			}
			private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
			public static String generateExternalWalletId(int len){
			   StringBuilder sb = new StringBuilder(len);
			   for(int i = 0; i < len; i++)
			      sb.append(AB.charAt(rnd.nextInt(AB.length())));
			   return sb.toString();
			}
			
			public static String displayDateFormat(String sDate, String sDateFormat) {
			 	DateFormat df = new SimpleDateFormat(sDateFormat);
			 	String infi = null;
			 	try {
					Date date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate);
					try {
						infi = df.format(date1);
					} finally {
						if(df!=null)df=null;if(date1!=null)date1=null;
					}
				} catch (Exception e) {
					NeoBankEnvironment.setComment(1, classname, "Error in method displayDateFormat, error : "+e.getMessage());
				}  
			   
			return infi;
			
		}
			
	  public static String ellipsis(final String text, int length)
		 {
		     return text.substring(0, length - 3) + "...";
		 }

	  public static String shortenPublicKey(String pkey) {
		  return pkey.substring(0, 3) + "..."+ pkey.substring(53, pkey.length());
		}
		public static String encryptJsonString(String stringToEncrypt) throws Exception {
	  		return EncryptionHandler.encryptJson(stringToEncrypt);
	  	}
	  	public static String decryptJsonString(String stringToDecrypt) throws Exception {
	  		return EncryptionHandler.decryptJson(stringToDecrypt);
	  	}

	  
	  public static String generateMobileToken(String relationshipNo) {
		  String md5TokenValue = "";
			try {
				//relationshipNo-dd-mm-yyyy
				//We should hex it first, and then hash it.
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				Date date = new Date();  
				String dateString =df.format(date);
				String tokenValue = relationshipNo.concat("-").concat(dateString);
				//NeoBankEnvironment.setComment(3, classname, "tokenValue before hex "+tokenValue);
				String tokenValueHex = Utilities.asciiToHex(tokenValue);
				//NeoBankEnvironment.setComment(3, classname, "tokenValueHex before hex "+tokenValueHex);
				try {
					md5TokenValue = DigestUtils.md5Hex(tokenValueHex);
					//NeoBankEnvironment.setComment(3, classname, "md5TokenValue  "+md5TokenValue);
				} finally {
					if(tokenValue!=null)tokenValue=null; if(tokenValueHex!=null)tokenValueHex=null;
					if(df!=null)df=null;if(dateString!=null)dateString=null;
				}
				
			} catch (Exception e) {
				md5TokenValue = "";
				NeoBankEnvironment.setComment(1, classname, "Error in generateMobileToken is: "+e.getMessage());
			}
			return md5TokenValue;
		}
		
		public static boolean compareMobileToken(String relationshipNo, String tokenValue) {
			boolean result = false;
			try {
				String generatedToken = generateMobileToken(relationshipNo);
				//NeoBankEnvironment.setComment(3, classname, "Token value from user is "+tokenValue);
				//NeoBankEnvironment.setComment(3, classname, "Token generatedToken "+generatedToken);
				if(generatedToken.equals(tokenValue)) {
					result = true;
					//NeoBankEnvironment.setComment(3, classname, "Token matches");
				}
			} catch (Exception e) {
				result = false;
				NeoBankEnvironment.setComment(1, classname, "Error in compareMobileToken is: "+e.getMessage());
			}
			return result;
		}


	  
	  public static String tripleEncryptData(String data) {
		  String encryptedData=null; String encryptedDataHex=null; String reversedHexEncryptedDataHex=null; String encryptedJsonHashofReversedEncryptedDataHex=null;
		  try {
			  encryptedData=Utilities.encryptString(data);
			  encryptedDataHex=Utilities.asciiToHex(encryptedData);
			  reversedHexEncryptedDataHex=StringUtils.reverse(encryptedDataHex);
			  encryptedJsonHashofReversedEncryptedDataHex=Utilities.encryptJsonString(reversedHexEncryptedDataHex);
			  try {
				  
			  }finally {
				  if (encryptedData!=null)encryptedData=null;  if (encryptedDataHex!=null)encryptedDataHex=null;
				  if (reversedHexEncryptedDataHex!=null)reversedHexEncryptedDataHex=null; 
			  }
		  }catch (Exception e) {
			  NeoBankEnvironment.setComment(1, classname, "Error in method tripleEncryptData, error : "+e.getMessage());
		  }
		  return encryptedJsonHashofReversedEncryptedDataHex;
	  }
	  public static String tripleDecryptData(String data) {
		  String decryptedData=null; String decryptedDataHex=null; String reversedHexDecryptedDataHex=null; String decryptedJsonHashofReversedDecryptedDataHex=null;
		  try {
			  decryptedJsonHashofReversedDecryptedDataHex=Utilities.decryptJsonString(data);
			  reversedHexDecryptedDataHex=StringUtils.reverse(decryptedJsonHashofReversedDecryptedDataHex);
			  decryptedDataHex=Utilities.hexToASCII(reversedHexDecryptedDataHex);
			  decryptedData=Utilities.decryptString(decryptedDataHex);
			  try {
				  
			  }finally {
				  if (decryptedDataHex!=null)decryptedDataHex=null;  if (reversedHexDecryptedDataHex!=null)reversedHexDecryptedDataHex=null;
				  if (decryptedJsonHashofReversedDecryptedDataHex!=null)decryptedJsonHashofReversedDecryptedDataHex=null; 
			  }
		  }catch (Exception e) {
			  NeoBankEnvironment.setComment(1, classname, "Error in method tripleDecryptData, error : "+e.getMessage());
		  }
		  return decryptedData;
	  }
	  
	  public static String maskEmailAddress( String email) {
		  try {
		    String mask = "*****";
		     int at = email.indexOf("@");
		    if (at > 2) {
		         int maskLen = Math.min(Math.max(at / 2, 2), 4);
		         int start = (at - maskLen) / 2;
		        return email.substring(0, start) + mask.substring(0, maskLen) + email.substring(start + maskLen);
		    }
		    try {
		    	
		    }finally {
		    	if (mask!=null) mask=null;
		    }
		  }catch (Exception e) {
			  NeoBankEnvironment.setComment(1, classname, "Error in method maskEmailAddress, error : "+e.getMessage());
		  }
		    return email;
		}
	  
	  public static String maskMobileNumber(String mobile) {
		  try {
		     String mask = "*******";
		    mobile = mobile == null ? mask : mobile;
		    final int lengthOfMobileNumber = mobile.length();
		    if (lengthOfMobileNumber > 2) {
		        final int maskLen = Math.min(Math.max(lengthOfMobileNumber / 2, 2), 6);
		        final int start = (lengthOfMobileNumber - maskLen) / 2;
		        return mobile.substring(0, start) + mask.substring(0, maskLen) + mobile.substring(start + maskLen);
		    }
		    	try {
		    	
		    }finally {
		    	if (mask!=null) mask=null;
		    }
		  }catch (Exception e) {
			  NeoBankEnvironment.setComment(1, classname, "Error in method maskMobileNumber, error : "+e.getMessage());

		  }
		    return mobile;
		}
	  
	  public static String maskedRelationshipNumber(String relationshipNo) {
		  try {
			  String mask = "##########";
			  relationshipNo = relationshipNo == null ? mask : relationshipNo;
			  final int lengthOfRelationshipNo= relationshipNo.length();
			  if (lengthOfRelationshipNo > 2) {
				  final int maskLen = Math.min(Math.max(lengthOfRelationshipNo / 2, 2), 6);
				  final int start = (lengthOfRelationshipNo - maskLen) / 2;
				  return relationshipNo.substring(0, start) + mask.substring(0, maskLen) + relationshipNo.substring(start + maskLen);
			  }
			  try {
				  
			  }finally {
				  if (mask!=null) mask=null;
			  }
		  }catch (Exception e) {
			  NeoBankEnvironment.setComment(1, classname, "Error in method maskedRelationshipNumber, error : "+e.getMessage());
			  
		  }
		  return relationshipNo;
	  }
		// Get current date at midnight
		public static String getCurrentDateAtMidnight() throws Exception{
	     	 SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");     	        
	     	 java.util.Date date = new Date();      	             	        
	     	return formatter1.format(date);
	     	}
		public static String convertSatoshiToBTC(String satoshi) {
				// 1 BTC = 100000000 Satoshi && 1 Satoshi = 0.00000001
			Double satoshiValue=0.00000001; 
			DecimalFormat btcformat = new DecimalFormat("######0.00000000");
			return btcformat.format(Double.valueOf(satoshi)*satoshiValue);
		}
		public static String convertBTCToSatoshi(String btc) {
			// 1 BTC = 100000000 Satoshi && 1 Satoshi = 0.00000001
			String btcValue= "100000000"; 
			String convertedSatoshi=Double.toString(Double.valueOf(btc)*Double.valueOf(btcValue));
			return convertedSatoshi;
		}
		
		@SuppressWarnings("unused")
		private String getFileExtension(File file) {
		    String name = file.getName();
		    int lastIndexOf = name.lastIndexOf(".");
		    if (lastIndexOf == -1) {
		        return ""; // empty extension
		    }
		    return name.substring(lastIndexOf);
		}
		
		  public static String convertStringToHex(String str) throws Exception{
		        return String.valueOf(Hex.encodeHex(str.getBytes(StandardCharsets.UTF_8)));
		    }
		   public static String convertHexToString(String hex) throws Exception{
		        String result = "";
		        try {
		            byte[] bytes = Hex.decodeHex(hex);
		            result = new String(bytes, StandardCharsets.UTF_8);
		            if(bytes!=null) bytes=null;
		        } catch (DecoderException e) {
		            throw new IllegalArgumentException("Invalid Hex format!");
		        }
		        return result;
		    }
		   public static String getSysParam(String encString) throws Exception {	    	
		    	encString  =StringUtils.trim( convertHexToString( EncryptionHandler.decryptJson(encString)));
		    	return encString;
		    }
}
