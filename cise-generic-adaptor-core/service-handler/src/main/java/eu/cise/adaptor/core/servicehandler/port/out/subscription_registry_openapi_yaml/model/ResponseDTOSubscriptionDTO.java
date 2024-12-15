package eu.cise.adaptor.core.servicehandler.port.out.subscription_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDTOSubscriptionDTO  {

    private List<SubscriptionDTO> data = null;
    private Integer total;

    @JsonProperty("data")
    public List<SubscriptionDTO> getData() {
        return data;
    }

    public void setData(List<SubscriptionDTO> data) {
        this.data = data;
    }

    public ResponseDTOSubscriptionDTO data(List<SubscriptionDTO> data) {
        this.data = data;
        return this;
    }
    public ResponseDTOSubscriptionDTO addDataItem(SubscriptionDTO dataItem) {
        this.data.add(dataItem);
        return this;
    }

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public ResponseDTOSubscriptionDTO total(Integer total) {
        this.total = total;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ResponseDTOSubscriptionDTO {\n");

        sb.append("    data: ").append(toIndentedString(data)).append("\n");
        sb.append("    total: ").append(toIndentedString(total)).append("\n");
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
    public static class ResponseDTOSubscriptionDTOQueryParam  {

        private List<SubscriptionDTO> data = null;
        private Integer total;

        @JsonProperty("data")
        public List<SubscriptionDTO> getData() {
            return data;
        }

        public void setData(List<SubscriptionDTO> data) {
            this.data = data;
        }

        public ResponseDTOSubscriptionDTOQueryParam data(List<SubscriptionDTO> data) {
            this.data = data;
            return this;
        }
        public ResponseDTOSubscriptionDTOQueryParam addDataItem(SubscriptionDTO dataItem) {
            this.data.add(dataItem);
            return this;
        }

        @JsonProperty("total")
        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public ResponseDTOSubscriptionDTOQueryParam total(Integer total) {
            this.total = total;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class ResponseDTOSubscriptionDTOQueryParam {\n");

            sb.append("    data: ").append(toIndentedString(data)).append("\n");
            sb.append("    total: ").append(toIndentedString(total)).append("\n");
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