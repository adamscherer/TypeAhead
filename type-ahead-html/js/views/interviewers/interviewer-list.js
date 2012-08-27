define([
    'jQuery',
    'Underscore',
    'Backbone',
    'collections/interviewers',
    'text!templates/interviewers/interviewer-list.html'
], function($, _, Backbone, InterviewersCollection, template) {

    var View = Backbone.View.extend({
        selector : "#interviewer-list-widget",

        template : _.template(template),

        initialize : function() {
            _.bindAll(this, "render", "display");

            // Once the collection is fetched re-render the view
            InterviewersCollection.bind("reset", this.display);
        },
        render : function() {
            InterviewersCollection.fetch();
        },
        display : function() {
            $(this.selector).html(this.$el.html(this.template({
                data : {
                    interviewers : InterviewersCollection.toJSON()
                }
            })));
        }
    });

    return View;

});