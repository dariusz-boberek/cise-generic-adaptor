package eu.cise.adaptor.core.servicehandler.domain.message.validation;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceRoleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MessageDataContextValidatorTest {

    @Test
    void it_fail_to_validate_when_no_sender_service() {
        // Arrange
        MessageDataContext messageDataContext = mock(MessageDataContext.class);
        when(messageDataContext.getCurrentService()).thenReturn(null);

        // Act & Assert
        assertThatThrownBy(() -> MessageDataContextValidator.validateContext(messageDataContext))
                .isInstanceOf(CiseAdaptorValidationException.class)
                .hasMessageContaining("Validation error message: Mandatory currentService parameter is not valid");
    }

    @Test
    void it_returns_pull_request_validator() throws CiseAdaptorValidationException {
        Service currentService = new Service();
        currentService.setServiceRole(ServiceRoleType.CONSUMER);
        currentService.setServiceOperation(ServiceOperationType.PULL);

        MessageValidator validator = MessageDataContextValidator.getMessageValidator(currentService);
        assertInstanceOf(PullRequestValidator.class, validator);
    }

    @Test
    void it_returns_pull_response_validator() throws CiseAdaptorValidationException {
        Service currentService = new Service();
        currentService.setServiceRole(ServiceRoleType.PROVIDER);
        currentService.setServiceOperation(ServiceOperationType.PULL);

        MessageValidator validator = MessageDataContextValidator.getMessageValidator(currentService);
        assertInstanceOf(PullResponseValidator.class, validator);
    }

    @Test
    void it_returns_push_known_validator() throws CiseAdaptorValidationException {
        Service currentService = new Service();
        currentService.setServiceRole(ServiceRoleType.PROVIDER);
        currentService.setServiceOperation(ServiceOperationType.PUSH);

        MessageValidator validator = MessageDataContextValidator.getMessageValidator(currentService);
        assertInstanceOf(PushValidator.class, validator);
    }
    @Test
    void it_returns_subscribe_consumer_validator() throws CiseAdaptorValidationException {
        Service currentService = new Service();
        currentService.setServiceRole(ServiceRoleType.CONSUMER);
        currentService.setServiceOperation(ServiceOperationType.SUBSCRIBE);

        MessageValidator validator = MessageDataContextValidator.getMessageValidator(currentService);
        assertInstanceOf(SubscribeConsumerValidator.class, validator);
    }
    @Test
    void it_returns_subscribe_provider_validator() throws CiseAdaptorValidationException {
        Service currentService = new Service();
        currentService.setServiceRole(ServiceRoleType.PROVIDER);
        currentService.setServiceOperation(ServiceOperationType.SUBSCRIBE);

        MessageValidator validator = MessageDataContextValidator.getMessageValidator(currentService);
        assertInstanceOf(SubscribeProviderValidator.class, validator);
    }
    @Test
    void it_returns_feedback_validator() throws CiseAdaptorValidationException {
        Service currentService = new Service();
        currentService.setServiceRole(ServiceRoleType.PROVIDER);
        currentService.setServiceOperation(ServiceOperationType.FEEDBACK);

        MessageValidator validator = MessageDataContextValidator.getMessageValidator(currentService);
        assertInstanceOf(FeedbackValidator.class, validator);
    }

}