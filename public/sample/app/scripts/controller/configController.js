
simpleWebDevTool.controller.configController = function(){
    'use strict';
    var component = simpleWebDevTool.component;
    var service = simpleWebDevTool.service.configService;
    //var addButton = component.sampleButton('#addButton');
    var nameForm = component.form('#userForm');
    var passForm = component.form('#passForm');

    nameForm.keyUpEStream.assign(function(e) {
        console.log('miniForm.keyUpEStream');
        if(e.keyCode === 13){//Enter Key
            var name = nameForm.getValue();
            var pass = passForm.getValue();
            service.post({name: name, pass: pass}, console.log('done'));
        }
    });

    return {
        load : function(){
            console.log('load');
        }
    };
};