package org.drools.examples.decisiontable

import java.util.Arrays

import org.kie.api.KieServices
import org.kie.api.runtime.{KieContainer, StatelessKieSession}

/**
 * Created by Simon on 2014/8/28
 */
object PricingRuleDTExample extends App{
  executeExample

  def executeExample: Int = {
    val kc: KieContainer = KieServices.Factory.get.getKieClasspathContainer
    System.out.println(kc.verify.getMessages.toString)
    val ksession: StatelessKieSession = kc.newStatelessKieSession("DecisionTableKS")
    val driver = Driver()
    val policy = Policy()

    //todo: can't work
    ksession.execute(Arrays.asList(Arrays.asList(driver, policy).toArray))

    System.out.println("BASE PRICE IS: " + policy.getBasePrice)
    System.out.println("DISCOUNT IS: " + policy.getDiscountPercent)
    policy.getBasePrice
  }
}