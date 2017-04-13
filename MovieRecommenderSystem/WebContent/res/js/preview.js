/*!
 * index.js
 * Author: Jinke He
 * Date: 10 April, 2017
 */

if (typeof jQuery === 'undefined') {
	throw new Error('jQuery required');
}

(function(win, doc, $, undefined) {

	$(function() {
		var movieId = $('#movieId').val();
		var query = "http://www.omdbapi.com/?i=" + movieId + "&plot=full";
		$.getJSON(query, function(result) {
			$("title").text(result.Title);
			$("#title").text(result.Title);
			$("#plot").text(result.Plot);
			$("#genre").text(result.Genre);
			$("#imdbRating").text(result.imdbRating);
			$("#actors").text(result.Actors);
			$("#released").text(result.Released);
			$("#runtime").text(result.Runtime);
			$("#poster").attr("src", result.Poster);
			$("#poster").attr("alt", result.Title);
			$("#background").attr("style", "background-image: url(" + result.Poster + ")");
		});
	});
	
	$(function () {
		$('.rating-kv').rating();
	});

})(window, document, jQuery);