define([
    'require',
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'text!templates/dashboard/plan.html',
], function(require, $, _, Backbone, GlobalEvents, MainTemplate) {

    var View = Backbone.View.extend({
        initialize : function() {
            _.bindAll(this, "render");

            this.$el.html(MainTemplate);
        },
        render : function(el) {
        }
    });

    return View;
});