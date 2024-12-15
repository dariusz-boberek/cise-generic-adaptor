package eu.cise.adaptor.core.servicehandler.domain;

import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * This class serves as a container for messages in the CISE Adaptor system.
 *
 * <p>It provides functionalities to wrap CISE messages with additional metadata and processing information.
 * It includes details such as message ID, correlation ID, creation date, and the date/time when the message was processed.
 * The class also encapsulates the message content using the {@link Message} class from the CISE service model.
 * <p>
 * Basic Usage:
 * <pre>
 * {@code
 *  Message ciseMessage = ...; // Acquire a CISE message
 *  RegisteredMessage wrappedReceived = RegisteredMessage.ofCISEMessageDateReceived(ciseMessage, ZonedDateTime.now());
 *  RegisteredMessage wrappedSent = RegisteredMessage.ofCISEMessageDateSent(ciseMessage, ZonedDateTime.now());
 *  // Further operations with wrappedReceived and wrappedSent
 * }
 * </pre>
 *
 * @see Message
 * @see XmlMapper
 */
public class RegisteredMessage {

    private static final XmlMapper xmlMapper = new DefaultXmlMapper();

    private Message ciseMessage;

    private String messageId;

    private String correlationId;

    private String contextId;

    //taken from the CISE message
    private XMLGregorianCalendar dateCreated;

    private ZonedDateTime dateTimeProcessed;

    private MessageProcessAction messageProcessAction;

    public RegisteredMessage() {
    }

    public RegisteredMessage(Message ciseMessage,
                             String messageId,
                             String correlationId,
                             String contextId,
                             XMLGregorianCalendar dateCreated,
                             ZonedDateTime dateTimeProcessed,
                             MessageProcessAction processAction) {
        validateId(messageId, "MessageId value cannot be null or empty");
        validateId(correlationId, "CorrelationId value cannot be null or empty");

        this.ciseMessage = ciseMessage;
        this.messageId = messageId;
        this.correlationId = correlationId;
        this.contextId = contextId;
        this.dateCreated = dateCreated;
        this.dateTimeProcessed = dateTimeProcessed;
        this.messageProcessAction = processAction;
    }

    public static String randomMessageId() {
        return UUID.randomUUID().toString();
    }

    public static RegisteredMessage ofCISEMessageDateReceived(Message ciseMessage) {
        return new RegisteredMessage(ciseMessage,
                ciseMessage.getMessageID(),
                ciseMessage.getCorrelationID(),
                ciseMessage.getContextID(),
                ciseMessage.getCreationDateTime(),
                ZonedDateTime.now(), MessageProcessAction.RECEIVED);
    }

    public static RegisteredMessage ofCISEMessageDateSent(Message ciseMessage) {
        return new RegisteredMessage(ciseMessage,
                ciseMessage.getMessageID(),
                ciseMessage.getCorrelationID(),
                ciseMessage.getContextID(),
                ciseMessage.getCreationDateTime(),
                ZonedDateTime.now(),
                MessageProcessAction.SENT);
    }

    public static RegisteredMessage ofNotPersistedCISEMessage(Message ciseMessage) {
        return new RegisteredMessage(ciseMessage,
                ciseMessage.getMessageID(),
                ciseMessage.getCorrelationID(),
                ciseMessage.getContextID(),
                ciseMessage.getCreationDateTime(),
                null,
                null);
    }

    public static Message translateMessageFromXML(String xmlMessage) {
        return xmlMapper.fromXML(xmlMessage);
    }

    private void validateId(String id, String errorMessage) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public Message getCiseMessage() {
        return ciseMessage;
    }

    public String translateMessageFromDomain() {
        return xmlMapper.toXML(ciseMessage);
    }

    public String getMessageId() {
        return messageId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public XMLGregorianCalendar getDateCreated() {
        return dateCreated;
    }

    public ZonedDateTime getDateTimeProcessed() {
        return dateTimeProcessed;
    }

    public MessageProcessAction getMessageProcessAction() {
        return messageProcessAction;
    }

    public String getContextId() {
        return contextId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegisteredMessage that = (RegisteredMessage) o;

        // removal of Signature in order to be able to compare the objects
        Message cloneThis = (Message) this.getCiseMessage().clone();
        Message cloneThat = (Message) that.getCiseMessage().clone();
        cloneThis.setAny(null);
        cloneThat.setAny(null);

        return cloneThis.equals(cloneThat) &&
                getMessageId().equals(that.getMessageId()) &&
                getCorrelationId().equals(that.getCorrelationId()) &&
                getContextId().equals(that.getContextId()) &&
                getDateCreated().equals(that.getDateCreated()) &&
                getDateTimeProcessed().equals(that.getDateTimeProcessed()) &&
                getMessageProcessAction() == that.getMessageProcessAction();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCiseMessage(), getMessageId(), getCorrelationId(), getDateCreated(), getDateTimeProcessed(), getMessageProcessAction(), getContextId());
    }
}