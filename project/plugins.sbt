val scalaJSVersion =
  Option(System.getenv("SCALAJS_VERSION")).getOrElse("1.20.2")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % scalaJSVersion)
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.3.2")
