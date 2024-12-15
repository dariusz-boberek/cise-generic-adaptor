package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Enum to use as value catalog for QueryByExample API requests
 */
public enum QueryByExample implements ValueEnum<String> {
    BEST_EFFORT("BestEffort"),
    EXACT_SEARCH("ExactSearch");

    private final String value;

    QueryByExample(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    public static QueryByExample fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (QueryByExample queryByExample : QueryByExample.values()) {
            if (queryByExample.value.equals(value)) {
                return queryByExample;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + QueryByExample.class.getName() + " with value: " + value);
    }

}
