package eu.cise.adaptor.core.servicehandler.port.in;

import eu.cise.servicemodel.v1.message.Message;

/**
 * Interface for handling incoming messages incoming from MessageHandler.
 * This port is the entry point of the Service Handler and calls the ServiceHandlerRoutingService
 *
 * <p>Responsibilities:
 * <ul>
 * <li>Defines an endpoint for receiving messages from the Message Handler.</li>
 * <li>Manage the message routing to the Legacy System.</li>
 * </ul>
 *
 * <p>Usage:
 * Implement this interface to handle incoming CISE messages.
 *
 */
public interface IncomingMessageHandlerToServiceHandlerPort {

    /**
     * This method is the entrypoint where messages from the MessageHandler reach the ServiceHandler.
     * The implementation of this method should focus on the routing of the incoming message.
     *
     * @param message the CISE Message received
     */
    void handleMessage(Message message);

}