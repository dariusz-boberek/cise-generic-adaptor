package eu.cise.adaptor.core.adapters.servicehandler.out.stub;

import eu.cise.adaptor.core.servicehandler.domain.RegisteredMessage;
import eu.cise.adaptor.core.servicehandler.port.out.SendToLegacySystemPort;
import eu.cise.adaptor.core.servicehandler.port.out.UpdateLegacySystemResult;

import java.util.List;

/**
 * Stub implementation of the {@code SendToLegacySystemPort} used in functional tests.
 *
 * <p>This adapter stub provides a basic framework for simulating the sending of messages to legacy systems
 * in a testing environment. It acts as a placeholder for testing the Generic Adaptor's capabilities
 * in communicating with various legacy systems.
 *
 * @see SendToLegacySystemPort
 */
public class SendToLegacySystemAdapterStub implements SendToLegacySystemPort {

    @Override
    public UpdateLegacySystemResult updateLegacySystem(RegisteredMessage newRegisteredMessage, String messageId, List<RegisteredMessage> messagesHistory, String contextId) {
        return new UpdateLegacySystemResult("OK");
    }

}
