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
                sh 'mvn -B -DskipTests clean package'
            }
        }
//         stage('Checkstyle') {
//             steps {
//                 sh 'mvn checkstyle:checkstyle'
//             }
//             post {
//                 always {
//                     recordIssues tools: [checkStyle(pattern: 'target/checkstyle-result.xml')]
//                 }
//             }
//         }
        stage('Test') {
            steps {
                echo "Run ${params.testsType} tests"
                sh 'mvn test -Dgroups=${params.testsType}'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Allure report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }
}