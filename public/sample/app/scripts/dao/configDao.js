/**
 * Created by shiba on 14/07/13.
 */

(function() {
    'use strict';
    var util = simpleWebDevTool.util;

    simpleWebDevTool.dao.configDao = {

        post: function (reqData) {
            console.log('dao.configDao.save');
            return Bacon.fromPromise(
                util.postAjaxAsync('jsonApi/config', JSON.stringify(reqData))
            );
        },

        getSession: function () {
            console.log('dao.mainDao.load');
            return Bacon.fromPromise(util.getAjaxAsync('jsonApi/config'));
        },
    };
})(jQuery);