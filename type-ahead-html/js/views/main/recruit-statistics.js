define([
    'jQuery',
    'Underscore',
    'Backbone',
    'models/session',
    'models/statistic',
    'text!templates/main/recruit-statistics.html'
], function($, _, Backbone, Session, Statistics, template) {

    var View = Backbone.View.extend({
        selector : "#recruit-statistics-widget",
        template : _.template(template),
        initialize : function() {
            _.bindAll(this, "render");

            // Once the collection is fetched re-render the view
            Statistics.bind("change", this.render);
        },
        render : function() {
            $(this.selector).html(this.template({
                data : {
                    schools : Statistics.schools(),
                    majors : Statistics.majors()
                }
            }));
        }
    });

    return View;
});