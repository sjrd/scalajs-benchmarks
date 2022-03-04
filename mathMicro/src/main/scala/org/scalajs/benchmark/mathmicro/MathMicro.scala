/*                     __                                               *\
**     ________ ___   / /  ___      __ ____  Scala.js Benchmarks        **
**    / __/ __// _ | / /  / _ | __ / // __/  (c) 2016, LAMP/EPFL        **
**  __\ \/ /__/ __ |/ /__/ __ |/_// /_\ \                               **
** /____/\___/_/ |_/____/_/ | |__/ /____/                               **
**                          |/____/                                     **
\*                                                                      */

package org.scalajs.benchmark.mathmicro

import org.scalajs.benchmark.Benchmark

object MathMicroDataSets {

  private val rng = new scala.util.Random(3168253622807457169L)

  val NaNs = Array.fill(100)(Double.NaN)

  val Specials = Array.tabulate(100) { i =>
    (i % 3) match {
      case 0 => Double.NaN
      case 1 => Double.PositiveInfinity
      case _ => Double.NegativeInfinity
    }
  }

  val Normals = Array.fill(100)(rng.nextDouble())
  val NormalsBits = Normals.map(java.lang.Double.doubleToLongBits(_))

  val Subnormals = Array.fill(100)(rng.nextDouble() * java.lang.Double.MIN_NORMAL)

  val MinusZerosAndOnes = Array.tabulate(100)(i => if (i % 2 == 0) -0.0 else 1.0)

  // Floats

  val FloatNaNs = Array.fill(100)(Float.NaN)

  val FloatSpecials = Array.tabulate(100) { i =>
    (i % 3) match {
      case 0 => Float.NaN
      case 1 => Float.PositiveInfinity
      case _ => Float.NegativeInfinity
    }
  }

  val FloatNormals = Array.fill(100)(rng.nextFloat())
  val FloatNormalsBits = FloatNormals.map(java.lang.Float.floatToIntBits(_))

  val FloatSubnormals = Array.fill(100)(rng.nextFloat() * java.lang.Float.MIN_NORMAL)

  val FloatMinusZerosAndOnes = Array.tabulate(100)(i => if (i % 2 == 0) -0.0f else 1.0f)

}

import MathMicroDataSets._

object MathMicroAll extends Benchmark {
  private val allBenches = Array[Benchmark](
      new RintMathMethod("NaNs", NaNs, Double.NaN),
      new RintMathMethod("Specials", Specials, Double.NaN),
      new RintMathMethod("Normals", Normals, 52.0),
      new RintMathMethod("Subnormals", Subnormals, 1.0),
      new RintMathMethod("MinusZerosAndOnes", MinusZerosAndOnes, 51.0),

      new RintSubnormalTrick("NaNs", NaNs, Double.NaN),
      new RintSubnormalTrick("Specials", Specials, Double.NaN),
      new RintSubnormalTrick("Normals", Normals, 52.0),
      new RintSubnormalTrick("Subnormals", Subnormals, 1.0),
      new RintSubnormalTrick("MinusZerosAndOnes", MinusZerosAndOnes, 51.0),

      new RintSplitting("NaNs", NaNs, Double.NaN),
      new RintSplitting("Specials", Specials, Double.NaN),
      new RintSplitting("Normals", Normals, 52.0),
      new RintSplitting("Subnormals", Subnormals, 1.0),
      new RintSplitting("MinusZerosAndOnes", MinusZerosAndOnes, 51.0),

      new RintSplittingInline("NaNs", NaNs, Double.NaN),
      new RintSplittingInline("Specials", Specials, Double.NaN),
      new RintSplittingInline("Normals", Normals, 52.0),
      new RintSplittingInline("Subnormals", Subnormals, 1.0),
      new RintSplittingInline("MinusZerosAndOnes", MinusZerosAndOnes, 51.0),

      new FloorMathMethod("NaNs", NaNs, Double.NaN),
      new FloorMathMethod("Specials", Specials, Double.NaN),
      new FloorMathMethod("Normals", Normals, 1.0),
      new FloorMathMethod("Subnormals", Subnormals, 1.0),
      new FloorMathMethod("MinusZerosAndOnes", MinusZerosAndOnes, 51.0),

      new FloorCustom("NaNs", NaNs, Double.NaN),
      new FloorCustom("Specials", Specials, Double.NaN),
      new FloorCustom("Normals", Normals, 1.0),
      new FloorCustom("Subnormals", Subnormals, 1.0),
      new FloorCustom("MinusZerosAndOnes", MinusZerosAndOnes, 51.0),

      new NextUpMathMethod("NaNs", NaNs, Double.NaN),
      new NextUpMathMethod("Specials", Specials, Double.NaN),
      new NextUpMathMethod("Normals", Normals, 49.18103203812996),
      new NextUpMathMethod("Subnormals", Subnormals, 1.0),
      new NextUpMathMethod("MinusZerosAndOnes", MinusZerosAndOnes, 51.0),

      new NextUpCustom("NaNs", NaNs, Double.NaN),
      new NextUpCustom("Specials", Specials, Double.NaN),
      new NextUpCustom("Normals", Normals, 49.18103203812996),
      new NextUpCustom("Subnormals", Subnormals, 1.0),
      new NextUpCustom("MinusZerosAndOnes", MinusZerosAndOnes, 51.0),

      new FloatNextUpMathMethod("NaNs", FloatNaNs, Float.NaN),
      new FloatNextUpMathMethod("Specials", FloatSpecials, Float.NaN),
      new FloatNextUpMathMethod("Normals", FloatNormals, 51.404415f),
      new FloatNextUpMathMethod("Subnormals", FloatSubnormals, 1.0f),
      new FloatNextUpMathMethod("MinusZerosAndOnes", FloatMinusZerosAndOnes, 51.0f),

      new FloatNextUpCustom("NaNs", FloatNaNs, Float.NaN),
      new FloatNextUpCustom("Specials", FloatSpecials, Float.NaN),
      new FloatNextUpCustom("Normals", FloatNormals, 51.404415f),
      new FloatNextUpCustom("Subnormals", FloatSubnormals, 1.0f),
      new FloatNextUpCustom("MinusZerosAndOnes", FloatMinusZerosAndOnes, 51.0f),

      new NumberHashCode("NaNs", NaNs, 0),
      new NumberHashCode("Specials", Specials, -2147483648),
      new NumberHashCode("Normals", Normals, 2144013960),
      new NumberHashCode("Subnormals", Subnormals, -1203097025),
      new NumberHashCode("MinusZerosAndOnes", MinusZerosAndOnes, 0),

      new LongBitsToDouble("Normals", NormalsBits, 49.181032038129956),
  )

  override def main(args: Array[String]): Unit = {
    for (bench <- allBenches)
      bench.main(args)
  }

  override def report(): String =
    allBenches.map(_.report()).mkString("\n")

  def run(): Unit = ???
}

class NumberHashCode(kind: String, dataSet: Array[Double], expectedResult: Int) extends Benchmark {
  override def prefix = s"NumberHashCode-$kind"

  def run(): Unit = {
    val dataSet = this.dataSet
    val len = dataSet.length

    var result = 0
    var i = 0
    while (i != len) {
      result ^= dataSet(i).hashCode()
      i += 1
    }
    if (result != expectedResult)
      throw new Exception(s"wrong result: expected $expectedResult but was $result")
  }
}

class LongBitsToDouble(kind: String, dataSet: Array[Long], expectedResult: Double) extends Benchmark {
  override def prefix = s"LongBitsToDouble-$kind"

  def run(): Unit = {
    val dataSet = this.dataSet
    val len = dataSet.length

    var result = 1.0
    var i = 0
    while (i != len) {
      result += java.lang.Double.longBitsToDouble(dataSet(i))
      i += 1
    }
    if (result != expectedResult)
      throw new Exception(s"wrong result: expected $expectedResult but was $result")
  }
}

object RintImplementations {

  /*def statusQuo(a: scala.Double): scala.Double = {
    val rounded = js.Math.round(a)
    val mod = a % 1.0
    // The following test is also false for specials (0's, Infinities and NaN)
    if (mod == 0.5 || mod == -0.5) {
      // js.Math.round(a) rounds up but we have to round to even
      if (rounded % 2.0 == 0.0) rounded
      else rounded - 1.0
    } else {
      rounded
    }
  }*/

  @inline
  def subnormalTrick(a: scala.Double): scala.Double =
    a * Double.MinPositiveValue / Double.MinPositiveValue

  def splitting(x: Double): Double = {
    /* We apply the technique described in Section II of
     *   Claude-Pierre Jeannerod, Jean-Michel Muller, Paul Zimmermann.
     *   On various ways to split a floating-point number.
     *   ARITH 2018 - 25th IEEE Symposium on Computer Arithmetic,
     *   Jun 2018, Amherst (MA), United States.
     *   pp.53-60, 10.1109/ARITH.2018.8464793. hal-01774587v2
     * available at
     *   https://hal.inria.fr/hal-01774587v2/document
     * with β = 2, p = 53, and C = 2^(p-1) = 2^52.
     *
     * That is only valid for values x <= 2^52. Fortunately, all values that
     * are >= 2^52 are already integers, so we can return them as is.
     *
     * We cannot use "the 1.5 trick" with C = 2^(p-1) + 2^(p-2) to handle
     * negative numbers, because that would further reduce the range of valid
     * `x` to maximum 2^51, although we actually need it up to 2^52. Therefore,
     * we have a separate branch for negative numbers. This also allows to
     * gracefully deal with the fact that we need to return -0.0 for values in
     * the range [-0.5,-0.0).
     *
     * ---
     *
     * Even though there are several conditions, this implementation remains
     * much faster (20x for normal inputs) than the subnormal trick
     *   x * MinPositiveValue / MinPositiveValue
     * because using subnormal values is much slower in hardware.
     */
    val C = 4503599627370496.0 // 2^52
    if (x > 0) {
      if (x >= C) x
      else (C + x) - C
    } else if (x < 0) {
      // do not "optimize" as `C - (C - a)`, as it would return +0.0 where it should return -0.0
      if (x <= -C) x
      else -((C - x) - C)
    } else {
      // Handling zeroes here avoids the need to distinguish +0.0 from -0.0
      x // 0.0, -0.0 and NaN
    }
  }

  @inline
  def splittingInline(x: Double): Double = {
    val C = 4503599627370496.0 // 2^52
    if (x > 0) {
      if (x >= C) x
      else (C + x) - C
    } else if (x < 0) {
      if (x <= -C) x
      else -((C - x) - C)
    } else {
      x
    }
  }

}

class RintMathMethod(kind: String, dataSet: Array[Double], expectedResult: Double) extends Benchmark {
  override def prefix = s"RintMathMethod-$kind"

  def run(): Unit = {
    val dataSet = this.dataSet
    val len = dataSet.length

    var result = 1.0
    var i = 0
    while (i != len) {
      result += Math.rint(dataSet(i))
      i += 1
    }
    if (!expectedResult.equals(result))
      throw new Exception(s"wrong result: expected $expectedResult but was $result")
  }
}

class RintSubnormalTrick(kind: String, dataSet: Array[Double], expectedResult: Double) extends Benchmark {
  override def prefix = s"RintSubnormalTrick-$kind"

  def run(): Unit = {
    val dataSet = this.dataSet
    val len = dataSet.length

    var result = 1.0
    var i = 0
    while (i != len) {
      result += RintImplementations.subnormalTrick(dataSet(i))
      i += 1
    }
    if (!expectedResult.equals(result))
      throw new Exception(s"wrong result: expected $expectedResult but was $result")
  }
}

class RintSplitting(kind: String, dataSet: Array[Double], expectedResult: Double) extends Benchmark {
  override def prefix = s"RintSplitting-$kind"

  def run(): Unit = {
    val dataSet = this.dataSet
    val len = dataSet.length

    var result = 1.0
    var i = 0
    while (i != len) {
      result += RintImplementations.splitting(dataSet(i))
      i += 1
    }
    if (!expectedResult.equals(result))
      throw new Exception(s"wrong result: expected $expectedResult but was $result")
  }
}

class RintSplittingInline(kind: String, dataSet: Array[Double], expectedResult: Double) extends Benchmark {
  override def prefix = s"RintSplittingInline-$kind"

  def run(): Unit = {
    val dataSet = this.dataSet
    val len = dataSet.length

    var result = 1.0
    var i = 0
    while (i != len) {
      result += RintImplementations.splittingInline(dataSet(i))
      i += 1
    }
    if (!expectedResult.equals(result))
      throw new Exception(s"wrong result: expected $expectedResult but was $result")
  }
}

object FloorImplementation {
  def ceil(x: scala.Double): scala.Double = {
    if (x > 0.0)
      posCeil(x)
    else if (x < 0.0)
      -posFloor(-x)
    else
      x
  }

  def floor(x: scala.Double): scala.Double = {
    if (x > 0.0)
      posFloor(x)
    else if (x < 0.0)
      -posCeil(-x)
    else
      x
  }

  @inline private def posCeil(x: scala.Double): scala.Double = {
    val f = posFloor(x)
    if (f == x) f else f + 1.0
  }

  @inline private def posFloor(x: scala.Double): scala.Double = {
    val twoPow52 = 4.503599627370496e15
    val twoPow53 = 9.007199254740992e15
    if (x < twoPow52) {
      val C = twoPow53 - x
      (C + (x - 0.5)) - C
    } else {
      x
    }
  }
}

class FloorMathMethod(kind: String, dataSet: Array[Double], expectedResult: Double) extends Benchmark {
  override def prefix = s"FloorMathMethod-$kind"

  def run(): Unit = {
    val dataSet = this.dataSet
    val len = dataSet.length

    var result = 1.0
    var i = 0
    while (i != len) {
      result += Math.floor(dataSet(i))
      i += 1
    }
    if (!expectedResult.equals(result))
      throw new Exception(s"wrong result: expected $expectedResult but was $result")
  }
}

class FloorCustom(kind: String, dataSet: Array[Double], expectedResult: Double) extends Benchmark {
  override def prefix = s"FloorCustom-$kind"

  def run(): Unit = {
    val dataSet = this.dataSet
    val len = dataSet.length

    var result = 1.0
    var i = 0
    while (i != len) {
      result += FloorImplementation.floor(dataSet(i))
      i += 1
    }
    if (!expectedResult.equals(result))
      throw new Exception(s"wrong result: expected $expectedResult but was $result")
  }
}

object NextUpImplementation {
  def nextUp(c: scala.Double): scala.Double = {
    val u = 1.1102230246251565e-16 // 2^(-53)
    val invu = 9007199254740992.0 // 2^53
    val phi = 1.1102230246251568e-16 // nextUp(u)
    val eta = scala.Double.MinPositiveValue // 2^(-1047)
    val threshold1 = 0.5 * (invu * invu) * eta
    val threshold2 = eta * invu
    if (c >= 0.0) {
      if (c >= threshold1) {
        c + (phi * c)
      } else if (c < threshold2) {
        c + eta
      } else {
        val C = c * invu
        (C + (phi * C)) * u
      }
    } else {
      if (c <= -threshold1) {
        if (c == scala.Double.NegativeInfinity)
          -scala.Double.MaxValue // special case
        else
          c - (phi * c)
      } else if (c > -threshold2) {
        if (c == -eta)
          -0.0 // special case
        else
          c + eta
      } else {
        val C = c * invu
        (C - (phi * C)) * u
      }
    }
  }

  def nextUp(c: scala.Float): scala.Float = {
    val u = 5.960464477539063e-8f // 2^(-24)
    val invu = 16777216.0f // 2^24
    val phi = 5.960465e-8f // nextUp(u)
    val eta = scala.Float.MinPositiveValue // 2^(-149)
    val threshold1 = 0.5f * (invu * invu) * eta
    val threshold2 = eta * invu
    if (c >= 0.0f) {
      if (c >= threshold1) {
        c + (phi * c)
      } else if (c < threshold2) {
        c + eta
      } else {
        val C = c * invu
        (C + (phi * C)) * u
      }
    } else {
      if (c <= -threshold1) {
        if (c == scala.Float.NegativeInfinity)
          -scala.Float.MaxValue // special case
        else
          c - (phi * c)
      } else if (c > -threshold2) {
        if (c == -eta)
          -0.0f // special case
        else
          c + eta
      } else {
        val C = c * invu
        (C - (phi * C)) * u
      }
    }
  }
}

class NextUpMathMethod(kind: String, dataSet: Array[Double], expectedResult: Double) extends Benchmark {
  override def prefix = s"NextUpMathMethod-$kind"

  def run(): Unit = {
    val dataSet = this.dataSet
    val len = dataSet.length

    var result = 1.0
    var i = 0
    while (i != len) {
      result += Math.nextUp(dataSet(i))
      i += 1
    }
    if (!expectedResult.equals(result))
      throw new Exception(s"wrong result: expected $expectedResult but was $result")
  }
}

class NextUpCustom(kind: String, dataSet: Array[Double], expectedResult: Double) extends Benchmark {
  override def prefix = s"NextUpCustom-$kind"

  def run(): Unit = {
    val dataSet = this.dataSet
    val len = dataSet.length

    var result = 1.0
    var i = 0
    while (i != len) {
      result += NextUpImplementation.nextUp(dataSet(i))
      i += 1
    }
    if (!expectedResult.equals(result))
      throw new Exception(s"wrong result: expected $expectedResult but was $result")
  }
}

class FloatNextUpMathMethod(kind: String, dataSet: Array[Float], expectedResult: Float) extends Benchmark {
  override def prefix = s"FloatNextUpMathMethod-$kind"

  def run(): Unit = {
    val dataSet = this.dataSet
    val len = dataSet.length

    var result = 1.0f
    var i = 0
    while (i != len) {
      result += Math.nextUp(dataSet(i))
      i += 1
    }
    if (!expectedResult.equals(result))
      throw new Exception(s"wrong result: expected $expectedResult but was $result")
  }
}

class FloatNextUpCustom(kind: String, dataSet: Array[Float], expectedResult: Float) extends Benchmark {
  override def prefix = s"FloatNextUpCustom-$kind"

  def run(): Unit = {
    val dataSet = this.dataSet
    val len = dataSet.length

    var result = 1.0f
    var i = 0
    while (i != len) {
      result += NextUpImplementation.nextUp(dataSet(i))
      i += 1
    }
    if (!expectedResult.equals(result))
      throw new Exception(s"wrong result: expected $expectedResult but was $result")
  }
}
