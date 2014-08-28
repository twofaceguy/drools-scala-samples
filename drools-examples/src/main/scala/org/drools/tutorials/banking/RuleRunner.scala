package org.drools.tutorials.banking

import org.kie.api.io.ResourceType
import org.kie.internal.KnowledgeBaseFactory
import org.kie.internal.builder.KnowledgeBuilderFactory
import org.kie.internal.io.ResourceFactory
import org.kie.internal.runtime.StatefulKnowledgeSession

/**
 * Created by Simon on 2014/8/28
 */
class RuleRunner {
  def runRules(rules: Seq[String], facts: Seq[Any]) {
    val kbase = KnowledgeBaseFactory.newKnowledgeBase
    val kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder()

    rules.foreach { ruleFile =>
      System.out.println("Loading file: " + ruleFile)
      kbuilder.add(ResourceFactory.newClassPathResource(ruleFile, classOf[RuleRunner]), ResourceType.DRL)
    }

    val pkgs = kbuilder.getKnowledgePackages
    kbase.addKnowledgePackages(pkgs)
    val ksession: StatefulKnowledgeSession = kbase.newStatefulKnowledgeSession

    facts.foreach { fact =>
      System.out.println("Inserting fact: " + fact)
      ksession.insert(fact)
    }
    ksession.fireAllRules
  }
}
