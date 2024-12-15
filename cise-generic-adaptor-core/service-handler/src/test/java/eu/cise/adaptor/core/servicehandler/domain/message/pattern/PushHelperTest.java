package eu.cise.adaptor.core.servicehandler.domain.message.pattern;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.adaptor.core.servicehandler.utils.ResourcesUtils;
import eu.cise.servicemodel.v1.authority.SeaBasinType;
import eu.cise.servicemodel.v1.message.CoreEntityPayload;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.servicemodel.v1.service.DataFreshnessType;
import eu.cise.servicemodel.v1.service.Service;
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

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PushHelperTest {

    private static CoreEntityPayload payload;
    private static Service currentService;
    private static Service recipientService_1;

    private static Service recipientService_2;
    private static ServiceProfile discoveryProfile_1;

    private static ServiceProfile discoveryProfile_2;

    @BeforeAll
    static void setup() {
        String payloadXML = ResourcesUtils.readResource("Payload_For_vesselPushProvider_Push_OK.xml", PushHelperTest.class);
        XmlMapper payloadXmlMapper = new DefaultXmlMapper.NotValidating();
        payload = payloadXmlMapper.fromXML(payloadXML);

        currentService = ServiceBuilder.newService()
                .serviceRole(ServiceRoleType.PROVIDER)
                .operation(ServiceOperationType.PUSH)
                .type(ServiceType.VESSEL_SERVICE)
                .id("push.provider.service.id")
                .build();

        recipientService_1 = ServiceBuilder.newService()
                .serviceRole(ServiceRoleType.CONSUMER)
                .operation(ServiceOperationType.PUSH)
                .type(ServiceType.VESSEL_SERVICE)
                .id("push.consumer.service.id")
                .build();

        recipientService_2 = ServiceBuilder.newService()
                .serviceRole(ServiceRoleType.CONSUMER)
                .operation(ServiceOperationType.PUSH)
                .type(ServiceType.VESSEL_SERVICE)
                .id("push.consumer.service.id2")
                .build();

        discoveryProfile_1 = ServiceProfileBuilder.newProfile()
                .serviceRole(ServiceRoleType.CONSUMER)
                .serviceOperation(ServiceOperationType.PUSH)
                .serviceType(ServiceType.VESSEL_SERVICE)
                .seaBasin(SeaBasinType.MEDITERRANEAN)
                .dataFreshness(DataFreshnessType.NEARLY_REAL_TIME)
                .build();

        discoveryProfile_2 = ServiceProfileBuilder.newProfile()
                .serviceRole(ServiceRoleType.CONSUMER)
                .serviceOperation(ServiceOperationType.PUSH)
                .serviceType(ServiceType.VESSEL_SERVICE)
                .seaBasin(SeaBasinType.MEDITERRANEAN)
                .dataFreshness(DataFreshnessType.NEARLY_REAL_TIME)
                .build();


    }


    @Test
    void it_builds_push_known_with_one_recipient() throws CiseAdaptorValidationException {

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .recipients(Collections.singletonList(recipientService_1))
                .buildContext();

        List<Message> pushMessagesList = PushHelper.generateListOfPushMessage(messageDataContext);

        assertTrue(CollectionUtils.isNotEmpty(pushMessagesList));
        Push pushMessage = (Push) pushMessagesList.get(0);
        assertTrue(CollectionUtils.isEmpty(pushMessage.getDiscoveryProfiles()));
        assertEquals(currentService.getServiceID(), pushMessage.getSender().getServiceID());
        assertEquals(recipientService_1.getServiceID(), pushMessage.getRecipient().getServiceID());

    }

    @Test
    void it_builds_two_push_known_with_two_recipients() throws CiseAdaptorValidationException {

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .recipients(List.of(recipientService_1, recipientService_2))
                .buildContext();

        List<Message> pushMessagesList = PushHelper.generateListOfPushMessage(messageDataContext);

        assertTrue(CollectionUtils.isNotEmpty(pushMessagesList));
        assertEquals(2, pushMessagesList.size());
        // first message
        Push pushMessage1 = (Push) pushMessagesList.get(0);
        assertTrue(CollectionUtils.isEmpty(pushMessage1.getDiscoveryProfiles()));
        assertEquals(currentService.getServiceID(), pushMessage1.getSender().getServiceID());
        assertEquals(recipientService_1.getServiceID(), pushMessage1.getRecipient().getServiceID());

        // second message
        Push pushMessage2 = (Push) pushMessagesList.get(1);
        assertTrue(CollectionUtils.isEmpty(pushMessage2.getDiscoveryProfiles()));
        assertEquals(currentService.getServiceID(), pushMessage2.getSender().getServiceID());
        assertEquals(recipientService_2.getServiceID(), pushMessage2.getRecipient().getServiceID());

    }

    @Test
    void it_builds_simple_push_unknown() throws CiseAdaptorValidationException {

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .discoveryProfiles(List.of(discoveryProfile_1))
                .buildContext();

        List<Message> pushMessagesList = PushHelper.generateListOfPushMessage(messageDataContext);

        assertTrue(CollectionUtils.isNotEmpty(pushMessagesList));
        assertEquals(1, pushMessagesList.size());
        // first message
        Push pushMessage1 = (Push) pushMessagesList.get(0);
        assertEquals(1, pushMessage1.getDiscoveryProfiles().size());
        assertEquals(discoveryProfile_1, pushMessage1.getDiscoveryProfiles().get(0));
        assertNull(pushMessage1.getRecipient());
        assertEquals(currentService.getServiceID(), pushMessage1.getSender().getServiceID());
    }

    @Test
    void it_builds_simple_push_unknown_with_multiple_profiles() throws CiseAdaptorValidationException {

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .discoveryProfiles(List.of(discoveryProfile_1, discoveryProfile_2))
                .buildContext();

        List<Message> pushMessagesList = PushHelper.generateListOfPushMessage(messageDataContext);

        assertTrue(CollectionUtils.isNotEmpty(pushMessagesList));
        assertEquals(1, pushMessagesList.size());
        // first message
        Push pushMessage1 = (Push) pushMessagesList.get(0);
        assertEquals(2, pushMessage1.getDiscoveryProfiles().size());
        assertEquals(discoveryProfile_1, pushMessage1.getDiscoveryProfiles().get(0));
        assertEquals(discoveryProfile_2, pushMessage1.getDiscoveryProfiles().get(1));
        assertNull(pushMessage1.getRecipient());
        assertEquals(currentService.getServiceID(), pushMessage1.getSender().getServiceID());
    }

}