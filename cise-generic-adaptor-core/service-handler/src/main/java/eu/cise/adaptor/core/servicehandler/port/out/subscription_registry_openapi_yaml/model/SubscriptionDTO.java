package eu.cise.adaptor.core.servicehandler.port.out.subscription_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionDTO  {

    private LocalDate expireDate;
    private String providerParticipantId;
    private String providerServiceId;
    private String status;
    private String subscriberParticipantId;
    private String subscriberServiceId;
    private String uuid;

    @JsonProperty("expireDate")
    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public SubscriptionDTO expireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
        return this;
    }

    @JsonProperty("providerParticipantId")
    public String getProviderParticipantId() {
        return providerParticipantId;
    }

    public void setProviderParticipantId(String providerParticipantId) {
        this.providerParticipantId = providerParticipantId;
    }

    public SubscriptionDTO providerParticipantId(String providerParticipantId) {
        this.providerParticipantId = providerParticipantId;
        return this;
    }

    @JsonProperty("providerServiceId")
    public String getProviderServiceId() {
        return providerServiceId;
    }

    public void setProviderServiceId(String providerServiceId) {
        this.providerServiceId = providerServiceId;
    }

    public SubscriptionDTO providerServiceId(String providerServiceId) {
        this.providerServiceId = providerServiceId;
        return this;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SubscriptionDTO status(String status) {
        this.status = status;
        return this;
    }

    @JsonProperty("subscriberParticipantId")
    public String getSubscriberParticipantId() {
        return subscriberParticipantId;
    }

    public void setSubscriberParticipantId(String subscriberParticipantId) {
        this.subscriberParticipantId = subscriberParticipantId;
    }

    public SubscriptionDTO subscriberParticipantId(String subscriberParticipantId) {
        this.subscriberParticipantId = subscriberParticipantId;
        return this;
    }

    @JsonProperty("subscriberServiceId")
    public String getSubscriberServiceId() {
        return subscriberServiceId;
    }

    public void setSubscriberServiceId(String subscriberServiceId) {
        this.subscriberServiceId = subscriberServiceId;
    }

    public SubscriptionDTO subscriberServiceId(String subscriberServiceId) {
        this.subscriberServiceId = subscriberServiceId;
        return this;
    }

    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public SubscriptionDTO uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SubscriptionDTO {\n");

        sb.append("    expireDate: ").append(toIndentedString(expireDate)).append("\n");
        sb.append("    providerParticipantId: ").append(toIndentedString(providerParticipantId)).append("\n");
        sb.append("    providerServiceId: ").append(toIndentedString(providerServiceId)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    subscriberParticipantId: ").append(toIndentedString(subscriberParticipantId)).append("\n");
        sb.append("    subscriberServiceId: ").append(toIndentedString(subscriberServiceId)).append("\n");
        sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
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
    public static class SubscriptionDTOQueryParam  {

        private LocalDate expireDate;
        private String providerParticipantId;
        private String providerServiceId;
        private String status;
        private String subscriberParticipantId;
        private String subscriberServiceId;
        private String uuid;

        @JsonProperty("expireDate")
        public LocalDate getExpireDate() {
            return expireDate;
        }

        public void setExpireDate(LocalDate expireDate) {
            this.expireDate = expireDate;
        }

        public SubscriptionDTOQueryParam expireDate(LocalDate expireDate) {
            this.expireDate = expireDate;
            return this;
        }

        @JsonProperty("providerParticipantId")
        public String getProviderParticipantId() {
            return providerParticipantId;
        }

        public void setProviderParticipantId(String providerParticipantId) {
            this.providerParticipantId = providerParticipantId;
        }

        public SubscriptionDTOQueryParam providerParticipantId(String providerParticipantId) {
            this.providerParticipantId = providerParticipantId;
            return this;
        }

        @JsonProperty("providerServiceId")
        public String getProviderServiceId() {
            return providerServiceId;
        }

        public void setProviderServiceId(String providerServiceId) {
            this.providerServiceId = providerServiceId;
        }

        public SubscriptionDTOQueryParam providerServiceId(String providerServiceId) {
            this.providerServiceId = providerServiceId;
            return this;
        }

        @JsonProperty("status")
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public SubscriptionDTOQueryParam status(String status) {
            this.status = status;
            return this;
        }

        @JsonProperty("subscriberParticipantId")
        public String getSubscriberParticipantId() {
            return subscriberParticipantId;
        }

        public void setSubscriberParticipantId(String subscriberParticipantId) {
            this.subscriberParticipantId = subscriberParticipantId;
        }

        public SubscriptionDTOQueryParam subscriberParticipantId(String subscriberParticipantId) {
            this.subscriberParticipantId = subscriberParticipantId;
            return this;
        }

        @JsonProperty("subscriberServiceId")
        public String getSubscriberServiceId() {
            return subscriberServiceId;
        }

        public void setSubscriberServiceId(String subscriberServiceId) {
            this.subscriberServiceId = subscriberServiceId;
        }

        public SubscriptionDTOQueryParam subscriberServiceId(String subscriberServiceId) {
            this.subscriberServiceId = subscriberServiceId;
            return this;
        }

        @JsonProperty("uuid")
        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public SubscriptionDTOQueryParam uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class SubscriptionDTOQueryParam {\n");

            sb.append("    expireDate: ").append(toIndentedString(expireDate)).append("\n");
            sb.append("    providerParticipantId: ").append(toIndentedString(providerParticipantId)).append("\n");
            sb.append("    providerServiceId: ").append(toIndentedString(providerServiceId)).append("\n");
            sb.append("    status: ").append(toIndentedString(status)).append("\n");
            sb.append("    subscriberParticipantId: ").append(toIndentedString(subscriberParticipantId)).append("\n");
            sb.append("    subscriberServiceId: ").append(toIndentedString(subscriberServiceId)).append("\n");
            sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
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