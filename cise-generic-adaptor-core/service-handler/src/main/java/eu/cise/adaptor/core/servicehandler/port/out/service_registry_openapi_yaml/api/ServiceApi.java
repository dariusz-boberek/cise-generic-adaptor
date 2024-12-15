package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.api;

import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.LongDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ResponseDTOServiceDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ServiceBaseDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ServiceCreateDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ServiceDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ServiceStatusUpdateDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.UUIDDescriptionCountryDTO;

import java.util.List;
import java.util.UUID;

/**
 * CISE Service Registry
 * <p>Service Registry API for CISE Node</p>
 *
 * Reference package for API Enums {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums}
 */
public interface ServiceApi {


    /**
     * Get a list of services with filtering.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * service registry may throw: RequestInvalidException if any parameter is invalid.
     *
     * @param limit The size of the results page. Minimum: 10, Maximum: 100, Default: 10.
     * @param offset The starting entry of the page. Minimum: 0, Default: 0.
     * @param serviceId The identifier of the service.
     * @param serviceOperation The operation of the service. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.ServiceOperation}
     * @param serviceRole The role of the service. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.ServiceRole}
     * @param serviceStatus The status of the service. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.ServiceStatus}
     * @param serviceType The type of the service. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.ServiceType}
     * @param smart Indicates whether a smart search with distinct will be performed.
     * @param sortBy The field to sort by the results. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortByService}
     * @param sortOrder The sorting direction. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortOrder} . Note:
     * @return {@link ResponseDTOServiceDTO} containing a list of {@link ServiceDTO} and the total number of results.
     */
    ResponseDTOServiceDTO servicesGet(
          Integer limit, 
          Integer offset, 
          String serviceId, 
          String serviceOperation, 
          String serviceRole, 
          List<String> serviceStatus, 
          String serviceType, 
          Boolean smart, 
          String sortBy, 
          String sortOrder
    );

    /**
     * Get a filtered list of Services for the local node.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * service registry may throw: RequestInvalidException if any parameter is invalid.
     *
     * @param limit The size of the results page. Minimum: 10, Maximum: 100, Default: 10.
     * @param offset The starting entry of the page. Minimum: 0, Default: 0.
     * @param participantCommunity The community of the participant. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.Community}
     * @param participantFunction The function of the participant. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.Function}
     * @param participantId The identifier of the participant.
     * @param serviceId The identifier of the service.
     * @param serviceOperation The operation of the service. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.ServiceOperation}
     * @param serviceRole The role of the service. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.ServiceRole}
     * @param serviceStatus The status of the service. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.ServiceStatus}
     * @param serviceType The type of the service. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.ServiceType}
     * @param sortBy The field to sort by the results. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortByService}
     * @param sortOrder The sorting direction. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortOrder}
     * @return {@link ResponseDTOServiceDTO} containing a list of {@link ServiceDTO} and the total number of results.
     */
    ResponseDTOServiceDTO servicesLocalGet(
          Integer limit, 
          Integer offset, 
          String participantCommunity, 
          String participantFunction, 
          String participantId, 
          String serviceId, 
          String serviceOperation, 
          String serviceRole, 
          String serviceStatus, 
          String serviceType, 
          String sortBy, 
          String sortOrder
    );

    /**
     * Get a list of Participants for the local node.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     */
    void servicesLocalPicklistsParticipantsGet(
    );

    /**
     * Create a local Service.
     *
     * REST API response codes: HTTP status 201 indicates a successful operation. HTTP status 400 indicates the request was not valid.
     *
     * service registry may throw: RequestInvalidException if the request body is invalid.
     * service registry may throw: InternalServerErrorException if an internal server error occurs.
     *
     * @param serviceCreateDTO The data transfer object containing the service details. Cannot be null or empty.
     */
    void servicesLocalPost(
        ServiceCreateDTO serviceCreateDTO
    );

    /**
     * Get a filtered list of Participants for the local node.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * @param limit The size of the results page. Minimum: 10, Maximum: 100, Default: 10.
     * @param participantCommunity The community of the participant. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.Community}
     * @param participantFunction The function of the participant. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.Function}
     * @param participantId The identifier of the participant.
     * @return A list of participant IDs.
     */
    List<String> servicesLocalSmartSearchParticipantsGet(
          Integer limit, 
          String participantCommunity, 
          String participantFunction, 
          String participantId
    );

    /**
     * Get a filtered list of Services for the local node.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * @param limit The size of the results page. Minimum: 10, Maximum: 100, Default: 10.
     * @param serviceId The identifier of the service.
     * @return A list of service IDs.
     */
    List<String> servicesLocalSmartSearchServicesGet(
          Integer limit, 
          String serviceId
    );

    /**
     * Get the number of Services in the local node.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * @return {@link LongDTO} containing the count of local services.
     */
    LongDTO servicesLocalStatsCountGet(
    );

    /**
     * Delete a local Service.
     *
     * REST API response codes: HTTP status 204 indicates a successful operation. HTTP status 400 indicates the request was not valid. HTTP status 404 indicates the service was not found.
     *
     * service registry may throw: ResourceNotFoundException if the service with the specified UUID is not found.
     * service registry may throw: RequestInvalidException if the UUID format is invalid.
     *
     * @param uuid The unique identifier of the service. Cannot be null or empty.
     */
    void servicesLocalUuidDelete(
          UUID uuid
    );

    /**
     * Get a local Service.
     *
     * @param uuid The unique identifier of the service. Cannot be null or empty.
     * @return {@link ServiceDTO} containing the service details. HTTP status 200 indicates a successful response. HTTP status 404 indicates the service was not found.
     * service registry may throw: ResourceNotFoundException if the service with the specified UUID is not found.
     */
    ServiceDTO servicesLocalUuidGet(
          UUID uuid
    );

    /**
     * Get a local Service.
     *
     * REST API response codes: HTTP status 200 indicates a successful response. HTTP status 404 indicates the service was not found.
     *
     * service registry may throw: ResourceNotFoundException if the service with the specified UUID is not found.
     *
     * @param uuid The unique identifier of the service. Cannot be null or empty.
     * @param serviceBaseDTO The body with new data.
     */
    void servicesLocalUuidPut(
          UUID uuid, 
        ServiceBaseDTO serviceBaseDTO
    );

    /**
     * Get a list of remote services with filtering.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * @param dataFreshness The data freshness of the service. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.DataFreshness}
     * @param dataLocation The area of interest for the service. Maximum: 1000. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.AreaOfInterestDataLocation}
     * @param limit The size of the results page. Minimum: 10, Maximum: 100, Default: 10.
     * @param offset The starting entry of the page. Minimum: 0, Default: 0.
     * @param participantCommunity The community of the participant. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.Community}
     * @param participantCountry The country of the participant. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.Country}
     * @param participantFunction The function of the participant. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.Function}
     * @param participantId The identifier of the participant.
     * @param serviceId The identifier of the service.
     * @param serviceOperation The operation of the service. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.ServiceOperation}
     * @param serviceRole The role of the service. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.ServiceRole}
     * @param serviceStatus The status of the service. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.ServiceStatus}
     * @param serviceType The type of the service. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.ServiceType}
     * @param sortBy The field to sort by the results. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortByService}
     * @param sortOrder The sorting direction. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortOrder}
     * @return {@link ResponseDTOServiceDTO} containing a list of {@link ServiceDTO} and the total number of results.
     */
    ResponseDTOServiceDTO servicesRemoteGet(
          String dataFreshness, 
          List<String> dataLocation, 
          Integer limit, 
          Integer offset, 
          String participantCommunity, 
          String participantCountry, 
          String participantFunction, 
          String participantId, 
          String serviceId, 
          String serviceOperation, 
          String serviceRole, 
          String serviceStatus, 
          String serviceType, 
          String sortBy, 
          String sortOrder
    );

    /**
     * Get a filtered list of Participants for the remote nodes.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * @param limit The size of the results page. Minimum: 10, Maximum: 100, Default: 10.
     * @param participantCommunity The community of the participant. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.Community}
     * @param participantFunction The function of the participant. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.Function}
     * @param participantId The identifier of the participant.
     * @return A list of {@link UUIDDescriptionCountryDTO} containing participant details.
     */
    List<UUIDDescriptionCountryDTO> servicesRemoteSmartSearchParticipantsGet(
          Integer limit, 
          String participantCommunity, 
          String participantFunction, 
          String participantId
    );

    /**
     * Get a filtered list of Services for the remote nodes.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * @param limit The size of the results page. Minimum: 10, Maximum: 100, Default: 10.
     * @param serviceId The identifier of the service.
     * @return A list of service IDs.
     */
    List<String> servicesRemoteSmartSearchServicesGet(
          Integer limit, 
          String serviceId
    );

    /**
     * Get the number of Services in the remote nodes.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * @return {@link LongDTO} containing the number of remote Services.
     */
    LongDTO servicesRemoteStatsCountGet(
    );

    /**
     * Get a remote Service.
     *
     * REST API response codes: HTTP status 200 indicates a successful response. HTTP status 404 indicates the service was not found.
     *
     * service registry may throw: ResourceNotFoundException if the service with the specified UUID is not found.
     *
     * @param uuid UUID of the Service. Cannot be null or empty.
     * @return {@link ServiceDTO} containing the details of the remote Service.
     */
    ServiceDTO servicesRemoteUuidGet(
          UUID uuid
    );

    /**
     * Update local Service status.
     *
     * REST API response codes: HTTP status 204 indicates a successful operation. HTTP status 404 indicates the service was not found. HTTP status 400 indicates the request was not valid.
     *
     * service registry may throw: ResourceNotFoundException if the service with the specified UUID is not found.
     * service registry may throw: RequestInvalidException if the request is not valid.
     *
     * @param uuid UUID of the Service. Cannot be null or empty.
     * @param serviceStatusUpdateDTO {@link ServiceStatusUpdateDTO} containing the status update information. Cannot be null or empty.
     */
    void servicesStatusLocalUuidPut(
          UUID uuid, 
        ServiceStatusUpdateDTO serviceStatusUpdateDTO
    );

}
