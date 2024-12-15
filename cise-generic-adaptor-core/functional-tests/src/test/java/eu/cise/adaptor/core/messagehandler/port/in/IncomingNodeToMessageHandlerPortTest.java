package eu.cise.adaptor.core.messagehandler.port.in;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cise.adaptor.core.FunctionalAppContext;
import eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage;
import eu.cise.adaptor.core.servicehandler.port.out.SendToLegacySystemPort;
import eu.cise.adaptor.core.servicehandler.port.out.UpdateLegacySystemResult;
import eu.cise.adaptor.core.servicehandler.service.DefaultServiceHandlerRoutingService;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.SignatureService;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class IncomingNodeToMessageHandlerPortTest {

    private final SendToLegacySystemPort sendToLegacySystemPortMock = Mockito.mock(SendToLegacySystemPort.class);

    private SignatureService signatureService;

    private XmlMapper xmlMapper;

    private Map<String, SendToLegacySystemPort> outgoingPorts;


    @BeforeEach
    void before() throws IOException {
        this.xmlMapper = new DefaultXmlMapper();
        Properties signatureProperties = readSignatureProperties();

        signatureService = newSignatureService(xmlMapper)
                .withKeyStoreName(signatureProperties.getProperty("signature.keystore.filename"))
                .withKeyStorePassword(signatureProperties.getProperty("signature.keystore.password"))
                .withPrivateKeyAlias(signatureProperties.getProperty("signature.privatekey.alias"))
                .withPrivateKeyPassword(signatureProperties.getProperty("signature.privatekey.password"))
                .build();

        outgoingPorts = new HashMap<>();
        outgoingPorts.put("node08.risk.push.consumer", sendToLegacySystemPortMock);

        MockitoAnnotations.openMocks(this);
    }

    @Captor
    ArgumentCaptor<RegisteredMessage> captor;

    @Test
    void it_retrieves_a_push_message_mock_style() {

        // given
        FunctionalAppContext ctx = FunctionalAppContext
                .builder()
                .setSignatureService(signatureService)
                .setSendToLegacySystemPorts(outgoingPorts)
                .buildWithFallbackToDefault();
        IncomingNodeToMessageHandlerPort incomingNodeToMessageHandlerPort = ctx.getDomainCtx().getIncomingNodeToMessageHandlerPort();
        String rawCiseMessage = readResource("messages/good_ones/Push_riskPushConsumer_Signature_OK.xml");
        UpdateLegacySystemResult updateLegacySystemResult = new UpdateLegacySystemResult("OK");
        when(sendToLegacySystemPortMock.updateLegacySystem(any(), any(), any(), any())).thenReturn(updateLegacySystemResult);

        // when
        Acknowledgement acknowledgement = incomingNodeToMessageHandlerPort.requestSyncAck(rawCiseMessage);

        // then
        assertThat(acknowledgement.getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);
        verify(sendToLegacySystemPortMock, times(1)).updateLegacySystem(captor.capture(), any(), any(), any());
        Message message = captor.getValue().getCiseMessage();
        assertThat(message.getMessageID()).isEqualTo("f16467a3-bd06-4166-ab04-71aee6a5f6b2");
    }

    @Test
    void it_retrieves_a_push_message_stub_impl_style() {
        // given
        FunctionalAppContext ctx = FunctionalAppContext
                .builder()
                .setSignatureService(signatureService)
                .setSendToLegacySystemPorts(outgoingPorts)
                .buildWithFallbackToDefault();
        IncomingNodeToMessageHandlerPort incomingNodeToMessageHandlerPort = ctx.getDomainCtx().getIncomingNodeToMessageHandlerPort();
        String rawCiseMessage = readResource("messages/good_ones/Push_riskPushConsumer_Signature_OK.xml");

        // when
        Acknowledgement acknowledgement = incomingNodeToMessageHandlerPort.requestSyncAck(rawCiseMessage);

        // then
        assertThat(acknowledgement.getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);
    }


    @Test
    void it_correctly_displays_log_message() throws JsonProcessingException {
        // given

        FunctionalAppContext ctx = FunctionalAppContext
                .builder()
                .setSignatureService(signatureService)
                .setSendToLegacySystemPorts(outgoingPorts)
                .buildWithFallbackToDefault();
        IncomingNodeToMessageHandlerPort incomingNodeToMessageHandlerPort = ctx.getDomainCtx().getIncomingNodeToMessageHandlerPort();
        String rawCiseMessage = readResource("messages/good_ones/Push_riskPushConsumer_Signature_OK.xml");
        UpdateLegacySystemResult updateLegacySystemResult = new UpdateLegacySystemResult("OK");
        when(sendToLegacySystemPortMock.updateLegacySystem(any(), any(), any(), any())).thenReturn(updateLegacySystemResult);

        //logger for testing purpose - for capturing logs
        ch.qos.logback.classic.Logger loggerCaptor = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(DefaultServiceHandlerRoutingService.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        loggerCaptor.addAppender(listAppender);

        // when
        Acknowledgement acknowledgement = incomingNodeToMessageHandlerPort.requestSyncAck(rawCiseMessage);

        // then
        assertThat(acknowledgement.getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);
        verify(sendToLegacySystemPortMock, times(1)).updateLegacySystem(any(), any(), any(), any());

        assertThat(listAppender.list).isNotEmpty();

        ILoggingEvent logEvent = findLogEventByMessageContent(listAppender.list, "updateLegacySystemResult content: UpdateLegacySystemResult");
        assertNotNull(logEvent);
        assertThat(logEvent.getLevel()).isEqualTo(Level.DEBUG);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode logJson = objectMapper.readTree(logEvent.getFormattedMessage());
        assertThat(logJson.has("MESSAGE_ID")).isTrue();
        assertThat(logJson.has("COMMENT")).isTrue();
        String commentValue = logJson.get("COMMENT").asText();
        assertThat(commentValue).contains("updateLegacySystemResult content: UpdateLegacySystemResult"); // slf4j brackets syntax check

        listAppender.stop();
        listAppender.list.clear();
    }

    private ILoggingEvent findLogEventByMessageContent(List<ILoggingEvent> logs, String content) {
        return logs.stream()
                .filter(log -> log.getFormattedMessage().contains(content))
                .findFirst()
                .orElse(null);
    }

    private static String readResource(String resourceName) {
        try {
            return Files.readString(Paths.get(getResourceURI(resourceName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static URI getResourceURI(String resourceDir) {
        try {
            return Objects.requireNonNull(IncomingNodeToMessageHandlerPortTest.class.getClassLoader().getResource(resourceDir)).toURI();
        } catch (URISyntaxException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    private Properties readSignatureProperties() throws IOException {
        Properties properties = new Properties();
        URI resourceURI = getResourceURI("signature.properties", IncomingNodeToMessageHandlerPortTest.class);
        try (FileInputStream in = new FileInputStream(new File(resourceURI))) {
            properties.load(in);
        }
        return properties;
    }


    public static URI getResourceURI(String resourceName, Class clazz) {
        try {
            return Objects.requireNonNull(clazz.getClassLoader().getResource(resourceName)).toURI();
        } catch (URISyntaxException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

}