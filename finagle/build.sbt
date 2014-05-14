import sbt.Keys._
import AssemblyKeys._
import NativePackagerHelper._

name := "finagle-demo"

version := "1.0"

scalaVersion := "2.10.4"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:higherKinds")

resolvers += "twitter.com" at "http://maven.twttr.com/"

libraryDependencies ++= Seq(
  "com.twitter"       %% "scrooge-core"       % "3.14.1",
  "com.twitter"       %% "finagle-thriftmux"  % "6.15.0",
  "com.twitter"       %% "finagle-http"       % "6.15.0",
  "com.twitter"       %% "finagle-serversets" % "6.15.0",
  "org.apache.thrift" % "libthrift"           % "0.9.1"
)


assemblySettings

com.twitter.scrooge.ScroogeSBT.newSettings

packageArchetype.java_application

assemblyOption in assembly ~= { _.copy(prependShellScript = Some(defaultShellScript)) }

mergeStrategy in assembly <<= (mergeStrategy in assembly) {
  (old) => {
    case PathList("com", "twitter", "common", "args", xs@_*) => MergeStrategy.first
    case x => old(x)
  }
}

mainClass in Compile := Some("Server")

