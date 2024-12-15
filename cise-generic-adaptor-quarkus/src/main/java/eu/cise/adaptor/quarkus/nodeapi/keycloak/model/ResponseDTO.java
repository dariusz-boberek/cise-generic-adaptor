package eu.cise.adaptor.quarkus.nodeapi.keycloak.model;



import eu.cise.adaptor.core.servicehandler.port.out.service_registry_openapi_yaml.model.ServiceDTO;

import java.util.ArrayList;
import java.util.List;

public class ResponseDTO {

    private List<ServiceDTO> data;
    private int total;

    public ResponseDTO(String data, int total) {
        if (data.isEmpty()) {
            this.data = new ArrayList<>();
        }
        this.total = total;
    }

    public ResponseDTO(List<ServiceDTO> data, int total) {
        this.data = data;
        this.total = total;
    }

    public ResponseDTO() {
        this.data = new ArrayList<>();
        this.total = 0;
    }

    public List<ServiceDTO> getData() {
        return data;
    }

    public void setData(List<ServiceDTO> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
