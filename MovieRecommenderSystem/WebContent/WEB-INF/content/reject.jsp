<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Reject</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/css/bootstrap.min.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="header.jsp"%>
<div class="container">
    <div class="row" id="row">
        <div class="col-md-12 text-center">
            <h1>Sorry, you have no permission to access this page.</h1>
        </div>
    </div>
</div>
<%@include file="footer.jsp"%>
<script type="text/javascript">
(function (win, doc, $, undefined) {
	$(function () {
		$('#navbar').attr('class', 'navbar navbar-static-top');
		$('#row').css('marginTop', $(win).innerHeight() / 4);
		$('#row').css('marginBottom', $(win).innerHeight() / 4);
	});
})(window, document, jQuery);
</script>
</body>
</html>