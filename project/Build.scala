import sbt._
import Keys._


object Build extends Build {

  import BuildSettings._
  import Dependencies._

  // configure prompt to show current project
  override lazy val settings = super.settings :+ {
    shellPrompt := {
      s => Project.extract(s).currentProject.id + " > "
    }
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Root Project
  // -------------------------------------------------------------------------------------------------------------------

  lazy val root = Project("drools-demo", file("."))
    .aggregate(droolsExamples)
    .dependsOn(droolsExamples) //加上对所有子模块的依赖
    .settings(basicSettings: _*)

  lazy val droolsExamples = Project("drools-scala-examples", file("drools-examples"))
    .settings(moduleSettings: _*)
    .settings(libraryDependencies ++=
    compile(slf4jApi,logback, droolsCore,droolsJsr94, droolsCompiler, kieApi) ++
      test(scalatest ) ++
      runtime(logback)
    )



}