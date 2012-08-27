define([
    'jQuery', 'Underscore', 'Backbone'
], function($, _, Backbone) {

    var View = Backbone.View.extend({
        // Sub classes should override
        template : _.template(""),

        collection : null,

        defaultLimit : 10,

        defaultSort : null,

        postInitialize : function() {},

        initialize : function() {
            _.bindAll(this, "render", "display", "destroy", "refresh", "sort", "next", "prev");

            // Once the collection is fetched re-render the view
            this.collection.bind("reset", this.display);

            this.postInitialize();
        },

        //Subclasses need to extend this on the events proptery
        pageEvents : {
            "click .delete" : "destroy",
            "click .header" : "sort",
            "change #totalRows" : "refresh",
            "click .next" : "next",
            "click .prev" : "prev"
        },
        
        defaultFetch : {
            
        },

        render : function() {
            this.collection.fetch({
                data : _.extend({
                    limit : this.defaultLimit,
                    sort : this.defaultSort
                }, this.defaultFetch)
            });
        },

        display : function() {
            console.log('render recruits');
            this.$el.html(this.template({
                data : {
                    models : this.collection.toJSON(),
                    pagination : this.collection.pagination,
                    sort : this.collection.sort
                }
            }));
        },

        showAdd : function(ev) {
            AddRecruitModal.render();

            return false;
        },

        destroy : function(ev) {
            var id = $(ev.target).parents('td').data('id');
            var model = this.collection.get(id);
            if (model) {
                model.destroy({
                    success : _.bind(function() {
                        this.render();
                    }, this)
                });
            }

            return false;
        },

        sort : function(ev) {
            this.$el.find('.datatable .header').each(function() {
                $(this).removeClass('headerSortDown');
            });

            $(ev.currentTarget).addClass('headerSortDown');
            return this.refresh();
        },

        next : function(ev) {
            if ($(ev.currentTarget).parent().hasClass('disabled')) {
                return false;
            }

            var el = this.$el.find('.pagination');
            var page = parseInt(el.data('page'), 10);
            el.data('page', page + 1);
            return this.refresh();
        },

        prev : function(ev) {
            if ($(ev.currentTarget).parent().hasClass('disabled')) {
                return false;
            }

            var el = this.$el.find('.pagination');
            var page = parseInt(el.data('page'), 10);
            el.data('page', page - 1);
            return this.refresh();
        },

        refresh : function() {
            var data = {
                limit : $('#totalRows').val(),
                sort : this.$el.find('.datatable .headerSortDown').data('sort'),
                page : this.$el.find('.pagination').data('page')
            }
            this.collection.fetch({
                data : data
            });

            return false;
        }
    });

    return View;
});
