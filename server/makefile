include ../common/makefile

server.jar: Student.class Evaluator.class Config.class StudentImpl.class EvaluatorImpl.class Server.class
	$(JAR) cvfe $@ Server *.class -C ../build scala  -C ../build org -C ../build biz -C ../build com

all: server.jar

clean:
	rm server.jar *.class

.PHONY: all clean
