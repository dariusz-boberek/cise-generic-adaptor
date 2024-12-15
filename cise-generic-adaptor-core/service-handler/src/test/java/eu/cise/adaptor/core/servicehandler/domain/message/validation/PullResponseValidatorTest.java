package eu.cise.adaptor.core.servicehandler.domain.message.validation;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.adaptor.core.servicehandler.utils.ResourcesUtils;
import eu.cise.servicemodel.v1.message.CoreEntityPayload;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceRoleType;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

class PullResponseValidatorTest {

    private static PullResponseValidator pullResponseValidator;
    private static Service currentService;

    private static Service previousMessageService;
    private static XmlMapper payloadXmlMapper;
    private static XmlMapper messageXmlMapper;
    private static CoreEntityPayload payload;

    private static Message referencePullRequestMessage;

    private static MockedStatic<MessageDataContextValidator> messageDataContextValidatorMock;

    @BeforeAll
    static void setup() {
        pullResponseValidator= new PullResponseValidator();
        currentService = new Service();
        currentService.setServiceRole(ServiceRoleType.PROVIDER);
        currentService.setServiceOperation(ServiceOperationType.PULL);
        currentService.setServiceID("1");
        previousMessageService = new Service();
        previousMessageService.setServiceOperation(ServiceOperationType.PULL);
        previousMessageService.setServiceRole(ServiceRoleType.CONSUMER);
        previousMessageService.setServiceID("2");
        messageXmlMapper = new DefaultXmlMapper();
        payloadXmlMapper = new DefaultXmlMapper.NotValidating();
        String payloadXML = ResourcesUtils.readResource("Payload_For_vesselPullProvider_PullResponse_OK.xml", PullResponseValidatorTest.class);
        payload = payloadXmlMapper.fromXML(payloadXML);
        messageDataContextValidatorMock = mockStatic(MessageDataContextValidator.class);
        messageDataContextValidatorMock.when(() -> MessageDataContextValidator.validateContext(any())).thenAnswer(invocationOnMock -> null);
        referencePullRequestMessage = messageXmlMapper.fromXML(ResourcesUtils.readResource("PullRequest_riskPullProvider_Signature_OK.xml", PullResponseValidatorTest.class));
    }

    @AfterAll
    static void tearDown() {
        messageDataContextValidatorMock.close();
    }


    @Test
    void it_validate_parameters_for_pull_response_ok() throws CiseAdaptorValidationException {
        // TODO to be decided. The validation is in the  buildContext(). So the test should be done when this method is called. Here the validation call is disabled via mock: is this the correct way ?
        //Arrange
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(referencePullRequestMessage.getRecipient())
                .cisePayload(payload)
                .recipients(List.of(previousMessageService))
                .referenceMessage(referencePullRequestMessage)
                .buildContext();

        //Act & Assert
        assertDoesNotThrow(() -> pullResponseValidator.validate(messageDataContext));
    }


   @Test
    void it_fail_to_validate_parameters_for_pull_response_no_recipient_service() throws CiseAdaptorValidationException {
        //Arrange
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .cisePayload(payload)
                .buildContext();

        //Act & Assert
        assertThatThrownBy(() -> pullResponseValidator.validate(messageDataContext))
                .isInstanceOf(CiseAdaptorValidationException.class)
                .hasMessageContaining("Validation error message: Reference message is null");
    }

    @Test
    void it_fail_to_validate_parameters_for_pull_response_no_previous_pullRequest_present() throws CiseAdaptorValidationException {
        //Arrange

        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .cisePayload(payload)
                .recipients(List.of(previousMessageService))
                .buildContext();

        //Act & Assert
        assertThatThrownBy(() -> pullResponseValidator.validate(messageDataContext))
                .isInstanceOf(CiseAdaptorValidationException.class)
                .hasMessageContaining("Validation error message: Reference message is null");
    }

    //

    @Test
    void it_fail_to_validate_parameters_for_pull_response_due_to_difference_in_mandatory_current_service_fields_against_previous_pullRequest() throws CiseAdaptorValidationException {
        //Arrange
        MessageDataContext messageDataContext = MessageDataContext.getManager()
                .currentService(currentService)
                .cisePayload(payload)
                .recipients(List.of(previousMessageService))
                .referenceMessage(referencePullRequestMessage)
                .buildContext();

        //Act & Assert
        assertThatThrownBy(() -> pullResponseValidator.validate(messageDataContext))
                .isInstanceOf(CiseAdaptorValidationException.class)
                .hasMessageContaining("Validation error message: CurrentService and previous pullRequest.getRecipient() mandatory fields differ");
    }

}