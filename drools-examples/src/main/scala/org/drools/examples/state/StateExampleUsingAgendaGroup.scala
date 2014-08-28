package org.drools.examples.state

import java.io.PrintStream

import org.kie.api.KieServices
import org.kie.api.runtime.{KieSession, KieContainer}

/**
 * Created by Simon on 2014/8/28
 */
object StateExampleUsingAgendaGroup extends App{

  go(System.out)

  def  go( out:PrintStream) {
    // KieServices is the factory for all KIE services
    val ks: KieServices = KieServices.Factory.get

    // From the kie services, a container is created from the classpath
    val kc: KieContainer = ks.getKieClasspathContainer

    // From the container, a session is created based on
    // its definition and configuration in the META-INF/kmodule.xml file
    val ksession: KieSession = kc.newKieSession("StateAgendaGroupKS")

    ksession.setGlobal("out", out)


    // To setup a file based audit logger, uncomment the next line
    // KieRuntimeLogger logger = ks.getLoggers().newFileLogger( ksession, "./state" );

    val a: State = new State("A")
    val b: State = new State("B")
    val c: State = new State("C")
    val d: State = new State("D")

    ksession.insert(a)
    ksession.insert(b)
    ksession.insert(c)
    ksession.insert(d)

    ksession.fireAllRules
    // logger.close();
    ksession.dispose()
  }
}
