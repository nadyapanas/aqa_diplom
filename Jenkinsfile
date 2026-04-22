pipeline {
    agent any

    tools {
        jdk 'jdk21'
        maven 'maven'
    }

    parameters {
        choice(name: 'testsType', choices: ['api', 'web'], description: 'Choose tests type to run')
    }

    triggers {
        cron('H 15 * * *')
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean install'
            }
        }
        stage('Test') {
            environment {
                DB_NAME = 'basketball'
                DB_PORT = '5432'
                DB_HOST = 'localhost'
                API_BASE_URL = 'http://localhost:5030'
                WEB_USER_FIRST_NAME = credentials('jenkins-web-user-first-name')
                WEB_USER_SECOND_NAME = credentials('jenkins-web-user-second-name')
            }
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'jenkins-db-creds',
                        usernameVariable: 'DB_USER',
                        passwordVariable: 'DB_PASSWORD'
                    ),
                    usernamePassword(
                        credentialsId: 'jenkins-web-user-creds',
                        usernameVariable: 'WEB_USER_EMAIL',
                        passwordVariable: 'WEB_USER_PASSWORD'
                    )
                ]) {
                    echo "Run ${params.testsType} tests"
                    sh "mvn test -Dgroups=${params.testsType}"
                }
            }
            post {
                always {
                    junit "${params.testsType}-tests/target/surefire-reports/*.xml"
                }
            }
        }
        stage('Allure report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: "${params.testsType}-tests/target/allure-results"]]
                ])
            }
        }
    }
}