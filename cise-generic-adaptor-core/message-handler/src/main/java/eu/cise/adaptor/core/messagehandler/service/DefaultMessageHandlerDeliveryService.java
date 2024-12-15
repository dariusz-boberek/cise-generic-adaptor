package eu.cise.adaptor.core.messagehandler.service;

import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.common.logging.LogConfig;
import eu.cise.adaptor.core.common.utils.AcknowledgementUtils;
import eu.cise.adaptor.core.messagehandler.port.out.OutgoingMessageHandlerToNodePort;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.SignatureService;
import eu.eucise.helpers.DateHelper;

import java.util.Date;

import static eu.cise.adaptor.core.common.logging.LoggerMessage.of;
import static eu.cise.adaptor.core.common.logging.MessageRouteValue.LS_TO_CISE;
import static eu.cise.adaptor.core.common.message.MessageTypeEnum.messageTypeOf;

/**
 * Default implementation of {@code MessageHandlerDeliveryService}.
 * <p>
 * Implements the logic for delivering messages to the CISE Node. It updates the creation time, signs the messages, sends them
 * to the Node, and handles the SyncAcks.
 *
 * @see MessageHandlerDeliveryService
 */
public class DefaultMessageHandlerDeliveryService implements MessageHandlerDeliveryService {

    private final static AdaptorLogger log = LogConfig.configureLogging(DefaultMessageHandlerDeliveryService.class);
    private OutgoingMessageHandlerToNodePort outgoingMessageHandlerToNodePort;
    private SignatureService signatureService;

    public DefaultMessageHandlerDeliveryService(OutgoingMessageHandlerToNodePort outgoingMessageHandlerToNodePort, SignatureService signatureService) {
        this.outgoingMessageHandlerToNodePort = outgoingMessageHandlerToNodePort;
        this.signatureService = signatureService;
    }

    @Override
    public Acknowledgement prepareAndForwardMessageToCiseNode(Message message) {
        try {
            message.setCreationDateTime(DateHelper.toXMLGregorianCalendar(new Date()));
            Message signedMessage = signatureService.sign(message);
            log.info(of(messageTypeOf(signedMessage), signedMessage.getMessageID(), "Forwarding CISE message to the CISE Node.").addRoutingAttribute(LS_TO_CISE));
            return outgoingMessageHandlerToNodePort.sendStringMessage(signedMessage);
        } catch (RuntimeException e) {
            return AcknowledgementUtils.buildAckError(e, message);
        }
    }
}
