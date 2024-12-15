package eu.cise.adaptor.quarkus;


import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.common.logging.LogConfig;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static eu.cise.adaptor.core.common.logging.LoggerMessage.of;
import static eu.cise.adaptor.core.common.logging.MessageRouteValue.CISE_TO_LS;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;


/**
 * The class provides the REST message resource available
 * for the adaptors and CISE nodes in the network.
 * <p>
 * Messages are received by this rest endpoint, verified
 * and sent to a kafka partition toward the discovery
 * agent microservice.
 * <p>
 * Every message received is passed to the acceptance
 * agent that returns an xml Acknowledgment packaged
 * by this resource in a HTTP response.
 * <p>
 * NOTE: to follow the EUCISE2020 implementation the only
 * HTTP code 200 and 202 have been used.
 */
@Path("/rest/api")
@OpenAPIDefinition(
        tags = {
                @Tag(name = "CISE", description = "Common Information Sharing Environment"),
                @Tag(name = "Submission", description = "Submission agent for messages")
        },
        info = @Info(
                title = "Submission Agent messages API",
                version = "1.0.0",
                contact = @Contact(
                        name = "Submission Agent",
                        url = "https://node.e-cise.eu/rest/api/messages",
                        email = "techsupport@cise.com"),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"))
)
public class MessageResource {

    private static final AdaptorLogger logger = LogConfig.configureLogging(MessageResource.class);


    @Inject
    EventBus eventBus;

    /**
     * This is the entry point of CISE node for the adaptor and other nodes.
     *
     * @param payload the POST payload to be processed
     * @return the RESTeasy response
     */
    @POST
    @Consumes(APPLICATION_XML)
    @Produces(APPLICATION_XML)
    @Path("/messages")
    @Operation(
            summary = "REST Service Endpoint to accept CISE Messages",
            description = "The message resource allow the client to send CISE messages to the CISE network")
    @RequestBody(
            description = "The CISE Message is an xml document that complies to the CISE Data Model XSDs",
            required = true)
    @APIResponses(
            value = {
                    @APIResponse(
                            name = "Message accepted successfully",
                            responseCode = "202",
                            description = "Message accepted and in elaboration. A successful Sync Ack is returned",
                            content = @Content(mediaType = APPLICATION_XML)),
                    @APIResponse(
                            name = "Message elaboration caused an error",
                            responseCode = "200",
                            description = "Request terminated with an error. A Sync Ack with the error is returned",
                            content = @Content(mediaType = APPLICATION_XML))})

    public Uni<Response> create(String payload) {

        return eventBus.<Acknowledgement>request("incoming-message", payload)
                .onFailure()
                .invoke(throwable -> logger.error(of("Error while processing incoming message").addRoutingAttribute(CISE_TO_LS), throwable))
                .onItem()
                .transform(acknowledgementMessage -> HTTPAdapter.toResponse(acknowledgementMessage.body()));

    }

}
