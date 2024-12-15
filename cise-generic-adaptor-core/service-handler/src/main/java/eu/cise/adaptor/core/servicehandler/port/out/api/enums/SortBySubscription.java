package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Enum to use as value catalog for Sort By Subscription properties API requests
 */
public enum SortBySubscription implements SortByEnum {

    SUBSCRIBER_SERVICE_ID("SubscriberServiceId", "s.subscriberServiceId asc", "s.subscriberServiceId desc"),
    SUBSCRIBER_PARTICIPANT_ID("SubscriberParticipantId", "s.subscriberParticipantId asc", "s.subscriberParticipantId desc"),
    STATUS("Status", "s.status asc", "s.status desc"),
    EXPIRATION_DATE("ExpirationDate", "s.expirationDate asc NULLS LAST", "s.expirationDate desc NULLS FIRST");

    private final String value;
    private final String orderByExpressionAsc;
    private final String orderByExpressionDesc;

    SortBySubscription(String value, String orderByExpressionAsc, String orderByExpressionDesc) {
        this.value = value;
        this.orderByExpressionAsc = orderByExpressionAsc;
        this.orderByExpressionDesc = orderByExpressionDesc;
    }

    public static SortBySubscription fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SortBySubscription sortBySubscription : SortBySubscription.values()) {
            if (sortBySubscription.value.equals(value)) {
                return sortBySubscription;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + SortBySubscription.class.getName() + " with value: " + value);
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String getOrderByExpressionAsc() {
        return orderByExpressionAsc;
    }

    @Override
    public String getOrderByExpressionDesc() {
        return orderByExpressionDesc;
    }

}
