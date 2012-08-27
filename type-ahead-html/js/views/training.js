define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'collections/questions',
    'views/paged-view',
    'text!templates/training.html',
    'views/modals/question',
], function($, _, Backbone, GlobalEvents, QuestionsCollection, PagedView, Template) {

    var QuestionModal = require('views/modals/question');

    var View = PagedView.extend({

        template : _.template(Template),

        collection : QuestionsCollection,

        defaultSort : "category",

        postInitialize : function() {
            _.bindAll(this, "showAdd", "showEdit", "modalSave");

            this.events = _.extend(this.pageEvents, {
                "click .x-add-question" : "showAdd",
                "click .edit" : "showEdit"
            });

            QuestionModal.on('question:save', this.modalSave);
        },

        modalSave : function(model) {
            if (model) {
                QuestionsCollection.add(model);
            }

            QuestionModal.hideModal();
            this.display();
        },

        showAdd : function(ev) {
            QuestionModal.render();

            return false;
        },

        showEdit : function(ev) {
            var id = $(ev.target).parents('td').data('id');
            var question = QuestionsCollection.get(id);
            QuestionModal.render(question);

            return false;
        }
    });

    return View;
});