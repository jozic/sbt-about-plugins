package com.github.sbt.aboutplugins

import sbt._
import collection.Seq
import sbt.Load.BuildStructure

object AboutPluginsPlugin extends Plugin {
  override lazy val settings = Seq(
    Keys.commands += aboutPluginsCommand
  )

  private lazy val aboutPluginsCommand = Command.command("about-plugins")(doCommand)

  def doCommand(state: State): State = {
    loadedPlugins2(state) match {
      case Seq() => state.log.info("Plugins list is empty...")
      case plugins =>
        state.log.info("The following " + plugins.size + " plugins are loaded with this build:")
        plugins foreach {
          case (name, module) =>
            state.log.info("\t" + name + " in module ")
            state.log.info("\t\t" + module.map(_.toString).getOrElse("NO MODULE FOUND"))
        }
    }
    state
  }

  def loadedPlugins(state: State): Seq[(String, ModuleID)] = {
    val structure: BuildStructure = Project.extract(state).structure
    val pluginNamesAndLoaders = structure.units.values.map(un => (un.unit.plugins.pluginNames, un.unit.plugins.loader)).toSeq
    state.log.debug(pluginNamesAndLoaders.mkString("\n"))

    val pluginArtifactPaths: Seq[(String, String)] = for {
      (names, loader) <- pluginNamesAndLoaders
      name <- names
      source <- Option(Class.forName(name, true, loader).getProtectionDomain.getCodeSource)
      location <- Option(source.getLocation)
    } yield (name, location.getPath)
    state.log.debug("plugin artifact paths:\n")
    state.log.debug(pluginArtifactPaths.mkString("\n"))

    val reports: Seq[UpdateReport] = structure.units.values.flatMap(un => un.unit.plugins.pluginData.report).toSeq
    reports.flatMap {
      report =>
        val moduleReports: Map[ModuleID, Seq[(Artifact, File)]] = (for {
          configReport <- report.configurations
          moduleReport <- configReport.modules
        } yield moduleReport.module -> moduleReport.artifacts).toMap

        val plugins: Seq[(String, ModuleID)] = for {
          (name, artifactPath) <- pluginArtifactPaths
          (module, artifacts) <- moduleReports if artifacts.exists {
          case (_, file) => artifactPath == file.getPath
        }
        } yield (name, module)
        plugins
    }.sortBy(_._1)
  }

  def loadedPlugins2(state: State): Seq[(String, Option[ModuleID])] = {
    val structure: BuildStructure = Project.extract(state).structure
    val pluginNamesAndLoaders = structure.units.values.map(un => (un.unit.plugins.pluginNames, un.unit.plugins.loader)).toSeq
    state.log.debug(pluginNamesAndLoaders.mkString("\n"))

    val pluginArtifactPaths: Seq[(String, String)] = for {
      (names, loader) <- pluginNamesAndLoaders
      name <- names
      source <- Option(Class.forName(name, true, loader).getProtectionDomain.getCodeSource)
      location <- Option(source.getLocation)
    } yield (name, location.getPath)
    state.log.debug("plugin artifact paths:\n")
    state.log.debug(pluginArtifactPaths.mkString("\n"))

    val reports: Seq[UpdateReport] = structure.units.values.flatMap(un => un.unit.plugins.pluginData.report).toSeq
    reports.flatMap {
      report =>
        val moduleReports: Map[ModuleID, Seq[(Artifact, File)]] = (for {
          configReport <- report.configurations
          moduleReport <- configReport.modules
        } yield moduleReport.module -> moduleReport.artifacts).toMap

        state.log.debug("moduleReports:\n")
        state.log.debug(moduleReports.mkString("\n\t"))

        val plugins: Seq[(String, Option[ModuleID])] = pluginArtifactPaths.map {
          case (name, artifactPath) => name -> moduleReports.collect {
            case (module, artifacts) if artifacts.exists {
              case (_, file) => artifactPath == file.getPath
            } => module
          }.headOption
        }
        plugins
    }.sortBy(_._1)
  }
}