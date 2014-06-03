import sbt._
import Keys._

import scala.scalajs.sbtplugin._
import ScalaJSPlugin._
import ScalaJSKeys._

object ScalaJSBenchmarks extends Build {

  val scalaJSScalaVersion = "2.11.1"

  val projectSettings = Defaults.defaultSettings ++ Seq(
      organization := "scalajs-benchmarks",
      version := "0.1-SNAPSHOT"
  )

  val defaultSettings = projectSettings ++ scalaJSSettings ++ Seq(
      scalaVersion := scalaJSScalaVersion,
      scalacOptions ++= Seq(
          "-deprecation",
          "-unchecked",
          "-feature",
          "-encoding", "utf8"
      ),
      persistLauncher := true
  )

  lazy val parent: Project = Project(
      id = "parent",
      base = file("."),
      settings = projectSettings ++ Seq(
          name := "Scala.js Benchmarks",
          publishArtifact in Compile := false,

          clean := clean.dependsOn(
	     clean in common,
	     clean in deltablue,
	     clean in richards,
	     clean in sudoku,
	     clean in tracer
	  ).value
      )
  ).aggregate(
      common,
      deltablue,
      richards,
      sudoku,
      tracer
  )

  lazy val common = project("Common", defaultSettings)
  lazy val deltablue = project("DeltaBlue", defaultSettings).dependsOn(common)
  lazy val richards = project("Richards", defaultSettings).dependsOn(common)
  lazy val sudoku = project("Sudoku", defaultSettings).dependsOn(common)
  lazy val tracer = project("Tracer", defaultSettings).dependsOn(common)

  def project(id: String, settings: Seq[sbt.Def.Setting[_]]) = Project(
      id = id.toLowerCase,
      base = file(id.toLowerCase),
      settings = settings ++ Seq(
          name := s"Scala.js Benchmarks - $id",
          moduleName := id.toLowerCase
      )
  )
}
