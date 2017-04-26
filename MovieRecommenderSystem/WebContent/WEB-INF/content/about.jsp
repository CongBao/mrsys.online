<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author" content="Zekun Wang">
<title>About</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/res/img/icon.png"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/css/normal.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/bootstrap.min.js"></script>
<style type="text/css">
    #about {
        font-size: 1.2em;
        color: #6c6c6c;
    }
    #about h2 {
        margin-left: -1.8em;
    }
</style>
</head>
<body>
<%@include file="header.jsp"%>
<div class="container" id="about">
    <div class="row">
        <div class="col-md-12">
            <div class="row inverse-block">
                <h1><span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>&nbsp;&nbsp;&nbsp;About</h1>
            </div>
            <div class="row">
                <div class="col-md-10 col-md-offset-1">
	                <h2><span class="glyphicon glyphicon-user" aria-hidden="true"></span>&nbsp;&nbsp;&nbsp;About our team</h2>
	                <p>We are COMP214 team1 </p>
	                <p>Team member:Cong Bao, Jinke He, Yuan Zhu, Zekun Wang, Xiang Li, Hongchi Chen</p>
                </div>
            </div>
            <div class="row">
                <div class="col-md-10 col-md-offset-1">
	                <h2><span class=" glyphicon glyphicon-cog " aria-hidden="true"></span>&nbsp;&nbsp;&nbsp;Functions of this website</h2>
	                <p>This website will supply a movie recommendation function for its user. For each registered user, the website will invite him or her to rate movies that shown on the website. After a registered user has rated at least 10 movies, the website will analyze the preferences of the user and provide a recommended movie list for the user. The website also supply a social function for registered users. Registered users will be provided with a list of recommended friends who share the same preferences about movies. The user can follow recommended friends whom he or she is interested in. </p>
                </div>
            </div>
            <div class="row">
                <div class="col-md-10 col-md-offset-1">
                    <h2><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>&nbsp;&nbsp;&nbsp;Use of "cookies"</h2>
                    <h3>What are cookies?</h3>
                    <p>Cookies can regarded as your ID that has put on the server. The Information Commissioner’s Office (ICO) defines a cookie as “a small file of letters and numbers that is downloaded on to your computer when you visit a website. Cookies are used by many websites and can do a number of things e.g. remembering your preferences, recording what you have put in your shopping basket, and counting the number of people looking at a website.”</p>
                    <h3>Why do we use cookies?</h3>
                    <p>We will use cookies to serve you faster and better. The cookies will make your experiences of visiting our website more personalized. With the help of cookies, our website will ensure the path of visiting and record your activities on the website to make the evaluations and improvements for our website. Cookies can help us provide a better service for you.</p>
                </div>
            </div>
            <div class="row">
                <div class="col-md-10 col-md-offset-1">
                    <h2><span class="glyphicon glyphicon-lock" aria-hidden="true"></span>&nbsp;&nbsp;&nbsp;Personal information secrecy</h2>
                    <p>All the personal information that you have provided for the website will only be used by our website. We will try to keep your personal information safe, and we will not share your personal information with the third party without your permission.</p>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp"%>
<script type="text/javascript">
(function (win, doc, $, undefined) {
    $(function () {
        $('#navbar').attr('class', 'navbar navbar-static-top');
    });
})(window, document, jQuery);
</script>
</body>
</html>