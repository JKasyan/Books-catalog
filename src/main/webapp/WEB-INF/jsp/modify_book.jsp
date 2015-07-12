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
<title>Insert title here</title>
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
					<td><a href="${contextPath}/all_books.html">Books list</a></td>
					<td><h2>Modification of the book</h2></td>
				</tr>
			</table>
		</div>
		<div>
			<form action="${contextPath}/modify_book/change_book.html" method="post">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<div>
				<input name="id_book" type="hidden" value="${book.id}">
					<table class="tables">
						<tr>
							<td><h2>Fill in the required fields and push enter</h2></td>
							<td><input type="submit" value="Submit"></td>
						</tr>
					</table>
				</div>
				<table class="tables">
					<tr>
						<td><h3>Title</h3></td>
						<td><input maxlength="45" name="title" value="${book.title}" /></td>
					</tr>
					<tr>
						<td><h3>Short description</h3></td>
						<td><textarea maxlength="45" name="desc" rows="3" cols="">${book.shortDescription}</textarea></td>
					</tr>
					<tr>
						<td><h3>Publishing date</h3></td>
						<td><input maxlength="4" name="date" value="${book.datePublish}" /></td>
					</tr>
					<tr>
						<td><h3>Authors</h3></td>
						<td><select name="authors" multiple="multiple">
								<c:forEach items="${authors}" var="author">
									<option value="${author.id}">${author.name} ${author.secondName}</option>
								</c:forEach>
						</select></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<script type="text/javascript"
		src="${contextPath}/resources/js/catalog.js"></script>
</body>
</html>