package eu.cise.adaptor.core.servicehandler.domain.message.pattern.subscribe;

import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.servicemodel.v1.message.CoreEntityPayload;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.service.Service;
import eu.eucise.helpers.PushBuilder;

import java.util.List;

import static eu.cise.adaptor.core.common.utils.MessageBuildingServiceUtils.createServiceFrom;
import static eu.cise.adaptor.core.servicehandler.domain.message.utils.MessageBuildingCommonUtils.addCommon;
import static eu.cise.adaptor.core.servicehandler.domain.message.utils.MessageBuildingCommonUtils.addReliability;
import static eu.eucise.helpers.PushBuilder.newPush;

/**
 * Helper class for Push CISE Messages,
 * It provides methods to build standard Push Message for Subscribe service, from custom data or from other sources (like other messages)
 */
public class SubscribeProviderHelper {

    private SubscribeProviderHelper() {}

    /**
     * The function creates a push message that is sent to all the subscribed services through the CISE Node.
     * It will return the message in a list, that will contain only one Subscribe provider message
     *
     * @param msgCtx MessageDataContext instance with all the information to build the message
     * @return List containing only one Subscribe provider message.
     */
    public static List<Message> generateListOfSubscribeProviderMessage(MessageDataContext msgCtx) {
        Service currentService = msgCtx.getCurrentService();
        CoreEntityPayload cisePayload =  msgCtx.getCisePayload();

        PushBuilder pushForSubscribeProviderBuilder = newPush()
                .sender(createServiceFrom(currentService));

        addCommon(msgCtx, pushForSubscribeProviderBuilder);
        addReliability(msgCtx, pushForSubscribeProviderBuilder);

        Message pushForSubscribeProvider = pushForSubscribeProviderBuilder.build();
        pushForSubscribeProvider.setPayload(cisePayload);

        return List.of(pushForSubscribeProvider);
    }
}
