package eu.cise.adaptor.quarkus.servicehandler.adapter.out;

import eu.cise.adaptor.core.servicehandler.port.out.OutgoingServiceHandlerToMessageHandlerPort;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import io.vertx.mutiny.core.eventbus.EventBus;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * The adapter for the {@link OutgoingServiceHandlerToMessageHandlerPort}. This implementation is responsible for forwarding the high-level domain calls with CISE Message
 * from the Service Handler into the Message Handler. It acts as a bridge in the communication process, enabling the effective sending of CISE messages to the Node.
 *
 * @see OutgoingServiceHandlerToMessageHandlerPort
 */
@ApplicationScoped
public class OutgoingServiceHandlerToMessageHandlerAdapter implements OutgoingServiceHandlerToMessageHandlerPort {

    @Inject
    EventBus eventBus;

    @Override
    public Acknowledgement forwardMessageToMessageHandler(Message message) {
        return eventBus.<Acknowledgement>request("incoming-from-legacy-system", message)
                .onItem()
                .transform(acknowledgmentBody -> acknowledgmentBody.body()).await().indefinitely(); // TODO should we await here indefinitely?
    }
}
