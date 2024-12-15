package eu.cise.adaptor.core.servicehandler.domain.message.pattern;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.adaptor.core.servicehandler.utils.ResourcesUtils;
import eu.cise.servicemodel.v1.authority.SeaBasinType;
import eu.cise.servicemodel.v1.message.ConditionOperatorType;
import eu.cise.servicemodel.v1.message.CoreEntityPayload;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PayloadSelector;
import eu.cise.servicemodel.v1.message.PullRequest;
import eu.cise.servicemodel.v1.message.SelectorCondition;
import eu.cise.servicemodel.v1.service.DataFreshnessType;
import eu.cise.servicemodel.v1.service.QueryByExampleType;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceCapability;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceProfile;
import eu.cise.servicemodel.v1.service.ServiceRoleType;
import eu.cise.servicemodel.v1.service.ServiceType;
import eu.eucise.helpers.ServiceBuilder;
import eu.eucise.helpers.ServiceProfileBuilder;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PullRequestHelperTest {

    private static CoreEntityPayload payload;
    private static Service currentService;
    private static Service recipientService;
    private static ServiceProfile discoveryProfile;

    @BeforeAll
    static void setup() {
        String payloadXML = ResourcesUtils.readResource("Payload_For_vesselPullProvider_PullResponse_OK.xml", PullRequestHelperTest.class);
        XmlMapper payloadXmlMapper = new DefaultXmlMapper.NotValidating();
        payload = payloadXmlMapper.fromXML(payloadXML);

        currentService = ServiceBuilder.newService()
                .serviceRole(ServiceRoleType.CONSUMER)
                .operation(ServiceOperationType.PULL)
                .type(ServiceType.VESSEL_SERVICE)
                .id("pull.consumer.service.id")
                .build();

        recipientService = ServiceBuilder.newService()
                .serviceRole(ServiceRoleType.PROVIDER)
                .operation(ServiceOperationType.PULL)
                .type(ServiceType.VESSEL_SERVICE)
                .id("pull.provider.service.id")
                .build();

        discoveryProfile = ServiceProfileBuilder.newProfile()
                .serviceRole(ServiceRoleType.PROVIDER)
                .serviceOperation(ServiceOperationType.PULL)
                .serviceType(ServiceType.VESSEL_SERVICE)
                .seaBasin(SeaBasinType.MEDITERRANEAN)
                .dataFreshness(DataFreshnessType.NEARLY_REAL_TIME)
                .build();


    }

    @Test
    void it_builds_simple_pull_request_known() throws CiseAdaptorValidationException {

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .recipients(List.of(recipientService))
                .cisePayload(payload)
                .buildContext();

        List<Message> pullRequestList = PullRequestHelper.generateListOfPullRequestMessage(messageDataContext);

        assertTrue(CollectionUtils.isNotEmpty(pullRequestList));
        PullRequest pullRequest = (PullRequest) pullRequestList.get(0);
        assertTrue(CollectionUtils.isEmpty(pullRequest.getDiscoveryProfiles()));
        assertEquals(currentService.getServiceID(), pullRequest.getSender().getServiceID());
        assertEquals(recipientService.getServiceID(), pullRequest.getRecipient().getServiceID());
    }

    @Test
    void it_builds_simple_pull_request_unknown() throws CiseAdaptorValidationException {

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .discoveryProfiles(List.of(discoveryProfile))
                .cisePayload(payload)
                .buildContext();

        List<Message> pullRequestList = PullRequestHelper.generateListOfPullRequestMessage(messageDataContext);

        assertTrue(CollectionUtils.isNotEmpty(pullRequestList));
        PullRequest pullRequest = (PullRequest) pullRequestList.get(0);
        assertEquals(currentService.getServiceID(), pullRequest.getSender().getServiceID());
        assertNull(pullRequest.getRecipient());
        assertTrue(CollectionUtils.isNotEmpty(pullRequest.getDiscoveryProfiles()));
        assertEquals(discoveryProfile, pullRequest.getDiscoveryProfiles().get(0));
    }

    @Test
    void it_builds_pull_request_with_non_mandatory_params() throws CiseAdaptorValidationException {

        Integer pullRequestResponseTimeOut = 12000;
        String selector ="//Vessel[1]/IMONumber";
        PayloadSelector payloadSelector = buildPayloadSelector(selector);

        Integer responseTime = 32;
        Integer maxNumberOfRequests = 1;
        Integer maxEntitiesPerMsg = 100;
        ServiceCapability requests = buildServiceCapability(responseTime ,maxNumberOfRequests ,maxEntitiesPerMsg);

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .recipients(List.of(recipientService))
                .cisePayload(payload)
                .pullRequestResponseTimeOut(pullRequestResponseTimeOut)
                .pullRequestPayloadSelector(payloadSelector)
                .pullRequestRequests(requests)
                .buildContext();

        List<Message> pullRequestList = PullRequestHelper.generateListOfPullRequestMessage(messageDataContext);

        assertTrue(CollectionUtils.isNotEmpty(pullRequestList));
        PullRequest pullRequest = (PullRequest) pullRequestList.get(0);
        assertEquals(currentService.getServiceID(), pullRequest.getSender().getServiceID());
        assertEquals(recipientService.getServiceID(), pullRequest.getRecipient().getServiceID());

        assertEquals(pullRequestResponseTimeOut, pullRequest.getResponseTimeOut());
        assertEquals(payloadSelector, pullRequest.getPayloadSelector());
        assertEquals(requests, pullRequest.getRequests());
    }

    @Test
    void it_builds_pull_request_with_multiple_discovery_profiles() throws CiseAdaptorValidationException {
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .discoveryProfiles(List.of(discoveryProfile, discoveryProfile))
                .cisePayload(payload)
                .buildContext();

        List<Message> pullRequestList = PullRequestHelper.generateListOfPullRequestMessage(messageDataContext);

        assertTrue(CollectionUtils.isNotEmpty(pullRequestList));
        PullRequest pullRequest = (PullRequest) pullRequestList.get(0);
        assertEquals(currentService.getServiceID(), pullRequest.getSender().getServiceID());
        assertNull(pullRequest.getRecipient());
        assertTrue(CollectionUtils.isNotEmpty(pullRequest.getDiscoveryProfiles()));
        assertEquals(2, pullRequest.getDiscoveryProfiles().size());
        assertEquals(discoveryProfile, pullRequest.getDiscoveryProfiles().get(0));
        assertEquals(discoveryProfile, pullRequest.getDiscoveryProfiles().get(1));
    }


    private PayloadSelector buildPayloadSelector(String selector) {
        PayloadSelector payloadSelector = new PayloadSelector();
        SelectorCondition sc = new SelectorCondition();
        sc.setSelector(selector);
        sc.setOperator(ConditionOperatorType.EQUAL);
        payloadSelector.getSelectors().add(sc);

        return payloadSelector;
    }

    private ServiceCapability buildServiceCapability (Integer responseTime, Integer maxNumberOfRequests, Integer maxEntitiesPerMsg) {
        ServiceCapability requests = new ServiceCapability();
        requests.setExpectedResponseTime(responseTime);
        requests.setMaxNumberOfRequests(maxNumberOfRequests);
        requests.setMaxEntitiesPerMsg(maxEntitiesPerMsg);
        requests.setQueryByExampleType(QueryByExampleType.BEST_EFFORT);

        return requests;
    }

}