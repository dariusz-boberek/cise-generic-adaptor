Feature: Adaptor for CISE PullResponse Message.
  In order to forward information to the Legacy System,
  the Adaptor can receive a CISE PullResponse Message.

  Background: Receiving a message by the Adaptor.

  Scenario: 1) The CISE Adaptor successfully receives a CISE PullResponse Message and forwards it to the Legacy System
    Given a properly signed CISE PullResponse Message represented by file 'messages/good_ones/PullResponse_riskPullConsumer_Signature_OK.xml'
    When the Adaptor receives the message
    Then an Acknowledgement with SUCCESS Status is replied
    And information is forwarded to the Legacy System.

  Scenario: 2) The CISE Adaptor receives a PullResponse Message with a signature error
    Given an improperly signed CISE PullResponse Message represented by file 'messages/incorrect/PullResponse_riskPullConsumer_Signature_KO.xml'
    When the Adaptor receives the message
    Then an Acknowledgement with AUTHENTICATION_ERROR Status is replied.
    And no information is forwarded to the Legacy System.

  Scenario: 3) The CISE Adaptor receives a PullResponse Message with invalid XML
    Given an invalid XML representation of CISE PullResponse Message represented by file 'messages/incorrect/PullResponse_riskPullConsumer_not_valid_XML.xml'
    When the Adaptor receives the message
    Then an Acknowledgement with BAD_REQUEST Status is replied.
    And no information is forwarded to the Legacy System.

  Scenario: 4) The CISE Adaptor successfully receives a CISE PullResponse Message, saves it, and forwards it to the Legacy System
    Given Adapter with persistence and a properly signed CISE PullResponse Message represented by file 'messages/good_ones/PullResponse_riskPullConsumer_Signature_OK.xml'
    When the Adaptor receives the message
    Then an Acknowledgement with SUCCESS Status is replied
    And message is being saved
    And information is forwarded to the Legacy System.
    And contextId with value '91e6a630-59ce-439d-bb2c-06e0b8325c6d' is forwarded to the Legacy System.
