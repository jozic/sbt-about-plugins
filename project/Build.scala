import sbt._
import Keys._

object AboutPluginsBuild extends Build with BuildExtra {
  lazy val root = Project("sbt-about-plugins", file("."), settings = mainSettings)

  lazy val mainSettings: Seq[Project.Setting[_]] = Defaults.defaultSettings ++ ScriptedPlugin.scriptedSettings ++ Seq(
    sbtPlugin := true,
    organization := "com.github.jozic",
    name := "sbt-about-plugins",
    version := "0.1.0-SNAPSHOT",
    publishTo := Some(Resolver.file("Github Pages", Path.userHome / "git" / "jozic.github.com" / "maven" asFile)(Patterns(true, Resolver.mavenStyleBasePattern))),
    publishTo <<= version { (v: String) =>
      val nexus = "https://oss.sonatype.org/"
      if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots")
      else Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := (_ => false),
    pomExtra := extraPom,
    resolvers ++= Seq(
      Classpaths.typesafeSnapshots,
      "Github Repo" at "http://jozic.github.com/maven"
    ),
    scalacOptions ++= Seq("-deprecation", "-unchecked"),
    crossScalaVersions := Seq("2.9.2", "2.9.3", "2.10.0", "2.10.1"),
    libraryDependencies ++= Seq()
  ) 

  def extraPom = (
    <url>http://github.com/jozic/sbt-about-plugins</url>
    <licenses>
      <license>
        <name>BSD-style</name>
        <url>http://www.opensource.org/licenses/BSD-3-Clause</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:jozic/sbt-about-plugins.git</url>
      <connection>scm:git:git@github.com:jozic/sbt-about-plugins.git</connection>
    </scm>
    <developers>
      <developer>
      <id>jozic</id>
      <name>Eugene Platonov</name>
      <url>http://github.com/jozic</url>
    </developer>
  </developers>)
}
