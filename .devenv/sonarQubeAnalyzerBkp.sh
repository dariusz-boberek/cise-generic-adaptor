#!/bin/bash

###########################################################
# SonarQube Script -  CLI Backup implementation
# WARNING: this backup version is not splitting maven modules into separate sonarqube projects
#     This mean we will have issues generating reports for plugins (classpath issues)
#
# Requires SonarQube CLI installation.
#
# Initial installation and configuration:
#   `sudo apt-get install unzip`
#   `wget https://binaries.sonarsource.com/Distribution/sonar-scanner-cli/sonar-scanner-cli-4.6.2.2472-linux.zip`
#   `unzip sonar-scanner-cli-4.6.2.2472-linux.zip`
#   `sudo mv sonar-scanner-4.6.2.2472-linux /opt/sonar-scanner`
#
# Configure sonar-scanner:
#    `nano /opt/sonar-scanner/conf/sonar-scanner.properties`
#    Add `sonar.host.url=${SONARQUBE_URL}` (for example "http://localhost:9000")
#
# - it's imperative to run `mvn clean install -Dsurefire.java.args=` for all jacoco.xml reports to generate properly.
#   Ensure proper amount of jacoco.xml beeing generated with `find ./ -type f -name 'jacoco.xml' 2>/dev/null`
###########################################################

# Define constants
SONARQUBE_URL="http://localhost:9000"
SONARQUBE_LOGIN="admin"
SONARQUBE_PASSWORD="admin1"
SONARQUBE_PROJECT_KEY="CISEGenericAdaptorBkp"
SONARQUBE_PROJECT_NAME="CISEGenericAdaptorBkp"
SCRIPT_DIR="$(dirname $(realpath $0))"
MY_PROJECT_ROOT="$SCRIPT_DIR/.."

# Define function to run sonar-scanner
run_sonar_scanner() {

# Gather all jacoco.xml paths and convert array to comma-separated strings
xml_paths_string=$(find $MY_PROJECT_ROOT -name "jacoco.xml" -print | paste -sd "," -)

# Define the paths to the class directories and convert array to comma-separated strings
classes_paths_string=$(find $MY_PROJECT_ROOT -type d -name "classes" -print | paste -sd "," -)

/opt/sonar-scanner/bin/sonar-scanner \
-Dsonar.projectKey=$SONARQUBE_PROJECT_KEY \
-Dsonar.projectName=$SONARQUBE_PROJECT_NAME \
-Dsonar.host.url=$SONARQUBE_URL \
-Dsonar.login=$SONARQUBE_LOGIN \
-Dsonar.password=$SONARQUBE_PASSWORD \
-Dsonar.coverage.jacoco.xmlReportPaths=$xml_paths_string \
-Dsonar.sources=$MY_PROJECT_ROOT \
-Dsonar.java.binaries=$classes_paths_string
}

echo "SONARQUBE_SCANNER_VERSION: $(/opt/sonar-scanner/bin/sonar-scanner --version)"
echo "JAVA_VERSION: $(java -version 2>&1 | head -n 1)"
echo "REMOVING SONAR CACHE ..."
rm -rf "$MY_PROJECT_ROOT/.scannerwork"
run_sonar_scanner
