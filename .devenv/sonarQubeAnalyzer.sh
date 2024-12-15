#!/bin/bash

###########################################################
# SonarQube Script for Multi-Module Maven Project
# NOTE: This script can be run only with JDK lower than 11.0.17
#
# Features:
# - Runs SonarQube analysis for each Maven module separately.
# - Only analyzes modules with 'target/classes' and 'jacoco.xml'
# - Dynamically determines paths for 'jacoco.xml' and 'classes' directories per module.
# - Executes SonarQube analysis using Maven ('mvn sonar:sonar') available at SONARQUBE_URL
#
# Usage:
# - It's imperative to run `mvn clean install -Dsurefire.java.args=` for all jacoco.xml reports to generate properly.
# - (Check) Ensure proper amount of jacoco.xml being generated with `find ./ -type f -name 'jacoco.xml' 2>/dev/null`
# - Ensure Maven and SonarQube server configurations are correctly set (execute: `docker run -d --name sonarqube -p 9000:9000 sonarqube`).
# - Check that the sonarqube has finished the startup, just logging in it (the default user/password are admin/admin)
# - Script must be run from the root path `./.devenv/sonarQubeAnalyzer.sh` (it must be executable with chmod +x)
# - Script parameters like URL, login, and password are adjustable.
###########################################################

SONARQUBE_URL="http://localhost:9000"
SONARQUBE_LOGIN="admin"
SONARQUBE_PASSWORD="admin1"
SCRIPT_DIR="$(dirname $(realpath $0))"
MY_PROJECT_ROOT="$SCRIPT_DIR/.."

run_sonar_scanner_for_module() {
    local module_dir=$1
    local module_name=$(basename "$module_dir")
    local classes_path="$module_dir/target/classes"
    local xml_path

    # The adapters module is a special case of code coverage because the tests code reside in the functional-tests module.
    # So the jacoco files generated inside the functional test module should be used for the adapter module in the sonarqube analysis
    # TODO check existence adapters absolute path
    if [[ "$module_name" == "adapters" ]]; then
        xml_path="../functional-tests/target/site/jacoco/jacoco.xml"
        local absolute_xml_path="$(realpath "$module_dir/$xml_path")"
        echo "Special case for adapters module. XML Path: $xml_path"
        if [[ ! -f "$absolute_xml_path" ]]; then
            echo "jacoco.xml not found for adapters module. Skipping analysis."
            exit 1
        fi
    else
        xml_path=$(find "$module_dir" -path "*/target/site/jacoco/jacoco.xml" -print)
        echo "XML Path for $module_name module: $xml_path"
    fi

    echo "Running SonarQube analysis for module: $module_name"

    if [[ -d "$classes_path" ]]; then
        mvn sonar:sonar \
        -f "$module_dir/pom.xml" \
        -Dsonar.projectKey="$module_name" \
        -Dsonar.projectName="$module_name" \
        -Dsonar.host.url="$SONARQUBE_URL" \
        -Dsonar.login="$SONARQUBE_LOGIN" \
        -Dsonar.password="$SONARQUBE_PASSWORD" \
        -Dsonar.coverage.jacoco.xmlReportPaths="$xml_path" \
        -Dsonar.java.binaries="$classes_path"
    else
        echo "Skipping module: $module_name - Missing target/classes"
    fi
}

echo "JAVA_VERSION: $(java -version 2>&1 | head -n 1)"
echo "REMOVING SONAR CACHE ..."

# Recursively find and iterate over Maven module directories
find "$MY_PROJECT_ROOT" -name "pom.xml" -not -path "$MY_PROJECT_ROOT/pom.xml" | while read module_pom; do
    module_dir=$(dirname "$module_pom")
    rm -rf "$module_dir/target/.scannerwork"
    run_sonar_scanner_for_module "$module_dir"
done
