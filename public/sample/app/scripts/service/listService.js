
(function() {
    'use strict';
    var dao = simpleWebDevTool.dao;
    var util = simpleWebDevTool.util;
    simpleWebDevTool.service.listService = {

        load : function (okCallBack) {
            console.log('listService.load');
            var bacon = Bacon.combineTemplate({
                slickData: dao.formStrDao.getList()
            });
            bacon.assign(util.okFunc(okCallBack));
            bacon.onError(util.ngFunc);
        }
    };
})(jQuery);