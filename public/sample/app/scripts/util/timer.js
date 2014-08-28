/**
 * Created by shiba on 14/07/13.
 */

'use strict';

simpleWebDevTool.util.dummyWait = function(time) {
    var d1 = new Date().getTime();
    var d2 = new Date().getTime();
    while (d2 < d1 + time) {
        d2 = new Date().getTime();
    }
    return;
};

simpleWebDevTool.util.countStart = function() {
    simpleWebDevTool.util.startTime = new Date().getTime();
    console.info('count start');
};

simpleWebDevTool.util.timeShow = function() {
    var now = new Date().getTime();
    console.info(now - simpleWebDevTool.util.startTime + ' ms');
};