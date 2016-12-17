node{
  stage("checkout"){
   checkout scm
  }
  stage("la all"){
     sh "ls -al"
    }
  stage("assmeble"){
    sh "./gradlew assemble"
  }
  stage("build"){
    sh "./gradlew build -x check"
  }
  stage("test"){
    sh "./gradlew check"
  }
}
