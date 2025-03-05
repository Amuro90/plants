pipeline {
    agent any

    tools {
        git 'Git 2.43' // Replace 'Git 2.43' with the name you gave to your Git tool configuration
        maven 'Maven 3.8.7' // Ensure Maven is also properly configured
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Amuro90/plants.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -Dmaven.test.skip=true'  // Adjust based on your build tool
            }
        }

        stage('Deploy') {
            steps {
                withCredentials([sshUserPrivateKey(credentialsId: 'root', keyFileVariable: 'KEYFILE', usernameVariable: 'SSH_USER')]) {
                    sh '''
                        /var/lib/jenkins/workspace/deploy.sh
                    '''
                }
            }
        }
    }

    post {
        always {
            echo 'Cleaning up...'
            // Add any necessary cleanup steps here
        }
    }
}
