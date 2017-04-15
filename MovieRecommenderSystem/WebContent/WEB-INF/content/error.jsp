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
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/bootstrap.min.js"></script>
<style type="text/css">
    .inverse-block {
        color: #fff;
        background: #6c6c6c;
    }
    #error h1 {
        margin-left: 0.8em;
    }
    #error h2 {
        font-size: 3em;
        color: #6c6c6c;
    }
    #error p {
        font-size: 1.2em;
        color: #6c6c6c;
    }
    #error table th, td {
        font-size: 1.2em;
        color: #6c6c6c;
    }
    #footer > div {
        color: #fff;
        background: #6c6c6c;
    }
</style>
</head>
<body>
<%@include file="header.jsp"%>
<div class="container" id="error">
    <div class="row inverse-block">
        <h1><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>&nbsp;&nbsp;&nbsp;Oops...</h1>
    </div>
    <div class="row" id="row">
        <div class="col-md-12 text-center">
          <s:if test="#session.intercept != null">
            <p>${sessionScope.intercept}</p>
          </s:if>
          <s:else>
            <h2>Sorry, there're something wrong with the server.</h2>
            <p>The following table contains some error messages. Please submit the error messages to the developers so they can handle them asap.</p>
            <table class="table table-striped table-bordered">
                <tr>
                    <th class="text-center">Exception Type</th>
                    <th class="text-center">Exception Message</th>
                </tr>
                <tr>
                    <td><s:property value="exception.class"/></td>
                    <td><s:property value="exception.message"/></td>
                </tr>
            </table>
          </s:else>
        </div>
    </div>
</div>
<%@include file="footer.jsp"%>
<script type="text/javascript">
(function (win, doc, $, undefined) {
    $(function () {
    	$('#navbar').attr('class', 'navbar navbar-static-top');
        $('#row').css('marginTop', '100px');
        $('#row').css('marginBottom', '100px');
    });
})(window, document, jQuery);
</script>
</body>
</html>