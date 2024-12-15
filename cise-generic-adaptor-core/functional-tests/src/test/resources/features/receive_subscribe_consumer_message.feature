Feature: Adaptor for CISE Subscribe Consumer Message
  In order to forward information to the Legacy System,
  the Adaptor can receive a CISE Push Message sent from the subscribe provider.

  Background: Receiving a message by the Adaptor.

  Scenario: 1) The CISE Adaptor receives a Subscribe Consumer Message, which is CISE Push Message send from Subscribe Provider, and then forwards information to the Legacy System.
    Given a properly signed CISE 'Subscribe Consumer' Message represented by file 'messages/good_ones/Push_riskSubscribeConsumer_Signature_OK.xml'
    When the Adaptor receives the message
    Then an Acknowledgement with SUCCESS Status is replied
    And information is forwarded to the Legacy System.
