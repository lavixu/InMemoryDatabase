InMemoryDatabase
================

Creates a in memory database for given Product and queries jsons as input file.
To Compile:
From The Main Dir:
javac -cp .:lib/jackson-core-2.2.3.jar:lib/jackson-databind-2.2.3.jar:lib/jackson-annotations-2.2.3.jar src/*.java -d bin

To Run: 
cd bin
java -cp .:../lib/jackson-core-2.2.3.jar:../lib/jackson-databind-2.2.3.jar:../lib/jackson-annotations-2.2.3.jar Search
