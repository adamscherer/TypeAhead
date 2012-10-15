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

            // Cancel last call if already in progress
            if (this.xhr) this.xhr.abort();

            var options = this.options.remote;
            this.xhr = $.ajax({
                url: options.url,
                type: options.method,
                data: options.preDispatch ? options.preDispatch(query) : { query : query },
                dataType: options.dataType,
                timeout: options.timeout,
                success: function(data) {
                    process(options.preProcess ? options.preProcess(data) : data);
                }
              });
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
                        },
                        preProcess : function(data) {
                            var items = [];
                            
                            $.each(data.values, function() {
                                items.push(this.name);
                            });
                            
                            return items;
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
