@echo off
title QT Server
echo Avvio del Server... 
java -cp "../../bin;lib/*;mysql-connector-java-8.0.17.jar" server.MultiServer 8080

pause