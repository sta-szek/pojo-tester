#!/bin/bash

set -o errexit -o nounset

SOURCE_BRANCH="master"
TARGET_BRANCH="gh-pages"

POJO_TESTER_REPO="https://$TRAVIS_DEPLOY_GH_PAGES_TOKEN:x-oauth-basic@github.com/sta-szek/pojo-tester.git"
echo "TRAVIS_PULL_REQUEST=$TRAVIS_PULL_REQUEST"
echo "TRAVIS_BRANCH=$TRAVIS_BRANCH"

if [[ "$TRAVIS_PULL_REQUEST" != "false" ]]
then
  echo "This is a pull request. No deploy!"
  exit 0
fi

if [[ "$TRAVIS_BRANCH" != "$SOURCE_BRANCH" ]]
then
  echo "This commit was made against the $TRAVIS_BRANCH and not the $SOURCE_BRANCH! No deploy!"
  exit 0
fi
REV=$(git rev-parse --short HEAD)

echo "1/4 INSTALL GITBOOK-CLI"
npm install gitbook-cli


echo "2/4 GENERATE JAVADOCS"
./gradlew javadoc >/dev/null

echo "3/4 GENERATE GITBOOK"
gitbook install ./src/book/ >/dev/null
gitbook build ./src/book/ ./repo

echo "4/4 PUBLISH PAGES"
cd repo
git init >/dev/null
git config user.name "Piotr Joński"
git config user.email "yoyo@wp.eu"
git remote add origin ${POJO_TESTER_REPO}
git fetch -q -n origin
git reset -q origin/gh-pages

git add -A .
git commit -m "Rebuild pojo-tester pages at ${REV}" >/dev/null
git push origin HEAD:gh-pages