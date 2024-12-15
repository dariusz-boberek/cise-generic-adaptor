package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDTOServiceDTO  {

    private Integer total;
    private List<ServiceDTO> data = null;

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public ResponseDTOServiceDTO total(Integer total) {
        this.total = total;
        return this;
    }

    @JsonProperty("data")
    public List<ServiceDTO> getData() {
        return data;
    }

    public void setData(List<ServiceDTO> data) {
        this.data = data;
    }

    public ResponseDTOServiceDTO data(List<ServiceDTO> data) {
        this.data = data;
        return this;
    }
    public ResponseDTOServiceDTO addDataItem(ServiceDTO dataItem) {
        this.data.add(dataItem);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ResponseDTOServiceDTO {\n");

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
    public static class ResponseDTOServiceDTOQueryParam  {

        private Integer total;
        private List<ServiceDTO> data = null;

        @JsonProperty("total")
        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public ResponseDTOServiceDTOQueryParam total(Integer total) {
            this.total = total;
            return this;
        }

        @JsonProperty("data")
        public List<ServiceDTO> getData() {
            return data;
        }

        public void setData(List<ServiceDTO> data) {
            this.data = data;
        }

        public ResponseDTOServiceDTOQueryParam data(List<ServiceDTO> data) {
            this.data = data;
            return this;
        }
        public ResponseDTOServiceDTOQueryParam addDataItem(ServiceDTO dataItem) {
            this.data.add(dataItem);
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class ResponseDTOServiceDTOQueryParam {\n");

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