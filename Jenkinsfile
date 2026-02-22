pipeline {
  agent any

  parameters {
    choice(name: 'SUITE', choices: ['smoke-api', 'smoke-web'], description: 'Qual suíte executar?')
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
    HEADLESS = "true"
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Run Tests') {
      steps {
        script {
          if (params.SUITE == 'smoke-api') {
            sh 'mvn -U -B clean test -Dcucumber.filter.tags="@smoke-api"'
          } else {
            sh 'mvn -U -B clean test -Dcucumber.filter.tags="@smoke-web" -Dweb.headless=true'
          }
        }
      }
    }

    stage('Smoke Web') {
      steps {
        script {
          sh '''
            set -e

            # sobe selenium standalone chrome
            docker rm -f selenium-chrome || true
            docker run -d --name selenium-chrome \
              -p 4444:4444 \
              --shm-size="2g" \
              selenium/standalone-chrome:latest

            # espera o selenium ficar pronto
            for i in $(seq 1 30); do
              if curl -s http://localhost:4444/wd/hub/status | grep -q '"ready":true'; then
                echo "Selenium ready"
                break
              fi
              echo "Waiting selenium..."
              sleep 1
            done

            # roda web tests em headless apontando para selenium remote
            mvn -U -B clean test \
              -Dcucumber.filter.tags="@smoke-web" \
              -Dweb.remote=true \
              -Dweb.remote.url=http://localhost:4444/wd/hub \
              -Dweb.headless=true
          '''
        }
      }
      post {
        always {
          sh 'docker rm -f selenium-chrome || true'
        }
      }
    }

    stage('Archive Reports') {
      steps {
        archiveArtifacts artifacts: 'target/surefire-reports/**, target/cucumber.json, target/cucumber-report.html', fingerprint: true, allowEmptyArchive: true
        junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
      }
    }
  }

  post {
    always {
      echo "Finished: ${currentBuild.currentResult}"
    }
  }
}