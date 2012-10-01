package com.typeahead.entity;

import com.annconia.security.entity.SecurityUser;

/*
 * TODO: needed a place to dump the stripe customer id
 * 
 * @author mrusso
 *
 */
public class AppUser extends SecurityUser {

    private String stripeCustomerId;

    public AppUser(String email, String password) {
        super(email, password);
    }

    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    public void setStripeCustomerId(String stripeCustomerId) {
        this.stripeCustomerId = stripeCustomerId;
    }
}
