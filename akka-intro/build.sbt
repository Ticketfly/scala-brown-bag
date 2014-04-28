organization := "com.ticketfly"

name := "akka-intro"

version := "1.0"

scalaVersion := "2.11.0"

libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.3.2"
)

initialCommands in console :=
  """
    | import akkademo._
    | import akka.actor._
    | import scala.concurrent._
    | import scala.concurrent.duration._
    | import akka.util._
    | import akka.pattern._
    |
    | implicit val timeout = Timeout(2 seconds)
  """.stripMargin

