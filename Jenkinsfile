node {
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
