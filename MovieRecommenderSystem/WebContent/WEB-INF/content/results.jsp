<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author" content="Jinke He">
<title>Search Results</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/css/normal.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/search.js"></script>
</head>
<body>
<%@include file="header.jsp"%>
<script type="text/javascript">
(function ($) {
    $('#navbar').attr('class', 'navbar navbar-static-top');
})(jQuery);
</script>
<div class="container" id="main">
    <div>
        <h3 id="results-num">No Movie Found</h3>   
        <hr style="border-top: 1px solid #9d9d9d; margin-top:0;">
    </div>
    <div class="row" id="loading">
        <div class="col-md-12 text-center">
            <img alt="loading" src="${pageContext.request.contextPath}/res/img/loading.gif">
        </div>
    </div>
    <ul class="media-list">    
    </ul>
    <nav aria-label="...">
        <ul class="pager">
            <li><a id='previous' href="#">Previous</a></li>
            <li><a id='next' href="#">Next</a></li>
        </ul>
    </nav>
</div>
<%@include file="footer.jsp"%>
</body>
</html>