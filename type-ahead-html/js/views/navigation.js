define(
    [
        'jQuery',
        'Underscore',
        'Backbone',
        'GlobalEvents',
        'models/session',
        'text!templates/header.html',
        'text!templates/sidebar.html',
        'text!templates/footer.html'
    ],
    function($, _, Backbone, GlobalEvents, Session, HeaderTemplate, SidebarTemplate, FooterTemplate) {

        var FooterView = Backbone.View.extend({
            render : function() {
                this.$el.html(FooterTemplate);
            }
        });

        var HeaderView = Backbone.View.extend({
            template : _.template(HeaderTemplate),

            events : {
                "click #logout" : "logout",
                "click .user-info" : "handleDropdown"
            },
            render : function() {
                this.$el.html(this.template({
                    data : Session.toJSON()
                }));
            },

            logout : function(ev) {
                Session.logout();

                return false;
            },

            handleDropdown : function(ev) {
                var active = $(ev.currentTarget);
                var dropDown = $('.user-dropbox');
                if (!active.hasClass('user-active')) {
                    dropDown.slideDown('fast');
                    active.addClass('user-active');
                } else {
                    active.removeClass('user-active');
                    dropDown.hide();
                }

                $(document).one("click", function(ev) {
                    active.removeClass('user-active');
                    dropDown.hide();
                });

                return false;
            }
        });

        var SidebarView = Backbone.View.extend({
            template : _.template(SidebarTemplate),
            initialize : function() {
                this.render();
                this.bindGlobalEvents();
            },
            render : function() {
                this.$el.html(this.template());
            },
            bindGlobalEvents : function() {
                var menuItems = this.$el.find('.leftmenu ul li');
                GlobalEvents.bind('render:sidebar', function(selectedItem) {
                    menuItems.each(function() {
                        var item = $(this);
                        item.removeClass('current');

                        if (item.attr('id') === selectedItem) {
                            item.addClass('current');
                        }
                    });
                });
            }
        });

        return {
            header : HeaderView,
            footer : FooterView,
            sidebar : SidebarView
        }
    });
