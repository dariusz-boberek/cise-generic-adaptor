package eu.cise.adaptor.core.common.logging;

/**
 * This enum is used inside the Logger Message to hold the type of information referring to the CISE Message
 */
public enum MessageAttributeKey {

    /**
     * The CISE Message Type. Values of this key are of type {@link eu.cise.adaptor.core.common.message.MessageTypeEnum}
     */
    MESSAGE_TYPE,
    /**
     * The Message ID of the CISE Message
     */
    MESSAGE_ID,
    /**
     * The Comment of the Logger Message
     */
    COMMENT,
    /**
     * The Correlation ID of the CISE Message
     */
    CORRELATION_ID,
    /**
     * The reference message ID (previous communication Message ID)
     */
    REFERENCE_MESSAGE_ID,

    /**
     * The Context ID of the CISE Message
     */
    CONTEXT_ID;
}
