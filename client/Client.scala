// Security
import java.security.Permission

// For java RMI
import java.rmi.Naming
import java.rmi.RemoteException
import java.rmi.RMISecurityManager
import java.rmi.registry.LocateRegistry
import java.rmi.registry.Registry
import java.rmi.server.UnicastRemoteObject

// For message box
import javax.swing.JOptionPane

object Client extends Application {
  val configFile = "evaluator.yaml"

  try {
    val config = new Config(configFile)

    val port = config.settings("port").asInstanceOf[Integer].intValue
    val name = config.settings("service_name")
    val address = config.settings("service_address")

    // Setup the security manager so we can get the Student object shipped to us over RMI
    if (System.getSecurityManager == null) {
      System.setSecurityManager(new SecurityManager);
    }

    val registry = LocateRegistry.getRegistry(port)
    val evaluator = registry.lookup("//" + address + ":" + port.toString + "/" + name).asInstanceOf[Evaluator]

    val username = JOptionPane.showInputDialog("Enter your username:")
    val password = JOptionPane.showInputDialog("Enter your password:")

    val student = evaluator.student(username)

    if(student != null && student.authenticate(password)) {

      // Launch gui
      val results = new ResultWindow(student)
      results.open

    } else {
      JOptionPane.showMessageDialog(
        null,
        "Invalid username or password",
        "Authentication failure",
        JOptionPane.INFORMATION_MESSAGE )
    }

  } catch {
    case e: java.security.AccessControlException =>
      missingPermissionError(e.getMessage, e.getPermission)
    case e: java.rmi.ConnectException =>
      noServerWarning
    case e: java.io.FileNotFoundException =>
      noConfigFileWarning
    case e: java.util.NoSuchElementException =>
      missingConfigWarning(e.getMessage)
  }

  private def noConfigFileWarning {
    println("Missing configuration file " + configFile);
  }

  private def missingConfigWarning(msg: String) {
    println("Missing setting in configuration file, " + msg);
  }

  private def noServerWarning {
    println("Unable to connect to server, are you sure it's running?");
  }

  private def missingPermissionError(msg: String, permission: Permission) {
    println("Unable to perform operation, reason: " + msg + " missing permission " + permission.toString())
  }
}
