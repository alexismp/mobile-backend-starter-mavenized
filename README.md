mobile-backend-starter-mavenized
================================

Maven version of https://github.com/GoogleCloudPlatform/solutions-mobile-backend-starter-java
Need to manually install the GCM jar:
$ mvn -DartifactId=gcm-server -DgroupId=com.google.android.gcm -Dversion=1.0.2 -Dpackaging=jar -Dfile=/path/to/gcm-server-1.0.2.jar -DgeneratePom=false install:install-file
