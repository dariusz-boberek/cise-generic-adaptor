package eu.cise.adaptor.quarkus.nodeapi.subscription_registry_openapi_yaml.api;

import eu.cise.adaptor.core.servicehandler.port.out.subscription_registry_openapi_yaml.model.ResponseDTOSubscriptionDTO;
import eu.cise.adaptor.core.servicehandler.port.out.subscription_registry_openapi_yaml.model.SubscriptionUpdateDTO;
import eu.cise.adaptor.quarkus.nodeapi.KeycloakInterceptor;
import eu.cise.adaptor.quarkus.nodeapi.keycloak.exceptions.NotAuthorizedResponseExceptionMapper;
import eu.cise.adaptor.quarkus.nodeapi.keycloak.rest.client.ServiceRegistryBearerAuthenticationFilter;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.time.LocalDate;


@Path("/subscriptions")
@RegisterRestClient( configKey="cise-rest-api-client")
@RegisterProvider(ServiceRegistryBearerAuthenticationFilter.class)
@RegisterProvider(NotAuthorizedResponseExceptionMapper.class)
@KeycloakInterceptor
@ApplicationScoped
public interface SubscriptionApi extends eu.cise.adaptor.core.servicehandler.port.out.subscription_registry_openapi_yaml.api.SubscriptionApi {

    @GET
    @Produces({"application/json"})
    public ResponseDTOSubscriptionDTO subscriptionsGet(
         @QueryParam("expireDateFrom") LocalDate expireDateFrom, 
         @QueryParam("expireDateTo") LocalDate expireDateTo, 
         @QueryParam("limit") Integer limit, 
         @QueryParam("offset") Integer offset, 
         @QueryParam("providerServiceId") String providerServiceId, 
         @QueryParam("smart") Boolean smart, 
         @QueryParam("sortBy") String sortBy, 
         @QueryParam("sortOrder") String sortOrder, 
         @QueryParam("status") String status, 
         @QueryParam("subscriberParticipantId") String subscriberParticipantId, 
         @QueryParam("subscriberServiceId") String subscriberServiceId
    );

    @DELETE
    @Path("/{uuid}")
    public void subscriptionsUuidDelete(
         @PathParam("uuid") String uuid
    );

    @PUT
    @Path("/{uuid}")
    @Consumes({"application/json"})
    public void subscriptionsUuidPut(
         @PathParam("uuid") String uuid, 
        SubscriptionUpdateDTO subscriptionUpdateDTO
    );

}
