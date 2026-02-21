@smoke @api
Feature: Consultar posts da API pública

  @smoke-api @regression-api
  Scenario: Buscar lista de posts com sucesso
    Given que eu faço uma requisição GET para "/posts"
    Then o status code deve ser 200
    And a resposta deve conter uma lista de posts
