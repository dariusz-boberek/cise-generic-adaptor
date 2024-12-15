package eu.cise.adaptor.core.common.logging;

/**
 * This enum conveys information about the routing status of the CISE Message processing
 */
public enum MessageRouteValue {
    /**
     * Routing value referring a message originating from the Legacy System targeting the CISE Adaptor / Node
     */
    LS_TO_CISE,
    /**
     * Routing value referring a message originating from the CISE Adaptor/ Node targeting the Legacy System
     */
    CISE_TO_LS,
    /**
     * Routing value depicting that the current LoggerMessage does not refer to a CISE Message routing status but to a general Adaptor context
     */
    CONTEXT;
}
