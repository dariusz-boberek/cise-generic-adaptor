package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorityDetailsDTO  {

    private String country;
    private String name;
    private String address;
    private List<String> communities = new ArrayList<>();
    private String uuid;
    private AuthorityDTONode node;
    private List<UUIDDescriptionDTO> participants = null;
    private List<ContactPersonBaseDTO> contactPersons = null;

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public AuthorityDetailsDTO country(String country) {
        this.country = country;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthorityDetailsDTO name(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AuthorityDetailsDTO address(String address) {
        this.address = address;
        return this;
    }

    @JsonProperty("communities")
    public List<String> getCommunities() {
        return communities;
    }

    public void setCommunities(List<String> communities) {
        this.communities = communities;
    }

    public AuthorityDetailsDTO communities(List<String> communities) {
        this.communities = communities;
        return this;
    }
    public AuthorityDetailsDTO addCommunitiesItem(String communitiesItem) {
        this.communities.add(communitiesItem);
        return this;
    }

    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public AuthorityDetailsDTO uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @JsonProperty("node")
    public AuthorityDTONode getNode() {
        return node;
    }

    public void setNode(AuthorityDTONode node) {
        this.node = node;
    }

    public AuthorityDetailsDTO node(AuthorityDTONode node) {
        this.node = node;
        return this;
    }

    @JsonProperty("participants")
    public List<UUIDDescriptionDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<UUIDDescriptionDTO> participants) {
        this.participants = participants;
    }

    public AuthorityDetailsDTO participants(List<UUIDDescriptionDTO> participants) {
        this.participants = participants;
        return this;
    }
    public AuthorityDetailsDTO addParticipantsItem(UUIDDescriptionDTO participantsItem) {
        this.participants.add(participantsItem);
        return this;
    }

    @JsonProperty("contactPersons")
    public List<ContactPersonBaseDTO> getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(List<ContactPersonBaseDTO> contactPersons) {
        this.contactPersons = contactPersons;
    }

    public AuthorityDetailsDTO contactPersons(List<ContactPersonBaseDTO> contactPersons) {
        this.contactPersons = contactPersons;
        return this;
    }
    public AuthorityDetailsDTO addContactPersonsItem(ContactPersonBaseDTO contactPersonsItem) {
        this.contactPersons.add(contactPersonsItem);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AuthorityDetailsDTO {\n");

        sb.append("    country: ").append(toIndentedString(country)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    address: ").append(toIndentedString(address)).append("\n");
        sb.append("    communities: ").append(toIndentedString(communities)).append("\n");
        sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
        sb.append("    node: ").append(toIndentedString(node)).append("\n");
        sb.append("    participants: ").append(toIndentedString(participants)).append("\n");
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
    public static class AuthorityDetailsDTOQueryParam  {

        private String country;
        private String name;
        private String address;
        private List<String> communities = new ArrayList<>();
        private String uuid;
        private AuthorityDTONode node;
        private List<UUIDDescriptionDTO> participants = null;
        private List<ContactPersonBaseDTO> contactPersons = null;

        @JsonProperty("country")
        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public AuthorityDetailsDTOQueryParam country(String country) {
            this.country = country;
            return this;
        }

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public AuthorityDetailsDTOQueryParam name(String name) {
            this.name = name;
            return this;
        }

        @JsonProperty("address")
        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public AuthorityDetailsDTOQueryParam address(String address) {
            this.address = address;
            return this;
        }

        @JsonProperty("communities")
        public List<String> getCommunities() {
            return communities;
        }

        public void setCommunities(List<String> communities) {
            this.communities = communities;
        }

        public AuthorityDetailsDTOQueryParam communities(List<String> communities) {
            this.communities = communities;
            return this;
        }
        public AuthorityDetailsDTOQueryParam addCommunitiesItem(String communitiesItem) {
            this.communities.add(communitiesItem);
            return this;
        }

        @JsonProperty("uuid")
        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public AuthorityDetailsDTOQueryParam uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        @JsonProperty("node")
        public AuthorityDTONode getNode() {
            return node;
        }

        public void setNode(AuthorityDTONode node) {
            this.node = node;
        }

        public AuthorityDetailsDTOQueryParam node(AuthorityDTONode node) {
            this.node = node;
            return this;
        }

        @JsonProperty("participants")
        public List<UUIDDescriptionDTO> getParticipants() {
            return participants;
        }

        public void setParticipants(List<UUIDDescriptionDTO> participants) {
            this.participants = participants;
        }

        public AuthorityDetailsDTOQueryParam participants(List<UUIDDescriptionDTO> participants) {
            this.participants = participants;
            return this;
        }
        public AuthorityDetailsDTOQueryParam addParticipantsItem(UUIDDescriptionDTO participantsItem) {
            this.participants.add(participantsItem);
            return this;
        }

        @JsonProperty("contactPersons")
        public List<ContactPersonBaseDTO> getContactPersons() {
            return contactPersons;
        }

        public void setContactPersons(List<ContactPersonBaseDTO> contactPersons) {
            this.contactPersons = contactPersons;
        }

        public AuthorityDetailsDTOQueryParam contactPersons(List<ContactPersonBaseDTO> contactPersons) {
            this.contactPersons = contactPersons;
            return this;
        }
        public AuthorityDetailsDTOQueryParam addContactPersonsItem(ContactPersonBaseDTO contactPersonsItem) {
            this.contactPersons.add(contactPersonsItem);
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class AuthorityDetailsDTOQueryParam {\n");

            sb.append("    country: ").append(toIndentedString(country)).append("\n");
            sb.append("    name: ").append(toIndentedString(name)).append("\n");
            sb.append("    address: ").append(toIndentedString(address)).append("\n");
            sb.append("    communities: ").append(toIndentedString(communities)).append("\n");
            sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
            sb.append("    node: ").append(toIndentedString(node)).append("\n");
            sb.append("    participants: ").append(toIndentedString(participants)).append("\n");
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