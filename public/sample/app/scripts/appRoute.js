
var simpleWebDevTool = {};
simpleWebDevTool.controller = {};
simpleWebDevTool.views = {};
simpleWebDevTool.service = {};
simpleWebDevTool.dao = {};
simpleWebDevTool.util = {};
simpleWebDevTool.cache = {};
simpleWebDevTool.component = {};

jQuery(function() {
    'use strict';
    var app = Sammy('#SimpleWebDevTool', function(app) {
        // define a 'get' route that will be triggered at '#/path'
        app.get('#/top', function(context) {
            console.log('access to #/top');
            $('#template').html(_.template(simpleWebDevTool.util.render('topPageTemplate')));
            simpleWebDevTool.controller.topPageController().load();
        });

        app.get('#/config', function(context) {
            console.log('access to #/config');
            $('#template').html(_.template(simpleWebDevTool.util.render('configTemplate')));
            simpleWebDevTool.controller.configController().load();
        });

        app.get('#/list', function(context) {
            console.log('access to #/list');
            $('#template').html(_.template(simpleWebDevTool.util.render('listTemplate')));
            simpleWebDevTool.controller.listController().load();
        });

    });
    app.run();
});
