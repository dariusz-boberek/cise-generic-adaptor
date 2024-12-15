package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.EnumSet;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum NodeHealth {
    GREEN(String.valueOf("GREEN")), YELLOW(String.valueOf("YELLOW")), RED(String.valueOf("RED")), UNKNOWN(String.valueOf("UNKNOWN"));

    // caching enum access
    private static final EnumSet<NodeHealth> values = EnumSet.allOf(NodeHealth.class);

    private String value;

    NodeHealth(String value){
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static NodeHealth fromValue(String text) {
        for (NodeHealth b : values) {
            if (String.valueOf(b.value).equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + text + "'");
    }
}