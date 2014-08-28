/**
 * Created by shiba on 14/07/17.
 */

simpleWebDevTool.component.sampleForm = function(selector) {
    'use strict';
    var currentData = {};
    var $select = $(selector);
    var id;

//    form.validate();

    return {
        refresh : function(newData){
            $select.val(newData.formStr);
            id = newData.id;
        },

        getValue : function(){
            return {formStr:$select.val(),
                    id : id};
        },

        keyUpEStream : $select.asEventStream('keyup')
    };
};