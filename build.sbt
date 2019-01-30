ThisBuild / organization := "de.swiesend"
ThisBuild / scalaVersion := "2.12.8"
ThisBuild / version      := "0.1.0"

lazy val root = (project in file("."))
  .settings(
    name := "scala-aes-cbc",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.5" % Test,
      "commons-codec" % "commons-codec" % "1.10"
    ),
  )
