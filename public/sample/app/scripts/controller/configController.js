
simpleWebDevTool.controller.configController = function(){
    'use strict';
    var component = simpleWebDevTool.component;
    var service = simpleWebDevTool.service.configService;
    //var addButton = component.sampleButton('#addButton');
    var nameForm = component.form('#userForm');
    var passForm = component.form('#passForm');
    var sessionForm = component.form('#sessionForm');

    nameForm.keyUpEStream.assign(function(e) {
        console.log('miniForm.keyUpEStream');
        if(e.keyCode === 13){//Enter Key
            var id = nameForm.getValue();
            var pass = passForm.getValue();
            service.post({id: id, pass: pass}, console.log('done'));
        }
    });

    passForm.keyUpEStream.assign(function(e) {
        console.log('miniForm.keyUpEStream');
        if(e.keyCode === 13){//Enter Key
            var id = nameForm.getValue();
            var pass = passForm.getValue();
            service.post({id: id, pass: pass}, console.log('done'));
        }
    });

    var _refreshAll = function(refreshData) {
        console.log('refreshAll');
        sessionForm.refresh('name:' + refreshData.sessionData.id +
            ' sessionId:' + refreshData.sessionData.sessionId);
    };

    return {
        load : function(){
            console.log('load');
        }
    };
};