package com.pporte.rules;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import javax.imageio.ImageIO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.pporte.NeoBankEnvironment;
import com.pporte.dao.MerchantDao;
import com.pporte.dao.MerchantPaymentsDao;
import com.pporte.model.Merchant;
import com.pporte.model.AssetTransaction;
import com.pporte.model.TransactionRules;
import com.pporte.model.User;
import com.pporte.utilities.Utilities;

import framework.v8.Rules;

public class MerchantPaymentsRulesImpl implements Rules {

	private static String className = MerchantPaymentsRulesImpl.class.getSimpleName();
	@Override
	public void performJSONOperation(String rules, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		switch (rules) {		
		
		}
		
	}

	@Override
	public void performMultiPartOperation(String rules, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx) throws Exception {
		HttpSession session	= request.getSession(false);
		switch (rules) {
		
		case "get_merchant_txns":
			try {
				JsonObject obj = new JsonObject();
				PrintWriter output = null;
				User user = null;
				String merchantCode = null;
				String dateFrom = null;
				String dateTo = null;
				String payMode = null;
				Gson gson = new Gson();
				List<AssetTransaction> merchantTransactionList = null;

				if (session.getAttribute("SESS_USER") == null) 
					throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				merchantCode = user.getRelationId();
				if(request.getParameter("datefrom")!=null)
					dateFrom = StringUtils.trim( request.getParameter("datefrom") );
				if(request.getParameter("dateto")!=null)
					dateTo = StringUtils.trim( request.getParameter("dateto") );
				if(request.getParameter("paymode")!=null)
					payMode = StringUtils.trim( request.getParameter("paymode"));
				
				
				if(dateFrom==""||dateFrom==null||dateTo==""||dateTo==null) {
					merchantTransactionList = (List<AssetTransaction>)MerchantPaymentsDao.class.
							getConstructor().newInstance().getMerchantTransactions(merchantCode);
				}else {
					merchantTransactionList = (List<AssetTransaction>)MerchantPaymentsDao.class.
							getConstructor().newInstance().getMerchantTransactionsFromDateTo(merchantCode, dateTo, dateTo);
				}
				
				if(payMode==""||payMode==null) {
					obj.addProperty("error", "false"); 
	        		obj.add("data", gson.toJsonTree(merchantTransactionList));
				}else if (payMode.equals("WCO")) {
					merchantTransactionList = merchantTransactionList.stream().
							filter(t->t.getSystemReferenceInt().startsWith("WCO")) //TODO Get transaction parameters from Ben
							.collect(Collectors.toList()); 
					obj.addProperty("error", "false"); 
	        		obj.add("data", gson.toJsonTree(merchantTransactionList));
				}else if (payMode.equals("WRP")) {
					merchantTransactionList = merchantTransactionList.stream().
							filter(t->t.getSystemReferenceInt().startsWith("WRP"))//TODO Get transaction parameters from Ben
							.collect(Collectors.toList());   
					obj.addProperty("error", "false"); 
	        		obj.add("data", gson.toJsonTree(merchantTransactionList));
				}else if (payMode.equals("WTM")) {
					merchantTransactionList = merchantTransactionList.stream().
							filter(t->t.getSystemReferenceInt().startsWith("WTM"))//TODO Get transaction parameters from ben
							.collect(Collectors.toList()); 
					obj.addProperty("error", "false"); 
	        		obj.add("data", gson.toJsonTree(merchantTransactionList));
				}
				try {
					output = response.getWriter();
					output.print(obj);
				}finally {
					
					if(obj!=null) obj = null;
					if(gson!=null) gson = null; if(user!=null) user = null;
					if(merchantCode!=null) merchantCode = null; if(dateFrom!=null) dateFrom = null;
					if(dateTo!=null) dateTo = null; if(payMode!=null) payMode = null;
					if(merchantTransactionList!=null) merchantTransactionList = null;
					if(output!=null) output.close();
				}
				
				
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rules+" is "+e.getMessage());
				Utilities.sendJsonResponse(response, "error", "Geting Merchant Trasactions failed, Please try again letter");
			}
		break;
		
		}
		
		
	}

	@Override
	public void performOperation(String rules, HttpServletRequest request, HttpServletResponse response,
			ServletContext ctx)throws Exception {
		HttpSession session	= request.getSession(false);
		if (session.getAttribute("SESS_USER") == null)
			Utilities.callException(request, response, ctx, "Session has expired, please log in again");
		switch (rules) {	
		case "Store Payment":
			try {
				request.setAttribute("lastaction", "merchqrcode");
				request.setAttribute("lastrule", "Store Payment");
				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getMerchantStorePaymentPage()).forward(request, response);
				}finally {
					
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
		break;
		case "Cash Out":
			try {
				request.setAttribute("lastaction", "merchqrcode");
				request.setAttribute("lastrule", "Cash Out");
				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getMerchantCashOutPage()).forward(request, response);
				}finally {
					
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
		break;
		case "Top Up":
			try {
				request.setAttribute("lastaction", "merchqrcode");
				request.setAttribute("lastrule", "Top Up");
				response.setContentType("text/html");
				try {
					ctx.getRequestDispatcher(NeoBankEnvironment.getMerchantTopUpPage()).forward(request, response);
				}finally {
					
				}
			}catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, "Error for  rules: "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
		break;
		
		case "storepayment_static_qr":
			try {
				if (session.getAttribute("SESS_USER") == null)
					throw new Exception ("Session has expired, please log in again");
				FileInputStream in = null;
				OutputStream out = null;
				File myFile = null;
				String filePath = NeoBankEnvironment.getFileUploadPath() + File.separator + "QR.png";
				String currentDateValue = null;
				long CRCValue = 0;
				StringBuffer strAppend = null;
				String billerCodeValue = null;
				Merchant mMerchant = null;
				String mccCodeValue = null;
				//String merchCityValue = null;
				String merchNameValue = null;
				String tnxType = "WRP"; //TODO Take this parameters from Ben
				String txnCurrencyIdValue = NeoBankEnvironment.getUSDCurrencyId();
				String retailPayAmount = null;
				String countryCodeValue = "PA";//TODO Take this parameters from Ben
				int size = 500;
				String merchantId=null; 
				String fileType = "png";
				String myCodeText = null;
				myFile = new File(filePath);
				if (session.getAttribute("SESS_USER") != null)
					billerCodeValue = ((User) session.getAttribute("SESS_USER")).getRelationId();
				if (session.getAttribute("SESS_USER") != null)merchantId = ((User) session.getAttribute("SESS_USER")).getUserId();
				mMerchant = (Merchant) MerchantDao.class.getConstructor().newInstance().getMerchantDetailsForQR(merchantId);
				
				if (mMerchant != null) {
					mccCodeValue = mMerchant.getMccCategoryId();
					//merchCityValue =  mMerchant.getCounty().replace(" ", ""); 
					merchNameValue = mMerchant.getCompanyName().replace(" ", "");
					
					
				} else {
					NeoBankEnvironment.setComment(1, className, "Merchant Details not available");
					throw new Exception("Merchant Details not available ");
				}

				int txnAmountLength = 13;
				int merchantNameLength = 25;
				int dateLength = 10;
				
				// note for static make it 13 zeroes
				retailPayAmount = "0";
				currentDateValue = "0";
				retailPayAmount = StringUtils.leftPad("" + retailPayAmount, txnAmountLength, "0");
				merchNameValue = StringUtils.leftPad("" + merchNameValue, merchantNameLength, "0");
				currentDateValue = StringUtils.leftPad("" + currentDateValue, dateLength, "0");
				


				strAppend = new StringBuffer();
				strAppend.append("");
				// Payload Indicator
				strAppend.append("000201");// Indicator - 00, length - 02, value - 01, constant
				// Point of Initiation Method
				strAppend.append("010211");// Indicator - 01, length - 02, 12 (dynamic), 11 (static), constant
				// Merchant Account Information
				strAppend.append("5112" + billerCodeValue);// Indicator - 51, length - 12, value - merchant's
															// billerCode,variable
				// Merchant Category Code
				strAppend.append("5104" + mccCodeValue); // Indicator - 52, length - 04, value - merchant's category
															// code , constant
				// Transaction Currency
				strAppend.append("5303" + txnCurrencyIdValue); // Indicator - 53, length - 03, constant,
				// Transaction Amount
				strAppend.append("5413" + retailPayAmount); // Indicator - 54, length - 13, constant,
				// Country Code
				strAppend.append("5802" + countryCodeValue); // Indicator - 58, length - 02, constant,
				// Merchant Name
				strAppend.append("5925" + merchNameValue); // Indicator - 59, length - 25, variable,
				// Merchant City
			//	strAppend.append("6015" + merchCityValue); // Indicator - 60, length - 15, variable,
				
				// Pay Type
				strAppend.append("625013" + tnxType + currentDateValue); // Indicator - 62, 50-Payment Specific Data length - 13,(TxType = 3, Datetime
																	// =10 , variable, ()

				// Calculate Checksum
				myCodeText = strAppend.toString();
				
				
				byte[] bytes = myCodeText.getBytes();

				Checksum crc32 = new CRC32();
				crc32.update(bytes, 0, bytes.length);
				CRCValue = crc32.getValue();

				NeoBankEnvironment.setComment(3, className, "Check Sum value    " + CRCValue);
				// Checksum
				strAppend.append("6304" + Hex.encodeHexString(String.valueOf(CRCValue).substring(0, 2).getBytes())); // Indicator
																														// -
																														// 63,
																														// length
																														// -
																														// 04,
																														// constant,
																														// ()

				myCodeText = strAppend.toString();
				NeoBankEnvironment.setComment(3,className, "Length of string is "+myCodeText.length());


				NeoBankEnvironment.setComment(3, className, "QR Code to be generated is    " + myCodeText);

				Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
				hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
				hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
				hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

				QRCodeWriter qrCodeWriter = new QRCodeWriter();
				BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
				
				int matrixWidth  = byteMatrix.getWidth();
				BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
				Graphics2D graphics = (Graphics2D) image.getGraphics();
				graphics.setColor(Color.WHITE);
				graphics.fillRect(0, 0, matrixWidth, matrixWidth);
				graphics.setColor(Color.BLACK);
				
				graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

				for (int i = 0; i < matrixWidth; i++) {
					for (int j = 0; j < matrixWidth; j++) {
						if (byteMatrix.get(i, j)) {
							graphics.fillRect(i, j, 1, 1);
						}
					}
				}
				ImageIO.write(image, fileType, myFile);
				
				in = new FileInputStream(myFile);
				out = response.getOutputStream();
				response.setContentType("image/png");
				response.setContentLength((int) myFile.length());
				response.setHeader("Content-Disposition", "inline; filename=\"" + myFile.getName() + "\"");
				// Copy the contents of the file to the output stream
				byte[] buf = new byte[1024];
				int count = 0;
				while ((count = in.read(buf)) >= 0) {
					out.write(buf, 0, count);
				}
				try {

				} finally {
					if (out != null) out.close();
					if (in != null) in.close();
					if (myFile.exists()) myFile.delete();
					if (myCodeText != null) myCodeText = null;
					if (image != null) image = null;
					if (byteMatrix != null) byteMatrix.clear();
					if (hintMap != null) hintMap = null;
					if (bytes != null) bytes = null;
					if (mMerchant != null) mMerchant = null;
					if (mccCodeValue != null) mccCodeValue = null;
					if (retailPayAmount != null) retailPayAmount = null;
					if (countryCodeValue != null) countryCodeValue = null;
					if (merchNameValue != null) merchNameValue = null;
					if (tnxType != null) tnxType = null;
					if (txnCurrencyIdValue != null) txnCurrencyIdValue = null;
					if (merchantId != null)merchantId = null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
			
		break;
		case "storepayment_dynamic_qr":
			try {
				if (session.getAttribute("SESS_USER") == null)
					throw new Exception ("Session has expired, please log in again");
				FileInputStream merchDqrin = null;
				OutputStream merchDqrout = null;
				File mFile = null;
				String countryCodeValue = "PA";//TODO Take this parameters from Ben
				String merchNameValue = null;
				long CRCValue = 0;
				StringBuffer strAppend = null;
				String billerCodeValue = null;
				Merchant mMerchant = null;
				String mccCodeValue = null;
				String tnxType = "WRP";//TODO Take this parameters from Ben
				String txnCurrencyIdValue = NeoBankEnvironment.getUSDCurrencyId();
				String retailPayAmount = null;
				String merchantId=null; 	
				SimpleDateFormat df = new SimpleDateFormat("ddMMyyHHmm");
				Date dateobj = new Date();
				

				NeoBankEnvironment.setComment(3, className, "inside merch retail payments    " + df.format(dateobj));
				String filePath = NeoBankEnvironment.getFileUploadPath() + "/" + "QR.png";

				int size = 500;
				String fileType = "png";
				String myCodeText = null;
				mFile = new File(filePath);

				String currentDate = (df.format(dateobj));

				if (session.getAttribute("SESS_USER") != null)
					billerCodeValue = ((User) session.getAttribute("SESS_USER")).getRelationId();
				if (session.getAttribute("SESS_USER") != null)
					merchantId = ((User) session.getAttribute("SESS_USER")).getUserId();
				if (request.getParameter("amount") != null)
					retailPayAmount = StringUtils.trim(request.getParameter("amount"));

				mMerchant = (Merchant) MerchantDao.class.getConstructor().newInstance().getMerchantDetailsForQR(merchantId);
				if (mMerchant != null) {
					mccCodeValue = mMerchant.getMccCategoryId();
					//merchCityValue = mMerchant.getCounty().replace(" ", "");
					merchNameValue = mMerchant.getCompanyName().replace(" ", "");
					
				} else {
					NeoBankEnvironment.setComment(1, className, "Merchant Details not available");
					throw new Exception("Merchant Details not available ");
				}

				int txnAmountLength = 13;
				int merchantNameLength = 25;
				

				// note for static make it 13 zeroes
				retailPayAmount = Utilities.getMoneyinSimpleDecimalFormat(retailPayAmount);
				retailPayAmount = StringUtils.leftPad("" + retailPayAmount, txnAmountLength, "0");
				merchNameValue = StringUtils.leftPad("" + merchNameValue, merchantNameLength, "0");
				//merchCityValue = StringUtils.leftPad("" + merchCityValue, merchantCityLength, "0");
				

				strAppend = new StringBuffer();
				strAppend.append("");
				// Payload Indicator
				strAppend.append("000201");// Indicator - 00, length - 02, value - 01, constant
				// Point of Initiation Method
				strAppend.append("010212");// Indicator - 01, length - 02, value-12 (dynamic), 11 (static), constant
				// Merchant Account Information
				strAppend.append("5112" + billerCodeValue);// Indicator - 51, length - 12, value - merchant's
															// billerCode, variable
				// Merchant Category Code
				strAppend.append("5104" + mccCodeValue); // Indicator - 52, length - 04, value - merchant's category
															// code , constant
				
				
				// Transaction Currency
				strAppend.append("5303" + txnCurrencyIdValue); // Indicator - 53, length - 03, constant,
				// Transaction Amount
				strAppend.append("5413" + retailPayAmount); // Indicator - 54, length - 13, constant,
				// Country Code
				strAppend.append("5802" + countryCodeValue); // Indicator - 58, length - 02, constant,
				// Merchant Name
				strAppend.append("5925" + merchNameValue); // Indicator - 59, length - 25, variable,
				// Merchant City
				// strAppend.append("6015" + merchCityValue); // Indicator - 60, length - 15, variable,
				
				// Pay Type
				strAppend.append("625013" + tnxType + currentDate); // Indicator - 62, 50-Payment Specific Data length - 13,(TxType = 3, Datetime
																	// =10 , variable, ()

				
				// 000201010212511211917762148451048999530340454130000000500.005802KE5925000000000000000000000Noba601500Tharaka-Nithi6213WRP030321100963043233
				
				// Calculate Checksum
				myCodeText = strAppend.toString();
				
				
				byte[] bytes = myCodeText.getBytes();

				Checksum crc32 = new CRC32();
				crc32.update(bytes, 0, bytes.length);
				CRCValue = crc32.getValue();

				NeoBankEnvironment.setComment(3, className, "Check Sum value    " + CRCValue);
				// Checksum
				strAppend.append("6304" + Hex.encodeHexString(String.valueOf(CRCValue).substring(0, 2).getBytes())); // Indicator
																														// -
																														// 63,
																														// length
																														// -
																														// 04,
																														// constant,
																														// ()

				myCodeText = strAppend.toString();
				// at this moment we are not encoding the string with dynamic ID generation
				// myCodeText =
				// "D"+"|"+tnxType+"|"+billerCodeValue+"|"+txnCurrencyIdValue+"|"+retailPayAmount+"|"+Utilities.getMYSQLCurrentTimeStampForInsert()
				// ;

				NeoBankEnvironment.setComment(3, className, "QR String is     " + myCodeText);
				NeoBankEnvironment.setComment(3,className, "Length of string is "+myCodeText.length());


				Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
				hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
				hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
				hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

				QRCodeWriter qrCodeWriter = new QRCodeWriter();
				BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
				
				int matrixWidth = byteMatrix.getWidth();
				

				BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
				image.createGraphics();		

				Graphics2D graphics = (Graphics2D) image.getGraphics();
				graphics.setColor(Color.WHITE);
				graphics.fillRect(0, 0, matrixWidth, matrixWidth);
				graphics.setColor(Color.BLACK);
				graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				

				for (int i = 0; i < matrixWidth; i++) {
					for (int j = 0; j < matrixWidth; j++) {
						if (byteMatrix.get(i, j)) {
							graphics.fillRect(i, j, 1, 1);
						}
					}
				}
				NeoBankEnvironment.setComment(3, className, "before writing to      " + myCodeText);
				ImageIO.write(image, fileType, mFile);

				merchDqrin = new FileInputStream(mFile);
				merchDqrout = response.getOutputStream();
				response.setContentType("image/png");
				response.setContentLength((int) mFile.length());
				response.setHeader("Content-Disposition", "inline; filename=\"" + mFile.getName() + "\"");
				// Copy the contents of the file to the output stream
				byte[] buf = new byte[1024];
				int count = 0;
				while ((count = merchDqrin.read(buf)) >= 0) {
					merchDqrout.write(buf, 0, count);
				}
				try {

				} finally {
					if (merchDqrout != null) merchDqrout.close();
					if (merchDqrin != null) merchDqrin.close();
					if (mFile.exists()) mFile.delete();
					if (qrCodeWriter != null) qrCodeWriter = null;
					if (myCodeText != null) myCodeText = null;
					if (hintMap != null) hintMap = null;
					if (bytes != null) bytes = null;
					if (mMerchant != null) mMerchant = null;
					if (mccCodeValue != null) mccCodeValue = null;
					if (retailPayAmount != null) retailPayAmount = null;
					if (countryCodeValue != null) countryCodeValue = null;
					if (merchNameValue != null) merchNameValue = null;
					if (tnxType != null) tnxType = null;
					if (txnCurrencyIdValue != null) txnCurrencyIdValue = null;
					if (currentDate != null) currentDate = null;
					if (merchantId != null) merchantId = null;
					if (image != null) image = null;
				}
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
		break;
		case "cashout_dynamic_qr":
			try {
				
				FileInputStream merchDqrin = null;
				OutputStream merchDqrout = null;
				File mFile = null;
				String countryCodeValue = "PA";//TODO Take this parameters from Ben
				String merchNameValue = null;
				long CRCValue = 0;
				StringBuffer strAppend = null;
				String merchantCodeValue = null;
				Merchant mMerchant = null;
				String mccCodeValue = null;
				String txnCurrencyIdValue = NeoBankEnvironment.getUSDCurrencyId();
				String merchantId=null;
				User user = null;
				String filePath = NeoBankEnvironment.getFileUploadPath() + "/" + "QR.png";
				String tnxType = "WCO";//TODO Take this parameters from Ben
				String cashoutAmount = null;
				SimpleDateFormat df = new SimpleDateFormat("ddMMyyHHmm");
				Date dateobj = new Date();
				int size = 500;
				String fileType = "png";
				String myCodeText = null;
				mFile = new File(filePath);
				
				if (session.getAttribute("SESS_USER") == null)
					throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				merchantCodeValue = user.getRelationId();
				merchantId = user.getUserId();
				if (request.getParameter("amount") != null)
					cashoutAmount = request.getParameter("amount").trim();
				String currentDate = (df.format(dateobj));
				mMerchant = (Merchant) MerchantDao.class.getConstructor().newInstance().getMerchantDetailsForQR(merchantId);
				if (mMerchant != null) {
					mccCodeValue = mMerchant.getMccCategoryId();
					merchNameValue = mMerchant.getCompanyName().replace(" ", "");
				} else {
					NeoBankEnvironment.setComment(1, className, "Merchant Details not available");
					throw new Exception("Merchant Details not available ");
				}

				int txnAmountLength = 13;
				int merchantNameLength = 25;
				
				// note for static make it 13 zeroes
				cashoutAmount = Utilities.getMoneyinSimpleDecimalFormat(cashoutAmount);
				cashoutAmount = StringUtils.leftPad("" + cashoutAmount, txnAmountLength, "0");
				merchNameValue = StringUtils.leftPad("" + merchNameValue, merchantNameLength, "0");
			

				strAppend = new StringBuffer();
				strAppend.append("");
				// Payload Indicator
				strAppend.append("000201");// Indicator - 00, length - 02, value - 01, constant
				// Point of Initiation Method
				strAppend.append("010212");// Indicator - 01, length - 02, value-12 (dynamic), 11 (static), constant
				// Merchant Account Information
				strAppend.append("5112" + merchantCodeValue);// Indicator - 51, length - 12, value - merchant's
															// billerCode, variable
				// Merchant Category Code
				strAppend.append("5104" + mccCodeValue); // Indicator - 52, length - 04, value - merchant's category
															// code , constant
				// Transaction Currency
				strAppend.append("5303" + txnCurrencyIdValue); // Indicator - 53, length - 03, constant,
				// Transaction Amount
				strAppend.append("5413" + cashoutAmount); // Indicator - 54, length - 13, constant,
				// Country Code
				strAppend.append("5802" + countryCodeValue); // Indicator - 58, length - 02, constant,
				// Merchant Name
				strAppend.append("5925" + merchNameValue); // Indicator - 59, length - 25, variable,
			
				// Pay Type
				strAppend.append("625013" + tnxType + currentDate); // Indicator - 62, 50-Payment Specific Data length - 13,(TxType = 3, Datetime
																	// =10 , variable, ()
				

				// Calculate Checksum
				myCodeText = strAppend.toString();
				byte[] bytes = myCodeText.getBytes();

				Checksum crc32 = new CRC32();
				crc32.update(bytes, 0, bytes.length);
				CRCValue = crc32.getValue();

				NeoBankEnvironment.setComment(3, className, "Check Sum value    " + CRCValue);
				// Checksum
				strAppend.append("6304" + Hex.encodeHexString(String.valueOf(CRCValue).substring(0, 2).getBytes())); // Indicator
																														// -
																														// 63,
																														// length
																														// -
																														// 04,
																														// constant,
																														// ()

				myCodeText = strAppend.toString();
				NeoBankEnvironment.setComment(3,className, "Length of string is "+myCodeText.length());

				Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
				hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
				hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
				hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

				QRCodeWriter qrCodeWriter = new QRCodeWriter();
				BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
				int matrixWidth = byteMatrix.getWidth();

				BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
				image.createGraphics();
				Graphics2D graphics = (Graphics2D) image.getGraphics();
				graphics.setColor(Color.WHITE);
				graphics.fillRect(0, 0, matrixWidth, matrixWidth);
				graphics.setColor(Color.BLACK);
				graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				

				for (int i = 0; i < matrixWidth; i++) {
					for (int j = 0; j < matrixWidth; j++) {
						if (byteMatrix.get(i, j)) {
							graphics.fillRect(i, j, 1, 1);
						}
					}
				}
				NeoBankEnvironment.setComment(3, className, "before writing to      " + myCodeText);
				ImageIO.write(image, fileType, mFile);

				merchDqrin = new FileInputStream(mFile);
				merchDqrout = response.getOutputStream();
				response.setContentType("image/png");
				response.setContentLength((int) mFile.length());
				response.setHeader("Content-Disposition", "inline; filename=\"" + mFile.getName() + "\"");
				// Copy the contents of the file to the output stream
				byte[] buf = new byte[1024];
				int count = 0;
				while ((count = merchDqrin.read(buf)) >= 0) {
					merchDqrout.write(buf, 0, count);
				}
				try {
				} finally {
					if (merchDqrout != null) merchDqrout.close();
					if (merchDqrin != null) merchDqrin.close();
					if (mFile.exists()) mFile.delete();
					if (qrCodeWriter != null) qrCodeWriter = null;
					if (myCodeText != null) myCodeText = null;
					if (image != null) image.flush();
					if (graphics != null) graphics.dispose();
					if (hintMap != null) hintMap = null;
					if (bytes != null) bytes = null;
					if (mMerchant != null) mMerchant = null;
					if (mccCodeValue != null) mccCodeValue = null;
					if (cashoutAmount != null) cashoutAmount = null;
					if (countryCodeValue != null) countryCodeValue = null;
					if (merchNameValue != null) merchNameValue = null;
					if (tnxType != null) tnxType = null;
					if (txnCurrencyIdValue != null) txnCurrencyIdValue = null;
					if (currentDate != null) currentDate = null;
					if (merchantId != null)merchantId = null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
		break;
		case "topup_dynamic_qr":
			try {
				
				FileInputStream merchDqrin = null;
				OutputStream merchDqrout = null;
				File mFile = null;
				String countryCodeValue = "PA"; //TODO Take this parameters from Ben
				String merchNameValue = null;
				long CRCValue = 0;
				StringBuffer strAppend = null;
				String merchantCodeValue = null;
				Merchant mMerchant = null;
				String mccCodeValue = null;
				String txnCurrencyIdValue = NeoBankEnvironment.getUSDCurrencyId();
				String filePath = NeoBankEnvironment.getFileUploadPath() + "/" + "QR.png";
				String tnxType = "MWT";//TODO Take this parameters from Ben
				String topupAmount = null;
				int size = 500;
				String fileType = "png";
				String myCodeText = null;
				mFile = new File(filePath);
				SimpleDateFormat df = new SimpleDateFormat("ddMMyyHHmm");
				Date dateobj = new Date();
				String currentDate = (df.format(dateobj));
				String merchantId=null; 
				User user= null;
				if (session.getAttribute("SESS_USER") == null)
					throw new Exception ("Session has expired, please log in again");
				user = (User) session.getAttribute("SESS_USER");
				if (request.getParameter("amount") != null)
					topupAmount = request.getParameter("amount").trim();
				if (session.getAttribute("SESS_USER") == null)
					throw new Exception ("Session has expired, please log in again");
				merchantCodeValue = user.getRelationId();
				merchantId = user.getUserId();
				mMerchant = (Merchant) MerchantDao.class.getConstructor().newInstance().getMerchantDetailsForQR(merchantId);
				if (mMerchant != null) {
					mccCodeValue = mMerchant.getMccCategoryId();
					merchNameValue = mMerchant.getCompanyName().replace(" ", "");
				} else {
					NeoBankEnvironment.setComment(1, className, "Merchant Details not available");
					throw new Exception("Merchant Details not available ");
				}

				int txnAmountLength = 13;
				int merchantNameLength = 25;
				
				// note for static make it 13 zeroes
				topupAmount = Utilities.getMoneyinSimpleDecimalFormat(topupAmount);
				topupAmount = StringUtils.leftPad("" + topupAmount, txnAmountLength, "0");
				merchNameValue = StringUtils.leftPad("" + merchNameValue, merchantNameLength, "0");
		

				strAppend = new StringBuffer();
				strAppend.append("");
				// Payload Indicator
				strAppend.append("000201");// Indicator - 00, length - 02, value - 01, constant
				// Point of Initiation Method
				strAppend.append("010212");// Indicator - 01, length - 02, value-12 (dynamic), 11 (static), constant
				// Merchant Account Information
				strAppend.append("5112" + merchantCodeValue);// Indicator - 51, length - 12, value - merchant's
															// billerCode, variable
				// Merchant Category Code
				strAppend.append("5104" + mccCodeValue); // Indicator - 52, length - 04, value - merchant's category
															// code , constant
				// Transaction Currency
				strAppend.append("5303" + txnCurrencyIdValue); // Indicator - 53, length - 03, constant,
				// Transaction Amount
				strAppend.append("5413" + topupAmount); // Indicator - 54, length - 13, constant,
				// Country Code
				strAppend.append("5802" + countryCodeValue); // Indicator - 58, length - 02, constant,
				// Merchant Name
				strAppend.append("5925" + merchNameValue); // Indicator - 59, length - 25, variable,
				// Pay Type
				strAppend.append("625013" + tnxType + currentDate); // Indicator - 62, 50-Payment Specific Data length - 13,(TxType = 3, Datetime
																	// =10 , variable, ()

				// Calculate Checksum
				myCodeText = strAppend.toString();
				byte[] bytes = myCodeText.getBytes();

				Checksum crc32 = new CRC32();
				crc32.update(bytes, 0, bytes.length);
				CRCValue = crc32.getValue();

				NeoBankEnvironment.setComment(3, className, "Check Sum value    " + CRCValue);
				// Checksum
				strAppend.append("6304" + Hex.encodeHexString(String.valueOf(CRCValue).substring(0, 2).getBytes())); // Indicator
																														// -
																														// 63,
																														// length
																														// -
																														// 04,
																														// constant,
																														// ()

				myCodeText = strAppend.toString();

				NeoBankEnvironment.setComment(3, className, "QR String is     " + myCodeText);
				NeoBankEnvironment.setComment(3,className, "Length of string is "+myCodeText.length());

				Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
				hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
				hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
				hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

				QRCodeWriter qrCodeWriter = new QRCodeWriter();
				BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
				int matrixWidth = byteMatrix.getWidth();
				BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
				image.createGraphics();

				Graphics2D graphics = (Graphics2D) image.getGraphics();
				graphics.setColor(Color.WHITE);
				graphics.fillRect(0, 0, matrixWidth, matrixWidth);
				graphics.setColor(Color.BLACK);
				graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				

				for (int i = 0; i < matrixWidth; i++) {
					for (int j = 0; j < matrixWidth; j++) {
						if (byteMatrix.get(i, j)) {
							graphics.fillRect(i, j, 1, 1);
						}
					}
				}
				NeoBankEnvironment.setComment(3, className, "before writing to " + myCodeText);
				ImageIO.write(image, fileType, mFile);

				merchDqrin = new FileInputStream(mFile);
				merchDqrout = response.getOutputStream();
				response.setContentType("image/png");
				response.setContentLength((int) mFile.length());
				response.setHeader("Content-Disposition", "inline; filename=\"" + mFile.getName() + "\"");
				// Copy the contents of the file to the output stream
				byte[] buf = new byte[1024];
				int count = 0;
				while ((count = merchDqrin.read(buf)) >= 0) {
					merchDqrout.write(buf, 0, count);
				}

				try {

				} finally {
					if (merchDqrout != null) merchDqrout.close();
					if (merchDqrin != null) merchDqrin.close();
					if (mFile.exists()) mFile.delete();
					if (qrCodeWriter != null) qrCodeWriter = null;
					if (myCodeText != null) myCodeText = null;
					if (image != null) image.flush();
					if (graphics != null) graphics.dispose();
					if (hintMap != null) hintMap = null;
					if (bytes != null) bytes = null;
					if (mMerchant != null) mMerchant = null;
					if (mccCodeValue != null) mccCodeValue = null;
 					if (topupAmount != null) topupAmount = null;
					if (countryCodeValue != null) countryCodeValue = null;
					if (merchNameValue != null) merchNameValue = null;
					if (tnxType != null) tnxType = null;
					if (txnCurrencyIdValue != null) txnCurrencyIdValue = null;
					if (currentDate != null) currentDate = null;
					if (merchantId != null)merchantId = null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
		break;
		
		case "Transactions History":
			try {
				request.setAttribute("lastaction", "merchtxn");
				request.setAttribute("lastrule", "View Transactions");
				response.setContentType("text/html");
				User user = (User) session.getAttribute("SESS_USER");
				
				if (session.getAttribute("SESS_USER") == null)
					throw new Exception ("Session has expired, please log in again");
				String merchantCodeValue = user.getRelationId();
				try {
					
					request.setAttribute("txnrules", 
							(List<TransactionRules>)MerchantPaymentsDao.class.getConstructor().newInstance().getMerchantTransactionRules());
					request.setAttribute("merchtxn", 
							(List<AssetTransaction>)MerchantPaymentsDao.class.getConstructor().newInstance().getMerchantTransactions(merchantCodeValue));
					ctx.getRequestDispatcher(NeoBankEnvironment.getMerchantTransctionHistoryPagePage()).forward(request, response);
				}finally {
					if (merchantCodeValue != null)merchantCodeValue = null;
					if (user != null)user = null;
				}
				
			} catch (Exception e) {
				NeoBankEnvironment.setComment(1, className, " Overall Exception for case "+rules+" is "+e.getMessage());
				Utilities.callException(request, response, ctx, e.getMessage());
			}
		break;
		
		
		
		}
		
	}

}
