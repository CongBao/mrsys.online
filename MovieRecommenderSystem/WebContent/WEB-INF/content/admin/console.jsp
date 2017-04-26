<%@ page import="online.mrsys.movierecommender.action.base.WebConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author" content="Zekun Wang">
<title>Console</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/res/img/icon.png"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/css/normal.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="../header.jsp"%>
<div class="container">
    <div class="row inverse-block">
        <h1><span class="glyphicon glyphicon-wrench"></span>&nbsp;&nbsp;&nbsp;Console</h1>
    </div>
    <div class="row" style="margin-top: 50px;">
        <div class="col-md-3">
            <ul class="nav nav-pills nav-stacked" role="tablist" id="tabs">
                <li role="presentation" class="active">
                    <a href="#movie" aria-controls="movie" role="tab" data-toggle="tab">
                        <span class="glyphicon glyphicon-film"></span>&nbsp;&nbsp;&nbsp;Movie Manager
                    </a>
                </li>
                <li role="presentation">
                    <a href="#user" aria-controls="user" role="tab" data-toggle="tab">
                        <span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;&nbsp;User Manager
                    </a>
                </li>
            </ul>
        </div>
        <div class="col-md-9">
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane fade in active" id="movie">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Add New Movies</h3>
                        </div>
                        <div class="panel-body">
				            <form class="form-horizontal" method="post" action="/admin/addMovie">
				                <div class="form-group">
				                    <label for="mvTitle" class="col-sm-2 control-label">Movie Title</label>
				                    <div class="col-sm-10">
				                        <input type="text" class="form-control" id="mvTitle" placeholder="title" name="movie.title" required>
				                    </div>
				                </div>
				                <div class="form-group">
				                    <label for="mvYear" class="col-sm-2 control-label">Movie Year</label>
				                    <div class="col-sm-4">
				                        <input type="number" class="form-control" id="mvYear" placeholder="year" name="movie.year" required>
				                    </div>
				                    <label for="mvImdb" class="col-sm-2 control-label">IMDB ID</label>
				                    <div class="col-sm-4">
				                        <input type="text" class="form-control" id="mvImdb" placeholder="imdb" name="movie.imdb" required>
				                    </div>
				                </div>
				                <div class="form-group">
				                    <div class="col-sm-offset-2 col-sm-10">
					                    <button type="submit" id="addMovieBtn" value="submit" class="btn btn-primary">Update</button>
	                                    <button type="reset" id="addMovieResetBtn" value="reset" class="btn btn-default">Reset</button>
	                                    <label style="color: red;">&nbsp;
	                                        <s:if test="#session.intercept_1 != null">${sessionScope.intercept_1}</s:if>
	                                        <%session.setAttribute(WebConstant.INTERCEPT_1, null);%>
	                                    </label>
				                    </div>
                                </div>
				            </form>
			            </div>
		            </div>
		            <div class="panel panel-default">
		                <div class="panel-heading">
                            <h3 class="panel-title">Delete Movies</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" method="post" action="deleteMovie">
                                <div class="form-group">
                                    <label for="mvId" class="col-sm-2 control-label">Movie ID</label>
                                    <div class="col-sm-10">
                                        <input type="number" class="form-control" id="mvId" placeholder="movie id" name="id" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" id="delMovieBtn" value="submit" class="btn btn-primary">Update</button>
                                        <button type="reset" id="delMovieResetBtn" value="reset" class="btn btn-default">Reset</button>
                                        <label style="color: red;">&nbsp;
                                            <s:if test="#session.intercept_2 != null">${sessionScope.intercept_2}</s:if>
                                            <%session.setAttribute(WebConstant.INTERCEPT_2, null);%>
                                        </label>
                                    </div>
                                </div>
                            </form>
                        </div>
		            </div>
	            </div>
	            <div role="tabpanel" class="tab-pane fade" id="user">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">User Permission Upgrade</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" method="post" action="upgradeUser">
                                <div class="form-group">
                                    <label for="userId" class="col-sm-2 control-label">User ID</label>
                                    <div class="col-sm-10">
                                        <input type="number" class="form-control" id="userId" placeholder="user id" name="id" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" id="upUserBtn" value="submit" class="btn btn-primary">Upgrade to Admin</button>
                                        <button type="reset" id="upUserResetBtn" value="reset" class="btn btn-default">Reset</button>
                                        <label style="color: red;">&nbsp;
                                            <s:if test="#session.intercept != null">${sessionScope.intercept}</s:if>
                                            <%session.setAttribute(WebConstant.INTERCEPT, null);%>
                                        </label>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../footer.jsp"%>
<script type="text/javascript">
(function (win, doc, $, undefined) {
    $(function () {
        $('#navbar').attr('class', 'navbar navbar-static-top');
    });
})(window, document, jQuery);
</script>
</body>
</html>