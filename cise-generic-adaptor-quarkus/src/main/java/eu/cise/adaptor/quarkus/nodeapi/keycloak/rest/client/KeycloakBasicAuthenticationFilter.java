package eu.cise.adaptor.quarkus.nodeapi.keycloak.rest.client;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import java.util.Base64;

@Priority(Priorities.AUTHENTICATION)
public class KeycloakBasicAuthenticationFilter extends AuthenticationRequestFilter {

	@ConfigProperty(name = "cisenode.token.credentials.username")
	String clientUsername;

	@Override
	protected String getAccessToken() {
		return "Basic " + Base64.getEncoder().encodeToString((clientUsername + ":").getBytes());
	}

}
