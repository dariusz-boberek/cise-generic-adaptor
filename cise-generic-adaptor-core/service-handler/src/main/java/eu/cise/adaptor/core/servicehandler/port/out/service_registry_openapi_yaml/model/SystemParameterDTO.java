package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemParameterDTO  {

    private Boolean syncEnabled;
    private Integer syncPullInterval;
    private Integer tableviewsDefaultNumOfElements;
    private LocalDate syncLatestExecutionTime;

    @JsonProperty("syncEnabled")
    public Boolean getSyncEnabled() {
        return syncEnabled;
    }

    public void setSyncEnabled(Boolean syncEnabled) {
        this.syncEnabled = syncEnabled;
    }

    public SystemParameterDTO syncEnabled(Boolean syncEnabled) {
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

    public SystemParameterDTO syncPullInterval(Integer syncPullInterval) {
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

    public SystemParameterDTO tableviewsDefaultNumOfElements(Integer tableviewsDefaultNumOfElements) {
        this.tableviewsDefaultNumOfElements = tableviewsDefaultNumOfElements;
        return this;
    }

    @JsonProperty("syncLatestExecutionTime")
    public LocalDate getSyncLatestExecutionTime() {
        return syncLatestExecutionTime;
    }

    public void setSyncLatestExecutionTime(LocalDate syncLatestExecutionTime) {
        this.syncLatestExecutionTime = syncLatestExecutionTime;
    }

    public SystemParameterDTO syncLatestExecutionTime(LocalDate syncLatestExecutionTime) {
        this.syncLatestExecutionTime = syncLatestExecutionTime;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SystemParameterDTO {\n");

        sb.append("    syncEnabled: ").append(toIndentedString(syncEnabled)).append("\n");
        sb.append("    syncPullInterval: ").append(toIndentedString(syncPullInterval)).append("\n");
        sb.append("    tableviewsDefaultNumOfElements: ").append(toIndentedString(tableviewsDefaultNumOfElements)).append("\n");
        sb.append("    syncLatestExecutionTime: ").append(toIndentedString(syncLatestExecutionTime)).append("\n");
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
    public static class SystemParameterDTOQueryParam  {

        private Boolean syncEnabled;
        private Integer syncPullInterval;
        private Integer tableviewsDefaultNumOfElements;
        private LocalDate syncLatestExecutionTime;

        @JsonProperty("syncEnabled")
        public Boolean getSyncEnabled() {
            return syncEnabled;
        }

        public void setSyncEnabled(Boolean syncEnabled) {
            this.syncEnabled = syncEnabled;
        }

        public SystemParameterDTOQueryParam syncEnabled(Boolean syncEnabled) {
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

        public SystemParameterDTOQueryParam syncPullInterval(Integer syncPullInterval) {
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

        public SystemParameterDTOQueryParam tableviewsDefaultNumOfElements(Integer tableviewsDefaultNumOfElements) {
            this.tableviewsDefaultNumOfElements = tableviewsDefaultNumOfElements;
            return this;
        }

        @JsonProperty("syncLatestExecutionTime")
        public LocalDate getSyncLatestExecutionTime() {
            return syncLatestExecutionTime;
        }

        public void setSyncLatestExecutionTime(LocalDate syncLatestExecutionTime) {
            this.syncLatestExecutionTime = syncLatestExecutionTime;
        }

        public SystemParameterDTOQueryParam syncLatestExecutionTime(LocalDate syncLatestExecutionTime) {
            this.syncLatestExecutionTime = syncLatestExecutionTime;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class SystemParameterDTOQueryParam {\n");

            sb.append("    syncEnabled: ").append(toIndentedString(syncEnabled)).append("\n");
            sb.append("    syncPullInterval: ").append(toIndentedString(syncPullInterval)).append("\n");
            sb.append("    tableviewsDefaultNumOfElements: ").append(toIndentedString(tableviewsDefaultNumOfElements)).append("\n");
            sb.append("    syncLatestExecutionTime: ").append(toIndentedString(syncLatestExecutionTime)).append("\n");
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