package eu.cise.adaptor.core.servicehandler.port.out;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

/**
 * Represents the port used for outgoing communication from the Service Handler to the Message Handler.
 * This port plays a critical role in the process of sending messages from the Service Handler to the CISE Node,
 * via the Message Handler.
 */
public interface OutgoingServiceHandlerToMessageHandlerPort {

    /**
     * This method handle the routing of the message from the ServiceHandler reach the MessageHandler.
     *
     * @param message the CISE Message to share with the MessageHandler
     * @return The generated Synchronous Acknowledgment which carries the result of the message delivery to MessageHandler
     */
    Acknowledgement forwardMessageToMessageHandler(Message message);
}
