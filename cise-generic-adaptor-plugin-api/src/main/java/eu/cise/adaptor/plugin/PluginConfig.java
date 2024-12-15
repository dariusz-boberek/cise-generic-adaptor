package eu.cise.adaptor.plugin;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorRuntimeException;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceRoleType;
import eu.cise.servicemodel.v1.service.ServiceType;
import org.aeonbits.owner.Config;

import java.util.StringJoiner;

import static java.util.Arrays.stream;

/**
 * The interface for CISE plugin configuration.
 * <p>
 * It must be extended by the plugin configuration implementation
 * to allow the generic adaptor to have access to the plugin CISE services configuration
 */
public interface PluginConfig extends Config {


    /**
     * Retrieves the unique identifier of the service in CISE, following a URN scheme.
     * This identifier ensures consistent, location-independent referencing of the service.
     *
     * @return String representing the service's unique identifier in URN format.
     */
    @Key("service-id")
    String getServiceId();

    /**
     * Determines the role of the service in CISE communication patterns.
     * The role can be either 'provider' (disseminating information) or 'consumer' (receiving or subscribing to information).
     *
     * @return String specifying the service role, such as 'provider' or 'consumer'.
     */
    @Key("service-role")
    String getServiceRole();

    /**
     * Identifies the type of communication pattern the service supports.
     * These patterns include Pull, Push, and Subscribe, defining how the service interacts in data exchange.
     *
     * @return String indicating the supported service operation pattern.
     */
    @Key("service-operation")
    String getServiceOperation();

    /**
     * Defines the main type of data or entity the service handles, indicating the primary function of the service.
     * For example, a 'VesselService' would primarily handle vessel-related data.
     *
     * @return String representing the primary data type handled by the service.
     */
    @Key("service-type")
    String getServiceType();

    /**
     * Converts the properties of this PluginConfig implementation into a structured, readable string format.
     * This representation includes the class name, and all get-method names and their corresponding values in a comma-separated format.
     *
     * @return String formatted as "{ ClassName {methodName: 'value', ...} }", encapsulating the plugin configuration.
     */
    default String pluginConfigToString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        stream(this.getClass().getDeclaredMethods())
                .filter(method -> method.getName().startsWith("get"))
                .filter(method -> !method.getName().equals("getMBeanInfo"))
                .forEach(method -> {
                    try {
                        Object value = method.invoke(this);
                        joiner.add(String.format("%s: '%s'", method.getName(), value.toString()));
                    } catch (Exception ignored) {
                    }
                });
        String className = stream(this.getClass().getInterfaces())
                .filter(PluginConfig.class::isAssignableFrom)
                .findFirst()
                .map(Class::getSimpleName)
                .orElse("PluginConfig");
        return className + " " + joiner.toString();
    }

    /**
     * Validates the configuration parameters of the service.
     * It checks for the presence and non-emptiness of mandatory parameters and throws a runtime exception if any are missing or empty.
     *
     * @throws CiseAdaptorRuntimeException if any mandatory service configuration parameter is missing or empty.
     */
    default void serviceConfigurationValidation() {
        if (isNullOrTrimmedEmpty(getServiceId())) {
            throw new CiseAdaptorRuntimeException("service-id is mandatory");
        }
        if (isNullOrTrimmedEmpty(getServiceRole())) {
            throw new CiseAdaptorRuntimeException("service-role is mandatory");
        }
        if (isNullOrTrimmedEmpty(getServiceOperation())) {
            throw new CiseAdaptorRuntimeException("service-operation is mandatory");
        }
        if (isNullOrTrimmedEmpty(getServiceType())) {
            throw new CiseAdaptorRuntimeException("service-type is mandatory");
        }
    }

    private boolean isNullOrTrimmedEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * This method is used to generate the CISE Service from the Plugin Configuration
     * @return the generated CISE Service
     * @throws CiseAdaptorRuntimeException the method first validates the  required properties (Service ID, Operation, Role and Type)
     *         and if anything is missing it throws this exception
     */
    default  Service generateCISEServiceFromConfig() throws CiseAdaptorRuntimeException {
        serviceConfigurationValidation();
        Service service = new Service();
        service.setServiceID(getServiceId());
        service.setServiceOperation(ServiceOperationType.fromValue(getServiceOperation()));
        service.setServiceRole(ServiceRoleType.fromValue(getServiceRole()));
        service.setServiceType(ServiceType.fromValue(getServiceType()));
        return service;
    }

}
