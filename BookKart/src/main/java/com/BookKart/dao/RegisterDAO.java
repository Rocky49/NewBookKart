/**
 * 
 */
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
public class RegisterDAO {
	public boolean flag = false;

	/**
	 * 
	 */
	public RegisterDAO() {

	}

	public boolean registerPersonToDB(Person person) {

		// Creating Person object to get values from servlet

		// intializing value using getter
		String fName = person.getFirstName();
		String lName = person.getLastName();
		String eMail = person.getEmailAddress();
		String password = person.getPassword();
		long contact = person.getContact();
		String role=person.getRole();

		Connection conn = null;

		PreparedStatement prep = null;

		try {
			// creating object of DBConnection to load all the JDBC Drivers
			DBConnection dbc = new DBConnection();
			// insert query
			conn = dbc.createConnection();
			String insertQuery = "INSERT INTO `person` (`first_name`, `last_name`, `email_address`, `password_base64`, `contact`, `role`) VALUES (?, ?, ?, ?, ?,?);";
			// inserting query
			prep = conn.prepareStatement(insertQuery);
			// setting values
			prep.setString(1, fName);
			prep.setString(2, lName);
			prep.setString(3, eMail);
			prep.setString(4, password);
			prep.setLong(5, contact);
			prep.setString(6, role);
			// exceution
			int result = prep.executeUpdate();
			if (result== 0) {

				return flag = false;

			} else {

				return flag = true;

			}
		} catch (SQLException e) {

			System.out.print("error: " + e.getMessage());

		}
		return flag;

	}

}
