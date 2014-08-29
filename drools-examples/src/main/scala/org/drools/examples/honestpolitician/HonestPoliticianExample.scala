package org.drools.examples.honestpolitician

import org.kie.api.KieServices
import org.kie.api.runtime.{KieSession, KieContainer}

import scala.beans.BeanProperty

/**
 * Created by Simon on 2014/8/29
 */
object HonestPoliticianExample extends App {

  val kc: KieContainer = KieServices.Factory.get.getKieClasspathContainer
  System.out.println(kc.verify.getMessages.toString)

  val ksession: KieSession = kc.newKieSession("HonestPoliticianKS")
  val p1 = Politician("President of Umpa Lumpa", honest = true)
  val p2 = Politician("Prime Minster of Cheeseland", honest = true)
  val p3 = Politician("Tsar of Pringapopaloo", honest = true)
  val p4 = Politician("Omnipotence Om", honest = true)

  ksession.insert(p1)
  ksession.insert(p2)
  ksession.insert(p3)
  ksession.insert(p4)

  ksession.fireAllRules
  ksession.dispose()
}


class Hope

case class Politician(@BeanProperty name: String, @BeanProperty var honest: Boolean) {
  def isHonest = honest
}