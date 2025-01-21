package com.pporte.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.lang3.RandomStringUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.pporte.NeoBankEnvironment;


public class ReceiptInvoiceUtility {
	private static String classname = ReceiptInvoiceUtility.class.getSimpleName();
      public static String printInvoice(String partnerName, String emailId, String partnerContact,String date,String receiverEmail,
    		  String receiverName,String receiverBank, String bankCode,String receiverBankAcc,String txnReference,String stellarHash,
    		  String srcAmount,String destAmount) throws Exception{
    	  String result = "false";
    	  String fileToBeSent=null;
    	  try {
    		  NeoBankEnvironment.setComment(3, classname, "Inside printInvoice");
    		  Font font = new Font(Font.FontFamily.TIMES_ROMAN, 15,Font.BOLD);
    		  Document document = new Document();
    		  String fileName=partnerName.concat("-").concat(RandomStringUtils.randomAlphabetic(6));
    		  String filePath=NeoBankEnvironment.getInvoiceFilePath().concat(fileName).concat(".pdf");
    		  fileToBeSent=fileName;
	    		  NeoBankEnvironment.setComment(3, classname, "fileName "+fileName);
	    		  NeoBankEnvironment.setComment(3, classname, "filePath "+filePath);
	    		  NeoBankEnvironment.setComment(3, classname, "fileToBeSent "+fileToBeSent);
	    		  
    		  OutputStream outputStream = new FileOutputStream(new File(filePath));
    		  
    		  //Create PDFWriter instance.
		      PdfWriter.getInstance(document, outputStream);
		      
		      //Open the document.
		       document.open();
		       Image image = Image.getInstance(NeoBankEnvironment.getPorteLogoPath());
		       image.setAlignment(Image.ALIGN_RIGHT);
		       image.scaleAbsolute(200,80);
		       document.add(image);
		       
		       //Add content to the document.
		        Paragraph paragraphCompany = new Paragraph("Payment Porte",font);
		        paragraphCompany.setSpacingAfter(10f);
		        document.add(paragraphCompany);
		        document.add(new Paragraph("Partner Name: " +partnerName));
		        document.add(new Paragraph("Email Id: " +emailId));
		        document.add(new Paragraph("Contact: " +partnerContact));
		        document.add(new Paragraph("Date: " +date));
		        document.left(32f);
		        
		        LineSeparator ls = new LineSeparator();
				BaseColor lineColor= new BaseColor(32, 178, 170);
				ls.setLineColor(lineColor);
				float lineWidth =12f;
				ls.setLineWidth(lineWidth);
				
				//ls.setOffset(15);
		        document.add(new Chunk(ls));
		        //Close document and outputStream.
		        
		        Chunk glue = new Chunk(new VerticalPositionMark());
		        Paragraph paragraphReceiver = new Paragraph("Receiver Details",font);
		        paragraphReceiver.setSpacingBefore(35f);
		        paragraphReceiver.setSpacingAfter(5f);
		        document.add(paragraphReceiver);
		        
		        Paragraph paragraphReceiverName = new Paragraph("Receiver Name: " +receiverName);
		        paragraphReceiverName.add(new Chunk(glue));
		        paragraphReceiverName.add("Receiver Email: "+receiverEmail);
		        document.add(paragraphReceiverName);
		        
		        Paragraph paragraphReceiverBank = new Paragraph("Receiver Bank: "+receiverBank);
		        paragraphReceiverBank.add(new Chunk(glue));
		        paragraphReceiverBank.add("Receiver Bank Code: "+bankCode);
		        document.add(paragraphReceiverBank);
		        
		        Paragraph paragraphReceiverBankAcc = new Paragraph("Receiver Account Number: " +receiverBankAcc);
		        document.add(paragraphReceiverBankAcc);
		        
		        LineSeparator ls2 = new LineSeparator();
				document.add(new Chunk(ls2));
				
				Paragraph transactionDetails = new Paragraph("Transaction Details",font);
		        transactionDetails.setSpacingBefore(15f);
		        transactionDetails.setSpacingAfter(5f);
		        document.add(transactionDetails);
		        
		        Paragraph transactionReference = new Paragraph("Transcation Reference: " +txnReference);
		        transactionReference.add(new Chunk(glue));
		        transactionReference.add("Stellar Transaction Hash: "+stellarHash);
		        document.add(transactionReference);
		        
		        Paragraph srcAmout = new Paragraph("Source Amount: "+ srcAmount);
		        srcAmout.add(new Chunk(glue));
		        srcAmout.add("Destination Amout: "+destAmount);
		        srcAmout.setSpacingAfter(52f);
		        document.add(srcAmout);
		        
		        LineSeparator l4 = new LineSeparator();
				l4.setLineColor(lineColor);
				l4.setLineWidth(lineWidth);
				
				document.add(new Chunk(l4));
				
				NeoBankEnvironment.setComment(3, classname, "Going to print pdf");
		        
		        try {
		        	NeoBankEnvironment.setComment(3, classname, "pdf created successfully");
		        	result="true"+"|"+fileToBeSent;
		        	NeoBankEnvironment.setComment(3, classname, "result is "+result);
		        	 document.close();
				     outputStream.close();
		        		
				} finally {
					if(font !=null) font = null;if(document !=null) document = null;if(emailId !=null) emailId = null;if(image !=null) image = null;
					if(paragraphCompany !=null) paragraphCompany = null;if(partnerName !=null) partnerName = null;if(partnerContact !=null) partnerContact = null;
					if(date !=null) date = null;if(receiverName !=null) receiverName = null;if(paragraphReceiverName !=null) paragraphReceiverName = null;
					if(receiverEmail !=null) receiverEmail = null;if(ls2 !=null) ls2 = null;if(transactionDetails !=null) transactionDetails = null;
					if(receiverBank !=null) receiverBank = null;if(paragraphReceiverBank !=null) paragraphReceiverBank = null;if(bankCode !=null) bankCode = null;
					if(paragraphReceiverBankAcc !=null) paragraphReceiverBankAcc = null;if(transactionReference !=null) transactionReference = null;
					if(srcAmout !=null) srcAmout = null;if(destAmount !=null) destAmount = null;if(l4 !=null) l4 = null;
					if (fileName!=null)fileName=null; if (filePath!=null)filePath=null; if (fileToBeSent!=null)fileToBeSent=null;
					
				}
		} catch (Exception e) {
			result = "false";
			NeoBankEnvironment.setComment(1,classname,"The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
			throw new Exception ("The exception in method "+Thread.currentThread().getStackTrace()[1].getMethodName()+" is "+e.getMessage());
		}
		return result;
    	  
      }


}
