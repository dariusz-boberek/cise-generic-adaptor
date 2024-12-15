package eu.cise.adaptor.quarkus.nodeapi.keycloak.rest.client;

import eu.cise.adaptor.quarkus.nodeapi.keycloak.auth.KeycloakTokenHandler;
import eu.cise.adaptor.quarkus.nodeapi.keycloak.model.KeycloakTokenDTO;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;

@Priority(Priorities.AUTHENTICATION)
public class ServiceRegistryBearerAuthenticationFilter extends AuthenticationRequestFilter {

    @Override
    protected String getAccessToken() {
        KeycloakTokenDTO token = KeycloakTokenHandler.getToken();
        if (token == null) {
            return "";
        }
        return token.getTokenType() + " " + token.getAccessToken();
    }

}
