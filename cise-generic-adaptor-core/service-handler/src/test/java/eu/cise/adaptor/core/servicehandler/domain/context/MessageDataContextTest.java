package eu.cise.adaptor.core.servicehandler.domain.context;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.servicehandler.domain.message.validation.MessageDataContextValidator;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceProfile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

// TODO Are these tests necessary ? they are testing the correctness of getter and setter, no logic is tested. Maybe just one test with setting all the parameters
class MessageDataContextTest {

    private Service currentService;
    private MockedStatic<MessageDataContextValidator> messageDataContextValidatorMock;

    @BeforeEach
    void setUp() {
        currentService = new Service();
        messageDataContextValidatorMock = mockStatic(MessageDataContextValidator.class);
        messageDataContextValidatorMock.when(() -> MessageDataContextValidator.validateContext(any())).thenAnswer(invocationOnMock -> null);
    }

    @AfterEach
    void tearDown() {
        messageDataContextValidatorMock.close();
    }

    @Test
    void testBuilderSetsValuesCorrectly() throws CiseAdaptorValidationException {

        List<Service> recipients = Arrays.asList(new Service(), new Service());
        List<ServiceProfile> discoveryProfiles = Arrays.asList(new ServiceProfile(), new ServiceProfile());
        String contextId = "123";

        MessageDataContext context = MessageDataContext.getManager()
                .recipients(recipients)
                .currentService(currentService)
                .discoveryProfiles(discoveryProfiles)
                .contextId(contextId)
                .buildContext();

        assertEquals(recipients, context.getRecipients());
        assertEquals(currentService, context.getCurrentService());
        assertEquals(discoveryProfiles, context.getDiscoveryProfiles());
        assertEquals(contextId, context.getContextId());
    }

    @Test
    void testBuilderWithNullContextId() throws CiseAdaptorValidationException {
        MessageDataContext context = MessageDataContext.getManager()
                .contextId(null)
                .currentService(currentService)
                .buildContext();
        assertNotNull(context);
        assertEquals(null, context.getContextId());
    }

    @Test
    void testBuilderWithNullRecipients() throws CiseAdaptorValidationException {
        MessageDataContext context = MessageDataContext.getManager()
                .recipients(null)
                .currentService(currentService)
                .buildContext();
        assertNotNull(context);
        assertEquals(Collections.emptyList(), context.getRecipients());
    }

    @Test
    void testBuilderWithEmptyRecipients() throws CiseAdaptorValidationException {
        MessageDataContext context = MessageDataContext.getManager()
                .recipients(Collections.emptyList())
                .currentService(currentService)
                .buildContext();
        assertNotNull(context);
        assertTrue(context.getRecipients().isEmpty());
    }

}