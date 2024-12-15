package eu.cise.adaptor.core.servicehandler.service;

import eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;


/**
 * <p>This is an interface responsible for constructing CISE messages.
 * It encapsulates the CISE Payload into a CISE Message, creating the message envelope with required information.</p>
 *
 * <p>This service is crucial for communication between the CISE Node and Legacy Systems, ensuring that messages
 * conform to the CISE data and service models. It is used by plugins in the Generic Adaptor to prepare messages
 * for transmission or to process received messages.</p>
 */
public interface ServiceHandlerMessageBuilderService {

    /**
     * Manage the validated message context data:
     * <ul>
     * <li>Creates the appropriate CISE message using the data provided by the message data context</li>
     * <li>Saves the message created into the history persistence system</li>
     * <li>Gives the messages to the service handler</li>
     * <li>Return the CISE message sent and the ack code for success or failure</li>
     * </ul>
     *
     * @param messageDataContext Contains all the information about the message to be sent
     * @return List of couple RegisteredMessage (the CISE message sent) and Acknowledgement (the Acknowledgment result)
     */
    List<Pair<RegisteredMessage, Acknowledgement>> handleMessage(MessageDataContext messageDataContext);

    /**
     * Retrieve the message identified by its ID (CISE property MessageId)
     *
     * @param referenceMessageId MessageId
     * @return Message instance with the referenced message
     */
    Optional<Message> getMessageById(String referenceMessageId);

    /**
     * Retrieve all the messages with the same correlationId of the referred message (by the same CISE property CorrelationId)
     *
     * @param referenceMessageId MessageId
     * @return List of RegisteredMessage instances with the same correlationId of the referenced message, ordered from the newest to the oldest
     */
    List<RegisteredMessage> getMessageHistoryById(String referenceMessageId);

    /**
     * Retrieve all the messages with the same contextId (CISE property ContextId)
     *
     * @param contextId ContextId
     * @return List of RegisteredMessage instances with the same contextId, ordered from the newest to the oldest
     */
    List<RegisteredMessage> getMessageHistoryByContextId(String contextId);

    /**
     * Retrieve all the messages with the same correlationId of the referred message (by the same CISE property CorrelationId)
     * and the messages with the same contextId (CISE property ContextId)
     *
     * @param referenceMessageId MessageId
     * @param contextId ContextId
     * @return List of RegisteredMessage instances with the same correlationId of the referred message
     * and with the contextId given in input, ordered from the newest to the oldest
     */
    List<RegisteredMessage> getMessageHistory(String referenceMessageId, String contextId);

}
