package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Enum to use as value catalog for ContactFor API requests
 */
public enum ContactFor implements ValueEnum<String> {
    NODE("Node"),
    PARTICIPANT("Participant");

    private final String value;

    ContactFor(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    public static ContactFor fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ContactFor contactFor : ContactFor.values()) {
            if (contactFor.value.equals(value)) {
                return contactFor;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + ContactFor.class.getName() + " with value: " + value);
    }

}
