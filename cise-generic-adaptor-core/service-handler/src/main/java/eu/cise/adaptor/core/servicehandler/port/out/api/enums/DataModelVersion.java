package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Enum to use as value catalog for Data Model Version API requests
 */
public enum DataModelVersion implements ValueEnum<String> {
    V_1_5_3("1.5.3");

    private final String value;

    DataModelVersion(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    public static DataModelVersion fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (DataModelVersion dataFreshness : DataModelVersion.values()) {
            if (dataFreshness.value.equals(value)) {
                return dataFreshness;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + DataModelVersion.class.getName() + " with value: " + value);
    }

}
