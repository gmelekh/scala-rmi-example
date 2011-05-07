// For SHA1 hashes
import javax.crypto
import java.security._

// For hex encoding the hash
import org.apache.commons.codec.binary.Hex._

//
// The Student class is the core of our program, it authenticates students, accepts
// a list of results for the user, calculates averages and the top marks and
// returns an assessment of the suitability of a user for honours study.
//
@serializable
class StudentImpl(
  val name: String,
  private val password: String,
  private val salt: String,
  val studentId: String) extends Student {

  // Count of the number of marks to use as the basis for the 'best of'
  // when assessing a student's performance
  val topMarkCount = 8

  // Parameters for processing the results
  val minimumResults = 12
  val maximumResults = 30
  val disqualifyCount = 6

  // The results for a student
  var results = List.empty[Double]

  // Convert a string to bytes for encryption routines
  private def bytes(str: String) = str.getBytes

  // Get the results
  def getResults : List[Double] = results

  // Clear the results
  def clearResults {
    results = List.empty[Double]
  }

  //
  // Given the plaintext password, determine if authentication is successful, hashes
  // the salt and password using the SHA1 algorithm
  // \returns true for authentication success, false otherwise
  //
  def authenticate(user_password: String) : Boolean = {

    // Message digest for authenticating a student
    // XXX: Could go in the class body but is not serializable so blows up
    val messageDigest = MessageDigest.getInstance("SHA1")

    messageDigest.reset
    messageDigest.update(bytes(salt))
    messageDigest.update(bytes(user_password))
    val hashedPassword = encodeHex(messageDigest.digest).mkString

    password == hashedPassword
  }

  // Gets the top marks for this student
  def topMarks : List[Double] = {
    results.sorted.reverse.take(topMarkCount)
  }

  // Get the average of the top marks for the student
  def topMarkAverage : Double = {
    if(topMarks.size == 0) return 0
    topMarks.sum / topMarks.size
  }

  // Get the course average for this student
  def courseAverage : Double = {
    if(results.size == 0) return 0
    results.sum / results.size
  }

  // Record a result for a student
  def addResult(result : Double) : (Boolean, Boolean) = {
    // Too many results, can't add any more
    if(results.size >= maximumResults) {
      throw new TooManyResultsException("Too many results for student, no more can be added")
    }

    if(result < 0 || result > 100) {
      throw new InvalidResultException("Invalid result, please enter another")
    }

    // Add items to the back of the list
    results ++= List(result)

    // Check if this student has been disqualified
    if(results.count(value => value < 50) >= disqualifyCount) {
      throw new DisqualifiedException("More than " + disqualifyCount + " results failed, student is disqualified");
    }

    // Return the result state
    (results.size >= minimumResults, results.size >= maximumResults)
  }

  // Obtain an assessment of the student's marks as a descriptive string
  def assessment : String = {
    var resultStr = List[String](studentId, courseAverage.toString)
    resultStr ++= (
      if(courseAverage >= 70) {
        List("QUALIFIED FOR HONOURS STUDY!")

      } else {
        List(topMarkAverage.toString, (
          if(topMarkAverage >= 80) {
            "May have a good chance! Need further assessment!"

          } else if(topMarkAverage >= 70) {
            "May have a chance! Must be carefully reassessed and get the coordinator's special permission!"

          } else {
            "Not qualified for honours study! Try masters by course work."

          }))
      })
    resultStr.mkString(", ")
  }
}
