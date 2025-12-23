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
