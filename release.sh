#!/bin/sh
# Bootstraps gradle task options for automatic releases

if [[ $# -lt 3 ]]
then
  echo "Usage: release.sh [Scope; valid are major,minor,patch] [Stage; valid are dev, alpha, beta, rc, final] [Track; valid are alpha, beta, production]"
  echo "Eg. command ./release minor beta alpha"
  exit
fi

SCOPE=$1
STAGE=$2
TRACK=$3

echo "Releasing..."

./gradlew release -Prelease.scope=$SCOPE -Prelease.stage=$STAGE -PuploadTrack=$TRACK

echo "Done!"