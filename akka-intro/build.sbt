organization := "com.ticketfly"

name := "akka-intro"

version := "1.0"

scalaVersion := "2.11.0"

libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.3.2"
)

initialCommands in console := "import akkademo._"

