package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceDTO  {

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
    private String uuid;
    private String serviceId;
    private String participantId;
    private String nodeId;
    private String serviceStatus;

    @JsonProperty("serviceType")
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public ServiceDTO serviceType(String serviceType) {
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

    public ServiceDTO serviceOperation(String serviceOperation) {
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

    public ServiceDTO serviceRole(String serviceRole) {
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

    public ServiceDTO serviceName(String serviceName) {
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

    public ServiceDTO dataModelVersion(String dataModelVersion) {
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

    public ServiceDTO serviceModelVersion(String serviceModelVersion) {
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

    public ServiceDTO dataFreshness(String dataFreshness) {
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

    public ServiceDTO maxNoEntities(Integer maxNoEntities) {
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

    public ServiceDTO maxNoRequests(Integer maxNoRequests) {
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

    public ServiceDTO expResponseTime(Integer expResponseTime) {
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

    public ServiceDTO dataLocation(List<String> dataLocation) {
        this.dataLocation = dataLocation;
        return this;
    }
    public ServiceDTO addDataLocationItem(String dataLocationItem) {
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

    public ServiceDTO informationExchanged(List<String> informationExchanged) {
        this.informationExchanged = informationExchanged;
        return this;
    }
    public ServiceDTO addInformationExchangedItem(String informationExchangedItem) {
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

    public ServiceDTO queryByExample(String queryByExample) {
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

    public ServiceDTO supportedQueries(List<String> supportedQueries) {
        this.supportedQueries = supportedQueries;
        return this;
    }
    public ServiceDTO addSupportedQueriesItem(String supportedQueriesItem) {
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

    public ServiceDTO streamServiceType(String streamServiceType) {
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

    public ServiceDTO gisServerUrl(String gisServerUrl) {
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

    public ServiceDTO gisServerUsername(String gisServerUsername) {
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

    public ServiceDTO gisServerPassword(String gisServerPassword) {
        this.gisServerPassword = gisServerPassword;
        return this;
    }

    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ServiceDTO uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @JsonProperty("serviceId")
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public ServiceDTO serviceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    @JsonProperty("participantId")
    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public ServiceDTO participantId(String participantId) {
        this.participantId = participantId;
        return this;
    }

    @JsonProperty("nodeId")
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public ServiceDTO nodeId(String nodeId) {
        this.nodeId = nodeId;
        return this;
    }

    @JsonProperty("serviceStatus")
    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public ServiceDTO serviceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ServiceDTO {\n");

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
        sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
        sb.append("    serviceId: ").append(toIndentedString(serviceId)).append("\n");
        sb.append("    participantId: ").append(toIndentedString(participantId)).append("\n");
        sb.append("    nodeId: ").append(toIndentedString(nodeId)).append("\n");
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
    public static class ServiceDTOQueryParam  {

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
        private String uuid;
        private String serviceId;
        private String participantId;
        private String nodeId;
        private String serviceStatus;

        @JsonProperty("serviceType")
        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public ServiceDTOQueryParam serviceType(String serviceType) {
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

        public ServiceDTOQueryParam serviceOperation(String serviceOperation) {
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

        public ServiceDTOQueryParam serviceRole(String serviceRole) {
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

        public ServiceDTOQueryParam serviceName(String serviceName) {
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

        public ServiceDTOQueryParam dataModelVersion(String dataModelVersion) {
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

        public ServiceDTOQueryParam serviceModelVersion(String serviceModelVersion) {
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

        public ServiceDTOQueryParam dataFreshness(String dataFreshness) {
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

        public ServiceDTOQueryParam maxNoEntities(Integer maxNoEntities) {
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

        public ServiceDTOQueryParam maxNoRequests(Integer maxNoRequests) {
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

        public ServiceDTOQueryParam expResponseTime(Integer expResponseTime) {
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

        public ServiceDTOQueryParam dataLocation(List<String> dataLocation) {
            this.dataLocation = dataLocation;
            return this;
        }
        public ServiceDTOQueryParam addDataLocationItem(String dataLocationItem) {
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

        public ServiceDTOQueryParam informationExchanged(List<String> informationExchanged) {
            this.informationExchanged = informationExchanged;
            return this;
        }
        public ServiceDTOQueryParam addInformationExchangedItem(String informationExchangedItem) {
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

        public ServiceDTOQueryParam queryByExample(String queryByExample) {
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

        public ServiceDTOQueryParam supportedQueries(List<String> supportedQueries) {
            this.supportedQueries = supportedQueries;
            return this;
        }
        public ServiceDTOQueryParam addSupportedQueriesItem(String supportedQueriesItem) {
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

        public ServiceDTOQueryParam streamServiceType(String streamServiceType) {
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

        public ServiceDTOQueryParam gisServerUrl(String gisServerUrl) {
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

        public ServiceDTOQueryParam gisServerUsername(String gisServerUsername) {
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

        public ServiceDTOQueryParam gisServerPassword(String gisServerPassword) {
            this.gisServerPassword = gisServerPassword;
            return this;
        }

        @JsonProperty("uuid")
        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public ServiceDTOQueryParam uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        @JsonProperty("serviceId")
        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public ServiceDTOQueryParam serviceId(String serviceId) {
            this.serviceId = serviceId;
            return this;
        }

        @JsonProperty("participantId")
        public String getParticipantId() {
            return participantId;
        }

        public void setParticipantId(String participantId) {
            this.participantId = participantId;
        }

        public ServiceDTOQueryParam participantId(String participantId) {
            this.participantId = participantId;
            return this;
        }

        @JsonProperty("nodeId")
        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public ServiceDTOQueryParam nodeId(String nodeId) {
            this.nodeId = nodeId;
            return this;
        }

        @JsonProperty("serviceStatus")
        public String getServiceStatus() {
            return serviceStatus;
        }

        public void setServiceStatus(String serviceStatus) {
            this.serviceStatus = serviceStatus;
        }

        public ServiceDTOQueryParam serviceStatus(String serviceStatus) {
            this.serviceStatus = serviceStatus;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class ServiceDTOQueryParam {\n");

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
            sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
            sb.append("    serviceId: ").append(toIndentedString(serviceId)).append("\n");
            sb.append("    participantId: ").append(toIndentedString(participantId)).append("\n");
            sb.append("    nodeId: ").append(toIndentedString(nodeId)).append("\n");
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