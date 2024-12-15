package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeConfigurationCreateDTONodeProperties  {

    private String classification;
    private List<String> countries = new ArrayList<>();

    @JsonProperty("classification")
    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public NodeConfigurationCreateDTONodeProperties classification(String classification) {
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

    public NodeConfigurationCreateDTONodeProperties countries(List<String> countries) {
        this.countries = countries;
        return this;
    }
    public NodeConfigurationCreateDTONodeProperties addCountriesItem(String countriesItem) {
        this.countries.add(countriesItem);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NodeConfigurationCreateDTONodeProperties {\n");

        sb.append("    classification: ").append(toIndentedString(classification)).append("\n");
        sb.append("    countries: ").append(toIndentedString(countries)).append("\n");
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
    public static class NodeConfigurationCreateDTONodePropertiesQueryParam  {

        private String classification;
        private List<String> countries = new ArrayList<>();

        @JsonProperty("classification")
        public String getClassification() {
            return classification;
        }

        public void setClassification(String classification) {
            this.classification = classification;
        }

        public NodeConfigurationCreateDTONodePropertiesQueryParam classification(String classification) {
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

        public NodeConfigurationCreateDTONodePropertiesQueryParam countries(List<String> countries) {
            this.countries = countries;
            return this;
        }
        public NodeConfigurationCreateDTONodePropertiesQueryParam addCountriesItem(String countriesItem) {
            this.countries.add(countriesItem);
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class NodeConfigurationCreateDTONodePropertiesQueryParam {\n");

            sb.append("    classification: ").append(toIndentedString(classification)).append("\n");
            sb.append("    countries: ").append(toIndentedString(countries)).append("\n");
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