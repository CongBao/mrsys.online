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

})(window, document, jQuery);