pipeline {
    agent any
    stages {
        stage('Java Version') {
            steps {
                bat 'mvn -version'
            }
        }
        stage('Build') {
            steps {
                bat 'mvn compile'
            }
        }
        stage('Static Analysis') {
            steps {
                bat 'mvn checkstyle:check'
            }
        }
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
        stage('Package') {
            steps {
                bat 'mvn package -DskipTests'
            }
        }
    }
}
