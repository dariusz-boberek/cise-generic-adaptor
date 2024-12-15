package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Enum to use as value catalog for Subscription Status API requests
 */
public enum SubscriptionStatus implements ValueEnum<String> {

    ACTIVE("ACTIVE"),
    SUSPENDED("SUSPENDED");

    private final String value;

    SubscriptionStatus(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

}
