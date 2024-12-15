package eu.cise.adaptor.core.servicehandler.domain.message.pattern;

import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.servicemodel.v1.message.CoreEntityPayload;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PullResponse;
import eu.cise.servicemodel.v1.service.Service;
import eu.eucise.helpers.PullResponseBuilder;

import java.util.List;

import static eu.cise.adaptor.core.common.utils.MessageBuildingServiceUtils.createServiceFrom;
import static eu.cise.adaptor.core.servicehandler.domain.message.utils.MessageBuildingCommonUtils.addCommon;
import static eu.cise.adaptor.core.servicehandler.domain.message.utils.MessageBuildingCommonUtils.addReliability;
import static eu.eucise.helpers.PullResponseBuilder.newPullResponse;

/**
 * Helper class for Pull Response CISE Messages
 * It provides methods to build standard Pull Response Message from custom data or from other sources (like other messages)
 */
public class PullResponseHelper {

    private PullResponseHelper() {
    }

    /**
     * The function creates a PullResponse object with the given PullRequest, currentService, and cisePayload.
     * It will return the message in a list, that will contain only one PullRequest based on the reference message
     *
     * @param msgCtx MessageDataContext instance with all the information to build the message
     * @return The method is returning a List of PullResponse objects. Usually with only one PullRequest as a reference message, the list will contain only one message
     */
    public static List<Message> generateListOfPullResponseMessage(MessageDataContext msgCtx) {
        Message pullRequest = msgCtx.getReferenceMessage();
        Service currentService = msgCtx.getCurrentService();
        CoreEntityPayload cisePayload = msgCtx.getCisePayload();

        PullResponseBuilder pullResponseBuilder = newPullResponse();

        pullResponseBuilder.correlationId(pullRequest.getCorrelationID())
                .sender(createServiceFrom(currentService))
                .recipient(createServiceFrom(pullRequest.getSender()))
                .resultCode(msgCtx.getPullResponseResultCode())
                .errorDetail(msgCtx.getPullResponseErrorDetail())
                .capabilities(msgCtx.getPullResponseFulfils());

        addCommon(msgCtx, pullResponseBuilder);
        addReliability(msgCtx, pullResponseBuilder);

        pullResponseBuilder.contextId(pullRequest.getContextID());

        PullResponse pullResponse = pullResponseBuilder.build();
        pullResponse.setPayload(cisePayload);

        return List.of(pullResponse);
    }

}