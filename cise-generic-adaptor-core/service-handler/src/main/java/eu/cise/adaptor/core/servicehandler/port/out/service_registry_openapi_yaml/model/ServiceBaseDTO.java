package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceBaseDTO  {

    private String serviceType;
    private String serviceOperation;
    private String serviceRole;
    private String serviceName;
    private String dataModelVersion;
    private String serviceModelVersion;
    private String dataFreshness;
    private Integer maxNoEntities;
    private Integer maxNoRequests;
    private Integer expResponseTime;
    private List<String> dataLocation = null;
    private List<String> informationExchanged = null;
    private String queryByExample;
    private List<String> supportedQueries = null;
    private String streamServiceType;
    private String gisServerUrl;
    private String gisServerUsername;
    private String gisServerPassword;

    @JsonProperty("serviceType")
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public ServiceBaseDTO serviceType(String serviceType) {
        this.serviceType = serviceType;
        return this;
    }

    @JsonProperty("serviceOperation")
    public String getServiceOperation() {
        return serviceOperation;
    }

    public void setServiceOperation(String serviceOperation) {
        this.serviceOperation = serviceOperation;
    }

    public ServiceBaseDTO serviceOperation(String serviceOperation) {
        this.serviceOperation = serviceOperation;
        return this;
    }

    @JsonProperty("serviceRole")
    public String getServiceRole() {
        return serviceRole;
    }

    public void setServiceRole(String serviceRole) {
        this.serviceRole = serviceRole;
    }

    public ServiceBaseDTO serviceRole(String serviceRole) {
        this.serviceRole = serviceRole;
        return this;
    }

    @JsonProperty("serviceName")
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public ServiceBaseDTO serviceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    @JsonProperty("dataModelVersion")
    public String getDataModelVersion() {
        return dataModelVersion;
    }

    public void setDataModelVersion(String dataModelVersion) {
        this.dataModelVersion = dataModelVersion;
    }

    public ServiceBaseDTO dataModelVersion(String dataModelVersion) {
        this.dataModelVersion = dataModelVersion;
        return this;
    }

    @JsonProperty("serviceModelVersion")
    public String getServiceModelVersion() {
        return serviceModelVersion;
    }

    public void setServiceModelVersion(String serviceModelVersion) {
        this.serviceModelVersion = serviceModelVersion;
    }

    public ServiceBaseDTO serviceModelVersion(String serviceModelVersion) {
        this.serviceModelVersion = serviceModelVersion;
        return this;
    }

    @JsonProperty("dataFreshness")
    public String getDataFreshness() {
        return dataFreshness;
    }

    public void setDataFreshness(String dataFreshness) {
        this.dataFreshness = dataFreshness;
    }

    public ServiceBaseDTO dataFreshness(String dataFreshness) {
        this.dataFreshness = dataFreshness;
        return this;
    }

    @JsonProperty("maxNoEntities")
    public Integer getMaxNoEntities() {
        return maxNoEntities;
    }

    public void setMaxNoEntities(Integer maxNoEntities) {
        this.maxNoEntities = maxNoEntities;
    }

    public ServiceBaseDTO maxNoEntities(Integer maxNoEntities) {
        this.maxNoEntities = maxNoEntities;
        return this;
    }

    @JsonProperty("maxNoRequests")
    public Integer getMaxNoRequests() {
        return maxNoRequests;
    }

    public void setMaxNoRequests(Integer maxNoRequests) {
        this.maxNoRequests = maxNoRequests;
    }

    public ServiceBaseDTO maxNoRequests(Integer maxNoRequests) {
        this.maxNoRequests = maxNoRequests;
        return this;
    }

    @JsonProperty("expResponseTime")
    public Integer getExpResponseTime() {
        return expResponseTime;
    }

    public void setExpResponseTime(Integer expResponseTime) {
        this.expResponseTime = expResponseTime;
    }

    public ServiceBaseDTO expResponseTime(Integer expResponseTime) {
        this.expResponseTime = expResponseTime;
        return this;
    }

    @JsonProperty("dataLocation")
    public List<String> getDataLocation() {
        return dataLocation;
    }

    public void setDataLocation(List<String> dataLocation) {
        this.dataLocation = dataLocation;
    }

    public ServiceBaseDTO dataLocation(List<String> dataLocation) {
        this.dataLocation = dataLocation;
        return this;
    }
    public ServiceBaseDTO addDataLocationItem(String dataLocationItem) {
        this.dataLocation.add(dataLocationItem);
        return this;
    }

    @JsonProperty("informationExchanged")
    public List<String> getInformationExchanged() {
        return informationExchanged;
    }

    public void setInformationExchanged(List<String> informationExchanged) {
        this.informationExchanged = informationExchanged;
    }

    public ServiceBaseDTO informationExchanged(List<String> informationExchanged) {
        this.informationExchanged = informationExchanged;
        return this;
    }
    public ServiceBaseDTO addInformationExchangedItem(String informationExchangedItem) {
        this.informationExchanged.add(informationExchangedItem);
        return this;
    }

    @JsonProperty("queryByExample")
    public String getQueryByExample() {
        return queryByExample;
    }

    public void setQueryByExample(String queryByExample) {
        this.queryByExample = queryByExample;
    }

    public ServiceBaseDTO queryByExample(String queryByExample) {
        this.queryByExample = queryByExample;
        return this;
    }

    @JsonProperty("supportedQueries")
    public List<String> getSupportedQueries() {
        return supportedQueries;
    }

    public void setSupportedQueries(List<String> supportedQueries) {
        this.supportedQueries = supportedQueries;
    }

    public ServiceBaseDTO supportedQueries(List<String> supportedQueries) {
        this.supportedQueries = supportedQueries;
        return this;
    }
    public ServiceBaseDTO addSupportedQueriesItem(String supportedQueriesItem) {
        this.supportedQueries.add(supportedQueriesItem);
        return this;
    }

    @JsonProperty("streamServiceType")
    public String getStreamServiceType() {
        return streamServiceType;
    }

    public void setStreamServiceType(String streamServiceType) {
        this.streamServiceType = streamServiceType;
    }

    public ServiceBaseDTO streamServiceType(String streamServiceType) {
        this.streamServiceType = streamServiceType;
        return this;
    }

    @JsonProperty("gisServerUrl")
    public String getGisServerUrl() {
        return gisServerUrl;
    }

    public void setGisServerUrl(String gisServerUrl) {
        this.gisServerUrl = gisServerUrl;
    }

    public ServiceBaseDTO gisServerUrl(String gisServerUrl) {
        this.gisServerUrl = gisServerUrl;
        return this;
    }

    @JsonProperty("gisServerUsername")
    public String getGisServerUsername() {
        return gisServerUsername;
    }

    public void setGisServerUsername(String gisServerUsername) {
        this.gisServerUsername = gisServerUsername;
    }

    public ServiceBaseDTO gisServerUsername(String gisServerUsername) {
        this.gisServerUsername = gisServerUsername;
        return this;
    }

    @JsonProperty("gisServerPassword")
    public String getGisServerPassword() {
        return gisServerPassword;
    }

    public void setGisServerPassword(String gisServerPassword) {
        this.gisServerPassword = gisServerPassword;
    }

    public ServiceBaseDTO gisServerPassword(String gisServerPassword) {
        this.gisServerPassword = gisServerPassword;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ServiceBaseDTO {\n");

        sb.append("    serviceType: ").append(toIndentedString(serviceType)).append("\n");
        sb.append("    serviceOperation: ").append(toIndentedString(serviceOperation)).append("\n");
        sb.append("    serviceRole: ").append(toIndentedString(serviceRole)).append("\n");
        sb.append("    serviceName: ").append(toIndentedString(serviceName)).append("\n");
        sb.append("    dataModelVersion: ").append(toIndentedString(dataModelVersion)).append("\n");
        sb.append("    serviceModelVersion: ").append(toIndentedString(serviceModelVersion)).append("\n");
        sb.append("    dataFreshness: ").append(toIndentedString(dataFreshness)).append("\n");
        sb.append("    maxNoEntities: ").append(toIndentedString(maxNoEntities)).append("\n");
        sb.append("    maxNoRequests: ").append(toIndentedString(maxNoRequests)).append("\n");
        sb.append("    expResponseTime: ").append(toIndentedString(expResponseTime)).append("\n");
        sb.append("    dataLocation: ").append(toIndentedString(dataLocation)).append("\n");
        sb.append("    informationExchanged: ").append(toIndentedString(informationExchanged)).append("\n");
        sb.append("    queryByExample: ").append(toIndentedString(queryByExample)).append("\n");
        sb.append("    supportedQueries: ").append(toIndentedString(supportedQueries)).append("\n");
        sb.append("    streamServiceType: ").append(toIndentedString(streamServiceType)).append("\n");
        sb.append("    gisServerUrl: ").append(toIndentedString(gisServerUrl)).append("\n");
        sb.append("    gisServerUsername: ").append(toIndentedString(gisServerUsername)).append("\n");
        sb.append("    gisServerPassword: ").append(toIndentedString(gisServerPassword)).append("\n");
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
    public static class ServiceBaseDTOQueryParam  {

        private String serviceType;
        private String serviceOperation;
        private String serviceRole;
        private String serviceName;
        private String dataModelVersion;
        private String serviceModelVersion;
        private String dataFreshness;
        private Integer maxNoEntities;
        private Integer maxNoRequests;
        private Integer expResponseTime;
        private List<String> dataLocation = null;
        private List<String> informationExchanged = null;
        private String queryByExample;
        private List<String> supportedQueries = null;
        private String streamServiceType;
        private String gisServerUrl;
        private String gisServerUsername;
        private String gisServerPassword;

        @JsonProperty("serviceType")
        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public ServiceBaseDTOQueryParam serviceType(String serviceType) {
            this.serviceType = serviceType;
            return this;
        }

        @JsonProperty("serviceOperation")
        public String getServiceOperation() {
            return serviceOperation;
        }

        public void setServiceOperation(String serviceOperation) {
            this.serviceOperation = serviceOperation;
        }

        public ServiceBaseDTOQueryParam serviceOperation(String serviceOperation) {
            this.serviceOperation = serviceOperation;
            return this;
        }

        @JsonProperty("serviceRole")
        public String getServiceRole() {
            return serviceRole;
        }

        public void setServiceRole(String serviceRole) {
            this.serviceRole = serviceRole;
        }

        public ServiceBaseDTOQueryParam serviceRole(String serviceRole) {
            this.serviceRole = serviceRole;
            return this;
        }

        @JsonProperty("serviceName")
        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public ServiceBaseDTOQueryParam serviceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        @JsonProperty("dataModelVersion")
        public String getDataModelVersion() {
            return dataModelVersion;
        }

        public void setDataModelVersion(String dataModelVersion) {
            this.dataModelVersion = dataModelVersion;
        }

        public ServiceBaseDTOQueryParam dataModelVersion(String dataModelVersion) {
            this.dataModelVersion = dataModelVersion;
            return this;
        }

        @JsonProperty("serviceModelVersion")
        public String getServiceModelVersion() {
            return serviceModelVersion;
        }

        public void setServiceModelVersion(String serviceModelVersion) {
            this.serviceModelVersion = serviceModelVersion;
        }

        public ServiceBaseDTOQueryParam serviceModelVersion(String serviceModelVersion) {
            this.serviceModelVersion = serviceModelVersion;
            return this;
        }

        @JsonProperty("dataFreshness")
        public String getDataFreshness() {
            return dataFreshness;
        }

        public void setDataFreshness(String dataFreshness) {
            this.dataFreshness = dataFreshness;
        }

        public ServiceBaseDTOQueryParam dataFreshness(String dataFreshness) {
            this.dataFreshness = dataFreshness;
            return this;
        }

        @JsonProperty("maxNoEntities")
        public Integer getMaxNoEntities() {
            return maxNoEntities;
        }

        public void setMaxNoEntities(Integer maxNoEntities) {
            this.maxNoEntities = maxNoEntities;
        }

        public ServiceBaseDTOQueryParam maxNoEntities(Integer maxNoEntities) {
            this.maxNoEntities = maxNoEntities;
            return this;
        }

        @JsonProperty("maxNoRequests")
        public Integer getMaxNoRequests() {
            return maxNoRequests;
        }

        public void setMaxNoRequests(Integer maxNoRequests) {
            this.maxNoRequests = maxNoRequests;
        }

        public ServiceBaseDTOQueryParam maxNoRequests(Integer maxNoRequests) {
            this.maxNoRequests = maxNoRequests;
            return this;
        }

        @JsonProperty("expResponseTime")
        public Integer getExpResponseTime() {
            return expResponseTime;
        }

        public void setExpResponseTime(Integer expResponseTime) {
            this.expResponseTime = expResponseTime;
        }

        public ServiceBaseDTOQueryParam expResponseTime(Integer expResponseTime) {
            this.expResponseTime = expResponseTime;
            return this;
        }

        @JsonProperty("dataLocation")
        public List<String> getDataLocation() {
            return dataLocation;
        }

        public void setDataLocation(List<String> dataLocation) {
            this.dataLocation = dataLocation;
        }

        public ServiceBaseDTOQueryParam dataLocation(List<String> dataLocation) {
            this.dataLocation = dataLocation;
            return this;
        }
        public ServiceBaseDTOQueryParam addDataLocationItem(String dataLocationItem) {
            this.dataLocation.add(dataLocationItem);
            return this;
        }

        @JsonProperty("informationExchanged")
        public List<String> getInformationExchanged() {
            return informationExchanged;
        }

        public void setInformationExchanged(List<String> informationExchanged) {
            this.informationExchanged = informationExchanged;
        }

        public ServiceBaseDTOQueryParam informationExchanged(List<String> informationExchanged) {
            this.informationExchanged = informationExchanged;
            return this;
        }
        public ServiceBaseDTOQueryParam addInformationExchangedItem(String informationExchangedItem) {
            this.informationExchanged.add(informationExchangedItem);
            return this;
        }

        @JsonProperty("queryByExample")
        public String getQueryByExample() {
            return queryByExample;
        }

        public void setQueryByExample(String queryByExample) {
            this.queryByExample = queryByExample;
        }

        public ServiceBaseDTOQueryParam queryByExample(String queryByExample) {
            this.queryByExample = queryByExample;
            return this;
        }

        @JsonProperty("supportedQueries")
        public List<String> getSupportedQueries() {
            return supportedQueries;
        }

        public void setSupportedQueries(List<String> supportedQueries) {
            this.supportedQueries = supportedQueries;
        }

        public ServiceBaseDTOQueryParam supportedQueries(List<String> supportedQueries) {
            this.supportedQueries = supportedQueries;
            return this;
        }
        public ServiceBaseDTOQueryParam addSupportedQueriesItem(String supportedQueriesItem) {
            this.supportedQueries.add(supportedQueriesItem);
            return this;
        }

        @JsonProperty("streamServiceType")
        public String getStreamServiceType() {
            return streamServiceType;
        }

        public void setStreamServiceType(String streamServiceType) {
            this.streamServiceType = streamServiceType;
        }

        public ServiceBaseDTOQueryParam streamServiceType(String streamServiceType) {
            this.streamServiceType = streamServiceType;
            return this;
        }

        @JsonProperty("gisServerUrl")
        public String getGisServerUrl() {
            return gisServerUrl;
        }

        public void setGisServerUrl(String gisServerUrl) {
            this.gisServerUrl = gisServerUrl;
        }

        public ServiceBaseDTOQueryParam gisServerUrl(String gisServerUrl) {
            this.gisServerUrl = gisServerUrl;
            return this;
        }

        @JsonProperty("gisServerUsername")
        public String getGisServerUsername() {
            return gisServerUsername;
        }

        public void setGisServerUsername(String gisServerUsername) {
            this.gisServerUsername = gisServerUsername;
        }

        public ServiceBaseDTOQueryParam gisServerUsername(String gisServerUsername) {
            this.gisServerUsername = gisServerUsername;
            return this;
        }

        @JsonProperty("gisServerPassword")
        public String getGisServerPassword() {
            return gisServerPassword;
        }

        public void setGisServerPassword(String gisServerPassword) {
            this.gisServerPassword = gisServerPassword;
        }

        public ServiceBaseDTOQueryParam gisServerPassword(String gisServerPassword) {
            this.gisServerPassword = gisServerPassword;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class ServiceBaseDTOQueryParam {\n");

            sb.append("    serviceType: ").append(toIndentedString(serviceType)).append("\n");
            sb.append("    serviceOperation: ").append(toIndentedString(serviceOperation)).append("\n");
            sb.append("    serviceRole: ").append(toIndentedString(serviceRole)).append("\n");
            sb.append("    serviceName: ").append(toIndentedString(serviceName)).append("\n");
            sb.append("    dataModelVersion: ").append(toIndentedString(dataModelVersion)).append("\n");
            sb.append("    serviceModelVersion: ").append(toIndentedString(serviceModelVersion)).append("\n");
            sb.append("    dataFreshness: ").append(toIndentedString(dataFreshness)).append("\n");
            sb.append("    maxNoEntities: ").append(toIndentedString(maxNoEntities)).append("\n");
            sb.append("    maxNoRequests: ").append(toIndentedString(maxNoRequests)).append("\n");
            sb.append("    expResponseTime: ").append(toIndentedString(expResponseTime)).append("\n");
            sb.append("    dataLocation: ").append(toIndentedString(dataLocation)).append("\n");
            sb.append("    informationExchanged: ").append(toIndentedString(informationExchanged)).append("\n");
            sb.append("    queryByExample: ").append(toIndentedString(queryByExample)).append("\n");
            sb.append("    supportedQueries: ").append(toIndentedString(supportedQueries)).append("\n");
            sb.append("    streamServiceType: ").append(toIndentedString(streamServiceType)).append("\n");
            sb.append("    gisServerUrl: ").append(toIndentedString(gisServerUrl)).append("\n");
            sb.append("    gisServerUsername: ").append(toIndentedString(gisServerUsername)).append("\n");
            sb.append("    gisServerPassword: ").append(toIndentedString(gisServerPassword)).append("\n");
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