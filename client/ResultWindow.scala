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


// vim: set ts=4 sw=4 et:
