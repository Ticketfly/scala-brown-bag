package http

import com.twitter.finagle.{Http, Service}
import com.twitter.util.{Await, Future}
import org.jboss.netty.handler.codec.http._


object HttpServer extends App {

  val service = new Service[HttpRequest, HttpResponse] {

    override def apply(req: HttpRequest): Future[HttpResponse] = {
      println("Got "+ req)
      Future.value(new DefaultHttpResponse(req.getProtocolVersion, HttpResponseStatus.OK))
    }

  }
  val server = Http.serve(":8080", service)
  Await.ready(server)

}
