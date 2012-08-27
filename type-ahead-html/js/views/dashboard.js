define([
    'require',
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'text!templates/dashboard.html',
    'views/main/status',
    'views/main/top-recruits',
    'views/main/activity',
    'views/main/recruit-statistics',
    'views/main/interviewer-statistics'
], function(require, $, _, Backbone, GlobalEvents, MainTemplate) {

    var StatusView = require('views/main/status');
    var TopRecruitsView = require('views/main/top-recruits');
    var ActivityView = require('views/main/activity');
    var RecruitStatisticsView = require('views/main/recruit-statistics');
    var InterviewerStatisticsView = require('views/main/interviewer-statistics');

    var View = Backbone.View.extend({
        initialize : function() {
            _.bindAll(this, "render");

            this.$el.html(MainTemplate);

            this.subviews = [
                new StatusView({
                    el : $("#status-section")
                }),
                new TopRecruitsView({
                    el : $("#top-recruits-widget")
                }),
                new RecruitStatisticsView(),
                new ActivityView(),
                new InterviewerStatisticsView()
            ]
        },
        render : function(el) {

            _.each(this.subviews, function(view) {
                view.render();
            });
        }
    });

    return View;
});