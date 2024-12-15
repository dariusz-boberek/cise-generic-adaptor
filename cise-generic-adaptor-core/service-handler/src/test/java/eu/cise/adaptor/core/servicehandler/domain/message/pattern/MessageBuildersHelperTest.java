package eu.cise.adaptor.core.servicehandler.domain.message.pattern;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.adaptor.core.servicehandler.domain.message.pattern.subscribe.SubscribeConsumerHelper;
import eu.cise.adaptor.core.servicehandler.domain.message.pattern.subscribe.SubscribeProviderHelper;
import eu.cise.adaptor.core.servicehandler.utils.ResourcesUtils;
import eu.cise.servicemodel.v1.message.CoreEntityPayload;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PriorityType;
import eu.cise.servicemodel.v1.message.PullRequest;
import eu.cise.servicemodel.v1.message.RetryStrategyType;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceRoleType;
import eu.eucise.helpers.PullRequestBuilder;
import eu.eucise.helpers.ServiceBuilder;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageBuildersHelperTest {

    private static CoreEntityPayload payload;

    @BeforeAll
    static void setup() {
        String payloadXML = ResourcesUtils.readResource("Payload_For_vesselPullProvider_PullResponse_OK.xml", MessageBuildersHelperTest.class);
        XmlMapper payloadXmlMapper = new DefaultXmlMapper.NotValidating();
        payload = payloadXmlMapper.fromXML(payloadXML);
    }

    @Test
    void it_helps_to_build_pull_response() throws CiseAdaptorValidationException {

        Service senderService = ServiceBuilder.newService()
                .serviceRole(ServiceRoleType.CONSUMER)
                .operation(ServiceOperationType.PULL)
                .id("pull.consumer.service.id")
                .build();

        Service currentService = ServiceBuilder.newService()
                .serviceRole(ServiceRoleType.PROVIDER)
                .operation(ServiceOperationType.PULL)
                .id("pull.provider.service.id")
                .build();

        PullRequest pullRequest = PullRequestBuilder.newPullRequest()
                .correlationId("correlation-id-pull-request")
                .contextId("context-id-pull-request")
                .generateId()
                .sender(senderService)
                .recipient(currentService)
                .build();

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .referenceMessage(pullRequest)
                .currentService(currentService)
                .cisePayload(payload)
                .buildContext();

        List<Message> pullResponseList = PullResponseHelper.generateListOfPullResponseMessage(messageDataContext);

        for(Message pullResponse : pullResponseList) {
            assertEquals(pullRequest.getCorrelationID(), pullResponse.getCorrelationID());
            assertEquals(pullRequest.getContextID(), pullResponse.getContextID());
            assertEquals(pullRequest.getRecipient().getServiceID(), pullResponse.getSender().getServiceID());
            assertEquals(pullRequest.getSender().getServiceID(), pullResponse.getRecipient().getServiceID());
            assertEquals(payload, pullResponse.getPayload());
        }
    }

    @Test
    void it_helps_to_build_push_known() throws CiseAdaptorValidationException {

        Service recipientService = ServiceBuilder.newService()
                .serviceRole(ServiceRoleType.CONSUMER)
                .operation(ServiceOperationType.PUSH)
                .id("push.consumer.service.id")
                .build();

        Service currentService = ServiceBuilder.newService()
                .serviceRole(ServiceRoleType.PROVIDER)
                .operation(ServiceOperationType.PUSH)
                .id("push.provider.service.id")
                .build();


        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .recipients(Collections.singletonList(recipientService))
                .contextId("desired-context-id")
                .cisePayload(payload)
                .buildContext();

        List<Message> pushKnownList = PushHelper.generateListOfPushMessage(messageDataContext);
        Message pushKnown = pushKnownList.get(0);

        assertTrue(StringUtils.isNotBlank(pushKnown.getMessageID()));
        assertTrue(StringUtils.isNotBlank(pushKnown.getCorrelationID()));
        assertTrue(StringUtils.isNotBlank(pushKnown.getContextID()));

        assertEquals(pushKnown.getCorrelationID(), pushKnown.getCorrelationID());
        assertEquals("desired-context-id", pushKnown.getContextID());
        assertEquals(pushKnown.getSender().getServiceID(), currentService.getServiceID());
        assertEquals(pushKnown.getRecipient().getServiceID(), recipientService.getServiceID());
        assertEquals(payload, pushKnown.getPayload());

    }

    @Test
    void it_helps_to_build_subscribe_consumer() throws CiseAdaptorValidationException {

        Service recipientService = ServiceBuilder.newService()
                .serviceRole(ServiceRoleType.PROVIDER)
                .operation(ServiceOperationType.PULL)
                .id("subscribe.provider.service.id")
                .build();

        Service currentService = ServiceBuilder.newService()
                .serviceRole(ServiceRoleType.CONSUMER)
                .operation(ServiceOperationType.SUBSCRIBE)
                .id("subscribe.consumer.service.id")
                .build();

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .recipients(Collections.singletonList(recipientService))
                .contextId("desired-context-id")
                .buildContext();

        List<Message> subscribeConsumerList = SubscribeConsumerHelper.generateListOfSubscribeConsumerMessage(messageDataContext);

        Message subscribeConsumer = subscribeConsumerList.get(0);
        assertTrue(StringUtils.isNotBlank(subscribeConsumer.getMessageID()));
        assertTrue(StringUtils.isNotBlank(subscribeConsumer.getCorrelationID()));
        assertTrue(StringUtils.isNotBlank(subscribeConsumer.getContextID()));

        assertEquals(subscribeConsumer.getCorrelationID(), subscribeConsumer.getCorrelationID());
        assertEquals("desired-context-id", subscribeConsumer.getContextID());
        assertEquals(subscribeConsumer.getSender().getServiceID(), currentService.getServiceID());
        assertEquals(subscribeConsumer.getRecipient().getServiceID(), recipientService.getServiceID());
    }

    @Test
    void it_helps_to_build_subscribe_provider() throws CiseAdaptorValidationException {

        Service currentService = ServiceBuilder.newService()
                .serviceRole(ServiceRoleType.PROVIDER)
                .operation(ServiceOperationType.SUBSCRIBE)
                .id("subscribe.provider.service.id")
                .build();

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .contextId("desired-context-id")
                .cisePayload(payload)
                .buildContext();

        List<Message> subscribeProviderList = SubscribeProviderHelper.generateListOfSubscribeProviderMessage(messageDataContext);

        for (Message subscribeProvider : subscribeProviderList ) {
            assertTrue(StringUtils.isNotBlank(subscribeProvider.getMessageID()));
            assertTrue(StringUtils.isNotBlank(subscribeProvider.getCorrelationID()));
            assertTrue(StringUtils.isNotBlank(subscribeProvider.getContextID()));

            assertEquals(subscribeProvider.getCorrelationID(), subscribeProvider.getCorrelationID());
            assertEquals("desired-context-id", subscribeProvider.getContextID());
            assertEquals(subscribeProvider.getSender().getServiceID(), currentService.getServiceID());
            assertEquals(payload, subscribeProvider.getPayload());
        }
    }

    @Test
    void it_checks_the_common_parts_are_added() throws CiseAdaptorValidationException {
        Boolean useAck = Boolean.TRUE;
        PriorityType priority = PriorityType.LOW;
        RetryStrategyType retryStrategy = RetryStrategyType.NO_RETRY;

        Service currentService = ServiceBuilder.newService()
                .serviceRole(ServiceRoleType.PROVIDER)
                .operation(ServiceOperationType.SUBSCRIBE)
                .id("subscribe.provider.service.id")
                .build();

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .contextId("desired-context-id")
                .cisePayload(payload)
                .requiresAck(useAck)
                .priority(priority)
                .retryStrategy(retryStrategy)
                .buildContext();

        List<Message> subscribeProviderList = SubscribeProviderHelper.generateListOfSubscribeProviderMessage(messageDataContext);

        for (Message subscribeProvider : subscribeProviderList) {
            assertEquals(useAck, subscribeProvider.isRequiresAck());
            assertEquals(priority, subscribeProvider.getPriority());
            assertEquals(retryStrategy, subscribeProvider.getReliability().getRetryStrategy());
        }
    }
}