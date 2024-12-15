package eu.cise.adaptor.core.adapters.servicehandler.in.stub;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.adaptor.core.servicehandler.port.in.ReceiveFromLegacySystemPort;
import eu.cise.adaptor.core.servicehandler.service.ServiceHandlerMessageBuilderService;
import eu.cise.servicemodel.v1.service.Service;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;


/**
 * Stub implementation of the {@code ReceiveFromLegacySystemPort} used in functional tests.
 *
 * <p>This adapter stub provides a basic structure for simulating the integration of legacy systems
 * in a testing environment. It serves as a simple placeholder to test the processing capabilities
 * of the Generic Adapter.
 *
 * @see ReceiveFromLegacySystemPort
 */
public class ReceiveFromLegacySystemAdapterStub extends ReceiveFromLegacySystemPort {

    private final XmlMapper xmlMapper;

    public ReceiveFromLegacySystemAdapterStub(ServiceHandlerMessageBuilderService serviceHandlerMessageBuilderService, Service currentService) {
        super(serviceHandlerMessageBuilderService, currentService);
        this.xmlMapper = new DefaultXmlMapper.NotValidating();
    }

    @Override
    public MessageDataContext handleCustomTranslation(String legacySystemPayload, MessageDataContext.MessageDataContextManager messageDataContextManager) throws CiseAdaptorValidationException {
        messageDataContextManager.cisePayload(xmlMapper.fromXML(legacySystemPayload));
        return messageDataContextManager.buildContext();
    }
}
