## To run examples from sbt:

sbt "runMain Server _port_"

sbt "runMain Client"


## To build single-jar distribution:

sbt assembly

java -cp target/scala-2.10/finagle-demo-assembly-1.0.jar Server _port_
java -cp target/scala-2.10/finagle-demo-assembly-1.0.jar Client

## To build zip distribution:

sbt universal:packageBin


* You have to have Zookeeper running 


