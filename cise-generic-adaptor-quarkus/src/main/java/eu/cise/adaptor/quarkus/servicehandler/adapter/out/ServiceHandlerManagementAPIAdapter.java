package eu.cise.adaptor.quarkus.servicehandler.adapter.out;

import eu.cise.adaptor.core.servicehandler.port.out.ServiceHandlerManagementAPIPort;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.api.AuthorityApi;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.api.ContactPersonApi;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.api.NodeApi;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.api.ParticipantApi;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.api.ServiceApi;
import eu.cise.adaptor.core.servicehandler.port.out.subscription_registry_openapi_yaml.api.SubscriptionApi;

public final class ServiceHandlerManagementAPIAdapter implements ServiceHandlerManagementAPIPort {

    private final ServiceApi serviceApi;
    private final ContactPersonApi contactPersonApi;
    private final AuthorityApi authorityApi;
    private final NodeApi nodeApi;
    private final ParticipantApi participantApi;

    private final SubscriptionApi subscriptionApi;

    public ServiceHandlerManagementAPIAdapter(ServiceApi serviceApi, ContactPersonApi contactPersonApi, AuthorityApi authorityApi, NodeApi nodeApi, ParticipantApi participantApi, SubscriptionApi subscriptionApi) {
        this.serviceApi = serviceApi;
        this.contactPersonApi = contactPersonApi;
        this.authorityApi = authorityApi;
        this.nodeApi = nodeApi;
        this.participantApi = participantApi;

        this.subscriptionApi = subscriptionApi;
    }

    @Override
    public ServiceApi getServiceApi() {
        return serviceApi;
    }

    @Override
    public ParticipantApi getParticipantApi() {
        return participantApi;
    }

    @Override
    public NodeApi getNodeApi() {
        return nodeApi;
    }

    @Override
    public ContactPersonApi getContactPersonApi() {
        return contactPersonApi;
    }

    @Override
    public AuthorityApi getAuthorityApi() {
        return authorityApi;
    }

    @Override
    public SubscriptionApi getSubscriptionApi() {
        return subscriptionApi;
    }

}
