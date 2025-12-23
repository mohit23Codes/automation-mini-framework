pipeline {
    agent any

    parameters {
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

    stages {

        stage('Checkout SCM') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
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
}
