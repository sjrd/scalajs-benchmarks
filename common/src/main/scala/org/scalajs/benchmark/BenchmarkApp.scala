/*                     __                                               *\
**     ________ ___   / /  ___      __ ____  Scala.js Benchmarks        **
**    / __/ __// _ | / /  / _ | __ / // __/  (c) 2013, Jonas Fonseca    **
**  __\ \/ /__/ __ |/ /__/ __ |/_// /_\ \                               **
** /____/\___/_/ |_/____/_/ | |__/ /____/                               **
**                          |/____/                                     **
\*                                                                      */

package org.scalajs.benchmark

import scala.scalajs.js
import org.scalajs.benchmark.dom._

trait BenchmarkApp {

  def onClick(): Unit

  def init() {
    val button = $("run")

    if (button != null) {
      button.onclick = onClick _
    }
  }

  def time(run: => Unit) {
    val before = new js.Date()
    run
    val after = new js.Date()
    val timeElement = $("time")
    if (timeElement != null) {
      val elapsedTime = after.getTime() - before.getTime()

      timeElement.innerHTML = elapsedTime.toString()
    }
  }

  def $(id: String): DOMElement =
    js.Dynamic.global.document.getElementById(id).asInstanceOf[DOMElement]

  def int(id: String): Int =
    js.parseInt($(id).asInstanceOf[HTMLInputElement].value).toInt

  def bool(id: String): Boolean =
    $(id).asInstanceOf[HTMLCheckboxElement].checked.toString == "true"
}