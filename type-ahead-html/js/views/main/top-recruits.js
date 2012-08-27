define([
    'jQuery',
    'Underscore',
    'Backbone',
    'views/paged-view',
    'views/modals/add-recruit',
    'collections/recruits',
    'text!templates/main/top-recruits.html'
], function($, _, Backbone, PagedView, AddRecruitModal, RecruitsCollection, template) {

    var RecruitsCollectionInstance = new RecruitsCollection();

    var View = PagedView.extend({

        template : _.template(template),

        collection : RecruitsCollectionInstance,

        defaultSort : 'firstName',
        
        defaultFetch : {
            stage : 'NOT_STAGED'
        },

        postInitialize : function() {
            this.events = _.extend(this.pageEvents, {
                "click .x-add-recruit" : "showAdd"
            });
        },

        showAdd : function(ev) {
            AddRecruitModal.render();

            return false;
        }
    });

    return View;
});