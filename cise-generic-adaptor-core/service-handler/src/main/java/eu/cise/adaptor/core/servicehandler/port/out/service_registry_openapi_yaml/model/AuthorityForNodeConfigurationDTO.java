package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorityForNodeConfigurationDTO  {

    private String country;
    private String name;
    private String address;
    private List<String> communities = new ArrayList<>();
    private String uuid;

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public AuthorityForNodeConfigurationDTO country(String country) {
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

    public AuthorityForNodeConfigurationDTO name(String name) {
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

    public AuthorityForNodeConfigurationDTO address(String address) {
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

    public AuthorityForNodeConfigurationDTO communities(List<String> communities) {
        this.communities = communities;
        return this;
    }
    public AuthorityForNodeConfigurationDTO addCommunitiesItem(String communitiesItem) {
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

    public AuthorityForNodeConfigurationDTO uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AuthorityForNodeConfigurationDTO {\n");

        sb.append("    country: ").append(toIndentedString(country)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    address: ").append(toIndentedString(address)).append("\n");
        sb.append("    communities: ").append(toIndentedString(communities)).append("\n");
        sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
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
    public static class AuthorityForNodeConfigurationDTOQueryParam  {

        private String country;
        private String name;
        private String address;
        private List<String> communities = new ArrayList<>();
        private String uuid;

        @JsonProperty("country")
        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public AuthorityForNodeConfigurationDTOQueryParam country(String country) {
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

        public AuthorityForNodeConfigurationDTOQueryParam name(String name) {
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

        public AuthorityForNodeConfigurationDTOQueryParam address(String address) {
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

        public AuthorityForNodeConfigurationDTOQueryParam communities(List<String> communities) {
            this.communities = communities;
            return this;
        }
        public AuthorityForNodeConfigurationDTOQueryParam addCommunitiesItem(String communitiesItem) {
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

        public AuthorityForNodeConfigurationDTOQueryParam uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class AuthorityForNodeConfigurationDTOQueryParam {\n");

            sb.append("    country: ").append(toIndentedString(country)).append("\n");
            sb.append("    name: ").append(toIndentedString(name)).append("\n");
            sb.append("    address: ").append(toIndentedString(address)).append("\n");
            sb.append("    communities: ").append(toIndentedString(communities)).append("\n");
            sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
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