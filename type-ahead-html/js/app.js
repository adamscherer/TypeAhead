define([
    'jQuery',
    'Underscore',
    'Bootstrap'
], function($, _, Bootstrap) {

    var initialize = function() {
        // make code pretty
        window.prettyPrint && prettyPrint();
        
        typeahead.initialize();
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
