<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>/register" method="post">
		<strong>You want to register as?</strong><br> <input type="radio"
			id="customer" name="user" value="Customer" checked>Customer <br>
		<input type="radio" id="vendor" name="user" value="Vendor">Wholesaler
		<br> <input type="text" id="fName" name="fName"
			placeholder="First name" required><br> <input
			type="text" id="lName" name="lName" placeholder="Last name" required><br>
		<input type="text" id="email" name="email" placeholder="Email address"
			required><br> <input type="password" id="password"
			name="password" placeholder="Password" required><br> <input
			type="text" id="contact" name="contact" placeholder="Contact"
			required><br> <input type="submit" id="register"
			value="Register">
	</form>
</body>
</html>