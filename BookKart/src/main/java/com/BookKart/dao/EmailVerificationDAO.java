 /**
 * 
 */
package com.BookKart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




import com.BookKart.models.DBConnection;
import com.BookKart.models.Person;

/**
 * @author rocky
 *
 */
public class EmailVerificationDAO {

	/**
	 * 
	 */
	public EmailVerificationDAO() {

	}

	public boolean verifyEmail(Person person) {
		boolean check = false;
		String emailAddress = person.getEmailAddress();
		System.out.print(emailAddress);
		DBConnection db = new DBConnection();
		Connection conn = null;
		String query = "select email_address from person where email_address=?;";
		PreparedStatement prep = null;

		try {
			conn = db.createConnection();
			prep = conn.prepareStatement(query);
			prep.setString(1, emailAddress);
			//int ch = prep.executeUpdate();
			ResultSet ch=prep.executeQuery();
			if (!ch.next()) {
				check = false;
				System.out.print("Email not registered");
			} else {
				check = true;
			}

		} catch (SQLException e) {
			System.out.print("error" + e);
		}
		return check;

	}

}
