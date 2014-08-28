package org.drools.tutorials.banking


/**
 * Created by Simon on 2014/8/28
 */
object BankingExample5 extends App{

  import Cashflow._

  val cashflows = List(
    TypedCashflow(SimpleDate("01/01/2007"), CREDIT, 300.00),
    TypedCashflow(SimpleDate("05/01/2007"), CREDIT, 100.00),
    TypedCashflow(SimpleDate("11/01/2007"), CREDIT, 500.00),
    TypedCashflow(SimpleDate("07/01/2007"), DEBIT, 800.00),
    TypedCashflow(SimpleDate("02/01/2007"), DEBIT, 400.00)
  )
  new RuleRunner().runRules(List("Example5.drl"), cashflows)
}
