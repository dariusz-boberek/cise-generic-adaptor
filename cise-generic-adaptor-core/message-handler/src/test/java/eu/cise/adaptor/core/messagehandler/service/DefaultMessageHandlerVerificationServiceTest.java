package eu.cise.adaptor.core.messagehandler.service;

import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.common.message.MessageTypeEnum;
import eu.cise.adaptor.core.messagehandler.port.out.OutgoingMessageHandlerToServiceHandlerPort;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.SignatureService;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import static eu.cise.adaptor.core.common.logging.LogConfig.configureLogging;
import static eu.cise.adaptor.core.common.logging.LoggerMessage.of;
import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DefaultMessageHandlerVerificationServiceTest {

    public static final String ACKNOWLEDGEMENT = "Acknowledgement: {}";
    public static final String PUSH_UNDER_TEST_SERVICE_ID = "node08.risk.push.consumer";
    public static final String PUSH_UNDER_TEST = "Push_Signature_OK.xml";

    private OutgoingMessageHandlerToServiceHandlerPort outgoingMessageHandlerToServiceHandlerPort = mock(OutgoingMessageHandlerToServiceHandlerPort.class);

    private SignatureService signatureService;
    private MessageHandlerVerificationService messageHandlerVerificationService;
    private XmlMapper xmlMapper;

    static AdaptorLogger log = configureLogging(DefaultMessageHandlerVerificationServiceTest.class);

    private Set<String> availableServiceIds;


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
        messageHandlerVerificationService = new DefaultMessageHandlerVerificationService(outgoingMessageHandlerToServiceHandlerPort, signatureService);
        availableServiceIds = new HashSet<>();
        availableServiceIds.add(PUSH_UNDER_TEST_SERVICE_ID);
    }


    @Test
    void it_accepts_signed_message() {
        //given
        String pushXml = readResource("messages/good_ones/"+ PUSH_UNDER_TEST);

        //when
        Acknowledgement ack = messageHandlerVerificationService.requestSyncAck(pushXml, availableServiceIds);
        log.debug(of(MessageTypeEnum.SYNC_ACK, ack.getMessageID(),ACKNOWLEDGEMENT, ack.getAckCode().value()));

        //then
        assertThat(ack.getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);
    }

    @Test
    void it_declines_message_when_serviceId_not_registered_in_adaptor() {
        //given
        availableServiceIds.clear();
        String pushXml = readResource("messages/good_ones/" + PUSH_UNDER_TEST);

        //when
        Acknowledgement ack = messageHandlerVerificationService.requestSyncAck(pushXml, availableServiceIds);
        log.debug(of(MessageTypeEnum.SYNC_ACK, ack.getMessageID(), ACKNOWLEDGEMENT, ack.getAckCode().value()));

        //then
        assertThat(ack.getAckCode()).isEqualTo(AcknowledgementType.BAD_REQUEST);
        assertThat(ack.getAckDetail()).contains("Recipient not recognized by adaptor");
    }

    @Test
    void it_declines_message_without_correct_recipient() {
        //given
        String pullRequestXml = readResource("messages/incorrect/PullRequest_NoRecipient.xml");

        //when
        Acknowledgement ack = messageHandlerVerificationService.requestSyncAck(pullRequestXml, availableServiceIds);
        log.debug(of(MessageTypeEnum.SYNC_ACK, ack.getMessageID(), ACKNOWLEDGEMENT, ack.getAckCode().value()));

        //then
        assertThat(ack.getAckCode()).isEqualTo(AcknowledgementType.BAD_REQUEST);
        assertThat(ack.getAckDetail()).contains("No recipient in message");
    }


    @Test
    void it_passes_message_to_outgoing_from_message_receiver() {
        //given
        String pushXml = readResource("messages/good_ones/"+PUSH_UNDER_TEST);
        ArgumentCaptor<Message> outgoingMessageCaptor = ArgumentCaptor.forClass(Message.class);

        //when
        Acknowledgement ack = messageHandlerVerificationService.requestSyncAck(pushXml, availableServiceIds);
        log.debug(of(MessageTypeEnum.SYNC_ACK, ack.getMessageID(),ACKNOWLEDGEMENT, ack.getAckCode().value()));

        //then
        assertThat(ack.getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);
        verify(outgoingMessageHandlerToServiceHandlerPort).handleMessage(outgoingMessageCaptor.capture());
        Message resultOutgoingMessage = outgoingMessageCaptor.getValue();
        assertThat(resultOutgoingMessage.getMessageID())
                .isEqualTo("f16467a3-bd06-4166-ab04-71aee6a5f6b2");
        then(outgoingMessageHandlerToServiceHandlerPort).should(times(1)).handleMessage(any());
    }

    @Test
    void it_declines_unsigned_message() {
        //given
        String pushXml = readResource("messages/incorrect/Push_Signature_KO.xml");

        //when
        Acknowledgement ack = messageHandlerVerificationService.requestSyncAck(pushXml, availableServiceIds);
        log.debug(of(MessageTypeEnum.SYNC_ACK, ack.getMessageID(),ACKNOWLEDGEMENT, ack.getAckCode().value()));

        //then
        assertThat(ack.getAckCode()).isEqualTo(AcknowledgementType.AUTHENTICATION_ERROR);
    }

    @Test
    void it_declines_message_signed_with_wrong_date() {
        //given
        String pushXml = readResource("messages/incorrect/Push_Signature_WrongDate.xml");

        //when
        Acknowledgement ack = messageHandlerVerificationService.requestSyncAck(pushXml, availableServiceIds);
        log.debug(of(MessageTypeEnum.SYNC_ACK, ack.getMessageID(),ACKNOWLEDGEMENT, ack.getAckCode().value()));

        //then
        assertThat(ack.getAckCode()).isEqualTo(AcknowledgementType.AUTHENTICATION_ERROR);
    }


    private String readResource(String resourceName) {
        try {
            return Files.readString(Paths.get(getResourceURI(resourceName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URI getResourceURI(String resourceName) {
        try {
            return Objects.requireNonNull(DefaultMessageHandlerVerificationServiceTest.class.getClassLoader().getResource(resourceName)).toURI();
        } catch (URISyntaxException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    private Properties readSignatureProperties() throws IOException {
        Properties properties = new Properties();
        URI resourceURI = getResourceURI("signature.properties", DefaultMessageHandlerVerificationServiceTest.class);
        try(FileInputStream in = new FileInputStream(new File(resourceURI))) {
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