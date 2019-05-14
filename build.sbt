name := """pg-sample-project"""
organization := "com.kschltz"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += javaJpa
libraryDependencies += javaJdbc
libraryDependencies ++= Seq(javaJdbc,
  "org.postgresql" % "postgresql" % "42.2.1",
  "org.projectlombok" % "lombok" % "1.18.8")