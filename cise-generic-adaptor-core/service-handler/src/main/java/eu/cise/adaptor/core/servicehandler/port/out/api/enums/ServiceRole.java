package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Enum to use as value catalog for Service Role API requests
 */
public enum ServiceRole implements ValueEnum<String> {
    CONSUMER("Consumer"),
    PROVIDER("Provider");

    private final String value;

    ServiceRole(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    public static ServiceRole fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ServiceRole serviceRole : ServiceRole.values()) {
            if (serviceRole.value.equals(value)) {
                return serviceRole;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + ServiceRole.class.getName() + " with value: " + value);
    }

}
