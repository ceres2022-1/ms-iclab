import groovy.json.JsonSlurperClassic

def jsonParse(def json) {
    new groovy.json.JsonSlurperClassic().parseText(json)
}

pipeline {
    agent any
    stages {
        stage('Paso 1: Compliar') {
            steps {
                script {
                    sh "echo 'Compile Code!'"
                    // Run Maven on a Unix agent.
                    sh './mvnw clean compile -e'
                }
            }
        }
        stage('Paso 2: Testear') {
            steps {
                script {
                    sh "echo 'Test Code!'"
                    // Run Maven on a Unix agent.
                    sh './mvnw clean test -e'
                }
            }
        }
        stage('Paso 3: Build .Jar') {
            steps {
                script {
                    sh "echo 'Build .Jar!'"
                    // Run Maven on a Unix agent.
                    sh './mvnw clean package -e'
                }
            }
        }
        stage('Paso 4: Spring-boot run') {
            steps {
                script {
                    sh "echo 'spring-boot run'"
                    // Run Maven on a Unix agent.
                    sh 'nohup bash mvnw spring-boot:run &'
                }
            }
        }
        stage('Paso 5: Análisis SonarQube') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh "echo 'Calling sonar Service in another docker container!'"
                    // Run Maven on a Unix agent to execute Sonar.
                    sh './mvnw clean verify sonar:sonar -Dsonar.projectKey=lab-m4-equipo1 -Dsonar.projectName=lab-m4-equipo1'
                }
            }
        }
         stage("Paso 6: Subir Artefacto a Nexus"){
            steps {
                script{
                    nexusPublisher nexusInstanceId: 'nexus',
                        nexusRepositoryId: 'maven-usach-ceres',
                        packages: [
                            [$class: 'MavenPackage',
                                mavenAssetList: [
                                    [classifier: '',
                                    extension: 'jar',
                                    filePath: 'build/DevOpsUsach2020-0.0.1.jar'
                                ]
                            ],
                                mavenCoordinate: [
                                    artifactId: 'DevOpsUsach2020',
                                    groupId: 'com.devopsusach2020',
                                    packaging: 'jar',
                                    version: '0.0.1'
                                ]
                            ]
                        ]
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
