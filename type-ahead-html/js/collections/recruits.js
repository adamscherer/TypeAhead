define([
    'jQuery', 'Underscore', 'Backbone', 'collections/paged-collection', 'models/recruit'
], function($, _, Backbone, PagedCollection, Model) {
    var Collection = PagedCollection.extend({

        model : Model,

        url : function() {
            return '/api/recruit/all';
        }
    });

    return Collection;
});