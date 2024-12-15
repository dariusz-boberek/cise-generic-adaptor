package eu.cise.adaptor.core.servicehandler.port.out;

import eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage;
import eu.cise.adaptor.core.servicehandler.service.ServiceHandlerRoutingService;

import java.util.List;

/**
 * This interface is responsible for defining the communication contract between the Service Handler and the Legacy System.
 * This port is used for sending data from the CISE Node to the Legacy System, handled by specific plugin implementations.
 * Each plugin in the Generic Adaptor should provide its own implementation of this port
 * to handle specific communication requirements of the Legacy System it interfaces with.
 */
public interface SendToLegacySystemPort {

    /**
     * This method is implemented by the Generic Adaptor Plugin and called by {@link ServiceHandlerRoutingService}.
     * The implementation should manage the communication towards the Legacy System
     *
     * @param newRegisteredMessage the new message to be sent to the Legacy System. This encapsulates the CISE message along with additional metadata such as message ID, correlation ID, and processing timestamps.
     * @param messageId         can be obtained from newRegisteredMessage also
     * @param messagesHistory   a list of previous messages related to the same context or correlation ID, providing historical context for the new message.
     * @param contextId         an optional contextId of CISE message (can be obtained from newRegisteredMessage also).
     * @return an instance of {@link UpdateLegacySystemResult} indicating the outcome of the operation.
     */
    UpdateLegacySystemResult updateLegacySystem(RegisteredMessage newRegisteredMessage,
                                                String messageId,
                                                List<RegisteredMessage> messagesHistory,
                                                String contextId);

}
