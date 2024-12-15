package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorityDTONode  {

    private String uuid;
    private String nodeId;

    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public AuthorityDTONode uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @JsonProperty("nodeId")
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public AuthorityDTONode nodeId(String nodeId) {
        this.nodeId = nodeId;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AuthorityDTONode {\n");

        sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
        sb.append("    nodeId: ").append(toIndentedString(nodeId)).append("\n");
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
    public static class AuthorityDTONodeQueryParam  {

        private String uuid;
        private String nodeId;

        @JsonProperty("uuid")
        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public AuthorityDTONodeQueryParam uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        @JsonProperty("nodeId")
        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public AuthorityDTONodeQueryParam nodeId(String nodeId) {
            this.nodeId = nodeId;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class AuthorityDTONodeQueryParam {\n");

            sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
            sb.append("    nodeId: ").append(toIndentedString(nodeId)).append("\n");
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