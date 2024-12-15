package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UUIDDescriptionDTO  {

    private UUID uuid;
    private String description;

    @JsonProperty("uuid")
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUIDDescriptionDTO uuid(UUID uuid) {
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

    public UUIDDescriptionDTO description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UUIDDescriptionDTO {\n");

        sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
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
    public static class UUIDDescriptionDTOQueryParam  {

        private UUID uuid;
        private String description;

        @JsonProperty("uuid")
        public UUID getUuid() {
            return uuid;
        }

        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }

        public UUIDDescriptionDTOQueryParam uuid(UUID uuid) {
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

        public UUIDDescriptionDTOQueryParam description(String description) {
            this.description = description;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class UUIDDescriptionDTOQueryParam {\n");

            sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
            sb.append("    description: ").append(toIndentedString(description)).append("\n");
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