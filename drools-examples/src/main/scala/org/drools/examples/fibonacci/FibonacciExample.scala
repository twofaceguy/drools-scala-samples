package org.drools.examples.fibonacci

import java.io.PrintStream

import org.kie.api.KieServices
import org.kie.api.runtime.{KieContainer, KieSession}

import scala.beans.BeanProperty

/**
 * Created by Simon on 2014/8/28
 */
object FibonacciExample extends App {
  go(System.out)

  def go(out: PrintStream) {

    val kc: KieContainer = KieServices.Factory.get.getKieClasspathContainer
    val ksession: KieSession = kc.newKieSession("FibonacciKS")
    ksession.setGlobal("out", out)
    ksession.insert(Fibonacci(50))
    ksession.fireAllRules
    ksession.dispose()
  }
}

case class Fibonacci(@BeanProperty sequence: Int, @BeanProperty var value: Long = -1) {
  override def toString: String = s"Fibonacci($sequence/$value)"

  def this(seq: Int) = this(seq, -1)
}
