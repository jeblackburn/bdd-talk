  Scenario Outline: Reports must have all required fields configured
    Given a report request specifying a <request-type>
    And the report is configured for account <account>
    And the report is configured for date range <date-range>
    When the report is submitted
    Then <report-count> reports are sent to NMR
    And the response indicates <response-message>
    
    Examples: 
    |request-type|account|date-range|report-count|response-message|
    |            |       |          |            |                |
