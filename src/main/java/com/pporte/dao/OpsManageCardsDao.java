package com.pporte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.pporte.NeoBankEnvironment;
import com.pporte.model.CardDetails;
import com.pporte.model.Customer;
import com.pporte.utilities.Utilities;

public class OpsManageCardsDao extends HandleConnections {
	private static String className = OpsManageCardsDao.class.getSimpleName();
	
	public List<Customer> getCustomersWithCards() throws Exception{
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		Customer customer = null;
		List<Customer> customers = null;
		try{
			
			connection = super.getConnection();	
			
			query =   " select  a.relationshipno relationshipno, a.customerid customerid, a.customername customername, a.custcontact custcontact,"
					+ "	b.cardname cardname,b.cardalias cardalias,b.tokenid tokenid from customer_details a, card_tokenization_acquiring_bc b where a.relationshipno = b.relationshipno order by b.createdon desc limit 1000";
			pstmt = connection.prepareStatement(query);
			rs = (ResultSet)pstmt.executeQuery();

			 if(rs!=null){
				 customers = new ArrayList<Customer>();
				 	while(rs.next()){	
				 		customer = new Customer();
				 		customer.setRelationshipNo(StringUtils.trim(rs.getString("relationshipno")));
				 		customer.setCustomerId(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("customerid"))));
				 		customer.setCustomerName(StringUtils.trim(rs.getString("customername")));
				 		customer.setContact(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("custcontact"))));
				 		customer.setCardAlias(StringUtils.trim(rs.getString("cardalias")));
				 		customer.setTokenId(StringUtils.trim(rs.getString("tokenid")));
				 		customers.add(customer);
				 	}
				 	if(customers !=null)
					NeoBankEnvironment.setComment(3,className,"customers size is "+customers.size());
			 }	

			 if(customers!=null)
				 if(customers.size()==0)
					 customers=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getCustomersWithCards  is  "+e.getMessage());
			throw new Exception ("The exception in method getCustomersWithCards  is  "+e.getMessage());			
		}finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
			}		
		return customers;  
		
	}

	public List <CardDetails> getTokenizedCards(String relationshipNo) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		List <CardDetails> cardsList = null;
		String userType=null;
		
		try{			
			connection = super.getConnection();	
			userType="C";
			              //   1          2        3			4
			query = "select tokenid, cardalias, cardnumber, createdon from card_tokenization_acquiring_bc  where relationshipno=?  and usertype=?  order by createdon desc ";
		  
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, relationshipNo);
			pstmt.setString(2, userType);
			
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){
				 cardsList = new ArrayList<CardDetails>();
				 	while(rs.next()){	
				 		CardDetails m_CardDetails= new CardDetails();
				 		m_CardDetails.setTokenId( StringUtils.trim(rs.getString("tokenid"))    );
				 		m_CardDetails.setCardAlias( StringUtils.trim(rs.getString("cardalias"))  );
				 		m_CardDetails.setMaskedCardNumber(Utilities.maskCardNumber( Utilities.tripleDecryptData( StringUtils.trim(rs.getString("cardnumber")) )   ) );
				 		m_CardDetails.setCreatedOn(Utilities.getMySQLDateTimeConvertor(StringUtils.trim(rs.getString("createdon"))));

				 		cardsList.add(m_CardDetails);
				 		} 	
				 	if(cardsList !=null)
				 	NeoBankEnvironment.setComment(2,className," arrCards size is "+cardsList.size());
				 	} 
			 if(cardsList!=null)
				 if(cardsList.size()==0)
					 cardsList=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getTokenizedCards  is  "+e.getMessage());
			throw new Exception ("The exception in method getTokenizedCards  is  "+e.getMessage());			
		}finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close(); if (userType!=null) userType=null;
			}
		return cardsList;
	}
	public ArrayList<Customer> getSearchOpsSpecificCardCustomerDetails(String customerName, String relationshipNo, String customerId,
			String mobileNo)throws Exception {
		    PreparedStatement pstmt=null;
			Connection connection = null;
			ResultSet rs=null;
			String query = null;
			ArrayList<Customer> arrCustomer = null;
			try {
				NeoBankEnvironment.setComment(3,className,"in  getOpsSpecificCustomerDetails searched relationshipNo is "+
						relationshipNo+ "searched customerId is  "+customerId+ "searched custName  "+ customerName + "phoneNumber is  "+ mobileNo            );

				connection = super.getConnection();	                    
				query="select a.relationshipno relationshipno, a.customername customername, a.customerid customerid,a.custcontact custcontact,"
						+ "b.cardalias cardalias, b.cardnumber cardnumber,b.tokenid tokenid,b.createdon createdon from customer_details a,card_tokenization_acquiring_bc b"
						+ " where a.relationshipno = b.relationshipno and";

				if(relationshipNo.equals("")==false) {	
					query+= "  a.relationshipno = ? and  ";
				}else {
					if(customerName.equals("")==false) {
						query+= "  a.customername like '%"+customerName+"%' and  ";
					}
					if(customerId.equals("")==false) {
						query+= "  a.customerid =? and  ";
					}
					if(mobileNo.equals("")==false) {
						query+= "a.custcontact =? and  ";
					}
				}
				query+= "  1=1 ";
				
				NeoBankEnvironment.setComment(1,className,"search query being executed  is  "+query);


				pstmt = connection.prepareStatement(query);
				if(relationshipNo.equals("")==false) {
					pstmt.setString(1, relationshipNo);
				}
				
				if(customerId.equals("")==false) {
					pstmt.setString(1, Utilities.tripleEncryptData(customerId));
				}
				if(mobileNo.equals("")==false) {
					pstmt.setString(1, Utilities.tripleEncryptData(mobileNo));
				}
				
				rs = (ResultSet)pstmt.executeQuery();
				
				 if(rs!=null){
					 arrCustomer = new ArrayList<Customer>();
					 	while(rs.next()){	 
					 		Customer m_Customer = new Customer();
							m_Customer.setRelationshipNo(StringUtils.trim(rs.getString("relationshipno")));
							m_Customer.setCustomerId(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("customerid"))));
							m_Customer.setCustomerName(StringUtils.trim(rs.getString("customername")));
							m_Customer.setCardAlias(StringUtils.trim(rs.getString("cardalias")));
							m_Customer.setCardNumber(Utilities.tripleDecryptData(StringUtils.trim(rs.getString("cardnumber"))));
							m_Customer.setTokenId(StringUtils.trim(rs.getString("tokenid")));
							m_Customer.setCreatedOn(Utilities.getMySQLDateConvertor(StringUtils.trim(rs.getString("createdon"))));
							arrCustomer.add(m_Customer);
					 		} 

					 	} 

				 if(arrCustomer!=null)
					 if(arrCustomer.size()==0)
						 arrCustomer=null;	
				
			}catch(Exception e) {
				NeoBankEnvironment.setComment(1,className,"The exception in method getSearchOpsSpecificCardCustomerDetails  is  "+e.getMessage());
				throw new Exception ("The exception in method getSearchOpsSpecificCardCustomerDetails  is  "+e.getMessage());			
			}finally{
				if(connection!=null)
					try {
						super.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if(rs!=null) rs.close();
					if(pstmt!=null) pstmt.close();
				}
		
			return arrCustomer;
	}

	public List<CardDetails> getSpecificTokenizedCards(String customerRelNo, String tokenId) throws Exception {
		PreparedStatement pstmt=null;
		Connection connection = null;
		ResultSet rs=null;
		String query = null;
		List <CardDetails> cardsList = null;
		String userType=null;
		
		try{			
			connection = super.getConnection();	
			userType="C";
			              //   1          2        3			4
			query = "select tokenid, cardalias, cardnumber, createdon from card_tokenization_acquiring_bc  where relationshipno=?  and tokenid=?  order by createdon desc ";
		  
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, customerRelNo);
			pstmt.setString(2, tokenId);
			
			rs = (ResultSet)pstmt.executeQuery();
			
			 if(rs!=null){
				 cardsList = new ArrayList<CardDetails>();
				 	while(rs.next()){	 
				 		CardDetails m_CardDetails= new CardDetails();
				 		m_CardDetails.setTokenId( StringUtils.trim(rs.getString("tokenid"))    );
				 		m_CardDetails.setCardAlias( StringUtils.trim(rs.getString("cardalias"))  );
				 		m_CardDetails.setMaskedCardNumber(Utilities.maskCardNumber( Utilities.tripleDecryptData( StringUtils.trim(rs.getString("cardnumber")) )   ) );
				 		m_CardDetails.setCreatedOn(Utilities.getMySQLDateTimeConvertor(StringUtils.trim(rs.getString("createdon"))));

				 		cardsList.add(m_CardDetails);
				 		} 	
				 	if(cardsList !=null)
				 	NeoBankEnvironment.setComment(2,className," arrCards size is "+cardsList.size());
				 	} 
			 if(cardsList!=null)
				 if(cardsList.size()==0)
					 cardsList=null;
		}catch(Exception e){
			NeoBankEnvironment.setComment(1,className,"The exception in method getTokenizedCards  is  "+e.getMessage());
			throw new Exception ("The exception in method getTokenizedCards  is  "+e.getMessage());			
		}finally{
			if(connection!=null)
				try {
					super.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close(); if (userType!=null) userType=null;
			}
		return cardsList;
	}

}
