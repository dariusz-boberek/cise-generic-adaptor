Feature: Adaptor for CISE Subscribe Consumer Message
  In order to be able to receive information from providers,
  the Adaptor first need to register in CISE node to a service.

  Background: Generic Adaptor sends CISE PullRequest Subscribe Message to CISE node.

  Scenario: 1) The CISE Adaptor constructs a CISE Subscribe Consumer Message using details from the Legacy System and sends it.
    Given internal persistence turned 'ON'
    And the Adaptor configured to handle 'vessel subscribe consumer'
    And add element to list of recipients with details 'VesselService' 'Subscribe' 'Provider' 'node21.vessel.subscribe.provider'
    And the MessageDataContext is initialized without a referenceMessage
    When the Adaptor constructs the 'Subscribe Consumer Message'
    Then the constructed message does not use a payload from the legacy system
    And the constructed message have sender data set corresponding to 'vessel subscribe consumer'
    And the constructed message does not have a discoveryProfile
    And the Message is sent to all recipients in the list
    And the creation date is updated
    And it signs the message
    And an adaptorToLegacySystemAck with SUCCESS Status is retrieved
    And the Message(s) sent are persisted correctly

  Scenario: 2) The CISE Adaptor fails to construct a CISE Subscribe Consumer Message due to missing recipients.
    Given internal persistence turned 'ON'
    And the Adaptor configured to handle 'vessel subscribe consumer'
    And no recipients are added to the list
    And the MessageDataContext is initialized without a referenceMessage
    When the Adaptor constructs the 'Subscribe Consumer Message'
    Then a CiseAdaptorValidationException is thrown
    And no message is persisted

  Scenario: 3) The CISE Adaptor constructs a CISE Subscribe Consumer Message for the Legacy System with multiple recipients.
    Given internal persistence turned 'ON'
    And the Adaptor configured to handle 'vessel subscribe consumer'
    And add element to list of recipients with details 'VesselService' 'Subscribe' 'Provider' 'node21.vessel.subscribe.provider'
    And add element to list of recipients with details 'VesselService' 'Subscribe' 'Provider' 'node22.vessel.subscribe.provider'
    And the MessageDataContext is initialized without a referenceMessage
    When the Adaptor constructs the 'Subscribe Consumer Message'
    Then the constructed message does not use a payload from the legacy system
    And the constructed message have sender data set corresponding to 'vessel subscribe consumer'
    And the constructed message does not have a discoveryProfile
    And the Message is sent to all recipients in the list
    And the creation date is updated
    And it signs the message
    And all acknowledgements with SUCCESS Status are retrieved for calls to the node
    And the Message(s) sent are persisted correctly

  Scenario: 4) The CISE Adaptor constructs a CISE Subscribe Consumer Message for an alternative Legacy System with multiple recipients.
    Given internal persistence turned 'ON'
    And the Adaptor configured to handle 'risk subscribe consumer'
    And add element to list of recipients with details 'RiskService' 'Subscribe' 'Provider' 'node23.risk.subscribe.provider'
    And add element to list of recipients with details 'RiskService' 'Subscribe' 'Provider' 'node24.risk.subscribe.provider'
    And the MessageDataContext is initialized without a referenceMessage
    When the Adaptor constructs the 'Subscribe Consumer Message'
    Then the constructed message does not use a payload from the legacy system
    And the constructed message have sender data set corresponding to 'risk subscribe consumer'
    And the constructed message does not have a discoveryProfile
    And the Message is sent to all recipients in the list
    And the creation date is updated
    And it signs the message
    And all acknowledgements with SUCCESS Status are retrieved for calls to the node
    And the Message(s) sent are persisted correctly

  Scenario: 5) The CISE Adaptor constructs a CISE Subscribe Consumer Message Unknown and sends it.
    Given internal persistence turned 'ON'
    And the Adaptor configured to handle 'vessel subscribe consumer'
    And no recipients are added to the list
    And the MessageDataContext is initialized without a referenceMessage
    And the MessageDataContext is configured with discoveryProfile 'VesselService' 'Subscribe' 'Provider'
    When the Adaptor constructs the 'Subscribe Consumer Message'
    Then the constructed message does not use a payload from the legacy system
    And the constructed message have sender data set corresponding to 'vessel subscribe consumer'
    And the constructed 'PullRequest' message has a discoveryProfile
    And the 'PullRequest' Message is sent without recipient
    And the creation date is updated
    And it signs the message
    And an adaptorToLegacySystemAck with SUCCESS Status is retrieved
    And the Message(s) sent are persisted correctly
