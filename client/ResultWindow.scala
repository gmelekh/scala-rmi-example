// Printing decimals
import java.text.DecimalFormat

// For swing gui components
import swing._
import javax.swing.JOptionPane

class ResultWindow(val student: Student) extends MainFrame {

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
          var continue = true
          while (continue) {
            try {
              var input = JOptionPane.showInputDialog(
                if(enoughResults) {
                  "Enter Integer Grade, -1 to Stop:"
                } else {
                  "Enter Integer Grade:"
                })
              
              if(input == null) {
                // Escape or cancel pressed, clear the results and break out
                student.clearResults
                continue = false

              } else {

                // convert grade from a String to an integer
                val gradeValue = Integer.parseInt(input)

                // enough results were entered last round, can finish here
                if(gradeValue == -1 && enoughResults) {
                  continue = false

                } else {
                  val (enough, max) = student.addResult(gradeValue)

                  // Save result of enough results
                  enoughResults = enough

                  // Should we continue?
                  continue = continue && !max
                }
              }
            } catch {
              // Do nothing if entry is bad, so the dialog just gets shown again
              case e: java.lang.NumberFormatException => null
              case e: InvalidResultException => null
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
