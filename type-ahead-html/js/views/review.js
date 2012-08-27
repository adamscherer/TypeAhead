define([
    'jQuery',
    'Underscore',
    'Backbone',
    'GlobalEvents',
    'models/review',
    'models/recruit',
    'collections/interviewers',
    'text!templates/review.html',
    'jQuery_serialize'
], function($, _, Backbone, GlobalEvents, ReviewModel, RecruitModel, InterviewersCollection, Template) {

    var View = Backbone.View.extend({

        template : _.template(Template),

        initialize : function() {
            _.bindAll(this, "load", "render", "toggleSection", "save");
            
            InterviewersCollection.fetch();
        },

        render : function() {
            console.log('render review');
            this.$el.html(this.template({
                data : {
                    review : this.review.toJSON(),
                    recruit : this.recruit.toJSON(),
                    interviewers : InterviewersCollection.toJSON()
                }
            }));
        },

        events : {
            "submit form" : "save",
            "click div.review-toggle button" : "toggleSection",
            "click div.rating button" : "ratingClick"
        },

        toggleSection : function(ev) {
            ev.preventDefault();
            var target = $(ev.target);
            var value = target.data('value');
            this.$el.find("#type").val(target.data('value'));
            this.$el.find('.section').addClass('hide');
            this.$el.find(target.data('section')).removeClass('hide');
        },

        ratingClick : function(ev) {
            ev.preventDefault();

            var target = $(ev.target);
            var parent = target.closest('.btn-group');
            var value = target.data('value');
            var label = target.data('label')
            parent.find('input').val(target.data('value'));
            parent.find('.rating-label').html(label);

            // Don't return false because we want the event to bubble
        },

        save : function(ev) {
            this.review.set({
                recruitId : this.recruit.id
            });
            this.review.set($(ev.currentTarget).serializeObject());
            this.review.save(null, {
                success : _.bind(function() {
                    Backbone.history.navigate('recruit/' + this.recruit.id, {
                        trigger : true
                    });
                }, this)
            });

            return false;
        },

        load : function(id, reviewId) {
            this.recruit = new RecruitModel({
                id : id
            });
            this.review = new ReviewModel({
                id : reviewId
            });

            this.recruit.fetch({
                success : _.bind(function() {
                    this.review.fetch({
                        success : _.bind(function() {
                            this.render()
                        }, this)
                    })
                }, this)
            });
        }
    });

    return View;
});