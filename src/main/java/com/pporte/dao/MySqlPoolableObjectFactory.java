package com.pporte.dao;

import java.sql.Connection;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import com.pporte.NeoBankEnvironment;


public class MySqlPoolableObjectFactory {
	private static PoolProperties pool=null;
	private static DataSource datasource = null;
	private static String className = MySqlPoolableObjectFactory.class.getSimpleName();

	public static boolean checkConnPoolformed() {
		if(datasource!=null){
			//if(datasource.)
			return true;
		}else{
			return false;
		}			
	}
	public static void init() throws Exception {
		
		try{
			

			if( pool==null && datasource==null){
				pool = new PoolProperties();
				pool.setUrl(NeoBankEnvironment.getDBURL());
				pool.setDriverClassName(NeoBankEnvironment.getMYSQLDriver());
				pool.setUsername(NeoBankEnvironment.getDBUser());
				pool.setPassword(NeoBankEnvironment.getDBPwd());
				//TODO All values below are the default value - change it for production
				pool.setJmxEnabled(true);
				pool.setTestWhileIdle(false);
				pool.setTestOnBorrow(true);
				pool.setValidationQuery("SELECT 1");
				pool.setInitSQL("SELECT 1");
				pool.setTestOnReturn(false);
				pool.setValidationInterval(60000); // setting it to 60 sec instead of 30 secs
				pool.setTimeBetweenEvictionRunsMillis(30000);
				pool.setMaxActive(100);
				pool.setInitialSize(10);
				pool.setMaxWait(10000);
				pool.setRemoveAbandonedTimeout(60);
				pool.setMinEvictableIdleTimeMillis(30000);
				pool.setMinIdle(10);
				pool.setLogAbandoned(true);
				pool.setRemoveAbandoned(true);
				pool.setCommitOnReturn(false);
				pool.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
				pool.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer;org.apache.tomcat.jdbc.pool.interceptor.ResetAbandonedTimer");
				datasource = new DataSource();
				datasource.setPoolProperties(pool);
				NeoBankEnvironment.setComment(3,className," ConPool Datasource formed with no of connections: "+ datasource.getSize());
		}
			
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className," Exception in Datasource formation "+e.getMessage());
			System.out.println(className+ "  Exception in Datasource formation "+e.getMessage());
		}
	}
	
	public static Connection getConnection() throws Exception {
		Connection conn=null;
		try{
			conn = datasource.getConnection();
			}catch(Exception e){
				NeoBankEnvironment.setComment(1,className," Can't get connection "+e.getMessage());
			}
		
		return conn;
	}
	
	public static void shutdownDriver() throws Exception {
		if(datasource!=null) {
				datasource.close();
				datasource=null;
		}
		if(pool!=null)
			pool=null;
	}
}
