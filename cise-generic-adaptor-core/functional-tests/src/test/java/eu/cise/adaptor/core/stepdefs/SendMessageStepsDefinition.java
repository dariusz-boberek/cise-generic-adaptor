package eu.cise.adaptor.core.stepdefs;

import eu.cise.adaptor.core.FunctionalAppContext;
import eu.cise.adaptor.core.Utils;
import eu.cise.adaptor.core.adapters.servicehandler.out.stub.MessageRepositoryPortAdapterStub;
import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.common.logging.LogConfig;
import eu.cise.adaptor.core.messagehandler.port.out.OutgoingMessageHandlerToNodePort;
import eu.cise.adaptor.core.servicehandler.domain.MessageProcessAction;
import eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.adaptor.core.servicehandler.port.in.ReceiveFromLegacySystemPort;
import eu.cise.adaptor.core.servicehandler.port.out.MessageRepositoryPort;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.ConditionOperatorType;
import eu.cise.servicemodel.v1.message.CoreEntityPayload;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PayloadSelector;
import eu.cise.servicemodel.v1.message.PriorityType;
import eu.cise.servicemodel.v1.message.PullRequest;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.servicemodel.v1.message.SelectorCondition;
import eu.cise.servicemodel.v1.message.XmlEntityPayload;
import eu.cise.servicemodel.v1.service.QueryByExampleType;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceCapability;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceProfile;
import eu.cise.servicemodel.v1.service.ServiceRoleType;
import eu.cise.servicemodel.v1.service.ServiceType;
import eu.cise.signature.SignatureService;
import eu.eucise.helpers.DateHelper;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import io.cucumber.java8.En;
import org.apache.commons.lang3.tuple.Pair;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import static eu.cise.adaptor.core.Constants.PERSISTENCE_TURNED_OFF;
import static eu.cise.adaptor.core.Constants.PERSISTENCE_TURNED_ON;
import static eu.cise.adaptor.core.common.logging.LoggerMessage.of;
import static eu.cise.adaptor.core.common.utils.MessageBuildingServiceUtils.areServicesSimilar;
import static eu.cise.servicemodel.v1.service.ServiceOperationType.PULL;
import static eu.cise.servicemodel.v1.service.ServiceOperationType.PUSH;
import static eu.cise.servicemodel.v1.service.ServiceOperationType.SUBSCRIBE;
import static eu.cise.servicemodel.v1.service.ServiceRoleType.CONSUMER;
import static eu.cise.servicemodel.v1.service.ServiceRoleType.PROVIDER;
import static eu.cise.servicemodel.v1.service.ServiceType.RISK_SERVICE;
import static eu.cise.servicemodel.v1.service.ServiceType.VESSEL_SERVICE;
import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;
import static eu.eucise.helpers.AckBuilder.newAck;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SendMessageStepsDefinition implements En {

    public static final String RISK_PULL_PROVIDER = "risk pull provider";
    public static final String VESSEL_PULL_PROVIDER = "vessel pull provider";
    public static final String VESSEL_PULL_CONSUMER = "vessel pull consumer";
    public static final String RISK_PUSH_PROVIDER = "risk push provider";
    public static final String VESSEL_PUSH_PROVIDER = "vessel push provider";
    public static final String VESSEL_SUBSCRIBE_PROVIDER = "vessel subscribe provider";

    public static final String RISK_SUBSCRIBE_CONSUMER = "risk subscribe consumer";

    public static final String VESSEL_SUBSCRIBE_CONSUMER = "vessel subscribe consumer";

    public static final String PUSH_MESSAGE = "Push";
    public static final String PULL_REQUEST_MESSAGE = "PullRequest";


    List<String> adaptorToLegacySystemAcks;
    @Captor
    ArgumentCaptor<Message> ciseMessageCaptor;
    MockedStatic<DateHelper> dateHelperMockedStatic;
    private XmlMapper xmlMapper;

    private XmlMapper xmlMapperNotValidating;
    private SignatureService signatureService;
    private Properties signatureProperties;
    private ReceiveFromLegacySystemPort currentReceiveFromLegacySystemPort;
    private FunctionalAppContext ctx;
    private String referenceMessageId;
    private OutgoingMessageHandlerToNodePort outgoingMessageHandlerToNodePortMock;
    private Acknowledgement ciseAcknowledgementStub;
    private XMLGregorianCalendar fixedNow;
    private List<Message> allMessages;
    private List<Service> currentRecipientsList;
    private boolean isPersistenceTurnedOn = false;

    private Exception caughtException;

    private MessageDataContext.MessageDataContextManager messageDataContextManager;

    private Optional<MessageRepositoryPort> messageRepositoryPort;

    private String legacySystemPayloadXML;
    private String legacySystemPayloadSelectorXML;

    private Service riskPullProviderService;

    private Service vesselPullProviderService;

    private Service vesselPullConsumerService;

    private Service riskPushProviderService;

    private Service vesselPushProviderService;

    private Service vesselSubscribeProviderService;

    private Service vesselSubscribeConsumerService;

    private Service riskSubscribeConsumerService;

    private List<ServiceProfile> discoveryProfiles;

    private final static AdaptorLogger log = LogConfig.configureLogging(SendMessageStepsDefinition.class);

    public SendMessageStepsDefinition() {

        Before(() -> {
            MockitoAnnotations.openMocks(this);
            xmlMapper = new DefaultXmlMapper();
            xmlMapperNotValidating = new DefaultXmlMapper.NotValidating();
            signatureProperties = new Properties();
            readSignatureProperties();
            signatureService = newSignatureService(xmlMapper)
                    .withKeyStoreName(signatureProperties.getProperty("signature.keystore.filename"))
                    .withKeyStorePassword(signatureProperties.getProperty("signature.keystore.password"))
                    .withPrivateKeyAlias(signatureProperties.getProperty("signature.privatekey.alias"))
                    .withPrivateKeyPassword(signatureProperties.getProperty("signature.privatekey.password"))
                    .build();
            outgoingMessageHandlerToNodePortMock = mock(OutgoingMessageHandlerToNodePort.class);
            ciseAcknowledgementStub = newAck()
                    .id("any")
                    .correlationId("any")
                    .creationDateTime(new Date())
                    .priority(PriorityType.HIGH)
                    .isRequiresAck(false)
                    .ackCode(AcknowledgementType.SUCCESS)
                    .ackDetail("Message delivered")
                    .build();
            ciseAcknowledgementStub.setPayload(null);
            when(outgoingMessageHandlerToNodePortMock.sendStringMessage(ciseMessageCaptor.capture())).thenReturn(ciseAcknowledgementStub);
            fixedNow = DateHelper.toXMLGregorianCalendar(new Date());
            if (dateHelperMockedStatic == null) {
                dateHelperMockedStatic = Mockito.mockStatic(DateHelper.class);
                dateHelperMockedStatic.when(() -> DateHelper.toXMLGregorianCalendar(any(Date.class))).thenReturn(fixedNow);
            }

            riskPullProviderService = createTempService(PROVIDER, PULL, "node04.risk.pull.provider", RISK_SERVICE);
            vesselPullProviderService = createTempService(PROVIDER, PULL, "node02.vessel.pull.provider", VESSEL_SERVICE);
            vesselPullConsumerService = createTempService(CONSUMER, PULL, "node20.vessel.pull.consumer", VESSEL_SERVICE);
            riskPushProviderService = createTempService(PROVIDER, PUSH, "node11.risk.push.provider", RISK_SERVICE);
            vesselPushProviderService = createTempService(PROVIDER, PUSH, "node12.vessel.push.provider", VESSEL_SERVICE);
            vesselSubscribeProviderService = createTempService(PROVIDER, SUBSCRIBE, "node13.vessel.subscribe.provider", VESSEL_SERVICE);
            riskSubscribeConsumerService = createTempService(CONSUMER, SUBSCRIBE, "node14.risk.subscribe.consumer", RISK_SERVICE);
            vesselSubscribeConsumerService = createTempService(CONSUMER, SUBSCRIBE, "node15.vessel.subscribe.consumer", VESSEL_SERVICE);

            caughtException = null;
        });


        Given("internal persistence turned {string}", (String internalPersistenceSwitch) -> {
            if ("OFF".equalsIgnoreCase(internalPersistenceSwitch)) {
                initContext(PERSISTENCE_TURNED_OFF);
            } else {
                initContext(PERSISTENCE_TURNED_ON);
            }
        });

        Given("the Adaptor configured to handle {string}", (String domain) -> {
            if (RISK_PULL_PROVIDER.equalsIgnoreCase(domain)) {
                currentReceiveFromLegacySystemPort = ctx.getDomainCtx().getRiskPullProviderReceiveFromLegacySystemPort();
            } else if (VESSEL_PULL_PROVIDER.equalsIgnoreCase(domain)) {
                currentReceiveFromLegacySystemPort = ctx.getDomainCtx().getVesselPullProviderReceiveFromLegacySystemPort();
            } else if (RISK_PUSH_PROVIDER.equalsIgnoreCase(domain)) {
                currentReceiveFromLegacySystemPort = ctx.getDomainCtx().getRiskPushProviderReceiveFromLegacySystemPort();
            } else if (VESSEL_PUSH_PROVIDER.equalsIgnoreCase(domain)) {
                currentReceiveFromLegacySystemPort = ctx.getDomainCtx().getVesselPushProviderReceiveFromLegacySystemPort();
            } else if (VESSEL_SUBSCRIBE_PROVIDER.equalsIgnoreCase(domain)) {
                currentReceiveFromLegacySystemPort = ctx.getDomainCtx().getVesselSubscribeProviderReceiveFromLegacySystemPort();
            } else if (RISK_SUBSCRIBE_CONSUMER.equalsIgnoreCase(domain)) {
                currentReceiveFromLegacySystemPort = ctx.getDomainCtx().getRiskSubscribeConsumerReceiveFromLegacySystemPort();
            } else if (VESSEL_SUBSCRIBE_CONSUMER.equalsIgnoreCase(domain)) {
                currentReceiveFromLegacySystemPort = ctx.getDomainCtx().getVesselSubscribeConsumerReceiveFromLegacySystemPort();
            } else if (VESSEL_PULL_CONSUMER.equalsIgnoreCase(domain)) {
                currentReceiveFromLegacySystemPort = ctx.getDomainCtx().getVesselPullConsumerReceiveFromLegacySystemPort();
            } else {
                fail("wrong case ID or unhandled implementation for: [{}]", domain);
            }
            assertThat(currentReceiveFromLegacySystemPort)
                    .withFailMessage("The currentReceiveFromLegacySystemPort should be present")
                    .isNotNull();
        });

        Given("saved by adapter PullRequest in {string} containing messageId {string}", (String filePath, String messageId) -> {
            initContext(PERSISTENCE_TURNED_ON);
            assertThat(messageRepositoryPort).isPresent();
            MessageRepositoryPort messageRepository = messageRepositoryPort.get();


            // seed for database
            String pullRequestXML = Utils.readResource(filePath, SendMessageStepsDefinition.class);
            RegisteredMessage registeredMessage = RegisteredMessage.ofCISEMessageDateReceived(RegisteredMessage.translateMessageFromXML(pullRequestXML));
            messageRepository.save(registeredMessage);
            assertThat(messageRepository.getByMessageId(registeredMessage.getMessageId()))
                    .isNotNull()
                    .isNotEmpty();
        });

        Given("a payload from the Legacy System represented by file {string} and a referenceMessageId {string}", (String filePath, String referenceMessageId) -> {
            legacySystemPayloadXML = Utils.readResource(filePath, SendMessageStepsDefinition.class);
            this.referenceMessageId = referenceMessageId;
        });

        Given("add element to list of recipients with details {string} {string} {string} {string}", (String serviceType, String serviceOperation, String serviceRole, String serviceId) -> {
            if (currentRecipientsList == null) {
                currentRecipientsList = new ArrayList<>();
            }
            Service recipientService = new Service();
            recipientService.setServiceID(serviceId);
            recipientService.setServiceOperation(ServiceOperationType.fromValue(serviceOperation));
            recipientService.setServiceRole(ServiceRoleType.fromValue(serviceRole));
            recipientService.setServiceType(ServiceType.fromValue(serviceType));
            currentRecipientsList.add(recipientService);
        });

        Given("no recipients are added to the list", () -> {
            currentRecipientsList = new ArrayList<>();
        });

        Given("the MessageDataContext is initialized with a referenceMessage", () -> {
            messageDataContextManager = MessageDataContext.getManager();
            messageDataContextManager
                    .referenceMessageId(referenceMessageId)
                    .recipients(currentRecipientsList)
                    .referenceMessage(currentReceiveFromLegacySystemPort.getMessageById(referenceMessageId).orElse(null));
        });

        Given("the MessageDataContext is initialized without a referenceMessage", () -> {
            messageDataContextManager = MessageDataContext.getManager();
            messageDataContextManager
                    .recipients(currentRecipientsList);
        });

        Given("the MessageDataContext have pullRequestPayloadSelector field initialized with Selector value {string} and to be equal", (String pullRequestPayloadSelector) -> {
            PayloadSelector payloadSelector = new PayloadSelector();
            SelectorCondition sc = new SelectorCondition();
            sc.setSelector(pullRequestPayloadSelector);
            sc.setOperator(ConditionOperatorType.EQUAL);
            payloadSelector.getSelectors().add(sc);
            messageDataContextManager.pullRequestPayloadSelector(payloadSelector);
        });

        Given("the MessageDataContext have pullRequestRequests field initialized with responseTime {int} maxNumberOfRequests {int} maxEntitiesPerMsg {int} and query to be done in best effort",
                (Integer responseTime, Integer maxNumberOfRequests, Integer maxEntitiesPerMsg) -> {
                    ServiceCapability pullRequestRequests = new ServiceCapability();
                    pullRequestRequests.setExpectedResponseTime(responseTime);
                    pullRequestRequests.setMaxNumberOfRequests(maxNumberOfRequests);
                    pullRequestRequests.setMaxEntitiesPerMsg(maxEntitiesPerMsg);
                    pullRequestRequests.setQueryByExampleType(QueryByExampleType.BEST_EFFORT);
                    messageDataContextManager.pullRequestRequests(pullRequestRequests);
                });

        Given("the MessageDataContext have contextId field initialized with value {string}", (String contextId) -> {
            messageDataContextManager.contextId(contextId);
        });

        Given("the MessageDataContext have pullRequestResponseTimeOut field initialized with value {int}", (Integer pullRequestResponseTimeOut) -> {
            messageDataContextManager.pullRequestResponseTimeOut(pullRequestResponseTimeOut);
        });

        Given("the MessageDataContext is configured with discoveryProfile {string} {string} {string}", (String serviceType, String serviceOperation, String serviceRole) -> {
            ServiceProfile discoveryProfile = new ServiceProfile();
            discoveryProfile.setServiceType(ServiceType.fromValue(serviceType));
            discoveryProfile.setServiceOperation(ServiceOperationType.fromValue(serviceOperation));
            discoveryProfile.setServiceRole(ServiceRoleType.fromValue(serviceRole));
            discoveryProfiles = singletonList(discoveryProfile);
            messageDataContextManager.discoveryProfiles(discoveryProfiles);
        });

        When("the Adaptor constructs the {string}", (String messageType) -> {
            log.info(of("messageType under test: {}", messageType));
            assertThat(messageDataContextManager)
                    .as("Ensure MessageDataContext is initialized")
                    .isNotNull();
            try {
                List<Pair<RegisteredMessage, Acknowledgement>> result = currentReceiveFromLegacySystemPort.handleIncomingLegacyData(legacySystemPayloadXML, messageDataContextManager);
                adaptorToLegacySystemAcks = result.stream()
                        .map(pair -> pair.getRight().getAckCode().value())
                        .collect(Collectors.toList());
                allMessages = ciseMessageCaptor.getAllValues();
                caughtException = null;
            } catch (CiseAdaptorValidationException ciseAdaptorValidationException) {
                caughtException = ciseAdaptorValidationException;
            }
        });

        When("the Adaptor constructs the {string} passing payload to the MessageDataContextManager", (String messageType) -> {
            log.info(of("messageType under test: {}", messageType));
            assertThat(messageDataContextManager)
                    .as("Ensure MessageDataContext is initialized")
                    .isNotNull();
            try {
                messageDataContextManager.cisePayload(xmlMapperNotValidating.fromXML(legacySystemPayloadXML));
                List<Pair<RegisteredMessage, Acknowledgement>> result = currentReceiveFromLegacySystemPort.handleIncomingLegacyData(messageDataContextManager);
                adaptorToLegacySystemAcks = result.stream()
                        .map(pair -> pair.getRight().getAckCode().value())
                        .collect(Collectors.toList());
                allMessages = ciseMessageCaptor.getAllValues();
                caughtException = null;
            } catch (CiseAdaptorValidationException ciseAdaptorValidationException) {
                caughtException = ciseAdaptorValidationException;
            }
        });

        Then("an adaptorToLegacySystemAck with SUCCESS Status is retrieved", () -> {
            assertThat(caughtException).isNull();
            assertThat(adaptorToLegacySystemAcks)
                    .as("Check if all acknowledgements are SUCCESS")
                    .allMatch(ack -> ack.equals(AcknowledgementType.SUCCESS.value()));
            verify(outgoingMessageHandlerToNodePortMock, times(1)).sendStringMessage(any());
        });

        Then("contextId with value {string} is forwarded to the CISE Node.", (String expectedContextId) -> {
            List<Message> allMessages = ciseMessageCaptor.getAllValues();
            for (Message currentMessage : allMessages) {
                assertThat(currentMessage.getContextID()).isEqualTo(expectedContextId);
            }
        });

        Then("a CiseAdaptorValidationException is thrown", () -> {
            assertNotNull(caughtException);
            assertTrue(caughtException instanceof CiseAdaptorValidationException);
        });

        Then("all acknowledgements with SUCCESS Status are retrieved for calls to the node", () -> {
            assertThat(adaptorToLegacySystemAcks)
                    .as("Check if all acknowledgements are SUCCESS")
                    .allMatch(ack -> ack.equals(AcknowledgementType.SUCCESS.value()));
            verify(outgoingMessageHandlerToNodePortMock, times(currentRecipientsList.size())).sendStringMessage(any());
        });


        Then("the constructed message reuses data from the PullRequest in {string}", (String filePath) -> {
            String pullRequestXML = Utils.readResource(filePath, SendMessageStepsDefinition.class);
            Message pullRequest = xmlMapper.fromXML(pullRequestXML);
            List<Message> allMessages = ciseMessageCaptor.getAllValues();

            for (Message currentMessage : allMessages) {
                assertThat(currentMessage.getMessageID()).isNotNull();
                assertThat(currentMessage.getContextID()).isEqualTo(pullRequest.getContextID());
                assertThat(currentMessage.getCorrelationID()).isNotNull();
                assertThat(currentMessage.getSender().getServiceID()).isEqualTo(pullRequest.getRecipient().getServiceID());
                assertThat(currentMessage.getSender().getServiceRole()).isEqualTo(pullRequest.getRecipient().getServiceRole());
                assertThat(currentMessage.getSender().getServiceType()).isEqualTo(pullRequest.getRecipient().getServiceType());
                assertThat(currentMessage.getSender().getServiceOperation()).isEqualTo(pullRequest.getRecipient().getServiceOperation());
                assertThat(currentMessage.getRecipient()).isEqualTo(pullRequest.getSender());
                assertThat(currentMessage.getPayload().getInformationSecurityLevel()).isEqualTo(pullRequest.getPayload().getInformationSecurityLevel());
                assertThat(currentMessage.getPayload().getInformationSensitivity()).isEqualTo(pullRequest.getPayload().getInformationSensitivity());
                assertThat(currentMessage.getPayload().isIsPersonalData()).isEqualTo(pullRequest.getPayload().isIsPersonalData());
                assertThat(currentMessage.getPayload().getPurpose()).isEqualTo(pullRequest.getPayload().getPurpose());
            }
        });


        Then("the constructed message uses the payload from legacy system", () -> {
            assertThat(legacySystemPayloadXML).isNotNull();
            XmlMapper tempMapper = new DefaultXmlMapper.NotValidating();
            CoreEntityPayload legacySystemPayload = tempMapper.fromXML(legacySystemPayloadXML);

            List<Message> allMessages = ciseMessageCaptor.getAllValues();
            for (Message message : allMessages) {
                assertThat(message.getPayload()).isEqualTo(legacySystemPayload);
            }
        });

        Then("the creation date is updated", () -> {
            List<Message> allMessages = ciseMessageCaptor.getAllValues();
            for (Message message : allMessages) {
                XMLGregorianCalendar resultTime = message.getCreationDateTime();
                assertThat(resultTime).isEqualTo(fixedNow);
            }
        });

        Then("it signs the message", () -> {
            List<Message> allMessages = ciseMessageCaptor.getAllValues();
            for (Message message : allMessages) {
                assertThatCode(() -> signatureService.verify(message)).doesNotThrowAnyException();
            }
        });

        Then("the constructed message have sender data set corresponding to {string}", (String domain) -> {
            List<Message> allMessages = ciseMessageCaptor.getAllValues();
            String failMessageSuffix = " service is not the same as sender service.";
            for (Message message : allMessages) {
                if (RISK_PULL_PROVIDER.equalsIgnoreCase(domain)) {
                    assertThat(areServicesSimilar(message.getSender(), riskPullProviderService)).withFailMessage(RISK_PULL_PROVIDER + failMessageSuffix).isTrue();
                } else if (VESSEL_PULL_PROVIDER.equalsIgnoreCase(domain)) {
                    assertThat(areServicesSimilar(message.getSender(), vesselPullProviderService)).withFailMessage(VESSEL_PULL_PROVIDER + failMessageSuffix).isTrue();
                } else if (VESSEL_PULL_CONSUMER.equalsIgnoreCase(domain)) {
                    assertThat(areServicesSimilar(message.getSender(), vesselPullConsumerService)).withFailMessage(VESSEL_PULL_CONSUMER + failMessageSuffix).isTrue();
                } else if (RISK_PUSH_PROVIDER.equalsIgnoreCase(domain)) {
                    assertThat(areServicesSimilar(message.getSender(), riskPushProviderService)).withFailMessage(RISK_PUSH_PROVIDER + failMessageSuffix).isTrue();
                } else if (VESSEL_PUSH_PROVIDER.equalsIgnoreCase(domain)) {
                    assertThat(areServicesSimilar(message.getSender(), vesselPushProviderService)).withFailMessage(VESSEL_PUSH_PROVIDER + failMessageSuffix).isTrue();
                } else if (VESSEL_SUBSCRIBE_PROVIDER.equalsIgnoreCase(domain)) {
                    assertThat(areServicesSimilar(message.getSender(), vesselSubscribeProviderService)).withFailMessage(VESSEL_SUBSCRIBE_PROVIDER + failMessageSuffix).isTrue();
                } else if (VESSEL_SUBSCRIBE_CONSUMER.equalsIgnoreCase(domain)) {
                    assertThat(areServicesSimilar(message.getSender(), vesselSubscribeConsumerService)).withFailMessage(VESSEL_SUBSCRIBE_CONSUMER + failMessageSuffix).isTrue();
                } else if (RISK_SUBSCRIBE_CONSUMER.equalsIgnoreCase(domain)) {
                    assertThat(areServicesSimilar(message.getSender(), riskSubscribeConsumerService)).withFailMessage(RISK_SUBSCRIBE_CONSUMER + failMessageSuffix).isTrue();
                } else {
                    fail("Not expecting this domain: {}", domain);
                }
            }
        });

        Then("the constructed message does not have a discoveryProfile", () -> {
            List<Message> allMessages = ciseMessageCaptor.getAllValues();
            for (Message message : allMessages) {
                if (message instanceof PullRequest) {
                    assertThat(((PullRequest) message).getDiscoveryProfiles()).isNullOrEmpty();
                } else if (message instanceof Push) {
                    assertThat(((Push) message).getDiscoveryProfiles()).isNullOrEmpty();
                }
            }
        });

        Then("no message is persisted", () -> {
            assertTrue(isPersistenceTurnedOn);
            assertThat(messageRepositoryPort).isPresent();
            MessageRepositoryPort messageRepository = messageRepositoryPort.get();
            List<Message> allMessages = ciseMessageCaptor.getAllValues();
            assertThat(allMessages)
                    .describedAs("List of messages with SENT status")
                    .hasSize(0);
        });

        Then("the Message\\(s) sent are persisted correctly", () -> {
            assertTrue(isPersistenceTurnedOn);
            assertThat(messageRepositoryPort).isPresent();
            MessageRepositoryPort messageRepository = messageRepositoryPort.get();

            List<Message> sentMessagesList = ciseMessageCaptor.getAllValues();
            for (Message sentMessage : sentMessagesList) {
                List<RegisteredMessage> sentMessages = messageRepository.getMessagesHistoryByCorrelationId(sentMessage.getCorrelationID()).stream()
                        .filter(registeredMessage -> registeredMessage.getMessageProcessAction() == MessageProcessAction.SENT)
                        .collect(Collectors.toList());
                assertThat(sentMessages)
                        .describedAs("List of messages with SENT status for correlation ID: " + sentMessage.getCorrelationID())
                        .hasSize(1);
            }
        });

        Then("the Message contains the PayloadSelector with one selector valued {string} and to be equal", (String selector) -> {
            List<Message> allMessages = ciseMessageCaptor.getAllValues();
            for (Message currentMessage : allMessages) {
                assertInstanceOf(PullRequest.class, currentMessage);

                PullRequest pullRequest = (PullRequest) currentMessage;
                assertNotNull(pullRequest.getPayloadSelector());

                PayloadSelector ps = pullRequest.getPayloadSelector();
                assertNotNull(ps.getSelectors());
                assertEquals(1, ps.getSelectors().size());

                SelectorCondition selectorCondition = ps.getSelectors().get(0);
                assertEquals(selector, selectorCondition.getSelector());
                assertEquals(ConditionOperatorType.EQUAL, selectorCondition.getOperator());
            }
        });

        Then("the Message contains the ServiceCapabilities with the values responseTime {int} maxNumberOfRequests {int} maxEntitiesPerMsg {int} and query to be done in best effort",
                (Integer responseTime, Integer maxNumberOfRequests, Integer maxEntitiesPerMsg) -> {
                    List<Message> allMessages = ciseMessageCaptor.getAllValues();
                    for (Message currentMessage : allMessages) {
                        assertInstanceOf(PullRequest.class, currentMessage);

                        PullRequest pullRequest = (PullRequest) currentMessage;
                        assertNotNull(pullRequest.getPayloadSelector());

                        ServiceCapability requests = pullRequest.getRequests();
                        assertNotNull(requests);
                        assertEquals(responseTime, requests.getExpectedResponseTime());
                        assertEquals(maxNumberOfRequests, requests.getMaxNumberOfRequests());
                        assertEquals(maxEntitiesPerMsg, requests.getMaxEntitiesPerMsg());
                        assertEquals(QueryByExampleType.BEST_EFFORT, requests.getQueryByExampleType());
                    }
                });

        Then("the Message is sent to all recipients in the list", () -> {
            if (discoveryProfiles != null && !discoveryProfiles.isEmpty()) {
                verify(outgoingMessageHandlerToNodePortMock, times(1)).sendStringMessage(any());
                List<Message> sentMessages = ciseMessageCaptor.getAllValues();
                assertThat(sentMessages.size()).isEqualTo(1);
            } else {
                verify(outgoingMessageHandlerToNodePortMock, times(currentRecipientsList.size())).sendStringMessage(any());
                List<Message> sentMessages = ciseMessageCaptor.getAllValues();
                assertThat(sentMessages.size()).isEqualTo(currentRecipientsList.size());
            }
        });

        Then("the {string} Message is sent without recipient", (String messageType) -> {
            verify(outgoingMessageHandlerToNodePortMock, times(1)).sendStringMessage(any());
            List<Message> sentMessages = ciseMessageCaptor.getAllValues();
            assertThat(sentMessages.size()).isEqualTo(1);
            if (messageType.equalsIgnoreCase(PUSH_MESSAGE)) {
                Push sentMessage = (Push) sentMessages.get(0);
                assertNull(sentMessage.getRecipient());
                assertEquals(discoveryProfiles, sentMessage.getDiscoveryProfiles());
            } else if (messageType.equalsIgnoreCase(PULL_REQUEST_MESSAGE)) {
                PullRequest sentMessage = (PullRequest) sentMessages.get(0);
                assertNull(sentMessage.getRecipient());
                assertEquals(discoveryProfiles, sentMessage.getDiscoveryProfiles());
            } else {
                fail("Unknown message type: " + messageType);
            }
        });

        Then("the Message is sent to one recipient", () -> {
            verify(outgoingMessageHandlerToNodePortMock, times(1)).sendStringMessage(any());
            List<Message> sentMessages = ciseMessageCaptor.getAllValues();
            assertThat(sentMessages.size()).isEqualTo(1);
        });

        Then("the constructed message does not use a payload from the legacy system", () -> {
            List<Message> allMessages = ciseMessageCaptor.getAllValues();
            for (Message msg : allMessages) {
                assertThat(msg.getPayload())
                        .as("Payload should be of type XmlEntityPayload")
                        .isInstanceOf(XmlEntityPayload.class);
                XmlEntityPayload xmlPayload = (XmlEntityPayload) msg.getPayload();
                assertThat(xmlPayload.getAnies())
                        .as("Anies list in the payload should be empty")
                        .isEmpty();
            }
        });

        Then("the constructed {string} message has a discoveryProfile", (String messageType) -> {
            List<Message> allMessages = ciseMessageCaptor.getAllValues();

            if (messageType.equalsIgnoreCase(PUSH_MESSAGE)) {
                for (Message message : allMessages) {
                    assertInstanceOf(Push.class, message);
                    assertThat(((Push) message).getDiscoveryProfiles()).isNotEmpty();
                }
            } else if (messageType.equalsIgnoreCase(PULL_REQUEST_MESSAGE)) {
                for (Message message : allMessages) {
                    assertInstanceOf(PullRequest.class, message);
                    assertThat(((PullRequest) message).getDiscoveryProfiles()).isNotEmpty();
                }
            } else {
                fail("Unknown message type: " + messageType);
            }

        });

        After(() -> {
            if (dateHelperMockedStatic != null) {
                dateHelperMockedStatic.close();
            }
        });
    }

    private void initContext(boolean isPersistenceTurnedOn) {
        if (isPersistenceTurnedOn) {
            this.isPersistenceTurnedOn = true;
            this.messageRepositoryPort = Optional.of(new MessageRepositoryPortAdapterStub());
            ctx = FunctionalAppContext
                    .builder()
                    .setMessageRepositoryPort(messageRepositoryPort.get())
                    .setSignatureService(signatureService)
                    .setOutgoingMessageHandlerToNodePort(outgoingMessageHandlerToNodePortMock)
                    .setRiskPullProviderService(riskPullProviderService)
                    .setVesselPullProviderService(vesselPullProviderService)
                    .setVesselPullConsumerService(vesselPullConsumerService)
                    .setRiskPushProviderService(riskPushProviderService)
                    .setVesselPushProviderService(vesselPushProviderService)
                    .setVesselSubscribeProviderService(vesselSubscribeProviderService)
                    .setRiskSubscribeConsumerService(riskSubscribeConsumerService)
                    .setVesselSubscribeConsumerService(vesselSubscribeConsumerService)
                    .buildWithFallbackToDefault();
        } else {
            ctx = FunctionalAppContext
                    .builder()
                    .setSignatureService(signatureService)
                    .setOutgoingMessageHandlerToNodePort(outgoingMessageHandlerToNodePortMock)
                    .setRiskPullProviderService(riskPullProviderService)
                    .setVesselPullProviderService(vesselPullProviderService)
                    .setVesselPullConsumerService(vesselPullConsumerService)
                    .setRiskPushProviderService(riskPushProviderService)
                    .setVesselPushProviderService(vesselPushProviderService)
                    .setVesselSubscribeProviderService(vesselSubscribeProviderService)
                    .setRiskSubscribeConsumerService(riskSubscribeConsumerService)
                    .setVesselSubscribeConsumerService(vesselSubscribeConsumerService)
                    .buildWithFallbackToDefault();
        }
    }

    private void readSignatureProperties() throws IOException {
        URI resourceURI = Utils.getResourceURI("signature.properties", SendMessageStepsDefinition.class);
        try (FileInputStream in = new FileInputStream(new File(resourceURI));) {
            signatureProperties.load(in);
        }
    }


    private static Service createTempService(ServiceRoleType serviceRoleType, ServiceOperationType serviceOperationType, String serviceId, ServiceType type) {
        return newService()
                .serviceRole(serviceRoleType)
                .operation(serviceOperationType)
                .id(serviceId)
                .type(type)
                .build();
    }

}