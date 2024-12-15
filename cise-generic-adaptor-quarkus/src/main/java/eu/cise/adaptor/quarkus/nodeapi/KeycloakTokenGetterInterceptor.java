package eu.cise.adaptor.quarkus.nodeapi;

import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.common.logging.LogConfig;
import eu.cise.adaptor.quarkus.nodeapi.keycloak.auth.KeycloakTokenHandler;
import eu.cise.adaptor.quarkus.nodeapi.keycloak.rest.client.KeycloakLoginRESTClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.WebApplicationException;

import static eu.cise.adaptor.core.common.logging.LoggerMessage.of;

@ApplicationScoped
@Interceptor
@KeycloakInterceptor
@Priority(Interceptor.Priority.APPLICATION)
public class KeycloakTokenGetterInterceptor {

    private final static AdaptorLogger log = LogConfig.configureLogging(KeycloakTokenGetterInterceptor.class);

    @ConfigProperty(name = "cisenode.api.credentials.username")
    String username;

    @ConfigProperty(name = "cisenode.api.credentials.password")
    String password;

    private final KeycloakLoginRESTClient keycloakLoginRESTClient;

    public KeycloakTokenGetterInterceptor(@RestClient KeycloakLoginRESTClient keycloakLoginRESTClient) {
        this.keycloakLoginRESTClient = keycloakLoginRESTClient;
    }

    @AroundInvoke
    Object getToken(InvocationContext ic) throws Exception {
        try {
            return ic.proceed();
        } catch (WebApplicationException webApplicationException) {
            log.debug(of("WebApplicationException occurred {} message: {}", webApplicationException.getClass(), webApplicationException.getMessage()));
            if (webApplicationException.getCause() instanceof NotAuthorizedException) {
                log.debug(of("NotAuthorizedException occurred, retrying with retrieveToken()"));
                KeycloakTokenHandler.setToken(keycloakLoginRESTClient.retrieveToken(username, password, "password"));
                return ic.proceed();
            } else {
                log.error(of("webApplicationException.getCause(): {}", webApplicationException.getCause().getClass()));
                throw webApplicationException;
            }
        }
    }
}
