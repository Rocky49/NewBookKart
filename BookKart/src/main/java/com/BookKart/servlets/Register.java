package com.BookKart.servlets;

import java.io.IOException;
import java.io.PrintWriter;
//import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

//import javax.mail.Authenticator;
import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import com.BookKart.dao.EmailVerificationDAO;
import com.BookKart.models.Person;
//import com.sun.mail.util.MailSSLSocketFactory;

/**
 * Servlet implementation class Register
 */
/**
 * @author rocky
 *
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public Register() {

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// get attributes from jsp
		String choice=request.getParameter("user");
		System.out.println(choice);
		String firstName = request.getParameter("fName");
		String lastName = request.getParameter("lName");
		String emailAddress = request.getParameter("email");
		String pwd = request.getParameter("password");
		long contact = Long.parseLong(request.getParameter("contact"));
		// encryption password
		byte[] password = pwd.getBytes("UTF-8");
		String encoded = DatatypeConverter.printBase64Binary(password);
		System.out.println("Encoded message: " + encoded);

		Person person = new Person();

		// set attributes
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setEmailAddress(emailAddress);
		person.setPassword(encoded);
		person.setContact(contact);
		person.setRole(choice);

		PrintWriter out = response.getWriter();
		// Email verification
		EmailVerificationDAO edao = new EmailVerificationDAO();
		boolean check = edao.verifyEmail(person);

		// creating session to get attributes
		HttpSession sess = request.getSession();
		String otp = "";

		if (check == true) {
			System.out.println("email already registered");
			out.println("<script src='https://unpkg.com/sweetalert/dist/sweetalert.min.js'></script>");
			out.println("<script type='text/javascript' src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>");
			out.println("<script>");
			out.println("swal('User already registered!');");
			out.println("</script>");
			RequestDispatcher rd = request
					.getRequestDispatcher("views/login.jsp");
			rd.forward(request, response);

		} else {
			sess.setAttribute("user", person);
			try {
				String host = "smtp.gmail.com";
				String user = "bookkartteam@gmail.com";
				String pass = "RockyKaran1442";
				String to = emailAddress;
				String from = "bookkartteam@gmail.com";
				String subject = "Your One-Time Password for BookKart";
				String randomNum = Math.random() + "".toString();
				otp = randomNum.substring(randomNum.length() - 4,
						randomNum.length());
				
				System.out.println(otp);

				String messageText = "Your OTP for registration in BookKart is:"
						+ otp + "\n Regards\n Team BookKart";
				boolean sessionDebug = false;

				Properties props = System.getProperties();

				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", host);
				props.put("mail.smtp.port", "587");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.required", "true");

				java.security.Security
						.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
				Session mailSession = Session.getDefaultInstance(props, null);
				mailSession.setDebug(sessionDebug);
				Message msg = new MimeMessage(mailSession);
				msg.setFrom(new InternetAddress(from));
				InternetAddress[] address = { new InternetAddress(to) };
				msg.setRecipients(Message.RecipientType.TO, address);
				msg.setSubject(subject);
				msg.setSentDate(new Date());
				msg.setText(messageText);

				Transport transport = mailSession.getTransport("smtp");
				transport.connect(host, user, pass);
				transport.sendMessage(msg, msg.getAllRecipients());
				transport.close();
				System.out.println("message send successfully");
			} catch (Exception ex) {
				System.out.println(ex);
			}

			sess.setAttribute("otp", otp);
			request.getRequestDispatcher("views/verifyOTP.jsp").include(request,
					response);

		}
	}
}
