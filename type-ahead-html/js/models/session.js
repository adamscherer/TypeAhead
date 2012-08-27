define([
    'jQuery', 'Underscore', 'Backbone'
], function($, _, Backbone) {

    var onSuccess = function(model) {
        model.trigger('change:auth', true);
    };
    var onError = function(model) {
        model.trigger('change:auth', false);
    };

    var SessionModel = Backbone.Model.extend({

        urlRoot : '/session',

        login : function(creds) {
            // Do a POST to /session and send the serialized form creds
            this.fetch({
                data : creds,
                url : '/api/authenticate',
                success : onSuccess,
                error : onError
            });
        },

        logout : function() {
            this.fetch({
                url : '/api/logout',
                success : function(model) {
                    model.clear();
                    model.trigger('change:auth', false);
                },
                error : function() {
                    alert('error logging out');
                }
            });
        },

        getAuth : function() {
            this.fetch({
                success : onSuccess,
                error : onError,
                url : '/api/authenticate'
            });
        }
    });

    return new SessionModel();
});