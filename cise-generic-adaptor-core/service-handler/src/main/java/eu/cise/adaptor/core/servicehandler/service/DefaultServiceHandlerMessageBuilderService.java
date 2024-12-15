package eu.cise.adaptor.core.servicehandler.service;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorRuntimeException;
import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.common.logging.LogConfig;
import eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageUseCase;
import eu.cise.adaptor.core.servicehandler.domain.message.pattern.PullRequestHelper;
import eu.cise.adaptor.core.servicehandler.domain.message.pattern.PullResponseHelper;
import eu.cise.adaptor.core.servicehandler.domain.message.pattern.PushHelper;
import eu.cise.adaptor.core.servicehandler.domain.message.pattern.subscribe.SubscribeConsumerHelper;
import eu.cise.adaptor.core.servicehandler.domain.message.pattern.subscribe.SubscribeProviderHelper;
import eu.cise.adaptor.core.servicehandler.port.out.MessageRepositoryPort;
import eu.cise.adaptor.core.servicehandler.port.out.OutgoingServiceHandlerToMessageHandlerPort;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static eu.cise.adaptor.core.common.logging.LoggerMessage.of;
import static eu.cise.adaptor.core.common.logging.MessageRouteValue.LS_TO_CISE;
import static eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage.ofCISEMessageDateSent;
import static eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage.ofNotPersistedCISEMessage;


/**
 * <p>This is the default implementation of the ServiceHandlerMessageBuilderService interface.
 * It handles the construction of CISE messages based on inputs from Legacy Systems and the specific requirements of the CISE Service Model.</p>
 *
 * <p>This class is a key component in the Generic Adaptor, enabling it to create and manage CISE messages efficiently,
 * thereby facilitating smooth communication between the CISE Node and various Legacy Systems.</p>
 *
 * @see ServiceHandlerMessageBuilderService
 */
public class DefaultServiceHandlerMessageBuilderService implements ServiceHandlerMessageBuilderService {

    private static final AdaptorLogger log = LogConfig.configureLogging(DefaultServiceHandlerMessageBuilderService.class);
    private final OutgoingServiceHandlerToMessageHandlerPort outgoingServiceHandlerToMessageHandlerPort;
    private final MessageRepositoryPort messageRepositoryPort;

    public DefaultServiceHandlerMessageBuilderService(OutgoingServiceHandlerToMessageHandlerPort outgoingServiceHandlerToMessageHandlerPort, MessageRepositoryPort messageRepositoryPort) {
        this.outgoingServiceHandlerToMessageHandlerPort = outgoingServiceHandlerToMessageHandlerPort;
        this.messageRepositoryPort = messageRepositoryPort;
    }

    @Override
    public List<Pair<RegisteredMessage, Acknowledgement>> handleMessage(MessageDataContext msgCtx) {

        List<Pair<RegisteredMessage, Acknowledgement>> resultList = new ArrayList<>();
        List<Message> messageToSendList;

        // Build the message(s) from the MessageDataContext
        MessageUseCase messageUseCase = MessageUseCase.valueOf(msgCtx.getCurrentService());
        switch (messageUseCase) {
            case PULL_RESPONSE:
                messageToSendList = PullResponseHelper.generateListOfPullResponseMessage(msgCtx);
                break;
            case PUSH:
                messageToSendList = PushHelper.generateListOfPushMessage(msgCtx);
                break;
            case PUSH_SUBSCRIBE_PROVIDER:
                messageToSendList = SubscribeProviderHelper.generateListOfSubscribeProviderMessage(msgCtx);
                break;
            case PULL_SUBSCRIBE_CONSUMER:
                messageToSendList = SubscribeConsumerHelper.generateListOfSubscribeConsumerMessage(msgCtx);
                break;
            case PULL_REQUEST:
                messageToSendList = PullRequestHelper.generateListOfPullRequestMessage(msgCtx);
                break;
            case FEEDBACK:
            default:
                throw new CiseAdaptorRuntimeException("The message with Role " + msgCtx.getCurrentService().getServiceRole() +
                        " and operation " + msgCtx.getCurrentService().getServiceOperation() + " is not supported");
        }

        for (Message message : messageToSendList) {

            log.info(of("handleMessage: {} --> MessageHandler, messageId {}",
                    messageUseCase, message.getMessageID())
                    .addRoutingAttribute(LS_TO_CISE));

            // Send the message
            Acknowledgement acknowledgement = outgoingServiceHandlerToMessageHandlerPort.forwardMessageToMessageHandler(message);

            // Save in case of success and add the registeredMessage and the acknowledgement to the couple result list
            if (acknowledgement.getAckCode() == AcknowledgementType.SUCCESS) {

                RegisteredMessage registeredMessage = ofCISEMessageDateSent(message);
                messageRepositoryPort.save(registeredMessage);
                resultList.add(Pair.of(registeredMessage, acknowledgement));

                log.info(of("handleMessage: {} MessageHandler --> Success, messageId {}",
                        messageUseCase, message.getMessageID())
                        .addRoutingAttribute(LS_TO_CISE));
            } else {
                resultList.add(Pair.of(ofNotPersistedCISEMessage(message),acknowledgement));
                log.error(of("handleMessage: Failed Acknowledgement, code {} detail {} for messageId: {}",
                        acknowledgement.getAckCode(), acknowledgement.getAckDetail(), message.getMessageID())
                        .addRoutingAttribute(LS_TO_CISE));
            }
        }

        return resultList;
    }

    @Override
    public Optional<Message> getMessageById(String referenceMessageId) {
        return messageRepositoryPort.getByMessageId(referenceMessageId);
    }

    @Override
    public List<RegisteredMessage> getMessageHistoryById(String referenceMessageId) {
        Optional<Message> message = messageRepositoryPort.getByMessageId(referenceMessageId);
        return message.isPresent() ? messageRepositoryPort.getMessagesHistoryByCorrelationId(message.get().getCorrelationID()) : new ArrayList<>();
    }

    @Override
    public List<RegisteredMessage> getMessageHistoryByContextId(String contextId) {
        return messageRepositoryPort.getMessagesHistoryByContextId(contextId);
    }

    @Override
    public List<RegisteredMessage> getMessageHistory(String referenceMessageId, String contextId) {
        Optional<Message> message = messageRepositoryPort.getByMessageId(referenceMessageId);
        return messageRepositoryPort.getMessagesHistory(message.map(Message::getCorrelationID).orElse(null), contextId);
    }
}
