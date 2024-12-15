package eu.cise.adaptor.quarkus.nodeapi.service_registry_openapi_yaml;

import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ContactPersonBaseWithUuidDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.NodeBaseDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.NodeConfigurationCreateDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.NodeConfigurationDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.NodeConfigurationUpdateDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.NodeDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ResponseDTONodeDTO;
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


@Path("")
@RegisterRestClient( configKey="cise-rest-api-client")
@RegisterProvider(ServiceRegistryBearerAuthenticationFilter.class)
@RegisterProvider(NotAuthorizedResponseExceptionMapper.class)
@KeycloakInterceptor
@ApplicationScoped
public interface NodeApi extends eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.api.NodeApi {

    @GET
    @Path("/node/config")
    @Produces({"application/json"})
    public NodeConfigurationDTO nodeConfigGet(
    );

    @POST
    @Path("/node/config")
    @Consumes({"application/json"})
    public void nodeConfigPost(
        NodeConfigurationCreateDTO nodeConfigurationCreateDTO
    );

    @PUT
    @Path("/node/config")
    @Consumes({"application/json"})
    public void nodeConfigPut(
        NodeConfigurationUpdateDTO nodeConfigurationUpdateDTO
    );

    @PUT
    @Path("/node/config/trigger-sync-pull")
    public void nodeConfigTriggerSyncPullPut(
    );

    @GET
    @Path("/node")
    @Produces({"application/json"})
    public NodeDTO nodeGet(
    );

    @GET
    @Path("/node/picklists/contact-persons/{authorityUUID}")
    @Produces({"application/json"})
    public List<ContactPersonBaseWithUuidDTO> nodePicklistsContactPersonsAuthorityUUIDGet(
         @PathParam("authorityUUID") String authorityUUID
    );

    @GET
    @Path("/nodes/remote")
    @Produces({"application/json"})
    public ResponseDTONodeDTO nodesRemoteGet(
         @QueryParam("sortBy") String sortBy, 
         @QueryParam("sortOrder") String sortOrder
    );

    @POST
    @Path("/nodes/remote")
    @Consumes({"application/json"})
    public void nodesRemotePost(
        NodeBaseDTO nodeBaseDTO
    );

    @DELETE
    @Path("/nodes/remote/{uuid}")
    public void nodesRemoteUuidDelete(
         @PathParam("uuid") UUID uuid
    );

    @GET
    @Path("/nodes/remote/{uuid}")
    @Produces({"application/json"})
    public NodeDTO nodesRemoteUuidGet(
         @PathParam("uuid") UUID uuid
    );

    @PUT
    @Path("/nodes/remote/{uuid}")
    @Consumes({"application/json"})
    public void nodesRemoteUuidPut(
         @PathParam("uuid") UUID uuid, 
        NodeBaseDTO nodeBaseDTO
    );

}
