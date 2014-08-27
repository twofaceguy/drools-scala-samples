import com.typesafe.sbt._
import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import org.sbtidea.SbtIdeaPlugin._
import sbt._
import sbt.Keys._


object BuildSettings {
  val VERSION = "0.1.0-SNAPSHOT"


  lazy val basicSettings = seq(
    version               := NightlyBuildSupport.buildVersion(VERSION),
    homepage              := Some(new URL("http://baoxian.com")),
    organization          := "com.baoxian.drools.demo",
    organizationHomepage  := Some(new URL("http://baoxian.com")),
    description           := "Baoxian.com Car Insure Biz",
    startYear             := Some(2013),
    //    licenses              := Seq("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt")),
    scalaVersion          := "2.10.4",
    resolvers             ++= Dependencies.resolutionRepos,
    scalacOptions         := Seq(
      "-encoding", "utf8",
      "-feature",
      "-unchecked",
      "-deprecation",
      "-target:jvm-1.6",
      "-language:postfixOps",
      "-language:implicitConversions",
      "-Xlog-reflective-calls",
      "-language:reflectiveCalls",
      "-language:existentials"
    ),
    ideaExcludeFolders := ".idea" :: ".idea_modules" :: "log" :: Nil,
    parallelExecution in Test := false
  )

  val boxedServiceSettings =
    seq(
      mainClass in (Compile,run) := Some("zzb.srvbox.BoxApp")
    )

  lazy val moduleSettings =
    basicSettings ++
      NightlyBuildSupport.settings ++
      net.virtualvoid.sbt.graph.Plugin.graphSettings ++
      seq(
        // scaladoc settings
        (scalacOptions in doc) <++= (name, version).map {
          (n, v) => Seq("-doc-title", n, "-doc-version", v)
        },

        // publishing
        crossPaths := false,
        publishMavenStyle := true,
        publishTo <<= version {
          version =>
            Some {
              "zzb nexus" at {
                // public uri is repo.spray.io, we use an SSH tunnel to the nexus here
                "http://maven.baoxan.org:8083/nexus/content/repositories/" + {
                  if (version.trim.endsWith("SNAPSHOT")) "baoxianSnapshots/"
                  else
                  if (NightlyBuildSupport.isNightly) "baoxianNightlies/" else "baoxian/"
                }
              }
            }
        }
      )

  lazy val noPublishing = seq(
    publish := (),
    publishLocal := ()
  )

  lazy val docsSettings = basicSettings ++ noPublishing ++ seq(
    unmanagedSourceDirectories in Test <<= baseDirectory {
      _ ** "code" get
    }
  )

  lazy val groupSettings = basicSettings ++ noPublishing

  lazy val exampleSettings = basicSettings ++ noPublishing

  lazy val exampleBoxedServiceSettings = exampleSettings ++ boxedServiceSettings


  lazy val formatSettings = SbtScalariform.scalariformSettings ++ Seq(
    ScalariformKeys.preferences in Compile := formattingPreferences,
    ScalariformKeys.preferences in Test    := formattingPreferences
  )

  import scalariform.formatter.preferences._

  def formattingPreferences =
    FormattingPreferences()
      .setPreference(RewriteArrowSymbols, false)
      .setPreference(AlignParameters, true)
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(DoubleIndentClassDeclaration, true)

}