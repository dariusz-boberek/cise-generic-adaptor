package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Enum to use as value catalog for Sort By Participant properties API requests
 */
public enum SortByParticipant implements SortByEnum {
    PARTICIPANT_ID("participantId", "p.participantId asc", "p.participantId desc"),
    NAME("name", "p.name asc, p.participantId asc", "p.name desc, p.participantId asc"),
    NODE_ID("nodeId", "n.nodeId asc, p.participantId asc", "n.nodeId desc, p.participantId asc"),
    COUNTRY("country", "a.country asc, p.participantId asc", "a.country desc, p.participantId asc"),
    ENDPOINT_PROTOCOL("endpointProtocol", "p.endpointProtocol asc, p.participantId asc", "p.endpointProtocol desc, p.participantId asc"),
    ENDPOINT_URL("endpointUrl", "p.endpointUrl asc, p.participantId asc", "p.endpointUrl desc, p.participantId asc");

    private final String value;
    private final String orderByExpressionAsc;
    private final String orderByExpressionDesc;

    SortByParticipant(String value, String orderByExpressionAsc, String orderByExpressionDesc) {
        this.value = value;
        this.orderByExpressionAsc = orderByExpressionAsc;
        this.orderByExpressionDesc = orderByExpressionDesc;
    }

    @Override
    public String value() {
        return value;
    }

    public static SortByParticipant fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SortByParticipant sortByParticipant : SortByParticipant.values()) {
            if (sortByParticipant.value.equals(value)) {
                return sortByParticipant;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + SortByParticipant.class.getName() + " with value: " + value);
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
