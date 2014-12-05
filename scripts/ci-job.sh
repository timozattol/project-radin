#!/bin/sh -x

set -x

PROJECT_NAME=ProjectRadinAndroidApp
TEST_PROJECT=$PROJECT_NAME/tests

export ANDROID_HOME=/home/jenkins/tools/android-sdk
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
export REPO_DIR=/home/jenkins/workspace/2014-sweng-team-project-radin
export SCRIPTS_DIR=$REPO_DIR/scripts

# Run android tests
ant_cmd="ant clean emma debug install test"
cd $REPO_DIR
android update project --path $PROJECT_NAME --target android-19
android update test-project --path $TEST_PROJECT --main ..
cd $REPO_DIR/$TEST_PROJECT
# The following dirty hack is due to the fact that ant returns 0 EVEN IF TESTS FAIL
# But if something else goes wrong, then it returns 1
echo "Running $ant_cmd"
result=`$ant_cmd 2>&1 | tee /dev/stderr`
status="$?"
result=`echo "$result" | grep "FAIL"`
cd $REPO_DIR
if [ ! -z "$result" ] || [ ! "$status" -eq 0 ]; then
  echo "Errors occured while testing android app"
  exit 1
fi
echo "Mooooooooh"
exit 0