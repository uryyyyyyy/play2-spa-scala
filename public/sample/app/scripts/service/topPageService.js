
(function() {
    'use strict';
    var dao = simpleWebDevTool.dao;
    var util = simpleWebDevTool.util;
    simpleWebDevTool.service.topPageService = {

        load : function () {
            console.log('service.topPageService.load');
            return Bacon.combineTemplate({
                formData: dao.topPageDao.getString()
            });
        },

        post : function (formValue, okCallBack) {
            console.log('service.topPageService.post');
            var bacon = dao.topPageDao.post(formValue);
            //when post was success(include correct error msg)
            bacon.onValue(util.okFunc(okCallBack));

            //when post was error(internal server error)
            bacon.onError(util.ngFunc());
        },

        fileUpload : function (fd) {
            var postData = {
              type : 'POST',
              dataType : 'text',
              data : fd,
              processData : false, //specification (not allow jQuery to exec data)
              contentType : false //specification
            };

            $.ajax( 'jsonApi/upload', postData ).done(function(text){
              console.log(text);
            });
        }

    };
})(jQuery);