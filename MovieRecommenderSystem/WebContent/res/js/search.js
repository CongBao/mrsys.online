/*!
 * search.js
 * Author: Jinke He
 * Date: 115 April, 2017
 */

if (typeof jQuery === 'undefined') {
    throw new Error('jQuery required');
}

(function (win, doc, $, undefined) {
	
	$(function () {
		if (getParam('s') != null){
	        $("#search").val(getParam('s'));
	        $('#loading').show();
	        search();
	    }
	}); 

    function getParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); 
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return unescape(r[2]);
        } else {
            return null;
        }
    }
    
    var content = [];
   
   
    function loadPage(num){
        var start_index = (num-1) * 10;
        if ((num) * 10 - 1 > content.length - 1){
            var end_index = content.length - 1;
        } else {
            var end_index = (num) * 10 - 1;
        }
        $(".media-list").html('');
        $("#results-num").text(content.length + " movie results - Page " + num);
        if (finished == false){
            $("#results-num").append('<br><br>(Please try to use more accurate keywords.)');
        }
        for (var i=start_index; i<=end_index; i++){
            var imdbID = content[i].imdbID;
            var poster = content[i].Poster;
            var item = '<li class="media" id="'+imdbID+'"><div class="media-left"><a><img class="media-object" src=' + poster + ' alt=' + poster + '></a></div><div class="media-body" style="padding-left: 100px"><h1 class="media-heading" style="font-size: 32px"><a id="link-' + imdbID + '" href="N/A">'+content[i].Title+'</a></h1><h4 class="movie-info">YEAR</h4><p class="info-content" id="year-'+imdbID+'"></p><h4 class="movie-info">GENRE</h4><p  class="info-content" id="genre-'+imdbID+'"></p><h4 class="movie-info">ACTORS</h4><p  class="info-content" id="actors-'+imdbID+'"></p><h4 class="movie-info">Plot</h4><p  class="info-content" id="plot-'+imdbID+'"></p></div></li>';
            $(".media-list").append(item);
            $(".media-list").append('<HR>');
            var sub_query = "https://www.omdbapi.com/?i="+imdbID;
            $.getJSON(sub_query,function(result){                  
                $("#year-"+result.imdbID).text(result.Year);
                $("#genre-"+result.imdbID).text(result.Genre);
                $("#actors-"+result.imdbID).text(result.Actors);
                $("#plot-"+result.imdbID).text(result.Plot);
            });
            $.ajax({
            	type: 'post',
            	url: '/ajax/askId',
            	data: JSON.stringify({ 'movie': {
            		'imdb': imdbID,
            		'year': content[i].Year,
            		'title': content[i].Title
            	}}),
            	contentType: 'application/json',
            	success: function (result) {
            		$('#link-'+result.movie.imdb).attr('href', '/movie/' + result.id);
            	}
            });
        }
        $('#loading').hide();
        // there's no next page
        if (end_index == content.length - 1){
            $("#next").attr("href","#main");
        } else {
            $("#next").click(function(){
                console.log(num+1)
                loadPage(num+1);
            });
        }
        // there's no previous page
        if (start_index == 0){
            $("#previous").attr("href","#main");
        } else {
            $("#previous").click(function(){
                loadPage(num-1);
            });
        }
 
    }
 
    var finished = false;
    function gather(title){
        var query = "https://www.omdbapi.com";
        var page = 1;
        finished = false;
        while (!finished && content.length < 50){
            $.ajax({
                url: query,
                async: false,
                data: {s:title, page:page, type:"movie"},
                success: function(result){
                    if (result.Response != "True"){ finished = true; return;}
                    var results = result.Search;
                    for (var i=0; i < results.length; i++){
                        if (results[i].Poster != "N/A" && parseInt(results[i].Year) >= 2000 && $('#'+results[i].imdbID).length == 0){
                            content.push(results[i]);
                        }
                    }
                },
                dataType: "json"
            });
            page += 1;
        }
        var page = 1;        
        loadPage(1);
    }
    
    function search(){
        var page = getParam('page')==null? 1 : parseInt(getParam('page'))
        $(".media-list").html('');
        var count = 0;
        var totalNum = 0;
        var title = $("#search").val();
        $("title").text('Search · ' + title);
        gather(title);               
    }
	
})(window, document, jQuery);