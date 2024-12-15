package eu.cise.adaptor.quarkus.servicehandler.adapter.out;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorRuntimeException;
import eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage;
import eu.cise.adaptor.quarkus.servicehandler.domain.CiseMessageEntity;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

public class MessageMapper {

    public static CiseMessageEntity toEntity(RegisteredMessage registeredMessage) {
        CiseMessageEntity entity = new CiseMessageEntity();
        entity.setCiseMessageId(registeredMessage.getMessageId());
        entity.setCiseMessage(registeredMessage.translateMessageFromDomain());
        entity.setCorrelationId(registeredMessage.getCorrelationId());
        entity.setContextId(registeredMessage.getContextId());
        entity.setDateCreated(registeredMessage.getDateCreated().toGregorianCalendar().toZonedDateTime());
        entity.setDateTimeProcessed(registeredMessage.getDateTimeProcessed());
        entity.setMessageProcessAction(registeredMessage.getMessageProcessAction());
        return entity;
    }

    public static RegisteredMessage toDomain(CiseMessageEntity entity) {
        Message ciseMessage = RegisteredMessage.translateMessageFromXML(entity.getCiseMessage());

        GregorianCalendar calendar = GregorianCalendar.from(entity.getDateCreated());
        XMLGregorianCalendar dateCreated;
        try {
            dateCreated = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (DatatypeConfigurationException e) {
            throw new CiseAdaptorRuntimeException(e.getMessage(), AcknowledgementType.INVALID_REQUEST_OBJECT, e);
        }

        return new RegisteredMessage(ciseMessage,
                entity.getCiseMessageId(),
                entity.getCorrelationId(),
                entity.getContextId(),
                dateCreated,
                entity.getDateTimeProcessed(),
                entity.getMessageProcessAction());
    }
}
