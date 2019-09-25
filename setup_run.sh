#!/bin/bash

# ### ENVIRONMENTAL VARIABLE SUPPORT: ###
# QA_CRYSTAL_TEST_BROWSER
# QA_CRYSTAL_TEST_ISHEADLESS
# QA_CRYSTAL_TEST_ISREMOTE
# QA_CRYSTAL_TEST_REMOTEIP
# QA_CRYSTAL_TEST_REMOTEPORT
# QA_CRYSTAL_TEST_ISPROXY
# QA_CRYSTAL_TEST_ENV

# ### ENVIRONMENT SUPPORT: ###
# PROD:    https://
# STAGE:   https://
# TEST:    https://
# DEV:     https://
# BRANCH:  https://

# ### BROWSER SUPPORT: ###
# chrome
# firefox
# phantomjs
# safari
# internetexplorer
# edge

# ### INSTRUCTIONS: ###
# 1. be sure to set all the necessary env variables
# 2. Set the specified values below including browser, isRemote, remoteIP, remotePort, isProxy, outputFile and env
# 3. Execute the script: sh setup.run.sh
# 4. Your built xml file will be ready to use based on the build_template.xml

########################################################################################
########################################################################################
########################################################################################

browser="chrome"                              # which browser to use?
isHeadless="false"                            # run the browser in headless mode?
isRemote="true"                               # is invoke selenium grid?
remoteIP="127.0.0.1"                          # will work only if isRemote equals true
remotePort="4444"                             # will work only if isRemote equals true
isProxy="false"                               # is use proxy server?
outputFile="build.xml"                        # output build file
env="https://www.armondsarkisian.com"         # defaults to PROD environment

########################################################################################
########################################################################################
########################################################################################

# determine the browser (if specified via external env var otherwise set to default)
if [ ! -z "$QA_CRYSTAL_TEST_BROWSER" ]; then
    browser="$QA_CRYSTAL_TEST_BROWSER"
fi

# determine if isheadless (if specified via external env var otherwise set to default)
if [ ! -z "$QA_CRYSTAL_TEST_ISHEADLESS" ]; then
    isHeadless="$QA_CRYSTAL_TEST_ISHEADLESS"
fi

# determine if isremote (if specified via external env var otherwise set to default)
if [ ! -z "$QA_CRYSTAL_TEST_ISREMOTE" ]; then
    isRemote="$QA_CRYSTAL_TEST_ISREMOTE"
fi

# determine the remoteip (if specified via external env var otherwise set to default)
if [ ! -z "$QA_CRYSTAL_TEST_REMOTEIP" ]; then
    remoteIP="$QA_CRYSTAL_TEST_REMOTEIP"
fi

# determine the remoteport (if specified via external env var otherwise set to default)
if [ ! -z "$QA_CRYSTAL_TEST_REMOTEPORT" ]; then
    remotePort="$QA_CRYSTAL_TEST_REMOTEPORT"
fi

# determine if isproxy (if specified via external env var otherwise set to default)
if [ ! -z "$QA_CRYSTAL_TEST_ISPROXY" ]; then
    isProxy="$QA_CRYSTAL_TEST_ISPROXY"
fi

# determine the environment (if specified via external env var otherwise set to default)
if [ ! -z "$QA_CRYSTAL_TEST_ENV" ]; then
    env="$QA_CRYSTAL_TEST_ENV"
fi

########################################################################################
########################################################################################
########################################################################################

env="$(echo $env | sed 's/\//\\\//g')"
workingDir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

driverPathOther="$(echo ${workingDir} | sed 's/\//\\\//g')"
# TODO: fix win path
driverPathWin=""

# running on mac
if [ "$(uname)" == "Darwin" ]; then

	cat build_template.xml | \
	sed "s/__driverPathOther__/$driverPathOther/g" | \
	sed "s/__driverPathWin__/$driverPathWin/g" | \
	sed "s/crystal_framework/crystal_framework\/drv/g" | \
	sed "s/__environment__/$env/g" | \
	sed "s/__browser__/$browser/g" | \
	sed "s/__isHeadless__/$isHeadless/g" | \
	sed "s/__isRemote__/$isRemote/g" | \
	sed "s/__remoteIP__/$remoteIP/g" | \
	sed "s/__remotePort__/$remotePort/g" | \
	sed "s/__isProxy__/$isProxy/g" > $outputFile

    echo "Running on MacOS"
	echo "Mac Driver Path: $driverPathOther"
else
    # TODO: This is temporary. It will have support for Windows in the future
    echo "Running on Unknown OS -- not supported"
    exit 0;
fi

# display configuration
echo ""

echo "Environment: $env"
echo "Browser: $browser"
echo "Headless: $isHeadless"

echo "Is Remote: $isRemote"
if [ $isRemote == "true" ]; then
	echo "Remote IP: $remoteIP"
	echo "Remote Port: $remotePort"
fi

echo "Is Proxy: $isProxy"

echo "Output File: $outputFile"

echo ""
echo "Congratulations! You are ready to begin testing with $outputFile on $env"
echo ""

########################################################################################
########################################################################################
########################################################################################
