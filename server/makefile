include ../common/makefile

server.jar: Student.class Evaluator.class Config.class StudentImpl.class EvaluatorImpl.class Server.class
	$(JAR) cvfm $@ manifest.mf *.class ../lib/*.jar

all: server.jar

clean:
	rm server.jar *.class

.PHONY: all clean
