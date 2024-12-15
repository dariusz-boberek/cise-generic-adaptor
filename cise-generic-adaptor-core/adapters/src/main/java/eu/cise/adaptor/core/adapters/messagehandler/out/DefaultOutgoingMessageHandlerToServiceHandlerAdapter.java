package eu.cise.adaptor.core.adapters.messagehandler.out;

import eu.cise.adaptor.core.messagehandler.port.out.OutgoingMessageHandlerToServiceHandlerPort;
import eu.cise.adaptor.core.servicehandler.port.in.IncomingMessageHandlerToServiceHandlerPort;
import eu.cise.servicemodel.v1.message.Message;

/**
 * Default adapter implementing the {@code OutgoingMessageHandlerToServiceHandlerPort} used for Domain Testing.
 *
 * <p>This adapter manages the transfer of messages from the Message Handler to the Service Handler
 * within the Generic Adaptor. It serves as a conduit, ensuring that messages processed by the Message Handler
 * are correctly routed to the Service Handler for further processing and eventual dispatch to the appropriate plugins
 * or legacy systems.
 *
 * @see OutgoingMessageHandlerToServiceHandlerPort
 */
public class DefaultOutgoingMessageHandlerToServiceHandlerAdapter implements OutgoingMessageHandlerToServiceHandlerPort {

    //INFO: Such property would not exist in microservice context/implementation, instead for example message would be put on some Queue (so event emitter impl would be here).
    private final IncomingMessageHandlerToServiceHandlerPort incomingMessageHandlerToServiceHandlerPort;

    public DefaultOutgoingMessageHandlerToServiceHandlerAdapter(IncomingMessageHandlerToServiceHandlerPort incomingMessageHandlerToServiceHandlerPort) {
        this.incomingMessageHandlerToServiceHandlerPort = incomingMessageHandlerToServiceHandlerPort;
    }

    @Override
    public void handleMessage(Message message) {
        incomingMessageHandlerToServiceHandlerPort.handleMessage(message);
    }
}