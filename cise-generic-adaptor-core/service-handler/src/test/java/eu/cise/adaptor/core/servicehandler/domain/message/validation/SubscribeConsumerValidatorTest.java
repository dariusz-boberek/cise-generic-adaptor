package eu.cise.adaptor.core.servicehandler.domain.message.validation;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceProfile;
import eu.cise.servicemodel.v1.service.ServiceRoleType;
import eu.cise.servicemodel.v1.service.ServiceType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static eu.cise.servicemodel.v1.service.ServiceRoleType.CONSUMER;
import static eu.cise.servicemodel.v1.service.ServiceRoleType.PROVIDER;
import static eu.cise.servicemodel.v1.service.ServiceType.RISK_SERVICE;
import static eu.cise.servicemodel.v1.service.ServiceType.VESSEL_SERVICE;
import static eu.eucise.helpers.ServiceProfileBuilder.newProfile;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

class SubscribeConsumerValidatorTest {

    private static SubscribeConsumerValidator subscribeConsumerValidator;
    private static Service currentService;
    private static MockedStatic<MessageDataContextValidator> messageDataContextValidatorMock;

    @BeforeAll
    static void setup() {

        subscribeConsumerValidator= new SubscribeConsumerValidator();

        currentService = new Service();
        currentService.setServiceRole(ServiceRoleType.CONSUMER);
        currentService.setServiceOperation(ServiceOperationType.SUBSCRIBE);
        currentService.setServiceType(VESSEL_SERVICE);

        messageDataContextValidatorMock = mockStatic(MessageDataContextValidator.class);
        messageDataContextValidatorMock.when(() -> MessageDataContextValidator.validateContext(any())).thenAnswer(invocationOnMock -> null);
    }

    @AfterAll
    static void tearDown() {
        messageDataContextValidatorMock.close();
    }


    @Test
    void it_constructs_and_sends_subscribe_consumer_message_to_one_recipient() throws CiseAdaptorValidationException {

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .recipients(singletonList(createServiceWithRole(PROVIDER)))
                .buildContext();

        assertDoesNotThrow(() -> subscribeConsumerValidator.validate(messageDataContext));
    }

    @Test
    void it_creates_subscribe_consumer_for_all_recipients_and_forwards_them_to_cise() throws CiseAdaptorValidationException {

        List<Service> recipients = asList(createServiceWithRole(PROVIDER), createServiceWithRole(PROVIDER));

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .recipients(recipients)
                .buildContext();

        assertDoesNotThrow(() -> subscribeConsumerValidator.validate(messageDataContext));


    }

    @Test
    void it_dont_create_subscribe_consumer_when_no_recipients_present() throws CiseAdaptorValidationException {

        MessageDataContext messageDataContext1 = MessageDataContext.getManager()
                .currentService(currentService)
                .buildContext();

        assertThrows(CiseAdaptorValidationException.class, () -> subscribeConsumerValidator.validate(messageDataContext1));

        // Empty list
        MessageDataContext messageDataContext2 = MessageDataContext.getManager()
                .currentService(currentService)
                .recipients(new ArrayList<>())
                .buildContext();

        assertThrows(CiseAdaptorValidationException.class, () -> subscribeConsumerValidator.validate(messageDataContext2));
    }


    // Subscribe Unknown

    @Test
    void it_constructs_a_subscribe_unknown_message() throws CiseAdaptorValidationException {
        // Given
        List<ServiceProfile> discoveryProfiles = new ArrayList<>();
        discoveryProfiles.add(newProfile().serviceType(VESSEL_SERVICE).serviceOperation(ServiceOperationType.SUBSCRIBE).build());
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .discoveryProfiles(discoveryProfiles)
                .buildContext();

        // When&Then
        assertDoesNotThrow(() -> subscribeConsumerValidator.validate(messageDataContext));
    }

    @Test
    void it_doesnt_create_subscribe_messages_when_invalid_discovery_profiles_for_service_type() throws CiseAdaptorValidationException {
        // Given
        ServiceType invalidServiceType = RISK_SERVICE;
        List<ServiceProfile> discoveryProfiles = new ArrayList<>();
        discoveryProfiles.add(newProfile().serviceType(invalidServiceType).serviceOperation(ServiceOperationType.SUBSCRIBE).build());
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .discoveryProfiles(discoveryProfiles)
                .buildContext();
        // When&Then
        Throwable exceptionThrowable = assertThrows(CiseAdaptorValidationException.class, () -> subscribeConsumerValidator.validate(messageDataContext));
        assertEquals("Validation error message: Discovery profile with ServiceType different from Sender ServiceType", exceptionThrowable.getMessage());
    }

    @Test
    void it_doesnt_create_subscribe_messages_when_invalid_discovery_profiles_for_service_role() throws CiseAdaptorValidationException {
        // Given
        ServiceRoleType invalidServiceRole = CONSUMER; // since the currentService is a Consumer it should discover Consumers
        ServiceType validServiceType = VESSEL_SERVICE;
        List<ServiceProfile> discoveryProfiles = new ArrayList<>();
        discoveryProfiles.add(newProfile().serviceRole(invalidServiceRole).serviceOperation(ServiceOperationType.SUBSCRIBE).serviceType(validServiceType).build());
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .discoveryProfiles(discoveryProfiles)
                .buildContext();
        // When&Then
        Throwable exceptionThrowable = assertThrows(CiseAdaptorValidationException.class, () -> subscribeConsumerValidator.validate(messageDataContext));
        assertEquals("Validation error message: Discovery profile with ServiceRole same as Sender ServiceRole", exceptionThrowable.getMessage());
    }

    @Test
    void it_doesnt_create_subscribe_messages_when_invalid_discovery_profiles_for_service_operation() throws CiseAdaptorValidationException {
        // Given
        ServiceOperationType invalidServiceOperation = ServiceOperationType.PULL; // since the currentService is Subscribe it should target Subscribe
        ServiceType validServiceType = VESSEL_SERVICE;
        List<ServiceProfile> discoveryProfiles = new ArrayList<>();
        discoveryProfiles.add(newProfile().serviceOperation(invalidServiceOperation).serviceType(validServiceType).build());
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .discoveryProfiles(discoveryProfiles)
                .buildContext();
        // When&Then

        assertThatThrownBy(() -> subscribeConsumerValidator.validate(messageDataContext))
                .isInstanceOf(CiseAdaptorValidationException.class)
                .hasMessageContaining("Validation error message: Discovery profile with ServiceOperation different from Sender ServiceOperation");
    }

    @Test
    void it_doesnt_create_subscribe_messages_when_no_recipienst_and_no_discovery_profile_present() throws CiseAdaptorValidationException {
        // Given
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .buildContext();
        // When&Then
        assertThatThrownBy(() -> subscribeConsumerValidator.validate(messageDataContext))
                .isInstanceOf(CiseAdaptorValidationException.class)
                .hasMessageContaining("Validation error message: Recipients list and discoveryProfiles list are both null or empty");
    }

    @Test
    void it_doesnt_create_subscribe_messages_when_both_recipients_and_discovery_profile_present() throws CiseAdaptorValidationException {
        // Given
        List<ServiceProfile> discoveryProfiles = new ArrayList<>();
        discoveryProfiles.add(newProfile().serviceType(VESSEL_SERVICE).serviceOperation(ServiceOperationType.SUBSCRIBE).build());
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .recipients(singletonList(createServiceWithRole(PROVIDER)))
                .discoveryProfiles(discoveryProfiles)
                .buildContext();
        // When&Then

        assertThatThrownBy(() -> subscribeConsumerValidator.validate(messageDataContext))
                .isInstanceOf(CiseAdaptorValidationException.class)
                .hasMessageContaining("Validation error message: Recipients list and discovery profile are both not empty");
    }

    private static int serviceId = 0;

    private static Service createServiceWithRole(ServiceRoleType serviceRoleType) {
        Service consumerService = new Service();
        consumerService.setServiceRole(serviceRoleType);
        consumerService.setServiceID("" + serviceId++);
        return consumerService;
    }
}