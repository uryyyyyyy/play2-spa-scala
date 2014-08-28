/**
 * Created by shiba on 14/07/16.
 */

simpleWebDevTool.component.sampleButton = function(selector) {
    'use strict';
    var $select = $(selector);
    var currentData = {};

    return {
        refresh: function (text) {
            if ((!_.isEqual(currentData, text) && text)) {
                currentData = _.cloneDeep(text);
                $select.val(text);
            }
        },

        getText: function () {
            return $select.val();
        },
        clickEStream : $(selector).asEventStream('click')
    };
};