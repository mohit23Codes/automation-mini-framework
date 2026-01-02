pipeline {
    agent any
    
    options {
        skipDefaultCheckout(true)
    }

    parameters {
        string(
            name: 'BRANCH',
            defaultValue: 'main',
            description: 'Git branch to build'
        )

        choice(
            name: 'SUITE',
            choices: ['ui', 'smoke', 'regression'],
            description: 'Which TestNG suite to run'
        )

        choice(
            name: 'ENV',
            choices: ['local', 'qa', 'staging'],
            description: 'Target environment'
        )

        booleanParam(
            name: 'HEADLESS',
            defaultValue: true,
            description: 'Run browser in headless mode'
        )
    }

    triggers {
        cron('H 2 * * *')
    }

    stages {

        stage('Checkout Source Code') {
            steps {
                git branch: params.BRANCH,
                    url: 'https://github.com/mohit23Codes/automation-mini-framework.git'
            }
        }

        stage('Build & Run Tests') {
            steps {
                bat """
                mvn clean test ^
                -Dsuite=${params.SUITE} ^
                -Denv=${params.ENV} ^
                -Dheadless=${params.HEADLESS}
                """
            }
        }
    }

    post {
        always {
            allure([
                includeProperties: false,
                results: [[path: 'target/allure-results']]
            ])
        }
    }
}
