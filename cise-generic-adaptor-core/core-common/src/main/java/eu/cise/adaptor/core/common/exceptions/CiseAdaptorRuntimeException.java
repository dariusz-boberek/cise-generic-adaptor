package eu.cise.adaptor.core.common.exceptions;

import eu.cise.servicemodel.v1.message.AcknowledgementType;

/**
 * The CiseAdaptorRuntimeException class is a custom RuntimeException that includes additional fields for acknowledgement details and codes.
 */
public class CiseAdaptorRuntimeException extends RuntimeException {

    private String ackDetail;
    private AcknowledgementType ackCode;

    public CiseAdaptorRuntimeException(String message) {
        super(message);
    }

    public CiseAdaptorRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CiseAdaptorRuntimeException(String ackDetail, AcknowledgementType ackCode, Throwable cause) {
        super(ackDetail, cause);
        this.ackDetail = ackDetail;
        this.ackCode = ackCode;
    }

    public CiseAdaptorRuntimeException(String ackDetail, AcknowledgementType ackCode) {
        super(ackDetail);
        this.ackDetail = ackDetail;
        this.ackCode = ackCode;
    }

    public AcknowledgementType getAckCode() {
        return ackCode;
    }

    public String getAckDetail() {
        return ackDetail;
    }
}