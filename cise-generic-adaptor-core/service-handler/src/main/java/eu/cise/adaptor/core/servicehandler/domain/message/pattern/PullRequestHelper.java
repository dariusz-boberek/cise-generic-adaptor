package eu.cise.adaptor.core.servicehandler.domain.message.pattern;

import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PullRequest;
import eu.cise.servicemodel.v1.message.PullType;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceProfile;
import eu.eucise.helpers.PullRequestBuilder;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static eu.cise.adaptor.core.common.utils.MessageBuildingServiceUtils.createServiceFrom;
import static eu.cise.adaptor.core.servicehandler.domain.message.utils.MessageBuildingCommonUtils.addCommon;
import static eu.cise.adaptor.core.servicehandler.domain.message.utils.MessageBuildingCommonUtils.addReliability;
import static eu.eucise.helpers.PullRequestBuilder.newPullRequest;

/**
 * Helper class for Pull Response CISE Messages
 * It provides methods to build standard Pull Request Message from custom data or from other sources (like other messages)
 */
public class PullRequestHelper {

    private PullRequestHelper() {
    }

    /**
     * The function creates PullRequest objects with the given information in the MessageDataContext.
     * If discovery profiles are present, it will create a Pull Request using these profiles,
     * otherwise, it will return as many messages in a list as the provided recipients
     *
     * @param msgCtx MessageDataContext instance with all the information to build the message
     * @return The method is returning a List of PullRequest objects.
     */
    public static List<Message> generateListOfPullRequestMessage(MessageDataContext msgCtx) {
        Message referenceMessage = msgCtx.getReferenceMessage();

        List<Message> messageList = new ArrayList<>();
        if (referenceMessage != null) {
            // todo: Build a PullRequest using as recipient the sender service of the previous message (but it could be wrong)
            messageList.add(buildPullRequest(msgCtx, createServiceFrom(referenceMessage.getSender()), null));
        }
        else if (CollectionUtils.isNotEmpty(msgCtx.getDiscoveryProfiles())) {
            // Build a PullRequest Unknown using the discovery profile provided by the user
            messageList.add(buildPullRequest(msgCtx, null, msgCtx.getDiscoveryProfiles()));
        }
        else {
            // Build a multiple PullRequest using the recipients provided by the user
            for (Service recipientService : msgCtx.getRecipients()) {
                messageList.add(buildPullRequest(msgCtx, recipientService, null));
            }
        }

        return messageList;
    }


    private static PullRequest buildPullRequest(MessageDataContext msgCtx, Service recipient, List<ServiceProfile> discoveryProfiles) {

        PullRequestBuilder pullRequestBuilder = newPullRequest();
        pullRequestBuilder
                .sender(createServiceFrom(msgCtx.getCurrentService()))
                .responseTimeout(msgCtx.getPullRequestResponseTimeOut())
                .pullType(PullType.REQUEST);

        // Known (recipient) or Unknown (discovery profiles)
        if (recipient != null) {
            pullRequestBuilder.recipient(recipient);
        } else {
            pullRequestBuilder.addProfiles(discoveryProfiles);
        }

        // Commons and reliability
        addCommon(msgCtx, pullRequestBuilder);
        addReliability(msgCtx, pullRequestBuilder);

        // Payload,Payload selectors,Requests
        PullRequest pullRequest = pullRequestBuilder.build();
        pullRequest.setPayload(msgCtx.getCisePayload());
        pullRequest.setPayloadSelector(msgCtx.getPullRequestPayloadSelector());
        pullRequest.setRequests(msgCtx.getPullRequestServiceCapabilityRequests());

        return pullRequest;
    }

}