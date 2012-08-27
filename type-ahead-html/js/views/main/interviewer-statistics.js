define([
    'jQuery',
    'Underscore',
    'Backbone',
    'models/session',
    'models/statistic',
    'text!templates/main/interviewer-statistics.html'
], function($, _, Backbone, Session, Statistics, template) {

    var View = Backbone.View.extend({
        selector : "#interviewer-statistics-widget",
        template : _.template(template),
        initialize : function() {
            _.bindAll(this, "render");

            // Once the collection is fetched re-render the view
            Statistics.bind("change", this.render);
            
            if (Statistics.attributes) {
                //this.render();
            }
        },
        render : function() {
            $(this.selector).html(this.template({
                data : Statistics.stages()
            }));
        }
    });

    return View;
});