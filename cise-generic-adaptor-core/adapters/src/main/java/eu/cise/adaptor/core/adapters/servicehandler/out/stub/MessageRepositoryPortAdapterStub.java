package eu.cise.adaptor.core.adapters.servicehandler.out.stub;


import eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage;
import eu.cise.adaptor.core.servicehandler.port.out.MessageRepositoryPort;
import eu.cise.servicemodel.v1.message.Message;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * Stub implementation of the {@code MessageRepositoryPort} used in functional tests.
 *
 * <p>This adapter stub serves as a basic framework for testing the message repository functionalities
 * within the Generic Adaptor. It provides a simplified structure for simulating the storage and retrieval
 * of messages, aiding in the verification of the Adaptor's capabilities to manage message data effectively.
 *
 * @see MessageRepositoryPort
 */
public class MessageRepositoryPortAdapterStub implements MessageRepositoryPort {

    private final Map<String, RegisteredMessage> messages = new ConcurrentHashMap<>();

    public MessageRepositoryPortAdapterStub() {
    }

    @Override
    public void save(RegisteredMessage registeredMessage) {
        RegisteredMessage savedRegisteredMessage = messages.get(registeredMessage.getMessageId());
        if (savedRegisteredMessage != null) {
            throw new RuntimeException("message already persisted for messageId: " + registeredMessage.getMessageId());
        }

        messages.put(registeredMessage.getMessageId(), registeredMessage);
    }

    @Override
    public Optional<Message> getByMessageId(String messageId) {
        if (messageId == null) {
            return Optional.empty();
        }
        RegisteredMessage registeredMessage = messages.get(messageId);
        if (registeredMessage != null) {
            return Optional.of(registeredMessage.getCiseMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<RegisteredMessage> getMessagesHistoryByCorrelationId(String correlationId) {
        return messages.values().stream()
                .filter(registeredMessage -> correlationId.equals(registeredMessage.getCorrelationId()))
                .sorted(comparing(RegisteredMessage::getDateTimeProcessed).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<RegisteredMessage> getMessagesHistoryByContextId(String contextId) {
        return messages.values().stream()
                .filter(registeredMessage -> contextId.equals(registeredMessage.getContextId()))
                .sorted(comparing(RegisteredMessage::getDateTimeProcessed).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<RegisteredMessage> getMessagesHistory(String correlationId, String contextId) {
        return messages.values().stream()
                .filter(registeredMessage -> correlationId.equals(registeredMessage.getCorrelationId()) || contextId.equals(registeredMessage.getContextId()))
                .sorted(comparing(RegisteredMessage::getDateTimeProcessed).reversed())
                .collect(Collectors.toList());
    }
}
