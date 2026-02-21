@smoke @web
Feature: Web Login

  @smoke-web @regression-web
  Scenario: Login com credenciais válidas
    Given I am on the login page
    When I login with username "tomsmith" and password "SuperSecretPassword!"
    Then I should see a success message