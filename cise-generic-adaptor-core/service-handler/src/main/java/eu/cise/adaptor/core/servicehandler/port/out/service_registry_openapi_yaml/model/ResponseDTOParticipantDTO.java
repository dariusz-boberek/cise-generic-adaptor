package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDTOParticipantDTO  {

    private Integer total;
    private List<ParticipantDTO> data = null;

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public ResponseDTOParticipantDTO total(Integer total) {
        this.total = total;
        return this;
    }

    @JsonProperty("data")
    public List<ParticipantDTO> getData() {
        return data;
    }

    public void setData(List<ParticipantDTO> data) {
        this.data = data;
    }

    public ResponseDTOParticipantDTO data(List<ParticipantDTO> data) {
        this.data = data;
        return this;
    }
    public ResponseDTOParticipantDTO addDataItem(ParticipantDTO dataItem) {
        this.data.add(dataItem);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ResponseDTOParticipantDTO {\n");

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
    public static class ResponseDTOParticipantDTOQueryParam  {

        private Integer total;
        private List<ParticipantDTO> data = null;

        @JsonProperty("total")
        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public ResponseDTOParticipantDTOQueryParam total(Integer total) {
            this.total = total;
            return this;
        }

        @JsonProperty("data")
        public List<ParticipantDTO> getData() {
            return data;
        }

        public void setData(List<ParticipantDTO> data) {
            this.data = data;
        }

        public ResponseDTOParticipantDTOQueryParam data(List<ParticipantDTO> data) {
            this.data = data;
            return this;
        }
        public ResponseDTOParticipantDTOQueryParam addDataItem(ParticipantDTO dataItem) {
            this.data.add(dataItem);
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class ResponseDTOParticipantDTOQueryParam {\n");

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