/*!
 * index.js
 * Author: Cong Bao
 * Date: 10 April, 2017
 */

if (typeof jQuery === 'undefined') {
    throw new Error('jQuery required');
}

(function (win, doc, $, undefined) {

    var resize = function () {
        $('.index-img').css('height', $(win).innerHeight());
        $('.index-img').css('width', $(win).innerWidth());
    }

    $(function () {
        resize();
        $(win).resize(resize);
    });

    $(function () {
        $('.timeline').timelify({
            animLeft: 'fadeInLeft',
            animRight: 'fadeInRight',
            animSpeed: 600,
            offset: 150
        });
    });

    $(function () {
        var $container = $('#masonry');
        $container.imagesLoaded(function () {
            $container.masonry({
                itemSelector: '.box',
                gutter: 20,
                isAnimated: true
            });
        });
        $(win).resize(function () {
            $container.masonry('bindResize');
        });
        // (T_T)/ (-_-)#
        /*$(win).resize(function () {
            $container.masonry('destroy');
            $container.masonry({
                itemSelector: '.box',
                gutter: 20,
                isAnimated: true
            });
        });*/
    });
    
    $(function () {
    	$('#loading').hide();
    	var refreshing = false;
    	var autoRefresh = function () {
    		refreshing = true;
			var mvIds = new Array();
	    	$('#masonry > .box').each(function () {
	    		mvIds.push(Number($(this).attr('id')));
	    	});
	    	$.ajax({
	    		cache: false,
	    		type: 'post',
	    		url: 'ajax/refreshMovies',
	    		data: JSON.stringify({ 'oldMovies': mvIds }),
	    		contentType: 'application/json',
	    		beforeSend: function () {
	    			$('#loading').show();
	    		},
	    		complete: function () {
	    			$('#loading').hide();
	    			setTimeout(function () {
	    				refreshing = false;
	    			}, 1000);
	    		},
	    		success: function (data, statusText) {
	    			$.each(data.newMovies, function (key, value) {
	    				setTimeout(function () {
	    					$.ajax({
		    					cache: false,
		    					type: 'get',
		    					url: 'https://www.omdbapi.com',
		    					data: { 'i': value },
		    					success: function (data, statusText) {
		    						if (data.Poster != 'N/A') {
		    							var $box = $('<div class="box" id="' + key + '"><a href="movie/' + key + '"><img src="' + data.Poster + '"></a></div>');
			    						$('#masonry').append($box);
		    	    					$box.imagesLoaded(function () {
			    							$('#masonry').masonry('appended', $box);
			    						});
		    						}
		    					}
		    				});
	    				}, 500);
	    			});
	    		}
	    	});
    	};
    	var src = $('#masonry > .box > a > img').attr('src');
    	$.ajax({
    		cache: false,
    		type: 'get',
    		url: 'https://www.omdbapi.com',
    		data: { i: src },
    		success: function (data, statusText) {
    			$('#masonry > .box > a > img').attr('src', data.Poster);
    		}
    	});
    	$('#masonry').imagesLoaded(autoRefresh);
    	$(win).scroll(function () {
    		if ($(doc).scrollTop() + $(win).height() > $(doc).height() - 50 && !refreshing) {
    			autoRefresh();
    		}
    	});
    });

})(window, document, jQuery);