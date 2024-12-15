package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeDTO  {

    private String name;
    private String domain;
    private String gateway;
    private String classification;
    private List<String> countries = new ArrayList<>();
    private Boolean trusted;
    private Boolean secureConnection;
    private String certificateFileContent;
    private String certificateFileName;
    private String uuid;
    private String nodeId;
    private String nodeVersion;
    private String syncEndpointUrl;
    private String messageEndpointUrl;
    private String healthEndpointUrl;
    private String nodeAdaptorEndpointUrl;
    private String nodeAdaptorManagementEndpointUrl;
    private NodeDTOManagingAuthority managingAuthority;
    private List<ContactPersonDTO> contactPersons = null;
    private LocalDate lastSyncAttempt;
    private LocalDate lastSuccessfulSync;
    private SyncStatus syncStatus;
    private NodeHealth health;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NodeDTO name(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("domain")
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public NodeDTO domain(String domain) {
        this.domain = domain;
        return this;
    }

    @JsonProperty("gateway")
    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public NodeDTO gateway(String gateway) {
        this.gateway = gateway;
        return this;
    }

    @JsonProperty("classification")
    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public NodeDTO classification(String classification) {
        this.classification = classification;
        return this;
    }

    @JsonProperty("countries")
    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public NodeDTO countries(List<String> countries) {
        this.countries = countries;
        return this;
    }
    public NodeDTO addCountriesItem(String countriesItem) {
        this.countries.add(countriesItem);
        return this;
    }

    @JsonProperty("trusted")
    public Boolean getTrusted() {
        return trusted;
    }

    public void setTrusted(Boolean trusted) {
        this.trusted = trusted;
    }

    public NodeDTO trusted(Boolean trusted) {
        this.trusted = trusted;
        return this;
    }

    @JsonProperty("secureConnection")
    public Boolean getSecureConnection() {
        return secureConnection;
    }

    public void setSecureConnection(Boolean secureConnection) {
        this.secureConnection = secureConnection;
    }

    public NodeDTO secureConnection(Boolean secureConnection) {
        this.secureConnection = secureConnection;
        return this;
    }

    @JsonProperty("certificateFileContent")
    public String getCertificateFileContent() {
        return certificateFileContent;
    }

    public void setCertificateFileContent(String certificateFileContent) {
        this.certificateFileContent = certificateFileContent;
    }

    public NodeDTO certificateFileContent(String certificateFileContent) {
        this.certificateFileContent = certificateFileContent;
        return this;
    }

    @JsonProperty("certificateFileName")
    public String getCertificateFileName() {
        return certificateFileName;
    }

    public void setCertificateFileName(String certificateFileName) {
        this.certificateFileName = certificateFileName;
    }

    public NodeDTO certificateFileName(String certificateFileName) {
        this.certificateFileName = certificateFileName;
        return this;
    }

    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public NodeDTO uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @JsonProperty("nodeId")
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public NodeDTO nodeId(String nodeId) {
        this.nodeId = nodeId;
        return this;
    }

    @JsonProperty("nodeVersion")
    public String getNodeVersion() {
        return nodeVersion;
    }

    public void setNodeVersion(String nodeVersion) {
        this.nodeVersion = nodeVersion;
    }

    public NodeDTO nodeVersion(String nodeVersion) {
        this.nodeVersion = nodeVersion;
        return this;
    }

    @JsonProperty("syncEndpointUrl")
    public String getSyncEndpointUrl() {
        return syncEndpointUrl;
    }

    public void setSyncEndpointUrl(String syncEndpointUrl) {
        this.syncEndpointUrl = syncEndpointUrl;
    }

    public NodeDTO syncEndpointUrl(String syncEndpointUrl) {
        this.syncEndpointUrl = syncEndpointUrl;
        return this;
    }

    @JsonProperty("messageEndpointUrl")
    public String getMessageEndpointUrl() {
        return messageEndpointUrl;
    }

    public void setMessageEndpointUrl(String messageEndpointUrl) {
        this.messageEndpointUrl = messageEndpointUrl;
    }

    public NodeDTO messageEndpointUrl(String messageEndpointUrl) {
        this.messageEndpointUrl = messageEndpointUrl;
        return this;
    }

    @JsonProperty("healthEndpointUrl")
    public String getHealthEndpointUrl() {
        return healthEndpointUrl;
    }

    public void setHealthEndpointUrl(String healthEndpointUrl) {
        this.healthEndpointUrl = healthEndpointUrl;
    }

    public NodeDTO healthEndpointUrl(String healthEndpointUrl) {
        this.healthEndpointUrl = healthEndpointUrl;
        return this;
    }

    @JsonProperty("nodeAdaptorEndpointUrl")
    public String getNodeAdaptorEndpointUrl() {
        return nodeAdaptorEndpointUrl;
    }

    public void setNodeAdaptorEndpointUrl(String nodeAdaptorEndpointUrl) {
        this.nodeAdaptorEndpointUrl = nodeAdaptorEndpointUrl;
    }

    public NodeDTO nodeAdaptorEndpointUrl(String nodeAdaptorEndpointUrl) {
        this.nodeAdaptorEndpointUrl = nodeAdaptorEndpointUrl;
        return this;
    }

    @JsonProperty("nodeAdaptorManagementEndpointUrl")
    public String getNodeAdaptorManagementEndpointUrl() {
        return nodeAdaptorManagementEndpointUrl;
    }

    public void setNodeAdaptorManagementEndpointUrl(String nodeAdaptorManagementEndpointUrl) {
        this.nodeAdaptorManagementEndpointUrl = nodeAdaptorManagementEndpointUrl;
    }

    public NodeDTO nodeAdaptorManagementEndpointUrl(String nodeAdaptorManagementEndpointUrl) {
        this.nodeAdaptorManagementEndpointUrl = nodeAdaptorManagementEndpointUrl;
        return this;
    }

    @JsonProperty("managingAuthority")
    public NodeDTOManagingAuthority getManagingAuthority() {
        return managingAuthority;
    }

    public void setManagingAuthority(NodeDTOManagingAuthority managingAuthority) {
        this.managingAuthority = managingAuthority;
    }

    public NodeDTO managingAuthority(NodeDTOManagingAuthority managingAuthority) {
        this.managingAuthority = managingAuthority;
        return this;
    }

    @JsonProperty("contactPersons")
    public List<ContactPersonDTO> getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(List<ContactPersonDTO> contactPersons) {
        this.contactPersons = contactPersons;
    }

    public NodeDTO contactPersons(List<ContactPersonDTO> contactPersons) {
        this.contactPersons = contactPersons;
        return this;
    }
    public NodeDTO addContactPersonsItem(ContactPersonDTO contactPersonsItem) {
        this.contactPersons.add(contactPersonsItem);
        return this;
    }

    @JsonProperty("lastSyncAttempt")
    public LocalDate getLastSyncAttempt() {
        return lastSyncAttempt;
    }

    public void setLastSyncAttempt(LocalDate lastSyncAttempt) {
        this.lastSyncAttempt = lastSyncAttempt;
    }

    public NodeDTO lastSyncAttempt(LocalDate lastSyncAttempt) {
        this.lastSyncAttempt = lastSyncAttempt;
        return this;
    }

    @JsonProperty("lastSuccessfulSync")
    public LocalDate getLastSuccessfulSync() {
        return lastSuccessfulSync;
    }

    public void setLastSuccessfulSync(LocalDate lastSuccessfulSync) {
        this.lastSuccessfulSync = lastSuccessfulSync;
    }

    public NodeDTO lastSuccessfulSync(LocalDate lastSuccessfulSync) {
        this.lastSuccessfulSync = lastSuccessfulSync;
        return this;
    }

    @JsonProperty("syncStatus")
    public SyncStatus getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(SyncStatus syncStatus) {
        this.syncStatus = syncStatus;
    }

    public NodeDTO syncStatus(SyncStatus syncStatus) {
        this.syncStatus = syncStatus;
        return this;
    }

    @JsonProperty("health")
    public NodeHealth getHealth() {
        return health;
    }

    public void setHealth(NodeHealth health) {
        this.health = health;
    }

    public NodeDTO health(NodeHealth health) {
        this.health = health;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NodeDTO {\n");

        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
        sb.append("    gateway: ").append(toIndentedString(gateway)).append("\n");
        sb.append("    classification: ").append(toIndentedString(classification)).append("\n");
        sb.append("    countries: ").append(toIndentedString(countries)).append("\n");
        sb.append("    trusted: ").append(toIndentedString(trusted)).append("\n");
        sb.append("    secureConnection: ").append(toIndentedString(secureConnection)).append("\n");
        sb.append("    certificateFileContent: ").append(toIndentedString(certificateFileContent)).append("\n");
        sb.append("    certificateFileName: ").append(toIndentedString(certificateFileName)).append("\n");
        sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
        sb.append("    nodeId: ").append(toIndentedString(nodeId)).append("\n");
        sb.append("    nodeVersion: ").append(toIndentedString(nodeVersion)).append("\n");
        sb.append("    syncEndpointUrl: ").append(toIndentedString(syncEndpointUrl)).append("\n");
        sb.append("    messageEndpointUrl: ").append(toIndentedString(messageEndpointUrl)).append("\n");
        sb.append("    healthEndpointUrl: ").append(toIndentedString(healthEndpointUrl)).append("\n");
        sb.append("    nodeAdaptorEndpointUrl: ").append(toIndentedString(nodeAdaptorEndpointUrl)).append("\n");
        sb.append("    nodeAdaptorManagementEndpointUrl: ").append(toIndentedString(nodeAdaptorManagementEndpointUrl)).append("\n");
        sb.append("    managingAuthority: ").append(toIndentedString(managingAuthority)).append("\n");
        sb.append("    contactPersons: ").append(toIndentedString(contactPersons)).append("\n");
        sb.append("    lastSyncAttempt: ").append(toIndentedString(lastSyncAttempt)).append("\n");
        sb.append("    lastSuccessfulSync: ").append(toIndentedString(lastSuccessfulSync)).append("\n");
        sb.append("    syncStatus: ").append(toIndentedString(syncStatus)).append("\n");
        sb.append("    health: ").append(toIndentedString(health)).append("\n");
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
    public static class NodeDTOQueryParam  {

        private String name;
        private String domain;
        private String gateway;
        private String classification;
        private List<String> countries = new ArrayList<>();
        private Boolean trusted;
        private Boolean secureConnection;
        private String certificateFileContent;
        private String certificateFileName;
        private String uuid;
        private String nodeId;
        private String nodeVersion;
        private String syncEndpointUrl;
        private String messageEndpointUrl;
        private String healthEndpointUrl;
        private String nodeAdaptorEndpointUrl;
        private String nodeAdaptorManagementEndpointUrl;
        private NodeDTOManagingAuthority managingAuthority;
        private List<ContactPersonDTO> contactPersons = null;
        private LocalDate lastSyncAttempt;
        private LocalDate lastSuccessfulSync;
        private SyncStatus syncStatus;
        private NodeHealth health;

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public NodeDTOQueryParam name(String name) {
            this.name = name;
            return this;
        }

        @JsonProperty("domain")
        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public NodeDTOQueryParam domain(String domain) {
            this.domain = domain;
            return this;
        }

        @JsonProperty("gateway")
        public String getGateway() {
            return gateway;
        }

        public void setGateway(String gateway) {
            this.gateway = gateway;
        }

        public NodeDTOQueryParam gateway(String gateway) {
            this.gateway = gateway;
            return this;
        }

        @JsonProperty("classification")
        public String getClassification() {
            return classification;
        }

        public void setClassification(String classification) {
            this.classification = classification;
        }

        public NodeDTOQueryParam classification(String classification) {
            this.classification = classification;
            return this;
        }

        @JsonProperty("countries")
        public List<String> getCountries() {
            return countries;
        }

        public void setCountries(List<String> countries) {
            this.countries = countries;
        }

        public NodeDTOQueryParam countries(List<String> countries) {
            this.countries = countries;
            return this;
        }
        public NodeDTOQueryParam addCountriesItem(String countriesItem) {
            this.countries.add(countriesItem);
            return this;
        }

        @JsonProperty("trusted")
        public Boolean getTrusted() {
            return trusted;
        }

        public void setTrusted(Boolean trusted) {
            this.trusted = trusted;
        }

        public NodeDTOQueryParam trusted(Boolean trusted) {
            this.trusted = trusted;
            return this;
        }

        @JsonProperty("secureConnection")
        public Boolean getSecureConnection() {
            return secureConnection;
        }

        public void setSecureConnection(Boolean secureConnection) {
            this.secureConnection = secureConnection;
        }

        public NodeDTOQueryParam secureConnection(Boolean secureConnection) {
            this.secureConnection = secureConnection;
            return this;
        }

        @JsonProperty("certificateFileContent")
        public String getCertificateFileContent() {
            return certificateFileContent;
        }

        public void setCertificateFileContent(String certificateFileContent) {
            this.certificateFileContent = certificateFileContent;
        }

        public NodeDTOQueryParam certificateFileContent(String certificateFileContent) {
            this.certificateFileContent = certificateFileContent;
            return this;
        }

        @JsonProperty("certificateFileName")
        public String getCertificateFileName() {
            return certificateFileName;
        }

        public void setCertificateFileName(String certificateFileName) {
            this.certificateFileName = certificateFileName;
        }

        public NodeDTOQueryParam certificateFileName(String certificateFileName) {
            this.certificateFileName = certificateFileName;
            return this;
        }

        @JsonProperty("uuid")
        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public NodeDTOQueryParam uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        @JsonProperty("nodeId")
        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public NodeDTOQueryParam nodeId(String nodeId) {
            this.nodeId = nodeId;
            return this;
        }

        @JsonProperty("nodeVersion")
        public String getNodeVersion() {
            return nodeVersion;
        }

        public void setNodeVersion(String nodeVersion) {
            this.nodeVersion = nodeVersion;
        }

        public NodeDTOQueryParam nodeVersion(String nodeVersion) {
            this.nodeVersion = nodeVersion;
            return this;
        }

        @JsonProperty("syncEndpointUrl")
        public String getSyncEndpointUrl() {
            return syncEndpointUrl;
        }

        public void setSyncEndpointUrl(String syncEndpointUrl) {
            this.syncEndpointUrl = syncEndpointUrl;
        }

        public NodeDTOQueryParam syncEndpointUrl(String syncEndpointUrl) {
            this.syncEndpointUrl = syncEndpointUrl;
            return this;
        }

        @JsonProperty("messageEndpointUrl")
        public String getMessageEndpointUrl() {
            return messageEndpointUrl;
        }

        public void setMessageEndpointUrl(String messageEndpointUrl) {
            this.messageEndpointUrl = messageEndpointUrl;
        }

        public NodeDTOQueryParam messageEndpointUrl(String messageEndpointUrl) {
            this.messageEndpointUrl = messageEndpointUrl;
            return this;
        }

        @JsonProperty("healthEndpointUrl")
        public String getHealthEndpointUrl() {
            return healthEndpointUrl;
        }

        public void setHealthEndpointUrl(String healthEndpointUrl) {
            this.healthEndpointUrl = healthEndpointUrl;
        }

        public NodeDTOQueryParam healthEndpointUrl(String healthEndpointUrl) {
            this.healthEndpointUrl = healthEndpointUrl;
            return this;
        }

        @JsonProperty("nodeAdaptorEndpointUrl")
        public String getNodeAdaptorEndpointUrl() {
            return nodeAdaptorEndpointUrl;
        }

        public void setNodeAdaptorEndpointUrl(String nodeAdaptorEndpointUrl) {
            this.nodeAdaptorEndpointUrl = nodeAdaptorEndpointUrl;
        }

        public NodeDTOQueryParam nodeAdaptorEndpointUrl(String nodeAdaptorEndpointUrl) {
            this.nodeAdaptorEndpointUrl = nodeAdaptorEndpointUrl;
            return this;
        }

        @JsonProperty("nodeAdaptorManagementEndpointUrl")
        public String getNodeAdaptorManagementEndpointUrl() {
            return nodeAdaptorManagementEndpointUrl;
        }

        public void setNodeAdaptorManagementEndpointUrl(String nodeAdaptorManagementEndpointUrl) {
            this.nodeAdaptorManagementEndpointUrl = nodeAdaptorManagementEndpointUrl;
        }

        public NodeDTOQueryParam nodeAdaptorManagementEndpointUrl(String nodeAdaptorManagementEndpointUrl) {
            this.nodeAdaptorManagementEndpointUrl = nodeAdaptorManagementEndpointUrl;
            return this;
        }

        @JsonProperty("managingAuthority")
        public NodeDTOManagingAuthority getManagingAuthority() {
            return managingAuthority;
        }

        public void setManagingAuthority(NodeDTOManagingAuthority managingAuthority) {
            this.managingAuthority = managingAuthority;
        }

        public NodeDTOQueryParam managingAuthority(NodeDTOManagingAuthority managingAuthority) {
            this.managingAuthority = managingAuthority;
            return this;
        }

        @JsonProperty("contactPersons")
        public List<ContactPersonDTO> getContactPersons() {
            return contactPersons;
        }

        public void setContactPersons(List<ContactPersonDTO> contactPersons) {
            this.contactPersons = contactPersons;
        }

        public NodeDTOQueryParam contactPersons(List<ContactPersonDTO> contactPersons) {
            this.contactPersons = contactPersons;
            return this;
        }
        public NodeDTOQueryParam addContactPersonsItem(ContactPersonDTO contactPersonsItem) {
            this.contactPersons.add(contactPersonsItem);
            return this;
        }

        @JsonProperty("lastSyncAttempt")
        public LocalDate getLastSyncAttempt() {
            return lastSyncAttempt;
        }

        public void setLastSyncAttempt(LocalDate lastSyncAttempt) {
            this.lastSyncAttempt = lastSyncAttempt;
        }

        public NodeDTOQueryParam lastSyncAttempt(LocalDate lastSyncAttempt) {
            this.lastSyncAttempt = lastSyncAttempt;
            return this;
        }

        @JsonProperty("lastSuccessfulSync")
        public LocalDate getLastSuccessfulSync() {
            return lastSuccessfulSync;
        }

        public void setLastSuccessfulSync(LocalDate lastSuccessfulSync) {
            this.lastSuccessfulSync = lastSuccessfulSync;
        }

        public NodeDTOQueryParam lastSuccessfulSync(LocalDate lastSuccessfulSync) {
            this.lastSuccessfulSync = lastSuccessfulSync;
            return this;
        }

        @JsonProperty("syncStatus")
        public SyncStatus getSyncStatus() {
            return syncStatus;
        }

        public void setSyncStatus(SyncStatus syncStatus) {
            this.syncStatus = syncStatus;
        }

        public NodeDTOQueryParam syncStatus(SyncStatus syncStatus) {
            this.syncStatus = syncStatus;
            return this;
        }

        @JsonProperty("health")
        public NodeHealth getHealth() {
            return health;
        }

        public void setHealth(NodeHealth health) {
            this.health = health;
        }

        public NodeDTOQueryParam health(NodeHealth health) {
            this.health = health;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class NodeDTOQueryParam {\n");

            sb.append("    name: ").append(toIndentedString(name)).append("\n");
            sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
            sb.append("    gateway: ").append(toIndentedString(gateway)).append("\n");
            sb.append("    classification: ").append(toIndentedString(classification)).append("\n");
            sb.append("    countries: ").append(toIndentedString(countries)).append("\n");
            sb.append("    trusted: ").append(toIndentedString(trusted)).append("\n");
            sb.append("    secureConnection: ").append(toIndentedString(secureConnection)).append("\n");
            sb.append("    certificateFileContent: ").append(toIndentedString(certificateFileContent)).append("\n");
            sb.append("    certificateFileName: ").append(toIndentedString(certificateFileName)).append("\n");
            sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
            sb.append("    nodeId: ").append(toIndentedString(nodeId)).append("\n");
            sb.append("    nodeVersion: ").append(toIndentedString(nodeVersion)).append("\n");
            sb.append("    syncEndpointUrl: ").append(toIndentedString(syncEndpointUrl)).append("\n");
            sb.append("    messageEndpointUrl: ").append(toIndentedString(messageEndpointUrl)).append("\n");
            sb.append("    healthEndpointUrl: ").append(toIndentedString(healthEndpointUrl)).append("\n");
            sb.append("    nodeAdaptorEndpointUrl: ").append(toIndentedString(nodeAdaptorEndpointUrl)).append("\n");
            sb.append("    nodeAdaptorManagementEndpointUrl: ").append(toIndentedString(nodeAdaptorManagementEndpointUrl)).append("\n");
            sb.append("    managingAuthority: ").append(toIndentedString(managingAuthority)).append("\n");
            sb.append("    contactPersons: ").append(toIndentedString(contactPersons)).append("\n");
            sb.append("    lastSyncAttempt: ").append(toIndentedString(lastSyncAttempt)).append("\n");
            sb.append("    lastSuccessfulSync: ").append(toIndentedString(lastSuccessfulSync)).append("\n");
            sb.append("    syncStatus: ").append(toIndentedString(syncStatus)).append("\n");
            sb.append("    health: ").append(toIndentedString(health)).append("\n");
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