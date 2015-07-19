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
		<div id="log_out">
			<a href="javascript:logOut()">Log out</a>
		</div>
		<div id="links">
			<table>
				<tr>
					<td><a href="${contextPath}/main.html">Main page</a></td>
					<td><a href="${contextPath}/authors.html">Authors list</a></td>
					<td><h2>Modification of the author</h2></td>
				</tr>
			</table>
		</div>
		<div>
			<form:form modelAttribute="author" id="modify_author_form"
				action="${contextPath}/modify_author/update_author.html" method="post">
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
						<td><h3>Name</h3></td>
						<td><form:input path="name" maxlength="45" /></td>
						<td><form:errors cssClass="error" path="name" /></td>
					</tr>
					<tr>
						<td><h3>Second name</h3></td>
						<td><form:input path="secondName" maxlength="45" /></td>
						<td><form:errors cssClass="error" path="secondName" /></td>
					</tr>
				</table>
				<form:hidden path="id"/>
			</form:form>
		</div>
	</div>
	<script type="text/javascript"
		src="${contextPath}/resources/js/catalog.js"></script>
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
					$("#modify_author_form").validate({
						rules : {
							name : "required",
							secondName : "required",
						},
						messages : {
							name : "Please enter name of author",
							secondName : "Please enter second name of name",
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