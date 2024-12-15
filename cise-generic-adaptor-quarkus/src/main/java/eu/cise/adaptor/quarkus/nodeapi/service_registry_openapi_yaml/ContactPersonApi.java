package eu.cise.adaptor.quarkus.nodeapi.service_registry_openapi_yaml;

import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ContactPersonBaseDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ContactPersonCreateDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ContactPersonDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ResponseDTOContactPersonDTO;
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


@Path("/contact-persons")
@RegisterRestClient( configKey="cise-rest-api-client")
@RegisterProvider(ServiceRegistryBearerAuthenticationFilter.class)
@RegisterProvider(NotAuthorizedResponseExceptionMapper.class)
@KeycloakInterceptor
@ApplicationScoped
public interface ContactPersonApi extends eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.api.ContactPersonApi {

    @GET
    @Path("/local")
    @Produces({"application/json"})
    public ResponseDTOContactPersonDTO contactPersonsLocalGet(
         @QueryParam("authorityUUID") List<String> authorityUUID, 
         @QueryParam("contactFor") List<String> contactFor, 
         @QueryParam("country") List<String> country, 
         @QueryParam("email") String email, 
         @QueryParam("limit") Integer limit, 
         @QueryParam("nameSurname") String nameSurname, 
         @QueryParam("offset") Integer offset, 
         @QueryParam("sortBy") String sortBy, 
         @QueryParam("sortOrder") String sortOrder
    );

    @GET
    @Path("/local/picklists/authorities")
    public void contactPersonsLocalPicklistsAuthoritiesGet(
    );

    @POST
    @Path("/local")
    @Consumes({"application/json"})
    public void contactPersonsLocalPost(
        ContactPersonCreateDTO contactPersonCreateDTO
    );

    @DELETE
    @Path("/local/{uuid}")
    public void contactPersonsLocalUuidDelete(
         @PathParam("uuid") UUID uuid
    );

    @GET
    @Path("/local/{uuid}")
    @Produces({"application/json"})
    public ContactPersonDTO contactPersonsLocalUuidGet(
         @PathParam("uuid") UUID uuid
    );

    @PUT
    @Path("/local/{uuid}")
    @Consumes({"application/json"})
    public void contactPersonsLocalUuidPut(
         @PathParam("uuid") UUID uuid, 
        ContactPersonBaseDTO contactPersonBaseDTO
    );

    @GET
    @Path("/remote")
    @Produces({"application/json"})
    public ResponseDTOContactPersonDTO contactPersonsRemoteGet(
         @QueryParam("authorityUUID") List<String> authorityUUID, 
         @QueryParam("contactFor") List<String> contactFor, 
         @QueryParam("country") List<String> country, 
         @QueryParam("email") String email, 
         @QueryParam("limit") Integer limit, 
         @QueryParam("nameSurname") String nameSurname, 
         @QueryParam("offset") Integer offset, 
         @QueryParam("sortBy") String sortBy, 
         @QueryParam("sortOrder") String sortOrder
    );

    @GET
    @Path("/remote/picklists/authorities")
    public void contactPersonsRemotePicklistsAuthoritiesGet(
    );

    @GET
    @Path("/remote/{uuid}")
    @Produces({"application/json"})
    public ContactPersonDTO contactPersonsRemoteUuidGet(
         @PathParam("uuid") UUID uuid
    );

}
