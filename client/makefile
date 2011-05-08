SCALAC = scalac
CLASSPATH = ../lib/*:.

%.class: %.scala
	$(SCALAC) -unchecked -cp $(CLASSPATH) $<

Client.class: ../common/Student.class ../common/Evaluator.class ../common/Config.class

client: Client.class

all: client

clean:
	rm client
	rm *.class
