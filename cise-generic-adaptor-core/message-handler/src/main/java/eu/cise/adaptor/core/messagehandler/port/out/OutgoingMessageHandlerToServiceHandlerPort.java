package eu.cise.adaptor.core.messagehandler.port.out;

import eu.cise.servicemodel.v1.message.Message;

/**
 * Represents the outgoing port of the Message Handler to the Service Handler in the Generic Adaptor.
 * It acts as a wire for messages from the Message Handler to the Service Handler, facilitating the communication between
 * these two components.
 */
public interface OutgoingMessageHandlerToServiceHandlerPort {

    /**
     * This method is used to pass the CISE Message from the Message Handler to the Service Handler hexagon.
     * @param message The message to pass to the ServiceHandler hexagon
     */
    void handleMessage(Message message);

}
