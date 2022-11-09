import groovy.json.JsonSlurperClassic

def jsonParse(def json) {
    new groovy.json.JsonSlurperClassic().parseText(json)
}
pipeline {
    agent any
    stages {

        stage("Paso 1: Compliar"){
            steps {
                script {
                sh "echo 'Compile Code!'"
                // Run Maven on a Unix agent.
                sh "./mvnw clean compile -e"
                }
            }
        }
        stage("Paso 2: Testear"){
            steps {
                script {
                sh "echo 'Test Code!'"
                // Run Maven on a Unix agent.
                sh "./mvnw clean test -e"
                }
            }
        }
        stage("Paso 3: Build .Jar"){
            steps {
                script {
                sh "echo 'Build .Jar!'"
                // Run Maven on a Unix agent.
                sh "./mvnw clean package -e"
                }
            }
        }
        stage("Paso 4: Spring-boot run"){
            steps {
                script {
                sh "echo 'spring-boot run'"
                // Run Maven on a Unix agent.
                sh "nohup bash mvnw spring-boot:run &"
                //sh "./mvnw spring-boot:run"
                }
            }
        }
        stage("Paso 5: Testing App"){
            steps {
                script {
                sh "echo 'Testing Application'"
                retry(3){
                    sleep(60)
                    sh "curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing'"
                    sh "curl -X GET 'http://localhost:8081/rest/mscovid/estadoMundial'"
                }
                }
            }
        }
    }
    post {
        always {
            sh "echo 'fase always executed post'"
        }
        success {
            sh "echo 'fase success'"
        }

        failure {
            sh "echo 'fase failure'"
        }
    }
}