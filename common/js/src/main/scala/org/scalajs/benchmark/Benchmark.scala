/*                     __                                               *\
**     ________ ___   / /  ___      __ ____  Scala.js Benchmarks        **
**    / __/ __// _ | / /  / _ | __ / // __/  (c) 2003-2013, LAMP/EPFL   **
**  __\ \/ /__/ __ |/ /__/ __ |/_// /_\ \    (c) 2013, Jonas Fonseca    **
** /____/\___/_/ |_/____/_/ | |__/ /____/                               **
**                          |/____/                                     **
\*                                                                      */

package org.scalajs.benchmark

import scala.scalajs.js
import scala.scalajs.js.annotation._

import scala.scalajs.reflect.annotation._
import scala.scalajs.reflect.Reflect

import org.scalajs.benchmark.dom._

object Benchmark {
  private val userAgent = {
    if (js.typeOf(js.Dynamic.global.process) != "undefined") {
      "Node.js"
    } else if (js.typeOf(js.Dynamic.global.navigator) != "undefined") {
      val userAgent = js.Dynamic.global.navigator.userAgent.asInstanceOf[String]
      if (userAgent.contains("Chrome"))
        "Chrome"
      else if (userAgent.contains("Firefox"))
        "Firefox"
      else
        "Unknown"
    } else {
      "Unknown"
    }
  }

  @JSExportTopLevel("setupHTMLBenchmark")
  def setupHTMLBenchmark(className: String): Unit = {
    val clazz = Reflect.lookupLoadableModuleClass(className + "$").getOrElse {
      throw new RuntimeException(s"Module $className does not exist")
    }
    clazz.loadModule().asInstanceOf[Benchmark].mainHTML()
  }
}

/** `Benchmark` base class based on the deprecated scala.testing.Benchmark.
 *
 *  The `run` method has to be defined by the user, who will perform the
 *  timed operation there.
 *
 *  This will run the benchmark a minimum of 5 times and for at least 2
 *  seconds.
 *
 *  @author Iulian Dragos, Burak Emir
 */
@EnableReflectiveInstantiation
abstract class Benchmark {

  private val performanceTime: js.Function0[Double] = {
    import js.Dynamic.{global => g}
    import js.DynamicImplicits._
    if (js.typeOf(g.performance) != "undefined" && g.performance.now) {
      () => g.performance.now().asInstanceOf[Double]
    } else if (js.typeOf(g.process) != "undefined") {
      { () =>
        val pair = g.process.hrtime().asInstanceOf[js.Tuple2[Double, Double]]
        (pair._1 * 1000.0) + (pair._2 / 1000000.0)
      }
    } else {
      () => (new js.Date).getTime()
    }
  }

  def main(args: Array[String]): Unit = {
    if (js.typeOf(js.Dynamic.global.window) == "undefined") {
      val result = report()
      println(result)

      if (js.typeOf(js.Dynamic.global.require) == "function") {
        val fs = js.Dynamic.global.require("fs")
        val name = reportConfig().takeWhile(Character.isLetterOrDigit(_))
        val fileName = s"./devirtualized-$name.csv"
        fs.appendFileSync(fileName, result + "\n")
      }
    }
  }

  def mainHTML(): Unit = {
    import DOM.document

    document.title = prefix

    val body = document.body

    val title = document.createElement("h1")
    title.textContent = prefix
    body.appendChild(title)

    val runButton =
      document.createElement("button").asInstanceOf[HTMLButtonElement]
    runButton.textContent = "Run benchmarks"
    body.appendChild(runButton)

    val statusText = document.createElement("pre")
    body.appendChild(statusText)

    runButton.onclick = { () =>
      runButton.enabled = false
      statusText.textContent = "Running ..."

      js.timers.setTimeout(10) {
        val status = {
          try {
            report()
          } catch {
            case th: Throwable => th.toString()
          }
        }
        statusText.textContent = status
        runButton.enabled = true
      }
    }
  }

  /** This method should be implemented by the concrete benchmark.
   *  It will be called by the benchmarking code for a number of times.
   *
   *  @see setUp
   *  @see tearDown
   */
  def run(): Unit

  /** Run the benchmark the specified number of milliseconds and return
   *  the mean execution time and SEM in microseconds.
   */
  def runBenchmark(timeMinimum: Long, runsMinimum: Int): Array[Double] = {
    var runs = 0
    var enoughTime = false
    val stopTime = performanceTime() + timeMinimum

    val samples = Array.newBuilder[Double]

    do {
      val startTime = performanceTime()
      run()
      val endTime = performanceTime()
      samples += (endTime - startTime) * 1000.0
      runs += 1
      enoughTime = endTime >= stopTime
    } while (!enoughTime || runs < runsMinimum)

    //meanAndSEM(samples.result())
    samples.result()
  }

  private def meanAndSEM(samples: Array[Double]): (Double, Double) = {
    val n = samples.length
    val mean = samples.sum / n
    val sem = standardErrorOfTheMean(samples, mean)
    (mean, sem)
  }

  private def standardErrorOfTheMean(samples: Array[Double],
      mean: Double): Double = {
    val n = samples.length.toDouble
    Math.sqrt(samples.map(xi => Math.pow(xi - mean, 2)).sum / (n * (n - 1)))
  }

  /** Prepare any data needed by the benchmark, but whose execution time
   *  should not be measured. This method is run before each call to the
   *  benchmark payload, 'run'.
   */
  def setUp(): Unit = ()

  /** Perform cleanup operations after each 'run'. For micro benchmarks,
   *  think about using the result of 'run' in a way that prevents the JVM
   *  to dead-code eliminate the whole 'run' method. For instance, print or
   *  write the results to a file. The execution time of this method is not
   *  measured.
   */
  def tearDown(): Unit = ()

  /** A string that is written at the beginning of the output line
   *  that contains the timings. By default, this is the class name.
   */
  def prefix: String = getClass().getName()

  def warmUp(): Unit = {
    runBenchmark(1000, 10)
  }

  def reportConfig(): String = {
    import js.Dynamic.{global => g}

    val reportPrefix =
      if (js.typeOf(g.ScalaJSBenchmarkPrefix) != "string") prefix + "-"
      else g.ScalaJSBenchmarkPrefix.asInstanceOf[String]
    reportPrefix + Benchmark.userAgent
  }

  def report(): String = {
    setUp()
    warmUp()
    //val (mean, sem) = runBenchmark(3000, 20)
    val results = runBenchmark(3000, 20)
    tearDown()

    //s"$reportPrefix${Benchmark.userAgent};$mean;$sem"

    results.map(r => s"${reportConfig()};$r").mkString("\n")
  }
}
