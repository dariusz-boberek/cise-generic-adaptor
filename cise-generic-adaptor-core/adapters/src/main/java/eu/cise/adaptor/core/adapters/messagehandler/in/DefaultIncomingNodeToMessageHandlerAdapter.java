package eu.cise.adaptor.core.adapters.messagehandler.in;



import eu.cise.adaptor.core.messagehandler.port.in.IncomingNodeToMessageHandlerPort;
import eu.cise.adaptor.core.messagehandler.service.MessageHandlerVerificationService;
import eu.cise.servicemodel.v1.message.Acknowledgement;

import java.util.Set;

/**
 * Default adapter implementing the {@code IncomingNodeToMessageHandlerPort} used for Domain Testing.
 *
 * <p>This adapter serves as the initial point of interaction in the CISE Adaptor architecture
 * for handling messages received directly from the CISE Node. It is responsible for the
 * initial routing and preliminary processing of these messages, acting as a bridge to the
 * internal message processing services within the Generic Adaptor.
 *
 * <p>Upon receiving a message, this adapter initiates the processing workflow by
 * coordinating with the appropriate internal services for further handling and validation.
 * Its primary role is to ensure that the incoming messages are correctly routed to
 * the suitable components within the Adaptor.
 *
 * @see IncomingNodeToMessageHandlerPort
 */
public class DefaultIncomingNodeToMessageHandlerAdapter implements IncomingNodeToMessageHandlerPort {

    private final MessageHandlerVerificationService messageHandlerVerificationService;

    private final Set<String> availableServiceIds;

    public DefaultIncomingNodeToMessageHandlerAdapter(MessageHandlerVerificationService messageHandlerVerificationService, Set<String> availableServiceIds) {
        this.messageHandlerVerificationService = messageHandlerVerificationService;
        this.availableServiceIds = availableServiceIds;
    }

    @Override
    public Acknowledgement requestSyncAck(String xmlMessage) {
        return messageHandlerVerificationService.requestSyncAck(xmlMessage, availableServiceIds);
    }
}
