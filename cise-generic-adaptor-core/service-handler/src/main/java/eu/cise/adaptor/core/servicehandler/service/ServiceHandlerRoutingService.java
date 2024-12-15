package eu.cise.adaptor.core.servicehandler.service;

import eu.cise.servicemodel.v1.message.Message;

/**
 * This service is responsible for managing the routing of messages within the Service Handler component of the Generic Adaptor.
 * It deals with the direction of incoming messages from the Message Handler to the appropriate plugin, based on the service ID and other relevant criteria.
 *
 * @see DefaultServiceHandlerRoutingService
 */
public interface ServiceHandlerRoutingService {

    void handleMessage(Message xmlMessage);

}
