package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceStatusUpdateDTO  {

    private String serviceStatus;

    @JsonProperty("serviceStatus")
    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public ServiceStatusUpdateDTO serviceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ServiceStatusUpdateDTO {\n");

        sb.append("    serviceStatus: ").append(toIndentedString(serviceStatus)).append("\n");
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
    public static class ServiceStatusUpdateDTOQueryParam  {

        private String serviceStatus;

        @JsonProperty("serviceStatus")
        public String getServiceStatus() {
            return serviceStatus;
        }

        public void setServiceStatus(String serviceStatus) {
            this.serviceStatus = serviceStatus;
        }

        public ServiceStatusUpdateDTOQueryParam serviceStatus(String serviceStatus) {
            this.serviceStatus = serviceStatus;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class ServiceStatusUpdateDTOQueryParam {\n");

            sb.append("    serviceStatus: ").append(toIndentedString(serviceStatus)).append("\n");
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