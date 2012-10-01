package com.typeahead.repository.stripe;

public class StripeUserSubscription {

    private String appUserId;
    private String stripeCustomerId;
    private SubscriptionPlan subscriptionType;

    public enum SubscriptionPlan {

        NONE("NONE", "None Selected"), BASIC("package-basic", "Basic"), BUSINESS(
                "package-business", "Small Business"), ENTERPRISE("package-enterprise",
                "Enterprise-Level");

        private String id;
        private String title;

        private SubscriptionPlan(String id, String title) {
            this.id = id;
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public static SubscriptionPlan getPlanById(String planId) {
            for (SubscriptionPlan plan : SubscriptionPlan.values()) {
                if (plan.getId().equals(planId))
                    return plan;
            }

            return NONE;
        }
    }

    public String getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    public void setStripeCustomerId(String stripeCustomerId) {
        this.stripeCustomerId = stripeCustomerId;
    }

    public SubscriptionPlan getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionPlan subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User Id: ");
        sb.append(getAppUserId());
        sb.append(" | ");
        sb.append("Stripe Customer Id: ");
        sb.append(getStripeCustomerId());
        sb.append(" | ");
        sb.append("Subscription: ");
        sb.append(getSubscriptionType().getId());

        return sb.toString();
    }
}
