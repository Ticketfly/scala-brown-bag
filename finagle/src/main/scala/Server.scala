import com.twitter.finagle.ThriftMux
import com.twitter.util.{Await, Future}
import foo._

object Server extends App {
  args =>

  val port = args(0)

  val server = ThriftMux.serveIface(s":$port", new Foo[Future]{

    def echo(v: String)  = Future {
      println("Got "+ v)

      "Hello " + v
    }

  })

  server.announce("zk!localhost:2181!/foo!1")

  Await.ready(server)


}
