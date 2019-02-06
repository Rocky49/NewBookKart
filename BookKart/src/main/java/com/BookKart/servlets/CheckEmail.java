package com.BookKart.servlets;

import java.io.IOException;
/*import java.io.PrintWriter;*/
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.BookKart.dao.EmailVerificationDAO;
import com.BookKart.models.Person;

/**
 * Servlet implementation class ForgotOTP
 */
/**
 * @author rocky
 *
 */
public class CheckEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckEmail() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	/* @SuppressWarnings("restriction") */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String otp = "";
		Person person = new Person();

		person.setEmailAddress(username);
//		PrintWriter out = response.getWriter();
		try {
			HttpSession sess = request.getSession();
/*			RecoveryDAO recover = new RecoveryDAO();
			boolean flag = recover.clearPassword(person);*/
			EmailVerificationDAO email=new EmailVerificationDAO();
			boolean flag=email.verifyEmail(person);
			if (flag == true) {

				System.out.println("password cleared");
				sess.setAttribute("user", person.getEmailAddress());
				String host = "smtp.gmail.com";
				String user = "bookkartteam@gmail.com";
				String pass = "RockyKaran1442";
				String to = username;
				String from = "bookkartteam@gmail.com";
				String subject = "Your One-Time Password for BookKart";
				String randomNum = Math.random() + "".toString();
				otp = randomNum.substring(randomNum.length() - 4, randomNum.length());

				System.out.println(otp);

				String messageText = "Your OTP for recovery of your account in BookKart is: " + otp
						+ "\n Regards\n Team BookKart";
				boolean sessionDebug = false;

				Properties props = System.getProperties();

				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", host);
				props.put("mail.smtp.port", "587");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.required", "true");

				java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
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
				sess.setAttribute("otp", otp);
				request.getRequestDispatcher("/views/newPassword.jsp").forward(request, response);
			} else {

				System.out.println("wrong credentials!");
				request.getRequestDispatcher("/views/login.jsp").forward(request, response);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

}
