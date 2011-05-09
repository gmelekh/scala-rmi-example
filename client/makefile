include ../common/makefile

client.jar: Student.class Evaluator.class Config.class ResultWindow.class Client.class
	$(JAR) cvfe $@ Client *.class -C ../build org -C ../build scala -C ../build biz -C ../build com

all: client.jar

clean:
	rm client.jar *.class

.PHONY: all clean
