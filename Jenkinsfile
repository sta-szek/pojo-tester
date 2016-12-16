node{
  stage("checkout"){
    checkout scm
  }
  stage("asseble"){
    sh "./gradlew assemble"
  }
  stage("build"){
    sh "./gradlew build -x check"
  }
  stage("test"){
    sh "./gradlew check"
  }
}
