object Server {
  val configFile = "evaluator.yaml"

  def main( args: Array[String] ) {
    val Evaluator = new EvaluatorImpl(configFile)
  }
}
