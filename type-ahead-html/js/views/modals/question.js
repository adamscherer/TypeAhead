define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'models/question',
    'text!templates/modals/question-modal.html'
], function($, _, Backbone, GlobalEvents, QuestionModel, template) {

    var View = Backbone.View.extend({
        template : _.template(template),
        initialize : function() {
            _.bindAll(this, 'render', 'save');

            $('#modals').append(this.$el);
        },

        render : function(model) {
            this.model = model || new QuestionModel();
            this.$el.html(this.template({
                data : this.model.toJSON()
            }));
            this.errorMessage = this.$el.find('.alert-error');
            this.modalEl = this.$el.find('.modal').modal({
                show : true
            });
        },
        
        hideModal : function() {
            this.modalEl.modal('hide');
        },

        events : {
            "submit form" : "save",
            "click .btn-group button" : "categoryClick"
        },

        categoryClick : function(ev) {
            ev.preventDefault();

            var target = $(ev.target);
            var parent = target.closest('.btn-group');
            var value = target.data('value');
            parent.find('input').val(target.data('value'));

            // Don't return false because we want the event to bubble
        },

        save : function(ev) {
            this.errorMessage.addClass('hide');
            var creds = $(ev.currentTarget).serializeObject();
            this.model.set(creds);
            this.model.save(null, {
                success : _.bind(function() {
                    this.trigger('question:save', this.model);
                }, this)
            });

            return false;
        }
    });

    // Return an instantiate view to ensure there is only one per page.
    return new View();
});