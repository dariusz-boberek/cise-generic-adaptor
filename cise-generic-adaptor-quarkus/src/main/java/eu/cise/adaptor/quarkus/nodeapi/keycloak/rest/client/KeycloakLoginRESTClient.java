package eu.cise.adaptor.quarkus.nodeapi.keycloak.rest.client;

import eu.cise.adaptor.quarkus.nodeapi.keycloak.exceptions.NotAuthorizedResponseExceptionMapper;
import eu.cise.adaptor.quarkus.nodeapi.keycloak.model.KeycloakTokenDTO;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/auth/realms/cise/protocol/openid-connect/token")
@RegisterRestClient(configKey = "cise-rest-api-client")
@RegisterProvider(KeycloakBasicAuthenticationFilter.class)
@RegisterProvider(NotAuthorizedResponseExceptionMapper.class)
public interface KeycloakLoginRESTClient {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    KeycloakTokenDTO retrieveToken(@FormParam("username") String username, @FormParam("password") String password,
                                   @FormParam("grant_type") String grantType);

}
