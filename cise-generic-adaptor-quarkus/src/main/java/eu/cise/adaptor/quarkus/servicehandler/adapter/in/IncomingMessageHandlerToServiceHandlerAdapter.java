package eu.cise.adaptor.quarkus.servicehandler.adapter.in;

import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.common.logging.LogConfig;
import eu.cise.adaptor.core.servicehandler.port.in.IncomingMessageHandlerToServiceHandlerPort;
import eu.cise.adaptor.core.servicehandler.service.ServiceHandlerRoutingService;
import eu.cise.servicemodel.v1.message.Message;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;

import javax.enterprise.context.ApplicationScoped;

import static eu.cise.adaptor.core.common.logging.LoggerMessage.of;
import static eu.cise.adaptor.core.common.logging.MessageRouteValue.CISE_TO_LS;
import static eu.cise.adaptor.core.common.message.MessageTypeEnum.UNDEFINED;

@ApplicationScoped
public class IncomingMessageHandlerToServiceHandlerAdapter implements IncomingMessageHandlerToServiceHandlerPort {

    private static final AdaptorLogger logger = LogConfig.configureLogging(IncomingMessageHandlerToServiceHandlerAdapter.class);

    private final ServiceHandlerRoutingService serviceHandlerRoutingService;

    public IncomingMessageHandlerToServiceHandlerAdapter(ServiceHandlerRoutingService serviceHandlerRoutingService) {
        this.serviceHandlerRoutingService = serviceHandlerRoutingService;
    }

    @Override
    public void handleMessage(Message message) {
        serviceHandlerRoutingService.handleMessage(message);
    }

    @ConsumeEvent("processed-message")
    @Blocking
    public void processedMessagesHandler(Message message) {
        logger.info(of(UNDEFINED, message.getMessageID(), "processed-message consumed").addRoutingAttribute(CISE_TO_LS));
        try {
            handleMessage(message);
        } catch (RuntimeException exception) {
            logger.error(of(UNDEFINED, message.getMessageID(), "Error processing incoming message").addRoutingAttribute(CISE_TO_LS), exception);
        }
    }

}