package org.drools.examples.decisiontable

import java.io.{IOException, InputStream}

import org.drools.core.io.impl.ByteArrayResource
import org.drools.core.util.IoUtils
import org.drools.decisiontable.ExternalSpreadsheetCompiler
import org.kie.api.io.ResourceType
import org.kie.internal.KnowledgeBaseFactory
import org.kie.internal.builder.{KnowledgeBuilder, KnowledgeBuilderFactory}
import org.kie.internal.io.ResourceFactory
import org.kie.internal.runtime.StatefulKnowledgeSession

/**
 * Created by Simon on 2014/8/28
 */
object PricingRuleTemplateExample extends App{

  executeExample

  private def executeExample: Int = {
    val kbase = this.buildKBase
    val ksession: StatefulKnowledgeSession = kbase.newStatefulKnowledgeSession
    val driver: Driver = new Driver
    val policy: Policy = new Policy
    ksession.insert(driver)
    ksession.insert(policy)
    ksession.fireAllRules
    System.out.println("BASE PRICE IS: " + policy.getBasePrice)
    System.out.println("DISCOUNT IS: " + policy.getDiscountPercent)
    ksession.dispose()
    policy.getBasePrice
  }

  /**
   * Creates a new kbase containing the rules generated from the xls file and
   * the templates.
   * @return
   * @throws IOException
   */
  private def buildKBase = {
    val converter: ExternalSpreadsheetCompiler = new ExternalSpreadsheetCompiler
    var basePricingDRL: String = null
    var promotionalPricingDRL: String = null
    try {
      basePricingDRL = converter.compile(getSpreadsheetStream, getBasePricingRulesStream, 10, 3)
      promotionalPricingDRL = converter.compile(getSpreadsheetStream, getPromotionalPricingRulesStream, 30, 3)
    }
    catch {
      case e: IOException =>
        throw new IllegalArgumentException("Invalid spreadsheet stream.", e)

    }
    val kbuilder: KnowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder
    kbuilder.add(new ByteArrayResource(basePricingDRL.getBytes(IoUtils.UTF8_CHARSET)), ResourceType.DRL)
    kbuilder.add(new ByteArrayResource(promotionalPricingDRL.getBytes(IoUtils.UTF8_CHARSET)), ResourceType.DRL)
    if (kbuilder.hasErrors) {
      System.out.println("Error compiling resources:")
      val errors = kbuilder.getErrors.iterator
      while (errors.hasNext) {
        System.out.println("\t" + errors.next.getMessage)
      }
      throw new IllegalStateException("Error compiling resources")
    }
    val kbase = KnowledgeBaseFactory.newKnowledgeBase
    kbase.addKnowledgePackages(kbuilder.getKnowledgePackages)
    kbase
  }

  private def getSpreadsheetStream: InputStream =
    ResourceFactory.newClassPathResource("org/drools/examples/decisiontable/ExamplePolicyPricing.xls").getInputStream

  private def getBasePricingRulesStream: InputStream =
    ResourceFactory.newClassPathResource("org/drools/examples/decisiontable/BasePricing.drt").getInputStream


  private def getPromotionalPricingRulesStream: InputStream =
    ResourceFactory.newClassPathResource("org/drools/examples/decisiontable/PromotionalPricing.drt").getInputStream


}
