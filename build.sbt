import org.scalajs.linker.interface.CheckedBehavior.Unchecked
import sbtcrossproject.CrossProject

import org.scalajs.jsenv.Input

val setupPrefixPropertyCode = taskKey[String]("JS code to setup the prefix property")
val createHTMLRunner = taskKey[File]("Create the HTML runner for this benchmark")

val projectSettings: Seq[Setting[_]] = Seq(
  organization := "scalajs-benchmarks",
  version := "0.1-SNAPSHOT"
)

scalaJSLinkerConfig in Global :=
  org.scalajs.linker.interface.StandardConfig()

val defaultSettings: Seq[Setting[_]] = projectSettings ++ Seq(
  scalaVersion := "2.12.10",
  scalacOptions ++= Seq(
      "-deprecation",
      "-unchecked",
      "-feature",
      "-encoding", "utf8"
  )
)

val defaultJVMSettings: Seq[Setting[_]] = Def.settings(
  fork in run := !scala.sys.env.get("TRAVIS").exists(_ == "true"),

  inConfig(Compile)(Def.settings(
    javaOptions += {
      val benchmarkName = moduleName.value
      val compiler = "JVM"
      val prefix = s"$benchmarkName;$compiler;;;;;"
      s"-Dbenchmark.prefix=$prefix"
    }
  ))
)

val defaultJSSettings: Seq[Setting[_]] = Def.settings(
  scalaJSLinkerConfig := (scalaJSLinkerConfig in ThisBuild).value,
  scalaJSUseMainModuleInitializer := true,

  inConfig(Compile)(Def.settings(
    setupPrefixPropertyCode := {
      val benchmarkName = moduleName.value
      val linkerConfig = scalaJSLinkerConfig.value
      val compiler = "Scala.js"
      val es2015 = if (linkerConfig.esFeatures.useECMAScript2015) "es2015" else "es5.1"
      val ubChecks = if (linkerConfig.semantics.productionMode) "prod" else "dev"
      val optimizer = if (linkerConfig.optimizer) "opt" else "no-opt"
      val gcc = if (linkerConfig.closureCompiler) "gcc" else "no-gcc"
      val prefix = s"$benchmarkName;$compiler;$es2015;$ubChecks;$optimizer;$gcc;"
      val code = s"var ScalaJSBenchmarkPrefix = '$prefix';"
      code
    },

    jsEnvInput := {
      val prev = jsEnvInput.value
      val code = setupPrefixPropertyCode.value
      val path = java.nio.file.Files.write(
          com.google.common.jimfs.Jimfs.newFileSystem().getPath("setup-prefix-property.js"),
          code.getBytes(java.nio.charset.StandardCharsets.UTF_8))
      Input.Script(path) +: prev
    },

    createHTMLRunner := {
      val jsFile = fastOptJS.value.data
      val jsFileName = jsFile.getName
      val htmlFile = jsFile.getParentFile / (jsFileName.stripSuffix(".js") + ".html")
      val title = name.value
      val mainClassName = mainClass.value.getOrElse {
        throw new Exception("Oops, no main class")
      }
      val content = s"""
        |<!DOCTYPE html>
        |<html>
        |  <head>
        |    <title>$title</title>
        |    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        |  </head>
        |  <body>
        |    <script type="text/javascript">
        |      ${setupPrefixPropertyCode.value}
        |    </script>
        |    <script type="text/javascript" src="$jsFileName"></script>
        |    <script type="text/javascript">
        |      setupHTMLBenchmark("$mainClassName");
        |    </script>
        |  </body>
        |</html>
      """.stripMargin
      IO.write(htmlFile, content)
      streams.value.log.info(htmlFile.toURI.toASCIIString)
      htmlFile
    }
  ))
)

lazy val parent = project.in(file(".")).
  settings(projectSettings: _*).
  settings(
    name := "scalajs-benchmarks",
    publishArtifact in Compile := false,
    clean := clean.dependsOn(allProjects.map(clean in _): _*).value
  )

lazy val allProjects = Seq(
    commonJVM, commonJS,
    deltablueJSRef, richardsJSRef, tracerJSRef,
    deltablueJVM, deltablueJS,
    richardsJVM, richardsJS,
    sudokuJVM, sudokuJS,
    tracerJVM, tracerJS,
    sha512JVM, sha512JS,
    sha512IntJVM, sha512IntJS,
    longMicroJVM, longMicroJS,
    intMicroJVM, intMicroJS,
    kmeansJVM, kmeansJS,
    bounceJVM, bounceJS,
    cdJVM, cdJS,
    gcbenchJVM, gcbenchJS,
    mandelbrotJVM, mandelbrotJS,
    permuteJVM, permuteJS,
    brainfuckJVM, brainfuckJS,
    jsonJVM, jsonJS,
    listJVM, listJS,
    nbodyJVM, nbodyJS,
    queensJVM, queensJS,
    bigNumbersJVM, bigNumbersJS
)

lazy val common = crossProject(JSPlatform, JVMPlatform).
  settings(defaultSettings: _*).
  settings(
    name := "scalajs-benchmarks-common",
    moduleName := "common"
  )

lazy val commonJVM = common.jvm
lazy val commonJS = common.js

def autoConfigFull(cp: CrossProject): CrossProject = {
  cp.settings(defaultSettings: _*)
    .jvmSettings(defaultJVMSettings: _*)
    .jsSettings(defaultJSSettings: _*)
    .settings(
      name := "scalajs-benchmark-" + name.value,
      moduleName := name.value.stripPrefix("scalajs-benchmark-")
    )
    .dependsOn(common)
}

def autoConfig(cp: CrossProject.Builder): CrossProject = {
  autoConfigFull(cp.crossType(CrossType.Pure))
}

def autoConfigJSRef(p: Project, jsFile: String, benchmarkFunName: String): Project = {
  val theName = p.id.stripSuffix("JSRef")
  val parent = LocalProject("parent")
  p.in(file("references/" + theName))
    .enablePlugins(ScalaJSPlugin)
    .settings(projectSettings: _*)
    .settings(
      name := theName,

      inConfig(Compile)(Def.settings(
        scalaJSUseMainModuleInitializer := true,
        mainClass := Some(name.value),
        jsEnvInput := {
          val dir = (baseDirectory in parent).value / "common/reference"
          val files = List(dir / "bench.js", dir / jsFile)
          files.map(f => Input.Script(f.toPath))
        },

        createHTMLRunner := {
          val dir = (baseDirectory in parent).value / "common/reference"
          val jsFileURI1 = (dir / "bench.js").toURI.toASCIIString
          val jsFileURI2 = (dir / jsFile).toURI.toASCIIString

          val trg = target.value
          val htmlFile = trg / (theName + ".html")
          val title = name.value

          val content = s"""
            |<!DOCTYPE html>
            |<html>
            |  <head>
            |    <title>$title</title>
            |    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            |  </head>
            |  <body>
            |    <script type="text/javascript" src="$jsFileURI1"></script>
            |    <script type="text/javascript" src="$jsFileURI2"></script>
            |    <script type="text/javascript">
            |      Benchmark.mainHTML("$title", $benchmarkFunName);
            |    </script>
            |  </body>
            |</html>
          """.stripMargin
          IO.write(htmlFile, content)
          streams.value.log.info(htmlFile.toURI.toASCIIString)
          htmlFile
        }
      ))
    )
}

lazy val deltablueJSRef = autoConfigJSRef(project, "deltablue.js", "deltaBlue")
lazy val richardsJSRef = autoConfigJSRef(project, "richards.js", "runRichards")
lazy val tracerJSRef = autoConfigJSRef(project, "tracer.js", "renderScene")

lazy val deltablue = autoConfig(crossProject(JSPlatform, JVMPlatform))
lazy val deltablueJVM = deltablue.jvm
lazy val deltablueJS = deltablue.js

lazy val richards = autoConfig(crossProject(JSPlatform, JVMPlatform))
lazy val richardsJVM = richards.jvm
lazy val richardsJS = richards.js

lazy val sudoku = autoConfig(crossProject(JSPlatform, JVMPlatform))
lazy val sudokuJVM = sudoku.jvm
lazy val sudokuJS = sudoku.js

lazy val tracer = autoConfigFull(crossProject(JSPlatform, JVMPlatform))
lazy val tracerJVM = tracer.jvm
lazy val tracerJS = tracer.js

lazy val sha512 = autoConfig(crossProject(JSPlatform, JVMPlatform))
lazy val sha512JVM = sha512.jvm
lazy val sha512JS = sha512.js

lazy val sha512Int = autoConfig(crossProject(JSPlatform, JVMPlatform))
lazy val sha512IntJVM = sha512Int.jvm
lazy val sha512IntJS = sha512Int.js

lazy val longMicro = autoConfig(crossProject(JSPlatform, JVMPlatform))
  .settings(
    mainClass in Compile := Some("org.scalajs.benchmark.longmicro.LongMicroAll")
  )
lazy val longMicroJVM = longMicro.jvm
lazy val longMicroJS = longMicro.js

lazy val intMicro = autoConfig(crossProject(JSPlatform, JVMPlatform))
  .settings(
    mainClass in Compile := Some("org.scalajs.benchmark.intmicro.IntMicroAll")
  )
lazy val intMicroJVM = intMicro.jvm
lazy val intMicroJS = intMicro.js

lazy val kmeans = autoConfig(crossProject(JSPlatform, JVMPlatform))
lazy val kmeansJVM = kmeans.jvm
lazy val kmeansJS = kmeans.js

lazy val bounce = autoConfig(crossProject(JSPlatform, JVMPlatform))
lazy val bounceJVM = bounce.jvm
lazy val bounceJS = bounce.js

lazy val cd = autoConfig(crossProject(JSPlatform, JVMPlatform))
lazy val cdJVM = cd.jvm
lazy val cdJS = cd.js

lazy val gcbench = autoConfig(crossProject(JSPlatform, JVMPlatform))
lazy val gcbenchJVM = gcbench.jvm
lazy val gcbenchJS = gcbench.js

lazy val mandelbrot = autoConfig(crossProject(JSPlatform, JVMPlatform))
lazy val mandelbrotJVM = mandelbrot.jvm
lazy val mandelbrotJS = mandelbrot.js

lazy val permute = autoConfig(crossProject(JSPlatform, JVMPlatform))
lazy val permuteJVM = permute.jvm
lazy val permuteJS = permute.js

lazy val brainfuck = autoConfig(crossProject(JSPlatform, JVMPlatform))
lazy val brainfuckJVM = brainfuck.jvm
lazy val brainfuckJS = brainfuck.js

lazy val json = autoConfig(crossProject(JSPlatform, JVMPlatform))
lazy val jsonJVM = json.jvm
lazy val jsonJS = json.js

lazy val list = autoConfig(crossProject(JSPlatform, JVMPlatform))
lazy val listJVM = list.jvm
lazy val listJS = list.js

lazy val nbody = autoConfig(crossProject(JSPlatform, JVMPlatform))
lazy val nbodyJVM = nbody.jvm
lazy val nbodyJS = nbody.js

lazy val queens = autoConfig(crossProject(JSPlatform, JVMPlatform))
lazy val queensJVM = queens.jvm
lazy val queensJS = queens.js

lazy val bigNumbers = autoConfig(crossProject(JSPlatform, JVMPlatform))
lazy val bigNumbersJVM = bigNumbers.jvm
lazy val bigNumbersJS = bigNumbers.js
