package org.drools.tutorials.banking


/**
 * Created by Simon on 2014/8/28
 */
object BankingExample2 extends App {
  val numbers = List(wrap(3), wrap(1), wrap(4), wrap(1), wrap(5))
  new RuleRunner().runRules(List("Example2.drl"), numbers)

  private def wrap(i: Int): Integer = {
    new Integer(i)
  }
}
