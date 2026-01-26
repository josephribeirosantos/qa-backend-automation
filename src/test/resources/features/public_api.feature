Feature: Validate GitHub API

  Scenario: Validate GitHub public API root endpoint
    Given the public API endpoint is available
    When I perform a GET request to the GitHub API
    Then the response status code should be 200
    And the response should contain a non-empty field "current_user_url"