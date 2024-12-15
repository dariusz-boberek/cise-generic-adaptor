package eu.cise.adaptor.core.messagehandler.service;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

/**
 * Represents the service responsible for the delivery of messages from the Service Handler to the CISE Node.
 * <p>
 * This service is a part of the Message Handler hexagon in the Generic Adaptor architecture. It handles
 * the final preparation and sending of CISE messages to the Node.
 */
public interface MessageHandlerDeliveryService {

    /**
     * This method should be called when the Message has been received from the Service Handler and it should be sent to the CISE Node.
     * The implementation of this method should perform 3 actions:
     * <ul>
     * <li>Update the Creation Date of the CISE Message</li>
     * <li>Sign the CISE Message</li>
     * <li>Route the CISE Message to the OutgoingMessageHandlerToNodePort in order to be sent to the CISE Node</li>
     * </ul>
     *
     * @param message The message to be sent to the CISE Node
     * @return The Synchronous Acknowledgement generated by the CISE Node
     */
    Acknowledgement prepareAndForwardMessageToCiseNode(Message message);

}
