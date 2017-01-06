pipeline {
    environment {
        SONARQUBE_TOKEN = credentials('SONARQUBE_TOKEN')
        BINTRAY_API_KEY = credentials('BINTRAY_API_KEY')
        GIT_ASKPASS = credentials('0eeac3bc-28c1-452e-b0dc-671f49c8ebf1')
    }
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
                sh "git config --global credential.helper cache"
                sh "./gradlew assemble testClasses"
            }
        }
        stage("Test") {
            steps {
                sh "./gradlew check"
            }
        }
        stage("QA") {
            steps {
                sh "./gradlew sonarqube -Dsonar.host.url=https://sonarqube.com -Dsonar.login=${env.SONARQUBE_TOKEN}"
            }
        }

        stage("Publish release") {
            when {
                env.BRANCH_NAME == "master" && env.RELEASE == "true" && env.RELEASEVERSION != "" && env.NEWVERSION != ""
            }
            steps {
                sh "git checkout -f master"
                sh "git config --global push.default simple"
                sh "git remote remove origin"
                sh "git remote add origin https://${env.GIT_ASKPASS}@github.com/sta-szek/pojo-tester.git"
                sh "sed -i 's|version=.*|version=${env.RELEASEVERSION}|g' gradle.properties"
                sh "git tag ${env.RELEASEVERSION}"
                sh "./gradlew clean build bintrayUpload -x check"
                sh "sed -i 's|version=.*|version=${env.NEWVERSION}|g' gradle.properties"
                sh "git add gradle.properties"
                sh "git commit -m 'Next development version ${env.NEWVERSION}'"
                sh "git push --set-upstream origin ${env.RELEASEVERSION}"
                sh "git push --set-upstream origin master"

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
