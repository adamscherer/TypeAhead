define([
    'jQuery', 'Underscore', 'Backbone'
], function($, _, Backbone) {
    var Model = Backbone.Model.extend({

        urlRoot : '/api/review',
        defaults : {
            type : 'RESUME'
        }
    });

    return Model;
});