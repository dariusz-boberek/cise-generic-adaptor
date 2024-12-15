package eu.cise.quarkus.servicehandler;

import eu.cise.adaptor.quarkus.servicehandler.adapter.in.IncomingMessageHandlerToServiceHandlerAdapter;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static eu.cise.quarkus.utils.ResourcesUtils.getResource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@QuarkusTest
class IncomingMessageHandlerToServiceHandlerAdapterTest {

    Logger logger = LoggerFactory.getLogger(IncomingMessageHandlerToServiceHandlerAdapterTest.class);

    @Inject
    EventBus eventBus;

    @InjectMock
    IncomingMessageHandlerToServiceHandlerAdapter incomingMessageHandlerToServiceHandlerAdapter;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void it_receives_a_processed_message_event_then_triggers_processedMessagesHandler() throws InterruptedException {
        // GIVEN
        XmlMapper xmlMapper = new DefaultXmlMapper();
        Message message = xmlMapper.fromXML(getResource("messages/good_ones/Push_riskPushConsumer_Signature_OK.xml"));
        logger.info("Message with message id: {}", message.getMessageID());
        CountDownLatch latch = new CountDownLatch(1);

        doAnswer(invocation -> {
            latch.countDown();
            return null;
        }).when(incomingMessageHandlerToServiceHandlerAdapter).processedMessagesHandler(any());

        // WHEN
        eventBus.<Message>requestAndForget("processed-message", message);
        latch.await(10, TimeUnit.SECONDS);

        // THEN
        verify(incomingMessageHandlerToServiceHandlerAdapter, times(1)).processedMessagesHandler(any());
    }
}