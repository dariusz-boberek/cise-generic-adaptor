package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.api;

import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.AuthorityBaseDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.AuthorityDetailsDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ResponseDTOAuthorityDTO;

import java.util.List;
import java.util.UUID;



/**
 * CISE Service Registry AuthorityAPI
 * <p>Service Registry API for CISE Node</p>
 *
 * Reference package for API Enums {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums}
 *
 */
public interface AuthorityApi {

    /**
     * Get a filtered list of Authorities for the local node.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * @param community Community of the Authority (multiple). Maximum: 1000.
     * @param country Country of the Authority (multiple). Maximum: 1000.
     * @param limit The size of the results page. Minimum: 10, Maximum: 100, Default: 10.
     * @param name Name of the Authority (like).
     * @param offset The starting entry of the page. Minimum: 0, Default: 0.
     * @param sortBy The field to sort by the results. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortByAuthority}
     * @param sortOrder The sorting direction. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortOrder}
     * @return {@link ResponseDTOAuthorityDTO} containing a list of authorities.
     */
    ResponseDTOAuthorityDTO authoritiesLocalGet(
          List<String> community, 
          List<String> country, 
          Integer limit, 
          String name, 
          Integer offset, 
          String sortBy, 
          String sortOrder
    );

    /**
     * Create a local authority.
     *
     * REST API response codes: HTTP status 201 indicates a successful creation. The Location header contains the URI of the created resource. HTTP status 400 indicates a bad request.
     *
     * service registry may throw: RequestInvalidException if the request is not valid.
     * service registry may throw: InternalServerErrorException if there is an error creating the authority.
     *
     * @param authorityBaseDTO The details of the Authority to create. Cannot be null or empty.
     */
    void authoritiesLocalPost(
        AuthorityBaseDTO authorityBaseDTO
    );

    /**
     * Delete a local authority.
     *
     * REST API response codes:  HTTP status 204 indicates a successful deletion. HTTP status 400 indicates a bad request. HTTP status 404 indicates the resource was not found.
     *
     * service registry may throw: RequestInvalidException if the request is not valid.
     * service registry may throw: InternalServerErrorException if there is an error deleting the authority.
     *
     * @param uuid UUID of the authority. Cannot be null or empty.
     */
    void authoritiesLocalUuidDelete(
          UUID uuid
    );

    /**
     * Get a local authority.
     *
     * REST API response codes: HTTP status 200 indicates a successful response. HTTP status 404 indicates the resource was not found.
     *
     * service registry may throw: RequestInvalidException if the request is not valid.
     * service registry may throw: InternalServerErrorException if there is an error retrieving the authority.
     *
     * @param uuid UUID of the authority. Cannot be null or empty.
     * @return {@link AuthorityDetailsDTO} containing the details of the authority.
     */
    AuthorityDetailsDTO authoritiesLocalUuidGet(
          UUID uuid
    );

    /**
     * Get a local authority.
     *
     * REST API response codes: HTTP status 200 indicates a successful response. HTTP status 404 indicates the resource was not found.
     *
     * service registry may throw: RequestInvalidException if the request is not valid.
     * service registry may throw: InternalServerErrorException if there is an error retrieving the authority.
     *
     * @param uuid UUID of the authority. Cannot be null or empty.
     * @param authorityBaseDTO The details of the Authority to create. Cannot be null or empty.
     */
    void authoritiesLocalUuidPut(
          UUID uuid, 
        AuthorityBaseDTO authorityBaseDTO
    );

    /**
     * Get a filtered list of Authorities for the remote nodes.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * @param community Community of the Authority (multiple). Maximum: 1000. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.Community}
     * @param country Country of the Authority (multiple). Maximum: 1000. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.Country}
     * @param limit The size of the results page. Minimum: 10, Maximum: 100, Default: 10.
     * @param name Name of the Authority (like).
     * @param offset The starting entry of the page. Minimum: 0, Default: 0.
     * @param sortBy The field to sort by the results. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortByAuthority}
     * @param sortOrder The sorting direction. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortOrder}
     * @return {@link ResponseDTOAuthorityDTO} containing a list of authorities.
     */
    ResponseDTOAuthorityDTO authoritiesRemoteGet(
          List<String> community, 
          List<String> country, 
          Integer limit, 
          String name, 
          Integer offset, 
          String sortBy, 
          String sortOrder
    );



    /**
     * Get a remote authority.
     *
     * REST API response codes: HTTP status 200 indicates a successful response. HTTP status 404 indicates the resource was not found.
     *
     * service registry may throw: RequestInvalidException if the request is not valid.
     * service registry may throw: InternalServerErrorException if there is an error retrieving the authority.
     *
     * @param uuid UUID of the authority. Cannot be null or empty.
     * @return {@link AuthorityDetailsDTO} containing the details of the authority.
     */
    AuthorityDetailsDTO authoritiesRemoteUuidGet(
          UUID uuid
    );

}
