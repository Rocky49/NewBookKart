package com.BookKart.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import com.BookKart.dao.LoginDAO;
import com.BookKart.models.LoginModel;;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();

	}

	int status = 0;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	public LoginModel checkCookie(HttpServletRequest request) {
		LoginModel user = null;

		String email = "";
		String pass = "";
		Cookie[] cookie = request.getCookies();
		if (cookie == null) {
			return user;
		} else {
			for (Cookie ck : cookie) {
				if (ck.getName().equalsIgnoreCase("emailAddress")) {
					email = ck.getValue();
				}
				if (ck.getName().equalsIgnoreCase("password")) {
					pass = ck.getValue();
				}
			}

			if (!email.isEmpty() && !pass.isEmpty()) {

				user = new LoginModel(email, pass);

			}
		}

		return user;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		HttpSession sess = request.getSession();
		if (action == null) {
			LoginModel loginCheck = checkCookie(request);
			if (loginCheck == null) {
				request.getRequestDispatcher("views/login.jsp").include(request, response);
			} else {
				LoginDAO dao = new LoginDAO();
				status = dao.checkLogin(loginCheck);
				if (status == 3) {
					sess.setAttribute("user", loginCheck.getUserName());
					request.getRequestDispatcher("views/welcome.jsp").forward(request, response);
				}

				if (status == 2) {
					sess.setAttribute("user", loginCheck.getUserName());
					request.getRequestDispatcher("views/vendor.jsp").forward(request, response);
				}

				if (status == 1) {
					sess.setAttribute("user", loginCheck.getUserName());
					request.getRequestDispatcher("views/admin.jsp").forward(request, response);
				}
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("user");
		String password = request.getParameter("passwd");
		boolean rememberMe = request.getParameter("rememberMe") != null;

		System.out.println("remeber me : " + rememberMe);

		// using encoded password for storing in cookie
		byte[] pwd = password.getBytes("UTF-8");
		String encoded = DatatypeConverter.printBase64Binary(pwd);
		System.out.println("Encoded message: " + encoded);

		HttpSession session = request.getSession();

		PrintWriter out = response.getWriter();

		LoginModel login = new LoginModel(username, password);

		login.setUserName(username);
		login.setPassword(password);

		LoginDAO dao = new LoginDAO();

		status = dao.checkLogin(login);
		System.out.println(status);
		if (status == 3) {
			// for keeping the user in session
			session.setAttribute("username", username);
			request.getRequestDispatcher("/views/welcomeUser.jsp").include(request, response);

		} else {

			if (status == 2) {
				session.setAttribute("username", username);
				request.getRequestDispatcher("views/vendor.jsp").include(request, response);
			} else {

				if (status == 1) {
					session.setAttribute("username", username);
					request.getRequestDispatcher("views/admin.jsp").include(request, response);
				}

				else {
					out.println("<script src='https://unpkg.com/sweetalert/dist/sweetalert.min.js'></script>");
					out.println(
							"<script type='text/javascript' src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>");
					out.println("<script>");
					out.println("swal ( 'Oops' ,  'Something went wrong!' ,  'error' )");
					out.println("</script>");
					request.getRequestDispatcher("/views/login.jsp").include(request, response);
				}
			}
		}
		if (status == 1 || status == 2 || status == 3) {
			if (rememberMe == true) {
				// if user checks on remember me his login details will be
				// stored in cookie
				Cookie cookieMail = new Cookie("emailAddress", username);
				response.addCookie(cookieMail);
				cookieMail.setMaxAge(6000);

				Cookie cookiePass = new Cookie("password", encoded);
				response.addCookie(cookiePass);
				cookiePass.setMaxAge(6000);
			}
		}
	}
}
