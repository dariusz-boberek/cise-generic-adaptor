package eu.cise.quarkus.messagehandler;

import eu.cise.adaptor.quarkus.servicehandler.adapter.in.IncomingMessageHandlerToServiceHandlerAdapter;
import eu.cise.adaptor.core.servicehandler.port.out.SendToLegacySystemPort;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static eu.cise.quarkus.utils.ResourcesUtils.getResource;
import static eu.cise.quarkus.utils.TestSetupData.serviceIdsStub;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@QuarkusTest
class IncomingNodeToMessageHandlerAdapterTest {

    @Inject
    EventBus eventBus;

    @InjectSpy
    IncomingMessageHandlerToServiceHandlerAdapter incomingMessageHandlerToServiceHandlerAdapterSpy;

    @InjectMock
    Map<String, SendToLegacySystemPort> sendToLegacySystemPortMapMock;

    @BeforeEach
    public void setup() {
        when(sendToLegacySystemPortMapMock.keySet()).thenReturn(serviceIdsStub);
    }

    @Test
    void it_receives_a_message_event_then_replies_with_success_ack_AND_generates_a_processed_message_event() throws InterruptedException {
        // GIVEN
        XmlMapper xmlMapper = new DefaultXmlMapper();
        String xmlMessage = getResource("messages/good_ones/Push_riskPushConsumer_Signature_OK.xml");
        Message[] processedMessageHolder = new Message[1];
        CountDownLatch latch = new CountDownLatch(1);
        doAnswer(invocation -> {
            processedMessageHolder[0] = invocation.getArgument(0);
            latch.countDown();
            return null;
        }).when(incomingMessageHandlerToServiceHandlerAdapterSpy).processedMessagesHandler(any(Message.class));

        // WHEN
        Uni<Acknowledgement> ackReceiver = eventBus.<Acknowledgement>request("incoming-message", xmlMessage)
                .onItem()
                .transform(acknowledgementMessage -> acknowledgementMessage.body());

        // THEN
        Acknowledgement ackResponse = ackReceiver.await().indefinitely();
        boolean isUpdated = latch.await(10, TimeUnit.SECONDS);
        Message processedMessage = processedMessageHolder[0];

        assertThat(isUpdated).isTrue();
        assertThat(ackResponse).isNotNull();
        assertThat(ackResponse.getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);

        assertThat(processedMessage).isNotNull();
        String xmlProcessedOriginalMessage = xmlMapper.toXML(xmlMapper.fromXML(xmlMessage));
        String xmlProcessedMessage = xmlMapper.toXML(processedMessage);
        assertThat(xmlProcessedOriginalMessage).isEqualTo(xmlProcessedMessage);
    }

    @Test
    void it_receives_a_message_event_then_replies_with_success_ack_AND_logs_exception_from_servicehandler() throws InterruptedException {

        // GIVEN
        XmlMapper xmlMapper = new DefaultXmlMapper();
        String xmlMessage = getResource("messages/good_ones/Push_riskPushConsumer_Signature_OK.xml");
        Message[] processedMessageHolder = new Message[1];
        CountDownLatch latch = new CountDownLatch(1);


        doAnswer(invocation -> {
            processedMessageHolder[0] = invocation.getArgument(0);
            latch.countDown();
            throw new RuntimeException("Test Runtime Exception");
        }).when(incomingMessageHandlerToServiceHandlerAdapterSpy).handleMessage(any(Message.class));

        // WHEN
        Uni<Acknowledgement> ackReceiver = eventBus.<Acknowledgement>request("incoming-message", xmlMessage)
                .onItem()
                .transform(acknowledgementMessage -> acknowledgementMessage.body());

        // THEN
        Acknowledgement ackResponse = ackReceiver.await().indefinitely();
        boolean isUpdated = latch.await(10, TimeUnit.SECONDS);
        Message processedMessage = processedMessageHolder[0];

        assertThat(isUpdated).isTrue();
        assertThat(ackResponse).isNotNull();
        assertThat(ackResponse.getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);

        assertThat(processedMessage).isNotNull();
        String xmlProcessedOriginalMessage = xmlMapper.toXML(xmlMapper.fromXML(xmlMessage));
        String xmlProcessedMessage = xmlMapper.toXML(processedMessage);
        assertThat(xmlProcessedOriginalMessage).isEqualTo(xmlProcessedMessage);
    }

}