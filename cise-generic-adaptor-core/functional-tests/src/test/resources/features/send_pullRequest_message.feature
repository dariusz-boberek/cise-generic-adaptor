Feature: Adaptor for CISE PullRequest Message Sending
  In order to send information to the CISE Node,
  the Adaptor creates and sends a CISE PullRequest Message.

  Background: Adaptor sends a message to the CISE Node.

  Scenario: 1) The CISE Adaptor constructs a CISE PullRequest Message using data from the Legacy System and sends it.
    Given a payload from the Legacy System represented by file 'legacymessages/good_ones/Payload_For_vesselPullConsumer_PullRequest_OK.xml' and a referenceMessageId ''
    And internal persistence turned 'ON'
    And the Adaptor configured to handle 'vessel pull consumer'
    And add element to list of recipients with details 'VesselService' 'Pull' 'Provider' 'node13.vessel.pull.provider'
    And the MessageDataContext is initialized without a referenceMessage
    And the MessageDataContext have pullRequestResponseTimeOut field initialized with value 1000
    When the Adaptor constructs the 'PullRequest Message'
    Then the constructed message uses the payload from legacy system
    And the constructed message have sender data set corresponding to 'vessel pull consumer'
    And the constructed message does not have a discoveryProfile
    And the Message is sent to one recipient
    And the creation date is updated
    And it signs the message
    And an adaptorToLegacySystemAck with SUCCESS Status is retrieved
    And the Message(s) sent are persisted correctly


  Scenario: 2) The CISE Adaptor constructs a CISE PullRequest Message using data from the Legacy System, it adds a PayloadSelector and sends it.
    Given a payload from the Legacy System represented by file 'legacymessages/good_ones/Payload_For_vesselPullConsumer_PullRequest_OK.xml' and a referenceMessageId ''
    And internal persistence turned 'ON'
    And the Adaptor configured to handle 'vessel pull consumer'
    And add element to list of recipients with details 'VesselService' 'Pull' 'Provider' 'node13.vessel.pull.provider'
    And the MessageDataContext is initialized without a referenceMessage
    And the MessageDataContext have pullRequestPayloadSelector field initialized with Selector value '//Vessel[1]/IMONumber' and to be equal
    And the MessageDataContext have pullRequestRequests field initialized with responseTime 32 maxNumberOfRequests 1 maxEntitiesPerMsg 100 and query to be done in best effort
    And the MessageDataContext have pullRequestResponseTimeOut field initialized with value 1000
    When the Adaptor constructs the 'PullRequest Message'
    Then the constructed message uses the payload from legacy system
    And the constructed message have sender data set corresponding to 'vessel pull consumer'
    And the constructed message does not have a discoveryProfile
    And the Message is sent to one recipient
    And the creation date is updated
    And it signs the message
    And an adaptorToLegacySystemAck with SUCCESS Status is retrieved
    And the Message(s) sent are persisted correctly
    And the Message contains the PayloadSelector with one selector valued '//Vessel[1]/IMONumber' and to be equal
    And the Message contains the ServiceCapabilities with the values responseTime 32 maxNumberOfRequests 1 maxEntitiesPerMsg 100 and query to be done in best effort

  Scenario: 3) The CISE Adaptor fails to construct a CISE PullRequest Message due to an invalid payload.
    Given a payload from the Legacy System represented by file 'legacymessages/incorrect/Payload_Without_Anies_notValid.xml' and a referenceMessageId ''
    And internal persistence turned 'ON'
    And the Adaptor configured to handle 'vessel pull consumer'
    And add element to list of recipients with details 'VesselService' 'Pull' 'Provider' 'node13.vessel.pull.provider'
    And the MessageDataContext is initialized without a referenceMessage
    And the MessageDataContext have pullRequestResponseTimeOut field initialized with value 1000
    When the Adaptor constructs the 'PullRequest Message'
    Then a CiseAdaptorValidationException is thrown
    And no message is persisted

  Scenario: 4) The CISE Adaptor fails to construct a PullRequest Message due to missing recipients.
    Given internal persistence turned 'ON'
    And the Adaptor configured to handle 'vessel pull consumer'
    And no recipients are added to the list
    And the MessageDataContext is initialized without a referenceMessage
    And the MessageDataContext have pullRequestResponseTimeOut field initialized with value 1000
    When the Adaptor constructs the 'PullRequest Message'
    Then a CiseAdaptorValidationException is thrown
    And no message is persisted

  Scenario: 6) The CISE Adaptor constructs a CISE PullRequest Unknown Message using data from the Legacy System and sends it using the MessageDataContextManager only.
    Given a payload from the Legacy System represented by file 'legacymessages/good_ones/Payload_For_vesselPullConsumer_PullRequest_OK.xml' and a referenceMessageId ''
    And internal persistence turned 'ON'
    And the Adaptor configured to handle 'vessel pull consumer'
    And no recipients are added to the list
    And the MessageDataContext is initialized without a referenceMessage
    And the MessageDataContext is configured with discoveryProfile 'VesselService' 'Pull' 'Provider'
    When the Adaptor constructs the 'PullRequest Message' passing payload to the MessageDataContextManager
    Then the constructed message uses the payload from legacy system
    And the constructed message have sender data set corresponding to 'vessel pull consumer'
    And the constructed 'PullRequest' message has a discoveryProfile
    And the 'PullRequest' Message is sent without recipient
    And the creation date is updated
    And it signs the message
    And an adaptorToLegacySystemAck with SUCCESS Status is retrieved
    And the Message(s) sent are persisted correctly
