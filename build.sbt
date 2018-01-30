//import org.scalajs.core.tools.linker.CheckedBehavior.Unchecked
import org.scalajs.linker.CheckedBehavior.Unchecked

val projectSettings: Seq[Setting[_]] = Seq(
  organization := "scalajs-benchmarks",
  version := "0.1-SNAPSHOT"
)

val defaultSettings: Seq[Setting[_]] = projectSettings ++ Seq(
  scalaVersion := "2.12.4",
  scalacOptions ++= Seq(
      "-deprecation",
      "-unchecked",
      "-feature",
      "-encoding", "utf8"
  )
)

val defaultJVMSettings: Seq[Setting[_]] = Seq(
  fork in run := !scala.sys.env.get("TRAVIS").exists(_ == "true")
)

val defaultJSSettings: Seq[Setting[_]] = Seq(
  scalaJSLinkerConfig ~= {
    _.withSemantics(_.withAsInstanceOfs(Unchecked).withArrayIndexOutOfBounds(Unchecked))
  },
  scalaJSUseMainModuleInitializer := true
)

lazy val parent = project.in(file(".")).
  settings(projectSettings: _*).
  settings(
    name := "Scala.js Benchmarks",
    publishArtifact in Compile := false
  )

lazy val common = crossProject.
  settings(defaultSettings: _*).
  settings(
    name := "Scala.js Benchmarks - Common",
    moduleName := "common"
  )

lazy val commonJVM = common.jvm
lazy val commonJS = common.js

lazy val deltablue = crossProject.crossType(CrossType.Pure).
  settings(defaultSettings: _*).
  jvmSettings(defaultJVMSettings: _*).
  jsSettings(defaultJSSettings: _*).
  settings(
    name := "Scala.js Benchmarks - DeltaBlue",
    moduleName := "deltablue"
  ).
  dependsOn(common)

lazy val deltablueJVM = deltablue.jvm
lazy val deltablueJS = deltablue.js

lazy val richards = crossProject.crossType(CrossType.Pure).
  settings(defaultSettings: _*).
  jvmSettings(defaultJVMSettings: _*).
  jsSettings(defaultJSSettings: _*).
  settings(
    name := "Scala.js Benchmarks - Richards",
    moduleName := "richards"
  ).
  dependsOn(common)

lazy val richardsJVM = richards.jvm
lazy val richardsJS = richards.js

lazy val sudoku = crossProject.crossType(CrossType.Pure).
  settings(defaultSettings: _*).
  jvmSettings(defaultJVMSettings: _*).
  jsSettings(defaultJSSettings: _*).
  settings(
    name := "Scala.js Benchmarks - Sudoku",
    moduleName := "sudoku"
  ).
  dependsOn(common)

lazy val sudokuJVM = sudoku.jvm
lazy val sudokuJS = sudoku.js

lazy val tracer = crossProject.
  settings(defaultSettings: _*).
  jvmSettings(defaultJVMSettings: _*).
  jsSettings(defaultJSSettings: _*).
  settings(
    name := "Scala.js Benchmarks - Tracer",
    moduleName := "tracer"
  ).
  dependsOn(common)

lazy val tracerJVM = tracer.jvm
lazy val tracerJS = tracer.js

lazy val sha512 = crossProject.crossType(CrossType.Pure).
  settings(defaultSettings: _*).
  jvmSettings(defaultJVMSettings: _*).
  jsSettings(defaultJSSettings: _*).
  settings(
    name := "Scala.js Benchmarks - SHA-512",
    moduleName := "sha512"
  ).
  dependsOn(common)

lazy val sha512JVM = sha512.jvm
lazy val sha512JS = sha512.js

lazy val sha512Int = crossProject.crossType(CrossType.Pure).
  settings(defaultSettings: _*).
  jvmSettings(defaultJVMSettings: _*).
  jsSettings(defaultJSSettings: _*).
  settings(
    name := "Scala.js Benchmarks - SHA-512-Int",
    moduleName := "sha512Int"
  ).
  dependsOn(common)

lazy val sha512IntJVM = sha512Int.jvm
lazy val sha512IntJS = sha512Int.js

lazy val longMicro = crossProject.crossType(CrossType.Pure).
  settings(defaultSettings: _*).
  jvmSettings(defaultJVMSettings: _*).
  jsSettings(defaultJSSettings: _*).
  settings(
    name := "Scala.js Benchmarks - LongMicro",
    moduleName := "longmicro",
    mainClass in Compile := Some("org.scalajs.benchmark.longmicro.LongMicroAll")
  ).
  dependsOn(common)

lazy val longMicroJVM = longMicro.jvm
lazy val longMicroJS = longMicro.js
