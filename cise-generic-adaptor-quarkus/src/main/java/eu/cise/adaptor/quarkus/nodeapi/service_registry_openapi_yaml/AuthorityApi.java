package eu.cise.adaptor.quarkus.nodeapi.service_registry_openapi_yaml;

import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.AuthorityBaseDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.AuthorityDetailsDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ResponseDTOAuthorityDTO;
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


@Path("/authorities")
@RegisterRestClient( configKey="cise-rest-api-client")
@RegisterProvider(ServiceRegistryBearerAuthenticationFilter.class)
@RegisterProvider(NotAuthorizedResponseExceptionMapper.class)
@KeycloakInterceptor
@ApplicationScoped
public interface AuthorityApi extends eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.api.AuthorityApi {

    @GET
    @Path("/local")
    @Produces({"application/json"})
    public ResponseDTOAuthorityDTO authoritiesLocalGet(
         @QueryParam("community") List<String> community, 
         @QueryParam("country") List<String> country, 
         @QueryParam("limit") Integer limit, 
         @QueryParam("name") String name, 
         @QueryParam("offset") Integer offset, 
         @QueryParam("sortBy") String sortBy, 
         @QueryParam("sortOrder") String sortOrder
    );

    @POST
    @Path("/local")
    @Consumes({"application/json"})
    public void authoritiesLocalPost(
        AuthorityBaseDTO authorityBaseDTO
    );

    @DELETE
    @Path("/local/{uuid}")
    public void authoritiesLocalUuidDelete(
         @PathParam("uuid") UUID uuid
    );

    @GET
    @Path("/local/{uuid}")
    @Produces({"application/json"})
    public AuthorityDetailsDTO authoritiesLocalUuidGet(
         @PathParam("uuid") UUID uuid
    );

    @PUT
    @Path("/local/{uuid}")
    @Consumes({"application/json"})
    public void authoritiesLocalUuidPut(
         @PathParam("uuid") UUID uuid, 
        AuthorityBaseDTO authorityBaseDTO
    );

    @GET
    @Path("/remote")
    @Produces({"application/json"})
    public ResponseDTOAuthorityDTO authoritiesRemoteGet(
         @QueryParam("community") List<String> community, 
         @QueryParam("country") List<String> country, 
         @QueryParam("limit") Integer limit, 
         @QueryParam("name") String name, 
         @QueryParam("offset") Integer offset, 
         @QueryParam("sortBy") String sortBy, 
         @QueryParam("sortOrder") String sortOrder
    );

    @GET
    @Path("/remote/{uuid}")
    @Produces({"application/json"})
    public AuthorityDetailsDTO authoritiesRemoteUuidGet(
         @PathParam("uuid") UUID uuid
    );

}
