
lazy val commonSettings = Seq(
  organization := "ch.megard",
  version := "0.1.5-SNAPSHOT",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation",
    "-target:jvm-1.8",
    "-encoding", "utf8"
  )
)

lazy val publishSettings = Seq(
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },

  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },

  pomExtra := {
    <url>https://github.com/lomigmegard/akka-http-cors</url>
    <licenses>
      <license>
        <name>Apache 2</name>
        <url>https://www.apache.org/licenses/LICENSE-2.0.txt</url>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:lomigmegard/akka-http-cors.git</url>
      <connection>scm:git:git@github.com:lomigmegard/akka-http-cors.git</connection>
    </scm>
    <developers>
      <developer>
        <id>lomigmegard</id>
        <name>Lomig Mégard</name>
        <url>http://lomig.megard.ch</url>
      </developer>
    </developers>
  }
)

lazy val dontPublishSettings = Seq(
  //publishSigned := (),
  publish := (),
  publishLocal := (),
  publishArtifact := false
)

lazy val root = (project in file(".")).
  aggregate(cors, benchJmh).
  settings(commonSettings: _*).
  settings(dontPublishSettings: _*)

lazy val cors = Project(id = "akka-http-cors", base = file("akka-http-cors")).
  settings(commonSettings: _*).
  settings(publishSettings: _*).
  settings(
    libraryDependencies += "com.typesafe.akka" %% "akka-http-experimental" % "2.4.8",
    libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit" % "2.4.8" % "test",
    libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"
  )

lazy val benchJmh = Project(id = "akka-http-cors-bench-jmh", base = file("akka-http-cors-bench-jmh")).
  dependsOn(cors).
  enablePlugins(JmhPlugin).
  settings(commonSettings: _*).
  settings(dontPublishSettings: _*)
