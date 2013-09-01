sbt-about-plugins
=================

SBT plugin to show some details about plugins loaded

Requirements
------------

* sbt 0.12.x, 0.13.x

Installation
------------

To use **latest stable** version add this line to your **plugins.sbt**:

    addSbtPlugin("com.github.jozic" % "sbt-about-plugins" % "0.1.0")

To use the **latest snapshot** version, also add Sonatype snapshots repository resolver:

    resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

    addSbtPlugin("com.github.jozic" % "sbt-about-plugins" % "0.1.1-SNAPSHOT")
    
Alternatively you can declare the plugin in global configuration to make it available for all your projects:  
    for sbt 0.12.x add it to `~/.sbt/plugins/build.sbt`  
    for sbt 0.13.x add it to `~/.sbt/0.13/plugins/build.sbt`

Usage
-----

```
> about
[info] This is sbt 0.12.4
[info] The current project is {file:/home/jozic/projects/sbt-about-plugins/}sbt-about-plugins
[info] The current project is built against Scala 2.9.2
[info] Available Plugins: com.typesafe.sbt.SbtPgp, org.sbtidea.SbtIdeaPlugin, com.github.sbt.aboutplugins.AboutPluginsPlugin, com.timushev.sbt.updates.UpdatesPlugin, sbt.ScriptedPlugin, net.virtualvoid.sbt.cross.CrossPlugin
[info] sbt, sbt plugins, and build definitions are using Scala 2.9.2
> 
> about-plugins
[info] The following 6 plugins are loaded with this build:
[info] 	com.github.sbt.aboutplugins.AboutPluginsPlugin in module
[info] 		com.github.jozic:sbt-about-plugins:0.1.0-SNAPSHOT (sbtVersion=0.12, scalaVersion=2.9.2)
[info] 	com.timushev.sbt.updates.UpdatesPlugin in module
[info] 		com.timushev.sbt:sbt-updates:0.1.2 (sbtVersion=0.12, scalaVersion=2.9.2)
[info] 	com.typesafe.sbt.SbtPgp in module
[info] 		com.typesafe.sbt:sbt-pgp:0.8 (sbtVersion=0.12, scalaVersion=2.9.2)
[info] 	net.virtualvoid.sbt.cross.CrossPlugin in module
[info] 		net.virtual-void:sbt-cross-building:0.8.0 (sbtVersion=0.12, scalaVersion=2.9.2)
[info] 	org.sbtidea.SbtIdeaPlugin in module
[info] 		com.github.mpeltonen:sbt-idea:1.6.0-SNAPSHOT (sbtVersion=0.12, scalaVersion=2.9.2)
[info] 	sbt.ScriptedPlugin in module
[info] 		org.scala-sbt:scripted-plugin:0.12.4
> 
```
