define([
    'jQuery',
    'Underscore',
    'Backbone',
    'dashboard-router',
    'models/session',
    'views/dashboard/navigation',
], function($, _, Backbone, Router, Session) {
    
    var Navigation = require('views/dashboard/navigation');

    var initialize = function() {

        this.mainBody = $('#main-body');

        this.sidebarView = new Navigation.sidebar({
            el : $("#sidebar")
        });

        this.headerView = new Navigation.header({
            el : $("#header")
        });
        
        this.footerView = new Navigation.footer({
            el : $("#footer")
        });
        
        Session.bind('change:auth', function(authenticated) {
            if (authenticated) {
                Router.initialize();
                this.headerView.render();
                this.footerView.render();
            } else {
                window.location.href = "/login.html";
            }
        }.bind(this));

        Session.getAuth();
    }

    return {
        initialize : initialize
    };
});
