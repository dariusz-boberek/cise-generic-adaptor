package eu.cise.adaptor.core.servicehandler.domain.message.pattern;

import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.servicemodel.v1.message.CoreEntityPayload;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.servicemodel.v1.service.Service;
import eu.eucise.helpers.PushBuilder;

import java.util.ArrayList;
import java.util.List;

import static eu.cise.adaptor.core.common.utils.MessageBuildingServiceUtils.createServiceFrom;
import static eu.cise.adaptor.core.servicehandler.domain.message.utils.MessageBuildingCommonUtils.addCommon;
import static eu.cise.adaptor.core.servicehandler.domain.message.utils.MessageBuildingCommonUtils.addDiscoveryProfiles;
import static eu.cise.adaptor.core.servicehandler.domain.message.utils.MessageBuildingCommonUtils.addReliability;
import static eu.eucise.helpers.PushBuilder.newPush;

/**
 * Helper class for Push Know CISE Messages
 * It provides methods to build standard Push Know Message from custom data or from other sources (like other messages)
 */
public class PushHelper {

    private PushHelper() {
    }


    /**
     * The function creates a push message, either with known recipient services or based on discovery profiles.
     * If discovery profiles are present, it will create a push message using these profiles.
     * Otherwise, it will create a push message for each known recipient service.
     *
     * @param msgCtx MessageDataContext instance with all the information to build the message
     * @return List of Push messages.
     */
    public static List<Message> generateListOfPushMessage(MessageDataContext msgCtx) {

        List<Message> pushMessageList = new ArrayList<>();
        Service currentService = msgCtx.getCurrentService();
        CoreEntityPayload cisePayload = msgCtx.getCisePayload();

        // because of the MessageDataContextManager validation we know that there exist either recipients or discovery profile - not both - not none

        if (!msgCtx.getDiscoveryProfiles().isEmpty()){
            PushBuilder pushBuilder = newPush()
                    .sender(createServiceFrom(currentService));

            addCommon(msgCtx, pushBuilder);
            addReliability(msgCtx, pushBuilder);
            addDiscoveryProfiles(msgCtx, pushBuilder);

            Push push = pushBuilder.build();
            push.setPayload(cisePayload);
            pushMessageList.add(push);

        }
        else { // since there are no discovery profiles we should use the recipients

            for (Service recipientService : msgCtx.getRecipients()) {

                PushBuilder pushBuilder = newPush()
                        .sender(createServiceFrom(currentService))
                        .recipient(recipientService);

                addCommon(msgCtx, pushBuilder);
                addReliability(msgCtx, pushBuilder);

                Push push = pushBuilder.build();
                push.setPayload(cisePayload);
                pushMessageList.add(push);
            }
        }
        return pushMessageList;
    }
}