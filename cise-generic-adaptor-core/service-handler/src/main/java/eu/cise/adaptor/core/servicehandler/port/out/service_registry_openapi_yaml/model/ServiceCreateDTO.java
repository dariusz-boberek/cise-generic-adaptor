package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceCreateDTO  {

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
    private String participantId;
    private String serviceId;

    @JsonProperty("serviceType")
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public ServiceCreateDTO serviceType(String serviceType) {
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

    public ServiceCreateDTO serviceOperation(String serviceOperation) {
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

    public ServiceCreateDTO serviceRole(String serviceRole) {
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

    public ServiceCreateDTO serviceName(String serviceName) {
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

    public ServiceCreateDTO dataModelVersion(String dataModelVersion) {
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

    public ServiceCreateDTO serviceModelVersion(String serviceModelVersion) {
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

    public ServiceCreateDTO dataFreshness(String dataFreshness) {
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

    public ServiceCreateDTO maxNoEntities(Integer maxNoEntities) {
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

    public ServiceCreateDTO maxNoRequests(Integer maxNoRequests) {
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

    public ServiceCreateDTO expResponseTime(Integer expResponseTime) {
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

    public ServiceCreateDTO dataLocation(List<String> dataLocation) {
        this.dataLocation = dataLocation;
        return this;
    }
    public ServiceCreateDTO addDataLocationItem(String dataLocationItem) {
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

    public ServiceCreateDTO informationExchanged(List<String> informationExchanged) {
        this.informationExchanged = informationExchanged;
        return this;
    }
    public ServiceCreateDTO addInformationExchangedItem(String informationExchangedItem) {
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

    public ServiceCreateDTO queryByExample(String queryByExample) {
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

    public ServiceCreateDTO supportedQueries(List<String> supportedQueries) {
        this.supportedQueries = supportedQueries;
        return this;
    }
    public ServiceCreateDTO addSupportedQueriesItem(String supportedQueriesItem) {
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

    public ServiceCreateDTO streamServiceType(String streamServiceType) {
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

    public ServiceCreateDTO gisServerUrl(String gisServerUrl) {
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

    public ServiceCreateDTO gisServerUsername(String gisServerUsername) {
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

    public ServiceCreateDTO gisServerPassword(String gisServerPassword) {
        this.gisServerPassword = gisServerPassword;
        return this;
    }

    @JsonProperty("participantId")
    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public ServiceCreateDTO participantId(String participantId) {
        this.participantId = participantId;
        return this;
    }

    @JsonProperty("serviceId")
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public ServiceCreateDTO serviceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ServiceCreateDTO {\n");

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
        sb.append("    participantId: ").append(toIndentedString(participantId)).append("\n");
        sb.append("    serviceId: ").append(toIndentedString(serviceId)).append("\n");
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
    public static class ServiceCreateDTOQueryParam  {

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
        private String participantId;
        private String serviceId;

        @JsonProperty("serviceType")
        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public ServiceCreateDTOQueryParam serviceType(String serviceType) {
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

        public ServiceCreateDTOQueryParam serviceOperation(String serviceOperation) {
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

        public ServiceCreateDTOQueryParam serviceRole(String serviceRole) {
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

        public ServiceCreateDTOQueryParam serviceName(String serviceName) {
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

        public ServiceCreateDTOQueryParam dataModelVersion(String dataModelVersion) {
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

        public ServiceCreateDTOQueryParam serviceModelVersion(String serviceModelVersion) {
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

        public ServiceCreateDTOQueryParam dataFreshness(String dataFreshness) {
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

        public ServiceCreateDTOQueryParam maxNoEntities(Integer maxNoEntities) {
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

        public ServiceCreateDTOQueryParam maxNoRequests(Integer maxNoRequests) {
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

        public ServiceCreateDTOQueryParam expResponseTime(Integer expResponseTime) {
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

        public ServiceCreateDTOQueryParam dataLocation(List<String> dataLocation) {
            this.dataLocation = dataLocation;
            return this;
        }
        public ServiceCreateDTOQueryParam addDataLocationItem(String dataLocationItem) {
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

        public ServiceCreateDTOQueryParam informationExchanged(List<String> informationExchanged) {
            this.informationExchanged = informationExchanged;
            return this;
        }
        public ServiceCreateDTOQueryParam addInformationExchangedItem(String informationExchangedItem) {
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

        public ServiceCreateDTOQueryParam queryByExample(String queryByExample) {
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

        public ServiceCreateDTOQueryParam supportedQueries(List<String> supportedQueries) {
            this.supportedQueries = supportedQueries;
            return this;
        }
        public ServiceCreateDTOQueryParam addSupportedQueriesItem(String supportedQueriesItem) {
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

        public ServiceCreateDTOQueryParam streamServiceType(String streamServiceType) {
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

        public ServiceCreateDTOQueryParam gisServerUrl(String gisServerUrl) {
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

        public ServiceCreateDTOQueryParam gisServerUsername(String gisServerUsername) {
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

        public ServiceCreateDTOQueryParam gisServerPassword(String gisServerPassword) {
            this.gisServerPassword = gisServerPassword;
            return this;
        }

        @JsonProperty("participantId")
        public String getParticipantId() {
            return participantId;
        }

        public void setParticipantId(String participantId) {
            this.participantId = participantId;
        }

        public ServiceCreateDTOQueryParam participantId(String participantId) {
            this.participantId = participantId;
            return this;
        }

        @JsonProperty("serviceId")
        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public ServiceCreateDTOQueryParam serviceId(String serviceId) {
            this.serviceId = serviceId;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class ServiceCreateDTOQueryParam {\n");

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
            sb.append("    participantId: ").append(toIndentedString(participantId)).append("\n");
            sb.append("    serviceId: ").append(toIndentedString(serviceId)).append("\n");
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