<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/jquery.md5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/header.js"></script>
<nav class="navbar navbar-inverse navbar-fixed-top" id="navbar">
    <div class="container" id="navbar-container">
        <div class="navbar-header">
            <button type="button"
                    class="navbar-toggle collapsed"
                    data-toggle="collapse"
                    data-target="#bs-navbar-collapse"
                    aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/index">MRSYS.ONLINE</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
              <s:if test="#session.user != null">
                <li><p class="navbar-text"><b>Welcome, ${sessionScope.user.account}</b></p></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        <span class="glyphicon glyphicon-user"></span>
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li class="dropdown-header">Signed in as <b>${sessionScope.user.account}</b></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="/user/${sessionScope.user.account}#profile"><span class="glyphicon glyphicon-book"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;View Your Profile</a></li>
                        <li><a href="/user/${sessionScope.user.account}#setting"><span class="glyphicon glyphicon-cog"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;User Settings</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="/logout" style="color: #F00; font-weight: bold;"><span class="glyphicon glyphicon-off"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sign Out</a></li>
                    </ul>
                </li>
              </s:if>
              <s:else>
                <li>
                    <button type="button" class="btn btn-default navbar-btn" id="loginBtn" data-toggle="modal" data-target="#loginModal">Sign In</button>
                </li>
              </s:else>
                <li>
                    <form class="navbar-form" action="">
                        <div class="form-group has-feedback">
                            <input type="text" class="form-control" placeholder="Search in website">
                            <span class="glyphicon glyphicon-search form-control-feedback"></span>
                        </div>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Sign In Form -->
<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form class="form-horizontal" id="loginForm" method="post" action="login">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="loginModalLabel">Sign In</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group" id="loginAccountGroup">
                        <label for="loginAccount" class="col-sm-2 control-label">Account</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="loginAccount" placeholder="Account" name="user.account" required="required">
                        </div>
                    </div>
                    <div class="form-group" id="loginPassGroup">
                        <label for="loginPassword" class="col-sm-2 control-label">Password</label>
                        <div class="col-sm-8">
                            <input type="password" class="form-control" id="loginPassword" placeholder="Password" name="user.password" required="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <label>New to MRSYS.ONLINE? </label>
                            <button type="button" class="btn btn-default btn-sm" id="changeBtn">Sign Up</button>
                            <label> first!</label>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="reset" id="loginResetBtn" value="reset" class="btn btn-default">Reset</button>
                    <button type="submit" id="loginSubmitBtn" value="submit" class="btn btn-primary">Submit</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Sign Up Form -->
<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="registerModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form class="form-horizontal" id="registerForm" method="post" action="register">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="registerModalLabel">Sign Up</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group" id="registerAccountGroup">
                        <label for="loginAccount" class="col-sm-2 control-label">Account</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="registerAccount" placeholder="Account" name="user.account" required="required">
                        </div>
                    </div>
                    <div class="form-group" id="registerEmailGroup">
                        <label for="loginEmail" class="col-sm-2 control-label">Email</label>
                        <div class="col-sm-8">
                            <input type="email" class="form-control" id="registerEmail" placeholder="Email" name="user.email" required="required">
                        </div>
                    </div>
                    <div class="form-group" id="registerPassGroup">
                        <label for="loginPassword" class="col-sm-2 control-label">Password</label>
                        <div class="col-sm-8">
                            <input type="password" class="form-control" id="registerPassword" placeholder="Password" name="user.password" required="required">
                        </div>
                    </div>
                    <div class="form-group" id="passConfirmGroup">
                        <label for="confirmPassword" class="col-sm-2 control-label">Confirm</label>
                        <div class="col-sm-8">
                            <input type="password" class="form-control" id="confirmPassword" placeholder="Confirm Password" required="required">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="reset" id="registerResetBtn" value="reset" class="btn btn-default">Reset</button>
                    <button type="submit" id="registerSubmitBtn" value="submit" class="btn btn-primary" disabled="disabled">Submit</button>
                </div>
            </form>
        </div>
    </div>
</div>