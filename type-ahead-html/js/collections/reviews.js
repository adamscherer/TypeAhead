define([
		'jQuery', 'Underscore', 'Backbone', 'models/recruit'
], function($, _, Backbone, Model) {
	var Collection = Backbone.Collection.extend({
		model : Model,
		url : function() {
			return '/api/review/all';
		},
		parse : function(resp, xhr) {
			return resp;
		}
	});

	return new Collection;
});