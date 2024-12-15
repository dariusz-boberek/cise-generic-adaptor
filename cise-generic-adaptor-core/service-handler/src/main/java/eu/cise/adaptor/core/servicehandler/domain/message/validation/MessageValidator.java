package eu.cise.adaptor.core.servicehandler.domain.message.validation;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;

public interface MessageValidator {

    /**
     * Validates that provided {@link MessageDataContext} is configured appropriately for requested protocol
     * to allow for CISE Message creation.
     *
     * @param messageDataContext The MessageDataContext with the information received from the Legacy System.
     * @throws CiseAdaptorValidationException It will be thrown if some problem is found while validating.
     */
    void validate(MessageDataContext messageDataContext) throws CiseAdaptorValidationException;

}
