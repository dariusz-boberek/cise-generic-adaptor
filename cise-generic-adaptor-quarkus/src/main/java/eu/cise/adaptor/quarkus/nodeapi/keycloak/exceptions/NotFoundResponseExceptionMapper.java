package eu.cise.adaptor.quarkus.nodeapi.keycloak.exceptions;

import org.apache.http.HttpStatus;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.jboss.resteasy.client.exception.WebApplicationExceptionWrapper;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class NotFoundResponseExceptionMapper implements ResponseExceptionMapper<WebApplicationException> {
    @Override
    public WebApplicationException toThrowable(Response response) {
        if (response.getStatus() == HttpStatus.SC_NOT_FOUND) {
            return new NotFoundException();
        }
        return WebApplicationExceptionWrapper.wrap(new WebApplicationException("Unknown error, status code " + response.getStatus(), response));
    }
}
