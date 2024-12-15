package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Enum to use as value catalog for Sort Order API requests
 */
public enum SortOrder implements ValueEnum<String> {
    ASC("Ascending", "asc"),
    DESC("Descending", "desc");

    private final String value;
    private final String orderByExpression;

    SortOrder(String value, String orderByExpression) {
        this.value = value;
        this.orderByExpression = orderByExpression;
    }

    @Override
    public String value() {
        return value;
    }

    public static SortOrder fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SortOrder sortOrder : SortOrder.values()) {
            if (sortOrder.value.equals(value)) {
                return sortOrder;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + SortOrder.class.getName() + " with value: " + value);
    }

    /**
     * Returns the orderByExpression of this Enum object <b>Do not use in API calls</b>!
     * <p>Note: this should not be used when executing API calls. Instead use the {@link SortOrder#value}</p>
     * @return The orderByExpression as String
     */
    public String getOrderByExpression() {
        return orderByExpression;
    }
}
