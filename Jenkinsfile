pipeline {
    agent any
    environment {
        NEW_VERSION = '1.3.0'
        SERVER_CREDENTIALS = credentials('server-credentials')
        EMAIL_TO = 'bkacha@yahoo.com'
    }

    stages {
        stage('Build'){
            steps {
                echo 'Building an application.'
                echo "Building version ${NEW_VERSION}"
                emailext body: 'Check console output at $BUILD_URL to view the results.',
                       to: "${EMAIL_TO}",
                  subject: 'Jenkins build is back to normal: $PROJECT_NAME - #$BUILD_NUMBER'
            }
        }
        stage('Test'){
            when {
                expression {
                    env.BRANCH_NAME == 'dev'
                }
            }
            steps {
                echo 'Testing an application in DEV.'
            }
        }
        stage('Deploy'){
            steps {
                echo 'Deploying an application.'
                echo "Deploying an ${SERVER_CREDENTIALS}."
                withCredentials([
                    usernamePassword(credentials: 'git-credentials', usernameVariable: 'USER', passwordVariable: 'PWD')
                ]) {
                      echo 'some script ${USER} , ${PWD}'
                }
            }
        }
    }

    post {
        always {
            echo "I'm done..."
            emailext body: 'Check console output at $BUILD_URL to view the results.',
                       to: "${EMAIL_TO}",
                  subject: 'Jenkins build is back to normal: $PROJECT_NAME - #$BUILD_NUMBER'
        }
        success {
            echo 'Success!!'
        }
        failure {
            echo 'Failure'
        }
    }
}