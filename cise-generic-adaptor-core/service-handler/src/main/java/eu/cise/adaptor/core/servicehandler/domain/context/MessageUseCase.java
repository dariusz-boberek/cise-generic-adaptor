package eu.cise.adaptor.core.servicehandler.domain.context;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorRuntimeException;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceRoleType;

import static eu.cise.servicemodel.v1.service.ServiceRoleType.CONSUMER;

/**
 * This enum contains all the message case scenarios supported by the generic adaptor.
 * The use case is determinate by the Sender Service Role and Operation tags
 */
public enum MessageUseCase {
    PULL_RESPONSE,
    PULL_REQUEST,
    PUSH,
    PUSH_SUBSCRIBE_PROVIDER,
    PULL_SUBSCRIBE_CONSUMER,
    FEEDBACK;

    /**
     * Determine the use case using the Sender Service
     *
     * @param service Sender Service
     * @return Enum with the use case
     * @throws CiseAdaptorRuntimeException If the use case is not supported
     */
    public static MessageUseCase valueOf(Service service) {
        ServiceRoleType role = service.getServiceRole();
        ServiceOperationType operation = service.getServiceOperation();
        String errorMessage = "The message with Role " + role +" and Operation " + operation + " is not supported";

        switch (operation) {
            case PULL:
                // PullResponse: serviceOperation = Pull, serviceRole = Provider
                return role == CONSUMER ? PULL_REQUEST : PULL_RESPONSE;
            case PUSH:
                // Push Known: serviceOperation = Push, serviceRole = Provider
                if (role == CONSUMER) {
                    throw new CiseAdaptorRuntimeException(errorMessage);
                } else {
                    return PUSH;
                }
            case FEEDBACK:
                // Feedback: serviceOperation = Feedback, serviceRole = Provider
                if (role == CONSUMER) {
                    throw new CiseAdaptorRuntimeException(errorMessage);
                } else {
                    return FEEDBACK;
                }
            case SUBSCRIBE:
                // Publish: serviceOperation = Subscribe, serviceRole = Provider
                return role == CONSUMER ? PULL_SUBSCRIBE_CONSUMER : PUSH_SUBSCRIBE_PROVIDER;
            default:
                throw new CiseAdaptorRuntimeException(errorMessage);
        }

    }
}
