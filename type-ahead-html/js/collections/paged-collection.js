define([
    'jQuery', 'Underscore', 'Backbone'
], function($, _, Backbone) {
    var Collection = Backbone.Collection.extend({
        parse : function(resp, xhr) {
            this.pagination = resp;

            var sort = (_.isArray(resp.sort)) ? resp.sort : [];
            this.sort = (sort.length > 0) ? sort[0] : {};

            return resp.content;
        }
    });

    return Collection;
});