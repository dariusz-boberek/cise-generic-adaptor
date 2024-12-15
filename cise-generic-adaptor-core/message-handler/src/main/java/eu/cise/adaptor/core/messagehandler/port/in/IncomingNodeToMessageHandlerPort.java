package eu.cise.adaptor.core.messagehandler.port.in;

import eu.cise.adaptor.core.messagehandler.service.MessageHandlerVerificationService;
import eu.cise.servicemodel.v1.message.Acknowledgement;

/**
 * Interface for handling incoming messages from the CISE Node. This port defines the contract
 * for receiving and processing messages in the adaptor.
 *
 * <p>Responsibilities:<br>
 * <ul>
 * <li>Defines an endpoint for receiving messages from the CISE Node.</li>
 * <li>Specifies the method for handling and validating incoming messages.</li>
 * </ul>
 *
 * <p>Usage:
 * Implement this interface to handle incoming messages according to the CISE adaptor's architecture.
 *
 * @see MessageHandlerVerificationService
 */
public interface IncomingNodeToMessageHandlerPort {

    /**
     * This method is the entrypoint where messages from the CISE Node reach the Generic Adaptor. The implementation of this method should focus on
     * the validation of the incoming message structure and signature.
     * @param xmlMessage the CISE Message received
     * @return The generated Synchronous Acknowledgment which carries the result of the initial validation of the message back to the caller
     */
    Acknowledgement requestSyncAck(String xmlMessage);

}