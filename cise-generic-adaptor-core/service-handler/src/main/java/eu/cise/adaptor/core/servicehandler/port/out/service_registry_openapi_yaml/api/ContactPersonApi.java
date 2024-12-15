package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.api;

import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ContactPersonBaseDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ContactPersonCreateDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ContactPersonDTO;
import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ResponseDTOContactPersonDTO;

import java.util.List;
import java.util.UUID;



/**
 * CISE Service Registry ContactPersonAPI
 * <p>Service Registry API for CISE Node</p>
 *
 * Reference package for API Enums {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums}
 *
 */
public interface ContactPersonApi {

    /**
     * Get a filtered list of Contact Persons for the local node.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * @param authorityUUID The UUID of the parent Authority. Maximum: 1000.
     * @param contactFor contactFor param. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.ContactFor}
     * @param country The Country of the parent Authority. Maximum: 1000. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.Country}
     * @param email Email of the Contact Person.
     * @param limit The size of the results page. Minimum: 10, Maximum: 100, Default: 10.
     * @param nameSurname Name and Surname of the Contact Person.
     * @param offset The starting entry of the page. Minimum: 0, Default: 0.
     * @param sortBy The field to sort by the results. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortByContactPerson}
     * @param sortOrder The sorting direction. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortOrder}
     * @return {@link ResponseDTOContactPersonDTO} containing a list of contact persons.
     */
    ResponseDTOContactPersonDTO contactPersonsLocalGet(
          List<String> authorityUUID, 
          List<String> contactFor, 
          List<String> country, 
          String email, 
          Integer limit, 
          String nameSurname, 
          Integer offset, 
          String sortBy, 
          String sortOrder
    );

    /**
     * Get a list of Authorities for the local node.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     */
    void contactPersonsLocalPicklistsAuthoritiesGet(
    );

    /**
     * Create a local Contact Person.
     *
     * REST API response codes: HTTP status 201 indicates a successful creation. The Location header contains the URI of the created resource. HTTP status 400 indicates a bad request.
     *
     * service registry may throw: RequestInvalidException if the request is not valid.
     * service registry may throw: InternalServerErrorException if there is an error creating the contact person.
     *
     * @param contactPersonCreateDTO The details of the contact person to create. Cannot be null or empty.
     */
    void contactPersonsLocalPost(
        ContactPersonCreateDTO contactPersonCreateDTO
    );

    /**
     * Delete a local Contact Person.
     *
     * REST API response codes: HTTP status 204 indicates a successful deletion. HTTP status 400 indicates a bad request. HTTP status 404 indicates the resource was not found.
     *
     * service registry may throw: RequestInvalidException if the request is not valid.
     * service registry may throw: InternalServerErrorException if there is an error deleting the contact person.
     *
     * @param uuid UUID of the local Contact Person. Cannot be null or empty.
     */
    void contactPersonsLocalUuidDelete(
          UUID uuid
    );

    /**
     * Get a local Contact Person.
     *
     * REST API response codes: HTTP status 200 indicates a successful response. HTTP status 404 indicates the resource was not found.
     *
     * service registry may throw: RequestInvalidException if the request is not valid.
     * service registry may throw: InternalServerErrorException if there is an error retrieving the contact person.
     *
     * @param uuid UUID of the local Contact Person. Cannot be null or empty.
     * @return {@link ContactPersonDTO} containing the details of the contact person.
     */
    ContactPersonDTO contactPersonsLocalUuidGet(
          UUID uuid
    );

    /**
     * Update a local Contact Person.
     *
     * REST API response codes: HTTP status 204 indicates a successful update. HTTP status 400 indicates a bad request. HTTP status 404 indicates the resource was not found.
     *
     * service registry may throw: RequestInvalidException if the request is not valid.
     * service registry may throw: InternalServerErrorException if there is an error updating the contact person.
     *
     * @param uuid UUID of the local Contact Person. Cannot be null or empty.
     * @param contactPersonBaseDTO The details of the contact person to update. Cannot be null or empty.
     */
    void contactPersonsLocalUuidPut(
          UUID uuid, 
        ContactPersonBaseDTO contactPersonBaseDTO
    );

    /**
     * Get a filtered list of Contact Persons for the remote nodes.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     *
     * @param authorityUUID The UUID of the parent Authority. Maximum: 1000.
     * @param contactFor contactFor param. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.ContactFor}
     * @param country Country of the Contact Person. Maximum: 1000. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.Country}
     * @param email Email of the Contact Person.
     * @param limit The size of the results page. Minimum: 10, Maximum: 100, Default: 10.
     * @param nameSurname Name and Surname of the Contact Person.
     * @param offset The starting entry of the page. Minimum: 0, Default: 0.
     * @param sortBy The field to sort by the results. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortByContactPerson}
     * @param sortOrder The sorting direction. Enum that can be used: {@link  eu.cise.adaptor.core.servicehandler.port.out.api.enums.SortOrder}
     * @return {@link ResponseDTOContactPersonDTO} containing a list of contact persons.
     */
    ResponseDTOContactPersonDTO contactPersonsRemoteGet(
          List<String> authorityUUID, 
          List<String> contactFor, 
          List<String> country, 
          String email, 
          Integer limit, 
          String nameSurname, 
          Integer offset, 
          String sortBy, 
          String sortOrder
    );

    /**
     * Get a list of Authorities for the remote nodes.
     *
     * REST API response codes: HTTP status 200 indicates a successful response.
     */
    void contactPersonsRemotePicklistsAuthoritiesGet(
    );

    /**
     * Get a remote Contact Person.
     *
     * REST API response codes: HTTP status 200 indicates a successful response. HTTP status 404 indicates the resource was not found.
     *
     * service registry may throw: RequestInvalidException if the request is not valid.
     * service registry may throw: InternalServerErrorException if there is an error retrieving the contact person.
     *
     * @param uuid UUID of the remote Contact Person. Cannot be null or empty.
     * @return {@link ContactPersonDTO} containing the details of the contact person.
     */
    ContactPersonDTO contactPersonsRemoteUuidGet(
          UUID uuid
    );

}
