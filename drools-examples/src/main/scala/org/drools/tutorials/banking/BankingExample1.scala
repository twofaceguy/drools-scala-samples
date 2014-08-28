package org.drools.tutorials.banking

/**
 * Created by Simon on 2014/8/28
 */
object BankingExample1 extends App{
  new RuleRunner().runRules(List("Example1.drl"), List())
}
