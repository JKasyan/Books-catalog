<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:url var="cssURL" value="/resources/css/catalog.css" />
<link href="${cssURL}" rel="stylesheet" type="text/css">
<title>Authors</title>
</head>
<body>
	<div id="header">
		<h1>Books Catalog</h1>
	</div>
	<div id="main">
		<div id="log_out">
			<a href="javascript:logOut()">Log out</a>
		</div>
		<div id="links">
			<table>
				<tr>
					<td><a href="${contextPath}/main.html">Main page</a></td>
					<td><h2>Authors list</h2></td>
				</tr>
			</table>
		</div>
		<div id="head_of_table">
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<form action="${contextPath}/create_author.html" method="get">
					<input id="create_book" value="Create new author" type="submit">
				</form>
			</sec:authorize>
			<h1>Authors list</h1>
		</div>
		<div id="authors_table">
			<table>
				<tr>
					<td>Name</td>
					<td>Second name</td>
					<td>Actions</td>
				</tr>
				<c:forEach items="${authors}" var="author">
					<tr>
						<td>${author.name}</td>
						<td>${author.secondName}</td>
						<td><sec:authorize access="hasRole('ROLE_ADMIN')">
								<a href="${contextPath}/modify_author/${author.id}.html">Modify</a>
								<a href="${contextPath}/delete_author/${author.id}.html">Delete</a>
							</sec:authorize> <a href="${contextPath}/books_of_author/${author.id}.html">Books
								list</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<script type="text/javascript"
		src="${contextPath}/resources/js/catalog.js"></script>
	<script type="text/javascript"
		src="${contextPath}/resources/js/jquery-1.11.3.js"></script>
</body>
</html>