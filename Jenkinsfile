pipeline {
    agent any
    parameters {
        booleanParam(defaultValue: false, description: 'Publish new release from this build?', name: 'release')
        stringParam(defaultValue: "", description: 'New release version', name: 'releaseVersion')
        stringParam(defaultValue: "-SNAPSHOT", description: 'New release development', name: 'newVersion')
    }
    stages {
        stage("Build") {
            steps {
                sh "env"
                sh "./gradlew assemble testClasses"
            }
        }
        stage("Test") {
            steps {
                sh "./gradlew check"
            }
        }

        stage("Publish release") {
            when {
                env.BRANCH_NAME == "master" && env.RELEASE == "true" && env.RELEASEVERSION != "" && env.NEWVERSION != ""
            }
            steps {
                sh "./gradlew release -Prelease.useAutomaticVersion=true -Prelease.releaseVersion=${releaseVersion} -Prelease.newVersion=${newVersion}"
                sh "./gradlew bintrayUpload"
                sh "./gradlew javadoc >/dev/null"
                sh "rm -rf ./src/book/javadoc"
                sh "mv ./build/docs/javadoc ./src/book/javadoc"
                sh "gitbook install ./src/book/"
                sh "gitbook build ./src/book/ ./repo"
                sh "git --work-tree=repo/ --git-dir=repo/.git init"
                sh "git --work-tree=repo/ --git-dir=repo/.git config user.name 'jenkins'"
                sh "git --work-tree=repo/ --git-dir=repo/.git config user.email 'jenkins@ci.pojo.pl'"
                sh "git --work-tree=repo/ --git-dir=repo/.git remote add origin git@github.com:sta-szek/pojo-tester.git"
                sh "git --work-tree=repo/ --git-dir=repo/.git fetch -q -n origin"
                sh "git --work-tree=repo/ --git-dir=repo/.git reset -q origin/gh-pages"
                sh "git --work-tree=repo/ --git-dir=repo/.git add -A ."
                sh "git --work-tree=repo/ --git-dir=repo/.git commit -m 'Rebuild pojo-tester pages by jenkins'"
                sh "git --work-tree=repo/ --git-dir=repo/.git push origin HEAD:gh-pages"
            }
        }
    }
}
