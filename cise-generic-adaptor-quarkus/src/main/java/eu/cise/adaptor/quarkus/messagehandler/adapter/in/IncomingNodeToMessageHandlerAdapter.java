package eu.cise.adaptor.quarkus.messagehandler.adapter.in;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorRuntimeException;
import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.messagehandler.port.in.IncomingNodeToMessageHandlerPort;
import eu.cise.adaptor.core.messagehandler.port.out.OutgoingMessageHandlerToServiceHandlerPort;
import eu.cise.adaptor.core.messagehandler.service.MessageHandlerVerificationService;
import eu.cise.adaptor.core.servicehandler.port.out.SendToLegacySystemPort;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static eu.cise.adaptor.core.common.logging.LogConfig.configureLogging;
import static eu.cise.adaptor.core.common.logging.LoggerMessage.of;


/**
 * Implementation of the IncomingNodeToMessageHandlerPort, responsible for actual handling of
 * incoming messages from the CISE Node.
 * <p>
 * Responsibilities:
 * <ul>
 * <li>Validates incoming messages for structure, signature, and applicability against current CISE services.</li>
 * <li>Constructs the Synchronous Acknowledgment (SyncAck) for the Node’s request.</li>
 * <li>Passes the validated message asynchronously to the OutgoingMessageHandlerToServiceHandlerPort</li>
 * </ul>
 * for further processing.
 *
 * @see IncomingNodeToMessageHandlerPort
 * @see MessageHandlerVerificationService
 * @see OutgoingMessageHandlerToServiceHandlerPort
 */
@ApplicationScoped
public class IncomingNodeToMessageHandlerAdapter implements IncomingNodeToMessageHandlerPort {

    private static final AdaptorLogger log = configureLogging(IncomingNodeToMessageHandlerAdapter.class);

    private final MessageHandlerVerificationService messageHandlerVerificationService;
    private final Set<String> availableServiceIds;


    public IncomingNodeToMessageHandlerAdapter(MessageHandlerVerificationService messageHandlerVerificationService, Map<String, SendToLegacySystemPort> sendToLegacySystemPortMap) {
        this.messageHandlerVerificationService = messageHandlerVerificationService;
        availableServiceIds = Collections.unmodifiableSet(sendToLegacySystemPortMap.keySet());
        if (availableServiceIds == null || availableServiceIds.isEmpty()) {
            log.error(of("serviceId registry for checking recipient of incoming message is null or empty."));
        }
    }

    @Override
    public Acknowledgement requestSyncAck(String xmlMessage) {
        return messageHandlerVerificationService.requestSyncAck(xmlMessage, availableServiceIds);
    }

    @ConsumeEvent("incoming-message")
    public Uni<Acknowledgement> incomingMessageHandler(String xmlMessage) {
        log.info(of("incoming-message consumed"));
        try {
            return Uni.createFrom().item(requestSyncAck(xmlMessage));
        } catch (Exception exception) {
            log.error(of("incoming-message exception: {}", exception.getMessage(), exception));
            throw new CiseAdaptorRuntimeException("FATAL: Error processing incoming message: " + xmlMessage, AcknowledgementType.SERVER_ERROR, exception);
        }
    }

}
