package eu.cise.adaptor.core.messagehandler.service;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorRuntimeException;
import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.common.logging.LogConfig;
import eu.cise.adaptor.core.common.logging.MessageAttributeKey;
import eu.cise.adaptor.core.common.utils.AcknowledgementUtils;
import eu.cise.adaptor.core.messagehandler.port.out.OutgoingMessageHandlerToServiceHandlerPort;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.SignatureService;
import eu.cise.signature.exceptions.SignatureEx;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;

import static eu.cise.adaptor.core.common.logging.LoggerMessage.of;
import static eu.cise.adaptor.core.common.message.MessageTypeEnum.UNDEFINED;
import static eu.cise.adaptor.core.common.message.MessageTypeEnum.messageTypeOf;
import static eu.cise.adaptor.core.common.utils.AcknowledgementUtils.buildAckError;
import static eu.cise.adaptor.core.common.utils.AcknowledgementUtils.buildAckSuccess;
import static eu.cise.adaptor.core.common.utils.AcknowledgementUtils.getErrorMessage;


/**
 * Default implementation of {@code MessageHandlerVerificationService}.
 * <p>
 * This class provides the default mechanism for verifying the structure and authenticity of messages exchanged with the CISE Node.
 *
 * @see MessageHandlerVerificationService
 */
public final class DefaultMessageHandlerVerificationService implements MessageHandlerVerificationService {

    private static final AdaptorLogger log = LogConfig.configureLogging(DefaultMessageHandlerVerificationService.class);
    private final OutgoingMessageHandlerToServiceHandlerPort outgoingMessageHandlerToServiceHandlerPort;
    private final XmlMapper xmlMapper;
    private final SignatureService signatureService;

    public DefaultMessageHandlerVerificationService(OutgoingMessageHandlerToServiceHandlerPort outgoingMessageHandlerToServiceHandlerPort, SignatureService signatureService) {
        this.outgoingMessageHandlerToServiceHandlerPort = outgoingMessageHandlerToServiceHandlerPort;
        this.xmlMapper = new DefaultXmlMapper();
        this.signatureService = signatureService;
    }

    public static String getStackTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    @Override
    public Acknowledgement requestSyncAck(String messageXML, Set<String> availableServiceIds) {
        Message message = null;
        try {
            message = xmlMapper.fromXML(messageXML);
            log.info(of(messageTypeOf(message), message.getMessageID(), "Message received.").addAttribute(MessageAttributeKey.CORRELATION_ID, message.getCorrelationID()));
            if (message.getRecipient() == null) {
                log.error(of(messageTypeOf(message), message.getMessageID(), "No recipient in message.").addAttribute(MessageAttributeKey.CORRELATION_ID, message.getCorrelationID()));
                return buildAckError(new CiseAdaptorRuntimeException("No recipient in message", AcknowledgementType.BAD_REQUEST, null), messageXML, message);
            }
            String currentServiceID = message.getRecipient().getServiceID();

            log.debug(of(messageTypeOf(message), message.getMessageID(), "availableServiceIds size {}.", availableServiceIds.size())
                    .addAttribute(MessageAttributeKey.CORRELATION_ID, message.getCorrelationID())
                    .addAttribute(MessageAttributeKey.CONTEXT_ID, message.getContextID()));

            if (availableServiceIds.contains(currentServiceID)) {
                log.debug(of(messageTypeOf(message), message.getMessageID(), "ServiceId {} verified.", currentServiceID).addAttribute(MessageAttributeKey.CORRELATION_ID, message.getCorrelationID()));
            } else {
                log.error(of(messageTypeOf(message), message.getMessageID(), "Recipient not recognized by adaptor. Registered ServiceIds: {}. Incoming ServiceId: {}", String.join(" - ", availableServiceIds), currentServiceID).addAttribute(MessageAttributeKey.CORRELATION_ID, message.getCorrelationID()));
                return buildAckError(new CiseAdaptorRuntimeException("Recipient not recognized by adaptor", AcknowledgementType.BAD_REQUEST, null), messageXML, message);
            }
            signatureService.verify(message);
            log.debug(of(messageTypeOf(message), message.getMessageID(), "Message verified.").addAttribute(MessageAttributeKey.CORRELATION_ID, message.getCorrelationID()));
            outgoingMessageHandlerToServiceHandlerPort.handleMessage(message);
            return buildAckSuccess(message);
        } catch (SignatureEx signatureEx) {
            String errMsg = "Message signature not validated:\n" + getErrorMessage(signatureEx);
            log.error(of(UNDEFINED, AcknowledgementUtils.getMessageId(null, messageXML), "Signature Exception for message, {}", errMsg));
            return buildAckError(new CiseAdaptorRuntimeException(errMsg, AcknowledgementType.AUTHENTICATION_ERROR, signatureEx), messageXML, message);
        } catch (Exception e) {
            String errMsg = "Exception detail:\n" + e.getMessage() + getStackTraceAsString(e);
            log.error(of(UNDEFINED, AcknowledgementUtils.getMessageId(null, messageXML), "Processing Exception for message, {}", errMsg));
            return buildAckError(new CiseAdaptorRuntimeException(errMsg, AcknowledgementType.BAD_REQUEST, e), messageXML, message);
        }
    }

}