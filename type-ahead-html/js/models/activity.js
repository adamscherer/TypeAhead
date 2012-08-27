define([
		'jQuery', 'Underscore', 'Backbone'
], function($, _, Backbone) {
	var ActivityModel = Backbone.Model.extend({

		url : '/activity',
		initialize : function() {

		}
	});

	return ActivityModel;
});