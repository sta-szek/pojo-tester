#!/bin/bash

set -o errexit -o nounset

SOURCE_BRANCH="master"
TARGET_BRANCH="gh-pages"
TRAVIS_PULL_REQUEST="false"
TRAVIS_BRANCH="master"
POJO_TESTER_REPO="https://sta-szek:$TRAVIS_DEPLOY_GH_PAGES_TOKEN@github.com/sta-szek/pojo-tester.git"

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
gitbook build ./src/book/ ./build/website/ >/dev/null

echo "3/4 CLONE POJO-TESTER REPOSITORY"
cd ./build/website/
git init --quiet
git config user.name "Piotr Joński"
git config user.email "yoyo@wp.eu"

git remote add origin ${POJO_TESTER_REPO}
git fetch upstream --no-tags --quiet
git reset upstream/gh-pages --quiet

echo "4/4 PUBLISH PAGES"
git add -A . >/dev/null 2>/dev/null
git commit -m "rebuild pages at ${rev}" >/dev/null 2>/dev/null
git push --quiet upstream HEAD:gh-pages