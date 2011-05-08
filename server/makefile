include ../common/makefile

server.jar: Student.class Evaluator.class Config.class StudentImpl.class EvaluatorImpl.class Server.class
	$(JAR) cvfe $@ Server $^ $(SCALA_RUNTIME) ../lib/*.jar

all: server.jar

clean:
	rm server.jar *.class

.PHONY: all clean
