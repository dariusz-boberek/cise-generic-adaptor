package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Enum to use as value catalog for Service Operation API requests
 */
public enum ServiceOperation implements ValueEnum<String> {
    PULL("Pull"),
    PUSH("Push"),
    SUBSCRIBE("Subscribe"),
    FEEDBACK("Feedback");

    private final String value;

    ServiceOperation(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    public static ServiceOperation fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ServiceOperation serviceOperation : ServiceOperation.values()) {
            if (serviceOperation.value.equals(value)) {
                return serviceOperation;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + ServiceOperation.class.getName() + " with value: " + value);
    }

}
