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

    $.fn.typeahead.defaults = $.extend($.fn.typeahead.defaults, {
        source: function (query, process) {
            
            if (this.timerId) {
                clearTimeout(this.timerId);
                this.timerId = null;
            }
            
            // Cancel last call if already in progress
            if (this.xhr) this.xhr.abort();
            
            var params = this.options.remote.preDispatch ? this.options.remote.preDispatch(query) : { query : query };
            
            this.timerId = setTimeout($.proxy(function() {
                this.xhr = $.ajax({
                    type: this.options.remote.method,
                    url: this.options.remote.url,
                    data: params,
                    dataType: this.options.remote.dataType,
                    success: function(data) {
                        var items = [];
                        
                        $.each(data.values, function() {
                            items.push(this.name);
                        });
                        
                        process(items);
                    }
                  });
                this.timerId = null;
            }, this), this.options.remote.timeout);
        },
        remote: {
            url: null,
            timeout: 300,
            method: 'post',
            dataType: 'json',
            preDispatch: null,
            preProcess: null
        }
    });

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
                    remote : {
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
