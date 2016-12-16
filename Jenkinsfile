node{
  stage("checkout"){
    checkout scm
  }
  stage("asseble"){
    sh "./gradlew assemble"
  }
  stage("build"){
    sh "./gradlew builx -x check"
  }
  stage("build"){
    sh "./gradlew check"
  }
}
