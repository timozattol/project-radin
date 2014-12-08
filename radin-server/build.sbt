name := """radin-server"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

scalacOptions += "-feature"

libraryDependencies ++= Seq(
  anorm,
  cache,
  ws,
  "com.typesafe.play" %% "play-slick" % "0.8.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "ws.securesocial" % "securesocial_2.11" % "3.0-M1",
  "org.xerial" % "sqlite-jdbc" % "3.7.2",
  jdbc
)
