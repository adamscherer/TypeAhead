define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'models/recruit',
    'text!templates/recruit.html',
    'text!templates/recruit-school-form.html',
    'text!templates/recruit-work-form.html',
    'views/modals/add-document',
    'jQuery_serialize'
], function($, _, Backbone, GlobalEvents, RecruitModel, Template, SchoolForm, WorkForm) {

    var RecruitCompositeModel = Backbone.Model.extend({

        urlRoot : '/api/recruit/composite'

    });

    var AddDocumentModal = require('views/modals/add-document');

    var View = Backbone.View.extend({

        template : _.template(Template),

        initialize : function() {
            _.bindAll(this, "load", "render", "showEdit", "hideEdit", "save");
        },

        load : function(id) {
            this.model = new RecruitCompositeModel({
                id : id
            });
            this.model.bind("change", this.render);
            this.model.fetch();
        },

        render : function() {
            console.log('render recruit');
            this.$el.html(this.template({
                data : this.model.toJSON(),
                schoolForm : _.template(SchoolForm),
                workForm : _.template(WorkForm)
            }));
        },

        events : {
            "click .x-edit" : "showEdit",
            "click #add-document" : "addDocument",
            "click .x-cancel" : "hideEdit",
            "submit form" : "save",
            "click .x-add-school" : "addSchool",
            "click .x-add-work" : "addWork",
            "click .btn-group button" : "buttonGroupClick"
        },

        showEdit : function(ev) {
            this._toggleEditing($(ev.target), 'addClass')

            return false;
        },

        hideEdit : function(ev) {
            this._toggleEditing($(ev.target), 'removeClass')

            return false;
        },

        buttonGroupClick : function(ev) {
            ev.preventDefault();

            var target = $(ev.target);
            var parent = target.closest('.btn-group');
            var value = target.data('value');
            parent.find('input').val(target.data('value'));

            // Don't return false because we want the event to bubble
        },
        
        save : function(ev) {
            console.log('save');
            ev.preventDefault();

            var form = $(ev.currentTarget);
            if (form.data('collection')) {
                var obj = {}
                var data = [];
                form.find('fieldset').each(function() {
                    data.push($(this).serializeObject());
                });
                obj[form.data('collection')] = data;
                this.model.set(obj);
            } else {
                this.model.set(form.serializeObject());
            }

            this.model.save();
            this._toggleEditing($(ev.target), 'removeClass')

            return false;
        },

        addSchool : function(ev) {
            $(ev.target).closest('.edit').find('.data').append(_.template(SchoolForm, {
                data : {}
            }));

            return false;
        },

        addWork : function(ev) {
            $(ev.target).closest('.edit').find('.data').append(_.template(WorkForm, {
                data : {}
            }));

            return false;
        },

        addDocument : function(ev) {
            AddDocumentModal.showModal(this.model);

            return false;
        },

        _toggleEditing : function(target, action) {
            var container = target.closest('.recruit-info');
            container[action]('editing');
        }
    });

    return View;
});