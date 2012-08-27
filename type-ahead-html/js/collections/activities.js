define([
    'jQuery', 'Underscore', 'Backbone', 'models/activity'
], function($, _, Backbone, Model) {
    var keepPolling = false;
    var activityTypes = {
        "ADD_RECRUIT" : {
            "description" : "Added a new recruit"
        },
        "UPDATE_RECRUIT" : {
            "description" : "Updated a recruit"
        },
        "ADD_REVIEW" : {
            "description" : "Added a new review"
        },
        "UPDATE_REVIEW" : {
            "description" : "Updated a review"
        },
        "ADD_INTEVIEWER" : {
            "description" : "Added a new interviewer"
        },
        "UPDATE_INTEVIEWER" : {
            "description" : "Updated an inteviewer"
        }
    }
    var Collection = Backbone.Collection.extend({
        model : Model,
        url : function() {
            return '/api/activity/all?limit=5&sort=creationTime&creationTime.dir=DESC';
        },

        parse : function(resp, xhr) {
            _.each(resp.content, function(activity) {
                var type = activityTypes[activity.activityType];
                if (type) {
                    _.extend(activity, type);
                }
            });
            return resp.content;
        },

        startListener : function() {
            keepPolling = true;
            this._startListener();
        },

        _startListener : function() {
            if (!keepPolling) {
                return;
            }

            var that = this;
            var jqXHR = this.fetch({
                url : '/api/activity/subscribe',
                success : function(model, response) {
                    console.log("Merge succeeded...");
                },
                error : function(model, response) {
                    console.log("Merge failed...");
                    keepPolling = false;
                },
                complete : function() {
                    console.log("Merge attempt complete...");
                    that._startListener();
                }
            });
        }
    });

    return new Collection();
});
