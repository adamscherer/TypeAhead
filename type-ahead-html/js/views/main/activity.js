define([
    'jQuery',
    'Underscore',
    'Backbone',
    'models/session',
    'collections/activities',
    'text!templates/main/activity.html'
], function($, _, Backbone, Session, ActivitiesCollection, template) {

    var View = Backbone.View.extend({
        selector : "#activity-widget",
        template : _.template(template),
        initialize : function() {
            _.bindAll(this, "render", "display");

            // Once the collection is fetched re-render the view
            ActivitiesCollection.bind("reset", this.display);
        },

        render : function() {
            ActivitiesCollection.fetch();
        },

        display : function() {
            $(this.selector).html(this.$el.html(this.template({
                data : {
                    activities : ActivitiesCollection.toJSON()
                }
            })));

            // ActivitiesCollection.startListener();
        }
    });

    return View;
});