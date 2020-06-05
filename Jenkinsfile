pipeline {
    agent any
    environment {
        NEW_VERSION = '1.3.0'
        SERVER_CREDENTIALS = credentials('server-credentials')
    }

    stages {
        stage('Build'){
            steps {
                echo 'Building an application.'
                echo "Building version ${NEW_VERSION}"
            }
        }
        stage('Test'){
            when {
                expression {
                    env.BRANCH_NAME == 'dev'
                }
            }
            steps {
                echo 'Testing an application.'
            }
        }
        stage('Deploy'){
            steps {
                echo 'Deploying an application.'
                echo "Deploying an ${SERVER_CREDENTIALS}."
                withCredentials([
                    usernamePassword(credentials: 'server-credentials', usernameName: USER, passwordVariable: PWD])
                ]) {
                      echo 'some script ${USER} , ${PWD}'
                }
            )
        }
    }

    post {
        always {
            echo "I'm done..."
        }
        success {
            echo 'Success!!'
        }
        failure {
            echo 'Failure'
        }
    }
}