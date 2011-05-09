// Security
import java.security.Permission

object Server extends Application {
  val configFile = "evaluator.yaml"

  try {
    val Evaluator = new EvaluatorImpl(configFile)

  } catch {
    case e: java.security.AccessControlException =>
      missingPermissionError(e.getMessage, e.getPermission)
  }

  private def missingPermissionError(msg: String, permission: Permission) {
    println("Unable to perform operation, reason: " + msg + " missing permission " + permission.toString())
  }
}
