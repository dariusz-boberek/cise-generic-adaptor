package eu.cise.adaptor.core.servicehandler.port.out;

import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.api.AuthorityApi;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.api.ContactPersonApi;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.api.NodeApi;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.api.ParticipantApi;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.api.ServiceApi;
import eu.cise.adaptor.core.servicehandler.port.out.subscription_registry_openapi_yaml.api.SubscriptionApi;

/**
 * This Port exposes to the plugins the CISE Node Management API of the
 * Service and Subscription services.
 */
public interface ServiceHandlerManagementAPIPort {

    /* Service Registry API for CISE Node */

    /**
     * Provides access to the Service API exposed by the CISE Node in order to manipulate information about CISE Services
     * @return the REST client that implements the ServiceAPI
     */
    ServiceApi getServiceApi();

    /**
     * Provides access to the Participant API exposed by the CISE Node in order to  manipulate information about CISE Participants
     * @return the REST client that implements the ParticipantAPI
     */
    ParticipantApi getParticipantApi();

    /**
     * Provides access to the Node API exposed by the CISE Node in order to  manipulate information about CISE Node
     * @return the REST client that implements the NodeAPI
     */
    NodeApi getNodeApi();

    /**
     * Provides access to the ContactPerson API exposed by the CISE Node in order to  manipulate information about Contact Persons
     * @return the REST client that implements the ContactPersonAPI
     */
    ContactPersonApi getContactPersonApi();

    /**
     * Provides access to the Authority API exposed by the CISE Node in order to  manipulate information about CISE Authorities
     * @return the REST client that implements the AuthorityAPI
     */
    AuthorityApi getAuthorityApi();

    /* Subscription Registry API for CISE Node */

    /**
     * Provides access to the Subscription API exposed by the CISE Node in order to  manipulate information about CISE Subscriptions
     * @return the REST client that implements the SubscriptionAPI
     */
    SubscriptionApi getSubscriptionApi();
}
