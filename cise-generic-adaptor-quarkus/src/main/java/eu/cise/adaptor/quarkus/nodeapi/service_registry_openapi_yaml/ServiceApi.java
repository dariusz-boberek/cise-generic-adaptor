package eu.cise.adaptor.quarkus.nodeapi.service_registry_openapi_yaml;

import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.LongDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ResponseDTOServiceDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ServiceBaseDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ServiceCreateDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ServiceDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ServiceStatusUpdateDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.UUIDDescriptionCountryDTO;
import eu.cise.adaptor.quarkus.nodeapi.KeycloakInterceptor;
import eu.cise.adaptor.quarkus.nodeapi.keycloak.exceptions.NotAuthorizedResponseExceptionMapper;
import eu.cise.adaptor.quarkus.nodeapi.keycloak.rest.client.ServiceRegistryBearerAuthenticationFilter;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.UUID;


@Path("/services")
@RegisterRestClient(configKey = "cise-rest-api-client")
@RegisterProvider(ServiceRegistryBearerAuthenticationFilter.class)
@RegisterProvider(NotAuthorizedResponseExceptionMapper.class)
@KeycloakInterceptor
@ApplicationScoped
public interface ServiceApi extends eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.api.ServiceApi {

    @GET
    @Produces({"application/json"})
    public ResponseDTOServiceDTO servicesGet(
            @QueryParam("limit") Integer limit,
            @QueryParam("offset") Integer offset,
            @QueryParam("serviceId") String serviceId,
            @QueryParam("serviceOperation") String serviceOperation,
            @QueryParam("serviceRole") String serviceRole,
            @QueryParam("serviceStatus") List<String> serviceStatus,
            @QueryParam("serviceType") String serviceType,
            @QueryParam("smart") Boolean smart,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("sortOrder") String sortOrder
    );

    @GET
    @Path("/local")
    @Produces({"application/json"})
    public ResponseDTOServiceDTO servicesLocalGet(
            @QueryParam("limit") Integer limit,
            @QueryParam("offset") Integer offset,
            @QueryParam("participantCommunity") String participantCommunity,
            @QueryParam("participantFunction") String participantFunction,
            @QueryParam("participantId") String participantId,
            @QueryParam("serviceId") String serviceId,
            @QueryParam("serviceOperation") String serviceOperation,
            @QueryParam("serviceRole") String serviceRole,
            @QueryParam("serviceStatus") String serviceStatus,
            @QueryParam("serviceType") String serviceType,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("sortOrder") String sortOrder
    );

    @GET
    @Path("/local/picklists/participants")
    public void servicesLocalPicklistsParticipantsGet(
    );

    @POST
    @Path("/local")
    @Consumes({"application/json"})
    public void servicesLocalPost(
            ServiceCreateDTO serviceCreateDTO
    );

    @GET
    @Path("/local/smart-search/participants")
    @Produces({"application/json"})
    public List<String> servicesLocalSmartSearchParticipantsGet(
            @QueryParam("limit") Integer limit,
            @QueryParam("participantCommunity") String participantCommunity,
            @QueryParam("participantFunction") String participantFunction,
            @QueryParam("participantId") String participantId
    );

    @GET
    @Path("/local/smart-search/services")
    @Produces({"application/json"})
    public List<String> servicesLocalSmartSearchServicesGet(
            @QueryParam("limit") Integer limit,
            @QueryParam("serviceId") String serviceId
    );

    @GET
    @Path("/local/stats/count")
    @Produces({"application/json"})
    public LongDTO servicesLocalStatsCountGet(
    );

    @DELETE
    @Path("/local/{uuid}")
    public void servicesLocalUuidDelete(
            @PathParam("uuid") UUID uuid
    );

    @GET
    @Path("/local/{uuid}")
    @Produces({"application/json"})
    public ServiceDTO servicesLocalUuidGet(
            @PathParam("uuid") UUID uuid
    );

    @PUT
    @Path("/local/{uuid}")
    @Consumes({"application/json"})
    public void servicesLocalUuidPut(
            @PathParam("uuid") UUID uuid,
            ServiceBaseDTO serviceBaseDTO
    );

    @GET
    @Path("/remote")
    @Produces({"application/json"})
    public ResponseDTOServiceDTO servicesRemoteGet(
            @QueryParam("dataFreshness") String dataFreshness,
            @QueryParam("dataLocation") List<String> dataLocation,
            @QueryParam("limit") Integer limit,
            @QueryParam("offset") Integer offset,
            @QueryParam("participantCommunity") String participantCommunity,
            @QueryParam("participantCountry") String participantCountry,
            @QueryParam("participantFunction") String participantFunction,
            @QueryParam("participantId") String participantId,
            @QueryParam("serviceId") String serviceId,
            @QueryParam("serviceOperation") String serviceOperation,
            @QueryParam("serviceRole") String serviceRole,
            @QueryParam("serviceStatus") String serviceStatus,
            @QueryParam("serviceType") String serviceType,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("sortOrder") String sortOrder
    );

    @GET
    @Path("/remote/smart-search/participants")
    @Produces({"application/json"})
    public List<UUIDDescriptionCountryDTO> servicesRemoteSmartSearchParticipantsGet(
            @QueryParam("limit") Integer limit,
            @QueryParam("participantCommunity") String participantCommunity,
            @QueryParam("participantFunction") String participantFunction,
            @QueryParam("participantId") String participantId
    );

    @GET
    @Path("/remote/smart-search/services")
    @Produces({"application/json"})
    public List<String> servicesRemoteSmartSearchServicesGet(
            @QueryParam("limit") Integer limit,
            @QueryParam("serviceId") String serviceId
    );

    @GET
    @Path("/remote/stats/count")
    @Produces({"application/json"})
    public LongDTO servicesRemoteStatsCountGet(
    );

    @GET
    @Path("/remote/{uuid}")
    @Produces({"application/json"})
    public ServiceDTO servicesRemoteUuidGet(
            @PathParam("uuid") UUID uuid
    );

    @PUT
    @Path("/status/local/{uuid}")
    @Consumes({"application/json"})
    public void servicesStatusLocalUuidPut(
            @PathParam("uuid") UUID uuid,
            ServiceStatusUpdateDTO serviceStatusUpdateDTO
    );


}
