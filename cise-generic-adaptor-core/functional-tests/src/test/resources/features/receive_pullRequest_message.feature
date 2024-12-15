Feature: Adaptor for CISE PullRequest Message
  in order to forward information to the Legacy System,
  the Adaptor can receive a CISE PullRequest Message.

  Background: Receiving a message by the Adaptor.

  Scenario: 1) The CISE Adaptor receives a CISE PullRequest Message and forwards information to the Legacy System.
    Given a properly signed CISE PullRequest Message represented by file 'messages/good_ones/PullRequest_riskPullProvider_Signature_OK.xml'
    When the Adaptor receives the message
    Then an Acknowledgement with SUCCESS Status is replied
    And messageId, message is forwarded to the Legacy System.

  Scenario: 2) The CISE Adaptor accepts a CISE PullRequest Message and replies with an XML validation error.
    Given an invalid XML representation of CISE PullRequest Message represented by file 'messages/incorrect/PullRequest_not_valid_XML.xml'
    When the Adaptor receives the message
    Then an Acknowledgement with BAD_REQUEST Status is replied.
    And no information is forwarded to the Legacy System.

  Scenario: 3) The CISE Adaptor accepts a CISE PullRequest Message and replies with a signature error.
    Given an improperly signed CISE PullRequest Message represented by file 'messages/incorrect/PullRequest_Signature_KO.xml'
    When the Adaptor receives the message
    Then an Acknowledgement with AUTHENTICATION_ERROR Status is replied.
    And no information is forwarded to the Legacy System.

  Scenario: 4) The CISE Adaptor receives a CISE PullRequest Message, stores it, and forwards information to the Legacy System.
    Given Adapter with persistence and a properly signed CISE PullRequest Message represented by file 'messages/good_ones/PullRequest_riskPullProvider_Signature_OK.xml'
    When the Adaptor receives the message
    Then an Acknowledgement with SUCCESS Status is replied
    And message is being saved
    And messageId, message, message history is forwarded to the Legacy System.

  Scenario: 5) The CISE Adaptor receives a CISE PullRequest Message, stores it, and forwards information to alternative Legacy System.
    Given Adapter with persistence and a properly signed CISE PullRequest Message represented by file 'messages/good_ones/PullRequest_vesselPullProvider_Signature_OK.xml'
    When the Adaptor receives the message
    Then an Acknowledgement with SUCCESS Status is replied
    And message is being saved
    And message is forwarded to alternative Legacy System

  Scenario: 6) The CISE Adaptor receives a PullRequest Message and ensures contextId is present when message is forwarded to the Legacy System.
    Given Adapter with persistence and a properly signed CISE PullRequest Message represented by file 'messages/good_ones/PullRequest_riskPullProvider_Signature_OK.xml'
    When the Adaptor receives the message
    Then an Acknowledgement with SUCCESS Status is replied
    And contextId with value '91e6a630-59ce-439d-bb2c-06e0b8325c6d' is forwarded to the Legacy System.