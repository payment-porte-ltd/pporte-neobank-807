package com.pporte.web;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;



import com.pporte.NeoBankEnvironment;
import com.pporte.utilities.DaemonThreadFactory;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class MailExecutorContextListener
 *
 */
@WebListener
public class ExecutorContextListener implements ServletContextListener {
	private  static String className = ExecutorContextListener.class.getSimpleName();

	private  ExecutorService executorEmail;
	//private  ExecutorService executorSomethingElse;
    /**
     * Default constructor. 
     */
    public ExecutorContextListener() {
    	NeoBankEnvironment.setComment(3,className,"Inside constructor method of MailExecutorContextListener");
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
		//System.out.println("============== starting :" +className + " at "+java.time.LocalTime.now());

    	ServletContext context = sce.getServletContext();
    	
		try {
			if(NeoBankEnvironment.getInstance()==null) {
				NeoBankEnvironment.init();
				NeoBankEnvironment.setComment(3,className,"===> NeoBankEnvironment is initialized......");
				//NeoBankEnvironment.setComment(3,className,"Inside contextInitialized method of MailExecutorContextListener");
			}
			
		} catch (Exception e1) {
			System.out.println("CRITICAL ERROR : Failed to initialize NeoBankEnvironment "+e1.getMessage());		
			}
    	
    	
		NeoBankEnvironment.setComment(3,className,"============== starting :" +className + " at "+java.time.LocalTime.now());

    	try {
			int nr_executors = Integer.parseInt(NeoBankEnvironment.getEmailThreadCount());
	        ThreadFactory daemonFactory = new DaemonThreadFactory();
	        try {
	            nr_executors = Integer.parseInt(context.getInitParameter("nr-executors"));
	        } catch (NumberFormatException ignore ) {}

	        if(nr_executors <= 1) {
	        	executorEmail = Executors.newSingleThreadExecutor(daemonFactory);
	        } else {
	        	executorEmail = Executors.newFixedThreadPool(nr_executors,daemonFactory);
	       }
	       // Publish the executor in Setvlet Environment to be grabbed by workflows   
	       context.setAttribute("EMAIL_EXECUTOR", executorEmail);
			
			
		} catch (Exception e) {
			NeoBankEnvironment.setComment(1,className,"Inside init method of MailExecutorContextListener");		} 
    	
    }
	

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
    	try {
    		NeoBankEnvironment.setComment(3,className,"attempt to shutdown executor");
    	    executorEmail.shutdown();
    	    executorEmail.awaitTermination(5, TimeUnit.SECONDS);
    	}
    	catch (InterruptedException e) {
    		NeoBankEnvironment.setComment(1,className,"tasks interrupted "+e.getMessage());
    	}
    	finally {
    	    if (!executorEmail.isTerminated()) {
    	    	NeoBankEnvironment.setComment(3,className,"cancel non-finished tasks");
    	    }
    	    
    	    executorEmail.shutdownNow();
    	    NeoBankEnvironment.setComment(3,className,"shutdown finished");
    	}
    }


}

