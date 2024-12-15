package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Enum to use as value catalog for Service Model Version API requests
 */
public enum ServiceModelVersion implements ValueEnum<String> {
    V_1_5_3("1.5.3");

    private final String value;

    ServiceModelVersion(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    public static ServiceModelVersion fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ServiceModelVersion serviceModelVersion : ServiceModelVersion.values()) {
            if (serviceModelVersion.value.equals(value)) {
                return serviceModelVersion;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + ServiceModelVersion.class.getName() + " with value: " + value);
    }

}
