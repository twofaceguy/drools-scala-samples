import sbt._

object Dependencies {

  val resolutionRepos = Seq(
    "jboss" at "https://repository.jboss.org/nexus/content/repositories/public-jboss"
  )

  val droolsVersion = "6.2.0.Beta3"

  def compile(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")

  def provided(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")

  def test(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")

  def runtime(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")

  def container(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "container")

  val scalatest = "org.scalatest" %% "scalatest" % "2.2.0-RC1"
  val logback = "ch.qos.logback" % "logback-classic" % "1.1.2"
  val slf4jApi = "org.slf4j" % "slf4j-api" % "1.7.6"

  val droolsCompiler = "org.drools" % "drools-compiler" % droolsVersion
  val droolsCore = "org.drools" % "drools-core" % droolsVersion
  val droolsJsr94 = "org.drools" % "drools-jsr94" % droolsVersion
  val droolsDecisiontables = "org.drools" % "drools-decisiontables" % droolsVersion
  //val droolsReteoo = "org.drools" % "drools-reteoo" % droolsVersion

  val kieApi = "org.kie" % "kie-api" % droolsVersion

}
