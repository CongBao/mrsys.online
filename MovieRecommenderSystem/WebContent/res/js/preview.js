/*!
 * preview.js
 * Author: Jinke He
 * Date: 10 April, 2017
 */

if (typeof jQuery === 'undefined') {
	throw new Error('jQuery required');
}

(function(win, doc, $, undefined) {

	$(function() {
		var movieId = $('#movieId').val();
		var query = "https://www.omdbapi.com/?i=" + movieId + "&plot=full";
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
		for (var i = 0; i < 5; i++) {
			var $star = $('#' + (i + 1) + 'star');
			var max = $star.attr('aria-valuemax');
			var now = $star.attr('aria-valuenow');
			$star.attr('style', 'width: ' + (now / max * 100) + '%;');
			$star.children('span').text((now / max * 100) + '% Complete');
		}
	});
	
	$(function () {
		$('#ratingFeed').hide();
		$('#ratingBtn').click(function () {
			$.ajax({
				cache: false,
				type: 'post',
				url: '/ajax/rateMovie',
				data: { 'rating': $('#rating').val() },
				success: function (data, statusText) {
					if (data.valid == false) {
						$('#ratingFeed').text('Sorry, You have already rated for this movie!');
					}
					$('#ratingFeed').show();
				}
			});
		});
	});

})(window, document, jQuery);