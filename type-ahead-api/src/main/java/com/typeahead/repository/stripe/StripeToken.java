package com.typeahead.repository.stripe;

public class StripeToken {
    String token;

    public StripeToken() {
        super();
    }

    public StripeToken(String token) {
        super();
        setToken(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
