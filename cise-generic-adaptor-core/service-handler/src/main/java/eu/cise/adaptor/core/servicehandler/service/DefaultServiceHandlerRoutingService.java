package eu.cise.adaptor.core.servicehandler.service;

import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.common.logging.LogConfig;
import eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage;
import eu.cise.adaptor.core.servicehandler.port.out.MessageRepositoryPort;
import eu.cise.adaptor.core.servicehandler.port.out.SendToLegacySystemPort;
import eu.cise.adaptor.core.servicehandler.port.out.UpdateLegacySystemResult;
import eu.cise.servicemodel.v1.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static eu.cise.adaptor.core.common.logging.LoggerMessage.of;
import static eu.cise.adaptor.core.common.logging.MessageAttributeKey.CONTEXT_ID;
import static eu.cise.adaptor.core.common.logging.MessageRouteValue.CISE_TO_LS;
import static eu.cise.adaptor.core.common.message.MessageTypeEnum.UNDEFINED;
import static java.util.Optional.ofNullable;

/**
 * Default implementation of the ServiceHandlerRoutingService interface.
 * It implements the logic required to route messages to the correct plugin based on the CISE Service ID and other criteria.
 *
 * @see ServiceHandlerRoutingService
 */
public final class DefaultServiceHandlerRoutingService implements ServiceHandlerRoutingService {

    private static final AdaptorLogger log = LogConfig.configureLogging(DefaultServiceHandlerRoutingService.class);

    private final Map<String, SendToLegacySystemPort> sendToLegacySystemPortMap;

    private final MessageRepositoryPort messageRepositoryPort;


    public DefaultServiceHandlerRoutingService(Map<String, SendToLegacySystemPort> sendToLegacySystemPortMap, MessageRepositoryPort messageRepositoryPort) {
        this.sendToLegacySystemPortMap = sendToLegacySystemPortMap;
        this.messageRepositoryPort = messageRepositoryPort;
    }

    /**
     * Processes incoming CISE messages and routes them for appropriate handling.
     * <p>
     * Important:
     * <ul>
     * <li>The message must already have a designated recipient.</li>
     * <li>When updating legacy systems, the message history used will not include the latest incoming message (by design).</li>
     * </ul>
     *
     * @param message incoming CISE message to be processed
     */
    public void handleMessage(Message message) {
        log.info(of(UNDEFINED, message.getMessageID(), "message is being processed by DefaultServiceHandlerRoutingService").addRoutingAttribute(CISE_TO_LS));
        RegisteredMessage registeredMessage = RegisteredMessage.ofCISEMessageDateReceived(message);
        final List<RegisteredMessage> messagesHistory = new ArrayList<>();
        if (messageRepositoryPort != null) {
            messagesHistory.addAll(messageRepositoryPort.getMessagesHistory(message.getCorrelationID(), message.getContextID()));
            messageRepositoryPort.save(registeredMessage);
        } else {
            log.debug(of(UNDEFINED, registeredMessage.getMessageId(), "cannot reach database").addRoutingAttribute(CISE_TO_LS));
        }
        String messageServiceID = message.getRecipient().getServiceID();
        log.info(of(UNDEFINED, registeredMessage.getMessageId(), "message service id: {}", messageServiceID).addRoutingAttribute(CISE_TO_LS).addAttribute(CONTEXT_ID, registeredMessage.getContextId()));
        ofNullable(sendToLegacySystemPortMap.get(messageServiceID)).ifPresentOrElse(sendToLegacySystemPort -> {
            UpdateLegacySystemResult updateLegacySystemResult = sendToLegacySystemPort.updateLegacySystem(registeredMessage, registeredMessage.getMessageId(), messagesHistory, registeredMessage.getContextId());
            log.debug(of(UNDEFINED, registeredMessage.getMessageId(), "updateLegacySystemResult content: {}", updateLegacySystemResult).addRoutingAttribute(CISE_TO_LS));
        }, () -> log.error(of(UNDEFINED, registeredMessage.getMessageId(), "sendToLegacySystemPort is not available to handle the message.").addRoutingAttribute(CISE_TO_LS)));
    }

}