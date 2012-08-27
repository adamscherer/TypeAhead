define([
    'jQuery', 'Underscore', 'Backbone', 'models/interviewer'
], function($, _, Backbone, Model) {
    var Collection = Backbone.Collection.extend({
        model : Model,

        url : function() {
            return '/api/interviewer/all';
        },

        parse : function(resp, xhr) {
            return resp.content;
        }
    });

    return new Collection;
});