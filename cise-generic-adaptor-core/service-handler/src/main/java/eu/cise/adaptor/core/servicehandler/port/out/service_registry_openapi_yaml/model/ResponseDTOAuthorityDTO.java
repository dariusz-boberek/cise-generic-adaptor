package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDTOAuthorityDTO  {

    private Integer total;
    private List<AuthorityDTO> data = null;

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public ResponseDTOAuthorityDTO total(Integer total) {
        this.total = total;
        return this;
    }

    @JsonProperty("data")
    public List<AuthorityDTO> getData() {
        return data;
    }

    public void setData(List<AuthorityDTO> data) {
        this.data = data;
    }

    public ResponseDTOAuthorityDTO data(List<AuthorityDTO> data) {
        this.data = data;
        return this;
    }
    public ResponseDTOAuthorityDTO addDataItem(AuthorityDTO dataItem) {
        this.data.add(dataItem);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ResponseDTOAuthorityDTO {\n");

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
    public static class ResponseDTOAuthorityDTOQueryParam  {

        private Integer total;
        private List<AuthorityDTO> data = null;

        @JsonProperty("total")
        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public ResponseDTOAuthorityDTOQueryParam total(Integer total) {
            this.total = total;
            return this;
        }

        @JsonProperty("data")
        public List<AuthorityDTO> getData() {
            return data;
        }

        public void setData(List<AuthorityDTO> data) {
            this.data = data;
        }

        public ResponseDTOAuthorityDTOQueryParam data(List<AuthorityDTO> data) {
            this.data = data;
            return this;
        }
        public ResponseDTOAuthorityDTOQueryParam addDataItem(AuthorityDTO dataItem) {
            this.data.add(dataItem);
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class ResponseDTOAuthorityDTOQueryParam {\n");

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