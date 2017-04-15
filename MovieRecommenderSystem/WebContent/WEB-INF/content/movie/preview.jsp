<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author" content="Jinke He">
<title>Preview</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/css/star-rating.min.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/css/header.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/css/preview.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/star-rating.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/preview.js"></script>
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
</head>
<body>
<%@include file="../header.jsp"%>
<input type="hidden" id="movieId" value="${sessionScope.movie.imdb}">
<div class="container-fluid" id="main">
    <div id="background">
        <div id="video_bg_dim"></div>
        <div class="box">
        <div class="container" style="position:relative; z-index:2;">
            <div class="row">
                <div class="col-lg-4 col-md-4 col-sm-4">
                    <div class="poster">
                        <img id="poster" src="N/A" class="img-thumbnail" alt="N/A"/>
                    </div>
                </div>
                <div>
                  <h2 id="title">N/A</h2>
                  <p id="plot">N/A</p>
                </div>
            </div>
        </div>
      <div style="background:#181818; position:relative; z-index:1; padding-bottom:100px; margin-bottom:-100px">
        <div class="container">
            <div class="col-md-6" id="introduction">
                <div class="col">
                    <h4>Genre</h4>
                    <p id="genre">N/A</p>
                </div>
                <div class="col">
                    <h4>IMDB Rating</h4>
                    <p id="imdbRating">N/A</p>
                </div>
                <div class="col">
                    <h4>Actors</h4>
                    <p id="actors">N/A</p>
                </div>
                <div class="col">
                    <h4>Released</h4>
                    <p id="released">N/A</p>
                </div>
                <div class="col">
                    <h4>Runtime</h4>
                    <p id="runtime">N/A</p>
                </div>
            </div>

            <div class="col-md-6" id="r">
                <h4>User Ratings</h4>
                <div class="row">
                    <div class="col-md-2">5 star</div>
                    <div class="col-md-10">
                        <div class="progress">
                            <div class="progress-bar progress-bar-warning" id="5star" role="progressbar" aria-valuenow="${sessionScope.ratingMap[5]}" aria-valuemin="0" aria-valuemax="${sessionScope.ratingMap[-1]}" style="width: 0%">
                                <span class="sr-only">20% Complete</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-2">4 star</div>
                    <div class="col-md-10">
                        <div class="progress">
                            <div class="progress-bar progress-bar-warning" id="4star" role="progressbar" aria-valuenow="${sessionScope.ratingMap[4]}" aria-valuemin="0" aria-valuemax="${sessionScope.ratingMap[-1]}" style="width: 0%">
                                <span class="sr-only">10% Complete</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-2">3 star</div>
                    <div class="col-md-10">
                        <div class="progress">
                            <div class="progress-bar progress-bar-warning" id="3star" role="progressbar" aria-valuenow="${sessionScope.ratingMap[3]}" aria-valuemin="0" aria-valuemax="${sessionScope.ratingMap[-1]}" style="width: 0%">
                                <span class="sr-only">30% Complete</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-2">2 star</div>
                    <div class="col-md-10">
                        <div class="progress">
                            <div class="progress-bar progress-bar-warning" id="2star" role="progressbar" aria-valuenow="${sessionScope.ratingMap[2]}" aria-valuemin="0" aria-valuemax="${sessionScope.ratingMap[-1]}" style="width: 0%">
                                <span class="sr-only">20% Complete</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-2">1 star</div>
                    <div class="col-md-10">
                        <div class="progress">
                            <div class="progress-bar progress-bar-warning" id="1star" role="progressbar" aria-valuenow="${sessionScope.ratingMap[1]}" aria-valuemin="0" aria-valuemax="${sessionScope.ratingMap[-1]}" style="width: 0%">
                                <span class="sr-only">10% Complete</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="rate">
                    <div>
                        <div class="form-group text-center">
                            <input id="rating" name="rating" value="<s:if test="#session.rating != null">${sessionScope.rating.rating}</s:if><s:else>0</s:else>" type="number" class="rating" min="0" max="5" step="0.5" data-size="md" />
                            <div class="clearfix"></div>
                        </div>
                        <div class="form-group text-center">
                            <button id="ratingBtn" type="button" class="btn btn-lg btn-default btn-block" <s:if test="#session.rating != null">disabled="disabled"</s:if>>Submit Your Rating</button>
                            <p id="ratingFeed">Rating succeeds! Thank you!</p>
                        </div>
                    </div>
                    <br/>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</div>
<footer style="background: #181818;"><%@include file="../footer.jsp"%></footer>
</body>
</html>