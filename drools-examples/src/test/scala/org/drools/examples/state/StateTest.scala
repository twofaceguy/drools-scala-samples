package org.drools.examples.state

import java.io.{PrintStream, ByteArrayOutputStream}

import org.drools.examples.helloworld.HelloWorldExample
import org.scalatest.{MustMatchers, WordSpec}

/**
 * Created by Simon on 2014/8/28
 */
class StateTest extends WordSpec with MustMatchers {
  private final val NL: String = System.getProperty("line.separator")

  "state " must {

    "Salience" in {

      val bs = new ByteArrayOutputStream()
      val out = new PrintStream(bs)

      StateExampleUsingSalience.go(out)

      bs.toString mustBe "A finished" + NL +
        "B finished" + NL +
        "C finished" + NL +
        "D finished" + NL
    }

    "AgendaGroup" in {

      val bs = new ByteArrayOutputStream()
      val out = new PrintStream(bs)

      StateExampleUsingAgendaGroup.go(out)

      bs.toString mustBe "A finished" + NL +
        "B finished" + NL +
        "C finished" + NL +
        "D finished" + NL
    }
  }
}
