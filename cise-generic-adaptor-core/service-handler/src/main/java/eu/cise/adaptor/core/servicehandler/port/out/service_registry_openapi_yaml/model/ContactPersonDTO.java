package eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactPersonDTO  {

    private String surname;
    private String name;
    private String role;
    private String email;
    private String phone;
    private String address;
    private String uuid;
    private List<String> contactFor = null;
    private NodeDTOManagingAuthority authority;

    @JsonProperty("surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public ContactPersonDTO surname(String surname) {
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

    public ContactPersonDTO name(String name) {
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

    public ContactPersonDTO role(String role) {
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

    public ContactPersonDTO email(String email) {
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

    public ContactPersonDTO phone(String phone) {
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

    public ContactPersonDTO address(String address) {
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

    public ContactPersonDTO uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @JsonProperty("contactFor")
    public List<String> getContactFor() {
        return contactFor;
    }

    public void setContactFor(List<String> contactFor) {
        this.contactFor = contactFor;
    }

    public ContactPersonDTO contactFor(List<String> contactFor) {
        this.contactFor = contactFor;
        return this;
    }
    public ContactPersonDTO addContactForItem(String contactForItem) {
        this.contactFor.add(contactForItem);
        return this;
    }

    @JsonProperty("authority")
    public NodeDTOManagingAuthority getAuthority() {
        return authority;
    }

    public void setAuthority(NodeDTOManagingAuthority authority) {
        this.authority = authority;
    }

    public ContactPersonDTO authority(NodeDTOManagingAuthority authority) {
        this.authority = authority;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ContactPersonDTO {\n");

        sb.append("    surname: ").append(toIndentedString(surname)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    role: ").append(toIndentedString(role)).append("\n");
        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
        sb.append("    address: ").append(toIndentedString(address)).append("\n");
        sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
        sb.append("    contactFor: ").append(toIndentedString(contactFor)).append("\n");
        sb.append("    authority: ").append(toIndentedString(authority)).append("\n");
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
    public static class ContactPersonDTOQueryParam  {

        private String surname;
        private String name;
        private String role;
        private String email;
        private String phone;
        private String address;
        private String uuid;
        private List<String> contactFor = null;
        private NodeDTOManagingAuthority authority;

        @JsonProperty("surname")
        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public ContactPersonDTOQueryParam surname(String surname) {
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

        public ContactPersonDTOQueryParam name(String name) {
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

        public ContactPersonDTOQueryParam role(String role) {
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

        public ContactPersonDTOQueryParam email(String email) {
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

        public ContactPersonDTOQueryParam phone(String phone) {
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

        public ContactPersonDTOQueryParam address(String address) {
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

        public ContactPersonDTOQueryParam uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        @JsonProperty("contactFor")
        public List<String> getContactFor() {
            return contactFor;
        }

        public void setContactFor(List<String> contactFor) {
            this.contactFor = contactFor;
        }

        public ContactPersonDTOQueryParam contactFor(List<String> contactFor) {
            this.contactFor = contactFor;
            return this;
        }
        public ContactPersonDTOQueryParam addContactForItem(String contactForItem) {
            this.contactFor.add(contactForItem);
            return this;
        }

        @JsonProperty("authority")
        public NodeDTOManagingAuthority getAuthority() {
            return authority;
        }

        public void setAuthority(NodeDTOManagingAuthority authority) {
            this.authority = authority;
        }

        public ContactPersonDTOQueryParam authority(NodeDTOManagingAuthority authority) {
            this.authority = authority;
            return this;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class ContactPersonDTOQueryParam {\n");

            sb.append("    surname: ").append(toIndentedString(surname)).append("\n");
            sb.append("    name: ").append(toIndentedString(name)).append("\n");
            sb.append("    role: ").append(toIndentedString(role)).append("\n");
            sb.append("    email: ").append(toIndentedString(email)).append("\n");
            sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
            sb.append("    address: ").append(toIndentedString(address)).append("\n");
            sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
            sb.append("    contactFor: ").append(toIndentedString(contactFor)).append("\n");
            sb.append("    authority: ").append(toIndentedString(authority)).append("\n");
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