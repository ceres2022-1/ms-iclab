
pipeline {
    agent any
    environment {        
        NEXUS_PASSWORD     = credentials('nexus-password')
    }
    stages {
        stage('Paso 1: Compliar') {
            steps {
                script {
                    env.channel = 'C04B17VE0JH'
                    env.STAGE = 'Paso 1: Compliar'
                    sh "echo 'Compile Code!'"
                    // Run Maven on a Unix agent.
                    sh './mvnw clean compile -e'
                }
            }
        }
        stage('Paso 2: Testear') {
            steps {
                script {
                    env.STAGE = 'Paso 2: Testear'
                    sh "echo 'Test Code!'"
                    // Run Maven on a Unix agent.
                    sh './mvnw clean test -e'
                }
            }
        }
        stage('Paso 3: Build .Jar') {
            steps {
                script {
                    env.STAGE = 'Paso 3: Build .Jar'
                    sh "echo 'Build .Jar!'"
                    // Run Maven on a Unix agent.
                    sh './mvnw clean package -e'
                }
            }
        }
        stage('Paso 4: Spring-boot run') {
            steps {
                script {
                    env.STAGE = 'Paso 4: Spring-boot run'
                    sh "echo 'spring-boot run'"
                    // Run Maven on a Unix agent.
                    sh 'nohup bash mvnw spring-boot:run &'
                }
            }
        }
        // stage('Paso 5: Análisis SonarQube') {
        //     steps {
        //         script {
        //             env.STAGE = 'Paso 5: Análisis SonarQube'
        //         }
        //         withSonarQubeEnv('sonarqube') {
        //             sh "echo 'Calling sonar Service in another docker container!'"
        //             // Run Maven on a Unix agent to execute Sonar.
        //             sh './mvnw clean verify sonar:sonar -Dsonar.projectKey=lab-m4-equipo1 -Dsonar.projectName=lab-m4-equipo1'
        //         }
        //     }
        // }
        stage('Paso 6: Subir Artefacto a Nexus') {
            steps {
                script {
                    env.STAGE = 'Paso 6: Subir Artefacto a Nexus'
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
        stage('Paso 7: Descargar Nexus') {
            steps {
                script {
                    sh 'curl -X GET -u admin:$NEXUS_PASSWORD "http://nexus:8081/repository/maven-usach-ceres/com/devopsusach2020/DevOpsUsach2020/0.0.1/DevOpsUsach2020-0.0.1.jar" -O'
                }
            }
        }
        stage('Paso 8: Levantar Artefacto Jar en server Jenkins') {
            steps {
                script {
                    sh 'nohup java -jar DevOpsUsach2020-0.0.1.jar & >/dev/null'
                }
            }
        }
        stage('Paso 9: Testear Artefacto - Dormir(Esperar 20sg) ') {
            steps {
                script {
                    sh "sleep 20 && curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing'"
                }
            }
        }
        stage('Paso 10:Detener Atefacto jar en Jenkins server') {
            steps {
                sh '''
                    echo 'Process Java .jar: ' $(pidof java | awk '{print $1}')
                    sleep 20
                    kill -9 $(pidof java | awk '{print $1}')
                '''
            }
        }
    }
    post {
        always {
            sh "echo 'fase always executed post'"
        }
        // success {
        //     slackSend color: 'good',
        //     message: "[Grupo 1] [${JOB_NAME}] [Rama: [${env.BRANCH_NAME}]] [Stage: [${env.STAGE}]] [Resultado: Éxito/Success]",
        //     teamDomain: 'devopsusach20-lzc3526',
        //     channel: "${env.channel}"
        // }
        // failure {
        //     slackSend color: 'danger',
        //     message: "[Grupo 1] [${JOB_NAME}] [Rama: [${env.BRANCH_NAME}]] [Stage: [${env.STAGE}]] [Resultado: Error/Fail]",
        //     teamDomain: 'devopsusach20-lzc3526',
        //     channel: "${env.channel}"
        // }
    }
}
