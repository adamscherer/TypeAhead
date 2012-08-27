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

    var DashboardInstance = new DashboardView({
        el : $("#dashboard-body")
    });

    var RecruitsInstance = new RecruitsView({
        el : $("#recruits-body")
    });

    var GeolocationInstance = new GeolocationView({
        el : $("#geolocation-body")
    });

    var InterviewersInstance = new InterviewersView({
        el : $("#interviewers-body")
    });

    var TrainingInstance = new TrainingView({
        el : $("#training-body")
    });

    var RecruitInstance = new RecruitView({
        el : $("#recruit-body")
    });

    var ReviewInstance = new ReviewView({
        el : $("#review-body")
    });
    
    var bodyPages = $('.body-page');

    var AppRouter = Backbone.Router.extend({
        
        routes : {
            // Define some URL routes
            'dashboard' : 'showDashboard',
            'recruits' : 'showRecruits',
            'geolocation' : 'showGeolocation',
            'interviewers' : 'showInterviewers',
            'training' : 'showTraining',
            'recruit/:id' : 'showRecruit',
            'recruit/:id/review' : 'showReview',
            'recruit/:id/review/:reviewId' : 'showReview',

            // Default
            '*actions' : 'defaultAction'
        },

        showDashboard : function() {
            this.defaultAction();
        },
        showRecruits : function() {
            RecruitsInstance.render();
            this.activatePage(RecruitsInstance);
            this.sidebarTrigger('recruits');
        },
        showGeolocation : function() {
            GeolocationInstance.render();
            this.activatePage(GeolocationInstance);
            this.sidebarTrigger('geolocation');
        },
        showInterviewers : function() {
            InterviewersInstance.render();
            this.activatePage(InterviewersInstance);
            this.sidebarTrigger('interviewers');
        },
        showTraining : function() {
            TrainingInstance.render();
            this.activatePage(TrainingInstance);
            this.sidebarTrigger('training');
        },
        showRecruit : function(id) {
            RecruitInstance.load(id);
            this.activatePage(RecruitInstance);
            this.sidebarTrigger('recruits');
        },
        showReview : function(id, reviewId) {
            ReviewInstance.load(id, reviewId);
            this.activatePage(ReviewInstance);
            this.sidebarTrigger('recruits');
        },
        defaultAction : function(actions) {
            DashboardInstance.render();
            this.activatePage(DashboardInstance);
            this.sidebarTrigger('dashboard');
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
