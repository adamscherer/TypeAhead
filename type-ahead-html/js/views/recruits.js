define(
    [
        'jQuery',
        'Underscore',
        'Backbone',
        'GlobalEvents',
        'text!templates/recruits.html',
        'collections/recruits',
        'views/paged-view',
        'views/modals/add-recruit'
    ],
    function($, _, Backbone, GlobalEvents, Template, RecruitsCollection, PagedView, AddRecruitModal) {

        var RecruitsCollectionInstance = new RecruitsCollection();

        var View = PagedView.extend({
            template : _.template(Template),

            collection : RecruitsCollectionInstance,

            defaultSort : 'firstName',

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
