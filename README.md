# Cordova Polymer TODO list app

Based on [this tutorial](http://code.tutsplus.com/tutorials/how-to-create-a-to-do-list-app-with-polymer-and-cordova--cms-25434)

## Pre-requisits

* Ensure java version is 8 using `java -version`
* Ensure maven command line (mvn) is in PATH by typing `mvn -version`, if command not found, download apache maven 3.3.x and include its bin folder in PATH, then try again
* Ensure node packet manager (npm) is in PATH by typing `npm -v`, if command not found, download and install NodeJS, then try again
* Install Cordova >= 5.3 if not done yet: `npm install cordova@5.3.3 -g`, -g switch means install globally

## Compiling

Compile JSweet using
```
mvn clean generate-sources
```

JavaScript files are generated in `www/js/app/`, check that they are up to date

## Running 

Run using cordova, for instance:
```
cordova platform add browser
mvn run browser
```
OR 
```
cordova platform add android
mvn run android
```