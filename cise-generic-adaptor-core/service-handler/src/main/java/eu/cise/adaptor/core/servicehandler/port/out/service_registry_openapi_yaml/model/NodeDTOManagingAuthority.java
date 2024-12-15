package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeDTOManagingAuthority  {

    private String uuid;
    private String name;
    private String country;

    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public NodeDTOManagingAuthority uuid(String uuid) {
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

    public NodeDTOManagingAuthority name(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public NodeDTOManagingAuthority country(String country) {
        this.country = country;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NodeDTOManagingAuthority {\n");

        sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    country: ").append(toIndentedString(country)).append("\n");
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
    public static class NodeDTOManagingAuthorityQueryParam  {

        private String uuid;
        private String name;
        private String country;

        @JsonProperty("uuid")
        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public NodeDTOManagingAuthorityQueryParam uuid(String uuid) {
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

        public NodeDTOManagingAuthorityQueryParam name(String name) {
            this.name = name;
            return this;
        }

        @JsonProperty("country")
        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public NodeDTOManagingAuthorityQueryParam country(String country) {
            this.country = country;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class NodeDTOManagingAuthorityQueryParam {\n");

            sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
            sb.append("    name: ").append(toIndentedString(name)).append("\n");
            sb.append("    country: ").append(toIndentedString(country)).append("\n");
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