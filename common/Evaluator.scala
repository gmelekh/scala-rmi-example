import java.rmi.Remote
import java.rmi.RemoteException

trait Evaluator extends Remote {

  @throws(classOf[RemoteException])
  def student(name: String) : Student

}
