package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Enum to use as value catalog for Service Status API requests
 */
public enum ServiceStatus implements ValueEnum<String> {
    DRAFT("Draft"),
    ONLINE("Online"),
    TEST("Test"),
    MAINTENANCE("Maintenance"),
    DEPRECATED("Deprecated");

    private final String value;

    ServiceStatus(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    public static ServiceStatus fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ServiceStatus serviceStatus : ServiceStatus.values()) {
            if (serviceStatus.value.equals(value)) {
                return serviceStatus;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + ServiceStatus.class.getName() + " with value: " + value);
    }

}
