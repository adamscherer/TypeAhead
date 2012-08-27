define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GoogleMaps',
    'models/session',
    'collections/recruits',
    'text!templates/main/recruit-map.html'
], function($, _, Backbone, GoogleMaps, Session, RecruitsCollection, template) {

    var View = Backbone.View.extend({
        selector : "#recruit-map-widget",
        template : _.template(template),
        initialize : function() {
            _.bindAll(this, "render");
        },
        render : function() {
            $(this.selector).html(this.template());

            var mapCanvas = $("#utopia-google-map-5").get(0);

            GoogleMaps.addMapToCanvas(mapCanvas);
        }
    });

    return View;
});