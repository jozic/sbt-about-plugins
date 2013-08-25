import com.typesafe.sbt.pgp.PgpSettings
import sbt._
import Keys._

object AboutPluginsBuild extends Build with BuildExtra {
  lazy val root = Project("sbt-about-plugins", file("."), settings = mainSettings)

  lazy val mainSettings: Seq[Project.Setting[_]] =
    Defaults.defaultSettings ++
      CrossBuilding.scriptedSettings ++
      CrossBuilding.settings ++
      PgpSettings.projectSettings ++
      Seq(
        sbtPlugin := true,
        organization := "com.github.jozic",
        name := "sbt-about-plugins",
        version := "0.1.0-SNAPSHOT",
        publishTo <<= version {
          (v: String) =>
            val nexus = "https://oss.sonatype.org/"
            if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots")
            else Some("releases" at nexus + "service/local/staging/deploy/maven2")
        },
        publishMavenStyle := true,
        publishArtifact in Test := false,
        pomIncludeRepository := (_ => false),
        pomExtra := extraPom,
        scalacOptions ++= Seq("-deprecation", "-unchecked"),
        CrossBuilding.crossSbtVersions := Seq("0.12", "0.13"),
        PgpSettings.useGpg := true
      )

  def extraPom =
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
      </developers>
}