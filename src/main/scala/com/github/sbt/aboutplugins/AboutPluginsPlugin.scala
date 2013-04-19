package com.github.sbt.aboutplugins

import sbt._
import collection.Seq

object AboutPluginsPlugin extends Plugin {
  override lazy val settings = Seq(
    Keys.commands += aboutPluginsCommand
  )

  private lazy val aboutPluginsCommand = Command.command("about-plugins")(doCommand)

  def doCommand(state: State): State = {
    val structure = Project.extract(state).structure

    state.log.info("The following plugins are loaded with this build:")

    val pluginNames = structure.units.values.flatMap(un => un.unit.plugins.pluginNames).toSeq
    val artifactPaths: Seq[String] = for {
      name <- pluginNames
      source <- Option(Class.forName(name).getProtectionDomain.getCodeSource)
      location <- Option(source.getLocation)
    } yield location.getPath

    val reports: Seq[UpdateReport] = structure.units.values.flatMap(un => un.unit.plugins.pluginData.report).toSeq
    reports.foreach {
      report =>
        val plugins = report.configurations.flatMap(_.modules).distinct.filter {
          moduleReport => moduleReport.artifacts.exists {
            case (_, file) => artifactPaths.contains(file.getPath)
          }
        }.map(_.module).distinct

        pluginNames.zip(plugins).foreach {
          case (name, plugin) =>
            state.log.info("\t" + name + " in module " + plugin)
        }
    }
    state
  }
}