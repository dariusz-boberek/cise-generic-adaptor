package eu.cise.quarkus.servicehandler;

import eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage;
import eu.cise.adaptor.quarkus.servicehandler.adapter.out.MessageMapper;
import eu.cise.adaptor.quarkus.servicehandler.domain.CiseMessageEntity;
import io.quarkus.hibernate.orm.panache.Panache;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static javax.transaction.Transactional.TxType.MANDATORY;
import static javax.transaction.Transactional.TxType.NEVER;
import static javax.transaction.Transactional.TxType.NOT_SUPPORTED;
import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

@ApplicationScoped
public class MessageAuxiliaryOperationsImpl implements MessageAuxiliaryOperations {
    @Override
    @Transactional(NEVER)
    public Optional<RegisteredMessage> getByMessageIdWithoutTransaction(String messageId) {
        Optional<CiseMessageEntity> ciseMessageEntity = CiseMessageEntity.find("ciseMessageId", messageId).firstResultOptional();
        if (ciseMessageEntity.isPresent()) {
            return Optional.of(MessageMapper.toDomain(ciseMessageEntity.get()));
        }
        return Optional.empty();
    }

    @Override
    @Transactional(NOT_SUPPORTED)
    public Optional<RegisteredMessage> getByMessageIdWithTransactionNotSupported(String messageId) {
        Optional<CiseMessageEntity> ciseMessageEntity = CiseMessageEntity.find("ciseMessageId", messageId).firstResultOptional();
        if (ciseMessageEntity.isPresent()) {
            return Optional.of(MessageMapper.toDomain(ciseMessageEntity.get()));
        }
        return Optional.empty();
    }

    @Override
    @Transactional(SUPPORTS)
    public List<RegisteredMessage> getAllMessages() {
        List<CiseMessageEntity> panacheEntityBases = CiseMessageEntity.listAll();
        return panacheEntityBases.stream()
                .map(entity -> MessageMapper.toDomain(entity))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(REQUIRED)
    public void deleteMessage(String messageId) {
        CiseMessageEntity.delete("ciseMessageId", messageId);
    }

    @Override
    @Transactional(MANDATORY)
    public void deleteAllMessages() {
        CiseMessageEntity.deleteAll();
        Panache.getEntityManager().flush();
        Panache.getEntityManager().clear();
    }
}
