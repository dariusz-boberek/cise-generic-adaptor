package eu.cise.adaptor.core;


import eu.cise.adaptor.core.adapters.DomainContext;
import eu.cise.adaptor.core.adapters.messagehandler.in.DefaultIncomingNodeToMessageHandlerAdapter;
import eu.cise.adaptor.core.adapters.messagehandler.in.DefaultIncomingServiceHandlerToMessageHandlerAdapter;
import eu.cise.adaptor.core.adapters.messagehandler.out.DefaultOutgoingMessageHandlerToNodeAdapter;
import eu.cise.adaptor.core.adapters.messagehandler.out.DefaultOutgoingMessageHandlerToServiceHandlerAdapter;
import eu.cise.adaptor.core.adapters.servicehandler.in.DefaultIncomingMessageHandlerToServiceHandlerAdapter;
import eu.cise.adaptor.core.adapters.servicehandler.in.stub.ReceiveFromLegacySystemAdapterStub;
import eu.cise.adaptor.core.adapters.servicehandler.out.DefaultOutgoingServiceHandlerToMessageHandlerAdapter;
import eu.cise.adaptor.core.adapters.servicehandler.out.stub.SendToLegacySystemAdapterStub;
import eu.cise.adaptor.core.messagehandler.port.in.IncomingNodeToMessageHandlerPort;
import eu.cise.adaptor.core.messagehandler.port.in.IncomingServiceHandlerToMessageHandlerPort;
import eu.cise.adaptor.core.messagehandler.port.out.OutgoingMessageHandlerToNodePort;
import eu.cise.adaptor.core.messagehandler.port.out.OutgoingMessageHandlerToServiceHandlerPort;
import eu.cise.adaptor.core.messagehandler.service.DefaultMessageHandlerDeliveryService;
import eu.cise.adaptor.core.messagehandler.service.DefaultMessageHandlerVerificationService;
import eu.cise.adaptor.core.messagehandler.service.MessageHandlerDeliveryService;
import eu.cise.adaptor.core.servicehandler.port.in.IncomingMessageHandlerToServiceHandlerPort;
import eu.cise.adaptor.core.servicehandler.port.in.ReceiveFromLegacySystemPort;
import eu.cise.adaptor.core.servicehandler.port.out.MessageRepositoryPort;
import eu.cise.adaptor.core.servicehandler.port.out.OutgoingServiceHandlerToMessageHandlerPort;
import eu.cise.adaptor.core.servicehandler.port.out.SendToLegacySystemPort;
import eu.cise.adaptor.core.servicehandler.service.DefaultServiceHandlerMessageBuilderService;
import eu.cise.adaptor.core.servicehandler.service.DefaultServiceHandlerRoutingService;
import eu.cise.adaptor.core.servicehandler.service.ServiceHandlerMessageBuilderService;
import eu.cise.adaptor.core.servicehandler.service.ServiceHandlerRoutingService;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceRoleType;
import eu.cise.servicemodel.v1.service.ServiceType;
import eu.cise.signature.SignatureService;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FunctionalAppContext {


    protected static FunctionalAppContext appContext;
    private final DomainContext domainCtx;

    FunctionalAppContext(IncomingNodeToMessageHandlerPort incomingNodeToMessageHandlerPort,
                         ReceiveFromLegacySystemPort riskPullProviderReceiveFromLegacySystemPort,
                         ReceiveFromLegacySystemPort vesselPullProviderReceiveFromLegacySystemPort,
                         ReceiveFromLegacySystemPort vesselPullConsumerReceiveFromLegacySystemPort,
                         ReceiveFromLegacySystemPort riskPushProviderReceiveFromLegacySystemPort,
                         ReceiveFromLegacySystemPort vesselPushProviderReceiveFromLegacySystemPort,
                         ReceiveFromLegacySystemPort vesselSubscribeProviderReceiveFromLegacySystemPort,
                         ReceiveFromLegacySystemPort riskSubscribeConsumerReceiveFromLegacySystemPort,
                         ReceiveFromLegacySystemPort vesselSubscribeConsumerReceiveFromLegacySystemPort
    ) {
        this.domainCtx = new DomainContext(incomingNodeToMessageHandlerPort,
                riskPullProviderReceiveFromLegacySystemPort,
                vesselPullProviderReceiveFromLegacySystemPort,
                vesselPullConsumerReceiveFromLegacySystemPort,
                riskPushProviderReceiveFromLegacySystemPort,
                vesselPushProviderReceiveFromLegacySystemPort,
                vesselSubscribeProviderReceiveFromLegacySystemPort,
                riskSubscribeConsumerReceiveFromLegacySystemPort,
                vesselSubscribeConsumerReceiveFromLegacySystemPort
        );
    }

    public static AppContextBuilder builder() {
        return new AppContextBuilder();
    }

    public static FunctionalAppContext createDefaultAppContext() throws IOException {
        if (appContext != null) {
            return appContext;
        }
        return FunctionalAppContext.builder().buildWithFallbackToDefault();
    }

    public DomainContext getDomainCtx() {
        return domainCtx;
    }

    public static class AppContextBuilder {
        private ServiceHandlerRoutingService serviceHandlerRoutingService;
        private IncomingMessageHandlerToServiceHandlerPort incomingMessageHandlerToServiceHandlerPort;
        private OutgoingMessageHandlerToServiceHandlerPort outgoingMessageHandlerToServiceHandlerPort;
        private IncomingNodeToMessageHandlerPort incomingNodeToMessageHandlerPort;

        private OutgoingMessageHandlerToNodePort outgoingMessageHandlerToNodePort;

        private IncomingServiceHandlerToMessageHandlerPort incomingServiceHandlerToMessageHandlerPort;

        private OutgoingServiceHandlerToMessageHandlerPort outgoingServiceHandlerToMessageHandlerPort;

        private ReceiveFromLegacySystemPort riskPullProviderReceiveFromLegacySystemPort;

        private ReceiveFromLegacySystemPort vesselPullProviderReceiveFromLegacySystemPort;

        private ReceiveFromLegacySystemPort vesselPullConsumerReceiveFromLegacySystemPort;

        private ReceiveFromLegacySystemPort riskPushProviderReceiveFromLegacySystemPort;

        private ReceiveFromLegacySystemPort vesselPushProviderReceiveFromLegacySystemPort;

        private ReceiveFromLegacySystemPort vesselSubscribeProviderReceiveFromLegacySystemPort;


        private ReceiveFromLegacySystemPort riskSubscribeConsumerReceiveFromLegacySystemPort;

        private ReceiveFromLegacySystemPort vesselSubscribeConsumerReceiveFromLegacySystemPort;

        private SignatureService signatureService;

        private ServiceHandlerMessageBuilderService serviceHandlerMessageBuilderService;

        private MessageHandlerDeliveryService messageHandlerDeliveryService;

        private MessageRepositoryPort messageRepositoryPort;

        private Map<String, SendToLegacySystemPort> sendToLegacySystemPortMap;

        private Service riskPullProviderService;

        private Service vesselPullProviderService;

        private Service vesselPullConsumerService;

        private Service riskPushProviderService;

        private Service vesselPushProviderService;

        private Service vesselSubscribeProviderService;

        private Service vesselSubscribeConsumerService;
        private Service riskSubscribeConsumerService;

        private AppContextBuilder() {
        }

        public AppContextBuilder setMessageRepositoryPort(MessageRepositoryPort messageRepositoryPort) {
            this.messageRepositoryPort = messageRepositoryPort;
            return this;
        }

        public AppContextBuilder setSendToLegacySystemPorts(Map<String, SendToLegacySystemPort> outgoingPorts) {
            this.sendToLegacySystemPortMap = outgoingPorts;
            return this;
        }

        public AppContextBuilder setServiceHandlerRoutingService(ServiceHandlerRoutingService serviceHandlerRoutingService) {
            this.serviceHandlerRoutingService = serviceHandlerRoutingService;
            return this;
        }

        public AppContextBuilder setLegacyServiceHandlerRoutingService(ServiceHandlerMessageBuilderService serviceHandlerMessageBuilderService) {
            this.serviceHandlerMessageBuilderService = serviceHandlerMessageBuilderService;
            return this;
        }

        public AppContextBuilder setIncomingMessageHandlerToServiceHandlerPort(IncomingMessageHandlerToServiceHandlerPort incomingMessageHandlerToServiceHandlerPort) {
            this.incomingMessageHandlerToServiceHandlerPort = incomingMessageHandlerToServiceHandlerPort;
            return this;
        }

        public AppContextBuilder setOutgoingMessageHandlerToServiceHandlerPort(OutgoingMessageHandlerToServiceHandlerPort outgoingMessageHandlerToServiceHandlerPort) {
            this.outgoingMessageHandlerToServiceHandlerPort = outgoingMessageHandlerToServiceHandlerPort;
            return this;
        }

        public AppContextBuilder setIncomingNodeToMessageHandlerPort(IncomingNodeToMessageHandlerPort incomingNodeToMessageHandlerPort) {
            this.incomingNodeToMessageHandlerPort = incomingNodeToMessageHandlerPort;
            return this;
        }

        public AppContextBuilder setRiskPullProviderReceiveFromLegacySystemPort(ReceiveFromLegacySystemPort riskPullProviderReceiveFromLegacySystemPort) {
            this.riskPullProviderReceiveFromLegacySystemPort = riskPullProviderReceiveFromLegacySystemPort;
            return this;
        }

        public AppContextBuilder setRiskPushProviderReceiveFromLegacySystemPort(ReceiveFromLegacySystemPort riskPushProviderReceiveFromLegacySystemPort) {
            this.riskPushProviderReceiveFromLegacySystemPort = riskPushProviderReceiveFromLegacySystemPort;
            return this;
        }

        public AppContextBuilder setVesselPullProviderReceiveFromLegacySystemPort(ReceiveFromLegacySystemPort vesselPullProviderReceiveFromLegacySystemPort) {
            this.vesselPullProviderReceiveFromLegacySystemPort = vesselPullProviderReceiveFromLegacySystemPort;
            return this;
        }

        public AppContextBuilder setVesselPullConsumerReceiveFromLegacySystemPort(ReceiveFromLegacySystemPort vesselPullConsumerReceiveFromLegacySystemPort) {
            this.vesselPullConsumerReceiveFromLegacySystemPort = vesselPullConsumerReceiveFromLegacySystemPort;
            return this;
        }

        public AppContextBuilder setVesselPushProviderReceiveFromLegacySystemPort(ReceiveFromLegacySystemPort vesselPushProviderReceiveFromLegacySystemPort) {
            this.vesselPushProviderReceiveFromLegacySystemPort = vesselPushProviderReceiveFromLegacySystemPort;
            return this;
        }

        public AppContextBuilder setVesselSubscribeProviderReceiveFromLegacySystemPort(ReceiveFromLegacySystemPort vesselSubscribeProviderReceiveFromLegacySystemPort) {
            this.vesselSubscribeProviderReceiveFromLegacySystemPort = vesselSubscribeProviderReceiveFromLegacySystemPort;
            return this;
        }

        public AppContextBuilder setVesselSubscribeConsumerReceiveFromLegacySystemPort(ReceiveFromLegacySystemPort vesselSubscribeConsumerReceiveFromLegacySystemPort) {
            this.vesselSubscribeConsumerReceiveFromLegacySystemPort = vesselSubscribeConsumerReceiveFromLegacySystemPort;
            return this;
        }

        public AppContextBuilder setRiskSubscribeConsumerReceiveFromLegacySystemPort(ReceiveFromLegacySystemPort riskSubscribeConsumerReceiveFromLegacySystemPort) {
            this.riskSubscribeConsumerReceiveFromLegacySystemPort = riskSubscribeConsumerReceiveFromLegacySystemPort;
            return this;
        }

        public AppContextBuilder setOutgoingServiceHandlerToMessageHandlerPort(OutgoingServiceHandlerToMessageHandlerPort outgoingServiceHandlerToMessageHandlerPort) {
            this.outgoingServiceHandlerToMessageHandlerPort = outgoingServiceHandlerToMessageHandlerPort;
            return this;
        }

        public AppContextBuilder setIncomingServiceHandlerToMessageHandlerPort(IncomingServiceHandlerToMessageHandlerPort incomingServiceHandlerToMessageHandlerPort) {
            this.incomingServiceHandlerToMessageHandlerPort = incomingServiceHandlerToMessageHandlerPort;
            return this;
        }

        public AppContextBuilder setOutgoingMessageHandlerToNodePort(OutgoingMessageHandlerToNodePort outgoingMessageHandlerToNodePort) {
            this.outgoingMessageHandlerToNodePort = outgoingMessageHandlerToNodePort;
            return this;
        }

        public AppContextBuilder setSignatureService(SignatureService signatureService) {
            this.signatureService = signatureService;
            return this;
        }

        public AppContextBuilder setMessageHandlerDeliveryService(MessageHandlerDeliveryService messageHandlerDeliveryService) {
            this.messageHandlerDeliveryService = messageHandlerDeliveryService;
            return this;
        }

        public AppContextBuilder setRiskPullProviderService(Service riskPullProviderService) {
            this.riskPullProviderService = riskPullProviderService;
            return this;
        }

        public AppContextBuilder setVesselPullProviderService(Service vesselPullProviderService) {
            this.vesselPullProviderService = vesselPullProviderService;
            return this;
        }

        public AppContextBuilder setVesselPullConsumerService(Service vesselPullConsumerService) {
            this.vesselPullConsumerService = vesselPullConsumerService;
            return this;
        }

        public AppContextBuilder setRiskPushProviderService(Service riskPushProviderService) {
            this.riskPushProviderService = riskPushProviderService;
            return this;
        }

        public AppContextBuilder setVesselPushProviderService(Service vesselPushProviderService) {
            this.vesselPushProviderService = vesselPushProviderService;
            return this;
        }

        public AppContextBuilder setVesselSubscribeProviderService(Service vesselSubscribeProviderService) {
            this.vesselSubscribeProviderService = vesselSubscribeProviderService;
            return this;
        }

        public AppContextBuilder setRiskSubscribeConsumerService(Service riskSubscribeConsumerService) {
            this.riskSubscribeConsumerService = riskSubscribeConsumerService;
            return this;
        }

        public AppContextBuilder setVesselSubscribeConsumerService(Service vesselSubscribeConsumerService) {
            this.vesselSubscribeConsumerService = vesselSubscribeConsumerService;
            return this;
        }

        public FunctionalAppContext buildWithFallbackToDefault() {

            if (sendToLegacySystemPortMap == null) {
                sendToLegacySystemPortMap = new HashMap<>();
            }
            if (sendToLegacySystemPortMap.isEmpty()) {
                sendToLegacySystemPortMap.put("stubServiceId", new SendToLegacySystemAdapterStub());
            }
            if (serviceHandlerRoutingService == null) {
                serviceHandlerRoutingService = new DefaultServiceHandlerRoutingService(sendToLegacySystemPortMap, messageRepositoryPort);
            }
            if (incomingMessageHandlerToServiceHandlerPort == null) {
                incomingMessageHandlerToServiceHandlerPort = new DefaultIncomingMessageHandlerToServiceHandlerAdapter(serviceHandlerRoutingService);
            }
            if (outgoingMessageHandlerToServiceHandlerPort == null) {
                outgoingMessageHandlerToServiceHandlerPort = new DefaultOutgoingMessageHandlerToServiceHandlerAdapter(incomingMessageHandlerToServiceHandlerPort);
            }
            if (outgoingMessageHandlerToNodePort == null) {
                outgoingMessageHandlerToNodePort = new DefaultOutgoingMessageHandlerToNodeAdapter();
            }
            if (messageHandlerDeliveryService == null) {
                messageHandlerDeliveryService = new DefaultMessageHandlerDeliveryService(outgoingMessageHandlerToNodePort, signatureService);
            }
            if (incomingServiceHandlerToMessageHandlerPort == null) {
                incomingServiceHandlerToMessageHandlerPort = new DefaultIncomingServiceHandlerToMessageHandlerAdapter(messageHandlerDeliveryService);
            }
            if (outgoingServiceHandlerToMessageHandlerPort == null) {
                outgoingServiceHandlerToMessageHandlerPort = new DefaultOutgoingServiceHandlerToMessageHandlerAdapter(incomingServiceHandlerToMessageHandlerPort);
            }
            if (serviceHandlerMessageBuilderService == null) {

                serviceHandlerMessageBuilderService = new DefaultServiceHandlerMessageBuilderService(outgoingServiceHandlerToMessageHandlerPort, messageRepositoryPort);
            }
            if (riskPullProviderReceiveFromLegacySystemPort == null) {
                riskPullProviderService = createServiceIfNotPresent(riskPullProviderService, "someServiceId", ServiceOperationType.PULL, ServiceRoleType.PROVIDER, ServiceType.RISK_SERVICE);
                riskPullProviderReceiveFromLegacySystemPort = new ReceiveFromLegacySystemAdapterStub(serviceHandlerMessageBuilderService, riskPullProviderService);
            }
            if (vesselPullProviderReceiveFromLegacySystemPort == null) {
                vesselPullProviderService = createServiceIfNotPresent(vesselPullProviderService, "someAlternativeServiceId", ServiceOperationType.PULL, ServiceRoleType.PROVIDER, ServiceType.VESSEL_SERVICE);
                vesselPullProviderReceiveFromLegacySystemPort = new ReceiveFromLegacySystemAdapterStub(serviceHandlerMessageBuilderService, vesselPullProviderService);
            }
            if (vesselPullConsumerReceiveFromLegacySystemPort == null) {
                vesselPullConsumerService = createServiceIfNotPresent(vesselPullConsumerService, "someVesselPullConsumerServiceId", ServiceOperationType.PULL, ServiceRoleType.CONSUMER, ServiceType.VESSEL_SERVICE);
                vesselPullConsumerReceiveFromLegacySystemPort = new ReceiveFromLegacySystemAdapterStub(serviceHandlerMessageBuilderService, vesselPullConsumerService);
            }
            if (riskPushProviderReceiveFromLegacySystemPort == null) {
                riskPushProviderService = createServiceIfNotPresent(riskPushProviderService, "someRiskPushProviderServiceId", ServiceOperationType.PUSH, ServiceRoleType.PROVIDER, ServiceType.RISK_SERVICE);
                riskPushProviderReceiveFromLegacySystemPort = new ReceiveFromLegacySystemAdapterStub(serviceHandlerMessageBuilderService, riskPushProviderService);
            }
            if (vesselPushProviderReceiveFromLegacySystemPort == null) {
                vesselPushProviderService = createServiceIfNotPresent(vesselPushProviderService, "someVesselPushProviderServiceId", ServiceOperationType.PUSH, ServiceRoleType.PROVIDER, ServiceType.VESSEL_SERVICE);
                vesselPushProviderReceiveFromLegacySystemPort = new ReceiveFromLegacySystemAdapterStub(serviceHandlerMessageBuilderService, vesselPushProviderService);
            }
            if (vesselSubscribeProviderReceiveFromLegacySystemPort == null) {
                vesselSubscribeProviderService = createServiceIfNotPresent(vesselSubscribeProviderService, "someVesselSubscribeProviderServiceId", ServiceOperationType.SUBSCRIBE, ServiceRoleType.PROVIDER, ServiceType.VESSEL_SERVICE);
                vesselSubscribeProviderReceiveFromLegacySystemPort = new ReceiveFromLegacySystemAdapterStub(serviceHandlerMessageBuilderService, vesselSubscribeProviderService);
            }
            if (riskSubscribeConsumerReceiveFromLegacySystemPort == null) {
                riskSubscribeConsumerService = createServiceIfNotPresent(riskSubscribeConsumerService, "someRiskSubscribeConsumerServiceId", ServiceOperationType.SUBSCRIBE, ServiceRoleType.CONSUMER, ServiceType.RISK_SERVICE);
                riskSubscribeConsumerReceiveFromLegacySystemPort = new ReceiveFromLegacySystemAdapterStub(serviceHandlerMessageBuilderService, riskSubscribeConsumerService);
            }
            if (vesselSubscribeConsumerReceiveFromLegacySystemPort == null) {
                vesselSubscribeConsumerService = createServiceIfNotPresent(vesselSubscribeConsumerService, "someVesselSubscribeConsumerServiceId", ServiceOperationType.SUBSCRIBE, ServiceRoleType.CONSUMER, ServiceType.VESSEL_SERVICE);
                vesselSubscribeConsumerReceiveFromLegacySystemPort = new ReceiveFromLegacySystemAdapterStub(serviceHandlerMessageBuilderService, vesselSubscribeConsumerService);
            }
            if (incomingNodeToMessageHandlerPort == null) {
                incomingNodeToMessageHandlerPort = new DefaultIncomingNodeToMessageHandlerAdapter(
                        new DefaultMessageHandlerVerificationService(outgoingMessageHandlerToServiceHandlerPort, signatureService),
                        Collections.unmodifiableSet(sendToLegacySystemPortMap.keySet()));
            }
            return new FunctionalAppContext(incomingNodeToMessageHandlerPort,
                    riskPullProviderReceiveFromLegacySystemPort,
                    vesselPullProviderReceiveFromLegacySystemPort,
                    vesselPullConsumerReceiveFromLegacySystemPort,
                    riskPushProviderReceiveFromLegacySystemPort,
                    vesselPushProviderReceiveFromLegacySystemPort,
                    vesselSubscribeProviderReceiveFromLegacySystemPort,
                    riskSubscribeConsumerReceiveFromLegacySystemPort,
                    vesselSubscribeConsumerReceiveFromLegacySystemPort
            );
        }

        public Service createServiceIfNotPresent(Service service, String serviceId, ServiceOperationType operation, ServiceRoleType role, ServiceType type) {
            if (service == null) {
                service = new Service();
                service.setServiceID(serviceId);
                service.setServiceOperation(operation);
                service.setServiceRole(role);
                service.setServiceType(type);
            }
            return service;
        }
    }
}
