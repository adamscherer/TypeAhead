package com.typeahead.repository.stripe;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Plan;
import com.stripe.model.Subscription;
import com.typeahead.entity.AppUser;
import com.typeahead.repository.stripe.StripeUserSubscription.SubscriptionPlan;

/*
 * TODO: update our secret API keys once source code is private. Ask Mario in the meantime
 * TODO: add application configuration to toggle strip to test / pubish
 * 
 * @See: https://stripe.com/docs/api?lang=java#subscriptions
 */
public class StripeSubscriptionRepository {

    public static String STRIPE_API_TEST_KEY = "";
    public static String STRIPE_API_LIVE_KEY = "";

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    public void init() {
        Stripe.apiKey = STRIPE_API_TEST_KEY;
    }

    public StripeUserSubscription getSubscription(AppUser user) {
        Customer existingCustomer = getCustomer(user);
        return loadStripeSubscription(user, existingCustomer);
    }

    public StripeUserSubscription createSubscription(AppUser user, SubscriptionPlan plan,
            StripeToken details) {

        // providing userId as description for easy view on Stripe dashboard
        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("email", user.getEmail());
        customerParams.put("description", user.getId());
        customerParams.put("card", details.getToken());
        customerParams.put("plan", plan.getId());

        Customer newCustomer = null;

        try {
            newCustomer = Customer.create(customerParams);
        } catch (StripeException ex) {
            ex.printStackTrace();
        }

        return loadStripeSubscription(user, newCustomer);
    }

    public void modifySubscription(AppUser user, SubscriptionPlan plan) {
        Customer existingCustomer = getCustomer(user);

        if (existingCustomer != null) {
            Map<String, Object> subscriptionParams = new HashMap<String, Object>();
            subscriptionParams.put("plan", plan.getId());

            try {
                existingCustomer.updateSubscription(subscriptionParams);
            } catch (StripeException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void cancelSubscription(AppUser user) {
        Customer existingCustomer = getCustomer(user);

        if (existingCustomer != null) {
            Map<String, Object> cancelParams = new HashMap<String, Object>();
            cancelParams.put("at_period_end", true);

            try {
                existingCustomer.cancelSubscription(cancelParams);
            } catch (StripeException ex) {
                ex.printStackTrace();
            }
        }
    }

    protected Customer getCustomer(AppUser user) {

        Customer existingCustomer = null;

        try {
            existingCustomer = Customer.retrieve(user.getStripeCustomerId());
        } catch (StripeException ex) {
            ex.printStackTrace();
        }

        return existingCustomer;
    }

    protected StripeUserSubscription loadStripeSubscription(AppUser user, Customer stripeCustomer) {

        StripeUserSubscription userSubscription = null;

        if (stripeCustomer != null) {
            userSubscription = new StripeUserSubscription();
            userSubscription.setAppUserId(user.getId());
            userSubscription.setStripeCustomerId(stripeCustomer.getId());

            Subscription sub = stripeCustomer.getSubscription();

            if (sub != null) {
                Plan subPlan = sub.getPlan();
                userSubscription.setSubscriptionType(SubscriptionPlan.getPlanById(subPlan.getId()));
            } else
                userSubscription.setSubscriptionType(SubscriptionPlan.NONE);

            return userSubscription;
        }

        return null;
    }
}
