define([
    'jQuery', 'Underscore'
], function($, _) {

    var GlobalEvents = {
        fire : function() {
            console.log('fired');
        }
    };

    return GlobalEvents;
});
