Feature: Adaptor for CISE Push Message.
  In order to forward information to the Legacy System,
  the Adaptor can receive a CISE Push Message.

  Background: Receiving a message by the Adaptor.

  Scenario: 1) The CISE Adaptor receives a CISE Push Message and forwards information to the Legacy System.
    Given a properly signed CISE Push Message represented by file 'messages/good_ones/Push_riskPushConsumer_Signature_OK.xml'
    When the Adaptor receives the message
    Then an Acknowledgement with SUCCESS Status is replied
    And information is forwarded to the Legacy System.

  Scenario: 2) The CISE Adaptor accepts a CISE Push Message and replies with an XML validation error.
    Given an invalid XML representation of CISE Push Message represented by file 'messages/incorrect/Push_vesselPushProvider_notValid.xml'
    When the Adaptor receives the message
    Then an Acknowledgement with BAD_REQUEST Status is replied.
    And no information is forwarded to the Legacy System.

  Scenario: 3) The CISE Adaptor accepts a CISE Push Message and replies with a signature error.
    Given an improperly signed CISE Push Message represented by file 'messages/incorrect/Push_Signature_KO.xml'
    When the Adaptor receives the message
    Then an Acknowledgement with AUTHENTICATION_ERROR Status is replied.
    And no information is forwarded to the Legacy System.


  Scenario: 4) The CISE Adaptor receives a CISE Push Message and forwards information to alternative Legacy System.
    Given a properly signed CISE Push Message represented by file 'messages/good_ones/Push_vesselPushConsumer_OK.xml'
    When the Adaptor receives the message
    Then an Acknowledgement with SUCCESS Status is replied
    And message is forwarded to alternative Legacy System

