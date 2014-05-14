## To run examples from sbt:

sbt "runMain Server <port>"

sbt "runMain Client"


## To build single-jar distribution:

sbt assembly

java -cp target/scala-2.10/finagle-demo-assembly-1.0.jar Server <port>
java -cp target/scala-2.10/finagle-demo-assembly-1.0.jar Client

## To build zip distribution:

sbt universal:packageBin


* You have to have Zookeeper running 


