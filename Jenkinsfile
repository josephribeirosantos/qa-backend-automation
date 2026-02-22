pipeline {
  agent any

  options {
    timestamps()
    disableConcurrentBuilds()
  }

  environment {
    WEB_HEADLESS = "true"
    SELENIUM_CONTAINER = "selenium-chrome"
    SELENIUM_IMAGE = "selenium/standalone-chrome:latest"
    SELENIUM_PORT = "4444"
  }

  stages {
    stage('Checkout') { steps { checkout scm } }

    stage('Run Tests') {
      steps {
        script {
          if (!env.SUITE?.trim()) {
            error("SUITE não definida no Job. Configure SUITE=smoke-api ou SUITE=smoke-web no Jenkins.")
          }

          if (env.SUITE == 'smoke-api') {
            sh 'mvn -U -B clean test -Dcucumber.filter.tags="@smoke-api"'
          } else if (env.SUITE == 'smoke-web') {
            sh '''
              set -e
              docker rm -f "${SELENIUM_CONTAINER}" >/dev/null 2>&1 || true
              docker run -d --name "${SELENIUM_CONTAINER}" \
                -p ${SELENIUM_PORT}:4444 \
                -p 7900:7900 \
                --shm-size=2g \
                "${SELENIUM_IMAGE}"

              echo "Waiting Selenium..."
              for i in $(seq 1 30); do
                if curl -s "http://localhost:${SELENIUM_PORT}/wd/hub/status" | grep -q '"ready"[[:space:]]*:[[:space:]]*true'; then
                  echo "Selenium ready!"
                  break
                fi
                sleep 1
              done

              mvn -U -B clean test \
                -Dcucumber.filter.tags="@smoke-web" \
                -Dweb.remote=true \
                -Dweb.remote.url=http://localhost:${SELENIUM_PORT}/wd/hub \
                -Dweb.headless=${WEB_HEADLESS}
            '''
          } else {
            error("SUITE inválida: ${env.SUITE}")
          }
        }
      }
    }
  }

  post {
    always {
      sh 'docker rm -f "${SELENIUM_CONTAINER}" >/dev/null 2>&1 || true'
      echo "Finished: ${currentBuild.currentResult}"
    }
  }
}