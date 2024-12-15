Feature: Adaptor for CISE Push Message Sending
  In order to send information to the CISE Node,
  the Adaptor creates and sends a CISE Push Message.

  Background: Adaptor sends a message to the CISE Node.

  Scenario: 1) The CISE Adaptor constructs a CISE Push Message using data from the Legacy System and sends it.
    Given a payload from the Legacy System represented by file 'legacymessages/good_ones/Payload_For_vesselPushProvider_Push_OK.xml' and a referenceMessageId ''
    And internal persistence turned 'ON'
    And the Adaptor configured to handle 'vessel push provider'
    And add element to list of recipients with details 'VesselService' 'Push' 'Consumer' 'node13.vessel.push.consumer'
    And the MessageDataContext is initialized without a referenceMessage
    When the Adaptor constructs the 'Push Message'
    Then the constructed message uses the payload from legacy system
    And the constructed message have sender data set corresponding to 'vessel push provider'
    And the constructed message does not have a discoveryProfile
    And the Message is sent to all recipients in the list
    And the creation date is updated
    And it signs the message
    And an adaptorToLegacySystemAck with SUCCESS Status is retrieved
    And the Message(s) sent are persisted correctly

  Scenario: 2) The CISE Adaptor constructs a CISE Push Message for an alternative Legacy System with multiple recipients
    Given a payload from the Legacy System represented by file 'legacymessages/good_ones/Payload_For_riskPushProvider_Push_OK.xml' and a referenceMessageId ''
    And internal persistence turned 'ON'
    And the Adaptor configured to handle 'risk push provider'
    And add element to list of recipients with details 'RiskService' 'Push' 'Consumer' 'node14.risk.push.consumer'
    And add element to list of recipients with details 'RiskService' 'Push' 'Consumer' 'node15.risk.push.consumer'
    And the MessageDataContext is initialized without a referenceMessage
    When the Adaptor constructs the 'Push Message'
    Then the constructed message uses the payload from legacy system
    And the constructed message have sender data set corresponding to 'risk push provider'
    And the constructed message does not have a discoveryProfile
    And the Message is sent to all recipients in the list
    And the creation date is updated
    And it signs the message
    And all acknowledgements with SUCCESS Status are retrieved for calls to the node
    And the Message(s) sent are persisted correctly

  Scenario: 3) The CISE Adaptor fails to construct a CISE Push Message due to an invalid payload.
    Given a payload from the Legacy System represented by file 'legacymessages/incorrect/Payload_For_vesselPushProvider_Push_notValid.xml' and a referenceMessageId ''
    And internal persistence turned 'ON'
    And the Adaptor configured to handle 'vessel push provider'
    And add element to list of recipients with details 'VesselService' 'Push' 'Consumer' 'node13.vessel.push.consumer'
    And the MessageDataContext is initialized without a referenceMessage
    When the Adaptor constructs the 'Push Message'
    Then a CiseAdaptorValidationException is thrown
    And no message is persisted

  Scenario: 4) The CISE Adaptor constructs a CISE Push Message and ensures contextId is present when message is forwarded to the CISE Node.
    Given a payload from the Legacy System represented by file 'legacymessages/good_ones/Payload_For_riskPushProvider_Push_OK.xml' and a referenceMessageId ''
    And internal persistence turned 'ON'
    And the Adaptor configured to handle 'risk push provider'
    And add element to list of recipients with details 'RiskService' 'Push' 'Consumer' 'node14.risk.push.consumer'
    And add element to list of recipients with details 'RiskService' 'Push' 'Consumer' 'node15.risk.push.consumer'
    And the MessageDataContext is initialized without a referenceMessage
    And the MessageDataContext have contextId field initialized with value 'contextId-123'
    When the Adaptor constructs the 'Push Message'
    Then the Message is sent to all recipients in the list
    And all acknowledgements with SUCCESS Status are retrieved for calls to the node
    And contextId with value 'contextId-123' is forwarded to the CISE Node.
    And the Message(s) sent are persisted correctly

  Scenario: 5) The CISE Adaptor constructs a CISE Push Message using data from the Legacy System and sends it using the MessageDataContextManager only.
    Given a payload from the Legacy System represented by file 'legacymessages/good_ones/Payload_For_vesselPushProvider_Push_OK.xml' and a referenceMessageId ''
    And internal persistence turned 'ON'
    And the Adaptor configured to handle 'vessel push provider'
    And add element to list of recipients with details 'VesselService' 'Push' 'Consumer' 'node13.vessel.push.consumer'
    And the MessageDataContext is initialized without a referenceMessage
    When the Adaptor constructs the 'Push Message' passing payload to the MessageDataContextManager
    Then the constructed message uses the payload from legacy system
    And the constructed message have sender data set corresponding to 'vessel push provider'
    And the constructed message does not have a discoveryProfile
    And the Message is sent to all recipients in the list
    And the creation date is updated
    And it signs the message
    And an adaptorToLegacySystemAck with SUCCESS Status is retrieved
    And the Message(s) sent are persisted correctly


  Scenario: 6) The CISE Adaptor constructs a CISE Push Unknown Message using data from the Legacy System and sends it.
    Given a payload from the Legacy System represented by file 'legacymessages/good_ones/Payload_For_vesselPushProvider_Push_OK.xml' and a referenceMessageId ''
    And internal persistence turned 'ON'
    And the Adaptor configured to handle 'vessel push provider'
    And no recipients are added to the list
    And the MessageDataContext is initialized without a referenceMessage
    And the MessageDataContext is configured with discoveryProfile 'VesselService' 'Push' 'Consumer'
    When the Adaptor constructs the 'Push Message'
    Then the constructed message uses the payload from legacy system
    And the constructed message have sender data set corresponding to 'vessel push provider'
    And the constructed 'Push' message has a discoveryProfile
    And the 'Push' Message is sent without recipient
    And the creation date is updated
    And it signs the message
    And an adaptorToLegacySystemAck with SUCCESS Status is retrieved
    And the Message(s) sent are persisted correctly
