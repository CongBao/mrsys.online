<%@ page import="online.mrsys.movierecommender.action.base.WebConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author" content="Cong Bao">
<title>Error</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/css/header.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/bootstrap.min.js"></script>
<style type="text/css">
body {
    background: #181818;
    color: #fff;
}
</style>
</head>
<body>
<%@include file="header.jsp"%>
<div class="container">
    <div class="row" id="row">
        <div class="col-md-12 text-center">
          <s:if test="#session.intercept != null">
            <h1>${sessionScope.intercept}</h1>
          </s:if>
          <s:else>
            <h1>Sorry, there're something wrong with the server.</h1>
            <s:property value="exception.class"/>
            <s:property value="exception.message"/>
          </s:else>
        </div>
    </div>
</div>
<%@include file="footer.jsp"%>
<script type="text/javascript">
(function (win, doc, $, undefined) {
    $(function () {
    	$('#navbar').attr('class', 'navbar navbar-inverse navbar-static-top');
        $('#row').css('marginTop', $(win).innerHeight() / 3);
        $('#row').css('marginBottom', $(win).innerHeight() / 3);
    });
})(window, document, jQuery);
</script>
</body>
</html>