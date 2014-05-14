import com.twitter.finagle.ThriftMux
import foo._

object Client extends App {

  val client = ThriftMux.newIface[Foo.FutureIface]("zk!localhost:2181!/foo")


  while(true) {
    Thread.sleep(1000)

    client.echo("Foo").onSuccess(res => println(res))
  }

}
