package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Enum to use as value catalog for End Point Protocol API requests
 */
public enum EndpointProtocol implements ValueEnum<String> {
    REST("REST"),
    SOAP("SOAP");

    private final String value;

    EndpointProtocol(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    public static EndpointProtocol fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (EndpointProtocol endpointProtocol : EndpointProtocol.values()) {
            if (endpointProtocol.value.equals(value)) {
                return endpointProtocol;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + EndpointProtocol.class.getName() + " with value: " + value);
    }
}
