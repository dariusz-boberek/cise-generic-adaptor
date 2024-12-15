package eu.cise.adaptor.quarkus.servicehandler.adapter.out;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorRuntimeException;
import eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage;
import eu.cise.adaptor.core.servicehandler.port.out.MessageRepositoryPort;
import eu.cise.adaptor.quarkus.servicehandler.domain.CiseMessageEntity;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static eu.cise.adaptor.quarkus.servicehandler.adapter.out.MessageMapper.toEntity;

/**
 * The {@code MessageRepositoryAdaptor} implements the {@link MessageRepositoryPort} interface.
 * It acts as a concrete adapter, providing the actual data access logic and interacting with the
 * databases. This adapter translates generic message requests to specific
 * data store operations, ensuring the persistence and retrieval of message data.
 *
 * @see MessageRepositoryPort
 */
@ApplicationScoped
public class MessageRepositoryAdapter implements MessageRepositoryPort {

    @Transactional
    @Override
    public void save(RegisteredMessage registeredMessage) {
        Optional<CiseMessageEntity> maybeCiseMessageEntity = CiseMessageEntity.find("ciseMessageId", registeredMessage.getMessageId()).firstResultOptional();
        if (maybeCiseMessageEntity.isEmpty()) {
            CiseMessageEntity ciseMessageEntity = toEntity(registeredMessage);
            ciseMessageEntity.persist();
        } else {
            throw new CiseAdaptorRuntimeException("message already persisted for ciseMessageId : " + registeredMessage.getMessageId(), AcknowledgementType.INVALID_REQUEST_OBJECT);
        }
    }

    @Override
    @Transactional
    //TODO has to be changed to RegisteredMessage?
    public Optional<Message> getByMessageId(String messageId) {
        if (messageId == null) {
            return Optional.empty();
        }
        Optional<CiseMessageEntity> ciseMessageEntity = CiseMessageEntity.find("ciseMessageId", messageId).firstResultOptional();
        if (ciseMessageEntity.isPresent()) {
            Message ciseMessage = RegisteredMessage.translateMessageFromXML(ciseMessageEntity.get().getCiseMessage());
            return Optional.of(ciseMessage);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public List<RegisteredMessage> getMessagesHistoryByCorrelationId(String correlationId) {
        List<CiseMessageEntity> ciseMessageEntities = CiseMessageEntity.list("correlationId",
                Sort.by("dateTimeProcessed", Sort.Direction.Descending),
                correlationId);
        List<RegisteredMessage> result = new ArrayList<>(ciseMessageEntities.size());
        for (CiseMessageEntity ciseMessageEntity : ciseMessageEntities) {
            RegisteredMessage registeredMessage = MessageMapper.toDomain(ciseMessageEntity);
            result.add(registeredMessage);
        }
        return result;
    }

    @Override
    @Transactional
    public List<RegisteredMessage> getMessagesHistoryByContextId(String contextId) {
        List<CiseMessageEntity> ciseMessageEntities = CiseMessageEntity.list("contextId",
                Sort.by("dateTimeProcessed", Sort.Direction.Descending),
                contextId);
        List<RegisteredMessage> result = new ArrayList<>(ciseMessageEntities.size());
        for (CiseMessageEntity ciseMessageEntity : ciseMessageEntities) {
            RegisteredMessage registeredMessage = MessageMapper.toDomain(ciseMessageEntity);
            result.add(registeredMessage);
        }
        return result;
    }

    @Override
    @Transactional
    public List<RegisteredMessage> getMessagesHistory(String correlationId, String contextId) {
        List<CiseMessageEntity> ciseMessageEntities = CiseMessageEntity.list("correlationId = :corrID or contextId = :ctxID",
                Sort.by("dateTimeProcessed", Sort.Direction.Descending),
                Parameters.with("corrID", correlationId).and("ctxID", contextId).map());
        List<RegisteredMessage> result = new ArrayList<>(ciseMessageEntities.size());
        for (CiseMessageEntity ciseMessageEntity : ciseMessageEntities) {
            RegisteredMessage registeredMessage = MessageMapper.toDomain(ciseMessageEntity);
            result.add(registeredMessage);
        }
        return result;
    }

}