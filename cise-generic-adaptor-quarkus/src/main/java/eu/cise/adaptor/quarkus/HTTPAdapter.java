package eu.cise.adaptor.quarkus;


import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Adapts the adapter response to  REST protocol
 */
public class HTTPAdapter {



    public static Response toResponse(Acknowledgement result) {
        switch (result.getAckCode()) {
            case SUCCESS:
                return buildXmlResponse(result, 202);
            case AUTHENTICATION_ERROR:
            case BAD_REQUEST:
            case INVALID_REQUEST_OBJECT:
            case SERVICE_MANAGER_ERROR:
            case SERVER_ERROR:
                return buildXmlResponse(result, 200);
            default:
                throw new RuntimeException("Not Supported response");
        }
    }

    private static Response buildXmlResponse(Acknowledgement result, int status) {
        XmlMapper xmlMapper = new DefaultXmlMapper();
        return Response.status(status)
                .type(MediaType.APPLICATION_XML)
                .entity(xmlMapper.toXML(result))
                .build();
    }
}
