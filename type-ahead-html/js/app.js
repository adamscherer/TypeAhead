define([
    'jQuery',
    'Underscore',
    'Backbone',
    'router',
    'Bootstrap'
], function($, _, Backbone, Router) {

    var initialize = function() {

        typeahead.initialize();
    }

    var typeahead = {
        initialize : function() {
            this.startListener('typeahead-city', 'http://api.type-ahead.com/city');
            this.startListener('typeahead-county', '/api/county');
            this.startListener('typeahead-state', '/api/state');
            this.startListener('typeahead-city-terms', '/api/city/terms');
            this.startListener('typeahead-county-terms', '/api/county/terms');
            this.startListener('typeahead-state-terms', '/api/state/terms');
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
