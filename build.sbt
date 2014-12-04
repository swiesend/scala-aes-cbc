name := "scala-aes-cbc"

version := "0.0"

scalaVersion := "2.11.2"

resolvers ++= Seq(
    "RoundEights" at "http://maven.spikemark.net/roundeights"
)

libraryDependencies ++= Seq(
    "com.roundeights" %% "hasher" % "1.0.0"
)

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.2"

//libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.3.2"

libraryDependencies += "commons-codec" % "commons-codec" % "1.10"
