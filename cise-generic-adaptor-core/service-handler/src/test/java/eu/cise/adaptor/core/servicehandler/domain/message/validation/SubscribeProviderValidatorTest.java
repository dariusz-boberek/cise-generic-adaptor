package eu.cise.adaptor.core.servicehandler.domain.message.validation;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.adaptor.core.servicehandler.utils.ResourcesUtils;
import eu.cise.servicemodel.v1.message.CoreEntityPayload;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceProfile;
import eu.cise.servicemodel.v1.service.ServiceRoleType;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class SubscribeProviderValidatorTest {
    private static SubscribeProviderValidator subscribeProviderValidator;
    private static Service currentService;
    private static CoreEntityPayload payload;
    private static MockedStatic<MessageDataContextValidator> messageDataContextValidatorMock;

    @BeforeAll
    static void setup() {

        subscribeProviderValidator = new SubscribeProviderValidator();

        currentService = new Service();
        currentService.setServiceRole(ServiceRoleType.PROVIDER);
        currentService.setServiceOperation(ServiceOperationType.SUBSCRIBE);

        XmlMapper payloadXmlMapper = new DefaultXmlMapper.NotValidating();
        String payloadXML = ResourcesUtils.readResource("Payload_For_vesselPushProvider_Push_OK.xml", PushValidatorTest.class);
        payload = payloadXmlMapper.fromXML(payloadXML);

        messageDataContextValidatorMock = mockStatic(MessageDataContextValidator.class);
        messageDataContextValidatorMock.when(() -> MessageDataContextValidator.validateContext(any())).thenAnswer(invocationOnMock -> null);
    }

    @AfterAll
    static void tearDown() {
        messageDataContextValidatorMock.close();
    }

    @Test
    void it_creates_subscribe_provider_message_and_forwards_to_cise() throws CiseAdaptorValidationException {

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .buildContext();

        assertDoesNotThrow(() -> subscribeProviderValidator.validate(messageDataContext));

    }

    @Test
    void it_returns_server_error_when_recipients_are_present_for_subscribe_provider_message() throws CiseAdaptorValidationException {

        List<Service> recipients = new ArrayList<>();
        recipients.add(new Service());

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .recipients(recipients)
                .buildContext();

        assertThrows(CiseAdaptorValidationException.class, () -> subscribeProviderValidator.validate(messageDataContext));
    }

    @Test
    void it_returns_server_error_when_discovery_profiles_are_present_for_subscribe_provider_message() throws CiseAdaptorValidationException {

        List<ServiceProfile> discoveryProfiles = new ArrayList<>();
        discoveryProfiles.add(mock(ServiceProfile.class));

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .discoveryProfiles(discoveryProfiles)
                .currentService(currentService)
                .buildContext();

        assertThrows(CiseAdaptorValidationException.class, () -> subscribeProviderValidator.validate(messageDataContext));
    }
}