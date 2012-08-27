define([
    //"async!http://maps.google.com/maps/api/js?key=AIzaSyABEcyJSAWzQgxFvZx-eQnXt-PqgZDl0eg&sensor=true!callback"
], function() {
    return {
        addMapToCanvas : function(mapCanvas) {
            var myOptions = {
                center : new google.maps.LatLng(41.850, -87.650),
                zoom : 4,
                mapTypeId : google.maps.MapTypeId.ROADMAP
            };

            var map = new google.maps.Map(mapCanvas, myOptions);
        }
    }
});