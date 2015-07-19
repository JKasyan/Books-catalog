<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:url var="cssURL" value="/resources/css/catalog.css" />
<link href="${cssURL}" rel="stylesheet" type="text/css">
<title>Registration</title>
<style type="text/css">
.error {
	color: 			red;
	font:			bold 120%;
}
</style>
</head>
<body>
	<div id="header">
		<h1>Books Catalog</h1>
	</div>
	<div id="main">
		<div id="links">
			<a href="${contextPath}/login.html">Login</a><br>
		</div>
		<div id="user_registration_form">
			<form:form id="create_user_form" modelAttribute="user"
				action="${contextPath}/new_user.html" method="POST">
				<table>
					<tr>
						<td>Email</td>
						<td><form:input path="email" /></td>
						<td><form:errors cssClass="error" path="email" /></td>
					</tr>
					<tr>
						<td>Password</td>
						<td><form:password path="password" /></td>
						<td><form:errors cssClass="error" path="password" /></td>
					</tr>
					<tr>
						<td><input type="submit" value="Registration"></td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
	<script type="text/javascript"
		src="${contextPath}/resources/js/jquery-1.11.3.js"></script>
	<script
		src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"
		type="text/javascript">
		
	</script>
	<script type="text/javascript">
		(function($, W, D) {
			var JQUERY4U = {};

			JQUERY4U.UTIL = {
				setupFormValidation : function() {
					//form validation rules
					$("#create_user_form")
							.validate(
									{
										rules : {
											email : {
												required : true,
												email : true
											},
											password : {
												required : true,
												minlength : 5,
												maxlength : 20
											}
										},
										messages : {
											email : {
												required : "Email is required!",
												email : "Email incorrect!"
											},
											password : {
												required : "Password is required!",
												minlength : "Password should be no more 20 characters!",
												maxlength : "Password should be at least 5 characters!"
											},
										},
										submitHandler : function(form) {
											form.submit();
										}
									});
				}
			}
			//when the dom has loaded setup form validation rules
			$(D).ready(function($) {
				JQUERY4U.UTIL.setupFormValidation();
			});
		})(jQuery, window, document);
	</script>
</body>
</html>