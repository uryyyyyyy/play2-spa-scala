
(function() {
    'use strict';
    var dao = simpleWebDevTool.dao;
    var util = simpleWebDevTool.util;
    simpleWebDevTool.service.configService = {

        post : function (formValue, okCallBack) {
            console.log('service.configService.post');
            var bacon = dao.configDao.post(formValue);
            //when post was success(include correct error msg)
            bacon.onValue(function(res){util.okDialog(res, okCallBack);});

            //when post was error(internal server error)
            bacon.onError(util.errorDialog);
        },

        load : function () {
            console.log('service.configService.load');
            return Bacon.combineTemplate({
                sessionData: dao.configDao.getSession()
            });
        },
    };
})(jQuery);