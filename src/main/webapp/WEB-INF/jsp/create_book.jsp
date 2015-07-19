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
<title>Create new book</title>
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
					<td><a href="${contextPath}/books.html">Books list</a></td>
					<td><h2>Modification of the book</h2></td>
				</tr>
			</table>
		</div>
		<div>
			<form:form  id="create_book_form" commandName="book" action="${contextPath}/new_book.html" method="post">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<div>
					<table class="tables">
						<tr>
							<td><h2>Fill in the required fields and push enter</h2></td>
							<td><input id="create_book" type="submit" value="Create new book"></td>
						</tr>
					</table>
				</div>
				<table class="tables">
					<tr>
						<td><h3>Title</h3></td>
						<td><form:input path="title" maxlength="45" /></td>
						<td><form:errors cssClass="error" path="title"/></td>
					</tr>
					<tr>
						<td><h3>Short description</h3></td>
						<td><form:textarea path="shortDescription" maxlength="45" /></td>
						<td><form:errors cssClass="error" path="shortDescription"/></td>
					</tr>
					<tr>
						<td><h3>Publishing date</h3></td>
						<td><form:input path="datePublish" maxlength="4" /></td>
						<td><form:errors cssClass="error" path="datePublish"/></td>
					</tr>
					<tr>
						<td><h3>Authors</h3></td>
						<td><form:select path="authors">
								<c:forEach items="${authors}" var="author" varStatus="status">
									<form:option value="${author.name} ${author.secondName}" />
								</c:forEach>
						</form:select></td>
						<td><form:errors cssClass="error" path="authors"/></td>
					</tr>
				</table>
			</form:form>
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
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js" type="text/javascript">
	</script>
	
	<script type="text/javascript">
	(function($,W,D)
			{
			    var JQUERY4U = {};
			 
			    JQUERY4U.UTIL =
			    {
			        setupFormValidation: function()
			        {
			            //form validation rules
			            $("#create_book_form").validate({
			                rules: {
			                	title: "required",
			                	shortDescription: {
			                		required: true,
			                		minlength: 10,
			                		maxlength: 45
			                	},
			                	datePublish: {
			                		required: true,
			                	    number: true
			                	},
			                	authors: "required"
			                },
			                messages: {
			                	title: "Please enter title of book",
			                	shortDescription: {
			                		required: "Please enter description of book",
			                		minlength: "You must write at least 10 characters",
			                		maxlength: "You can not write more than 45 characters"
			                	},
			                	datePublish: {
			                		requird: "Please enter date of book",
			                		number: "The date must contain only numbers"
			                	}, 
			                	authors: "You must choose at least one author"
			                },
			                submitHandler: function(form) {
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