package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactPersonCreateDTO  {

    private String surname;
    private String name;
    private String role;
    private String email;
    private String phone;
    private String address;
    private String authorityUUID;

    @JsonProperty("surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public ContactPersonCreateDTO surname(String surname) {
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

    public ContactPersonCreateDTO name(String name) {
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

    public ContactPersonCreateDTO role(String role) {
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

    public ContactPersonCreateDTO email(String email) {
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

    public ContactPersonCreateDTO phone(String phone) {
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

    public ContactPersonCreateDTO address(String address) {
        this.address = address;
        return this;
    }

    @JsonProperty("authorityUUID")
    public String getAuthorityUUID() {
        return authorityUUID;
    }

    public void setAuthorityUUID(String authorityUUID) {
        this.authorityUUID = authorityUUID;
    }

    public ContactPersonCreateDTO authorityUUID(String authorityUUID) {
        this.authorityUUID = authorityUUID;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ContactPersonCreateDTO {\n");

        sb.append("    surname: ").append(toIndentedString(surname)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    role: ").append(toIndentedString(role)).append("\n");
        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
        sb.append("    address: ").append(toIndentedString(address)).append("\n");
        sb.append("    authorityUUID: ").append(toIndentedString(authorityUUID)).append("\n");
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
    public static class ContactPersonCreateDTOQueryParam  {

        private String surname;
        private String name;
        private String role;
        private String email;
        private String phone;
        private String address;
        private String authorityUUID;

        @JsonProperty("surname")
        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public ContactPersonCreateDTOQueryParam surname(String surname) {
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

        public ContactPersonCreateDTOQueryParam name(String name) {
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

        public ContactPersonCreateDTOQueryParam role(String role) {
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

        public ContactPersonCreateDTOQueryParam email(String email) {
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

        public ContactPersonCreateDTOQueryParam phone(String phone) {
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

        public ContactPersonCreateDTOQueryParam address(String address) {
            this.address = address;
            return this;
        }

        @JsonProperty("authorityUUID")
        public String getAuthorityUUID() {
            return authorityUUID;
        }

        public void setAuthorityUUID(String authorityUUID) {
            this.authorityUUID = authorityUUID;
        }

        public ContactPersonCreateDTOQueryParam authorityUUID(String authorityUUID) {
            this.authorityUUID = authorityUUID;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class ContactPersonCreateDTOQueryParam {\n");

            sb.append("    surname: ").append(toIndentedString(surname)).append("\n");
            sb.append("    name: ").append(toIndentedString(name)).append("\n");
            sb.append("    role: ").append(toIndentedString(role)).append("\n");
            sb.append("    email: ").append(toIndentedString(email)).append("\n");
            sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
            sb.append("    address: ").append(toIndentedString(address)).append("\n");
            sb.append("    authorityUUID: ").append(toIndentedString(authorityUUID)).append("\n");
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