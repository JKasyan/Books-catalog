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
					$("#create_book_form").validate({
						rules : {
							title : "required",
							desc : "required",
							date : {
								required : true,
								number : true
							},
							authors : "required"
						},
						messages : {
							title : "Please enter title of book",
							desc : "Please enter description of book",
							date : {
								requird : "Please enter date of book",
								number : "The date must contain only numbers"
							},
							authors : "You must choose at least one author"
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