define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'text!templates/interviewers.html',
    'views/interviewers/interviewer-list'
], function($, _, Backbone, GlobalEvents, Template) {

    var InterviewerListView = require('views/interviewers/interviewer-list');

    var SUBVIEWS = [
        new InterviewerListView()
    ];

    var View = Backbone.View.extend({

        template : _.template(Template),

        render : function() {
            this.$el.html(this.template({

            }));

            _.each(SUBVIEWS, function(view) {
                view.render();
            });

        }
    });

    return View;
});