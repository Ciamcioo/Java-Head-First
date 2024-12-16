compile:
	javac -d build -sourcepath src src/main/java/com/$(MAIN).java
compileTest:
	javac -d build -sourcepath src src/test/java/com/$(TEST_NAME).java
run:
	java -classpath build main.java.com.$(MAIN_CLASS)
runTest:
	java -classpath build test.java.$(TEST_RUNNER)
clear:
	rm -rf build/*
createStructur:
	mkdir build && mkdir src && mkdir src/main && mkdir src/test && mkdir src/main/java && mkdir src/main/resources && mkdir src/test/java && mkdir src/test/resources 

