#!/bin/bash

set -o errexit -o nounset

TRAVIS_PULL_REQUEST="false"
TRAVIS_BRANCH="master"

SOURCE_BRANCH="master"
TARGET_BRANCH="gh-pages"
POJO_TESTER_REPO="https://$TRAVIS_DEPLOY_GH_PAGES_TOKEN:x-oauth-basic@github.com/sta-szek/pojo-tester.git"

if [ "$TRAVIS_PULL_REQUEST" != "false" -o "$TRAVIS_BRANCH" != "$SOURCE_BRANCH" ]
then
  echo "This commit was made against the $TRAVIS_BRANCH and not the $SOURCE_BRANCH! No deploy!"
  exit 0
fi


rev=$(git rev-parse --short HEAD)

echo "1/4 GENERATE JAVADOCS"
./gradlew javadoc >/dev/null

echo "2/4 GENERATE GITBOOK"
gitbook install ./src/book/ >/dev/null
gitbook build ./src/book/ ./repo

echo "3/4 PUBLISH PAGES"
cd repo
git init
git config user.name "Piotr Joński"
git config user.email "yoyo@wp.eu"
git remote add origin ${POJO_TESTER_REPO}
git fetch origin
git reset origin/gh-pages

git remote -v
git status -s
ls -al

git add -A .
git status -s
git commit -m "Rebuild pojo-tester pages at ${rev}" >/dev/null
pwd
git status -s
git push ${POJO_TESTER_REPO} HEAD:gh-pages