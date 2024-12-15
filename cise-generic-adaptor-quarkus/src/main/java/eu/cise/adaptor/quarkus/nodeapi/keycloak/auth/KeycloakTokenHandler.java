package eu.cise.adaptor.quarkus.nodeapi.keycloak.auth;


import eu.cise.adaptor.quarkus.nodeapi.keycloak.model.KeycloakTokenDTO;

public class KeycloakTokenHandler {

    private static KeycloakTokenDTO token;

    private KeycloakTokenHandler() {}

    public static KeycloakTokenDTO getToken() {
        return token;
    }

    public static void setToken(KeycloakTokenDTO token) {
        KeycloakTokenHandler.token = token;
    }

}
