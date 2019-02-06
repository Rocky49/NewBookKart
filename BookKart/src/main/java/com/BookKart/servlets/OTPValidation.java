/**
 * 
 */
package com.BookKart.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.BookKart.dao.RegisterDAO;
import com.BookKart.models.Person;

/**
 * @author rocky
 *
 */
public class OTPValidation extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public OTPValidation() {

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// creating to get otp assigned to user & user name
		HttpSession session = request.getSession();
		// object for person to get values
		Person person = null;
		// otp from session
		String otpSession = (String) session.getAttribute("otp");
		System.out.println("otp from session:" + otpSession + "\n");
		// otp entered by user
		String otpCheck = request.getParameter("otpEntered");
		System.out.println("otp from user:" + otpCheck + "\n");

		//
		person = (Person) session.getAttribute("user");
		RegisterDAO reg = new RegisterDAO();
		boolean flag = reg.registerPersonToDB(person);
		System.out.println("register check: " + flag + "\n");
		//
		PrintWriter out = response.getWriter();
		try {
			// getting person attributes from session
			
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}

		if (otpSession.equals(otpCheck) && flag == true) {

			System.out.println("Registration successful");

			out.println("<script type='text/javascript' src='https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.11.4/sweetalert2.all.js'></script>");
			out.println("<script type='text/javascript' src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>");
			out.println("<script>");
			out.println("$(document).ready(function(){");
			out.println("swal('SUCCESS','Registration Successful!! Please Login to continue.','success'); ");
			out.println("});");
			out.println("</script>");
			session.invalidate();
			RequestDispatcher rdd=request.getRequestDispatcher("/views/login.jsp");
					rdd.forward(request, response);

		} else {
			out.println("<script type='text/javascript' src='https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.11.4/sweetalert2.all.js'></script>");
			out.println("<script type='text/javascript' src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>");
			out.println("<script>");
			out.println("$(document).ready(function(){");
			out.println("swal('Error','Oops something went wrong!! Try again.','error'); ");
			out.println("});");
			out.println("</script>");

			System.out.println("some error:");
			RequestDispatcher rd=request.getRequestDispatcher("/views/index.jsp");
					rd.forward(request, response);
		}

	}
}
