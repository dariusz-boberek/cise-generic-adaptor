package eu.cise.adaptor.core.adapters.messagehandler.out;

import eu.cise.adaptor.core.messagehandler.port.out.OutgoingMessageHandlerToNodePort;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

/**
 * Default Adapter implementing the {@code OutgoingMessageHandlerToNodePort} used for Domain Testing.
 *
 * <p>This adapter is responsible for managing the dispatch of outgoing messages from the Generic Adaptor to the CISE Node.
 * It acts as the final step in the message handling process, where it ensures that messages are correctly formatted,
 * signed, and sent to the CISE Node.
 *
 * <p>Upon preparing a message for dispatch, this adapter handles the communication with the CISE Node,
 * sending the message and awaiting a response. It processes the returned Acknowledgement, which includes
 * Sync Acks for successful processing or error indications, ensuring a reliable transmission of information
 * to the CISE Node.
 *
 * @see OutgoingMessageHandlerToNodePort
 */
public class DefaultOutgoingMessageHandlerToNodeAdapter implements OutgoingMessageHandlerToNodePort {

    @Override
    public Acknowledgement sendStringMessage(Message signedCiseMessage) {
        throw new RuntimeException("not impl");
    }

}
