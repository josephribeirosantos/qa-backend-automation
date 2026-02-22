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