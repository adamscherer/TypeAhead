package com.typeahead.repository.stripe;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Token;
import com.typeahead.entity.AppUser;
import com.typeahead.repository.stripe.StripeUserSubscription.SubscriptionPlan;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/context/stripe-context.xml" })
public class StripeIntegrationTest {

    public static String PLAN_CHANGE_CUSTOMER_ID = "cus_0SrM07hnZ1rtVA";
    public static String PLAN_CANCEL_CUSTOMER_ID = "cus_0Spy1zUVX2RbHQ";

    @Autowired
    com.typeahead.repository.stripe.StripeSubscriptionRepository repository;

    @Test
    public void test_createCustomerSubscription() {
        StripeUserSubscription userSubscription = repository.createSubscription(generateAppUser(),
                SubscriptionPlan.BASIC, createSingleUseToken());
        System.out.println(userSubscription);
        Assert.assertNotNull(userSubscription);
    }

    @Test
    public void test_modifyCustomerSubscription() {
        AppUser user = generateAppUser();
        user.setStripeCustomerId(PLAN_CHANGE_CUSTOMER_ID);

        // review current plan
        StripeUserSubscription current = repository.getSubscription(user);
        System.out.println(current);

        SubscriptionPlan newPlan;
        boolean changeToEnterprise;

        // toggle the plan
        if (current.getSubscriptionType().equals(SubscriptionPlan.BASIC)) {
            changeToEnterprise = true;
            newPlan = SubscriptionPlan.ENTERPRISE;
        } else {
            changeToEnterprise = false;
            newPlan = SubscriptionPlan.BASIC;
        }

        repository.modifySubscription(user, newPlan);

        // validate modification
        current = repository.getSubscription(user);
        System.out.println(current);

        if (changeToEnterprise)
            Assert.assertEquals(current.getSubscriptionType(), SubscriptionPlan.ENTERPRISE);
        else
            Assert.assertEquals(current.getSubscriptionType(), SubscriptionPlan.BASIC);
    }

    @Test
    public void test_cancelCustomerSubscription() {
        AppUser user = generateAppUser();
        user.setStripeCustomerId(PLAN_CANCEL_CUSTOMER_ID);

        // make sure customer has plan before canceling
        repository.modifySubscription(user, SubscriptionPlan.BASIC);
        System.out.println(repository.getSubscription(user));

        repository.cancelSubscription(user);

        // validate cancel plan
        StripeUserSubscription sub = repository.getSubscription(user);
        System.out.println(sub);
        Assert.assertEquals(sub.getSubscriptionType(), SubscriptionPlan.NONE);
    }

    protected AppUser generateAppUser() {
        String emailAddress = new Date().getTime() + "@annconia.com";

        AppUser user = new AppUser(emailAddress, emailAddress);
        user.setId("created-by-junit");

        return user;
    }

    /*
     * In real circumstances single use tokens are provided from Stripe after
     * sending/validating the user's credit card data via stripe.js. This token
     * will then be provided to our application via the request.
     */
    protected StripeToken createSingleUseToken() {

        Stripe.apiKey = StripeSubscriptionRepository.STRIPE_API_TEST_KEY;

        Map<String, Object> cardParams = new HashMap<String, Object>();
        cardParams.put("exp_year", 2013);
        cardParams.put("cvc", "314");
        cardParams.put("exp_month", 9);
        cardParams.put("number", "4242424242424242");

        Map<String, Object> tokenParams = new HashMap<String, Object>();
        tokenParams.put("card", cardParams);

        StripeToken stripeToken = null;

        try {
            Token token = Token.create(tokenParams);
            stripeToken = new StripeToken(token.getId());
        } catch (StripeException ex) {
            ex.printStackTrace();
            return null;
        }

        return stripeToken;
    }
}
