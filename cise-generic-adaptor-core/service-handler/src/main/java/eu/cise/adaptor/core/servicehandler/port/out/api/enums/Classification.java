package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Enum to use as value catalog for Classification API requests
 */
public enum Classification implements ValueEnum<String> {
    UNCLASSIFIED("Unclassified"),
    EU_RESTRICTED("EURestricted");

    private final String value;

    Classification(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    public static Classification fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (Classification classification : Classification.values()) {
            if (classification.value.equals(value)) {
                return classification;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + Classification.class.getName() + " with value: " + value);
    }
}
