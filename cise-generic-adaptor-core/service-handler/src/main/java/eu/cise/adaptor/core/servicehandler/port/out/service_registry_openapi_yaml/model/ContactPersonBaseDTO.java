package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactPersonBaseDTO  {

    private String surname;
    private String name;
    private String role;
    private String email;
    private String phone;
    private String address;

    @JsonProperty("surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public ContactPersonBaseDTO surname(String surname) {
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

    public ContactPersonBaseDTO name(String name) {
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

    public ContactPersonBaseDTO role(String role) {
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

    public ContactPersonBaseDTO email(String email) {
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

    public ContactPersonBaseDTO phone(String phone) {
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

    public ContactPersonBaseDTO address(String address) {
        this.address = address;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ContactPersonBaseDTO {\n");

        sb.append("    surname: ").append(toIndentedString(surname)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    role: ").append(toIndentedString(role)).append("\n");
        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
        sb.append("    address: ").append(toIndentedString(address)).append("\n");
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
    public static class ContactPersonBaseDTOQueryParam  {

        private String surname;
        private String name;
        private String role;
        private String email;
        private String phone;
        private String address;

        @JsonProperty("surname")
        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public ContactPersonBaseDTOQueryParam surname(String surname) {
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

        public ContactPersonBaseDTOQueryParam name(String name) {
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

        public ContactPersonBaseDTOQueryParam role(String role) {
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

        public ContactPersonBaseDTOQueryParam email(String email) {
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

        public ContactPersonBaseDTOQueryParam phone(String phone) {
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

        public ContactPersonBaseDTOQueryParam address(String address) {
            this.address = address;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class ContactPersonBaseDTOQueryParam {\n");

            sb.append("    surname: ").append(toIndentedString(surname)).append("\n");
            sb.append("    name: ").append(toIndentedString(name)).append("\n");
            sb.append("    role: ").append(toIndentedString(role)).append("\n");
            sb.append("    email: ").append(toIndentedString(email)).append("\n");
            sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
            sb.append("    address: ").append(toIndentedString(address)).append("\n");
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