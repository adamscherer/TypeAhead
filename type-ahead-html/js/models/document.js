define([
    'jQuery', 'Underscore', 'Backbone'
], function($, _, Backbone) {
    var Model = Backbone.Model.extend({

        urlRoot : '/api/document'

    });

    return Model;
});