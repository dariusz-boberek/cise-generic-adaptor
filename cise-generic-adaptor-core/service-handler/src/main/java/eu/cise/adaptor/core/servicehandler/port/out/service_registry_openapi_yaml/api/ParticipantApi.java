package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.api;

import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ContactPersonBaseWithUuidDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.LongDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ParticipantCreateDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ParticipantDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ParticipantUpdateDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ResponseDTOParticipantDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.UUIDDescriptionCountryDTO;

import java.util.List;
import java.util.UUID;



/**
 * CISE Service Registry Participant API
 * <p>Service Registry API for CISE Node</p>
 *
 * Reference package for API Enums {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums}
 *
 */
public interface ParticipantApi {

    /**
     * Get a list of participants with filtering.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * @param limit The size of the results page. Minimum: 10, Maximum: 100, Default: 10.
     * @param offset The starting entry of the page. Minimum: 0, Default: 0.
     * @param participantId The identifier of the Participant.
     * @param smart Indicates whether a smart search with distinct will be performed.
     * @param sortBy The field to sort by the results. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortByParticipant}
     * @param sortOrder The sorting direction. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortOrder}
     * @return {@link ResponseDTOParticipantDTO} containing a list of {@link ParticipantDTO} and the total number of results.
     */
    ResponseDTOParticipantDTO participantsGet(
          Integer limit, 
          Integer offset, 
          String participantId, 
          Boolean smart, 
          String sortBy, 
          String sortOrder
    );


    /**
     * Get a filtered list of Participants for the local node.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * @param authorityUUID UUID of the parent Authority.
     * @param community Community of the Participant (multiple). Maximum: 1000. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.Community}
     * @param function Function of the Participant (multiple). Maximum: 1000. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.Function}
     * @param limit The size of the results page. Minimum: 10, Maximum: 100, Default: 10.
     * @param name The name of the Participant (like).
     * @param offset The starting entry of the page. Minimum: 0, Default: 0.
     * @param participantId The identifier of the Participant.
     * @param sortBy The field to sort by the results. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortByParticipant}
     * @param sortOrder The sorting direction. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortOrder}
     * @return {@link ResponseDTOParticipantDTO} containing a list of {@link ParticipantDTO} and the total number of results.
     */
    ResponseDTOParticipantDTO participantsLocalGet(
          String authorityUUID, 
          List<String> community, 
          List<String> function, 
          Integer limit, 
          String name, 
          Integer offset, 
          String participantId, 
          String sortBy, 
          String sortOrder
    );

    /**
     * Get a list of Authorities for the local node.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * @return A list of {@link UUIDDescriptionCountryDTO} containing Authority details.
     */
    List<UUIDDescriptionCountryDTO> participantsLocalPicklistsAuthoritiesGet(
    );

    /**
     * Get a list of Contact Persons for the local authority.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * @param authorityUUID UUID of the local Authority. Cannot be null or empty.
     * @return A list of {@link ContactPersonBaseWithUuidDTO} containing contact person details.
     */
    List<ContactPersonBaseWithUuidDTO> participantsLocalPicklistsContactPersonsAuthorityUUIDGet(
          String authorityUUID
    );

    /**
     * Create a local participant.
     *
     * REST API response codes: HTTP status 201 indicates a successful operation. HTTP status 400 indicates the request was not valid.
     *
     * service registry may throw: RequestInvalidException if the request body is invalid.
     *
     * @param participantCreateDTO The data transfer object containing the participant details. Cannot be null or empty.
     */
    void participantsLocalPost(
        ParticipantCreateDTO participantCreateDTO
    );

    /**
     * Get the number of Participants in the local node.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * @return {@link LongDTO} containing the number of local Participants.
     */
    LongDTO participantsLocalStatsCountGet(
    );

    /**
     * Delete a local Participant.
     *
     * REST API response codes: HTTP status 204 indicates a successful operation. HTTP status 400 indicates the request was not valid. HTTP status 404 indicates the participant was not found.
     *
     * service registry may throw: ResourceNotFoundException if the participant with the specified UUID is not found.
     * service registry may throw: RequestInvalidException if the UUID format is invalid.
     *
     * @param uuid The unique identifier of the Participant. Cannot be null or empty.
     */
    void participantsLocalUuidDelete(
          UUID uuid
    );

    /**
     * Get a local Participant.
     *
     * REST API response codes: HTTP status 200 indicates a successful response. HTTP status 404 indicates the participant was not found.
     *
     * service registry may throw: ResourceNotFoundException if the participant with the specified UUID is not found.
     *
     * @param uuid The unique identifier of the Participant. Cannot be null or empty.
     * @return {@link ParticipantDTO} containing the participant details.
     */
    ParticipantDTO participantsLocalUuidGet(
          UUID uuid
    );

    /**
     * Update a local Participant.
     *
     * REST API response codes: HTTP status 204 indicates a successful operation. HTTP status 400 indicates the request was not valid. HTTP status 404 indicates the participant was not found.
     *
     * service registry may throw: ResourceNotFoundException if the participant with the specified UUID is not found.
     * service registry may throw: RequestInvalidException if the UUID format or request body is invalid.
     *
     * @param uuid The unique identifier of the Participant. Cannot be null or empty.
     * @param participantUpdateDTO The data transfer object containing the updated participant details. Cannot be null or empty.
     */
    void participantsLocalUuidPut(
          UUID uuid, 
        ParticipantUpdateDTO participantUpdateDTO
    );

    /**
     * Get a list of remote participants with filtering.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * @param community Community of the Participant (multiple). Maximum: 1000. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.Community}
     * @param country Country of the parent Authority (multiple). Maximum: 1000. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.Country}
     * @param function Function of the Participant (multiple). Maximum: 1000. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.Function}
     * @param limit The size of the results page. Minimum: 10, Maximum: 100, Default: 10.
     * @param name Name of the Participant (like).
     * @param nodeId The identifier of the Node.
     * @param offset The starting entry of the page. Minimum: 0, Default: 0.
     * @param participantId The identifier of the Participant.
     * @param sortBy The field to sort by the results. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortByParticipant}
     * @param sortOrder The sorting direction. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortOrder}
     * @return {@link ResponseDTOParticipantDTO} containing a list of {@link ParticipantDTO} and the total number of results.
     */
    ResponseDTOParticipantDTO participantsRemoteGet(
          List<String> community, 
          List<String> country, 
          List<String> function, 
          Integer limit, 
          String name, 
          String nodeId, 
          Integer offset, 
          String participantId, 
          String sortBy, 
          String sortOrder
    );

    /**
     * Get the number of Participants in the remote nodes.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * @return {@link LongDTO} containing the number of remote Participants.
     */
    LongDTO participantsRemoteStatsCountGet(
    );

    /**
     * Get a remote Participant.
     *
     * REST API response codes: HTTP status 200 indicates a successful response. HTTP status 404 indicates the participant was not found.
     *
     * service registry may throw: ResourceNotFoundException if the participant with the specified UUID is not found.
     *
     * @param uuid UUID of the remote Participant. Cannot be null or empty.
     * @return {@link ParticipantDTO} containing the participant details.
     */
    ParticipantDTO participantsRemoteUuidGet(
          UUID uuid
    );

}
