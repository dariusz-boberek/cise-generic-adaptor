package eu.cise.adaptor.core.servicehandler.domain.message.validation;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.common.logging.LogConfig;
import eu.cise.adaptor.core.common.logging.MessageAttributeKey;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PullRequest;
import eu.cise.servicemodel.v1.service.Service;

import static eu.cise.adaptor.core.common.logging.LoggerMessage.of;
import static eu.cise.adaptor.core.common.logging.MessageRouteValue.LS_TO_CISE;
import static eu.cise.adaptor.core.common.message.MessageTypeEnum.UNDEFINED;
import static eu.cise.adaptor.core.common.utils.MessageBuildingServiceUtils.areServicesSimilar;

public class PullResponseValidator extends CommonMessageValidator  {

    private static final AdaptorLogger log = LogConfig.configureLogging(PullResponseValidator.class);

    @Override
    public void validate(MessageDataContext msgCtx) throws CiseAdaptorValidationException {
        super.validate(msgCtx);
        verifyPayload(msgCtx);
        verifySameService(msgCtx);
        //TODO: add check that we dont have recipients present
        verifyMandatoryParameters(msgCtx);
    }

    private void verifyMandatoryParameters(MessageDataContext msgCtx) throws CiseAdaptorValidationException {
        verifyPreviousPullRequestMessagePresent(msgCtx);

        if (msgCtx.getPullResponseResultCode() == null) {
            String errorMsg = "Validation error message: pullResponseResultCode not set on an instance of PullRequest";
            log.error(of(errorMsg).addRoutingAttribute(LS_TO_CISE));
            throw new CiseAdaptorValidationException(errorMsg);
        }
    }

    private void verifyPreviousPullRequestMessagePresent(MessageDataContext msgCtx) throws CiseAdaptorValidationException {
        Message referenceMessage = msgCtx.getReferenceMessage();
        if (!(referenceMessage instanceof PullRequest)) {
            String errorMsg = "Validation error message: Reference message is not an instance of PullRequest";
            log.error(of(errorMsg).addRoutingAttribute(LS_TO_CISE));
            throw new CiseAdaptorValidationException(errorMsg);
        }
    }

    private void verifySameService(MessageDataContext msgCtx) throws CiseAdaptorValidationException {
        Service currentService = msgCtx.getCurrentService();

        if (msgCtx.getReferenceMessage() == null) {
            String errorMsg = "Validation error message: Reference message is null";
            log.error(of(UNDEFINED, null, errorMsg)
                    .addRoutingAttribute(LS_TO_CISE));
            throw new CiseAdaptorValidationException(errorMsg);
        }

        Service previousMessageRecipientService =  msgCtx.getReferenceMessage().getRecipient();
        String referenceMessageId = msgCtx.getReferenceMessage().getMessageID();

        if (!areServicesSimilar(currentService, previousMessageRecipientService)) {
            String errorMsg = "Validation error message: CurrentService and previous pullRequest.getRecipient() mandatory fields differ,  currentService: "+currentService+", previousMessageRecipientService: "+previousMessageRecipientService;
            log.error(of(UNDEFINED, null, errorMsg)
                    .addRoutingAttribute(LS_TO_CISE).addAttribute(MessageAttributeKey.REFERENCE_MESSAGE_ID, referenceMessageId));
            throw new CiseAdaptorValidationException(errorMsg);
        }
    }

}
