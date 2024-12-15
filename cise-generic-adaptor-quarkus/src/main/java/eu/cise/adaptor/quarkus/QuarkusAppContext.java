package eu.cise.adaptor.quarkus;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorRuntimeException;
import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.common.logging.LogConfig;
import eu.cise.adaptor.core.messagehandler.port.out.OutgoingMessageHandlerToNodePort;
import eu.cise.adaptor.core.messagehandler.port.out.OutgoingMessageHandlerToServiceHandlerPort;
import eu.cise.adaptor.core.messagehandler.service.DefaultMessageHandlerDeliveryService;
import eu.cise.adaptor.core.messagehandler.service.DefaultMessageHandlerVerificationService;
import eu.cise.adaptor.core.messagehandler.service.MessageHandlerDeliveryService;
import eu.cise.adaptor.core.messagehandler.service.MessageHandlerVerificationService;
import eu.cise.adaptor.core.servicehandler.port.in.ReceiveFromLegacySystemPort;
import eu.cise.adaptor.core.servicehandler.port.out.MessageRepositoryPort;
import eu.cise.adaptor.core.servicehandler.port.out.OutgoingServiceHandlerToMessageHandlerPort;
import eu.cise.adaptor.core.servicehandler.port.out.SendToLegacySystemPort;
import eu.cise.adaptor.core.servicehandler.port.out.ServiceHandlerManagementAPIPort;
import eu.cise.adaptor.core.servicehandler.service.DefaultServiceHandlerMessageBuilderService;
import eu.cise.adaptor.core.servicehandler.service.DefaultServiceHandlerRoutingService;
import eu.cise.adaptor.core.servicehandler.service.ServiceHandlerMessageBuilderService;
import eu.cise.adaptor.core.servicehandler.service.ServiceHandlerRoutingService;
import eu.cise.adaptor.plugin.CiseBasePlugin;
import eu.cise.adaptor.plugin.PluginConfig;
import eu.cise.adaptor.quarkus.config.ConfigChecker;
import eu.cise.adaptor.quarkus.config.SignatureConfig;
import eu.cise.adaptor.quarkus.dispatcher.AdaptorDispatcher;
import eu.cise.adaptor.quarkus.dispatcher.CISEMessagesRestClient;
import eu.cise.adaptor.quarkus.dispatcher.QuarkusRestClient;
import eu.cise.adaptor.quarkus.nodeapi.service_registry_openapi_yaml.AuthorityApi;
import eu.cise.adaptor.quarkus.nodeapi.service_registry_openapi_yaml.ContactPersonApi;
import eu.cise.adaptor.quarkus.nodeapi.service_registry_openapi_yaml.NodeApi;
import eu.cise.adaptor.quarkus.nodeapi.service_registry_openapi_yaml.ParticipantApi;
import eu.cise.adaptor.quarkus.nodeapi.service_registry_openapi_yaml.ServiceApi;
import eu.cise.adaptor.quarkus.nodeapi.subscription_registry_openapi_yaml.api.SubscriptionApi;
import eu.cise.adaptor.quarkus.servicehandler.adapter.out.ServiceHandlerManagementAPIAdapter;
import eu.cise.dispatcher.rest.RestDispatcher;
import eu.cise.signature.SignatureService;
import eu.eucise.xml.DefaultXmlMapper;
import io.quarkus.runtime.Startup;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.pf4j.ClassLoadingStrategy;
import org.pf4j.DefaultPluginLoader;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginClassLoader;
import org.pf4j.PluginDescriptor;
import org.pf4j.PluginManager;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static eu.cise.adaptor.core.common.logging.LoggerMessage.of;
import static eu.cise.adaptor.core.common.logging.MessageRouteValue.CONTEXT;
import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;

@Startup
@ApplicationScoped
public class QuarkusAppContext {

    private static final AdaptorLogger log = LogConfig.configureLogging(QuarkusAppContext.class);

    @ConfigProperty(name = "quarkus.rest-client.cise-messages-rest-client.url")
    String ciseMessagesRestClientUrl;

    @ApplicationScoped
    @Produces
    public Map<String, SendToLegacySystemPort> produceSendToLegacySystemPorts() {
        return new HashMap<>();
    }

    @ApplicationScoped
    @Produces
    public Map<String, ReceiveFromLegacySystemPort> produceReceiveFromLegacySystemPorts() {
        return new HashMap<>();
    }

    @Produces
    ServiceHandlerMessageBuilderService createLegacyServiceHandlerRoutingService(OutgoingServiceHandlerToMessageHandlerPort outgoingServiceHandlerToMessageHandlerPort, MessageRepositoryPort messageRepositoryPort) {
        log.debug(of("creating DefaultServiceHandlerMessageBuilderService in QuarkusAppContext.").addRoutingAttribute(CONTEXT));
        return new DefaultServiceHandlerMessageBuilderService(outgoingServiceHandlerToMessageHandlerPort, messageRepositoryPort);
    }

    @Produces
    MessageHandlerDeliveryService getMessageHandlerDeliveryService(OutgoingMessageHandlerToNodePort outgoingMessageHandlerToNodePort, SignatureService signatureService) {
        return new DefaultMessageHandlerDeliveryService(outgoingMessageHandlerToNodePort, signatureService);
    }

    @Produces
    MessageHandlerVerificationService createMessageHandlerVerificationService(OutgoingMessageHandlerToServiceHandlerPort outgoingMessageHandlerToServiceHandlerPort, SignatureService signatureService) {
        return new DefaultMessageHandlerVerificationService(outgoingMessageHandlerToServiceHandlerPort, signatureService);
    }

    @Produces
    ServiceHandlerRoutingService createServiceHandlerRoutingService(MessageRepositoryPort messageRepositoryPort, Map<String, SendToLegacySystemPort> sendToLegacySystemPortMap) {
        log.debug(of("sendToLegacySystemPortMap reference id: {}", System.identityHashCode(sendToLegacySystemPortMap)).addRoutingAttribute(CONTEXT));
        return new DefaultServiceHandlerRoutingService(sendToLegacySystemPortMap, messageRepositoryPort);
    }

    @Produces
    AdaptorDispatcher createDispatcherDecorator(@RestClient CISEMessagesRestClient messagesRestClient) {
       return new AdaptorDispatcher( new RestDispatcher(new QuarkusRestClient(messagesRestClient), new DefaultXmlMapper()), ciseMessagesRestClientUrl);
    }

    @Produces
    ServiceHandlerManagementAPIPort createServiceHandlerManagementAPIAdapter(@RestClient ServiceApi serviceApi,
                                                                                @RestClient ContactPersonApi contactPersonApi,
                                                                                @RestClient AuthorityApi authorityApi,
                                                                                @RestClient NodeApi nodeApi,
                                                                                @RestClient ParticipantApi participantApi,
                                                                                @RestClient SubscriptionApi subscriptionApi) {
        return new ServiceHandlerManagementAPIAdapter(serviceApi, contactPersonApi, authorityApi, nodeApi, participantApi, subscriptionApi);
    }
    /**
     * Responsible for the initialization and registration of plugins in the system.
     * <ul>
     *     <li>Loading plugins: Discovers and loads available plugins.</li>
     *     <li>Bootstrapping: Initializes plugins and integrates them with the legacy message handler service.</li>
     *     <li>Port Registration: Registers the provided port implementations from the plugins.</li>
     * </ul>
     */
    @Startup
    @Produces
    List<CiseBasePlugin> registerPortsFromLoadedPlugins(
            PluginManager pluginManager,
            ServiceHandlerMessageBuilderService serviceHandlerMessageBuilderService,
            ServiceHandlerManagementAPIPort serviceHandlerManagementAPIAdapter,
            Map<String, SendToLegacySystemPort> sendToLegacySystemPortMap,
            Map<String, ReceiveFromLegacySystemPort> receiveFromLegacySystemPortMap) {

        log.debug(of("registerPortsFromPlugins ").addRoutingAttribute(CONTEXT));
        List<CiseBasePlugin> loadedPlugins = loadPlugins(pluginManager);

        for (CiseBasePlugin plugin : loadedPlugins) {
            PluginConfig pluginConfiguration = plugin.getPluginConfiguration();
            try {
                pluginConfiguration.serviceConfigurationValidation();
            } catch (CiseAdaptorRuntimeException exception) {
                log.error(of("Configuration validation failed for plugin {}: {}", plugin.getClass(), exception.getMessage()).addRoutingAttribute(CONTEXT));
                throw exception;
            }
            String pluginServiceId = pluginConfiguration.getServiceId();
            log.debug(of("Loading plugin: {}, with service-id: {}.", plugin.getClass(), pluginServiceId).addRoutingAttribute(CONTEXT));
            plugin.bootStrapPlugin(serviceHandlerMessageBuilderService, serviceHandlerManagementAPIAdapter);
            registerPorts(serviceHandlerMessageBuilderService, plugin, pluginServiceId, sendToLegacySystemPortMap, receiveFromLegacySystemPortMap);
        }

        return loadedPlugins; //TODO: @Produces need to return something, reference to plugins may be handy in future or we should use other annotation
    }

    /**
     * Create and validate configuration entries in application.properties
     *
     * @return The method is returning an instance of the `SignatureService` class.
     */
    @Produces
    @Startup
    SignatureService createSignatureService() {
        List<Exception> configurationValidationExceptions = new ArrayList<>();
        SignatureConfig signatureConfig = ConfigChecker.buildConfig(SignatureConfig.class, configurationValidationExceptions);
        //TODO: also add validation about CiseNodeClientConfig
        ConfigChecker.checkConfigurationValidationExceptions(configurationValidationExceptions);
        System.setProperty("signature.conf.dir", signatureConfig.confDir());
        return newSignatureService(new DefaultXmlMapper()).withKeyStoreName(signatureConfig.keystoreFileName()).withKeyStorePassword(signatureConfig.keystorePassword()).withPrivateKeyAlias(signatureConfig.privateKeyAlias()).withPrivateKeyPassword(signatureConfig.privateKeyPassword()).build();
    }

    /**
     * Creates and initializes a {@code PluginManager}.
     *
     * <p>This method establishes a {@code PluginManager}, loads all available plugins, starts them,
     * and sets the appropriate paths for plugin loading. It's essential for the startup phase
     * to ensure all plugins are ready for use.</p>
     *
     * @return an initialized {@code PluginManager}.
     */
    @Startup
    @Produces
    PluginManager createPluginManager() {
        log.debug(of("createPluginManager called.").addRoutingAttribute(CONTEXT));
        PluginManager defaultPluginManager = getDefaultPluginManager();
        defaultPluginManager.loadPlugins();
        defaultPluginManager.startPlugins();
        for (Path path : defaultPluginManager.getPluginsRoots()) {
            log.info(of("Path for plugin loading: {}.", path.toAbsolutePath()).addRoutingAttribute(CONTEXT));
            System.setProperty("adaptor.pluginsDir", path.toAbsolutePath().toString());
        }
        return defaultPluginManager;
    }

    /**
     * Loads and retrieves all the plugins of type {@code CiseBasePlugin}.
     *
     * <p>This method inspects the provided {@code PluginManager}, filters for plugins of type {@code CiseBasePlugin},
     * and returns them in a list format.</p>
     *
     * @param pluginManager the manager handling the plugins.
     * @return a list of loaded {@code CiseBasePlugin}s.
     */
    private List<CiseBasePlugin> loadPlugins(PluginManager pluginManager) {
        log.debug(of("loadPlugins called.").addRoutingAttribute(CONTEXT));
        List<CiseBasePlugin> loadedPlugins = pluginManager.getPlugins().stream().filter(pluginWrapper -> pluginWrapper.getPlugin() instanceof CiseBasePlugin).map(pluginWrapper -> (CiseBasePlugin) pluginWrapper.getPlugin()).collect(Collectors.toList());
        if (loadedPlugins == null || loadedPlugins.size() == 0) {
            String errorMsg = "No plugins of type CiseBasePlugin were found.";
            log.error(of(errorMsg).addRoutingAttribute(CONTEXT));
        } else {
            log.debug(of("loadedPlugins size: {} ", loadedPlugins.size()).addRoutingAttribute(CONTEXT));
        }
        return loadedPlugins;
    }

    /**
     * Registers the ports provided by the plugins to the system.
     *
     * <p>Handles registration of both {@code SendToLegacySystemPort} and {@code ReceiveFromLegacySystemPort}.
     * Warns in case a plugin doesn't provide implementation for a specific port.</p>
     *
     * @param serviceHandlerMessageBuilderService the legacy message handling service.
     * @param plugin                      the plugin whose ports are to be registered.
     * @param pluginServiceId             the service ID of the plugin.
     */
    private void registerPorts(
            ServiceHandlerMessageBuilderService serviceHandlerMessageBuilderService,
            CiseBasePlugin plugin,
            String pluginServiceId,
            Map<String, SendToLegacySystemPort> sendToLegacySystemPortMap,
            Map<String, ReceiveFromLegacySystemPort> receiveFromLegacySystemPortMap) {

        log.debug(of("sendToLegacySystemPortMap reference id: {}", System.identityHashCode(sendToLegacySystemPortMap)).addRoutingAttribute(CONTEXT));
        log.debug(of("receiveFromLegacySystemPortMap reference id: {}", System.identityHashCode(receiveFromLegacySystemPortMap)).addRoutingAttribute(CONTEXT));

        SendToLegacySystemPort currentSendToLegacySystemPort = plugin.getSendToLegacySystemPort();
        if (currentSendToLegacySystemPort != null) {
            log.debug(of("Registering SendToLegacySystemPort for service-id: {}.", pluginServiceId).addRoutingAttribute(CONTEXT));
            sendToLegacySystemPortMap.put(pluginServiceId, currentSendToLegacySystemPort);
        } else {
            log.warn(of("Plugin for service-id: {} is not providing implementation of SendToLegacySystemPort.", pluginServiceId).addRoutingAttribute(CONTEXT));
        }

        ReceiveFromLegacySystemPort currentReceiveFromLegacySystemPort = plugin.getReceiveFromLegacySystemPort(serviceHandlerMessageBuilderService);
        if (currentReceiveFromLegacySystemPort != null) {
            log.debug(of("Registering ReceiveFromLegacySystemPort for service-id: {}.", pluginServiceId).addRoutingAttribute(CONTEXT));
            receiveFromLegacySystemPortMap.put(pluginServiceId, currentReceiveFromLegacySystemPort);
        } else {
            log.warn(of("Plugin for service-id: {} is not providing implementation of ReceiveFromLegacySystemPort.", pluginServiceId).addRoutingAttribute(CONTEXT));
        }
    }

    private DefaultPluginManager getDefaultPluginManager() {
        log.debug(of("getDefaultPluginManager called.").addRoutingAttribute(CONTEXT));
        return new DefaultPluginManager() {
            @Override
            protected org.pf4j.PluginLoader createPluginLoader() {
                return new DefaultPluginLoader(this) {
                    @Override
                    protected PluginClassLoader createPluginClassLoader(Path pluginPath, PluginDescriptor pluginDescriptor) {
                        PluginClassLoader pluginClassLoader = new PluginClassLoader(this.pluginManager, pluginDescriptor, QuarkusAppContext.class.getClassLoader(), ClassLoadingStrategy.APD);
                        pluginClassLoader.addFile(pluginPath.toFile());
                        return pluginClassLoader;
                    }
                };
            }
        };
    }


}