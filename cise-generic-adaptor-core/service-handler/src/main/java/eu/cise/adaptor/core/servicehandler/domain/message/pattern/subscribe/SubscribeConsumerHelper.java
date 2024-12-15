package eu.cise.adaptor.core.servicehandler.domain.message.pattern.subscribe;

import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.servicemodel.v1.message.CoreEntityPayload;
import eu.cise.servicemodel.v1.message.InformationSecurityLevelType;
import eu.cise.servicemodel.v1.message.InformationSensitivityType;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PullRequest;
import eu.cise.servicemodel.v1.message.PullType;
import eu.cise.servicemodel.v1.message.PurposeType;
import eu.cise.servicemodel.v1.service.Service;
import eu.eucise.helpers.PullRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static eu.cise.adaptor.core.common.utils.MessageBuildingServiceUtils.createServiceFrom;
import static eu.cise.adaptor.core.servicehandler.domain.message.utils.MessageBuildingCommonUtils.addCommon;
import static eu.cise.adaptor.core.servicehandler.domain.message.utils.MessageBuildingCommonUtils.addDiscoveryProfiles;
import static eu.cise.adaptor.core.servicehandler.domain.message.utils.MessageBuildingCommonUtils.addReliability;
import static eu.eucise.helpers.PullRequestBuilder.newPullRequest;

/**
 * Helper class for Pull Request Subscribe CISE Messages
 * It provides methods to build standard Pull Request Subscribe Message from custom data or from other sources (like other messages)
 */
public class SubscribeConsumerHelper {

    private SubscribeConsumerHelper() {
    }

    /**
     * The function creates a pull request for a subscribe consumer, setting various properties related to information security and sensitivity.
     * It will return the messages in a list, one message for each recipient in MessageDataContext or if Discovery Profiles are specified
     * it will return one Subscribe Unknown message with the given profiles
     *
     * @param msgCtx MessageDataContext instance with all the information to build the message
     * @return List of Subscribe consumer messages.
     */
    public static List<Message> generateListOfSubscribeConsumerMessage(MessageDataContext msgCtx) {

        List<Message> pullRequestList = new ArrayList<>();
        Service currentService = msgCtx.getCurrentService();

        // because of the MessageDataContextManager validation we know that there exist either recipients or discovery profile - not both - not none

        if (!msgCtx.getDiscoveryProfiles().isEmpty()) {
            PullRequestBuilder pullRequestBuilder = getPullRequestBuilder(msgCtx, currentService);
            // add the discovery profile
            addDiscoveryProfiles(msgCtx, pullRequestBuilder);
            PullRequest pullRequest = buildAndAddCommonPayload(pullRequestBuilder);
            pullRequestList.add(pullRequest);

        } else {
            for (Service recipientService : msgCtx.getRecipients()) {
                PullRequestBuilder pullRequestBuilder = getPullRequestBuilder(msgCtx, currentService);
                // add recipient
                pullRequestBuilder.recipient(createServiceFrom(recipientService));

                PullRequest pullRequest = buildAndAddCommonPayload(pullRequestBuilder);

                pullRequestList.add(pullRequest);
            }
        }
        return pullRequestList;
    }

    private static PullRequest buildAndAddCommonPayload(PullRequestBuilder pullRequestBuilder) {
        PullRequest pullRequest = pullRequestBuilder.build();
        CoreEntityPayload payload = pullRequest.getPayload();

        //TODO:  hardcoded payload data could be subject of customization
        payload.setInformationSecurityLevel(InformationSecurityLevelType.NON_CLASSIFIED);
        payload.setInformationSensitivity(InformationSensitivityType.GREEN);
        payload.setIsPersonalData(Boolean.FALSE);
        payload.setPurpose(PurposeType.NON_SPECIFIED);
        payload.setEnsureEncryption(Boolean.FALSE);
        return pullRequest;
    }

    private static PullRequestBuilder getPullRequestBuilder(MessageDataContext msgCtx, Service currentService) {
        PullRequestBuilder pullRequestBuilder = newPullRequest()
                .sender(createServiceFrom(currentService))
                .pullType(PullType.SUBSCRIBE);

        addCommon(msgCtx, pullRequestBuilder);
        addReliability(msgCtx, pullRequestBuilder);
        return pullRequestBuilder;
    }
}
