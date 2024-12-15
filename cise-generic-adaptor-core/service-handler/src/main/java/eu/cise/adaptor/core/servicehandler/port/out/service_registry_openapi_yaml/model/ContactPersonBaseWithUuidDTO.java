package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactPersonBaseWithUuidDTO  {

    private String surname;
    private String name;
    private String role;
    private String email;
    private String phone;
    private String address;
    private String uuid;

    @JsonProperty("surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public ContactPersonBaseWithUuidDTO surname(String surname) {
        this.surname = surname;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContactPersonBaseWithUuidDTO name(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ContactPersonBaseWithUuidDTO role(String role) {
        this.role = role;
        return this;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ContactPersonBaseWithUuidDTO email(String email) {
        this.email = email;
        return this;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ContactPersonBaseWithUuidDTO phone(String phone) {
        this.phone = phone;
        return this;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ContactPersonBaseWithUuidDTO address(String address) {
        this.address = address;
        return this;
    }

    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ContactPersonBaseWithUuidDTO uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ContactPersonBaseWithUuidDTO {\n");

        sb.append("    surname: ").append(toIndentedString(surname)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    role: ").append(toIndentedString(role)).append("\n");
        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
        sb.append("    address: ").append(toIndentedString(address)).append("\n");
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
    public static class ContactPersonBaseWithUuidDTOQueryParam  {

        private String surname;
        private String name;
        private String role;
        private String email;
        private String phone;
        private String address;
        private String uuid;

        @JsonProperty("surname")
        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public ContactPersonBaseWithUuidDTOQueryParam surname(String surname) {
            this.surname = surname;
            return this;
        }

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ContactPersonBaseWithUuidDTOQueryParam name(String name) {
            this.name = name;
            return this;
        }

        @JsonProperty("role")
        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public ContactPersonBaseWithUuidDTOQueryParam role(String role) {
            this.role = role;
            return this;
        }

        @JsonProperty("email")
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public ContactPersonBaseWithUuidDTOQueryParam email(String email) {
            this.email = email;
            return this;
        }

        @JsonProperty("phone")
        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public ContactPersonBaseWithUuidDTOQueryParam phone(String phone) {
            this.phone = phone;
            return this;
        }

        @JsonProperty("address")
        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public ContactPersonBaseWithUuidDTOQueryParam address(String address) {
            this.address = address;
            return this;
        }

        @JsonProperty("uuid")
        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public ContactPersonBaseWithUuidDTOQueryParam uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class ContactPersonBaseWithUuidDTOQueryParam {\n");

            sb.append("    surname: ").append(toIndentedString(surname)).append("\n");
            sb.append("    name: ").append(toIndentedString(name)).append("\n");
            sb.append("    role: ").append(toIndentedString(role)).append("\n");
            sb.append("    email: ").append(toIndentedString(email)).append("\n");
            sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
            sb.append("    address: ").append(toIndentedString(address)).append("\n");
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