package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorityDTO  {

    private String country;
    private String name;
    private String address;
    private List<String> communities = new ArrayList<>();
    private String uuid;
    private AuthorityDTONode node;

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public AuthorityDTO country(String country) {
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

    public AuthorityDTO name(String name) {
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

    public AuthorityDTO address(String address) {
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

    public AuthorityDTO communities(List<String> communities) {
        this.communities = communities;
        return this;
    }
    public AuthorityDTO addCommunitiesItem(String communitiesItem) {
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

    public AuthorityDTO uuid(String uuid) {
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

    public AuthorityDTO node(AuthorityDTONode node) {
        this.node = node;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AuthorityDTO {\n");

        sb.append("    country: ").append(toIndentedString(country)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    address: ").append(toIndentedString(address)).append("\n");
        sb.append("    communities: ").append(toIndentedString(communities)).append("\n");
        sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
        sb.append("    node: ").append(toIndentedString(node)).append("\n");
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
    public static class AuthorityDTOQueryParam  {

        private String country;
        private String name;
        private String address;
        private List<String> communities = new ArrayList<>();
        private String uuid;
        private AuthorityDTONode node;

        @JsonProperty("country")
        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public AuthorityDTOQueryParam country(String country) {
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

        public AuthorityDTOQueryParam name(String name) {
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

        public AuthorityDTOQueryParam address(String address) {
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

        public AuthorityDTOQueryParam communities(List<String> communities) {
            this.communities = communities;
            return this;
        }
        public AuthorityDTOQueryParam addCommunitiesItem(String communitiesItem) {
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

        public AuthorityDTOQueryParam uuid(String uuid) {
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

        public AuthorityDTOQueryParam node(AuthorityDTONode node) {
            this.node = node;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class AuthorityDTOQueryParam {\n");

            sb.append("    country: ").append(toIndentedString(country)).append("\n");
            sb.append("    name: ").append(toIndentedString(name)).append("\n");
            sb.append("    address: ").append(toIndentedString(address)).append("\n");
            sb.append("    communities: ").append(toIndentedString(communities)).append("\n");
            sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
            sb.append("    node: ").append(toIndentedString(node)).append("\n");
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