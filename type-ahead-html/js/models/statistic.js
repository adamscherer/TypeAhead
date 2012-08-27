define([
    'jQuery', 'Underscore', 'Backbone'
], function($, _, Backbone) {
    var Model = Backbone.Model.extend({

        url : function() {
            return '/api/statistics/all';
        },

        parse : function(resp, xhr) {
            var model = {};
            _.each(resp.content, function(content) {
                model[content.type] = content.values;
            });

            return model;
        },

        stages : function() {
            return this.attributes['STAGES'];
        },

        majors : function() {
            var col = [];
            var total = 0;
            for (major in this.attributes['MAJORS']) {
                var value = this.attributes['MAJORS'][major];
                total += value;
                col.push({
                    'name' : major,
                    'total' : value
                });
            }

            return _.sortBy(col, function(obj) {
                obj.percent = ((obj.total / total) * 100).toFixed(1);
                return -(obj.total);
            });
        },

        schools : function() {
            var col = [];
            var total = 0;
            for (school in this.attributes['SCHOOLS']) {
                var value = this.attributes['SCHOOLS'][school];
                total += value;
                col.push({
                    'name' : school,
                    'total' : value
                });
            }

            return _.sortBy(col, function(obj) {
                obj.percent = ((obj.total / total) * 100).toFixed(1);
                return -(obj.total);
            });
        }
    });

    return new Model;
});