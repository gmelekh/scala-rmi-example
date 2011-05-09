import java.rmi.Naming
import java.rmi.RemoteException
import java.rmi.RMISecurityManager
import java.rmi.registry.LocateRegistry
import java.rmi.registry.Registry
import java.rmi.server.UnicastRemoteObject

//
// Implementation of the Evaluator RMI object, has a very simple interface: a
// single method for obtaining a Student RMI object by username, the client app
// then interacts with that student.
//
class EvaluatorImpl(configFile: String) extends Evaluator {

  var students = Map.empty[String, Student]

  try {
    val config = new Config(configFile)

    // Load all the students from the settings in a map keyed by name
    for((name, values) <- config.students) {
      students += name -> new StudentImpl(name, values("password"), values("salt"), values("studentid"))
    }

    val port = config.settings("port").asInstanceOf[Integer].intValue
    val name = config.settings("service_name")
    val address = config.settings("service_address")

    // Setup the security manager since we're going to export the Student over RMI
    if (System.getSecurityManager == null) {
      System.setSecurityManager(new SecurityManager)
    }

    val stub = UnicastRemoteObject.exportObject(this, 0)
    val registry = LocateRegistry.createRegistry(port)

    // Register this object as the RMI handler
    val url = "//" + address + ":" + port.toString + "/" + name
    registry.rebind(url, stub)
    println("Server ready, java.RMI server listening on " + url)

  } catch {
    case e: java.rmi.server.ExportException =>
      serverAlreadyRunningWarning()
    case e: java.io.FileNotFoundException =>
      noConfigFileWarning()
  }

  def student(username: String) : Student = {
    try {
      println("Looking up student: " + username)
      students(username)
    } catch {
      // No student found
      case e: java.util.NoSuchElementException => null
    }
  }

  def noConfigFileWarning() {
    println("Missing configuration file " + configFile)
  }

  def serverAlreadyRunningWarning() {
    println("Unable to bind port, perhaps another server is already running?")
  }
}
