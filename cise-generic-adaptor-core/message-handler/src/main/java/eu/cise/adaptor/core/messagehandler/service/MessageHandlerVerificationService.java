package eu.cise.adaptor.core.messagehandler.service;

import eu.cise.servicemodel.v1.message.Acknowledgement;

import java.util.Set;

/**
 * The {@code MessageHandlerVerificationService} is responsible for verifying the structure and signature of CISE messages received from the CISE Node.
 * It ensures that messages conform to expected formats and are authentic, facilitating secure and reliable communication.
 */
public interface MessageHandlerVerificationService {

    /**
     * This method is called by the {@link eu.cise.adaptor.core.messagehandler.port.in.IncomingNodeToMessageHandlerPort} when the CISE Message is received from the CISE node
     * This method must verify the message for its structure and its signature and must forward it to the {@link eu.cise.adaptor.core.messagehandler.port.out.OutgoingMessageHandlerToServiceHandlerPort}
     * @param xmlMessage The String XML message received from the CISE Node
     * @param availableServiceIds The lis of available Service Ids handled by the plugins
     * @return The Synchronous Acknowledgment to be returned to the CISE Node
     */
    Acknowledgement requestSyncAck(String xmlMessage, Set<String> availableServiceIds);

}
