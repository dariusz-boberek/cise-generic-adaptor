#!/bin/bash
#
# Script to generate a local maven repo along with the checksums required
# To use it please install the following tool first:
#    sudo apt install libxml-xpath-perl
#
#

cd "${0%/*}"

# declare functions
function findInFileBetweenTagName() {
   tagName=$2
   file=$1
   xpath -q -e /project/$tagName $file | grep $tagName | grep -Po "(?<=($tagName>)).*(?=</$tagName)"
   if [ "$?" != "0" ]; then
      xpath -q -e /project/parent/$tagName $file | grep $tagName | grep -Po "(?<=($tagName>)).*(?=</$tagName)"
   fi
}

DUMMY_REPO=dummyRepo
TARGET_REPO=../../cise-core-repo
rm -rf sampleProject
mkdir sampleProject
cp ../pom.xml sampleProject/
cd sampleProject
echo "Changing the pom.xml"
sed -i '/<modules>/,/<\/modules>/{//!d}' pom.xml
sed -i '/<modules>/d' pom.xml
sed -i '/<\/modules>/d' pom.xml
sed -i '/<dependencyManagement>/d' pom.xml
sed -i '/<\/dependencyManagement>/d' pom.xml

echo "Deleting previous $DUMMY_REPO"
rm -rf $DUMMY_REPO
echo "Creating $DUMMY_REPO"

mvn -U org.apache.maven.plugins:maven-dependency-plugin:2.9:copy-dependencies -DincludeGroupIds=eu.europa.ec.jrc.marex -DoutputDirectory=$DUMMY_REPO -Dmdep.copyPom=true -Dmdep.addParentPoms=true -DoverWriteReleases=true
mvn -U org.apache.maven.plugins:maven-dependency-plugin:2.9:copy -DoutputDirectory=$DUMMY_REPO

# go through the poms
for pomFile in $(find $DUMMY_REPO -type f -name \*.pom); do
   echo "Found pomFile : $pomFile"
   artifactId="$(findInFileBetweenTagName $pomFile "artifactId")"
   groupId="$(findInFileBetweenTagName $pomFile "groupId")"
   version="$(findInFileBetweenTagName $pomFile "version")"
   echo "   $artifactId"
   echo "   $groupId"
   echo "   $version"
   if ! [[ "$version" =~ ^[0-9]+\.[0-9]+ ]]; then
     version=$(echo ${pomFile#"${pomFile%%[0-9]*}"} | sed 's/.pom//g')
     echo "Fixed version to $version"
   fi
   mavenJarFile=$pomFile
   jarFile=$(find . -type f -name "$artifactId-$version.jar")
   if [ -s "$jarFile" ]; then
      echo "   jarFile found: $jarFile"
      mavenJarFile=$jarFile
   fi

   mvn deploy:deploy-file -DgroupId=$groupId -DartifactId=$artifactId -Dversion=$version -Durl=file:$TARGET_REPO/releases -DrepositoryId=file-releases -DupdateReleaseInfo=true -Dfile=$mavenJarFile -DpomFile=$pomFile -DcreateChecksum=true

done

cd ..
rm -rf sampleProject
