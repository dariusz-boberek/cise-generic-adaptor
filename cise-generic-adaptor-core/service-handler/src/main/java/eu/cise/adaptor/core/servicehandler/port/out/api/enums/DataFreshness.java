package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Enum to use as value catalog for Data Freshness API requests
 */
public enum DataFreshness implements ValueEnum<String> {
    HISTORIC("Historic"),
    REAL_TIME("RealTime"),
    NEARLY_REAL_TIME("NearlyRealTime"),
    UNKNOWN("Unknown");

    private final String value;

    DataFreshness(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    public static DataFreshness fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (DataFreshness dataFreshness : DataFreshness.values()) {
            if (dataFreshness.value.equals(value)) {
                return dataFreshness;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + DataFreshness.class.getName() + " with value: " + value);
    }

}
