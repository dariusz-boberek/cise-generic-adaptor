package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.EnumSet;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum SyncStatus {
    FAIL(String.valueOf("FAIL")), SUCCESS(String.valueOf("SUCCESS")), NA(String.valueOf("NA"));

    // caching enum access
    private static final EnumSet<SyncStatus> values = EnumSet.allOf(SyncStatus.class);

    private String value;

    SyncStatus(String value){
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static SyncStatus fromValue(String text) {
        for (SyncStatus b : values) {
            if (String.valueOf(b.value).equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + text + "'");
    }
}