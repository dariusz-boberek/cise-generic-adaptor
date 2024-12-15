package eu.cise.adaptor.quarkus.servicehandler.domain;

import eu.cise.adaptor.core.servicehandler.domain.MessageProcessAction;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;

@Entity
public class CiseMessageEntity extends PanacheEntity {

    @NotBlank
    @Column(unique = true)
    private String ciseMessageId;

    @NotBlank
    @Column(columnDefinition = "CLOB")
    private String ciseMessage;

    @NotBlank
    private String correlationId;

    private String contextId;

    @Column(columnDefinition = "timestamp(9) with time zone")
    private ZonedDateTime dateCreated;


    @Column(columnDefinition = "timestamp(9) with time zone")
    private ZonedDateTime dateTimeProcessed;

    @Enumerated(EnumType.STRING)
    private MessageProcessAction messageProcessAction;

    public String getCiseMessageId() {
        return ciseMessageId;
    }

    public void setCiseMessageId(String ciseMessageId) {
        this.ciseMessageId = ciseMessageId;
    }

    public String getCiseMessage() {
        return ciseMessage;
    }

    public void setCiseMessage(String ciseMessage) {
        this.ciseMessage = ciseMessage;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ZonedDateTime getDateTimeProcessed() {
        return dateTimeProcessed;
    }

    public void setDateTimeProcessed(ZonedDateTime dateReceived) {
        this.dateTimeProcessed = dateReceived;
    }

    public MessageProcessAction getMessageProcessAction() {
        return this.messageProcessAction;
    }

    public void setMessageProcessAction(MessageProcessAction processAction) {
        this.messageProcessAction = processAction;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }
}
