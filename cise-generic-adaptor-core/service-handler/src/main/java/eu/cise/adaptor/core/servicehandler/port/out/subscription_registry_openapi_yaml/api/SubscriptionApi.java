package eu.cise.adaptor.core.servicehandler.port.out.subscription_registry_openapi_yaml.api;

import eu.cise.adaptor.core.servicehandler.port.out.subscription_registry_openapi_yaml.model.ResponseDTOSubscriptionDTO;
import eu.cise.adaptor.core.servicehandler.port.out.subscription_registry_openapi_yaml.model.SubscriptionUpdateDTO;

import java.time.LocalDate;

/**
 * CISE Node - Subscription Registry API
 * <p>The Subscription Registry manages the subscriptions to the subscription provider CISE services.</p>
 *
 * Reference package for API Enums {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums}
 *
 */
public interface SubscriptionApi {


    /**
     * Call the Node Service API to retrieve a list of CISE subscribers.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * subscription registry may throw: RequestInvalidException if any parameter is invalid or if smart filtering criteria are not met.
     *
     * @param expireDateFrom The starting date of the Subscription expiration date search period.
     * @param expireDateTo The ending date of the Subscription expiration date search period.
     * @param limit The size of the results page. Minimum: 10, Maximum: 100, Default: 10.
     * @param offset The starting entry of the page. Minimum: 0, Default: 0.
     * @param providerServiceId The ID of the Subscription Provider service (filter with exact match). Cannot be null or empty. Case insensitive field.
     * @param smart Indicates whether a smart search with distinct will be performed based on subscriberServiceId or subscriberParticipantId.
     * @param sortBy The field to sort by the results. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortBySubscription}
     * @param sortOrder The sorting direction. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortOrder}
     * @param status The status of the subscriptions to filter by. Case insensitive field. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SubscriptionStatus}
     * @param subscriberParticipantId The ID of the subscriber participant. Case insensitive field.
     * @param subscriberServiceId The ID of the Subscriber service. Case insensitive field.
     * @return {@link ResponseDTOSubscriptionDTO} containing a list of SubscriptionDTO and the total number of results.
     */
    ResponseDTOSubscriptionDTO subscriptionsGet(
          LocalDate expireDateFrom, 
          LocalDate expireDateTo, 
          Integer limit, 
          Integer offset, 
          String providerServiceId, 
          Boolean smart, 
          String sortBy, 
          String sortOrder, 
          String status, 
          String subscriberParticipantId, 
          String subscriberServiceId
    );

    /**
     * Delete a subscription identified by the UUID.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * subscription registry may throw: ResourceNotFoundException if the subscription with the specified UUID is not found.
     * subscription registry may throw: RequestInvalidException if the UUID format is invalid.
     *
     * @param uuid The unique identifier of the subscription to delete. Cannot be null or empty.
     */
    void subscriptionsUuidDelete(
          String uuid
    );

    /**
     * Modify the status of a subscription identified by the UUID.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * subscription registry may throw: ResourceNotFoundException if the subscription with the specified UUID is not found.
     * subscription registry may throw: RequestInvalidException if the UUID format is invalid or if the update data is invalid.
     *
     * @param uuid The unique identifier of the subscription to modify. Cannot be null or empty.
     * @param subscriptionUpdateDTO The data transfer object containing the updated subscription status. Cannot be null or empty.
     */
    void subscriptionsUuidPut(
          String uuid, 
        SubscriptionUpdateDTO subscriptionUpdateDTO
    );

}
