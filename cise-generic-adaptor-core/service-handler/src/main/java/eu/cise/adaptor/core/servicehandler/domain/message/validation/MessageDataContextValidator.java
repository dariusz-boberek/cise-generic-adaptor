package eu.cise.adaptor.core.servicehandler.domain.message.validation;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageUseCase;
import eu.cise.servicemodel.v1.service.Service;

public class MessageDataContextValidator {

    private static final PullRequestValidator pullRequestValidator = new PullRequestValidator();
    private static final PullResponseValidator pullResponseValidator = new PullResponseValidator();
    private static final PushValidator pushValidator = new PushValidator();
    private static final SubscribeConsumerValidator subscribeConsumerValidator = new SubscribeConsumerValidator();
    private static final SubscribeProviderValidator subscribeProviderValidator = new SubscribeProviderValidator();
    private static final FeedbackValidator feedbackValidator = new FeedbackValidator();


    public static void validateContext(MessageDataContext messageDataContext) throws CiseAdaptorValidationException {
        MessageValidator messageValidator = getMessageValidator(messageDataContext.getCurrentService());
        messageValidator.validate(messageDataContext);
    }

    static MessageValidator getMessageValidator(Service currentService) throws CiseAdaptorValidationException {

        if (currentService == null || currentService.getServiceRole() == null || currentService.getServiceOperation() == null) {
            throw new CiseAdaptorValidationException("Validation error message: Mandatory currentService parameter is not valid");
        }

        MessageUseCase messageUseCase = MessageUseCase.valueOf(currentService);
        switch(messageUseCase) {
            case PULL_RESPONSE:
                return pullResponseValidator;
            case PULL_REQUEST:
                return pullRequestValidator;
            case PUSH:
                return pushValidator;
            case PUSH_SUBSCRIBE_PROVIDER:
                return subscribeProviderValidator;
            case PULL_SUBSCRIBE_CONSUMER:
                return subscribeConsumerValidator;
            case FEEDBACK:
                return feedbackValidator;
            default:
                throw new CiseAdaptorValidationException("The message with sender Role " + currentService.getServiceRole() + " and operation " + currentService.getServiceOperation() + " is not supported");
        }
    }
}
