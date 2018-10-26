@ECHO OFF
start "Game" java -jar singlePlayerServerRunner.jar
start "Chat" java -jar ChatServer.jar
PAUSE