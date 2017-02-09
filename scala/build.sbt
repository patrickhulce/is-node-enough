name := """my-server"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)


libraryDependencies += "com.sksamuel.scrimage" %% "scrimage-core" % "2.1.7"

libraryDependencies += "com.sksamuel.scrimage" %% "scrimage-io-extra" % "2.1.7"

libraryDependencies += "com.sksamuel.scrimage" %% "scrimage-filters" % "2.1.7"

libraryDependencies += "com.github.etaty" %% "rediscala" % "1.8.0"