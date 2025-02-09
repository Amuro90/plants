pipeline {
    agent any
    
    tools {
        jdk 'jdk17'
        maven 'maven3'
    }
    
    environment {
        REMOTE_USER = 'your-username'
        REMOTE_HOST = 'your-bpi-m2-zero-ip'
        APP_DIR = '/opt/springapp'
        JAR_NAME = 'your-artifact-name-0.0.1-SNAPSHOT.jar'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Deploy to BPI') {
            steps {
                script {
                    // Create deployment directory
                    sh """
                        ssh ${REMOTE_USER}@${REMOTE_HOST} '
                            mkdir -p ${APP_DIR}
                        '
                    """
                    
                    // Copy JAR file
                    sh """
                        scp target/${JAR_NAME} ${REMOTE_USER}@${REMOTE_HOST}:${APP_DIR}/app.jar
                    """
                    
                    // Create or update service file
                    sh """
                        ssh ${REMOTE_USER}@${REMOTE_HOST} '
                            echo "[Unit]
                            Description=Spring Boot Application
                            After=network.target

                            [Service]
                            User=${REMOTE_USER}
                            WorkingDirectory=${APP_DIR}
                            ExecStart=java -jar ${APP_DIR}/app.jar
                            SuccessExitStatus=143
                            TimeoutStopSec=10
                            Restart=on-failure
                            RestartSec=5

                            [Install]
                            WantedBy=multi-user.target" | sudo tee /etc/systemd/system/springapp.service
                            
                            sudo systemctl daemon-reload
                            sudo systemctl restart springapp
                            sudo systemctl enable springapp
                        '
                    """
                }
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
