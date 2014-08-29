package org.drools.examples.traits

import org.drools.core.io.impl.ClassPathResource
import org.kie.api.io.ResourceType
import org.kie.internal.KnowledgeBaseFactory
import org.kie.internal.builder.{KnowledgeBuilder, KnowledgeBuilderFactory}
import org.kie.internal.runtime.StatefulKnowledgeSession

/**
 * Created by Simon on 2014/8/29
 */
object TraitExample extends App {

  run("noTraits.drl")

 // run("traitsDon.drl")

  run("multipleTraits.drl")

 // run("traitsMixins.drl")

  run("traitsShed.drl")

  private def getSession(drl: String): StatefulKnowledgeSession = {
    val kBuilder: KnowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder
    kBuilder.add(new ClassPathResource("org/drools/examples/traits/" + drl), ResourceType.DRL)
    if (kBuilder.hasErrors) {
      System.err.println(kBuilder.getErrors.toString)
    }
    val kBase = KnowledgeBaseFactory.newKnowledgeBase
    kBase.addKnowledgePackages(kBuilder.getKnowledgePackages)
    kBase.newStatefulKnowledgeSession
  }

  def run(demo: String) {
    val kSession: StatefulKnowledgeSession = getSession(demo)
    kSession.fireAllRules
    val c = kSession.getObjects
    System.out.println("------------------------- " + c.size + " ----------------------")
    import scala.collection.JavaConversions._
    for (o <- c) {
      System.out.println(" \t --- " + o)
    }
    System.out.println("-----------------------------------------------------------------")
    kSession.dispose()
  }
}

import org.drools.core.factmodel.traits.Trait

@Trait(impl = classOf[ScholarImpl[_]])
trait Scholar[K] {
  def learn(subject: String)
}

import org.drools.core.factmodel.traits.Thing

class ScholarImpl[K](core: Thing[K]) extends Scholar[K] {

  def learn(subject: String) {
    System.out.println("I " + core.getFields.get("name") + ", now know everything about " + subject)
  }

}

