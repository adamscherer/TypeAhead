define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'models/session',
    'text!templates/modals/login.html',
    'jQuery_serialize'
], function($, _, Backbone, GlobalEvents, Session, template) {

    var visible;

    var View = Backbone.View.extend({
        template : _.template(template),
        initialize : function() {
            $('#modals').append(this.$el);
            this.render();
            this.bindEvents();
            this.errorMessage = this.$el.find('.alert-error');
            this.modalEl = $('#myModal').modal({
                keyboard : false,
                backdrop : false,
                show : false
            });
        },

        render : function() {
            this.$el.html(this.template());
        },

        showModal : function() {
            this.errorMessage.addClass('hide');

            this.modalEl.modal('show');
        },

        hideModal : function() {
            this.modalEl.modal('hide');
        },

        events : {
            "submit form" : "login"
        },

        login : function(ev) {
            this.errorMessage.addClass('hide');
            var creds = $(ev.currentTarget).serializeObject();
            Session.login(creds);

            return false;
        },

        bindEvents : function() {
            // Bind to the Session auth attribute so we
            // make our view act recordingly when auth changes
            Session.bind('change:auth', function(authenticated) {
                if (authenticated) {
                    this.hideModal();
                    visible = false;
                } else {
                    if (visible) {
                        this.errorMessage.removeClass('hide');
                    } else {
                        this.showModal();
                    }

                    visible = true;
                }
            }.bind(this));
        }
    });

    return View;
});