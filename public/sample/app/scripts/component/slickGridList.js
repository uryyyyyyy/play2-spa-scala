
simpleWebDevTool.component.slickGridList = function(selector) {
	'use strict';

    var columns = [
        {id: 'sel', name: '#', field: 'id', behavior: 'select', cssClass: 'cell-selection', width: 40, resizable: false, sortable: true},
        {id: 'title', name: 'Title', field: 'formStr', width: 120, minWidth: 120, sortable: true}
    ];

    var options = {
        editable: false,
        enableAddRow: false,
        enableCellNavigation: true
    };

    var prevPercentCompleteThreshold = 0;

    function myFilter(item, args) {
        return item.percentComplete >= args;
    }

    var dataView = new Slick.Data.DataView({ inlineFilters: true });

//    sample_dataView =     [{"id": "1",
//               "formStr": "hogehoge1"},
//               {"id": "2",
//               "formStr": "hogehoge2"}]


    var grid = new Slick.Grid(selector, dataView, columns, options);
    var pager = new Slick.Controls.Pager(dataView, grid, $('#pager'));

    // wire up model events to drive the grid
    dataView.onRowsChanged.subscribe(function (e, args) {
        grid.invalidateRows(args.rows);
        grid.render();
    });

	// for sort
    grid.onSort.subscribe(function(e, args) {
        var sortdir = args.sortAsc ? 1 : -1;
        var sortcol = args.sortCol.field;

		var comparer = function(a,b) {
            var x = a[sortcol], y = b[sortcol];
            return (x === y ? 0 : (x > y ? 1 : -1));
        };
        // using native sort with comparer
        // preferred method but can be very slow in IE with huge datasets
        dataView.sort(comparer, args.sortAsc);
    });


    var returnObj = {};
    var currentData;
    // initialize the model after all the events have been hooked up
    returnObj.refresh = function(data){
        if((!_.isEqual(currentData, data)) && data){
            currentData = _.cloneDeep(data);
            dataView.beginUpdate();
            dataView.setItems(currentData);
            dataView.endUpdate();
        }
    };

    return returnObj;
};