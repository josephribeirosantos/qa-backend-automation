Feature: Consultar posts da API pública

  Scenario: Buscar lista de posts com sucesso
    Given que eu faço uma requisição GET para "/posts"
    Then o status code deve ser 200
    And a resposta deve conter uma lista de posts
