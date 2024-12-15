package eu.cise.quarkus.servicehandler;

import eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage;

import java.util.List;
import java.util.Optional;

public interface MessageAuxiliaryOperations {

    List<RegisteredMessage> getAllMessages();

    void deleteMessage(String messageId);

    void deleteAllMessages();

    Optional<RegisteredMessage> getByMessageIdWithTransactionNotSupported(String messageId);

    Optional<RegisteredMessage> getByMessageIdWithoutTransaction(String messageId);
}