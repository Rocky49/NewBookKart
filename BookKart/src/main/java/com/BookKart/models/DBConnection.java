/**
 * 
 */
package com.BookKart.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 * @author rocky
 *
 */
public class DBConnection {
	
	
	/**
	 * 
	 */
	public DBConnection() {
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException c){
			
			System.out.println("error: "+c);
		}
		
	}
	
	public Connection createConnection() {
		Connection conn=null;
		String url="jdbc:mysql://localhost:3306/bookkart";
		String user="root";
		String passwd="";
		
			
				
			try{
				conn= DriverManager.getConnection(url,user,passwd);
			}
			catch(SQLException s){
				System.out.println("error: "+s.toString());
		}
			if(conn==null){
				System.out.println("Connection not established");
			}
		return conn;
	}

}
