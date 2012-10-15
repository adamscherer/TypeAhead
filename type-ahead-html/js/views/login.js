define([
    'jQuery',
    'Underscore',
    'Backbone',
    'models/session',
    'jQuery_serialize'
], function($, _, Backbone, Session) {

    var View = Backbone.View.extend({

        el : "#login-container",
        events : {"submit" : "login"},

        login : function(ev) {
            var creds = $(ev.currentTarget).serializeObject();
            Session.login(creds);

            return false;
        },

        showLoginError : function() {
            this.errorMessage.removeClass('hide');
        },

        hideLoginError : function() {
            this.errorMessage.addClass('hide');
        },

        initialize : function() {
            this.bindEvents();
            this.errorMessage = this.$el.find('.alert-error');
        },

        bindEvents : function() {
            Session.bind('change:auth', function(authenticated) {
                if (authenticated) {
                    this.hideLoginError();
                } else {
                    this.showLoginError();
                }
            }.bind(this));
        }
    })

    return View;
});