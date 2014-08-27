package org.drools.examples.helloworld

import java.io.PrintStream
import java.util

import org.kie.api.KieServices
import org.kie.api.event.rule.{DebugAgendaEventListener, DebugRuleRuntimeEventListener}
import org.kie.api.runtime.{KieContainer, KieSession}

/**
 * Created by Simon on 2014/8/27
 */
object HelloWorldExample extends App{

  go(System.out)

  def  go( out:PrintStream) {

    // KieServices is the factory for all KIE services
    val ks: KieServices = KieServices.Factory.get

    // From the kie services, a container is created from the classpath
    val kc: KieContainer = ks.getKieClasspathContainer

    // From the container, a session is created based on
    // its definition and configuration in the META-INF/kmodule.xml file
    val ksession: KieSession = kc.newKieSession("HelloWorldKS")


    // Once the session is created, the application can interact with it
    // In this case it is setting a global as defined in the
    // org/drools/examples/helloworld/HelloWorld.drl file
    ksession.setGlobal("list", new util.ArrayList[AnyRef])
    ksession.setGlobal("out", out)



    // The application can also setup listeners
    ksession.addEventListener(new DebugAgendaEventListener)
    ksession.addEventListener(new DebugRuleRuntimeEventListener)

    // To setup a file based audit logger, uncomment the next line
    // KieRuntimeLogger logger = ks.getLoggers().newFileLogger( ksession, "./helloworld" );
    // To setup a ThreadedFileLogger, so that the audit view reflects events whilst debugging,
    // uncomment the next line
    // KieRuntimeLogger logger = ks.getLoggers().newThreadedFileLogger( ksession, "./helloworld", 1000 );


    // The application can insert facts into the session
    val message: Message = Message("Hello World", Message.HELLO)
    ksession.insert(message)

    // and fire the rules
    ksession.fireAllRules

    // Remove comment if using logging
    // logger.close();
    // and then dispose the session
    ksession.dispose()
  }

}

//for scala: Message is immutable,if you want to modify it in rules ,
// use 'case class Message( var message:String,var status:Int)'
case class Message( message:String,status:Int)

object Message{
  val HELLO =  0
  val GOODBYE =  1
}