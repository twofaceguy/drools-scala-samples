package org.drools.games.numberguess

import java.util.Random

import org.kie.api.KieServices
import org.kie.api.runtime.{KieContainer, KieSession}

import scala.beans.BeanProperty

/**
 * Created by Simon on 2014/8/29
 */
object NumberGuessMain extends App {
  val kc: KieContainer = KieServices.Factory.get.getKieClasspathContainer
  val ksession: KieSession = kc.newKieSession("NumberGuessKS")
  ksession.insert(new GameRules(25, 5))

  ksession.insert(new Game)
  ksession.fireAllRules
}

case class Guess(@BeanProperty value: Int)

case class GameRules(@BeanProperty maxRange: Int, @BeanProperty allowedGuesses: Int)

class RandomNumber(v: Int) {
  @BeanProperty val value = new Random().nextInt(v)
}

case class Game(@BeanProperty var biggest: Int = 0, @BeanProperty var smallest: Int = 100, @BeanProperty var guessCount: Int = 0){
  def incrementGuessCount() {
    guessCount += 1
  }
}