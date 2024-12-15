package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeConfigurationCreateDTO  {

    private NodeConfigurationCreateDTONodeProperties nodeProperties;
    private AuthorityBaseDTO managingAuthority;
    private List<ContactPersonBaseDTO> contactPersons = new ArrayList<>();
    private SystemParameterBaseDTO systemParameters;

    @JsonProperty("nodeProperties")
    public NodeConfigurationCreateDTONodeProperties getNodeProperties() {
        return nodeProperties;
    }

    public void setNodeProperties(NodeConfigurationCreateDTONodeProperties nodeProperties) {
        this.nodeProperties = nodeProperties;
    }

    public NodeConfigurationCreateDTO nodeProperties(NodeConfigurationCreateDTONodeProperties nodeProperties) {
        this.nodeProperties = nodeProperties;
        return this;
    }

    @JsonProperty("managingAuthority")
    public AuthorityBaseDTO getManagingAuthority() {
        return managingAuthority;
    }

    public void setManagingAuthority(AuthorityBaseDTO managingAuthority) {
        this.managingAuthority = managingAuthority;
    }

    public NodeConfigurationCreateDTO managingAuthority(AuthorityBaseDTO managingAuthority) {
        this.managingAuthority = managingAuthority;
        return this;
    }

    @JsonProperty("contactPersons")
    public List<ContactPersonBaseDTO> getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(List<ContactPersonBaseDTO> contactPersons) {
        this.contactPersons = contactPersons;
    }

    public NodeConfigurationCreateDTO contactPersons(List<ContactPersonBaseDTO> contactPersons) {
        this.contactPersons = contactPersons;
        return this;
    }
    public NodeConfigurationCreateDTO addContactPersonsItem(ContactPersonBaseDTO contactPersonsItem) {
        this.contactPersons.add(contactPersonsItem);
        return this;
    }

    @JsonProperty("systemParameters")
    public SystemParameterBaseDTO getSystemParameters() {
        return systemParameters;
    }

    public void setSystemParameters(SystemParameterBaseDTO systemParameters) {
        this.systemParameters = systemParameters;
    }

    public NodeConfigurationCreateDTO systemParameters(SystemParameterBaseDTO systemParameters) {
        this.systemParameters = systemParameters;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NodeConfigurationCreateDTO {\n");

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
    public static class NodeConfigurationCreateDTOQueryParam  {

        private NodeConfigurationCreateDTONodeProperties nodeProperties;
        private AuthorityBaseDTO managingAuthority;
        private List<ContactPersonBaseDTO> contactPersons = new ArrayList<>();
        private SystemParameterBaseDTO systemParameters;

        @JsonProperty("nodeProperties")
        public NodeConfigurationCreateDTONodeProperties getNodeProperties() {
            return nodeProperties;
        }

        public void setNodeProperties(NodeConfigurationCreateDTONodeProperties nodeProperties) {
            this.nodeProperties = nodeProperties;
        }

        public NodeConfigurationCreateDTOQueryParam nodeProperties(NodeConfigurationCreateDTONodeProperties nodeProperties) {
            this.nodeProperties = nodeProperties;
            return this;
        }

        @JsonProperty("managingAuthority")
        public AuthorityBaseDTO getManagingAuthority() {
            return managingAuthority;
        }

        public void setManagingAuthority(AuthorityBaseDTO managingAuthority) {
            this.managingAuthority = managingAuthority;
        }

        public NodeConfigurationCreateDTOQueryParam managingAuthority(AuthorityBaseDTO managingAuthority) {
            this.managingAuthority = managingAuthority;
            return this;
        }

        @JsonProperty("contactPersons")
        public List<ContactPersonBaseDTO> getContactPersons() {
            return contactPersons;
        }

        public void setContactPersons(List<ContactPersonBaseDTO> contactPersons) {
            this.contactPersons = contactPersons;
        }

        public NodeConfigurationCreateDTOQueryParam contactPersons(List<ContactPersonBaseDTO> contactPersons) {
            this.contactPersons = contactPersons;
            return this;
        }
        public NodeConfigurationCreateDTOQueryParam addContactPersonsItem(ContactPersonBaseDTO contactPersonsItem) {
            this.contactPersons.add(contactPersonsItem);
            return this;
        }

        @JsonProperty("systemParameters")
        public SystemParameterBaseDTO getSystemParameters() {
            return systemParameters;
        }

        public void setSystemParameters(SystemParameterBaseDTO systemParameters) {
            this.systemParameters = systemParameters;
        }

        public NodeConfigurationCreateDTOQueryParam systemParameters(SystemParameterBaseDTO systemParameters) {
            this.systemParameters = systemParameters;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class NodeConfigurationCreateDTOQueryParam {\n");

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