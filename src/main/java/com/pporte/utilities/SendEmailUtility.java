package com.pporte.utilities;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import com.pporte.NeoBankEnvironment;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;


public class SendEmailUtility {
	private static String className = SendEmailUtility.class.getSimpleName();
	
	private static String  LOGO_PATH = "https://paymentporte.com/assets/img/d-agency2/logo.png"; // TODO:- Once the application is hosted make sure to change the path to match our server's path. 
	static Properties mailServerProperties = null;
	static String username = null;
	static String password = null;
	
	public static boolean sendMail(String sendto, String sendSubject, String content, String name)    throws Exception	{
		boolean result = false;
		NeoBankEnvironment.setComment(3, className, "  inside sendmail start  ");
		username = NeoBankEnvironment.getSMTPUserId();
		password = NeoBankEnvironment.getSMTPUserPwd();
		String  PATH = NeoBankEnvironment.getEmailTemplatePath();
		//Message message =null;
		try {
			NeoBankEnvironment.setComment(3, className, "sendto  is  " + sendto   + "  sendSubject  is  " + sendSubject+ "  content  is  " + content+" name"+name); 

			mailServerProperties = new Properties();
			mailServerProperties.setProperty("mail.smtp.starttls.enable", "true");
			mailServerProperties.setProperty("mail.smtp.host", NeoBankEnvironment.getSMTPHost());
			mailServerProperties.setProperty("mail.smtp.user", username);
			mailServerProperties.setProperty("mail.smtp.password", password);
			mailServerProperties.setProperty("mail.smtp.port", NeoBankEnvironment.getSMTPTLSPort());
			mailServerProperties.setProperty("mail.smtp.auth", "true");
			mailServerProperties.setProperty("mail.smtp.ssl.trust", "*");
			mailServerProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

	        //System.out.println("Props : " + prop);
			NeoBankEnvironment.setComment(3, className, "  sending email here....  mailServerProperties  is   " +  mailServerProperties   );
		   
	        Session session = Session.getInstance(mailServerProperties, new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username,
	                		password);
	            }
	        });
	  
	        NeoBankEnvironment.setComment(3, className, "  mail session is   "+session);
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(NeoBankEnvironment.getSendFromEmailId()));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sendto));
			message.setSubject(sendSubject);
			
            NeoBankEnvironment.setComment(3, className, "content is   " + content); 
            
            
			// Free marker Template -2.3.31
			
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
            //Assume that the template is available under /src/main/java/utilities/template
            //cfg.setClassForTemplateLoading(Utilities.class, "/template/");
            cfg.setDirectoryForTemplateLoading(new File(PATH));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            Template template = cfg.getTemplate("welcome_email.ftl");
			
          //Pass custom param values
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("name", name);
            paramMap.put("message", content);
            paramMap.put("recipient", sendto);
            paramMap.put("logoPath", LOGO_PATH);
            paramMap.put("date", String.valueOf(Calendar.getInstance().getTime()));
            Writer out = new StringWriter();
            template.process(paramMap, out);
            BodyPart body = new MimeBodyPart();
            body.setContent(out.toString(), "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(body);
            message.setContent(multipart);
			//message.setContent(content,"text/html");
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", username, password);
			transport.sendMessage(message, message.getAllRecipients());
            
            NeoBankEnvironment.setComment(3, className, "message is  " + message); 
			result = true;
			NeoBankEnvironment.setComment(3, className, "Email send successfully to  " + message.getAllRecipients()); 

		} catch (MessagingException e) {
			result = false;
			NeoBankEnvironment.setComment(1, className, "Exception Occured - " +e.getMessage()); 
		}finally {
			//if(message!=null) message=null;
		}
		return result;		
	}	
	public static boolean sendMailWithAttachment(String sendto, String sendSubject, String content, String name, String fileName, String filePath)    throws Exception	{
		boolean result = false;
		NeoBankEnvironment.setComment(3, className, "  inside sendmail start  ");
		username = NeoBankEnvironment.getSMTPUserId();
		password = NeoBankEnvironment.getSMTPUserPwd();
		 File tempFile=null;
		 String  PATH = NeoBankEnvironment.getEmailTemplatePath();
		//Message message =null;
		try {
			NeoBankEnvironment.setComment(3, className, "sendto  is  " + sendto   + "  sendSubject  is  " + sendSubject+ "  content  is  " + content+" name"+name); 
			
			mailServerProperties = new Properties();
			mailServerProperties.setProperty("mail.smtp.starttls.enable", "true");
			mailServerProperties.setProperty("mail.smtp.host", NeoBankEnvironment.getSMTPHost());
			mailServerProperties.setProperty("mail.smtp.user", username);
			mailServerProperties.setProperty("mail.smtp.password", password);
			mailServerProperties.setProperty("mail.smtp.port", NeoBankEnvironment.getSMTPTLSPort());
			mailServerProperties.setProperty("mail.smtp.auth", "true");
			mailServerProperties.setProperty("mail.smtp.ssl.trust", "*");
			mailServerProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
			
			//System.out.println("Props : " + prop);
			NeoBankEnvironment.setComment(3, className, "  sending email here....  mailServerProperties  is   " +  mailServerProperties   );
			
			Session session = Session.getInstance(mailServerProperties, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username,
							password);
				}
			});
			tempFile  = new File(filePath);
			NeoBankEnvironment.setComment(3, className, "  mail session is   "+session);
			MimeMessage message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(NeoBankEnvironment.getSendFromEmailId()));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sendto));
			message.setSubject(sendSubject);
			
			NeoBankEnvironment.setComment(3, className, "content is   " + content); 
			
			 DataSource source = new FileDataSource(filePath);
			// Free marker Template -2.3.31
			 
			
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
			//Assume that the template is available under /src/main/java/utilities/template
			//cfg.setClassForTemplateLoading(Utilities.class, "/template/");
			cfg.setDirectoryForTemplateLoading(new File(PATH));
			cfg.setDefaultEncoding("UTF-8");
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			Template template = cfg.getTemplate("welcome_email.ftl");
			
			//Pass custom param values
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("name", name);
			paramMap.put("message", content);
			paramMap.put("recipient", sendto);
			paramMap.put("logoPath", LOGO_PATH);
			paramMap.put("date", String.valueOf(Calendar.getInstance().getTime()));
			Writer out = new StringWriter();
			template.process(paramMap, out);
			BodyPart body = new MimeBodyPart();
			body.setContent(out.toString(), "text/html");
			body.setDataHandler(new DataHandler(source));
			body.setFileName(fileName);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(body);
			message.setContent(multipart);
			
			//message.setContent(content,"text/html");
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", username, password);
			transport.sendMessage(message, message.getAllRecipients());
			
			NeoBankEnvironment.setComment(3, className, "message is  " + message); 
			result = true;
			NeoBankEnvironment.setComment(3, className, "Email send successfully to  " + message.getAllRecipients()); 
			
		} catch (MessagingException e) {
			result = false;
			NeoBankEnvironment.setComment(1, className, "Exception Occured - " +e.getMessage()); 
		}finally {
			//if(message!=null) message=null;
			if(tempFile!=null) {
		  		if(tempFile.exists()) {
		  			tempFile.delete();
		  		}
		        }
		}
		return result;		
	}	
	public static boolean sendMailWithHtmlExample(String sendto, String sendSubject)    throws Exception	{
		/*
		 obj.put("stmpuserid", setParam("info.c360group@gmail.com"));
			obj.put("smtppwd", setParam("c360@1234"));
			obj.put("smtphost", setParam("smtp.gmail.com"));
			obj.put("smtptlsport", setParam("587"));
			obj.put("smtpsslport", setParam("465"));
			obj.put("smtpsendfromemail", setParam("info.c360group@gmail.com"));
		 */
		boolean result = false;
		System.out.println("  inside sendmail start  ");
		username = "info.c360group@gmail.com";
		password = "ttmgfscntvjykwrw";
		//Message message =null;
		String PATH= "src/main/java/com/pporte/utilities/template";
		try {
			System.out.println( "sendto  is  " + sendto   + "  sendSubject  is  " + sendSubject); 
			
			mailServerProperties = new Properties();
			mailServerProperties.setProperty("mail.smtp.starttls.enable", "true");
			mailServerProperties.setProperty("mail.smtp.host", "smtp.gmail.com");
			mailServerProperties.setProperty("mail.smtp.user", username);
			mailServerProperties.setProperty("mail.smtp.password", password);
			mailServerProperties.setProperty("mail.smtp.port", "587");
			mailServerProperties.setProperty("mail.smtp.auth", "true");
			mailServerProperties.setProperty("mail.smtp.ssl.trust", "*");
			mailServerProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
			
			//System.out.println("Props : " + prop);
			System.out.println("  sending email here....  mailServerProperties  is   " +  mailServerProperties   );
			
			Session session = Session.getInstance(mailServerProperties, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username,
							password);
				}
			});
			
			System.out.println("  mail session is   "+session);
			MimeMessage message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress("info.c360group@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sendto));
			message.setSubject(sendSubject);
			
			// Free marker Template -2.3.31
			
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
            //Assume that the template is available under /src/main/java/utilities/template
            //cfg.setClassForTemplateLoading(Utilities.class, "/template/");
            cfg.setDirectoryForTemplateLoading(new File(PATH));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            Template template = cfg.getTemplate("welcome_email.ftl");
			
          //Pass custom param values
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("name", "Benjamin Kelong");
            paramMap.put("message", "Welcome to Payment Porte");
            paramMap.put("recipient", sendto);
            paramMap.put("logoPath", LOGO_PATH);
            paramMap.put("date", String.valueOf(Calendar.getInstance().getTime()));
            Writer out = new StringWriter();
            template.process(paramMap, out);
            BodyPart body = new MimeBodyPart();
            body.setContent(out.toString(), "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(body);
            message.setContent(multipart);
			//message.setContent(content,"text/html");
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", username, password);
			transport.sendMessage(message, message.getAllRecipients());
			System.out.println("message is  " + message); 
			result = true;
			System.out.println("Email send successfully to  " + message.getAllRecipients()); 
			
		} catch (MessagingException e) {
			result = false;
			System.out.println("Exception Occured - " +e.getMessage()); 
		}finally {
			//if(message!=null) message=null;
		}
		return result;		
	}	
}
