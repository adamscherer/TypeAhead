define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'text!templates/modals/add-document-modal.html',
    'jQuery_serialize'
], function($, _, Backbone, GlobalEvents, template) {

    // Since this data is submitted to an IFRAME, we are adding an accessible
    // callback function.
    var callbackFunctionName = 'addDocumentCallback';
    var callback = function(data) {
        instance.hideModal();
    };

    var View = Backbone.View.extend({
        template : _.template(template),
        initialize : function() {
            _.bindAll(this, 'showModal');
            
            $('#modals').append(this.$el);
            window[callbackFunctionName] = callback;
        },

        render : function() {
            this.$el.html(this.template(this.model.toJSON()));
            this.errorMessage = this.$el.find('.alert-error');
            this.modalEl = this.$el.find('.modal').modal({
                show : false
            });
        },

        showModal : function(recruit) {
            this.model = recruit;
            this.render();
            this.errorMessage.addClass('hide');
            this.modalEl.modal('show');
        },

        hideModal : function() {
            this.modalEl.modal('hide');
        },

        events : {

        }
    });

    // Return an instantiate view to ensure there is only one per page.
    var instance =  new View();
    
    return instance;
});