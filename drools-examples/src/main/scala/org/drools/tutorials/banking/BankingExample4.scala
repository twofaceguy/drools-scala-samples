package org.drools.tutorials.banking

/**
 * Created by Simon on 2014/8/28
 */
object BankingExample4 extends App {
  val cashflows = List(
    Cashflow(SimpleDate("01/01/2007"), 300.00),
    Cashflow(SimpleDate("05/01/2007"), 100.00),
    Cashflow(SimpleDate("11/01/2007"), 500.00),
    Cashflow(SimpleDate("07/01/2007"), 800.00),
    Cashflow(SimpleDate("02/01/2007"), 400.00))
  new RuleRunner().runRules(List("Example4.drl"), cashflows)
}
