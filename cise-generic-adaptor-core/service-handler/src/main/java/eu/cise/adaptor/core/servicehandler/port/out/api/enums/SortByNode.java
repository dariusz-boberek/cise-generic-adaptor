package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Enum to use as value catalog for Sort By Node properties API requests
 */
public enum SortByNode implements SortByEnum {
    NODE_ID("nodeId", "n.nodeId asc", "n.nodeId desc"),
    GATEWAY("gateway", "n.gateway asc, n.nodeId asc", "n.gateway desc, n.nodeId asc"),
    CLASSIFICATION("classification", "n.classification asc, n.nodeId asc", "n.classification desc, n.nodeId asc"),
    COUNTRY("country", "nc.nodeCountryPK.country asc, n.nodeId asc", "nc.nodeCountryPK.country desc, n.nodeId asc"),
    MANAGING_AUTHORITY_NAME("managingAuthorityName", "na.name asc, n.nodeId asc", "na.name desc, n.nodeId asc"),
    NODE_VERSION("nodeVersion", "n.nodeVersion asc, n.nodeId asc", "n.nodeVersion desc, n.nodeId desc"),
    TRUSTED("trusted", "n.trusted asc, n.nodeId asc", "n.trusted desc, n.nodeId asc"),
    SYNC_DATE("lastSuccessfulSync", "n.syncDate asc, n.nodeId asc", "n.syncDate desc, n.nodeId asc"),
    HEALTH("health", "n.nodeId asc", "n.nodeId asc");

    private final String value;
    private final String orderByExpressionAsc;
    private final String orderByExpressionDesc;

    SortByNode(String value, String orderByExpressionAsc, String orderByExpressionDesc) {
        this.value = value;
        this.orderByExpressionAsc = orderByExpressionAsc;
        this.orderByExpressionDesc = orderByExpressionDesc;
    }

    @Override
    public String value() {
        return value;
    }

    public static SortByNode fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SortByNode sortByNode : SortByNode.values()) {
            if (sortByNode.value.equals(value)) {
                return sortByNode;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + SortByNode.class.getName() + " with value: " + value);
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
