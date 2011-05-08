SCALAC = scalac
JAR = jar
CLASSPATH = ../lib/*:../common/*:.
SCALA_RUNTIME = /usr/local/Cellar/scala/2.8.1/libexec/lib/scala-library.jar

%.class: %.scala
	$(SCALAC) -unchecked -cp $(CLASSPATH) $<

%.class: ../common/%.scala
	$(SCALAC) -unchecked -cp $(CLASSPATH) $<
