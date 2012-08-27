define([
    'jQuery', 'Underscore', 'Backbone'
], function($, _, Backbone) {
    var RecruitModel = Backbone.Model.extend({

        urlRoot : '/api/recruit',
        defaults : {
            major : '',
            email : '',
            homeNumber : '',
            currentAddress : {}
        },
        initialize : function() {

        }

    });

    return RecruitModel;
});