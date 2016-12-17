node{
  stage("checkout"){
   checkout scm
  }
  stage("assmeble"){
    sh "./gradlew assemble"
  }
  stage("test"){
    sh "./gradlew check"
  }
}
