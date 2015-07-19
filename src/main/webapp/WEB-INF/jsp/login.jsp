<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:url var="cssURL" value="/resources/css/catalog.css" />
<link href="${cssURL}" rel="stylesheet" type="text/css">
<title>Insert title here</title>
</head>
<body>
	<a href="${contextPath}/registration.html">Registration</a>
	<form action="<c:url value='/j_spring_security_check' />" method="post"
		name="signInForm">
		<input style="margin-top: 10px" type="email"
			name="email" placeholder="Email" /><br>
		<input class="input_text"
			type="password" name="password" placeholder="Password" /><br>
		<input type="submit" value="Log in" /><br>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
</body>
</html>