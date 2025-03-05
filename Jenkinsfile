pipeline {
    agent any
    tools {
        git 'Git 2.43' // Replace 'Git 2.43' with the name you gave to your Git tool configuration
        maven 'Maven 3.7' // Ensure Maven is also properly configured
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Amuro90/plants.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn  clean package -Dmaven.test.skip=true'  // Adjust based on your build tool
            }
        }

        stage('Deploy') {
            steps {
                sshagent(['credenzialigiuste']) {  // Use Jenkins credentials ID
                    sh '''
                    scp target/*.jar root@192.168.1.100:/home/app.jar

                    '''
                }
            }
        }
    }
}
