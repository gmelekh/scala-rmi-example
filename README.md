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
