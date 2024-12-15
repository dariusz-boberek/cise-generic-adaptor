package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Enum to use as value catalog for Sort By Service properties API requests
 */
public enum SortByService implements SortByEnum {
    SERVICE_ID("serviceId", "s.serviceId asc", "s.serviceId desc"),
    SERVICE_STATUS("serviceStatus", "s.serviceStatus asc, s.serviceId asc", "s.serviceStatus desc, s.serviceId asc"),
    SERVICE_TYPE("serviceType", "s.serviceType asc, s.serviceId asc", "s.serviceType desc, s.serviceId asc"),
    SERVICE_OPERATION("serviceOperation", "s.serviceOperation asc, s.serviceId asc", "s.serviceOperation desc, s.serviceId asc"),
    SERVICE_ROLE("serviceRole", "s.serviceRole asc, s.serviceId asc", "s.serviceRole desc, s.serviceId asc"),
    NODE_ID("nodeId", "n.nodeId asc, s.serviceId asc", "n.nodeId desc, s.serviceId asc"),
    PARTICIPANT_ID("participantId", "p.participantId asc, s.serviceId asc", "p.participantId desc, s.serviceId asc");

    private final String value;
    private final String orderByExpressionAsc;
    private final String orderByExpressionDesc;

    SortByService(String value, String orderByExpressionAsc, String orderByExpressionDesc) {
        this.value = value;
        this.orderByExpressionAsc = orderByExpressionAsc;
        this.orderByExpressionDesc = orderByExpressionDesc;
    }

    @Override
    public String value() {
        return value;
    }

    public static SortByService fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SortByService sortByService : SortByService.values()) {
            if (sortByService.value.equals(value)) {
                return sortByService;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + SortByService.class.getName() + " with value: " + value);
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
