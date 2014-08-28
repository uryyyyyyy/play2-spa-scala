/**
 * Created by shiba on 14/07/13.
 */

'use strict';
simpleWebDevTool.cache.tmplCache = {};
// this function is cache, you don't need to change
simpleWebDevTool.util.render = function(tmplName) {
    var tmplCache = simpleWebDevTool.cache.tmplCache;

    if ( ! tmplCache[tmplName] ) {
        var tmplUrl = 'views/' + tmplName + '.html';
        $.ajax({
            url: tmplUrl,
            method: 'GET',
            async: false,
            dataType: 'html',
            success: function(data) {
                tmplCache[tmplName] = data;
            }
        });
    }
    return tmplCache[tmplName];
};