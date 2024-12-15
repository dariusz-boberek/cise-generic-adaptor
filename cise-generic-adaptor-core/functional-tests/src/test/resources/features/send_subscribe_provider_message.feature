Feature: Adaptor for CISE Subscribe Provider Message
  In order to provide information to subscribers,
  the Adaptor creates and sends a CISE Subscribe Provider Message.

  Background: Adaptor publishes a message to the CISE Node.

  Scenario: 1) The CISE Adaptor constructs a CISE Subscribe Provider Message using data from the Legacy System.
    Given a payload from the Legacy System represented by file 'legacymessages/good_ones/Payload_For_vesselSubscribeProvider_OK.xml' and a referenceMessageId ''
    And internal persistence turned 'ON'
    And the Adaptor configured to handle 'vessel subscribe provider'
    And the MessageDataContext is initialized without a referenceMessage
    When the Adaptor constructs the 'Subscribe Provider Message'
    Then the constructed message uses the payload from legacy system
    And the constructed message have sender data set corresponding to 'vessel subscribe provider'
    And the constructed message does not have a discoveryProfile
    And the Message is sent to one recipient
    And the creation date is updated
    And it signs the message
    And an adaptorToLegacySystemAck with SUCCESS Status is retrieved
    And the Message(s) sent are persisted correctly

  Scenario: 2) The CISE Adaptor fails to construct a CISE Subscribe Provider Message due to an invalid payload.
    Given a payload from the Legacy System represented by file 'legacymessages/incorrect/Payload_For_vesselPushProvider_Push_notValid.xml' and a referenceMessageId ''
    And internal persistence turned 'ON'
    And the Adaptor configured to handle 'vessel subscribe provider'
    And the MessageDataContext is initialized without a referenceMessage
    When the Adaptor constructs the 'Subscribe Provider Message'
    Then a CiseAdaptorValidationException is thrown
    And no message is persisted
