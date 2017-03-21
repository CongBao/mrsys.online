<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Error</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/css/bootstrap.min.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/bootstrap.min.js"></script>
</head>
<body>
	<header><%@include file="header.jsp"%></header>
	<div class="container">
		<div class="row">
			<div class="col-md-12 text-center">
				<h1>Error Page</h1>
				<table class="table table-striped table-bordered">
					<tr>
						<th class="text-center">Exception Type</th>
						<th class="text-center">Exception Message</th>
					</tr>
					<tr>
						<td><s:property value="exception.class" /></td>
						<td><s:property value="exception.message" /></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<footer><%@include file="footer.jsp"%></footer>
</body>
</html>