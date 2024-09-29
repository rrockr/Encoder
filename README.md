## Compile

javac -sourcepath ./src/ -d ./bin/ ./src/Main.java
jar cf ./lib/Main.jar ./bin/Main.class

## Run

java -cp ./bin Main input.txt
