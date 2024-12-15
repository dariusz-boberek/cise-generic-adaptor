package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeConfigurationDTO  {

    private NodeBaseDTO nodeProperties;
    private AuthorityForNodeConfigurationDTO managingAuthority;
    private List<ContactPersonBaseWithUuidDTO> contactPersons = null;
    private SystemParameterDTO systemParameters;

    @JsonProperty("nodeProperties")
    public NodeBaseDTO getNodeProperties() {
        return nodeProperties;
    }

    public void setNodeProperties(NodeBaseDTO nodeProperties) {
        this.nodeProperties = nodeProperties;
    }

    public NodeConfigurationDTO nodeProperties(NodeBaseDTO nodeProperties) {
        this.nodeProperties = nodeProperties;
        return this;
    }

    @JsonProperty("managingAuthority")
    public AuthorityForNodeConfigurationDTO getManagingAuthority() {
        return managingAuthority;
    }

    public void setManagingAuthority(AuthorityForNodeConfigurationDTO managingAuthority) {
        this.managingAuthority = managingAuthority;
    }

    public NodeConfigurationDTO managingAuthority(AuthorityForNodeConfigurationDTO managingAuthority) {
        this.managingAuthority = managingAuthority;
        return this;
    }

    @JsonProperty("contactPersons")
    public List<ContactPersonBaseWithUuidDTO> getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(List<ContactPersonBaseWithUuidDTO> contactPersons) {
        this.contactPersons = contactPersons;
    }

    public NodeConfigurationDTO contactPersons(List<ContactPersonBaseWithUuidDTO> contactPersons) {
        this.contactPersons = contactPersons;
        return this;
    }
    public NodeConfigurationDTO addContactPersonsItem(ContactPersonBaseWithUuidDTO contactPersonsItem) {
        this.contactPersons.add(contactPersonsItem);
        return this;
    }

    @JsonProperty("systemParameters")
    public SystemParameterDTO getSystemParameters() {
        return systemParameters;
    }

    public void setSystemParameters(SystemParameterDTO systemParameters) {
        this.systemParameters = systemParameters;
    }

    public NodeConfigurationDTO systemParameters(SystemParameterDTO systemParameters) {
        this.systemParameters = systemParameters;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NodeConfigurationDTO {\n");

        sb.append("    nodeProperties: ").append(toIndentedString(nodeProperties)).append("\n");
        sb.append("    managingAuthority: ").append(toIndentedString(managingAuthority)).append("\n");
        sb.append("    contactPersons: ").append(toIndentedString(contactPersons)).append("\n");
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
    public static class NodeConfigurationDTOQueryParam  {

        private NodeBaseDTO nodeProperties;
        private AuthorityForNodeConfigurationDTO managingAuthority;
        private List<ContactPersonBaseWithUuidDTO> contactPersons = null;
        private SystemParameterDTO systemParameters;

        @JsonProperty("nodeProperties")
        public NodeBaseDTO getNodeProperties() {
            return nodeProperties;
        }

        public void setNodeProperties(NodeBaseDTO nodeProperties) {
            this.nodeProperties = nodeProperties;
        }

        public NodeConfigurationDTOQueryParam nodeProperties(NodeBaseDTO nodeProperties) {
            this.nodeProperties = nodeProperties;
            return this;
        }

        @JsonProperty("managingAuthority")
        public AuthorityForNodeConfigurationDTO getManagingAuthority() {
            return managingAuthority;
        }

        public void setManagingAuthority(AuthorityForNodeConfigurationDTO managingAuthority) {
            this.managingAuthority = managingAuthority;
        }

        public NodeConfigurationDTOQueryParam managingAuthority(AuthorityForNodeConfigurationDTO managingAuthority) {
            this.managingAuthority = managingAuthority;
            return this;
        }

        @JsonProperty("contactPersons")
        public List<ContactPersonBaseWithUuidDTO> getContactPersons() {
            return contactPersons;
        }

        public void setContactPersons(List<ContactPersonBaseWithUuidDTO> contactPersons) {
            this.contactPersons = contactPersons;
        }

        public NodeConfigurationDTOQueryParam contactPersons(List<ContactPersonBaseWithUuidDTO> contactPersons) {
            this.contactPersons = contactPersons;
            return this;
        }
        public NodeConfigurationDTOQueryParam addContactPersonsItem(ContactPersonBaseWithUuidDTO contactPersonsItem) {
            this.contactPersons.add(contactPersonsItem);
            return this;
        }

        @JsonProperty("systemParameters")
        public SystemParameterDTO getSystemParameters() {
            return systemParameters;
        }

        public void setSystemParameters(SystemParameterDTO systemParameters) {
            this.systemParameters = systemParameters;
        }

        public NodeConfigurationDTOQueryParam systemParameters(SystemParameterDTO systemParameters) {
            this.systemParameters = systemParameters;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class NodeConfigurationDTOQueryParam {\n");

            sb.append("    nodeProperties: ").append(toIndentedString(nodeProperties)).append("\n");
            sb.append("    managingAuthority: ").append(toIndentedString(managingAuthority)).append("\n");
            sb.append("    contactPersons: ").append(toIndentedString(contactPersons)).append("\n");
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