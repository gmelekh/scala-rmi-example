include ../common/makefile

client.jar: Student.class Evaluator.class Config.class ResultWindow.class Client.class
	$(JAR) cvfm $@ manifest.mf *.class ../lib/*.jar

all: client.jar

clean:
	rm client.jar *.class

.PHONY: all clean
