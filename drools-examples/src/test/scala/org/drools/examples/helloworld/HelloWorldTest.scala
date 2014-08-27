package org.drools.examples.helloworld

import java.io.{ByteArrayOutputStream, PrintStream}

import org.scalatest.WordSpec
import org.scalatest.MustMatchers

/**
 * Created by Simon on 2014/8/27
 */
class HelloWorldTest extends WordSpec with MustMatchers {
  private final val NL: String = System.getProperty("line.separator")

  "hello world" must {

    "rule fire ok" in {

      val bs = new ByteArrayOutputStream()
      val out = new PrintStream(bs)

      HelloWorldExample.go(out)

      bs.toString mustBe "Hello World" + NL +
        "Goodbye cruel world" + NL
    }
  }
}
