define([
    'jQuery', 'Underscore', 'Backbone'
], function($, _, Backbone) {
    var Model = Backbone.Model.extend({

        urlRoot : '/api/question'

    });

    return Model;
});