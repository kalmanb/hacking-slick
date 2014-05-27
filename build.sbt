organization := "com.kalmanb"

name := "hacking-slick"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.3",
  "org.scalaz" %% "scalaz-core" % "7.1.0-M7",
  "com.chuusai" %% "shapeless" % "2.0.0"
)

lazy val root = project.in(file(".")).dependsOn(testSpecs % "test->test")

lazy val testSpecs = RootProject(uri("git://github.com/kalmanb/scalatest-specs.git#0.1.1"))





