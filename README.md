# QA Backend Automation (API Testing) — Java + Maven + Cucumber

Framework profissional de automação de testes de API, desenvolvido em **Java 17**, com **BDD (Cucumber)**, execução via **JUnit Platform**, validações com **Rest Assured**, e integração completa com **GitHub Actions (CI)**.

Este projeto foi construído para servir como base sólida para automação de APIs em ambientes reais (QA/Testing), com estrutura escalável e boas práticas.

---

## 📌 Tecnologias e Ferramentas Utilizadas

### Linguagem / Build
- **Java 17**
- **Maven**

### BDD + Runner
- **Cucumber (BDD)**
- **JUnit Platform Engine**
- Execução com `mvn test`

### Testes de API
- **Rest Assured** (requisições HTTP e validação de respostas)
- **Hamcrest Matchers** (assertions)
- **JSONPath** (extração e validação de campos no JSON)

### Configuração
- **application.properties**
- Classe `Config` para leitura centralizada de propriedades

### CI/CD
- **GitHub Actions**
- Pipeline configurado para rodar testes automaticamente em:
    - `push` na branch `main`
    - `pull_request` para `main`
    - `workflow_dispatch` (manual)

---

## 🎯 Objetivo do Projeto

Este repositório tem como foco:

- Automação de testes de APIs REST com boas práticas
- Estrutura BDD (Given/When/Then)
- Execução por tags (ex: `@smoke`)
- Base pronta para evolução com:
    - múltiplos ambientes (dev/hml/prod)
    - autenticação (Bearer, OAuth2, Basic)
    - relatórios HTML
    - integração com Allure / ExtentReports
    - testes de contrato e validação avançada

---

## 📂 Estrutura do Projeto

qa-backend-automation/
│
├─ .github/
│ └─ workflows/
│ └─ tests.yml # Pipeline CI (GitHub Actions)
│
├─ src/
│ ├─ main/
│ │ └─ resources/
│ │ └─ features/ # (Opcional) features se quiser separar
│
│ └─ test/
│ ├─ java/
│ │ └─ com/joseph/qa/automation/
│ │ ├─ runners/
│ │ │ └─ TestRunner.java # Runner JUnit Platform
│ │ │
│ │ ├─ stepdefinitions/
│ │ │ ├─ PublicApiSteps.java # Steps do teste GitHub
│ │ │ └─ GetPostsSteps.java # Steps do teste JSONPlaceholder
│ │ │
│ │ ├─ hooks/
│ │ │ └─ Hooks.java # Before/After do Cucumber
│ │ │
│ │ ├─ config/
│ │ │ └─ Config.java # Leitura de propriedades
│ │ │
│ │ └─ BaseTest.java # Setup base para testes
│ │
│ └─ resources/
│ ├─ config/
│ │ └─ application.properties # URLs e configs do projeto
│ │
│ ├─ cucumber.properties # Config global do Cucumber
│ │
│ └─ features/
│ ├─ public_api.feature # Feature GitHub API
│ └─ get_posts.feature # Feature JSONPlaceholder
│
├─ pom.xml
└─ README.md


---

## ✅ Cenários Implementados

### 1) GitHub Public API (GET)
- Faz um GET no endpoint raiz da API pública do GitHub
- Valida status code 200
- Valida presença de campo obrigatório no JSON (`current_user_url`)

Feature:
- `src/test/resources/features/public_api.feature`

---

### 2) JSONPlaceholder (GET /posts)
- Faz GET em `/posts`
- Valida status 200
- Valida que a resposta é uma lista com conteúdo

Feature:
- `src/test/resources/features/get_posts.feature`

---

## ⚙️ Configuração do Projeto

### Arquivo de propriedades
Local:

src/test/resources/config/application.properties
Exemplo:
```properties
github.url=https://api.github.com
jsonplaceholder.baseurl=https://jsonplaceholder.typicode.com

▶️ Como Executar Localmente
1) Executar todos os testes

mvn clean test

2) Executar apenas testes Smoke (por tags)

mvn clean test -Dcucumber.filter.tags="@smoke"

3) Executar por outra tag (exemplo)

mvn clean test -Dcucumber.filter.tags="@regression"

🧪 Padrão BDD (Cucumber)

O projeto utiliza Gherkin no formato:

Feature: Public API

  Scenario: Validate GitHub public API root endpoint
    Given the public API endpoint is available
    When I perform a GET request to the GitHub API
    Then the response status code should be 200
    And the response should contain a non-empty field "current_user_url"

E os steps ficam em:

src/test/java/com/joseph/qa/automation/stepdefinitions/

🏷️ Tags de Execução

Tags são usadas para filtrar cenários:

Exemplo:

@smoke
Scenario: Validate GitHub public API root endpoint

Executar apenas smoke:
mvn test -Dcucumber.filter.tags="@smoke"

🤖 GitHub Actions (CI)

Pipeline configurado em:

.github/workflows/tests.yml

Comando utilizado no CI:

mvn -U clean test -Dcucumber.filter.tags="@smoke"

📌 Boas Práticas Aplicadas

- Estrutura modular (runner / steps / hooks / config)
- Configuração centralizada por properties
- BDD com Gherkin + StepDefinitions limpas
- Uso de RestAssured para API Testing
- CI com GitHub Actions (execução real em ambiente Linux)
- Tags para smoke/regression (pronto para crescer)

🚀 Próximas Evoluções (Roadmap)

- Sugestões de evolução natural para este framework:
- Relatórios HTML (Cucumber Reports)
- Allure Reports
- Execução por profiles (dev/hml/prod)
- Autenticação (Bearer / OAuth2)
- Reutilização por Service Layer (API Client)
- DTOs com Jackson
- Testes de schema JSON (JSON Schema Validator)
- Integração com SonarQube e Quality Gate

👨‍💻 Autor
Joseph Ribeiro Santos
QA Engineer | QA Automation | Backend API Testing
GitHub: https://github.com/josephribeirosantos

