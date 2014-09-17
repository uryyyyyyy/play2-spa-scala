
(function() {
    'use strict';
    var util = simpleWebDevTool.util;

    simpleWebDevTool.dao.formStrDao = {
        getList: function () {
            console.log('dao.mainDao.load');
            return Bacon.fromPromise(util.getAjaxAsync('jsonApi/listSample/all'));
        }
    };
})(jQuery);