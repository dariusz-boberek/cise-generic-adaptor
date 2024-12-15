package eu.cise.adaptor.quarkus.nodeapi.keycloak.rest.client;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;

public abstract class AuthenticationRequestFilter implements ClientRequestFilter {

    @Override
    public void filter(ClientRequestContext requestContext) {
        requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, getAccessToken());
    }

    protected abstract String getAccessToken();

}
