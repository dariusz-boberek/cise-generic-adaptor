package eu.cise.quarkus.servicehandler;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorRuntimeException;
import eu.cise.adaptor.core.servicehandler.domain.MessageProcessAction;
import eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage;
import eu.cise.adaptor.quarkus.servicehandler.adapter.out.MessageRepositoryAdapter;
import eu.cise.quarkus.utils.ResourcesUtils;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.helpers.DateHelper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class MessageRepositoryAdapterTest {

    private RegisteredMessage testRegisteredMessage;

    @Inject
    MessageRepositoryAdapter messageRepositoryAdapter;

    @Inject
    MessageAuxiliaryOperationsImpl messageAuxiliaryOperations;

    private RegisteredMessage testRegisteredMessage1;
    private RegisteredMessage testRegisteredMessage2;
    private RegisteredMessage testRegisteredMessage3;
    private RegisteredMessage testRegisteredMessage4;

    @BeforeEach
    @Transactional
    public void setup() {
        messageAuxiliaryOperations.deleteAllMessages();
        String messageXml = ResourcesUtils.getResource("messages/good_ones/Push_riskPushConsumer_Signature_OK.xml");
        this.testRegisteredMessage = RegisteredMessage.ofCISEMessageDateReceived(RegisteredMessage.translateMessageFromXML(messageXml));
        setupMessageHistoryTestData();
    }

    @Test
    public void it_tests_CreateReadDelete_operations() {
        messageRepositoryAdapter.save(testRegisteredMessage);
        Optional<Message> retrievedMessage = messageRepositoryAdapter.getByMessageId(testRegisteredMessage.getMessageId());
        assertTrue(retrievedMessage.isPresent());
        assertEquals(testRegisteredMessage.getMessageId(), retrievedMessage.get().getMessageID());
        messageAuxiliaryOperations.deleteMessage(retrievedMessage.get().getMessageID());
        Optional<Message> deletedMessage = messageRepositoryAdapter.getByMessageId(retrievedMessage.get().getMessageID());
        assertTrue(deletedMessage.isEmpty());

    }

    @Test
    public void it_throws_error_on_duplicate_message_id() {
        // given
        messageRepositoryAdapter.save(testRegisteredMessage);

        //when & then
        CiseAdaptorRuntimeException exception = assertThrows(
                CiseAdaptorRuntimeException.class,
                () -> messageRepositoryAdapter.save(testRegisteredMessage),
                "Expected save to throw, but it didn't"
        );
        assertTrue(exception.getMessage().contains("message already persisted for ciseMessageId"));
    }

    @Test
    public void it_tests_persistence_after_transaction() {
        // given (testMessage) &  when
        messageRepositoryAdapter.save(testRegisteredMessage);
        // then
        Optional<Message> retrievedMessage = messageRepositoryAdapter.getByMessageId(testRegisteredMessage.getMessageId());
        assertTrue(retrievedMessage.isPresent(), "The message should persist after the transaction is completed");
    }

    @Test
    public void it_returns_empty_for_non_existent_message_id() {
        // given (no conditions) & when
        Optional<Message> retrievedMessage = messageRepositoryAdapter.getByMessageId("non_existent_id");

        // then
        assertTrue(retrievedMessage.isEmpty(), "Expected empty result for non existent message ID");
    }

    @Test
    public void it_will_get_message_without_transaction_if_exists() {
        // given
        messageRepositoryAdapter.save(testRegisteredMessage);

        // when
        Optional<RegisteredMessage> message = messageAuxiliaryOperations.getByMessageIdWithoutTransaction(testRegisteredMessage.getMessageId());
        Optional<RegisteredMessage> nonExistentMessage = messageAuxiliaryOperations.getByMessageIdWithoutTransaction("nonExistentMessageId");

        // then
        assertTrue(message.isPresent(), "The message should be retrieved even without a transaction");
        assertTrue(nonExistentMessage.isEmpty(), "Non-existent message retrieval without transaction should return an empty Optional");
    }

    @Test
    @Transactional // @Transactional is mandatory here
    public void it_will_delete_all_messages_in_transaction() {
        messageRepositoryAdapter.save(testRegisteredMessage);
        messageAuxiliaryOperations.deleteAllMessages();
        Optional<Message> maybeMessage = messageRepositoryAdapter.getByMessageId((testRegisteredMessage.getMessageId()));
        assertTrue(maybeMessage.isEmpty(), "Message should be deleted within a transaction");
    }

    @Test
    public void it_will_throw_exception_when_trying_to_delete_all_messages_without_transaction() {
        messageRepositoryAdapter.save(testRegisteredMessage);
        assertThrows(javax.transaction.TransactionalException.class, () -> messageAuxiliaryOperations.deleteAllMessages(),
                "Should have thrown TransactionalException because deleteAllMessages method requires a new transaction");
    }


    /**
     * tests it_will_get_message_with_transaction_notSupported_and_without_existing_transaction and
     * it_will_get_message_with_transaction_notSupported_and_with_existing_transaction
     * are testing that NOT_SUPPORTED will suspend transaction if it exists and will execute code in non transaction block.
     * It is enough that both tests are passing to state transaction propagation works as expected.
     */
    @Test
    public void it_will_get_message_with_transaction_notSupported_and_without_existing_transaction() {
        messageRepositoryAdapter.save(testRegisteredMessage);
        Optional<RegisteredMessage> message = messageAuxiliaryOperations.getByMessageIdWithTransactionNotSupported(testRegisteredMessage.getMessageId());
        assertTrue(message.isPresent(), "The message should be retrieved even without a transaction");
    }


    /**
     * This test is checking the behavior of the method getByMessageIdWithTransactionNotSupported, annotated with NOT_SUPPORTED transaction propagation.
     * The test will only work if the save operation used here is annotated with REQUIRES_NEW propagation.
     * This is necessary so that the saved result is stored before the current transaction gets suspended by NOT_SUPPORTED in getByMessageIdWithTransactionNotSupported.
     * If the save operation isn't using REQUIRES_NEW, the stored message may not be retrieved, causing the test assertion to fail.
     */
    @Test
    @Transactional
    @Disabled
    public void it_will_get_message_with_transaction_notSupported_and_with_existing_transaction() {
        messageRepositoryAdapter.save(testRegisteredMessage);
        Optional<RegisteredMessage> message = messageAuxiliaryOperations.getByMessageIdWithTransactionNotSupported(testRegisteredMessage.getMessageId());
        assertTrue(message.isPresent(), "The message should be retrieved even when the existing transaction is suspended");
    }

    @Test
    public void it_will_get_all_messages_with_transaction_supports_and_without_existing_transaction() {
        messageRepositoryAdapter.save(testRegisteredMessage);
        List<RegisteredMessage> messages = messageAuxiliaryOperations.getAllMessages();
        assertFalse(messages.isEmpty(), "Messages should be retrieved even without an active transaction");
    }

    @Test
    @Transactional
    public void it_will_get_all_messages_with_transaction_supports_and_with_existing_transaction() {
        messageRepositoryAdapter.save(testRegisteredMessage);
        List<RegisteredMessage> messages = messageAuxiliaryOperations.getAllMessages();
        assertFalse(messages.isEmpty(), "Messages should be retrieved within an existing transaction");
    }


    private void setupMessageHistoryTestData() {
        ZonedDateTime date1 = ZonedDateTime.now();
        ZonedDateTime date2 = date1.plusNanos(10);
        ZonedDateTime date3 = date2.plusMinutes(10);
        ZonedDateTime date4 = date3.plusMinutes(10);
        Message anyMessage = testRegisteredMessage.getCiseMessage();
        XMLGregorianCalendar xmlGregorianCalendar = DateHelper.toXMLGregorianCalendar(Calendar.getInstance().getTime());

        // correlationId cannot be null - validation of RegisteredMessage prevets that
        testRegisteredMessage1 = new RegisteredMessage(anyMessage, "id1", "corrId1", "contextId1", xmlGregorianCalendar, date1, MessageProcessAction.SENT);
        testRegisteredMessage2 = new RegisteredMessage(anyMessage, "id2", "corrId1", "contextId2", xmlGregorianCalendar, date2, MessageProcessAction.RECEIVED);
        testRegisteredMessage3 = new RegisteredMessage(anyMessage, "id3", "corrId2", "contextId1", xmlGregorianCalendar, date3, MessageProcessAction.SENT);
        testRegisteredMessage4 = new RegisteredMessage(anyMessage, "id4", "corrId2", "contextId2", xmlGregorianCalendar, date4, MessageProcessAction.SENT);

        messageRepositoryAdapter.save(testRegisteredMessage1);
        messageRepositoryAdapter.save(testRegisteredMessage2);
        messageRepositoryAdapter.save(testRegisteredMessage3);
        messageRepositoryAdapter.save(testRegisteredMessage4);
    }

    @Test
    @Transactional
    public void it_tests_getMessagesHistoryByCorrelationId_by_correlation_id_corrId1() {
        List<RegisteredMessage> savedMessages = messageRepositoryAdapter.getMessagesHistoryByCorrelationId("corrId1");
        assertEquals(2, savedMessages.size());
        assertEquals(testRegisteredMessage2, savedMessages.get(0));
        assertEquals(testRegisteredMessage1, savedMessages.get(1));
    }

    @Test
    @Transactional
    public void it_tests_getMessagesHistoryByCorrelationId_by_correlation_id_corrId2() {
        List<RegisteredMessage> savedMessages = messageRepositoryAdapter.getMessagesHistoryByCorrelationId("corrId2");
        assertEquals(2, savedMessages.size());
        assertEquals(testRegisteredMessage4, savedMessages.get(0));
        assertEquals(testRegisteredMessage3, savedMessages.get(1));
    }


    @Test
    @Transactional
    public void it_tests_getMessagesHistory_with_contextId1() {
        List<RegisteredMessage> savedMessages = messageRepositoryAdapter.getMessagesHistory(null, "contextId1");
        assertEquals(2, savedMessages.size());
        assertEquals(testRegisteredMessage3, savedMessages.get(0));
        assertEquals(testRegisteredMessage1, savedMessages.get(1));
    }

    @Test
    @Transactional
    public void it_tests_getMessagesHistory_with_corrId1_and_no_context() {
        List<RegisteredMessage> savedMessages = messageRepositoryAdapter.getMessagesHistory("corrId1", null);
        assertEquals(2, savedMessages.size());
        assertEquals(testRegisteredMessage2, savedMessages.get(0));
        assertEquals(testRegisteredMessage1, savedMessages.get(1));
    }

    @Test
    @Transactional
    public void it_tests_getMessagesHistory_with_corrId1_and_contextId1() {
        List<RegisteredMessage> savedMessages = messageRepositoryAdapter.getMessagesHistory("corrId1", "contextId1");
        assertEquals(3, savedMessages.size());
        assertEquals(testRegisteredMessage3, savedMessages.get(0));
        assertEquals(testRegisteredMessage2, savedMessages.get(1));
        assertEquals(testRegisteredMessage1, savedMessages.get(2));
    }

    @Test
    @Transactional
    public void it_tests_getMessagesHistory_with_corrId1_and_contextId2() {
        List<RegisteredMessage> savedMessages = messageRepositoryAdapter.getMessagesHistory("corrId1", "contextId2");
        assertEquals(3, savedMessages.size());
        assertEquals(testRegisteredMessage4, savedMessages.get(0));
        assertEquals(testRegisteredMessage2, savedMessages.get(1));
        assertEquals(testRegisteredMessage1, savedMessages.get(2));
    }

    @Test
    @Transactional
    public void it_tests_message_history_with_corrId2_and_contextId1() {
        List<RegisteredMessage> savedMessages = messageRepositoryAdapter.getMessagesHistory("corrId2", "contextId1");
        assertEquals(3, savedMessages.size());
        assertEquals(testRegisteredMessage4, savedMessages.get(0));
        assertEquals(testRegisteredMessage3, savedMessages.get(1));
        assertEquals(testRegisteredMessage1, savedMessages.get(2));
    }

    @Test
    @Transactional
    public void it_tests_getMessagesHistory_with_not_existing_correlation_id_and_contextId2() {
        List<RegisteredMessage> savedMessages = messageRepositoryAdapter.getMessagesHistory("notExistingCorrelationId", "contextId2");
        assertEquals(2, savedMessages.size());
        assertEquals(testRegisteredMessage4, savedMessages.get(0));
        assertEquals(testRegisteredMessage2, savedMessages.get(1));
    }

    @Test
    @Transactional
    public void it_tests_getMessagesHistoryByContextId_by_context_id_contextId1() {
        List<RegisteredMessage> savedMessages = messageRepositoryAdapter.getMessagesHistoryByContextId("contextId1");
        assertEquals(2, savedMessages.size());
        assertEquals(testRegisteredMessage3, savedMessages.get(0));
        assertEquals(testRegisteredMessage1, savedMessages.get(1));
    }

    @Test
    @Transactional
    public void it_tests_getMessagesHistoryByContextId_by_context_id_contextId2() {
        List<RegisteredMessage> savedMessages = messageRepositoryAdapter.getMessagesHistoryByContextId("contextId2");
        assertEquals(2, savedMessages.size());
        assertEquals(testRegisteredMessage4, savedMessages.get(0));
        assertEquals(testRegisteredMessage2, savedMessages.get(1));
    }

    @Test
    @Transactional
    public void it_tests_getMessagesHistoryByCorrelationId_with_non_existing_id() {
        List<RegisteredMessage> savedMessages = messageRepositoryAdapter.getMessagesHistoryByCorrelationId("nonExistingId");
        assertTrue(savedMessages.isEmpty());
    }

    @Test
    @Transactional
    public void it_tests_getMessagesHistoryByContextId_with_non_existing_id() {
        List<RegisteredMessage> savedMessages = messageRepositoryAdapter.getMessagesHistoryByContextId("nonExistingId");
        assertTrue(savedMessages.isEmpty());
    }

    @Test
    @Transactional
    public void it_tests_getMessagesHistory_with_null_correlation_id_and_context_id() {
        List<RegisteredMessage> savedMessages = messageRepositoryAdapter.getMessagesHistory(null, null);
        assertTrue(savedMessages.isEmpty());
    }

    @Test
    @Transactional
    public void it_tests_getMessagesHistoryByCorrelationId_with_null_id() {
        List<RegisteredMessage> savedMessages = messageRepositoryAdapter.getMessagesHistoryByCorrelationId(null);
        assertTrue(savedMessages.isEmpty());
    }

    @Test
    @Transactional
    public void it_tests_getMessagesHistoryByContextId_with_null_id() {
        // Arrange
        RegisteredMessage tempMessage = new RegisteredMessage(testRegisteredMessage.getCiseMessage(), "idX", "corrIdX", null, DateHelper.toXMLGregorianCalendar(Calendar.getInstance().getTime()), ZonedDateTime.now(), MessageProcessAction.SENT);
        messageRepositoryAdapter.save(tempMessage);

        // Act
        List<RegisteredMessage> savedMessages = messageRepositoryAdapter.getMessagesHistoryByContextId(null);

        // Assert
        assertTrue(savedMessages.isEmpty());
    }

    @Test
    @Transactional
    public void it_tests_getMessagesHistoryByContextId_case_sensitivity() {
        List<RegisteredMessage> savedMessages = messageRepositoryAdapter.getMessagesHistoryByContextId("CONTEXTID1");
        assertTrue(savedMessages.isEmpty(), "The method should be case sensitive");
    }

}