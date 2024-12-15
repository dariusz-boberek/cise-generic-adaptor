package eu.cise.adaptor.quarkus.dispatcher;

import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.common.logging.LogConfig;
import eu.cise.dispatcher.DispatchResult;
import eu.cise.dispatcher.Dispatcher;
import eu.cise.dispatcher.DispatcherException;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PriorityType;
import eu.eucise.helpers.AckBuilder;

import java.util.Date;

import static eu.cise.adaptor.core.common.logging.LoggerMessage.of;
import static eu.cise.adaptor.core.common.logging.MessageRouteValue.LS_TO_CISE;
import static eu.cise.adaptor.core.common.message.MessageTypeEnum.UNDEFINED;

public class AdaptorDispatcher {

    private final AdaptorLogger logger = LogConfig.configureLogging(AdaptorDispatcher.class);
    private final Dispatcher dispatcher;
    private final String nodeMessagesUrl;

    public AdaptorDispatcher(Dispatcher dispatcher, String nodeMessagesUrl) {
        this.dispatcher = dispatcher;
        this.nodeMessagesUrl = nodeMessagesUrl;
    }


    public Acknowledgement send(Message message) {
        DispatchResult result;
        try {
            result = dispatcher.send(message, nodeMessagesUrl);
        } catch (DispatcherException dispatcherException) {
            var cause = dispatcherException.getCause();
            if (cause != null) {
                var innerCause = cause.getCause();
                if (innerCause != null) {
                    cause = innerCause;
                }
            }
            logger.error(of(UNDEFINED, message.getMessageID(), "Dispatcher error").addRoutingAttribute(LS_TO_CISE), cause != null ? cause : dispatcherException);

            if (cause instanceof java.net.SocketException) {
                result = new DispatchResult(HttpErrors.SOCKET_ERROR, dispatcherException.getMessage());
            } else {
                result = new DispatchResult(HttpErrors.SERVER_ERROR, dispatcherException.getMessage());
            }
        }
        Acknowledgement ackFromNode;

        if (result.isOK()) {
            ackFromNode = result.getResult();
        } else {
            ackFromNode = AckBuilder.newAck()
                    .id(message.getMessageID())
                    .correlationId(message.getCorrelationID())
                    .creationDateTime(new Date())
                    .priority(PriorityType.HIGH)
                    .isRequiresAck(false)
                    .ackCode(AcknowledgementType.SERVER_ERROR)
                    .ackDetail(result.getHttpErrorMessage())
                    .build();
        }

        return ackFromNode;
    }
}
