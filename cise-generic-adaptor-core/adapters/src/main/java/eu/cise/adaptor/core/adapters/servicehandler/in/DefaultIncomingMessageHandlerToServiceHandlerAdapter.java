package eu.cise.adaptor.core.adapters.servicehandler.in;

import eu.cise.adaptor.core.servicehandler.port.in.IncomingMessageHandlerToServiceHandlerPort;
import eu.cise.adaptor.core.servicehandler.service.ServiceHandlerRoutingService;
import eu.cise.servicemodel.v1.message.Message;

/**
 * Default adapter implementing the {@code IncomingMessageHandlerToServiceHandlerPort} used for Domain Testing.
 *
 * <p>This adapter manages the transition of messages from the Message Handler to the Service Handler
 * in the Generic Adaptor. It acts as a crucial intermediary, ensuring messages are accurately conveyed
 * for further processing by the Service Handler.
 *
 * @see IncomingMessageHandlerToServiceHandlerPort
 */
public class DefaultIncomingMessageHandlerToServiceHandlerAdapter implements IncomingMessageHandlerToServiceHandlerPort {

    private final ServiceHandlerRoutingService messageHandlerVerificationService;

    public DefaultIncomingMessageHandlerToServiceHandlerAdapter(ServiceHandlerRoutingService messageHandlerVerificationService) {
        this.messageHandlerVerificationService = messageHandlerVerificationService;
    }

    @Override
    public void handleMessage(Message message) {
        messageHandlerVerificationService.handleMessage(message);
    }
}
