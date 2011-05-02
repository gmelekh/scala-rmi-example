SCALAC = scalac
CLASSPATH = ../lib/*:../common/*:.

%.class: %.scala
	$(SCALAC) -unchecked -cp $(CLASSPATH) $<

Server.class: ../common/Student.class ../common/Evaluator.class Server.scala EvaluatorImpl.class StudentImpl.class ../common/Config.class

all: Server.class

clean:
	rm *.class
