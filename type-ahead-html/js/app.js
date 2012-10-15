define([
    'jQuery',
    'Underscore',
    'Bootstrap',
    'models/session',
    'views/login'
], function($, _, Bootstrap, Session, LoginView) {

    var loginPage = new LoginView();

    var initialize = function() {
        // make code pretty
        window.prettyPrint && prettyPrint();
        
        setupAuthListeners();
        typeahead.initialize();
    }

    var setupAuthListeners = function() {
        Session.bind('change:auth', function(authenticated) {
            if (authenticated) {
                alert("app authenticated!");
            } else {
//                alert("app not authenticated!");
            }
        }.bind(this));

        // Session.getAuth();
    }

    var typeahead = {
        initialize : function() {
            this.startListener('typeahead-city', 'http://api.type-ahead.com/city');
            this.startListener('typeahead-county', 'http://api.type-ahead.com/county');
            this.startListener('typeahead-state', 'http://type-ahead.elasticbeanstalk.com/state');
        },
        startListener : function(key, url) {
            $("body").on('focus.typeahead.data-api', '[data-provide="' + key + '"]', function(e) {
                var $this = $(this)
                if ($this.data('typeahead'))
                    return

                e.preventDefault();
                $this.typeahead({
                    items : 10,
                    ajax : {
                        url : url,
                        dataType : 'jsonp',
                        triggerLength : 1,
                        preDispatch : function(query) {
                            return {
                                q : query,
                                size : 10
                            }
                        }
                    }
                });
            });
        }
    }

    return {
        initialize : initialize
    };
});
