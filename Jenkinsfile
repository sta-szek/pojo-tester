pipeline {
    environment {
        BINTRAY_API_KEY = credentials("BINTRAY_API_KEY")
        SONARQUBE_TOKEN = credentials("SONARQUBE_TOKEN")
        GIT_ASKPASS = credentials("GIT_ASKPASS")
        VER = "0.7.3"
    }
    agent any
    parameters {
        booleanParam(defaultValue: false, description: 'Build and deploy gitbook pages?', name: 'deployPages')
        booleanParam(defaultValue: false, description: 'Publish new release from this build?', name: 'release')
        string(defaultValue: "", description: 'New release version', name: 'releaseVersion')
        string(defaultValue: "-SNAPSHOT", description: 'New release development', name: 'newVersion')
    }
    stages {
        stage("Build") {
            steps {
                sh "git config --global credential.helper cache"
                sh "./gradlew assemble testClasses integrationTestClasses"
            }
        }
        stage("Unit Test") {
            steps {
                sh "./gradlew test"
            }
        }
        stage("Integration Test") {
            steps {
                sh "./gradlew integrationTest"
            }
        }
        stage("QA") {
            when {
                expression {
                    return env.RELEASE != "true"
                }
            }
            steps {
                sh "./gradlew junit5CodeCoverageReport"
                sh "./gradlew sonarqube -Dsonar.host.url=https://sonarqube.com -Dsonar.login=${env.SONARQUBE_TOKEN} | grep -v 'Class not found:'"
                publishHTML target: [
                        allowMissing         : false,
                        alwaysLinkToLastBuild: false,
                        keepAll              : true,
                        reportDir            : 'build/reports/jacoco',
                        reportFiles          : 'index.html',
                        reportName           : 'Coverage report'
                ]
            }
        }
        stage("Deploy pages") {
            when {
                expression {
                    boolean publish = false
                    if (env.DEPLOYPAGES == "true") {
                        return true
                    }
                    try {
                        timeout(time: 1, unit: 'MINUTES') {
                            input 'Deploy pages?'
                            publish = true
                        }
                    } catch (final ignore) {
                        publish = false
                    }
                    return publish
                }
            }
            steps {
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
        stage("Publish release to bintray") {
            when {
                expression {
                    return env.BRANCH_NAME == "master" && env.RELEASE == "true" && env.RELEASEVERSION != "" && env.NEWVERSION != ""
                }
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
            }
            post {
                success {
                    rocketSend channel: 'pojo-tester',
                            rawMessage: true,
                            avatar: 'http://ci.pojo.pl/static/be09d97b/images/headshot.png',
                            message: "@all: *pojo-tester ${env.RELEASEVERSION} released!* \n  https://bintray.com/sta-szek/maven/pojo-tester/_latestVersion \n"
                }
            }
        }
    }
    post {
        always {
            deleteDir()
        }
    }
}

