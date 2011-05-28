# scala-rmi-example

## Background

I wanted to learn a bit of Scala, and I had a quick uni assignment that needed
to demo some RMI code so I decided to whip up a small sample.

It's not a particularly user friendly application, or good GUI interface, but
it demonstrates a simple YML configuration file parser and a simple RMI
protocol which involves a collection class (Evaluator) which authenticates
students and then passes a serialized Student class over RMI to do some basic
grading and student result evaluation.

Some of the technologies used here:

  * Apache commons codec (SHA1, EncodeHex)
  * SnakeYAML YAML parser
  * Java RMI

## Building

You'll need the following jar files in the lib directory:

    commons-codec-1.5.jar
    scala-library.jar
    snakeyaml-1.8.jar

The scala-library.jar should come from your particular version of scala. There
is a makefile for the client and server, which can be executed with the small
shell scripts provided.

## Running

The command line to run each program is as follows:

Server:

    java -Djava.security.policy=../common/no.policy -jar server.jar

Client:

    java -Djava.rmi.server.codebase=http://127.0.0.1/~john/server.jar -Djava.security.policy=../common/no.policy -jar client.jar

As seen in the client command line, you'll need to specify a codebase
repository to download the serialized StudentImpl class. In the code I've used
a simple local Apache server hosting the server.jar file in my home directory.

Note that the policy specified here is a simple permit all policy, you may
prefer to set a more restrictive one.

## Configuration

Both the client and server have their own configuration file which is a simple
YAML format like so for the server:

    settings:
      service_address: 127.0.0.1
      service_name: evaluator
      port: 32717

    students:
      john:
        studentid: 0991300
        password: 0c7cc0fdbb9aaeab4f408f9ad1cfcacdcd85474c
        salt: D458728A0BBFB530EE

The password in this example is 'password'.
