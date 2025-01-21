package com.pporte.dao;

import com.pporte.NeoBankEnvironment;

public class DataPool {
	public static MySqlPoolableObjectFactory dbConnPool = null; // The Tomcat DB Connection POOL 
	public static final String className = DataPool.class.getSimpleName();
	
	public static void initDBPool() throws Exception{
		if(dbConnPool == null){ // Only created by first servlet to call
			try {
				//Initializes the connection broker which handles connection in the connection pool.
				NeoBankEnvironment.setComment(3,className,"Starting DBPool ");

				dbConnPool = new MySqlPoolableObjectFactory();
				MySqlPoolableObjectFactory.init();	
				//NeoBankEnvironment.setComment(3, className, "Inside initDBPool method...dbConnPool formed "+MySqlPoolableObjectFactory.checkConnPoolformed());										
		}catch (Exception e) {
			NeoBankEnvironment.setComment(1,className,"Internal error formed during the ConnectionPool formation "+e.getMessage());
				throw new Exception("An internal error has occurred,please retry");
			}			
		}		
	}
	
	
	public static void destroyDBPool() throws Exception{
		 try {
			 if(MySqlPoolableObjectFactory.checkConnPoolformed()) {
				 MySqlPoolableObjectFactory.shutdownDriver();
				 if(MySqlPoolableObjectFactory.checkConnPoolformed() == false)
					 NeoBankEnvironment.setComment(3,className,"Connection Pool is closed");
				 else
					 NeoBankEnvironment.setComment(3,className,"Connection Pool is still open");
			 }
		 	} catch (Exception e) {
		 		NeoBankEnvironment.setComment(1,className, "Problem with shutting of Connection Pool  "+e.getMessage());
		 		throw new Exception("Problem with shutting of Connection Pool  "+e.getMessage());
			}		
	}

}
