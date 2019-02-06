<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forgot password</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>/changePassword">
		<input type="text" name="username" id="username"
			placeholder="Enter email address"><br> <input
			type="submit" value="Verify">
	</form>
</body>
</html>