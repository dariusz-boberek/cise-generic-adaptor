package eu.cise.adaptor.core.servicehandler.domain.message.validation;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.common.logging.LogConfig;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;

public class PushValidator extends CommonMessageValidator {

    protected static final AdaptorLogger log = LogConfig.configureLogging(PushValidator.class);

    @Override
    public void validate(MessageDataContext msgCtx) throws CiseAdaptorValidationException {
        super.validate(msgCtx);
        /** Generic checks **/
        verifyPayload(msgCtx);

        verifyDestinationIsPresent(msgCtx);
    }
}
