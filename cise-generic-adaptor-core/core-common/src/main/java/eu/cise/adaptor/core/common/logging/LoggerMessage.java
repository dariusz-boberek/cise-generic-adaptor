package eu.cise.adaptor.core.common.logging;

import eu.cise.adaptor.core.common.message.MessageTypeEnum;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Main class that represents a Log Message to be processed by the {@link AdaptorLogger}.
 * This class combines the {@link MessageAttributeKey} with its appropriate values from the method call,
 * the {@link MessageTypeEnum} is used as a value for the {@code #MESSAGE_TYPE} attribute key
 * and also keeps a {@link MessageRouteValue} in order to show the routing path of the messages
 */
public class LoggerMessage {

    private final Map<MessageAttributeKey, Object> messageAttributes = new EnumMap<>(MessageAttributeKey.class);
    private final Object[] extraLogVariables;

    private MessageRouteValue messageRouteValue;

    /**
     * Basic constructor of the LoggerMessage
     * @param messageTypeEnum The type of the CISE message this log refers to
     * @param messageId The CISE Message ID
     * @param comment The comment to be logged
     * @param extraLogVariables Extra variables that substitute {} placeholders inside the comment
     */
    public LoggerMessage(MessageTypeEnum messageTypeEnum, String messageId, String comment, Object... extraLogVariables) {
        messageAttributes.put(MessageAttributeKey.MESSAGE_TYPE, messageTypeEnum.getLogName());
        messageAttributes.put(MessageAttributeKey.MESSAGE_ID, messageId);
        messageAttributes.put(MessageAttributeKey.COMMENT, comment);
        this.extraLogVariables = extraLogVariables;
    }

    /**
     * LoggerMessage constructor that does not refer to a CISE Message but can be used generically
     * @param comment The comment to be logged
     * @param extraLogVariables Extra variables that substitute {} placeholders inside the comment
     */
    public LoggerMessage(String comment,Object... extraLogVariables) {
        messageAttributes.put(MessageAttributeKey.COMMENT, comment);
        this.extraLogVariables = extraLogVariables;
    }

    /**
     * Helper method to create a LoggerMessage
     * @param messageTypeEnum The MessageType of the CISE Message this log refers to
     * @param messageId The message ID of the CISE Message
     * @param comment The comment to be logged
     * @param extraLogVariables Extra variables that substitute {} placeholders inside the comment
     * @return The resulting LoggerMessage
     */
    public static LoggerMessage of(MessageTypeEnum messageTypeEnum, String messageId, String comment, Object... extraLogVariables) {
        return new LoggerMessage(messageTypeEnum, messageId, comment, extraLogVariables);
    }

    /**
     * Helper method to create a LoggerMessage
     * @param comment The comment to be logged
     * @param extraLogVariables Extra variables that substitute {} placeholders inside the comment
     * @return The resulting LoggerMessage
     */
    public static LoggerMessage of(String comment, Object... extraLogVariables) {
        return new LoggerMessage(comment, extraLogVariables);
    }

    /**
     * This method can be used to add {@code<key,value> pairs of type <{@link MessageAttributeKey} , Object>}. These attributes are
     * logged along with the Logger Message
     * @param key The key conveying the information related to the provided value
     * @param value The value to log
     * @return The resulting LoggerMessage
     */
    public LoggerMessage addAttribute(MessageAttributeKey key, Object value) {
        if (value != null) {
            messageAttributes.put(key, value);
        }
        return this;
    }

    /**
     * This method can be used to add the {@link MessageRouteValue} explaining the routing stage of the logger message
     * @param messageRouteValue The value depicting the Routing stage
     * @return The resulting LoggerMessage
     */
    public LoggerMessage addRoutingAttribute(MessageRouteValue messageRouteValue) {
        if (messageRouteValue != null) {
            this.messageRouteValue = messageRouteValue;
        }
        return this;
    }

    /**
     * Method used to get the extra log variables. These variables substitute any {} placeholders inside the comment
     * @return The list of extraLogVariables added to this LoggerMessage
     */
    public Object[] getExtraLogVariables(){
        return extraLogVariables;
    }

    /**
     * This method returns a Map of all message attributes and the message route value if any has been added.
     * @return The resulting HashMap
     */
    public Map<Object, Object> getAttributes() {
        Map<Object, Object> result = new HashMap<>();
        result.putAll(messageAttributes);
        if(messageRouteValue != null) {
            result.put("ROUTE_KEY", messageRouteValue);
        }
        return Collections.unmodifiableMap(result);
    }

}
