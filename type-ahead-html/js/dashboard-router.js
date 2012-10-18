define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'views/dashboard/dashboard-main',
    'views/dashboard/usage',
    'views/dashboard/plan',
    'views/dashboard/profile'
], function($, _, Backbone, GlobalEvents, DashboardView, UsageView, PlanView, ProfileView) {
    
    var DashboardInstance = new DashboardView({
        el : $("#dashboard-body")
    });
    
    var UsageInstance = new UsageView({
        el : $("#usage-body")
    });

    var PlanInstance = new PlanView({
        el : $("#plan-body")
    });

    var ProfileInstance = new ProfileView({
        el : $("#profile-body")
    });
    
    var bodyPages = $('.body-page');
    
    var DashboardRouter = Backbone.Router.extend({
        
        routes : {
            // Define some URL routes
            'dashboard-main' : 'showDashboard',
            'usage' : 'showUsage',
            'plan' : 'showPlan',
            'profile' : 'showProfile',

            // Default
            '*actions' : 'defaultAction'
        },

        showDashboard : function() {
            this.defaultAction();
        },
        showUsage : function() {
//            UsageInstance.load(id);
            this.activatePage(UsageInstance);
            this.sidebarTrigger('usage');
        },
        showPlan : function() {
//            PlanInstance.load(id);
            this.activatePage(PlanInstance);
            this.sidebarTrigger('plan');
        },
        showProfile : function() {
//            ProfileInstance.load(id);
            this.activatePage(ProfileInstance);
            this.sidebarTrigger('profile');
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
            bodyPages.addClass('hide');
            view.$el.removeClass('hide');
        }
    });

    var initialize = function() {
        try {
            var app_router = new DashboardRouter;
            Backbone.history.start();
        } catch (e) {}
    };

    return {
        initialize : initialize
    };
});
