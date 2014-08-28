package org.drools.examples.fibonacci

import java.io.{PrintStream, ByteArrayOutputStream}

import org.scalatest.{MustMatchers, WordSpec}

/**
 * Created by Simon on 2014/8/28
 */
class FibonacciTest extends WordSpec with MustMatchers {

  private final val NL: String = System.getProperty("line.separator")
  "Fibonacci " must {

    "Fibonacci 50" in {

      val bs = new ByteArrayOutputStream()
      val out = new PrintStream(bs)

      FibonacciExample.go(out)

      bs.toString.split(NL).toList.reverse.head mustBe "50 == 12586269025"
    }

  }
}
