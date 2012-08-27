define([
		'order!libs/jquery/jquery-1.7.2.min', 'order!libs/underscore/underscore-min', 'order!libs/backbone/backbone-min'
], function() {
	return {
		Backbone : Backbone.noConflict(),
		_ : _.noConflict(),
		$ : jQuery.noConflict()
	};
});
