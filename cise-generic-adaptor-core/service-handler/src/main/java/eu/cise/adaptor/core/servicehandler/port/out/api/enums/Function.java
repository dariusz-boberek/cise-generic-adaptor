package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Enum to use as value catalog for Function API requests
 */
public enum Function implements ValueEnum<String> {
    VTM("VTM"),
    SAFETY("Safety"),
    SECURITY("Security"),
    SAR("SAR"),
    OPERATION("Operation"),
    FISHERIES_WARNING("FisheriesWarning"),
    FISHERIES_MONITORING("FisheriesMonitoring"),
    FISHERIES_OPERATION("FisheriesOperation"),
    ENVIRONMENT_MONITORING("EnvironmentMonitoring"),
    ENVIRONMENT_WARNING("EnvironmentWarning"),
    ENVIRONMENT_RESPONSE("EnvironmentResponse"),
    CUSTOMS_MONITORING("CustomsMonitoring"),
    CUSTOMS_OPERATION("CustomsOperation"),
    BORDER_MONITORING("BorderMonitoring"),
    BORDER_OPERATION("BorderOperation"),
    LAW_ENFORCEMENT_MONITORING("LawEnforcementMonitoring"),
    LAW_ENFORCEMENT_OPERATION("LawEnforcementOperation"),
    DEFENCE_MONITORING("DefenceMonitoring"),
    COUNTER_TERRORISM("CounterTerrorism"),
    CSDP_TASK("CSDPTask"),
    NON_SPECIFIED("NonSpecified");

    private final String value;

    Function(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    public static Function fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (Function function : Function.values()) {
            if (function.value.equals(value)) {
                return function;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + Function.class.getName() + " with value: " + value);
    }
}
