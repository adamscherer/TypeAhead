define([
    'jQuery',
    'Underscore',
    'Backbone',
    'views/paged-view',
    'views/modals/add-recruit',
    'collections/recruits',
    'text!templates/main/status-details.html'
], function($, _, Backbone, PagedView, AddRecruitModal, RecruitsCollection, template) {

    var RecruitsCollectionInstance = new RecruitsCollection();

    var View = PagedView.extend({

        template : _.template(template),

        collection : RecruitsCollectionInstance,

        defaultSort : 'firstName',

        postInitialize : function() {
            this.events = _.extend(this.pageEvents, {

            });
        }

    });

    return View;
});