
(function() {
    'use strict';
    var util = simpleWebDevTool.util;

    simpleWebDevTool.dao.topPageDao = {
        getString: function () {
            console.log('dao.mainDao.load');
            return Bacon.fromPromise(util.getAjaxAsync('jsonApi/topSample/1'));
        },

        post: function (reqData) {
            console.log('dao.mainDao.save');
            return Bacon.fromPromise(
                util.postAjaxAsync('jsonApi/topSample/1', JSON.stringify(reqData))
            );
        }
    };
})(jQuery);