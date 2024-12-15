package eu.cise.adaptor.core.common.exceptions;

public class CiseAdaptorValidationException extends Exception {

    public CiseAdaptorValidationException(String message) {
        super(message);
    }

    public CiseAdaptorValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
