package eu.cise.adaptor.core.servicehandler.domain.message.validation;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.common.logging.LogConfig;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.servicemodel.v1.message.CoreEntityPayload;
import eu.cise.servicemodel.v1.message.XmlEntityPayload;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceProfile;
import eu.cise.servicemodel.v1.service.ServiceRoleType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static eu.cise.adaptor.core.common.logging.LoggerMessage.of;
import static eu.cise.adaptor.core.common.logging.MessageRouteValue.LS_TO_CISE;

public abstract class CommonMessageValidator implements MessageValidator {

    protected static AdaptorLogger log = LogConfig.configureLogging(CommonMessageValidator.class);

    @Override
    public void validate(MessageDataContext msgCtx) throws CiseAdaptorValidationException {

        verifyCurrentService(msgCtx);

        if (!CollectionUtils.isEmpty(msgCtx.getDiscoveryProfiles()) && !CollectionUtils.isEmpty(msgCtx.getRecipients())) {
            String errorMsg = "Validation error message: Recipients list and discovery profile are both not empty";
            log.error(of(errorMsg).addRoutingAttribute(LS_TO_CISE));
            throw new CiseAdaptorValidationException(errorMsg);
        }

        verifyDestinationCoherence(msgCtx);
    }

    private void verifyCurrentService(MessageDataContext messageDataContext) throws CiseAdaptorValidationException {
        Service currentService = messageDataContext.getCurrentService();
        if (currentService == null) {
            String errorMsg = "Validation error message: Missing sender service";
            log.error(of(errorMsg).addRoutingAttribute(LS_TO_CISE));
            throw new CiseAdaptorValidationException(errorMsg);
        }
    }

    protected void verifyPayload(MessageDataContext messageDataContext) throws CiseAdaptorValidationException {
        String errorMsg;
        CoreEntityPayload cisePayload = messageDataContext.getCisePayload();

        if (cisePayload == null) {
            errorMsg = "Validation error message: CisePayload is null";
            log.error(of(errorMsg).addRoutingAttribute(LS_TO_CISE));
            throw new CiseAdaptorValidationException(errorMsg);
        }

        if (cisePayload instanceof XmlEntityPayload && CollectionUtils.isEmpty(((XmlEntityPayload) cisePayload).getAnies())) {
            errorMsg = "Validation error message: CisePayload does not contain any Anies";
            log.error(of(errorMsg).addRoutingAttribute(LS_TO_CISE));
            throw new CiseAdaptorValidationException(errorMsg);
        }
    }


    protected void verifyRecipientsPresent(MessageDataContext messageDataContext) throws CiseAdaptorValidationException {
        List<Service> recipients = messageDataContext.getRecipients();
        if (CollectionUtils.isEmpty(recipients)) {
            String errorMsg = "Validation error message: Recipients list is null or empty";
            log.error(of(errorMsg).addRoutingAttribute(LS_TO_CISE));
            throw new CiseAdaptorValidationException(errorMsg);
        }
    }

    protected void verifyDiscoveryProfilesPresent(MessageDataContext messageDataContext) throws CiseAdaptorValidationException {
        List<ServiceProfile> discoveryProfiles = messageDataContext.getDiscoveryProfiles();
        if (CollectionUtils.isEmpty(discoveryProfiles)) {
            String errorMsm = "Validation error message: DiscoveryProfiles list is null or empty";
            log.error(of(errorMsm).addRoutingAttribute(LS_TO_CISE));
            throw new CiseAdaptorValidationException(errorMsm);
        }
    }

    protected void verifyDestinationIsPresent(MessageDataContext messageDataContext) throws CiseAdaptorValidationException {
        List<ServiceProfile> discoveryProfiles = messageDataContext.getDiscoveryProfiles();
        List<Service> recipients = messageDataContext.getRecipients();
        if (CollectionUtils.isEmpty(discoveryProfiles) && CollectionUtils.isEmpty(recipients)) {
            String errorMsm = "Validation error message: Recipients list and discoveryProfiles list are both null or empty";
            log.error(of(errorMsm).addRoutingAttribute(LS_TO_CISE));
            throw new CiseAdaptorValidationException(errorMsm);
        }
    }

    protected void verifyDiscoveryProfilesNotPresent(MessageDataContext messageDataContext) throws CiseAdaptorValidationException {
        List<ServiceProfile> discoveryProfiles = messageDataContext.getDiscoveryProfiles();
        if (!CollectionUtils.isEmpty(discoveryProfiles)) {
            String errorMsm = "Validation error message: DiscoveryProfiles list contains elements but should be empty for this communication pattern";
            log.error(of(errorMsm).addRoutingAttribute(LS_TO_CISE));
            throw new CiseAdaptorValidationException(errorMsm);
        }
    }

    protected void verifyRecipientsNotPresent(MessageDataContext messageDataContext) throws CiseAdaptorValidationException {
        List<Service> recipients = messageDataContext.getRecipients();
        if (!CollectionUtils.isEmpty(recipients)) {
            String errorMsg = "Validation error message: Recipients list contains elements but should be empty for this communication pattern";
            log.error(of(errorMsg).addRoutingAttribute(LS_TO_CISE));
            throw new CiseAdaptorValidationException(errorMsg);
        }
    }

    private void verifyDestinationCoherence(MessageDataContext messageDataContext) throws CiseAdaptorValidationException {
        verifyRecipientsCoherenceIfPresent(messageDataContext);
        verifyDiscoveryProfilesCoherenceIfPresent(messageDataContext);
    }

    private void verifyDiscoveryProfilesCoherenceIfPresent(MessageDataContext messageDataContext) throws CiseAdaptorValidationException {

        // verify Service Type
        verifyWithServiceProfilePredicate(messageDataContext,
                dp -> dp.getServiceType() != null,
                dp -> dp.getServiceType() != messageDataContext.getCurrentService().getServiceType(),
                "Validation error message: Discovery profile with ServiceType different from Sender ServiceType");

        // verify Service Role
        verifyWithServiceProfilePredicate(messageDataContext,
                dp -> dp.getServiceRole() != null,
                dp -> dp.getServiceRole().equals(messageDataContext.getCurrentService().getServiceRole()),
                "Validation error message: Discovery profile with ServiceRole same as Sender ServiceRole");

        // verify Service Operation
        verifyWithServiceProfilePredicate(messageDataContext,
                dp -> dp.getServiceOperation() != null,
                dp -> dp.getServiceOperation() != (messageDataContext.getCurrentService().getServiceOperation()),
                "Validation error message: Discovery profile with ServiceOperation different from Sender ServiceOperation");
    }

    private void verifyWithServiceProfilePredicate(MessageDataContext messageDataContext,
                                                   Predicate<ServiceProfile> serviceProfilePredicateNotNull,
                                                   Predicate<ServiceProfile> serviceProfilePredicateValidityCheck,
                                                   String errorMessage
                                                                         ) throws CiseAdaptorValidationException {
        Optional<ServiceProfile> wrongProfile = messageDataContext.getDiscoveryProfiles().stream()
                .filter(serviceProfilePredicateNotNull)
                .filter(serviceProfilePredicateValidityCheck)
                .findFirst();
        if (wrongProfile.isPresent()) {
            log.error(of(errorMessage).addRoutingAttribute(LS_TO_CISE));
            throw new CiseAdaptorValidationException(errorMessage);
        }
    }

    private static void verifyRecipientsCoherenceIfPresent(MessageDataContext messageDataContext) throws CiseAdaptorValidationException {
        // check Coherence: If the current service is a Provider, then the recipient must be a Consumer and vice versa

        List<Service> recipients = new ArrayList<>();
        if (messageDataContext.getRecipients() != null) {
            recipients.addAll(messageDataContext.getRecipients());
        }

        if (messageDataContext.getReferenceMessage() != null) {
            recipients.add(messageDataContext.getReferenceMessage().getSender());
        }

        ServiceRoleType srRequested = messageDataContext.getCurrentService().getServiceRole() == ServiceRoleType.CONSUMER ?
                ServiceRoleType.PROVIDER : ServiceRoleType.CONSUMER;

        for (Service recipient : recipients) {
            if (recipient.getServiceRole() != srRequested) {
                String errorMsg = "Validation error message: Recipients list contain a wrong role: found " + recipient.getServiceRole() + " expected " + srRequested;
                log.error(of(errorMsg).addRoutingAttribute(LS_TO_CISE));
                throw new CiseAdaptorValidationException(errorMsg);
            }

            if (StringUtils.isEmpty(recipient.getServiceID())) {
                String errorMsg = "Validation error message: Found an invalid recipient, without service id";
                log.error(of(errorMsg).addRoutingAttribute(LS_TO_CISE));
                throw new CiseAdaptorValidationException(errorMsg);
            }
        }
    }



}
