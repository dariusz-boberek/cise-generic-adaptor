package eu.cise.adaptor.core.servicehandler.domain.message.validation;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.common.logging.LogConfig;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;

public class FeedbackValidator implements MessageValidator {

    protected static final AdaptorLogger log = LogConfig.configureLogging(FeedbackValidator.class);
    @Override
    public void validate(MessageDataContext messageDataContext) throws CiseAdaptorValidationException {
        // TODO
        throw new CiseAdaptorValidationException("Validation error message: Feedback validation id not supported");
    }
}
