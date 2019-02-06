package com.BookKart.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import com.BookKart.dao.RecoveryDAO;
import com.BookKart.models.Person;

/**
 * Servlet implementation class PasswordRecovery
 */

/**
 * @author rocky
 *
 */
public class PasswordRecovery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordRecovery() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String password=request.getParameter("newPass");
		String otpUser=request.getParameter("otpPass");
		System.out.println("user otp:"+otpUser);
		HttpSession session=request.getSession();
		String getOTP=(String)session.getAttribute("otp");
		String email=(String)session.getAttribute("user");
		System.out.println("email"+email);
		System.out.println("otp from system:"+getOTP);
		byte[] pwd = password.getBytes("UTF-8");
		String encoded = DatatypeConverter.printBase64Binary(pwd);
		System.out.println("Encoded message: " + encoded);
		if(otpUser.equalsIgnoreCase(getOTP)){
			
		Person person=new Person();
		person.setPassword(encoded);
		RecoveryDAO recover=new RecoveryDAO();
		try {
			boolean reset=recover.resetPassword(person,email);
			if(reset==true){
				System.out.println("password updated successfully");
				request.getRequestDispatcher("views/login.jsp").forward(request, response);
			}
			else{
				System.out.println("password not changed");
				request.getRequestDispatcher("views/forgotPassword.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			System.out.println("error:"+e.getMessage());
		}
		}
	}

}
