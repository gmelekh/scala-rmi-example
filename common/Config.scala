// File reading
import io.Source._

// YAML meta language
import org.yaml.snakeyaml.Yaml

// Converting the YAML java types to scala
import collection.JavaConverters._
import java.util.LinkedHashMap
import java.util.ArrayList

//
// The Config class loads a specified file as YAML, and loads out
// some user defined values (settings, students).
//
// Because the YAML library is Java, it also does some conversion from Java types
// to native Scala types.
//
class Config(file: String) {
  var settings = Map.empty[String, String]
  var students = Map.empty[String, Map[String, String]]

  // Parse the yaml
  private val yaml = new Yaml
  (yaml.load(fromFile(file).mkString)) match {
    case map: java.util.Map[_,_] => processConfig(map.asScala.toMap)
  }

  // Process the top level setting keys, grabbing the settings and student maps and
  // saving them as scala types
  private def processConfig[K, V](map: Map[K, V]): Unit = {
    for (entry <- map) {
      try {
        entry match {
          case ("settings", value: AnyRef) =>
            settings = value.asInstanceOf[LinkedHashMap[String, String]].asScala.toMap
          case ("students", value: AnyRef) =>
            students = value.asInstanceOf[
              LinkedHashMap[String, LinkedHashMap[String, String]]].asScala.toMap.mapValues((value) => value.asScala.toMap)
        }
      } catch {
        case e: scala.MatchError =>
          println("Unknown setting " + entry + " in configuration file, ignoring.")
      }
    }
  }
}
