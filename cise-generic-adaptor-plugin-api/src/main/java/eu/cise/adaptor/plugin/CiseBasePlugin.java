package eu.cise.adaptor.plugin;

import eu.cise.adaptor.core.servicehandler.port.in.ReceiveFromLegacySystemPort;
import eu.cise.adaptor.core.servicehandler.port.out.SendToLegacySystemPort;
import eu.cise.adaptor.core.servicehandler.port.out.ServiceHandlerManagementAPIPort;
import eu.cise.adaptor.core.servicehandler.service.ServiceHandlerMessageBuilderService;
import org.pf4j.Plugin;

/**
 * The abstract base class for CISE plugins. This class provides a foundation for plugins that interact with legacy systems.
 * Implementing classes are expected to provide specific functionalities for bootstrapping the plugin, receiving from, and sending to the legacy system.
 * <p>
 * Stakeholder who wish to extend the CISE Generic Adaptor should implement this class to create custom plugin, or extend it many times to create more than one plugin.
 * The provided methods offer a structured way to integrate with legacy systems without needing in-depth knowledge of the internal workings of the CISE Generic Adaptor..
 */
public abstract class CiseBasePlugin extends Plugin {

    /**
     * Initializes the plugin. This method is invoked first when the plugin is instantiated.
     * It sets up necessary configurations and services to ensure the plugin's proper operation.
     *
     * @param serviceHandlerMessageBuilderService The service responsible for handling messages from the legacy system.
     * @param serviceHandlerManagementAPIPort The service tha exposes the Node APIs.
     */
    public abstract void bootStrapPlugin(ServiceHandlerMessageBuilderService serviceHandlerMessageBuilderService, ServiceHandlerManagementAPIPort serviceHandlerManagementAPIPort);

    /**
     * Retrieves the incoming to adaptor port implementation for the plugin, which is responsible for handling incoming messages from the legacy system.
     * In certain communication patterns where "ReceiveFromLegacySystemPort" is not required, it's acceptable for this method to return null.
     *
     * @param serviceHandlerMessageBuilderService The service responsible for handling messages from the legacy system.
     * @return The ReceiveFromLegacySystemPort for the plugin or null if not applicable for the communication pattern.
     */
    public abstract ReceiveFromLegacySystemPort getReceiveFromLegacySystemPort(ServiceHandlerMessageBuilderService serviceHandlerMessageBuilderService);

    /**
     * Retrieves the outgoing from adaptor port implementation for the plugin, which is responsible for sending messages to the legacy system.
     * In certain communication patterns where "SendToLegacySystemPort" is not required, it's acceptable for this method to return null.
     *
     * @return The SendToLegacySystemPort for the plugin or null if not applicable for the communication pattern.
     */
    public abstract SendToLegacySystemPort getSendToLegacySystemPort();

    /**
     * Retrieves the configuration settings for the plugin. This configuration provides essential details
     * required for the plugin's operation, such as service IDs, roles, and operations.
     *
     * @return The configuration settings for the plugin.
     */
    public abstract PluginConfig getPluginConfiguration();

}
