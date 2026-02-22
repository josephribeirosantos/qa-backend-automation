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
    // opcional: evita checkout automático duplicado
    skipDefaultCheckout(true)
  }

  environment {
    // para web em CI
    HEADLESS = "true"
    // nome fixo do container Selenium
    SELENIUM_CONTAINER = "selenium-chrome"
    SELENIUM_URL = "http://localhost:4444/wd/hub"
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

          } else {

            sh """
              set -e

              echo "==> Cleaning previous Selenium container (if exists)"
              docker rm -f ${SELENIUM_CONTAINER} >/dev/null 2>&1 || true

              echo "==> Starting Selenium Standalone Chrome"
              docker run -d --name ${SELENIUM_CONTAINER} \\
                -p 4444:4444 \\
                --shm-size="2g" \\
                selenium/standalone-chrome:latest

              echo "==> Waiting Selenium to be ready..."
              sleep 8

              echo "==> Running WEB tests"
              mvn -U -B clean test \\
                -Dcucumber.filter.tags="@smoke-web" \\
                -Dweb.remote=true \\
                -Dweb.remote.url=${SELENIUM_URL} \\
                -Dweb.headless=${HEADLESS}
            """
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
      sh """
        set +e
        if command -v docker >/dev/null 2>&1; then
          docker rm -f ${SELENIUM_CONTAINER} >/dev/null 2>&1 || true
        fi
      """
      echo "Finished: ${currentBuild.currentResult}"
    }
  }
}