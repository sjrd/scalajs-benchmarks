import org.scalajs.core.tools.sem.CheckedBehavior.Unchecked

val projectSettings = Seq(
  organization := "scalajs-benchmarks",
  version := "0.1-SNAPSHOT"
)

val defaultSettings = projectSettings ++ Seq(
  scalaVersion := "2.11.6",
  scalacOptions ++= Seq(
      "-deprecation",
      "-unchecked",
      "-feature",
      "-encoding", "utf8"
  ),
  scalaJSSemantics ~= { _.withAsInstanceOfs(Unchecked) },
  persistLauncher := true
)

lazy val parent = project.in(file(".")).
  settings(projectSettings: _*).
  settings(
    name := "Scala.js Benchmarks",
    publishArtifact in Compile := false
  ).aggregate(
    common,
    deltablue,
    richards,
    sudoku,
    raytrace
  )

lazy val common = project.
  enablePlugins(ScalaJSPlugin).
  settings(defaultSettings: _*).
  settings(
    name := s"Scala.js Benchmarks - Common",
    moduleName := "common",
    persistLauncher := false
  )

lazy val deltablue = project.
  enablePlugins(ScalaJSPlugin).
  settings(defaultSettings: _*).
  settings(
    name := s"Scala.js Benchmarks - DeltaBlue",
    moduleName := "deltablue"
  ).
  dependsOn(common)

lazy val richards = project.
  enablePlugins(ScalaJSPlugin).
  settings(defaultSettings: _*).
  settings(
    name := s"Scala.js Benchmarks - Richards",
    moduleName := "richards"
  ).
  dependsOn(common)

lazy val sudoku = project.
  enablePlugins(ScalaJSPlugin).
  settings(defaultSettings: _*).
  settings(
    name := s"Scala.js Benchmarks - Sudoku",
    moduleName := "sudoku"
  ).
  dependsOn(common)

lazy val raytrace = project.
  enablePlugins(ScalaJSPlugin).
  settings(defaultSettings: _*).
  settings(
    name := s"Scala.js Benchmarks - Ray Trace",
    moduleName := "raytrace"
  ).
  dependsOn(common)
