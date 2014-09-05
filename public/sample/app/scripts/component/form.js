/**
 * Created by shiba on 14/07/17.
 */

simpleWebDevTool.component.form = function(selector) {
    'use strict';
    var $select = $(selector);
    var id;

//    form.validate();

    return {
        refresh : function(str){
            $select.val(str);
        },

        getValue : function(){
            return $select.val();
        },

        keyUpEStream : $select.asEventStream('keyup')
    };
};