package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParticipantDTO  {

    private String classification;
    private String description;
    private String endpointProtocol;
    private String endpointUrl;
    private String healthEndpointUrl;
    private List<String> communities = new ArrayList<>();
    private List<String> functions = new ArrayList<>();
    private List<String> areasOfInterest = new ArrayList<>();
    private String uuid;
    private String name;
    private String participantId;
    private String nodeId;
    private NodeDTOManagingAuthority authority;
    private List<ContactPersonDTO> contactPersons = null;

    @JsonProperty("classification")
    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public ParticipantDTO classification(String classification) {
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

    public ParticipantDTO description(String description) {
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

    public ParticipantDTO endpointProtocol(String endpointProtocol) {
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

    public ParticipantDTO endpointUrl(String endpointUrl) {
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

    public ParticipantDTO healthEndpointUrl(String healthEndpointUrl) {
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

    public ParticipantDTO communities(List<String> communities) {
        this.communities = communities;
        return this;
    }
    public ParticipantDTO addCommunitiesItem(String communitiesItem) {
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

    public ParticipantDTO functions(List<String> functions) {
        this.functions = functions;
        return this;
    }
    public ParticipantDTO addFunctionsItem(String functionsItem) {
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

    public ParticipantDTO areasOfInterest(List<String> areasOfInterest) {
        this.areasOfInterest = areasOfInterest;
        return this;
    }
    public ParticipantDTO addAreasOfInterestItem(String areasOfInterestItem) {
        this.areasOfInterest.add(areasOfInterestItem);
        return this;
    }

    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ParticipantDTO uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParticipantDTO name(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("participantId")
    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public ParticipantDTO participantId(String participantId) {
        this.participantId = participantId;
        return this;
    }

    @JsonProperty("nodeId")
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public ParticipantDTO nodeId(String nodeId) {
        this.nodeId = nodeId;
        return this;
    }

    @JsonProperty("authority")
    public NodeDTOManagingAuthority getAuthority() {
        return authority;
    }

    public void setAuthority(NodeDTOManagingAuthority authority) {
        this.authority = authority;
    }

    public ParticipantDTO authority(NodeDTOManagingAuthority authority) {
        this.authority = authority;
        return this;
    }

    @JsonProperty("contactPersons")
    public List<ContactPersonDTO> getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(List<ContactPersonDTO> contactPersons) {
        this.contactPersons = contactPersons;
    }

    public ParticipantDTO contactPersons(List<ContactPersonDTO> contactPersons) {
        this.contactPersons = contactPersons;
        return this;
    }
    public ParticipantDTO addContactPersonsItem(ContactPersonDTO contactPersonsItem) {
        this.contactPersons.add(contactPersonsItem);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ParticipantDTO {\n");

        sb.append("    classification: ").append(toIndentedString(classification)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    endpointProtocol: ").append(toIndentedString(endpointProtocol)).append("\n");
        sb.append("    endpointUrl: ").append(toIndentedString(endpointUrl)).append("\n");
        sb.append("    healthEndpointUrl: ").append(toIndentedString(healthEndpointUrl)).append("\n");
        sb.append("    communities: ").append(toIndentedString(communities)).append("\n");
        sb.append("    functions: ").append(toIndentedString(functions)).append("\n");
        sb.append("    areasOfInterest: ").append(toIndentedString(areasOfInterest)).append("\n");
        sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    participantId: ").append(toIndentedString(participantId)).append("\n");
        sb.append("    nodeId: ").append(toIndentedString(nodeId)).append("\n");
        sb.append("    authority: ").append(toIndentedString(authority)).append("\n");
        sb.append("    contactPersons: ").append(toIndentedString(contactPersons)).append("\n");
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
    public static class ParticipantDTOQueryParam  {

        private String classification;
        private String description;
        private String endpointProtocol;
        private String endpointUrl;
        private String healthEndpointUrl;
        private List<String> communities = new ArrayList<>();
        private List<String> functions = new ArrayList<>();
        private List<String> areasOfInterest = new ArrayList<>();
        private String uuid;
        private String name;
        private String participantId;
        private String nodeId;
        private NodeDTOManagingAuthority authority;
        private List<ContactPersonDTO> contactPersons = null;

        @JsonProperty("classification")
        public String getClassification() {
            return classification;
        }

        public void setClassification(String classification) {
            this.classification = classification;
        }

        public ParticipantDTOQueryParam classification(String classification) {
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

        public ParticipantDTOQueryParam description(String description) {
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

        public ParticipantDTOQueryParam endpointProtocol(String endpointProtocol) {
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

        public ParticipantDTOQueryParam endpointUrl(String endpointUrl) {
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

        public ParticipantDTOQueryParam healthEndpointUrl(String healthEndpointUrl) {
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

        public ParticipantDTOQueryParam communities(List<String> communities) {
            this.communities = communities;
            return this;
        }
        public ParticipantDTOQueryParam addCommunitiesItem(String communitiesItem) {
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

        public ParticipantDTOQueryParam functions(List<String> functions) {
            this.functions = functions;
            return this;
        }
        public ParticipantDTOQueryParam addFunctionsItem(String functionsItem) {
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

        public ParticipantDTOQueryParam areasOfInterest(List<String> areasOfInterest) {
            this.areasOfInterest = areasOfInterest;
            return this;
        }
        public ParticipantDTOQueryParam addAreasOfInterestItem(String areasOfInterestItem) {
            this.areasOfInterest.add(areasOfInterestItem);
            return this;
        }

        @JsonProperty("uuid")
        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public ParticipantDTOQueryParam uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ParticipantDTOQueryParam name(String name) {
            this.name = name;
            return this;
        }

        @JsonProperty("participantId")
        public String getParticipantId() {
            return participantId;
        }

        public void setParticipantId(String participantId) {
            this.participantId = participantId;
        }

        public ParticipantDTOQueryParam participantId(String participantId) {
            this.participantId = participantId;
            return this;
        }

        @JsonProperty("nodeId")
        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public ParticipantDTOQueryParam nodeId(String nodeId) {
            this.nodeId = nodeId;
            return this;
        }

        @JsonProperty("authority")
        public NodeDTOManagingAuthority getAuthority() {
            return authority;
        }

        public void setAuthority(NodeDTOManagingAuthority authority) {
            this.authority = authority;
        }

        public ParticipantDTOQueryParam authority(NodeDTOManagingAuthority authority) {
            this.authority = authority;
            return this;
        }

        @JsonProperty("contactPersons")
        public List<ContactPersonDTO> getContactPersons() {
            return contactPersons;
        }

        public void setContactPersons(List<ContactPersonDTO> contactPersons) {
            this.contactPersons = contactPersons;
        }

        public ParticipantDTOQueryParam contactPersons(List<ContactPersonDTO> contactPersons) {
            this.contactPersons = contactPersons;
            return this;
        }
        public ParticipantDTOQueryParam addContactPersonsItem(ContactPersonDTO contactPersonsItem) {
            this.contactPersons.add(contactPersonsItem);
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class ParticipantDTOQueryParam {\n");

            sb.append("    classification: ").append(toIndentedString(classification)).append("\n");
            sb.append("    description: ").append(toIndentedString(description)).append("\n");
            sb.append("    endpointProtocol: ").append(toIndentedString(endpointProtocol)).append("\n");
            sb.append("    endpointUrl: ").append(toIndentedString(endpointUrl)).append("\n");
            sb.append("    healthEndpointUrl: ").append(toIndentedString(healthEndpointUrl)).append("\n");
            sb.append("    communities: ").append(toIndentedString(communities)).append("\n");
            sb.append("    functions: ").append(toIndentedString(functions)).append("\n");
            sb.append("    areasOfInterest: ").append(toIndentedString(areasOfInterest)).append("\n");
            sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
            sb.append("    name: ").append(toIndentedString(name)).append("\n");
            sb.append("    participantId: ").append(toIndentedString(participantId)).append("\n");
            sb.append("    nodeId: ").append(toIndentedString(nodeId)).append("\n");
            sb.append("    authority: ").append(toIndentedString(authority)).append("\n");
            sb.append("    contactPersons: ").append(toIndentedString(contactPersons)).append("\n");
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