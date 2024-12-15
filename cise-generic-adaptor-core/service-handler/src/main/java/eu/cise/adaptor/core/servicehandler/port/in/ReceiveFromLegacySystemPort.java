package eu.cise.adaptor.core.servicehandler.port.in;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorRuntimeException;
import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.common.logging.LogConfig;
import eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.adaptor.core.servicehandler.service.ServiceHandlerMessageBuilderService;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.CoreEntityPayload;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.service.Service;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

import static eu.cise.adaptor.core.common.logging.LoggerMessage.of;
import static eu.cise.adaptor.core.common.logging.MessageRouteValue.LS_TO_CISE;

/**
 * This class will consume data received from the legacy system, and convert it in the CISE Payload Format.
 * This class must be extended by CISE Generic Adaptor Plugins in order to
 * provide an implementation of the method {@link #handleCustomTranslation(String, MessageDataContext.MessageDataContextManager)}
 * to translate legacy system data into CISE <br>
 * When data is received from the legacy system, the plugin should call the method {@link #handleIncomingLegacyData(String, MessageDataContext.MessageDataContextManager)}
 */
public abstract class ReceiveFromLegacySystemPort {

    private static final AdaptorLogger log = LogConfig.configureLogging(ReceiveFromLegacySystemPort.class);
    private final ServiceHandlerMessageBuilderService messageBuilderService;
    private final Service currentService;

    /**
     * The instance of this class needs specific information about how to manage the Legacy System conversion
     *
     * @param serviceHandlerMessageBuilderService Service handler hexagon business logic provided to the plugin during the startup
     * @param currentService                      CISE Service represented by the plugin
     */
    protected ReceiveFromLegacySystemPort(ServiceHandlerMessageBuilderService serviceHandlerMessageBuilderService, Service currentService) {
        this.messageBuilderService = serviceHandlerMessageBuilderService;
        this.currentService = currentService;
    }

    /**
     * Handles incoming legacy system data - main port API method to access hexagon.
     * This overloaded method should be used when there is no need for a payload or the payload has already been
     * set to the {@link MessageDataContext.MessageDataContextManager#cisePayload(CoreEntityPayload)}
     *
     * @param messageDataContextManager The data context builder with the information received from the Legacy System.
     * @return Acknowledgement status as a String.
     * @throws CiseAdaptorValidationException if mandatory field for given communication patter was not present or didn't pass validation.
     */
    public List<Pair<RegisteredMessage, Acknowledgement>> handleIncomingLegacyData(MessageDataContext.MessageDataContextManager messageDataContextManager) throws CiseAdaptorValidationException {
       return handleIncomingLegacyData(null, messageDataContextManager);
    }


        /**
         * Handles incoming legacy system data - main port API method to access hexagon.
         *
         * @param legacySystemPayload       The payload from the legacy system.
         * @param messageDataContextManager The data context builder with the information received from the Legacy System.
         * @return Acknowledgement status as a String.
         * @throws CiseAdaptorValidationException if mandatory field for given communication patter was not present or didn't pass validation.
         */
    public List<Pair<RegisteredMessage, Acknowledgement>> handleIncomingLegacyData(String legacySystemPayload, MessageDataContext.MessageDataContextManager messageDataContextManager) throws CiseAdaptorValidationException {

        if (messageBuilderService == null) {
            String errorMessage = "ReceiveFromLegacySystemPort needs to have the serviceHandlerMessageBuilderService set. Please use the setter method";
            log.error(of(errorMessage).addRoutingAttribute(LS_TO_CISE));
            throw new CiseAdaptorRuntimeException(errorMessage);
        }

        // Adding info to messageDataContextManager
        messageDataContextManager.currentService(currentService);

        MessageDataContext messageDataContext;
        if (legacySystemPayload == null || legacySystemPayload.trim().isEmpty()) {
            messageDataContext = messageDataContextManager.buildContext();
        } else {
            try {
                messageDataContext = handleCustomTranslation(legacySystemPayload, messageDataContextManager);
            } catch (CiseAdaptorValidationException exception) {
                log.error(of("Error during translation").addRoutingAttribute(LS_TO_CISE), exception);
                throw exception;
            }
        }
        return messageBuilderService.handleMessage(messageDataContext);
    }

    /**
     * Retrieve the message identified by its ID (CISE property MessageId)
     *
     * @param referenceMessageId MessageId
     * @return RegisteredMessage instance with the referenced message
     */
    public Optional<Message> getMessageById(String referenceMessageId) {
        return messageBuilderService.getMessageById(referenceMessageId);
    }

    /**
     * Retrieve all the messages with the same correlationId of the referred message (by the same CISE property CorrelationId)
     *
     * @param referenceMessageId MessageId
     * @return List of RegisteredMessage instances with the same correlationId of the referenced message, ordered from the newest to the oldest
     */
    public List<RegisteredMessage> getMessageHistoryById(String referenceMessageId) {
        return messageBuilderService.getMessageHistoryById(referenceMessageId);
    }

    /**
     * Retrieve all the messages with the same contextId (CISE property ContextId)
     *
     * @param contextId ContextId
     * @return List of RegisteredMessage instances with the same contextId, ordered from the newest to the oldest
     */
    public List<RegisteredMessage> getMessageHistoryByContextId(String contextId) {
        return messageBuilderService.getMessageHistoryByContextId(contextId);
    }

    /**
     * Retrieve all the messages with the same correlationId of the referred message (by the same CISE property CorrelationId)
     * and the messages with the same contextId (CISE property ContextId)
     *
     * @param referenceMessageId MessageId
     * @param contextId          ContextId
     * @return List of RegisteredMessage instances with the same correlationId of the referred message
     * and with the contextId given in input, ordered from the newest to the oldest
     */
    public List<RegisteredMessage> getMessageHistory(String referenceMessageId, String contextId) {
        return messageBuilderService.getMessageHistory(referenceMessageId, contextId);
    }

    /**
     * Method to be implemented by the plugin.
     * It receives the Legacy System data as String payload and the method implementation provides the conversion to the CISE Entity Payload
     * For creating a CISE PullRequest message to the CISE Node you may want to supply a PayloadSelector along with Payload.
     * refer to <a href="https://emsa.europa.eu/cise-documentation/The-PayloadSelector-for-query-by-example_891637594.html">PayloadSelector</a> for the details.
     *
     * @param legacySystemPayload       The payload data received from the Legacy System.
     * @param messageDataContextManager The manager object that includes the reference message from the legacy system and a list of previously saved messages.
     * @return An object representing the translated to CISE data.
     * @throws CiseAdaptorValidationException if mandatory field for given communication patter was not present or didn't pass validation.
     */
    public abstract MessageDataContext handleCustomTranslation(String legacySystemPayload, MessageDataContext.MessageDataContextManager messageDataContextManager) throws CiseAdaptorValidationException;

}