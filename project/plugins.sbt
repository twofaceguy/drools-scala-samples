resolvers += Classpaths.typesafeResolver

resolvers += "sbt-idea" at "http://mpeltonen.github.com/maven/"

resolvers += "BaoXian Repository" at "http://maven.baoxan.org:8083/nexus/content/groups/public/"

resolvers += "Typesafe Repository" at "http://repo.akka.io/releases/"

resolvers += "Spray Repository" at "http://repo.spray.io/"

libraryDependencies ++= Seq(
  "com.decodified" % "scala-ssh" % "0.6.2",
  "com.jcraft" % "jzlib" % "1.1.1",
  "org.kamranzafar" % "jtar" % "2.2",
  "org.slf4j" % "slf4j-nop" % "1.7.5"
)

addSbtPlugin("com.typesafe.sbt" % "sbt-multi-jvm" % "0.3.4")

addSbtPlugin("com.typesafe.akka" % "akka-sbt-plugin" % "2.2.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "1.0.1")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.0")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

addSbtPlugin("com.baoxian.sbt" %% "sbt-box" % "0.1-RC13")

