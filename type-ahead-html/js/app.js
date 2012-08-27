define([
    'jQuery',
    'Underscore',
    'Backbone',
    'router',
    'models/session',
    'Bootstrap',
    'views/navigation',
    'views/modals/login'
], function($, _, Backbone, Router, Session) {

    var Navigation = require('views/navigation');
    var LoginModalView = require('views/modals/login');

    var initialize = function() {

        this.mainBody = $('#main-body');

        this.loginModal = new LoginModalView();

        this.sidebarView = new Navigation.sidebar({
            el : $("#sidebar")
        });

        this.headerView = new Navigation.header({
            el : $("#header")
        });

        typeahead.initialize();

        Session.bind('change:auth', function(authenticated) {
            if (authenticated) {
                Router.initialize();

                this.headerView.render();

                this.mainBody.removeClass('hide');
            } else {
                this.mainBody.addClass('hide');
            }
        }.bind(this));

        Session.getAuth();
    }

    var typeahead = {
        initialize : function() {
            this.startListener('typeahead-school', '/api/search/college');
            this.startListener('typeahead-hometown', '/api/search/hometown');
            this.startListener('typeahead-interviewer', '/api/search/interviewer');
            this.startListener('typeahead-question', '/api/search/question');
        },
        startListener : function(key, url) {
            $("body").on('focus.typeahead.data-api', '[data-provide="' + key + '"]', function(e) {
                var $this = $(this)
                if ($this.data('typeahead'))
                    return

                

                e.preventDefault()
                $this.typeahead({
                    items : 10,
                    ajax : {
                        url : url,
                        triggerLength : 1,
                        preDispatch : function(query) {
                            return {
                                q : query,
                                size : 10
                            }
                        }
                    }
                })
            });
        }
    }

    return {
        initialize : initialize
    };
});
