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
<meta name="author" content="Jinke He">
<title>${sessionScope.user.account}</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/css/normal.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/profile.js"></script>
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
</head>
<body>
<%@include file="../header.jsp"%>
<div class="container">
    <div class="row inverse-block">
        <div class="col-md-12">
            <h1><span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;&nbsp;Personal Page</h1>
        </div>
    </div>
    <div class="row" style="margin-top: 50px;">
        <div class="col-md-3">
            <ul class="nav nav-pills nav-stacked" role="tablist" id="tabs">
                <li role="presentation" class="active">
                    <a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">
                        <span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;&nbsp;Profile
                    </a>
                </li>
                <li role="presentation">
                    <a href="#favorite" aria-controls="favorite" role="tab" data-toggle="tab">
                        <span class="glyphicon glyphicon-heart"></span>&nbsp;&nbsp;&nbsp;Favourites
                    </a>
                </li>
                <li role="presentation">
                    <a href="#recommend" aria-controls="recommend" role="tab" data-toggle="tab">
                        <span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;&nbsp;Recommendation List
                    </a>
                </li>
                <li role="presentation">
                    <a href="#setting" aria-controls="setting" role="tab" data-toggle="tab">
                        <span class="glyphicon glyphicon-cog"></span>&nbsp;&nbsp;&nbsp;Settings
                    </a>
                </li>
            </ul>
        </div>
        <div class="col-md-9">
            <div class="tab-content">
	            <div role="tabpanel" class="tab-pane fade in active" id="profile">
	                <div class="panel panel-default">
	                    <div class="panel-heading">
	                        <h3 class="panel-title">Account Information</h3>
	                    </div>
	                    <div class="panel-body">
	                        <table class="table table-hover">
	                            <tbody>
	                                <tr>
	                                    <th>Unique ID Number</th>
	                                    <td>${sessionScope.user.id}</td>
	                                </tr>
	                                <tr>
	                                    <th>Account</th>
	                                    <td>${sessionScope.user.account}</td>
	                                </tr>
	                                <tr>
	                                    <th>Email</th>
	                                    <td>${sessionScope.user.email}</td>
	                                </tr>
	                                <tr>
	                                    <th>Email Verification State</th>
	                                    <td>
	                                      <s:if test="#session.user.mailVerified">Verified</s:if>
	                                      <s:else>
	                                        Not Verified
	                                        <a id="mail_verify" href="" class="btn btn-default btn-xs" onclick="alert('This function is unavailable now');">Verify Now</a>
	                                      </s:else>
	                                    </td>
	                                </tr>
	                            </tbody>
	                        </table>
	                    </div>
	                </div>
	            </div>
	            <div role="tabpanel" class="tab-pane fade" id="favorite">
	              <s:if test="#session.favorites != null">
	                  <s:iterator value="#session.favorites" id="fav">
	                    <div class="panel panel-default" id="fav<s:property value="#fav.movie.imdb"/>">
	                        <div class="panel-heading">
                                <h3 class="panel-title" id="favtitle<s:property value="#fav.movie.imdb"/>">N/A</h3>
                            </div>
                            <div class="panel-body">
                                <div class="media">
                                    <div class="media-left">
                                        <img id="favimg<s:property value="#fav.movie.imdb"/>" alt="N/A" src="N/A">
                                    </div>
                                    <div class="media-body">
                                        <h4 class="media-heading" id="favinfo<s:property value="#fav.movie.imdb"/>">N/A</h4>
                                        <p id="favplot<s:property value="#fav.movie.imdb"/>">N/A</p>
                                        <a class="btn btn-default" href="/movie/<s:property value="#fav.movie.id"/>">Learn more »</a>
                                    </div>
                                </div>
                            </div>
	                    </div>
	                  </s:iterator>
	              </s:if>
	              <s:else>
	                <h2 class="text-center">There are no movies in your favourites</h2>
	              </s:else>
	            </div>
	            <div role="tabpanel" class="tab-pane fade" id="recommend">
	              <s:if test="#session.recommendations != null">
		              <s:iterator value="#session.recommendations" id="rec">
		                <div class="panel panel-default" id="rec<s:property value="#rec.imdb"/>">
		                    <div class="panel-heading">
		                        <h3 class="panel-title" id="rectitle<s:property value="#rec.imdb"/>">N/A</h3>
		                    </div>
		                    <div class="panel-body">
		                        <div class="media">
		                            <div class="media-left">
		                                <img id="recimg<s:property value="#rec.imdb"/>" alt="N/A" src="N/A">
		                            </div>
		                            <div class="media-body">
		                                <h4 class="media-heading" id="recinfo<s:property value="#rec.imdb"/>">N/A</h4>
		                                <p id="recplot<s:property value="#rec.imdb"/>">N/A</p>
		                                <a class="btn btn-default" href="/movie/<s:property value="#rec.id"/>">Learn more »</a>
		                            </div>
		                        </div>
		                    </div>
		                </div>
		              </s:iterator>
		          </s:if>
		          <s:else>
		              <h2 class="text-center">Sorry, recommendation list is not available now.</h2>
		          </s:else>
	            </div>
	            <div role="tabpanel" class="tab-pane fade" id="setting">
	              <s:if test="#session.user.role.id == 1">
	                <div class="panel panel-default">
	                    <div class="panel-heading">
	                        <h3 class="panel-title">Administrator</h3>
	                    </div>
	                    <div class="panel-body">
	                        <a href="/admin/console" type="button" class="btn btn-default btn-lg btn-block">Open Console</a>
	                    </div>
	                </div>
	              </s:if>
	                <div class="panel panel-default">
	                    <div class="panel-heading">
	                        <h3 class="panel-title">Change Email</h3>
	                    </div>
	                    <div class="panel-body">
	                        <form method="post" id="editEmailForm" action="editEmail">
	                            <input type="hidden" id="userId" name="id" value="${sessionScope.user.id}">
	                            <div class="form-group">
	                                <label for="oldEmail">Current Email</label>
	                                <input type="email" class="form-control" id="oldEmail" placeholder="${sessionScope.user.email}" readonly="readonly">
	                            </div>
	                            <div class="form-group" id="editEmailGroup">
	                                <label for="newEmail">New Email</label>
	                                <input type="email" class="form-control" id="newEmail" placeholder="New Email" name="newEmail">
	                            </div>
	                            <div class="form-group text-right">
	                                <label style="color: red;">
	                                    <s:if test="#session.intercept_1 != null">${sessionScope.intercept_1}</s:if>
	                                    <%session.setAttribute(WebConstant.INTERCEPT_1, null);%>&nbsp;
	                                </label>
	                                <button type="reset" id="resetEmailBtn" value="reset" class="btn btn-default">Reset</button>
	                                <button type="submit" id="submitEmailBtn" value="submit" class="btn btn-primary" disabled="disabled">Update</button>
	                            </div>
	                        </form>
	                    </div>
	                </div>
	                <div class="panel panel-default">
	                    <div class="panel-heading">
	                        <h3 class="panel-title">Change Password</h3>
	                    </div>
	                    <div class="panel-body">
	                        <form method="post" id="editPassForm" action="editPassword">
	                            <input type="hidden" name="user.account" value="${sessionScope.user.account}">
	                            <div class="form-group" id="oldPassGroup">
	                                <label for="oldPassword">Current Password</label>
	                                <input type="password" class="form-control" id="oldPassword" placeholder="Current Password" name="user.password">
	                            </div>
	                            <div class="form-group" id="newPassGroup">
	                                <label for="editPassword">New Password</label>
	                                <input type="password" class="form-control" id="newPassword" placeholder="New Password" name="newPassword">
	                            </div>
	                            <div class="form-group" id="confirmGroup">
	                                <label for="confirmPass">Confirm New Password</label>
	                                <input type="password" class="form-control" id="confirmPass" placeholder="Confirm New Password">
	                            </div>
	                            <div class="form-group text-right">
	                                <label style="color: red;">
	                                    <s:if test="#session.intercept_2 != null">${sessionScope.intercept_2}</s:if>
	                                    <%session.setAttribute(WebConstant.INTERCEPT_2, null);%>&nbsp;
	                                </label>
	                                <button type="reset" id="resetPasswordBtn" value="reset" class="btn btn-default">Reset</button>
	                                <button type="submit" id="submitPasswordBtn" value="submit" class="btn btn-primary" disabled="disabled">Update</button>
	                            </div>
	                        </form>
	                    </div>
	                </div>
	                <div class="panel panel-default">
	                    <div class="panel-heading">
	                        <h3 class="panel-title">Delete Account</h3>
	                    </div>
	                    <div class="panel-body">
	                        <form method="post" action="deleteAccount">
	                            <input type="hidden" name="id" value="${sessionScope.user.id}">
	                            <label>Confirm</label>
	                            <div class="checkbox">
	                                <label><input type="checkbox" id="agreement">I'm sure I want to delete my account.</label>
	                            </div>
	                            <div style="font-style: italic;">
	                                <label>Note</label>
	                                <p>Once you delete your account, there is no going back. Please be certain.</p>
	                            </div>
	                            <button type="submit" id="deleteAccount" value="delete" class="btn btn-danger btn-lg btn-block" disabled="disabled">Delete Account</button>
	                        </form>
	                    </div>
	                </div>
	            </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../footer.jsp"%>
</body>
</html>