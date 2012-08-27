define([
    'jQuery',
    'Underscore',
    'Backbone',
    'models/statistic',
    'text!templates/main/status.html',
    'views/main/status-details'
], function($, _, Backbone, Statistics, template) {

    var StatusDetailsView = require('views/main/status-details');

    var View = Backbone.View.extend({

        template : _.template(template),

        initialize : function() {
            _.bindAll(this, "render", "displayStatus", "displayDetails");

            this.statusDetails = new StatusDetailsView({
                el : $("#status-details")
            });
            
            // Once the collection is fetched re-render the view
            Statistics.bind("change", this.displayStatus);
        },

        events : {
            'click .utopia-chart-legend' : 'displayDetails'
        },

        render : function() {
            Statistics.fetch();
        },

        displayStatus : function() {
            this.$el.html(this.template({
                data : Statistics.stages()
            }));
            /*
            this.$el.tooltip({
                selector : "div[rel=tooltip]",
                delay : {
                    show : 750,
                    hide : 0
                }
            });
            */
        },

        displayDetails : function(ev) {
            var legend = $(ev.currentTarget);
            if (legend.hasClass('active')) {
                $('#status-details').slideUp(function() {
                    legend.removeClass('active');
                });
            } else {
                this.statusDetails.defaultFetch = {
                    stage : legend.data('type')
                };
                this.statusDetails.render();
                var legends = this.$el.find('.utopia-chart-legend');
                legends.removeClass('active');
                legend.addClass('active');
                $('#status-details').hide();

                $('#status-details').slideDown();
            }
        }
    });

    return View;
});