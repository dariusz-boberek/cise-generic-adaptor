package eu.cise.adaptor.core.adapters.messagehandler.in;


import eu.cise.adaptor.core.messagehandler.port.in.IncomingServiceHandlerToMessageHandlerPort;
import eu.cise.adaptor.core.messagehandler.service.MessageHandlerDeliveryService;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

/**
 * Default adapter implementing the {@code IncomingServiceHandlerToMessageHandlerPort} used for Domain Testing.
 *
 * <p>This adapter is tasked with the practical aspects of processing incoming messages
 * from the Service Handler. It prepares messages for delivery to the CISE Node,
 * including updating message timestamps, signing messages, and managing their dispatch.
 * It plays a crucial role in maintaining the integrity and compliance of the communication
 * with the CISE Node.
 *
 * @see IncomingServiceHandlerToMessageHandlerPort
 */
public class DefaultIncomingServiceHandlerToMessageHandlerAdapter implements IncomingServiceHandlerToMessageHandlerPort {

    private MessageHandlerDeliveryService messageHandlerDeliveryService;

    public DefaultIncomingServiceHandlerToMessageHandlerAdapter(MessageHandlerDeliveryService messageHandlerDeliveryService) {
        this.messageHandlerDeliveryService = messageHandlerDeliveryService;
    }

    @Override
    public Acknowledgement sendMessage(Message message) {
        return messageHandlerDeliveryService.prepareAndForwardMessageToCiseNode(message);
    }
}
