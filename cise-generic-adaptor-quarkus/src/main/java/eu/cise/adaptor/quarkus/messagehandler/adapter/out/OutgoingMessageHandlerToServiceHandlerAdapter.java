package eu.cise.adaptor.quarkus.messagehandler.adapter.out;

import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.common.logging.LogConfig;
import eu.cise.adaptor.core.messagehandler.port.out.OutgoingMessageHandlerToServiceHandlerPort;
import eu.cise.servicemodel.v1.message.Message;
import io.vertx.mutiny.core.eventbus.EventBus;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static eu.cise.adaptor.core.common.logging.LoggerMessage.of;
import static eu.cise.adaptor.core.common.logging.MessageRouteValue.CISE_TO_LS;
import static eu.cise.adaptor.core.common.message.MessageTypeEnum.UNDEFINED;

/**
 * Implementation of the OutgoingMessageHandlerToServiceHandlerPort. It effectively translates and forwards the messages received from the
 * Message Handler to the Service Handler for further processing and routing to the appropriate plugins or legacy systems.
 *
 * @see OutgoingMessageHandlerToServiceHandlerPort
 */
@ApplicationScoped
public class OutgoingMessageHandlerToServiceHandlerAdapter implements OutgoingMessageHandlerToServiceHandlerPort {

    private static final AdaptorLogger logger = LogConfig.configureLogging(OutgoingMessageHandlerToServiceHandlerAdapter.class);
    @Inject
    EventBus bus;

    @Override
    public void handleMessage(Message message) {
        logger.info(of(UNDEFINED, message.getMessageID(), "publishing to processed-message").addRoutingAttribute(CISE_TO_LS));
        bus.<Message>publish("processed-message", message);
    }
}
