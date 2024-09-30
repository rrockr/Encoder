## Compile

In project's root directory:

javac -sourcepath ./src/ -d ./bin/ ./src/Main.java

jar cfe ./lib/Main.jar Main -C ./bin/ .

## Run

java -jar ./lib/Main.jar input.txt
