Feature: Adaptor for CISE PullResponse Message.
  In order to send message to the CISE Node,
  the Adaptor creates and sends CISE PullResponse.

  Background: Adaptor sends message to the CISE Node.

  Scenario: 1) The CISE Adaptor retrieves a prior PullRequest message and sends a constructed CISE PullResponse using referenceMessageId from Risk adaptor
    Given saved by adapter PullRequest in 'messages/good_ones/PullRequest_riskPullProvider_Signature_OK.xml' containing messageId 'f28e1703-4c0d-48ce-aba5-e187a3321333'
    And the Adaptor configured to handle 'risk pull provider'
    And a payload from the Legacy System represented by file 'legacymessages/good_ones/Payload_For_riskPullProvider_PullResponse_OK.xml' and a referenceMessageId 'f28e1703-4c0d-48ce-aba5-e187a3321333'
    And the MessageDataContext is initialized with a referenceMessage
    When the Adaptor constructs the 'PullResponse Message'
    Then the constructed message reuses data from the PullRequest in 'messages/good_ones/PullRequest_riskPullProvider_Signature_OK.xml'
    And the constructed message uses the payload from legacy system
    And the constructed message have sender data set corresponding to 'risk pull provider'
    And the creation date is updated
    And it signs the message
    And an adaptorToLegacySystemAck with SUCCESS Status is retrieved
    And the Message(s) sent are persisted correctly

  Scenario: 2) The CISE Adaptor retrieves a prior PullRequest message and sends a constructed CISE PullResponse using referenceMessageId from Vessel adaptor
    Given saved by adapter PullRequest in 'messages/good_ones/PullRequest_vesselPullProvider_Signature_OK.xml' containing messageId 'f28e1703-4c0d-48ce-aba5-e187a3321333'
    And the Adaptor configured to handle 'vessel pull provider'
    And a payload from the Legacy System represented by file 'legacymessages/good_ones/Payload_For_vesselPullProvider_PullResponse_OK.xml' and a referenceMessageId 'f28e1703-4c0d-48ce-aba5-e187a3321333'
    And the MessageDataContext is initialized with a referenceMessage
    When the Adaptor constructs the 'PullResponse Message'
    Then the constructed message reuses data from the PullRequest in 'messages/good_ones/PullRequest_vesselPullProvider_Signature_OK.xml'
    And the constructed message uses the payload from legacy system
    And the constructed message have sender data set corresponding to 'vessel pull provider'
    And the creation date is updated
    And it signs the message
    And an adaptorToLegacySystemAck with SUCCESS Status is retrieved
    And the Message(s) sent are persisted correctly

#  Scenario: The CISE Adaptor fails to find a prior PullRequest using referenceMessageId and cannot send a CISE PullResponse.
#    Given database turned on but no PullRequest stored with messageId 'referenceMessageId'
#    And a payload from the Legacy System represented by file 'legacymessages/good_ones/Payload_For_vesselPullProvider_PullResponse_OK.xml' and a referenceMessageId 'f28e1703-4c0d-48ce-aba5-e187a3321333'
#    When the Adaptor constructs the PullResponse Message
#    Then an error indicating 'No matching PullRequest found' is raised and no PullResponse is constructed
#    And an adaptorToLegacySystemAck with ERROR Status is retrieved.
