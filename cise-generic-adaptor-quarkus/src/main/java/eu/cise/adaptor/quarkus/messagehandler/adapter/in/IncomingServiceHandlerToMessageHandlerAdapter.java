package eu.cise.adaptor.quarkus.messagehandler.adapter.in;

import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.common.logging.LogConfig;
import eu.cise.adaptor.core.messagehandler.port.in.IncomingServiceHandlerToMessageHandlerPort;
import eu.cise.adaptor.core.messagehandler.service.MessageHandlerDeliveryService;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.helpers.AckBuilder;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

import static eu.cise.adaptor.core.common.logging.LoggerMessage.of;
import static eu.cise.adaptor.core.common.logging.MessageRouteValue.LS_TO_CISE;
import static eu.cise.adaptor.core.common.message.MessageTypeEnum.UNDEFINED;

@ApplicationScoped
public class IncomingServiceHandlerToMessageHandlerAdapter implements IncomingServiceHandlerToMessageHandlerPort {

    private static final AdaptorLogger logger = LogConfig.configureLogging(IncomingServiceHandlerToMessageHandlerAdapter.class);

    private final MessageHandlerDeliveryService messageHandlerDeliveryService;

    public IncomingServiceHandlerToMessageHandlerAdapter(MessageHandlerDeliveryService messageHandlerDeliveryService) {
        this.messageHandlerDeliveryService = messageHandlerDeliveryService;
    }

    @Override
    public Acknowledgement sendMessage(Message message) {
        return messageHandlerDeliveryService.prepareAndForwardMessageToCiseNode(message);
    }

    @ConsumeEvent("incoming-from-legacy-system")
    @Blocking //TODO: maybe we should try to reduce the scope of the blocking execution
    public Uni<Acknowledgement> incomingMessageHandler(Message message) {
        logger.info(of(UNDEFINED, message.getMessageID(), "incoming-from-legacy-system consumed").addRoutingAttribute(LS_TO_CISE));

        return Uni.createFrom().item(() -> sendMessage(message))
                .onFailure().recoverWithItem(throwable -> {
                    logger.error(of(UNDEFINED, message.getMessageID(), "incoming-from-legacy-system exception").addRoutingAttribute(LS_TO_CISE), throwable);
                    return AckBuilder.newAck().build();
                });

    }
}
