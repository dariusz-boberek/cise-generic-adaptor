package eu.cise.adaptor.core.messagehandler.port.out;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

/**
 * This interface is defining a contract for a port that handles outgoing messages to the CISE Node.
 */
public interface OutgoingMessageHandlerToNodePort {

    //TODO: with argument change method name should be also changed, proposition: sendCiseMessage

    /**
     * Sends a signed CISE message to the node and returns an acknowledgement.
     *
     * @param signedCiseMessage A signed  message
     * @return An Acknowledgement object representing the response from the CISE Node, including Sync Ack for successful
     *         processing or error indications.
     */
    Acknowledgement sendStringMessage(Message signedCiseMessage);

}
