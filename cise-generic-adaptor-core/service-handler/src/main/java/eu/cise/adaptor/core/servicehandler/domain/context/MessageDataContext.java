package eu.cise.adaptor.core.servicehandler.domain.context;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage;
import eu.cise.adaptor.core.servicehandler.domain.message.validation.MessageDataContextValidator;
import eu.cise.servicemodel.v1.message.CoreEntityPayload;
import eu.cise.servicemodel.v1.message.FeedbackType;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PayloadSelector;
import eu.cise.servicemodel.v1.message.PriorityType;
import eu.cise.servicemodel.v1.message.ResponseCodeType;
import eu.cise.servicemodel.v1.message.RetryStrategyType;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceCapability;
import eu.cise.servicemodel.v1.service.ServiceProfile;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

/**
 * This class is used to give to the ServiceHandlerMessageBuilderService all the information that are needed to build a CISE Message and give it to the Generic Adaptor
 */
public class MessageDataContext {

    /**
     * General purpose properties
     **/

    private final String contextId;
    private final Service currentService;
    private final List<Service> recipients;
    private final List<ServiceProfile> discoveryProfiles;
    private final CoreEntityPayload cisePayload;
    private final PriorityType priority;
    private final Boolean requiresAck;
    private final RetryStrategyType retryStrategy;

    /**
     * PullRequest specific properties
     **/
    private final Integer pullRequestResponseTimeOut;
    private final PayloadSelector pullRequestPayloadSelector;
    private final ServiceCapability pullRequestServiceCapabilityRequests;

    /**
     * PullResponse specific properties
     **/
    private final String pullResponseErrorDetail;
    private final ResponseCodeType pullResponseResultCode;
    private final ServiceCapability pullResponseFulfils;

    /**
     * Feedback specific properties
     **/
    private final FeedbackType feedbackType;
    private final String feedbackReason;
    private final String feedbackRefMessageId;

    /**
     * Plugin specific data
     */
    private final String referenceMessageId;
    private final Message referenceMessage;
    private final List<RegisteredMessage> messageHistory;


    private MessageDataContext(
            // General purpose properties
            List<Service> recipients,
            Service currentService,
            List<ServiceProfile> discoveryProfiles,
            String contextId,
            CoreEntityPayload cisePayload,
            PriorityType priority,
            Boolean requiresAck,
            RetryStrategyType retryStrategy,
            // PullRequest specific properties
            Integer pullRequestResponseTimeOut,
            PayloadSelector pullRequestPayloadSelector,
            ServiceCapability pullRequestServiceCapabilityRequests,
            // PullResponse specific properties
            String pullResponseErrorDetail,
            ResponseCodeType pullResponseResultCode,
            ServiceCapability pullResponseFulfils,
            // Feedback specific properties
            FeedbackType feedbackType,
            String feedbackReason,
            String feedbackRefMessageId,
            //Plugin specific data
            String referenceMessageId,
            Message referenceMessage,
            List<RegisteredMessage> messageHistory
    ) {
        /** General purpose properties **/
        this.recipients = safeCopy(recipients);
        this.currentService = currentService;
        this.discoveryProfiles = safeCopy(discoveryProfiles);
        this.contextId = contextId;
        this.cisePayload = cisePayload;
        this.priority = priority;
        this.requiresAck = requiresAck;
        this.retryStrategy = retryStrategy;


        /** PullRequest specific properties **/
        this.pullRequestResponseTimeOut = pullRequestResponseTimeOut;
        this.pullRequestPayloadSelector = pullRequestPayloadSelector;
        this.pullRequestServiceCapabilityRequests = pullRequestServiceCapabilityRequests;

        /** PullResponse specific properties **/
        this.pullResponseErrorDetail = pullResponseErrorDetail;
        this.pullResponseResultCode = pullResponseResultCode;
        this.pullResponseFulfils = pullResponseFulfils;

        /** Feedback specific properties **/
        this.feedbackType = feedbackType;
        this.feedbackReason = feedbackReason;
        this.feedbackRefMessageId = feedbackRefMessageId;

        /** Specific plugin data **/
        this.referenceMessageId = referenceMessageId;
        this.referenceMessage = referenceMessage;
        this.messageHistory = messageHistory;
    }

    public List<Service> getRecipients() {
        return recipients;
    }

    public Service getCurrentService() {
        return currentService;
    }

    public List<ServiceProfile> getDiscoveryProfiles() {
        return discoveryProfiles;
    }

    public String getContextId() {
        return contextId;
    }

    public PriorityType getPriority() {
        return priority;
    }

    public Boolean getRequiresAck() {
        return requiresAck;
    }

    public RetryStrategyType getRetryStrategy() {
        return retryStrategy;
    }

    public Integer getPullRequestResponseTimeOut() {
        return pullRequestResponseTimeOut;
    }

    public PayloadSelector getPullRequestPayloadSelector() {
        return pullRequestPayloadSelector;
    }

    public ServiceCapability getPullRequestServiceCapabilityRequests() {
        return pullRequestServiceCapabilityRequests;
    }

    public String getPullResponseErrorDetail() {
        return pullResponseErrorDetail;
    }

    public ResponseCodeType getPullResponseResultCode() {
        return pullResponseResultCode;
    }

    public ServiceCapability getPullResponseFulfils() {
        return pullResponseFulfils;
    }

    public FeedbackType getFeedbackType() {
        return feedbackType;
    }

    public String getFeedbackReason() {
        return feedbackReason;
    }

    public String getFeedbackRefMessageId() {
        return feedbackRefMessageId;
    }

    public CoreEntityPayload getCisePayload() {
        return cisePayload;
    }

    public String getReferenceMessageId() {
        return referenceMessageId;
    }

    public Message getReferenceMessage() {
        return referenceMessage;
    }

    public List<RegisteredMessage> getMessageHistory() {
        return messageHistory;
    }

    private static <T> List<T> safeCopy(List<T> list) {
        return ofNullable(list).map(List::copyOf).orElseGet(ArrayList::new);
    }


    public static MessageDataContextManager getManager() {
        return new MessageDataContextManager();
    }

    public static class MessageDataContextManager {
        private List<Service> recipients = new ArrayList<>();
        private Service currentService;
        private List<ServiceProfile> discoveryProfiles = new ArrayList<>();
        private String contextId;
        private PriorityType priority;
        private Boolean requiresAck;
        private RetryStrategyType retryStrategy;
        private CoreEntityPayload cisePayload;

        /**
         * PullRequest specific properties
         **/
        private static final Integer DEFAULT_RESPONSE_TIMEOUT = 1000;

        private Integer pullRequestResponseTimeOut;
        private PayloadSelector pullRequestPayloadSelector;
        private ServiceCapability pullRequestRequests;

        /**
         * PullResponse specific properties
         **/
        private String pullResponseErrorDetail;
        private ResponseCodeType pullResponseResultCode;
        private ServiceCapability pullResponseFulfils;

        /**
         * Feedback specific properties
         **/
        private FeedbackType feedbackType;
        private String feedbackReason;
        private String feedbackRefMessageId;


        private String referenceMessageId;
        private Message referenceMessage;
        private List<RegisteredMessage> messageHistory;

        private MessageDataContextManager() {
            // Default values
            this.priority = PriorityType.HIGH;
            this.requiresAck = Boolean.FALSE;
            this.retryStrategy = RetryStrategyType.LOW_RELIABILITY;
            this.pullResponseResultCode = ResponseCodeType.SUCCESS;
            this.pullRequestResponseTimeOut = DEFAULT_RESPONSE_TIMEOUT;
        }

        /**
         * Call this method named contextId to set the contextId of the CISE Message that the Generic Adaptor will generate.
         *
         * @param recipients List of recipients
         * @return This builder instance
         */
        public MessageDataContextManager recipients(List<Service> recipients) {
            this.recipients = safeCopy(recipients);
            return this;
        }

        /**
         * Call this method named currentService to set the plugin configured Service
         * to be used as Sender service by the CISE Message that the Generic Adaptor will generate.
         *
         * @param currentService The plugin configured Service
         * @return This builder instance
         */
        public MessageDataContextManager currentService(Service currentService) {
            this.currentService = currentService;
            return this;
        }

        /**
         * Call this method named discoveryProfiles to set the discoveryProfiles of the CISE Message that the Generic Adaptor will generate.
         *
         * @param discoveryProfiles The lists of discoveryProfiles
         * @return This builder instance
         */
        public MessageDataContextManager discoveryProfiles(List<ServiceProfile> discoveryProfiles) {
            this.discoveryProfiles = safeCopy(discoveryProfiles);
            return this;
        }

        /**
         * Call this method named contextId to set the contextId of the CISE Message that the Generic Adaptor will generate.
         *
         * @param contextId The contextId
         * @return This builder instance
         */
        public MessageDataContextManager contextId(String contextId) {
            this.contextId = contextId;
            return this;
        }

        /**
         * Call this method named priority to set the priority of the CISE Message that the Generic Adaptor will generate.
         *
         * @param priority The priority type enum
         * @return This builder instance
         */
        public MessageDataContextManager priority(PriorityType priority) {
            this.priority = priority;
            return this;
        }

        /**
         * Call this method named requiresAck to set the ack requirement  of the CISE Message that the Generic Adaptor will generate.
         *
         * @param requiresAck True if an asynchronous ack is required when the message is received
         * @return This builder instance
         */
        public MessageDataContextManager requiresAck(Boolean requiresAck) {
            this.requiresAck = requiresAck;
            return this;
        }

        /**
         * Call this method named retryStrategy to set the retryStrategy of the CISE Message that the Generic Adaptor will generate.
         *
         * @param retryStrategy The retry strategy enum
         * @return This builder instance
         */
        public MessageDataContextManager retryStrategy(RetryStrategyType retryStrategy) {
            this.retryStrategy = retryStrategy;
            return this;
        }

        /**
         * Call this method named pullRequestResponseTimeOut to set the pullRequestResponseTimeOut of the PullRequest CISE Message that the Generic Adaptor will generate.
         *
         * @param pullRequestResponseTimeOut The pullRequestResponseTimeOut
         * @return This builder instance
         */
        public MessageDataContextManager pullRequestResponseTimeOut(Integer pullRequestResponseTimeOut) {
            this.pullRequestResponseTimeOut = pullRequestResponseTimeOut;
            return this;
        }

        /**
         * Call this method named pullRequestPayloadSelector to set the pullRequestPayloadSelector of the PullRequest CISE Message that the Generic Adaptor will generate.
         * Supply a Selectors array refereed to the Payload data specified in the PullRequest. Refer to <a href="https://emsa.europa.eu/cise-documentation/The-PayloadSelector-for-query-by-example_891637594.html">PayloadSelector</a>
         *
         * @param pullRequestPayloadSelector The pullRequestPayloadSelector
         * @return This builder instance
         */
        public MessageDataContextManager pullRequestPayloadSelector(PayloadSelector pullRequestPayloadSelector) {
            this.pullRequestPayloadSelector = pullRequestPayloadSelector;
            return this;
        }

        /**
         * Call this method named pullRequestRequests to set the pullRequestRequests of the PullRequest CISE Message that the Generic Adaptor will generate.
         *
         * @param pullRequestRequests The pullRequestRequests
         * @return This builder instance
         */
        public MessageDataContextManager pullRequestRequests(ServiceCapability pullRequestRequests) {
            this.pullRequestRequests = pullRequestRequests;
            return this;
        }

        /**
         * Call this method named pullResponseErrorDetail to set the pullResponseErrorDetail of the PullResponse CISE Message that the Generic Adaptor will generate.
         *
         * @param pullResponseErrorDetail The pullResponseErrorDetail
         * @return This builder instance
         */
        public MessageDataContextManager pullResponseErrorDetail(String pullResponseErrorDetail) {
            this.pullResponseErrorDetail = pullResponseErrorDetail;
            return this;
        }

        /**
         * Call this method named pullResponseResultCode to set the pullResponseResultCode of the PullResponse CISE Message that the Generic Adaptor will generate.
         *
         * @param pullResponseResultCode The pullResponseResultCode
         * @return This builder instance
         */
        public MessageDataContextManager pullResponseResultCode(ResponseCodeType pullResponseResultCode) {
            this.pullResponseResultCode = pullResponseResultCode;
            return this;
        }

        /**
         * Call this method named pullResponseFulfils to set the pullResponseFulfils of the PullResponse CISE Message that the Generic Adaptor will generate.
         *
         * @param pullResponseFulfils The pullResponseFulfils
         * @return This builder instance
         */
        public MessageDataContextManager pullResponseFulfils(ServiceCapability pullResponseFulfils) {
            this.pullResponseFulfils = pullResponseFulfils;
            return this;
        }

        /**
         * Call this method named feedbackType to set the feedbackType of the Feedback CISE Message that the Generic Adaptor will generate.
         *
         * @param feedbackType The feedbackType
         * @return This builder instance
         */
        public MessageDataContextManager feedbackType(FeedbackType feedbackType) {
            this.feedbackType = feedbackType;
            return this;
        }

        /**
         * Call this method named feedbackReason to set the feedbackReason of the Feedback CISE Message that the Generic Adaptor will generate.
         *
         * @param feedbackReason The feedbackReason
         * @return This builder instance
         */
        public MessageDataContextManager feedbackReason(String feedbackReason) {
            this.feedbackReason = feedbackReason;
            return this;
        }

        /**
         * Call this method named feedbackRefMessageId to set the feedbackRefMessageId of the Feedback CISE Message that the Generic Adaptor will generate.
         *
         * @param feedbackRefMessageId The feedbackRefMessageId
         * @return This builder instance
         */
        public MessageDataContextManager feedbackRefMessageId(String feedbackRefMessageId) {
            this.feedbackRefMessageId = feedbackRefMessageId;
            return this;
        }

        /**
         * Call this method named cisePayload to set the cisePayload of the CISE Message that the Generic Adaptor will generate.
         *
         * @param cisePayload The cisePayload
         * @return This builder instance
         */
        public MessageDataContextManager cisePayload(CoreEntityPayload cisePayload) {
            this.cisePayload = cisePayload;
            return this;
        }


        /**
         * Call this method named referenceMessageId to set the messageId of the previous CISE Message received.
         *
         * @param referenceMessageId The referenceMessageId
         * @return This builder instance
         */
        public MessageDataContextManager referenceMessageId(String referenceMessageId) {
            this.referenceMessageId = referenceMessageId;
            return this;
        }

        /**
         * Call this method named referenceMessage to set the previous CISE Message received.
         *
         * @param referenceMessage The referenceMessage
         * @return This builder instance
         */
        public MessageDataContextManager referenceMessage(Message referenceMessage) {
            this.referenceMessage = referenceMessage;
            return this;
        }

        /**
         * Call this method named messageHistory to set the list of the previous CISE Message received,
         * related to the CISE Message that the Generic Adaptor will generate.
         *
         * @param messageHistory The messageHistory
         * @return This builder instance
         */
        public MessageDataContextManager messageHistory(List<RegisteredMessage> messageHistory) {
            this.messageHistory = messageHistory;
            return this;
        }

        /**
         * Call this method named getRecipients to retrieve the value previously set in recipients method.
         *
         * @return List of recipient services
         */
        public List<Service> getRecipients() {
            return recipients;
        }

        /**
         * Call this method named getCurrentService to retrieve the value previously set in currentService method.
         *
         * @return The plugin configured service
         */
        public Service getCurrentService() {
            return currentService;
        }

        /**
         * Call this method named getDiscoveryProfiles to retrieve the value previously set in discoveryProfiles method.
         *
         * @return The list of ServiceProfile
         */
        public List<ServiceProfile> getDiscoveryProfiles() {
            return discoveryProfiles;
        }

        /**
         * Call this method named getContextId to retrieve the value previously set in contextId method.
         *
         * @return The contextId
         */
        public String getContextId() {
            return contextId;
        }

        /**
         * Call this method named getPriority to retrieve the value previously set in priority method.
         *
         * @return The priority type
         */
        public PriorityType getPriority() {
            return priority;
        }

        /**
         * Call this method named getRequiresAck to retrieve the value previously set in equiresAck method.
         *
         * @return True if the message requires an Ack
         */
        public Boolean getRequiresAck() {
            return requiresAck;
        }

        /**
         * Call this method named getRetryStrategy to retrieve the value previously set in retryStrategy method.
         *
         * @return The retry strategy
         */
        public RetryStrategyType getRetryStrategy() {
            return retryStrategy;
        }

        /**
         * Call this method named getCisePayload to retrieve the value previously set in cisePayload method.
         *
         * @return The CoreEntityPayload
         */
        public CoreEntityPayload getCisePayload() {
            return cisePayload;
        }

        /**
         * Call this method named getPullRequestResponseTimeOut to retrieve the value previously set in pullRequestPullRequestResponseTimeOut method.
         *
         * @return The PullRequestResponseTimeOut
         */
        public Integer getPullRequestResponseTimeOut() {
            return pullRequestResponseTimeOut;
        }

        /**
         * Call this method named getPullRequestPayloadSelector to retrieve the value previously set in pullRequestPayloadSelector method.
         *
         * @return The PullRequestPayloadSelector
         */
        public PayloadSelector getPullRequestPayloadSelector() {
            return pullRequestPayloadSelector;
        }

        /**
         * Call this method named getRequests to retrieve the value previously set in pullRequestRequests method.
         *
         * @return The ServiceCapability
         */
        public ServiceCapability getPullRequestRequests() {
            return pullRequestRequests;
        }

        /**
         * Call this method named getPullResponseErrorDetail to retrieve the value previously set in pullResponseErrorDetail method.
         *
         * @return The pullResponseErrorDetail
         */
        public String getPullResponseErrorDetail() {
            return pullResponseErrorDetail;
        }

        /**
         * Call this method named getPullResponseResultCode to retrieve the value previously set in pullResponseResultCode method.
         *
         * @return The pullResponseResultCode
         */
        public ResponseCodeType getPullResponseResultCode() {
            return pullResponseResultCode;
        }

        /**
         * Call this method named getPullResponseFulfils to retrieve the value previously set in pullResponseFulfils method.
         *
         * @return The pullResponseFulfils
         */
        public ServiceCapability getPullResponseFulfils() {
            return pullResponseFulfils;
        }

        /**
         * Call this method named getFeedbackType to retrieve the value previously set in feedbackType method.
         *
         * @return The FeedbackType
         */
        public FeedbackType getFeedbackType() {
            return feedbackType;
        }

        /**
         * Call this method named getFeedbackReason to retrieve the value previously set in feedbackReason method.
         *
         * @return The feedbackReason
         */
        public String getFeedbackReason() {
            return feedbackReason;
        }

        /**
         * Call this method named getFeedbackRefMessageId to retrieve the value previously set in feedbackRefMessageId method.
         *
         * @return The feedbackRefMessageId
         */
        public String getFeedbackRefMessageId() {
            return feedbackRefMessageId;
        }

        /**
         * Call this method named getReferenceMessageId to retrieve the value previously set in referenceMessageId method.
         *
         * @return The referenceMessageId
         */
        public String getReferenceMessageId() {
            return referenceMessageId;
        }

        /**
         * Call this method named referenceMessage to retrieve the value previously set in referenceMessage method.
         *
         * @return The referenceMessage
         */
        public Message getReferenceMessage() {
            return referenceMessage;
        }

        /**
         * Call this method named getMessageHistory to retrieve the value previously set in messageHistory method.
         *
         * @return The MessageHistory
         */
        public List<RegisteredMessage> getMessageHistory() {
            return messageHistory;
        }

        /**
         * Call this method named buildContext to create the instance of MessageDataContext, that will contain all the data previously set in the builder.
         * With the call, a data validation will be performed, and if there is some inconsistency, an exception will be thrown.
         *
         * @return The MessageDataContext
         * @throws CiseAdaptorValidationException It will be thrown if some problem is found in the data contained in the builder.
         */
        public MessageDataContext buildContext() throws CiseAdaptorValidationException {

            MessageDataContext messageDataContext = new MessageDataContext(
                    recipients,
                    currentService,
                    discoveryProfiles,
                    contextId,
                    cisePayload,
                    priority,
                    requiresAck,
                    retryStrategy,
                    pullRequestResponseTimeOut,
                    pullRequestPayloadSelector,
                    pullRequestRequests,
                    pullResponseErrorDetail,
                    pullResponseResultCode,
                    pullResponseFulfils,
                    feedbackType,
                    feedbackReason,
                    feedbackRefMessageId,
                    referenceMessageId,
                    referenceMessage,
                    messageHistory);

            MessageDataContextValidator.validateContext(messageDataContext);

            return messageDataContext;
        }

        /**
         * Helper to add specific PullRequest Known information:
         *
         * <ul>
         * <li>Recipients (<b>Required</b>): The PullRequest list of destination services.</li>
         * <li>ResponseTimeOut (Not Required): The request should be answered within this time limit. After this time, the response may not be considered by the requesting system.</li>
         * <li>Requests (Not Required): Service Capability required by the system provider. Refer to <a href="https://emsa.europa.eu/cise-documentation/The-ServiceCapability_888494703.html">ServiceCapability</a></li>
         * </ul>
         *
         * @param recipients      List of destination services. At least one service must be present.
         * @param pullRequestResponseTimeOut Time in seconds. An integer value, as for example: 3600.
         * @param pullRequestRequests        A Service Capability
         * @return The MessageDataContextManager instance
         */
        public MessageDataContextManager initializePullRequest(List<Service> recipients, Integer pullRequestResponseTimeOut, ServiceCapability pullRequestRequests) {
            this.recipients = recipients;
            if(pullRequestResponseTimeOut!=null) {
                this.pullRequestResponseTimeOut = pullRequestResponseTimeOut;
            }
            this.pullRequestRequests = pullRequestRequests;
            return this;
        }

        /**
         * Helper to add specific PullRequest Unknown information:
         * <ul>
         * <li><b>Discovery Profiles (Required):</b> Discovery profiles of recipient services</li>
         * <li><b>ResponseTimeOut (Not Required):</b> The request should be answered within this time limit. After this time, the response may not be considered by the requesting system.</li>
         * <li><b>Requests (Not Required):</b> Service Capability required by the system provider. Refer to <a href="https://emsa.europa.eu/cise-documentation/The-ServiceCapability_888494703.html">ServiceCapability</a></li>
         * </ul>
         *
         * @param discoveryProfiles Discovery profiles. It cannot be empty
         * @param pullRequestResponseTimeOut   Time in seconds. An integer value, as for example: 3600.
         * @param pullRequestRequests          A Service Capability
         * @return The MessageDataContextManager instance
         */
        public MessageDataContextManager initializePullRequestUnknown(List<ServiceProfile> discoveryProfiles, Integer pullRequestResponseTimeOut, ServiceCapability pullRequestRequests) {
            this.discoveryProfiles = discoveryProfiles;
            this.pullRequestResponseTimeOut = pullRequestResponseTimeOut;
            this.pullRequestRequests = pullRequestRequests;

            return this;
        }

        /**
         * Helper to add specific PullResponse information:
         * <ul>
         * <li><b>ReferenceMessage (Required):</b> This is the PullRequest CISE Message previously received that needs the response.</li>
         * <li><b>ErrorDetail (Not Required):</b> This field may give a textual description of an error that could have happened during the process of the pull request message. This can be used to communicate an error that happened after sending the acknowledgement message.</li>
         * <li><b>ResultCode (Required):</b> This field provides an OK code if the response is sent along with the pull response. It can also provide an error code if an error occurred after sending the acknowledgement message.</li>
         * <li><b>Fulfils (Not Required):</b> This value describes the characteristics used to respond to the request, as for example, the type of query performed (exact or best effort). Refer to <a href="https://emsa.europa.eu/cise-documentation/The-ServiceCapability_888494703.html">ServiceCapability</a></li>
         * </ul>
         *
         * @param referenceMessage PullRequest CISE Message previously received
         * @param pullResponseErrorDetail      String with the error description. It is not a required parameter so it can be null
         * @param pullResponseResultCode       Enumeration with the code values returned with a PullResponse
         * @param pullResponseFulfils          A Service Capability. It is not a required parameter so it can be null
         * @return The MessageDataContextManager instance
         */
        public MessageDataContextManager initializePullResponse(Message referenceMessage, String pullResponseErrorDetail, ResponseCodeType pullResponseResultCode, ServiceCapability pullResponseFulfils) {
            this.referenceMessage = referenceMessage;
            this.pullResponseErrorDetail = pullResponseErrorDetail;
            this.pullResponseResultCode = pullResponseResultCode;
            this.pullResponseFulfils = pullResponseFulfils;

            return this;
        }

        /**
         * Helper to add specific PushKnown information:
         * <ul>
         * <li><b>Recipients (Required):</b> The PushKnown list of destination services.</li>
         * </ul>
         *
         * @param recipients List of destination services. At least one service must be present.
         * @return The MessageDataContextManager instance
         */
        public MessageDataContextManager initializePushKnown(List<Service> recipients) {
            this.recipients = recipients;
            return this;
        }


        /**
         * Helper to add specific Push UnKnown information:
         * <ul>
         * <li><b>DiscoveryProfiles (Required):</b> The list of discovery profiles.</li>
         * </ul>
         *
         * @param discoveryProfiles List of discovery profiles. At least one profile must be present.
         * @return The MessageDataContextManager instance
         */
        public MessageDataContextManager initializePushUnknown(List<ServiceProfile> discoveryProfiles) {
            this.discoveryProfiles = safeCopy(discoveryProfiles);
            return this;
        }

        /**
         * Helper to add specific SubscriberConsumer information:
         * <ul>
         * <li><b>Recipients (Required):</b> The SubscriberConsumer list of destination services.</li>
         * </ul>
         *
         * @param recipients List of destination services. At least one service must be present.
         * @return The MessageDataContextManager instance
         */
        public MessageDataContextManager initializeSubscribeConsumer(List<Service> recipients) {
            this.recipients = recipients;
            return this;
        }

        /**
         * Helper to add specific SubscriberConsumerUnknown information:
         * <ul>
         * <li><b>DiscoveryProfiles (Required):</b> The list of discovery profiles for the unknown subscribers.</li>
         * </ul>
         *
         * @param discoveryProfiles List of discovery profiles. At least one profile must be present.
         * @return The MessageDataContextManager instance
         */
        public MessageDataContextManager initializeSubscribeConsumerUnknown(List<ServiceProfile> discoveryProfiles) {
            this.discoveryProfiles = safeCopy(discoveryProfiles);
            return this;
        }


        /**
         * Helper to add specific Feedback information:
         * <ul>
         * <li><b>Recipients (Required):</b> The Feedback list of destination services.</li>
         * <li><b>FeedbackType (Required):</b> The type of feedback to provide.</li>
         * <li><b>Reason (Not Required):</b> The description of the feedbackReason for feedback. This field is a free text.</li>
         * <li><b>RefMessageId (Required):</b> The MessageId that this feedback message refers to.</li>
         * </ul>
         *
         * @param recipients   List of destination services. At least one service must be present.
         * @param feedbackType Enumeration of the type of feedback for the feedback message.
         * @param feedbackReason       String text
         * @param feedbackRefMessageId A previous valid MessageId.
         * @return The MessageDataContextManager instance
         */
        public MessageDataContextManager initializeFeedback(List<Service> recipients, FeedbackType feedbackType, String feedbackReason, String feedbackRefMessageId) {
            this.recipients = recipients;
            this.feedbackType = feedbackType;
            this.feedbackReason = feedbackReason;
            this.feedbackRefMessageId = feedbackRefMessageId;

            return this;
        }

        /**
         * Helper to add priority, requiring ack and retry strategy communication parameters of the CISE Message
         *
         * @param priority Priority type enum
         * @param requiresAck True if an asynchronous ack is required when the message is received
         * @param retryStrategy The retry strategy enum
         * @return This builder instance
         */
        public MessageDataContextManager initializeMessageBehaviour(PriorityType priority, Boolean requiresAck, RetryStrategyType retryStrategy) {
            this.priority = priority;
            this.requiresAck = requiresAck;
            this.retryStrategy = retryStrategy;
            return this;
        }
    }
}
