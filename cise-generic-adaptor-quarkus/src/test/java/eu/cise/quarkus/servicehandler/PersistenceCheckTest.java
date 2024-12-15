package eu.cise.quarkus.servicehandler;

import eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage;
import eu.cise.adaptor.quarkus.servicehandler.adapter.out.MessageRepositoryAdapter;
import eu.cise.quarkus.utils.ResourcesUtils;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@QuarkusTest
public class PersistenceCheckTest {


    Logger logger = LoggerFactory.getLogger(MessageRepositoryAdapterTest.class);

    @Inject
    MessageRepositoryAdapter messageRepositoryAdapter;

    @Inject
    MessageAuxiliaryOperationsImpl messageAuxiliaryOperations;

    /**
     * This test validates the persistence of a message across two consecutive runs. It's crucial to run this test twice for it to pass and verify the expected behavior.
     * Between the first and the second run, no changes should be made to the database. The test relies on the saved state from the first run to proceed with the second run.
     *
     * @throws IOException
     */
    @Test
    @Transactional
    @Disabled
    public void it_tests_persistence_across_multiple_runs() throws IOException {

        File flagFile = new File("db/.stateMarker");

        XmlMapper xmlMapper = new DefaultXmlMapper();
        String messageXml = ResourcesUtils.getResource("messages/good_ones/Push_riskPushConsumer_Signature_OK.xml");
        Message testMessage = xmlMapper.fromXML(messageXml);

        if (!flagFile.exists()) {
            // given
            logger.info("entering 1st execution block: assuming no message was present, saving 1 message");
            flagFile.createNewFile();
            Optional<Message> retrievedMessage = messageRepositoryAdapter.getByMessageId(testMessage.getMessageID());
            assertTrue(retrievedMessage.isEmpty(), "Unexpected data found in the first run");

            // when & then (no errors while saving)
            messageRepositoryAdapter.save(RegisteredMessage.ofCISEMessageDateReceived(testMessage));
        } else {
            // given
            logger.info("entering 2nd execution block: assuming 1 message was saved before and checking if its true, then deleting that message");
            flagFile.delete();

            // when
            Optional<Message> retrievedMessage = messageRepositoryAdapter.getByMessageId(testMessage.getMessageID());

            // then
            if(retrievedMessage.isPresent()) {
                logger.info("Data found as expected");
                assertEquals(testMessage.getMessageID(), retrievedMessage.get().getMessageID());
                messageAuxiliaryOperations.deleteMessage(retrievedMessage.get().getMessageID());
                Optional<Message> deletedMessage = messageRepositoryAdapter.getByMessageId(retrievedMessage.get().getMessageID());
                assertTrue(deletedMessage.isEmpty());
            } else {
                logger.warn(": Data not found in the second run OR this test was not run in isolation manually");
            }
        }
    }

}
