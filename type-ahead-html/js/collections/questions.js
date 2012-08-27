define([
    'jQuery', 'Underscore', 'Backbone', 'collections/paged-collection', 'models/question'
], function($, _, Backbone, PagedCollection, Model) {
    var Collection = PagedCollection.extend({
        model : Model,
        url : function() {
            return '/api/question/all';
        }
    });

    return new Collection;
});