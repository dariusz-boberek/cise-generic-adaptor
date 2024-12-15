package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeConfigurationUpdateDTO  {

    private UUID managingAuthorityUUID;
    private List<ContactPersonBaseDTO> newContactPersons = null;
    private List<UUID> registeredContactPersonUUIDs = null;
    private SystemParameterBaseDTO systemParameters;

    @JsonProperty("managingAuthorityUUID")
    public UUID getManagingAuthorityUUID() {
        return managingAuthorityUUID;
    }

    public void setManagingAuthorityUUID(UUID managingAuthorityUUID) {
        this.managingAuthorityUUID = managingAuthorityUUID;
    }

    public NodeConfigurationUpdateDTO managingAuthorityUUID(UUID managingAuthorityUUID) {
        this.managingAuthorityUUID = managingAuthorityUUID;
        return this;
    }

    @JsonProperty("newContactPersons")
    public List<ContactPersonBaseDTO> getNewContactPersons() {
        return newContactPersons;
    }

    public void setNewContactPersons(List<ContactPersonBaseDTO> newContactPersons) {
        this.newContactPersons = newContactPersons;
    }

    public NodeConfigurationUpdateDTO newContactPersons(List<ContactPersonBaseDTO> newContactPersons) {
        this.newContactPersons = newContactPersons;
        return this;
    }
    public NodeConfigurationUpdateDTO addNewContactPersonsItem(ContactPersonBaseDTO newContactPersonsItem) {
        this.newContactPersons.add(newContactPersonsItem);
        return this;
    }

    @JsonProperty("registeredContactPersonUUIDs")
    public List<UUID> getRegisteredContactPersonUUIDs() {
        return registeredContactPersonUUIDs;
    }

    public void setRegisteredContactPersonUUIDs(List<UUID> registeredContactPersonUUIDs) {
        this.registeredContactPersonUUIDs = registeredContactPersonUUIDs;
    }

    public NodeConfigurationUpdateDTO registeredContactPersonUUIDs(List<UUID> registeredContactPersonUUIDs) {
        this.registeredContactPersonUUIDs = registeredContactPersonUUIDs;
        return this;
    }
    public NodeConfigurationUpdateDTO addRegisteredContactPersonUUIDsItem(UUID registeredContactPersonUUIDsItem) {
        this.registeredContactPersonUUIDs.add(registeredContactPersonUUIDsItem);
        return this;
    }

    @JsonProperty("systemParameters")
    public SystemParameterBaseDTO getSystemParameters() {
        return systemParameters;
    }

    public void setSystemParameters(SystemParameterBaseDTO systemParameters) {
        this.systemParameters = systemParameters;
    }

    public NodeConfigurationUpdateDTO systemParameters(SystemParameterBaseDTO systemParameters) {
        this.systemParameters = systemParameters;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NodeConfigurationUpdateDTO {\n");

        sb.append("    managingAuthorityUUID: ").append(toIndentedString(managingAuthorityUUID)).append("\n");
        sb.append("    newContactPersons: ").append(toIndentedString(newContactPersons)).append("\n");
        sb.append("    registeredContactPersonUUIDs: ").append(toIndentedString(registeredContactPersonUUIDs)).append("\n");
        sb.append("    systemParameters: ").append(toIndentedString(systemParameters)).append("\n");
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
    public static class NodeConfigurationUpdateDTOQueryParam  {

        private UUID managingAuthorityUUID;
        private List<ContactPersonBaseDTO> newContactPersons = null;
        private List<UUID> registeredContactPersonUUIDs = null;
        private SystemParameterBaseDTO systemParameters;

        @JsonProperty("managingAuthorityUUID")
        public UUID getManagingAuthorityUUID() {
            return managingAuthorityUUID;
        }

        public void setManagingAuthorityUUID(UUID managingAuthorityUUID) {
            this.managingAuthorityUUID = managingAuthorityUUID;
        }

        public NodeConfigurationUpdateDTOQueryParam managingAuthorityUUID(UUID managingAuthorityUUID) {
            this.managingAuthorityUUID = managingAuthorityUUID;
            return this;
        }

        @JsonProperty("newContactPersons")
        public List<ContactPersonBaseDTO> getNewContactPersons() {
            return newContactPersons;
        }

        public void setNewContactPersons(List<ContactPersonBaseDTO> newContactPersons) {
            this.newContactPersons = newContactPersons;
        }

        public NodeConfigurationUpdateDTOQueryParam newContactPersons(List<ContactPersonBaseDTO> newContactPersons) {
            this.newContactPersons = newContactPersons;
            return this;
        }
        public NodeConfigurationUpdateDTOQueryParam addNewContactPersonsItem(ContactPersonBaseDTO newContactPersonsItem) {
            this.newContactPersons.add(newContactPersonsItem);
            return this;
        }

        @JsonProperty("registeredContactPersonUUIDs")
        public List<UUID> getRegisteredContactPersonUUIDs() {
            return registeredContactPersonUUIDs;
        }

        public void setRegisteredContactPersonUUIDs(List<UUID> registeredContactPersonUUIDs) {
            this.registeredContactPersonUUIDs = registeredContactPersonUUIDs;
        }

        public NodeConfigurationUpdateDTOQueryParam registeredContactPersonUUIDs(List<UUID> registeredContactPersonUUIDs) {
            this.registeredContactPersonUUIDs = registeredContactPersonUUIDs;
            return this;
        }
        public NodeConfigurationUpdateDTOQueryParam addRegisteredContactPersonUUIDsItem(UUID registeredContactPersonUUIDsItem) {
            this.registeredContactPersonUUIDs.add(registeredContactPersonUUIDsItem);
            return this;
        }

        @JsonProperty("systemParameters")
        public SystemParameterBaseDTO getSystemParameters() {
            return systemParameters;
        }

        public void setSystemParameters(SystemParameterBaseDTO systemParameters) {
            this.systemParameters = systemParameters;
        }

        public NodeConfigurationUpdateDTOQueryParam systemParameters(SystemParameterBaseDTO systemParameters) {
            this.systemParameters = systemParameters;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class NodeConfigurationUpdateDTOQueryParam {\n");

            sb.append("    managingAuthorityUUID: ").append(toIndentedString(managingAuthorityUUID)).append("\n");
            sb.append("    newContactPersons: ").append(toIndentedString(newContactPersons)).append("\n");
            sb.append("    registeredContactPersonUUIDs: ").append(toIndentedString(registeredContactPersonUUIDs)).append("\n");
            sb.append("    systemParameters: ").append(toIndentedString(systemParameters)).append("\n");
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