package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorityBaseDTO  {

    private String country;
    private String name;
    private String address;
    private List<String> communities = new ArrayList<>();

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public AuthorityBaseDTO country(String country) {
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

    public AuthorityBaseDTO name(String name) {
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

    public AuthorityBaseDTO address(String address) {
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

    public AuthorityBaseDTO communities(List<String> communities) {
        this.communities = communities;
        return this;
    }
    public AuthorityBaseDTO addCommunitiesItem(String communitiesItem) {
        this.communities.add(communitiesItem);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AuthorityBaseDTO {\n");

        sb.append("    country: ").append(toIndentedString(country)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    address: ").append(toIndentedString(address)).append("\n");
        sb.append("    communities: ").append(toIndentedString(communities)).append("\n");
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
    public static class AuthorityBaseDTOQueryParam  {

        private String country;
        private String name;
        private String address;
        private List<String> communities = new ArrayList<>();

        @JsonProperty("country")
        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public AuthorityBaseDTOQueryParam country(String country) {
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

        public AuthorityBaseDTOQueryParam name(String name) {
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

        public AuthorityBaseDTOQueryParam address(String address) {
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

        public AuthorityBaseDTOQueryParam communities(List<String> communities) {
            this.communities = communities;
            return this;
        }
        public AuthorityBaseDTOQueryParam addCommunitiesItem(String communitiesItem) {
            this.communities.add(communitiesItem);
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class AuthorityBaseDTOQueryParam {\n");

            sb.append("    country: ").append(toIndentedString(country)).append("\n");
            sb.append("    name: ").append(toIndentedString(name)).append("\n");
            sb.append("    address: ").append(toIndentedString(address)).append("\n");
            sb.append("    communities: ").append(toIndentedString(communities)).append("\n");
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