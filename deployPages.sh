#!/bin/bash

set -o errexit -o nounset

TRAVIS_PULL_REQUEST="false"
TRAVIS_BRANCH="master"

SOURCE_BRANCH="master"
TARGET_BRANCH="gh-pages"
POJO_TESTER_REPO="https://$TRAVIS_DEPLOY_GH_PAGES_TOKEN@github.com/sta-szek/pojo-tester.git"

if [ "$TRAVIS_PULL_REQUEST" != "false" -o "$TRAVIS_BRANCH" != "$SOURCE_BRANCH" ]
then
  echo "This commit was made against the $TRAVIS_BRANCH and not the $SOURCE_BRANCH! No deploy!"
  exit 0
fi

echo "1/4 CLONE POJO-TESTER REPOSITORY"
rev=$(git rev-parse --short HEAD)

git clone ${POJO_TESTER_REPO} repo
cd repo
git checkout ${TARGET_BRANCH} || git checkout --orphan ${TARGET_BRANCH}
git remote add upstream ${POJO_TESTER_REPO}
git fetch upstream
git reset upstream/gh-pages
cd ..
rm -rf repo/**/* || exit 0

echo "2/4 GENERATE JAVADOCS"
./gradlew javadoc >/dev/null

echo "3/4 GENERATE GITBOOK"
gitbook install ./src/book/ >/dev/null
gitbook build ./src/book/ ./repo >/dev/null

echo "4/4 PUBLISH PAGES"
cd repo
git config user.name "Piotr Joński"
git config user.email "yoyo@wp.eu"
git add -A . >/dev/null
git commit -m "Rebuild pojo-tester pages at ${rev}" >/dev/null
pwd
git status -s
git log --graph
git push