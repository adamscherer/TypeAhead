define([
		'jQuery', 'Underscore', 'Backbone'
], function($, _, Backbone) {
	var InterviewerModel = Backbone.Model.extend({

		url : '/interviewer',
		initialize : function() {

		}
	});

	return InterviewerModel;
});