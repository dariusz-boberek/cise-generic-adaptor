package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Enum to use as value catalog for Sort By Authority properties API requests
 */
public enum SortByAuthority implements SortByEnum {
    COUNTRY("country", "a.country asc, a.name asc", "a.country desc, a.name asc"),
    NAME("name", "a.name asc, a.country asc", "a.name desc, a.country asc");

    private final String value;
    private final String orderByExpressionAsc;
    private final String orderByExpressionDesc;

    SortByAuthority(String value, String orderByExpressionAsc, String orderByExpressionDesc) {
        this.value = value;
        this.orderByExpressionAsc = orderByExpressionAsc;
        this.orderByExpressionDesc = orderByExpressionDesc;
    }

    @Override
    public String value() {
        return value;
    }

    public static SortByAuthority fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SortByAuthority sortByAuthority : SortByAuthority.values()) {
            if (sortByAuthority.value.equals(value)) {
                return sortByAuthority;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + SortByAuthority.class.getName() + " with value: " + value);
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
