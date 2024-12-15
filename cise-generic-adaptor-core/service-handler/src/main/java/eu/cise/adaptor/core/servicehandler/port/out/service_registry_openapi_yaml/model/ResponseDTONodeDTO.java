package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDTONodeDTO  {

    private Integer total;
    private List<NodeDTO> data = null;

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public ResponseDTONodeDTO total(Integer total) {
        this.total = total;
        return this;
    }

    @JsonProperty("data")
    public List<NodeDTO> getData() {
        return data;
    }

    public void setData(List<NodeDTO> data) {
        this.data = data;
    }

    public ResponseDTONodeDTO data(List<NodeDTO> data) {
        this.data = data;
        return this;
    }
    public ResponseDTONodeDTO addDataItem(NodeDTO dataItem) {
        this.data.add(dataItem);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ResponseDTONodeDTO {\n");

        sb.append("    total: ").append(toIndentedString(total)).append("\n");
        sb.append("    data: ").append(toIndentedString(data)).append("\n");
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
    public static class ResponseDTONodeDTOQueryParam  {

        private Integer total;
        private List<NodeDTO> data = null;

        @JsonProperty("total")
        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public ResponseDTONodeDTOQueryParam total(Integer total) {
            this.total = total;
            return this;
        }

        @JsonProperty("data")
        public List<NodeDTO> getData() {
            return data;
        }

        public void setData(List<NodeDTO> data) {
            this.data = data;
        }

        public ResponseDTONodeDTOQueryParam data(List<NodeDTO> data) {
            this.data = data;
            return this;
        }
        public ResponseDTONodeDTOQueryParam addDataItem(NodeDTO dataItem) {
            this.data.add(dataItem);
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class ResponseDTONodeDTOQueryParam {\n");

            sb.append("    total: ").append(toIndentedString(total)).append("\n");
            sb.append("    data: ").append(toIndentedString(data)).append("\n");
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