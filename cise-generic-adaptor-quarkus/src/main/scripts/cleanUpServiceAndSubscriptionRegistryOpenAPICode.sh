#!/bin/bash

# This script is used to generate the required classes to access the CISE Node APIs from the openapi.yaml files provided by the node
# Please refer to the docs/asciidoc/installation-setup.adoc document to see how to use it correctly
#


cd "${0%/*}"

function global_replacements() {
  for i in $(find $1 -type f -name \*.java); do
    echo "file: $i"
    sed -i "s|@GeneratedParam([^)]*)||g" ${i}
    sed -i "/@GeneratedMethod/d" ${i}
    sed -i "/@GeneratedClass(.*)/d" ${i}
    sed -i "/io.quarkiverse.openapi.generator.annotations/d" ${i}
  done
}

function api_replacements() {
  for i in $(find $1 -type f -name \*.java); do
    echo "file: $i"
    sed -i "/@Path(.*)/d" ${i}
    sed -i "/@RegisterRestClient(.*)/d" ${i}
    sed -i "/@ApplicationScoped/d" ${i}
    sed -i "/@GET/d" ${i}
    sed -i "/@POST/d" ${i}
    sed -i "/@DELETE/d" ${i}
    sed -i "/@PUT/d" ${i}
    sed -i "s|@QueryParam(.*)||g" ${i}
    sed -i "s|@PathParam(.*)||g" ${i}
    sed -i "/@Consumes(.*)/d" ${i}
    sed -i "/@Produces(.*)/d" ${i}
    sed -i "/org.eclipse.microprofile.rest.client.inject.RegisterRestClient/d" ${i}
    sed -i "/import javax.ws.rs./d" ${i}
    sed -i "/import javax.enterprise.context.ApplicationScoped/d" ${i}
    sed -i "s| public | |g" ${i}
  done
}

function model_replacements() {
  for i in $(find $1 -type f -name \*.java); do
    echo "file: $i"
    sed -i "/@Path(.*)/d" ${i}
    sed -i "/@RegisterRestClient(.*)/d" ${i}
    sed -i "/@ApplicationScoped/d" ${i}
    sed -i "/@GET/d" ${i}
    sed -i "/@POST/d" ${i}
    sed -i "/@DELETE/d" ${i}
    sed -i "/@PUT/d" ${i}
    sed -i "/@javax.ws.rs.QueryParam(.*)/d" ${i}
    sed -i "/@Consumes(.*)/d" ${i}
    sed -i "/@Produces(.*)/d" ${i}
    sed -i "/org.eclipse.microprofile.rest.client.inject.RegisterRestClient/d" ${i}
    sed -i "/import javax.ws.rs./d" ${i}
    sed -i "/import javax.enterprise.context.ApplicationScoped/d" ${i}
  done
}

function remove_javadoc() {
  for i in $(find $1 -type f -name \*.java); do
    echo "file: $i"
    sed -i "/\/\*\*/,/\*\//d" ${i}
  done
}

# Service Regisry

generated_dir_service_registry="../../../target/generated-sources/open-api-yaml/eu/cise/adaptor/core/servicehandler/port/out/service_registry_openapi_yaml"
dest_dir_service_registry="../../../../cise-generic-adaptor-core/service-handler/src/main/java/eu/cise/adaptor/core/servicehandler/port/out/service_registry_openapi_yaml"


global_replacements $generated_dir_service_registry
mkdir -p $dest_dir_service_registry
cp -R $generated_dir_service_registry/* $dest_dir_service_registry
api_replacements $dest_dir_service_registry/api
model_replacements $dest_dir_service_registry/model
remove_javadoc $dest_dir_service_registry/api
remove_javadoc $dest_dir_service_registry/model

# Subscription Registry
generated_dir_subscription_registry="../../../target/generated-sources/open-api-yaml/eu/cise/adaptor/core/servicehandler/port/out/subscription_registry_openapi_yaml"
dest_dir_subscription_registry="../../../../cise-generic-adaptor-core/service-handler/src/main/java/eu/cise/adaptor/core/servicehandler/port/out/subscription_registry_openapi_yaml"

global_replacements $generated_dir_subscription_registry
mkdir -p $dest_dir_subscription_registry
cp -R $generated_dir_subscription_registry/* $dest_dir_subscription_registry
api_replacements $dest_dir_subscription_registry/api
model_replacements $dest_dir_subscription_registry/model
remove_javadoc $dest_dir_subscription_registry/api
remove_javadoc $dest_dir_subscription_registry/model
