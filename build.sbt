name := "scala-aes-cbc"

version := "0.1.0"

scalaVersion := "2.11.8"

//
// Hasher dep + resolver
//
resolvers ++= Seq(
    "RoundEights" at "http://maven.spikemark.net/roundeights"
)
libraryDependencies += "com.roundeights" %% "hasher" % "1.0.0"

//
// Common utils
//
libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "3.0.0",
  "commons-codec" % "commons-codec" % "1.10"
)
