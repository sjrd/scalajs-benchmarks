package org.scalajs.benchmark

import scalajs.js

class Benchmark (
    val name: String,
    val doWarmup: Boolean,
    val doDeterministic: Boolean,
    val deterministicIterations: Int,
    val run: js.Function0[Unit],
    val setup: js.UndefOr[js.Function0[Unit]] = js.undefined,
    val tearDown: js.UndefOr[js.Function0[Unit]] = js.undefined,
    val rmsResult: js.UndefOr[Double] = js.undefined,
    val minIterations: js.UndefOr[Int] = js.undefined
) extends js.Object
