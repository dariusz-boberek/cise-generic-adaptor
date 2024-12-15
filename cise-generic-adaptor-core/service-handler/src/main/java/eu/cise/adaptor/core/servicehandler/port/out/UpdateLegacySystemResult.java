package eu.cise.adaptor.core.servicehandler.port.out;

import java.util.Objects;

/**
 * Represents the result of an update operation on a legacy system in the context of the CISE Adaptor.
 * This class encapsulates the status and details of the update process when a legacy system is interfaced with the CISE Node via an adaptor.
 */
public class UpdateLegacySystemResult {
    String result;

    public UpdateLegacySystemResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpdateLegacySystemResult that = (UpdateLegacySystemResult) o;
        return Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result);
    }

    @Override
    public String toString() {
        return "UpdateLegacySystemResult{" +
                "result='" + result + '\'' +
                '}';
    }
}
