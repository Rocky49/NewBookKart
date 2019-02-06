 package com.BookKart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.BookKart.models.DBConnection;
import com.BookKart.models.Person;

/**
 * @author rocky
 *
 */
public class RecoveryDAO {

	public RecoveryDAO() {
	}

/*	public boolean clearPassword(Person person) throws SQLException {

		boolean empty = false;
		String email = person.getEmailAddress();
		// query to empty password block
		String query = "UPDATE `person` SET `password_base64` = '' WHERE `person`.`email_address` = ?";
		DBConnection db = new DBConnection();
		Connection conn = null;
		conn = db.createConnection();
		PreparedStatement prep = conn.prepareStatement(query);
		prep.setString(1, email);
		int result = prep.executeUpdate();
		if (result == 0) {
			//if password is not set null i.e. email entered does not exist in db
			empty = false;
		} else {
			//if password is set null
			empty = true;
		}
		return empty;

	}
*/
	public boolean resetPassword(Person person,String email) throws SQLException{

		boolean reset=false;
		String pass=person.getPassword();
		System.out.println(pass);
		String emailID=email;
		System.out.println(emailID);
		DBConnection db=new DBConnection();
		Connection conn=null;
		conn=db.createConnection();
		String query="UPDATE `person` SET `password_base64` = ? WHERE `person`.`email_address` = ?";
		PreparedStatement ps=conn.prepareStatement(query);
		ps.setString(1, pass);
		ps.setString(2, emailID);
		int res=ps.executeUpdate();
		if(res!=0){
			reset=true;
		}
		return reset;
	}

}
