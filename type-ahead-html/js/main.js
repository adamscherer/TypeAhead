require.config({
    paths : {
        loader : '/js/libs/backbone/loader',
        jQuery : '/js/libs/jquery/jquery',
        Underscore : '/js/libs/underscore/underscore',
        Backbone : '/js/libs/backbone/backbone',
        Bootstrap : '/js/libs/bootstrap/bootstrap.min',
        GlobalEvents : '/js/global',
        templates : '../templates',
        GoogleMaps : '/js/libs/google-maps',
        jQuery_serialize : '/js/libs/jquery/jquery-serialize',
        jQuery_chosen : '/js/libs/jquery/chosen.jquery',
        jQuery_ui : '/js/libs/jquery/jquery-ui',
        jQuery_tagedit : '/js/libs/jquery/jquery-tagedit',
        autoGrowInput : '/js/libs/jquery/autoGrowInput',
        async : '/js/libs/async',
        order : '/js/libs/order',
        text : '/js/libs/text'
    }
});

require([
    // Load our app module and pass it to our definition function
    'jQuery', 'Underscore', 'app'

// Some plugins have to be loaded in order due to their non AMD compliance
// Because these scripts are not "modules" they do not pass any values to the
// definition function below
], function($, _, App) {

    // Expose Underscore globally to ease template rendering
    window._ = _;

    // The "app" dependency is passed in as "App"
    // Again, the other dependencies passed in are not "AMD" therefore don't
    // pass a parameter to this function
    App.initialize();
});
