package eu.cise.adaptor.core.common.message;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Feedback;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PullRequest;
import eu.cise.servicemodel.v1.message.PullResponse;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import org.apache.commons.collections.CollectionUtils;

/**
 * The MessageTypeEnum contains all possible values relating to the type of a CISE Message in order to make the Logging and the business logic handling
 * of the corresponding CISE Message more straightforward
 */
public enum MessageTypeEnum {
    PUSH("PUSH"),
    PUSH_UNKNOWN("PUSH_UNKNOWN"),
    SUBSCRIBE_PROVIDER("SUBSCRIBE_PROVIDER"),
    PULL_RESPONSE("PULL_RESPONSE"),
    PULL_REQUEST("PULL_REQUEST"),
    PULL_UNKNOWN("PULL_UNKNOWN"),
    SUBSCRIBE("SUBSCRIBE"),
    SUBSCRIBE_UNKNOWN("SUBSCRIBE_UNKNOWN"),
    DISCOVER("DISCOVER"),
    UNSUBSCRIBE("UNSUBSCRIBE"),
    GET_SUBSCRIBERS("GET_SUBSCRIBERS"),
    FEEDBACK("FEEDBACK"),
    SYNC_ACK("SYNC_ACK"),
    ASYNC_ACK("ASYNC_ACK"),
    UNDEFINED("UNDEFINED");

    private final String logName;

    MessageTypeEnum(String logName) {
        this.logName = logName;
    }

    /**
     * Helper method that given a CISE Message it returns the corresponding MessageTypeEnum value
     * @param message The CISE Message to identify
     * @return The identified MessageTypeEnum value
     */
    public static MessageTypeEnum messageTypeOf(Message message) {
        if (message instanceof PullRequest) {
            return mapPullRequest((PullRequest) message);
        }
        if (message instanceof PullResponse) {
            return PULL_RESPONSE;
        }
        if (message instanceof Push) {
            return mapPush((Push) message);
        }
        if (message instanceof Feedback) {
            return FEEDBACK;
        }
        if (message instanceof Acknowledgement) {
            if (message.getAny() == null || message.getAny().getElementsByTagName("Signature") == null) {
                return SYNC_ACK;
            } else {
                return ASYNC_ACK;
            }
        }
        throw new IllegalArgumentException("Message class is unknown: [" + message.getClass().getCanonicalName() + "]. ");
    }

    private static MessageTypeEnum mapPullRequest(PullRequest pullRequest) {
        switch (pullRequest.getPullType()) {
            case SUBSCRIBE:
                return CollectionUtils.isEmpty(pullRequest.getDiscoveryProfiles()) ? SUBSCRIBE : SUBSCRIBE_UNKNOWN;
            case DISCOVER:
                return DISCOVER;
            case UNSUBSCRIBE:
                return UNSUBSCRIBE;
            case GET_SUBSCRIBERS:
                return GET_SUBSCRIBERS;
            default:
                return CollectionUtils.isEmpty(pullRequest.getDiscoveryProfiles()) ? PULL_REQUEST : PULL_UNKNOWN;
        }
    }

    private static MessageTypeEnum mapPush(Push pushMessage) {
        if (pushMessage.getSender().getServiceOperation() == ServiceOperationType.SUBSCRIBE) {
            return SUBSCRIBE_PROVIDER;
        } else {
            return CollectionUtils.isEmpty(pushMessage.getDiscoveryProfiles()) ?
                    PUSH :
                    PUSH_UNKNOWN;
        }
    }

    /**
     * Returns the String that corresponds to the log value of the current Enum value
     * @return The String to use in the logs when the MessageTypeEnum is logged
     */
    public String getLogName() {
        return logName;
    }
}


