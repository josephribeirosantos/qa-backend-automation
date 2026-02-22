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

            sh '''
              set -e

              docker rm -f selenium-chrome || true

              docker run -d --name selenium-chrome \
                -p 4444:4444 \
                --shm-size="2g" \
                selenium/standalone-chrome:latest

              echo "Waiting Selenium..."
              sleep 8

              mvn -U -B clean test \
                -Dcucumber.filter.tags="@smoke-web" \
                -Dweb.remote=true \
                -Dweb.remote.url=http://localhost:4444/wd/hub \
                -Dweb.headless=true
            '''
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
      sh 'docker rm -f selenium-chrome || true'
      echo "Finished: ${currentBuild.currentResult}"
    }
  }
}