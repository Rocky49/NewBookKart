 /**
 * 
 */
package com.BookKart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.DatatypeConverter;

import com.BookKart.models.DBConnection;
import com.BookKart.models.LoginModel;

/**
 * @author rocky
 *
 */
public class LoginDAO {

	public int checkLogin(LoginModel values) {
		// boolean to return result
		// boolean check=false;
		int check = 0;
		// getting values from login.jsp
		String user = values.getUserName();
		String password = values.getPassword();
		// query for checking
		String query = "select email_address,password_base64,role from person where email_address=?";
		// creating object for connection to db
		DBConnection db = new DBConnection();
		Connection conn = null;
		try {
			conn = db.createConnection();
			// prepared statement
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, user);
			ResultSet rs = ps.executeQuery();
			String emailCheck = "";
			String passCheck = "";
			String role = "";
			final String admin = "Admin";
			final String vendor = "Vendor";
			final String customer = "Customer";
			while (rs.next()) {
				emailCheck = rs.getString("email_address");
				passCheck = rs.getString("password_base64");
				role = rs.getString("role");
			}
			byte[] decoded = DatatypeConverter.parseBase64Binary(passCheck);
			String decrypt = new String(decoded);

			System.out.println(decoded);
			if (user.equalsIgnoreCase(emailCheck) && password.equalsIgnoreCase(decrypt)
					&& admin.equalsIgnoreCase(role)) {
				check = 1;

			} else {

				if (user.equalsIgnoreCase(emailCheck) && password.equalsIgnoreCase(decrypt)
						&& vendor.equalsIgnoreCase(role)) {
					check = 2;
				} else {
					if (user.equalsIgnoreCase(emailCheck) && password.equalsIgnoreCase(decrypt)
							&& customer.equalsIgnoreCase(role)) {
						check = 3;
					} else {
						check = 0;
					}
				}
			}
		} catch (SQLException s) {

			System.out.println("Error:" + s.getMessage());
		}
		return check;

	}

}
