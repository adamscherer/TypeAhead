define([
    'jQuery', 'Underscore', 'Backbone'
], function($, _, Backbone) {

    var GlobalEvents = {
        fire : function() {
            console.log('fired');
        }
    };

    _.extend(GlobalEvents, Backbone.Events);

    return GlobalEvents;
});
