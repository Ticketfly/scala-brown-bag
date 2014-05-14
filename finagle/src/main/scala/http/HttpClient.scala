package http

import org.jboss.netty.handler.codec.http._
import com.twitter.util.{Await, Future}
import com.twitter.finagle._

object HttpClient extends App {

  val client: Service[HttpRequest, HttpResponse] = Http.newService("localhost:8080")

  val request =  new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/")

  while(true) {

    Thread.sleep(1000)

    val response: Future[HttpResponse] = client(request)

    response onSuccess { resp: HttpResponse =>
      println("GET success: " + resp)
    }

    Await.ready(response)
  }


}
