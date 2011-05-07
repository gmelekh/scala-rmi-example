import java.rmi.Remote
import java.rmi.RemoteException

class DisqualifiedException(msg: String) extends RuntimeException(msg)
class TooManyResultsException(msg: String) extends RuntimeException(msg)
class InvalidResultException(msg: String) extends RuntimeException(msg)

trait Student extends Remote {
  @throws(classOf[RemoteException])
  def authenticate(user_password: String) : Boolean

  @throws(classOf[RemoteException])
  def topMarks : List[Double]

  @throws(classOf[RemoteException])
  def topMarkAverage : Double

  @throws(classOf[RemoteException])
  def courseAverage : Double

  @throws(classOf[RemoteException])
  def addResult(result : Double) : (Boolean, Boolean)

  @throws(classOf[RemoteException])
  def assessment : String

  @throws(classOf[RemoteException])
  def getResults : List[Double]

  @throws(classOf[RemoteException])
  def clearResults
}
