/**
 * Created by shiba on 14/07/13.
 */

simpleWebDevTool.util.getAjaxAsync = function(url) {
    'use strict';
    console.log('getAjaxAsync url:' + url);
    return $.ajax({
        type: 'GET',
        url: url,
        async: true
    });
};

simpleWebDevTool.util.postAjaxAsync = function(url, reqData) {
    'use strict';
    console.log('putAjaxAsync url:' + url);
    return $.ajax({
        type: 'POST',
        url: url,
        async: true,
        contentType: 'text/json',
        data: reqData
    });
};

simpleWebDevTool.util.okFunc = function(okCallBack) {
    'use strict';
    console.log('okDialog');

    return function(res){
        if(res.errMsg){
            $('#alert').html("<div class='alert alert-warning alert-dismissible' role='alert'>" +
                  "<button type='button' class='close' data-dismiss='alert'><span aria-hidden='true'>&times;</span><span class='sr-only'>Close</span></button>" +
                  "posted, but some data is not good" + "</div>");
        }else{
            $('#alert').html("<div class='alert alert-success alert-dismissible' role='alert'>" +
                  "<button type='button' class='close' data-dismiss='alert'><span aria-hidden='true'>&times;</span><span class='sr-only'>Close</span></button>" +
                  "post success" + "</div>");
            okCallBack(res);
        }
    };
};

simpleWebDevTool.util.ngFunc = function() {
    'use strict';
    console.log('ngFunc');
    return function(res){
        console.log(res)
        if(res.responseJSON){
            var errMsg = res.responseJSON.errMsg;
        } else {
            var errMsg = res.responseText;
        }
        $('#alert').html(
            "<div class='alert alert-danger alert-dismissible' role='alert'>" +
            "<button type='button' class='close' data-dismiss='alert'><span aria-hidden='true'>&times;</span><span class='sr-only'>Close</span></button>" +
            errMsg + "</div>");
    };
};