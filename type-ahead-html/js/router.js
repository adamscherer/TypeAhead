define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'models/session',
    'views/dashboard',
    'views/recruits',
    'views/geolocation',
    'views/interviewers',
    'views/training',
    'views/recruit',
    'views/review'
], function($, _, Backbone, GlobalEvents, Session, DashboardView, RecruitsView, GeolocationView,
    InterviewersView, TrainingView, RecruitView, ReviewView) {

    var bodyPages = $('.body-page');

    var AppRouter = Backbone.Router.extend({
        
        routes : {
            // Define some URL routes
            'dashboard' : 'showDashboard',

            // Default
            '*actions' : 'defaultAction'
        },

        showDashboard : function() {
            this.defaultAction();
        },

        showReview : function(id, reviewId) {
            ReviewInstance.load(id, reviewId);
            this.activatePage(ReviewInstance);
            this.sidebarTrigger('recruits');
        },
        defaultAction : function(actions) {

        },
        sidebarTrigger : function(name) {
            GlobalEvents.trigger('render:sidebar', name);
        },
        activatePage : function(view) {
            bodyPages.removeClass('in');
            view.$el.addClass('in');
        }
    });

    var initialize = function() {
        try {
            var app_router = new AppRouter;
            Backbone.history.start();
        } catch (e) {}
    };

    return {
        initialize : initialize
    };
});
