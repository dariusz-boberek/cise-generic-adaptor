package eu.cise.adaptor.quarkus.messagehandler.adapter.out;

import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.messagehandler.port.out.OutgoingMessageHandlerToNodePort;
import eu.cise.adaptor.quarkus.dispatcher.AdaptorDispatcher;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;

import javax.enterprise.context.ApplicationScoped;

import static eu.cise.adaptor.core.common.logging.LogConfig.configureLogging;
import static eu.cise.adaptor.core.common.logging.LoggerMessage.of;
import static eu.cise.adaptor.core.common.logging.MessageRouteValue.LS_TO_CISE;
import static eu.cise.adaptor.core.common.message.MessageTypeEnum.UNDEFINED;

/**
 * The {@code OutgoingMessageHandlerToNodeAdapter} class implements the {@code OutgoingMessageHandlerToNodePort} interface,
 * providing the functionality to handle outgoing messages to a CISE Node.
 *
 * <p> This adapter is responsible for direct communication with the CISE Dispatcher, sending messages,
 * and capturing the results of the communication. It also manages synchronous acknowledgments
 * (SyncAcks) received from the Node and forwards them to the Service Handler.
 *
 * <p> Collaborates with the {@code MessageHandlerDeliveryService} for message preparation, which includes updating
 * the creation date and signing the message before dispatch.
 *
 * @see OutgoingMessageHandlerToNodePort
 */
@ApplicationScoped
public class OutgoingMessageHandlerToNodeAdapter implements OutgoingMessageHandlerToNodePort {

    private static final AdaptorLogger logger = configureLogging(OutgoingMessageHandlerToNodeAdapter.class);
    private static XmlMapper xmlMapper = new DefaultXmlMapper.PrettyNotValidating();


    private final AdaptorDispatcher adaptorDispatcher;

    public OutgoingMessageHandlerToNodeAdapter(AdaptorDispatcher adaptorDispatcher) {
        this.adaptorDispatcher = adaptorDispatcher;
    }

    @Override
    public Acknowledgement sendStringMessage(Message signedCiseMessage) {
        // TODO: we should use vertx here and retry sending the message and check for errors.
        // return Uni.createFrom().item(signedCiseMessage).onItem().invoke(() -> )
        if (logger.isDebugEnabled()) {
            logger.debug(of(UNDEFINED, signedCiseMessage.getMessageID(), "Sending signed Message to the Node:\n" + xmlMapper.toXML(signedCiseMessage)).addRoutingAttribute(LS_TO_CISE));
        }

        Acknowledgement ackFromNode = adaptorDispatcher.send(signedCiseMessage);
        if (logger.isDebugEnabled()) {
            logger.debug(of(UNDEFINED, signedCiseMessage.getMessageID(), "Received Ack from the Node: \n " + xmlMapper.toXML(ackFromNode)).addRoutingAttribute(LS_TO_CISE));
        }

        return ackFromNode;
    }
}
