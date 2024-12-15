package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDTOContactPersonDTO  {

    private Integer total;
    private List<ContactPersonDTO> data = null;

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public ResponseDTOContactPersonDTO total(Integer total) {
        this.total = total;
        return this;
    }

    @JsonProperty("data")
    public List<ContactPersonDTO> getData() {
        return data;
    }

    public void setData(List<ContactPersonDTO> data) {
        this.data = data;
    }

    public ResponseDTOContactPersonDTO data(List<ContactPersonDTO> data) {
        this.data = data;
        return this;
    }
    public ResponseDTOContactPersonDTO addDataItem(ContactPersonDTO dataItem) {
        this.data.add(dataItem);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ResponseDTOContactPersonDTO {\n");

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
    public static class ResponseDTOContactPersonDTOQueryParam  {

        private Integer total;
        private List<ContactPersonDTO> data = null;

        @JsonProperty("total")
        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public ResponseDTOContactPersonDTOQueryParam total(Integer total) {
            this.total = total;
            return this;
        }

        @JsonProperty("data")
        public List<ContactPersonDTO> getData() {
            return data;
        }

        public void setData(List<ContactPersonDTO> data) {
            this.data = data;
        }

        public ResponseDTOContactPersonDTOQueryParam data(List<ContactPersonDTO> data) {
            this.data = data;
            return this;
        }
        public ResponseDTOContactPersonDTOQueryParam addDataItem(ContactPersonDTO dataItem) {
            this.data.add(dataItem);
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class ResponseDTOContactPersonDTOQueryParam {\n");

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