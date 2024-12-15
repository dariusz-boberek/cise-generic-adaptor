package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemParameterBaseDTO  {

    private Boolean syncEnabled;
    private Integer syncPullInterval;
    private Integer tableviewsDefaultNumOfElements;

    @JsonProperty("syncEnabled")
    public Boolean getSyncEnabled() {
        return syncEnabled;
    }

    public void setSyncEnabled(Boolean syncEnabled) {
        this.syncEnabled = syncEnabled;
    }

    public SystemParameterBaseDTO syncEnabled(Boolean syncEnabled) {
        this.syncEnabled = syncEnabled;
        return this;
    }

    @JsonProperty("syncPullInterval")
    public Integer getSyncPullInterval() {
        return syncPullInterval;
    }

    public void setSyncPullInterval(Integer syncPullInterval) {
        this.syncPullInterval = syncPullInterval;
    }

    public SystemParameterBaseDTO syncPullInterval(Integer syncPullInterval) {
        this.syncPullInterval = syncPullInterval;
        return this;
    }

    @JsonProperty("tableviewsDefaultNumOfElements")
    public Integer getTableviewsDefaultNumOfElements() {
        return tableviewsDefaultNumOfElements;
    }

    public void setTableviewsDefaultNumOfElements(Integer tableviewsDefaultNumOfElements) {
        this.tableviewsDefaultNumOfElements = tableviewsDefaultNumOfElements;
    }

    public SystemParameterBaseDTO tableviewsDefaultNumOfElements(Integer tableviewsDefaultNumOfElements) {
        this.tableviewsDefaultNumOfElements = tableviewsDefaultNumOfElements;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SystemParameterBaseDTO {\n");

        sb.append("    syncEnabled: ").append(toIndentedString(syncEnabled)).append("\n");
        sb.append("    syncPullInterval: ").append(toIndentedString(syncPullInterval)).append("\n");
        sb.append("    tableviewsDefaultNumOfElements: ").append(toIndentedString(tableviewsDefaultNumOfElements)).append("\n");
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
    public static class SystemParameterBaseDTOQueryParam  {

        private Boolean syncEnabled;
        private Integer syncPullInterval;
        private Integer tableviewsDefaultNumOfElements;

        @JsonProperty("syncEnabled")
        public Boolean getSyncEnabled() {
            return syncEnabled;
        }

        public void setSyncEnabled(Boolean syncEnabled) {
            this.syncEnabled = syncEnabled;
        }

        public SystemParameterBaseDTOQueryParam syncEnabled(Boolean syncEnabled) {
            this.syncEnabled = syncEnabled;
            return this;
        }

        @JsonProperty("syncPullInterval")
        public Integer getSyncPullInterval() {
            return syncPullInterval;
        }

        public void setSyncPullInterval(Integer syncPullInterval) {
            this.syncPullInterval = syncPullInterval;
        }

        public SystemParameterBaseDTOQueryParam syncPullInterval(Integer syncPullInterval) {
            this.syncPullInterval = syncPullInterval;
            return this;
        }

        @JsonProperty("tableviewsDefaultNumOfElements")
        public Integer getTableviewsDefaultNumOfElements() {
            return tableviewsDefaultNumOfElements;
        }

        public void setTableviewsDefaultNumOfElements(Integer tableviewsDefaultNumOfElements) {
            this.tableviewsDefaultNumOfElements = tableviewsDefaultNumOfElements;
        }

        public SystemParameterBaseDTOQueryParam tableviewsDefaultNumOfElements(Integer tableviewsDefaultNumOfElements) {
            this.tableviewsDefaultNumOfElements = tableviewsDefaultNumOfElements;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class SystemParameterBaseDTOQueryParam {\n");

            sb.append("    syncEnabled: ").append(toIndentedString(syncEnabled)).append("\n");
            sb.append("    syncPullInterval: ").append(toIndentedString(syncPullInterval)).append("\n");
            sb.append("    tableviewsDefaultNumOfElements: ").append(toIndentedString(tableviewsDefaultNumOfElements)).append("\n");
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