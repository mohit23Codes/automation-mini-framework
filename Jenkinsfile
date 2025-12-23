pipeline {
    agent any
    
    parameters {
		
		choice(
			
			name: 'SUITE',
			choices: ['ui', 'smoke', 'regression'],
			description: 'Which TestNG Suite to run'
		)
		
		choice(
			
			name: 'ENV',
			choices: ['local', 'qa', 'staging'],
			description: 'Target Environment'
		)
		
		choice(
			
			name: 'HEADLESS',
			defaultValue: true,
			description: 'Run browser in headless mode'
		)
	}

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean test'
            }
        }
    }
}
