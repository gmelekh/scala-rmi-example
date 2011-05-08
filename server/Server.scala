object Server {
  val configFile = "evaluator.yaml"

  def main(args: Array[String]) : Unit = {
    val Evaluator = new EvaluatorImpl(configFile)
  }
}
