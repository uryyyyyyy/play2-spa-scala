
simpleWebDevTool.controller.topPageController = function(){
    'use strict';
    var component = simpleWebDevTool.component;
    var service = simpleWebDevTool.service.topPageService;
    var miniForm = component.sampleForm('#form');
    var uploadBtn = component.sampleButton('#upload_btn');
    var downloadBtn = component.sampleButton('#download_btn');

    miniForm.keyUpEStream.assign(function(e) {
        console.log('miniForm.keyUpEStream');
        if(e.keyCode === 13){//Enter Key
            service.post(miniForm.getValue(), _refresh);
        }
    });

    uploadBtn.clickEStream.assign(function() {
        if ( $('#file').val() !== '' ) {
          var fd = new FormData();
          fd.append( 'file', $('#file').prop('files')[0] );
          service.fileUpload(fd);
        }
    });

    downloadBtn.clickEStream.assign(function() {
    	console.log($('#file').prop('files')[0]);
		//service.fileDownload($('#file').prop('files')[0].name);
		location.assign('jsonApi/download/' + $('#file').prop('files')[0].name);
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