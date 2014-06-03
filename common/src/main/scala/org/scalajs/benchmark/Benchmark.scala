/*                     __                                               *\
**     ________ ___   / /  ___      __ ____  Scala.js Benchmarks        **
**    / __/ __// _ | / /  / _ | __ / // __/  (c) 2003-2013, LAMP/EPFL   **
**  __\ \/ /__/ __ |/ /__/ __ |/_// /_\ \    (c) 2013, Jonas Fonseca    **
** /____/\___/_/ |_/____/_/ | |__/ /____/                               **
**                          |/____/                                     **
\*                                                                      */

package org.scalajs.benchmark

import scala.compat.Platform
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport

@JSExport
object Benchmark {
  val benchmarks = js.Array[Benchmark]()
  val benchmarkApps = js.Array[BenchmarkApp]()

  def add(benchmark: Benchmark) {
    benchmarks.push(benchmark)
    if (benchmark.isInstanceOf[BenchmarkApp]) {
      benchmarkApps.push(benchmark.asInstanceOf[BenchmarkApp])
    }
  }

  @JSExport
  def runBenchmarks() {
    benchmarks.foreach { _.report }
  }

  @JSExport
  def initBenchmarkApps() {
    benchmarkApps.foreach { _.init }
  }
}

/** `Benchmark` base class based on the deprecated scala.testing.Benchmark.
 *
 *  The `run` method has to be defined by the user, who will perform the
 *  timed operation there.
 *
 *  This will run the benchmark 5 times, forcing a garbage collection
 *  between runs, and printing the execution times to stdout.
 *
 *  @author Iulian Dragos, Burak Emir
 */
abstract class Benchmark extends js.JSApp {

  def main(): Unit = report()

  /** This method should be implemented by the concrete benchmark.
   *  It will be called by the benchmarking code for a number of times.
   *
   *  @see setUp
   *  @see tearDown
   */
  def run()

  /** Run the benchmark the specified number of milliseconds and return
   *  the average execution time in microseconds.
   */
  def runBenchmark(timeMinimum: Long): Double = {
    var runs = 0
    val startTime = Platform.currentTime
    var stopTime = startTime + timeMinimum
    var currentTime = startTime

    do {
      run()
      runs += 1
      currentTime = Platform.currentTime
    } while (currentTime < stopTime)

    val elapsed = currentTime - startTime
    1000.0 * elapsed / runs
  }

  /** Prepare any data needed by the benchmark, but whose execution time
   *  should not be measured. This method is run before each call to the
   *  benchmark payload, 'run'.
   */
  def setUp() {}

  /** Perform cleanup operations after each 'run'. For micro benchmarks,
   *  think about using the result of 'run' in a way that prevents the JVM
   *  to dead-code eliminate the whole 'run' method. For instance, print or
   *  write the results to a file. The execution time of this method is not
   *  measured.
   */
  def tearDown() {}

  /** A string that is written at the beginning of the output line
   *  that contains the timings. By default, this is the class name.
   */
  def prefix: String = getClass().getName()

  def warmUp {
    runBenchmark(100)
  }

  @JSExport
  def report() {
    setUp
    warmUp
    val avg = runBenchmark(2000)
    tearDown

    println(s"$prefix: $avg us")
  }
}
