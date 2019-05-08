Feature: View master library report templates

  Scenario: User with access to everything
    Given a user with super-user privileges
    And our example master library
    When the user views the master library
    Then The user sees 4 folders and 3 templates

  Scenario: User with access to some templates
    Given a user with access to templates T1,T2
    And our example master library
    When the user views the master library
    Then The user sees 4 folders and 2 templates

  Scenario: User with access to no templates within a folder
    Given a user with access to templates T2,T3
    And our example master library
    When the user views the master library
    Then The user sees 2 folders and 2 templates

