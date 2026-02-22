pipeline {
  agent any

  parameters {
    choice(
      name: 'SUITE',
      choices: ['smoke-api', 'smoke-web'],
      description: 'Qual suíte executar?'
    )
  }

  tools {
    jdk 'JDK17'
    maven 'Maven3'
  }

  options {
    timestamps()
    disableConcurrentBuilds()
  }

  environment {
    // Mantém padrão para web em CI
    WEB_HEADLESS = "true"
    SELENIUM_CONTAINER = "selenium-chrome"
    SELENIUM_IMAGE = "selenium/standalone-chrome:latest"
    SELENIUM_PORT = "4444"
  }

  stages {

    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Run Tests') {
      steps {
        script {

          if (params.SUITE == 'smoke-api') {

            sh 'mvn -U -B clean test -Dcucumber.filter.tags="@smoke-api"'

          } else if (params.SUITE == 'smoke-web') {

            sh '''
              set -e

              echo "==> Cleaning old selenium container (if exists)..."
              if command -v docker >/dev/null 2>&1; then
                docker rm -f "${SELENIUM_CONTAINER}" >/dev/null 2>&1 || true
              else
                echo "Docker CLI not available in this agent."
                exit 1
              fi

              echo "==> Starting Selenium standalone Chrome..."
              docker run -d --name "${SELENIUM_CONTAINER}" \
                -p ${SELENIUM_PORT}:4444 \
                -p 7900:7900 \
                --shm-size=2g \
                "${SELENIUM_IMAGE}"

              echo "==> Waiting Selenium to be ready..."
              # espera até 30s pela API de status retornar 'ready: true'
              for i in $(seq 1 30); do
                if curl -s "http://localhost:${SELENIUM_PORT}/wd/hub/status" | grep -q '"ready"[[:space:]]*:[[:space:]]*true'; then
                  echo "Selenium is ready!"
                  break
                fi
                sleep 1
              done

              echo "==> Running WEB smoke tests..."
              mvn -U -B clean test \
                -Dcucumber.filter.tags="@smoke-web" \
                -Dweb.remote=true \
                -Dweb.remote.url=http://localhost:${SELENIUM_PORT}/wd/hub \
                -Dweb.headless=${WEB_HEADLESS}

            '''
          } else {
            error("SUITE inválida: ${params.SUITE}")
          }
        }
      }
    }

    stage('Archive Reports') {
      steps {
        archiveArtifacts artifacts: 'target/surefire-reports/**, target/cucumber.json, target/cucumber-report.html', allowEmptyArchive: true
        junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
      }
    }
  }

  post {
    always {
      sh '''
        if command -v docker >/dev/null 2>&1; then
          docker rm -f "${SELENIUM_CONTAINER}" >/dev/null 2>&1 || true
        else
          echo "Docker CLI not available in this agent. Skipping selenium cleanup."
        fi
      '''
      echo "Finished: ${currentBuild.currentResult}"
    }
  }
}