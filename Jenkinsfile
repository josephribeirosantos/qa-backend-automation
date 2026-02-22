pipeline {
  agent any

  tools {
    jdk 'JDK17'          // configura no Jenkins (Manage Jenkins > Tools)
    maven 'Maven3'       // configura no Jenkins (Manage Jenkins > Tools)
  }

  options {
    timestamps()
    ansiColor('xterm')
    disableConcurrentBuilds()
  }

  environment {
    // para testes web em CI
    HEADLESS = "true"
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Smoke API') {
      steps {
        sh 'mvn -U -B clean test -Dcucumber.filter.tags="@smoke-api"'
      }
    }

    stage('Smoke Web') {
      steps {
        sh 'mvn -U -B clean test -Dcucumber.filter.tags="@smoke-web" -Dweb.headless=${HEADLESS}'
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