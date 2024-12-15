package eu.cise.adaptor.core.adapters;


import eu.cise.adaptor.core.messagehandler.port.in.IncomingNodeToMessageHandlerPort;
import eu.cise.adaptor.core.servicehandler.port.in.ReceiveFromLegacySystemPort;

/**
 * This class is used for testing the core business logic of the Generic Adaptor. It is an aggregate class that performs the instrumentation of the
 * Ports (and default adapters) with the services of the Generic Adaptor hexagons
 */
public class DomainContext {

    private final IncomingNodeToMessageHandlerPort incomingNodeToMessageHandlerPort;
    private final ReceiveFromLegacySystemPort riskPullProviderReceiveFromLegacySystemPort;
    private final ReceiveFromLegacySystemPort vesselPullProviderReceiveFromLegacySystemPort;

    private final ReceiveFromLegacySystemPort vesselPullConsumerReceiveFromLegacySystemPort;

    private final ReceiveFromLegacySystemPort riskPushProviderReceiveFromLegacySystemPort;

    private final ReceiveFromLegacySystemPort vesselPushProviderReceiveFromLegacySystemPort;

    private final ReceiveFromLegacySystemPort vesselSubscribeProviderReceiveFromLegacySystemPort;

    private final ReceiveFromLegacySystemPort riskSubscribeConsumerReceiveFromLegacySystemPort;

    private final ReceiveFromLegacySystemPort vesselSubscribeConsumerReceiveFromLegacySystemPort;

    public DomainContext(IncomingNodeToMessageHandlerPort incomingNodeToMessageHandlerPort,
                         ReceiveFromLegacySystemPort riskPullProviderReceiveFromLegacySystemPort,
                         ReceiveFromLegacySystemPort vesselPullProviderReceiveFromLegacySystemPort,
                         ReceiveFromLegacySystemPort vesselPullConsumerReceiveFromLegacySystemPort,
                         ReceiveFromLegacySystemPort riskPushProviderReceiveFromLegacySystemPort,
                         ReceiveFromLegacySystemPort vesselPushProviderReceiveFromLegacySystemPort,
                         ReceiveFromLegacySystemPort vesselSubscribeProviderReceiveFromLegacySystemPort,
                         ReceiveFromLegacySystemPort riskSubscribeConsumerReceiveFromLegacySystemPort,
                         ReceiveFromLegacySystemPort vesselSubscribeConsumerReceiveFromLegacySystemPort
    ) {
        this.incomingNodeToMessageHandlerPort = incomingNodeToMessageHandlerPort;
        this.riskPullProviderReceiveFromLegacySystemPort = riskPullProviderReceiveFromLegacySystemPort;
        this.vesselPullProviderReceiveFromLegacySystemPort = vesselPullProviderReceiveFromLegacySystemPort;
        this.vesselPullConsumerReceiveFromLegacySystemPort = vesselPullConsumerReceiveFromLegacySystemPort;
        this.riskPushProviderReceiveFromLegacySystemPort = riskPushProviderReceiveFromLegacySystemPort;
        this.vesselPushProviderReceiveFromLegacySystemPort = vesselPushProviderReceiveFromLegacySystemPort;
        this.vesselSubscribeProviderReceiveFromLegacySystemPort = vesselSubscribeProviderReceiveFromLegacySystemPort;
        this.riskSubscribeConsumerReceiveFromLegacySystemPort = riskSubscribeConsumerReceiveFromLegacySystemPort;
        this.vesselSubscribeConsumerReceiveFromLegacySystemPort = vesselSubscribeConsumerReceiveFromLegacySystemPort;
    }

    public IncomingNodeToMessageHandlerPort getIncomingNodeToMessageHandlerPort() {
        return incomingNodeToMessageHandlerPort;
    }

    public ReceiveFromLegacySystemPort getRiskPullProviderReceiveFromLegacySystemPort() {
        return riskPullProviderReceiveFromLegacySystemPort;
    }

    public ReceiveFromLegacySystemPort getVesselPullProviderReceiveFromLegacySystemPort() {
        return vesselPullProviderReceiveFromLegacySystemPort;
    }

    public ReceiveFromLegacySystemPort getVesselPullConsumerReceiveFromLegacySystemPort() {
        return vesselPullConsumerReceiveFromLegacySystemPort;
    }

    public ReceiveFromLegacySystemPort getRiskPushProviderReceiveFromLegacySystemPort() {
        return riskPushProviderReceiveFromLegacySystemPort;
    }

    public ReceiveFromLegacySystemPort getVesselPushProviderReceiveFromLegacySystemPort() {
        return vesselPushProviderReceiveFromLegacySystemPort;
    }

    public ReceiveFromLegacySystemPort getVesselSubscribeProviderReceiveFromLegacySystemPort() {
        return vesselSubscribeProviderReceiveFromLegacySystemPort;
    }

    public ReceiveFromLegacySystemPort getRiskSubscribeConsumerReceiveFromLegacySystemPort() {
        return riskSubscribeConsumerReceiveFromLegacySystemPort;
    }

    public ReceiveFromLegacySystemPort getVesselSubscribeConsumerReceiveFromLegacySystemPort() {
        return vesselSubscribeConsumerReceiveFromLegacySystemPort;
    }

}