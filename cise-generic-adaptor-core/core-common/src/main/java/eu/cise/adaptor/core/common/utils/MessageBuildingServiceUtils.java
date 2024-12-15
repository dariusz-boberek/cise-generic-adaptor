package eu.cise.adaptor.core.common.utils;

import eu.cise.servicemodel.v1.service.Service;

import java.util.Objects;

/**
 * This class holds helper methods used in CISE Service creation and verification of equality for two CISE Services
 */
public class MessageBuildingServiceUtils {

    /**
     * This method creates a clone of the provided CISE Service but sets its Participant to null
     *
     * @param originalService The service to clone
     * @return The cloned Service where the Participant is set to null
     */
    public static Service createServiceFrom(Service originalService) {
        Service newService = (Service) originalService.clone();
        newService.setParticipant(null);
        return newService;
    }

    /**
     * This method compares two Services to check if they are similar by checking only the following elements:<br>
     * the serviceId<br>
     * the service Role <br>
     * the service Operation <br>
     * the service Type <br>
     * Null values are safely handled in the comparison.
     *
     * @param serviceA The first service
     * @param serviceB The second service
     * @return true if the elements are similar, false otherwise
     */
    public static boolean areServicesSimilar(Service serviceA, Service serviceB) {
        if (serviceA == null || serviceB == null) {
            return false;
        }

        return Objects.equals(serviceA.getServiceID(), serviceB.getServiceID()) &&
                Objects.equals(serviceA.getServiceRole(), serviceB.getServiceRole()) &&
                Objects.equals(serviceA.getServiceOperation(), serviceB.getServiceOperation()) &&
                Objects.equals(serviceA.getServiceType(), serviceB.getServiceType());
    }

}