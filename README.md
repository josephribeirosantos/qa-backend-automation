# QA Backend Automation --- Java + Cucumber + RestAssured + CI

Framework profissional de automação de testes de API REST, desenvolvido
em **Java 17**, utilizando **BDD com Cucumber**, validações com
**RestAssured**, execução via **JUnit Platform**, e integração completa
com **GitHub Actions (CI/CD)** com geração de relatórios automatizados.

Este projeto foi estruturado com foco em escalabilidade, organização
limpa e padrão de mercado para automação backend.

------------------------------------------------------------------------

# 🚀 Stack Tecnológica

## Linguagem & Build

-   Java 17
-   Maven

## BDD & Execução

-   Cucumber
-   JUnit Platform (Suite Engine)
-   Execução via `mvn clean test`

## API Testing

-   RestAssured
-   Hamcrest Matchers
-   JSONPath

## Relatórios

-   HTML Report
-   JSON Report
-   JUnit XML Report
-   Upload automático como artifact no GitHub Actions

## CI/CD

-   GitHub Actions
-   Execução automática em:
    -   push para main
    -   pull_request
    -   workflow_dispatch

------------------------------------------------------------------------

# 📂 Estrutura do Projeto

    qa-backend-automation/
    │
    ├─ .github/
    │  └─ workflows/
    │     └─ tests.yml
    │
    ├─ src/
    │  ├─ test/
    │  │  ├─ java/com/joseph/qa/automation/
    │  │  │   ├─ runners/
    │  │  │   │   └─ TestRunner.java
    │  │  │   ├─ stepdefinitions/
    │  │  │   │   ├─ PublicApiSteps.java
    │  │  │   │   └─ GetPostsSteps.java
    │  │  │   ├─ hooks/
    │  │  │   │   └─ Hooks.java
    │  │  │   ├─ config/
    │  │  │   │   └─ Config.java
    │  │  │   └─ BaseTest.java
    │  │
    │  │  └─ resources/
    │  │       ├─ features/
    │  │       │   ├─ public_api.feature
    │  │       │   └─ get_posts.feature
    │  │       ├─ config/application.properties
    │  │       └─ cucumber.properties
    │
    ├─ pom.xml
    └─ README.md

------------------------------------------------------------------------

# 🧪 Cenários Implementados

## 1️⃣ GitHub Public API (GET)

-   Requisição GET para API pública do GitHub
-   Validação de status 200
-   Validação de campo obrigatório no JSON (`current_user_url`)

## 2️⃣ JSONPlaceholder API (GET /posts)

-   Requisição GET para `/posts`
-   Validação de status 200
-   Validação de retorno de lista não vazia

------------------------------------------------------------------------

# 🏷️ Execução por Tags

### Executar todos os testes

    mvn clean test

### Executar apenas Smoke

    mvn clean test -Dcucumber.filter.tags="@smoke"

### Executar apenas Regression

    mvn clean test -Dcucumber.filter.tags="@regression"

------------------------------------------------------------------------

# 📊 Relatórios Gerados

Após execução local:

    target/
    ├── cucumber-report.html
    ├── cucumber.json
    ├── cucumber-junit.xml
    └── surefire-reports/

## Tipos de relatório

  Tipo        Finalidade
  ----------- ------------------------------------
  HTML        Visualização manual
  JSON        Integração com Allure / Dashboards
  JUnit XML   Integração com CI e ferramentas

------------------------------------------------------------------------

# 🤖 CI/CD -- GitHub Actions

Pipeline localizado em:

    .github/workflows/tests.yml

### O que o pipeline faz:

1.  Checkout do código
2.  Setup do Java 17
3.  Execução dos testes com filtro `@smoke`
4.  Upload automático de:
    -   surefire-reports
    -   cucumber-report.html
    -   cucumber.json
    -   cucumber-junit.xml

Os relatórios ficam disponíveis como Artifacts na aba:

Repository → Actions → Workflow Run → Artifacts

------------------------------------------------------------------------

# ⚙️ Configuração

Arquivo:

    src/test/resources/config/application.properties

Exemplo:

    github.url=https://api.github.com
    jsonplaceholder.baseurl=https://jsonplaceholder.typicode.com

------------------------------------------------------------------------

# 🏗️ Arquitetura Atual

O framework está estruturado seguindo princípios de organização limpa:

-   Runner isolado
-   Steps organizados por feature
-   Hooks centralizados
-   Configuração externa por properties
-   Separação clara entre Feature, Step Definition, Config, Runner e CI

Preparado para evolução futura com:

-   Service Layer (API Client)
-   DTOs
-   Autenticação
-   Multi-ambiente
-   Testes de contrato
-   Relatórios avançados (Allure)

------------------------------------------------------------------------

# 📌 Boas Práticas Aplicadas

-   Uso de BDD estruturado
-   Separação de responsabilidades
-   Configuração desacoplada
-   Execução por tags
-   CI automatizado
-   Geração de relatórios padrão mercado
-   Compatível com integração enterprise

------------------------------------------------------------------------

# 🚀 Próximos Passos Técnicos (Roadmap)

-   Implementação de Service Layer (API Client Pattern)
-   DTOs com Jackson
-   Allure Reports
-   Multi-environment profile
-   Testes autenticados (Bearer / OAuth2)
-   Schema validation
-   Pipeline paralela (Smoke / Regression)
-   Quality Gate

------------------------------------------------------------------------

# 👨‍💻 Autor

Joseph Ribeiro Santos\
QA Engineer \| QA Automation Engineer\
Backend API Testing Specialist

GitHub: https://github.com/josephribeirosantos
