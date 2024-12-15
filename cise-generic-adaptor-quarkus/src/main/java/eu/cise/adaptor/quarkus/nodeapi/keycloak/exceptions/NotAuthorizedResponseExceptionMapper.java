package eu.cise.adaptor.quarkus.nodeapi.keycloak.exceptions;

import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.common.logging.LogConfig;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.jboss.resteasy.client.exception.WebApplicationExceptionWrapper;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static eu.cise.adaptor.core.common.logging.LoggerMessage.of;

public class NotAuthorizedResponseExceptionMapper implements ResponseExceptionMapper<WebApplicationException> {
    private final static AdaptorLogger log = LogConfig.configureLogging(NotAuthorizedResponseExceptionMapper.class);
    @Override
    public WebApplicationException toThrowable(Response response) {
        log.debug(of("Exception caught by the filter. Status: {} . Response {}", response.getStatus(), response));
        if (response.getStatus() == HttpStatus.SC_UNAUTHORIZED) {
            WebApplicationException result = new NotAuthorizedException("User unauthorized");
            log.debug(of("Exception created. class: {} . message {}", result.getClass(), result.getMessage()));
            return result;
        }
        return WebApplicationExceptionWrapper.wrap(new WebApplicationException("Unknown error, status code " + response.getStatus(), response));
    }
}