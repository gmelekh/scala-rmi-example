include ../common/makefile

client.jar: Student.class Evaluator.class Config.class Client.class
	$(JAR) cvfe $@ Client $^ $(SCALA_RUNTIME) ../lib/*.jar

all: client.jar

clean:
	rm client.jar *.class

.PHONY: all clean
