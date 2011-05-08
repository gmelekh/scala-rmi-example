SCALAC = scalac
CLASSPATH = ../lib/*:../common/*:.

%.class: %.scala
	$(SCALAC) -unchecked -cp $(CLASSPATH) $<

Server.class: ../common/Student.class ../common/Evaluator.class ../common/Config.class Server.scala StudentImpl.class EvaluatorImpl.class
all: Server.class

clean:
	rm server
	rm *.class
