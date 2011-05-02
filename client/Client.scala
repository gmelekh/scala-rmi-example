// Printing decimals
import java.text.DecimalFormat

// For swing gui components
import swing._
import javax.swing.JOptionPane

// For java RMI
import java.rmi.Naming
import java.rmi.RemoteException
import java.rmi.RMISecurityManager
import java.rmi.registry.LocateRegistry
import java.rmi.registry.Registry
import java.rmi.server.UnicastRemoteObject

class Results(val student: Student) extends MainFrame {

  private val width = 800

  // Declare in body of class so accessible in addMessage
  lazy val textarea = new TextArea {
    preferredSize = new Dimension(width, 400)
  }

  contents = new BorderPanel {
    add(new FlowPanel {
      contents.append(Button("Get Results") {
        addMessage("Results as entered in order: " + student.getResults.mkString(", "))
      })
      contents.append(Button("Enter Results") {
        // Clear the results
        student.clearResults
        
        try {
          var enoughResults = false
          var maximumResults = false
          var input = new String

          while (!maximumResults || !(enoughResults && input == "-1")) { 
            try {
              input = JOptionPane.showInputDialog(
                if(enoughResults) {
                  "Enter Integer Grade, -1 to Quit:"
                } else {
                  "Enter Integer Grade:"
                })

              // convert grade from a String to an integer
              val gradeValue = Integer.parseInt(input)

              val (enough, max) = student.addResult(gradeValue)
              enoughResults = enough
              maximumResults = max
            } catch {
              // Do nothing if entry is bad, so the dialog just gets shown again
              case e: java.lang.NumberFormatException => null
            }
          }
        } catch {
          case e: DisqualifiedException =>
            JOptionPane.showMessageDialog(
              null,
              e.getMessage,
              "Error",
              JOptionPane.INFORMATION_MESSAGE )
        }
      })
      contents.append(Button("Get Assessment") {
        addMessage(student.assessment)
      })
      contents.append(Button("Course Average") {
        addMessage("Course average: " + student.courseAverage)
      })
      contents.append(Button("Top Mark Average") {
        addMessage("Top mark average: " + student.topMarkAverage.toString)
      })
      contents.append(Button("Top Marks") {
        addMessage("Top results: " + student.topMarks.mkString(", "))
      })
    }, BorderPanel.Position.North)
    add(new BorderPanel {
      add(textarea, BorderPanel.Position.Center)
    }, BorderPanel.Position.South)
  }
  size = new Dimension(width, 480)

  private def addMessage(message: String) {
    textarea.append(message + "\n")
  }
}

object Client {
  val configFile = "evaluator.yaml"

  def noConfigFileWarning {
    println("Missing configuration file " + configFile);
  }

  def missingConfigWarning(msg: String) {
    println("Missing setting in configuration file, " + msg);
  }

  def noServerWarning {
    println("Unable to connect to server, are you sure it's running?");
  }

  def main(args: Array[String]) {
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
        val results = new Results(student)
        results.open

      } else {
        JOptionPane.showMessageDialog(
          null,
          "Invalid username or password",
          "Authentication failure",
          JOptionPane.INFORMATION_MESSAGE )
      }

    } catch {
      case e: java.rmi.ConnectException =>
        noServerWarning
      case e: java.io.FileNotFoundException =>
        noConfigFileWarning
      case e: java.util.NoSuchElementException =>
        missingConfigWarning(e.getMessage)
    }
  }
}
