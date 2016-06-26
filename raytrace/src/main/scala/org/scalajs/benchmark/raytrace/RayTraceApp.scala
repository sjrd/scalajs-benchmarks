/*                     __                                               *\
**     ________ ___   / /  ___      __ ____  Scala.js Benchmarks        **
**    / __/ __// _ | / /  / _ | __ / // __/  (c) 2013, Jonas Fonseca    **
**  __\ \/ /__/ __ |/ /__/ __ |/_// /_\ \                               **
** /____/\___/_/ |_/____/_/ | |__/ /____/                               **
**                          |/____/                                     **
\*                                                                      */

package org.scalajs.benchmark.raytrace

import org.scalajs.benchmark.dom._
import scala.scalajs.js

trait RayTraceApp extends org.scalajs.benchmark.BenchmarkApp {

  def onClick() {
    val config = new EngineConfiguration(
      imageWidth = int("imageWidth"),
      imageHeight = int("imageHeight"),
      pixelWidth = int("pixelSize"),
      pixelHeight = int("pixelSize"),
      rayDepth = 2,
      renderDiffuse = bool("renderDiffuse"),
      renderShadows = bool("renderShadows"),
      renderHighlights = bool("renderHighlights"),
      renderReflections = bool("renderReflections")
    )

    val canvas = $("canvasContext").asInstanceOf[HTMLCanvasElement]
    canvas.width = config.imageWidth
    canvas.height = config.imageHeight
    val canvasContext = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

    time {
      new RenderScene().renderScene(config, canvasContext)
    }
  }

}