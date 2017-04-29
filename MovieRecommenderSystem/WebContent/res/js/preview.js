/*!
 * preview.js v1.1
 * Author: Jinke He
 * Author: Cong Bao
 * Date: 26 April, 2017
 */

if (typeof jQuery === 'undefined') {
	throw new Error('jQuery required');
}

(function(win, doc, $, undefined) {

	$(function () {
		$('[data-toggle="tooltip"]').tooltip();
	});
	
	$(function () {
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
			$("#poster").attr("src", result.Poster.replace(/https?/, 'https'));
			$("#poster").attr("alt", result.Title);
			$("#background").attr("style", "background-image: url(" + result.Poster.replace(/https?/, 'https') + ")");
		});
	});
	
	$(function () {
		$('#rateLoading').show();
		$.ajax({
			cache: false,
			type: 'post',
			url: '/ajax/loadRatings',
			data: { 'imdb': $('#movieId').val() },
			success: function (data, statusText) {
				var map = data.ratingMap;
				for (var i = 0; i < 5; i++) {
					var $star = $('#' + (i + 1) + 'star');
					$star.attr('aria-valuemax', map['-1']);
					$star.attr('aria-valuenow', map[String(i + 1)]);
					$star.attr('style', 'width: ' + (map[String(i + 1)] / map['-1'] * 100) + '%;');
					$star.html('<span class="sr-only">' + (map[String(i + 1)] / map['-1'] * 100) + '%</span>')
				}
				$('#rateLoading').hide();
			}
		});
	});
	
	$(function () {
		$('#favBtn').click(function () {
			$.ajax({
				cache: false,
				type: 'post',
				url: '/ajax/updateFavorite',
				data: {},
				success: function (data, statusText) {
					if (data.status == 'added') {
						$('#favBtn').attr('title', 'remove favourite');
						$('#favBtn').attr('style', 'color: #f00;');
						$('#favBtn').html('<span id="favorite" class=" glyphicon glyphicon-heart"></span>');
					} else if (data.status == 'removed') {
						$('#favBtn').attr('title', 'add to favourites');
						$('#favBtn').removeAttr('style');
						$('#favBtn').html('<span id="favorite" class="glyphicon glyphicon-heart-empty"></span>');
					} else {
						// TODO
					}
				}
			});
		});
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