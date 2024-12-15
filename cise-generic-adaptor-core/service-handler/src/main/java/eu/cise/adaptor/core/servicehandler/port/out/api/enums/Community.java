package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Enum to use as value catalog for Community API requests
 */
public enum Community implements ValueEnum<String> {
    GENERAL_LAW_ENFORCEMENT("GeneralLawEnforcement"),
    CUSTOMS("Customs"),
    MARINE_ENVIRONMENT("MarineEnvironment"),
    MARITIME_SAFETY_SECURITY("MaritimeSafetySecurity"),
    DEFENCE_MONITORING("DefenceMonitoring"),
    FISHERIES_CONTROL("FisheriesControl"),
    BORDER_CONTROL("BorderControl"),
    OTHER("Other"),
    NON_SPECIFIED("NonSpecified");

    private final String value;

    Community(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    public static Community fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (Community community : Community.values()) {
            if (community.value.equals(value)) {
                return community;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + Community.class.getName() + " with value: " + value);
    }
}
