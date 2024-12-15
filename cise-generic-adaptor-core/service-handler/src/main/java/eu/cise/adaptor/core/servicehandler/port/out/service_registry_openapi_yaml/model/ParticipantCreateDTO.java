package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParticipantCreateDTO  {

    private String classification;
    private String description;
    private String endpointProtocol;
    private String endpointUrl;
    private String healthEndpointUrl;
    private List<String> communities = new ArrayList<>();
    private List<String> functions = new ArrayList<>();
    private List<String> areasOfInterest = new ArrayList<>();
    private UUID authorityUUID;
    private List<UUID> registeredContactPersonUUIDs = null;
    private List<ContactPersonBaseDTO> newContactPersons = null;
    private String name;

    @JsonProperty("classification")
    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public ParticipantCreateDTO classification(String classification) {
        this.classification = classification;
        return this;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ParticipantCreateDTO description(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("endpointProtocol")
    public String getEndpointProtocol() {
        return endpointProtocol;
    }

    public void setEndpointProtocol(String endpointProtocol) {
        this.endpointProtocol = endpointProtocol;
    }

    public ParticipantCreateDTO endpointProtocol(String endpointProtocol) {
        this.endpointProtocol = endpointProtocol;
        return this;
    }

    @JsonProperty("endpointUrl")
    public String getEndpointUrl() {
        return endpointUrl;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public ParticipantCreateDTO endpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
        return this;
    }

    @JsonProperty("healthEndpointUrl")
    public String getHealthEndpointUrl() {
        return healthEndpointUrl;
    }

    public void setHealthEndpointUrl(String healthEndpointUrl) {
        this.healthEndpointUrl = healthEndpointUrl;
    }

    public ParticipantCreateDTO healthEndpointUrl(String healthEndpointUrl) {
        this.healthEndpointUrl = healthEndpointUrl;
        return this;
    }

    @JsonProperty("communities")
    public List<String> getCommunities() {
        return communities;
    }

    public void setCommunities(List<String> communities) {
        this.communities = communities;
    }

    public ParticipantCreateDTO communities(List<String> communities) {
        this.communities = communities;
        return this;
    }
    public ParticipantCreateDTO addCommunitiesItem(String communitiesItem) {
        this.communities.add(communitiesItem);
        return this;
    }

    @JsonProperty("functions")
    public List<String> getFunctions() {
        return functions;
    }

    public void setFunctions(List<String> functions) {
        this.functions = functions;
    }

    public ParticipantCreateDTO functions(List<String> functions) {
        this.functions = functions;
        return this;
    }
    public ParticipantCreateDTO addFunctionsItem(String functionsItem) {
        this.functions.add(functionsItem);
        return this;
    }

    @JsonProperty("areasOfInterest")
    public List<String> getAreasOfInterest() {
        return areasOfInterest;
    }

    public void setAreasOfInterest(List<String> areasOfInterest) {
        this.areasOfInterest = areasOfInterest;
    }

    public ParticipantCreateDTO areasOfInterest(List<String> areasOfInterest) {
        this.areasOfInterest = areasOfInterest;
        return this;
    }
    public ParticipantCreateDTO addAreasOfInterestItem(String areasOfInterestItem) {
        this.areasOfInterest.add(areasOfInterestItem);
        return this;
    }

    @JsonProperty("authorityUUID")
    public UUID getAuthorityUUID() {
        return authorityUUID;
    }

    public void setAuthorityUUID(UUID authorityUUID) {
        this.authorityUUID = authorityUUID;
    }

    public ParticipantCreateDTO authorityUUID(UUID authorityUUID) {
        this.authorityUUID = authorityUUID;
        return this;
    }

    @JsonProperty("registeredContactPersonUUIDs")
    public List<UUID> getRegisteredContactPersonUUIDs() {
        return registeredContactPersonUUIDs;
    }

    public void setRegisteredContactPersonUUIDs(List<UUID> registeredContactPersonUUIDs) {
        this.registeredContactPersonUUIDs = registeredContactPersonUUIDs;
    }

    public ParticipantCreateDTO registeredContactPersonUUIDs(List<UUID> registeredContactPersonUUIDs) {
        this.registeredContactPersonUUIDs = registeredContactPersonUUIDs;
        return this;
    }
    public ParticipantCreateDTO addRegisteredContactPersonUUIDsItem(UUID registeredContactPersonUUIDsItem) {
        this.registeredContactPersonUUIDs.add(registeredContactPersonUUIDsItem);
        return this;
    }

    @JsonProperty("newContactPersons")
    public List<ContactPersonBaseDTO> getNewContactPersons() {
        return newContactPersons;
    }

    public void setNewContactPersons(List<ContactPersonBaseDTO> newContactPersons) {
        this.newContactPersons = newContactPersons;
    }

    public ParticipantCreateDTO newContactPersons(List<ContactPersonBaseDTO> newContactPersons) {
        this.newContactPersons = newContactPersons;
        return this;
    }
    public ParticipantCreateDTO addNewContactPersonsItem(ContactPersonBaseDTO newContactPersonsItem) {
        this.newContactPersons.add(newContactPersonsItem);
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParticipantCreateDTO name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ParticipantCreateDTO {\n");

        sb.append("    classification: ").append(toIndentedString(classification)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    endpointProtocol: ").append(toIndentedString(endpointProtocol)).append("\n");
        sb.append("    endpointUrl: ").append(toIndentedString(endpointUrl)).append("\n");
        sb.append("    healthEndpointUrl: ").append(toIndentedString(healthEndpointUrl)).append("\n");
        sb.append("    communities: ").append(toIndentedString(communities)).append("\n");
        sb.append("    functions: ").append(toIndentedString(functions)).append("\n");
        sb.append("    areasOfInterest: ").append(toIndentedString(areasOfInterest)).append("\n");
        sb.append("    authorityUUID: ").append(toIndentedString(authorityUUID)).append("\n");
        sb.append("    registeredContactPersonUUIDs: ").append(toIndentedString(registeredContactPersonUUIDs)).append("\n");
        sb.append("    newContactPersons: ").append(toIndentedString(newContactPersons)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private static String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ParticipantCreateDTOQueryParam  {

        private String classification;
        private String description;
        private String endpointProtocol;
        private String endpointUrl;
        private String healthEndpointUrl;
        private List<String> communities = new ArrayList<>();
        private List<String> functions = new ArrayList<>();
        private List<String> areasOfInterest = new ArrayList<>();
        private UUID authorityUUID;
        private List<UUID> registeredContactPersonUUIDs = null;
        private List<ContactPersonBaseDTO> newContactPersons = null;
        private String name;

        @JsonProperty("classification")
        public String getClassification() {
            return classification;
        }

        public void setClassification(String classification) {
            this.classification = classification;
        }

        public ParticipantCreateDTOQueryParam classification(String classification) {
            this.classification = classification;
            return this;
        }

        @JsonProperty("description")
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public ParticipantCreateDTOQueryParam description(String description) {
            this.description = description;
            return this;
        }

        @JsonProperty("endpointProtocol")
        public String getEndpointProtocol() {
            return endpointProtocol;
        }

        public void setEndpointProtocol(String endpointProtocol) {
            this.endpointProtocol = endpointProtocol;
        }

        public ParticipantCreateDTOQueryParam endpointProtocol(String endpointProtocol) {
            this.endpointProtocol = endpointProtocol;
            return this;
        }

        @JsonProperty("endpointUrl")
        public String getEndpointUrl() {
            return endpointUrl;
        }

        public void setEndpointUrl(String endpointUrl) {
            this.endpointUrl = endpointUrl;
        }

        public ParticipantCreateDTOQueryParam endpointUrl(String endpointUrl) {
            this.endpointUrl = endpointUrl;
            return this;
        }

        @JsonProperty("healthEndpointUrl")
        public String getHealthEndpointUrl() {
            return healthEndpointUrl;
        }

        public void setHealthEndpointUrl(String healthEndpointUrl) {
            this.healthEndpointUrl = healthEndpointUrl;
        }

        public ParticipantCreateDTOQueryParam healthEndpointUrl(String healthEndpointUrl) {
            this.healthEndpointUrl = healthEndpointUrl;
            return this;
        }

        @JsonProperty("communities")
        public List<String> getCommunities() {
            return communities;
        }

        public void setCommunities(List<String> communities) {
            this.communities = communities;
        }

        public ParticipantCreateDTOQueryParam communities(List<String> communities) {
            this.communities = communities;
            return this;
        }
        public ParticipantCreateDTOQueryParam addCommunitiesItem(String communitiesItem) {
            this.communities.add(communitiesItem);
            return this;
        }

        @JsonProperty("functions")
        public List<String> getFunctions() {
            return functions;
        }

        public void setFunctions(List<String> functions) {
            this.functions = functions;
        }

        public ParticipantCreateDTOQueryParam functions(List<String> functions) {
            this.functions = functions;
            return this;
        }
        public ParticipantCreateDTOQueryParam addFunctionsItem(String functionsItem) {
            this.functions.add(functionsItem);
            return this;
        }

        @JsonProperty("areasOfInterest")
        public List<String> getAreasOfInterest() {
            return areasOfInterest;
        }

        public void setAreasOfInterest(List<String> areasOfInterest) {
            this.areasOfInterest = areasOfInterest;
        }

        public ParticipantCreateDTOQueryParam areasOfInterest(List<String> areasOfInterest) {
            this.areasOfInterest = areasOfInterest;
            return this;
        }
        public ParticipantCreateDTOQueryParam addAreasOfInterestItem(String areasOfInterestItem) {
            this.areasOfInterest.add(areasOfInterestItem);
            return this;
        }

        @JsonProperty("authorityUUID")
        public UUID getAuthorityUUID() {
            return authorityUUID;
        }

        public void setAuthorityUUID(UUID authorityUUID) {
            this.authorityUUID = authorityUUID;
        }

        public ParticipantCreateDTOQueryParam authorityUUID(UUID authorityUUID) {
            this.authorityUUID = authorityUUID;
            return this;
        }

        @JsonProperty("registeredContactPersonUUIDs")
        public List<UUID> getRegisteredContactPersonUUIDs() {
            return registeredContactPersonUUIDs;
        }

        public void setRegisteredContactPersonUUIDs(List<UUID> registeredContactPersonUUIDs) {
            this.registeredContactPersonUUIDs = registeredContactPersonUUIDs;
        }

        public ParticipantCreateDTOQueryParam registeredContactPersonUUIDs(List<UUID> registeredContactPersonUUIDs) {
            this.registeredContactPersonUUIDs = registeredContactPersonUUIDs;
            return this;
        }
        public ParticipantCreateDTOQueryParam addRegisteredContactPersonUUIDsItem(UUID registeredContactPersonUUIDsItem) {
            this.registeredContactPersonUUIDs.add(registeredContactPersonUUIDsItem);
            return this;
        }

        @JsonProperty("newContactPersons")
        public List<ContactPersonBaseDTO> getNewContactPersons() {
            return newContactPersons;
        }

        public void setNewContactPersons(List<ContactPersonBaseDTO> newContactPersons) {
            this.newContactPersons = newContactPersons;
        }

        public ParticipantCreateDTOQueryParam newContactPersons(List<ContactPersonBaseDTO> newContactPersons) {
            this.newContactPersons = newContactPersons;
            return this;
        }
        public ParticipantCreateDTOQueryParam addNewContactPersonsItem(ContactPersonBaseDTO newContactPersonsItem) {
            this.newContactPersons.add(newContactPersonsItem);
            return this;
        }

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ParticipantCreateDTOQueryParam name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class ParticipantCreateDTOQueryParam {\n");

            sb.append("    classification: ").append(toIndentedString(classification)).append("\n");
            sb.append("    description: ").append(toIndentedString(description)).append("\n");
            sb.append("    endpointProtocol: ").append(toIndentedString(endpointProtocol)).append("\n");
            sb.append("    endpointUrl: ").append(toIndentedString(endpointUrl)).append("\n");
            sb.append("    healthEndpointUrl: ").append(toIndentedString(healthEndpointUrl)).append("\n");
            sb.append("    communities: ").append(toIndentedString(communities)).append("\n");
            sb.append("    functions: ").append(toIndentedString(functions)).append("\n");
            sb.append("    areasOfInterest: ").append(toIndentedString(areasOfInterest)).append("\n");
            sb.append("    authorityUUID: ").append(toIndentedString(authorityUUID)).append("\n");
            sb.append("    registeredContactPersonUUIDs: ").append(toIndentedString(registeredContactPersonUUIDs)).append("\n");
            sb.append("    newContactPersons: ").append(toIndentedString(newContactPersons)).append("\n");
            sb.append("    name: ").append(toIndentedString(name)).append("\n");
            sb.append("}");
            return sb.toString();
        }

        private static String toIndentedString(Object o) {
            if (o == null) {
                return "null";
            }
            return o.toString().replace("\n", "\n    ");
        }
    }

}