package eu.cise.adaptor.core.servicehandler.port.out;

import eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage;
import eu.cise.servicemodel.v1.message.Message;

import java.util.List;
import java.util.Optional;

/**
 * Serves as an interface for handling message persistence operations.
 * It abstracts the underlying storage mechanism and provides methods for saving, retrieving,
 * and managing message data within the system.
 */
public interface MessageRepositoryPort {

    /**
     * Persists the information contained in the RegisteredMessage instance into the underling persistence system
     *
     * @param message Information to be persisted
     */
    void save(RegisteredMessage message);

    /**
     * Retrieve the CISE Message identified by its MessageId
     *
     * @param messageId MessageId value
     * @return CISE Message instance, if found
     */
    Optional<Message> getByMessageId(String messageId);

    /**
     * Retrieve all the CISE messages saved in the persistence system with the same correlationId
     *
     * @param correlationId The correlationId to match messages against.
     * @return List of all the CISE messages with the same correlationId, from the newest to oldest
     */
    List<RegisteredMessage> getMessagesHistoryByCorrelationId(String correlationId);

    //INFO: possible refactor would merge getMessagesHistoryByCorrelationId and getMessagesHistoryByContextId into one getMessagesHistory method.
    /**
     *  Retrieve all the CISE messages saved in the persistence system with the same contextId
     *
     *
     * @param contextId The contextId to match messages against.
     * @return List of all the CISE messages with the same contextId, from the newest to oldest
     */
    List<RegisteredMessage> getMessagesHistoryByContextId(String contextId);

    /**
     *  Retrieve all the CISE messages saved in the persistence system using contextId and correlationId
     *  If one parameter is null or empty, then the search is done only on the other:
     *  - correlationId valued, contextId null/empty = search history by correlationId
     *  - contextId valued, correlationId null/empty = search history by contextId
     *  - correlationId valued, contextId valued = search history for messages that have at least one of the two
     *
     * @param correlationId The correlationId to match messages against.
     * @param contextId The contextId to match messages against.
     *
     * @return List of all the CISE messages with the same contextId, from the newest to oldest
     */
    List<RegisteredMessage> getMessagesHistory(String correlationId, String contextId);

}
