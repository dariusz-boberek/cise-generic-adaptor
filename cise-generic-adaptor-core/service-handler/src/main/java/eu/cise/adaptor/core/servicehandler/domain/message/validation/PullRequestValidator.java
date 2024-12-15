package eu.cise.adaptor.core.servicehandler.domain.message.validation;

import eu.cise.adaptor.core.common.exceptions.CiseAdaptorValidationException;
import eu.cise.adaptor.core.common.logging.AdaptorLogger;
import eu.cise.adaptor.core.common.logging.LogConfig;
import eu.cise.adaptor.core.servicehandler.domain.context.MessageDataContext;
import eu.cise.servicemodel.v1.message.CoreEntityPayload;
import eu.cise.servicemodel.v1.message.PayloadSelector;
import eu.cise.servicemodel.v1.message.SelectorCondition;
import eu.cise.servicemodel.v1.service.ServiceCapability;
import eu.cise.servicemodel.v1.service.SubscriptionCapability;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.apache.commons.lang3.StringUtils;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class PullRequestValidator extends CommonMessageValidator {

    protected static final AdaptorLogger log = LogConfig.configureLogging(PullRequestValidator.class);

    private final XPath xPath = XPathFactory.newInstance().newXPath();
    private final XmlMapper xmlMapper = new DefaultXmlMapper();

    @Override
    public void validate(MessageDataContext msgCtx) throws CiseAdaptorValidationException {
        super.validate(msgCtx);

        /** Generic checks **/
        verifyDestinationIsPresent(msgCtx);
        verifyPayload(msgCtx);

        /** Specific checks for PullRequest **/
        verifyPullRequestResponseTimeOut(msgCtx);
        verifyNonMandatoryRequestsField(msgCtx);
        verifyPullRequestPayloadSelectorAgainstPayload(msgCtx);
    }

    private void verifyPullRequestResponseTimeOut(MessageDataContext msgCtx) throws CiseAdaptorValidationException {
        Integer pullRequestResponseTimeOut  = msgCtx.getPullRequestResponseTimeOut();

        // The parameter is mandatory, cannot be null or below equal to zero
        if (pullRequestResponseTimeOut == null || pullRequestResponseTimeOut <= 0) {
            throw new CiseAdaptorValidationException("Validation error message: pullRequestResponseTimeOut is mandatory, cannot be null or below equal to zero");
        }
    }


    private void verifyNonMandatoryRequestsField(MessageDataContext msgCtx) throws CiseAdaptorValidationException {
        Object pullRequestRequests = msgCtx.getPullRequestServiceCapabilityRequests();
        if (pullRequestRequests != null) {
            if (!(pullRequestRequests instanceof ServiceCapability)) {
                throw new CiseAdaptorValidationException("Validation error message: Requests is not an instance of ServiceCapability");
            } else if (pullRequestRequests instanceof SubscriptionCapability) {
                throw new CiseAdaptorValidationException("Validation error message: Requests cannot be an instance of SubscriptionCapability");
            }
        }
    }

    private void verifyPullRequestPayloadSelectorAgainstPayload(MessageDataContext msgCtx) throws CiseAdaptorValidationException {
        PayloadSelector pullRequestPayloadSelector = msgCtx.getPullRequestPayloadSelector();
        if (pullRequestPayloadSelector == null) {
            // No payload selector, nothing to be verified
            return;
        }

        // The validation checks only if the path returns something
        CoreEntityPayload cisePayload = msgCtx.getCisePayload();
        for (SelectorCondition sc : pullRequestPayloadSelector.getSelectors()) {
            try {
                String ciseConditionValue = getCiseConditionValue(sc, cisePayload);
                if (StringUtils.isEmpty(ciseConditionValue)) {
                    throw new CiseAdaptorValidationException("Validation error message: The PayloadSelector returned an empty value for this pattern: " + sc.getSelector());
                }
            } catch (XPathExpressionException e) {
                throw new CiseAdaptorValidationException("Validation error message: Xpath parsing thrown an error: " + e.getMessage());
            } catch (CiseAdaptorValidationException e) {
                throw e;
            }
        }
    }

    private String getCiseConditionValue(SelectorCondition selectorCondition, CoreEntityPayload payload) throws XPathExpressionException {
        String selector = selectorCondition.getSelector();
        XPathExpression selectorXPathExpression = xPath.compile(selector);
        return selectorXPathExpression.evaluate(xmlMapper.toDOM(payload));
    }
}
