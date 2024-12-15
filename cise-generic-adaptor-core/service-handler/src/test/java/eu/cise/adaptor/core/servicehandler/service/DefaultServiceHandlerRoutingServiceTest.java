package eu.cise.adaptor.core.servicehandler.service;

import eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage;
import eu.cise.adaptor.core.servicehandler.port.out.MessageRepositoryPort;
import eu.cise.adaptor.core.servicehandler.port.out.SendToLegacySystemPort;
import eu.cise.adaptor.core.servicehandler.utils.ResourcesUtils;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DefaultServiceHandlerRoutingServiceTest {

    private ServiceHandlerRoutingService serviceHandlerRoutingService;

    private MessageRepositoryPort messageRepositoryPortMock;

    private Message message;
    private XmlMapper xmlMapper;

    private Map<String, SendToLegacySystemPort> outgoingPorts;

    private String pushXml = "Push_riskPushConsumer.xml";

    private String pushRecipientServiceId = "node08.risk.push.consumer";

    private String messageContextId = "91e6a630-59ce-439d-bb2c-06e0b8325c6d";
    private String messageCorrelationId = "96fb8a70-be29-49b4-a34d-f20390e557ee";

    @BeforeEach
    void setUp() {
        xmlMapper = new DefaultXmlMapper();
        SendToLegacySystemPort sendToLegacySystemPortMock1 = mock(SendToLegacySystemPort.class);
        SendToLegacySystemPort sendToLegacySystemPortMock2 = mock(SendToLegacySystemPort.class);
        SendToLegacySystemPort sendToLegacySystemPortMock3 = mock(SendToLegacySystemPort.class);
        outgoingPorts = new HashMap<>();
        outgoingPorts.put("fakeId", sendToLegacySystemPortMock1);
        outgoingPorts.put(pushRecipientServiceId, sendToLegacySystemPortMock2);
        outgoingPorts.put("anotherFakeId", sendToLegacySystemPortMock3);

        messageRepositoryPortMock = mock(MessageRepositoryPort.class);
        serviceHandlerRoutingService = new DefaultServiceHandlerRoutingService(outgoingPorts, messageRepositoryPortMock);

        message = xmlMapper.fromXML(ResourcesUtils.readResource(pushXml, DefaultServiceHandlerRoutingServiceTest.class));
        when(messageRepositoryPortMock.getByMessageId(any())).thenReturn(Optional.of(message));
    }

    @Test
    void it_calls_the_message_persistence_module_to_save_information() {
        serviceHandlerRoutingService.handleMessage(message);
        verify(messageRepositoryPortMock).save((any(RegisteredMessage.class)));
    }

    @Test
    void it_does_not_call_the_message_persistence_module_when_it_is_not_defined() {
        ServiceHandlerRoutingService localServiceHandlerRoutingService = new DefaultServiceHandlerRoutingService(outgoingPorts, null);
        localServiceHandlerRoutingService.handleMessage(message);
        verify(messageRepositoryPortMock, times(0)).save(any());
    }

    @Test
    void it_updates_legacy_system_with_cise_message_when_serviceId_matches() {
        SendToLegacySystemPort sendToLegacySystemPortMock = outgoingPorts.get(pushRecipientServiceId);
        serviceHandlerRoutingService.handleMessage(message);
        verify(sendToLegacySystemPortMock).updateLegacySystem(any(RegisteredMessage.class), any(String.class), any(List.class), any(String.class));
    }

    @Test
    void it_fails_when_trying_to_update_legacy_system_for_not_match_service_id_reason() {
        Map<String, SendToLegacySystemPort> localOutgoingPorts = new HashMap<>();
        localOutgoingPorts.put("not a match - 1", mock(SendToLegacySystemPort.class));
        localOutgoingPorts.put("not a match - 2", mock(SendToLegacySystemPort.class));

        ServiceHandlerRoutingService localServiceHandlerRoutingService = new DefaultServiceHandlerRoutingService(localOutgoingPorts, messageRepositoryPortMock);
        localServiceHandlerRoutingService.handleMessage(message);

        for (SendToLegacySystemPort port : localOutgoingPorts.values()) {
            verify(port, times(0)).updateLegacySystem(any(RegisteredMessage.class), any(String.class), any(List.class), any(String.class));
        }

    }

    @Test
    void it_correctly_recognize_xml_message_IDs() {
        // Arrange
        String expectedCorrelationId = "96fb8a70-be29-49b4-a34d-f20390e557ee";
        String expectedContextId = "91e6a630-59ce-439d-bb2c-06e0b8325c6d";
        String expectedMessageId = "f16467a3-bd06-4166-ab04-71aee6a5f6b2";

        ArgumentCaptor<RegisteredMessage> registeredMessageArgumentCaptor = ArgumentCaptor.forClass(RegisteredMessage.class);
        ArgumentCaptor<String> messageIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> contextIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<List<RegisteredMessage>> listCaptor = ArgumentCaptor.forClass(List.class);

        SendToLegacySystemPort sendToLegacySystemPortMock = outgoingPorts.get(pushRecipientServiceId);

        // Act
        serviceHandlerRoutingService.handleMessage(message);

        // Assert
        verify(sendToLegacySystemPortMock, times(1)).updateLegacySystem(
                registeredMessageArgumentCaptor.capture(),
                messageIdCaptor.capture(),
                listCaptor.capture(),
                contextIdCaptor.capture());

        RegisteredMessage capturedRegisteredMessage = registeredMessageArgumentCaptor.getValue();
        String capturedMessageId = messageIdCaptor.getValue();
        String capturedContextId = contextIdCaptor.getValue();
        List<RegisteredMessage> capturedMessageHistory = listCaptor.getValue();

        assertThat(capturedRegisteredMessage.getCiseMessage().getCorrelationID()).isEqualTo(expectedCorrelationId);
        assertThat(capturedMessageId).isEqualTo(expectedMessageId);
        assertThat(capturedContextId).isEqualTo(expectedContextId);
        assertThat(capturedMessageHistory).isEmpty();

    }

    @Test
    void it_handles_combined_history_both_by_correlationId_and_contextId() {
        //Arrange
        Message messageWithCorrelationId = xmlMapper.fromXML(ResourcesUtils.readResource("Push_riskPushConsumer_matchByCorrelationId.xml", DefaultServiceHandlerRoutingServiceTest.class));
        Message messageWithContextId = xmlMapper.fromXML(ResourcesUtils.readResource("Push_riskPushConsumer_matchByContextId.xml", DefaultServiceHandlerRoutingServiceTest.class));
        RegisteredMessage olderRegisteredMessage = RegisteredMessage.ofCISEMessageDateReceived(messageWithCorrelationId);
        RegisteredMessage newestRegisteredMessage = RegisteredMessage.ofCISEMessageDateReceived(messageWithContextId);
        when(messageRepositoryPortMock.getMessagesHistory(messageCorrelationId, messageContextId))
                .thenReturn(List.of(newestRegisteredMessage, olderRegisteredMessage));
        SendToLegacySystemPort sendToLegacySystemPortMock = outgoingPorts.get(pushRecipientServiceId);
        ArgumentCaptor<List<RegisteredMessage>> listCaptor = ArgumentCaptor.forClass(List.class);

        // Act
        serviceHandlerRoutingService.handleMessage(message);

        //Assert
        verify(sendToLegacySystemPortMock, times(1)).updateLegacySystem(
                any(),
                any(),
                listCaptor.capture(),
                any());
        List<RegisteredMessage> capturedMessageHistory = listCaptor.getValue();
        assertThat(capturedMessageHistory).isNotEmpty();
        assertThat(capturedMessageHistory.size()).isEqualTo(2);
        ZonedDateTime dateTimeOfNewerMessage = capturedMessageHistory.get(0).getDateTimeProcessed();
        ZonedDateTime dateTimeOfOlderMessage = capturedMessageHistory.get(1).getDateTimeProcessed();
        assertThat(dateTimeOfNewerMessage).withFailMessage("Expected dateTimeOfNewerMessage to be after or equal to dateTimeOfOlderMessage")
                .isAfterOrEqualTo(dateTimeOfOlderMessage);


    }

}