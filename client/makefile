SCALAC = scalac
CLASSPATH = ../lib/*:.

%.class: %.scala
	$(SCALAC) -unchecked -cp $(CLASSPATH) $<

Client.class: ../common/Student.class ../common/Evaluator.class Client.scala ../common/Config.class

all: Client.class

clean:
	rm *.class
