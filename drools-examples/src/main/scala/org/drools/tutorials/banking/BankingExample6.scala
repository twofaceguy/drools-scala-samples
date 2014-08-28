package org.drools.tutorials.banking

/**
 * Created by Simon on 2014/8/28
 */
object BankingExample6 extends App {
  val acc1 = Account(1)
  val acc2 = Account(2)
  val cashflows = List(
    AllocatedCashflow(acc1, SimpleDate("01/01/2007"), Cashflow.CREDIT, 300.00),
    AllocatedCashflow(acc1, SimpleDate("05/02/2007"), Cashflow.CREDIT, 100.00),
    AllocatedCashflow(acc2, SimpleDate("11/03/2007"), Cashflow.CREDIT, 500.00),
    AllocatedCashflow(acc1, SimpleDate("07/02/2007"), Cashflow.DEBIT, 800.00),
    AllocatedCashflow(acc2, SimpleDate("02/03/2007"), Cashflow.DEBIT, 400.00),
    AllocatedCashflow(acc1, SimpleDate("01/04/2007"), Cashflow.CREDIT, 200.00),
    AllocatedCashflow(acc1, SimpleDate("05/04/2007"), Cashflow.CREDIT, 300.00),
    AllocatedCashflow(acc2, SimpleDate("11/05/2007"), Cashflow.CREDIT, 700.00),
    AllocatedCashflow(acc1, SimpleDate("07/05/2007"), Cashflow.DEBIT, 900.00),
    AllocatedCashflow(acc2, SimpleDate("02/05/2007"), Cashflow.DEBIT, 100.00))

  new RuleRunner().runRules(Array[String]("Example6.drl"), cashflows)
}
