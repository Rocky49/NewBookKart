<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reset password</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>/resetPassword"
		method="post">
		<div class="container-fluid">
			<div class="form-control-col-xm-6">
				<input type="password" id="newPass" name="newPass"
					placeholder="Enter new password"><br>
			</div>
			<div class="form-control-col-xm-6">
				<input type="password" id="reEnter" name="reEnter"
					placeholder="Re-enter new password">
			</div>
			<div class="form-control-col-xm-6">
				<input type="text" id="otpPass" name="otpPass"
					placeholder="Enter OTP">
			</div>
			<div class="form-control-col-xm-6">
				<input type="submit" value="change password">
			</div>
		</div>
	</form>
</body>
</html>