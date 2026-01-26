# language: pt
@smoke @posts
Funcionalidade: Consultar posts da API pública

  @get_posts
  Cenário: Buscar lista de posts com sucesso
    Dado que eu faço uma requisição GET para "/posts"
    Então o status code deve ser 200
    E a resposta deve conter uma lista de posts