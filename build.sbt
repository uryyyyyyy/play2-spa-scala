name := """play2-spa-scala"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  "com.typesafe.slick" %% "slick" % "2.1.0",
  "com.typesafe.play" %% "play-slick" % "0.8.0",
  "com.amazonaws" % "aws-java-sdk" % "1.6.8",
  "org.scalatestplus" %% "play" % "1.1.0" % "test"
)
