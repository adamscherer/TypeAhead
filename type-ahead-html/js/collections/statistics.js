define([
    'jQuery', 'Underscore', 'Backbone', 'models/statistic'
], function($, _, Backbone, Model) {

    var Collection = Backbone.Collection.extend({
        // model : Model,
        initialize : function() {

        },
        url : function() {
            return '/api/statistics/all';
        },
        parse : function(resp, xhr) {
            return resp.content;
        }
    });

    return new Collection();
});
