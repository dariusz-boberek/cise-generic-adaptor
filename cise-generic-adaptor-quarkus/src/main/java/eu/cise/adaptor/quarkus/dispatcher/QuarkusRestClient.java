package eu.cise.adaptor.quarkus.dispatcher;

import eu.cise.dispatcher.DispatcherException;
import eu.cise.dispatcher.rest.RestClient;
import eu.cise.dispatcher.rest.RestResult;

import javax.ws.rs.core.Response;

/**
 * Implements {@link RestClient} to send messages to the CISE Node using POST. Handles only XML data
 * (without supporting GET or DELETE methods), and properly encapsulates response details in {@link RestResult}.
 * All exceptions are wrapped in {@link DispatcherException} for better error management.
 */
public class QuarkusRestClient implements RestClient {

    private final CISEMessagesRestClient messagesRestClient;

    /**
     * Constructor initializes QuarkusRestClient with a CISEMessagesRestClient for dispatching XML messages over MTLS (if configured in Quarkus).
     *
     * @param messagesRestClient provides the configured client for communication.
     */
    public QuarkusRestClient(CISEMessagesRestClient messagesRestClient) {
        this.messagesRestClient = messagesRestClient;
    }

    /**
     * Sends XML content as a POST request over MTLS if enabled in Quarkus.
     * Returns HTTP status, response body, and reason phrase encapsulated in {@link RestResult}.
     * Throws DispatcherException if posting fails.
     *
     * @param address not used
     * @param payload XML content to send.
     * @return {@link RestResult} with response details.
     */
    @Override
    public RestResult post(String address, String payload) {

        try (Response result = messagesRestClient.post(payload)) {
            return new RestResult(result.getStatus(), result.readEntity(String.class), result.getStatusInfo().getReasonPhrase());

        } catch (RuntimeException ex) {
            throw new DispatcherException("Error while connecting to cise-node" , ex);
        }
    }

    @Override
    public RestResult get(String address) {
        throw new UnsupportedOperationException("HTTP Get Method is not supported");
    }

    @Override
    public RestResult delete(String address) {
        throw new UnsupportedOperationException("HTTP Delete Method is not supported");
    }
}
