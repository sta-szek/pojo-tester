//node {
//    def BRANCH_TO_PUBLISH_PAGES = "master"
//    stage("checkout") {
//        checkout scm
//    }
//    stage("assmeble") {
//        sh "./gradlew assemble testClasses"
//    }
//    stage("test") {
//        sh "./gradlew check"
//    }
//    if (env.BRANCH_NAME == BRANCH_TO_PUBLISH_PAGES) {
//        stage("deploy pages") {
//
//        }
//    }
//}

pipeline {
    agent any
    stages {
        def BRANCH_TO_PUBLISH_PAGES = "master"
        stage("checkout") {
            checkout scm
        }
        stage("assmeble") {
            sh "./gradlew assemble testClasses"
        }
        stage("test") {
            sh "./gradlew check"
        }
    }
}
