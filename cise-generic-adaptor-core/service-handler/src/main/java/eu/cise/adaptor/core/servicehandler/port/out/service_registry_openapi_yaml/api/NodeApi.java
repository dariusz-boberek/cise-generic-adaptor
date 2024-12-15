package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.api;

import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ContactPersonBaseWithUuidDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.NodeBaseDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.NodeConfigurationCreateDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.NodeConfigurationDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.NodeConfigurationUpdateDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.NodeDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ResponseDTONodeDTO;

import java.util.List;
import java.util.UUID;



/**
 * CISE Service Registry Node API
 * <p>Service Registry API for CISE Node</p>
 *
 * Reference package for API Enums {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums}
 *
 */
public interface NodeApi {

    /**
     * Get the detailed configuration of the node running the cise node application.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * service registry may throw: InternalServerErrorException if there is an error retrieving the node configuration.
     *
     * @return {@link NodeConfigurationDTO} containing the metadata of the node hosting the cise application.
     */
    NodeConfigurationDTO nodeConfigGet(
    );

    /**
     * Creates the detailed configuration of the node running the cise node application.
     *
     * REST API response codes: HTTP status 201 indicates a successful creation. The Location header contains the URI of the created resource. HTTP status 400 indicates a bad request.
     *
     * service registry may throw: RequestInvalidException if the request is not valid.
     * service registry may throw: InternalServerErrorException if there is an error creating the node configuration.
     *
     * @param nodeConfigurationCreateDTO The configuration details to create. Cannot be null or empty.
     */
    void nodeConfigPost(
        NodeConfigurationCreateDTO nodeConfigurationCreateDTO
    );

    /**
     * Updates the configuration of the node running the cise node application.
     *
     * REST API response codes: HTTP status 204 indicates a successful update. HTTP status 400 indicates a bad request.
     *
     * service registry may throw: RequestInvalidException if the request is not valid.
     * service registry may throw: InternalServerErrorException if there is an error updating the node configuration.
     *
     * @param nodeConfigurationUpdateDTO The configuration details to update. Cannot be null or empty.
     */
    void nodeConfigPut(
        NodeConfigurationUpdateDTO nodeConfigurationUpdateDTO
    );

    /**
     * Triggers sync pull for the node running the cise node application.
     *
     * REST API response codes: HTTP status 204 indicates a successful operation.
     *
     * service registry may throw: InternalServerErrorException if there is an error triggering the sync pull.
     */
    void nodeConfigTriggerSyncPullPut(
    );

    /**
     * Get the metadata of the node hosting the cise application.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * service registry may throw: InternalServerErrorException if there is an error retrieving the node metadata.
     *
     * @return {@link NodeDTO} containing the metadata of the node.
     */
    NodeDTO nodeGet(
    );

    /**
     * Get a list of Contact Persons for the local authority.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * @param authorityUUID UUID of the local authority. Cannot be null or empty. Must match pattern: [a-f0-9]{8}(\\-[a-f0-9]{4}){3}\\-[a-f0-9]{12}.
     * @return List of {@link ContactPersonBaseWithUuidDTO}.
     */
    List<ContactPersonBaseWithUuidDTO> nodePicklistsContactPersonsAuthorityUUIDGet(
          String authorityUUID
    );

    /**
     * Get a list of remote nodes with filtering.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * @param sortBy The field to sort by the results. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortByNode}
     * @param sortOrder The sorting direction. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortOrder}
     * @return {@link ResponseDTONodeDTO} containing a list of remote nodes.
     */
    ResponseDTONodeDTO nodesRemoteGet(
          String sortBy, 
          String sortOrder
    );

    /**
     * Create a remote Node reference in the local Node.
     *
     * REST API response codes: HTTP status 201 indicates a successful creation. The Location header contains the URI of the created resource. HTTP status 400 indicates a bad request.
     *
     * service registry may throw: RequestInvalidException if the request is not valid.
     * service registry may throw: InternalServerErrorException if there is an error creating the remote Node reference.
     *
     * @param nodeBaseDTO The details of the remote Node to create. Cannot be null or empty.
     */
    void nodesRemotePost(
        NodeBaseDTO nodeBaseDTO
    );

    /**
     * Delete a local reference of a remote Node.
     *
     * REST API response codes: HTTP status 204 indicates a successful deletion. HTTP status 400 indicates a bad request. HTTP status 404 indicates the resource was not found.
     *
     * service registry may throw: RequestInvalidException if the request is not valid.
     * service registry may throw: InternalServerErrorException if there is an error deleting the remote Node reference.
     *
     * @param uuid UUID of the remote Node. Cannot be null or empty.
     */
    void nodesRemoteUuidDelete(
          UUID uuid
    );

    /**
     * Get a local reference of a remote Node.
     *
     * REST API response codes: HTTP status 200 indicates a successful response. HTTP status 404 indicates the resource was not found.
     *
     * service registry may throw: RequestInvalidException if the request is not valid.
     * service registry may throw: InternalServerErrorException if there is an error retrieving the remote Node reference.
     *
     * @param uuid UUID of the remote Node. Cannot be null or empty.
     * @return {@link NodeDTO} containing the metadata of the remote Node.
     */
    NodeDTO nodesRemoteUuidGet(
          UUID uuid
    );

    /**
     * Update a local reference of a remote Node.
     *
     * REST API response codes: HTTP status 204 indicates a successful update. HTTP status 400 indicates a bad request. HTTP status 404 indicates the resource was not found.
     *
     * service registry may throw: RequestInvalidException if the request is not valid.
     * service registry may throw: InternalServerErrorException if there is an error updating the remote Node reference.
     *
     * @param uuid UUID of the remote Node. Cannot be null or empty.
     * @param nodeBaseDTO The details of the remote Node to update. Cannot be null or empty.
     */
    void nodesRemoteUuidPut(
          UUID uuid, 
        NodeBaseDTO nodeBaseDTO
    );

}
