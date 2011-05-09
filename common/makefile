SCALAC = scalac
JAR = jar
CLASSPATH = ../lib/*:../common/*.class:.

%.class: %.scala
	$(SCALAC) -unchecked -cp $(CLASSPATH) $<

%.class: ../common/%.scala
	$(SCALAC) -unchecked -cp $(CLASSPATH) $<
