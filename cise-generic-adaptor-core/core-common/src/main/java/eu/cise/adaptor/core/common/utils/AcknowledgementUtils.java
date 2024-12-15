package eu.cise.adaptor.core.common.utils;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorRuntimeException;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PriorityType;
import eu.eucise.helpers.AckBuilder;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.UUID;

/**
 * This class holds helper methods to operate on Acknowledgments and CISE Messages
 */
public class AcknowledgementUtils {

    /**
     * This method builds a standard Success Acknowledgment based on information from the CISE Message provided
     *
     * @param message The CISE Message to build Success Acknowledgment for
     * @return The generated Acknowledgment
     */
    public static Acknowledgement buildAckSuccess(Message message) {
        Acknowledgement ack = AckBuilder.newAck()
                .id(message.getMessageID() + "_" + UUID.randomUUID().toString())
                .correlationId(StringUtils.isEmpty(message.getCorrelationID()) ? message.getMessageID() : message.getCorrelationID())
                .creationDateTime(new Date())
                .priority(PriorityType.HIGH)
                .isRequiresAck(false)
                .ackCode(AcknowledgementType.SUCCESS)
                .ackDetail("Message received")
                .build();
        ack.setPayload(null);
        return ack;
    }

    /**
     * This method builds an Error Acknowledgment based on the CISE Message and throwable provided
     *
     * @param e       The Throwable that needs to be logged in the Acknowledgment
     * @param message The CISE message to build the Acknowledgment for
     * @return The generated Acknowledgment
     */
    public static Acknowledgement buildAckError(Throwable e, Message message) {
        XmlMapper xmlMapper = new DefaultXmlMapper();
        return buildAckError(e, xmlMapper.toXML(message), message);
    }

    /**
     * This method should be used in cases the CISE Message cannot be created or is created with Errors from the String representing the CISE Message
     * in order to build an Error Acknowledgment
     *
     * @param e          The Exception to build the Acknowledgment for
     * @param messageXML the String representation of the CISE Message
     * @param message    The message object (null can also be passed if the CISE Message is not available)
     * @return The generated Acknowledgment
     */
    public static Acknowledgement buildAckError(Throwable e, String messageXML, Message message) {
        String idFromMessage = getMessageId(message, messageXML);
        String ackId = StringUtils.isBlank(idFromMessage) ? "" + UUID.randomUUID() : idFromMessage + "_" + UUID.randomUUID();

        Acknowledgement ack = AckBuilder.newAck()
                .id(ackId)
                .correlationId(getCorrelationId(message, messageXML, ackId))
                .creationDateTime(new Date())
                .priority(PriorityType.HIGH)
                .isRequiresAck(false)
                .ackCode(buildAcknowledgementType(e))
                .ackDetail(buildAckDetail(e))
                .build();
        ack.setPayload(null);
        return ack;
    }

    private static String buildAckDetail(Throwable e) {
        String ackDetail;
        if (e instanceof CiseAdaptorRuntimeException) {
            ackDetail = ((CiseAdaptorRuntimeException) e).getAckDetail();
        } else {
            ackDetail = e.getMessage() + getStackTraceAsString(e);
        }
        return ackDetail;
    }

    private static AcknowledgementType buildAcknowledgementType(Throwable e) {
        AcknowledgementType ackCode;
        if (e instanceof CiseAdaptorRuntimeException) {
            ackCode = ((CiseAdaptorRuntimeException) e).getAckCode();
        } else {
            ackCode = AcknowledgementType.SERVER_ERROR;
        }
        return ackCode;
    }

    private static String getCorrelationId(Message message, String messageXML, String messageId) {
        if (message != null) {
            return StringUtils.isBlank(message.getCorrelationID()) ? messageId : message.getCorrelationID();
        } else {
            String corrId = StringUtils.substringBetween(messageXML, "<CorrelationID>", "</CorrelationID>");
            return StringUtils.isBlank(corrId) ? messageId : corrId;
        }
    }

    /**
     * This method returns the Message ID found in either the CISE Message object or in the provided messageXML between the {@code<MessageId></MessageId>} tag
     * @param message The CISE Message to get the ID from. If null the message ID is taken from the messageXML
     * @param messageXML The String representation of the CISE Message
     * @return The message ID found
     */
    public static String getMessageId(Message message, String messageXML) {
        if (message == null) {
            return StringUtils.substringBetween(messageXML, "<MessageID>", "</MessageID>");
        } else {
            return message.getMessageID();
        }
    }

    /**
     * Helper method to transform a Throwable into a String representing all the information possible
     *
     * @param e The Throwable to process
     * @return The generated String representation of the Throwable
     */
    public static String getErrorMessage(Throwable e) {
        String errMsg = e.getMessage();
        if (StringUtils.isEmpty(errMsg) && e.getCause() != null) {
            Throwable t = e.getCause();
            errMsg = t.getMessage();
            if (StringUtils.isEmpty(errMsg) && t.getCause() != null) {
                t = t.getCause();
                errMsg = t.getMessage();
                if (StringUtils.isEmpty(errMsg) && t.getCause() != null) {
                    t = t.getCause();
                    errMsg = t.getMessage();
                }
            }
        }

        return StringUtils.isEmpty(errMsg) ? errMsg : errMsg.trim();
    }


    private static String nullToEmpty(String string) {
        return string == null ? "" : string;
    }

    private static String getStackTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
