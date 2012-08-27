define([
    'jQuery', 'Underscore', 'Backbone', 'models/document'
], function($, _, Backbone, Model) {
    var Collection = Backbone.Collection.extend({
        model : Model,

        url : function() {
            return '/api/document/all';
        },

        parse : function(resp, xhr) {
            return resp;
        }
    });

    return new Collection;
});