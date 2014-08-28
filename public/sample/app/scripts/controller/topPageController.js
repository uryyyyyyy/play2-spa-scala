
simpleWebDevTool.controller.topPageController = function(){
    'use strict';
    var component = simpleWebDevTool.component;
    var service = simpleWebDevTool.service.topPageService;
    //var addButton = component.sampleButton('#addButton');
    var miniForm = component.sampleForm('#form');
    var button = component.sampleButton('#upload_btn');

    miniForm.keyUpEStream.assign(function(e) {
        console.log('miniForm.keyUpEStream');
        if(e.keyCode === 13){//Enter Key
            service.post(miniForm.getValue(), _refresh);
        }
    });

    button.clickEStream.assign(function() {
        if ( $('#file').val() !== '' ) {
          var fd = new FormData();
          fd.append( 'file', $('#file').prop('files')[0] );
          service.fileUpload(fd);
        }
    });

    var _refresh = function(refreshData) {
        console.log('refresh');
        miniForm.refresh(refreshData);
    };

    var _refreshAll = function(refreshData) {
        console.log('refreshAll');
        miniForm.refresh(refreshData.formData);
    };

    return {
        load : function(){
            console.log('load');
            service.load().assign(_refreshAll);
        }
    };
};