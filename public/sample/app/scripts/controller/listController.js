
simpleWebDevTool.controller.listController = function(){
    'use strict';
    var component = simpleWebDevTool.component;
    var service = simpleWebDevTool.service.listService;
    var slickGrid = component.slickGridList('#myList');

//	searchButton.clickEStream.assign(function() {
//        console.log('searchButton');
//        var listElems = service.search(sampleList.getList(), simpleForm.getValue());
//        _refresh({ listData: listElems});
//        slickGrid.filterAndUpdate(Number(simpleForm.getValue()));
//    });

    var _refresh = function(refreshData){
        console.log('refresh');
        var tmp = _.cloneDeep(refreshData);
        slickGrid.refresh(tmp.slickData);
    };

    return {
        load : function(){
            //simpleWebDevTool.util.countStart();
            console.log('load');
            service.load(_refresh);
            //simpleWebDevTool.util.timeShow();
        }
    };

};