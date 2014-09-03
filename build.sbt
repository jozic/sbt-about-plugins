sbtPlugin := true

name := "sbt-about-plugins"

organization := "com.github.jozic"

scalaVersion := "2.10.4"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-Xlint", "-Xfatal-warnings")

javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

CrossBuilding.crossSbtVersions := Seq("0.12", "0.13")