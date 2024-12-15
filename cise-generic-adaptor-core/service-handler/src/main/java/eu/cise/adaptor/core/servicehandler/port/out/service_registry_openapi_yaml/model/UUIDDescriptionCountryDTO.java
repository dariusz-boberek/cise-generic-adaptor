package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UUIDDescriptionCountryDTO  {

    private UUID uuid;
    private String description;
    private String country;

    @JsonProperty("uuid")
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUIDDescriptionCountryDTO uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUIDDescriptionCountryDTO description(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public UUIDDescriptionCountryDTO country(String country) {
        this.country = country;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UUIDDescriptionCountryDTO {\n");

        sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
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
    public static class UUIDDescriptionCountryDTOQueryParam  {

        private UUID uuid;
        private String description;
        private String country;

        @JsonProperty("uuid")
        public UUID getUuid() {
            return uuid;
        }

        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }

        public UUIDDescriptionCountryDTOQueryParam uuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        @JsonProperty("description")
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public UUIDDescriptionCountryDTOQueryParam description(String description) {
            this.description = description;
            return this;
        }

        @JsonProperty("country")
        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public UUIDDescriptionCountryDTOQueryParam country(String country) {
            this.country = country;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class UUIDDescriptionCountryDTOQueryParam {\n");

            sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
            sb.append("    description: ").append(toIndentedString(description)).append("\n");
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