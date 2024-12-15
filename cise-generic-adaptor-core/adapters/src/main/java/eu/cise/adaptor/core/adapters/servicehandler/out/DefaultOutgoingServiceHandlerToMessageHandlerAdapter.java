package eu.cise.adaptor.core.adapters.servicehandler.out;

import eu.cise.adaptor.core.messagehandler.port.in.IncomingServiceHandlerToMessageHandlerPort;
import eu.cise.adaptor.core.servicehandler.port.out.OutgoingServiceHandlerToMessageHandlerPort;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

/**
 * Default adapter implementing the {@code OutgoingServiceHandlerToMessageHandlerPort} used in Domain Testing.
 *
 * <p>This adapter is responsible for facilitating the outgoing communication from the Service Handler to the Message Handler
 * in the Generic Adaptor. It plays a pivotal role in ensuring that messages originating from the Legacy System,
 * processed through the plugin and then the Service Handler, are correctly forwarded to the Message Handler
 * for subsequent dispatch to the CISE Node.
 *
 * @see OutgoingServiceHandlerToMessageHandlerPort
 */
public class DefaultOutgoingServiceHandlerToMessageHandlerAdapter implements OutgoingServiceHandlerToMessageHandlerPort {


    //INFO: Such property would not exist in microservice context/implementation, instead for example message would be put on some Queue (so event emitter impl would be here).
    private final IncomingServiceHandlerToMessageHandlerPort incomingServiceHandlerToMessageHandlerPort;

    public DefaultOutgoingServiceHandlerToMessageHandlerAdapter(IncomingServiceHandlerToMessageHandlerPort incomingServiceHandlerToMessageHandlerPort) {
        this.incomingServiceHandlerToMessageHandlerPort = incomingServiceHandlerToMessageHandlerPort;
    }

    @Override
    public Acknowledgement forwardMessageToMessageHandler(Message message) {
        return incomingServiceHandlerToMessageHandlerPort.sendMessage(message);
    }

}
