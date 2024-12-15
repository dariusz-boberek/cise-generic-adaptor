package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeBaseDTO  {

    private String name;
    private String domain;
    private String gateway;
    private String classification;
    private List<String> countries = new ArrayList<>();
    private Boolean trusted;
    private Boolean secureConnection;
    private String certificateFileContent;
    private String certificateFileName;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NodeBaseDTO name(String name) {
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

    public NodeBaseDTO domain(String domain) {
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

    public NodeBaseDTO gateway(String gateway) {
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

    public NodeBaseDTO classification(String classification) {
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

    public NodeBaseDTO countries(List<String> countries) {
        this.countries = countries;
        return this;
    }
    public NodeBaseDTO addCountriesItem(String countriesItem) {
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

    public NodeBaseDTO trusted(Boolean trusted) {
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

    public NodeBaseDTO secureConnection(Boolean secureConnection) {
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

    public NodeBaseDTO certificateFileContent(String certificateFileContent) {
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

    public NodeBaseDTO certificateFileName(String certificateFileName) {
        this.certificateFileName = certificateFileName;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NodeBaseDTO {\n");

        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
        sb.append("    gateway: ").append(toIndentedString(gateway)).append("\n");
        sb.append("    classification: ").append(toIndentedString(classification)).append("\n");
        sb.append("    countries: ").append(toIndentedString(countries)).append("\n");
        sb.append("    trusted: ").append(toIndentedString(trusted)).append("\n");
        sb.append("    secureConnection: ").append(toIndentedString(secureConnection)).append("\n");
        sb.append("    certificateFileContent: ").append(toIndentedString(certificateFileContent)).append("\n");
        sb.append("    certificateFileName: ").append(toIndentedString(certificateFileName)).append("\n");
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
    public static class NodeBaseDTOQueryParam  {

        private String name;
        private String domain;
        private String gateway;
        private String classification;
        private List<String> countries = new ArrayList<>();
        private Boolean trusted;
        private Boolean secureConnection;
        private String certificateFileContent;
        private String certificateFileName;

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public NodeBaseDTOQueryParam name(String name) {
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

        public NodeBaseDTOQueryParam domain(String domain) {
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

        public NodeBaseDTOQueryParam gateway(String gateway) {
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

        public NodeBaseDTOQueryParam classification(String classification) {
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

        public NodeBaseDTOQueryParam countries(List<String> countries) {
            this.countries = countries;
            return this;
        }
        public NodeBaseDTOQueryParam addCountriesItem(String countriesItem) {
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

        public NodeBaseDTOQueryParam trusted(Boolean trusted) {
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

        public NodeBaseDTOQueryParam secureConnection(Boolean secureConnection) {
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

        public NodeBaseDTOQueryParam certificateFileContent(String certificateFileContent) {
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

        public NodeBaseDTOQueryParam certificateFileName(String certificateFileName) {
            this.certificateFileName = certificateFileName;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class NodeBaseDTOQueryParam {\n");

            sb.append("    name: ").append(toIndentedString(name)).append("\n");
            sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
            sb.append("    gateway: ").append(toIndentedString(gateway)).append("\n");
            sb.append("    classification: ").append(toIndentedString(classification)).append("\n");
            sb.append("    countries: ").append(toIndentedString(countries)).append("\n");
            sb.append("    trusted: ").append(toIndentedString(trusted)).append("\n");
            sb.append("    secureConnection: ").append(toIndentedString(secureConnection)).append("\n");
            sb.append("    certificateFileContent: ").append(toIndentedString(certificateFileContent)).append("\n");
            sb.append("    certificateFileName: ").append(toIndentedString(certificateFileName)).append("\n");
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