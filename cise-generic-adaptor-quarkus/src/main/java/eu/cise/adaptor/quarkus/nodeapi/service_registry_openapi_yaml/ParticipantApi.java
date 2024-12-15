package eu.cise.adaptor.quarkus.nodeapi.service_registry_openapi_yaml;

import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ContactPersonBaseWithUuidDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.LongDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ParticipantCreateDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ParticipantDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ParticipantUpdateDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ResponseDTOParticipantDTO;
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


@Path("/participants")
@RegisterRestClient( configKey="cise-rest-api-client")
@RegisterProvider(ServiceRegistryBearerAuthenticationFilter.class)
@RegisterProvider(NotAuthorizedResponseExceptionMapper.class)
@KeycloakInterceptor
@ApplicationScoped
public interface ParticipantApi extends eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.api.ParticipantApi {

    @GET
    @Produces({"application/json"})
    public ResponseDTOParticipantDTO participantsGet(
         @QueryParam("limit") Integer limit, 
         @QueryParam("offset") Integer offset, 
         @QueryParam("participantId") String participantId, 
         @QueryParam("smart") Boolean smart, 
         @QueryParam("sortBy") String sortBy, 
         @QueryParam("sortOrder") String sortOrder
    );

    @GET
    @Path("/local")
    @Produces({"application/json"})
    public ResponseDTOParticipantDTO participantsLocalGet(
         @QueryParam("authorityUUID") String authorityUUID, 
         @QueryParam("community") List<String> community, 
         @QueryParam("function") List<String> function, 
         @QueryParam("limit") Integer limit, 
         @QueryParam("name") String name, 
         @QueryParam("offset") Integer offset, 
         @QueryParam("participantId") String participantId, 
         @QueryParam("sortBy") String sortBy, 
         @QueryParam("sortOrder") String sortOrder
    );

    @GET
    @Path("/local/picklists/authorities")
    @Produces({"application/json"})
    public List<UUIDDescriptionCountryDTO> participantsLocalPicklistsAuthoritiesGet(
    );

    @GET
    @Path("/local/picklists/contact-persons/{authorityUUID}")
    @Produces({"application/json"})
    public List<ContactPersonBaseWithUuidDTO> participantsLocalPicklistsContactPersonsAuthorityUUIDGet(
         @PathParam("authorityUUID") String authorityUUID
    );

    @POST
    @Path("/local")
    @Consumes({"application/json"})
    public void participantsLocalPost(
        ParticipantCreateDTO participantCreateDTO
    );

    @GET
    @Path("/local/stats/count")
    @Produces({"application/json"})
    public LongDTO participantsLocalStatsCountGet(
    );

    @DELETE
    @Path("/local/{uuid}")
    public void participantsLocalUuidDelete(
         @PathParam("uuid") UUID uuid
    );

    @GET
    @Path("/local/{uuid}")
    @Produces({"application/json"})
    public ParticipantDTO participantsLocalUuidGet(
         @PathParam("uuid") UUID uuid
    );

    @PUT
    @Path("/local/{uuid}")
    @Consumes({"application/json"})
    public void participantsLocalUuidPut(
         @PathParam("uuid") UUID uuid, 
        ParticipantUpdateDTO participantUpdateDTO
    );

    @GET
    @Path("/remote")
    @Produces({"application/json"})
    public ResponseDTOParticipantDTO participantsRemoteGet(
         @QueryParam("community") List<String> community, 
         @QueryParam("country") List<String> country, 
         @QueryParam("function") List<String> function, 
         @QueryParam("limit") Integer limit, 
         @QueryParam("name") String name, 
         @QueryParam("nodeId") String nodeId, 
         @QueryParam("offset") Integer offset, 
         @QueryParam("participantId") String participantId, 
         @QueryParam("sortBy") String sortBy, 
         @QueryParam("sortOrder") String sortOrder
    );

    @GET
    @Path("/remote/stats/count")
    @Produces({"application/json"})
    public LongDTO participantsRemoteStatsCountGet(
    );

    @GET
    @Path("/remote/{uuid}")
    @Produces({"application/json"})
    public ParticipantDTO participantsRemoteUuidGet(
         @PathParam("uuid") UUID uuid
    );

}
