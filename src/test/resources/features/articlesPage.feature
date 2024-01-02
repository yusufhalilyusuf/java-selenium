Feature: Articles Page

  Scenario: Validate elements on the page
    Given the user is on articles page
    Then the following should be presented
      | HOME     |
      | ARTICLES |
      | TOPICS   |

@wip
  Scenario: All articles are presented as expected
    Given the user is on articles page
    Then all articles are shown on the page
    And sorted by Date as default
    And ordered by Descending as default