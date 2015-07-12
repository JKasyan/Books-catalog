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
			<form id="create_book_form" action="${contextPath}/new_book.html" method="post">
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
						<td><input id="title_book" maxlength="45" name="title"/><h4 id="title_counter"></h4></td>
						<td><h2 id="title_error"></h2></td>
					</tr>
					<tr>
						<td><h3>Short description</h3></td>
						<td><textarea id="desc" maxlength="45" name="desc" rows="3" cols=""></textarea></td>
						<td><h2 id="desc_error"></h2></td>
					</tr>
					<tr>
						<td><h3>Publishing date</h3></td>
						<td><input id="date" maxlength="4" name="date" /></td>
						<td><h2 id="date_error"></h2></td>
					</tr>
					<tr>
						<td><h3>Authors</h3></td>
						<td><select id="authors" name="authors" multiple="multiple">
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
			                	desc: {
			                		required: true,
			                		minlength: 10,
			                		maxlength: 45
			                	},
			                	date: {
			                		required: true,
			                	    number: true
			                	},
			                	authors: "required"
			                },
			                messages: {
			                	title: "Please enter title of book",
			                	desc: {
			                		required: "Please enter description of book",
			                		minlength: "You must write at least 10 characters",
			                		maxlength: "You can not write more than 45 characters"
			                	},
			                	date: {
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