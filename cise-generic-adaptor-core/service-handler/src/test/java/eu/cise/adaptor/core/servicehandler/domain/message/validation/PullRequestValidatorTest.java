package eu.cise.adaptor.core.servicehandler.domain.message.validation;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.adaptor.core.servicehandler.utils.ResourcesUtils;
import eu.cise.servicemodel.v1.message.ConditionOperatorType;
import eu.cise.servicemodel.v1.message.CoreEntityPayload;
import eu.cise.servicemodel.v1.message.PayloadSelector;
import eu.cise.servicemodel.v1.message.SelectorCondition;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceCapability;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceProfile;
import eu.cise.servicemodel.v1.service.ServiceRoleType;
import eu.cise.servicemodel.v1.service.ServiceType;
import eu.cise.servicemodel.v1.service.SubscriptionCapability;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;

import static eu.cise.servicemodel.v1.service.ServiceRoleType.PROVIDER;
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

class PullRequestValidatorTest {

    private static PullRequestValidator pullRequestValidator;
    private static Service currentService;
    private static CoreEntityPayload payload;
    private static MockedStatic<MessageDataContextValidator> messageDataContextValidatorMock;

    @BeforeAll
    static void setup() {
        pullRequestValidator = new PullRequestValidator();

        currentService = new Service();
        currentService.setServiceRole(ServiceRoleType.CONSUMER);
        currentService.setServiceOperation(ServiceOperationType.PULL);
        currentService.setServiceType(VESSEL_SERVICE);

        XmlMapper payloadXmlMapper = new DefaultXmlMapper.NotValidating();
        String payloadXML = ResourcesUtils.readResource("Payload_For_vesselPullConsumer_PullRequest_OK.xml", PullRequestValidatorTest.class);
        payload = payloadXmlMapper.fromXML(payloadXML);

        messageDataContextValidatorMock = mockStatic(MessageDataContextValidator.class);
        messageDataContextValidatorMock.when(() -> MessageDataContextValidator.validateContext(any())).thenAnswer(invocationOnMock -> null);
    }

    @AfterAll
    static void tearDown() {
        messageDataContextValidatorMock.close();
    }

    @Test
    void it_validates_pull_request_parameters_ok() throws CiseAdaptorValidationException {
        // Arrange
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .cisePayload(payload)
                .recipients(singletonList(createServiceWithRole(PROVIDER)))
                .buildContext();

        // Act & Assert
        assertDoesNotThrow(() -> pullRequestValidator.validate(messageDataContext));
    }

    @Test
    void it_validate_pullRequestPayloadSelector_against_payload() throws XPathExpressionException, CiseAdaptorValidationException {
        PayloadSelector ps = buildPayloadSelector("//Vessel[1]/IMONumber");

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .cisePayload(payload)
                .recipients(singletonList(createServiceWithRole(PROVIDER)))
                .pullRequestPayloadSelector(ps)
                .buildContext();

        assertDoesNotThrow(() -> pullRequestValidator.validate(messageDataContext));
    }

    @Test
    void it_fails_to_validate_pullRequestPayloadSelector_against_payload() throws CiseAdaptorValidationException {
        String selector = "//Vessel[2]/IMONumber";
        PayloadSelector ps = buildPayloadSelector(selector);

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .cisePayload(payload)
                .recipients(singletonList(createServiceWithRole(PROVIDER)))
                .pullRequestPayloadSelector(ps)
                .buildContext();

        assertThatThrownBy(() -> pullRequestValidator.validate(messageDataContext))
                .isInstanceOf(CiseAdaptorValidationException.class)
                .hasMessageContaining("The PayloadSelector returned an empty value for this pattern: " + selector);
    }

    @Test
    void it_fail_to_validate_parameters_for_pull_request_no_recipient_service() throws CiseAdaptorValidationException {
        // Arrange
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .cisePayload(payload)
                // Not setting recipients to simulate no recipient service scenario
                .buildContext();

        // Act & Assert
        assertThatThrownBy(() -> pullRequestValidator.validate(messageDataContext))
                .isInstanceOf(CiseAdaptorValidationException.class)
                .hasMessageContaining("Validation error message: Recipients list and discoveryProfiles list are both null or empty");
    }

    @Test
    void it_fails_validation_when_pullRequestRequests_field_is_not_ServiceCapability() throws CiseAdaptorValidationException {
        // Arrange
        SubscriptionCapability nonServiceCapabilityObject = new SubscriptionCapability();

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .cisePayload(payload)
                .recipients(singletonList(createServiceWithRole(PROVIDER)))
                .pullRequestRequests((ServiceCapability) nonServiceCapabilityObject) // Casting to simulate incorrect type
                .buildContext();

        // Act & Assert
        assertThatThrownBy(() -> pullRequestValidator.validate(messageDataContext))
                .isInstanceOf(CiseAdaptorValidationException.class)
                .hasMessageContaining("Requests cannot be an instance of SubscriptionCapability");
    }

    @Test
    void it_fail_to_validate_parameters_for_pull_request_no_PullRequestResponseTimeOut() throws CiseAdaptorValidationException {
        // Arrange
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .cisePayload(payload)
                .recipients(singletonList(createServiceWithRole(PROVIDER)))
                .pullRequestResponseTimeOut(null)
                .buildContext();

        // Act & Assert
        assertThatThrownBy(() -> pullRequestValidator.validate(messageDataContext))
                .isInstanceOf(CiseAdaptorValidationException.class)
                .hasMessageContaining("pullRequestResponseTimeOut is mandatory, cannot be null or below equal to zero");
    }

    @Test
    void it_throws_error_when_invalid_destination() throws CiseAdaptorValidationException {
        // Arrange
        List<ServiceProfile> discoveryProfiles = new ArrayList<>();
        discoveryProfiles.add(newProfile().serviceType(VESSEL_SERVICE).serviceOperation(ServiceOperationType.PUSH).build());

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .recipients(singletonList(createServiceWithRole(PROVIDER)))
                .discoveryProfiles(discoveryProfiles)
                .cisePayload(payload)
                .buildContext();

        // Act & Assert
        assertThatThrownBy(() -> pullRequestValidator.validate(messageDataContext))
                .isInstanceOf(CiseAdaptorValidationException.class)
                .hasMessageContaining("Validation error message: Recipients list and discovery profile are both not empty");
    }

    @Test
    void it_creates_pull_request_for_all_recipients() throws CiseAdaptorValidationException {
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .recipients(asList(createServiceWithRole(PROVIDER), createServiceWithRole(PROVIDER)))
                .buildContext();

        assertDoesNotThrow(() -> pullRequestValidator.validate(messageDataContext));
    }

    @Test
    void it_doesnt_create_pull_request_when_no_recipients_present() throws CiseAdaptorValidationException {
        // Given
        List<Service> recipients = new ArrayList<>();

        // No recipients
        MessageDataContext messageDataContext1 = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .buildContext();

        assertThrows(CiseAdaptorValidationException.class, () -> pullRequestValidator.validate(messageDataContext1));

        // Empty list
        MessageDataContext messageDataContext2 = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .recipients(new ArrayList<>())
                .buildContext();

        assertThrows(CiseAdaptorValidationException.class, () -> pullRequestValidator.validate(messageDataContext2));
    }

    @Test
    void it_constructs_a_pull_request_unknown() throws CiseAdaptorValidationException {
        // Given
        List<ServiceProfile> discoveryProfiles = new ArrayList<>();
        discoveryProfiles.add(newProfile().serviceType(VESSEL_SERVICE).serviceOperation(ServiceOperationType.PULL).build());

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .discoveryProfiles(discoveryProfiles)
                .buildContext();

        // When&Then
        assertDoesNotThrow(() -> pullRequestValidator.validate(messageDataContext));
    }

    @Test
    void it_doesnt_create_pull_request_when_invalid_discovery_profiles_for_service_type() throws CiseAdaptorValidationException {
        // Given
        ServiceType invalidServiceType = ServiceType.RISK_SERVICE;
        List<ServiceProfile> discoveryProfiles = new ArrayList<>();
        discoveryProfiles.add(newProfile().serviceType(invalidServiceType).serviceOperation(ServiceOperationType.PULL).build());
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .discoveryProfiles(discoveryProfiles)
                .buildContext();
        // When&Then
        Throwable exceptionThrowable = assertThrows(CiseAdaptorValidationException.class, () -> pullRequestValidator.validate(messageDataContext));
        assertEquals("Validation error message: Discovery profile with ServiceType different from Sender ServiceType", exceptionThrowable.getMessage());
    }

    @Test
    void it_doesnt_create_pull_request_when_invalid_discovery_profiles_for_service_role() throws CiseAdaptorValidationException {
        // Given
        ServiceRoleType invalidServiceRole = ServiceRoleType.CONSUMER; // since the currentService is a Consumer it should discover Providers
        ServiceType validServiceType = VESSEL_SERVICE;
        List<ServiceProfile> discoveryProfiles = new ArrayList<>();
        discoveryProfiles.add(newProfile().serviceRole(invalidServiceRole).serviceOperation(ServiceOperationType.PULL).serviceType(validServiceType).build());
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .discoveryProfiles(discoveryProfiles)
                .buildContext();
        // When&Then
        Throwable exceptionThrowable = assertThrows(CiseAdaptorValidationException.class, () -> pullRequestValidator.validate(messageDataContext));
        assertEquals("Validation error message: Discovery profile with ServiceRole same as Sender ServiceRole", exceptionThrowable.getMessage());
    }

    @Test
    void it_doesnt_create_pull_request_when_invalid_discovery_profiles_for_service_operation() throws CiseAdaptorValidationException {
        // Given
        ServiceOperationType invalidServiceOperation = ServiceOperationType.PUSH; // since the currentService is Pull it should target Pull
        ServiceType validServiceType = VESSEL_SERVICE;
        List<ServiceProfile> discoveryProfiles = new ArrayList<>();
        discoveryProfiles.add(newProfile().serviceOperation(invalidServiceOperation).serviceType(validServiceType).build());
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .discoveryProfiles(discoveryProfiles)
                .buildContext();
        // When&Then
        assertThatThrownBy(() -> pullRequestValidator.validate(messageDataContext))
                .isInstanceOf(CiseAdaptorValidationException.class)
                .hasMessageContaining("Validation error message: Discovery profile with ServiceOperation different from Sender ServiceOperation");
    }

    @Test
    void it_doesnt_create_pull_request_when_no_recipients_and_no_discovery_profile_present() throws CiseAdaptorValidationException {
        // Given
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .buildContext();
        // When&Then
        assertThatThrownBy(() -> pullRequestValidator.validate(messageDataContext))
                .isInstanceOf(CiseAdaptorValidationException.class)
                .hasMessageContaining("Validation error message: Recipients list and discoveryProfiles list are both null or empty");
    }

    @Test
    void it_doesnt_create_pull_request_when_both_recipients_and_discovery_profile_present() throws CiseAdaptorValidationException {
        // Given
        List<ServiceProfile> discoveryProfiles = new ArrayList<>();
        discoveryProfiles.add(newProfile().serviceType(VESSEL_SERVICE).serviceOperation(ServiceOperationType.PULL).build());
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .cisePayload(payload)
                .currentService(currentService)
                .recipients(singletonList(createServiceWithRole(PROVIDER)))
                .discoveryProfiles(discoveryProfiles)
                .buildContext();
        // When&Then
        assertThatThrownBy(() -> pullRequestValidator.validate(messageDataContext))
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

    private PayloadSelector buildPayloadSelector(String selector) {
        PayloadSelector pullRequestPayloadSelector = new PayloadSelector();
        SelectorCondition sc = new SelectorCondition();
        sc.setSelector(selector);
        sc.setOperator(ConditionOperatorType.EQUAL);
        pullRequestPayloadSelector.getSelectors().add(sc);
        return pullRequestPayloadSelector;
    }
}