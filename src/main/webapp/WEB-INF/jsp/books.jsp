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
<title>Books</title>
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
					<td><h2>Books list</h2></td>
				</tr>
			</table>
		</div>
		<div>
			<form action="${contextPath}/books_search.html" method="post">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<table id="searching_book">
					<tr>
						<td><h2>Enter the title of book</h2></td>
						<td><input type="text" id="title" name="title"></td>
						<td><input type="submit" value="Search book"></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="head_of_table">
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<form action="${contextPath}/create_book.html" method="get">
					<input id="create_book" value="Create new book" type="submit">
				</form>
			</sec:authorize>
			<h1>Books list</h1>
		</div>
		<div id="books_table">
			<table>
				<tr>
					<td>Title</td>
					<td>Short description</td>
					<td>Publishing year</td>
					<td>Authors</td>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<td>Actions</td>
					</sec:authorize>
				</tr>
				<c:forEach items="${books}" var="book">
					<tr>
						<td>${book.title}</td>
						<td>${book.shortDescription}</td>
						<td>${book.datePublish}</td>
						<td><c:forEach items="${book.authors}" var="author">
								<p>${author.name}${author.secondName}</p>
								<br>
							</c:forEach></td>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<td><a href="${contextPath}/modify_book/${book.id}.html">Modify</a>
								<a href="${contextPath}/delete_book/${book.id}.html">Delete</a>
							</td>
						</sec:authorize>
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